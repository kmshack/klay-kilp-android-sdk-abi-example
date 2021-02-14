package com.klipwallet.app2app.api.response;

import org.junit.Test;

import com.klipwallet.app2app.common.KlipTestCase;
import com.klipwallet.app2app.exception.KlipResponseException;
import com.klipwallet.app2app.network.response.ResponseStringConverter;

public class KlipResponseTest extends KlipTestCase {

    @Test
    public void convert() {
        String res = "{\"request_key\":\"my request key\", \"expiration_time\":\"my expiration time\", \"status\":\"my status\"}";
        ResponseStringConverter<KlipResponse> convert = KlipResponse.getConverter();
        convert.convert(res);
    }

    @Test(expected = KlipResponseException.class)
    public void convert_Fail_Wrong_Response() {
        ResponseStringConverter<KlipResponse> convert = KlipResponse.getConverter();
        convert.convert("{}");
    }
}
