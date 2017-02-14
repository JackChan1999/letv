package com.letv.android.client.ui.download;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.letv.android.client.LetvApplication;
import java.util.ArrayList;

public class DownloadFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mArrayFragment = new ArrayList();
    private String[] title = new String[]{getString(2131100064), getString(2131100063), getString(2131100054), getString(2131100076)};

    public DownloadFragmentPagerAdapter(FragmentManager fm) {
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
                this.title[1] = getString(2131100063);
                return;
            }
            this.title[1] = getString(2131100063) + "(" + num + ")";
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

    private String getString(int id) {
        return LetvApplication.getInstance().getString(id);
    }
}
