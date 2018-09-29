package com.sun.hotelproject.moudle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.hotelproject.R;
import com.sun.hotelproject.base.BaseActivity;
import com.sun.hotelproject.dao.DaoSimple;
import com.sun.hotelproject.entity.RoomNo;
import com.sun.hotelproject.utils.ActivityManager;
import com.sun.hotelproject.utils.CommonSharedPreferences;
import com.sun.hotelproject.utils.DataTime;
import com.sun.hotelproject.utils.StringUtils;
import com.sun.hotelproject.utils.Tip;
import com.szxb.smart.pos.jni_interface.printer;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import K720_Package.K720_Serial;
import butterknife.BindView;
import butterknife.OnClick;

import static com.sun.hotelproject.moudle.MainActivity.MacAddr;

/**
 * @author  sun 2018/3/12
 * 支付成功界面
 */
public class PaySussecsActivity extends BaseActivity {

    @BindView(R.id.success)
    TextView success;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.relative1)
    RelativeLayout relative1;
    @BindView(R.id.relative2)
    RelativeLayout relative2;
    @BindView(R.id.sp_tv8)
    TextView sp_tv8;
    @BindView(R.id.sp4_content3)TextView sp4_content3;
    @BindView(R.id.linear_sp1)LinearLayout linear_sp1;
    @BindView(R.id.linear_sp2)LinearLayout linear_sp2;
    @BindView(R.id.linear_sp3)LinearLayout linear_sp3;
    @BindView(R.id.linear_sp4)LinearLayout linear_sp4;
    @BindView(R.id.sp4_img8)ImageView sp4_img8;
    @BindView(R.id.sp4_tv8)TextView sp4_tv8;
    @BindView(R.id.sp3_layout3)LinearLayout sp3_layout3;
    @BindView(R.id.sp2_tv7)TextView sp2_tv7;
    @BindView(R.id.sp3_tv4)TextView sp3_tv4;
    @BindView(R.id.sp3_content3)TextView sp3_content3;
    @BindView(R.id.sp_img8)ImageView sp_img8;
    @BindView(R.id.sp2_img7)ImageView sp2_img7;
    @BindView(R.id.sp3_img4)ImageView sp3_img4;
    @BindView(R.id.sp3_img_layout)LinearLayout sp3_img_layout;
    //@BindView(R.id.fial) Button fial;
    @BindView(R.id.toolbarBack)Button toolbarBack;
    @BindView(R.id.fial_layout)RelativeLayout fail_layout;
    @BindView(R.id.fial_content)TextView fail_content;
    @BindView(R.id.fial_bt)Button fail_bt;
    private static final String TAG = "PaySussecsActivity";
    private String roomNum;
    private String k;
    private DaoSimple daoSimple;
    private RoomNo roomNo;
    private String  querytype;
    private String flags;//是否有消费的标志
    private String beginTime,endTime;
    private String name;
    private String state;
    private int toolTime = 30;
    private String price;
    private String orderId;
    private String payway;
    printer p;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){};
    @Override
    protected int layoutID() {
        return R.layout.activity_pay_success;
    }
    @Override
    protected void initData() {
        isRuning = false;
        toolbarBack.setText("返回("+toolTime+"s)");
        ActivityManager.getInstance().addActivity(this);
        fail_layout.setVisibility(View.GONE);
        relative2.setVisibility(View.GONE);
        daoSimple = new DaoSimple(this);
        roomNum = (String) CommonSharedPreferences.get("roomNum", "");
        beginTime = (String) CommonSharedPreferences.get("beginTime", "");
        endTime = (String) CommonSharedPreferences.get("endTime", "");
        k = getIntent().getStringExtra("k");
        name = getIntent().getStringExtra("name");
        price = getIntent().getStringExtra("price");
        orderId = getIntent().getStringExtra("orderId");
        payway = getIntent().getStringExtra("payway");
        switch (k) {
            case "1":
            case "999":
                state = "入住  Check in";
                linear_sp1.setVisibility(View.VISIBLE);
                StringUtils.setCorlor(sp_tv8,sp_img8);
                relative1.setVisibility(View.VISIBLE);
                tv1.setText("");
                tv2.setText("");

                moveCard();
                println();

                break;
            case "2":
                linear_sp2.setVisibility(View.VISIBLE);
//                sp_img7.setVisibility(View.VISIBLE);
//                sp_tv7.setTextColor(getResources().getColor(R.color.Swrite));
//                sp_tv7.setBackgroundResource(R.drawable.oval_shape);

                querytype = getIntent().getStringExtra("querytype");
                if (querytype.equals("3")) {
                    relative1.setVisibility(View.VISIBLE);
                    getCard();
                } else {
                    relative1.setVisibility(View.VISIBLE);
                }

                break;
            case "3":
                state = "退房  Check out";
                relative1.setVisibility(View.GONE);
                relative2.setVisibility(View.VISIBLE);
                linear_sp3.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);
                flags =getIntent().getStringExtra("flag");
                if (flags.equals("0")){
                    sp3_layout3.setVisibility(View.GONE);
                    sp3_img_layout.setVisibility(View.GONE);
                    sp3_tv4.setText("3");
                    sp3_content3.setVisibility(View.GONE);
                    StringUtils.setCorlor(sp3_tv4,sp3_img4);
                }else {
                    StringUtils.setCorlor(sp3_tv4,sp3_img4);
                }
                success.setText("退卡成功");
                reTrieve();
                println();
                break;
            case "4":
                state = "预定入住  Reserve";
                tv1.setText("正在办理入住....");
                tv2.setText("");
                linear_sp4.setVisibility(View.VISIBLE);
                querytype = getIntent().getStringExtra("querytype");
                StringUtils.setCorlor(sp4_tv8,sp4_img8,sp4_content3,querytype);
                relative1.setVisibility(View.VISIBLE);
                tv1.setText("");
                tv2.setText("");
                moveCard();
                println();
                break;
            default:
                break;
        }
        handler.post(task);

    }
    private Runnable task =new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            if (toolTime>0 ){
                toolTime--;
                handler.postDelayed(this,1000);
                toolbarBack.setText("返回("+toolTime+"s)");
            }else {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        }
    };

    @OnClick({R.id.toolbarBack,R.id.fial_bt})
   protected void OnClick(View v){
        switch (v.getId()) {
            case R.id.fial_bt:
                toolTime = 30;
                handler.post(task);
                seachCard();
                fail_layout.setVisibility(View.GONE);
            break;
            case R.id.toolbarBack:
                startActivity(new Intent(PaySussecsActivity.this,MainActivity.class));
                finish();
                break;
                default:
                    break;
        }
    }
      /**
     * 回收到卡箱
     */
    private void reTrieve(){
        int nRet;
        byte[] SendBuf=new byte[3];
        String[] RecordInfo=new String[2];
        SendBuf[0] = 0x44;
        SendBuf[1] = 0x42;
        nRet = K720_Serial.K720_SendCmd(MacAddr, SendBuf, 2, RecordInfo);
        if(nRet == 0)
            Tip.show(getApplicationContext(),"卡片回收成功",true);
        else
            Tip.show(getApplicationContext(),"回收到卡箱命令执行失败",false);

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(task);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonSharedPreferences.put("beginTime","");
        CommonSharedPreferences.put("endTime","");
        CommonSharedPreferences.put("beginTime1","");
        CommonSharedPreferences.put("endTime1","");
        CommonSharedPreferences.put("content","");
        handler.removeCallbacks(task);
    }

//    /**
//     * 回收到卡箱
//     */
//    private void reTrieve2(){
//        int nRet;
//        byte[] SendBuf=new byte[3];
//        String[] RecordInfo=new String[2];
//        SendBuf[0] = 0x44;
//        SendBuf[1] = 0x42;
//        nRet = K720_Serial.K720_SendCmd(MacAddr, SendBuf, 2, RecordInfo);
//        if(nRet == 0) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            DisConnect();
//
//        }
//        else
//            Tip.show(getApplicationContext(),"回收到卡箱命令执行失败",false);
//    }


    /**
     * 写卡
     */
    void wridtCard(){
        try {
            String cardNumber="100100100001"+roomNum;
            byte [] wrbuf=cardNumber.getBytes();
            int nRet;
            String[] RecordInfo=new String[2];
            nRet=K720_Serial.K720_S50WriteBlock(MacAddr,(byte)0x02,(byte)0x02,wrbuf,RecordInfo);
            if(nRet == 0){
                String cardNum= DataTime.bytesToHexString(wrbuf);
                String card_No=DataTime.hexStr2Str(cardNum);

                Log.e(TAG, "wridtCard: 写卡命令执行成功" );
                Log.e("TAG", "wridtCard: "+card_No );
                getCard();
            }
            else{
                Log.e(TAG, "wridtCard: 写卡命令执行失败");
                Tip.show(getApplicationContext(),"写卡命令执行失败",false);
            }
        }catch (Exception e){
            Tip.show(this,"出卡失败,请联系管理员",false);
        }


    }

    /**
     * 卡密钥验证
     */
    void checkCard(){
        int nRet;
        byte[] key = { (byte) 0xff, (byte) 0xff,
                (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0xff};
        String[] RecordInfo=new String[2];
        nRet=K720_Serial.K720_S50LoadSecKey(MacAddr,(byte)0x02,(byte)0x30,key,RecordInfo);
        if(nRet == 0)
        {
            Log.e(TAG, "checkCard: 密钥验证成功" );
            wridtCard();
        }
        else{
//            reTrieve();
//            DisConnect();
            handler.removeCallbacks(task);
            fail_content.setText("密钥验证失败"+nRet);
            fail_layout.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 移动到读卡位置
     */
    private void moveCard(){
        tv1.setText("正在出卡中......");
        int nRet;
        byte[] SendBuf=new byte[3];
        String[] RecordInfo=new String[2];
        SendBuf[0] = 0x46;
        SendBuf[1] = 0x43;
        SendBuf[2] = 0x37;
        nRet = K720_Serial.K720_SendCmd(MacAddr, SendBuf, 3, RecordInfo);
        if(nRet == 0){
            Log.e(TAG, "moveCard: 移动卡片命令成功" );
            seachCard();
        }
        else {
            fail_content.setText("卡片移动到读卡位置失败");
            fail_layout.setVisibility(View.VISIBLE);
            Executors.newScheduledThreadPool(1).schedule(new Runnable() {
                @Override
                public void run() {
                    moveCard();
                }
            },5, TimeUnit.SECONDS);

            Log.e(TAG, "moveCard: 卡片移动到读卡位置失败");
        }
    }

    /**
     * 寻卡
     */
    private void seachCard(){
        // TODO Auto-generated method stub
        int nRet;
        String[] RecordInfo=new String[2];
        nRet = K720_Serial.K720_S50DetectCard(MacAddr, RecordInfo);
        if(nRet == 0) {
            Log.e(TAG, "seachCard: S50寻卡成功");
            checkCard();
        }
        else {
            Log.e(TAG, "seachCard: S50寻卡失败");

            fail_content.setText("请检查卡槽是否有卡");
            fail_layout.setVisibility(View.VISIBLE);
            fail_bt.setVisibility(View.GONE);
        }
    }

    /**
     *
     * 出卡
     */
    private void getCard(){
        int nRet;
        byte[] SendBuf=new byte[3];
        String[] RecordInfo=new String[2];
        SendBuf[0] = 0x46;
        SendBuf[1] = 0x43;
        SendBuf[2] = 0x34;
        nRet = K720_Serial.K720_SendCmd(MacAddr, SendBuf, 3, RecordInfo);
        if(nRet == 0){
            Log.e(TAG, "getCard: 出卡成功" );
            tv1.setText("出卡中请稍候...");
            tv2.setText("出卡成功,请取走您的卡片和身份证");
        }
        else{
            Log.e(TAG, "getCard: 出卡失败" );
            fail_content.setText("出卡失败");
            fail_layout.setVisibility(View.VISIBLE);

        }
    }
    private void println(){
        p = new printer();
        int i = p.PrinterOpen();
        int key = p.PrinterStatus();
        if (key == 0){
            Tip.show(getApplicationContext(),"打印机缺纸",false);
           return;
        }
        Log.e(TAG, "initData: 打印纸状态"+key );
        Log.e(TAG, "initData: i---"+i );
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
        p.PrinterOpen();
        byte[] b = new byte[] { (byte) 0x1c, (byte) 0x70, (byte) 0x01,
                (byte) 0x00 };
        int data = p.PrinterWrite(b, 4);
        byte[] one = HexString2Bytes(sb("CHIC"));
        byte[] fir = HexString2Bytes(sb("房间号  Room No"));
        byte[] sec = HexString2Bytes(sb(roomNum.trim()));

        byte[] thr2 = HexString2Bytes(sb("住房状态  Housing status"));
        byte[] fou2 = HexString2Bytes(sb(state.trim()));
        byte[] thr3 = HexString2Bytes(sb("支付金额  Amount of payment"));
        byte[] fou3 = HexString2Bytes(sb(price.trim()));
        byte[] thr1 = HexString2Bytes(sb("入住人姓名  Name of the occupant"));
        byte[] fou1 = HexString2Bytes(sb(name.trim()));
        byte[] thr4 = HexString2Bytes(sb("订单号  Order number"));
        byte[] fou4 = HexString2Bytes(sb(orderId.trim()));
        byte[] thr = HexString2Bytes(sb("入住日期  Arrival Date"));
        byte[] fou = HexString2Bytes(sb(beginTime.trim()));
        byte[] fiv = HexString2Bytes(sb("离店日期  Departure Date"));
        byte[] sev = HexString2Bytes(sb(endTime.trim()));
        byte[] fiv1 = HexString2Bytes(sb("支付方式  Payment method"));
        byte[] sev1 = HexString2Bytes(sb(payway.trim()));
        byte[] six = HexString2Bytes(sb("----------------------------------------"));
       // byte[] six = HexString2Bytes(sb("____________________________________________"));
        byte[] th = HexString2Bytes(sb("打印时间  Print Time "+ DataTime.currentTime()));
        p.PrinterWrite(printer.getCmdEscEN(1), 3);// 加粗
        p.PrinterWrite(printer.getCmdEscAN(1), 3);// 居中
        byte[] a = { (byte) 1, (byte) 2 };
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
        p.PrinterWrite(sec, sec.length);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(printer.getCmdLf(), 1);
//        p.PrinterWrite(printer.getCmdLf(), 1);
//        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(printer.getCmdEscDc4(), 2);// 加宽还原

        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(thr3, thr3.length);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(fou3, fou3.length);

        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(thr2, thr2.length);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(fou2, fou2.length);

        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(thr1, thr1.length);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(fou1, fou1.length);

        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(thr4, thr4.length);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(fou4, fou4.length);

        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(thr, thr.length);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(fou, fou.length);

        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(fiv, fiv.length);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(sev, sev.length);

        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(fiv1, fiv1.length);
        p.PrinterWrite(printer.getCmdLf(), 1);
        p.PrinterWrite(sev1, sev1.length);

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

        byte[] b1 = new byte[] { (byte) 0x1b, (byte) 0x69 };// 切纸

        int lock = p.PrinterWrite(b1, 2);
        System.out.println(lock + "打印完成？0：其他");

        p.PrinterClose();
    }


    public  byte[] HexString2Bytes(String src) {
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

    public  byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0}));
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1}));
        return (byte) (_b0 ^ _b1);
    }

    public  String sb(String content) {
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

    /**
     * 连接
     */
    private void Connect(){
        String strPort = "/dev/ttyS3";
        int re;
        byte i;
        String[] RecordInfo=new String[2];
        int Baudate = 9600;
        re = K720_Serial.K720_CommOpen(strPort);

        if(re==0)
        {
            for(i = 0; i < 16; i++)
            {
                re = K720_Serial.K720_AutoTestMac(i, RecordInfo);
                if(re == 0)
                {
                    MacAddr = i;
                    break;
                }
            }
            if(i == 16 && MacAddr == 0)
            {
                K720_Serial.K720_CommClose();
            }
            Log.e(TAG, "Connect: "+"连接成功" );
        }
    }
    private void DisConnect(){
        int nRet;
        nRet = K720_Serial.K720_CommClose();
        if(nRet == 0)
        {
            MacAddr = 0;
            Log.e(TAG, "DisConnect: "+"断开成功" );
        }
        else{
            Log.e(TAG, "DisConnect: "+"断开失败" );
        }
       // Tip.show(this,"设备断开失败，错误代码为："+K720_Serial.ErrorCode(nRet, 0),false);
    }
}
