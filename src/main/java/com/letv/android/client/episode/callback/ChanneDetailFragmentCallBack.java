package com.letv.android.client.episode.callback;

import com.letv.core.bean.HomeBlock;
import com.letv.core.bean.SiftKVP;
import java.util.ArrayList;

public interface ChanneDetailFragmentCallBack {
    void callBack(HomeBlock homeBlock, int i);

    void callBack(ArrayList<SiftKVP> arrayList, String str);
}
