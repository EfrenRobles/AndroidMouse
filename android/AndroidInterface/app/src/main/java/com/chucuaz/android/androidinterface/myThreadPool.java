package com.chucuaz.android.androidinterface;

import android.util.Log;
import java.net.InetSocketAddress;

/**
 * Created by efren.robles on 10/14/2015.
 */


class  myThreadPool implements Runnable {

    private myNetworkPool mynet = new myNetworkPool();

    private final int SERVERPORT = 1800;
    private final int SERERTIMEOUT = 50;
    private String SERVER_IP = "192.168.42.39";

    @Override
    public void run() {
        Log.d("ClientThread", "run");
        InetSocketAddress scktAddrss = new InetSocketAddress(SERVER_IP, SERVERPORT);
        mynet.initSocket(scktAddrss, SERERTIMEOUT);
    }

    public boolean isConnected() {
        return !mynet.isConnected();
    }

    public void sendData(String data) {
        mynet.sendData(data);
    }
}
