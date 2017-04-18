package com.thirteenyao.rrdd.base.util;

import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;


import com.thirteenyao.rrdd.base.MApplication;

import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gray on 2016/5/11.
 */
public class StringUtils {
    /**
     * 字符判空  null | "" | "null"
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (TextUtils.isEmpty(str) || "null".equals(str) || "".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取非空字符串，
     *
     * @param str
     * @return str|""
     */
    public static String getNotNullStr(String str) {
        return isEmpty(str) ? "" : str;
    }

    /**
     * 从url解析param的map集合
     *
     * @return
     */
    public static Map<String, String> getURLParamsMap(String url) {
        if (isEmpty(url)) { //为空返回
            return null;
        } else {              //处理
            try {
                Map<String, String> map = new HashMap<>();
                url = Html.fromHtml(URLDecoder.decode(url)).toString();
                //截参数部分 -- key1=value1&key2=value2&……
                String paramStr = url.substring((url.indexOf("?") + 1), url.length());
                //拆分键值数组 -- [key1=value,key2=value2,……]
                String[] paramArr = paramStr.split("&");

                //封装map集合
                if (paramArr != null && paramArr.length > 0) {
                    for (int i = 0; i < paramArr.length; i++) {
                        map.put(paramArr[i].split("=")[0], paramArr[i].split("=")[1]);
                    }
                }

                return map;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
    }

    /**
     * 按照指定长度缩略字符串
     *
     * @param str
     * @param length
     * @return
     */
    public static String getShorterStr(String str, int length) {
        if (isEmpty(str) || length <= 0 || length > str.length()) {
            return str;
        }
        //截取字符
        return str.substring(0, length) + "…";
    }

    /**
     * 获取URL转义的字符串
     *
     * @param str
     * @return
     */
    public static String getDecodedStr(String str) {
        if (isEmpty(str)) {
            return str;
        }
        //截取字符
        return Html.fromHtml(str).toString();
    }

    /**
     * @param curr   现有
     * @param newStr 准备添加
     * @param total  目标大小
     * @return
     */
    public static boolean willOverLimit(String curr, String newStr, int total) {
        if (StringUtils.isEmpty(curr) || StringUtils.isEmpty(newStr) || total < 1) {
            return false;
        }

        if ((curr.length() + newStr.length()) > total) {
            return true;
        }
        return false;
    }

    /**
     * 取整
     *
     * @param price
     * @return
     */
    public static String cutLimitString(String price) {
        if (price.contains(".")) {
            int index = price.indexOf(".");
            price = price.substring(0, index);
        }
        return price;
    }

    /**
     * 将double类型转String,去除科学记数法,取整
     *
     * @param value
     * @return
     */
    public static String parseDoubleToString(double value) {
        String prize = value + "";
        if (prize.contains("E")) {
            DecimalFormat df = new DecimalFormat("0");
            prize = df.format(new Double(prize));
        }
        return cutLimitString(prize);
    }

    /**
     * 截取价格的小数点后两位，若小数点后都是0，只取整数部分
     *
     * @param price
     * @return
     */
    public static String cutLimitPrice(String price) {
        String before = null;
        String after = null;
        if (price.contains(".")) {
            int index = price.indexOf(".");
            int end = index + 3;
            if (end > price.length()) {
                end = price.length();
            }
            price = price.substring(0, end);
            //整数部分
            before = price.substring(0, index);
            after = price.substring(index + 1, price.length());
            char[] theafter = after.toCharArray();
            //小数部分不全为0时为true
            boolean flag = false;
            for (char temp : theafter) {
                if (temp != '0') {
                    flag = true;
                }
            }
            if (flag) {
                return price;
            } else {
                return before;
            }
        } else {
            return price;
        }
    }

    /**
     * 限制输入价格，小数点后两位
     *
     * @param editText
     */
    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    public static String getFormatedPrice(String price,int totalLength){
/*
            防止老数据中出现超长并且自动转化为科学计数法的情况
            首先将s尝试转化为double类型，然后再转化为String
            例：2.456+7 -> 24560000
         */
        try {
            String pStr = new DecimalFormat("#.00").format(price);
            pStr = StringUtils.isEmpty(pStr) ? price : pStr;
            if (pStr.length() > totalLength) {
                pStr.substring((pStr.length() - totalLength),pStr.length());
            }
            return pStr;
        } catch (Exception e) {
            e.printStackTrace();
            return price;
        }
    }

    /**
     * 将string转为int 失败返回默认值i
     * @param string
     * @param i 默认值
     * @return
     */
    public static int parseInt(String string, int i) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            if (MApplication.DEVELOP_DEBUG_MODE) {
                e.printStackTrace();
            }

            return i;
        }
    }

    /**
     * 将string转为double 失败返回默认值i
     * @param string
     * @param d 默认值
     * @return
     */
    public static double parseDouble(String string, double d) {
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException e) {
            if (MApplication.DEVELOP_DEBUG_MODE) {
                e.printStackTrace();
            }

            return d;
        }
    }
}
