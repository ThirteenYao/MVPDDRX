package com.thirteenyao.rrdd.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ThirteenYao on 2017/4/7.
 * 常用正则表达式  手机，邮箱，身份证，密码
 */

public class PassWordUtil {
    /**
     * 判定密码是否全是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否全是英文
     * @param str
     * @return
     */
    public  static boolean isEnglish(String str){
        Pattern pattern = Pattern.compile("[A-Za-z]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断手机格式是否正确
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^[1][34578][0-9]{9}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }
    /**
     *  判断email格式是否正确
     */
    public static boolean  isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 身份证格式
     * @param idCard0
     * @return
     */
    public static boolean isIdCard(String idCard0){
        String str="(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(idCard0);
        return m.matches();
    }

    /**
     * 只能输入汉字和字母
     */
    public static boolean isOnlyChineseAndDigit(String str1){
        String str="^[a-zA-Z\\u4e00-\\u9fa5]+$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(str1);
        return m.matches();
    }
    /**
     * 获取密码等级
     * @param password
     * @return
     */
    public static  int getPasswordLevel(String password){
        int mode = 0;
        if(password.length() <6) {
            mode =0;
        }
        if(password.matches("(.*)\\d(.*)")){
            mode ++;
        }

        if(password.matches("(.*)[a-zA-Z](.*)")){
            mode ++;
        }

        if(password.matches("(.*)\\W(.*)")){
            mode ++;
        }
        return mode;
    }
}
