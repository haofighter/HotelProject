package com.sun.hotelproject.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.sun.hotelproject.entity.Nation;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * Created by a'su's on 2018/4/8.
 * 工具类
 */

public class Utils {
    private static final String TAG = "Utils";
    private static long lastClickTime;

    public static boolean isFastClick() {
        if (System.currentTimeMillis() - lastClickTime < 1500) {
            Log.e(TAG, "isFastClick: 重复点击");
            return false;
        }
        lastClickTime = System.currentTimeMillis();
        return true;
    }


    public final static String PHONE_PATTERN = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([0,1,6,7,]))|(18[0-2,5-9]))\\d{8}$";

    /**
     * 判断是否是手机号
     *
     * @param s 判断的数字
     * @return 真或假
     */
    public static boolean isPhone(String s) {
        boolean isPhone = Pattern.compile(PHONE_PATTERN).matcher(s).matches();
        return isPhone;
    }

    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 由年月日时分秒+3位随机数
     * 生成流水号
     *
     * @return
     */
    public static String Getnum() {
        String t = getStringDate();
        int x = (int) (Math.random() * 900) + 100;
        String serial = t + x;
        return serial;
    }


    public static File saveBitmapFile(Bitmap bitmap) {

        File file = new File("storage/emulated/legacy/" + System.currentTimeMillis() + "tupian.jpg");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * bitmap转base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        // 要返回的字符串
        String reslut = null;

        ByteArrayOutputStream baos = null;

        try {

            if (bitmap != null) {

                baos = new ByteArrayOutputStream();
                /**
                 * 压缩只对保存有效果bitmap还是原来的大小
                 */
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);

                baos.flush();
                baos.close();
                // 转换为字节数组
                byte[] byteArray = baos.toByteArray();

                // 转换为字符串
                reslut = Base64.encodeToString(byteArray, Base64.DEFAULT);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return reslut;

    }


    private static List<Nation> initAllnation() {
        List<Nation> nationList = new ArrayList<>();
        nationList.add(new Nation("01", "汉族"));
        nationList.add(new Nation("02", "蒙古族"));
        nationList.add(new Nation("03", "回族"));
        nationList.add(new Nation("04", "藏族"));
        nationList.add(new Nation("05", "维吾尔族"));
        nationList.add(new Nation("06", "苗族"));
        nationList.add(new Nation("07", "彝族"));
        nationList.add(new Nation("08", "壮族"));
        nationList.add(new Nation("09", "布依族"));
        nationList.add(new Nation("10", "朝鲜族"));
        nationList.add(new Nation("11", "满族"));
        nationList.add(new Nation("12", "侗族"));
        nationList.add(new Nation("13", "瑶族"));
        nationList.add(new Nation("14", "白族"));
        nationList.add(new Nation("15", "土家族"));
        nationList.add(new Nation("16", "哈尼族"));
        nationList.add(new Nation("17", "哈萨克族"));
        nationList.add(new Nation("18", "傣族"));
        nationList.add(new Nation("19", "黎族"));
        nationList.add(new Nation("20", "傈僳族"));
        nationList.add(new Nation("21", "佤族"));
        nationList.add(new Nation("22", "畲族"));
        nationList.add(new Nation("23", "高山族"));
        nationList.add(new Nation("24", "拉祜族"));
        nationList.add(new Nation("25", "水族"));
        nationList.add(new Nation("26", "东乡族"));
        nationList.add(new Nation("27", "纳西族"));
        nationList.add(new Nation("28", "景颇族"));
        nationList.add(new Nation("29", "柯尔克孜族"));
        nationList.add(new Nation("30", "土族"));
        nationList.add(new Nation("31", "达翰尔族"));
        nationList.add(new Nation("32", "仫佬族"));
        nationList.add(new Nation("33", "羌族"));
        nationList.add(new Nation("34", "布朗族"));
        nationList.add(new Nation("35", "撒拉族"));
        nationList.add(new Nation("36", "毛南族"));
        nationList.add(new Nation("37", "仡佬族"));
        nationList.add(new Nation("38", "锡伯族"));
        nationList.add(new Nation("39", "阿昌族"));
        nationList.add(new Nation("40", "普米族"));
        nationList.add(new Nation("41", "塔吉克族"));
        nationList.add(new Nation("42", "怒族"));
        nationList.add(new Nation("43", "乌孜别克族"));
        nationList.add(new Nation("44", "俄罗斯族"));
        nationList.add(new Nation("45", "鄂温克族"));
        nationList.add(new Nation("46", "德昂族"));
        nationList.add(new Nation("47", "保安族"));
        nationList.add(new Nation("48", "裕固族"));
        nationList.add(new Nation("49", "京族"));
        nationList.add(new Nation("50", "塔塔尔族"));
        nationList.add(new Nation("51", "独龙族"));
        nationList.add(new Nation("52", "鄂伦春族"));
        nationList.add(new Nation("53", "赫哲族"));
        nationList.add(new Nation("54", "门巴族"));
        nationList.add(new Nation("55", "珞巴族"));
        nationList.add(new Nation("56", "基诺族"));
        nationList.add(new Nation("57", "其它"));
        nationList.add(new Nation("98", "外国人入籍"));

        return nationList;
    }


    public static String checkNation(String str) {
        for (int i = 0; i < initAllnation().size(); i++) {
            if (initAllnation().get(i).getNum().equals(str)) {
                return initAllnation().get(i).getName();
            }
        }
        return "";
    }

    public static TreeMap<String, String> getMap() {
        return new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 升序排序
                        return obj1.compareTo(obj2);
                    }
                });

    }


}
