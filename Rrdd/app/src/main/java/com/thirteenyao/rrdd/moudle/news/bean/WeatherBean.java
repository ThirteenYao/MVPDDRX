package com.thirteenyao.rrdd.moudle.news.bean;

import com.thirteenyao.rrdd.base.bean.BaseModel;

import java.util.List;

/**
 * Created by ThirteenYao on 2017/4/17.
 */

public class WeatherBean extends BaseModel {

    /**
     * wendu : 28
     * ganmao : 各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。
     * forecast : [{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 30℃","type":"多云","low":"低温 23℃","date":"17日星期一"},{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 30℃","type":"多云","low":"低温 23℃","date":"18日星期二"},{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 30℃","type":"阵雨","low":"低温 24℃","date":"19日星期三"},{"fengxiang":"无持续风向","fengli":"微风级","high":"高温 29℃","type":"雷阵雨","low":"低温 19℃","date":"20日星期四"},{"fengxiang":"东北风","fengli":"3-4级","high":"高温 24℃","type":"雷阵雨","low":"低温 20℃","date":"21日星期五"}]
     * yesterday : {"fl":"微风","fx":"无持续风向","high":"高温 29℃","type":"多云","low":"低温 23℃","date":"16日星期日"}
     * aqi : 45
     * city : 深圳
     */

    private String wendu;
    private String ganmao;
    private YesterdayBean yesterday;
    private String aqi;
    private String city;
    private List<ForecastBean> forecast;

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getGanmao() {
        return ganmao;
    }

    public void setGanmao(String ganmao) {
        this.ganmao = ganmao;
    }

    public YesterdayBean getYesterday() {
        return yesterday;
    }

    public void setYesterday(YesterdayBean yesterday) {
        this.yesterday = yesterday;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<ForecastBean> getForecast() {
        return forecast;
    }

    public void setForecast(List<ForecastBean> forecast) {
        this.forecast = forecast;
    }



}
