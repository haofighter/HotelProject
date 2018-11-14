package com.sun.hotelproject.moudle;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import android.widget.Button;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.alibaba.android.arouter.facade.annotation.Route;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.net.md5.Constant;
import com.sun.hotelproject.R;
import com.sun.hotelproject.base.BaseActivity;

import com.sun.hotelproject.entity.FaceRecognition;
import com.sun.hotelproject.entity.GuestRoom;
import com.sun.hotelproject.entity.QueryBookOrder;
import com.sun.hotelproject.entity.SeqNo;
import com.sun.hotelproject.moudle.id_card.IDCardInfo;
import com.sun.hotelproject.moudle.id_card.IDCardReaderCallBack;
import com.sun.hotelproject.moudle.id_card.IDCarderReader;

import com.sun.hotelproject.moudle.id_card.ReadIDThread;
import com.sun.hotelproject.moudle.net.HotelHttp;
import com.sun.hotelproject.utils.ActivityManager;
import com.sun.hotelproject.utils.CommonSharedPreferences;
import com.sun.hotelproject.app.Constants;
import com.sun.hotelproject.utils.HttpUrl;
import com.sun.hotelproject.utils.JsonCallBack;
import com.sun.hotelproject.utils.StringUtils;
import com.sun.hotelproject.utils.Tip;
import com.szxb.smart.pos.jni_interface.Card_Sender;


import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static com.sun.hotelproject.utils.PlaySound.play;

/**
 * @author sun
 * 时间 2017/11/22
 * 身份证界面
 */
@Route(path = "/hotel/identification")
public class IdentificationActivity extends BaseActivity {
    private static final String TAG = "IdentificationActivity";
    @BindView(R.id.piv_tv)
    TextView piv_tv;
    @BindView(R.id.sp_tv4)
    TextView sp_tv4;
    @BindView(R.id.sp_tv3)
    TextView sp_tv3;
    @BindView(R.id.linear_sp1)
    LinearLayout linear_sp1;
    @BindView(R.id.linear_sp2)
    LinearLayout linear_sp2;
    @BindView(R.id.linear_sp4)
    LinearLayout linear_sp4;
    @BindView(R.id.sp2_content3)
    TextView sp2_content3;
    @BindView(R.id.sp_content4)
    TextView sp_content4;
    @BindView(R.id.sp2_tv3)
    TextView sp2_tv3;
    @BindView(R.id.sp_img4)
    ImageView sp_img4;
    @BindView(R.id.sp2_img3)
    ImageView sp2_img3;
    @BindView(R.id.sp4_img2)
    ImageView sp4_img2;
    @BindView(R.id.sp4_tv2)
    TextView sp4_tv2;
    @BindView(R.id.sp4_img5)
    ImageView sp4_img5;
    @BindView(R.id.sp4_tv5)
    TextView sp4_tv5;
    @BindView(R.id.sp4_tv3)
    TextView sp4_tv3;
    @BindView(R.id.sp4_img3)
    ImageView sp4_img3;
    @BindView(R.id.sp4_content3)
    TextView sp4_content3;
    @BindView(R.id.sp4_content4)
    TextView sp4_content4;
    @BindView(R.id.toolbarBack)
    Button toolbarBack;
    byte[] bs;
    boolean key;
    int count = 0;
    IDCardInfo idCardInfo;//身份证读取的信息
    private IDCardReaderCallBack readerCallBack;
    private IDCarderReader idCarderReader;
    private GuestRoom.Bean gBean;
    private String locksign;
    private String k;
    private String querytype = "";
    private QueryBookOrder.Bean qBean;
    private Double price = 0.00;
    private String mchid;
    private boolean isIntent = true;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //count = 6;
            if (msg.what == 1) {
                idCardInfo = (IDCardInfo) msg.obj;

                if (idCardInfo != null) {
                    play(R.raw.readcard_success, IdentificationActivity.this);
                    //  Log.e(TAG, "handleMessage: "+idCardInfo.toString() );
                    //  piv_tv.setText("读取成功!");
                    // handler.removeCallbacks(task);
                    Log.e(TAG, "handleMessage: " + idCardInfo.toString());
                    if (isIntent) {
                        if (k.equals("1")) {
                            Intent intent = new Intent(IdentificationActivity.this, FaceRecognitionActivity.class);
                            intent.putExtra("bean", gBean);
                            intent.putExtra("locksign", locksign);
                            intent.putExtra("mchid", mchid);
                            intent.putExtra("name", idCardInfo.getStrName().replace(" ", ""));
                            intent.putExtra("idCard", idCardInfo);
                            intent.putExtra("id_CardNo", idCardInfo.getStrIdCode());
                            intent.putExtra("k", k);
                            startActivity(intent);
                            finish();
                        } else if (k.equals("2")) {//续住
                            Intent intent = new Intent(IdentificationActivity.this, RenwalActivity.class);
                            intent.putExtra("idCard", idCardInfo);
                            intent.putExtra("k", k);
                            intent.putExtra("locksign", locksign);
                            intent.putExtra("querytype", querytype);
                            startActivity(intent);
                            finish();
                        } else if (k.equals("4")) {
                            if (querytype.equals("")) {
                                Intent intent = new Intent(IdentificationActivity.this, FaceRecognitionActivity.class);
                                intent.putExtra("name", idCardInfo.getStrName());
                                intent.putExtra("id_CardNo", idCardInfo.getStrIdCode());
                                intent.putExtra("birth", idCardInfo.getStrBirth());
                                intent.putExtra("querytype", querytype);
                                intent.putExtra("mchid", mchid);
                                intent.putExtra("idCard", idCardInfo);
                                intent.putExtra("k", k);
                                intent.putExtra("bean", qBean);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(IdentificationActivity.this, OrderDetailsActivity.class);
                                intent.putExtra("phone_No", idCardInfo.getStrName());
                                intent.putExtra("k", k);
                                intent.putExtra("idCard", idCardInfo);
                                intent.putExtra("bean", qBean);
                                intent.putExtra("locksign", locksign);
                                intent.putExtra("querytype", querytype);
                                startActivity(intent);
                                finish();
                            }
                        }
                    } else {
                    }
                } else {
                    play(R.raw.readcard_fail, IdentificationActivity.this);
                    Log.e(TAG, "handleMessage: 读取失败");
                    handler.postDelayed(task, 5000);
                    //piv_tv.setText("读取失败！");
                }
            }
        }
    };
    Runnable task = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, 5000);
            Log.e(TAG, "readCard_No: " + "5");
            readCard_No();
        }
    };

    @Override
    public void toolBacBcak() {
        super.toolBacBcak();
        HotelHttp.cancalLockRoom(k, gBean, mchid);
    }

//    /**
//     * 生成流水号
//     */
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    public void get(final IDCardInfo idCardInfo, final String path) {
//        OkGo.<SeqNo>get(HttpUrl.SEQNO)
//                .tag(this)
//                .execute(new JsonCallBack<SeqNo>(SeqNo.class) {
//                    @Override
//                    public void onSuccess(Response<SeqNo> response) {
//                        super.onSuccess(response);
//                        Log.d(TAG, "onSuccess() called with: response = [" + response.body().toString() + "]");
//                        if (response.body().getRescode().equals("00") && response.body().getRetcode().equals("0")) {
//                            Post(response.body().getSeq_no(), response.body().getAccount(), path, idCardInfo);
//                        } else {
//                            Tip.show(getApplicationContext(), response.body().getRetmsg(), false);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Response<SeqNo> response) {
//                        Tip.show(getApplicationContext(), "服务器连接异常", false);
//                        super.onError(response);
//                    }
//                });
//    }
//
//    /**
//     * 人脸识别
//     *
//     * @param seq_no  流水号
//     * @param account 账号
//     */
//    public void Post(String seq_no, String account, String path, final IDCardInfo idCardInfo) {
//        OkGo.<FaceRecognition>post(HttpUrl.FACERECOQNITION)
//                .tag(this)
//                .retryCount(3)//超时重连次数
//                .cacheTime(3000)//缓存过期时间
//                .params("name", idCardInfo.getStrName())
//                .params("creid_no", idCardInfo.getStrIdCode().trim())
//                .params("account", account)
//                .params("type", 8)
//                .params("seq_no", seq_no)
//                .params("photo_check_live", 0) //0防翻拍，1关闭防翻拍
//                .isMultipart(true)//强制使用multipart/form-data 表单上传
//                .params("image_fn", new File(path))
//                .execute(new JsonCallBack<FaceRecognition>(FaceRecognition.class) {
//                    @Override
//                    public void onSuccess(Response<FaceRecognition> response) {
//                        super.onSuccess(response);
//
//                        Log.d(TAG, "人脸识别onSuccess() called with: response = [" + response.body() + "]");
//                        if (Constants.isTest) {
//                            FaceRecognition response1 = response.body();
//                            response1.setStatus("1");
//                            response1.setRetcode("0");
//                            response.setBody(response1);
//                        }
//                        if (response.body().getRescode().equals("00") && response.body().getRetcode().equals("0")) {
//                            if (response.body().getStatus().equals("1")) {//认证成功
//                                play(R.raw.shibei_success, getApplicationContext());
//                                Intent intent = new Intent(IdentificationActivity.this, PhoneMsg.class);
//                                intent.putExtra("idCard", idCardInfo);
//                                intent.putExtra("k", k);
//                                intent.putExtra("locksign", locksign);
//                                intent.putExtra("bean", gBean);
//                                intent.putExtra("name", idCardInfo.getStrName());
//                                startActivity(intent);
//                                finish();
//                            } else {
//                                play(R.raw.shibei_fail, getApplicationContext());
//                            }
//                        } else {
//                            play(R.raw.shibei_fail, getApplicationContext());
//                        }
//                    }
//
//                    @Override
//                    public void onError(Response<FaceRecognition> response) {
//                        Log.d(TAG, "人脸识别onSuccess() called with: response = [" + response.body() + "]");
//                        Tip.show(getApplicationContext(), "服务器连接异常", false);
//                    }
//                });
//    }

    @Override
    protected int layoutID() {
        return R.layout.activity_identification;
    }

    @Override
    protected void initData() {
        ActivityManager.getInstance().addActivity(this);
        mchid = (String) CommonSharedPreferences.get("mchid", "");

        count = 0;
        k = getIntent().getStringExtra("k");
        if (k.equals("1")) {
            linear_sp4.setVisibility(View.GONE);
            linear_sp2.setVisibility(View.GONE);
            if (Constants.USE_IDCARD) {
                sp_content4.setText("身份证验证");
            } else {
                sp_content4.setText("E证通");
            }
            sp_img4.setVisibility(View.VISIBLE);
            sp_tv4.setBackgroundResource(R.drawable.oval_shape);
            sp_tv4.setTextColor(getResources().getColor(R.color.Swrite));
            gBean = (GuestRoom.Bean) getIntent().getSerializableExtra("bean");
            locksign = getIntent().getStringExtra("locksign");
        } else if (k.equals("2")) {
            linear_sp1.setVisibility(View.GONE);
            linear_sp4.setVisibility(View.GONE);
            sp2_img3.setVisibility(View.VISIBLE);
            sp2_tv3.setTextColor(getResources().getColor(R.color.Swrite));
            sp2_tv3.setBackgroundResource(R.drawable.oval_shape);
            if (Constants.USE_IDCARD) {
                sp2_content3.setText("身份证验证");
            } else {
                sp2_content3.setText("E证通");
            }
            querytype = getIntent().getStringExtra("querytype");
        } else if (k.equals("4")) {
            querytype = getIntent().getStringExtra("querytype");
            StringUtils.setCorlor(sp4_tv3, sp4_img3, sp4_content3, querytype);
            linear_sp1.setVisibility(View.GONE);
            linear_sp2.setVisibility(View.GONE);
            qBean = (QueryBookOrder.Bean) getIntent().getSerializableExtra("bean");
//            if (querytype.equals("")) {
//
//                linear_sp1.setVisibility(View.GONE);
//                linear_sp2.setVisibility(View.GONE);
////                sp4_img5.setVisibility(View.VISIBLE);
////                sp4_tv5.setTextColor(getResources().getColor(R.color.Swrite));
////                sp4_tv5.setBackgroundResource(R.drawable.oval_shape);
//
//            }else {
//                linear_sp1.setVisibility(View.GONE);
//                linear_sp2.setVisibility(View.GONE);
//                sp4_img3.setVisibility(View.VISIBLE);
//                sp4_tv3.setTextColor(getResources().getColor(R.color.Swrite));
//                sp4_tv3.setBackgroundResource(R.drawable.oval_shape);
//                sp4_content3.setText("姓名");
//            }
        }

        Card_Sender my_Card_Sender = new Card_Sender();
        int[] nStatus = new int[1];
        boolean zt = my_Card_Sender.TY_GetStatus(nStatus);
        Log.e("发卡机状态:", "发卡机状态:" + zt);

        handler.post(task);
    }

    @OnClick({R.id.toolbarBack})
    void OnClick() {
        isIntent = false;
        // handler.removeCallbacks(task);
        HotelHttp.cancalLockRoom(k, gBean, mchid);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
//    /**
//     * 查询客房账单
//     */
//    private void queryRoomBill(final String querytype, final String querydata){
//        OkGo.<QueryRoomBill>post(HttpUrl.QUERYROOMBILL)
//                .tag(this)
//                .params("mchid",mchid)
//                .params("querytype",querytype)
//                .params("querydata",querydata)
//                .execute(new JsonCallBack<QueryRoomBill>(QueryRoomBill.class) {
//                    @Override
//                    public void onSuccess(Response<QueryRoomBill> response) {
//                        super.onSuccess(response);
//                        Log.d(TAG, "onSuccess() called with: response = [" + response.body().getDatalist().toString() + "]");
//                        if (response.body().getRescode().equals("0000")){
//                            String ss =response.body().getDatalist().get(0).getAccountprice();
//                         //判断是否有消费
//                                if(ss.contains("-")){
//                                    ss=ss.replace("-","");
//                                }else{
//                                    ss="-"+ss;
//                                }
//                                if (Double.valueOf(ss)>price){
//                                Intent intent =new Intent(IdentificationActivity.this,PaymentActivity.class);
//                                intent.putExtra("price",response.body().getDatalist().get(0).getAddprice());
//                                intent.putExtra("k","3");
//                                intent.putExtra("querytype",querytype);
//                                intent.putExtra("inorderpmsno",response.body().getDatalist().get(0).getInorderpmsno());
//                                intent.putExtra("list", (Serializable) response.body().getDatalist().get(0).getBills());
//                                startActivity(intent);
//                                finish();
//                            }else { //无消费
//                                checkOutRoom(response.body().getDatalist().get(0).getInorderpmsno(),"12",ss);
//
//                            }
//
//                        }else {
//                            Tip.show(getApplicationContext(),response.body().getResult(),false);
//                        }
//                    }
//                });
//    }
//
//    /**
//     * 无消费，直接退房
//     */
//    private void checkOutRoom(String inorderpmsno,String payway,String ss){
//        StringBuffer sb=new StringBuffer();
//        sb.append("0").append("#").append(payway)
//                .append("#").append(ss)
//                .append("##").append(DataTime.currentTime())
//                .append("####").append("0");
//
//        OkGo.<Draw>post(HttpUrl.CHECKOUTROOM)
//                .tag(this)
//                .params("mchid",mchid)
//                .params("inorderpmsno",inorderpmsno)
//                .params("dutypmsno","1")
//                .params("arid","")
//                .params("userno","")
//                .params("payinfo", String.valueOf(sb))
//                .params("payway",payway)
//                .params("devno","")
//                .execute(new JsonCallBack<Draw>(Draw.class) {
//                    @Override
//                    public void onSuccess(Response<Draw> response) {
//                        super.onSuccess(response);
//                       // Log.d(TAG, "onSuccess() called with: response = [" + response.body().getDatalist().toString() + "]");
//                        if (response.body().getRescode().equals("0000")){
//                            Intent intent =new Intent(IdentificationActivity.this,PaySussecsActivity.class);
//                            intent.putExtra("k","3");
//                            startActivity(intent);
//                            finish();
//                        }else {
//                            Tip.show(getApplicationContext(),response.body().getResult(),false);
//                        }
//                    }
//                });
//    }
//


    private void readCard_No() {
        while (true) {
            try {
                Log.e(TAG, "readCard_No: " + "2");
                ReadIDThread rt = new ReadIDThread();
                bs = rt.ReadCardUID();
                break;
            } catch (Exception ignored) {

            }
        }


        //  System.out.println(11111);
        key = true;

        IDCardReaderCallBack rc = new IDCardReaderCallBack() {
            @Override
            public void onReadIdComplete(int iMode, IDCardInfo idCardInfo) {
                // skip(idCardInfo);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = idCardInfo;
                Log.e(TAG, "readCard_No: " + "4");
                handler.sendMessage(msg);
                handler.removeCallbacks(task);
            }
        };
        IDCarderReader iR = new IDCarderReader();
        iR.startReaderIDCard(rc, 1);
    }

//    /**
//     * 页面跳转
//     */
//    public void skip(IDCardInfo idCardInfo){
//        if (idCardInfo != null) {
//            //  Log.e(TAG, "handleMessage: "+idCardInfo.toString() );
//            //  piv_tv.setText("读取成功!");
//            // handler.removeCallbacks(task);
//
//            String name = idCardInfo.getStrName();
//            String id_cardNo = idCardInfo.getStrIdCode();
//            String birth = idCardInfo.getStrBirth();
//            if (k.equals("1")){
//                Intent intent = new Intent(IdentificationActivity.this,FaceRecognitionActivity.class);
//                intent.putExtra("name", name);
//                intent.putExtra("id_CardNo", id_cardNo);
//                intent.putExtra("birth", birth);
//                intent.putExtra("bean",gBean);
//                intent.putExtra("locksign",locksign);
//                intent.putExtra("k",k);
//                startActivity(intent);
//                finish();
//            }else if (k.equals("2")){
//                Intent intent = new Intent(IdentificationActivity.this,RenwalActivity.class);
//                intent.putExtra("name", name);
//                intent.putExtra("id_CardNo", id_cardNo);
//                intent.putExtra("birth", birth);
//                intent.putExtra("k",k);
//                intent.putExtra("querytype",querytype);
//                startActivity(intent);
//                finish();
//            }else if (k.equals("4")){
//                Intent intent = new Intent(IdentificationActivity.this,FaceRecognitionActivity.class);
//                intent.putExtra("name", name);
//                intent.putExtra("id_CardNo", id_cardNo);
//                intent.putExtra("birth", birth);
//                intent.putExtra("k",k);
//                intent.putExtra("bean",qBean);
//                startActivity(intent);
//                finish();
//            }
//        } else {
//            //piv_tv.setText("读取失败！");
//        }
//    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        piv_tv.setText("请将身份证放置在下方感应区");
//        handler.post(task);
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//    }


    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(task);
        Log.e(TAG, "onDestroy: " + "7");
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(task);
        Log.e(TAG, "onDestroy: " + "6");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(task);
        Log.e(TAG, "onDestroy: " + "3");

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            String string = data.getStringExtra("path");

           /* if(Constants.isTest){
                if (idCardInfo == null || string == null) {
                    Tip.show(this, "认证失败,请重试", false);
                    return;
                }
                Intent intent = new Intent(IdentificationActivity.this, PhoneMsg.class);
                intent.putExtra("idCard", idCardInfo);
                intent.putExtra("path",string);
                intent.putExtra("bean", gBean);
                intent.putExtra("locksign",locksign);
                intent.putExtra("k",k);
                startActivity(intent);
                finish();
            }else {*/
            if (idCardInfo == null || string == null) {
                Tip.show(this, "认证失败,请重试", false);
                return;
            }
//            get(idCardInfo, string);
            //}
        }
    }
}
