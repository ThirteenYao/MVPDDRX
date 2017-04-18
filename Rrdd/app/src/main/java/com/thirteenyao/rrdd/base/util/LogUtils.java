package com.thirteenyao.rrdd.base.util;

import com.orhanobut.logger.Logger;
import com.thirteenyao.rrdd.base.MApplication;

/**
 * Created by ThirteenYao on 2017/4/11.
 * 实现调试打印(MApplication---DEVELOP_DEBUG_MODE)
 */

public class LogUtils {

    static boolean isLog = true;

    //no instance
    private LogUtils() {
        isLog = MApplication.DEVELOP_DEBUG_MODE;
    }


    public static void log(int priority, String tag, String message, Throwable throwable) {
        if (isLog)
            Logger.log(priority, tag, message, throwable);
    }

    public static void d(String message, Object... args) {
        if (isLog)
            Logger.d(message, args);
    }

    public static void d(Object object) {
        if (isLog)
            Logger.d(object);
    }

    public static void e(String message, Object... args) {
        if (isLog)
            Logger.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        if (isLog)
            Logger.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        if (isLog)
            Logger.i(message, args);
    }

    public static void v(String message, Object... args) {
        if (isLog)
            Logger.v(message, args);
    }

    public static void w(String message, Object... args) {
        if (isLog)
            Logger.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        if (isLog)
            Logger.wtf(message, args);
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        if (isLog)
            Logger.json(json);
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml) {
        if (isLog)
            Logger.xml(xml);
    }

}
