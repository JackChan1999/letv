package com.letv.component.player.Interface;

public interface OnSwitchListener {
    public static final int FAIL_CODE_CANNOT_HARD_DECODE = 302;
    public static final int FAIL_CODE_CANNOT_SOFT_DECODE = 301;
    public static final int FAIL_CODE_LLLEGL_SWITCH = 303;
    public static final int FAIL_CODE_NO_PARENT = 300;
    public static final int SWITCH_TO_HARD = 200;
    public static final int SWITCH_TO_MP4 = 201;
    public static final int SWITCH_TO_SOFT = 201;

    void onSwitchSuccess(int i);

    void onSwtichFail(int i, int i2);
}
