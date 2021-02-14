package com.klipwallet.app2app.api.request.model;

import com.klipwallet.app2app.api.KlipProtocol;
import com.klipwallet.app2app.common.KlipTestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class BAppInfoTest extends KlipTestCase {

    @Test
    public void toJson_Success_CB_Is_Null() throws JSONException {
        String expectedBAppName = "my app";
        BAppDeepLinkCB expectedBAppCB = null;

        BAppInfo bAppInfo = new BAppInfo(expectedBAppName);
        JSONObject obj = bAppInfo.toJson();

        Assert.assertEquals(expectedBAppName, obj.getString(KlipProtocol.BAPP_NAME));
        Assert.assertEquals(expectedBAppCB, obj.optJSONObject(KlipProtocol.BAPP_CALLBACK));
    }

    @Test
    public void toJson_Success_CB_Is_Not_Null() throws JSONException {
        String expectedBAppName = "my app";
        String exepctedSuccessUrl = "myApp://success..";
        String exepctedFailUrl = "myApp://fail..";
        BAppDeepLinkCB expectedBAppCB = new BAppDeepLinkCB(exepctedSuccessUrl, exepctedFailUrl);

        BAppInfo bAppInfo = new BAppInfo(expectedBAppName);
        bAppInfo.setCallback(expectedBAppCB);
        JSONObject jsonBApp = bAppInfo.toJson();

        Assert.assertEquals(expectedBAppName, jsonBApp.getString(KlipProtocol.BAPP_NAME));
        JSONObject jsonBAppCB = jsonBApp.getJSONObject(KlipProtocol.BAPP_CALLBACK);
        Assert.assertEquals(exepctedSuccessUrl, jsonBAppCB.getString(KlipProtocol.BAPP_SUCCESS_URL));
        Assert.assertEquals(exepctedFailUrl, jsonBAppCB.getString(KlipProtocol.BAPP_FAIL_URL));
    }
}
