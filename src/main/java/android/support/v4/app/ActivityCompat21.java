package android.support.v4.app;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.media.session.MediaController;

class ActivityCompat21 {
    ActivityCompat21() {
    }

    public static void setMediaController(Activity activity, Object mediaControllerObj) {
        activity.setMediaController((MediaController) mediaControllerObj);
    }

    public static void finishAfterTransition(Activity activity) {
        activity.finishAfterTransition();
    }

    public static void setEnterSharedElementCallback(Activity activity, SharedElementCallback21 callback) {
        activity.setEnterSharedElementCallback(createCallback(callback));
    }

    public static void setExitSharedElementCallback(Activity activity, SharedElementCallback21 callback) {
        activity.setExitSharedElementCallback(createCallback(callback));
    }

    public static void postponeEnterTransition(Activity activity) {
        activity.postponeEnterTransition();
    }

    public static void startPostponedEnterTransition(Activity activity) {
        activity.startPostponedEnterTransition();
    }

    private static SharedElementCallback createCallback(SharedElementCallback21 callback) {
        if (callback != null) {
            return new SharedElementCallbackImpl(callback);
        }
        return null;
    }
}
