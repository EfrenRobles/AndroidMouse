package com.chucuaz.android.virtualgdt;

/**
 * Created by efren.robles on 7/20/2016.
 */

public class Globals {
    static private Globals g_instance = new Globals();
    private myNetworkPool mynet = new myNetworkPool();
    private myThreadPool mythr = new myThreadPool();

    private boolean isConnected = false;
    private boolean isServerPing = false;

    public class netData {
        public String name;
        public String ip;
        public int mask;
        public boolean auto;

        public netData() {
            resetData();
        }

        public void resetData() {
            name = "";
            ip = "";
            mask = 0;
            auto = false;
        }
    }

    private String SERVER_IP = "192.168.0.100";
    final private int SERVERPORT = 1800;
    final private int SERVER_TIME_OUT = 50;

    public final String USB0 = "usb0";      //priority 1
    public final String RNDIS = "rndis0";   //priority 2
    public final String BTPAN = "bt-pan";   //priority 3
    public final String WLAN0 = "wlan0";    //priority 4

    public final byte NUMINTERFACE = 4; //Numbers of interfaces
    public netData[] netDataA = new netData[NUMINTERFACE];

    //----------------------------------------------------------------------------------------------
    public boolean getPingStatus() {
        return isServerPing;
    }

    public void setPingStatus(boolean data) {
        isServerPing = data;
    }

    public boolean getConnectionStatus() {
        debug.INFO("ClientThread", " --- isConnected( " + isConnected + " ); --- ");
        return isConnected;
    }

    public void setConnectionStatus(boolean data) {
        isConnected = data;
    }

    //----------------------------------------------------------------------------------------------
    public void initnetData() {
        debug.WARN("myNetworkPool"," initnetData() ");
        for (byte i=0; i<NUMINTERFACE; i++) {
            if (g_instance.netDataA[i] == null) {
                g_instance.netDataA[i] = new netData();
            }
        }
    }

    public void fillNetDAta(String Name, String IP, int Mask) {

        debug.WARN("myNetworkPool"," IP intf: " + Name);
        debug.ERR("myNetworkPool"," IP Address: " + IP);
        debug.ERR("myNetworkPool"," IP netMask: " + convertPrefixtoIp(Mask));


        if (Name.contains(USB0) || Name.contains(RNDIS) || Name.contains(BTPAN) || Name.contains(WLAN0)) {
            int i = 0;
            if (Name.contains(USB0) ) i = 0;
            if (Name.contains(RNDIS) ) i = 1;
            if (Name.contains(BTPAN) ) i = 2;
            if (Name.contains(WLAN0) ) i = 3;

            g_instance.netDataA[i].name = Name;
            g_instance.netDataA[i].ip = IP;
            g_instance.netDataA[i].mask = Mask;

            if (g_instance.netDataA[i].mask > 23) {
                g_instance.netDataA[i].auto = true;
                debug.WARN("myNetworkPool","--- Auto True with ip: " + IP + " on interface name: " + Name);
            } else {
                g_instance.netDataA[i].auto = false;
                debug.WARN("myNetworkPool","--- Auto False ---");
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    static public Globals getInstance() {
        return g_instance;
    }

    static public void killInstance() {
        g_instance=null;
    }

    //----------------------------------------------------------------------------------------------
    public myThreadPool getMythr() {
        return mythr;
    }

    //----------------------------------------------------------------------------------------------
    public myNetworkPool getMynet() {
        return mynet;
    }

    //----------------------------------------------------------------------------------------------
    public void setSERVER_IP(String server_ip) {
        SERVER_IP = server_ip;
    }

    public String getSERVER_IP() {
        return SERVER_IP;
    }

    public int getSERVERPORT() {
        return SERVERPORT;
    }

    public int getSERVERTIMEOUT() {
        return SERVER_TIME_OUT;
    }

    public String convertPrefixtoIp(int index) {

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

    public boolean getAutoConnectStatus() {
        int i = 0;
        boolean result = false;

        while (i < NUMINTERFACE) {
            result = g_instance.netDataA[i].auto;
            debug.INFO("myNetworkPool", "--- getAutoConnectStatus()->g_instance.netDataA[" + i + "].auto: " + g_instance.netDataA[i].auto);
            if (result) i = 4;
            i++;
        }

        return result;
    }
}
