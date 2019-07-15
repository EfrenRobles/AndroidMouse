package com.chucuaz.android.virtualgdt.engine;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class engineSingle extends engineDebug implements Runnable {
    private static final engineSingle s_instance = new engineSingle();
    private static final String TAG = "Single";
    private static final String encryptionKey = "MZygpewJsCpRrfOr";
    private static final int SERVERPORT = 1800;
    private static final int SERVER_TIME_OUT = 50;

    private String server_ip = "";
    private InetSocketAddress socketAddress;

    private engineAES aes = new engineAES(encryptionKey);
    private static boolean l_ready = false;
    private static Socket socket = null;

    @Override
    public void run() {
        s_instance.socketAddress = new InetSocketAddress(s_instance.server_ip, SERVERPORT);
        initSocket(s_instance.socketAddress);
    }

    protected static engineSingle getInstance() {
        return s_instance;
    }

    protected void setServerIp(String ip) {
        if (!ip.isEmpty()) {
            s_instance.server_ip = ip;
        }
    }

    protected boolean isConnected() {
        return s_instance.l_ready;
    }

    // ---------------------------------------------------------------------------------------------------------------------------
    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Connection -----------------------------------------------------------------------------------------------------------------
    public void initSocket(final InetSocketAddress scatAddress) {

        if (s_instance.l_ready) {
            s_instance.l_ready = false;
            try {
                s_instance.socket.close();
                WARN("ClientThread", "connection is closed thank you ");
            } catch (Exception e1) {
                ERR("ClientThread", "connection problem 2:" + e1.toString());
            }
            sleep(200);
        }

        INFO("ClientThread", " --- begin initSocket(InetSocketAddress, int); --- ");

        try {
            s_instance.socket = new Socket();
            //WARN("ClientThread", " --- initSocket step 1 --- ");
            s_instance.socket.connect(scatAddress, SERVER_TIME_OUT);

            WARN("ClientThread", " --- initSocket() --> success connection <--- ");
            System.setProperty("http.keepAlive", "false");
            s_instance.l_ready = true;
        } catch (Throwable e) {
            ERR("ClientThread", "connection problem: " + e.toString());
            s_instance.l_ready = false;
            try {
                s_instance.socket.close();
                WARN("ClientThread", "connection is closed thank you ");
            } catch (Exception e1) {
                ERR("ClientThread", "connection problem 2:" + e1.toString());
            }
        }

    }
    // Connection -----------------------------------------------------------------------------------------------------------------

    // send data ------------------------------------------------------------------------------------------------------------------
    public void sendData(String data ) {
        if (s_instance.l_ready) {
            try {
                Thread.sleep(10);
                //INFO("sendData", "Send data: " + s_instance.data);
                data = aes.encryptAsBase64(data).trim();

                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s_instance.socket.getOutputStream())), true);

                if (out != null) {
                    out.println(data);
                }

                //DBG("sendData", "Send data: " + data);

            } catch (Exception e) {
                ERR("sendData", "sendData fail 3 e: " + e.getMessage());
                s_instance.l_ready = false;
                e.printStackTrace();
            }
        }
    }
    // send data ------------------------------------------------------------------------------------------------------------------
}
