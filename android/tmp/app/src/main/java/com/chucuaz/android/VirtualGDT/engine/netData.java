package com.chucuaz.android.virtualgdt.engine;

public class netData {
    protected String name;
    protected String ip;
    protected int mask;
    protected boolean auto;

    protected netData() {
        resetData();
    }

    protected void resetData() {
        name = "";
        ip = "";
        mask = 0;
        auto = false;
    }
}
