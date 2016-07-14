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
    private static String SERVER_IP = "192.168.86.223";
    //private String SERVER_IP = "192.168.42.2";

    @Override
    public void run() {
        Log.w("ClientThread", "--- run new myNetworkPool from init( SERVERPORT= " + SERVERPORT + " SERERTIMEOUT= " + SERERTIMEOUT + " SERVER_IP= " + SERVER_IP + " )");
        InetSocketAddress socketAddress = new InetSocketAddress(SERVER_IP, SERVERPORT);
        mynet.initSocket(socketAddress, SERERTIMEOUT);
    }

    public void initSocket () {
        SERVER_IP = mynet.initSocket();
        Log.v("ClientThread", " --- SERVER_IP: " + SERVER_IP + " --- ");

    }

    public boolean isConnected() {
        return mynet.isConnected();
    }

    public void sendData(String data) {
        mynet.sendData(data);
        new myNetworkPool().execute("");
    }
}
