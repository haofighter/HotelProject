package com.net;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/7/26.
 */

public abstract class FileSubscribe<T> extends Subscriber<T> {
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {

    }
    public void onLoading(long total, long progress) {
    }


}
