package com.letv.marlindrm.http;

import com.intertrust.wasabi.media.PlaylistProxy.MediaSourceType;
import com.letv.marlindrm.constants.ContentTypeEnum;
import com.letv.marlindrm.constants.DrmVideoTypeContants;

public class DrmCommenUtils {
    public static ContentTypeEnum getContentTypeByVideoType(String videoType) {
        ContentTypeEnum contentType = ContentTypeEnum.DASH;
        if (DrmVideoTypeContants.VIDEO_TYPE_DASH.equals(videoType)) {
            return ContentTypeEnum.DASH;
        }
        if (DrmVideoTypeContants.VIDEO_TYPE_HLS.equals(videoType)) {
            return ContentTypeEnum.HLS;
        }
        if (DrmVideoTypeContants.VIDEO_TYPE_MP4.equals(videoType)) {
            return ContentTypeEnum.M4F;
        }
        if (DrmVideoTypeContants.VIDEO_TYPE_M3U8.equals(videoType)) {
            return ContentTypeEnum.BBTS;
        }
        return contentType;
    }

    public static MediaSourceType getSourceTypeByContentType(ContentTypeEnum contentType) {
        switch (contentType) {
            case DASH:
                return MediaSourceType.DASH;
            case HLS:
                return MediaSourceType.HLS;
            case M4F:
            case BBTS:
                return MediaSourceType.SINGLE_FILE;
            default:
                return MediaSourceType.DASH;
        }
    }
}
