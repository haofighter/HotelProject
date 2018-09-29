package com.sun.hotelproject.utils;

/**
 * @author sun
 * 时间：2017/11/22
 * TODO 网络地址
 */
public class HttpUrl {
//  private static final String URL = "http://blackskin.imwork.net:35540";
// "http://118.89.152.33:8080";
//    public static final String SEQNO = "/bipwlt/facerec/seqNo";
//    public static final String FACERECOQNITION = "/bipwlt/facerec/faceRecognition";
    //private static final String URL = "http://139.199.158.253";
    private static final String URL = "http://134.175.56.14";
    public static final String SEQNO = URL + "/biphotel/facerec/seqNo";
    public static final String FACERECOQNITION = URL + "/biphotel/facerec/faceRecognition";
    public static final String CHECKOUTROOM = URL + "/biphotel/checkout/checkoutroom";
    public static final String QUERYROOMBILL = URL + "/biphotel/checkout/queryroombill";
    public static final String QUERYBUILDING = URL + "/biphotel/initialization/querybuilding";
    public static final String QUERYFLOOR = URL + "/biphotel/initialization/queryfloor";
    public static final String QUERYROOMTYPE = URL + "/biphotel/initialization/queryroomtype";
    public static final String QUERYROOMINFO = URL + "/biphotel/initialization/queryroominfo";
    public static final String QUERYROOMINFO2 = URL + "/biphotel/checkin/queryroominfo";
    public static final String SCANPAY = URL + "/biphotel/interaction/scanpay";
    public static final String LOCKROOM = URL + "/biphotel/checkin/lockroom";
    public static final String QUERYPAYSTATUS = URL + "/biphotel/checkin/querypaystatus";
    public static final String QUERYCHECKIN = URL + "/biphotel/stay/querycheckin";
    public static final String AFFIRMSTAY = URL + "/biphotel/stay/affirmstay";
    public static final String LOGIN = URL + "/biphotel/initialization/login";
    public static final String QUERYBOOKORDER = URL + "/biphotel/booking/querybookorder";
    public static final String INROOMNOPAY = URL + "/biphotel/checkin/inroomnopay";
    public static final String SMSCHECK = URL + "/biphotel/smsinter/smscheck";
    public static final String SMSSEND = URL + "/biphotel/smsinter/smssend";
    public static final String QUERYROOMTYPE2 = URL + "/biphotel/checkin/queryroomtypes";
    public static final String EPOCOMCATION = URL + "/biphotel/political/epocomcation";
    public static final String FINDKEY = URL + "/biphotel/rootkey/findkey";
    public static final String SENDSERNUMBER = URL + "/biphotel/interaction/changeorder";
    public static final String CANCALCHECKROOM = URL + "/biphotel/checkin/cancellockroom";


    public static final String policeUrl = "http://g214c52972.iok.la:22619";
    public static final String serialNumber = policeUrl + "/box/service/base/serialNumber";
    public static final String machine = policeUrl + "/box/service/travellers/certificate/idCard/registration/machine";
    public static final String checkout = policeUrl + "/box/service/travellers/certificate/idCard/checkout/machine";
    public static final String nocertificate = policeUrl + "/box/service/travellers/nocertificate/verification/machine";
}
