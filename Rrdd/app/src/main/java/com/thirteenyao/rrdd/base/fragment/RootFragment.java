package com.thirteenyao.rrdd.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.thirteenyao.rrdd.R;
import com.thirteenyao.rrdd.base.mvp.view.IBaseView;
import com.thirteenyao.rrdd.base.util.ACache;
import com.thirteenyao.rrdd.base.widget.AbnormalLayout;
import com.thirteenyao.rrdd.base.widget.CommonNoticeDialog;

import org.apache.http.conn.ConnectTimeoutException;

import butterknife.BindView;

/**
 * Created by ThirteenYao on 2017/4/13.
 */

public class RootFragment extends Fragment implements IBaseView {

    protected Activity activity;

    /**
     * 把 EmptyLayout 放在基类统一处理，@Nullable 表明 View 可以为 null，详细可看 ButterKnife
     */
    @Nullable
    @BindView(R.id.error_layout)
    protected AbnormalLayout mErrorLayout;

    /**
     * 键盘管理
     */
    private InputMethodManager manager;

    /**
     * 对话框
     */
    CommonNoticeDialog dialog;

    protected ACache mCache;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        // 获取键盘管理
        manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        mCache = ACache.get(activity);activity = getActivity();

    }

    @Override
    public void showProgress() {
        if (mErrorLayout != null) {
            mErrorLayout.setEmptyStatus(mErrorLayout.STATUS_LOADING);
        }
    }

    @Override
    public void hideErrPage() {
        if (mErrorLayout != null) {
            mErrorLayout.hide();
        }
    }

    @Override
    public void showNetErrorLayout(AbnormalLayout.OnRetryListener listener) {
        if (mErrorLayout != null) {
            mErrorLayout.setEmptyStatus(mErrorLayout.STATUS_NO_NET);
            mErrorLayout.setRetryListener(listener);
        }
    }
    @Override
    public void showNullMessageLayout() {
        if (mErrorLayout != null) {
            mErrorLayout.setEmptyStatus(mErrorLayout.STATUS_NO_DATA);
        }
    }

    @Override
    public void showSysErrLayout(AbnormalLayout.OnRetryListener listener) {
        if (mErrorLayout != null) {
            mErrorLayout.setEmptyStatus(mErrorLayout.STATUS_SYS_ERR);
            mErrorLayout.setRetryListener(listener);
        }
    }

    @Override
    public void showSysErrLayout(String errMsg, AbnormalLayout.OnRetryListener listener) {
        if (mErrorLayout!=null){
            mErrorLayout.setSysErrMessage(errMsg);
            mErrorLayout.setEmptyStatus(mErrorLayout.STATUS_SYS_ERR);
            mErrorLayout.setRetryListener(listener);
        }
    }

    @Override
    public void toast(int id) {

    }


    @Override
    public void toggleKeyboard(boolean isOpen) {

        //打开
        if (isOpen) {
            if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
                if (activity.getCurrentFocus() != null)
                    manager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        }
        //关闭
        else {
            if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                if (activity.getCurrentFocus() != null)
                    manager.hideSoftInputFromWindow(activity.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

    }
    @Override
    public void toast(int id, Object... value) {

    }

    @Override
    public void toast(CharSequence c) {

    }
    /** 公共对话框  */

    /**
     * 使用对话框显示通知
     *
     * @param titleId    标题
     * @param msgId      通知内容
     * @param btnTextId  按钮文本
     * @param positionId 文本位置
     */
    public void dialogShowMessage(String titleId, String msgId, String btnTextId,
                                  int positionId) {
        dialogShowMessage(titleId, msgId, btnTextId, positionId, null, null);
    }

    /**
     * 使用对话框显示通知
     *
     * @param titleId                 标题
     * @param msgId                   通知内容
     * @param btnTextId               按钮文本
     * @param positionId
     * @param positiveOnClickListener 点击监听
     */
    public void dialogShowMessage(String titleId, String msgId, String btnTextId,
                                  int positionId, DialogInterface.OnClickListener positiveOnClickListener) {
        dialogShowMessage(titleId, msgId, btnTextId, positionId, positiveOnClickListener, null);
    }

    /**
     * 使用对话框显示通知
     *
     * @param titleId     标题
     * @param msgId       通知内容
     * @param btnTextId   按钮文本
     * @param positionId  文本位置
     * @param keyListener 按键监听器
     */
    public void dialogShowMessage(String titleId, String msgId, String btnTextId,
                                  int positionId, DialogInterface.OnClickListener positiveOnClickListener, DialogInterface.OnKeyListener keyListener) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        dialog = new CommonNoticeDialog(activity);
        dialog.setTitle(titleId);
        dialog.setMessage(msgId);
        dialog.setpositive(btnTextId);
        dialog.setMsgTxtpoist(positionId);
        dialog.setDividerVisiable(false);
        dialog.setpositiveColor(R.drawable.commdialog_btn_all_selector);
        if (positiveOnClickListener == null) {
            dialog.setPositiveBtnListener(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else {
            dialog.setPositiveBtnListener(positiveOnClickListener);
        }
        //按键监听
        if (keyListener != null) {
            dialog.setOnKeyListener(keyListener);
        }
        dialog.show();
    }


    /**
     * 使用对话框显示通知
     *
     * @param titleId    标题
     * @param msgId      通知内容
     * @param btnTextId  按钮文本
     * @param positionId 文本位置
     * @param flag       点击dialog外部是否消失
     * @param isExit     屏蔽back键
     */
    public void dialogShowMessage(String titleId, String msgId, String btnTextId,
                                  int positionId, DialogInterface.OnClickListener positiveOnClickListener, boolean flag, boolean isExit) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        dialog = new CommonNoticeDialog(activity);
        dialog.setTitle(titleId);
        dialog.setMessage(msgId);
        dialog.setpositive(btnTextId);
        dialog.setMsgTxtpoist(positionId);
        dialog.setDividerVisiable(false);
        dialog.setpositiveColor(R.drawable.commdialog_btn_all_selector);
        if (positiveOnClickListener == null) {
            dialog.setPositiveBtnListener(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else {
            dialog.setPositiveBtnListener(positiveOnClickListener);
        }
        dialog.setCanceledOnTouchOutside(flag);
        dialog.setCancelable(isExit);
        dialog.show();
    }


    /**
     * 使用带图提示框
     *
     * @param imageResource 图片资源
     * @param msgId         通知内容
     * @param cancelTxt     按钮文本
     * @param okTxt         文本位置
     */
    public void dialogShowIcon(int imageResource, String msgId, String cancelTxt, String okTxt,
                               DialogInterface.OnClickListener nagetiveOnClickListener,
                               DialogInterface.OnClickListener positiveOnClickListener) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        dialog = new CommonNoticeDialog(activity);
        dialog.setIcon(imageResource);
        dialog.setMessage(msgId);
        dialog.setpositive(okTxt);
        dialog.setcancel(cancelTxt);
        if (positiveOnClickListener == null) {
            dialog.setPositiveBtnListener(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else {
            dialog.setPositiveBtnListener(positiveOnClickListener);
        }
        //取消按钮
        if (nagetiveOnClickListener == null) {
            dialog.setCancelBtnListener(new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else {
            dialog.setCancelBtnListener(nagetiveOnClickListener);
        }

        dialog.show();
    }

    /**
     * 网络异常提醒
     * 取消监听可为null 默认关闭
     */
    public void dialogShowNetError(DialogInterface.OnClickListener nagetiveOnClickListener, DialogInterface.OnClickListener positiveOnClickListener) {
        dialogShowIcon(R.mipmap.icon_common_wifi_dialog, getString(R.string.common_neterror_exc), getString(R.string.common_back), getString(R.string.common_dialog_error_retry), nagetiveOnClickListener, positiveOnClickListener);
    }

    /**
     * 系统异常提醒
     * 取消监听可为null 默认关闭
     */
    public void dialogShowSystemError(DialogInterface.OnClickListener nagetiveOnClickListener, DialogInterface.OnClickListener positiveOnClickListener) {

        dialogShowIcon(R.mipmap.icon_common_system_dialog, getString(R.string.common_syserror_exc), getString(R.string.common_back), getString(R.string.common_dialog_error_retry), nagetiveOnClickListener, positiveOnClickListener);
    }

    /**
     * 系统异常提醒
     * 取消监听可为null 默认关闭
     */
    public void dialogShowSystemError(Exception error, DialogInterface.OnClickListener nagetiveOnClickListener, DialogInterface.OnClickListener positiveOnClickListener) {

        if (!isNetworkAvailable(activity) || (error != null && error.getCause() != null && (error.getCause() instanceof ConnectTimeoutException))) {
            dialogShowNetError(nagetiveOnClickListener, positiveOnClickListener);
        } else {
            dialogShowSystemError(nagetiveOnClickListener, positiveOnClickListener);
        }

    }

    /**
     * 使用对话框显示通知
     *
     * @param titleId   标题
     * @param msgId     通知内容
     * @param btnTextId 按钮文本
     */
    public void dialogShowMessage(String titleId, String msgId, String btnTextId) {
        dialogShowMessage(titleId, msgId, btnTextId, Gravity.CENTER);
    }

    /**
     * @param title                   标题
     * @param msg                     提醒内容
     * @param positiveText            确定按钮文本
     * @param cancelText              取消按钮文本
     * @param positiveOnClickListener 确定按钮监听器
     * @param cancelOnClickListener   取消按钮监听器
     * @return
     */
    public CommonNoticeDialog dialogShowRemind(String title, CharSequence msg,
                                               String positiveText, String cancelText,
                                               DialogInterface.OnClickListener positiveOnClickListener,
                                               DialogInterface.OnClickListener cancelOnClickListener) {

        return dialogShowRemind(title, msg, positiveText, cancelText, positiveOnClickListener, cancelOnClickListener, Gravity.CENTER);
    }

    /**
     * 使用对话框显示提醒，需要点击确定或取消选择
     * 已屏蔽返回按键
     *
     * @param title                   标题
     * @param msg                     提醒内容
     * @param positiveText            确定按钮文本
     * @param cancelText              取消按钮文本
     * @param positiveOnClickListener 确定按钮监听器
     * @param cancelOnClickListener   取消按钮监听器
     */
    public CommonNoticeDialog dialogShowRemind2(String title, CharSequence msg,
                                                String positiveText, String cancelText,
                                                DialogInterface.OnClickListener positiveOnClickListener,
                                                DialogInterface.OnClickListener cancelOnClickListener
    ) {

        return dialogShowRemind2(title, msg, positiveText, cancelText, positiveOnClickListener, cancelOnClickListener, Gravity.CENTER);
    }


    /**
     * 使用对话框显示提醒，需要点击确定或取消选择
     *
     * @param title                   标题
     * @param msg                     提醒内容
     * @param positiveText            确定按钮文本
     * @param cancelText              取消按钮文本
     * @param positiveOnClickListener 确定按钮监听器
     * @param cancelOnClickListener   取消按钮监听器
     */
    public CommonNoticeDialog dialogShowRemind(String title, CharSequence msg,
                                               String positiveText, String cancelText,
                                               DialogInterface.OnClickListener positiveOnClickListener,
                                               DialogInterface.OnClickListener cancelOnClickListener,
                                               int positionId) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        dialog = new CommonNoticeDialog(activity);
        dialog.setTitle(title);
        dialog.setMessage(msg);

        dialog.setpositive(positiveText);
        dialog.setcancel(cancelText);
        dialog.setMsgTxtpoist(positionId);
        if (positiveOnClickListener != null) {
            dialog.setPositiveBtnListener(positiveOnClickListener);
        } else {
            dialog.setPositiveBtnListener(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        if (cancelOnClickListener != null) {
            dialog.setCancelBtnListener(cancelOnClickListener);
        } else {
            dialog.setCancelBtnListener(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
        return dialog;
    }

    /**
     * 使用对话框显示提醒，需要点击确定或取消选择
     * 已屏蔽返回键
     *
     * @param title                   标题
     * @param msg                     提醒内容
     * @param positiveText            取消按钮文本
     * @param cancelText              确定按钮文本
     * @param positiveOnClickListener 确定按钮监听器
     * @param cancelOnClickListener   取消按钮监听器
     */
    public CommonNoticeDialog dialogShowRemind2(String title, CharSequence msg,
                                                String positiveText, String cancelText,
                                                DialogInterface.OnClickListener positiveOnClickListener,
                                                DialogInterface.OnClickListener cancelOnClickListener, int positionId) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        dialog = new CommonNoticeDialog(activity);
        dialog.setTitle(title);
        dialog.setMessage(msg);

        dialog.setpositive(positiveText);
        dialog.setcancel(cancelText);
        dialog.setMsgTxtpoist(positionId);
        if (positiveOnClickListener != null) {
            dialog.setPositiveBtnListener(positiveOnClickListener);
        } else {
            dialog.setPositiveBtnListener(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        if (cancelOnClickListener != null) {
            dialog.setCancelBtnListener(cancelOnClickListener);
        } else {
            dialog.setCancelBtnListener(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    /**
     * 检查当前（所有类型的）网络是否连接正常并且可用
     *
     * @param context 上下文平台环境
     * @return 网络连接正常并且可用 返回 true,否则返回false
     */
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        if (cm == null) {
            return false;
        }
        NetworkInfo network = cm.getActiveNetworkInfo();
        if (network == null) {
            return false;
        }
        return network != null && network.isConnected() && network.isAvailable();

    }
}
