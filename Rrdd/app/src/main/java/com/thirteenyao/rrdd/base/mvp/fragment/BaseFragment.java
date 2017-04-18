package com.thirteenyao.rrdd.base.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.thirteenyao.rrdd.base.fragment.RootFragment;
import com.thirteenyao.rrdd.base.mvp.presenter.IBasePresenter;
import com.thirteenyao.rrdd.base.mvp.view.IBaseView;

/**
 * Created by ThirteenYao on 2017/4/13.
 */

public abstract class BaseFragment<V extends IBaseView,P extends IBasePresenter> extends RootFragment {
    /**
     * 将代理类通用行为抽出来
     */
    protected P mPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPresenter();
        mPresenter.attachView((V) this, activity);
    }

    protected abstract void createPresenter();

    protected void setPresenter(P presenter) {
        this.mPresenter = presenter;
    }

    protected P getPresenter() {
        return mPresenter;
    }


    @Override
    public void onDestroy() {
        if (mPresenter != null)
            mPresenter.detachView();
        super.onDestroy();
    }
}
