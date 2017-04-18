package com.thirteenyao.rrdd.base.net.bean;

/**
 * Created by gray on 2016/5/16.
 */
public class BaseResponseModel<T> extends RootResponseModel {

    /**
     * 数据对象
     */
    public T data;

    public BaseResponseModel(String status) {
        this.status = status;
    }

    public BaseResponseModel() {
    }

    @Override
    public String toString() {
        return "BaseResponseModel{" +
                "data=" + data.toString() +
                '}';
    }
}
