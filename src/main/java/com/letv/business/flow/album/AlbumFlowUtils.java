package com.letv.business.flow.album;

import android.content.Context;
import android.os.Bundle;
import com.letv.core.utils.LogInfo;

public class AlbumFlowUtils {
    public static AlbumPlayFlow getPlayFlow(Context context, int launchMode, Bundle bundle) {
        LogInfo.log("zhuqiao", "launchMode:" + launchMode);
        if (launchMode == 1) {
            return new AlbumPlayFlow(context, 0, bundle);
        }
        if (launchMode == 2) {
            return new AlbumPlayFlow(context, 1, bundle);
        }
        if (launchMode == 3) {
            return new AlbumPlayFlow(context, 2, bundle);
        }
        if (launchMode == 4) {
            return new AlbumPlayFlow(context, 3, bundle);
        }
        if (launchMode == 6) {
            return new AlbumPlayTopicFlow(context, 11, bundle);
        }
        if (launchMode == 7) {
            return new AlbumPlayFlow(context, 4, bundle);
        }
        return null;
    }
}
