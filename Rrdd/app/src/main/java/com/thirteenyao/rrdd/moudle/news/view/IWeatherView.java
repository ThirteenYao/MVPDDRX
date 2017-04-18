package com.thirteenyao.rrdd.moudle.news.view;

import com.thirteenyao.rrdd.base.mvp.view.IBaseView;
import com.thirteenyao.rrdd.moudle.news.bean.WeatherBean;

/**
 * Created by ThirteenYao on 2017/4/18.
 */

public interface IWeatherView extends IBaseView {
    void toast(WeatherBean bean);
}
