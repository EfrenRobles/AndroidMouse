package com.chucuaz.android.androidinterface;


import com.chucuaz.android.androidinterface.*;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by efren.robles on 10/14/2015.
 */
public class myNetworkPool {
    private final String encryptionKey = "MZygpewJsCpRrfOr";
    private myAESPool aes = new myAESPool(encryptionKey);
    private static boolean l_ready = false;

    private static Socket socket;

    public void initSocket(final InetSocketAddress scatAddress, final int SERVER_TIME_OUT) {
        l_ready = true;
        socket = new Socket();

        try {
            socket.connect(scatAddress, SERVER_TIME_OUT);
        } catch (Throwable e) {
            Log.e("ClientThread", "connection problem:" + e.toString());
            l_ready = false;
            try {
                Log.i("ClientThread", "connection is closed thank you ");
                socket.close();
            } catch (Exception e1) {
                Log.e("ClientThread", "connection problem 2:" + e1.toString());
            }
        }
    }

    public boolean isConnected() {
        if ( socket != null) {
            Log.i("ClientThread", " --- connection status is :  " + socket.isConnected());
            return socket.isConnected();
        }
        return false;
    }

    public void sendData(String data) {
        try {
            Thread.sleep(10);
            data = aes.encryptAsBase64(data).trim();
        } catch (Exception e) {
            Log.e("sendData", "Exception detected by e: " + e.getMessage());
        }

        if (l_ready) {
            try {
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                out.println(data);
                //out.flush();
                Log.d("sendData", "Send data: " + data + " socket.isConnected(): " + socket.isConnected());
            } catch (UnknownHostException e) {
                Log.e("sendData", "sendData fail 1: " + e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("sendData", "sendData fail 2: " + e.toString());
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("sendData", "sendData fail 3: " + e.toString());
                e.printStackTrace();
            }
        }
    }

}
