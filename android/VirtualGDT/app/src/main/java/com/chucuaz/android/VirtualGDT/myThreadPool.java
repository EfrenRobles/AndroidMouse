package com.chucuaz.android.VirtualGDT;

import java.net.InetSocketAddress;

/**
 * Created by efren.robles on 10/14/2015.
 */


class  myThreadPool implements Runnable {


    @Override
    public void run() {
        int SERERTIMEOUT = 50;
        //debug.WARN("ClientThread", "--- run new myNetworkPool from init( SERVERPORT= " + Globals.getInstance().getSERVERPORT() + " SERERTIMEOUT= " + SERERTIMEOUT + " SERVER_IP= " + Globals.getInstance().getSERVER_IP()  + " )");
        InetSocketAddress socketAddress = new InetSocketAddress(Globals.getInstance().getSERVER_IP() , Globals.getInstance().getSERVERPORT());
        Globals.getInstance().getMynet().initSocket(socketAddress, SERERTIMEOUT);
    }

    public void initSocket (String serverIP, int serverPort) {
        Globals.getInstance().setSERVER_IP(serverIP); //debe de ir en la pantalla de coneccion.
        Globals.getInstance().setSERVERPORT(serverPort);

        Globals.getInstance().getMynet().initSocket();
        debug.VERB("ClientThread", " --- SERVER_IP: " + Globals.getInstance().getSERVER_IP() + " --- ");
    }

    public boolean isConnected() {
        return Globals.getInstance().getMynet().isConnected();
    }

    public void sendData(String data) {
        Globals.getInstance().getMynet().sendData(data);
        new myNetworkPool().execute();
    }
}
