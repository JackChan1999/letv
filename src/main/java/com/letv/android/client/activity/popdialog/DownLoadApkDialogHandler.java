package com.letv.android.client.activity.popdialog;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.utils.UIs;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.ExchangePopBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.ExchangePopParser;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class DownLoadApkDialogHandler extends DialogHandler {
    private static final String TAG = DownLoadApkDialogHandler.class.getSimpleName();
    private Context mContext;

    public DownLoadApkDialogHandler(MainActivity mainActivity) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(mainActivity);
        this.mContext = LetvApplication.getInstance();
    }

    public void handleRequest() {
        setFragmentRecordVisiable(false);
        showHomeRecommend(false);
        if (!isShowDownloadApk()) {
            LogInfo.log(TAG, "DownLoadApkDialogHandler handleRequest end");
            setFragmentRecordVisiable(true);
            showHomeRecommend(true);
            if (getSuccessor() != null) {
                getSuccessor().handleRequest();
            }
        }
    }

    private boolean isShowDownloadApk() {
        LogInfo.log(TAG, "isShowDownloadApk isPopRecommendSwitch >>" + PreferencesManager.getInstance().isPopRecommendSwitch());
        if (PreferencesManager.getInstance().isPopRecommendSwitch()) {
            if (System.currentTimeMillis() - PreferencesManager.getInstance().getLastExchangePopTime() <= 86400000) {
                return false;
            }
            PreferencesManager.getInstance().setLastExchangePopTime(System.currentTimeMillis());
            LogInfo.log(TAG, "apk111------------->>>>");
            requestExchangePopTask();
            return true;
        }
        setFragmentRecordVisiable(true);
        showHomeRecommend(true);
        return false;
    }

    private void requestExchangePopTask() {
        String url = LetvUrlMaker.getExchangePopUrl();
        LogInfo.log(TAG, "requestExchangePopTask url : " + url);
        new LetvRequest(ExchangePopBean.class).setUrl(url).setParser(new ExchangePopParser()).setCache(new VolleyNoCache()).setCallback(new 1(this)).add();
    }

    private void showDownloadLogin(Activity activity, ExchangePopBean exchangePopBean) {
        if (TextUtils.isEmpty(exchangePopBean.icon) || TextUtils.isEmpty(exchangePopBean.name) || TextUtils.isEmpty(exchangePopBean.url) || activity.isFinishing()) {
            setFragmentRecordVisiable(true);
            return;
        }
        setFragmentRecordVisiable(false);
        try {
            if (activity instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) activity;
                StatisticsUtils.staticticsInfoPost(activity, "19", "17", null, -1, null, mainActivity.getCurrentPageId(), null, null, null, null, null);
                LogInfo.LogStatistics("OnshowDownload:pageid=" + mainActivity.getCurrentPageId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Dialog dialog = new Builder(activity).create();
        dialog.setCanceledOnTouchOutside(true);
        View view = UIs.inflate(activity, R.layout.exchange_pop_dialog, null);
        TextView close = (TextView) view.findViewById(R.id.exchange_pop_close);
        View icon = (ImageView) view.findViewById(R.id.exchange_pop_icon);
        View download = view.findViewById(R.id.exchange_pop_download);
        ImageDownloader.getInstance().download(icon, exchangePopBean.icon);
        close.setOnClickListener(new 2(this, dialog, activity));
        download.setOnClickListener(new 3(this, activity, exchangePopBean, dialog));
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setContentView(view);
        dialog.getWindow().setLayout((int) ((float) UIs.zoomWidth(280)), -2);
        setThreeDialogShowAlready(true);
    }
}
