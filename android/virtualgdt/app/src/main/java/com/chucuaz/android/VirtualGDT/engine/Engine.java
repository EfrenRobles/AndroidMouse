package com.chucuaz.android.virtualgdt.engine;


public class Engine extends engineDebug {
    private static final String TAG = "Engine";

    public void connect(final String server_ip) {
        engineSingle.getInstance().setServerIp(server_ip);
        new Thread(new engineSingle()).start();
    }

    public boolean isConnected() {
        return engineSingle.getInstance().isConnected();
    }

    public void sendData(final String data) {

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    engineSingle.getInstance().sendData(data);
                } catch (Exception e) {
                    ERR(TAG, e.getMessage());
                }
            }
        });

        thread.start();
    }
}
