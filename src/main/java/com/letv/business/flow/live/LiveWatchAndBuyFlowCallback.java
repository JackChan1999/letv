package com.letv.business.flow.live;

import com.letv.core.bean.WatchAndBuyGetNumResultBean;
import com.letv.core.bean.WatchAndBuyGoodsListBean;

public interface LiveWatchAndBuyFlowCallback {
    void onAddToCartResponse(String str);

    void onGetAttentionCount(int i);

    void onGetCartNumResponse(WatchAndBuyGetNumResultBean watchAndBuyGetNumResultBean);

    void onGetGoodsResponse(String str, WatchAndBuyGoodsListBean watchAndBuyGoodsListBean);
}
