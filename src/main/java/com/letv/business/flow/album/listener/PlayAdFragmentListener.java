package com.letv.business.flow.album.listener;

import android.os.Message;
import com.letv.ads.ex.client.IVideoStatusInformer;
import com.letv.ads.ex.ui.AdPlayFragmentProxy;
import java.util.HashMap;

public interface PlayAdFragmentListener {
    void cancelRequestFrontAdTask();

    void closePauseAd();

    AdPlayFragmentProxy getAdFragment();

    void getDemandPauseAd(int i, long j, long j2, String str, String str2, String str3, String str4, String str5, String str6);

    IVideoStatusInformer getIVideoStatusInformer();

    void getOfflineFrontAd(int i, long j, long j2, String str, String str2, String str3, String str4, String str5, String str6, boolean z, boolean z2, boolean z3, boolean z4, boolean z5);

    HashMap<String, String> getVODFrontADParameter(String str, String str2, String str3, String str4, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10);

    boolean isPlaying();

    void notifyADEvent(Message message);

    void onResume();

    void setADPause(boolean z);

    void setIVideoStatusInformer(IVideoStatusInformer iVideoStatusInformer);

    void stopPlayback(boolean z);

    void stopPlayback(boolean z, boolean z2);
}
