package com.chucuaz.android.VirtualGDT;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Created by efren.robles on 10/14/2015.
 */
public class myNetworkPool extends AsyncTask<String,Void,String> {
    private final String encryptionKey = "MZygpewJsCpRrfOr";
    private myAESPool aes = new myAESPool(encryptionKey);
    private static boolean l_ready = false;
    private static Socket socket = null;

    public Context context;

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected String  doInBackground (String  ... arg0) {
        if(l_ready) {
            try {
                InputStream inFromServer = socket.getInputStream();
                DataInputStream in = new DataInputStream(inFromServer);
                debug.INFO("sendData", "Server says " + in.readByte());
                //inFromServer.close();
            } catch (UnknownHostException e) {
                debug.ERR("sendData", "sendData fail 1 e: " + e.getMessage());
                l_ready = false;
                //e.printStackTrace();
            } catch (IOException e) {
                debug.ERR("sendData", "sendData fail 2 e: " + e.getMessage());
                l_ready = false;
                //e.printStackTrace();
            } catch (Exception e) {
                debug.ERR("sendData", "sendData fail 3 e: " + e.getMessage());
                l_ready = false;
                //e.printStackTrace();
            } finally {
                if (!l_ready) {
                    try {
                        debug.WARN("sendData", "Close socket: ");
                        socket.close();
                        socket = null;
                    } catch (IOException e) {
                        debug.ERR("sendData", "Close socket fail 1 e: " + e.getMessage());
                        //e.printStackTrace();
                    }
                }
            }
        }
        //return l_ready;
        return null;
    }

    public String initSocket () {
        //socket = new Socket();
        debug.ERR("ClientThread", " --- begin initSocket() --- ");

        WifiManager wifii = (WifiManager)  DrawActivity.mainContext.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo d = wifii.getDhcpInfo();

        debug.ERR("myNetworkPool"," DNS 1: " + d.dns1);
        debug.ERR("myNetworkPool"," DNS 2: " + d.dns2);
        debug.ERR("myNetworkPool"," Default Gateway: " + d.gateway);
        debug.ERR("myNetworkPool"," IP Address: " + d.ipAddress);
        debug.ERR("myNetworkPool"," Lease Time: " + d.leaseDuration);
        debug.ERR("myNetworkPool"," Subnet Mask: " + d.netmask);
        debug.ERR("myNetworkPool"," Server IP: " + d.serverAddress);

        return "0.0.0.0";

    }

    public void initSocket(final InetSocketAddress scatAddress, final int SERVER_TIME_OUT) {
        debug.INFO("ClientThread", " --- begin initSocket(InetSocketAddress, int); --- ");
        try {
            socket = new Socket();
            debug.WARN("ClientThread", " --- initSocket step 1 --- ");
            socket.connect(scatAddress, SERVER_TIME_OUT); //aqui hay pedos con el android 6.0 no se conecta por usb, falta probar por wifi

            debug.WARN("ClientThread", " --- initSocket step 2 --- ");
            System.setProperty("http.keepAlive", "false");
            l_ready = true;
        } catch (Throwable e) {
            debug.ERR("ClientThread", "connection problem: " + e.toString());
            l_ready = false;
            try {
                socket.close();
                debug.WARN("ClientThread", "connection is closed thank you ");
            } catch (Exception e1) {
                debug.ERR("ClientThread", "connection problem 2:" + e1.toString());
            }
        }
        debug.INFO("ClientThread", " --- end initSocket --- ");

    }

    public boolean isConnected() {
        return l_ready;
    }

    public void sendData(String data) {
        if (l_ready) {
            try {
                Thread.sleep(10);
                debug.INFO("sendData", "Send data: " + data);
                data = aes.encryptAsBase64(data).trim();

                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                out.println(data);

                debug.DBG("sendData", "Send data: " + data);

            } catch (Exception e) {
                debug.ERR("sendData", "sendData fail 3 e: " + e.getMessage());
                l_ready = false;
                e.printStackTrace();
            }
        }
    }

}
