package com.letv.business.flow.album.listener;

import android.view.View;

public interface AlbumPlayHotListener {
    void createPlayerForPlay(Object obj, View view);

    void dismissErrorLayout(Object obj);

    View getAttachView();

    Object getBaseNetChangeLayer();

    View getConvertView();

    int getCurrentPlayTime();

    Object getHotSquareShareDialog();

    void hide3gLayout();

    void initHotVideo(View view);

    void onDataError(boolean z, Object obj, View view);

    void onIpError(boolean z, Object obj, View view);

    void onNetError(boolean z, Object obj, View view);

    void resume();

    void setCurrentPlayTime(int i);

    void setErrorText(int i, boolean z, Object obj, View view);

    void setIsScreenOn(boolean z);

    void show3gLayout(boolean z, View view);

    boolean showNetChangeDialog();
}
