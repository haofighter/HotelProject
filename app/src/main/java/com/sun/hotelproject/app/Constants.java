package com.sun.hotelproject.app;

/**
 * Created by a'su's on 2018/5/4.
 */

public class Constants {
    //是否为调试模式
    public static boolean isTest=true;

    // 密钥
    public final static String secretKey = "0535YANTAIJIANWANZHONG99";
    // 向量
    public final static String iv = "12345678";
    // 加解密统一使用的编码方式
    public final static String encoding = "utf-8";
    // 3des加密
    public static final String algorithm = "desede";

    // 公安认证码
    public static final String policeCode = "58d0dffdf5bc11e79ee65d6abaf250ad";
    public final static String ERROR="error";//失败
    public final static String SUCCESS="success";//成功

    public final static String USER_IN = "1";//入住
    public final static String USER_OUT = "3";//退房
    public final static String USER_SCHEDULE = "4";//预定
    public static boolean USE_IDCARD = true;//是否使用身份证


}
