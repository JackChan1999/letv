package com.tencent.connect.avatar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.connect.common.Constants;
import com.tencent.open.SocialConstants;
import com.tencent.open.b.d;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.Util;
import com.tencent.open.yyb.TitleBar;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class ImageActivity extends Activity {
    RelativeLayout a;
    private QQToken b;
    private String c;
    private Handler d;
    private c e;
    private Button f;
    private Button g;
    private b h;
    private TextView i;
    private ProgressBar j;
    private int k = 0;
    private boolean l = false;
    private long m = 0;
    private int n = 0;
    private final int o = 640;
    private final int p = 640;
    private Rect q = new Rect();
    private String r;
    private Bitmap s;
    private final OnClickListener t = new OnClickListener(this) {
        final /* synthetic */ ImageActivity a;

        {
            this.a = r1;
        }

        public void onClick(View view) {
            this.a.j.setVisibility(0);
            this.a.g.setEnabled(false);
            this.a.g.setTextColor(Color.rgb(21, 21, 21));
            this.a.f.setEnabled(false);
            this.a.f.setTextColor(Color.rgb(36, 94, 134));
            new Thread(new Runnable(this) {
                final /* synthetic */ AnonymousClass2 a;

                {
                    this.a = r1;
                }

                public void run() {
                    this.a.a.c();
                }
            }).start();
            if (this.a.l) {
                this.a.a("10657", 0);
                return;
            }
            this.a.a("10655", System.currentTimeMillis() - this.a.m);
            if (this.a.e.b) {
                this.a.a("10654", 0);
            }
        }
    };
    private final OnClickListener u = new OnClickListener(this) {
        final /* synthetic */ ImageActivity a;

        {
            this.a = r1;
        }

        public void onClick(View view) {
            this.a.a("10656", System.currentTimeMillis() - this.a.m);
            this.a.setResult(0);
            this.a.d();
        }
    };
    private final IUiListener v = new IUiListener(this) {
        final /* synthetic */ ImageActivity a;

        {
            this.a = r1;
        }

        public void onError(UiError uiError) {
            this.a.g.setEnabled(true);
            this.a.g.setTextColor(-1);
            this.a.f.setEnabled(true);
            this.a.f.setTextColor(-1);
            this.a.f.setText("重试");
            this.a.j.setVisibility(8);
            this.a.l = true;
            this.a.a(uiError.errorMessage, 1);
            this.a.a("10660", 0);
        }

        public void onComplete(Object obj) {
            int i;
            this.a.g.setEnabled(true);
            this.a.g.setTextColor(-1);
            this.a.f.setEnabled(true);
            this.a.f.setTextColor(-1);
            this.a.j.setVisibility(8);
            JSONObject jSONObject = (JSONObject) obj;
            try {
                i = jSONObject.getInt("ret");
            } catch (JSONException e) {
                e.printStackTrace();
                i = -1;
            }
            if (i == 0) {
                this.a.a("设置成功", 0);
                this.a.a("10658", 0);
                d.a().a(this.a.b.getOpenId(), this.a.b.getAppId(), Constants.VIA_SET_AVATAR_SUCCEED, "12", "3", "0");
                Context context = this.a;
                if (!(this.a.c == null || "".equals(this.a.c))) {
                    Intent intent = new Intent();
                    intent.setClassName(context, this.a.c);
                    if (context.getPackageManager().resolveActivity(intent, 0) != null) {
                        context.startActivity(intent);
                    }
                }
                this.a.a(0, jSONObject.toString(), null, null);
                this.a.d();
                return;
            }
            this.a.a("设置出错了，请重新登录再尝试下呢：）", 1);
            d.a().a(this.a.b.getOpenId(), this.a.b.getAppId(), Constants.VIA_SET_AVATAR_SUCCEED, "12", "19", "1");
        }

        public void onCancel() {
        }
    };
    private final IUiListener w = new IUiListener(this) {
        final /* synthetic */ ImageActivity a;

        {
            this.a = r1;
        }

        public void onError(UiError uiError) {
            a(0);
        }

        public void onComplete(Object obj) {
            JSONObject jSONObject = (JSONObject) obj;
            int i = -1;
            try {
                i = jSONObject.getInt("ret");
                if (i == 0) {
                    final String string = jSONObject.getString("nickname");
                    this.a.d.post(new Runnable(this) {
                        final /* synthetic */ AnonymousClass6 b;

                        public void run() {
                            this.b.a.c(string);
                        }
                    });
                    this.a.a("10659", 0);
                } else {
                    this.a.a("10661", 0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (i != 0) {
                a(i);
            }
        }

        public void onCancel() {
        }

        private void a(int i) {
            if (this.a.k < 2) {
                this.a.e();
            }
        }
    };

    /* compiled from: ProGuard */
    private class QQAvatarImp extends BaseApi {
        final /* synthetic */ ImageActivity a;

        public QQAvatarImp(ImageActivity imageActivity, QQToken qQToken) {
            this.a = imageActivity;
            super(qQToken);
        }

        public void setAvator(Bitmap bitmap, IUiListener iUiListener) {
            Bundle composeCGIParams = composeCGIParams();
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, 40, byteArrayOutputStream);
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            bitmap.recycle();
            IRequestListener tempRequestListener = new TempRequestListener(iUiListener);
            composeCGIParams.putByteArray(SocialConstants.PARAM_AVATAR_URI, toByteArray);
            HttpUtils.requestAsync(this.mToken, Global.getContext(), "user/set_user_face", composeCGIParams, "POST", tempRequestListener);
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_SET_AVATAR_SUCCEED, "12", "19", "0");
        }
    }

    /* compiled from: ProGuard */
    class a extends View {
        final /* synthetic */ ImageActivity a;

        public a(ImageActivity imageActivity, Context context) {
            this.a = imageActivity;
            super(context);
        }

        public void a(Button button) {
            Drawable stateListDrawable = new StateListDrawable();
            Drawable a = this.a.b("com.tencent.plus.blue_normal.png");
            Drawable a2 = this.a.b("com.tencent.plus.blue_down.png");
            Drawable a3 = this.a.b("com.tencent.plus.blue_disable.png");
            stateListDrawable.addState(View.PRESSED_ENABLED_STATE_SET, a2);
            stateListDrawable.addState(View.ENABLED_FOCUSED_STATE_SET, a);
            stateListDrawable.addState(View.ENABLED_STATE_SET, a);
            stateListDrawable.addState(View.FOCUSED_STATE_SET, a);
            stateListDrawable.addState(View.EMPTY_STATE_SET, a3);
            button.setBackgroundDrawable(stateListDrawable);
        }

        public void b(Button button) {
            Drawable stateListDrawable = new StateListDrawable();
            Drawable a = this.a.b("com.tencent.plus.gray_normal.png");
            Drawable a2 = this.a.b("com.tencent.plus.gray_down.png");
            Drawable a3 = this.a.b("com.tencent.plus.gray_disable.png");
            stateListDrawable.addState(View.PRESSED_ENABLED_STATE_SET, a2);
            stateListDrawable.addState(View.ENABLED_FOCUSED_STATE_SET, a);
            stateListDrawable.addState(View.ENABLED_STATE_SET, a);
            stateListDrawable.addState(View.FOCUSED_STATE_SET, a);
            stateListDrawable.addState(View.EMPTY_STATE_SET, a3);
            button.setBackgroundDrawable(stateListDrawable);
        }
    }

    private Bitmap a(String str) throws IOException {
        int i = 1;
        Options options = new Options();
        options.inJustDecodeBounds = true;
        Uri parse = Uri.parse(str);
        InputStream openInputStream = getContentResolver().openInputStream(parse);
        if (openInputStream == null) {
            return null;
        }
        BitmapFactory.decodeStream(openInputStream, null, options);
        openInputStream.close();
        int i2 = options.outWidth;
        int i3 = options.outHeight;
        while (i2 * i3 > 4194304) {
            i2 /= 2;
            i3 /= 2;
            i *= 2;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = i;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(parse), null, options);
    }

    private Drawable b(String str) {
        Drawable createFromStream;
        IOException e;
        try {
            InputStream open = getAssets().open(str);
            createFromStream = Drawable.createFromStream(open, str);
            try {
                open.close();
            } catch (IOException e2) {
                e = e2;
                e.printStackTrace();
                return createFromStream;
            }
        } catch (IOException e3) {
            IOException iOException = e3;
            createFromStream = null;
            e = iOException;
            e.printStackTrace();
            return createFromStream;
        }
        return createFromStream;
    }

    private View a() {
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        LayoutParams layoutParams2 = new LayoutParams(-1, -1);
        LayoutParams layoutParams3 = new LayoutParams(-2, -2);
        this.a = new RelativeLayout(this);
        this.a.setLayoutParams(layoutParams);
        this.a.setBackgroundColor(-16777216);
        View relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(layoutParams3);
        this.a.addView(relativeLayout);
        this.e = new c(this);
        this.e.setLayoutParams(layoutParams2);
        this.e.setScaleType(ScaleType.MATRIX);
        relativeLayout.addView(this.e);
        this.h = new b(this);
        LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(layoutParams2);
        layoutParams4.addRule(14, -1);
        layoutParams4.addRule(15, -1);
        this.h.setLayoutParams(layoutParams4);
        relativeLayout.addView(this.h);
        relativeLayout = new LinearLayout(this);
        layoutParams2 = new RelativeLayout.LayoutParams(-2, a.a(this, 80.0f));
        layoutParams2.addRule(14, -1);
        relativeLayout.setLayoutParams(layoutParams2);
        relativeLayout.setOrientation(0);
        relativeLayout.setGravity(17);
        this.a.addView(relativeLayout);
        View imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(a.a(this, 24.0f), a.a(this, 24.0f)));
        imageView.setImageDrawable(b("com.tencent.plus.logo.png"));
        relativeLayout.addView(imageView);
        this.i = new TextView(this);
        layoutParams2 = new LinearLayout.LayoutParams(layoutParams3);
        layoutParams2.leftMargin = a.a(this, 7.0f);
        this.i.setLayoutParams(layoutParams2);
        this.i.setEllipsize(TruncateAt.END);
        this.i.setSingleLine();
        this.i.setTextColor(-1);
        this.i.setTextSize(24.0f);
        this.i.setVisibility(8);
        relativeLayout.addView(this.i);
        relativeLayout = new RelativeLayout(this);
        layoutParams2 = new RelativeLayout.LayoutParams(-1, a.a(this, 60.0f));
        layoutParams2.addRule(12, -1);
        layoutParams2.addRule(9, -1);
        relativeLayout.setLayoutParams(layoutParams2);
        relativeLayout.setBackgroundDrawable(b("com.tencent.plus.bar.png"));
        int a = a.a(this, TitleBar.SHAREBTN_RIGHT_MARGIN);
        relativeLayout.setPadding(a, a, a, 0);
        this.a.addView(relativeLayout);
        a aVar = new a(this, this);
        int a2 = a.a(this, 14.0f);
        int a3 = a.a(this, 7.0f);
        this.g = new Button(this);
        this.g.setLayoutParams(new RelativeLayout.LayoutParams(a.a(this, 78.0f), a.a(this, 45.0f)));
        this.g.setText("取消");
        this.g.setTextColor(-1);
        this.g.setTextSize(18.0f);
        this.g.setPadding(a2, a3, a2, a3);
        aVar.b(this.g);
        relativeLayout.addView(this.g);
        this.f = new Button(this);
        LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(a.a(this, 78.0f), a.a(this, 45.0f));
        layoutParams5.addRule(11, -1);
        this.f.setLayoutParams(layoutParams5);
        this.f.setTextColor(-1);
        this.f.setTextSize(18.0f);
        this.f.setPadding(a2, a3, a2, a3);
        this.f.setText("选取");
        aVar.a(this.f);
        relativeLayout.addView(this.f);
        imageView = new TextView(this);
        layoutParams4 = new RelativeLayout.LayoutParams(layoutParams3);
        layoutParams4.addRule(13, -1);
        imageView.setLayoutParams(layoutParams4);
        imageView.setText("移动和缩放");
        imageView.setPadding(0, a.a(this, 3.0f), 0, 0);
        imageView.setTextSize(18.0f);
        imageView.setTextColor(-1);
        relativeLayout.addView(imageView);
        this.j = new ProgressBar(this);
        layoutParams = new RelativeLayout.LayoutParams(layoutParams3);
        layoutParams.addRule(14, -1);
        layoutParams.addRule(15, -1);
        this.j.setLayoutParams(layoutParams);
        this.j.setVisibility(8);
        this.a.addView(this.j);
        return this.a;
    }

    private void b() {
        try {
            this.s = a(this.r);
            if (this.s == null) {
                throw new IOException("cannot read picture: '" + this.r + "'!");
            }
            this.e.setImageBitmap(this.s);
            this.f.setOnClickListener(this.t);
            this.g.setOnClickListener(this.u);
            this.a.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(this) {
                final /* synthetic */ ImageActivity a;

                {
                    this.a = r1;
                }

                public void onGlobalLayout() {
                    this.a.a.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    this.a.q = this.a.h.a();
                    this.a.e.a(this.a.q);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            String str = Constants.MSG_IMAGE_ERROR;
            b(str, 1);
            a(-5, null, str, e.getMessage());
            d();
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setRequestedOrientation(1);
        setContentView(a());
        this.d = new Handler();
        Bundle bundleExtra = getIntent().getBundleExtra(Constants.KEY_PARAMS);
        this.r = bundleExtra.getString(SocialConstants.PARAM_AVATAR_URI);
        this.c = bundleExtra.getString("return_activity");
        String string = bundleExtra.getString("appid");
        String string2 = bundleExtra.getString("access_token");
        long j = bundleExtra.getLong("expires_in");
        String string3 = bundleExtra.getString("openid");
        this.n = bundleExtra.getInt("exitAnim");
        this.b = new QQToken(string);
        this.b.setAccessToken(string2, ((j - System.currentTimeMillis()) / 1000) + "");
        this.b.setOpenId(string3);
        b();
        e();
        this.m = System.currentTimeMillis();
        a("10653", 0);
    }

    public void onBackPressed() {
        setResult(0);
        d();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.e.setImageBitmap(null);
        if (this.s != null && !this.s.isRecycled()) {
            this.s.recycle();
        }
    }

    private void c() {
        float width = (float) this.q.width();
        Matrix imageMatrix = this.e.getImageMatrix();
        float[] fArr = new float[9];
        imageMatrix.getValues(fArr);
        float f = fArr[2];
        float f2 = fArr[5];
        float f3 = fArr[0];
        width = 640.0f / width;
        int i = (int) ((((float) this.q.left) - f) / f3);
        int i2 = (int) ((((float) this.q.top) - f2) / f3);
        Matrix matrix = new Matrix();
        matrix.set(imageMatrix);
        matrix.postScale(width, width);
        int i3 = (int) (650.0f / f3);
        Bitmap createBitmap = Bitmap.createBitmap(this.s, i, i2, Math.min(this.s.getWidth() - i, i3), Math.min(this.s.getHeight() - i2, i3), matrix, true);
        Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap, 0, 0, 640, 640);
        createBitmap.recycle();
        a(createBitmap2);
    }

    private void a(Bitmap bitmap) {
        new QQAvatarImp(this, this.b).setAvator(bitmap, this.v);
    }

    private void a(final String str, final int i) {
        this.d.post(new Runnable(this) {
            final /* synthetic */ ImageActivity c;

            public void run() {
                this.c.b(str, i);
            }
        });
    }

    private void b(String str, int i) {
        Toast makeText = Toast.makeText(this, str, 1);
        LinearLayout linearLayout = (LinearLayout) makeText.getView();
        ((TextView) linearLayout.getChildAt(0)).setPadding(8, 0, 0, 0);
        View imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(a.a(this, 16.0f), a.a(this, 16.0f)));
        if (i == 0) {
            imageView.setImageDrawable(b("com.tencent.plus.ic_success.png"));
        } else {
            imageView.setImageDrawable(b("com.tencent.plus.ic_error.png"));
        }
        linearLayout.addView(imageView, 0);
        linearLayout.setOrientation(0);
        linearLayout.setGravity(17);
        makeText.setView(linearLayout);
        makeText.setGravity(17, 0, 0);
        makeText.show();
    }

    private void a(int i, String str, String str2, String str3) {
        Intent intent = new Intent();
        intent.putExtra(Constants.KEY_ERROR_CODE, i);
        intent.putExtra(Constants.KEY_ERROR_MSG, str2);
        intent.putExtra(Constants.KEY_ERROR_DETAIL, str3);
        intent.putExtra(Constants.KEY_RESPONSE, str);
        setResult(-1, intent);
    }

    private void d() {
        finish();
        if (this.n != 0) {
            overridePendingTransition(0, this.n);
        }
    }

    private void e() {
        this.k++;
        new UserInfo(this, this.b).getUserInfo(this.w);
    }

    private void c(String str) {
        CharSequence d = d(str);
        if (!"".equals(d)) {
            this.i.setText(d);
            this.i.setVisibility(0);
        }
    }

    private String d(String str) {
        return str.replaceAll("&gt;", SearchCriteria.GT).replaceAll("&lt;", SearchCriteria.LT).replaceAll("&quot;", "\"").replaceAll("&#39;", "'").replaceAll("&amp;", "&");
    }

    public void a(String str, long j) {
        Util.reportBernoulli(this, str, j, this.b.getAppId());
    }
}
