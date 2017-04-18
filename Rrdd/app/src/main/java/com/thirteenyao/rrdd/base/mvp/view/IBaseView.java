package com.thirteenyao.rrdd.base.mvp.view;

import android.content.DialogInterface;
import android.view.View;

import com.thirteenyao.rrdd.base.widget.AbnormalLayout;

/**
 * Created by ThirteenYao on 2017/4/5.
 */

public interface IBaseView {
    /**
     * 展示空数据界面
     */
    void showNullMessageLayout();

    /**
     *  展示网络异常界面
     * @param listener
     */
    void showNetErrorLayout(AbnormalLayout.OnRetryListener listener);

    /**
     * 展示接口异常界面
     * @param listener
     */
    void showSysErrLayout(AbnormalLayout.OnRetryListener listener);

    /**
     * 带有错误信息
     * @param errMsg
     * @param listener
     */
    void showSysErrLayout(String errMsg,AbnormalLayout.OnRetryListener listener);

    /**
     * 加载中
     */
    void showProgress();

    /**
     * 取消加载界面
     */
    void hideErrPage();

    /**
     * toast
     * @param c
     */
    void toast(CharSequence c);
    void toast(int id);
    void toast(int id, Object... value);

    /**
     * 交互型界面--网络异常
     * @param nagetiveOnClickListener
     * @param positiveOnClickListener
     */
    void dialogShowNetError(DialogInterface.OnClickListener nagetiveOnClickListener, DialogInterface.OnClickListener positiveOnClickListener);

    /**
     * 交互型界面--接口异常
     * @param nagetiveOnClickListener
     * @param positiveOnClickListener
     */
    void dialogShowSystemError(DialogInterface.OnClickListener nagetiveOnClickListener, DialogInterface.OnClickListener positiveOnClickListener);

    /**
     * 软键盘控制
     *
     * @param isOpen true-打开键盘，false-关闭键盘
     */
    void toggleKeyboard(boolean isOpen);
    /**
     * 提示框
     */
    void dialogShowMessage(String titleId, String msgId, String btnTextId, int positionId);
    /**
     * 提示框
     */
    void dialogShowMessage(String titleId, String msgId, String btnTextId);
    /**
     * 提示框 带事件
     */
    void dialogShowMessage(String titleId, String msgId, String btnTextId,
                           int positionId, DialogInterface.OnClickListener positiveOnClickListener);


}
