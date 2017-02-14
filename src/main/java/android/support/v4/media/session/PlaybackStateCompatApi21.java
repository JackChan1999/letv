package android.support.v4.media.session;

import android.media.session.PlaybackState;
import android.media.session.PlaybackState.Builder;
import android.media.session.PlaybackState.CustomAction;
import java.util.Iterator;
import java.util.List;

class PlaybackStateCompatApi21 {
    PlaybackStateCompatApi21() {
    }

    public static int getState(Object stateObj) {
        return ((PlaybackState) stateObj).getState();
    }

    public static long getPosition(Object stateObj) {
        return ((PlaybackState) stateObj).getPosition();
    }

    public static long getBufferedPosition(Object stateObj) {
        return ((PlaybackState) stateObj).getBufferedPosition();
    }

    public static float getPlaybackSpeed(Object stateObj) {
        return ((PlaybackState) stateObj).getPlaybackSpeed();
    }

    public static long getActions(Object stateObj) {
        return ((PlaybackState) stateObj).getActions();
    }

    public static CharSequence getErrorMessage(Object stateObj) {
        return ((PlaybackState) stateObj).getErrorMessage();
    }

    public static long getLastPositionUpdateTime(Object stateObj) {
        return ((PlaybackState) stateObj).getLastPositionUpdateTime();
    }

    public static List<Object> getCustomActions(Object stateObj) {
        return ((PlaybackState) stateObj).getCustomActions();
    }

    public static long getActiveQueueItemId(Object stateObj) {
        return ((PlaybackState) stateObj).getActiveQueueItemId();
    }

    public static Object newInstance(int state, long position, long bufferedPosition, float speed, long actions, CharSequence errorMessage, long updateTime, List<Object> customActions, long activeItemId) {
        Builder stateObj = new Builder();
        stateObj.setState(state, position, speed, updateTime);
        stateObj.setBufferedPosition(bufferedPosition);
        stateObj.setActions(actions);
        stateObj.setErrorMessage(errorMessage);
        Iterator i$ = customActions.iterator();
        while (i$.hasNext()) {
            stateObj.addCustomAction((CustomAction) i$.next());
        }
        stateObj.setActiveQueueItemId(activeItemId);
        return stateObj.build();
    }
}
