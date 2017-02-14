package com.letv.component.player.utils;

public class PanoramaUtils {
    static String TAG = "PanoramaUtils";
    private static final float TOUCH_SCALE_FACTOR = 0.1f;
    private static boolean isGravityEnable = true;
    private static float total_angle_x = 0.0f;
    private static float total_angle_y = 0.0f;
    private static float total_angle_z = 0.0f;

    public static void enableGravity(boolean enable) {
        isGravityEnable = enable;
    }

    public static boolean isGravityEnable() {
        return isGravityEnable;
    }

    public static int setMachineInfomation(float ScreenResolution) {
        return 0;
    }

    public int setAngleInit() {
        return 0;
    }

    public static PointF changeCameraDirection(float begin_x, float begin_y, float end_x, float end_y) {
        float dy = end_x - begin_x;
        float dx = end_y - begin_y;
        if (total_angle_z < -135.0f || total_angle_z > 135.0f) {
            total_angle_y -= dy * TOUCH_SCALE_FACTOR;
            total_angle_x -= dx * TOUCH_SCALE_FACTOR;
        } else if (45.0f <= total_angle_z && total_angle_z <= 135.0f) {
            total_angle_y -= dx * TOUCH_SCALE_FACTOR;
            total_angle_x += dy * TOUCH_SCALE_FACTOR;
        } else if (-45.0f <= total_angle_z && total_angle_z < 45.0f) {
            total_angle_y += dy * TOUCH_SCALE_FACTOR;
            total_angle_x += dx * TOUCH_SCALE_FACTOR;
        } else if (-135.0f <= total_angle_z && total_angle_z < -45.0f) {
            total_angle_y += dx * TOUCH_SCALE_FACTOR;
            total_angle_x -= dy * TOUCH_SCALE_FACTOR;
        }
        return new PointF(total_angle_x, total_angle_y, total_angle_z);
    }

    public static float processDoubleTouchInfo(float begin_x0, float begin_y0, float begin_x1, float begin_y1, float end_x0, float end_y0, float end_x1, float end_y1) {
        float zoom = 1.0f;
        float _nx = begin_x1 - begin_x0;
        float _ny = begin_y1 - begin_y0;
        float oldDest = (float) Math.sqrt((double) ((_nx * _nx) + (_ny * _ny)));
        float _nx1 = end_x1 - end_x0;
        float _ny1 = end_y1 - end_y0;
        float newDest = (float) Math.sqrt((double) ((_nx1 * _nx1) + (_ny1 * _ny1)));
        LogTag.i(TAG, "oldDest=" + oldDest + "newDest" + newDest);
        if (((double) 1065353216) >= 0.5d && 1.0f <= 2.0f) {
            zoom = 1.0f * (newDest / oldDest);
            LogTag.i(TAG, "(zoom>=0.5)&&(zoom<=2)####oldDest=" + oldDest + "newDest" + newDest);
        } else if (((double) 1065353216) < 0.5d) {
            LogTag.i(TAG, "(zoom<0.5)####oldDest=" + oldDest + "newDest" + newDest);
            if (newDest > oldDest) {
                zoom = 1.0f * (newDest / oldDest);
            }
        } else if (1.0f > 2.0f) {
            LogTag.i(TAG, "(zoom>2)####oldDest=" + oldDest + "newDest" + newDest);
            if (newDest < oldDest) {
                zoom = 1.0f * (newDest / oldDest);
            }
        } else {
            LogTag.i(TAG, "####oldDest=" + oldDest + "newDest" + newDest);
        }
        LogTag.i(TAG, "processDoubleTouchInfo, zoom: " + zoom);
        return zoom;
    }

    public static PointF processGyroscopeInfo(float gravity_yro_x, float gravity_yro_y, float gravity_yro_z) {
        if (((double) Math.abs(gravity_yro_x)) > 0.05d) {
            if (total_angle_z < -135.0f || total_angle_z > 135.0f) {
                total_angle_y -= gravity_yro_x;
            } else if (45.0f <= total_angle_z && total_angle_z <= 135.0f) {
                total_angle_x += gravity_yro_x;
            } else if (-45.0f <= total_angle_z && total_angle_z < 45.0f) {
                total_angle_y += gravity_yro_x;
            } else if (-135.0f <= total_angle_z && total_angle_z < -45.0f) {
                total_angle_x -= gravity_yro_x;
            }
        }
        if (((double) Math.abs(gravity_yro_y)) > 0.05d) {
            if (total_angle_z < -135.0f || total_angle_z > 135.0f) {
                total_angle_x += gravity_yro_y;
            } else if (45.0f <= total_angle_z && total_angle_z <= 135.0f) {
                total_angle_y += gravity_yro_y;
            } else if (-45.0f <= total_angle_z && total_angle_z < 45.0f) {
                total_angle_x -= gravity_yro_y;
            } else if (-135.0f <= total_angle_z && total_angle_z < -45.0f) {
                total_angle_y -= gravity_yro_y;
            }
        }
        float x = total_angle_x;
        float y = total_angle_y;
        PointF pt = new PointF(total_angle_x, total_angle_y, total_angle_z);
        LogTag.i("processGyroscopeInfo, x:y:z<->" + pt.toString());
        return pt;
    }

    public static float processGravitySensorInfo(float gravity_x, float gravity_y, float gravity_z) {
        float g = 0.0f;
        if (gravity_y <= 0.0f) {
            if (gravity_x <= 0.0f) {
                g = (float) ((Math.asin(((double) gravity_y) / Math.sqrt((double) ((gravity_x * gravity_x) + (gravity_y * gravity_y)))) * 180.0d) / 3.141592653589793d);
            } else {
                g = -180.0f - ((float) ((Math.asin(((double) gravity_y) / Math.sqrt((double) ((gravity_x * gravity_x) + (gravity_y * gravity_y)))) * 180.0d) / 3.141592653589793d));
            }
        } else if (gravity_y > 0.0f) {
            if (gravity_x <= 0.0f) {
                g = (float) ((Math.asin(((double) gravity_y) / Math.sqrt((double) ((gravity_x * gravity_x) + (gravity_y * gravity_y)))) * 180.0d) / 3.141592653589793d);
            } else {
                g = 180.0f - ((float) ((Math.asin(((double) gravity_y) / Math.sqrt((double) ((gravity_x * gravity_x) + (gravity_y * gravity_y)))) * 180.0d) / 3.141592653589793d));
            }
        }
        if (Math.abs(gravity_x) > 1.0f || Math.abs(gravity_y) > 1.0f) {
            if ((-g) < 0.0f) {
                total_angle_z = (-g) + 180.0f;
            } else if ((-g) > 0.0f) {
                total_angle_z = (-g) - 180.0f;
            }
        }
        LogTag.i("processGravitySensorInfo, total_angle_z: " + total_angle_z);
        return total_angle_z;
    }
}
