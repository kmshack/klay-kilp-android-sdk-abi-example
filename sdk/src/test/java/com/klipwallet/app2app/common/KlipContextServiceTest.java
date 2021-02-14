package com.klipwallet.app2app.common;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.klipwallet.app2app.api.KlipProtocol;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class KlipContextServiceTest extends KlipTestCase {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private Context context;
    @Mock
    private ContentResolver contentResolver;
    @Mock
    private PackageManager manger;

    @Before
    public void setup() {
        doReturn(contentResolver).when(context).getContentResolver();
        doReturn(manger).when(context).getPackageManager();
    }

    @Test
    public void getPhaseInfo() throws PackageManager.NameNotFoundException {
        ApplicationInfo appInfo = new ApplicationInfo();
        {
            Bundle bundle = new Bundle();
            bundle.putString(KlipProtocol.PHASE, KlipPhase.DEV.toString());
            appInfo.metaData = bundle;
        }
        when(context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)).thenReturn(appInfo);

        KlipContextService.getInstance().initialize(context);
        Assert.assertEquals(KlipContextService.getInstance().getPhaseInfo().getPhase(), KlipPhase.DEV);
    }
}
