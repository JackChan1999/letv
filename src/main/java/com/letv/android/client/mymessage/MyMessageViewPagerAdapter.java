package com.letv.android.client.mymessage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.letv.android.client.LetvApplication;
import java.util.ArrayList;
import java.util.List;

public class MyMessageViewPagerAdapter extends FragmentPagerAdapter {
    List<MyMessageBaseFragment> mFragments = new ArrayList();
    List<String> mTitles = new ArrayList();

    public MyMessageViewPagerAdapter(FragmentManager fm, MyMessageActivityCallback callback) {
        super(fm);
        this.mFragments.add(new MySystemMessageFragment(callback));
        this.mFragments.add(new MyReplyCommentsFragment(callback));
        this.mTitles.add(LetvApplication.getInstance().getString(2131100484));
        this.mTitles.add(LetvApplication.getInstance().getString(2131100483));
    }

    public Fragment getItem(int position) {
        return (Fragment) this.mFragments.get(position);
    }

    public void setEditMode(int position, boolean isEdit) {
        if (position < this.mFragments.size()) {
            ((MyMessageBaseFragment) this.mFragments.get(position)).setEdit(isEdit);
        }
    }

    public void allExitEditMode() {
        for (MyMessageBaseFragment fragment : this.mFragments) {
            fragment.setEdit(false);
        }
    }

    public CharSequence getPageTitle(int position) {
        return (CharSequence) this.mTitles.get(position);
    }

    public int getCount() {
        return this.mFragments.size();
    }
}
