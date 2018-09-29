package com.sun.hotelproject.moudle.net;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;
import com.net.RetrofitManager;
import com.sun.hotelproject.app.App;
import com.sun.hotelproject.app.BackCall;
import com.sun.hotelproject.entity.Draw;
import com.sun.hotelproject.entity.OrderDetalieInfo;
import com.sun.hotelproject.entity.PoliceSerialNumber;
import com.sun.hotelproject.app.Constants;
import com.sun.hotelproject.utils.HttpUrl;
import com.sun.hotelproject.utils.IPAddressUtils;
import com.sun.hotelproject.utils.JsonCallBack;
import com.sun.hotelproject.utils.Tip;
import com.sun.hotelproject.utils.Utils;

import java.io.IOException;
import java.util.TreeMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * =================================================
 * Author：  hao
 * Time：    2018/7/26 0026
 * Describe: 公安盒子数据接入
 * =================================================
 **/
public class PoliceInfoSend {
    public static PoliceSerialNumber policeSerialNumber;
    static HttpHeaders headers;

    {
        if (headers == null) {
            headers = new HttpHeaders();
            headers.put("Content-Type", "text/html; charset=utf-8");
        }
    }


    //入住
    public static void checkin(final OrderDetalieInfo orderDetalieInfo, final BackCall backCall) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TreeMap<String, String> GetUserInfoMap = Utils.getMap();

                  /*
                    GetUserInfoMap.put("authCode", "58d0dffdf5bc11e79ee65d6abaf250ad");
                    GetUserInfoMap.put("mode", "1");
                    GetUserInfoMap.put("serialNumber", titleInfo.getResult());
                    GetUserInfoMap.put("certificateNumber", "421181199208041914");
                    GetUserInfoMap.put("roomNumber", "101");*/

                    GetUserInfoMap.put("authCode", Constants.policeCode.trim());
                    GetUserInfoMap.put("serialNumber", policeSerialNumber.getResult().trim());
                    GetUserInfoMap.put("roomNumber",  orderDetalieInfo.getOrderlist().getRPMSNO().trim());//房间号
                    GetUserInfoMap.put("phoneNumber",  orderDetalieInfo.getOrderlist().getTEL().trim());//手机号
                    GetUserInfoMap.put("remark", orderDetalieInfo.getOrderlist().getRMK());//备注
                    GetUserInfoMap.put("certificateNumber", orderDetalieInfo.getGuetlist().getDOCNO());//身份证
                    GetUserInfoMap.put("name", orderDetalieInfo.getGuetlist().getNAME().trim());//姓名
                    GetUserInfoMap.put("gender", orderDetalieInfo.getGuetlist().getGENDER().trim());//性别
                    GetUserInfoMap.put("nation", orderDetalieInfo.getGuetlist().getNATION().trim());//名族
                    GetUserInfoMap.put("birthTime", orderDetalieInfo.getGuetlist().getBIRTH() + " 00:00:00");//出生日期,精确到日 yyyy-MM-dd HH:mm:ss
                    GetUserInfoMap.put("address", orderDetalieInfo.getGuetlist().getADDRESS().trim());//详细地址
                    GetUserInfoMap.put("headPhoto", orderDetalieInfo.getGuetlist().getPHOTO().trim());//身份读取

                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    Log.i("error", "121231313:" + new Gson().toJson(GetUserInfoMap));
                    RequestBody body = RequestBody.create(JSON, new Gson().toJson(GetUserInfoMap));

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().
                            url(HttpUrl.machine).
                            header("Content-Type", "application/json;charset=utf-8").post(body).build();
                    try {
                        Response response = client.newCall(request).execute();
                        PoliceRespons policeRespons = new Gson().fromJson(response.body().string(), PoliceRespons.class);
                        if (policeRespons.getCode() == 200) {
                            backCall.deal("");
                        } else {
                            Tip.show(App.getInstance(), policeRespons.getMessage(), true);
                        }
                    } catch (IOException e) {
                        Log.i("盒子入住error", e.getMessage());
                    }
                } catch (Exception e) {
                    Log.i("error", "121231313:" + e.getMessage());
                }

            }
        }).start();
    }


     /*   OkGo.<PoliceSerialNumber>post(HttpUrl.machine)
                .tag(activity)
                .params("authCode", Constants.policeCode.trim())
                .params("serialNumber", policeSerialNumber.getResult().trim())
                .params("roomNumber",roomNumber.trim())//房间号
                .params("phoneNumber",phoneNumber.trim())//手机号
                .params("remark", remark.trim())//备注
                .params("certificateNumber",certificateNumber)//身份证
                .params("name",name.trim())//姓名
                .params("gender",gender.trim())//性别
                .params("nation",nation.trim())//名族
                .params("birthTime",birthTime+" 00:00:00")//出生日期,精确到日 yyyy-MM-dd HH:mm:ss
                .params("address",address.trim())//详细地址
                .params("headPhoto",headPhoto.trim())//身份读取
                .execute(new JsonCallBack<PoliceSerialNumber>(PoliceSerialNumber.class) {
                    @Override
                    public void onError(Response<PoliceSerialNumber> response) {
                        super.onError(response);
                        Log.i("盒子", "数据上传失败了");
                        Tip.show(App.getInstance().getApplicationContext(), "服务器连接异常", false);
                    }

                    @Override
                    public void onSuccess(Response<PoliceSerialNumber> response) {
                        super.onSuccess(response);
                        Log.i("盒子", "数据上传成功了" + response.body().getResult());
                        Tip.show(App.getInstance().getApplicationContext(), "服务器连接成功", true);
                    }
                });*/


    public static void getPoliceserialNumber(final BackCall backCall) {

        OkGo.<PoliceSerialNumber>get(HttpUrl.serialNumber)
                .removeAllHeaders()
                .headers(headers)
                .params("authCode", Constants.policeCode)
                .params("type", "1")
                .params("productType", "0102030506_xbzn_pad")
                .params("productIp", IPAddressUtils.getIp())
                .params("productId", IPAddressUtils.getIp())
                .params("productName", "华住会")
                .execute(new JsonCallBack<PoliceSerialNumber>(PoliceSerialNumber.class) {
                    @Override
                    public void onError(com.lzy.okgo.model.Response<PoliceSerialNumber> response) {
                        super.onError(response);
                        Log.i("盒子", "校验码获取失败了" + response.body());
                        Toast.makeText(App.getInstance(), "公安校验码获取失败，请联系前台", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<PoliceSerialNumber> response) {
                        super.onSuccess(response);
                        policeSerialNumber = response.body();
                        backCall.deal(policeSerialNumber.getResult());
                        Log.i("盒子", "校验码获取成功了" + response.body().getResult());
                    }
                });









    }


    //绑定id
    public static void sendSernumber(final Draw draw, String sernumber, String mchid, String orderid, final BackCall backCall) {
        OkGo.<OrderDetalieInfo>get(HttpUrl.SENDSERNUMBER)
                .removeAllHeaders()
                .headers(headers)
                .params("sernumber", sernumber)
                .params("mchid", mchid)
                .params("orderid", orderid)
                .execute(new JsonCallBack<OrderDetalieInfo>(OrderDetalieInfo.class) {

                    @Override
                    public void onError(com.lzy.okgo.model.Response<OrderDetalieInfo> response) {
                        super.onError(response);
                        Toast.makeText(App.getInstance(), "公安盒子接入失败,请联系前台", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<OrderDetalieInfo> response) {
                        super.onSuccess(response);
                        Log.i("盒子", "校验码获取成功了" + response.body().getResult());
                        checkin(response.body(), backCall);
                    }
                });
    }

    public static final String policeUrl = "http://g214c52972.iok.la:22619";
    public static final String checkout = policeUrl + "/box/service/travellers/certificate/idCard/checkout/machine";

    public static void outHotel(final String serialNumber, final String certificateNumber, final String roomNumber, final BackCall backCall) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("盒子退房", "請求退房");
                try {
                    TreeMap<String, String> GetUserInfoMap = RetrofitManager.getMap();
                    GetUserInfoMap.put("authCode", Constants.policeCode);
                    GetUserInfoMap.put("mode", "2");
                    GetUserInfoMap.put("serialNumber", serialNumber);
                    GetUserInfoMap.put("certificateNumber", certificateNumber);
                    GetUserInfoMap.put("roomNumber", Constants.isTest ? "101" : roomNumber);

                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(JSON, new Gson().toJson(GetUserInfoMap));
                    Log.i("盒子退房", "請求退房   参数" + new Gson().toJson(GetUserInfoMap));
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().
                            url(checkout).
                            header("Content-Type", "application/json;charset=utf-8").post(body).build();
                    try {
                        Response response = client.newCall(request).execute();
                        PoliceRespons policeRespons=new Gson().fromJson(response.body().string(),PoliceRespons.class);
                        if(policeRespons.getCode()==200){
                            backCall.deal(Constants.SUCCESS);
                        }else{
                            backCall.deal(Constants.ERROR);
                          Tip.show(App.getInstance(),policeRespons.getMessage(),false);
                        }
                    } catch (IOException e) {
                        backCall.deal(Constants.ERROR);
                        Toast.makeText(App.getInstance(), "", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    backCall.deal(Constants.ERROR);
                    Log.i("error", "121231313:" + e.getMessage());
                }

            }
        }).start();

    }

}
