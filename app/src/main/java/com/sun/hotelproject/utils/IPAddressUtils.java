package com.sun.hotelproject.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.sun.hotelproject.app.App;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IPAddressUtils {
    public static String getLocalIpAddress() {
        try {
            String allIP = "";
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    allIP += inetAddress.getHostAddress()+"\n";
                    if (!inetAddress.isLoopbackAddress()&&inetAddress instanceof Inet4Address) {
//                        return inetAddress.getHostAddress();
                    }
                }
            }
            return allIP;
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static String getIp(){
        WifiManager wm=(WifiManager) App.getInstance().getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        //检查Wifi状态
        if(!wm.isWifiEnabled())
            return "wifi未开启";
        WifiInfo wi=wm.getConnectionInfo();
        //获取32位整型IP地址
        int ipAdd=wi.getIpAddress();
        //把整型地址转换成“*.*.*.*”地址
        String ip=intToIp(ipAdd);
        return ip;
    }
    private static String intToIp(int i) {
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }


    public static String getWlanId(){
        WifiManager wm = (WifiManager)App.getInstance().getApplicationContext().getSystemService(App.getInstance().getApplicationContext().WIFI_SERVICE);
        String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
        return m_szWLANMAC;
    }
}