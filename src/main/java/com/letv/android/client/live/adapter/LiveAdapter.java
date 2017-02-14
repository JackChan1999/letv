package com.letv.android.client.live.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.letv.android.client.R;
import com.letv.android.client.live.bean.LivePageBean;
import com.letv.android.client.live.view.LiveRemenComingSoonView;
import com.letv.android.client.live.view.LiveRemenFantasticView;
import com.letv.android.client.live.view.LiveRemenLunboView;
import com.letv.core.bean.LiveBeanLeChannelProgramList;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.HashMap;
import java.util.Set;

public class LiveAdapter extends BaseAdapter {
    private final int TYPE_FANTASTIC;
    private final int TYPE_LUNBO;
    private final int TYPE_ORDER;
    private final int TYPE_UNKNOWN;
    private final int TYPE_WEISHI;
    private HashMap<Integer, View> items;
    private Set<String> mBookedItems;
    private Context mContext;
    private LiveBeanLeChannelProgramList mLunboProgramList;
    private LivePageBean mPageBean;
    private LiveBeanLeChannelProgramList mWeishiProgramList;

    public LiveAdapter(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.TYPE_UNKNOWN = 0;
        this.TYPE_ORDER = 1;
        this.TYPE_LUNBO = 2;
        this.TYPE_WEISHI = 3;
        this.TYPE_FANTASTIC = 4;
        this.mContext = context;
    }

    public void setPageBean(LivePageBean pageBean) {
        this.mPageBean = pageBean;
        this.items = new HashMap();
    }

    public void setPrograms(boolean isLunbo, LiveBeanLeChannelProgramList programs) {
        if (isLunbo) {
            this.mLunboProgramList = programs;
        } else {
            this.mWeishiProgramList = programs;
        }
        for (int i = 0; i < getCount(); i++) {
            if (getItemViewType(i) == (isLunbo ? 2 : 3)) {
                View view = (View) this.items.get(Integer.valueOf(i));
                if (view != null) {
                    ((LiveRemenLunboView) view).setPrograms(programs);
                }
            }
        }
    }

    public void setBookedPrograms(Set<String> mBookedPrograms) {
        this.mBookedItems = mBookedPrograms;
        for (int i = 0; i < getCount(); i++) {
            if (getItemViewType(i) == 1) {
                View view = (View) this.items.get(Integer.valueOf(i));
                if (view != null) {
                    ((LiveRemenComingSoonView) view).setBookedData(this.mBookedItems);
                }
            }
        }
    }

    public int getCount() {
        int number = 0;
        if (this.mPageBean == null) {
            return 0;
        }
        if (this.mPageBean.mOrderData != null) {
            number = 0 + 1;
        }
        if (this.mPageBean.mLunboData != null) {
            number++;
        }
        if (this.mPageBean.mWeiShiData != null) {
            number++;
        }
        if (this.mPageBean.mFantasticData != null) {
            return number + 1;
        }
        return number;
    }

    public Object getItem(int position) {
        return this.items == null ? null : (View) this.items.get(Integer.valueOf(position));
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LogInfo.log("pjf", position + "  " + getItemViewType(position));
        if (getItemViewType(position) == 1) {
            LiveRemenComingSoonView view = (LiveRemenComingSoonView) LayoutInflater.from(this.mContext).inflate(R.layout.view_live_remen_comingsoon, parent, false);
            view.init(this.mContext);
            view.setData(this.mPageBean.mOrderData);
            if (this.mBookedItems != null) {
                view.setBookedData(this.mBookedItems);
            }
            view.setTitle(LetvUtils.isInHongKong() ? 2131100287 : 2131100285);
            convertView = view;
        } else if (getItemViewType(position) == 2) {
            view = (LiveRemenLunboView) LayoutInflater.from(this.mContext).inflate(R.layout.view_live_remen_lunbo, parent, false);
            view.init(this.mContext);
            view.setData(this.mPageBean.mLunboData, true);
            view.setTitle(LetvUtils.isInHongKong() ? 2131100289 : 2131100291);
            if (this.mLunboProgramList != null) {
                view.setPrograms(this.mLunboProgramList);
            }
            convertView = view;
        } else if (getItemViewType(position) == 3) {
            view = (LiveRemenLunboView) LayoutInflater.from(this.mContext).inflate(R.layout.view_live_remen_lunbo, parent, false);
            view.init(this.mContext);
            view.setData(this.mPageBean.mWeiShiData, false);
            view.setTitle(LetvUtils.isInHongKong() ? 2131100290 : 2131100292);
            if (this.mWeishiProgramList != null) {
                view.setPrograms(this.mWeishiProgramList);
            }
            convertView = view;
        } else if (getItemViewType(position) == 4) {
            LiveRemenFantasticView view2 = (LiveRemenFantasticView) LayoutInflater.from(this.mContext).inflate(R.layout.view_live_remen_fantastic, parent, false);
            view2.init(this.mContext);
            view2.setData(this.mPageBean.mFantasticData);
            view2.setTitle(LetvUtils.isInHongKong() ? 2131100288 : 2131100286);
            convertView = view2;
        }
        if (this.items != null) {
            this.items.put(Integer.valueOf(position), convertView);
        }
        return convertView;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getItemViewType(int r5) {
        /*
        r4 = this;
        r2 = 4;
        r1 = 3;
        r0 = 2;
        if (r5 != 0) goto L_0x0023;
    L_0x0005:
        r3 = r4.mPageBean;
        r3 = r3.mOrderData;
        if (r3 == 0) goto L_0x000d;
    L_0x000b:
        r0 = 1;
    L_0x000c:
        return r0;
    L_0x000d:
        r3 = r4.mPageBean;
        r3 = r3.mLunboData;
        if (r3 != 0) goto L_0x000c;
    L_0x0013:
        r0 = r4.mPageBean;
        r0 = r0.mWeiShiData;
        if (r0 == 0) goto L_0x001b;
    L_0x0019:
        r0 = r1;
        goto L_0x000c;
    L_0x001b:
        r0 = r4.mPageBean;
        r0 = r0.mFantasticData;
        if (r0 == 0) goto L_0x002e;
    L_0x0021:
        r0 = r2;
        goto L_0x000c;
    L_0x0023:
        if (r5 <= 0) goto L_0x002e;
    L_0x0025:
        r3 = r5 + -1;
        r3 = r4.getItemViewType(r3);
        switch(r3) {
            case 1: goto L_0x0030;
            case 2: goto L_0x0036;
            case 3: goto L_0x003e;
            default: goto L_0x002e;
        };
    L_0x002e:
        r0 = 0;
        goto L_0x000c;
    L_0x0030:
        r3 = r4.mPageBean;
        r3 = r3.mLunboData;
        if (r3 != 0) goto L_0x000c;
    L_0x0036:
        r0 = r4.mPageBean;
        r0 = r0.mWeiShiData;
        if (r0 == 0) goto L_0x003e;
    L_0x003c:
        r0 = r1;
        goto L_0x000c;
    L_0x003e:
        r0 = r4.mPageBean;
        r0 = r0.mFantasticData;
        if (r0 == 0) goto L_0x002e;
    L_0x0044:
        r0 = r2;
        goto L_0x000c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.android.client.live.adapter.LiveAdapter.getItemViewType(int):int");
    }

    public int getViewTypeCount() {
        int number = 1;
        if (this.mPageBean == null) {
            return 1;
        }
        if (this.mPageBean.mOrderData != null) {
            number = 1 + 1;
        }
        if (this.mPageBean.mLunboData != null) {
            number++;
        }
        if (this.mPageBean.mWeiShiData != null) {
            number++;
        }
        if (this.mPageBean.mFantasticData != null) {
            return number + 1;
        }
        return number;
    }
}
