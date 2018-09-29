package com.sun.hotelproject.moudle.net;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.net.md5.Constant;
import com.sun.hotelproject.R;
import com.sun.hotelproject.app.App;
import com.sun.hotelproject.app.BackCall;
import com.sun.hotelproject.app.Constants;
import com.sun.hotelproject.entity.FaceRecognition;
import com.sun.hotelproject.entity.SeqNo;
import com.sun.hotelproject.moudle.id_card.IDCardInfo;
import com.sun.hotelproject.utils.HttpUrl;
import com.sun.hotelproject.utils.JsonCallBack;
import com.sun.hotelproject.utils.Tip;

import java.io.File;

import static com.lzy.okgo.model.Progress.TAG;

public class FaceHttp {
   private FaceHttp(){}
  private static FaceHttp faceHttp;
   public static FaceHttp getInstance(){
       if(faceHttp==null){
           faceHttp=new FaceHttp();
       }
       return faceHttp;
   }

    /**
     * 生成流水号
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public  void cofimface(final IDCardInfo idCardInfo, final String path, final BackCall backCall) {
        OkGo.<SeqNo>get(HttpUrl.SEQNO)
                .tag(App.getInstance())
                .execute(new JsonCallBack<SeqNo>(SeqNo.class) {
                    @Override
                    public void onSuccess(Response<SeqNo> response) {
                        super.onSuccess(response);
                        if (response.body().getRescode().equals("00") && response.body().getRetcode().equals("0")) {
                            Post(response.body().getSeq_no(), response.body().getAccount(), path, idCardInfo,backCall);
                        } else {
                            backCall.deal(Constants.ERROR);
                            Tip.show(App.getInstance(), response.body().getRetmsg(), false);
                        }
                    }

                    @Override
                    public void onError(Response<SeqNo> response) {
                        backCall.deal(Constants.ERROR);
                        Tip.show(App.getInstance(), "服务器连接异常", false);
                        super.onError(response);
                    }
                });
    }

    /**
     //     * 人脸识别
     //     *
     //     * @param seq_no  流水号
     //     * @param account 账号
     //     */
    private  void Post(String seq_no, String account, String path, final IDCardInfo idCardInfo, final BackCall backCall) {
        OkGo.<FaceRecognition>post(HttpUrl.FACERECOQNITION)
                .tag(App.getInstance())
                .retryCount(3)//超时重连次数
                .cacheTime(3000)//缓存过期时间
                .params("name", idCardInfo.getStrName())
                .params("creid_no", idCardInfo.getStrIdCode().trim())
                .params("account", account)
                .params("type", 8)
                .params("seq_no", seq_no)
                .params("photo_check_live", 0) //0防翻拍，1关闭防翻拍
                .isMultipart(true)//强制使用multipart/form-data 表单上传
                .params("image_fn", new File(path))
                .execute(new JsonCallBack<FaceRecognition>(FaceRecognition.class) {
                    @Override
                    public void onSuccess(Response<FaceRecognition> response) {
                        super.onSuccess(response);

                        Log.d(TAG, "人脸识别onSuccess() called with: response = [" + response.body() + "]");
//                        if (Constants.isTest) {
//                            FaceRecognition response1 = response.body();
//                            response1.setStatus("1");
//                            response1.setRetcode("0");
//                            response.setBody(response1);
//                        }
                        if (response.body().getRescode().equals("00") && response.body().getRetcode().equals("0")) {
                            if (response.body().getStatus().equals("1")) {//认证成功
                                backCall.deal(Constants.SUCCESS);
//                                play(R.raw.shibei_success, getApplicationContext());
//                                Intent intent = new Intent(IdentificationActivity.this, PhoneMsg.class);
//                                intent.putExtra("idCard", idCardInfo);
//                                intent.putExtra("k", k);
//                                intent.putExtra("locksign", locksign);
//                                intent.putExtra("bean", gBean);
//                                intent.putExtra("name", idCardInfo.getStrName());
//                                startActivity(intent);
//                                finish();
                            } else {
                                backCall.deal(Constants.ERROR);
//                                play(R.raw.shibei_fail, getApplicationContext());
                            }
                        } else {
                            backCall.deal(Constants.ERROR);
//                            play(R.raw.shibei_fail, getApplicationContext());
                        }
                    }

                    @Override
                    public void onError(Response<FaceRecognition> response) {
                        Log.d(TAG, "人脸识别onSuccess() called with: response = [" + response.body() + "]");
                        Tip.show(App.getInstance(), "服务器连接异常", false);
                    }
                });
    }
}
