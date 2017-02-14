package com.letv.lemallsdk.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class PopupMenuItem {
    public Drawable mDrawable;
    public CharSequence mTitle;

    public PopupMenuItem(Drawable drawable, CharSequence title) {
        this.mDrawable = drawable;
        this.mTitle = title;
    }

    public PopupMenuItem(Context context, int titleId, int drawableId) {
        this.mTitle = context.getResources().getText(titleId);
        this.mDrawable = context.getResources().getDrawable(drawableId);
    }

    public PopupMenuItem(Context context, CharSequence title, int drawableId) {
        this.mTitle = title;
        this.mDrawable = context.getResources().getDrawable(drawableId);
    }
}
