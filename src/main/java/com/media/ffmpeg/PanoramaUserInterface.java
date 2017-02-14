package com.media.ffmpeg;

import com.letv.component.player.utils.LogTag;

public class PanoramaUserInterface {
    private final float TOUCH_SCALE_FACTOR = 0.1f;
    public boolean gravity_yro_valid_info = true;
    public float total_angle_x = 0.0f;
    public float total_angle_y = 0.0f;
    public float total_angle_z = 0.0f;
    public float total_zoom = 1.0f;

    public PanoramaUserInterface(float total_angle_x, float total_angle_y, float total_angle_z, float gravity_z, float total_zoom) {
        this.total_angle_x = total_angle_x;
        this.total_angle_y = total_angle_y;
        this.total_angle_z = total_angle_z;
        this.total_zoom = total_zoom;
    }

    public int setMachineInfomation(float ScreenResolution) {
        return 0;
    }

    public int setOneFingertouchInfomation(float begin_x, float begin_y, float end_x, float end_y) {
        int i = 1;
        float dy = end_x - begin_x;
        float dx = end_y - begin_y;
        if (((this.total_angle_z < -135.0f ? 1 : 0) | (this.total_angle_z > 135.0f ? 1 : 0)) != 0) {
            this.total_angle_y -= dy * 0.1f;
            this.total_angle_x -= dx * 0.1f;
        } else {
            int i2;
            if (45.0f <= this.total_angle_z) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            if ((i2 & (this.total_angle_z <= 135.0f ? 1 : 0)) != 0) {
                this.total_angle_y -= dx * 0.1f;
                this.total_angle_x += dy * 0.1f;
            } else {
                if (-45.0f <= this.total_angle_z) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                if ((i2 & (this.total_angle_z < 45.0f ? 1 : 0)) != 0) {
                    this.total_angle_y += dy * 0.1f;
                    this.total_angle_x += dx * 0.1f;
                } else {
                    if (-135.0f <= this.total_angle_z) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    if (this.total_angle_z >= -45.0f) {
                        i = 0;
                    }
                    if ((i2 & i) != 0) {
                        this.total_angle_y += dx * 0.1f;
                        this.total_angle_x -= dy * 0.1f;
                    }
                }
            }
        }
        return 0;
    }

    public int setTwoFingertouchInfomation(float begin_x0, float begin_y0, float begin_x1, float begin_y1, float end_x0, float end_y0, float end_x1, float end_y1) {
        float _nx = begin_x1 - begin_x0;
        float _ny = begin_y1 - begin_y0;
        float oldDest = (float) Math.sqrt((double) ((_nx * _nx) + (_ny * _ny)));
        float _nx1 = end_x1 - end_x0;
        float _ny1 = end_y1 - end_y0;
        float newDest = (float) Math.sqrt((double) ((_nx1 * _nx1) + (_ny1 * _ny1)));
        LogTag.i("oldDest=" + oldDest + "newDest" + newDest);
        if (((double) this.total_zoom) >= 0.5d && this.total_zoom <= 2.0f) {
            this.total_zoom *= newDest / oldDest;
            LogTag.i("");
            LogTag.i("(total_zoom>=0.5)&&(total_zoom<=2)####oldDest=" + oldDest + "newDest" + newDest);
        } else if (((double) this.total_zoom) < 0.5d) {
            LogTag.i("(total_zoom<0.5)####oldDest=" + oldDest + "newDest" + newDest);
            if (newDest > oldDest) {
                this.total_zoom *= newDest / oldDest;
            } else {
                this.total_zoom = this.total_zoom;
            }
        } else if (this.total_zoom > 2.0f) {
            LogTag.i("(total_zoom>2)####oldDest=" + oldDest + "newDest" + newDest);
            if (newDest < oldDest) {
                this.total_zoom *= newDest / oldDest;
            } else {
                this.total_zoom = this.total_zoom;
            }
        } else {
            LogTag.i("####oldDest=" + oldDest + "newDest" + newDest);
        }
        return 0;
    }

    public int setgravity_yroInfomation(float gravity_yro_x, float gravity_yro_y, float gravity_yro_z) {
        int i;
        int i2 = 1;
        if (((double) Math.abs(gravity_yro_x)) > 0.05d) {
            if (((this.total_angle_z < -135.0f ? 1 : 0) | (this.total_angle_z > 135.0f ? 1 : 0)) != 0) {
                this.total_angle_y -= gravity_yro_x;
            } else {
                if (45.0f <= this.total_angle_z) {
                    i = 1;
                } else {
                    i = 0;
                }
                if ((i & (this.total_angle_z <= 135.0f ? 1 : 0)) != 0) {
                    this.total_angle_x += gravity_yro_x;
                } else {
                    if (-45.0f <= this.total_angle_z) {
                        i = 1;
                    } else {
                        i = 0;
                    }
                    if ((i & (this.total_angle_z < 45.0f ? 1 : 0)) != 0) {
                        this.total_angle_y += gravity_yro_x;
                    } else {
                        if (-135.0f <= this.total_angle_z) {
                            i = 1;
                        } else {
                            i = 0;
                        }
                        if ((i & (this.total_angle_z < -45.0f ? 1 : 0)) != 0) {
                            this.total_angle_x -= gravity_yro_x;
                        }
                    }
                }
            }
        }
        if (((double) Math.abs(gravity_yro_y)) > 0.05d) {
            if (this.total_angle_z < -135.0f) {
                i = 1;
            } else {
                i = 0;
            }
            if ((i | (this.total_angle_z > 135.0f ? 1 : 0)) != 0) {
                this.total_angle_x += gravity_yro_y;
            } else {
                if (45.0f <= this.total_angle_z) {
                    i = 1;
                } else {
                    i = 0;
                }
                if ((i & (this.total_angle_z <= 135.0f ? 1 : 0)) != 0) {
                    this.total_angle_y += gravity_yro_y;
                } else {
                    if (-45.0f <= this.total_angle_z) {
                        i = 1;
                    } else {
                        i = 0;
                    }
                    if ((i & (this.total_angle_z < 45.0f ? 1 : 0)) != 0) {
                        this.total_angle_x -= gravity_yro_y;
                    } else {
                        if (-135.0f <= this.total_angle_z) {
                            i = 1;
                        } else {
                            i = 0;
                        }
                        if (this.total_angle_z >= -45.0f) {
                            i2 = 0;
                        }
                        if ((i & i2) != 0) {
                            this.total_angle_y -= gravity_yro_y;
                        }
                    }
                }
            }
        }
        return 0;
    }

    public int setGravityInfomation(float gravity_x, float gravity_y, float gravity_z) {
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
                this.total_angle_z = (-g) + 180.0f;
            } else if ((-g) > 0.0f) {
                this.total_angle_z = (-g) - 180.0f;
            }
        }
        return 0;
    }

    public int setgravity_yroValidInfomation(boolean gravityValid) {
        this.gravity_yro_valid_info = gravityValid;
        return 0;
    }

    public int setAngleInit() {
        return 0;
    }

    public int setTwoFingerZoom(float zoom) {
        this.total_zoom = zoom;
        return 0;
    }
}
