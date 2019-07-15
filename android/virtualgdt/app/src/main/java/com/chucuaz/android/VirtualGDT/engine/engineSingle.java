package com.chucuaz.android.virtualgdt.engine;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

public class engineSingle extends engineDebug implements Runnable {
    private static final engineSingle s_instance = new engineSingle();
    private static final String TAG = "Single";
    private static final int SERVERPORT = 1800;
    private static final int SERVER_TIME_OUT = 50;

    private static final String USB0  = "usb0";     //priority 1
    private static final String RNDIS = "rndis0";   //priority 2 when used as a NIC for tethering
    private static final String BTPAN = "bt-pan";   //priority 3
    private static final String WLAN0 = "wlan0";    //priority 4

    private final int NUMINTERFACE = 4; //Numbers of interfaces
    private netData[] netDataA = new netData[NUMINTERFACE];

    private String server_ip = "192.168.0.100";
    private boolean sigue = true;

    private engineEnum state = engineEnum.INICIO;
    private InetSocketAddress socketAddress;

    private final String encryptionKey = "MZygpewJsCpRrfOr";
    private engineAES aes = new engineAES(encryptionKey);
    private static boolean l_ready = false;
    private static Socket socket = null;

    private boolean isServerPing = false;


    @Override
    public void run() {
        s_instance.socketAddress = new InetSocketAddress(s_instance.server_ip, SERVERPORT);
        initSocket(s_instance.socketAddress);

    }

    protected static engineSingle getInstance() {
        return s_instance;
    }

    protected void setServerIp(String ip) {
        s_instance.server_ip = ip;
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

    // Manual ---------------------------------------------------------------------------------------------------------------------
    public void initSocket(final InetSocketAddress scatAddress) {

        if (s_instance.l_ready) {
            s_instance.l_ready = false;
            try {
                s_instance.socket.close();
                WARN("ClientThread", "connection is closed thank you ");
            } catch (Exception e1) {
                ERR("ClientThread", "connection problem 2:" + e1.toString());
            }
            sleep(500);
        }

        INFO("ClientThread", " --- begin initSocket(InetSocketAddress, int); --- ");

        try {
            s_instance.socket = new Socket();
            //WARN("ClientThread", " --- initSocket step 1 --- ");
            s_instance.socket.connect(scatAddress, SERVER_TIME_OUT);

            WARN("ClientThread", " --- initSocket() --> success connection <--- ");
            s_instance.isServerPing = true;
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

    // Manual ---------------------------------------------------------------------------------------------------------------------

    // Auto  ----------------------------------------------------------------------------------------------------------------------
    protected boolean getPingStatus() {
        return s_instance.isServerPing;
    }

    protected void setPingStatus(boolean data) {
        s_instance.isServerPing = data;
    }

    protected void initSocket() {
        getIPs(); //busco las ip del device.
        findServidor(); //busca al servidor e intenta conectarse
    }

    private void getIPs() {
        try {
            initnetData();

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
                    fillNetDAta(Intf.getDisplayName(), inetAddress.getHostAddress(), (intAddr.getNetworkPrefixLength()));
                    //}
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void fillNetDAta(String Name, String IP, int Mask) {

        WARN("myNetworkPool"," IP intf: " + Name);
        ERR("myNetworkPool"," IP Address: " + IP);
        ERR("myNetworkPool"," IP netMask: " + convertPrefixtoIp(Mask));


        int i = selectNetworkDevice(Name);
        if (i != -1) {

            s_instance.netDataA[i].name = Name;
            s_instance.netDataA[i].ip = IP;
            s_instance.netDataA[i].mask = Mask;

            if (s_instance.netDataA[i].mask > 23) {
                s_instance.netDataA[i].auto = true;
                WARN("myNetworkPool","--- Auto True with ip: " + IP + " on interface name: " + Name);
            } else {
                s_instance.netDataA[i].auto = false;
                WARN("myNetworkPool","--- Auto False ---");
            }
        }
    }

    private String convertPrefixtoIp(int index) {

        switch (index) {
            case 0: return "0.0.0.0";
            case 1: return "128.0.0.0";
            case 2: return "192.0.0.0";
            case 3: return "224.0.0.0";
            case 4: return "240.0.0.0";
            case 5: return "248.0.0.0";
            case 6: return "252.0.0.0";
            case 7: return "254.0.0.0";
            case 8: return "255.0.0.0";
            case 9: return "255.128.0.0";
            case 10: return "255.192.0.0";
            case 11: return "255.224.0.0";
            case 12: return "255.240.0.0";
            case 13: return "255.248.0.0";
            case 14: return "255.252.0.0";
            case 15: return "255.254.0.0";
            case 16: return "255.255.0.0";
            case 17: return "255.255.128.0";
            case 18: return "255.255.192.0";
            case 19: return "255.255.224.0";
            case 20: return "255.255.240.0";
            case 21: return "255.255.248.0";
            case 22: return "255.255.252.0";
            case 23: return "255.255.254.0";
            case 24: return "255.255.255.0";
            case 25: return "255.255.255.128";
            case 26: return "255.255.255.192";
            case 27: return "255.255.255.224";
            case 28: return "255.255.255.240";
            case 29: return "255.255.255.248";
            case 30: return "255.255.255.252";
            case 31: return "255.255.255.254";
            default: return "255.255.255.255";
        }
    }

    private int selectNetworkDevice(String Name) {

        if (Name.contains(USB0)) {
            return 0;
        } else if (Name.contains(RNDIS)) {
            return 1;
        } else if (Name.contains(BTPAN)) {
            return 2;
        } else if (Name.contains(WLAN0)) {
            return 3;
        }
        return -1;
    }

    public void initnetData() {
        WARN("myNetworkPool"," initnetData() ");
        for (byte i=0; i < NUMINTERFACE; i++) {
            if (s_instance.netDataA[i] == null) {
                s_instance.netDataA[i] = new netData();
            }
        }
    }

    private void findServidor() {
        for (int i = 0; i < NUMINTERFACE; i++) {
            WARN("ClientThread", "--- findServidor with i: " + i);
            if (pingServer(i)) {
                return;
            }
        }
    }

    private boolean pingServer(int i ) {
        if (s_instance.netDataA[i].auto) {
            ERR("ClientThread", "--- pingServer test with : " + s_instance.netDataA[i].ip);
            String[] parts = s_instance.netDataA[i].ip.split("\\.", 4);

            s_instance.state = engineEnum.CONECTAR_AUTO;

            // falta doble for para iterar el net mask, solo itera 255.255.255.0

            for (int j = 1; j < 255; j++) {
                s_instance.server_ip  = parts[0] + "." + parts[1] + "." + parts[2] + "." + j;

                askForServer();

                if (getPingStatus()) {
                    ERR("ClientThread", "--- ip server is :" + s_instance.server_ip);
                    return true;
                }
            }
        }
        return false;
    }

    private void askForServer() {
        sleep(100);
        new Thread(new engineSingle()).start();
    }
    // Auto  ----------------------------------------------------------------------------------------------------------------------

    // send data ------------------------------------------------------------------------------------------------------------------
    public void sendData(String data) {
        if (s_instance.l_ready) {
            try {
                Thread.sleep(10);
                INFO("sendData", "Send data: " + data);
                data = aes.encryptAsBase64(data).trim();

                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s_instance.socket.getOutputStream())), true);

                if (out != null) {
                    out.println(data);
                }

                DBG("sendData", "Send data: " + data);

            } catch (Exception e) {
                ERR("sendData", "sendData fail 3 e: " + e.getMessage());
                s_instance.l_ready = false;
                e.printStackTrace();
            }
        }
    }
    // send data ------------------------------------------------------------------------------------------------------------------

}
