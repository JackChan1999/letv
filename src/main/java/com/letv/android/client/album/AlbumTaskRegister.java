package com.letv.android.client.album;

import com.letv.android.client.commonlib.messagemodel.AlbumTask.AlbumBarrageProtocol;
import com.letv.android.client.commonlib.messagemodel.AlbumTask.AlbumCacheProtocol;
import com.letv.android.client.commonlib.messagemodel.AlbumTask.AlbumProtocol;
import com.letv.android.client.commonlib.messagemodel.DLNAToPlayerProtocol;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.task.LeMessageTask;

public class AlbumTaskRegister {
    private AlbumPlayActivity mActivity;
    private AlbumBarrageProtocol mBarrageProtocol;
    private AlbumCacheProtocol mCacheProtocol;
    private DLNAToPlayerProtocol mDlnaProtocol;
    private AlbumProtocol mProtocol;

    public AlbumTaskRegister(AlbumPlayActivity activity) {
        this.mActivity = activity;
        initNormalProtocol();
        initBarrageProtocol();
        initCacheProtocol();
        initDLNAProtocol();
        registerNormalTask();
        registerBarrageTask();
        registerCacheTask();
        registerDLNATask();
    }

    private void initNormalProtocol() {
        this.mProtocol = new 1(this);
    }

    private void initBarrageProtocol() {
        this.mBarrageProtocol = new 2(this);
    }

    private void initCacheProtocol() {
        this.mCacheProtocol = new 3(this);
    }

    private void initDLNAProtocol() {
        this.mDlnaProtocol = new 4(this);
    }

    private void registerNormalTask() {
        LeMessageManager.getInstance().registerTask(new LeMessageTask(100, new 5(this)));
    }

    private void registerBarrageTask() {
        LeMessageManager.getInstance().registerTask(new LeMessageTask(101, new 6(this)));
        LeMessageManager.getInstance().registerTask(new LeMessageTask(LeMessageIds.MSG_BARRAGE_CHECK_IS_PANORAMA, new 7(this)));
        LeMessageManager.getInstance().registerTask(new LeMessageTask(321, new 8(this)));
    }

    private void registerCacheTask() {
        LeMessageManager.getInstance().registerTask(new LeMessageTask(102, new 9(this)));
    }

    private void registerDLNATask() {
        LeMessageManager.getInstance().registerTask(new LeMessageTask(LeMessageIds.MSG_DLNA_ALBUM_PROTOCOL, new 10(this)));
    }

    public void onDestory() {
        LeMessageManager.getInstance().unRegister(100);
        LeMessageManager.getInstance().unRegister(101);
        LeMessageManager.getInstance().unRegister(102);
        LeMessageManager.getInstance().unRegister(LeMessageIds.MSG_DLNA_ALBUM_PROTOCOL);
    }
}
