package com.thirteenyao.rrdd.base.bean;

/**
 * Created by gray on 2016/8/18.
 * 公共参数
 */
public interface BaseConfig {

    /* ========== 项目基本信息参数 ========== */

    /**
     * 语言
     */
    String LANGUAGE = "zh_CN";
    /**
     * 平台
     */
    String PLATFORM = "Android";
    /**
     * 默认rolecode
     */
    String ROLECODE = "_USER";

    /**
     * 请求加密秘钥
     */
    String ENCRYPTKEY = "exGlmXca3CmHdAjXrnctER21Hes6K3xT";
    String CHARSET = "UTF-8";
    String APP_CODE = "30005";

    /* ========== 服务器参数 ========== */

    /**
     * 服务器根路径
     */
    String ROOT_PATH = "https://www.chekunet.com/develccar/";

    /**
     * 接口根路径
     */
    String ROOT_SERVER = ROOT_PATH + "api/";

    /**
     * 默认超时时间
     */
    int DEFAULT_NET_OUTDATE = 30;



}

