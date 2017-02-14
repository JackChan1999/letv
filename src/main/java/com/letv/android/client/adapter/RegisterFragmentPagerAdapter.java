package com.letv.android.client.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.letv.core.utils.LetvUtils;
import java.util.ArrayList;

public class RegisterFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mArrayFragment = new ArrayList();
    private String[] title = new String[]{LetvUtils.getString(2131100602), LetvUtils.getString(2131099685)};

    public RegisterFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment) {
        if (!this.mArrayFragment.contains(fragment)) {
            this.mArrayFragment.add(fragment);
        }
    }

    public void setDownloadingNum(int num) {
        if (this.title.length > 1) {
            String textStr = this.title[1];
            if (num <= 0) {
                this.title[1] = LetvUtils.getString(2131100063);
            } else {
                this.title[1] = textStr + "(" + num + ")";
            }
        }
    }

    public CharSequence getPageTitle(int position) {
        return this.title[position % this.title.length].toUpperCase();
    }

    public Fragment getItem(int arg0) {
        return (Fragment) this.mArrayFragment.get(arg0);
    }

    public int getCount() {
        return this.mArrayFragment.size();
    }
}
