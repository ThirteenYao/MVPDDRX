package com.thirteenyao.rrdd.base.net.bean;

import retrofit2.Response;

/**
 * Created by ThirteenYao on 2017/4/7.
 * 网络请求监听基类
 */

public interface RequestCallback<T> {


    /**
     * 请求之前调用 show Loading
     */
    void beforeRequest();

    /**
     * 请求完成调用  dismiss Loading
     */
    void requestComplete();

    /**
     * 请求成功调用
     *
     * @param data 数据
     */
    void onSuc(T data);

    /**
     * 请求失败
     *
     * @param responseModel
     */
    void onFail(RootResponseModel responseModel);

    /**
     * 系统错误
     */
    void onSysErr();

    /**
     * 网络错误
     */
    void onNetErr();


}
