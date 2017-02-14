package com.coolcloud.uac.android.api.view.basic;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import java.io.IOException;

public class CustomResourceMgmt {
    public static boolean ISENGLISH = false;
    protected static volatile CustomResourceMgmt myResouceMgmt;
    private AssetsUtil mAssetsUtil = AssetsUtil.getInstance(this.mContext);
    private Context mContext;
    private LayoutInflater mLayoutInflater = LayoutInflater.from(this.mContext);
    private Resources mResources = this.mContext.getResources();
    private String packageName = this.mContext.getApplicationInfo().packageName;

    private CustomResourceMgmt(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static CustomResourceMgmt get(Context context) {
        if (myResouceMgmt == null) {
            synchronized (CustomResourceMgmt.class) {
                if (myResouceMgmt == null) {
                    myResouceMgmt = new CustomResourceMgmt(context);
                }
            }
        }
        return myResouceMgmt;
    }

    public String getString(String resouceKey) {
        return this.mAssetsUtil.getString(resouceKey);
    }

    public View getLayout(String resouceKey, boolean island) {
        return (island && havelandlayout(resouceKey)) ? getLayoutLand(resouceKey) : getLayoutPort(resouceKey);
    }

    private boolean havelandlayout(String resouceKey) {
        try {
            this.mContext.getAssets().open("layout-land/" + resouceKey + ".xml");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private View getLayoutPort(String resouceKey) {
        int resouceId = this.mResources.getIdentifier(resouceKey, "layout", this.packageName);
        if (resouceId == 0) {
            return this.mAssetsUtil.getLayout("layout/" + resouceKey + ".xml", null);
        }
        return this.mLayoutInflater.inflate(resouceId, null);
    }

    private View getLayoutLand(String resouceKey) {
        int resouceId = this.mResources.getIdentifier(resouceKey, "layout", this.packageName);
        if (resouceId == 0) {
            return this.mAssetsUtil.getLayout("layout-land/" + resouceKey + ".xml", null);
        }
        return this.mLayoutInflater.inflate(resouceId, null);
    }

    public Drawable getDrawable(String resouceKey, boolean is9Png) {
        return getDrawable(resouceKey, is9Png, 480);
    }

    public Drawable getDrawable(String resouceKey, boolean is9Png, int density) {
        int resouceId = this.mResources.getIdentifier(resouceKey, "drawable", this.packageName);
        if (resouceId != 0) {
            return this.mResources.getDrawable(resouceId);
        }
        return this.mAssetsUtil.getDrawable("drawable/" + resouceKey + ((is9Png ? ".9" : "") + ".png"), density);
    }

    public Drawable getStatusDrawable(String origin, String press, boolean is9Png) {
        int originId = this.mResources.getIdentifier(origin, "drawable", this.packageName);
        int pressId = this.mResources.getIdentifier(press, "drawable", this.packageName);
        if (originId == 0 && pressId == 0) {
            String suffix = (is9Png ? ".9" : "") + ".png";
            return new StateListDrawableBuilder(this.mContext, "drawable/" + origin + suffix).setPressDrawable("drawable/" + press + suffix).create();
        }
        Drawable mStateListDrawable = new StateListDrawable();
        mStateListDrawable.addState(new int[]{16842919, 16842910}, this.mResources.getDrawable(pressId));
        mStateListDrawable.addState(new int[0], this.mResources.getDrawable(originId));
        return mStateListDrawable;
    }

    public Drawable getCheckStatusDrawable(String origin, String checked, String pressed, String checkpressed, boolean is9Png) {
        int originId = this.mResources.getIdentifier(origin, "drawable", this.packageName);
        int selectId = this.mResources.getIdentifier(checked, "drawable", this.packageName);
        int pressedId = this.mResources.getIdentifier(pressed, "drawable", this.packageName);
        int selectpressId = this.mResources.getIdentifier(checkpressed, "drawable", this.packageName);
        if (originId == 0 && selectId == 0 && pressedId == 0 && selectpressId == 0) {
            String suffix = (is9Png ? ".9" : "") + ".png";
            return new StateListDrawableBuilder(this.mContext, "drawable/" + origin + suffix).setPressDrawable("drawable/" + pressed + suffix).setCheckedDrawable("drawable/" + checked + suffix).setPressCheckedDrawable("drawable/" + checkpressed + suffix).create();
        }
        Drawable mStateListDrawable = new StateListDrawable();
        mStateListDrawable.addState(new int[]{16842912}, this.mResources.getDrawable(selectId));
        mStateListDrawable.addState(new int[]{16842919}, this.mResources.getDrawable(pressedId));
        mStateListDrawable.addState(new int[]{16842919, 16842912}, this.mResources.getDrawable(selectpressId));
        mStateListDrawable.addState(new int[0], this.mResources.getDrawable(originId));
        return mStateListDrawable;
    }

    public Drawable getCheckStatusDrawable(String origin, String checked, boolean is9Png) {
        int originId = this.mResources.getIdentifier(origin, "drawable", this.packageName);
        int selectId = this.mResources.getIdentifier(checked, "drawable", this.packageName);
        if (originId == 0 && selectId == 0) {
            String suffix = (is9Png ? ".9" : "") + ".png";
            return new StateListDrawableBuilder(this.mContext, "drawable/" + origin + suffix).setCheckedDrawable("drawable/" + checked + suffix).create();
        }
        Drawable mStateListDrawable = new StateListDrawable();
        mStateListDrawable.addState(new int[]{16842912}, this.mResources.getDrawable(selectId));
        mStateListDrawable.addState(new int[0], this.mResources.getDrawable(originId));
        return mStateListDrawable;
    }

    public ColorStateList createSelector(String pressedClr, String normalClr) {
        state = new int[2][];
        state[0] = new int[]{16842919};
        state[1] = new int[]{-16842913};
        int color1 = Color.parseColor(pressedClr);
        int color2 = Color.parseColor(normalClr);
        return new ColorStateList(state, new int[]{color1, color2});
    }
}
