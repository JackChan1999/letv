package cn.jpush.android.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.drawable.NinePatchDrawable;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.jpush.android.data.c;
import cn.jpush.android.data.i;
import cn.jpush.android.util.a;
import cn.jpush.android.util.z;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class e extends ArrayAdapter<c> {
    private static final String[] z;
    private Context a = null;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r2 = 1;
        r1 = 0;
        r0 = 3;
        r4 = new java.lang.String[r0];
        r3 = "6'[l:86B-(q$N%44&\u000fax3-](=#\u001dM+vhl_\"?";
        r0 = -1;
        r5 = r4;
        r6 = r4;
        r4 = r1;
    L_0x000b:
        r3 = r3.toCharArray();
        r7 = r3.length;
        if (r7 > r2) goto L_0x0061;
    L_0x0012:
        r8 = r1;
    L_0x0013:
        r9 = r3;
        r10 = r8;
        r13 = r7;
        r7 = r3;
        r3 = r13;
    L_0x0018:
        r12 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x0055;
            case 1: goto L_0x0058;
            case 2: goto L_0x005b;
            case 3: goto L_0x005e;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = 88;
    L_0x0021:
        r11 = r11 ^ r12;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r3 != 0) goto L_0x002d;
    L_0x0029:
        r7 = r9;
        r10 = r8;
        r8 = r3;
        goto L_0x0018;
    L_0x002d:
        r7 = r3;
        r3 = r9;
    L_0x002f:
        if (r7 > r8) goto L_0x0013;
    L_0x0031:
        r7 = new java.lang.String;
        r7.<init>(r3);
        r3 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0046;
            case 1: goto L_0x0050;
            default: goto L_0x003d;
        };
    L_0x003d:
        r5[r4] = r3;
        r0 = "3-](=#\u001dM+vhl_\"?";
        r3 = r0;
        r4 = r2;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0046:
        r5[r4] = r3;
        r3 = 2;
        r0 = "77C \u0007\"6N>v!,H";
        r4 = r3;
        r5 = r6;
        r3 = r0;
        r0 = r2;
        goto L_0x000b;
    L_0x0050:
        r5[r4] = r3;
        z = r6;
        return;
    L_0x0055:
        r11 = 81;
        goto L_0x0021;
    L_0x0058:
        r11 = 66;
        goto L_0x0021;
    L_0x005b:
        r11 = 47;
        goto L_0x0021;
    L_0x005e:
        r11 = 76;
        goto L_0x0021;
    L_0x0061:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.ui.e.<clinit>():void");
    }

    public e(Context context, int i, List<c> list) {
        super(context, Integer.MIN_VALUE, list);
        this.a = context;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            c cVar = (c) getItem(i);
            int a = a.a(this.a, 5.0f);
            view = new FrameLayout(this.a);
            View linearLayout = new LinearLayout(this.a);
            linearLayout.setPadding(a, a, a, a);
            linearLayout.setOrientation(1);
            try {
                InputStream open = this.a.getResources().getAssets().open(z[1]);
                Bitmap decodeStream = BitmapFactory.decodeStream(open);
                if (decodeStream == null) {
                    throw new RuntimeException(z[0]);
                }
                view.setBackgroundDrawable(new NinePatchDrawable(this.a.getResources(), new NinePatch(decodeStream, decodeStream.getNinePatchChunk(), null)));
                open.close();
                LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                View linearLayout2 = new LinearLayout(this.a);
                linearLayout2.setOrientation(0);
                linearLayout.addView(linearLayout2, layoutParams);
                View imageView = new ImageView(this.a);
                imageView.setBackgroundColor(-16711936);
                imageView.setScaleType(ScaleType.CENTER_CROP);
                Bitmap decodeFile = BitmapFactory.decodeFile(((i) cVar).L.l);
                if (decodeFile != null) {
                    imageView.setImageBitmap(decodeFile);
                }
                a = a.a(this.a, 45.0f);
                linearLayout2.addView(imageView, new LinearLayout.LayoutParams(a, a));
                View linearLayout3 = new LinearLayout(this.a);
                linearLayout3.setOrientation(1);
                imageView = new TextView(this.a);
                imageView.setTextSize(22.0f);
                imageView.setSingleLine();
                imageView.setEllipsize(TruncateAt.END);
                imageView.setText(cVar.c);
                linearLayout3.addView(imageView);
                imageView = new TextView(this.a);
                imageView.setSingleLine();
                imageView.setEllipsize(TruncateAt.END);
                imageView.setText(cVar.c);
                linearLayout3.addView(imageView);
                LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
                layoutParams2.gravity = 16;
                layoutParams2.leftMargin = a.a(this.a, 5.0f);
                layoutParams2.rightMargin = a.a(this.a, 5.0f);
                linearLayout2.addView(linearLayout3, layoutParams2);
                linearLayout2 = new RelativeLayout(this.a);
                int a2 = a.a(this.a, 18.0f);
                LayoutParams layoutParams3 = new LinearLayout.LayoutParams(a2, a2);
                View linearLayout4 = new LinearLayout(this.a);
                linearLayout4.setOrientation(0);
                for (a = 0; a < 3; a++) {
                    View imageView2 = new ImageView(this.a);
                    try {
                        imageView2.setImageBitmap(BitmapFactory.decodeStream(this.a.getAssets().open(z[2])));
                    } catch (IOException e) {
                        e.class.getSimpleName();
                        e.getMessage();
                        z.e();
                    }
                    linearLayout4.addView(imageView2, layoutParams3);
                }
                layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
                layoutParams2.addRule(9, -1);
                layoutParams2.addRule(15, -1);
                linearLayout2.addView(linearLayout4, layoutParams2);
                layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
                layoutParams2.addRule(11, -1);
                layoutParams2.addRule(15, -1);
                linearLayout2.addView(new TextView(this.a), layoutParams2);
                layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
                layoutParams2.topMargin = a.a(this.a, 5.0f);
                linearLayout.addView(linearLayout2, layoutParams2);
                layoutParams2 = new FrameLayout.LayoutParams(-1, -2);
                layoutParams2.gravity = 17;
                layoutParams2.topMargin = a.a(this.a, 2.0f);
                layoutParams2.leftMargin = layoutParams2.topMargin;
                layoutParams2.bottomMargin = layoutParams2.topMargin;
                layoutParams2.rightMargin = layoutParams2.topMargin;
                view.addView(linearLayout, layoutParams2);
            } catch (IOException e2) {
            }
        }
        return view;
    }
}
