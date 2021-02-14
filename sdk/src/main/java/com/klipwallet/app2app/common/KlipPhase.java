package com.klipwallet.app2app.common;

public enum KlipPhase {
    /**
     * dev (alpha) phase
     */
    DEV("dev"),
    /**
     * qa (qa) phase
     */
    QA("qa"),
    /**
     * stg (staging) phase
     */
    STG("stg"),
    /**
     * prod (real, release) phase
     */
    PROD("prod");

    private final String phaseName;

    KlipPhase(final String phaseName) {
        this.phaseName = phaseName;
    };

    public static KlipPhase ofName(final String name) {
        switch (name) {
            case "dev":
                return KlipPhase.DEV;
            case "qa":
                return KlipPhase.QA;
            case "stg":
                return KlipPhase.STG;
            case "prod":
            default:
                return KlipPhase.PROD;
        }
    }

    @Override
    public String toString() {
        return phaseName;
    }
}
