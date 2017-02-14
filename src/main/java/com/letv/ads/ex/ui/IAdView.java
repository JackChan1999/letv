package com.letv.ads.ex.ui;

import com.letv.adlib.sdk.types.AdElementMime;
import com.letv.ads.ex.client.AdPlayStateListener;

public interface IAdView {

    public interface AdMaterialLoadListener {
        boolean onLoadComplete();

        void onLoadFailed();
    }

    public interface AdViewOnclickListenr {
        void onADClick(AdElementMime adElementMime);
    }

    void closeAD();

    void setAdMaterialLoadListener(AdMaterialLoadListener adMaterialLoadListener);

    void setAdOnClickListener(AdViewOnclickListenr adViewOnclickListenr);

    void setAdPlayStateListener(AdPlayStateListener adPlayStateListener);

    void showAD(AdElementMime adElementMime);
}
