
package com.chucuaz.android.virtualgdt;
import android.os.AsyncTask;
import android.os.SystemClock;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;


/**
 * Created by efren.robles on 10/14/2015.
 */
public class myNetworkPool extends AsyncTask {
    private final String encryptionKey = "MZygpewJsCpRrfOr";
    private myAESPool aes = new myAESPool(encryptionKey);
    private static boolean l_ready = false;
    private static Socket socket = null;

    @Override
    protected Boolean  doInBackground (Object  ... arg0) {
        if(l_ready) {
            try {
                InputStream inFromServer = socket.getInputStream();
                DataInputStream in = new DataInputStream(inFromServer);
                debug.INFO("sendData", "Server says " + in.readByte());
            } catch (UnknownHostException e) {
                debug.ERR("sendData", "sendData fail 1 e: " + e.getMessage());
                l_ready = false;
            } catch (IOException e) {
                debug.ERR("sendData", "sendData fail 2 e: " + e.getMessage());
                l_ready = false;
            } catch (Exception e) {
                debug.ERR("sendData", "sendData fail 3 e: " + e.getMessage());
                l_ready = false;
            } finally {
                if (!l_ready) {
                    try {
                        debug.WARN("sendData", "Close socket: ");
                        socket.close();
                        socket = null;
                    } catch (IOException e) {
                        debug.ERR("sendData", "Close socket fail 1 e: " + e.getMessage());
                    }
                }
            }
        }
        return l_ready;
    }

    public void initSocket () {
        debug.ERR("ClientThread", " --- begin initSocket() --- ");
        getIPs(); //busco las ip del device.
        findServidor(); //busca al servidor e intenta conectarse
    }

    public void initSocket(final InetSocketAddress scatAddress) {

        if (l_ready) {
            l_ready = false;
            try {
                socket.close();
                debug.WARN("ClientThread", "connection is closed thank you ");
            } catch (Exception e1) {
                debug.ERR("ClientThread", "connection problem 2:" + e1.toString());
            }
            SystemClock.sleep(500);
        }

        //debug.INFO("ClientThread", " --- begin initSocket(InetSocketAddress, int); --- ");
        try {
            socket = new Socket();
            //debug.WARN("ClientThread", " --- initSocket step 1 --- ");
            socket.connect(scatAddress, Globals.getInstance().getSERVERTIMEOUT());

            debug.WARN("ClientThread", " --- initSocket() --> success connection <--- ");
            Globals.getInstance().setPingStatus(true);
            System.setProperty("http.keepAlive", "false");
            l_ready = true;
        } catch (Throwable e) {
            debug.ERR("ClientThread", "connection problem: " + e.toString());
            l_ready = false;
            try {
                socket.close();
                //debug.WARN("ClientThread", "connection is closed thank you ");
            } catch (Exception e1) {
                debug.ERR("ClientThread", "connection problem 2:" + e1.toString());
            }
        }

        Globals.getInstance().setConnectionStatus(l_ready);
        //debug.INFO("ClientThread", " --- end initSocket --- ");

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

                if (out != null) {
                    out.println(data);
                }

                debug.DBG("sendData", "Send data: " + data);

            } catch (Exception e) {
                debug.ERR("sendData", "sendData fail 3 e: " + e.getMessage());
                l_ready = false;
                e.printStackTrace();
            }
        }
    }

    private String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8 ) & 0xFF) + "." + ((i >> 16 ) & 0xFF) + "." + ((i >> 24 ) & 0xFF);
    }

    private void getIPs() {
        try {
            Globals.getInstance().initnetData();

            Enumeration<NetworkInterface> theIntfList = NetworkInterface.getNetworkInterfaces();

            List<InterfaceAddress> theAddrList = null;
            NetworkInterface Intf = null;
            InetAddress inetAddress = null;

            while (theIntfList.hasMoreElements()) {
                Intf = theIntfList.nextElement();
                theAddrList = Intf.getInterfaceAddresses();

                for (InterfaceAddress intAddr : theAddrList) {
                    inetAddress = intAddr.getAddress();

                   // if (inetAddress.isLoopbackAddress()  && inetAddress instanceof Inet4Address) {
                        Globals.getInstance().fillNetDAta(Intf.getDisplayName(), inetAddress.getHostAddress(), (intAddr.getNetworkPrefixLength()));
                    //}
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
   }

    private void findServidor() {
        for (int i = 0; i < Globals.getInstance().NUMINTERFACE; i++) {
            debug.ERR("ClientThread", "--- findServidor with i: " + i);
            if (pingServer(i)) return;
        }
    }

    private boolean pingServer(int i ) {
        if (Globals.getInstance().netDataA[i].auto) {
            debug.ERR("ClientThread", "--- pingServer test with : " + Globals.getInstance().netDataA[i].ip);
            String[] parts = Globals.getInstance().netDataA[i].ip.split("\\.", 4);

            for (int j = 1; j < 255; j++) {
                String ipserver = parts[0] + "." + parts[1] + "." + parts[2] + "." + j;

                Globals.getInstance().setSERVER_IP(ipserver);
                askForServer();

                try {
                    Thread.sleep(51);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (Globals.getInstance().getPingStatus()) {
                    debug.ERR("ClientThread", "--- ip server is :" + ipserver);
                    return true;
                }
            }
        }
        return false;
    }

    private void askForServer() {
        new Thread(new myThreadPool()).start();
    }


}
