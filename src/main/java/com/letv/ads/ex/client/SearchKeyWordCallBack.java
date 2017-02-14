package com.letv.ads.ex.client;

import com.letv.adlib.sdk.types.AdElementMime;

public interface SearchKeyWordCallBack {
    void updateSearchBannerTextView(AdElementMime adElementMime);

    void updateSearchTextView(String str);
}
