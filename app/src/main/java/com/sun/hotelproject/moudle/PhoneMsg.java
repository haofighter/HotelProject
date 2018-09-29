package com.sun.hotelproject.moudle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sun.hotelproject.R;
import com.sun.hotelproject.app.App;
import com.sun.hotelproject.app.BackCall;
import com.sun.hotelproject.base.BaseActivity;
import com.sun.hotelproject.entity.GuestRoom;
import com.sun.hotelproject.entity.QueryBookOrder;
import com.sun.hotelproject.moudle.id_card.IDCardInfo;
import com.sun.hotelproject.moudle.net.CodeInfo;
import com.sun.hotelproject.utils.StringUtils;
import com.sun.hotelproject.utils.Tip;
import com.sun.hotelproject.utils.Utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by a'su's on 2018/7/3.
 * 填写手机号界面
 */

public class PhoneMsg extends BaseActivity {
    @BindView(R.id.phone_num0)
    TextView phone_num0;
    @BindView(R.id.phone_num1)
    TextView phone_num1;
    @BindView(R.id.phone_num2)
    TextView phone_num2;
    @BindView(R.id.phone_num3)
    TextView phone_num3;
    @BindView(R.id.phone_num4)
    TextView phone_num4;
    @BindView(R.id.phone_num5)
    TextView phone_num5;
    @BindView(R.id.phone_num6)
    TextView phone_num6;
    @BindView(R.id.phone_num7)
    TextView phone_num7;
    @BindView(R.id.phone_num8)
    TextView phone_num8;
    @BindView(R.id.phone_num9)
    TextView phone_num9;
    @BindView(R.id.phone_del)
    LinearLayout phone_del;
    @BindView(R.id.phone_et)
    TextView phone_et;
    @BindView(R.id.get_code)
    TextView get_code;
    @BindView(R.id.input_code)
    TextView input_code;
    @BindView(R.id.phone_bt)
    Button phone_bt;
    @BindView(R.id.skip_tv)
    TextView skip_tv;
    private int toolTime = 30;
    @BindView(R.id.toolbarBack)
    Button toolbarBack;
    @BindView(R.id.linear_sp1)
    LinearLayout linear_sp1;
    @BindView(R.id.linear_sp2)
    LinearLayout linear_sp2;
    @BindView(R.id.linear_sp4)
    LinearLayout linear_sp4;
    @BindView(R.id.sp_tv6)
    TextView sp_tv6;
    @BindView(R.id.sp_img6)
    ImageView sp_img6;
    private GuestRoom.Bean gBean; //客房信息
    private String locksign;//锁房标记值
    private String k;
    private String name;
    private String cardID;
    private String path;
    private String birth;
    private QueryBookOrder.Bean qb;
    private static final String TAG = "PhoneMsg";

    @Override
    protected int layoutID() {
        return R.layout.phone;
    }

    private int state = 0;//目前选中的输入框
    private int time = 0;//倒计时时间


    @Override

    protected void initData() {
        isRuning = false;
        toolbarBack.setText("跳过");
        k = getIntent().getStringExtra("k");
        //  timeCountDown();//倒计时

        switch (k) {
            case "1":
                StringUtils.setCorlor(sp_tv6, sp_img6);
                gBean = (GuestRoom.Bean) getIntent().getSerializableExtra("bean");
                locksign = getIntent().getStringExtra("locksign");
                name = getIntent().getStringExtra("name");
                cardID = getIntent().getStringExtra("card_id");
                break;
            case "999":
                StringUtils.setCorlor(sp_tv6, sp_img6);
                gBean = (GuestRoom.Bean) getIntent().getSerializableExtra("bean");
                locksign = getIntent().getStringExtra("locksign");
                name = getIntent().getStringExtra("name");
                cardID = getIntent().getStringExtra("card_id");
                IDCardInfo idCardInfo = getIntent().getParcelableExtra("idCard");
                cardID = idCardInfo.getStrIdCode();
                Log.e(TAG, "initData: " + name + cardID);
                break;
            case "4":
                break;
        }

    }


    ScheduledFuture scheduledFuture;

    public void timeCountDown() {
        if (scheduledFuture == null) {
            scheduledFuture = Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    if (time > 0) {
                        time--;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            changeGetCodrText();
                        }
                    });
                }
            }, 0, 1, TimeUnit.SECONDS);
        } else {
            scheduledFuture.cancel(false);
        }
    }

    public void changeGetCodrText() {
        if (time != 0) {
            get_code.setText(time + "s后重发");
            get_code.setBackgroundResource(R.drawable.btn_bg4);
        } else {
            get_code.setText("获取验证码");
            get_code.setBackgroundResource(R.drawable.btn_bg3);
        }
    }


    @OnClick({R.id.phone_num0, R.id.phone_num1, R.id.phone_num2, R.id.phone_num3, R.id.phone_num4,
            R.id.phone_num5, R.id.phone_num6, R.id.phone_num7, R.id.phone_num8, R.id.phone_num9,
            R.id.phone_del, R.id.phone_bt, R.id.skip_tv, R.id.toolbarBack, R.id.input_code, R.id.get_code, R.id.phone_et})
    protected void OnClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.phone_num0:
            case R.id.phone_num1:
            case R.id.phone_num2:
            case R.id.phone_num3:
            case R.id.phone_num4:
            case R.id.phone_num5:
            case R.id.phone_num6:
            case R.id.phone_num7:
            case R.id.phone_num8:
            case R.id.phone_num9:
                TextView tv = (TextView) v;
                String str = tv.getText().toString().trim();
                input(str);
                break;
            case R.id.phone_del:
                if (state == 0) {
                    phone_et.setText("");
                } else {
                    input_code.setText("");
                }
                break;
            case R.id.phone_et:
                state = 0;
                break;
            case R.id.get_code:
                if (Utils.isPhone(phone_et.getText().toString().trim())) {
                    state = 1;
                    if (time == 0) {
                        CodeInfo.smsSend(this, phone_et.getText().toString().trim(), new BackCall() {
                            @Override
                            public void deal(String s) {

                            }

                            @Override
                            public void deal(Object s) {
                                changeGetCodrText();
                                Tip.show(PhoneMsg.this, "验证码发送成功", true);
                            }
                        });

                    }
                    time = 60;
                } else {
                    Tip.show(App.getInstance(), "手机号格式错误", false);
                }
                break;
            case R.id.input_code:
                state = 1;
                break;
            case R.id.phone_bt:
                       if (Utils.isPhone(phone_et.getText().toString().trim())) {
                //          CodeInfo.smsCheck(this, phone_et.getText().toString().trim(), input_code.getText().toString().trim(), new BackCall() {
                //            @Override
                //          public void deal(String s) {

                //        }

                //      @Override
                //    public void deal(Object s) {   Intent intent = new Intent();
                intent.setClass(PhoneMsg.this, PaymentActivity.class);
                intent.putExtra("phoneNum", phone_et.getText().toString().trim());
                intent.putExtra("name", name);
                // intent.putExtra("path",path);
                // intent.putExtra("birth",birth);
                intent.putExtra("card_id", cardID);
                intent.putExtra("bean", gBean);
                intent.putExtra("locksign", locksign);
                intent.putExtra("idCard", getIntent().getParcelableExtra("idCard"));
                intent.putExtra("k", k);
                startActivity(intent);
                finish();
                   // }
                //     });
                } else {
                   Tip.show(App.getInstance(), "手机号格式错误", false);
               }
                break;
            case R.id.skip_tv:
            case R.id.toolbarBack:
                intent.setClass(PhoneMsg.this, PaymentActivity.class);
                intent.putExtra("phoneNum", "");
                intent.putExtra("name", name);
                // intent.putExtra("path",path);
                //  intent.putExtra("birth",birth);
                intent.putExtra("card_id", cardID);
                intent.putExtra("bean", gBean);
                intent.putExtra("locksign", locksign);
                intent.putExtra("idCard", getIntent().getParcelableExtra("idCard"));
                intent.putExtra("k", k);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    private void input(String s) {
        if (state == 0) {
            String result = phone_et.getText().toString().trim() + s;
            if (result.length() > 11) {
                return;
            }
            phone_et.setText(result);
        } else {
            String res = input_code.getText().toString().trim() + s;
            input_code.setText(res);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
    }
}
