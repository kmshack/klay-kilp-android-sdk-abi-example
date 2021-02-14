package com.klipwallet.app2app.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.klipwallet.app2app.api.KlipProtocol;

public class KlipPhaseInfo {
    private final KlipPhase phase;

    public KlipPhase getPhase() {
        return phase;
    }

    public KlipPhaseInfo(final Context context) {
        final String phaseFromMetaData = getMetadata(context, KlipProtocol.PHASE);
        if (phaseFromMetaData != null) {
            this.phase = KlipPhase.ofName(phaseFromMetaData);
        } else {
            this.phase = KlipPhase.PROD;
        }
    }

    public static String getMetadata(final Context context, final String key) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (ai == null)
                return null;
            else if (ai.metaData == null)
                return null;
            else
                return ai.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}
