package com.sun.hotelproject.moudle;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;


import com.sun.hotelproject.R;
import com.sun.hotelproject.base.BaseActivity;

import com.sun.hotelproject.dao.DaoSimple;
import com.sun.hotelproject.entity.BuildingTable;

import com.sun.hotelproject.entity.FloorTable;
import com.sun.hotelproject.entity.HouseTable;
import com.sun.hotelproject.entity.Login;
import com.sun.hotelproject.entity.MacKey;
import com.sun.hotelproject.entity.RoomTable;
import com.sun.hotelproject.http.CallServer;
import com.sun.hotelproject.http.HttpListener;
import com.sun.hotelproject.http.JavaBeanRequest;
import com.sun.hotelproject.http.JsonRequest;
import com.sun.hotelproject.rx.RetryWithDelay;
import com.sun.hotelproject.utils.ActivityManager;
import com.sun.hotelproject.utils.Animutils;
import com.sun.hotelproject.utils.CommonSharedPreferences;
import com.sun.hotelproject.utils.DataTime;
import com.sun.hotelproject.utils.HttpUrl;
import com.sun.hotelproject.utils.JsonCallBack;
import com.sun.hotelproject.utils.Router;
import com.sun.hotelproject.utils.Tip;
import com.sun.hotelproject.utils.Utils;

import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SyncRequestExecutor;


import java.util.HashMap;

import java.util.Map;


import butterknife.BindView;

import butterknife.OnTouch;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import rx.functions.Func4;
import rx.functions.Func5;
import rx.schedulers.Schedulers;


public class LoginActivity extends BaseActivity  {
    @BindView(R.id.linear) RelativeLayout linear;
    @BindView(R.id.login) Button login;
    @BindView(R.id.mchid) EditText mchid_et;
    @BindView(R.id.user) EditText user_et;
    @BindView(R.id.password) EditText password_et;
    @BindView(R.id.tishi) TextView tishi;
    @BindView(R.id.line) View line;
    @BindView(R.id.exit) Button exit;
    @BindView(R.id.anim_layout) RelativeLayout anim_lauout;
    @BindView(R.id.anim_img)ImageView anim_img;
    @BindView(R.id.anim_tv)TextView anim_tv;
    //@BindView(R.id.anim_iv)ImageView iv;
    @BindView(R.id.activity_login)RelativeLayout activity_login;
    private String mchid;
    private String user;
    private String password;
    Animation animation1, animation2;
    private AnimationDrawable animationDrawable;
    private String data = "";
    private static final String TAG = "LoginActivity";
    private DaoSimple daoSimple;
    TranslateAnimation translateAnimation;
    Animation operatingAnim;
    float ivX,ivY;
    boolean isText ;
    private Subscription subscribe;
    private String state = "";
//    TelephonyManager telephonyManager = (TelephonyManager)this.getSystemService( Context.TELEPHONY_SERVICE);

    @Override
    protected int layoutID() {
        return R.layout.activity_login;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initView() {
        super.initView();
        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.load_animation);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        daoSimple =new DaoSimple(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); //不自动弹出键盘
        ActivityManager.getInstance().addActivity(this);

        //startService(new Intent(this, QrCodeService.class));
        data = (String) CommonSharedPreferences.get("data","");
        if (!TextUtils.isEmpty(data)){
            login.setText(data);
            password_et.setText("");
        }

        if ((TextUtils.isEmpty(mchid_et.getText()) || TextUtils.isEmpty(user_et.getText()))|| TextUtils.isEmpty(password_et.getText())){
            login.getBackground().setAlpha(100);
        }else {
            login.getBackground().setAlpha(255);
        }

        isRuning = false;
        animation1 = AnimationUtils.loadAnimation(this, R.anim.in_from_left);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.out_to_left);
        linear.startAnimation(animation1);
        linear.setVisibility(View.VISIBLE);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linear.setX(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linear.setX(-762);
                Router.jumpL("/hotel/main");
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mchid_et.addTextChangedListener(new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(mchid_et.getText()) || TextUtils.isEmpty(user_et.getText()) || TextUtils.isEmpty(password_et.getText())){
                    login.setEnabled(false);
                    login.getBackground().setAlpha(100);
                }else {
                    login.setEnabled(true);
                    login.getBackground().setAlpha(255);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,int after) {

            }

            public void afterTextChanged(Editable s) {
//
            }
        });
        user_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(mchid_et.getText())|| TextUtils.isEmpty(user_et.getText()) || TextUtils.isEmpty(password_et.getText())){
                    login.setEnabled(false);
                    login.getBackground().setAlpha(100);
                }else {
                    login.setEnabled(true);
                    login.getBackground().setAlpha(255);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e("TAG", "beforeTextChanged: "+"123" );
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(mchid_et.getText())|| TextUtils.isEmpty(user_et.getText()) || TextUtils.isEmpty(password_et.getText())){
                    login.setEnabled(false);
                    login.getBackground().setAlpha(100);
                }else {
                    login.setEnabled(true);
                    login.getBackground().setAlpha(255);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    @OnTouch({R.id.login,R.id.exit})
    boolean onTouch(View v, MotionEvent event){
        switch (v.getId()){
            case R.id.login:
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    login.getBackground().setAlpha(128);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    login.getBackground().setAlpha(255);
                    mchid = mchid_et.getText().toString().trim();
                    user = user_et.getText().toString().trim();
                    password = password_et.getText().toString().trim();
                    if (Utils.isFastClick()) {
                        login(mchid, user, password, login.getText().toString().trim());
                    }
                }
                break;
            case R.id.exit:
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    if (Utils.isFastClick()){
                        CommonSharedPreferences.put("data","");
                        login.setText("登录");
                        ActivityManager.getInstance().exit();
                    }
                }
                break;
        }
        return false;
    }
    Map<String,Object> getmap(){
        Map<String,Object> map = new HashMap<>();
        map.put("mchid",mchid);
        return  map;
    }
    void start(){
        Observable observable1 = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                JavaBeanRequest javaBeanRequest = new JavaBeanRequest(HttpUrl.QUERYBUILDING,BuildingTable.class);
                javaBeanRequest.set(getmap());
                Response<BuildingTable> response = SyncRequestExecutor.INSTANCE.execute(javaBeanRequest);
                if (response.isSucceed()){
                    if (TextUtils.equals(response.get().getRescode(),"0000")){
                        for (BuildingTable.Bean bean : response.get().getDatalist()) {
                                bean.setFlag("0");
                                daoSimple.buildAdd(bean);
                        }
                        Log.e(TAG, "call: 314行"+daoSimple.buildSelAll() );
                    }else {
                        Tip.show(getApplicationContext(),response.get().getResult(),false);
                    }
                    subscriber.onNext(true);
                }else subscriber.onError(response.getException());
            }
        });

        Observable observable2 = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                JavaBeanRequest javaBeanRequest = new JavaBeanRequest(HttpUrl.QUERYFLOOR,FloorTable.class);
                javaBeanRequest.set(getmap());
                Response<FloorTable> response = SyncRequestExecutor.INSTANCE.execute(javaBeanRequest);
                if (response.isSucceed()){
                    Log.e(TAG, "call: "+response.get().toString());
                    if (TextUtils.equals(response.get().getRescode(),"0000")){
                        for (FloorTable.Bean bean : response.get().getDatalist()) {
                            bean.setFlag("0");
                            daoSimple.floorAdd(bean);
                        }
                        Log.e(TAG, "call: 336行"+daoSimple.floorSelAll() );
                    }else {
                        Tip.show(getApplicationContext(),response.get().getResult(),false);
                    }
                    subscriber.onNext(true);
                }else subscriber.onError(response.getException());
            }
        });

        Observable observable3 = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                JavaBeanRequest javaBeanRequest = new JavaBeanRequest(HttpUrl.QUERYROOMTYPE,HouseTable.class);
                javaBeanRequest.set(getmap());
                Response<HouseTable> response = SyncRequestExecutor.INSTANCE.execute(javaBeanRequest);
                if (response.isSucceed()){
                    if (TextUtils.equals(response.get().getRescode(),"0000")){
                        for (HouseTable.Bean bean : response.get().getDatalist()) {
                            bean.setFlag("0");
                            daoSimple.houseAdd(bean);
                        }
                        Log.e(TAG, "call: 357行"+daoSimple.houseSelAll() );
                    }else {
                        Tip.show(getApplicationContext(),response.get().getResult(),false);
                    }
                    subscriber.onNext(true);
                }else subscriber.onError(response.getException());
            }
        });

        Observable observable4 = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                JavaBeanRequest javaBeanRequest = new JavaBeanRequest(HttpUrl.QUERYROOMINFO,RoomTable.class);
                javaBeanRequest.set(getmap());
                Response<RoomTable> response = SyncRequestExecutor.INSTANCE.execute(javaBeanRequest);
                if (response.isSucceed()){
                    if (TextUtils.equals(response.get().getRescode(),"0000")){
                        for (RoomTable.Bean bean : response.get().getDatalist()) {
                            bean.setFlag("0");
                            daoSimple.roomAdd(bean);
                        }
                        Log.e(TAG, "call: 386行"+daoSimple.roomSelAll() );
                        daoSimple.delete("1");
                    }else {
                        Tip.show(getApplicationContext(),response.get().getResult(),false);
                    }
                    subscriber.onNext(true);
                }else subscriber.onError(response.getException());
            }
        });
         Map<String,String> map = new HashMap<>();
           // map.put("startdate", DataTime.curenData());
            map.put("startdate", "");
        Observable observable5 = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                JavaBeanRequest javaBeanRequest = new JavaBeanRequest(HttpUrl.FINDKEY,MacKey.class);
                Map<String,String> map = new HashMap<>();
                 map.put("startdate", DataTime.curenData());
               // map.put("startdate", "2018-07-16");
                javaBeanRequest.set(map);
                Response<MacKey> response = SyncRequestExecutor.INSTANCE.execute(javaBeanRequest);
                if (response.isSucceed()){
                    if (TextUtils.equals(response.get().getRescode(),"0000")){
                        for (MacKey.Bean bean : response.get().getList()) {
                            bean.setFlag("0");
                            daoSimple.macAdd(bean);
                        }
                        Log.e(TAG, "call: 391行"+daoSimple.macSelAll() );
                        daoSimple.delete("1");
                    }else {
                        Tip.show(getApplicationContext(),response.get().getResult(),false);
                    }
                    subscriber.onNext(true);
                }else subscriber.onError(response.getException());
            }
        });


        /**
         * 身份证 不走E证通
         */
        subscribe = Observable.zip(observable1, observable2, observable3, observable4, new Func4<Boolean,Boolean,Boolean,Boolean,Boolean>() {
            @Override
            public Boolean call(Boolean aBoolean, Boolean aBoolean2, Boolean aBoolean3, Boolean aBoolean4) {
                return aBoolean && aBoolean2 && aBoolean3 && aBoolean4;
            }
        })
     //   subscribe = Observable.zip(observable1, observable2, observable3, observable4, observable5, new Func5<Boolean,Boolean,Boolean,Boolean,Boolean,Boolean>() {
      //      @Override
      //      public Boolean call(Boolean aBoolean, Boolean aBoolean2, Boolean aBoolean3, Boolean aBoolean4, Boolean aBoolean5) {
      //          return aBoolean && aBoolean2 && aBoolean3 && aBoolean4&& aBoolean5;
      //      }
      //  })
                .retryWhen(new RetryWithDelay(3, 3000))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean b) {
                        if (b) {
                            anim_tv.setText("登陆成功");
                            anim_img.clearAnimation();
                            anim_lauout.setVisibility(View.GONE);
                            linear.startAnimation(animation2);
                            Log.e(TAG, "call: "+"初始化成功" );
                        } else {
                            Log.e(TAG, "call: "+"部分初始化失败" );

                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        anim_img.clearAnimation();
                        anim_lauout.setVisibility(View.GONE);
                        Log.e(TAG, "call: "+throwable.toString() );
                        Log.e(TAG, "call: "+"网络或服务器异常" );
                        subscribe.unsubscribe();
                    }
                });

//        subscribe = Observable.zip(observable1, observable2, observable3, observable4,observable5,new Func5<Boolean,Boolean,Boolean,Boolean,Boolean,Boolean>() {
//            @Override
//            public Boolean call(Boolean aBoolean, Boolean aBoolean2, Boolean aBoolean3, Boolean aBoolean4) {
//                return aBoolean && aBoolean2 && aBoolean3 && aBoolean4;
//            }
//        }).retryWhen(new RetryWithDelay(3, 3000))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Boolean>() {
//                    @Override
//                    public void call(Boolean b) {
//                        if (b) {
//                            anim_tv.setText("登陆成功");
//                            anim_img.clearAnimation();
//                            anim_lauout.setVisibility(View.GONE);
//                            linear.startAnimation(animation2);
//                            Log.e(TAG, "call: "+"初始化成功" );
//                        } else {
//                            Log.e(TAG, "call: "+"部分初始化失败" );
//
//                        }
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        anim_img.clearAnimation();
//                        anim_lauout.setVisibility(View.GONE);
//                        Log.e(TAG, "call: "+throwable.toString() );
//                        Log.e(TAG, "call: "+"网络或服务器异常" );
//                        subscribe.unsubscribe();
//                    }
//                });
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void login(final String mchid, String user, String password, final String s) {
        anim_lauout.setVisibility(View.VISIBLE);
        anim_img.setAnimation(operatingAnim);
        anim_img.startAnimation(operatingAnim);
        anim_tv.setText("正在加载中......");

        Map<String,Object> map = new HashMap<>();
            map.put("mchid", mchid);
            map.put("username", user);
            map.put("password", password);
        JavaBeanRequest javaBeanRequest = new JavaBeanRequest(HttpUrl.LOGIN,Login.class);
        javaBeanRequest.set(map);
        CallServer.getHttpclient().add(0, javaBeanRequest, new HttpListener<Login>() {
            @Override
            public void success(int what, Response<Login> response) {
                if (response.isSucceed()) {
                    if (TextUtils.equals(response.get().getRescode(), "0000")) {
                        CommonSharedPreferences.put("mchid", response.get().getMchid());
                        Log.e(TAG, "success: 450行"+login.toString());
                  //      if (s.equals("登录")){
                            // new MyAsyncTask().execute();
                            if (daoSimple.buildSelAll() != null && daoSimple.floorSelAll() != null
                                    && daoSimple.houseSelAll() != null && daoSimple.roomSelAll() != null){
                                daoSimple.buildUpd("1","0");
                                daoSimple.floorUpd("1","0");
                                daoSimple.houseUpd("1","0");
                                daoSimple.roomUpd("1","0");
                            }
                            start();
                  //      }else {
                   //         anim_lauout.setVisibility(View.GONE);
                   //         anim_img.clearAnimation();
                   //         exit.setVisibility(View.VISIBLE);
                    //        line.setVisibility(View.VISIBLE);
                   //     }
                    } else {
                        anim_lauout.setVisibility(View.GONE);
                        anim_img.clearAnimation();
                        Tip.show(getApplicationContext(), response.get().getResult(), false);
                    }
                }
            }
            @Override
            public void fail(int what, String e) {
                Tip.show(getApplicationContext(),e,false);
                anim_img.clearAnimation();
                anim_lauout.setVisibility(View.GONE);
            }});
    }

    @Override
    protected void onDestroy() {
        CommonSharedPreferences.put("data","");
        login.setText("登录");
        anim_lauout.setVisibility(View.GONE);
        anim_img.clearAnimation();
        super.onDestroy();
    }
}
