package com.sun.hotelproject.rx;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

/**
 * 作者：Tangren on 2018-01-10
 * 包名：com.czgj.Util.rx
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class RetryWithDelay implements
        Func1<Observable<? extends Throwable>, Observable<?>> {

    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;

    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> attempts) {
        return attempts
                .flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        if (++retryCount <= maxRetries) {
                            Log.d("RetryWithDelay",
                                    "log(RetryWithDelay.java:225)" + "get error, it will try after " + retryDelayMillis
                                            + " millisecond, retry count " + retryCount);
                            return Observable.timer(retryDelayMillis,
                                    TimeUnit.MILLISECONDS);
                        }
                        return Observable.error(throwable);
                    }
                });
    }
}
