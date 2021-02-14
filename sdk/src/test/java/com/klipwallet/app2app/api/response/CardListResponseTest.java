package com.klipwallet.app2app.api.response;

import org.junit.Test;

import com.klipwallet.app2app.common.KlipTestCase;
import com.klipwallet.app2app.exception.KlipResponseException;
import com.klipwallet.app2app.network.response.ResponseStringConverter;

public class CardListResponseTest extends KlipTestCase {

    @Test
    public void convert() {
        String res = "{\"name\":\"my name\", \"symbol_img\":\"my symbol img\", \"next_cursor\":\"my next cursor\"}";
        ResponseStringConverter<CardListResponse> convert = CardListResponse.getConverter();
        convert.convert(res);
    }

    @Test(expected = KlipResponseException.class)
    public void convert_Fail_Wrong_Response() {
        ResponseStringConverter<CardListResponse> convert = CardListResponse.getConverter();
        convert.convert("{}");
    }
}
