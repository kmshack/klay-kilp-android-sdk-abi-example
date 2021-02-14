package com.klipwallet.app2app.api;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.klipwallet.app2app.api.request.CardTxRequest;
import com.klipwallet.app2app.api.response.KlipResponse;
import com.klipwallet.app2app.common.KlipContextService;
import com.klipwallet.app2app.exception.KlipRequestException;
import com.klipwallet.app2app.util.Logger;
import com.klipwallet.app2app.network.NetworkService;
import com.klipwallet.app2app.exception.KlipErrorCode;
import com.klipwallet.app2app.network.Utility;
import com.klipwallet.app2app.api.request.AuthRequest;
import com.klipwallet.app2app.api.request.model.BAppInfo;
import com.klipwallet.app2app.api.request.ContractTxRequest;
import com.klipwallet.app2app.api.request.KlayTxRequest;
import com.klipwallet.app2app.internal.PrepareRequest;
import com.klipwallet.app2app.internal.Request;
import com.klipwallet.app2app.api.request.KlipRequest;
import com.klipwallet.app2app.api.response.CardListResponse;
import com.klipwallet.app2app.api.request.TokenTxRequest;

import java.io.UnsupportedEncodingException;

/**
 * Klip API 제공하는 Class
 */
public class Klip {
    /**
     * Klip 인스턴스를 가져온다.
     * @param context Application 컨텍스트
     * @return Klip 인스턴스
     */
    public synchronized static Klip getInstance(Context context) {
        if(instance == null) {
            instance = new Klip(context, NetworkService.Factory.getInstance());
            KlipContextService.getInstance().initialize(context);
        }
        return instance;
    }

    private Context context;
    private static Klip instance;
    private NetworkService networkService;

    Klip(Context context, NetworkService networkService) {
        this.context = context;
        this.networkService = networkService;
    }

    /**
     * Klip 요청 준비를 한다. (Request Key 획득)
     * @param request 요청하는 실행 정보
     * @param bappInfo 요청하는 BApp 정보
     * @param callback 요청 결과를 받는 Callback (이때, T값은 KlipResponse을 사용)
     * @throws KlipRequestException 입력 데이터가 유효하지 않을 경우 발생
     */
    public void prepare(final KlipRequest request, final BAppInfo bappInfo, final KlipCallback callback) throws KlipRequestException {
        if(request == null || bappInfo == null || callback == null) {
            throw new KlipRequestException(KlipErrorCode.CORE_PARAMETER_MISSING, "required parameters is missing");
        }

        PrepareRequest req = new PrepareRequest();
        req.setBapp(bappInfo);

        if (request instanceof AuthRequest) {
            req.setType(KlipRequest.AUTH);
        } else if (request instanceof KlayTxRequest) {
            req.setType(KlipRequest.SEND_KLAY);
            req.setTransaction(request);
        } else if (request instanceof TokenTxRequest) {
            req.setType(KlipRequest.SEND_TOKEN);
            req.setTransaction(request);
        } else if (request instanceof CardTxRequest) {
            req.setType(KlipRequest.SEND_CARD);
            req.setTransaction(request);
        } else if (request instanceof ContractTxRequest) {
            req.setType(KlipRequest.EXECUTE_CONTRACT);
            req.setTransaction(request);
        } else {
            throw new KlipRequestException(KlipErrorCode.NOT_SUPPORTED_REQUEST_TYPE, "not supported request type");
        }

        networkService.execute(getApiURL(KlipProtocol.API_PREPARE_URL), KlipProtocol.API_PREPARE_METHOD, req.toJson().toString(), KlipResponse.getConverter(), callback);
    }

    /**
     * Klip 요청 실행을 한다. (Deep Link 사용)
     * @param requestKey 요청 키
     * @throws KlipRequestException 입력 데이터가 유효하지 않을 경우 발생
     */
    public void request(String requestKey) throws KlipRequestException {
        if(requestKey == null || requestKey.length() == 0) {
            throw new KlipRequestException(KlipErrorCode.CORE_PARAMETER_MISSING, "requestKey is required");
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (!isKakaoTalkInstalled()) {
            Logger.info("kakaotalk is not installed. it's trying to open application market.");
            intent.setData(Uri.parse("market://details?id=" + KlipProtocol.KAKAO_PACKAGE + "&referrer=" + KlipProtocol.INSTALL_REFERRER));
        } else if(!isAvailable()) {
            Logger.info("kakaotalk is not supported. it's trying to open application market to updated to the latest version.");
            intent.setData(Uri.parse("market://details?id=" + KlipProtocol.KAKAO_PACKAGE + "&referrer=" + KlipProtocol.INSTALL_REFERRER));
        } else {
            intent.setData(Uri.parse("kakaotalk://klipwallet/open?url=" + getLinkURL() + requestKey));
        }
        context.startActivity(intent);
    }

    /**
     * Klip 요청에 대한 실행 결과 데이터를 가져온다.
     * @param requestKey 요청 키
     * @param callback 요청 결과를 받는 Callback
     * @throws KlipRequestException 입력 데이터가 유효하지 않을 경우 발생
     */
    public void getResult(final String requestKey, final KlipCallback callback) throws KlipRequestException {
        if(requestKey == null || requestKey.length() == 0 || callback == null) {
            throw new KlipRequestException(KlipErrorCode.CORE_PARAMETER_MISSING, "required parameters is missing");
        }

        Request req = new Request();
        req.setRequestKey(requestKey);

        try {
            final String getParams = Utility.getQueryString(req.toMap(), "UTF-8");
            networkService.execute(getApiURL(KlipProtocol.API_RESULT_URL + "?" + getParams), KlipProtocol.API_RESULT_METHOD, null, KlipResponse.getConverter(), callback);
        } catch (UnsupportedEncodingException ignore) {
            // ignore
        }
    }

    /**
     * 사용자가 보유한 카드 정보 목록을 가져온다.
     * @param cardAddress 조회하는 카드 주소
     * @param userAddress 조회하는 사용자 주소
     * @param cursor 조회하는 시작 커서
     * @param callback 요청 결과를 받는 Callback (이때, T값은 CardListResponse를 사용)
     * @throws KlipRequestException 입력 데이터가 유효하지 않을 경우 발생
     */
    public void getCardList(final String cardAddress, final String userAddress, final String cursor, final KlipCallback callback) throws KlipRequestException
    {
        if(cardAddress == null || cardAddress.length() == 0 ||
                userAddress == null  || userAddress.length() == 0 || callback == null) {
            throw new KlipRequestException(KlipErrorCode.CORE_PARAMETER_MISSING, "required parameters is missing");
        }

        String getParams = ""
                + "sca=" + cardAddress
                + "&eoa=" + userAddress;
        if(cursor != null && cursor.length() > 0)
            getParams += "&cursor=" + cursor;
        networkService.execute(getApiURL(KlipProtocol.API_GET_CARD_URL + "?" + getParams), KlipProtocol.API_GET_CARD_METHOD, null, CardListResponse.getConverter(), callback);
    }

    private String getApiURL(String path) {
        return KlipProtocol.SCHEME + "://" + KlipProtocol.apiAuthority() + KlipProtocol.API_VERSION + path;
    }

    private String getLinkURL() {
        return KlipProtocol.SCHEME + "://" + KlipProtocol.linkAuthority() + KlipProtocol.KAKAO_KLIP_LINK;
    }

    /**
     * KakaoTalk 설치 여부를 확인한다.
     * @return KakaoTalk 설치 여부
     */
    public boolean isKakaoTalkInstalled() {
        return isPackageInstalled(KlipProtocol.KAKAO_PACKAGE);
    }

    private boolean isPackageInstalled(final String packageName) {
        return context.getPackageManager().getLaunchIntentForPackage(packageName) != null;
    }

    /**
     * 설치된 KakaoTalk에서 Klip 지원 여부를 확인한다.
     * @return KakaoTalk의 Klip 지원 여부
     */
    public boolean isAvailable() {
        return isSupportedKlipWallet(KlipProtocol.KAKAO_PACKAGE, KlipProtocol.KAKAO_KLIP_SUPPORT_VERSION);
    }

    private boolean isSupportedKlipWallet(final String packageName, final int minVersion) {
        PackageInfo talkPackageInfo;
        try {
            talkPackageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);
            return talkPackageInfo.versionCode >= minVersion;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
