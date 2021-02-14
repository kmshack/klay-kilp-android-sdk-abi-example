package com.klipwallet.app2app.api;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.robolectric.android.util.concurrent.RoboExecutorService;

import com.klipwallet.app2app.api.request.KlipRequest;
import com.klipwallet.app2app.api.response.CardListResponse;
import com.klipwallet.app2app.api.response.KlipResponse;
import com.klipwallet.app2app.common.KlipTestCase;
import com.klipwallet.app2app.exception.KlipErrorCode;
import com.klipwallet.app2app.exception.KlipRequestException;
import com.klipwallet.app2app.mocks.TestNetworkService;
import com.klipwallet.app2app.api.request.AuthRequest;
import com.klipwallet.app2app.api.request.model.BAppInfo;
import com.klipwallet.app2app.api.response.KlipErrorResponse;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class KlipTest extends KlipTestCase {
    private Context ctx;
    private Klip klip;

    @Before
    public void setup() {
        ctx = mock(Context.class);
        klip = spy(new Klip(ctx, new TestNetworkService(new Handler(Looper.getMainLooper()), new RoboExecutorService())));
    }

    @Test
    public void prepare() throws KlipRequestException {
        AuthRequest req = new AuthRequest();
        BAppInfo bAppInfo = new BAppInfo("Test BApp");
        KlipCallback<KlipResponse> callback = spy(new KlipCallback<KlipResponse>() {
            @Override
            public void onSuccess(KlipResponse response) {
            }

            @Override
            public void onFail(KlipErrorResponse code) {
            }
        });
        klip.prepare(req, bAppInfo, callback);
        verify(callback).onSuccess(null);
    }

    @Test
    public void prepare_Fail() {
        AuthRequest req = new AuthRequest();
        BAppInfo bAppInfo = new BAppInfo("Test BApp");
        KlipCallback<KlipResponse> callback = mock(KlipCallback.class);

        Map<String, Object[]> testCase = new HashMap<>();
        testCase.put("request is null", new Object[]{null, bAppInfo, callback});
        testCase.put("bappInfo is null", new Object[]{req, null, callback});
        testCase.put("callback is null", new Object[]{req, bAppInfo, null});

        for(String key : testCase.keySet()) {
            boolean thrown = false;
            Object[] inputs = testCase.get(key);
            try {
                klip.prepare((KlipRequest) inputs[0], (BAppInfo) inputs[1], (KlipCallback) inputs[2]);
            } catch (KlipRequestException e) {
                thrown = true;
                Assert.assertEquals(KlipErrorCode.CORE_PARAMETER_MISSING, e.getErrorCode());
            }
            Assert.assertTrue(thrown);
        }
    }

    @Test
    public void request() throws KlipRequestException {
        doReturn(false).when(klip).isKakaoTalkInstalled();
        klip.request("request-key-1234");
        verify(ctx).startActivity(any(Intent.class));
    }

    @Test
    public void request_Fail() {
        Map<String, String> testCase = new HashMap<>();
        testCase.put("request key is null", null);
        testCase.put("request key's length is 0", "");

        for(String key : testCase.keySet()) {
            boolean thrown = false;
            String input = testCase.get(key);
            try {
                klip.request(input);
            } catch (KlipRequestException e) {
                thrown = true;
                Assert.assertEquals(KlipErrorCode.CORE_PARAMETER_MISSING, e.getErrorCode());
            }
            Assert.assertTrue(thrown);
        }
    }

    @Test
    public void getResult() throws KlipRequestException {
        KlipCallback<KlipResponse> callback = spy(new KlipCallback<KlipResponse>() {
            @Override
            public void onSuccess(KlipResponse response) {
            }

            @Override
            public void onFail(KlipErrorResponse code) {
            }
        });
        klip.getResult("request-key-1234", callback);
        verify(callback).onSuccess(null);
    }

    @Test
    public void getResult_Fail() {
        KlipCallback<KlipResponse> callback = mock(KlipCallback.class);

        Map<String, Object[]> testCase = new HashMap<>();
        testCase.put("request key is null", new Object[]{null, callback});
        testCase.put("request key's length is 0", new Object[]{"", callback});
        testCase.put("callback is null", new Object[]{"request key", null});

        for(String key : testCase.keySet()) {
            boolean thrown = false;
            Object[] inputs = testCase.get(key);
            try {
                klip.getResult((String)inputs[0], (KlipCallback)inputs[1]);
            } catch (KlipRequestException e) {
                thrown = true;
                Assert.assertEquals(KlipErrorCode.CORE_PARAMETER_MISSING, e.getErrorCode());
            }
            Assert.assertTrue(thrown);
        }
    }

    @Test
    public void getCardList() throws KlipRequestException {
        KlipCallback<CardListResponse> callback = spy(new KlipCallback<CardListResponse>() {
            @Override
            public void onSuccess(CardListResponse response) {
            }

            @Override
            public void onFail(KlipErrorResponse code) {
            }
        });
        klip.getCardList("card address", "user address", null, callback);
        verify(callback).onSuccess(null);
    }

    @Test
    public void getCardList_Fail() {
        KlipCallback<CardListResponse> callback = mock(KlipCallback.class);

        Map<String, Object[]> testCase = new HashMap<>();
        testCase.put("card address is null", new Object[]{null, "user address", null, callback});
        testCase.put("card address's length is 0", new Object[]{"", "user address", null, callback});
        testCase.put("user address is null", new Object[]{"card address", null, null, callback});
        testCase.put("user address's length is 0", new Object[]{"card address", "", null, callback});
        testCase.put("callback is null", new Object[]{"card address", "user address", null, null});

        for(String key : testCase.keySet()) {
            boolean thrown = false;
            Object[] inputs = testCase.get(key);
            try {
                klip.getCardList((String) inputs[0], (String) inputs[1], (String) inputs[2], (KlipCallback)inputs[3]);
            } catch (KlipRequestException e) {
                thrown = true;
                Assert.assertEquals(KlipErrorCode.CORE_PARAMETER_MISSING, e.getErrorCode());
            }
            Assert.assertTrue(thrown);
        }
    }
}
