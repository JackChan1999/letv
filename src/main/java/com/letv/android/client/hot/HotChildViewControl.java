package com.letv.android.client.hot;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import com.letv.android.client.hot.HotChildViewList.HotListRefreshListener;
import com.letv.android.client.hot.HotVideoAdapter.HotVideoClickListener;
import com.letv.core.bean.HotTypeItemBean;
import com.letv.core.bean.HotTypeListBean;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class HotChildViewControl {
    private final Context mContext;
    private HotChildPageAdapter mHotChildPageAdapter;
    private HotTabPageIndicator mHotTabPageIndicator;
    private final HotVideoClickListener mHotVideoClickListener;
    private IHotListChangeListener mListener;
    private ViewPager mViewPager;

    public HotChildViewControl() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    private HotChildViewControl(Context context, ViewPager pager, HotTabPageIndicator indicator, HotVideoClickListener hotVideoClickListener) {
        this.mContext = context;
        this.mHotTabPageIndicator = indicator;
        this.mViewPager = pager;
        this.mHotVideoClickListener = hotVideoClickListener;
    }

    public static HotChildViewControl createControl(Context context, ViewPager pager, HotTabPageIndicator indicator, HotVideoClickListener hotVideoClickListener) {
        return new HotChildViewControl(context, pager, indicator, hotVideoClickListener);
    }

    public void setListener(IHotListChangeListener listener) {
        this.mListener = listener;
    }

    public void init(HotTypeListBean bean) {
        init(bean, "", 0);
    }

    public void init(HotTypeListBean bean, String pageId, int vid) {
        HotListRefreshListener hotListRefreshListener = new RefreshListener(this, null);
        this.mHotTabPageIndicator.setWidth(UIsUtils.getScreenWidth());
        this.mHotChildPageAdapter = new HotChildPageAdapter(this.mContext, bean.getHotpointChannel(), this.mHotVideoClickListener, hotListRefreshListener);
        this.mViewPager.setAdapter(this.mHotChildPageAdapter);
        this.mHotTabPageIndicator.setViewPager(this.mViewPager);
        if (vid > 0) {
            HotChildViewList.setSpecifyVid(vid);
        }
        String[] type = findType(bean, pageId);
        this.mHotTabPageIndicator.setOnPageChangeListener(new 1(this));
        if (!TextUtils.isEmpty(type[0])) {
            HotChildViewList.setCurrentType(type[0]);
            this.mHotTabPageIndicator.setCurrentItem(Integer.parseInt(type[1]));
        } else if (bean.getHotpointChannel().size() > 0) {
            HotChildViewList.setCurrentType(((HotTypeItemBean) bean.getHotpointChannel().get(0)).channel_name);
            this.mHotTabPageIndicator.setCurrentItem(0);
        }
    }

    private String[] findType(HotTypeListBean bean, String pageId) {
        String[] result = new String[]{"", ""};
        if (TextUtils.isEmpty(pageId)) {
            result[0] = "";
            result[1] = "-1";
        } else {
            for (int i = 0; i < bean.getHotpointChannel().size(); i++) {
                if (pageId.equals(((HotTypeItemBean) bean.getHotpointChannel().get(i)).page_id)) {
                    result[0] = ((HotTypeItemBean) bean.getHotpointChannel().get(i)).channel_name;
                    result[1] = i + "";
                    break;
                }
            }
            result[0] = "";
            result[1] = "-1";
        }
        return result;
    }
}
