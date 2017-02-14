package com.letv.mobile.lebox.ui;

import android.support.v4.app.FragmentActivity;

public abstract class WrapActivity extends FragmentActivity {

    public interface IBatchDel {
        void onCancelEditState();

        void onClearSelectAll();

        void onDoBatchDelete();

        void onInitData();

        boolean onIsAdapterEmpty();

        void onSelectAll();

        int onSelectNum();

        void onShowEditState();
    }
}
