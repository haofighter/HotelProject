package com.sun.hotelproject.moudle.net;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sun.hotelproject.app.App;
import com.sun.hotelproject.app.BackCall;
import com.sun.hotelproject.entity.Draw;
import com.sun.hotelproject.entity.GuestRoom;
import com.sun.hotelproject.utils.HttpUrl;
import com.sun.hotelproject.utils.JsonCallBack;
import com.sun.hotelproject.utils.Tip;

public class HotelHttp {
    public  static void cancalCheckRoom(String bpmsno, String rpsmno, String mchid, final BackCall call){
        OkGo.<Draw>post(HttpUrl.CANCALCHECKROOM)
                .tag(App.getInstance())
                .params("bpmsno", bpmsno)
                .params("rpsmno", rpsmno)
                .params("mchid", mchid)
                .execute(new JsonCallBack<Draw>(Draw.class) {
                    @Override
                    public void onError(Response<Draw> response) {
                        super.onError(response);
                        Tip.show(App.getInstance(), "服务器连接异常", false);
                    }

                    @Override
                    public void onSuccess(Response<Draw> response) {
                        super.onSuccess(response);
                        Log.i("酒店","解锁成功"+response.body().toString());
                        if (mResponse.getRescode().equals("0000")) {
                            Log.i("酒店","解锁成功");
                            call.deal(response);
                        }else{
                            Tip.show(App.getInstance(),"验证码错误",false);
                        }
                    }
                });
    }


    /**
     * 解锁房间
     */
    public static void cancalLockRoom(String k,GuestRoom.Bean gBean, String mchid){
        if(k.equals("1")||k.equals("999")) {
            HotelHttp.cancalCheckRoom("", gBean.getRpmsno(), mchid, new BackCall() {
                @Override
                public void deal(String s) {

                }

                @Override
                public void deal(Object s) {

                }
            });
        }
    }
}
