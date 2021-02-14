package com.klipwallet.app2app.common;

import android.content.Context;

public class KlipContextService {
    private KlipPhaseInfo phaseInfo;
    private static KlipContextService instance;

    public static synchronized KlipContextService getInstance() {
        if (instance == null) {
            instance = new KlipContextService();
        }
        return instance;
    }

    private KlipContextService() {
    }

    public synchronized void initialize(final Context context) {
        if (phaseInfo == null) {
            phaseInfo = new KlipPhaseInfo(context);
        }
    }

    public KlipPhaseInfo getPhaseInfo() {
        return phaseInfo;
    }
}
