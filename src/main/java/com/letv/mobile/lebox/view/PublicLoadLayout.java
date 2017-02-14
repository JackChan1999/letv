package com.letv.mobile.lebox.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.mobile.lebox.R;

public class PublicLoadLayout extends RelativeLayout {
    private static final String FILTER_NO_DATA_MSG_KEY = "700052";
    private static final String LOADING_MSG_KEY = "500008";
    private static final String NO_DATA_MSG_KEY = "500012";
    private static final String NO_NET_MSG_KEY = "500011";
    private LinearLayout content;
    private boolean contentIsFramelayout;
    private Context context;
    private View error;
    private ImageView errorImage;
    private TextView errorTxt1;
    private TextView errorTxt2;
    private RelativeLayout frameContent;
    private View fullError;
    private TextView fullErrorText;
    private boolean isFilter;
    private boolean isFullNetError;
    private View loading;
    private TextView loadingText;
    private String mDefaultTextTabLoadingTextUrl;
    private String mDefaultTextTabNoDataTextUrl;
    private String mDefaultTextTabNoFilterDataTextUrl;
    private String mDefaultTextTabNoNetTextUrl;
    private Handler mHandler;
    private View mLoadLayout;
    OnClickListener mOnClickListener;
    private RefreshData mRefreshData;
    final Runnable mRunnable;

    public static PublicLoadLayout createPage(Context context, int layoutId) {
        return createPage(context, layoutId, false);
    }

    public static PublicLoadLayout createPage(Context context, int layoutId, boolean isToFrameLayout) {
        PublicLoadLayout rootView = new PublicLoadLayout(context);
        rootView.addContent(layoutId, isToFrameLayout);
        return rootView;
    }

    public PublicLoadLayout(Context context) {
        this(context, null);
    }

    public PublicLoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.contentIsFramelayout = false;
        this.isFilter = false;
        this.mRunnable = new 1(this);
        this.mOnClickListener = new 2(this);
        init(context);
    }

    @SuppressLint({"NewApi"})
    public PublicLoadLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.contentIsFramelayout = false;
        this.isFilter = false;
        this.mRunnable = new 1(this);
        this.mOnClickListener = new 2(this);
        init(context);
    }

    public PublicLoadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.contentIsFramelayout = false;
        this.isFilter = false;
        this.mRunnable = new 1(this);
        this.mOnClickListener = new 2(this);
        init(context);
    }

    public void setRefreshData(RefreshData refreshData) {
        this.mRefreshData = refreshData;
    }

    private void init(Context context) {
        this.context = context;
        inflate(context, R.layout.public_loading_layout, this);
        this.mDefaultTextTabNoNetTextUrl = context.getResources().getString(R.string.public_loading_no_net);
        this.mDefaultTextTabNoDataTextUrl = context.getResources().getString(R.string.public_loading_datanull);
        this.mDefaultTextTabLoadingTextUrl = context.getResources().getString(R.string.public_loading_text);
        this.mDefaultTextTabNoFilterDataTextUrl = context.getResources().getString(R.string.channel_filter_no_content);
        this.mHandler = new Handler();
        findView();
        setBackgroundColor(getResources().getColor(R.color.letv_color_fff6f6f6));
    }

    public void addContent(int viewId) {
        addContent(viewId, false);
    }

    public void addContent(int viewId, boolean toFrameLayout) {
        this.contentIsFramelayout = toFrameLayout;
        if (toFrameLayout) {
            inflate(getContext(), viewId, this.frameContent);
            this.frameContent.setVisibility(0);
            this.content.setVisibility(8);
            return;
        }
        inflate(getContext(), viewId, this.content);
        this.frameContent.setVisibility(8);
        this.content.setVisibility(0);
    }

    public View getContentView() {
        return this.contentIsFramelayout ? this.frameContent : this.content;
    }

    private void findView() {
        this.errorImage = (ImageView) findViewById(R.id.net_error_flag);
        this.content = (LinearLayout) findViewById(R.id.content);
        this.loading = findViewById(R.id.loading_layout);
        this.loadingText = (TextView) findViewById(R.id.loadingText);
        this.error = findViewById(R.id.error);
        this.fullError = findViewById(R.id.full_net_error);
        this.errorTxt1 = (TextView) findViewById(R.id.errorTxt1);
        this.errorTxt2 = (TextView) findViewById(R.id.errorTxt2);
        this.fullErrorText = (TextView) findViewById(R.id.full_episode_play_errer_retry);
        this.frameContent = (RelativeLayout) findViewById(R.id.frame_content);
        this.mLoadLayout = findViewById(R.id.public_load_layout);
        this.error.setOnClickListener(this.mOnClickListener);
        this.fullError.setOnClickListener(this.mOnClickListener);
    }

    public void loading(boolean isShowContent) {
        int i = 0;
        if (TextUtils.isEmpty(this.mDefaultTextTabLoadingTextUrl)) {
            this.loadingText.setText(R.string.public_loading_text);
        } else {
            this.loadingText.setText(this.mDefaultTextTabLoadingTextUrl);
        }
        this.loading.setVisibility(0);
        this.error.setVisibility(8);
        View contentView = getContentView();
        if (!isShowContent) {
            i = 8;
        }
        contentView.setVisibility(i);
    }

    public void loadingforHot(boolean isShowContent) {
        int i;
        int i2 = 0;
        if (TextUtils.isEmpty(this.mDefaultTextTabLoadingTextUrl)) {
            this.loadingText.setText(R.string.public_loading_text);
        } else {
            this.loadingText.setText(this.mDefaultTextTabLoadingTextUrl);
        }
        this.error.setVisibility(8);
        View view = this.loading;
        if (isShowContent) {
            i = 8;
        } else {
            i = 0;
        }
        view.setVisibility(i);
        View contentView = getContentView();
        if (!isShowContent) {
            i2 = 8;
        }
        contentView.setVisibility(i2);
    }

    public void loading(boolean isShowContent, boolean shouLoading) {
        int i = 8;
        if (TextUtils.isEmpty(this.mDefaultTextTabLoadingTextUrl)) {
            this.loadingText.setText(R.string.public_loading_text);
        } else {
            this.loadingText.setText(this.mDefaultTextTabLoadingTextUrl);
        }
        this.loading.setVisibility(8);
        this.error.setVisibility(8);
        View contentView = getContentView();
        if (isShowContent) {
            i = 0;
        }
        contentView.setVisibility(i);
    }

    public void finish() {
        this.loading.setVisibility(8);
        this.error.setVisibility(8);
        getContentView().setVisibility(0);
        this.mLoadLayout.setPadding(0, 0, 0, 0);
    }

    public void finishError() {
        this.loading.setVisibility(8);
        this.error.setVisibility(8);
    }

    public void finishLayout() {
        this.loading.setVisibility(8);
        this.error.setVisibility(8);
        setVisibility(8);
    }

    public void finishLoad() {
        this.loading.setVisibility(8);
    }

    public void netError(boolean isShowContent) {
        netError(isShowContent, true);
    }

    public void netError(boolean isShowContent, boolean isErrorTransparent) {
        this.loading.setVisibility(8);
        this.error.setVisibility(0);
        View view = this.error;
        int color = (isShowContent && isErrorTransparent) ? 0 : getResources().getColor(R.color.letv_base_bg);
        view.setBackgroundColor(color);
        view = getContentView();
        if (isShowContent) {
            color = 0;
        } else {
            color = 8;
        }
        view.setVisibility(color);
        if (TextUtils.isEmpty(this.mDefaultTextTabNoNetTextUrl)) {
            this.errorTxt1.setText(this.mDefaultTextTabNoNetTextUrl);
            return;
        }
        if (this.mDefaultTextTabNoNetTextUrl.contains("#")) {
            String[] datas = this.mDefaultTextTabNoNetTextUrl.split("#");
            if (datas.length == 2) {
                this.errorTxt1.setText(datas[0]);
                this.errorTxt2.setText(datas[1]);
                return;
            }
        }
        this.errorTxt1.setText(this.mDefaultTextTabNoNetTextUrl);
    }

    public void fullNetError(boolean fullNetError) {
        this.loading.setVisibility(8);
        this.fullError.setVisibility(0);
        this.isFullNetError = fullNetError;
    }

    public void dataError(boolean isShowContent) {
        dataError(isShowContent, true);
    }

    public void dataError(boolean isShowContent, boolean isErrorTransparent) {
        String mNoData;
        this.loading.setVisibility(8);
        this.error.setVisibility(0);
        this.errorImage.setImageResource(R.drawable.data_null);
        View view = this.error;
        int color = (isShowContent && isErrorTransparent) ? 0 : getResources().getColor(R.color.letv_base_bg);
        view.setBackgroundColor(color);
        view = getContentView();
        if (isShowContent) {
            color = 0;
        } else {
            color = 8;
        }
        view.setVisibility(color);
        if (this.isFilter) {
            mNoData = this.mDefaultTextTabNoFilterDataTextUrl;
        } else {
            mNoData = this.mDefaultTextTabNoDataTextUrl;
        }
        if (TextUtils.isEmpty(mNoData)) {
            this.errorTxt1.setText(R.string.public_loading_datanull);
            return;
        }
        if (mNoData.contains("#")) {
            String[] datas = mNoData.split("#");
            if (datas.length == 2) {
                this.errorTxt1.setText(datas[0]);
                this.errorTxt2.setText(datas[1]);
                return;
            }
        }
        this.errorTxt1.setText(mNoData);
    }

    public void dataFilterError(boolean isShowContent, boolean isFilter) {
        this.isFilter = isFilter;
        dataError(isShowContent, true);
    }

    public void error(String errMsg) {
        this.error.setBackgroundColor(getResources().getColor(R.color.letv_color_fff6f6f6));
        this.loading.setVisibility(8);
        this.errorImage.setVisibility(8);
        this.error.setVisibility(0);
        this.errorTxt1.setText(errMsg);
        getContentView().setVisibility(8);
    }

    public void showErrorMessage(String errMsg) {
        this.loading.setVisibility(8);
        this.errorImage.setVisibility(8);
        this.error.setVisibility(0);
        this.error.setBackgroundColor(0);
        this.errorTxt1.setText(errMsg);
        getContentView().setVisibility(0);
    }

    public void error(int errmsg) {
        this.loading.setVisibility(8);
        this.errorImage.setVisibility(8);
        this.error.setVisibility(0);
        this.errorTxt1.setText(errmsg);
        this.errorTxt2.setVisibility(8);
        getContentView().setVisibility(8);
    }

    public void _error(int errmsg) {
        this.loading.setVisibility(8);
        this.errorImage.setVisibility(0);
        this.error.setVisibility(0);
        this.errorTxt1.setText(errmsg);
    }

    public void chatError(int errmsg) {
        this.loading.setVisibility(8);
        this.error.setVisibility(0);
        this.errorImage.setImageResource(R.drawable.chat_error_normal);
        this.errorTxt1.setText(errmsg);
        this.errorTxt2.setVisibility(8);
        this.error.setBackgroundColor(getResources().getColor(R.color.letv_base_bg));
        getContentView().setVisibility(0);
    }

    public void dataNull(int errmsg, int errIcon) {
        this.loading.setVisibility(8);
        this.error.setVisibility(0);
        this.errorImage.setImageResource(errIcon);
        this.errorTxt1.setText(errmsg);
        this.errorTxt2.setVisibility(8);
        this.error.setBackgroundColor(getResources().getColor(R.color.letv_base_bg));
        getContentView().setVisibility(0);
    }

    public void setErrorBackgroundColor(int color) {
        this.error.setBackgroundColor(color);
    }

    public TextView getLoadingText() {
        return this.loadingText;
    }

    public void finishHalfPlay() {
        this.loading.setVisibility(8);
        getContentView().setVisibility(0);
    }

    public View getLoadlayoutView() {
        return this.mLoadLayout;
    }
}
