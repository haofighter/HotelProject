package com.sun.hotelproject.moudle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.net.md5.Constant;
import com.sun.hotelproject.R;
import com.sun.hotelproject.app.App;
import com.sun.hotelproject.app.Constants;
import com.sun.hotelproject.base.BaseActivity;

import com.sun.hotelproject.entity.BannerModel;
import com.sun.hotelproject.entity.RoomNo;
import com.sun.hotelproject.utils.ActivityManager;
import com.sun.hotelproject.utils.Animutils;
import com.sun.hotelproject.utils.CommonSharedPreferences;
import com.sun.hotelproject.utils.DataTime;
import com.sun.hotelproject.utils.PopUtils;
import com.sun.hotelproject.utils.Tip;
import com.sun.hotelproject.utils.Utils;
import com.sun.hotelproject.view.MyVideoView;
import com.szxb.smart.pos.jni_interface.Barcode;
import com.szxb.smart.pos.jni_interface.Com4052;
import com.szxb.smart.pos.jni_interface.printer;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import K720_Package.K720_Serial;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;


/**
 * @author sun
 * 时间：2017/11/22
 * TODO:主界面
 */
@Route(path = "/hotel/main")
public class MainActivity extends BaseActivity {
    //    @BindView(R.id.bt1)TextView bt1;
//    @BindView(R.id.bt2)TextView bt2;
//    @BindView(R.id.bt3)TextView bt3;
//    @BindView(R.id.bt4)TextView bt4;
//    @BindView(R.id.bt5)TextView bt5;
    @BindView(R.id.invoice)
    LinearLayout invoice; //发票
    @BindView(R.id.check_in)
    LinearLayout check_in;//入住
    @BindView(R.id.check_out)
    LinearLayout check_out;//退房
    @BindView(R.id.renwal)
    LinearLayout renwal;//续住
    @BindView(R.id.reserve)
    LinearLayout reserve;//打印发票
    @BindView(R.id.play)
    ImageView play;//播放
    @BindView(R.id.toolBarTime)
    TextView toolBarTime;
    @BindView(R.id.toolBar_logo)
    ImageView toolBar_logo;
    @BindView(R.id.toolbarBack)
    Button toolbarBack;
    @BindView(R.id.title2)
    TextView title2;
    @BindView(R.id.img_wuka)
    ImageView img_wuka;
    @BindView(R.id.img_wangshang)
    ImageView wangshang;
    //@BindView(R.id.myVideo)MyVideoView myVideo;
    @BindView(R.id.img_xufang)
    ImageView xufang;

    @BindView(R.id.imgchange)
    ImageView imgchange;
    @BindView(R.id.videoView)
    MyVideoView videoView;
    private Timer timer = new Timer();
    private TimerTask task;
    private int flag = 0;
    //定义切换的图片的数组id
    int imgids[] = new int[]{R.drawable.beijing, R.drawable.beijing1,
            R.drawable.beijing2};
    int imgstart = 0;
    boolean isTrue = false;

    public static byte MacAddr = 0;
    private static final String TAG = "MainActivity";
    String url = Environment.getExternalStorageDirectory().getAbsolutePath() + "/123.mp4";

    //定时轮播图片，需要在主线程里面修改 UI
    @SuppressLint("HandlerLeak")
    private Handler myHandler = new Handler() {
        @Override
        //重写handleMessage方法,根据msg中what的值判断是否执行后续操作
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Log.d("数据", String.valueOf(imgstart));
                imgchange.setImageResource(imgids[imgstart++ % imgids.length]);
            } else if (msg.what == 1) {
                imgstart = 0;
                flag = 0;
//                imgchange.setVisibility(View.VISIBLE);
//                videoView.setVisibility(View.GONE);
//                flag = 2;//首先要将这个标签换掉 不然会出现因为定时器的原因导致视频播放不全的问题。
//                Log.d("测试", String.valueOf(flag));
//                imgchange.setVisibility(View.GONE);
//                videoView.setVisibility(View.VISIBLE);
//                initData();//播放视频的方法

            } else {
                Log.d(TAG, "啥我也不干  空定时器");
            }
        }
    };


    @Override
    protected int layoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        start();
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {
        isRuning = false;
        toolBarTime.setVisibility(View.VISIBLE);
        toolbarBack.setVisibility(View.GONE);
        handler.postDelayed(runnable, 1000);
        ActivityManager.getInstance().addActivity(this);
        Connect();
        handler.postDelayed(selectState, 5 * 1000);

    }

    Runnable selectState = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, 10 * 1000);
            if (getStates().equals("30")) {
                Log.e(TAG, "run: " + "卡箱有卡");
            } else if (getStates().equals("38")) {
                Log.e(TAG, "run: " + "卡箱少卡");
            } else {
                getCard();
            }
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, 1000);
            toolBarTime.setText(DataTime.curenTime());
        }
    };

    /**
     * 选择播放图片还是播放视频
     */
    public void start() {
        task = new TimerTask() {
            @Override
            public void run() {
                if (imgstart < imgids.length) {
                    Log.d(TAG, "长度" + imgids.length);
                    Message msg = Message.obtain();
                    msg.what = 0;
                    myHandler.sendEmptyMessage(flag);
                    Log.d(TAG, "flag" + flag);
                } else {
                    if (flag == 2) {
                        myHandler.sendEmptyMessage(flag);
                        //啥也不干
                    } else {
                        flag = 1;
                        //  Log.d("测试", String.valueOf(flag));
                        myHandler.sendEmptyMessage(flag);
                        //播放视频
                    }
                }
            }
        };
        //定时器开始执行
        timer.schedule(task, 0, 3000);

    }

    //播放视频
    public void initVideo() {

        //String uri = "android.resource://" + getPackageName() + "/" + R.raw.b;
        videoView.setVideoURI(Uri.parse(url));
        //开始播放
        videoView.start();

        //播放完成回调
        videoView.setOnCompletionListener(new MyPlayerOnCompletionListener());

        //防止出现视频播放错误的问题
        videoView.setOnErrorListener(videoErrorListener);

    }

    //防出现无法播放此视频窗口
    public MediaPlayer.OnErrorListener videoErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            return true;
        }
    };

    //回调方法
    private class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            /**再次启动图片的轮播,设置了imgstart为初始值*/
            /**多个视频可以在这进行切换，进行一次判断加入还有视频就播放，没有就走下面这一段*/
//            imgstart = 0;
//            flag = 0;
//            imgchange.setVisibility(View.VISIBLE);
//            videoView.setVisibility(View.GONE);
            videoView.start();
        }
    }

    @OnClick({R.id.check_in, R.id.check_out, R.id.invoice, R.id.renwal, R.id.reserve, R.id.play,})
    // R.id.bt1,R.id.bt2,R.id.bt3,R.id.bt4,R.id.bt5})
    protected void OnClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.check_in://入住 首先判断卡箱是否有卡
                if (Utils.isFastClick()) {
                    clean();
                    if (getStates().equals("30")) {
                        PopUtils.getInstance().setContentView(R.layout.select_card_view, MainActivity.this)
                                .setShowView(MainActivity.this.getWindow().getDecorView())
                                .setAnim(R.style.mypopwindow_anim_style)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent in = new Intent(MainActivity.this, LayoutHouseActivity.class);
                                        switch (view.getId()) {
                                            case R.id.card:
                                                Constants.USE_IDCARD = true;
                                                in.putExtra("k", "1");//入住
                                                startActivity(in);
                                                PopUtils.getInstance().dismiss();
                                                break;
                                            case R.id.ecard:
                                                Constants.USE_IDCARD = false;
                                                in.putExtra("k", "1");//入住
                                                startActivity(in);
                                                PopUtils.getInstance().dismiss();
                                                break;
                                            case R.id.close:
                                                PopUtils.getInstance().dismiss();
                                                break;
                                        }

                                    }
                                }).build();
                    } else if (getStates().equals("38")) {
                        img_wuka.setVisibility(View.VISIBLE);
                        Animutils.alphaAnimation(img_wuka);
                    } else {
                        Tip.show(getApplicationContext(), "卡槽有阻塞卡", false);
                        getCard();
                    }
                }
                break;
            case R.id.check_out: //退房
                if (Utils.isFastClick()) {
                    clean();
                    // play.setBackgroundResource(R.drawable.btn_play);
                    intent.setClass(MainActivity.this, CheckOutActivity.class);
                    intent.putExtra("k", "3");
                    startActivity(intent);
                }
                break;
            case R.id.invoice: //打印发票
                if (Utils.isFastClick()) {
                    clean();
                    //println();
                    Tip.show(getApplicationContext(), "该功能暂未开放", false);
                    return;

                    //                    String g_de_skey = "BI01pjJKUnpeM7hhDAAbJR2t/3CGM4glBoxFsRKBXNuNQiW9ZnE2jmpYQvZ4i/ysmfDC2ZpgZz2EKmwJeN3IpwqNAQV1KTqAb9kIJsnYnjs8F0jd+UXc5LoQHxrloFlcs86MW1k7cSvH3+8+fI4NZjA=";
//                    String g_user_info = "z+Dkv9xyPICbx3qe+GKBGUGQ7jxPVz8iYh/Fy+uZ/uiQYSfwnlCHcVwuMl4xjIIYpVoq9I9yvgMH9052GkCvKg==";
//                    System.out.println("\n\n==================================================================");
//                    System.out.println("eID数字身份信息查询（不带证书）\n");
//                    try {
//                        test_userinfo(g_de_skey, g_user_info);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    //                    String s = barcodeScan();
//
//                    Log.e(TAG, "OnClick: 270行"+ s);
                    // Tip.show(getApplicationContext(),barcodeScan(),true);
                    //   startActivity(new Intent(MainActivity.this,QrCodeActivity.class));
                    //moveCard();
                    //  play.setBackgroundResource(R.drawable.btn_play);
                }
                break;
            case R.id.renwal: //续住
                if (Utils.isFastClick()) {
                    clean();
                    xufang.setVisibility(View.VISIBLE);
                    Animutils.alphaAnimation(xufang);
                    //reTrieve();
                }
                break;
            case R.id.reserve://预定
                if (Utils.isFastClick()) {
                    clean();
                    intent.setClass(MainActivity.this, ChoiceActivity.class);
                    intent.putExtra("k", Constants.USER_SCHEDULE);
                    startActivity(intent);
                }
                break;
            case R.id.play://播放视频
                if (Utils.isFastClick()) {
                    isTrue = !isTrue;
                    if (isTrue) {
                        play.setBackgroundResource(R.drawable.play2);
                        flag = 2;
                        Log.d("测试", String.valueOf(flag));
                        imgchange.setVisibility(View.GONE);
                        videoView.setVisibility(View.VISIBLE);
                        initVideo();//播放视频的方法
                    } else {
                        clean();
                    }
                }
                break;
//            case R.id.bt1://入住 首先判断卡箱是否有卡
//                if (Utils.isFastClick()) {
//                    clean();
//                    if (getStates().equals("30")) {
//                        intent.setClass(MainActivity.this, LayoutHouseActivity.class);
//                        intent.putExtra("k", "1");
//                        startActivity(intent);
//                    } else if(getStates().equals("38")){
//                        img_wuka.setVisibility(View.VISIBLE);
//                        Animutils.alphaAnimation(img_wuka);
//                    }else {
//                        Tip.show(getApplicationContext(),"卡槽有阻塞卡",false);
//                        getCard();
//                    }
//                }
//                break;
//            case R.id.bt2: //退房
//                if (Utils.isFastClick()) {
//                    clean();
//                    // play.setBackgroundResource(R.drawable.btn_play);
//                    intent.setClass(MainActivity.this, CheckOutActivity.class);
//                    intent.putExtra("k", "3");
//                    startActivity(intent);
//                }
//                break;
//            case R.id.bt5: //打印发票
//                if (Utils.isFastClick()) {
//                    clean();
//                    startActivity(new Intent(MainActivity.this,TestActivity.class));
//                    //moveCard();
//                    //  play.setBackgroundResource(R.drawable.btn_play);
//                }
//                break;
//            case R.id.bt3: //续住
//                if (Utils.isFastClick()) {
//                    clean();
//                    xufang.setVisibility(View.VISIBLE);
//                    Animutils.alphaAnimation(xufang);
//                    //reTrieve();
//                }
//                break;
//            case R.id.bt4://预定
//                if (Utils.isFastClick()){
//                    clean();
//                    intent.setClass(MainActivity.this,ChoiceActivity.class);
//                    intent.putExtra("k","4");
//                    startActivity(intent);
//                }
//                break;

            default:
                break;
        }
        super.OnClick(v);
    }

    public void clean() {
        isTrue = false;
        play.setBackgroundResource(R.drawable.btn_play);
        imgstart = 0;
        flag = 0;
        imgchange.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.GONE);
    }

//    @OnTouch({R.id.check_in,R.id.check_out,R.id.invoice,R.id.renwal,R.id.reserve,R.id.play})
//    boolean OnTouch(View v, MotionEvent event){
//        switch (v.getId()){
//            case R.id.check_in://入住 首先判断卡箱是否有卡
//              if (event.getAction() == MotionEvent.ACTION_DOWN){
//                bt1.getBackground().setAlpha(128);
//              }else if (event.getAction() == MotionEvent.ACTION_UP){
//                  bt1.getBackground().setAlpha(255);
//              }
//                break;
//            case R.id.check_out: //退房
//                if (event.getAction() == MotionEvent.ACTION_DOWN){
//
//                    bt2.getBackground().setAlpha(128);
//                }else if (event.getAction() == MotionEvent.ACTION_UP){
//                    bt2.getBackground().setAlpha(255);
//                }
//                break;
//            case R.id.invoice: //打印发票
//                if (event.getAction() == MotionEvent.ACTION_DOWN){
//
//                    bt5.getBackground().setAlpha(128);
//                }else if (event.getAction() == MotionEvent.ACTION_UP){
//                    bt5.getBackground().setAlpha(255);
//                }
//                break;
//            case R.id.renwal: //续住
//                if (event.getAction() == MotionEvent.ACTION_DOWN){
//
//                    bt3.getBackground().setAlpha(128);
//                }else if (event.getAction() == MotionEvent.ACTION_UP){
//                    bt3.getBackground().setAlpha(255);
//                }
//                break;
//            case R.id.reserve://预定
//                if (event.getAction() == MotionEvent.ACTION_DOWN){
//
//                    bt4.getBackground().setAlpha(128);
//                }else if (event.getAction() == MotionEvent.ACTION_UP){
//                    bt4.getBackground().setAlpha(255);
//                }
//                break;
//            default:
//                break;
//        }
//        return false;
//    }

    /**
     * 出卡
     */
    private void getCard() {
        int nRet;
        byte[] SendBuf = new byte[3];
        String[] RecordInfo = new String[2];
        SendBuf[0] = 0x46;
        SendBuf[1] = 0x43;
        SendBuf[2] = 0x34;
        nRet = K720_Serial.K720_SendCmd(MacAddr, SendBuf, 3, RecordInfo);
        if (nRet == 0) {

        } else {
            // tv2.setText("出卡失败");
        }
    }

    /**
     * 查询卡箱
     */
    private String getStates() {
        int nRet;
        String state = null;
        byte[] StateInfo = new byte[4];
        String[] RecordInfo = new String[2];
        nRet = K720_Serial.K720_SensorQuery(MacAddr, StateInfo, RecordInfo);
        if (nRet == 0) {
            state = Integer.toHexString(StateInfo[3] & 0xFF).toUpperCase();
        } else {
            Tip.show(getApplicationContext(), "状态值查询失败!", false);
        }
        return state;
    }

    /**
     * 二维码条形码
     */
    Barcode bar;
    private int fd;

    String barcodeScan() {
        String str;

        byte[] sendbyte = {0x16, 0x54, 0x0d};

        fd = Com4052.Com4052Open();
        if (fd < 0) {
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

    /**
     * 连接
     */
    private void Connect() {
        String strPort = "/dev/ttyS3";
        int re;
        byte i;
        String[] RecordInfo = new String[2];
        int Baudate = 9600;
        re = K720_Serial.K720_CommOpen(strPort);

        if (re == 0) {
            for (i = 0; i < 16; i++) {
                re = K720_Serial.K720_AutoTestMac(i, RecordInfo);
                if (re == 0) {
                    MacAddr = i;
                    break;
                }
            }
            if (i == 16 && MacAddr == 0) {
                K720_Serial.K720_CommClose();
            }
        }
    }

    private void DisConnect() {
        int nRet;
        nRet = K720_Serial.K720_CommClose();
        if (nRet == 0) {
            MacAddr = 0;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Tip.show(this, "设备断开失败", false);
        }
        // Tip.show(this,"设备断开失败，错误代码为："+K720_Serial.ErrorCode(nRet, 0),false);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (timer != null) {
            timer.cancel();
            timer = null;
            timer = new Timer();
            start();
        }
        timer = new Timer();
        start();
        handler.post(runnable);
        handler.postDelayed(selectState, 5 * 1000);
        isRuning = false;


    }

    private void cancel() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cancel();
        handler.removeCallbacks(selectState);
        handler.removeCallbacks(runnable);

    }

    @Override
    protected void onDestroy() {
        DisConnect();
        cancel();
        handler.removeCallbacks(runnable);
        handler.removeCallbacks(runnable);
        CommonSharedPreferences.put("data", "");
        super.onDestroy();
    }

    printer p;

    private void println() {
        p = new printer();
        int i = p.PrinterOpen();
        int key = p.PrinterStatus();
        if (key == 0) {
            Tip.show(getApplicationContext(), "打印机缺纸", false);
            return;
        }
        Log.e(TAG, "initData: 打印纸状态" + key);
        Log.e(TAG, "initData: i---" + i);
//        byte[] b = new byte[] { (byte) 0x1c, (byte) 0x70, (byte) 0x01,
//                (byte) 0x00 };
//        int data = p.PrinterWrite(b, 4);
//
//        BitmapDrawable drawable = (BitmapDrawable) this.getResources()
//                .getDrawable(R.drawable.icon_logo);
//        Bitmap bitmap = drawable.getBitmap();
//        p.printBitmap(bitmap, 0, 0);
        //   	p.printBitmap(bm, 0, 0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                print(p);

            }
        }).start();

        p.PrinterClose();
    }

    private void print(printer p) {
        Log.e(TAG, "print: " + "进入打印方法");
        p.PrinterOpen();
        byte[] b = new byte[]{(byte) 0x1c, (byte) 0x70, (byte) 0x01,
                (byte) 0x00};
        int data = p.PrinterWrite(b, 4);
        byte[] one = HexString2Bytes(sb("CHIC"));
        byte[] fir = HexString2Bytes(sb("房间号  Room No"));
        //  byte[] sec = HexString2Bytes(sb(roomNum.trim()));

        byte[] thr2 = HexString2Bytes(sb("住房状态  Housing status"));
        // byte[] fou2 = HexString2Bytes(sb(state.trim()));
        byte[] thr3 = HexString2Bytes(sb("支付金额  Amount of payment"));
        // byte[] fou3 = HexString2Bytes(sb(price.trim()));
        byte[] thr1 = HexString2Bytes(sb("入住人姓名  Name of the occupant"));
        //  byte[] fou1 = HexString2Bytes(sb(name.trim()));
        byte[] thr4 = HexString2Bytes(sb("订单号  Order number"));
        // byte[] fou4 = HexString2Bytes(sb(orderId.trim()));
        byte[] thr = HexString2Bytes(sb("入住日期  Arrival Date"));
        //  byte[] fou = HexString2Bytes(sb(beginTime.trim()));
        byte[] fiv = HexString2Bytes(sb("离店日期  Departure Date"));
        // byte[] sev = HexString2Bytes(sb(endTime.trim()));
        byte[] fiv1 = HexString2Bytes(sb("支付方式  Payment method"));
        //  byte[] sev1 = HexString2Bytes(sb(payway.trim()));
        byte[] six = HexString2Bytes(sb("----------------------------------------"));
        // byte[] six = HexString2Bytes(sb("____________________________________________"));
        byte[] th = HexString2Bytes(sb("打印时间  Print Time " + DataTime.currentTime()));
        p.PrinterWrite(printer.getCmdEscEN(1), 3);// 加粗
        p.PrinterWrite(printer.getCmdEscAN(1), 3);// 居中
        byte[] a = {(byte) 1, (byte) 2};
        p.PrinterWrite(a, 2);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(printer.getCmdGs_N(1), printer.getCmdGs_N(1).length);
        p.PrinterWrite(printer.getCmdEscSo(), printer.getCmdEscSo().length);
        p.PrinterWrite(one, one.length);
        p.PrinterWrite(printer.getCmdGs_N(0), printer.getCmdGs_N(0).length);
        p.PrinterWrite(printer.getCmdEscDc4(), printer.getCmdEscDc4().length);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(printer.getCmdEsc___N(0), 3);
        p.PrinterWrite(six, six.length);
        p.PrinterWrite(printer.getCmdLf(), 1);
        // 两倍宽 getCmdEscSo() getCmdFf()换行
        p.PrinterWrite(fir, fir.length);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(printer.getCmdEscSo(), printer.getCmdEscSo().length);
        //   p.PrinterWrite(sec, sec.length);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(printer.getCmdLf(), 1);
//        p.PrinterWrite(printer.getCmdLf(), 1);
//        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(printer.getCmdEscDc4(), 2);// 加宽还原

        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(thr3, thr3.length);
        //  p.PrinterWrite(printer.getCmdLf(), 1);
        //  p.PrinterWrite(fou3, fou3.length);

        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(thr2, thr2.length);
        //  p.PrinterWrite(printer.getCmdLf(), 1);
        // p.PrinterWrite(fou2, fou2.length);

        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(thr1, thr1.length);
        //  p.PrinterWrite(printer.getCmdLf(), 1);
        // p.PrinterWrite(fou1, fou1.length);

        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(thr4, thr4.length);
        // p.PrinterWrite(printer.getCmdLf(), 1);
        // p.PrinterWrite(fou4, fou4.length);

        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(thr, thr.length);
        // p.PrinterWrite(printer.getCmdLf(), 1);
        // p.PrinterWrite(fou, fou.length);

        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(fiv, fiv.length);
        //  p.PrinterWrite(printer.getCmdLf(), 1);
        // p.PrinterWrite(sev, sev.length);

        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(fiv1, fiv1.length);
        // p.PrinterWrite(printer.getCmdLf(), 1);
        // p.PrinterWrite(sev1, sev1.length);

        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(printer.getCmdEsc___N(0), 3);
        p.PrinterWrite(six, six.length);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(th, th.length);
        p.PrinterWrite(printer.getCmdLf(), 1);
        // p.PrinterWrite(re, re.length);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(printer.getCmdLf(), 1);

        byte[] b1 = new byte[]{(byte) 0x1b, (byte) 0x69};// 切纸

        int lock = p.PrinterWrite(b1, 2);
        System.out.println(lock + "打印完成？0：其他");


        p.PrinterClose();
    }

    public byte[] HexString2Bytes(String src) {
        if (null == src || 0 == src.length()) {
            return null;
        }
        byte[] ret = new byte[src.length() / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < (tmp.length / 2); i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    public byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0}));
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1}));
        return (byte) (_b0 ^ _b1);
    }

    public String sb(String content) {
        String str = content;

        String hexString = "0123456789ABCDEF";
        byte[] bytes;
        try {
            bytes = str.getBytes("GBK");// 如果此处不加编码转化，得到的结果就不是理想的结果，中文转码
            StringBuilder sb = new StringBuilder(bytes.length * 2);

            for (byte aByte : bytes) {
                sb.append(hexString.charAt((aByte & 0xf0) >> 4));
                sb.append(hexString.charAt((aByte & 0x0f)));
                // sb.append("");
            }
            str = sb.toString();

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }


}
