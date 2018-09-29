package com.sun.hotelproject.entity;

/**
 * 作者: Tangren on 2017-09-11
 * 包名：szxb.com.commonbus.entity
 * 邮箱：996489865@qq.com
 * TODO:返回值
 */

public class QRCode {

    /*
    成功
     */
    public static final int EC_SUCCESS = 0;

    /*
    二维码格式错误
    */
    public static final int EC_FORMAT = -10000;

    /*
    卡证书公钥错误
    */
    public static final int EC_CARD_PUBLIC_KEY = -10001;

    /*
    卡证书验签失败
    */
    public static final int EC_CARD_CERT = -10002;

    /*
    卡证书用户公钥错误
    */
    public static final int EC_USER_PUBLIC_KEY = -10003;

    /*
    二维码验签错误
    */
    public static final int EC_USER_SIGN = -10004;

    /*
    卡证书过期
    */
    public static final int EC_CARD_CERT_TIME = -10005;

    /*
    二维码过期
    */
    public static final int EC_CODE_TIME = -10006;

    /*
    超过最大金额
    */
    public static final int EC_FEE = -10007;

    //余额不足
    public static final int EC_BALANCE = -10008;


    //输入的 open_id 不匹配
    public static final int EC_OPEN_ID = -10009;

    //参数错误
    public static final int EC_PARAM_ERR = -10010;

    //内存申请错误
    public static final int EC_MEM_ERR = -10011;

    //卡证书签名算法不支持
    public static final int EC_CARD_CERT_SIGN_ALG_NOT_SUPPORT = -10012;

    //加密的mac根密钥解密失败
    public static final int EC_MAC_ROOT_KEY_DECRYPT_ERR = -10013;

    //mac校验失败
    public static final int EC_MAC_SIGN_ERR = -10014;

    //二维码签名算法不支持
    public static final int EC_QRCODE_SIGN_ALG_NOT_SUPPORT = -10015;

    //扫码记录加密失败
    public static final int EC_SCAN_RECORD_ECRYPT_ERR = -10016;

    //扫码记录编码失败
    public static final int EC_SCAN_RECORD_ECODE_ERR = -10017;

    //其它错误
    public static final int EC_FAIL = -20000;

    //二维码错误,标示非tx,非Sszxbzn
    public static final int QR_ERROR = -4;

    //小兵二维码验证失败
    public static final int MY_QR_INSTALL_FAIL = -5;

    //小兵二维码验证成功
    public static final int MY_QR_INSTALL_SUCCESS = 6;

    //时钟
    public static final int TIMER = 7;

    //黑名单用户
    public static final int BLACK_LIST = 8;

    //软件异常
    public static final int SOFTWARE_EXCEPTION = 9;

    //重复刷码，请求刷新二维码
    public static final int REFRESH_QR_CODE = 10;
    //更新今日交易笔数
    public static final int REFRESH_PAY_COUNT = 11;
    //更新HomeActivity UI
    public static final int REFRESH_VIEW = 12;

    //线路配置码
    public static final int CONFIG_CODE_LINE = 113;
    //FTP配置码
    public static final int CONFIG_CODE_FTP = 114;
    //商户配置码
    public static final int CONFIG_CODE_MCH = 115;
    //IP配置码
    public static final int CONFIG_CODE_IP = 116;
    //配置线路
    public static final int LINE_CODE = 117;

    //配置线路
    public static final int FTP_SUCC_FAIL = 118;

    //配置线路
    public static final int FTP_SUCC = 120;

    //配置线路
    public static final int FTP_FALI = 121;

    //司机签到
    public static final int DRIVER_SGIN = 122;

    //站点
    public static final int SITE = 123;

    //线路号不存在
    public static final int LINE_NO_EXITES = 124;

    //票价更新
    public static final int PRICES = 125;

    //校准时间二维码
    public static final int TIME = 126;

    //接收launcher信息
    public static final int RES_LAUNCHER = 127;

    //报站器异常
    public static final int SITE_ERROR = 128;

}
