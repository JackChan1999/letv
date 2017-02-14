package com.letv.android.client.live.bean;

import com.letv.core.bean.LetvBaseBean;
import com.letv.core.bean.LiveBeanLeChannel;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;

public class LivePageBean implements LetvBaseBean {
    public ArrayList<LiveRemenBaseBean> mFantasticData;
    public ArrayList<LiveRemenBaseBean> mLiveData;
    public ArrayList<LiveBeanLeChannel> mLunboData;
    public String mMusicUrl;
    public ArrayList<LivePageOrderBean> mOrderData;
    public String mOtherUrl;
    public String mSportsUrl;
    public ArrayList<LiveBeanLeChannel> mWeiShiData;

    public LivePageBean() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }
}
