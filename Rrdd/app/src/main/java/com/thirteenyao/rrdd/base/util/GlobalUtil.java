package com.thirteenyao.rrdd.base.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;


import com.thirteenyao.rrdd.R;
import com.thirteenyao.rrdd.base.bean.BaseConfig;

import java.io.File;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by 18722 on 2016/5/10.
 */
public class GlobalUtil implements BaseConfig {

    private static final String HMAC_SHA1 = "HmacSHA1";

    /** 版本号 */
    private static String versionCode;
    /** 包名 */
    private static String packageName;
    /** 设备号 */
    private static String deviceId;
    /** 渠道 */
    private static String appkey;
    /** 设备名 */
    private static String moduleName;
    /** 设备token */
    private static String deviceToken;


    /**
     * 获取版本号
     *
     * @return String
     */
    public static String getAppVersion(Context context) {
        String versionname;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionname = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionname = "";
        }
        return versionname;
    }

    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    /**
     * 获取最后的请求参数
     * @param params 请求参数
     * @return
     */
    public static String getParamsSignStr(String params){
        return getParamsSignStr(ENCRYPTKEY,params);
    }

    /**
     *
     * @return 存储该应用文件的路径
     */
    public static String getAppFilePath(Context context){
        String folderPath = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            folderPath = context.getExternalFilesDir("data").getAbsolutePath();
        } else {
            folderPath = context.getCacheDir().getAbsolutePath();
        }
        return folderPath;
    }

    /**
     * 获取加密后的字符串
     * @param key 加密需要的key
     * @param paramsStr  要加密的请求参数
     * @return
     */
    public static String getParamsSignStr(String key ,String paramsStr){

        if(key == null || paramsStr == null){
            return "";
        }

        String signedStr = HMACSHA1(key.getBytes(), paramsStr.getBytes());
        String signedStr1 = base64(signedStr.getBytes());

        return signedStr1;
    }

    /**  HMACSHA1加密
     *
     * @param key 加密使用的key
     * @param strByte 待加密的数据
     * @return 生成HMAC_SHA1编码的字符串
     */

    public static String HMACSHA1(byte[] key, byte[] strByte) {

        byte[] rawHmac = null;
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);
            Mac mac = Mac.getInstance(HMAC_SHA1);

            mac.init(signingKey);
            rawHmac = mac.doFinal(strByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64(rawHmac);
    }

    /**  MD5加密
     * @param pwd 待加密的密码
     * @return 生成MD5加密后Base64编码的字符串
     */
    public static String getMD5Pwd(String pwd) {

        if (StringUtils.isEmpty(pwd)) {
            return pwd;
        }

        return MD5(pwd.getBytes());
    }

    /**  HMACSHA1加密
     *
     * @param strByte 待加密的数据
     * @return 生成HMAC_SHA1编码的字符串
     */
    public static String MD5(byte[] strByte) {

        byte[] rawHmac = null;
        StringBuffer buf = new StringBuffer("");

        try {
            MessageDigest md5=MessageDigest.getInstance("MD5");
            rawHmac = md5.digest(strByte);
            int i;
            for (int offset = 0; offset < rawHmac.length; offset++) {
                i = rawHmac[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64(buf.toString().getBytes());
    }

    /**
     * base64加密
     */
    public static String base64(byte[] val) {
        if(val==null)
            return null;
        else
            return Base64.encode(val);
    }

    /**
     * 打印网络请求连接
     */
    public static void printRequestURL(Request request){
        try {
            StringBuffer buffer = new StringBuffer(request.url().url().toString()+"?");
            FormBody param = (FormBody) request.body();
            if (param != null) {
                for (int i = 0; i < param.size(); i++) {
                    buffer.append(param.encodedName(i)+"="+param.encodedValue(i)+"&");
                }
            }
            Log.d("NEWRINGPU_URL" , URLDecoder.decode(buffer.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SpannableString getColoredText(Context context , String content , int start , int end , int colorId){
        //合法性检查
        if (StringUtils.isEmpty(content) ) {
            return null;
        }

        SpannableString ss = new SpannableString(content);

        //非法
        if (start < 0 || end < 0 || start >content.length() || end >content.length()) {
            return ss;
        }

        colorId = colorId > 0 ? colorId : R.color.gray;

        //确保开始结束顺序
        if (start > end) {
            int t = start;
            start = end;
            end = t;
        }

        //局部颜色渲染
        ss.setSpan(new ForegroundColorSpan(context.getResources().getColor(colorId)) , start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return ss;
    }

    /**
     * 初始化设备信息
     * @param context
     */
    public static void initDeviceInfo(Context context){
        //版本号
        versionCode = StringUtils.getNotNullStr(getAppVersion(context));
        //包名
        packageName = StringUtils.getNotNullStr(getPackageInfo(context).packageName);
        //设备号
        deviceId ="";
        //渠道
        appkey = "";

        deviceToken="";
    }

    public static String getVersionCode() {
        return versionCode;
    }

    public static String getPackageName() {
        return packageName;
    }

    public static String getDeviceId() {
        return deviceId;
    }

    public static String getAppkey() {
        return appkey;
    }

    public static String getModuleName() {
        return moduleName;
    }

    public static MultipartBody filesToMultipartBody(String content, String sign, List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("content",content);
        builder.addFormDataPart("sign",sign);
        for (File file : files) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart("file", file.getName(), requestBody);
        }

        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }

//    /**
//     * 讲照片选择List转成其地址的List
//     * @param photoItems
//     * @return
//     */
//    public static List<String> convertPhotoItem2List(List<PhotoItem> photoItems){
//        if (photoItems == null || photoItems.isEmpty()) {
//            return null;
//        }
//        else {
//            List<String> paths = new ArrayList<>();
//            for (PhotoItem item: photoItems
//                 ) {
//                paths.add(item.getPhotoPath());
//            }
//            return paths;
//        }
//    }

//    /**
//     * 将字符串List转选项Bean集合
//     * @param itemTexts
//     * @return
//     */
//    public static List<CommListPopBean> convertStringArr2Beanlist(List<String> itemTexts) {
//        if (itemTexts == null || itemTexts.isEmpty()) {
//            return null;
//        }
//
//        List<CommListPopBean> list = new ArrayList<>();
//        CommListPopBean bean = null;
//
//        //循环将字符数据封装到bean
//        for (String str:itemTexts
//                ) {
//            bean = new CommListPopBean();
//            bean.setTitle(str);
//            list.add(bean);
//        }
//
//        return list;
//    }
//    /**
//     * 将filebean中path按照,分割拼接成字符串
//     * @param files
//     * @return
//     */
//    public static String getFilePaths(List<FileBean> files) {
//        if (files == null && files.isEmpty()) {
//            return null;
//        }
//
//        StringBuffer buff = new StringBuffer();
//        for (FileBean file:files
//             ) {
//            buff.append(file.getPath() + ",");
//        }
//        return buff.toString();
//    }

//    /**
//     * 将filebean中path抽取list集合
//     * @param files
//     * @return
//     */
//    public static List<String> getFilePathArr(List<FileBean> files) {
//        if (files == null && files.isEmpty()) {
//            return null;
//        }
//
//        List<String> arr = new ArrayList<>();
//        for (FileBean file:files
//                ) {
//            arr.add(file.getPath());
//        }
//        return arr;
//    }



    public static String getDeviceToken(){
        return deviceToken;
    }

    /**
     * 获取密码等级
     * @param password
     * @return
     */

}
