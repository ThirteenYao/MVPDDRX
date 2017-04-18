package com.thirteenyao.rrdd.base.net.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.thirteenyao.rrdd.base.bean.BaseModel;


/**
 * Created by gray on 2016/7/18.
 */
public class RootResponseModel extends BaseModel {
    /**
     * 状态码
     */
    public String status = "";
    /**
     * 服务器响应消息
     */
    @JSONField(name="desc")
    public String message = "";
    /**
     * 当前响应时间
     */
    public String current;

    /**L
     * 调试编号
     */
    public String id = "";

    @Override
    public String toString() {
        return "RootResponseModel{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data='" + '\'' +
                ", current_datetime='" + current + '\'' +
                '}';
    }
}
