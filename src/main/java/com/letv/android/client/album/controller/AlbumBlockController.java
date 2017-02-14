package com.letv.android.client.album.controller;

import com.letv.core.utils.LogInfo;
import com.letv.core.utils.RxBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class AlbumBlockController {
    public static final String BLOCK_FIVE_SECOND = "block_five_second";
    public static final String BLOCK_TWO_SECOND = "block_two_second";
    public static final String BLOCK_TWO_SECOND_IN_THIRTY_SECOND = "block_two_second_in_thirty_second";
    private int mBlockTwoSecondCount;
    private long mLastBlockTwoSecond;
    private CompositeSubscription mSubscription = new CompositeSubscription();

    public AlbumBlockController() {
        this.mSubscription.add(RxBus.getInstance().toObserverable().observeOn(AndroidSchedulers.mainThread()).subscribe(new 1(this)));
    }

    private void blockTwoSecond() {
        long currTime = System.currentTimeMillis();
        if (this.mBlockTwoSecondCount == 0) {
            this.mBlockTwoSecondCount = 1;
            this.mLastBlockTwoSecond = currTime;
            LogInfo.log("zhuqiao", "第一次卡顿了2s");
        } else if (currTime - this.mLastBlockTwoSecond > 30000) {
            this.mLastBlockTwoSecond = currTime;
            LogInfo.log("zhuqiao", "两次卡顿2s时间超过30s");
        } else {
            RxBus.getInstance().send(BLOCK_TWO_SECOND_IN_THIRTY_SECOND);
            LogInfo.log("zhuqiao", "30S内出现两次超过2S的卡顿!!");
            this.mLastBlockTwoSecond = currTime;
            this.mBlockTwoSecondCount = 0;
        }
    }

    public void reset() {
        this.mBlockTwoSecondCount = 0;
        this.mLastBlockTwoSecond = 0;
    }

    public void destory() {
        reset();
        if (this.mSubscription != null) {
            this.mSubscription.unsubscribe();
            this.mSubscription = null;
        }
    }
}
