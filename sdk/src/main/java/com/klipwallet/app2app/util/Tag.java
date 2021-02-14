package com.klipwallet.app2app.util;

public enum Tag {
    DEFAULT("klip.sdk");

    private final String tag;

    Tag(String tag) {
        this.tag = tag;
    }

    public String tag() {
        return tag;
    }
}
