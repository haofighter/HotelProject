package com.sun.hotelproject.moudle;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.zxing.qrcode.encoder.QRCode;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sun.hotelproject.R;
import com.sun.hotelproject.dao.DaoSimple;
import com.sun.hotelproject.entity.GuestRoom;
import com.sun.hotelproject.entity.HouseTable;
import com.sun.hotelproject.entity.QueryRomm;
import com.sun.hotelproject.utils.DataTime;
import com.sun.hotelproject.utils.HttpUrl;
import com.sun.hotelproject.utils.JsonCallBack;
import com.sun.hotelproject.utils.Tip;
import com.sun.hotelproject.view.FinderViewStyle;
import com.sun.hotelproject.view.MyVideoView;
import com.zxing.support.library.QRCodeSupport;


import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



public class TestActivity extends AppCompatActivity implements QRCodeSupport.OnScanResultListener,QRCodeSupport.OnTestScanRectListener{

    private SurfaceView mSurfaceView;
    private FinderViewStyle mFinderView;
    private QRCodeSupport mQRCodeSupport;
    private ImageView mTestView;

    private static final int SCAN_W = 800;
    private static final int SCAN_H = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();


        QRCodeSupport.Builder builder = new QRCodeSupport.Builder();

        //demo 设置居中显示,宽500,高500
        builder.setScanRect((width - SCAN_W)/2, (height - SCAN_H)/2, SCAN_W, SCAN_H);
        mFinderView = (FinderViewStyle) findViewById(R.id.capture_viewfinder_view);
        mSurfaceView = (SurfaceView) findViewById(R.id.sufaceview);
        mTestView = (ImageView) findViewById(R.id.test);
        mQRCodeSupport = new QRCodeSupport(builder,mSurfaceView, mFinderView);
        mQRCodeSupport.setScanResultListener(this);
        mQRCodeSupport.setOnTestScanRectListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mQRCodeSupport.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mQRCodeSupport.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onScanResult(String notNullResult,byte[] resultBytes) {
        Intent intent = new Intent();
        intent.putExtra("qRcode", notNullResult);
        intent.putExtra("resultByte", resultBytes);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
    @Override
    public void onScanRect(byte[] rectByte) {
        Bitmap barcode = BitmapFactory.decodeByteArray(rectByte, 0, rectByte.length, null);
        barcode = barcode.copy(Bitmap.Config.RGB_565, true);
        mTestView.setImageBitmap(barcode);
    }


}
