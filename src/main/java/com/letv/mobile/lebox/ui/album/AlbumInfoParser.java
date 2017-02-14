package com.letv.mobile.lebox.ui.album;

import android.text.TextUtils;
import com.letv.core.parser.PushDataParser;
import com.letv.download.db.Download.DownloadVideoTable;
import com.letv.mobile.letvhttplib.parser.LetvMobileParser;
import com.letv.mobile.letvhttplib.utils.BaseTypeUtils;
import com.media.ffmpeg.FFMpegPlayer;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import org.json.JSONObject;

public class AlbumInfoParser extends LetvMobileParser<AlbumInfo> {
    public AlbumInfoParser(int from) {
        super(from);
    }

    public AlbumInfoParser() {
        super(FFMpegPlayer.PREPARE_VIDEO_CODECOPEN_ERROR);
    }

    public AlbumInfo parse(JSONObject data) throws Exception {
        if (data != null) {
            try {
                int count;
                int aid;
                AlbumInfo album = new AlbumInfo();
                int from = getFrom();
                switch (from) {
                    case 258:
                    case FFMpegPlayer.PREPARE_VIDEO_NOSTREAM_ERROR /*259*/:
                        break;
                    case FFMpegPlayer.PREPARE_VIDEO_NODECODER_ERROR /*260*/:
                        album.pid = (long) getInt(data, "id");
                        album.nameCn = getString(data, "title");
                        album.subTitle = getString(data, "subtitle");
                        album.pic400_300 = getString(data, SettingsJsonConstants.APP_ICON_KEY);
                        if (has(data, "icon_400x300")) {
                            album.icon_400_300 = getString(data, "icon_400x300");
                        } else {
                            album.icon_400_300 = null;
                        }
                        if (has(data, "icon_200x150")) {
                            album.icon_200_150 = getString(data, "icon_200x150");
                        } else {
                            album.icon_200_150 = null;
                        }
                        album.score = getString(data, "score");
                        album.cid = getInt(data, "cid");
                        album.type = getInt(data, "type");
                        album.at = getInt(data, PushDataParser.AT);
                        album.releaseDate = getString(data, "year");
                        count = getInt(data, "count");
                        if (count <= 0) {
                            count = 0;
                        }
                        album.nowEpisodes = count + "";
                        album.isEnd = getInt(data, "isend");
                        album.duration = getLong(data, "time_length");
                        album.directory = getString(data, "director");
                        album.starring = getString(data, "actor");
                        album.description = getString(data, "intro");
                        album.area = getString(data, "area");
                        album.style = getString(data, "style");
                        album.playTv = getString(data, "tv");
                        album.school = getString(data, "rcompany");
                        album.jump = getInt(data, "needJump");
                        setVTpyeFlag(getString(data, "vtypeFlag"), album);
                        if (!has(data, "pay")) {
                            album.pay = 0;
                        } else if (getInt(data, "pay") == 1) {
                            album.pay = 0;
                        } else {
                            album.pay = 1;
                        }
                        if (has(data, "aid")) {
                            aid = getInt(data, "aid");
                            if (aid <= 0) {
                                album.pid = album.pid;
                            } else {
                                album.pid = (long) aid;
                            }
                        } else {
                            album.pid = album.pid;
                        }
                        if (!has(data, "filmstyle")) {
                            return album;
                        }
                        album.filmstyle = getInt(data, "filmstyle");
                        return album;
                    case 272:
                    case 273:
                        album.isDolby = true;
                        break;
                    default:
                        album.pid = getLong(data, "id");
                        album.nameCn = getString(data, "nameCn");
                        album.albumType = getString(data, "albumType");
                        album.subTitle = getString(data, "subTitle");
                        if (has(data, "picCollections")) {
                            album.pic400_300 = getString(getJSONObject(data, "picCollections"), "150*200");
                        } else if (has(data, "picAll")) {
                            album.pic400_300 = getString(getJSONObject(data, "picAll"), "120*90");
                        }
                        album.score = getString(data, "score");
                        album.cid = getInt(data, "cid");
                        album.type = getInt(data, "type");
                        album.at = getInt(data, PushDataParser.AT);
                        album.releaseDate = getString(data, "releaseDate");
                        album.platformVideoNum = getInt(data, "platformVideoNum");
                        album.platformVideoInfo = getInt(data, "platformVideoInfo");
                        album.episode = getString(data, "episode");
                        album.nowEpisodes = getString(data, "nowEpisodes");
                        album.isEnd = getInt(data, "isEnd");
                        album.duration = getLong(data, DownloadVideoTable.COLUMN_DURATION);
                        album.directory = getString(data, "directory");
                        album.starring = getString(data, "starring");
                        album.description = getString(data, "description");
                        album.area = getString(data, "area");
                        album.language = getString(data, "language");
                        album.instructor = getString(data, "instructor");
                        album.subCategory = getString(data, "subCategory");
                        album.style = getString(data, "style");
                        album.playTv = getString(data, "playTv");
                        album.school = getString(data, "school");
                        album.controlAreas = getString(data, "controlAreas");
                        album.disableType = getInt(data, "disableType");
                        album.play = getInt(data, "play");
                        album.jump = getInt(data, "jump");
                        album.pay = getInt(data, "pay");
                        album.download = getInt(data, "download");
                        album.tag = getString(data, "tag");
                        album.travelType = getString(data, "travelType");
                        setVTpyeFlag(getString(data, "vtypeFlag"), album);
                        JSONObject picAll = null;
                        if (has(data, "picCollections")) {
                            picAll = getJSONObject(data, "picCollections");
                        } else if (has(data, "picAll")) {
                            picAll = getJSONObject(data, "picAll");
                        }
                        if (picAll == null) {
                            return album;
                        }
                        String str = getString(picAll, "300*300");
                        if (TextUtils.isEmpty(str)) {
                            str = getString(picAll, "400*300");
                            if (TextUtils.isEmpty(str)) {
                                album.pic300_300 = album.pic400_300;
                                return album;
                            }
                            album.pic300_300 = str;
                            return album;
                        }
                        album.pic300_300 = str;
                        return album;
                }
                album.type = 1;
                if (from == 273 || from == FFMpegPlayer.PREPARE_VIDEO_NOSTREAM_ERROR) {
                    album.type = 3;
                }
                album.pid = (long) getInt(data, "aid");
                album.vid = (long) getInt(data, "vid");
                album.nameCn = getString(data, "name");
                album.subTitle = getString(data, "subname");
                if (has(data, "actor")) {
                    album.starring = getString(data, "actor");
                }
                album.icon_200_150 = getString(getJSONObject(data, "images"), "200*150");
                album.icon_400_300 = getString(getJSONObject(data, "images"), "400*300");
                album.pic400_300 = album.icon_400_300;
                album.cid = getInt(data, WidgetRequestParam.REQ_PARAM_COMMENT_CATEGORY);
                album.releaseDate = getString(data, "year");
                album.episode = getString(data, "episodes");
                count = getInt(data, "nowEpisodes");
                if (count <= 0) {
                    count = 0;
                }
                album.nowEpisodes = count + "";
                album.isEnd = getInt(data, "isEnd");
                album.jump = getInt(data, "jump");
                album.pay = getInt(data, "pay");
                setVTpyeFlag(getString(data, "vtypeFlag"), album);
                if (has(data, "id")) {
                    aid = getInt(data, "id");
                    if (aid <= 0) {
                        album.pid = album.pid;
                        return album;
                    }
                    album.pid = (long) aid;
                    return album;
                }
                album.pid = album.pid;
                return album;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void setVTpyeFlag(String vtypeFlag, AlbumInfo album) {
        if (!TextUtils.isEmpty(vtypeFlag)) {
            String[] flagArr = vtypeFlag.split(",");
            if (!BaseTypeUtils.isArrayEmpty(flagArr)) {
                for (String flag : flagArr) {
                    album.vTypeFlagList.add(flag.trim());
                }
            }
        }
    }
}
