package com.klipwallet.app2app.util;

import android.util.Log;

public class Logger {
    public static final int VERBOSE = Log.VERBOSE;
    public static final int DEBUG = Log.DEBUG;
    public static final int INFO = Log.INFO;
    public static final int WARN = Log.WARN;
    public static final int ERROR = Log.ERROR;

    private static Tag tag = Tag.DEFAULT;

    public static int debug(String msg) {
        return debugTag(tag, msg);
    }

    public static int debugTag(Tag tag, String msg) {
        return printLog(tag, DEBUG, msg);
    }

    public static int verbose(String msg) {
        return verboseTag(tag, msg);
    }

    public static int verboseTag(Tag tag, String msg) {
        return printLog(tag, VERBOSE, msg);
    }

    public static int warn(String msg) {
        return warnTag(tag, msg);
    }

    public static int warnTag(Tag tag, String msg) {
        return printLog(tag, WARN, msg);
    }

    public static int info(String msg) {
        return infoTag(tag, msg);
    }

    public static int infoTag(Tag tag, String msg) {
        return printLog(tag, INFO, msg);
    }

    public static int error(String msg) {
        return errorTag(tag, msg);
    }

    public static int errorTag(Tag tag, String msg) {
        return printLog(tag, ERROR, msg);
    }

    private static int printLog(Tag tag, int logLevel, String msg) {
        if (msg == null) {
            return 0;
        }

        String tagMsg = tag.tag();
        switch (logLevel) {
            case VERBOSE:
                return Log.v(tagMsg, msg);
            case DEBUG:
                return Log.d(tagMsg, msg);
            case INFO:
                return Log.i(tagMsg, msg);
            case WARN:
                return Log.w(tagMsg, msg);
            case ERROR:
                return Log.e(tagMsg, msg);
        }

        return 0;
    }
}
