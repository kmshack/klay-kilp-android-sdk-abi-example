package com.klipwallet.app2app.api.request;

import com.klipwallet.app2app.api.KlipProtocol;
import com.klipwallet.app2app.common.KlipTestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class KlipRequestTest extends KlipTestCase {

    @Test
    public void toJson_KlayTxRequest() throws JSONException {
        String expectedTo = "0xto";
        String expectedFrom = "0xfrom";
        String expectedAmount = "1";

        KlipRequest req = new KlayTxRequest.Builder()
                .to(expectedTo)
                .from(expectedFrom)
                .amount(expectedAmount)
                .build();
        JSONObject obj = req.toJson();
        Assert.assertEquals(expectedTo, obj.getString(KlipProtocol.TO));
        Assert.assertEquals(expectedFrom, obj.getString(KlipProtocol.FROM));
        Assert.assertEquals(expectedAmount, obj.getString(KlipProtocol.AMOUNT));
    }

    @Test
    public void toJson_TokenTxRequest() throws JSONException {
        String expectedContract = "0xtoken_contract";
        String expectedTo = "0xto";
        String expectedFrom = "0xfrom";
        String expectedAmount = "1";

        KlipRequest req = new TokenTxRequest.Builder()
                .contract(expectedContract)
                .to(expectedTo)
                .from(expectedFrom)
                .amount(expectedAmount)
                .build();
        JSONObject obj = req.toJson();
        Assert.assertEquals(expectedContract, obj.getString(KlipProtocol.CONTRACT));
        Assert.assertEquals(expectedTo, obj.getString(KlipProtocol.TO));
        Assert.assertEquals(expectedFrom, obj.getString(KlipProtocol.FROM));
        Assert.assertEquals(expectedAmount, obj.getString(KlipProtocol.AMOUNT));
    }

    @Test
    public void toJson_CardTxRequest() throws JSONException {
        String expectedContract = "0xcard_contract";
        String expectedTo = "0xto";
        String expectedFrom = "0xfrom";
        String expectedCardId = "100";

        KlipRequest req = new CardTxRequest.Builder()
                .contract(expectedContract)
                .to(expectedTo)
                .from(expectedFrom)
                .cardId(expectedCardId)
                .build();
        JSONObject obj = req.toJson();
        Assert.assertEquals(expectedContract, obj.getString(KlipProtocol.CONTRACT));
        Assert.assertEquals(expectedTo, obj.getString(KlipProtocol.TO));
        Assert.assertEquals(expectedFrom, obj.getString(KlipProtocol.FROM));
        Assert.assertEquals(expectedCardId, obj.getString(KlipProtocol.CARD_ID));
    }
}
