package com.sun.hotelproject.moudle.net;

import android.content.Context;
import android.content.Intent;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.net.md5.Constant;
import com.sun.hotelproject.app.App;
import com.sun.hotelproject.app.BackCall;
import com.sun.hotelproject.entity.Draw;
import com.sun.hotelproject.utils.HttpUrl;
import com.sun.hotelproject.utils.JsonCallBack;
import com.sun.hotelproject.utils.Tip;

public class CodeInfo {

    /**
     * 验证短信码
     */
    public static void smsCheck(Context constant, String phone, String dxcode, final BackCall call) {
        OkGo.<Draw>post(HttpUrl.SMSCHECK)
                .tag(constant)
                .params("phone", phone)
                .params("dxcode", dxcode)
                .execute(new JsonCallBack<Draw>(Draw.class) {
                    @Override
                    public void onError(Response<Draw> response) {
                        super.onError(response);
                        Tip.show(App.getInstance(), "服务器连接异常", false);
                    }

                    @Override
                    public void onSuccess(Response<Draw> response) {
                        super.onSuccess(response);
                        if (mResponse.getRescode().equals("0000")) {
                            call.deal(response);
                        }else{
                            Tip.show(App.getInstance(),"验证码错误",false);
                        }
                    }
                });
    }


    /**
     * 获取验证码
     */
    public static void smsSend(Context constant,String phone, final BackCall backCall) {
        OkGo.<Draw>post(HttpUrl.SMSSEND)
                .tag(constant)
                .params("phone", phone)
                .params("appcode", "")
                .execute(new JsonCallBack<Draw>(Draw.class) {
                    @Override
                    public void onError(Response<Draw> response) {
                        super.onError(response);
                        Tip.show(App.getInstance(), "服务器连接异常", false);
                    }

                    @Override
                    public void onSuccess(Response<Draw> response) {
                        super.onSuccess(response);
                        if (mResponse.getRescode().equals("0000")) {
                            backCall.deal(response);
                        }
                    }
                });
    }
}
