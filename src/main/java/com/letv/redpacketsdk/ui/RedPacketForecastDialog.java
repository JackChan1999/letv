package com.letv.redpacketsdk.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.letv.redpacketsdk.R;
import com.letv.redpacketsdk.RedPacketSdkManager;
import com.letv.redpacketsdk.bean.RedPacketBean;
import com.letv.redpacketsdk.callback.AsyncTaskImageLoaderCallback;
import com.letv.redpacketsdk.callback.RedPacketDialogDisplayCallback;
import com.letv.redpacketsdk.net.NetworkManager;
import com.letv.redpacketsdk.net.statistics.StatisticsUtil;

public class RedPacketForecastDialog extends Dialog {
    private RedPacketBean mBean;
    private RedPacketDialogDisplayCallback mDialogDisplayCallback;
    private ImageView mforecastView;

    public RedPacketForecastDialog(Context context, RedPacketBean bean, RedPacketDialogDisplayCallback dialogDisplayCallback) {
        super(context, R.style.redpacket_dialog);
        this.mBean = bean;
        this.mDialogDisplayCallback = dialogDisplayCallback;
        getWindow().setGravity(17);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.red_packet_forecast_layout);
        this.mforecastView = (ImageView) findViewById(R.id.forecast_view);
        NetworkManager.loadImage(this.mBean.mobilePic, new AsyncTaskImageLoaderCallback() {
            public void imageDownloadResult(Bitmap bitmap) {
                RedPacketForecastDialog.this.setImage(bitmap);
            }
        });
        this.mforecastView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                RedPacketForecastDialog.this.dismiss();
            }
        });
    }

    private void setImage(Bitmap bitmap) {
        this.mforecastView.setImageBitmap(bitmap);
    }

    public void show() {
        try {
            super.show();
            if (this.mDialogDisplayCallback != null) {
                this.mDialogDisplayCallback.onShow();
            }
            StatisticsUtil.statistics(12);
            Animation anima = AnimationUtils.loadAnimation(RedPacketSdkManager.getInstance().getApplicationContext(), R.anim.forecast_view_show);
            this.mforecastView.setAnimation(anima);
            anima.start();
        } catch (Exception e) {
        }
    }

    public void dismiss() {
        super.dismiss();
        if (this.mDialogDisplayCallback != null) {
            this.mDialogDisplayCallback.onDismiss();
        }
    }
}
