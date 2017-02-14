package com.letv.core.db;

import android.content.Context;
import com.letv.core.BaseApplication;

public class DBManager {
    private static DBManager instance = new DBManager();
    private ChannelHisListHandler channelHisListTrace = new ChannelHisListHandler(this.context);
    private ChannelListHandler channelListTrace = new ChannelListHandler(this.context);
    private Context context = BaseApplication.getInstance();
    private DialogMsgTraceHandler dialogMsgTrace = new DialogMsgTraceHandler(this.context);
    private DownloadTraceHandler downloadTrace = new DownloadTraceHandler(this.context);
    private EmojiHandler emojiHandler = new EmojiHandler(this.context);
    private FavoriteTraceHandler favoriteTrace = new FavoriteTraceHandler(this.context);
    private FestivalImageTraceHandler festivalImageTrace = new FestivalImageTraceHandler(this.context);
    private LanguageSettingsTraceHandler languageSettingsTrace = new LanguageSettingsTraceHandler(this.context);
    private LiveBookTraceHandler liveBookTrace = new LiveBookTraceHandler(this.context);
    private LocalVideoTraceHandler localVideoTrace = new LocalVideoTraceHandler(this.context);
    private PlayRecordHandler playTrace = new PlayRecordHandler(this.context);
    private WorldCupTraceHandler worldCupTrace = new WorldCupTraceHandler(this.context);

    public static DBManager getInstance() {
        return instance;
    }

    public EmojiHandler getEmojiHandler() {
        return this.emojiHandler;
    }

    public PlayRecordHandler getPlayTrace() {
        return this.playTrace;
    }

    public DownloadTraceHandler getDownloadTrace() {
        return this.downloadTrace;
    }

    public FavoriteTraceHandler getFavoriteTrace() {
        return this.favoriteTrace;
    }

    public FestivalImageTraceHandler getFestivalImageTrace() {
        return this.festivalImageTrace;
    }

    public LocalVideoTraceHandler getLocalVideoTrace() {
        return this.localVideoTrace;
    }

    public LiveBookTraceHandler getLiveBookTrace() {
        return this.liveBookTrace;
    }

    public DialogMsgTraceHandler getDialogMsgTrace() {
        return this.dialogMsgTrace;
    }

    public WorldCupTraceHandler getWorldCupTrace() {
        return this.worldCupTrace;
    }

    public ChannelListHandler getChannelListTrace() {
        return this.channelListTrace;
    }

    public ChannelHisListHandler getChannelHisListTrace() {
        return this.channelHisListTrace;
    }

    public LanguageSettingsTraceHandler getLanguageSettingsTrace() {
        return this.languageSettingsTrace;
    }
}
