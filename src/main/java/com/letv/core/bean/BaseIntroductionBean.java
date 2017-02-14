package com.letv.core.bean;

import android.text.TextUtils;
import com.letv.android.remotedevice.Constant.ControlAction;
import com.letv.base.R;
import com.letv.core.BaseApplication;
import com.letv.core.bean.AlbumPageCard.AlbumPageCardBlock;
import com.letv.core.constant.PlayConstant;
import com.letv.core.utils.LetvUtils;
import java.text.DecimalFormat;
import org.json.JSONException;
import org.json.JSONObject;

public class BaseIntroductionBean implements LetvBaseBean {
    private static int OFFICIAL = 180001;
    private String area;
    public int cardRows;
    public String cardStyle;
    public String cardTitle;
    public int cid;
    public int commentCount;
    public int danmuCount;
    protected String description;
    public long down;
    public int isHomemade;
    private int linenum = 4;
    public String name;
    public String nowEpisodes;
    public AlbumPageCardBlock pageCardBlock;
    private long playCount;
    public String playStatus;
    public long up;
    public int varietyShow;
    public int videoType;

    public BaseIntroductionBean(JSONObject data) throws JSONException {
        if (data != null) {
            setName(data.optString("nameCn"));
            this.danmuCount = data.optInt("danmuCount");
            this.cid = data.optInt("cid");
            this.isHomemade = data.optInt("isHomemade");
            this.varietyShow = data.optInt("varietyShow");
            this.videoType = data.optInt(PlayConstant.VIDEO_TYPE);
            this.description = data.optString("description");
            this.area = data.optString("area");
            this.commentCount = data.optInt("commentCount");
            this.playCount = data.optLong("playCount");
            this.linenum = data.optInt("linenum");
            this.up = data.optLong(ControlAction.ACTION_KEY_UP);
            this.down = data.optLong(ControlAction.ACTION_KEY_DOWN);
            this.nowEpisodes = data.optString("nowEpisodes");
            this.playStatus = data.optString("playStatus");
        }
    }

    public String getName() {
        if (TextUtils.isEmpty(this.name)) {
            return "";
        }
        if (this.isHomemade == 1) {
            return getString(R.string.Introduction_self_made) + this.name;
        }
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        if (TextUtils.isEmpty(this.description)) {
            return "";
        }
        return getString(R.string.Introduction_description) + this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArea() {
        if (TextUtils.isEmpty(this.area)) {
            return "";
        }
        return getString(R.string.Introduction_area) + this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPlayCount() {
        String play = toPlayCountText(this.playCount);
        if (TextUtils.isEmpty(play)) {
            return "";
        }
        return getString(R.string.Introduction_play) + play;
    }

    public void setPlayCount(int playCount) {
        this.playCount = (long) playCount;
    }

    public String getDanMuCount() {
        if (TextUtils.isEmpty(toPlayCountText((long) this.danmuCount))) {
            return "";
        }
        String str = String.format(BaseApplication.getInstance().getString(R.string.danmu_count), new Object[]{danmu});
        if (str.length() > 0) {
            return str.substring(0, str.length() - 1);
        }
        return str;
    }

    public int getLinenum() {
        return this.linenum;
    }

    public void setLinenum(int linenum) {
        if (linenum > 0) {
            this.linenum = linenum;
        }
    }

    public String toPlayCountText(long playCount) {
        if (playCount <= 0) {
            return "";
        }
        if (playCount <= 10000) {
            return new DecimalFormat("#,###").format(playCount) + getString(R.string.times);
        } else if (playCount <= 100000000) {
            return new DecimalFormat("#,###.0").format(((double) playCount) / 10000.0d) + LetvUtils.getString(R.string.ten_thousand_times);
        } else {
            return new DecimalFormat("#,###.0").format(((double) playCount) / 1.0E8d) + LetvUtils.getString(R.string.hundred_million_times);
        }
    }

    public int getType() {
        return toIntroductionType(this.cid, this.varietyShow, this.videoType, this.isHomemade);
    }

    public static int toIntroductionType(int cid, int varietyShow, int videoType, int isHomemade) {
        switch (cid) {
            case 1:
                return 1000;
            case 2:
                return 2000;
            case 3:
                if (varietyShow == 1) {
                    return 3000;
                }
                return 3001;
            case 4:
                if (varietyShow == 1) {
                    return 4000;
                }
                return 4001;
            case 5:
                return 5000;
            case 9:
                return 9000;
            case 11:
                if (videoType == OFFICIAL) {
                    return 11000;
                }
                return 11001;
            case 14:
                if (varietyShow == 1) {
                    return 14000;
                }
                return 14001;
            case 16:
                return 16000;
            case 20:
                if (varietyShow == 1) {
                    return 20000;
                }
                return 20001;
            case 22:
                if (varietyShow == 1) {
                    return 22000;
                }
                return 22001;
            case 23:
                if (varietyShow == 1) {
                    return 23000;
                }
                return 23001;
            case 30:
            case 1009:
                if (varietyShow == 1) {
                    return 30000;
                }
                return 30001;
            case 34:
                if (varietyShow == 1) {
                    return 34000;
                }
                return 34001;
            case 1000:
                return 1000000;
            default:
                return 0;
        }
    }

    protected String getString(int id) {
        return BaseApplication.getInstance().getApplicationContext().getString(id);
    }
}
