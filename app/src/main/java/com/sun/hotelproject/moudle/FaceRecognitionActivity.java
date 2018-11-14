package com.sun.hotelproject.moudle;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sun.hotelproject.R;
import com.sun.hotelproject.app.Constants;
import com.sun.hotelproject.base.BaseActivity;
import com.sun.hotelproject.entity.Affirmstay;
import com.sun.hotelproject.entity.FaceRecognition;
import com.sun.hotelproject.entity.GuestRoom;
import com.sun.hotelproject.entity.LayoutHouse;
import com.sun.hotelproject.entity.QueryBookOrder;
import com.sun.hotelproject.entity.QueryCheckin;
import com.sun.hotelproject.moudle.camera.CameraFragment;
import com.sun.hotelproject.moudle.id_card.IDCardInfo;
import com.sun.hotelproject.moudle.net.HotelHttp;
import com.sun.hotelproject.utils.ActivityManager;
import com.sun.hotelproject.utils.HttpUrl;
import com.sun.hotelproject.utils.JsonCallBack;
import com.sun.hotelproject.utils.StringUtils;
import com.sun.hotelproject.utils.Tip;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.sun.hotelproject.utils.PlaySound.play;

/**
 * @author sun
 * 时间：2017年12月19
 * TODO:人脸识别页面
 */
@Route(path = "/hotel/facerecognition")
public class FaceRecognitionActivity extends BaseActivity {

    @BindView(R.id.sp_tv5)
    TextView sp_tv5;
    @BindView(R.id.sp2_tv5)
    TextView sp2_tv5;
    @BindView(R.id.linear_sp2)
    LinearLayout linear_sp2;
    @BindView(R.id.linear_sp1)
    LinearLayout linear_sp1;
    @BindView(R.id.linear_sp4)
    LinearLayout linear_sp4;
    @BindView(R.id.sp4_img6)
    ImageView sp4_img6;
    @BindView(R.id.sp4_tv6)
    TextView sp4_tv6;
    @BindView(R.id.sp_img5)
    ImageView sp_img5;
    @BindView(R.id.sp2_img5)
    ImageView sp2_img5;
    @BindView(R.id.sp4_content3)
    TextView sp4_content3;
    @BindView(R.id.sp_content4)
    TextView sp_content4;
    CameraFragment fragment;
    private String name;
    private String id_CardNo;
    private String birth;
    private GuestRoom.Bean gBean;
    private LayoutHouse house;
    private String locksign;
    private IDCardInfo idCardInfo;
    private QueryCheckin.Bean b;
    private String k;
    private QueryBookOrder.Bean qBean;
    private static final String TAG = "FaceRecognitionActivity";
    private String querytype;
    private Affirmstay.Bean ab;

    @Override
    protected int layoutID() {
        Window window;
        //得到窗口
        window = getWindow();
        //设置全屏
        //   window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //保持屏幕 常亮
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return R.layout.activity_face_recognition;
    }

    @Override
    protected void initData() {
        ActivityManager.getInstance().addActivity(this);
        Bundle bundle = new Bundle();
        k = getIntent().getStringExtra("k");
        if (k.equals("1")) {
            locksign = getIntent().getStringExtra("locksign");
            gBean = (GuestRoom.Bean) getIntent().getSerializableExtra("bean");
            mchid = getIntent().getStringExtra("mchid");
            if(Constants.USE_IDCARD){
                linear_sp1.setVisibility(View.VISIBLE);
                StringUtils.setCorlor(sp_tv5, sp_img5);
                sp_content4.setText("身份证");
                bundle.putString("k", k);
                name = getIntent().getStringExtra("name");
                id_CardNo = getIntent().getStringExtra("id_CardNo");
                idCardInfo = getIntent().getParcelableExtra("idCard");

                birth = getIntent().getStringExtra("birth");
                bundle.putString("name", name);
                bundle.putString("k", k);
                bundle.putString("birth", birth);
                bundle.putString("id_CardNo", id_CardNo);
                bundle.putString("mchid", mchid);
                bundle.putSerializable("bean", gBean);
                bundle.putParcelable("idCard", idCardInfo);
                bundle.putString("locksign", locksign);
                Log.e(TAG, "initData: " + name + id_CardNo);
            }else{
                linear_sp2.setVisibility(View.VISIBLE);
                bundle.putString("k", k);
                gBean = (GuestRoom.Bean) getIntent().getSerializableExtra("bean");
                mchid = getIntent().getStringExtra("mchid");
                bundle.putString("locksign", locksign);
            }
        } else if (k.equals("2")) {
            StringUtils.setCorlor(sp_tv5, sp2_img5);
            linear_sp2.setVisibility(View.VISIBLE);
            querytype = getIntent().getStringExtra("querytype");
            b = (QueryCheckin.Bean) getIntent().getSerializableExtra("bean");
            ab = (Affirmstay.Bean) getIntent().getSerializableExtra("bean2");
            bundle.putSerializable("bean", b);
            bundle.putString("k", k);
            bundle.putSerializable("bean2", ab);
        } else if (k.equals("4")) {
            querytype = getIntent().getStringExtra("querytype");
            qBean = (QueryBookOrder.Bean) getIntent().getSerializableExtra("bean");
            idCardInfo = getIntent().getParcelableExtra("idCard");
            bundle.putString("k", k);
            bundle.putParcelable("idCard", idCardInfo);
            bundle.putSerializable("bean", qBean);
            linear_sp4.setVisibility(View.VISIBLE);
            StringUtils.setCorlor(sp4_tv6, sp4_img6, sp4_content3, querytype);
        } else if (k.equals("6")) {
            bundle.putString("k", k);
            //linear_sp1.setVisibility(View.VISIBLE);
            sp_img5.setVisibility(View.VISIBLE);
            sp_tv5.setBackgroundResource(R.drawable.oval_shape);
            sp_tv5.setTextColor(getResources().getColor(R.color.Swrite));
        } else {
            bundle.putString("k", k);
        }
        fragment = new CameraFragment();
        fragment.setArguments(bundle);
        ChangeFragment(fragment);
        setScreenBrightness(200);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @Override
    public void toolBacBcak() {
        super.toolBacBcak();
        HotelHttp.cancalLockRoom(k, gBean, mchid);
    }

    @OnClick(R.id.toolbarBack)
    public void onClick(View v) {
        super.OnClick(v);
        switch (v.getId()) {
            case R.id.toolbarBack:
                HotelHttp.cancalLockRoom(k, gBean, mchid);
                break;
        }
    }

    /**
     * 切换
     *
     * @param fragment 需要切换到的fragment
     */
    private void ChangeFragment(Fragment fragment) {
        fragment.setArguments(getIntent().getExtras());
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.commit();
    }

    private void setScreenBrightness(int i) {
        Window localWindow = getWindow();
        WindowManager.LayoutParams params = localWindow.getAttributes();
        float f = i / 255.0F;
        params.screenBrightness = f;
        localWindow.setAttributes(params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
