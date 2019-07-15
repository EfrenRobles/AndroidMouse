package com.chucuaz.android.virtualgdt.engine;

import android.util.Log;

/**
 * Created by efren.robles on 8/31/2015.
 */
public abstract class engineDebug {
    static private final boolean isDebug = true;

    static public void VERB(String Tag, String data) {
        if (isDebug)
            Log.v(Tag, data);
    }

    static public void DBG(String Tag, String data) {
        if (isDebug)
            Log.d(Tag, data);
    }

    static public void INFO(String Tag, String data) {
        if (isDebug)
            Log.i(Tag, data);
    }

    static public void WARN(String Tag, String data) {
        if (isDebug)
            Log.w(Tag, data);
    }

    static public void ERR(String Tag, String data) {
        if (isDebug)
            Log.e(Tag, data);
    }

    static public void ERR(String Tag, String data, Throwable tr) {
        if (isDebug)
            Log.e(Tag, data, tr);
    }
}
