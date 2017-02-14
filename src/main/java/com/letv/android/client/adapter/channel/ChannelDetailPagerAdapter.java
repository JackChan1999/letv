package com.letv.android.client.adapter.channel;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;
import android.view.ViewGroup;
import com.letv.android.client.fragment.ChannelDetailFragment;
import com.letv.android.client.fragment.ChannelWebViewFragment;
import com.letv.android.client.fragment.FirstPageFragment;
import com.letv.core.bean.ChannelListBean.Channel;
import com.letv.core.utils.BaseTypeUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChannelDetailPagerAdapter extends FragmentStatePagerAdapter {
    private static final int FIRST_PAGE_CID = 3000;
    private List<Channel> mChannelList = new ArrayList();
    private Context mContext;
    private HashMap<Channel, Fragment> mFragmetList = new HashMap();

    public ChannelDetailPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    public void setData(List<Channel> chanel) {
        if (!BaseTypeUtils.isListEmpty(chanel)) {
            this.mChannelList.clear();
            this.mChannelList.addAll(chanel);
            notifyDataSetChanged();
        }
        if (this.mFragmetList != null) {
            this.mFragmetList.clear();
        }
    }

    public Fragment getItem(int pos) {
        if (this.mContext == null || BaseTypeUtils.getElementFromList(this.mChannelList, pos) == null) {
            return new Fragment();
        }
        Channel channel = (Channel) this.mChannelList.get(pos);
        if (this.mFragmetList.containsKey(channel)) {
            return (Fragment) this.mFragmetList.get(channel);
        }
        Fragment fragment;
        if (channel.id == 3000) {
            fragment = new FirstPageFragment();
        } else if (TextUtils.isEmpty(channel.htmlUrl)) {
            fragment = new ChannelDetailFragment();
            mBundle = new Bundle();
            mBundle.putSerializable("channel", channel);
            fragment.setArguments(mBundle);
        } else {
            fragment = new ChannelWebViewFragment();
            mBundle = new Bundle();
            mBundle.putSerializable("url", channel.htmlUrl);
            fragment.setArguments(mBundle);
        }
        this.mFragmetList.put(channel, fragment);
        return fragment;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    public int getCount() {
        return BaseTypeUtils.isListEmpty(this.mChannelList) ? 0 : this.mChannelList.size();
    }

    public int getItemPosition(Object object) {
        return -2;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        try {
            if (!(this.mFragmetList == null || BaseTypeUtils.getElementFromList(this.mChannelList, position) == null)) {
                if (((Channel) this.mChannelList.get(position)).id != 3000) {
                    if (this.mFragmetList.containsKey(this.mChannelList.get(position))) {
                        this.mFragmetList.remove(this.mChannelList.get(position));
                    }
                } else {
                    return;
                }
            }
            super.destroyItem(container, position, object);
        } catch (Exception e) {
        }
    }

    public CharSequence getPageTitle(int position) {
        String title = "";
        if (BaseTypeUtils.getElementFromList(this.mChannelList, position) != null) {
            return ((Channel) this.mChannelList.get(position)).name;
        }
        return title;
    }

    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    public String getCid(int position) {
        if (BaseTypeUtils.getElementFromList(this.mChannelList, position) != null) {
            return String.valueOf(((Channel) this.mChannelList.get(position)).id);
        }
        return "";
    }

    public String getPageId(int position) {
        if (BaseTypeUtils.getElementFromList(this.mChannelList, position) != null) {
            return String.valueOf(((Channel) this.mChannelList.get(position)).pageid);
        }
        return "";
    }

    public void destroy() {
        if (this.mFragmetList != null) {
            this.mFragmetList.clear();
        }
    }
}
