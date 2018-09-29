package com.sun.hotelproject.moudle;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
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

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.squareup.picasso.Picasso;
import com.sun.hotelproject.R;
import com.sun.hotelproject.app.App;
import com.sun.hotelproject.app.BackCall;
import com.sun.hotelproject.base.BaseActivity;
import com.sun.hotelproject.entity.Affirmstay;
import com.sun.hotelproject.entity.Draw;
import com.sun.hotelproject.entity.GuestRoom;
import com.sun.hotelproject.entity.LayoutHouse;
import com.sun.hotelproject.entity.QueryBookOrder;
import com.sun.hotelproject.entity.QueryCheckin;
import com.sun.hotelproject.moudle.id_card.IDCardInfo;
import com.sun.hotelproject.moudle.net.HotelHttp;
import com.sun.hotelproject.moudle.net.PoliceInfoSend;
import com.sun.hotelproject.utils.ActivityManager;
import com.sun.hotelproject.utils.CommonSharedPreferences;

import com.sun.hotelproject.utils.DataTime;
import com.sun.hotelproject.utils.HttpUrl;
import com.sun.hotelproject.utils.JsonCallBack;
import com.sun.hotelproject.utils.StringUtils;
import com.sun.hotelproject.utils.Tip;
import com.sun.hotelproject.utils.Utils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author sun
 * 时间：2017/11/27
 * TODO:支付界面
 */
@Route(path = "/hotel/payment")
public class PaymentActivity extends BaseActivity {
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.check_inTime)
    TextView check_inTime;
    @BindView(R.id.tv20)
    TextView tv20;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.toolbarBack)
    Button toolbarBack;

    @BindView(R.id.linear_1)
    LinearLayout linearLayout;
    @BindView(R.id.relative)
    RelativeLayout relativeLayout;
    @BindView(R.id.wechat_pay)
    LinearLayout wechat_pay;
    @BindView(R.id.zhifub_pay)
    LinearLayout zhifub_pay;
    @BindView(R.id.house_type)
    TextView house_type;
    @BindView(R.id.parson_name)
    TextView parson_name;
    @BindView(R.id.relative_3)
    RelativeLayout relative_3;
    @BindView(R.id.result)
    TextView result;
    @BindView(R.id.confirm)
    Button confirm;
    @BindView(R.id.retry)
    Button retry;
    @BindView(R.id.anim_layout)
    RelativeLayout anim_lauout;
    @BindView(R.id.anim_img)
    ImageView anim_img;
    @BindView(R.id.anim_tv)
    TextView anim_tv;
    @BindView(R.id.sp_tv7)
    TextView sp_tv7;
    @BindView(R.id.sp2_tv6)
    TextView sp2_tv6;
    @BindView(R.id.linear_sp1)
    LinearLayout linear_sp1;
    @BindView(R.id.linear_sp2)
    LinearLayout linear_sp2;
    @BindView(R.id.linear_sp3)
    LinearLayout linear_sp3;
    @BindView(R.id.linear_sp4)
    LinearLayout linear_sp4;
    @BindView(R.id.sp4_tv7)
    TextView sp4_tv7;
    @BindView(R.id.sp4_img7)
    ImageView sp4_img7;
    @BindView(R.id.sp3_tv3)
    TextView sp3_tv3;
    @BindView(R.id.sp_img7)
    ImageView sp_img7;
    @BindView(R.id.sp2_img6)
    ImageView sp2_img6;
    @BindView(R.id.sp3_img3)
    ImageView sp3_img3;
    @BindView(R.id.sp4_content3)
    TextView sp4_content3;
    @BindView(R.id.relative1)
    RelativeLayout relative1;
    private AnimationDrawable animationDrawable;
    private String url;
    private String paytype;
    private LayoutHouse house;
    private String beginTime;
    private String endTime;
    private String stratTime;
    private String finsihTime;
    private String content;
    private String house_name;
    private String orderId;
    private String id_CardNo;
    private String birth;
    private String path;
    private String locksign;
    private GuestRoom.Bean gBean;
    private QueryCheckin.Bean qBean;
    private QueryBookOrder.Bean qb;
    private String k;
    private String payMoney;
    private int inDay;
    private List<Map<String, String>> list;
    private static final String TAG = "PaymentActivity";
    private String inorderpmsno;
    private String querytype;
    private String name;
    private String mchid;
    private Affirmstay.Bean ab;
    Animation operatingAnim;
    private String payway = "141";
    private Double payPrice, housePrice;
    private String addPirce;
    private String phoneNum = "";
    IDCardInfo idCardInfo;//身份证认证得到的身份证信息

    @Override
    protected int layoutID() {
        return R.layout.payment2;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        super.initView();
        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.load_animation);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        ActivityManager.getInstance().addActivity(this);
        linearLayout.setVisibility(View.GONE);
        relative_3.setVisibility(View.GONE);
        mchid = (String) CommonSharedPreferences.get("mchid", "");

        stratTime = (String) CommonSharedPreferences.get("beginTime1", "");
        finsihTime = (String) CommonSharedPreferences.get("endTime1", "");
        beginTime = (String) CommonSharedPreferences.get("beginTime", "");
        endTime = (String) CommonSharedPreferences.get("endTime", "");
        content = (String) CommonSharedPreferences.get("content", "");
        String day = (String) CommonSharedPreferences.get("inDay", "");
        Log.e(TAG, "initView: " + stratTime + " " + finsihTime + " " + beginTime + " " + endTime);
        inDay = Integer.parseInt(day);
        // Log.e(TAG, "initView: "+beginTime +endTime );
        //check_inTime.setText(stratTime + "——" + finsihTime + "   " + content);
        k = getIntent().getStringExtra("k");//k=1入住，2续住，3退房
        switch (k) {
            case "1":
                StringUtils.setCorlor(sp_tv7, sp_img7);
                //   shibie.setText("正在识别中，请稍后......");
                //  shibie.setTextColor(Color.WHITE);
                house_name = (String) CommonSharedPreferences.get("house_type", "");
                //  path = getIntent().getStringExtra("path");
                name = getIntent().getStringExtra("name");
                //  birth = getIntent().getStringExtra("birth");
                gBean = (GuestRoom.Bean) getIntent().getSerializableExtra("bean");
                id_CardNo = getIntent().getStringExtra("card_id");
                locksign = getIntent().getStringExtra("locksign");
                phoneNum = getIntent().getStringExtra("phoneNum");
                idCardInfo = (IDCardInfo) getIntent().getParcelableExtra("idCard");
                // check_inTime.setText(stratTime + "——" + finsihTime + "   " + content);
                try {
                    tv20.setText(gBean.getRpmsno());
                }catch (Exception e){}
                house_type.setText(house_name);
                parson_name.setText(name);
                Log.e(TAG, "initView: " + name + id_CardNo + phoneNum);
                price.setText(DataTime.updTextSize2(getApplicationContext(), "￥" + Double.valueOf(gBean.getDealprice()) * inDay, 1), TextView.BufferType.SPANNABLE);
                // Scanpay1(payway,locksign,phoneNum);
                Scanpay1(payway, locksign, phoneNum);
                check_inTime.setText(stratTime + "——" + finsihTime + "   " + content);
                // Scanpay("12");
                break;
            case "999":
                StringUtils.setCorlor(sp_tv7, sp_img7);
                //   shibie.setText("正在识别中，请稍后......");
                //  shibie.setTextColor(Color.WHITE);
                house_name = (String) CommonSharedPreferences.get("house_type", "");
                //  path = getIntent().getStringExtra("path");
                name = getIntent().getStringExtra("name");
                //  birth = getIntent().getStringExtra("birth");
                gBean = (GuestRoom.Bean) getIntent().getSerializableExtra("bean");
                id_CardNo = getIntent().getStringExtra("card_id");
                locksign = getIntent().getStringExtra("locksign");
                phoneNum = getIntent().getStringExtra("phoneNum");
                idCardInfo = (IDCardInfo) getIntent().getParcelableExtra("idCard");
                // check_inTime.setText(stratTime + "——" + finsihTime + "   " + content);
                try {
                    tv20.setText(gBean.getRpmsno());
                }catch (Exception e){}
                house_type.setText(house_name);
                parson_name.setText(name);
                Log.e(TAG, "initView: " + name + id_CardNo + phoneNum);
                price.setText(DataTime.updTextSize2(getApplicationContext(), "￥" + Double.valueOf(gBean.getDealprice()) * inDay, 1), TextView.BufferType.SPANNABLE);
                // Scanpay1(payway,locksign,phoneNum);
                Scanpay1(payway, locksign, phoneNum);
                check_inTime.setText(stratTime + "——" + finsihTime + "   " + content);
                // Scanpay("12");
                break;
            case "2":
//                linear_sp2.setVisibility(View.VISIBLE);
//                sp2_img6.setVisibility(View.VISIBLE);
//                sp2_tv6.setBackgroundResource(R.drawable.oval_shape);
//                sp2_tv6.setTextColor(getResources().getColor(R.color.Swrite));
//                //shibie.setText("正在识别中，请稍后......");
//                //shibie.setTextColor(Color.WHITE);
//                qBean = (QueryCheckin.Bean) getIntent().getSerializableExtra("bean");
//                path = getIntent().getStringExtra("path");
//                name = qBean.getGuestname();
//                id_CardNo = qBean.getDocno();
//                querytype = getIntent().getStringExtra("querytype");
//                ab = (Affirmstay.Bean) getIntent().getSerializableExtra("bean2");
//                Log.e(TAG, "initView: " + ab.toString());
//                birth = qBean.getBirth();
//                parson_name.setText(name);
//                tv20.setText(qBean.getRoomno());
//                price.setText(DataTime.updTextSize2(getApplicationContext(), "￥" + ab.getSureprice(), 1), TextView.BufferType.SPANNABLE);
//                check_inTime.setText(stratTime + "——" + finsihTime + "   " + content);
//
//                //Scanpay(ab.getSureprice(), "12");
                break;
            case "3":
                linear_sp1.setVisibility(View.GONE);
                linear_sp3.setVisibility(View.VISIBLE);
                StringUtils.setCorlor(sp3_tv3, sp3_img3);
                inorderpmsno = getIntent().getStringExtra("inorderpmsno");
                payMoney = getIntent().getStringExtra("price");
                name = getIntent().getStringExtra("name");
                addPirce = getIntent().getStringExtra("addprice");
                //  Log.e(TAG, "initView: " + payMoney);
                list = (List<Map<String, String>>) getIntent().getSerializableExtra("list");
                //  Log.e(TAG, "initView: " + payMoney + "\n" + list.toString());
                price.setText(DataTime.updTextSize2(getApplicationContext(), "￥" + payMoney, 1), TextView.BufferType.SPANNABLE);
                parson_name.setText(name);
                Log.e(TAG, "initView: " + name);
                check_inTime.setText(stratTime + "——" + finsihTime + "   " + content);
                Scanpay4(payMoney, payway, inorderpmsno);
                break;
            case "4":
                house_name = (String) CommonSharedPreferences.get("house_type", "");
                linearLayout.setVisibility(View.GONE);
                linear_sp1.setVisibility(View.GONE);
                linear_sp4.setVisibility(View.VISIBLE);

                //  path = getIntent().getStringExtra("path");
                name = getIntent().getStringExtra("name");
                //  birth = getIntent().getStringExtra("birth");
                qb = (QueryBookOrder.Bean) getIntent().getSerializableExtra("bean");
                id_CardNo = getIntent().getStringExtra("card_id");
                querytype = getIntent().getStringExtra("querytype");
                idCardInfo = (IDCardInfo) getIntent().getParcelableExtra("idCard");
                StringUtils.setCorlor(sp4_tv7, sp4_img7, sp4_content3, querytype);
                tv20.setText(qb.getRoomno());
                house_type.setText(house_name);
                parson_name.setText(name);
                check_inTime.setText(stratTime + "——" + finsihTime + "   " + content);
                payPrice = Double.valueOf(qb.getPayprice());
                housePrice = Double.valueOf(qb.getDealprice());
                if (payPrice < housePrice) {
                    price.setText(DataTime.updTextSize2(getApplicationContext(), "￥" + (housePrice - payPrice), 1), TextView.BufferType.SPANNABLE);
                }
                Log.e(TAG, "initView: querytype--->" + querytype);
            //    if (payPrice < housePrice) {
              //      String price = String.valueOf((housePrice - payPrice));
              //      Scanpay2(payway, "0", price, phoneNum);
             //   } else {
                    Scanpay3(payway, "0", phoneNum);
              //  }
                break;
            default:
                break;
        }
        Log.e(TAG, "initView: " + k);
    }

    @OnClick({R.id.img, R.id.zhifub_pay, R.id.wechat_pay, R.id.retry, R.id.confirm})
    protected void OnClick(View v) {
        super.OnClick(v);
        switch (v.getId()) {
            case R.id.confirm:
                Intent intent = new Intent();
                relative_3.setVisibility(View.GONE);
                startActivity(new Intent(PaymentActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.retry:
                intent = new Intent(PaymentActivity.this, FaceRecognitionActivity.class);
                intent.putExtra("k", "5");intent.putExtra("mchid", mchid);
                startActivityForResult(intent, 1);
                relative_3.setVisibility(View.GONE);
                // finish();
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
//        if (k.equals("1")){
//                                    Scanpay1(payway,locksign,phoneNum);
//                                }else if(k.equals("2")) {
//                                //Scanpay(ab.getSureprice(),payway);
//                                }else if (k.equals("4")){
//                                    if (payPrice<housePrice){
//                                        String price = String.valueOf((housePrice - payPrice));
//                                        Scanpay2(payway,"0",price,phoneNum);
//                                    }else {
//                                        Scanpay3(payway,"0",phoneNum);
//                                    }
    }

//    /**
//     * 生成流水号
//     */
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    public void get(){
//        anim_lauout.setVisibility(View.VISIBLE);
//        // animationDrawable = (AnimationDrawable) getResources().getDrawable(R.anim.load_animation);
//        anim_img.setAnimation(operatingAnim);
//        anim_img.startAnimation(operatingAnim);
//        anim_tv.setText("正在加载中......");
////        if (animationDrawable != null && !animationDrawable.isRunning()){
////            animationDrawable.start();
////        }
//        OkGo.<SeqNo>get(HttpUrl.SEQNO)
//                .tag(this)
//                .execute(new JsonCallBack<SeqNo>(SeqNo.class) {
//                    @Override
//                    public void onSuccess(Response<SeqNo> response) {
//                        super.onSuccess(response);
//                        Log.d(TAG, "onSuccess() called with: response = [" + response.body().toString() + "]");
//                        if (response.body().getRescode().equals("00") && response.body().getRetcode().equals("0")){
//                            Post(response.body().getSeq_no(),response.body().getAccount());
//                        }else {
//                           anim_img.clearAnimation();
//                           anim_lauout.setVisibility(View.GONE);
//                            Tip.show(getApplicationContext(),response.body().getRetmsg(),false);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Response<SeqNo> response) {
//                        anim_img.clearAnimation();
//                        anim_lauout.setVisibility(View.GONE);
//                        Tip.show(getApplicationContext(),"服务器连接异常",false);
//                        super.onError(response);
//                    }
//                });
//    }
//    /**
//     * 解锁
//     */
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    private void lockRoom(final GuestRoom.Bean bean){
//
//        OkGo.<LockRoom>post(HttpUrl.LOCKROOM)
//                .tag(this)
//                .params("mchid", mchid)
//                .params("bstype","2")
//                .params("constraint","1")
//                .params("operation", "1")
//                .params("bpmsno","")
//                .params("rpmsno",bean.getRpmsno())
//                .params("userno","")
//                .params("opmsno","")
//
//                .execute(new JsonCallBack<LockRoom>(LockRoom.class) {
//                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//                    @Override
//                    public void onSuccess(Response<LockRoom> response) {
//                        super.onSuccess(response);
//
//                    //    Log.d(TAG, "onSuccess() called with: response = [" + response.body().toString() + "]");
//                        if (response.body().getRescode().equals("0000")) {
//                            if (response.body().getDatalist().get(0).getLockres().equals("2")){
//                                Tip.show(getApplicationContext(),"解锁失败！",false);
//                            }else {
//                                Tip.show(getApplicationContext(),"解锁成功！",true);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(Response<LockRoom> response) {
//                        super.onError(response);
//                        Tip.show(getApplicationContext(),"服务器连接异常",false);
//                    }
//                });
//    }

    //    /**
//     * 人脸识别
//     * @param seq_no 流水号
//     * @param account 账号
//     */
//    public void Post(String seq_no,String account){
//        OkGo.<FaceRecognition>post(HttpUrl.FACERECOQNITION)
//                .tag(this)
//                .retryCount(3)//超时重连次数
//                .cacheTime(3000)//缓存过期时间
//                .params("name",name.trim())
//                .params("creid_no",id_CardNo.trim())
//                .params("account",account)
//                .params("type",8)
//                .params("seq_no",seq_no)
//                .params("photo_check_live",0) //0防翻拍，1关闭防翻拍
//                .isMultipart(true)//强制使用multipart/form-data 表单上传
//                .params("image_fn", new File(path))
//                .execute(new JsonCallBack<FaceRecognition>(FaceRecognition.class) {
//                    @Override
//                    public void onSuccess(Response<FaceRecognition> response) {
//                        super.onSuccess(response);
//                        Log.d(TAG, "onSuccess() called with: response = [" + response.body() + "]");
//                        if (response.body().getRescode().equals("00") && response.body().getRetcode().equals("0")){
//                           if (response.body().getStatus().equals("1")) {
//                               anim_img.clearAnimation();
//                               relative_3.setVisibility(View.GONE);
//                               play(R.raw.shibei_success,getApplicationContext());
//                               if (k.equals("1")){
//                                    Scanpay1(payway,locksign,phoneNum);
//                                }else if(k.equals("2")) {
//                                //Scanpay(ab.getSureprice(),payway);
//                                }else if (k.equals("4")){
//                                    if (payPrice<housePrice){
//                                        String price = String.valueOf((housePrice - payPrice));
//                                        Scanpay2(payway,"0",price,phoneNum);
//                                    }else {
//                                        Scanpay3(payway,"0",phoneNum);
//                                    }
//                            }
//                           }else {
//                           play(R.raw.shibei_fail,getApplicationContext());
//                             anim_img.clearAnimation();
//                             anim_lauout.setVisibility(View.GONE);
//                             relative_3.setVisibility(View.VISIBLE);
//                           }
//                        }else {
//                            play(R.raw.shibei_fail,getApplicationContext());
//                            relative_3.setVisibility(View.VISIBLE);
//                          anim_img.clearAnimation();
//                        anim_lauout.setVisibility(View.GONE);
//                    }
//                    }
//
//                    @Override
//                    public void onError(Response<FaceRecognition> response) {
//                        anim_img.clearAnimation();
//                        anim_lauout.setVisibility(View.GONE);
//                        Tip.show(getApplicationContext(), "服务器连接异常", false);
//                    }
//                });
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode ==1 && resultCode == Activity.RESULT_OK){
//            path = data.getStringExtra("path");
//            Log.e(TAG, "onActivityResult: "+path );
//            get();
//        }
    }

    /**
     * 退房扫码支付
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void Scanpay4(String price, String payway, String inorderpmsno) {
        anim_lauout.setVisibility(View.VISIBLE);

        // animationDrawable = (AnimationDrawable) getResources().getDrawable(R.anim.load_animation);
        anim_img.setAnimation(operatingAnim);
        anim_img.startAnimation(operatingAnim);
        anim_tv.setText("正在加载中......");
//        if (animationDrawable != null && !animationDrawable.isRunning()){
//            animationDrawable.start();
//        }
        orderId = DataTime.orderId();
        StringBuffer sb = new StringBuffer();
        sb.append("3").append("#").append(payway)
                .append("#").append(price)
                .append("##").append(DataTime.currentTime())
                .append("####").append("0");
        OkGo.<Draw>post(HttpUrl.SCANPAY)
                .tag(this)
                .params("orderid", orderId)
                .params("mchid", mchid)
                .params("type", "03")
                .params("inorderpmsno", inorderpmsno)
                .params("dutypmsno", "1")
                .params("arid", "")
                .params("userno", "")
                .params("payinfo", String.valueOf(sb))
                .params("payway", payway)
                .params("devno", "")
                .params("addprice", addPirce)
                .params("accountrmk", "")
                .params("breaknum", "0002")
                .execute(new JsonCallBack<Draw>(Draw.class) {
                    @Override
                    public void onSuccess(Response<Draw> response) {
                        super.onSuccess(response);
                        if (response.body().getRescode().equals("0000")) {
                            anim_img.clearAnimation();
                            anim_lauout.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                            relative1.setVisibility(View.GONE);
                            Picasso.with(PaymentActivity.this).load(response.body().getCodeimgurl()).into(img);
                            orderId = response.body().getOrderid();
                            handler.postDelayed(task, 5 * 1000);
                        } else {
                            Tip.show(getApplicationContext(), response.body().getResult(), false);
                            anim_img.clearAnimation();
                            anim_lauout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Response<Draw> response) {
                        super.onError(response);
                        anim_img.clearAnimation();
                        anim_lauout.setVisibility(View.GONE);
                        Tip.show(getApplicationContext(), "服务器连接异常", false);
                    }
                });
    }

//    /**
//     * 续住扫码支付
//     */
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    public void Scanpay(String price, String payway) {
//        anim_lauout.setVisibility(View.VISIBLE);
//
//        // animationDrawable = (AnimationDrawable) getResources().getDrawable(R.anim.load_animation);
//        anim_img.setAnimation(operatingAnim);
//        anim_img.startAnimation(operatingAnim);
//        anim_tv.setText("正在加载中......");
////        if (animationDrawable != null && !animationDrawable.isRunning()){
////            animationDrawable.start();
////        }
//        orderId= DataTime.orderId();
//        StringBuffer sb=new StringBuffer();
//        sb.append("3").append("#").append(payway)
//                .append("#").append(price)
//                .append("##").append(DataTime.currentTime())
//                .append("####").append("0");
//        OkGo.<Draw>post(HttpUrl.SCANPAY)
//                .tag(this)
//                .params("orderid", orderId)
//                .params("type","02")
//                .params("inorderpmsno",qBean.getInorderpmsno())
//                .params("mchid",mchid)
//                .params("indate", beginTime)
//                .params("outdate",endTime)
//                .params("devno",DataTime.getSerialNumber())
//                .params("intype",qBean.getIntype())
//                .params("dutypmsno","")
//                .params("couponno","")
//                .params("couponprice","")
//                .params("userno","")
//                .params("payinfo", String.valueOf(sb))
//                .params("payway",payway)
//                .params("dealprice",price)
//                .params("morncode","")
//
//                .execute(new JsonCallBack<Draw>(Draw.class) {
//                    @Override
//                    public void onSuccess(com.lzy.okgo.model.Response<Draw> response) {
//                        super.onSuccess(response);
//                        if (response.body().getRescode().equals("0000")) {
//                            anim_img.clearAnimation();
//                            anim_lauout.setVisibility(View.GONE);
//                            linearLayout.setVisibility(View.VISIBLE);
//                            Picasso.with(PaymentActivity.this).load(response.body().getCodeimgurl()).into(img);
//                            orderId = response.body().getOrderid();
//                            Log.e(TAG, "onSuccess: "+orderId);
//                            handler.postDelayed(task,5*1000);
//                        }else {
//                            anim_img.clearAnimation();
//                            anim_lauout.setVisibility(View.GONE);
//                            Tip.show(getApplicationContext(),response.body().getResult(),false);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Response<Draw> response) {
//                        super.onError(response);
//                        anim_img.clearAnimation();
//                        anim_lauout.setVisibility(View.GONE);
//                        Tip.show(getApplicationContext(),"服务器连接异常",false);
//                    }
//                });
//    }

    /**
     * 入住扫码支付
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void Scanpay1(String payway, String locksign, String phoneNum) {
        anim_lauout.setVisibility(View.VISIBLE);
        anim_img.setAnimation(operatingAnim);
        anim_img.startAnimation(operatingAnim);
        anim_tv.setText("正在加载中......");
        orderId = DataTime.orderId();
        // String arprice = String.valueOf(Double.valueOf(gBean.getDealprice()) * inDay);
        StringBuffer sb = new StringBuffer();

        sb.append(name);
        sb.append("#").append("1");
        sb.append("#").append(id_CardNo);//证件号
        sb.append("#").append(idCardInfo == null ? "" : Utils.checkNation(idCardInfo.getStrNation()));//民族
        sb.append("#").append(idCardInfo.getStrSex() % 2 == 0 ? "女" : "男");//性别
        sb.append("#").append(idCardInfo.getStrAddr());//住址
        StringBuffer stringBuffer = new StringBuffer();
        sb.append("#").append(stringBuffer.append(idCardInfo.getStrBirth()).insert(4, "-").insert(7, "-"));//出生
        sb.append("#");//姓名全拼
        sb.append("#");

        String price = String.valueOf(Double.valueOf(gBean.getDealprice()) * inDay);
        StringBuffer sb1 = new StringBuffer();
        sb1.append("3").append("#").append(payway)
                .append("#").append(price)
                .append("##").append(DataTime.currentTime())
                .append("####").append("0");
        OkGo.<Draw>post(HttpUrl.SCANPAY)
                .tag(this)
                .params("orderid", orderId)
                .params("payway", payway)
                .params("guestel", phoneNum)
                .params("type", "01")
                .params("guestinfo", String.valueOf(sb))
                .params("rpmsno", gBean.getRpmsno())
                .params("rpriceno", gBean.getPmspcode())
                .params("intime", DataTime.currentTime())
                .params("outtime", endTime + " 12:00:00")
                .params("intype", "1")
                .params("team", "1")
                // .params("optime",DataTime.currentTime())
                .params("devno", DataTime.getSerialNumber())
                .params("mchid", mchid)
                .params("rtpmsno", gBean.getRtpmsno())
                .params("opmsno", "")
                .params("indate", beginTime)
                .params("dealprice", gBean.getDealprice())
                .params("oldprice", gBean.getNormnightprice())
                .params("cardnum", "1")
                .params("dutypmsno", "1")
                .params("ordertype", "6")
                .params("rmk", "")
                .params("locksign", locksign)
                .params("arprice", price)
                .params("payinfo", String.valueOf(sb1))
                .params("breaknum", "0002")
                .params("photo", idCardInfo == null ? "" : Utils.bitmapToBase64(idCardInfo.getBitmapIdPhoto()))
                .execute(new JsonCallBack<Draw>(Draw.class) {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<Draw> response) {
                        super.onSuccess(response);
                        if (response.body().getRescode().equals("0000")) {
                            anim_img.clearAnimation();
                            anim_lauout.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                            Picasso.with(PaymentActivity.this).load(response.body().getCodeimgurl()).into(img);
                            orderId = response.body().getOrderid();
                            handler.postDelayed(task, 5 * 1000);
                        } else {
                            anim_img.clearAnimation();
                            anim_lauout.setVisibility(View.GONE);
                            Tip.show(getApplicationContext(), response.body().getResult(), false);
                        }
                    }

                    @Override
                    public void onError(Response<Draw> response) {
                        super.onError(response);
                        anim_img.clearAnimation();
                        anim_lauout.setVisibility(View.GONE);
                        Tip.show(getApplicationContext(), "服务器连接异常", false);

                    }
                });
    }

    /**
     * 预定支付
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void Scanpay2(String payway, String locksign, String price, String phoneNum) {
        anim_lauout.setVisibility(View.VISIBLE);
        anim_img.setAnimation(operatingAnim);
        anim_img.startAnimation(operatingAnim);
        anim_tv.setText("正在加载中......");
        orderId = DataTime.orderId();
        // String arprice = String.valueOf(Double.valueOf(qb.getDealprice()) * inDay);
        StringBuffer sb = new StringBuffer();
        sb.append(name);
        sb.append("#").append("1");
        sb.append("#").append(id_CardNo);//证件号
        sb.append("#").append(idCardInfo == null ? "" : Utils.checkNation(idCardInfo.getStrNation()));//民族
        sb.append("#").append(idCardInfo.getStrSex() % 2 == 0 ? "女" : "男");//性别
        sb.append("#").append(idCardInfo.getStrAddr());//住址
        sb.append("#").append(idCardInfo.getStrBirth());//出生
        sb.append("#");//姓名全拼
        sb.append("#");


        StringBuffer sb1 = new StringBuffer();
        sb1.append("3").append("#").append(payway)
                .append("#").append(price)
                .append("##").append(DataTime.currentTime())
                .append("####").append("0");
        OkGo.<Draw>post(HttpUrl.SCANPAY)
                .tag(this)
                .params("orderid", orderId)
                .params("payway", payway)
                .params("type", "01")
                .params("guestel", "")
                .params("guestinfo", String.valueOf(sb))
                .params("rpmsno", qb.getRoomno())
                .params("rpriceno", qb.getPcode())
                .params("intime", qb.getIndate())
                .params("outtime", qb.getOuttime())
                .params("intype", "1")
                .params("team", "1")
                // .params("optime",DataTime.currentTime())
                .params("devno", DataTime.getSerialNumber())
                .params("mchid", mchid)
                .params("rtpmsno", qb.getRtpmsno())
                .params("opmsno", qb.getOpmsno())
                .params("indate", qb.getIndate())
                .params("dealprice", price)
                .params("oldprice", qb.getOldprice())
                .params("cardnum", "1")
                .params("dutypmsno", "1")
                .params("ordertype", "6")
                .params("rmk", "")
                .params("locksign", locksign)
                .params("arprice", price)
                .params("payinfo", String.valueOf(sb1))
                .params("breaknum", "1000")
                .execute(new JsonCallBack<Draw>(Draw.class) {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<Draw> response) {
                        super.onSuccess(response);
                        if (response.body().getRescode().equals("0000")) {
                            anim_img.clearAnimation();
                            anim_lauout.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                            Picasso.with(PaymentActivity.this).load(response.body().getCodeimgurl()).into(img);
                            orderId = response.body().getOrderid();
                            handler.postDelayed(task, 5 * 1000);
                        } else {
                            anim_img.clearAnimation();
                            anim_lauout.setVisibility(View.GONE);
                            Tip.show(getApplicationContext(), response.body().getResult(), false);
                        }
                    }

                    @Override
                    public void onError(Response<Draw> response) {
                        super.onError(response);
                        anim_img.clearAnimation();
                        anim_lauout.setVisibility(View.GONE);
                        Tip.show(getApplicationContext(), "服务器连接异常", false);

                    }
                });
    }

    @Override
    public void toolBacBcak() {
        super.toolBacBcak();
        HotelHttp.cancalLockRoom(k, gBean, mchid);
    }

    /**
     * 预定支付,fq
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void Scanpay3(String payway, String locksign, String phoneNum) {
        anim_lauout.setVisibility(View.VISIBLE);
        anim_img.setAnimation(operatingAnim);
        anim_img.startAnimation(operatingAnim);
        anim_tv.setText("正在加载中......");
        orderId = DataTime.orderId();
        StringBuffer sb = new StringBuffer();


        sb.append(name);
        sb.append("#").append("1");
        sb.append("#").append(idCardInfo.getStrIdCode());//证件号
        sb.append("#").append(idCardInfo == null ? "" : Utils.checkNation(idCardInfo.getStrNation()));//民族
        sb.append("#").append(idCardInfo.getStrSex() % 2 == 0 ? "女" : "男");//性别
        sb.append("#").append(idCardInfo.getStrAddr());//住址
        StringBuffer stringBuffer = new StringBuffer();
        sb.append("#").append(stringBuffer.append(idCardInfo.getStrBirth()).insert(4, "-").insert(7, "-"));//出生
        sb.append("#");//姓名全拼
        sb.append("#");

        OkGo.<Draw>post(HttpUrl.INROOMNOPAY)
                .tag(this)
                .params("orderid", orderId)
                .params("payway", payway)
                .params("type", "04")
                .params("guestel", "")
                .params("guestinfo", String.valueOf(sb))
                .params("rpmsno", qb.getRoomno())
                .params("rpriceno", qb.getPcode())
                .params("intime", qb.getIndate())
                .params("outtime", qb.getOuttime())
                .params("intype", "1")
                .params("team", "1")
                // .params("optime",DataTime.currentTime())
                .params("devno", DataTime.getSerialNumber())
                .params("mchid", mchid)
                .params("rtpmsno", qb.getRtpmsno())
                .params("opmsno", qb.getOpmsno())
                .params("indate", qb.getIndate())
                .params("dealprice", qb.getDealprice())
                .params("oldprice", qb.getOldprice())
                .params("cardnum", "1")
                .params("dutypmsno", "1")
                .params("ordertype", "6")
                .params("rmk", "")
                .params("locksign", locksign)
                .params("arprice", qb.getDealprice())
                .params("breaknum", "1000")
                .execute(new JsonCallBack<Draw>(Draw.class) {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<Draw> response) {
                        super.onSuccess(response);
                        Log.d(TAG, "onSuccess() called with: response = [" + mResponse.toString() + "]");
                        if (response.body().getRescode().equals("0000")) {
                            policeInfoSend(response.body());
                        } else {
                            anim_img.clearAnimation();
                            anim_lauout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Response<Draw> response) {
                        super.onError(response);
                        anim_img.clearAnimation();
                        anim_lauout.setVisibility(View.GONE);
                        Tip.show(getApplicationContext(), "服务器连接异常", false);

                    }
                });
    }


    Runnable task = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, 5 * 1000);
            queryPayStatus(orderId);

        }
    };

    /**
     * 查询支付结果
     */
    public void queryPayStatus(final String orderId) {
        Log.e(TAG, "queryPayStatus: " + orderId);
        OkGo.<Draw>post(HttpUrl.QUERYPAYSTATUS)
                .tag(this)
                .params("mchid", mchid)
                .params("orderid", orderId)
                .execute(new JsonCallBack<Draw>(Draw.class) {
                    @Override
                    public void onSuccess(final Response<Draw> response) {
                        super.onSuccess(response);
                        if (response.body().getRescode().equals("0000")) {
                            policeInfoSend(response.body());
                        } else {
                            Log.e(TAG, "onSuccess: " + response.body().getResult());
                        }
                    }

                    @Override
                    public void onError(Response<Draw> response) {
                        super.onError(response);
                        Tip.show(getApplicationContext(), "服务器连接异常", false);

                    }
                });
    }


    //接通公安数据
    public void policeInfoSend(final Draw draw){
        PoliceInfoSend.getPoliceserialNumber(new BackCall() {
            @Override
            public void deal(String s) {
                PoliceInfoSend.sendSernumber(draw, s, mchid, orderId, new BackCall() {
                    @Override
                    public void deal(String s) {
                        if(s.equals("")){
                            Intent intent = new Intent(PaymentActivity.this, PaySussecsActivity.class);
                            intent.putExtra("k", k);
                            intent.putExtra("name", name);
                            intent.putExtra("orderId", orderId);
                            intent.putExtra("payway", "微信支付");
                            intent.putExtra("price", price.getText().toString());
                            if (k.equals("2")) {
                                intent.putExtra("querytype", querytype);
                            } else if (k.equals("3")) {
                                intent.putExtra("flag", "1");
                            } else if (k.equals("4")) {
                                anim_img.clearAnimation();
                                anim_lauout.setVisibility(View.GONE);
                                linearLayout.setVisibility(View.VISIBLE);
                                intent.putExtra("payway", "线上支付");
                                intent.putExtra("price", "0.00");
                                intent.putExtra("querytype", querytype);
                            }
                            startActivity(intent);
                            handler.removeCallbacks(task);
                            finish();
                        }else{
                            Tip.show(App.getInstance(),"公安数据接入失败，请联系前台",false);
                        }
                    }

                    @Override
                    public void deal(Object s) {

                    }
                });
            }

            @Override
            public void deal(Object s) {

            }
        });
    }


    @Override
    protected void onPause() {
        handler.removeCallbacks(task);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(task);
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
