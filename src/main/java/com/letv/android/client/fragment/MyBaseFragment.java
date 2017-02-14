package com.letv.android.client.fragment;

import android.widget.BaseAdapter;
import com.letv.android.client.commonlib.fragement.LetvBaseFragment;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public abstract class MyBaseFragment extends LetvBaseFragment {
    public abstract BaseAdapter getAdapter();

    public abstract void onViewPageScrollChangeEvent();

    public MyBaseFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }
}
