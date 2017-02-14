package io.fabric.sdk.android.services.settings;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;

public class IconRequest {
    public final String hash;
    public final int height;
    public final int iconResourceId;
    public final int width;

    public IconRequest(String hash, int iconResourceId, int width, int height) {
        this.hash = hash;
        this.iconResourceId = iconResourceId;
        this.width = width;
        this.height = height;
    }

    public static IconRequest build(Context context, String iconHash) {
        if (iconHash == null) {
            return null;
        }
        try {
            int iconId = CommonUtils.getAppIconResourceId(context);
            Fabric.getLogger().d(Fabric.TAG, "App icon resource ID is " + iconId);
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(context.getResources(), iconId, options);
            return new IconRequest(iconHash, iconId, options.outWidth, options.outHeight);
        } catch (Exception e) {
            Fabric.getLogger().e(Fabric.TAG, "Failed to load icon", e);
            return null;
        }
    }
}
