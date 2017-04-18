package com.thirteenyao.rrdd.moudle.news.bean;

import com.thirteenyao.rrdd.base.bean.BaseModel;

/**
 * Created by ThirteenYao on 2017/4/18.
 */

public class ForecastBean extends BaseModel {
    /**
     * fengxiang : 无持续风向
     * fengli : 微风级
     * high : 高温 30℃
     * type : 多云
     * low : 低温 23℃
     * date : 17日星期一
     */
    private String fengxiang;
    private String fengli;
    private String high;
    private String type;
    private String low;
    private String date;

    public String getFengxiang() {
        return fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getFengli() {
        return fengli;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}


