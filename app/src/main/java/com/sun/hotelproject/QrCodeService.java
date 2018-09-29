package com.sun.hotelproject;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.sun.hotelproject.app.App;
import com.szxb.smart.pos.jni_interface.Barcode;
import com.szxb.smart.pos.jni_interface.Com4052;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by a'su's on 2018/6/6.
 */
@SuppressLint("Registered")
public class QrCodeService extends Service {

    private ScheduledExecutorService scheduler;
    private String qrCode = "";
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                qrCode = (String) msg.obj;
                Logger.d("qrCode-->"+qrCode);
                Toast.makeText(App.getInstance(), qrCode, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                connect();
            }
        },0*1000,10*1000, TimeUnit.MILLISECONDS);
    }


    private void connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                    String qrCode = barcodeScan();
                    Message msg = new Message();
                    msg.obj = qrCode;
                    msg.what = 0;
                    handler.sendMessage(msg);
            }
        }).start();
    }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        scheduler.shutdown();
        QrCodeService.this.stopSelf();
    }
}
