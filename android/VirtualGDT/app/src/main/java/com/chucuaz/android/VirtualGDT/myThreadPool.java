package com.chucuaz.android.virtualgdt;

import java.net.InetSocketAddress;

/**
 * Created by efren.robles on 10/14/2015.
 */


class  myThreadPool implements Runnable {


    @Override
    public void run() {
        //Globals.getInstance().setSERVER_IP(serverIP); //debe de ir en la pantalla de coneccion.

        InetSocketAddress socketAddress = new InetSocketAddress(Globals.getInstance().getSERVER_IP() , Globals.getInstance().getSERVERPORT());
        Globals.getInstance().getMynet().initSocket(socketAddress);

        debug.VERB("ClientThread", " --- SERVER_IP: " + Globals.getInstance().getSERVER_IP() + " --- ");
    }

    public void initSocket () {
        Globals.getInstance().getMynet().initSocket();
    }

    public boolean isConnected() {
        return Globals.getInstance().getMynet().isConnected();
    }

    public void sendData(String data) {
        Globals.getInstance().getMynet().sendData(data);
        new myNetworkPool().execute();
    }
}
