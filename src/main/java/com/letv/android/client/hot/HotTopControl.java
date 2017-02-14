package com.letv.android.client.hot;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.utils.UIs;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.HotAddTopBean;
import com.letv.core.bean.HotVideoBean;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.HotAddTopParse;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class HotTopControl {
    private static String TAG = "HotTopControl";
    private Context mContext;
    private HotVideoAdapter mHotVideoAdapter;

    public interface OnAddHotTopListener {
        void OnAddHotTop(HotVideoBean hotVideoBean, View view, ImageView imageView, int i);

        void OnAddHotTread(HotVideoBean hotVideoBean, TextView textView, ImageView imageView, int i);
    }

    public HotTopControl(Context context, HotVideoAdapter hotVideoAdapter) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mContext = context;
        this.mHotVideoAdapter = hotVideoAdapter;
    }

    public void addTopCount(String vid, int count, TextView view, ImageView image, int position) {
        int index = HotTopDbHelper.isExist(this.mContext, vid);
        if (index != -1) {
            if (index == 1) {
                ToastUtils.showToast(this.mContext, 2131100180);
            } else if (index == 2) {
                ToastUtils.showToast(this.mContext, 2131100181);
            } else {
                requestAddToporTread(vid, count, "1", view, image, position);
            }
        }
    }

    public void addTreadCount(String vid, int count, TextView view, ImageView image, int position) {
        int index = HotTopDbHelper.isExist(this.mContext, vid);
        if (index != -1) {
            if (index == 1) {
                ToastUtils.showToast(this.mContext, 2131100180);
            } else if (index == 2) {
                ToastUtils.showToast(this.mContext, 2131100181);
            } else {
                requestAddToporTread(vid, count, "2", view, image, position);
            }
        }
    }

    private void requestAddToporTread(String vid, int count, String act, TextView view, ImageView image, int position) {
        new LetvRequest(HotAddTopBean.class).setUrl(LetvUrlMaker.getHotAddUpListUrl(vid, act)).setNeedCheckToken(true).setRequestType(RequestManner.NETWORK_ONLY).setCache(new VolleyNoCache()).setParser(new HotAddTopParse()).setCallback(new 1(this, count, view, image, vid, act, position)).add();
    }

    public void onAddTopCount(HotAddTopBean result, int count, TextView view, ImageView image, String vid, String act, int position) {
        if (result != null && HotTopDbHelper.isExist(this.mContext, vid) == 0) {
            LogInfo.log("HotChildViewList||wlx", "onAddTopCount result  " + result.code);
            if (result.code == 200) {
                view.setText(String.valueOf(count + 1));
                view.setTextColor(this.mContext.getResources().getColor(2131493280));
                LogInfo.log(TAG + "||wlx", "请求点赞成功");
                if ("1".equals(act)) {
                    image.setBackgroundResource(2130838204);
                    HotTopDbHelper.insert(this.mContext, vid, 1);
                } else if ("2".equals(act)) {
                    image.setBackgroundResource(2130838202);
                    HotTopDbHelper.insert(this.mContext, vid, 2);
                }
                UIs.animTop(this.mContext, image);
                this.mHotVideoAdapter.setVideoTopCount(vid, String.valueOf(count + 1), true, act);
            } else {
                LogInfo.log(TAG + "||wlx", "请求点赞失败");
            }
            HotVideoBean video = (HotVideoBean) this.mHotVideoAdapter.getItem(position);
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", "c31", null, position + 1, null, PageIdConstant.hotIndexCategoryPage, "", video.pid, video.id + "", null, null);
        }
    }
}
