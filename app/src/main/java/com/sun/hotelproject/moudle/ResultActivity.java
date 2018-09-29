package com.sun.hotelproject.moudle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.sun.hotelproject.R;
import com.sun.hotelproject.entity.PosRecord;
import com.sun.hotelproject.entity.QRCode;
import com.sun.hotelproject.entity.QRScanMessage;
import com.sun.hotelproject.http.CallServer;
import com.sun.hotelproject.http.HttpListener;
import com.sun.hotelproject.http.JsonRequest;
import com.sun.hotelproject.utils.DataTime;
import com.sun.hotelproject.utils.HttpUrl;
import com.sun.hotelproject.utils.Utils;
import com.tencent.wlxsdk.WlxSdk;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xubingbing on 2017/9/19.
 */

public class ResultActivity extends AppCompatActivity {

    private TextView mResultText;
    private ImageView mResultImage;
    private WlxSdk wxSdk;
    private static final String TAG = "ResultActivity";
    private String qrCode,record;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
//        PosScanInit posScanInit = new PosScanInit();
//        posScanInit.init(this);
        mResultImage = (ImageView) findViewById(R.id.result_image);
        mResultText = (TextView) findViewById(R.id.result);

        Intent data = getIntent();

        String result = data.getStringExtra("result");
        mResultText.setText(result);
        Bitmap barcode = null;
        byte[] compressedBitmap = data.getByteArrayExtra("resultByte");
        if (compressedBitmap != null) {
            barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
            barcode = barcode.copy(Bitmap.Config.RGB_565, true);
            mResultImage.setImageBitmap(barcode);
        }
        posScan(result);
    }
    public void posScan(String qrcode) {
        Log.e(TAG, "posScan: "+"11111" );
        if (wxSdk == null) wxSdk = new WlxSdk();
        int init = wxSdk.init(qrcode);
        Log.e(TAG, "posScan: init"+init );
        int key_id = wxSdk.get_key_id();
        Log.e(TAG, "posScan: key_id"+key_id );
        String open_id = wxSdk.get_open_id();
        Log.e(TAG, "posScan: open_id"+open_id );
        String mac_root_id = wxSdk.get_mac_root_id();
        Log.e(TAG, "posScan: mac_root_id"+mac_root_id );
        Log.e(TAG,
                "posScan(TenPosReportManager.java:50)init=" + init + "key_id=" + key_id + "open_id=" + open_id + "mac_root_id=" + mac_root_id);
        int verify = 0;

                if (init == 0 && key_id > 0) {
                    //String open_id, String pub_key, int payfee, byte scene, byte scantype, String pos_id, String pos_trx_id, String aes_mac_root
                    verify = wxSdk.verify(open_id
                            , "049C11FE1533BD248FD846F27B98019C4A39A1B737CC248F449EA5299EAB8F22358E16B03E57927185BFFF76D74200F3F203BB856F6C92EB92"
                            , 1//金额,上线修改为App.getPosManager().getMarkedPrice()
                            , (byte) 1
                            , (byte) 1
                            , Utils.Getnum() //CZBusApp.getBusRoom().DriverNo()
                            , Utils.Getnum()
                            , "20451254B3C10A6F88D4EAAC2077E854");
                    Log.e(TAG, "posScan: verify"+verify );
            Log.e(TAG, "posScan: "+"2222" );
                    String record = wxSdk.get_record();
                    PosRecord posRecord = new PosRecord();
                    posRecord.setOpen_id(open_id);
                    posRecord.setQr_code(qrcode);
                    posRecord.setOrder_time(1000l);
                    posRecord.setTotal_fee(1);//金额，上线修改为posRecord.setTotal_fee(App.getPosManager().getMarkedPrice());
                    posRecord.setPay_fee(1);//实际扣款金额，上线修改为posRecord.setTotal_fee(App.getPosManager().getPayMarkPrice());
                    posRecord.setCity_code("1");
                    posRecord.setOrder_desc("1");//线路名
                    posRecord.setRecord(record);
                    posRecord.setBus_no("1");
                    posRecord.setDriveno("1");
                    posRecord.setBus_line_name("1");
                    posRecord.setPos_no("1");
                    posRecord.setBus_line_no("1");
                    posRecord.setIn_station_id("1");
                    posRecord.setIn_station_name("1");

                    request(new QRScanMessage(posRecord, verify));
                }
            Log.e(TAG, "posScan: "+"33333" );
           // }
     //   }
    }

    public void request(final QRScanMessage message) {
        Log.d(TAG,
                "request(PosRequest.java:58)QRScanMessage信息=" + message.toString());
        rx.Observable.create(new rx.Observable.OnSubscribe<QRScanMessage>() {
            @Override
            public void call(Subscriber<? super QRScanMessage> subscriber) {
                subscriber.onNext(message);
            }
        }).filter(new Func1<QRScanMessage, Boolean>() {
            @Override
            public Boolean call(QRScanMessage qrScanMessage) {
                switch (qrScanMessage.getResult()) {
                    case QRCode.EC_SUCCESS://验码成功
                        //CZBusApp.getBusRoom().setQrPayNum();
                        Log.e(TAG, "call: 扫码成功" );
//                        SoundPoolUtil.play(CZBusApp.getBusRoom().getType() == 1 ? 27 : 19);
//                        BusToast.showToast(CZBusApp.getInstance(), "扫码成功", true);
                        return true;
                    case QRCode.QR_ERROR://非腾讯或者小兵二维码
                    case QRCode.EC_CARD_CERT_SIGN_ALG_NOT_SUPPORT://卡证书签名算法不支持
                    case QRCode.EC_MAC_ROOT_KEY_DECRYPT_ERR://加密的mac根密钥解密失败
                    case QRCode.EC_QRCODE_SIGN_ALG_NOT_SUPPORT://二维码签名算法不支持
                    case QRCode.EC_OPEN_ID://输入的openid不符
                    case QRCode.EC_CARD_CERT://卡证书签名错误
                        Log.e(TAG, "call: 二维码错误" );
//                        SoundPoolUtil.play(2);
//                        BusToast.showToast(CZBusApp.getInstance(), "二维码有误[" + message.getResult() + "]", false);
                        break;
                    case QRCode.SOFTWARE_EXCEPTION:
                        Log.e(TAG, "call: \"软件出现异常[\" + QRCode.SOFTWARE_EXCEPTION + \"]\"" );
//                        SoundPoolUtil.play(4);
//                        BusToast.showToast(CZBusApp.getInstance(), "软件出现异常[" + QRCode.SOFTWARE_EXCEPTION + "]", false);
                        break;
                    case QRCode.EC_USER_PUBLIC_KEY://卡证书用户公钥错误
                        Log.e(TAG, "call: \"卡证书数据错误[\" + QRCode.EC_USER_PUBLIC_KEY + \"]\"");
//                        SoundPoolUtil.play(5);
//                        BusToast.showToast(CZBusApp.getInstance(), "卡证书数据错误[" + QRCode.EC_USER_PUBLIC_KEY + "]", false);
                        break;
                    case QRCode.EC_FORMAT://二维码格式错误
                        Log.e(TAG, "call: \"二维码格式错误[\" + QRCode.EC_FORMAT + \"]\"" );
//                        SoundPoolUtil.play(6);
//                        BusToast.showToast(CZBusApp.getInstance(), "二维码格式错误[" + QRCode.EC_FORMAT + "]", false);
                        break;
                    case QRCode.EC_USER_SIGN://二维码签名错误
                        Log.e(TAG, "call: \"二维码签名错误[\" + QRCode.EC_USER_SIGN + \"]\"");
//                        SoundPoolUtil.play(7);
//                        BusToast.showToast(CZBusApp.getInstance(), "二维码签名错误[" + QRCode.EC_USER_SIGN + "]", false);
                        break;
                    case QRCode.EC_MAC_SIGN_ERR://mac校验失败
                        Log.e(TAG, "call: \"请更新二维码参数[\" + QRCode.EC_MAC_SIGN_ERR + \"]\"" );
//                        SoundPoolUtil.play(8);
//                        BusToast.showToast(CZBusApp.getInstance(), "请更新二维码参数[" + QRCode.EC_MAC_SIGN_ERR + "]", false);
                        break;
                    case QRCode.EC_CARD_PUBLIC_KEY://卡证书公钥错误
                        Log.e(TAG, "call: \"请更新二维码参数[\" + QRCode.EC_CARD_PUBLIC_KEY + \"]\"" );
//                        SoundPoolUtil.play(8);
//                        BusToast.showToast(CZBusApp.getInstance(), "请更新二维码参数[" + QRCode.EC_CARD_PUBLIC_KEY + "]", false);
                        break;
                    case QRCode.EC_FEE://超出最大金额
                        Log.e(TAG, "call: \"二维码格式错误[\" + QRCode.EC_FORMAT + \"]\"" );
//                        SoundPoolUtil.play(9);
//                        BusToast.showToast(CZBusApp.getInstance(), "超出最大金额[" + QRCode.EC_FEE + "]", false);
                        break;
                    case QRCode.EC_BALANCE://余额不足
                        Log.e(TAG, "call: \"二维码格式错误[\" + QRCode.EC_FORMAT + \"]\"" );
//                        SoundPoolUtil.play(10);
//                        BusToast.showToast(CZBusApp.getInstance(), "余额不足[" + QRCode.EC_BALANCE + "]", false);
                        break;
                    case QRCode.EC_PARAM_ERR://参数错误
                        Log.e(TAG, "call: \"二维码格式错误[\" + QRCode.EC_FORMAT + \"]\"" );
//                        SoundPoolUtil.play(11);
//                        BusToast.showToast(CZBusApp.getInstance(), "参数错误[" + QRCode.EC_PARAM_ERR + "]", false);
                        break;
                    case QRCode.EC_FAIL://系统异常
                        Log.e(TAG, "call: \"二维码格式错误[\" + QRCode.EC_FORMAT + \"]\"" );
//                        SoundPoolUtil.play(12);
//                        BusToast.showToast(CZBusApp.getInstance(), "系统异常[" + QRCode.EC_FAIL + "]", false);
                        break;
                    case QRCode.REFRESH_QR_CODE://请刷新二维码
                        Log.e(TAG, "call: \"二维码格式错误[\" + QRCode.EC_FORMAT + \"]\"" );
//                        SoundPoolUtil.play(13);
//                        BusToast.showToast(CZBusApp.getInstance(), "请刷新二维码[" + QRCode.REFRESH_QR_CODE + "]", false);
                        break;
                    case QRCode.EC_CODE_TIME://二维码过期
                        Log.e(TAG, "call: \"二维码格式错误[\" + QRCode.EC_FORMAT + \"]\"" );
//                        SoundPoolUtil.play(13);
//                        BusToast.showToast(CZBusApp.getInstance(), "请刷新二维码[" + QRCode.EC_CODE_TIME + "]", false);
                        break;

                    case QRCode.EC_CARD_CERT_TIME://卡证书过期，提示用户联网刷新二维码
                        Log.e(TAG, "call: \"二维码格式错误[\" + QRCode.EC_FORMAT + \"]\"" );
//                        SoundPoolUtil.play(14);
//                        BusToast.showToast(CZBusApp.getInstance(), "请联网刷新二维码[" + QRCode.EC_CARD_CERT_TIME + "]", false);
                        break;
                    default:
                        Log.e(TAG, "call: 验码失败" );
//                        SoundPoolUtil.play(3);
//                        BusToast.showToast(CZBusApp.getInstance(), "验码失败[" + qrScanMessage.getResult() + "]", false);
                        break;
                }

                return false;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Action1<QRScanMessage>() {
                    @Override
                    public void call(QRScanMessage qrScanMessage) {
                        qrCode = qrScanMessage.getPosRecord().getQr_code();
                        record = qrScanMessage.getPosRecord().getRecord();

                            Intent intent = new Intent(ResultActivity.this, FaceRecognitionActivity.class);
//                        intent.putExtra("qrCode",qrScanMessage.getPosRecord().getQr_code());
//                        intent.putExtra("record",qrScanMessage.getPosRecord().getRecord());
                            intent.putExtra("k", "6");
                            startActivityForResult(intent, 1);

                        //epccomcation(qrScanMessage.getPosRecord().getQr_code(),qrScanMessage.getPosRecord().getRecord());
//                        qrScanMessage.getPosRecord().setMch_trx_id(CZBusApp.getBusRoom().getmchTrxId());
//                        if (CZBusApp.getBusRoom().getType() == 1) {
////                            Map<String, Object> map = ParamsUtil.requestMapMany(qrScanMessage.getPosRecord());
////                            requestTX(UrlComm.MANY_PAY, UrlComm.getInstance().XB_MANY_PAY(), map);
//                        } else {
////                            Map<String, Object> map = ParamsUtil.requestMapOne(qrScanMessage.getPosRecord());
////                            requestTX(UrlComm.ONE_PAY, UrlComm.getInstance().XB_ONE_PAY(), map);
//                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1 && resultCode == Activity.RESULT_OK){
            String path = data.getStringExtra("path");
            epccomcation(qrCode,record,path);
        }
    }

    /**
     * E政通接口请求
     */
    void epccomcation(String qrcode,String record,String path){
        Map<String,Object> map = new HashMap<>();
        map.put("appid","001");
        map.put("qr_code",qrcode);
            map.put("imgB64", new File(path));
        map.put("qrcodeid",record);
        map.put("eid","1");
        map.put("createtime",DataTime.currentTime());
        map.put("validtime",DataTime.TomorrowTime());
        JsonRequest request = new JsonRequest(HttpUrl.EPOCOMCATION);
        request.set(map);
        CallServer.getHttpclient().add(0, request, new HttpListener<JSONObject>() {
            @Override
            public void success(int what, Response<JSONObject> response) {
                try {
                    String rescode = response.get().getString("rescode");
                    if (TextUtils.equals(rescode, "0000")) {
                        Log.e(TAG, "success: "+response.toString());
                        // BusToast.showToast(CZBusApp.getInstance(), "机具上报成功", true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail(int what, String e) {
                Log.e(TAG, "fail: "+"网络连接异常" );
            }
        });
    }
}



