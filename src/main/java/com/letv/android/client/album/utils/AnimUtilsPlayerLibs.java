package com.letv.android.client.album.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import com.letv.android.client.album.R;

public class AnimUtilsPlayerLibs {
    private static final int animTotallTime = 220;
    private static float[] collectionAniScaleRec = new float[]{1.0f, 0.6f, 1.5f, 0.8f, 1.0f};
    private static float scaleRate = 0.1f;

    public static void inFromLeftAnim(View videoBar, View downLoadBar, View animView, Context context) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.in_from_right);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setFillAfter(true);
        animation.setAnimationListener(new 1(animView, downLoadBar, videoBar));
        if (animView != null) {
            animView.setEnabled(false);
        }
        if (downLoadBar != null) {
            downLoadBar.setEnabled(false);
        }
        if (videoBar != null) {
            videoBar.setEnabled(false);
        }
        animView.startAnimation(animation);
    }

    public static void animTop(Context context, View view) {
        if (view == null) {
            return;
        }
        if (view.getAnimation() == null || view.getAnimation().hasEnded()) {
            view.post(new 2(view));
        }
    }

    private static void animCollection(View view, float[] aniScaleRec, int index) {
        animFrames(view, index, aniScaleRec, new 3(index, aniScaleRec, view));
    }

    private static void animFrames(View view, int index, float[] aniScaleRec, Runnable callback) {
        float totalFrame = 0.0f;
        for (int i = 0; i < aniScaleRec.length - 1; i++) {
            totalFrame += Math.abs(aniScaleRec[i + 1] - aniScaleRec[i]);
        }
        int animFrameTime = animTotallTime / ((int) (totalFrame / scaleRate));
        float animEnd = aniScaleRec[index + 1];
        float animStart = aniScaleRec[index];
        int duration = Math.abs((((int) (100.0f * animEnd)) - ((int) (100.0f * animStart))) / ((int) (scaleRate * 100.0f))) * animFrameTime;
        ScaleAnimation sa = new ScaleAnimation(animStart, animEnd, animStart, animEnd, 1, 0.5f, 1, 0.5f);
        sa.setFillAfter(true);
        sa.setDuration((long) duration);
        sa.setAnimationListener(new 4(callback));
        view.startAnimation(sa);
    }
}
