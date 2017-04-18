package com.thirteenyao.rrdd.base.mvp.presenter.impl;

import android.content.Context;
import android.view.View;

import com.thirteenyao.rrdd.base.mvp.presenter.IBasePresenter;
import com.thirteenyao.rrdd.base.mvp.view.IBaseView;
import com.thirteenyao.rrdd.base.net.bean.RequestCallback;
import com.thirteenyao.rrdd.base.net.bean.RootResponseModel;
import com.thirteenyao.rrdd.base.widget.AbnormalLayout;

import retrofit2.Call;
import retrofit2.Response;
import rx.Subscription;

/**
 * Created by ThirteenYao on 2017/4/7.
 * 代理的基类实现
 */

public abstract class BasePresenterImpl<V extends IBaseView> implements IBasePresenter<V> {

    protected Subscription mSubscription;
    public Context context;
    public V v;

    @Override
    public void attachView(V v, Context context) {
        this.v=v;
        this.context=context;
    }

    @Override
    public void detachView() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        v = null;

    }
}
