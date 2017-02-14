package com.letv.mobile.lebox.ui.download;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.letv.mobile.lebox.LeBoxApp;
import com.letv.mobile.lebox.R;
import java.util.ArrayList;

public class DownloadFragmentPagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> mArrayFragment = new ArrayList();
    private final String[] title = new String[]{getString(R.string.lebox_download_state_finish), getString(R.string.lebox_download_state_downloading)};

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
                this.title[1] = getString(R.string.lebox_download_state_downloading);
                return;
            }
            this.title[1] = getString(R.string.lebox_download_state_downloading) + "(" + num + ")";
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
        return LeBoxApp.getApplication().getString(id);
    }
}
