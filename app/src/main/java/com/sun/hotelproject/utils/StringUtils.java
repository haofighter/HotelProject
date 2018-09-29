package com.sun.hotelproject.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.StatFs;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.sun.hotelproject.R;
import com.sun.hotelproject.app.App;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by a'su's on 2018/5/22.
 */

public class StringUtils {
    /**
     * 获取分散密钥，注意，data与masterKey的长度必须为16
     * @param masterKey 上级密钥
     * @param data 分散数据
     * @return 分散后的密钥
     * @throws Exception
     */
    public static byte[] getSubKey(byte[] masterKey, byte[] data) throws Exception {
        byte[] key = new byte[24];
        System.arraycopy(masterKey, 0, key, 0, 16);
        System.arraycopy(masterKey, 0, key, 16, 8);

        SecretKeySpec keySepc = new SecretKeySpec(key, "DESede");
        Cipher cp = Cipher.getInstance("DESede/ECB/NoPadding");
        cp.init(Cipher.ENCRYPT_MODE, keySepc);
        return cp.doFinal(data);
    }

    /**
     * 计算MAC签名，注意，subKey长度必须为16
     * @param data 计算签名的数据
     * @param subKey 分散密钥
     * @return MAC签名
     * @throws Exception
     */
    public static byte[] getSign(byte[] data, byte[] subKey) throws Exception {
        int macLen = data.length + 8 - (data.length % 8);
        byte[] macData = new byte[macLen];
        System.arraycopy(data, 0, macData, 0, data.length);
        byte[] padding = {(byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        for (int i = data.length; i < macLen; i++) {
            macData[i] = padding[i - data.length];
        }
        byte[] leftKey = new byte[8];
        byte[] rightKey = new byte[8];
        System.arraycopy(subKey, 0, leftKey, 0, 8);
        System.arraycopy(subKey, 8, rightKey, 0, 8);
        SecretKeySpec leftKeySpec = new SecretKeySpec(leftKey, "DES");
        SecretKeySpec rightKeySpec = new SecretKeySpec(rightKey, "DES");

        Cipher cp = Cipher.getInstance("DES/ECB/NoPadding");
        cp.init(Cipher.ENCRYPT_MODE, leftKeySpec);

        byte[] text = new byte[8];
        for (int i = 0; i < macLen / 8; i++) {
            for (int j = 0; j < 8; j++) {
                text[j] ^= macData[i * 8 + j];
            }
            text = cp.doFinal(text);
        }
        cp.init(Cipher.DECRYPT_MODE, rightKeySpec);
        text = cp.doFinal(text);
        cp.init(Cipher.ENCRYPT_MODE, leftKeySpec);
        text = cp.doFinal(text);

        byte[] result = new byte[4];
        System.arraycopy(text, 0, result, 0, 4);
        return result;
    }

    /**
     * 十六进制转二进制
     * @param hex 十六进制编码的byte数组
     * @return 二进制byte数组
     */
    public static byte[] hex2Bin(String hex) {
        int len = hex.length() >> 1;
        byte[] bin = new byte[len];
        for (int i = 0; i < len; i++) {
            byte high = (byte) ascii2Bin(hex.charAt(i << 1));
            byte low = (byte) ascii2Bin(hex.charAt((i << 1) + 1));
            bin[i] = (byte) ((high << 4) + low);
        }
        return bin;
    }
    public static void setCorlor(TextView textView, ImageView imageView){
        imageView.setVisibility(View.VISIBLE);
        textView.setTextColor(App.getInstance().getResources().getColor(R.color.Swrite));
        textView.setBackgroundResource(R.drawable.oval_shape);
    }

    public static void setCorlor(TextView textView, ImageView imageView,TextView textView2 ,String type){
        if ("1".equals(type)){
            textView2.setText("订单号");
        }else if ("3".equals(type)){
            textView2.setText("手机号");
        }else {
            textView2.setText("身份证");
        }
        imageView.setVisibility(View.VISIBLE);
        textView.setTextColor(App.getInstance().getResources().getColor(R.color.Swrite));
        textView.setBackgroundResource(R.drawable.oval_shape);
    }

    /**
     * ascii编码转二进制
     * @param ch ascii字符
     * @return 对应二进制byte
     */
    public static byte ascii2Bin(char ch) {
        if (ch >= '0' && ch <= '9') {
            return (byte) (ch - '0');
        }
        if (ch >= 'a' && ch <= 'f')
        {
            return (byte) (ch - 'a' + 10);
        }
        if (ch >= 'A' && ch <= 'F')
        {
            return (byte) (ch - 'A' + 10);
        }
        return 0;
    }

    public static int byteArrayToInt(byte[] bytes) {
        return (((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF));
    }
    public static String byteArrayTos(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for(byte b : bytes) { // 使用String的format方法进行转换
            buf.append(String.format("%02x", new Integer(b & 0xff)));
        }
        //16进制
        System.out.println(buf.toString());
        long i= Long.parseLong(buf.toString(),16);
        //10进制
        System.out.println(i);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(i*1000);
        String res = dateformat.format(date);
        //解析成日期
        System.out.println(res);
        return res;
    }
    public static int byteArrayToShort(byte[] bytes) {
        return (((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF));
    }


}
