package com.thirteenyao.rrdd.moudle.news.model.impl;

import com.thirteenyao.rrdd.base.net.BaseSubscriber;
import com.thirteenyao.rrdd.base.net.HttpManager;
import com.thirteenyao.rrdd.base.net.IBaseListener;
import com.thirteenyao.rrdd.base.net.bean.BaseResponseModel;
import com.thirteenyao.rrdd.base.net.bean.RootResponseModel;
import com.thirteenyao.rrdd.moudle.news.bean.WeatherBean;
import com.thirteenyao.rrdd.moudle.news.model.TestModel;
import com.thirteenyao.rrdd.moudle.news.service.WeatherService;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ThirteenYao on 2017/4/17.
 */

public class TestModelImpl implements TestModel {


    @Override
    public Subscription test(String city,final TestListener listener) {
        return HttpManager.getInstance().req(WeatherService.class, "http://wthrcdn.etouch.cn/")
                .test(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponseModel<WeatherBean>>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onSuc(BaseResponseModel<WeatherBean> weatherBeanBaseResponseModel) {
                        listener.onSuc(weatherBeanBaseResponseModel.data);
                    }
                    @Override
                    public void onFail(RootResponseModel rootResponseModel) {
                        listener.onFail(rootResponseModel);

                    }

                    @Override
                    public void onNetErr() {
                        listener.onNerErr();

                    }

                    @Override
                    public void onSysErr() {
                        listener.onSysErr();

                    }


                });


    }

    public interface TestListener extends IBaseListener{
        void onSuc(WeatherBean bean);
        void onFail(RootResponseModel rootResponseModel);
    }

}
