package com.letv.redpacketsdk.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import com.letv.redpacketsdk.R;
import com.letv.redpacketsdk.RedPacketSdkManager;
import com.letv.redpacketsdk.bean.RedPacketBean;
import com.letv.redpacketsdk.callback.AsyncTaskImageLoaderCallback;
import com.letv.redpacketsdk.callback.ClickCallBack;
import com.letv.redpacketsdk.callback.RedPacketDialogDisplayCallback;
import com.letv.redpacketsdk.net.NetworkManager;
import com.letv.redpacketsdk.net.statistics.StatisticsUtil;
import com.letv.redpacketsdk.utils.DensityUtil;
import com.letv.redpacketsdk.utils.LogInfo;

public class RedPacketForecastView extends ImageView {
    private ClickCallBack mClickCallback;
    private Context mContext;
    private RedPacketDialogDisplayCallback mDialogDisplayCallback;
    private RedPacketBean mForecastBean = new RedPacketBean();

    public RedPacketForecastView(Context context, RedPacketBean bean) {
        super(context);
        this.mContext = context;
        this.mForecastBean = bean;
        init();
    }

    private void init() {
        LogInfo.log("RedPacketForecastView", "init");
        if (RedPacketBean.TYPE_FORCAST.equals(this.mForecastBean.type) && !TextUtils.isEmpty(this.mForecastBean.pic1) && !TextUtils.isEmpty(this.mForecastBean.mobilePic)) {
            NetworkManager.loadImage(this.mForecastBean.pic1, new AsyncTaskImageLoaderCallback() {
                public void imageDownloadResult(Bitmap bitmap) {
                    if (bitmap != null) {
                        RedPacketForecastView.this.setDate(bitmap);
                    }
                }
            });
        }
    }

    private void setDate(Bitmap bitmap) {
        LogInfo.log("RedPacketForecastView", "setDate");
        LayoutParams params = new LayoutParams(-2, -2);
        params.width = DensityUtil.px2dip(RedPacketSdkManager.getInstance().getApplicationContext(), 74.0f);
        params.height = DensityUtil.px2dip(RedPacketSdkManager.getInstance().getApplicationContext(), 74.0f);
        setLayoutParams(params);
        setImageBitmap(bitmap);
        setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                LogInfo.log("RedPacketForecastView", "onclick");
                StatisticsUtil.statistics(11);
                if (RedPacketForecastView.this.mContext != null) {
                    new RedPacketForecastDialog(RedPacketForecastView.this.mContext, RedPacketForecastView.this.mForecastBean, RedPacketForecastView.this.mDialogDisplayCallback).show();
                }
                RedPacketForecastView.this.setVisibility(8);
                if (RedPacketForecastView.this.mClickCallback != null) {
                    RedPacketForecastView.this.mClickCallback.onClick();
                }
            }
        });
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == 0) {
            setAnimation(AnimationUtils.loadAnimation(RedPacketSdkManager.getInstance().getApplicationContext(), R.anim.forecast_view_show));
            getAnimation().start();
        }
    }

    public void setOnClickCallBack(ClickCallBack callBack) {
        this.mClickCallback = callBack;
    }

    public void setDialogDisplayCallback(RedPacketDialogDisplayCallback callback) {
        this.mDialogDisplayCallback = callback;
    }

    public void clean() {
        this.mContext = null;
    }
}
