package com.letv.android.remotedevice;

public class Constant {
    public static final String ACTION_PICK_REMOTE_DEVICE = "com.letv.leui.intent.action.PICK_REMOTE_DEVICE";
    public static final int CALLBACK_EVENT_CONNECTION = 1;
    public static final int CALLBACK_EVENT_EDIT_TEXT_FOCUS = 2;
    public static final int CALLBACK_EVENT_NONE = 0;
    public static final int CALLBACK_EVENT_PACKAGE_CHANGE = 4;
    public static final String EXTRA_DEVICE_INFO = "com.letv.leui.intent.extra.DEVICE_INFO";
    public static final String EXTRA_DEVICE_TYPE = "com.letv.leui.intent.extra.DEVICE_TYPE";
    public static final String EXTRA_PACKAGES = "android.intent.extra.PACKAGES";
    public static final String VERSION = "0.52";

    public interface ControlAction {
        public static final String ACTION_KEY_CHANNEL_DOWN = "channel_down";
        public static final String ACTION_KEY_CHANNEL_UP = "channel_up";
        public static final String ACTION_KEY_CONTROL_MUTE = "mute";
        public static final String ACTION_KEY_DOWN = "down";
        public static final String ACTION_KEY_HOME = "home";
        public static final String ACTION_KEY_LEFT = "left";
        public static final String ACTION_KEY_MENU = "menu";
        public static final String ACTION_KEY_MOUSE_PRESS = "mouse_press";
        public static final String ACTION_KEY_NUMB_0 = "num_0";
        public static final String ACTION_KEY_NUMB_1 = "num_1";
        public static final String ACTION_KEY_NUMB_2 = "num_2";
        public static final String ACTION_KEY_NUMB_3 = "num_3";
        public static final String ACTION_KEY_NUMB_4 = "num_4";
        public static final String ACTION_KEY_NUMB_5 = "num_5";
        public static final String ACTION_KEY_NUMB_6 = "num_6";
        public static final String ACTION_KEY_NUMB_7 = "num_7";
        public static final String ACTION_KEY_NUMB_8 = "num_8";
        public static final String ACTION_KEY_NUMB_9 = "num_9";
        public static final String ACTION_KEY_OK = "ok";
        public static final String ACTION_KEY_PAGE_DOWN = "page_down";
        public static final String ACTION_KEY_PAGE_UP = "page_up";
        public static final String ACTION_KEY_POWER_OFF = "power_off";
        public static final String ACTION_KEY_RETURN = "return";
        public static final String ACTION_KEY_RIGHT = "right";
        public static final String ACTION_KEY_SETTING = "setting";
        public static final String ACTION_KEY_UP = "up";
        public static final String ACTION_KEY_VOLUME_DOWN = "volume_down";
        public static final String ACTION_KEY_VOLUME_UP = "volume_up";
        public static final String ACTION_KEY_WHEEL_DOWN = "mouse_wheel_down";
        public static final String ACTION_KEY_WHEEL_UP = "mouse_wheel_up";
        public static final String ACTION_MOVE_CURSOR = "move_cursor";
        public static final String ACTION_PLAY_PAUSE = "play_pause";
        public static final String ACTION_PLAY_SEEK = "play_seek";
        public static final String ACTION_PLAY_START = "play_start";
        public static final String ACTION_PLAY_STOP = "play_stop";
    }
}
