package com.klipwallet.app2app.api;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.klipwallet.app2app.api.request.AuthRequest;
import com.klipwallet.app2app.api.request.CardTxRequest;
import com.klipwallet.app2app.api.request.ContractTxRequest;
import com.klipwallet.app2app.api.request.KlayTxRequest;
import com.klipwallet.app2app.api.request.KlipRequest;
import com.klipwallet.app2app.api.request.TokenTxRequest;
import com.klipwallet.app2app.api.request.model.BAppInfo;
import com.klipwallet.app2app.api.response.CardListResponse;
import com.klipwallet.app2app.api.response.KlipErrorResponse;
import com.klipwallet.app2app.api.response.KlipResponse;
import com.klipwallet.app2app.api.response.model.Card;
import com.klipwallet.app2app.common.KlipContextService;
import com.klipwallet.app2app.common.KlipPhase;
import com.klipwallet.app2app.exception.KlipRequestException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@RunWith(AndroidJUnit4.class)
public class KlipTest {
    private final String senderAddress = "0x120E120bB50fD36847FA93197217d956b6ad1E93"; // test account A
    private final String receiverAddress = "0x2d9aea5038ca7efac0ebe1b627104c7ece1a3d1a"; // test account B
    private final String tokenContractAddress = "0x9a8d41a4CB5F8ff42954D7A667a7a84a55a3CEc8";
    private final String cardContractAddress = "0xcda6be802fa1f6861a5a574f5e349ea2b3f2d175";
    private final String cardContractAbi = "{\"constant\":false,\"inputs\":[{\"name\":\"from\",\"type\":\"address\"},{\"name\":\"to\",\"type\":\"address\"},{\"name\":\"tokenId\",\"type\":\"uint256\"}],\"name\":\"safeTransferFrom\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"}";

    private Context context;
    private Klip klip;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
        KlipContextService.getInstance().initialize(context);
        Assert.assertEquals(KlipContextService.getInstance().getPhaseInfo().getPhase(), KlipPhase.DEV);

        klip = Klip.getInstance(context);
    }

    @Test
    public void testPrepare() throws InterruptedException, KlipRequestException {
        Map<String, KlipResponse> expectedResponse = new HashMap<>();
        CountDownLatch prepareSignal = new CountDownLatch(4);

        BAppInfo bapp = new BAppInfo("bapp name");
        KlipCallback<KlipResponse> callback = new KlipCallback<KlipResponse>() {
            @Override
            public void onSuccess(KlipResponse response) {
                Assert.assertNotNull(response.getRequestKey());
                Assert.assertNotNull(response.getExpirationTime());
                Assert.assertNotNull(response.getStatus());

                expectedResponse.put(response.getRequestKey(), response);
                prepareSignal.countDown();
            }

            @Override
            public void onFail(KlipErrorResponse code) {
                Assert.fail();
                prepareSignal.countDown();
            }
        };

        // Prepare - Auth 테스트
        requestAuth(bapp, callback);

        // Prepare - Send KLAY 테스트
        requestSendKLAY(bapp, callback);

        // Prepare - Send Card 테스트
        requestSendCard(bapp, callback);

        // Prepare - Execute Contract 테스트
        requestExecuteContract(bapp, callback);

        prepareSignal.await();

        CountDownLatch resultSignal = new CountDownLatch(4);
        callback = new KlipCallback<KlipResponse>() {
            @Override
            public void onSuccess(KlipResponse response) {
                KlipResponse exepectedResponse = expectedResponse.get(response.getRequestKey());

                Assert.assertEquals(exepectedResponse.getRequestKey(), response.getRequestKey());
                Assert.assertEquals(exepectedResponse.getExpirationTime(), response.getExpirationTime());
                Assert.assertEquals(exepectedResponse.getStatus(), response.getStatus());
                resultSignal.countDown();
            }

            @Override
            public void onFail(KlipErrorResponse code) {
                Assert.fail();
                resultSignal.countDown();
            }
        };

        // GetResult 테스트
        for(String requestKey : expectedResponse.keySet()) {
            klip.getResult(requestKey, callback);
        }

        resultSignal.await();
    }

    @Test
    public void testPrepare_Fail() throws KlipRequestException, InterruptedException {
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(senderAddress);
        params.add(receiverAddress);
        params.add("1");

        Map<String, KlipRequest> testRequests = new HashMap<>();
        testRequests.put("klay request - from(wrong address)", new KlayTxRequest.Builder().to(receiverAddress).amount("1").from("wrong address").build());
        testRequests.put("token request - from(wrong address)", new TokenTxRequest.Builder().contract(tokenContractAddress).to(receiverAddress).amount("1").from("wrong address").build());
        testRequests.put("card request - from(wrong address)", new CardTxRequest.Builder().contract(cardContractAddress).to(receiverAddress).cardId("1").from("wrong address").build());
        // TODO : remove annotation after completing the task from klip server
//        testRequests.put("contract request - from(wrong address)", new ContractTxRequest.Builder().to(cardContractAddress).value("0").abi(cardContractAbi).params(params).from("wrong address").build());

        CountDownLatch prepareSignal = new CountDownLatch(testRequests.size());

        BAppInfo bapp = new BAppInfo("bapp name");
        KlipCallback<KlipResponse> callback = new KlipCallback<KlipResponse>() {
            @Override
            public void onSuccess(KlipResponse response) {
                Assert.fail();
            }

            @Override
            public void onFail(KlipErrorResponse code) {
                // error code : 400 (invalid from address)
                Assert.assertEquals(400, code.getHttpStatus());
                Assert.assertEquals(6411, code.getErrorCode());
                prepareSignal.countDown();
            }
        };

        for(String key : testRequests.keySet()) {
            klip.prepare(testRequests.get(key), bapp, callback);
        }
        prepareSignal.await();
    }

    @Test
    public void testGetCardList() throws KlipRequestException, InterruptedException {
        CountDownLatch signal = new CountDownLatch(1);

        KlipCallback<CardListResponse> callback = new KlipCallback<CardListResponse>() {
            @Override
            public void onSuccess(CardListResponse response) {
                Assert.assertNotNull(response.getName());
                Assert.assertNotNull(response.getSymbolImg());
                Assert.assertNotNull(response.getCards());
                {
                    // this test can progress in case sender account have one card at least.
                    Card card = response.getCards().get(0);
                    Assert.assertNotEquals(0, card.getCreatedAt()); // it's check if the value is initialize
                    Assert.assertNotEquals(0, card.getUpdatedAt()); // it's check if the value is initialize
                    Assert.assertNotNull(card.getOwner());
                    Assert.assertNotNull(card.getSender());
                    Assert.assertNotEquals(0, card.getCardId()); // it's check if the value is initialize
                    Assert.assertNotNull(card.getCardUri());
                    Assert.assertNotNull(card.getTransactionHash());
                }
                Assert.assertNotNull(response.getNextCursor());
                signal.countDown();
            }

            @Override
            public void onFail(KlipErrorResponse code) {
                Assert.fail();
                signal.countDown();
            }
        };

        // Get Card List 테스트
        klip.getCardList(cardContractAddress, senderAddress, null, callback);

        signal.await();
    }

    private void requestAuth(BAppInfo bapp, KlipCallback callback) throws KlipRequestException {
        KlipRequest authReq = new AuthRequest();
        klip.prepare(authReq, bapp, callback);
    }

    private void requestSendKLAY(BAppInfo bapp, KlipCallback callback) throws KlipRequestException {
        KlipRequest klayReq = new KlayTxRequest.Builder()
                .to(receiverAddress)
                .amount("1")
                .build();
        klip.prepare(klayReq, bapp, callback);
    }

    private void requestSendCard(BAppInfo bapp, KlipCallback callback) throws KlipRequestException {
        KlipRequest cardReq = new CardTxRequest.Builder()
                .contract(cardContractAddress)
                .to(receiverAddress)
                .cardId("1")
                .build();
        klip.prepare(cardReq, bapp, callback);
    }

    private void requestExecuteContract(BAppInfo bapp, KlipCallback callback) throws KlipRequestException {
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(senderAddress);
        params.add(receiverAddress);
        params.add("1");
        KlipRequest req = new ContractTxRequest.Builder()
                .to(cardContractAddress)
                .value("0")
                .abi(cardContractAbi)
                .params(params)
                .build();
        klip.prepare(req, bapp, callback);
    }
}