package com.letv.android.client.view;

import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class SuperscriptSpanAdjuster extends MetricAffectingSpan {
    double ratio;

    public SuperscriptSpanAdjuster() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.ratio = 0.5d;
    }

    public SuperscriptSpanAdjuster(double ratio) {
        this.ratio = 0.5d;
        this.ratio = ratio;
    }

    public void updateDrawState(TextPaint paint) {
        paint.baselineShift += (int) (((double) paint.ascent()) * this.ratio);
    }

    public void updateMeasureState(TextPaint paint) {
        paint.baselineShift += (int) (((double) paint.ascent()) * this.ratio);
    }
}
