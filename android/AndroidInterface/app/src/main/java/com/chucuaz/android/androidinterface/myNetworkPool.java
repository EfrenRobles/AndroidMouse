package com.chucuaz.android.androidinterface;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by efren.robles on 10/14/2015.
 */
public class myNetworkPool extends AsyncTask  {
    private final String encryptionKey = "MZygpewJsCpRrfOr";
    private myAESPool aes = new myAESPool(encryptionKey);
    private static boolean l_ready = false;
    private static Socket socket = null;

    @Override
    protected Boolean doInBackground (Object ... arg0) {
        try {
            InputStream inFromServer = socket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            Log.i("sendData", "Server says " + in.readByte());
            //inFromServer.close();
        } catch (UnknownHostException e) {
            Log.e("sendData", "sendData fail 1 e: " + e.getMessage());
            l_ready = false;
            //e.printStackTrace();
        } catch (IOException e) {
            Log.e("sendData", "sendData fail 2 e: " + e.getMessage());
            l_ready = false;
            //e.printStackTrace();
        } catch (Exception e) {
            Log.e("sendData", "sendData fail 3 e: " + e.getMessage());
            l_ready = false;
            //e.printStackTrace();
        } finally {
            if (!l_ready) {
                try {
                    Log.w("sendData", "Close socket: ");
                    socket.close();
                    socket = null;
                } catch (IOException e) {
                    Log.e("sendData", "Close socket fail 1 e: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return l_ready;
    }

    public void initSocket () {
        //socket = new Socket();
    }

    public void initSocket(final InetSocketAddress scatAddress, final int SERVER_TIME_OUT) {
        l_ready = true;

        Log.i("ClientThread", " --- begin initSocket --- ");
        try {
            socket = new Socket();
            socket.connect(scatAddress, SERVER_TIME_OUT);
            System.setProperty("http.keepAlive", "false");



        } catch (Throwable e) {
            Log.e("ClientThread", "connection problem: " + e.toString());
            l_ready = false;
            try {
                socket.close();
                Log.w("ClientThread", "connection is closed thank you ");
            } catch (Exception e1) {
                Log.e("ClientThread", "connection problem 2:" + e1.toString());
            }
        }
        Log.i("ClientThread", " --- end initSocket --- ");
    }

    public boolean isConnected() {
        if ( socket != null) {
            Log.i("ClientThread", " --- connection status isClosed :  " + socket.isClosed());
            Log.i("ClientThread", " --- connection status isConnected :  " + socket.isConnected());
            //return (!socket.isClosed() && socket.isConnected());

            if( socket.isConnected()) {
                if (socket.isClosed()) {
                    return false;
                }
                return true;

            } return false;
        }
        return false;
    }

    public void sendData(String data) {
        if (l_ready) {
            try {
                Thread.sleep(10);
                data = aes.encryptAsBase64(data).trim();

                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                out.println(data);

                Log.d("sendData", "Send data: " + data);

            } catch (Exception e) {
                Log.e("sendData", "sendData fail 3 e: " + e.getMessage());
                l_ready = false;
                e.printStackTrace();
            }
        }
    }

}
