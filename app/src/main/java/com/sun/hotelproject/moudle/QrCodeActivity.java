package com.sun.hotelproject.moudle;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.sun.hotelproject.R;
import com.sun.hotelproject.app.App;
import com.sun.hotelproject.base.BaseActivity;
import com.sun.hotelproject.dao.DaoSimple;
import com.sun.hotelproject.entity.E_politics;
import com.sun.hotelproject.entity.GuestRoom;
import com.sun.hotelproject.entity.MacKey;
import com.sun.hotelproject.entity.QueryBookOrder;
import com.sun.hotelproject.http.CallServer;
import com.sun.hotelproject.http.HttpListener;
import com.sun.hotelproject.http.JavaBeanRequest;
import com.sun.hotelproject.app.Constants;
import com.sun.hotelproject.utils.HttpUrl;
import com.sun.hotelproject.utils.StringUtils;
import com.sun.hotelproject.utils.Tip;
import com.szxb.smart.pos.jni_interface.Barcode;
import com.szxb.smart.pos.jni_interface.Com4052;
import com.yanzhenjie.nohttp.rest.Response;


import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import butterknife.BindView;
import rx.Subscription;

import static com.sun.hotelproject.utils.StringUtils.byteArrayToShort;
import static com.sun.hotelproject.utils.StringUtils.byteArrayTos;
import static com.sun.hotelproject.utils.StringUtils.getSign;
import static com.sun.hotelproject.utils.StringUtils.getSubKey;
import static com.sun.hotelproject.utils.StringUtils.hex2Bin;
import static com.sun.hotelproject.utils.pulicKey.appid;


public class QrCodeActivity extends BaseActivity {
    @BindView(R.id.linear_sp1)LinearLayout linear_sp1;
    @BindView(R.id.linear_sp2)LinearLayout linear_sp2;
    @BindView(R.id.linear_sp4)LinearLayout linear_sp4;
    @BindView(R.id.sp_tv4)TextView sp_tv4;
    @BindView(R.id.sp_img4)ImageView sp_img4;
    @BindView(R.id.sp4_content3)TextView sp4_content3;
    @BindView(R.id.sp4_img5)ImageView sp4_img5;
    @BindView(R.id.sp4_tv5)TextView sp4_tv5;
    @BindView(R.id.anim_layout)
    RelativeLayout anim_lauout;
    @BindView(R.id.anim_img)ImageView anim_img;
    @BindView(R.id.anim_tv)TextView anim_tv;
    //临时变量存储上次刷卡记录,为了防止重复刷卡
    private String tem = "0";
    //每次扫码后的时间
    private long lastTime = 0;
    private List<Map<String,Object>>list;
//    @BindView(R.id.tv)TextView tv;
//    @BindView(R.id.check)Button check;
//    @BindView(R.id.scan)Button scan;
    private static final String TAG = "QrCodeActivity";
   // private String Code = "TXEZTAQAAAhwBWvz1iwAjAQMBsY9s9QcSYI8W18LXBYzlSOC6cUrUcEsAALgUZ8o=";
    private String qRcode = "";//"TXEZTAQAAAhMBWzNFjv//AQIBo8qu3PWIN91aW6TvBInUw/G/+JjHOW8AAIxGAt8="; //"TXEZTAQAAAR4BWwYi9RwgAQIB7qBPDxQGZqyHhNkRLVabnqGpIklB5yIAADdj8uY=";//"TXEZTAQAAAkoBWwO+mxwgAQIBpgVNQBECYCi+qzLxNJdSKNNSOiaRtDsAACcrNwI=";//
    private byte versionNum ; //版本号
    private byte[] codeID = new byte[4];//码ID
    private byte EIDtype;//EID类型
    private byte []entry_into_force_time = new byte[4];//生效时间
    private byte []Effective_time_length = new byte[2];//有效时长
    private byte Signature_algorithm_ID ;//签名算法ID
    private byte Signature_public_key_index ;//签名公钥索引
    private byte []Root_TOKENID = new byte[8];//根TOKENID
    private byte []Root_signature = new byte[16];//根签名
    private byte []Extended_data_length = new byte[2];//扩展数据长度
    private byte []Extended_data = new byte[]{};//扩展数据
    private byte []Two_dimensional_code_signature = new byte[4];//二维码签名
    private byte[] data ;//二进制解码数据;
    private Map<String,Object> map;
    @BindView(R.id.toolbarBack)Button toolbarBack;
    //private String path = Environment.getDataDirectory().getPath();
    private byte[] oneKey = new byte[16];
    private byte[] twoKey = new byte[16];
    private byte[] base64Data;
    byte[] result = new byte[4];
    private GuestRoom.Bean gBean; //客房信息
    private String locksign;//锁房标记值
    private String k;
    private boolean b;
    Subscription subscription;
    String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/BaiduNetdisk/123.jpg";

    private List<MacKey.Bean>macList;
    private DaoSimple daoSimple;
    private MacKey.Bean macBean;
    TreeMap<String, byte[]> keyMap;
    private QueryBookOrder.Bean qBean;
    private String querytype;
    private int time = 30;
    private Timer timer = new Timer();
    private TimerTask task;
    private Handler handler = new Handler(){};
    @Override
    protected int layoutID() {
        return R.layout.activity_qr_code;
    }

    @Override
    protected void initData() {
        daoSimple = new DaoSimple(this);
        macList = daoSimple.macSelAll();
        macBean = macList.get(0);
        JSONArray jsonArray = JSON.parseArray(macBean.getMac_key_list());
        Log.e(TAG, "initData: " + jsonArray.toString());
        keyMap = new TreeMap<String, byte[]>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            String mac_key = object.getString("mac_key");
            String key_id = object.getString("key_id");
            byte[] rootKey = hex2Bin(mac_key);
            keyMap.put(key_id, rootKey);
        }
        Log.e(TAG, "initData: " + keyMap.toString());

        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.load_animation);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        //getService();
        start();

    }


//    public Runnable timeRunnable =new Runnable() {
//        @Override
//        public void run() {
//                handler.postDelayed(this,2000);
//                    qRcode = barcodeScan();
//                    Log.e(TAG, "run: " + qRcode);
//                    if (!qRcode.equals("")) {
//                        // subscription.unsubscribe();
//                        if (isMyQRcode(qRcode)) {
//                            Tip.show(App.getInstance(), "扫码成功", true);
//                            Log.e(TAG, "run: " + "扫码成功");
//                            init(qRcode);
//                        } else {
//                            Tip.show(App.getInstance(), "无效二维码", true);
//                            Log.e(TAG, "run: 无效二维码");
//                        }
//                    }
//        }
//    };

    /**选择播放图片还是播放视频*/
    public void start() {
        task = new TimerTask() {
            @Override
            public void run() {
                qRcode = barcodeScan();
                Log.e(TAG, "run: " + qRcode);
                if (!qRcode.equals("")) {
                  //  subscription.unsubscribe();
                    if (isMyQRcode(qRcode)) {
                        Tip.show(App.getInstance(), "扫码成功", true);
                        Log.e(TAG, "run: " + "扫码成功");
                        init(qRcode);
                    } else {
                        Tip.show(App.getInstance(), "无效二维码", true);
                        Log.e(TAG, "run: 无效二维码");
                    }
                }
            }
        };
        //定时器开始执行
        timer.schedule(task,0,3000);

    }

    private  void  cancel(){
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
//    void getService() {
//        subscription = Observable.interval(2,2, TimeUnit.SECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.newThread())
//                .subscribe(new Subscriber<Long>() {
//                    @Override
//                    public void onCompleted() {
//                        Tip.show(App.getInstance(), "扫码成功", true);
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG, "onError: "+e.getMessage() );
//                    }
//                    @Override
//                    public void onNext(Long aLong) {
//                        qRcode = barcodeScan();
//                        Log.e(TAG, "run: " + qRcode);
//                        if (!qRcode.equals("")) {
//                            subscription.unsubscribe();
//                            if (isMyQRcode(qRcode)) {
//                                Tip.show(App.getInstance(), "扫码成功", true);
//                                Log.e(TAG, "run: " + "扫码成功");
//                                init(qRcode);
//                            } else {
//                                Tip.show(App.getInstance(), "无效二维码", true);
//                                Log.e(TAG, "run: 无效二维码");
//                            }
//                        }
//                    }
//                });
//
//    }

    @Override
    protected void initView() {
        super.initView();
        k=getIntent().getStringExtra("k");
        switch (k){
            case "1":
                StringUtils.setCorlor(sp_tv4,sp_img4);
                gBean= (GuestRoom.Bean) getIntent().getSerializableExtra("bean");
                locksign=getIntent().getStringExtra("locksign");
                break;
            case "4":
                linear_sp1.setVisibility(View.GONE);
                linear_sp4.setVisibility(View.VISIBLE);
                qBean = (QueryBookOrder.Bean) getIntent().getSerializableExtra("bean");
                querytype = getIntent().getStringExtra("querytype");
                StringUtils.setCorlor(sp4_tv5,sp4_img5,sp4_content3,querytype);
                break;
                default:
                    break;
        }
              //  getService();
    }

    void init(String qRcode){
        Log.e(TAG, "init: "+"进入方法" );
        //base64解码
        base64Data = Base64.decode(qRcode.substring(5),Base64.DEFAULT);
        System.out.println("base64Data: " + Arrays.toString(base64Data));
//        // 初始化根密钥
//        byte[] rootKey1 = hex2Bin("279f71cb32f447cfa81a4c062e49d082");
//        System.out.println("root key1: " + Arrays.toString(rootKey1));
//        byte[] rootKey2 = hex2Bin("4263126a8da04bfa909248a46a495faf");
//        System.out.println("root key2: " + Arrays.toString(rootKey2));
//        byte[] rootKey3 = hex2Bin("cefe54c505fe42bc95a9e10f2cedbd82");
//        System.out.println("root key3: " + Arrays.toString(rootKey3));
//        byte[] rootKey4 = hex2Bin("fa2868cda6750eaf51b72828bc2ab5e9");
//        System.out.println("root key4: " + Arrays.toString(rootKey4));
//        byte[] rootKey5 = hex2Bin("380b1cf55acf5c598ee476f8d1403cab");
//        System.out.println("root key5: " + Arrays.toString(rootKey5));
//        TreeMap<String, byte[]> keyMap = new TreeMap<String, byte[]>();
//        keyMap.put("1", rootKey1);
//        keyMap.put("2", rootKey2);
//        keyMap.put("3", rootKey3);
//        keyMap.put("4", rootKey4);
//        keyMap.put("5", rootKey5);

        // 参与签名的数据
        data = new byte[base64Data.length - 4];
        System.arraycopy(base64Data, 0, data, 0, data.length);
        System.out.println("sign data: " + Arrays.toString(data));
        //EID类型
        EIDtype = base64Data[5];
        // 二维码签名
        //Two_dimensional_code_signature = new byte[4];
        System.arraycopy(base64Data, base64Data.length - 4, Two_dimensional_code_signature, 0, 4);
        System.out.println("sign: " + Arrays.toString(Two_dimensional_code_signature));
        byte[] divData1 = new byte[16];
        byte[] divData2 = new byte[16];
        // 二维码版本
        versionNum = base64Data[0];
        // 目前只支持版本1
        if (versionNum == 1) {
            // 二维码ID
            System.arraycopy(base64Data, 1, codeID, 0, 4);
            System.out.println("codeID: " + Arrays.toString(codeID));
            // 二维码生成时间
            System.arraycopy(base64Data, 6, entry_into_force_time, 0, 4);
            System.out.println("Effective_time_length: " + Arrays.toString(entry_into_force_time));
            //有效时长
            System.arraycopy(base64Data, 10, Effective_time_length, 0, 2);
            System.out.println("Effective_time_length: " + Arrays.toString(Effective_time_length));
            // 根token ID
            System.arraycopy(base64Data, 14, Root_TOKENID, 0, 8);
            System.out.println("Root_TOKENID : " + Arrays.toString(Root_TOKENID));

            // 根签名
            System.arraycopy(base64Data, 22, Root_signature, 0, 16);
            System.out.println("Root_signature : " + Arrays.toString(Root_signature));
            //扩展数据长度
            System.arraycopy(base64Data, 38, Extended_data_length, 0, 2);
            System.out.println("Extended_data_length : " + Arrays.toString(Extended_data_length));
            // 签名算法ID
            Signature_algorithm_ID = base64Data[12];
            // 目前只支持算法1
            if (Signature_algorithm_ID == 1) {
                // 根密钥ID，获取根密钥
                Signature_public_key_index = base64Data[13];
                byte[] rootKey = keyMap.get(String.valueOf(Signature_public_key_index));
                System.out.println("root key" + Arrays.toString(rootKey));
                // 第一次分散
                System.arraycopy(Root_TOKENID, 0, divData1, 0, 8);
                for (int i = 0; i < 8; ++i) {
                    //二进制取反
                    divData1[i + 8] = (byte) ~divData1[i];
                }
                System.out.println("div data1: " + Arrays.toString(divData1));

                try {
                    oneKey = getSubKey(rootKey, divData1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("sub key1: " + Arrays.toString(oneKey));
                // 第二次分散

                System.arraycopy(codeID, 0, divData2, 0, 4);
                System.arraycopy(entry_into_force_time, 0, divData2, 4, 4);
                for (int i = 0; i < 8; ++i) {
                    divData2[i + 8] = (byte) ~divData2[i];
                }
                System.out.println("div data2: " + Arrays.toString(divData2));

                try {
                    twoKey = getSubKey(oneKey, divData2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("sub key2: " + Arrays.toString(twoKey));
                // 计算签名
                try {
                    result = getSign(data, twoKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("calc sign: " + Arrays.toString(result));
                b = Arrays.equals(Two_dimensional_code_signature, result);
                // 校验签名
                System.out.println("verify result: " + b);
                if (b) {
                    if(Constants.isTest){
                        epccomcation("");
                    }else {
                        Intent intent = new Intent(this, FaceRecognitionActivity.class);
                        if (k.equals("4")) intent.putExtra("querytype", querytype);
                        intent.putExtra("k", k);intent.putExtra("mchid", mchid);
                        startActivityForResult(intent, 1);
                    }
                }else {
                    Tip.show(getApplicationContext(),"二维码不合法",false);
                }
            } else {
                System.out.println("unsupport qrcode sign alg");
            }
        } else {
            System.out.println("unsupport qrcode version");
        }
        //tv.setText(getStorgeFileSize(path,this));
//        tv.append("\n提供的二维码串 qRcode--->"+qRcode+
//                "\n解码后的 base64Data--->"+Arrays.toString(base64Data)+
//                "\n去掉签名后的 data--->"+Arrays.toString(data)+
//                "\n版本号 versionNum--->"+versionNum+
//                "      码id codeID--->"+Arrays.toString(codeID)+
//                "      EID类型 EIDtype--->"+EIDtype+
//                "\n生效时间 entry_into_force_time--->"+Arrays.toString(entry_into_force_time)+
//                "\n有效时长 Effective_time_length--->"+Arrays.toString(Effective_time_length)+
//                "\n签名算法id Signature_algorithm_ID--->"+Signature_algorithm_ID+
//                "\n签名公钥索引 Signature_public_key_index--->"+Signature_public_key_index+
//                "\n根tokenid Root_TOKENID--->"+Arrays.toString(Root_TOKENID)+
//                "\n根签名 Root_signature--->"+Arrays.toString(Root_signature)+
//                "\n扩展数据长度 Extended_data_length--->"+Arrays.toString(Extended_data_length)+
//                "\n二维码 Two_dimensional_code_signature--->"+Arrays.toString(Two_dimensional_code_signature)+
//                "\n合并后新的tokenid  divData1 --->"+Arrays.toString(divData1)+
//                "\n码id和生效时间合并后的数组  divData2 --->"+Arrays.toString(divData2)+
//                "\n一级分散秘钥 oneKey --->"+Arrays.toString(oneKey)+
//                "\n二级分散秘钥 twoKey --->"+Arrays.toString(twoKey)+
//                "\n生成结果值 result --->"+Arrays.toString(result)+
//                "\n验证签名 b --->"+b
//        );
    }

    @Override
    protected void onStop() {
        super.onStop();
     //  handler.removeCallbacks(timeRunnable);
        cancel();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
       // task();
        //getService();
        if ( timer !=null){
            timer.cancel();
            timer =null;
            timer = new Timer();
            start();
        }
        timer =new Timer();
        start();
       // handler.post(timeRunnable);

    }


//    @OnClick(R.id.toolbarBack)
//    void OnClick(){
//        startActivity(new Intent(getApplicationContext(),MainActivity.class));
//        finish();
//    }
//    @OnClick({R.id.check,R.id.scan})
//    void OnClick(View v){
//        switch (v.getId()) {
//            case R.id.check:
//                if (qRcode.equals(""))return;
//                b = Arrays.equals(Two_dimensional_code_signature, result);
//                if (b) {
//                    Intent intent = new Intent(this, FaceRecognitionActivity.class);
//                    intent.putExtra("k", "6");
//                    startActivityForResult(intent, 1);
//                    //  epccomcation(path);
//                }else {
//                    Tip.show(getApplicationContext(),"二维码不合法",false);
//                }
//                break;
//            case R.id.scan:
//                //startActivityForResult(new Intent(QrCodeActivity.this,TestActivity.class),2);
//
////                qRcode = barcodeScan();
////                init(qRcode);
//                break;
//        }
//    }
    /**
     * 二维码条形码
     */
    Barcode bar;
    private  int fd;
    String barcodeScan() {
        String str;

        byte[] sendbyte = { 0x16, 0x54, 0x0d };

        fd = Com4052.Com4052Open();
        if(fd < 0)
        {
            return null;
        }
        Com4052.Com4052Control(fd, 10);
        bar = new Barcode();
        bar.BarcodeOpen();

//		str = bar.BarcodeTrans(sendbyte, 3, 10000);
        str = bar.BarcodeTrans(sendbyte, 0, 10000);
        bar.BarcodeClose();
        Com4052.Com4052Close(fd);
        return str;
    }


    Animation operatingAnim;
    /**
     * E政通接口请求
     */
    void epccomcation(String path){
//        anim_lauout.setVisibility(View.VISIBLE);
//        anim_img.setAnimation(operatingAnim);
//        anim_img.startAnimation(operatingAnim);
        anim_tv.setText("正在加载中......");
        Map<String,Object> map = new HashMap<>();
        map.put("appid",appid);
        map.put("qr_code",qRcode);
        if(Constants.isTest){
            map.put("imgB64", new File(Environment.getExternalStorageDirectory()+"/renzhen.jpg"));
        }else {
            map.put("imgB64", new File(path));
        }
        map.put("qrcodeid",qRcode.substring(5));
        map.put("eid",EIDtype+"");
        map.put("createtime", byteArrayTos(entry_into_force_time));
        map.put("validtime",byteArrayToShort(Effective_time_length)+"");
        Log.e(TAG, "epccomcation: "+map.toString());
        JavaBeanRequest javaBeanRequest = new JavaBeanRequest(HttpUrl.EPOCOMCATION,E_politics.class);
        javaBeanRequest.set(map);

//        JsonRequest request = new JsonRequest(HttpUrl.EPOCOMCATION);
//        request.set(map);

        CallServer.getHttpclient().add(0, javaBeanRequest, new HttpListener<E_politics>() {
            @Override
            public void success(int what, Response<E_politics> response) {
                if (TextUtils.equals(response.get().getRescode(),"0000")){
                    anim_lauout.setVisibility(View.GONE);
                    anim_img.clearAnimation();
                    Intent intent = new Intent();
                        switch (k){
                            case "1":
                                intent.setClass(QrCodeActivity.this,PhoneMsg.class);
                                intent.putExtra("name",response.get().getList().get(0).getEncryptname());
                                intent.putExtra("card_id",response.get().getList().get(0).getEncryptidno());
                                intent.putExtra("bean", gBean);
                                intent.putExtra("locksign", locksign);
                                intent.putExtra("k", k);
                                break;
                            case "4":
                                intent.setClass(QrCodeActivity.this,PaymentActivity.class);
                                intent.putExtra("name",response.get().getList().get(0).getEncryptname());
                                intent.putExtra("card_id",response.get().getList().get(0).getEncryptidno());
                                intent.putExtra("bean", qBean);
                                intent.putExtra("querytype",querytype);
                                intent.putExtra("k", k);
                                break;
                        }
                        startActivity(intent);
                        finish();
                        Tip.show(getApplicationContext(),"成功",true);
                        Log.e(TAG, "success: "+"成功" );
                }else {
                    Tip.show(getApplicationContext(),response.get().getResult(),false);
                    Log.e(TAG, "success: "+"失败" +response.get().getResult());
                    anim_lauout.setVisibility(View.GONE);
                    anim_img.clearAnimation();
                }
            }
            @Override
            public void fail(int what, String e) {
                anim_lauout.setVisibility(View.GONE);
                anim_img.clearAnimation();
                Log.e(TAG, "fail: "+"网络连接异常"+e );
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1 && resultCode == Activity.RESULT_OK){
            String path = data.getStringExtra("path");
            epccomcation(path);
//            Intent intent = new Intent();
//            switch (k){
//                case "1":
//                    intent.setClass(QrCodeActivity.this,PhoneMsg.class);
//                    intent.putExtra("name","孙串");
//                    intent.putExtra("card_id","429004199202192753");
//                    intent.putExtra("bean", gBean);
//                    intent.putExtra("locksign", locksign);
//                    intent.putExtra("k", k);
//                    break;
//                case "4":
//                    intent.setClass(QrCodeActivity.this,PaymentActivity.class);
//                    intent.putExtra("name","孙串");
//                    intent.putExtra("card_id","429004199202192753");
//                    intent.putExtra("bean", qBean);
//                    intent.putExtra("querytype",querytype);
//                    intent.putExtra("k", k);
//                    break;
//            }
//            startActivity(intent);
//            finish();
        }
    }

    /**
     * @param qrcode 二维码数据
     * @return .
     */
    public boolean isMyQRcode(String qrcode) {
        return qrcode != null && qrcode.indexOf("eID01") == 0;
    }

    public boolean checkQR(long currentTime, long lastTime) {
        return currentTime - lastTime > 2000;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancel();
      // handler.removeCallbacks(timeRunnable);
      //  subscription.unsubscribe();
    }
}
