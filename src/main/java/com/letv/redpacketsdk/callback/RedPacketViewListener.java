package com.letv.redpacketsdk.callback;

public interface RedPacketViewListener {
    void gotoGiftPage(String str);

    void gotoWeb(String str);

    void hide();

    void share(String str, String str2, String str3);

    void show();

    void showToast();
}
