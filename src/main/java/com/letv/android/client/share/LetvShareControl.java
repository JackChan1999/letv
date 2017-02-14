package com.letv.android.client.share;

import android.text.TextUtils;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.Share;
import com.letv.core.bean.ShareAlbumBean;
import com.letv.core.bean.VideoBean;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class LetvShareControl {
    public static final LetvShareControl mLetvShareControl = new LetvShareControl();
    public static final ShareAlbumBean mShareAlbum = new ShareAlbumBean();
    private boolean isShare;
    private Share share;

    public interface LetvShareImp {
    }

    public LetvShareControl() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static LetvShareControl getInstance() {
        return mLetvShareControl;
    }

    public void setAblum_att(Object... array) {
        if (!isEmptyArray(array)) {
            VideoBean video;
            if (!isEmptyArray(array[0]) && (array[0] instanceof VideoBean)) {
                video = array[0];
                mShareAlbum.sharedPid = video.pid;
                mShareAlbum.Share_vid = (int) video.vid;
                mShareAlbum.playCount = video.playCount;
                mShareAlbum.position = video.episode;
                mShareAlbum.isFeature = video.isFeature();
                mShareAlbum.subTitle = video.subTitle;
                if (!TextUtils.isEmpty(video.pic320_200)) {
                    mShareAlbum.icon = video.pic320_200;
                    mShareAlbum.facebookIcon = video.pic320_200;
                } else if (TextUtils.isEmpty(video.pic200_150)) {
                    mShareAlbum.icon = video.pic120_90;
                } else {
                    mShareAlbum.icon = video.pic200_150;
                    mShareAlbum.facebookIcon = video.pic200_150;
                }
                if (video.cid == 9) {
                    mShareAlbum.Share_AlbumName = video.nameCn + "\n" + video.singer;
                } else {
                    mShareAlbum.Share_AlbumName = video.nameCn;
                }
                mShareAlbum.Share_PidName = mShareAlbum.Share_AlbumName;
            }
            if (!isEmptyArray(array[0]) && (array[0] instanceof VideoBean)) {
                video = (VideoBean) array[0];
                mShareAlbum.Share_vid = (int) video.vid;
                if (!TextUtils.isEmpty(video.pic200_150)) {
                    mShareAlbum.icon = video.pic200_150;
                } else if (!TextUtils.isEmpty(video.pic120_90)) {
                    mShareAlbum.icon = video.pic120_90;
                }
                if (video.cid == 9) {
                    mShareAlbum.Share_AlbumName = video.nameCn + "\n" + video.singer;
                } else {
                    mShareAlbum.Share_AlbumName = video.nameCn;
                }
                mShareAlbum.Share_PidName = mShareAlbum.Share_AlbumName;
            }
            if (array.length == 2) {
                if (!isEmptyArray(array[1]) && (array[1] instanceof AlbumInfo)) {
                    AlbumInfo albumInfo = array[1];
                    mShareAlbum.cid = albumInfo.cid;
                    mShareAlbum.director = albumInfo.directory;
                    mShareAlbum.Share_id = (int) albumInfo.pid;
                    mShareAlbum.actor = albumInfo.starring;
                    mShareAlbum.year = albumInfo.releaseDate;
                    mShareAlbum.Share_PidName = mShareAlbum.isFeature ? albumInfo.nameCn : mShareAlbum.Share_PidName;
                }
                LogInfo.log("fornia", "mShareAlbum Share_AlbumName:" + mShareAlbum.Share_AlbumName + "mShareAlbum isFeature:" + mShareAlbum.isFeature + "mShareAlbum director:" + mShareAlbum.director);
            }
        }
    }

    public void setmShareAlbumCid(int cid) {
        mShareAlbum.cid = cid;
    }

    public ShareAlbumBean getAblum() {
        return mShareAlbum;
    }

    public Share getShare() {
        return this.share;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public boolean isShare() {
        return this.isShare;
    }

    public void setIsShare(boolean isShare) {
        this.isShare = isShare;
    }

    public boolean isEmptyArray(Object[] array) {
        return isEmptyArray(array, 1);
    }

    public boolean isEmptyArray(Object array) {
        return array == null;
    }

    public boolean isEmptyArray(Object[] array, int len) {
        return array == null || array.length < len;
    }
}
