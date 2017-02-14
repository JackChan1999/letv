package com.letv.android.client.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.letv.adlib.sdk.types.AdElementMime;
import com.letv.ads.ex.ui.AdViewProxy;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.utils.UIControllerUtils;
import com.letv.android.client.fragment.HomeBaseFragment.CurrentPage;
import com.letv.android.client.utils.EpisodeTitleUtils;
import com.letv.core.bean.HomeMetaData;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.download.image.ImageDownloader.BitmapStyle;
import com.letv.core.download.image.ImageDownloader.CustomConfig;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.CategoryCode;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.pp.utils.NetworkUtils;

public class HomeFocusViewPager extends LoopViewPager<Object> {
    private final int DELAY_MILLIS;
    private final int VIEW_MOVE_TAG;
    private Context mContext;
    private CurrentPage mCurrentPage;
    private Handler mHandler;
    private ImageDownloader mImageDownloader;
    private boolean mIsStartMove;
    private boolean mNeedMargin;
    private ViewPager mPager;
    private PullToRefreshExpandableListView mPullView;

    public HomeFocusViewPager(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this(context, null);
    }

    public HomeFocusViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.VIEW_MOVE_TAG = 1;
        this.DELAY_MILLIS = 5000;
        this.mCurrentPage = CurrentPage.NORMAL;
        this.mIsStartMove = false;
        this.mNeedMargin = false;
        this.mHandler = new 1(this);
        this.mContext = context;
        this.mImageDownloader = ImageDownloader.getInstance();
    }

    public void setCurrentPage(CurrentPage page) {
        this.mCurrentPage = page;
    }

    public void setMarginFlag(boolean needMargin) {
        this.mNeedMargin = needMargin;
    }

    protected View fetchView(Object obj) {
        if (obj == null) {
            return new View(this.mContext);
        }
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.home_focus_item_view, null);
        AdViewProxy adView = (AdViewProxy) view.findViewById(R.id.gallery_item_ad);
        View imageView = (ImageView) view.findViewById(R.id.channel_top_gallery_item_picture);
        LinearLayout titleLayout = (LinearLayout) view.findViewById(R.id.title_layout);
        if (obj instanceof HomeMetaData) {
            TextView titleView = (TextView) view.findViewById(R.id.channel_top_gallery_item_title);
            TextView subTitleView = (TextView) view.findViewById(R.id.channel_top_gallery_item_subtitle);
            imageView.setVisibility(0);
            titleLayout.setVisibility(0);
            adView.setVisibility(8);
            HomeMetaData homeMetaData = (HomeMetaData) obj;
            if (this.mNeedMargin) {
                this.mImageDownloader.download(imageView, homeMetaData.pic169, 2130838713, new CustomConfig(BitmapStyle.CORNER, UIsUtils.dipToPx(2.0f), true));
                titleLayout.setBackgroundResource(2130838971);
            } else {
                this.mImageDownloader.download(imageView, homeMetaData.pic169, 2130838798, ScaleType.FIT_XY);
            }
            if (this.mCurrentPage == CurrentPage.HOME) {
                titleLayout.setVisibility(8);
            } else {
                EpisodeTitleUtils.setTitle(homeMetaData.nameCn, titleLayout, titleView, homeMetaData.subTitle, subTitleView);
            }
            imageView.setOnClickListener(new 2(this, homeMetaData));
            return view;
        } else if (!(obj instanceof AdElementMime)) {
            return view;
        } else {
            imageView.setVisibility(8);
            titleLayout.setVisibility(8);
            adView.setVisibility(0);
            doAdViewShow((AdElementMime) obj, adView);
            return view;
        }
    }

    private void doChannelFocusClick(HomeMetaData homeMetaData) {
        if (homeMetaData != null && this.mContext != null) {
            UIControllerUtils.gotoActivity(this.mContext, homeMetaData, homeMetaData.isPanorama() ? VideoType.Panorama : VideoType.Normal);
            String titleText = homeMetaData.nameCn;
            if (titleText == null || titleText.equals("")) {
                titleText = NetworkUtils.DELIMITER_LINE;
            }
            if (!BaseTypeUtils.isListEmpty(this.mList)) {
                StatisticsUtils.setActionProperty(CategoryCode.CHANNEL_CONTENT_HOME_FOCUS, (this.mPosition % this.mList.size()) + 1, PageIdConstant.getPageIdByChannelId(homeMetaData.cid), "", String.valueOf(homeMetaData.pageid));
                StatisticsUtils.sPlayStatisticsRelateInfo.mIsRecommend = homeMetaData.isRec();
                StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.getPageIdByChannelId(homeMetaData.cid), "0", CategoryCode.CHANNEL_CONTENT_HOME_FOCUS, titleText, (this.mPosition % this.mList.size()) + 1, "scid=" + homeMetaData.pageid, homeMetaData.cid + "", homeMetaData.pid + "", homeMetaData.vid + "", null, null);
            }
        }
    }

    private void doHomeFocusClick(HomeMetaData homeMetaData) {
        if (homeMetaData != null && this.mContext != null) {
            StatisticsUtils.setActionProperty("11", this.mPosition + 1, PageIdConstant.index);
            StatisticsUtils.sPlayStatisticsRelateInfo.mIsRecommend = homeMetaData.isRec();
            String titleText = TextUtils.isEmpty(homeMetaData.nameCn) ? NetworkUtils.DELIMITER_LINE : homeMetaData.nameCn;
            String pid = homeMetaData.pid == -1 ? NetworkUtils.DELIMITER_LINE : homeMetaData.pid + "";
            String vid = homeMetaData.vid == -1 ? NetworkUtils.DELIMITER_LINE : homeMetaData.vid + "";
            LogInfo.LogStatistics("home gallery image click:block.vid = " + homeMetaData.vid + " ,block.cid=" + homeMetaData.cid);
            StatisticsUtils.staticticsInfoPost(this.mContext.getApplicationContext(), "0", "11", titleText, this.mPosition + 1, null, PageIdConstant.index, "99", pid, vid, null, homeMetaData.zid + "");
            LogInfo.log("+-->", "---HomeGallery  getView---");
            UIControllerUtils.gotoActivity(this.mContext, homeMetaData, this.mPosition, 2);
        }
    }

    private void doAdViewShow(AdElementMime adi, AdViewProxy adView) {
        if (adi != null && adView != null) {
            adView.setVisibility(0);
            adView.setClientListener(new 3(this, adi));
            adView.showAD(adi);
            adView.setAdMaterialLoadListener(new 4(this));
        }
    }

    public void setParentView(ViewPager view, PullToRefreshExpandableListView listView) {
        this.mPager = view;
        this.mPullView = listView;
    }

    public void startMove() {
        if (!this.mIsStartMove) {
            this.mIsStartMove = true;
            this.mHandler.sendEmptyMessageDelayed(1, 5000);
        }
    }

    public void stopMove() {
        this.mIsStartMove = false;
        this.mHandler.removeMessages(1);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        doTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        doTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        doTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    private void doTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case 0:
            case 2:
                stopMove();
                if (this.mPager != null) {
                    this.mPager.requestDisallowInterceptTouchEvent(true);
                }
                if (this.mPullView != null) {
                    this.mPullView.requestDisallowInterceptTouchEvent(true);
                    return;
                }
                return;
            case 1:
            case 3:
                startMove();
                if (this.mPager != null) {
                    this.mPager.requestDisallowInterceptTouchEvent(false);
                }
                if (this.mPullView != null) {
                    this.mPullView.requestDisallowInterceptTouchEvent(false);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void destroy() {
        if (this.mHandler != null) {
            this.mHandler.removeMessages(1);
        }
        removeAllViews();
        if (this.mList != null) {
            this.mList.clear();
        }
    }
}
