package com.thirteenyao.rrdd.moudle.news.bean;

import com.thirteenyao.rrdd.base.bean.BaseModel;

/**
 * Created by ThirteenYao on 2017/4/18.
 */

public class YesterdayBean extends BaseModel {
    /**
     * fl : 微风
     * fx : 无持续风向
     * high : 高温 29℃
     * type : 多云
     * low : 低温 23℃
     * date : 16日星期日
     */

    private String fl;
    private String fx;
    private String high;
    private String type;
    private String low;
    private String date;

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getFx() {
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
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