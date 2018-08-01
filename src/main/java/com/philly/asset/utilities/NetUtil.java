package com.philly.asset.utilities;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

public class NetUtil {

    public static String convertIPtoHostName(String ipAddress) {
        String hostName = null;
        try {
            InetAddress address = InetAddress.getByName(ipAddress) ;
            hostName = address.getHostName().toUpperCase();
            if(hostName.contains(".")) {
                if(validIP(hostName)){
                    //when hostName cannot be looked up.
                    hostName = hostName;
                } else{
                    //get hostName
                    hostName = hostName.split("\\.")[0];
                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostName;
    }

    //For IPv4 ip address
    public  static boolean validIP (String ip) {
        try {
            if ( ip == null || ip.isEmpty() ) {
                return false;
            }

            String[] parts = ip.split( "\\." );
            if ( parts.length != 4 ) {
                return false;
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return false;
                }
            }
            if ( ip.endsWith(".") ) {
                return false;
            }
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    //this method not working only works for host mac address
    public static String convertIPtoMac(String ipAddress) throws  Exception{
        InetAddress address = InetAddress.getByName(ipAddress) ;

        NetworkInterface ni = NetworkInterface.getByInetAddress(address);
        byte[] mac = ni.getHardwareAddress();

        String macAddress = "";
        for(int i = 0 ; i < mac.length; i++)
            macAddress += String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : "");

        return macAddress;
    }
}
