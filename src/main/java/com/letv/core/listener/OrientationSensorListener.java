package com.letv.core.listener;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;
import com.letv.core.messagebus.config.LeMessageIds;

public class OrientationSensorListener implements SensorEventListener {
    public static final int ORIENTATION_UNKNOWN = -1;
    private static final int SENSOR_CHANGED_PROCESS_CONSTANT = 10001;
    private static final int SENSOR_CHANGED_PROCESS_TIME = 800;
    private static final int _DATA_X = 0;
    private static final int _DATA_Y = 1;
    private static final int _DATA_Z = 2;
    private Activity activity;
    private int currOrientation = -1;
    private boolean isLock;
    private boolean isSensorChanged = false;
    private boolean justLandscape;
    private int lastOrientation = -1;
    private int lockOnce = -1;
    private Handler mHandler = new 1(this);
    private int orientationGlobal = 0;
    private int requestedOrientationGlobal = 0;
    private Handler rotateHandler;

    public OrientationSensorListener(Handler handler, Activity activity) {
        this.rotateHandler = handler;
        this.activity = activity;
    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    public void onSensorChanged(SensorEvent event) {
        int requestedOrientation = this.activity.getRequestedOrientation();
        if (requestedOrientation == 4) {
            int rt = this.activity.getWindowManager().getDefaultDisplay().getRotation();
            if (rt == 0) {
                if (this.rotateHandler != null) {
                    this.rotateHandler.obtainMessage(4).sendToTarget();
                }
            } else if (rt == 1) {
                if (this.rotateHandler != null) {
                    this.rotateHandler.obtainMessage(3).sendToTarget();
                }
            } else if (rt == 2) {
                if (this.rotateHandler != null) {
                    this.rotateHandler.obtainMessage(2).sendToTarget();
                }
            } else if (rt == 3 && this.rotateHandler != null) {
                this.rotateHandler.obtainMessage(1).sendToTarget();
            }
        } else if (event.sensor != null) {
            float[] values = event.values;
            int orientation = -1;
            float X = -values[0];
            float Y = -values[1];
            float Z = -values[2];
            if (4.0f * ((X * X) + (Y * Y)) >= Z * Z) {
                orientation = 90 - Math.round(((float) Math.atan2((double) (-Y), (double) X)) * 57.29578f);
                while (orientation >= 360) {
                    orientation -= 360;
                }
                while (orientation < 0) {
                    orientation += 360;
                }
            }
            getCurrentOrientation(orientation);
            this.requestedOrientationGlobal = requestedOrientation;
            this.orientationGlobal = orientation;
        }
    }

    private void getCurrentOrientation(int orientation) {
        int lastOrientionTmp = this.currOrientation;
        if (orientation >= 60 && orientation <= LeMessageIds.MSG_ALBUM_HALF_FETCH_EXPEND_VIEWPAGER_LAYOUT) {
            this.currOrientation = 1;
        } else if (orientation > 150 && orientation < 210 && !this.justLandscape) {
            this.currOrientation = 2;
        } else if (orientation > 240 && orientation < 300) {
            this.currOrientation = 3;
        } else if (orientation > 330 && orientation < 360) {
            this.currOrientation = 4;
        } else if (orientation > 0 && orientation < 30 && !this.justLandscape) {
            this.currOrientation = 4;
        }
        if (lastOrientionTmp != this.currOrientation && this.mHandler != null && this.activity != null && !this.activity.isFinishing()) {
            if (this.isSensorChanged) {
                this.mHandler.removeMessages(10001);
            }
            this.isSensorChanged = true;
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(10001, lastOrientionTmp, 0), 800);
        }
    }

    private void sensorChangedProcess(int requestedOrientation, int orientation) {
        if (orientation < 60 || orientation > LeMessageIds.MSG_ALBUM_HALF_FETCH_EXPEND_VIEWPAGER_LAYOUT) {
            if (orientation <= 150 || orientation >= 210 || this.justLandscape) {
                if (orientation <= 240 || orientation >= 300) {
                    if (((orientation > 330 && orientation < 360) || (orientation > 0 && orientation < 30)) && !this.justLandscape) {
                        if (this.lockOnce == -1) {
                            if (requestedOrientation == 1) {
                                return;
                            }
                            if ((!this.isLock || requestedOrientation == 4) && this.rotateHandler != null) {
                                this.rotateHandler.obtainMessage(4).sendToTarget();
                            }
                        } else if (this.lockOnce != 1) {
                            this.lockOnce = -1;
                            if (requestedOrientation == 1) {
                                return;
                            }
                            if ((!this.isLock || requestedOrientation == 4) && this.rotateHandler != null) {
                                this.rotateHandler.obtainMessage(4).sendToTarget();
                            }
                        }
                    }
                } else if (this.lockOnce == -1) {
                    if (requestedOrientation == 0) {
                        return;
                    }
                    if ((!this.isLock || requestedOrientation == 4) && this.rotateHandler != null) {
                        this.rotateHandler.obtainMessage(3).sendToTarget();
                    }
                } else if (this.lockOnce != 0) {
                    this.lockOnce = -1;
                    if (requestedOrientation == 0) {
                        return;
                    }
                    if ((!this.isLock || requestedOrientation == 4) && this.rotateHandler != null) {
                        this.rotateHandler.obtainMessage(3).sendToTarget();
                    }
                }
            } else if (this.lockOnce == -1) {
                if (requestedOrientation == 9) {
                    return;
                }
                if ((!this.isLock || requestedOrientation == 4) && this.rotateHandler != null) {
                    this.rotateHandler.obtainMessage(2).sendToTarget();
                }
            } else if (this.lockOnce != 9) {
                this.lockOnce = -1;
                if (requestedOrientation == 9) {
                    return;
                }
                if ((!this.isLock || requestedOrientation == 4) && this.rotateHandler != null) {
                    this.rotateHandler.obtainMessage(2).sendToTarget();
                }
            }
        } else if (this.lockOnce == -1) {
            if (requestedOrientation == 8) {
                return;
            }
            if ((!this.isLock || requestedOrientation == 4) && this.rotateHandler != null) {
                this.rotateHandler.obtainMessage(1).sendToTarget();
            }
        } else if (this.lockOnce != 8) {
            this.lockOnce = -1;
            if (requestedOrientation == 8) {
                return;
            }
            if ((!this.isLock || requestedOrientation == 4) && this.rotateHandler != null) {
                this.rotateHandler.obtainMessage(1).sendToTarget();
            }
        }
    }

    public void refreshLandspaceWhenLock() {
        if (!this.isLock && this.lockOnce == -1) {
            return;
        }
        if (this.orientationGlobal < 60 || this.orientationGlobal > LeMessageIds.MSG_ALBUM_HALF_FETCH_EXPEND_VIEWPAGER_LAYOUT) {
            if (this.orientationGlobal > 240 && this.orientationGlobal < 300 && this.rotateHandler != null) {
                this.rotateHandler.obtainMessage(3).sendToTarget();
            }
        } else if (this.rotateHandler != null) {
            this.rotateHandler.obtainMessage(1).sendToTarget();
        }
    }

    public boolean isLock() {
        return this.isLock;
    }

    public void setLock(boolean isLock) {
        if (!isLock && this.isLock) {
            this.isLock = isLock;
            sensorChangedProcess(this.requestedOrientationGlobal, this.orientationGlobal);
        }
        this.isLock = isLock;
    }

    public void lockOnce(int orientation) {
        this.lockOnce = orientation;
    }

    public boolean isJustLandscape() {
        return this.justLandscape;
    }

    public void setJustLandscape(boolean justLandscape) {
        this.justLandscape = justLandscape;
    }
}
