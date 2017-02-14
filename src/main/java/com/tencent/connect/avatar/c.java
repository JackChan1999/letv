package com.tencent.connect.avatar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import com.tencent.open.yyb.TitleBar;

/* compiled from: ProGuard */
public class c extends ImageView {
    final String a = "TouchView";
    public boolean b = false;
    private Matrix c = new Matrix();
    private Matrix d = new Matrix();
    private int e = 0;
    private float f = 1.0f;
    private float g = 1.0f;
    private Bitmap h;
    private boolean i = false;
    private float j;
    private float k;
    private PointF l = new PointF();
    private PointF m = new PointF();
    private float n = 1.0f;
    private float o = 0.0f;
    private Rect p = new Rect();

    public c(Context context) {
        super(context);
        getDrawingRect(this.p);
        a();
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        this.h = bitmap;
        if (bitmap != null) {
            this.h = bitmap;
        }
    }

    private float a(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() < 2) {
            return 0.0f;
        }
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return FloatMath.sqrt((x * x) + (y * y));
    }

    private void a() {
    }

    public void a(Rect rect) {
        this.p = rect;
        if (this.h != null) {
            c();
        }
    }

    private void a(PointF pointF) {
        float f = 1.0f;
        if (this.h != null) {
            float[] fArr = new float[9];
            this.c.getValues(fArr);
            float f2 = fArr[2];
            float f3 = fArr[5];
            float f4 = fArr[0];
            float width = ((float) this.h.getWidth()) * f4;
            float height = ((float) this.h.getHeight()) * f4;
            f4 = ((float) this.p.left) - f2;
            if (f4 <= 1.0f) {
                f4 = 1.0f;
            }
            f2 = (f2 + width) - ((float) this.p.right);
            if (f2 <= 1.0f) {
                f2 = 1.0f;
            }
            width = ((f4 * ((float) this.p.width())) / (f2 + f4)) + ((float) this.p.left);
            f2 = ((float) this.p.top) - f3;
            f4 = (f3 + height) - ((float) this.p.bottom);
            if (f2 <= 1.0f) {
                f2 = 1.0f;
            }
            if (f4 > 1.0f) {
                f = f4;
            }
            pointF.set(width, ((((float) this.p.height()) * f2) / (f2 + f)) + ((float) this.p.top));
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.i) {
            switch (motionEvent.getAction() & 255) {
                case 0:
                    this.c.set(getImageMatrix());
                    this.d.set(this.c);
                    this.l.set(motionEvent.getX(), motionEvent.getY());
                    this.e = 1;
                    break;
                case 1:
                case 6:
                    b();
                    this.e = 0;
                    break;
                case 2:
                    if (this.e != 1) {
                        if (this.e == 2) {
                            this.c.set(this.c);
                            float a = a(motionEvent);
                            if (a > TitleBar.SHAREBTN_RIGHT_MARGIN) {
                                this.c.set(this.d);
                                a /= this.n;
                                this.c.postScale(a, a, this.m.x, this.m.y);
                            }
                            setImageMatrix(this.c);
                            break;
                        }
                    }
                    this.c.set(this.d);
                    this.c.postTranslate(motionEvent.getX() - this.l.x, motionEvent.getY() - this.l.y);
                    setImageMatrix(this.c);
                    break;
                    break;
                case 5:
                    this.n = a(motionEvent);
                    if (this.n > TitleBar.SHAREBTN_RIGHT_MARGIN) {
                        this.d.set(this.c);
                        a(this.m);
                        this.e = 2;
                        break;
                    }
                    break;
            }
            this.b = true;
        }
        return true;
    }

    private void b() {
        if (this.h != null) {
            float width = (float) this.p.width();
            float height = (float) this.p.height();
            float[] fArr = new float[9];
            this.c.getValues(fArr);
            float f = fArr[2];
            float f2 = fArr[5];
            float f3 = fArr[0];
            Animation animation = null;
            if (f3 > this.f) {
                this.o = this.f / f3;
                this.c.postScale(this.o, this.o, this.m.x, this.m.y);
                setImageMatrix(this.c);
                animation = new ScaleAnimation(1.0f / this.o, 1.0f, 1.0f / this.o, 1.0f, this.m.x, this.m.y);
            } else if (f3 < this.g) {
                this.o = this.g / f3;
                this.c.postScale(this.o, this.o, this.m.x, this.m.y);
                animation = new ScaleAnimation(1.0f, this.o, 1.0f, this.o, this.m.x, this.m.y);
            } else {
                Object obj = null;
                float width2 = ((float) this.h.getWidth()) * f3;
                f3 *= (float) this.h.getHeight();
                float f4 = ((float) this.p.left) - f;
                float f5 = ((float) this.p.top) - f2;
                if (f4 < 0.0f) {
                    f = (float) this.p.left;
                    obj = 1;
                }
                if (f5 < 0.0f) {
                    f2 = (float) this.p.top;
                    obj = 1;
                }
                f5 = f3 - f5;
                if (width2 - f4 < width) {
                    f = ((float) this.p.left) - (width2 - width);
                    obj = 1;
                }
                if (f5 < height) {
                    f2 = ((float) this.p.top) - (f3 - height);
                    obj = 1;
                }
                if (obj != null) {
                    float f6 = fArr[2] - f;
                    width = fArr[5] - f2;
                    fArr[2] = f;
                    fArr[5] = f2;
                    this.c.setValues(fArr);
                    setImageMatrix(this.c);
                    animation = new TranslateAnimation(f6, 0.0f, width, 0.0f);
                } else {
                    setImageMatrix(this.c);
                }
            }
            if (animation != null) {
                this.i = true;
                animation.setDuration(300);
                startAnimation(animation);
                new Thread(new Runnable(this) {
                    final /* synthetic */ c a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        this.a.post(new Runnable(this) {
                            final /* synthetic */ AnonymousClass1 a;

                            {
                                this.a = r1;
                            }

                            public void run() {
                                this.a.a.clearAnimation();
                                this.a.a.b();
                            }
                        });
                        this.a.i = false;
                    }
                }).start();
            }
        }
    }

    private void c() {
        if (this.h != null) {
            float[] fArr = new float[9];
            this.c.getValues(fArr);
            float max = Math.max(((float) this.p.width()) / ((float) this.h.getWidth()), ((float) this.p.height()) / ((float) this.h.getHeight()));
            this.j = ((float) this.p.left) - (((((float) this.h.getWidth()) * max) - ((float) this.p.width())) / 2.0f);
            this.k = ((float) this.p.top) - (((((float) this.h.getHeight()) * max) - ((float) this.p.height())) / 2.0f);
            fArr[2] = this.j;
            fArr[5] = this.k;
            fArr[4] = max;
            fArr[0] = max;
            this.c.setValues(fArr);
            this.f = Math.min(2048.0f / ((float) this.h.getWidth()), 2048.0f / ((float) this.h.getHeight()));
            this.g = max;
            if (this.f < this.g) {
                this.f = this.g;
            }
            setImageMatrix(this.c);
        }
    }
}
