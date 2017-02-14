package com.letv.android.client.commonlib.messagemodel;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public interface BarrageAlbumProtocol {
    void bindSettingView(View view);

    void changeVisibity(boolean z);

    void clickBarrageBtn();

    void end();

    void onCacheVideoFirstPlay();

    void onSeekEnd();

    void onStartPlay(boolean z);

    void pause(boolean z);

    void realStartPlay();

    void resume();

    void setBarrageButton(TextView textView, ImageView imageView);

    void showBarrageInputView();

    boolean showGuide();

    void start();
}
