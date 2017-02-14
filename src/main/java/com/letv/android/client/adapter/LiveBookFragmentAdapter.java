package com.letv.android.client.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import com.letv.android.client.live.fragment.LiveBookFragment;
import com.letv.core.bean.LiveBookTabBean;
import com.letv.core.utils.BaseTypeUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LiveBookFragmentAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private HashMap<String, Fragment> mFragmetList = new HashMap();
    private List<LiveBookTabBean> mList = new ArrayList();
    private LiveBookFragment mLivePreengageFragment;

    public LiveBookFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    public void setData(List<LiveBookTabBean> list) {
        if (!BaseTypeUtils.isListEmpty(list)) {
            this.mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public Fragment getItem(int position) {
        if (this.mContext == null || BaseTypeUtils.getElementFromList(this.mList, position) == null) {
            return new Fragment();
        }
        String type = ((LiveBookTabBean) this.mList.get(position)).type;
        if (this.mFragmetList.containsKey(type)) {
            return (Fragment) this.mFragmetList.get(type);
        }
        this.mLivePreengageFragment = new LiveBookFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", type);
        this.mLivePreengageFragment.setArguments(bundle);
        this.mFragmetList.put(type, this.mLivePreengageFragment);
        return this.mLivePreengageFragment;
    }

    public CharSequence getPageTitle(int position) {
        String title = "";
        if (BaseTypeUtils.getElementFromList(this.mList, position) != null) {
            return ((LiveBookTabBean) this.mList.get(position)).name;
        }
        return title;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        if (BaseTypeUtils.getElementFromList(this.mList, position) != null && BaseTypeUtils.isMapContainsKey(this.mFragmetList, ((LiveBookTabBean) this.mList.get(position)).type)) {
            this.mFragmetList.remove(((LiveBookTabBean) this.mList.get(position)).type);
        }
        super.destroyItem(container, position, object);
    }

    public int getCount() {
        return BaseTypeUtils.isListEmpty(this.mList) ? 0 : this.mList.size();
    }

    public void destroy() {
        if (this.mFragmetList != null) {
            this.mFragmetList.clear();
            this.mFragmetList = null;
        }
        if (this.mList != null) {
            this.mList.clear();
            this.mList = null;
        }
    }
}
