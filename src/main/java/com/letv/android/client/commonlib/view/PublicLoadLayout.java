package com.letv.android.client.commonlib.view;

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
import com.letv.android.client.commonlib.R;
import com.letv.core.BaseApplication;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;

public class PublicLoadLayout extends RelativeLayout {
    private static final String CUSTOM_CARD_NO_DATA_KEY = "700093";
    private static final String FILTER_NO_DATA_MSG_KEY = "700052";
    private static final String FILTER_NO_TYPE = "700072";
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
    private View finishNoComment;
    private View finishNoReply;
    private RelativeLayout frameContent;
    private View fullError;
    private boolean isFilter;
    private boolean isFilterTypeNull;
    private boolean isFullNetError;
    private View loading;
    private TextView loadingText;
    private String mCustomCardDataError;
    private String mDefaultTextFilterTypeNull;
    private String mDefaultTextTabLoadingTextUrl;
    private String mDefaultTextTabNoDataTextUrl;
    private String mDefaultTextTabNoFilterDataTextUrl;
    private String mDefaultTextTabNoNetTextUrl;
    private Handler mHandler;
    private View mLoadLayout;
    OnClickListener mOnClickListener;
    private RefreshData mRefreshData;
    final Runnable mRunnable;

    public interface RefreshData {
        void refreshData();
    }

    public static PublicLoadLayout createPage(Context context, int layoutId) {
        return createPage(context, layoutId, false);
    }

    public static PublicLoadLayout createPage(Context context, int layoutId, boolean isToFrameLayout) {
        PublicLoadLayout rootView = new PublicLoadLayout(context);
        rootView.addContent(layoutId, isToFrameLayout);
        return rootView;
    }

    public static PublicLoadLayout createPage(Context context, View view) {
        return createPage(context, view, false);
    }

    public static PublicLoadLayout createPage(Context context, View view, boolean isToFrameLayout) {
        PublicLoadLayout rootView = new PublicLoadLayout(context);
        rootView.addContent(view, isToFrameLayout);
        return rootView;
    }

    public PublicLoadLayout(Context context) {
        this(context, null);
    }

    public PublicLoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.contentIsFramelayout = false;
        this.isFilter = false;
        this.isFilterTypeNull = false;
        this.mRunnable = new Runnable() {
            public void run() {
                PublicLoadLayout.this.loading.setVisibility(8);
                if (PublicLoadLayout.this.isFullNetError) {
                    PublicLoadLayout.this.fullError.setVisibility(0);
                } else {
                    PublicLoadLayout.this.error.setVisibility(0);
                }
                PublicLoadLayout.this.mHandler.removeCallbacks(PublicLoadLayout.this.mRunnable);
            }
        };
        this.mOnClickListener = new OnClickListener() {
            public void onClick(View v) {
                if (!NetworkUtils.isNetworkAvailable()) {
                    PublicLoadLayout.this.loading.setVisibility(0);
                    PublicLoadLayout.this.fullError.setVisibility(8);
                    PublicLoadLayout.this.error.setVisibility(8);
                    PublicLoadLayout.this.mHandler.postDelayed(PublicLoadLayout.this.mRunnable, 1000);
                } else if (PublicLoadLayout.this.mRefreshData == null) {
                } else {
                    if (PublicLoadLayout.this.error.getVisibility() == 0 || PublicLoadLayout.this.fullError.getVisibility() == 0) {
                        PublicLoadLayout.this.mRefreshData.refreshData();
                        PublicLoadLayout.this.fullError.setVisibility(8);
                    }
                }
            }
        };
        init(context);
    }

    public void setRefreshData(RefreshData refreshData) {
        this.mRefreshData = refreshData;
    }

    private void init(Context context) {
        this.context = context;
        inflate(context, R.layout.public_loading_layout, this);
        this.mDefaultTextTabNoNetTextUrl = TipUtils.getTipMessage(NO_NET_MSG_KEY, R.string.public_loading_no_net);
        this.mDefaultTextTabNoDataTextUrl = TipUtils.getTipMessage(NO_DATA_MSG_KEY, R.string.public_loading_datanull);
        this.mDefaultTextTabLoadingTextUrl = TipUtils.getTipMessage(LOADING_MSG_KEY, R.string.public_loading_text);
        this.mDefaultTextTabNoFilterDataTextUrl = TipUtils.getTipMessage(FILTER_NO_DATA_MSG_KEY, R.string.channel_filter_no_content);
        this.mDefaultTextFilterTypeNull = TipUtils.getTipMessage(FILTER_NO_TYPE, R.string.filter_no_content);
        this.mCustomCardDataError = TipUtils.getTipMessage("700093", R.string.custom_card_data_error);
        this.mHandler = new Handler();
        findView();
        if (isAlbumFull()) {
            setBackgroundColor(getResources().getColor(R.color.letv_color_ffffff));
        }
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

    public void addContent(View view, boolean toFrameLayout) {
        this.contentIsFramelayout = toFrameLayout;
        if (toFrameLayout) {
            this.frameContent.addView(view);
            this.frameContent.setVisibility(0);
            this.content.setVisibility(8);
            return;
        }
        this.content.addView(view);
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
        this.finishNoReply = findViewById(R.id.rl_no_more_reply_layout);
        this.loadingText = (TextView) findViewById(R.id.loadingText);
        this.error = findViewById(R.id.error);
        this.fullError = findViewById(R.id.full_net_error);
        this.errorTxt1 = (TextView) findViewById(R.id.errorTxt1);
        this.errorTxt2 = (TextView) findViewById(R.id.errorTxt2);
        this.frameContent = (RelativeLayout) findViewById(R.id.frame_content);
        this.mLoadLayout = findViewById(R.id.public_load_layout);
        this.error.setOnClickListener(this.mOnClickListener);
        this.fullError.setOnClickListener(this.mOnClickListener);
        if (isAlbumFull()) {
            this.loading.setBackgroundColor(0);
        }
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

    public void finishLoadForNoReply() {
        this.loading.setVisibility(8);
        this.finishNoReply.setVisibility(0);
        this.error.setVisibility(8);
    }

    public void netError(boolean isShowContent) {
        netError(isShowContent, true);
    }

    public void netError(boolean isShowComtent, boolean isShowIcon, int titleColor, int subTitleColor) {
        newError(isShowComtent, true, isShowIcon, titleColor, subTitleColor);
    }

    public void netError(boolean isShowContent, boolean isErrorTransparent) {
        newError(isShowContent, isErrorTransparent, true, getResources().getColor(R.color.letv_color_444444), getResources().getColor(R.color.letv_color_ffa1a1a1));
    }

    public void newError(boolean isShowContent, boolean isErrorTransparent, boolean isShowIcon, int titleColor, int subTitleColor) {
        int i = 8;
        this.loading.setVisibility(8);
        this.errorImage.setImageResource(R.drawable.net_null_normal);
        this.errorImage.setVisibility(isShowIcon ? 0 : 8);
        this.error.setVisibility(0);
        this.errorTxt1.setTextColor(titleColor);
        this.errorTxt2.setTextColor(subTitleColor);
        if (isAlbumFull()) {
            this.error.setBackgroundColor(0);
        } else {
            View view = this.error;
            int color = (isShowContent && isErrorTransparent) ? 0 : getResources().getColor(R.color.letv_base_bg);
            view.setBackgroundColor(color);
        }
        View contentView = getContentView();
        if (isShowContent) {
            i = 0;
        }
        contentView.setVisibility(i);
        if (!TextUtils.isEmpty(this.mDefaultTextTabNoNetTextUrl)) {
            String[] datas;
            if (this.mDefaultTextTabNoNetTextUrl.contains("#")) {
                datas = this.mDefaultTextTabNoNetTextUrl.split("#");
                if (datas.length == 2) {
                    this.errorTxt1.setText(datas[0]);
                    this.errorTxt2.setText(datas[1]);
                    this.errorTxt2.setVisibility(0);
                    return;
                }
            }
            if (this.mDefaultTextTabNoNetTextUrl.contains("\n")) {
                datas = this.mDefaultTextTabNoNetTextUrl.split("\n");
                if (datas.length == 2) {
                    this.errorTxt1.setText(datas[0]);
                    this.errorTxt2.setText(datas[1]);
                    this.errorTxt2.setVisibility(0);
                    return;
                }
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

    private void dataError(boolean isShowContent, boolean isErrorTransparent) {
        dataError(isShowContent, isErrorTransparent, getResources().getColor(R.color.letv_color_444444), getResources().getColor(R.color.letv_color_ffa1a1a1));
    }

    public boolean isLoading() {
        return this.loading.getVisibility() == 0;
    }

    public void dataError(boolean isShowContent, boolean isErrorTransparent, int titleColor, int subTitleColor) {
        View view;
        int color;
        String mNoData;
        this.loading.setVisibility(8);
        this.error.setVisibility(0);
        this.errorImage.setImageResource(R.drawable.data_null);
        if (isAlbumFull()) {
            this.error.setBackgroundColor(0);
        } else {
            view = this.error;
            color = (isShowContent && isErrorTransparent) ? 0 : getResources().getColor(R.color.letv_base_bg);
            view.setBackgroundColor(color);
        }
        this.errorTxt1.setTextColor(titleColor);
        this.errorTxt2.setTextColor(subTitleColor);
        view = getContentView();
        if (isShowContent) {
            color = 0;
        } else {
            color = 8;
        }
        view.setVisibility(color);
        if (!this.isFilter) {
            mNoData = this.mDefaultTextTabNoDataTextUrl;
        } else if (this.isFilterTypeNull) {
            mNoData = this.mDefaultTextFilterTypeNull;
        } else {
            mNoData = this.mDefaultTextTabNoFilterDataTextUrl;
        }
        if (TextUtils.isEmpty(mNoData)) {
            this.errorTxt1.setText(R.string.public_loading_datanull);
            return;
        }
        String[] datas;
        if (mNoData.contains("#")) {
            datas = mNoData.split("#");
            if (datas.length == 2) {
                this.errorTxt1.setText(datas[0]);
                this.errorTxt2.setText(datas[1]);
                return;
            }
        }
        if (mNoData.contains("\n")) {
            datas = mNoData.split("\n");
            if (datas.length == 2) {
                this.errorTxt1.setText(datas[0]);
                this.errorTxt2.setText(datas[1]);
                return;
            }
        }
        this.errorTxt1.setText(mNoData);
    }

    public void dataFilterError(boolean isShowContent, boolean isFilter, boolean filterTypeNull) {
        this.isFilter = isFilter;
        this.isFilterTypeNull = filterTypeNull;
        dataError(isShowContent, true);
    }

    public void error(String errMsg) {
        if (isAlbumFull()) {
            this.error.setBackgroundColor(0);
        } else {
            this.error.setBackgroundColor(getResources().getColor(R.color.letv_color_fff6f6f6));
        }
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

    public void cardError() {
        this.loading.setVisibility(8);
        this.error.setVisibility(0);
        this.error.setOnClickListener(null);
        this.fullError.setOnClickListener(null);
        this.errorImage.setImageResource(R.drawable.data_null);
        String mNoData = this.mCustomCardDataError;
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

    public void chatError(int errmsg) {
        this.loading.setVisibility(8);
        this.error.setVisibility(0);
        this.errorImage.setImageResource(R.drawable.chat_error_normal);
        this.errorTxt1.setText(errmsg);
        this.errorTxt2.setVisibility(8);
        this.error.setBackgroundColor(getResources().getColor(R.color.letv_base_bg));
        getContentView().setVisibility(0);
    }

    public void refreshLiveBookError(int errmsg) {
        this.loading.setVisibility(8);
        this.error.setVisibility(0);
        this.errorImage.setImageResource(R.drawable.book_error_normal);
        this.errorTxt1.setText(errmsg);
        this.errorTxt2.setText(R.string.live_book_null_msg);
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
        if (isAlbumFull()) {
            this.error.setBackgroundColor(0);
        } else {
            this.error.setBackgroundColor(color);
        }
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

    private boolean isAlbumFull() {
        return (BaseApplication.getInstance().mIsAlbumActivityAlive && UIsUtils.isLandscape(getContext())) ? false : true;
    }
}
