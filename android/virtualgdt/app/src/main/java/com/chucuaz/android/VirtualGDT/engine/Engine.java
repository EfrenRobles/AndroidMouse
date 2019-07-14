package com.chucuaz.android.virtualgdt.engine;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class Engine extends engineDebug {
    private static final String TAG = "Engine";

    public void startEngine() {
        engineSingle.getInstance().inicio();
        new Thread(new engineSingle()).start();
    }

    public void stopEngine() {
        engineSingle.getInstance().alto();
    }

    public void manualConnect(String server_ip) {
        engineSingle.getInstance().setServerIp(server_ip);
        engineSingle.getInstance().setState(engineEnum.CONECTAR);
    }

    public boolean getPingStatus() {
        return engineSingle.getInstance().getPingStatus();
    }

    public void setPingStatus(boolean data) {
        engineSingle.getInstance().setPingStatus(data);
    }

    public void initSocket() {
        engineSingle.getInstance().initSocket();
    }

    public boolean getConnectionStatus() {
        return engineSingle.getInstance().getConnectionStatus();
    }

    public boolean isConnected() {
        return engineSingle.getInstance().isConnected();
    }

    public void sendData(String data) {
        engineSingle.getInstance().sendData(data);
    }
}
