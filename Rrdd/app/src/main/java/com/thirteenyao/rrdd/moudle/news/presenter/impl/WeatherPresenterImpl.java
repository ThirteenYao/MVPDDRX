package com.thirteenyao.rrdd.moudle.news.presenter.impl;

import android.widget.Toast;

import com.thirteenyao.rrdd.base.net.bean.RootResponseModel;
import com.thirteenyao.rrdd.base.widget.AbnormalLayout;
import com.thirteenyao.rrdd.moudle.news.bean.WeatherBean;
import com.thirteenyao.rrdd.moudle.news.model.TestModel;
import com.thirteenyao.rrdd.moudle.news.model.impl.TestModelImpl;
import com.thirteenyao.rrdd.moudle.news.presenter.WeatherPresenter;
import com.thirteenyao.rrdd.moudle.news.view.IWeatherView;

/**
 * Created by ThirteenYao on 2017/4/18.
 */

public class WeatherPresenterImpl extends WeatherPresenter<IWeatherView> implements TestModelImpl.TestListener {

    TestModel model;

    String city;

    public WeatherPresenterImpl() {
        model=new TestModelImpl();
    }

    @Override
    public void getWeather(String city) {
        this.city=city;
        v.showProgress();
        model.test(city,this);

    }
    @Override
    public void onSuc(WeatherBean bean) {
        v.hideErrPage();
        v.toast(bean);


    }

    @Override
    public void onFail(RootResponseModel rootResponseModel) {
        v.hideErrPage();
        Toast.makeText(context,rootResponseModel.toString(),Toast.LENGTH_LONG).show();

    }

    @Override
    public void onSysErr() {
        v.hideErrPage();
        v.showSysErrLayout(new AbnormalLayout.OnRetryListener() {
            @Override
            public void onRetry() {
                getWeather(city);
            }
        });

    }

    @Override
    public void onNerErr() {
        v.hideErrPage();
        v.showNetErrorLayout(new AbnormalLayout.OnRetryListener() {
            @Override
            public void onRetry() {
                getWeather(city);
            }
        });

    }



}
