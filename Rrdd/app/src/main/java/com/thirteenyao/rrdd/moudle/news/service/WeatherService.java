package com.thirteenyao.rrdd.moudle.news.service;

import com.thirteenyao.rrdd.base.net.bean.BaseResponseModel;
import com.thirteenyao.rrdd.moudle.news.bean.WeatherBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ThirteenYao on 2017/4/17.
 */

public interface WeatherService  {
    @GET("weather_mini")
    Observable<BaseResponseModel<WeatherBean>> test(@Query("city")String city);
}
