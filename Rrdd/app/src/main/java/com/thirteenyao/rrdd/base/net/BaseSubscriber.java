package com.thirteenyao.rrdd.base.net;


import com.thirteenyao.rrdd.base.net.bean.BaseResponseModel;
import com.thirteenyao.rrdd.base.net.bean.RequestCallback;
import com.thirteenyao.rrdd.base.net.bean.RootResponseModel;
import com.thirteenyao.rrdd.base.util.LogUtils;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

/**
 * Created by ThirteenYao on 2017/4/10.
 * 我所在公司的接口数据接口（BaseResponseModel）
 * 在一个正确运行的事件序列中, onCompleted() 和 onError() 有且只有一个，并且是事件序列中的最后一个。
 * 需要注意的是，onCompleted() 和 onError() 二者也是互斥的，即在队列中调用了其中一个，就不应该再调用另一个。
 */

public abstract class BaseSubscriber<T extends BaseResponseModel> extends Subscriber<T> {


    @Override
    public void onError(Throwable t) {
        LogUtils.e("request failer.\ndetail: " + t.toString());
        if (t instanceof SocketTimeoutException) {
            onNetErr();
        } else if (t instanceof ConnectException) {
            onNetErr();
        } else if (t instanceof UnknownHostException) {
            onNetErr();
        } else if (t instanceof RuntimeException) {
            onSysErr();
        } else if (t instanceof SocketException) {
            onNetErr();
        } else {
            onSysErr();
        }

    }

    @Override
    public void onNext(T t) {
        if (t.status.equals("1000")) {    //我所在公司的接口数据结构（BaseResponseModel）
            onSuc(t);
        } else {
            onFail(t);
        }
    }

    public abstract void onSysErr();

    public abstract void onNetErr();

    public abstract void onFail(RootResponseModel rootResponseModel);

    public abstract void onSuc(T t);

}
