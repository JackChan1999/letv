package com.letv.component.player.utils;

import android.util.FloatMath;

public class PointF {
    public float x;
    public float y;
    public float z;

    public PointF(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PointF(PointF p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }

    public final void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public final void set(PointF p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public final void offset(float dx, float dy, float dz) {
        this.x += dx;
        this.y += dy;
        this.z += dz;
    }

    public final boolean equals(float x, float y, float z) {
        return this.x == x && this.y == y && this.z == z;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PointF pointF = (PointF) o;
        if (Float.compare(pointF.x, this.x) != 0) {
            return false;
        }
        if (Float.compare(pointF.y, this.y) != 0) {
            return false;
        }
        if (Float.compare(pointF.z, this.z) != 0) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "PointF(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public static float length(float x, float y) {
        return FloatMath.sqrt((x * x) + (y * y));
    }
}
