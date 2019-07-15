package com.chucuaz.android.virtualgdt.engine;


public class Engine extends engineDebug {
    private static final String TAG = "Engine";

    public void connect(String server_ip) {
        engineSingle.getInstance().setServerIp(server_ip);
        new Thread(new engineSingle()).start();
    }

    public boolean isConnected() {
        return engineSingle.getInstance().isConnected();
    }

    public void sendData(String data) {
        engineSingle.getInstance().sendData(data);
    }

}
