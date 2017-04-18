package com.thirteenyao.rrdd.base;

import android.app.Application;
import android.content.Context;

import com.thirteenyao.rrdd.base.net.HttpManager;

/**
 * Created by ThirteenYao on 2017/4/6.
 */

public class MApplication extends Application{
    /** 是否开启调试模式 */
    public static final boolean DEVELOP_DEBUG_MODE = true;
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext=this;

        //初始化网络模组
        HttpManager.initCertificates(getApplicationContext());
    }
    public static Context getContext(){
        return mContext;
    }

}
