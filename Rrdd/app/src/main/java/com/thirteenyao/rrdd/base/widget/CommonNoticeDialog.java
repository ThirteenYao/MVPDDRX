package com.thirteenyao.rrdd.base.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thirteenyao.rrdd.R;

/**
 * Created by ThirteenYao on 2017/4/10.
 */

public class CommonNoticeDialog  extends Dialog implements View.OnClickListener {
    private TextView dialog_title;
    private ImageView dialogIcon;
    private ImageButton dialog_close;
    private FrameLayout dialog_content;
    private View buttonDivider;
    private CharSequence showMsg;
    private int customLayoutId;
    private static final int default_layout_id = R.layout.reminder_dialog_layout;
    private OnClickListener positiveListener;
    private OnClickListener cancelListener;
    private OnClickListener prepareListener;
    private String positiveBtnTxt;
    private String cancelBtnTxt;
    private String prepareBtnTxt;
    private float mMessageTextSize = 0;
    Button positiveBtn;
    Button cancelBtn;

    /**
     * @param @param context
     * @return
     * @throws
     * @Description:
     */
    public CommonNoticeDialog(Context context) {
        this(context, R.style.DialogStyle);
    }

    /**
     * @param @param context
     * @param @param theme
     * @return
     * @throws
     * @Description:
     */
    public CommonNoticeDialog(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.common_dialog_layout);
        initView();
    }

    /**
     * @param @param context
     * @param @param cancelable
     * @param @param cancelListener
     * @return
     * @throws
     * @Description:
     */
    public CommonNoticeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

    }

    /**
     * @param
     * @return void
     * @throws
     * @Title initView
     * @Description: 初始化 弹框视图
     */
    private void initView() {
        // dialog_title = (TextView) findViewById(R.id.dialog_title_txt);
        LayoutInflater lf = this.getLayoutInflater();
        View layout = lf.inflate(default_layout_id, null);
        dialog_close = (ImageButton) findViewById(R.id.dialog_close);
        dialog_content = (FrameLayout) findViewById(R.id.dialog_content);
        dialog_content.removeAllViews();
        dialog_content.addView(layout);
        dialog_title = (TextView) findViewById(R.id.dialog_title_txt);
        dialog_close.setOnClickListener(this);
        positiveBtn = (Button) findViewById(R.id.btn_ok);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        dialogIcon = (ImageView) findViewById(R.id.dialog_icon);
        buttonDivider = findViewById(R.id.v_divider);
    }

    /**
     * @Title: setMessageTextSize
     * @Description: 设置Message的字体大小
     * @author xingwu
     */
    public void setMessageTextSize(float size) {
        this.mMessageTextSize = size;
    }

    /**
     * @Description: 设置弹框标题
     */
    public void setTitle(CharSequence title) {
        dialog_title.setText(title);
    }

    /**
     * @Description: 设置确定文字
     */
    public void setpositive(CharSequence title) {
        positiveBtn.setText(title);
    }

    public void setIcon(int resId) {
        if (resId > 0) {
            dialogIcon.setImageResource(resId);
            dialogIcon.setVisibility(View.VISIBLE);
        }

    }

    /**
     * @Description: 设置button间隔
     */
    public void setDividerVisiable(boolean isShow) {
        if (isShow) {
            buttonDivider.setVisibility(View.VISIBLE);
        } else {
            buttonDivider.setVisibility(View.GONE);
        }
    }

    /**
     * @Description: 设置否定文字
     */
    public void setcancel(CharSequence title) {
        cancelBtn.setText(title);
    }

    /**
     * @Description: 设置弹框标题
     */
    public void setTitle(int resId) {
        dialog_title.setText(resId);
    }

    /**
     * @Description: 设置确定文字
     */
    public void setpositive(int resId) {
        positiveBtn.setText(resId);
    }

    /**
     * @Description: 设置否定文字
     */
    public void setcancel(int resId) {
        if (resId == 0) {

        } else
            cancelBtn.setText(resId);
    }

    /**
     * @Description: 删除取消按钮
     */
    public void delCancleBtn() {
        cancelBtn.setVisibility(View.GONE);
//		LinearLayout line = (LinearLayout) findViewById(R.id.line);
//		line.setVisibility(View.GONE);
    }

    /**
     * @Description: 内部使用 单击事件统一处理
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            // case R.id.dialog_close:
            // this.dismiss();
            // break;
            case R.id.btn_ok:
                positiveListener.onClick(this, R.id.btn_ok);
                break;
            case R.id.btn_cancel:
                cancelListener.onClick(this, R.id.btn_cancel);
                break;
        }
    }

    /**
     * @param @param resId
     * @return void
     * @throws
     * @Title setContentLayout
     * @Description: 设置自定义的内容布局
     */
    public void setContentLayout(int resId) {
        customLayoutId = resId;
        LayoutInflater lf = this.getLayoutInflater();
        View layout = lf.inflate(resId, null);
        dialog_content.removeAllViews();
        dialog_content.addView(layout);

    }

    /**
     * @param @param resId
     * @return void
     * @throws
     * @Title setContentLayout
     * @Description: 设置自定义的内容布局
     */
    public void setContentLayout(View view) {
        dialog_content.removeAllViews();
        dialog_content.addView(view);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Dialog#show()
     *
     * @Description: 显示弹框
     */
    @Override
    public void show() {
        if (0 != customLayoutId) {
            try {
                super.show();
            } catch (WindowManager.BadTokenException e) {
                this.dismiss();
            } catch (IllegalStateException e1) {
                this.dismiss();
            }
            return;
        }
        if (showMsg != null && (!"".equals(showMsg))) {

            if (positiveListener != null || cancelListener != null
                    || prepareListener != null) {
                LinearLayout ok_cancel_layout = (LinearLayout) findViewById(R.id.ok_cancel_layout);
                ok_cancel_layout.setVisibility(View.VISIBLE);
                Button positiveBtn = (Button) findViewById(R.id.btn_ok);
                if (positiveListener == null) {
                    positiveBtn.setVisibility(View.GONE);
                } else {
                    positiveBtn.setOnClickListener(this);
                    if (positiveBtnTxt != null && !positiveBtnTxt.equals("")) {
                        positiveBtn.setText(positiveBtnTxt);
                    }
                    positiveBtn.setVisibility(View.VISIBLE);
                }

                Button cancelBtn = (Button) findViewById(R.id.btn_cancel);
                if (cancelListener == null) {
                    cancelBtn.setVisibility(View.GONE);
                } else {
                    if (cancelBtnTxt != null && !cancelBtnTxt.equals("")) {
                        cancelBtn.setText(cancelBtnTxt);
                    }
                    cancelBtn.setOnClickListener(this);
                    cancelBtn.setVisibility(View.VISIBLE);
                }
            }
        }
        try {
            super.show();
        } catch (WindowManager.BadTokenException e) {
            this.dismiss();
        } catch (IllegalStateException e1) {
            this.dismiss();
        }
    }

    /**
     * @param @param showMsg
     * @return TextView
     * @throws
     * @Title setMessage
     * @Description: 设置弹框里显示的内容 调用此函数时，弹框内容视图默认使用缺省布局（default_dialog_layout.xml）
     */
    public TextView setMessage(CharSequence showMsg) {
        if (showMsg != null && (!"".equals(showMsg))) {
            this.showMsg = showMsg;
            TextView msg = (TextView) findViewById(R.id.default_dialog_content_txt);
            msg.setClickable(true);
            if (msg != null) {
                if (mMessageTextSize != 0)
                    msg.setTextSize(mMessageTextSize);
                msg.setText(showMsg);
            } else {
                msg = (TextView) findViewById(R.id.default_dialog_content_txt);
                if (mMessageTextSize != 0)
                    msg.setTextSize(mMessageTextSize);
                msg.setText(Html.fromHtml(showMsg.toString()));
            }
            return msg;
        }
        return null;
    }

    /**
     * @param @param showMsg
     * @return TextView
     * @throws
     * @Title setMessage
     * @Description: 设置弹框里显示的内容 调用此函数时，弹框内容视图默认使用缺省布局（default_dialog_layout.xml）
     */
    public TextView setMessage(String showMsg) {
        if (showMsg != null && (!"".equals(showMsg))) {
            this.showMsg = showMsg;
            TextView msg = (TextView) findViewById(R.id.default_dialog_content_txt);
            if (msg != null) {
                if (mMessageTextSize != 0)
                    msg.setTextSize(mMessageTextSize);
                msg.setText(showMsg);
            } else {
                msg = (TextView) findViewById(R.id.default_dialog_content_txt);
                if (mMessageTextSize != 0)
                    msg.setTextSize(mMessageTextSize);
                msg.setText(showMsg);
            }
            return msg;
        }
        return null;
    }

    /**
     * @param @param showMsg
     * @return TextView
     * @throws
     * @Title setMessage
     * @Description: 设置弹框里显示的内容 调用此函数时，弹框内容视图默认使用缺省布局（default_dialog_layout.xml）
     */
    public TextView setMessage(String showMsg, boolean isHtml) {
        if (isHtml) {
            if (showMsg != null && (!"".equals(showMsg))) {
                this.showMsg = showMsg;
                TextView msg = (TextView) findViewById(R.id.default_dialog_content_txt);
                if (msg != null) {
                    if (mMessageTextSize != 0)
                        msg.setTextSize(mMessageTextSize);
                    msg.setText(Html.fromHtml(showMsg));
                } else {
                    msg = (TextView) findViewById(R.id.default_dialog_content_txt);
                    if (mMessageTextSize != 0)
                        msg.setTextSize(mMessageTextSize);
                    msg.setText(Html.fromHtml(showMsg));
                }
                return msg;
            }
        } else {
            return setMessage(showMsg);
        }

        return null;
    }

    public void setMsgTxtpoist(int gravity) {
        TextView msg = (TextView) findViewById(R.id.default_dialog_content_txt);
        msg.setGravity(gravity);
    }

    /**
     * @param @param showMsg
     * @return TextView
     * @throws
     * @Title setMessage
     * @Description: 设置弹框里显示的内容 调用此函数时，弹框内容视图默认使用缺省布局（default_dialog_layout.xml）
     */
    public TextView setMessage(int resId) {
        showMsg = getContext().getString(resId);
        TextView msg = (TextView) findViewById(R.id.default_dialog_content_txt);
        if (msg != null) {
            if (mMessageTextSize != 0)
                msg.setTextSize(mMessageTextSize);
            msg.setText(resId);
        } else {
            msg = (TextView) findViewById(R.id.default_dialog_content_txt);
            if (mMessageTextSize != 0)
                msg.setTextSize(mMessageTextSize);
            msg.setText(resId);
        }
        return msg;
    }

    /**
     * @param @param listener
     * @return void
     * @throws
     * @Title setOnCanceBtnListener
     * @Description: 设置 点击取消按钮 事件监听
     */
    public void setCancelBtnListener(OnClickListener listener) {
        this.cancelListener = listener;
    }

    /**
     * @param @param listener
     * @return void
     * @throws
     * @Title setPositiveBtnListener
     * @Description: 设置 点击确定按钮 事件监听
     */
    public void setPositiveBtnListener(OnClickListener listener) {
        this.positiveListener = listener;
    }

    /**
     * @param @param listener
     * @return void
     * @throws
     * @Title setPrepareBtnListener
     * @Description: 设置 点击预留按钮 事件监听
     */
    public void setPrepareBtnListener(OnClickListener listener) {
        this.prepareListener = listener;
    }

    public void setPositiveBtnTxt(String txt) {
        if (txt != null && !"".equals(txt)) {
            this.positiveBtnTxt = txt;
        }
    }

    public void setCancelBtnTxt(String txt) {
        if (txt != null && !"".equals(txt)) {
            this.cancelBtnTxt = txt;
        }
    }

    public void setPrepareBtnTxt(String txt) {
        if (txt != null && !"".equals(txt)) {
            this.prepareBtnTxt = txt;
        }
    }

    /**
     * @param @return
     * @return TextView
     * @throws
     * @Title getDefaultText
     * @Description: 获取 使用缺省布局时的 TextView 对象s
     */
    public TextView getDefaultText() {
        return (TextView) findViewById(R.id.default_dialog_content_txt);
    }

    /**
     * @param @return
     * @return ViewGroup
     * @throws
     * @Title getContentView
     * @Description: 获取弹框内容视图
     */
    public ViewGroup getContentView() {
        return dialog_content;
    }

    public void setCloseEnable(boolean state) {
        dialog_close.setEnabled(state);
    }

    public String getMessage() {
        return this.showMsg.toString();
    }

    /**
     * @param @param flag
     * @return void
     * @throws
     * @Description: 设置对话框标题栏右边的关闭按钮是否显示
     */
    public void setCloseShow(boolean flag) {
        if (flag) {
            dialog_close.setVisibility(View.VISIBLE);
        } else {
            dialog_close.setVisibility(View.GONE);
        }
    }

    public void setpositiveColor(int ringpu_alert_btn_gray_all_bg_selector) {
        positiveBtn.setBackgroundResource(ringpu_alert_btn_gray_all_bg_selector);
    }
}