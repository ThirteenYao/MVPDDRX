package com.thirteenyao.rrdd.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.thirteenyao.rrdd.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ThirteenYao on 2017/4/7.
 * 展示型界面的异常界面---交互型界面还得写一个dialog
 * 包括--空数据，网络异常，接口异常
 */

public class AbnormalLayout extends FrameLayout{

    /**
     * 根布局
     */
    @BindView(R.id.error_layout)
    FrameLayout mFlErrorLayout;

    /**
     * 空数据
     */
    @BindView(R.id.fl_empty_container)
    FrameLayout mFlEmptyLayout;
    @BindView(R.id.tv_empty_error)
    TextView mTvEmptyError;

    /**
     * 网络异常
     */
    @BindView(R.id.fl_net_container)
    FrameLayout mFlNetErrLayout;
    @BindView(R.id.tv_net_error)
    TextView mTvNetError;

    /**
     * 系统异常（接口异常）
     */
    @BindView(R.id.fl_system_container)
    FrameLayout mFlSysErrLayout;
    @BindView(R.id.tv_system_error)
    TextView mTvSystemError;

    @BindView(R.id.loading)
    SpinKitView mLodingLayout;



    //隐藏所有的异常界面
    public static final int STATUS_HIDE = 1001;
    //加载
    public static final int STATUS_LOADING = 1;
    //没有网络
    public static final int STATUS_NO_NET = 2;
    //空数据
    public static final int STATUS_NO_DATA = 3;
    //系统异常
    public static final int STATUS_SYS_ERR=4;

    private Context mContext;

    private OnRetryListener mOnRetryListener;

    private int mEmptyStatus = STATUS_HIDE;
    private int mBgColor;
    public AbnormalLayout(Context context) {
        this(context, null);
    }

    public AbnormalLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    /**
     * 初始化
     */
    private void init(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.ErrorLayout);
        try {
            mBgColor = a.getColor(R.styleable.ErrorLayout_background_color, Color.WHITE);
        } finally {
            a.recycle();
        }
        View.inflate(mContext, R.layout.abnormal_layout, this);
        ButterKnife.bind(this);
        mFlErrorLayout.setBackgroundColor(mBgColor);
        _switchEmptyView();
    }

    /**
     * 隐藏视图
     */
    public void hide() {
        mEmptyStatus = STATUS_HIDE;
        _switchEmptyView();
    }

    /**
     * 设置状态
     *
     * @param emptyStatus
     */
    public void setEmptyStatus(@EmptyStatus int emptyStatus) {
        mEmptyStatus = emptyStatus;
        _switchEmptyView();
    }

    /**
     * 获取状态
     * @return  状态
     */
    public int getEmptyStatus() {
        return mEmptyStatus;
    }

    /**
     * 接口异常设置提示消息
     *
     * @param msg 显示消息
     */
    public void setSysErrMessage(String msg) {
        mTvSystemError.setText(msg);
    }

//    public void hideErrorIcon() {
//        mTvEmptyMessage.setCompoundDrawables(null, null, null, null);
//    }

//    /**
//     * 设置图标
//     * @param resId 资源ID
//     */
//    public void setEmptyIcon(int resId) {
//        mIvEmptyIcon.setImageResource(resId);
//    }
//
//    /**
//     * 设置图标
//     * @param drawable drawable
//     */
//    public void setEmptyIcon(Drawable drawable) {
//        mIvEmptyIcon.setImageDrawable(drawable);
//    }

    public void setLoadingIcon(Sprite d) {
        mLodingLayout.setIndeterminateDrawable(d);
    }

    /**
     * 切换视图
     */
    private void _switchEmptyView() {
        switch (mEmptyStatus) {
            case STATUS_LOADING:
                setVisibility(VISIBLE);
                mFlEmptyLayout.setVisibility(GONE);
                mFlNetErrLayout.setVisibility(GONE);
                mFlSysErrLayout.setVisibility(GONE);
                mLodingLayout.setVisibility(VISIBLE);
                break;
            case STATUS_NO_DATA:
                setVisibility(VISIBLE);
                mLodingLayout.setVisibility(GONE);
                mFlNetErrLayout.setVisibility(GONE);
                mFlSysErrLayout.setVisibility(GONE);
                mFlEmptyLayout.setVisibility(VISIBLE);
            case STATUS_NO_NET:
                setVisibility(VISIBLE);
                mLodingLayout.setVisibility(GONE);
                mFlEmptyLayout.setVisibility(GONE);
                mFlSysErrLayout.setVisibility(GONE);
                mFlNetErrLayout.setVisibility(VISIBLE);
                break;
            case STATUS_SYS_ERR:
                setVisibility(VISIBLE);
                mLodingLayout.setVisibility(GONE);
                mFlEmptyLayout.setVisibility(GONE);
                mFlNetErrLayout.setVisibility(GONE);
                mFlSysErrLayout.setVisibility(VISIBLE);
                break;
            case STATUS_HIDE:
                setVisibility(GONE);
                break;
            default:
                break;
        }
    }

    /**
     * 设置重试监听器
     *
     * @param retryListener 监听器
     */
    public void setRetryListener(OnRetryListener retryListener) {
        this.mOnRetryListener = retryListener;
    }

    @OnClick({R.id.tv_net_error,R.id.tv_system_error,R.id.tv_empty_error})
    public void onClick() {
        if (mOnRetryListener != null) {
            mOnRetryListener.onRetry();
        }
    }

    /**
     * 点击重试监听器
     */
    public interface OnRetryListener {
        void onRetry();
    }


    @Retention(RetentionPolicy.SOURCE)   //这种类型的Annotations只在源代码级别保留,编译时就会被忽略
    @IntDef({STATUS_LOADING, STATUS_NO_NET, STATUS_NO_DATA,STATUS_SYS_ERR})   //避免使用枚举
    public @interface EmptyStatus{}

}
