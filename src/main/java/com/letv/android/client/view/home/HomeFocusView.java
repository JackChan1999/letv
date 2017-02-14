package com.letv.android.client.view.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.letv.adlib.sdk.types.AdElementMime;
import com.letv.android.client.R;
import com.letv.android.client.view.HomeFocusViewPager;
import com.letv.core.bean.HomeMetaData;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.tencent.open.yyb.TitleBar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class HomeFocusView {
    private ArrayList<AdElementMime> mAdList;
    private Context mContext;
    private List<HomeMetaData> mFocusBlocks;
    private List<Object> mFocusList;
    private LinearLayout mIndicaterView;
    private boolean mNeedMargin;
    private View mView;
    private HomeFocusViewPager mViewPager;
    private long oldUpShowTime;

    public HomeFocusView(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this(context, false);
    }

    public HomeFocusView(Context context, boolean needMargin) {
        this.mNeedMargin = false;
        this.mContext = context;
        this.mNeedMargin = needMargin;
        init();
    }

    private void init() {
        this.mView = LayoutInflater.from(this.mContext).inflate(R.layout.home_focus_layout, null);
        int screenWidth = Math.min(UIsUtils.getScreenWidth(), UIsUtils.getScreenHeight());
        if (this.mNeedMargin) {
            this.mView.setLayoutParams(new LayoutParams(-1, UIsUtils.dipToPx(133.0f)));
        } else {
            this.mView.setLayoutParams(new LayoutParams(-1, (screenWidth * 9) / 16));
        }
        this.mViewPager = (HomeFocusViewPager) this.mView.findViewById(R.id.home_focus_viewpager);
        this.mIndicaterView = (LinearLayout) this.mView.findViewById(R.id.home_focus_indicater);
        this.mViewPager.setLoopOnPageChangeListener(new 1(this));
        if (this.mNeedMargin) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.mViewPager.getLayoutParams();
            int margin = UIsUtils.dipToPx(TitleBar.SHAREBTN_RIGHT_MARGIN);
            lp.setMargins(margin, 0, margin, margin);
            this.mViewPager.setLayoutParams(lp);
            RelativeLayout.LayoutParams rLP = (RelativeLayout.LayoutParams) this.mIndicaterView.getLayoutParams();
            rLP.rightMargin = UIsUtils.dipToPx(16.0f);
            rLP.bottomMargin = UIsUtils.dipToPx(12.0f);
            this.mIndicaterView.setLayoutParams(rLP);
        }
    }

    public HomeFocusViewPager getViewPager() {
        return this.mViewPager;
    }

    public View getFocusView() {
        return this.mView;
    }

    public void setList(List<HomeMetaData> focusBlocks) {
        if (!BaseTypeUtils.isListEmpty(focusBlocks)) {
            this.mFocusBlocks = focusBlocks;
            this.mFocusList = new ArrayList();
            this.mFocusList.addAll(focusBlocks);
            if (!BaseTypeUtils.isListEmpty(this.mAdList)) {
                Iterator it = this.mAdList.iterator();
                while (it.hasNext()) {
                    AdElementMime ad = (AdElementMime) it.next();
                    if (ad != null && ad.index > 0 && ad.index <= this.mFocusList.size()) {
                        this.mFocusList.add(ad.index - 1, ad);
                    } else if (ad != null) {
                        this.mFocusList.add(ad);
                    }
                }
            }
            if (this.mNeedMargin) {
                this.mViewPager.setMarginFlag(true);
            }
            this.mViewPager.setDataForPager(this.mFocusList);
        }
    }

    private void updataIndicaterView(int position) {
        if (BaseTypeUtils.isListEmpty(this.mFocusList) || this.mFocusList.size() == 1) {
            this.mIndicaterView.setVisibility(8);
            return;
        }
        this.mIndicaterView.setVisibility(0);
        this.mIndicaterView.removeAllViews();
        this.mIndicaterView.setPadding(0, 0, 0, UIsUtils.dipToPx(2.0f));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIsUtils.dipToPx(4.0f), UIsUtils.dipToPx(4.0f));
        params.setMargins(UIsUtils.dipToPx(3.0f), UIsUtils.dipToPx(4.0f), UIsUtils.dipToPx(3.0f), UIsUtils.dipToPx(4.0f));
        int count = this.mFocusList.size();
        int i = 0;
        while (i < count) {
            ImageView imageView = new ImageView(this.mContext);
            imageView.setImageResource(i == position ? 2130838111 : 2130838110);
            this.mIndicaterView.addView(imageView, params);
            i++;
        }
    }

    public void setAdList(ArrayList<AdElementMime> adList) {
        if (!BaseTypeUtils.isListEmpty(adList)) {
            this.mAdList = new ArrayList();
            try {
                Collections.sort(adList, new 2(this));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!BaseTypeUtils.isListEmpty(adList)) {
                this.mAdList.addAll(adList);
                setList(this.mFocusBlocks);
            }
        } else if (!BaseTypeUtils.isListEmpty(this.mAdList)) {
            this.mAdList = null;
            setList(this.mFocusBlocks);
        }
    }

    public void destroy() {
        if (this.mViewPager != null) {
            this.mViewPager.destroy();
            this.mViewPager = null;
        }
        if (this.mFocusList != null) {
            this.mFocusBlocks.clear();
            this.mFocusBlocks = null;
        }
        this.mView = null;
    }
}
