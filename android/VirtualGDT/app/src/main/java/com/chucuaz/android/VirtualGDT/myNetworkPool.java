package com.chucuaz.android.VirtualGDT;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
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
        if(l_ready) {
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
                        //e.printStackTrace();
                    }
                }
            }
        }
        return l_ready;
    }

    public String initSocket () {
        //socket = new Socket();
        Log.e("ClientThread", " --- begin initSocket() --- ");

        WifiManager wifii = (WifiManager)  MainActivity.mainContext.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo d = wifii.getDhcpInfo();

        Log.e("myNetworkPool"," DNS 1: " + d.dns1);
        Log.e("myNetworkPool"," DNS 2: " + d.dns2);
        Log.e("myNetworkPool"," Default Gateway: " + d.gateway);
        Log.e("myNetworkPool"," IP Address: " + d.ipAddress);
        Log.e("myNetworkPool"," Lease Time: " + d.leaseDuration);
        Log.e("myNetworkPool"," Subnet Mask: " + d.netmask);
        Log.e("myNetworkPool"," Server IP: " + d.serverAddress);



        return "0.0.0.0";
    }

    public void initSocket(final InetSocketAddress scatAddress, final int SERVER_TIME_OUT) {

        Log.i("ClientThread", " --- begin initSocket(InetSocketAddress, int); --- ");
        try {
            socket = new Socket();
            socket.connect(scatAddress, SERVER_TIME_OUT); //aqui hay pedos con el android 6.0 no se conecta por usb, falta probar por wifi
            System.setProperty("http.keepAlive", "false");
            l_ready = true;
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
        return l_ready;
    }

    public void sendData(String data) {
        if (l_ready) {
            try {
                Thread.sleep(10);
                Log.i("sendData", "Send data: " + data);
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
