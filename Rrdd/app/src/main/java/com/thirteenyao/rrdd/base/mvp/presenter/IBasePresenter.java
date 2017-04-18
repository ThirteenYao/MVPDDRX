package com.thirteenyao.rrdd.base.mvp.presenter;

import android.content.Context;

/**
 * Created by ThirteenYao on 2017/4/6.
 * 代理的基类
 */

public interface IBasePresenter<V> {

    void attachView(V v, Context context);
    void detachView();
}
