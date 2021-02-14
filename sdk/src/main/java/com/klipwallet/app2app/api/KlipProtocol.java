package com.klipwallet.app2app.api;

import com.klipwallet.app2app.common.KlipContextService;
import com.klipwallet.app2app.common.KlipPhaseInfo;

/**
 * Klip API 지원하기 위한 Klip Protocol 관련 Class
 */
public class KlipProtocol {

    // KakaoTalk 관련
    public static final String KAKAO_PACKAGE = "com.kakao.talk";
    public static final int KAKAO_KLIP_SUPPORT_VERSION = 1908750;

    // Klip Client 관련
    public static final String KAKAO_KLIP_LINK = "/?target=/a2a?request_key=";
    public static final String INSTALL_REFERRER = "klipwallet";

    // Klip Server 관련
    public static final String SCHEME = "https";
    public static final String API_VERSION = "/v2";

    public static final String API_PREPARE_METHOD = "POST";
    public static final String API_PREPARE_URL = "/a2a/prepare";
    public static final String API_RESULT_METHOD = "GET";
    public static final String API_RESULT_URL = "/a2a/result";
    public static final String API_GET_CARD_METHOD = "GET";
    public static final String API_GET_CARD_URL = "/a2a/cards";

    // Klip Server Request Field
    public static final String CONTRACT = "contract";
    public static final String TO = "to";
    public static final String FROM = "from";
    public static final String CARD_ID = "card_id";
    public static final String VALUE = "value";
    public static final String ABI = "abi";
    public static final String PARAMS = "params";
    public static final String AMOUNT = "amount";

    public static final String BAPP = "bapp";
    public static final String TRANSACTION = "transaction";
    public static final String TYPE = "type";

    public static final String BAPP_NAME = "name";
    public static final String BAPP_CALLBACK = "callback";
    public static final String BAPP_SUCCESS_URL = "success";
    public static final String BAPP_FAIL_URL = "fail";

    // Klip Server Response Field
    public static final String NAME = "name";
    public static final String SYMBOL_IMG = "symbol_img";
    public static final String CARDS = "cards";
    public static final String NEXT_CURSOR = "next_cursor";

    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    public static final String OWNER = "owner";
    public static final String SENDER = "sender";
    public static final String CARD_URI = "card_uri";
    public static final String TRANSACTION_HASH = "transaction_hash";

    public static final String REQUEST_KEY = "request_key";
    public static final String EXPIRATION_TIME = "expiration_time";
    public static final String STATUS = "status";
    public static final String RESULT = "result";
    public static final String ERROR = "error";

    public static final String TX_HASH = "tx_hash";
    public static final String KLAYTN_ADDRESS = "klaytn_address";

    public static final String CODE = "code";
    public static final String MESSAGE = "message";

    public static final String ERR = "err";

    // Klip 세팅
    public static final String PHASE = "com.klipwallet.app2app.Phase";

    public static String apiAuthority() {
        return "a2a-api.klipwallet.com";
    }

    public static String linkAuthority() {
        return "klipwallet.com";
    }
}
