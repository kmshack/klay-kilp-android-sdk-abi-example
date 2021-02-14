package com.klipwallet.app2app.network;

import android.support.test.runner.AndroidJUnit4;

import com.klipwallet.app2app.api.KlipProtocol;
import com.klipwallet.app2app.api.request.AuthRequest;
import com.klipwallet.app2app.api.request.KlipRequest;
import com.klipwallet.app2app.api.request.model.BAppInfo;
import com.klipwallet.app2app.internal.PrepareRequest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class NetworkImplTest {

    @Test
    public void configure_Success_With_Hangle() {
        String testUrl = KlipProtocol.SCHEME + "://" + KlipProtocol.apiAuthority() + KlipProtocol.API_VERSION + KlipProtocol.API_PREPARE_URL;
        String testMethod = KlipProtocol.API_PREPARE_METHOD;

        PrepareRequest prepare = new PrepareRequest();
        prepare.setTransaction(new AuthRequest());
        prepare.setType(KlipRequest.AUTH);
        prepare.setBapp(new BAppInfo("english and 한글"));

        INetwork network = new NetworkImpl();
        try {
            network.create(testUrl, testMethod);
            network.addBody(prepare.toJson().toString());
            network.configure();
            network.connect();
            network.disconnect();
        } catch (IOException e) {
            Assert.fail();
        }
    }
}
