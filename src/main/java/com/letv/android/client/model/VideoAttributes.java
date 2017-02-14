package com.letv.android.client.model;

import com.letv.component.player.LetvVideoViewBuilder.Type;
import com.letv.core.bean.AlbumCardList;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.AlbumPageCard;
import com.letv.core.bean.LanguageSettings;
import com.letv.core.bean.PlayRecord;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class VideoAttributes {
    public long aid;
    public AlbumInfo album;
    public AlbumCardList albumCardList;
    public AlbumPageCard albumPageCard;
    public String albumTitle;
    public int cid;
    public int curPage;
    public String deviceId;
    public String episodeTitle;
    public String format;
    public boolean isDolby;
    public boolean isFromLocal;
    public boolean isLive;
    public LanguageSettings languageSettings;
    public int launchMode;
    public Type mediaType;
    public String mid;
    public String order;
    public String pcode;
    public PlayRecord playRecord;
    public long seek;
    public int type;
    public String url;
    public String version;
    public long vid;

    public VideoAttributes() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" vid = " + this.vid);
        sb.append(" type = " + this.type);
        sb.append(" cid = " + this.cid);
        sb.append(" albumTitle = " + this.albumTitle);
        sb.append(" episodeTitle = " + this.episodeTitle);
        sb.append(" aid = " + this.aid);
        sb.append(" order = " + this.order);
        sb.append(" isDolby = " + this.isDolby);
        sb.append(" isLive = " + this.isLive);
        sb.append(" launchMode = " + this.launchMode);
        sb.append(" curPage = " + this.curPage);
        sb.append(" launchMode = " + this.launchMode);
        sb.append(" album = " + this.album);
        sb.append(" mid = " + this.mid);
        sb.append(" url = " + this.url);
        sb.append(" format = " + this.format);
        sb.append(" isFormLocal = " + this.isFromLocal);
        sb.append(" seek = " + this.seek);
        sb.append(" mediaType = " + this.mediaType);
        return sb.toString();
    }
}
