package com.letv.android.client.dlna.utils;

import android.text.TextUtils;
import com.letv.pp.utils.NetworkUtils;
import org.cybergarage.upnp.Device;

public class DLNAUtil {
    private static final String MEDIARENDER = "urn:schemas-upnp-org:device:MediaRenderer:1";

    public interface DLNATransportState {
        public static final String NO_MEDIA_PRESENT = "NO_MEDIA_PRESENT";
        public static final String PAUSED_PLAYBACK = "PAUSED_PLAYBACK";
        public static final String PAUSED_RECORDING = "PAUSED_RECORDING";
        public static final String PLAYING = "PLAYING";
        public static final String RECORDING = "RECORDING";
        public static final String STOPPED = "STOPPED";
        public static final String TRANSITIONING = "TRANSITIONING";
    }

    public static boolean isMediaRenderDevice(Device device) {
        return device != null && "urn:schemas-upnp-org:device:MediaRenderer:1".equalsIgnoreCase(device.getDeviceType());
    }

    public static int getIntLength(String length) {
        if (TextUtils.isEmpty(length)) {
            return 0;
        }
        String[] split = length.split(NetworkUtils.DELIMITER_COLON);
        try {
            if (split.length == 3) {
                return ((0 + ((Integer.parseInt(split[0]) * 60) * 60)) + (Integer.parseInt(split[1]) * 60)) + Integer.parseInt(split[2]);
            }
            if (split.length == 2) {
                return (0 + (Integer.parseInt(split[0]) * 60)) + Integer.parseInt(split[1]);
            }
            return 0;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String secToTime(int time) {
        if (time <= 0) {
            return "00:00:00";
        }
        String timeStr;
        int minute = time / 60;
        if (minute < 60) {
            timeStr = "00:" + unitFormat(minute) + NetworkUtils.DELIMITER_COLON + unitFormat(time % 60);
        } else {
            int hour = minute / 60;
            if (hour > 99) {
                return "99:59:59";
            }
            minute %= 60;
            timeStr = unitFormat(hour) + NetworkUtils.DELIMITER_COLON + unitFormat(minute) + NetworkUtils.DELIMITER_COLON + unitFormat((time - (hour * 3600)) - (minute * 60));
        }
        return timeStr;
    }

    private static String unitFormat(int i) {
        if (i >= 0 && i < 10) {
            return "0" + Integer.toString(i);
        }
        if (i < 10 || i > 60) {
            return "00";
        }
        return "" + i;
    }
}
