package com.thirteenyao.rrdd.base.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.thirteenyao.rrdd.base.activity.RootActivity;
import com.thirteenyao.rrdd.base.mvp.presenter.IBasePresenter;
import com.thirteenyao.rrdd.base.mvp.presenter.impl.BasePresenterImpl;
import com.thirteenyao.rrdd.base.mvp.view.IBaseView;

/**
 * Created by ThirteenYao on 2017/4/7.
 */

public abstract class BaseActivity<V extends IBaseView,P extends BasePresenterImpl<V>> extends RootActivity {

    /**
     * 将代理类通用行为抽出来
     */
    protected P mPresenter;

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPresenter();
        mPresenter.attachView((V)this,activity);
    }

    protected abstract void createPresenter();

    protected void setPresenter(P presenter) {
        this.mPresenter = presenter;
    }

    protected P getPresenter() {
        return mPresenter;
    }


    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.detachView();
        super.onDestroy();
    }
}
