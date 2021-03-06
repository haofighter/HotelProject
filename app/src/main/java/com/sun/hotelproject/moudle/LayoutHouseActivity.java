package com.sun.hotelproject.moudle;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;

import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.sun.hotelproject.R;
import com.sun.hotelproject.app.App;
import com.sun.hotelproject.app.Constants;
import com.sun.hotelproject.base.BaseActivity;
import com.sun.hotelproject.base.adapter.CommonAdapter;
import com.sun.hotelproject.base.adapter.ViewHolder;
import com.sun.hotelproject.dao.DaoSimple;
import com.sun.hotelproject.entity.FloorTable;
import com.sun.hotelproject.entity.GuestRoom;
import com.sun.hotelproject.entity.HouseTable;
import com.sun.hotelproject.entity.LayoutHouse;
import com.sun.hotelproject.entity.QueryBookOrder;
import com.sun.hotelproject.entity.QueryRomm;
import com.sun.hotelproject.entity.QueryRoomType;
import com.sun.hotelproject.entity.RoomTable;
import com.sun.hotelproject.utils.ActivityManager;
import com.sun.hotelproject.utils.CommonSharedPreferences;
import com.sun.hotelproject.utils.DataTime;
import com.sun.hotelproject.utils.DividerItemDecoration;
import com.sun.hotelproject.utils.HttpUrl;
import com.sun.hotelproject.utils.JsonCallBack;
import com.sun.hotelproject.utils.StringUtils;
import com.sun.hotelproject.utils.Tip;
import com.sun.hotelproject.utils.Utils;
import com.sun.hotelproject.view.RecyclerViewForEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * @author sun
 * 时间 2017/11/24
 * TODO:选择房型界面
 */
@Route(path = "/hotel/layouthouse")
public class LayoutHouseActivity extends BaseActivity {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.beginTime)
    TextView begin;
    @BindView(R.id.endTime)
    TextView end;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.sp_content4)
    TextView sp_content4;
    @BindView(R.id.imgdowm)
    ImageView imgdown;
    @BindView(R.id.show_data)
    LinearLayout show_data;
    @BindView(R.id.toolbarBack)
    Button toolbarBack;

    @BindView(R.id.sp_tv2)
    TextView sp_tv2;
    @BindView(R.id.sp_img2)
    ImageView sp_img2;
    @BindView(R.id.anim_layout)
    RelativeLayout anim_lauout;
    @BindView(R.id.anim_img)
    ImageView anim_img;
    @BindView(R.id.anim_tv)
    TextView anim_tv;
    CommonAdapter adapter;
    List<HouseTable.Bean> list;
    private String k;
    private int inDay = 0;
    private static final String TAG = "LayoutHouseActivity";
    private DaoSimple daoSimple;
    // String date="";
    private String mYear, mMonth, mDay, mMonth1, mDay1;
    private String month, month1;//英文
    private String startTime = "";
    private String finshTime = "";
    private String startTime1 = "";
    private String finshTime1 = "";
    Animation operatingAnim;
    private String mchid;
    private List<GuestRoom.Bean> gblist;
    private List<QueryRomm> datas;
    private QueryRomm qr;
    private List<String> rommtype;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    protected int layoutID() {
        return R.layout.activity_layout_house;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        ActivityManager.getInstance().addActivity(this);
        mchid = (String) CommonSharedPreferences.get("mchid", "");
        daoSimple = new DaoSimple(this);
        list = daoSimple.houseSelAll();

        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.load_animation);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        StringUtils.setCorlor(sp_tv2, sp_img2);
        k = getIntent().getStringExtra("k");
        startTime = DataTime.curenData();
        String[] time = startTime.split("-");
        mMonth = time[1];
        mDay = time[2];
        startTime1 = mMonth + "/" + mDay;
        finshTime = DataTime.Tomorrow();
        String[] time1 = finshTime.split("-");
        mMonth1 = time1[1];
        mDay1 = time1[2];
        finshTime1 = mMonth1 + "/" + mDay1;
        month = DataTime.returnToEnglish(Integer.parseInt(mMonth));
        month1 = DataTime.returnToEnglish(Integer.parseInt(mMonth1));
        begin.setText(DataTime.updTextSize(getApplicationContext(), mDay + " / " + mMonth + " " + month, 2), TextView.BufferType.SPANNABLE);
        end.setText(DataTime.updTextSize(getApplicationContext(), mDay1 + " / " + mMonth1 + " " + month1, 2), TextView.BufferType.SPANNABLE);
        inDay = DataTime.phase(startTime, finshTime);
        if (inDay < 10) {
            this.content.setText(DataTime.updTextSize(getApplicationContext(), "0" + inDay + " / 晚(night)", 2));
        } else {
            this.content.setText(DataTime.updTextSize(getApplicationContext(), inDay + " / 晚(night)", 2));
        }
        if (Constants.USE_IDCARD) {
            sp_content4.setText("身份证验证");
        } else {
            sp_content4.setText("E证通验证");
        }
        //   datas =new ArrayList<>();
        // qr =new QueryRomm();
        //  gblist =new ArrayList<>();
        // for (HouseTable.Bean bean:list) {

        //   Log.e(TAG, "initData: "+datas.toString());
        // }
//        Log.e(TAG, "initData: "+datas.toString());
//        init(datas);
//        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPost(startTime, finshTime);
    }

    private void init(List<String> list) {
//        for (QueryRomm queryRomm :list){
//            if (queryRomm.getDatas().size()==0){
//                datas.remove(queryRomm);
//            }
//        }
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler.setLayoutManager(manager);

        adapter = new CommonAdapter<String>(LayoutHouseActivity.this, R.layout.recycle_item, list) {
            @Override
            protected void convert(ViewHolder holder, final String s, final int position) {
                final HouseTable.Bean houseSel = daoSimple.houseSel(s);
                holder.setText(R.id.type, houseSel.getRtpmsnname());
                holder.setOnClickListener(R.id.check, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Utils.isFastClick()) {
                            if (end.getText().toString().equals("离店时间")) {
                                Tip.show(LayoutHouseActivity.this, "请选择入住天数", false);
                                return;
                            }
                            if (DataTime.phase(startTime, finshTime) <= 0) {
                                Tip.show(LayoutHouseActivity.this, "选择时间不合理！", false);
                                return;
                            }
                            CommonSharedPreferences.put("beginTime", startTime);
                            CommonSharedPreferences.put("endTime", finshTime);
                            CommonSharedPreferences.put("beginTime1", startTime1);
                            CommonSharedPreferences.put("endTime1", finshTime1);
                            CommonSharedPreferences.put("inDay", inDay + "");
                            CommonSharedPreferences.put("content", content.getText().toString());
                            queryRoom(s, startTime, finshTime, houseSel.getRtpmsnname(), position);
                        }
                    }
                });
            }
        };
        recycler.setAdapter(adapter);
    }

    @OnClick({R.id.show_data, R.id.toolbarBack})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_data:
                if (Utils.isFastClick()) {
                    Intent intent = new Intent(this, DatePickActivity.class);
                    intent.putExtra("k", "1");
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.toolbarBack:
                finish();
                break;

        }
    }


    /**
     * 查询可住房
     */

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void queryRoom(final String rtpmsno, String beginTime, String endTime, final String name, final int position) {
        anim_lauout.setVisibility(View.VISIBLE);
        anim_img.setAnimation(operatingAnim);
        anim_img.startAnimation(operatingAnim);
        anim_tv.setText("正在加载中......");

        OkGo.<GuestRoom>post(HttpUrl.QUERYROOMINFO2)
                .tag(this)
                .params("mchid", mchid)
                .params("indate", beginTime)
                .params("outdate", endTime)
                .params("rtpmsno", rtpmsno)
                .execute(new JsonCallBack<GuestRoom>(GuestRoom.class) {
                    @Override
                    public void onSuccess(Response<GuestRoom> response) {
                        if (response.body().getRescode().equals("0000")) {
                            gblist = response.body().getDatalist();
                            anim_img.clearAnimation();
                            anim_lauout.setVisibility(View.GONE);
                            if (gblist.size() == 0) {
                                Tip.show(LayoutHouseActivity.this, "暂未搜寻到此类型房间", false);
                                return;
                            }
                            Intent intent = new Intent(LayoutHouseActivity.this, SelectActivity.class);
                            intent.putExtra("queryRomm", rtpmsno);//房型码
                            intent.putExtra("name", name);
                            intent.putExtra("gList", (Serializable) gblist);
                            intent.putExtra("mchid", mchid);
                            intent.putExtra("k", k);
                            startActivityForResult(intent, 2);
                        } else {
                            anim_img.clearAnimation();
                            anim_lauout.setVisibility(View.GONE);
                            Tip.show(getApplicationContext(), response.body().getResult(), false);
                        }
                        super.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<GuestRoom> response) {
                        super.onError(response);
                        anim_img.clearAnimation();
                        anim_lauout.setVisibility(View.GONE);
                        Tip.show(getApplicationContext(), "服务器连接异常", false);
                    }
                });
    }

    /**
     * 查询可住房型
     */

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void getPost(String beginTime, String endTime) {
        anim_lauout.setVisibility(View.VISIBLE);
        anim_img.setAnimation(operatingAnim);
        anim_img.startAnimation(operatingAnim);
        anim_tv.setText("正在加载中......");

        OkGo.<QueryRoomType>post(HttpUrl.QUERYROOMTYPE2)
                .tag(this)
                .params("mchid", mchid)
                .params("indate", beginTime)
                .params("outdate", endTime)
                .params("rtpmsno", "")
                .execute(new JsonCallBack<QueryRoomType>(QueryRoomType.class) {
                    @Override
                    public void onSuccess(Response<QueryRoomType> response) {
                        if (response.body().getRescode().equals("0000")) {
                            rommtype = response.body().getDatalist();
                            init(rommtype);
                            adapter.notifyDataSetChanged();
                            anim_img.clearAnimation();
                            anim_lauout.setVisibility(View.GONE);
                        } else {
                            anim_img.clearAnimation();
                            anim_lauout.setVisibility(View.GONE);
                            try {
                                Tip.show(getApplicationContext(), mResponse.getResult(), false);
                            } catch (Exception e) {
                                Tip.show(getApplicationContext(), "请求失败", false);
                            }
                        }
                        super.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<QueryRoomType> response) {
                        super.onError(response);
                        anim_img.clearAnimation();
                        anim_lauout.setVisibility(View.GONE);
                        Tip.show(getApplicationContext(), "服务器连接异常", false);
                    }
                });
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 0) {
            if (isRuning)
                handler.postDelayed(timeRunnable, 1000);
        }
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (isRuning) {
                handler.postDelayed(timeRunnable, 1000);
            }
            String selectTime = data.getStringExtra("selectTime");
            String[] select = selectTime.split("-");
            String selectYear = select[0];
            String selectMonth = select[1];
            String selectDay = select[2];

            Log.e(TAG, "onActivityResult: " + selectYear + selectMonth + selectDay);

            finshTime = selectTime;
            finshTime1 = selectMonth + "/" + selectDay;
            month = DataTime.returnToEnglish(Integer.parseInt(selectMonth));

            end.setText(DataTime.updTextSize(getApplicationContext(), selectDay + " / " + selectMonth + " " + month, 2), TextView.BufferType.SPANNABLE);

            inDay = DataTime.phase(startTime, finshTime);
            if (inDay < 10) {
                this.content.setText(DataTime.updTextSize(getApplicationContext(), "0" + inDay + " / 晚(night)", 2));
            } else {
                this.content.setText(DataTime.updTextSize(getApplicationContext(), inDay + " / 晚(night)", 2));
            }//  getPost();

            getPost(startTime, finshTime);
        }
        if (requestCode == 2 && resultCode == 0) {
            if (isRuning) {
                toolbarBack.setText("返回(" + time + "s)");
                handler.post(timeRunnable);
            } else {

            }
        }
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            String locksign = data.getStringExtra("locksign");
            GuestRoom.Bean gBean = (GuestRoom.Bean) data.getSerializableExtra("bean");
            Intent intent = new Intent(LayoutHouseActivity.this, OrderDetailsActivity.class);
            intent.putExtra("bean", gBean);
            intent.putExtra("locksign", locksign);
            intent.putExtra("k", k);
            startActivity(intent);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        toolbarBack.setText("返回(" + time + "s)");
        OkGo.getInstance().cancelTag(this);
        super.onDestroy();
    }
}
