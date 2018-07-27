package com.philly.asset.utilities;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

public class NetUtil {
    public static String convertIPtoMac(String ipAddress) throws  Exception{
        InetAddress address = InetAddress.getByName(ipAddress) ;

        NetworkInterface ni = NetworkInterface.getByInetAddress(address);
        byte[] mac = ni.getHardwareAddress();

        String macAddress = "";
        for(int i = 0 ; i < mac.length; i++)
            macAddress += String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : "");

        return macAddress;
    }

    public static String convertIPtoHostName(String ipAddress) {

        String hostName = null;
        try {
            InetAddress address = InetAddress.getByName(ipAddress) ;
            hostName = address.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostName;
    }
}
