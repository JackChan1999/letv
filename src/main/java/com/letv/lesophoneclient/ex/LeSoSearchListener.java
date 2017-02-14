package com.letv.lesophoneclient.ex;

import android.content.Context;

public interface LeSoSearchListener {
    String getPcode();

    String getVersion();

    void onError(Context context, Object obj);

    void onJumpStar(Context context, String str, String str2);

    void onLiveLunboPlay(Context context, String str, String str2, String str3, String str4, String str5);

    void onLivePlay(Context context, String str, boolean z, String str2, boolean z2);

    void onPlay(Context context, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, boolean z);

    void onSearchKeyWords(String str);

    void onWebToPlay(String str);

    void onZidPlay(Context context, String str, String str2, String str3);
}
