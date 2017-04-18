package com.thirteenyao.rrdd.moudle.news.presenter;

import com.thirteenyao.rrdd.base.mvp.presenter.IBasePresenter;
import com.thirteenyao.rrdd.base.mvp.presenter.impl.BasePresenterImpl;
import com.thirteenyao.rrdd.base.mvp.view.IBaseView;

/**
 * Created by ThirteenYao on 2017/4/18.
 */

public abstract class WeatherPresenter<V extends IBaseView> extends BasePresenterImpl<V> {
    public abstract void getWeather(String city);
}
