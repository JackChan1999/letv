package com.letv.android.client.commonlib.activity;

public abstract class WrapActivity extends LetvBaseActivity {

    public interface IBatchDel {
        void onCancelEditState();

        void onClearSelectAll();

        void onDoBatchDelete();

        boolean onIsAdapterEmpty();

        void onSelectAll();

        int onSelectNum();

        void onShowEditState();
    }

    public String[] getAllFragmentTags() {
        return null;
    }
}
