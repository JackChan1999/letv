package com.tencent.open;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.HttpUtils.HttpStatusException;
import com.tencent.open.utils.HttpUtils.NetworkUnavailableException;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class TaskGuide extends BaseApi {
    private static int L = 3000;
    static long b = 5000;
    private static Drawable k;
    private static Drawable l;
    private static Drawable m;
    private static int n = 75;
    private static int o = 284;
    private static int p = 75;
    private static int q = 30;
    private static int r = 29;
    private static int s = 5;
    private static int t = 74;
    private static int u = 0;
    private static int v = 6;
    private static int w = 153;
    private static int x = 30;
    private static int y = 6;
    private static int z = 3;
    private int A = 0;
    private int B = 0;
    private float C = 0.0f;
    private Interpolator D = new AccelerateInterpolator();
    private boolean E = false;
    private Context F;
    private boolean G = false;
    private boolean H = false;
    private long I;
    private int J;
    private int K;
    private Runnable M = null;
    private Runnable N = null;
    boolean a = false;
    IUiListener c;
    private LayoutParams d = null;
    private ViewGroup e = null;
    private WindowManager f;
    private Handler g = new Handler(Looper.getMainLooper());
    private h h;
    private k i = k.INIT;
    private k j = k.INIT;

    /* compiled from: ProGuard */
    private abstract class a implements IRequestListener {
        final /* synthetic */ TaskGuide a;

        protected abstract void a(Exception exception);

        private a(TaskGuide taskGuide) {
            this.a = taskGuide;
        }

        public void onIOException(IOException iOException) {
            a(iOException);
        }

        public void onMalformedURLException(MalformedURLException malformedURLException) {
            a(malformedURLException);
        }

        public void onJSONException(JSONException jSONException) {
            a(jSONException);
        }

        public void onConnectTimeoutException(ConnectTimeoutException connectTimeoutException) {
            a(connectTimeoutException);
        }

        public void onSocketTimeoutException(SocketTimeoutException socketTimeoutException) {
            a(socketTimeoutException);
        }

        public void onNetworkUnavailableException(NetworkUnavailableException networkUnavailableException) {
            a(networkUnavailableException);
        }

        public void onHttpStatusException(HttpStatusException httpStatusException) {
            a(httpStatusException);
        }

        public void onUnknowException(Exception exception) {
            a(exception);
        }
    }

    /* compiled from: ProGuard */
    private class b implements Runnable {
        final /* synthetic */ TaskGuide a;

        private b(TaskGuide taskGuide) {
            this.a = taskGuide;
        }

        public void run() {
            this.a.l();
        }
    }

    /* compiled from: ProGuard */
    class c implements Runnable {
        boolean a = false;
        float b = 0.0f;
        final /* synthetic */ TaskGuide c;

        public c(TaskGuide taskGuide, boolean z) {
            this.c = taskGuide;
            this.a = z;
        }

        public void run() {
            Object obj = 1;
            SystemClock.currentThreadTimeMillis();
            this.b = (float) (((double) this.b) + 0.1d);
            float f = this.b;
            if (f > 1.0f) {
                f = 1.0f;
            }
            Object obj2 = f >= 1.0f ? 1 : null;
            int interpolation = (int) (this.c.D.getInterpolation(f) * ((float) this.c.J));
            if (this.a) {
                this.c.d.y = this.c.K + interpolation;
            } else {
                this.c.d.y = this.c.K - interpolation;
            }
            com.tencent.open.a.f.b("TAG", "mWinParams.y = " + this.c.d.y + "deltaDistence = " + interpolation);
            if (this.c.E) {
                this.c.f.updateViewLayout(this.c.e, this.c.d);
                obj = obj2;
            }
            if (obj != null) {
                this.c.i();
            } else {
                this.c.g.postDelayed(this.c.M, 5);
            }
        }
    }

    /* compiled from: ProGuard */
    private class d extends a {
        int b = -1;
        final /* synthetic */ TaskGuide c;

        public d(TaskGuide taskGuide, int i) {
            this.c = taskGuide;
            super();
            this.b = i;
        }

        public void onComplete(JSONObject jSONObject) {
            String str = null;
            try {
                int i = jSONObject.getInt("code");
                str = jSONObject.getString("message");
                JSONObject jSONObject2;
                if (i == 0) {
                    this.c.a(this.b, k.REWARD_SUCCESS);
                    jSONObject2 = new JSONObject();
                    try {
                        jSONObject2.put("result", "金券领取成功");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    this.c.c.onComplete(jSONObject2);
                    this.c.b(this.b);
                    this.c.d(2000);
                }
                this.c.a(this.b, k.NORAML);
                this.c.a(str);
                jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("result", "金券领取失败");
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                this.c.c.onComplete(jSONObject2);
                this.c.b(this.b);
                this.c.d(2000);
            } catch (JSONException e22) {
                this.c.a(this.b, k.NORAML);
                this.c.a(str);
                e22.printStackTrace();
            }
        }

        protected void a(final Exception exception) {
            if (exception != null) {
                exception.printStackTrace();
            }
            this.c.c.onError(new UiError(101, "error ", "金券领取时出现异常"));
            if (this.c.g != null) {
                this.c.g.post(new Runnable(this) {
                    final /* synthetic */ d b;

                    public void run() {
                        k kVar = k.INIT;
                        if (this.b.b == 0) {
                            kVar = this.b.c.i;
                        } else {
                            kVar = this.b.c.j;
                        }
                        if (kVar == k.WAITTING_BACK_REWARD) {
                            this.b.c.a(this.b.b, k.NORAML);
                            this.b.c.a("领取失败 :" + exception.getClass().getName());
                        }
                        this.b.c.b(this.b.b);
                        this.b.c.d(2000);
                    }
                });
            }
        }
    }

    /* compiled from: ProGuard */
    private class e extends RelativeLayout {
        int a = 0;
        final /* synthetic */ TaskGuide b;

        public e(TaskGuide taskGuide, Context context) {
            this.b = taskGuide;
            super(context);
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            int y = (int) motionEvent.getY();
            com.tencent.open.a.f.b("XXXX", "onInterceptTouchEvent-- action = " + motionEvent.getAction() + "currentY = " + y);
            this.b.d(3000);
            switch (motionEvent.getAction()) {
                case 0:
                    this.a = y;
                    return false;
                case 1:
                    if (this.a - y > ViewConfiguration.getTouchSlop() * 2) {
                        this.b.l();
                        return true;
                    }
                    break;
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            super.onTouchEvent(motionEvent);
            int y = (int) motionEvent.getY();
            com.tencent.open.a.f.b("XXXX", " onTouchEvent-----startY = " + this.a + "currentY = " + y);
            switch (motionEvent.getAction()) {
                case 0:
                    this.a = y;
                    break;
                case 1:
                    if (this.a - y > ViewConfiguration.getTouchSlop() * 2) {
                        this.b.l();
                        break;
                    }
                    break;
            }
            return false;
        }
    }

    /* compiled from: ProGuard */
    class f implements OnClickListener {
        int a;
        final /* synthetic */ TaskGuide b;

        public f(TaskGuide taskGuide, int i) {
            this.b = taskGuide;
            this.a = i;
        }

        public void onClick(View view) {
            Button button = (Button) view;
            if (this.b.c(this.a) == k.NORAML) {
                this.b.e(this.a);
                this.b.b(this.a);
            }
            this.b.h();
        }
    }

    /* compiled from: ProGuard */
    private static class g {
        int a;
        String b;
        String c;
        long d;
        int e;

        public g(int i, String str, String str2, long j, int i2) {
            this.a = i;
            this.b = str;
            this.c = str2;
            this.d = j;
            this.e = i2;
        }
    }

    /* compiled from: ProGuard */
    private static class h {
        String a;
        String b;
        g[] c;

        private h() {
        }

        public boolean a() {
            if (TextUtils.isEmpty(this.a) || this.c == null || this.c.length <= 0) {
                return false;
            }
            return true;
        }

        static h a(JSONObject jSONObject) throws JSONException {
            if (jSONObject == null) {
                return null;
            }
            h hVar = new h();
            JSONObject jSONObject2 = jSONObject.getJSONObject("task_info");
            hVar.a = jSONObject2.getString("task_id");
            hVar.b = jSONObject2.getString("task_desc");
            JSONArray jSONArray = jSONObject2.getJSONArray("step_info");
            int length = jSONArray.length();
            if (length > 0) {
                hVar.c = new g[length];
            }
            for (int i = 0; i < length; i++) {
                jSONObject2 = jSONArray.getJSONObject(i);
                hVar.c[i] = new g(jSONObject2.getInt("step_no"), jSONObject2.getString("step_desc"), jSONObject2.getString("step_gift"), jSONObject2.getLong("end_time"), jSONObject2.getInt("status"));
            }
            return hVar;
        }
    }

    /* compiled from: ProGuard */
    private class i extends LinearLayout {
        final /* synthetic */ TaskGuide a;
        private TextView b;
        private Button c;
        private g d;

        public i(TaskGuide taskGuide, Context context, g gVar) {
            this.a = taskGuide;
            super(context);
            this.d = gVar;
            setOrientation(0);
            a();
        }

        private void a() {
            this.b = new TextView(this.a.F);
            this.b.setTextColor(Color.rgb(255, 255, 255));
            this.b.setTextSize(15.0f);
            this.b.setShadowLayer(1.0f, 1.0f, 1.0f, Color.rgb(242, 211, 199));
            this.b.setGravity(3);
            this.b.setEllipsize(TruncateAt.END);
            this.b.setIncludeFontPadding(false);
            this.b.setSingleLine(true);
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -2);
            layoutParams.weight = 1.0f;
            layoutParams.leftMargin = this.a.a(4);
            addView(this.b, layoutParams);
            this.c = new Button(this.a.F);
            this.c.setPadding(0, 0, 0, 0);
            this.c.setTextSize(16.0f);
            this.c.setTextColor(Color.rgb(255, 255, 255));
            this.c.setShadowLayer(1.0f, 1.0f, 1.0f, Color.rgb(242, 211, 199));
            this.c.setIncludeFontPadding(false);
            this.c.setOnClickListener(new f(this.a, this.d.a));
            layoutParams = new LinearLayout.LayoutParams(this.a.a(TaskGuide.p), this.a.a(TaskGuide.q));
            layoutParams.leftMargin = this.a.a(2);
            layoutParams.rightMargin = this.a.a(8);
            addView(this.c, layoutParams);
        }

        public void a(k kVar) {
            if (!TextUtils.isEmpty(this.d.b)) {
                this.b.setText(this.d.b);
            }
            switch (kVar) {
                case INIT:
                    this.c.setEnabled(false);
                    return;
                case NORAML:
                    if (this.d.e == 1) {
                        this.c.setText(this.d.c);
                        this.c.setBackgroundDrawable(null);
                        this.c.setTextColor(Color.rgb(255, 246, 0));
                        this.c.setEnabled(false);
                        return;
                    } else if (this.d.e == 2) {
                        this.c.setText("领取奖励");
                        this.c.setTextColor(Color.rgb(255, 255, 255));
                        this.c.setBackgroundDrawable(this.a.f());
                        this.c.setEnabled(true);
                        return;
                    } else {
                        return;
                    }
                case WAITTING_BACK_REWARD:
                    this.c.setText("领取中...");
                    this.c.setEnabled(false);
                    return;
                case REWARD_SUCCESS:
                    this.c.setText("已领取");
                    this.c.setBackgroundDrawable(this.a.g());
                    this.c.setEnabled(false);
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: ProGuard */
    private class j extends a {
        final /* synthetic */ TaskGuide b;

        private j(TaskGuide taskGuide) {
            this.b = taskGuide;
            super();
        }

        public void onComplete(JSONObject jSONObject) {
            try {
                this.b.h = h.a(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (this.b.h == null || !this.b.h.a()) {
                a(null);
                return;
            }
            this.b.showWindow();
            this.b.a(2, k.NORAML);
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("result", "获取成功");
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            this.b.c.onComplete(jSONObject2);
        }

        protected void a(Exception exception) {
            if (exception != null) {
                exception.printStackTrace();
            }
            if (exception == null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("result", "暂无任务");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                this.b.c.onComplete(jSONObject);
            } else {
                this.b.c.onError(new UiError(100, "error ", "获取任务失败"));
            }
            this.b.g.post(new Runnable(this) {
                final /* synthetic */ j a;

                {
                    this.a = r1;
                }

                public void run() {
                    this.a.b.a(2, k.INIT);
                }
            });
        }
    }

    /* compiled from: ProGuard */
    private enum k {
        INIT,
        WAITTING_BACK_TASKINFO,
        WAITTING_BACK_REWARD,
        NORAML,
        REWARD_SUCCESS,
        REWARD_FAIL;

        public static k[] a() {
            return (k[]) g.clone();
        }
    }

    public TaskGuide(Context context, QQToken qQToken) {
        super(qQToken);
        this.F = context;
        this.f = (WindowManager) context.getSystemService("window");
        c();
    }

    public TaskGuide(Context context, QQAuth qQAuth, QQToken qQToken) {
        super(qQAuth, qQToken);
        this.F = context;
        this.f = (WindowManager) context.getSystemService("window");
        c();
    }

    private void c() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.f.getDefaultDisplay().getMetrics(displayMetrics);
        this.A = displayMetrics.widthPixels;
        this.B = displayMetrics.heightPixels;
        this.C = displayMetrics.density;
    }

    private LayoutParams a(Context context) {
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.gravity = 49;
        this.f.getDefaultDisplay().getWidth();
        this.f.getDefaultDisplay().getHeight();
        layoutParams.width = a(o);
        layoutParams.height = a(n);
        layoutParams.windowAnimations = 16973826;
        layoutParams.format = 1;
        layoutParams.flags |= 520;
        layoutParams.type = 2;
        this.d = layoutParams;
        return layoutParams;
    }

    private void d() {
        if (this.d != null) {
            this.d.y = -this.d.height;
        }
    }

    private int a(int i) {
        return (int) (((float) i) * this.C);
    }

    private ViewGroup b(Context context) {
        ViewGroup eVar = new e(this, context);
        g[] gVarArr = this.h.c;
        View iVar;
        ViewGroup.LayoutParams layoutParams;
        if (gVarArr.length == 1) {
            iVar = new i(this, context, gVarArr[0]);
            iVar.setId(1);
            layoutParams = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams.addRule(15);
            eVar.addView(iVar, layoutParams);
        } else {
            iVar = new i(this, context, gVarArr[0]);
            iVar.setId(1);
            View iVar2 = new i(this, context, gVarArr[1]);
            iVar2.setId(2);
            layoutParams = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams.addRule(14);
            layoutParams.setMargins(0, a(6), 0, 0);
            ViewGroup.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams2.addRule(14);
            layoutParams2.setMargins(0, a(4), 0, 0);
            layoutParams2.addRule(3, 1);
            layoutParams2.addRule(5, 1);
            eVar.addView(iVar, layoutParams);
            eVar.addView(iVar2, layoutParams2);
        }
        eVar.setBackgroundDrawable(e());
        return eVar;
    }

    private Drawable e() {
        if (k == null) {
            k = a("background.9.png", this.F);
        }
        return k;
    }

    private Drawable f() {
        if (l == null) {
            l = a("button_green.9.png", this.F);
        }
        return l;
    }

    private Drawable g() {
        if (m == null) {
            m = a("button_red.9.png", this.F);
        }
        return m;
    }

    private void b(final int i) {
        if (this.g != null) {
            this.g.post(new Runnable(this) {
                final /* synthetic */ TaskGuide b;

                public void run() {
                    if (!this.b.E) {
                        return;
                    }
                    if (i == 0) {
                        ((i) this.b.e.findViewById(1)).a(this.b.i);
                    } else if (i == 1) {
                        ((i) this.b.e.findViewById(2)).a(this.b.j);
                    } else if (i == 2) {
                        ((i) this.b.e.findViewById(1)).a(this.b.i);
                        if (this.b.e.getChildCount() > 1) {
                            ((i) this.b.e.findViewById(2)).a(this.b.j);
                        }
                    }
                }
            });
        }
    }

    private void a(int i, k kVar) {
        if (i == 0) {
            this.i = kVar;
        } else if (i == 1) {
            this.j = kVar;
        } else {
            this.i = kVar;
            this.j = kVar;
        }
    }

    private k c(int i) {
        if (i == 0) {
            return this.i;
        }
        if (i == 1) {
            return this.j;
        }
        return k.INIT;
    }

    @SuppressLint({"ResourceAsColor"})
    public void showWindow() {
        new Handler(Looper.getMainLooper()).post(new Runnable(this) {
            final /* synthetic */ TaskGuide a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.e = this.a.b(this.a.F);
                this.a.d = this.a.a(this.a.F);
                this.a.d();
                WindowManager windowManager = (WindowManager) this.a.F.getSystemService("window");
                if (!((Activity) this.a.F).isFinishing()) {
                    if (!this.a.E) {
                        windowManager.addView(this.a.e, this.a.d);
                    }
                    this.a.E = true;
                    this.a.b(2);
                    this.a.k();
                }
            }
        });
        com.tencent.connect.a.a.a(this.F, this.mToken, "TaskApi", "showTaskWindow");
    }

    private void d(int i) {
        h();
        this.N = new b();
        this.g.postDelayed(this.N, (long) i);
    }

    private void h() {
        this.g.removeCallbacks(this.N);
        if (!j()) {
            this.g.removeCallbacks(this.M);
        }
    }

    private void i() {
        if (this.G) {
            d(3000);
        } else {
            removeWindow();
        }
        if (this.G) {
            LayoutParams layoutParams = this.d;
            layoutParams.flags &= -17;
            this.f.updateViewLayout(this.e, this.d);
        }
        this.G = false;
        this.H = false;
    }

    private void a(boolean z) {
        this.I = SystemClock.currentThreadTimeMillis();
        if (z) {
            this.G = true;
        } else {
            this.H = true;
        }
        this.J = this.d.height;
        this.K = this.d.y;
        LayoutParams layoutParams = this.d;
        layoutParams.flags |= 16;
        this.f.updateViewLayout(this.e, this.d);
    }

    private boolean j() {
        return this.G || this.H;
    }

    private void k() {
        if (!j()) {
            this.g.removeCallbacks(this.N);
            this.g.removeCallbacks(this.M);
            this.M = new c(this, true);
            a(true);
            this.g.post(this.M);
        }
    }

    private void l() {
        if (!j()) {
            this.g.removeCallbacks(this.N);
            this.g.removeCallbacks(this.M);
            this.M = new c(this, false);
            a(false);
            this.g.post(this.M);
        }
    }

    public void removeWindow() {
        if (this.E) {
            this.f.removeView(this.e);
            this.E = false;
        }
    }

    private Drawable a(String str, Context context) {
        Drawable createFromStream;
        IOException e;
        try {
            InputStream open = context.getApplicationContext().getAssets().open(str);
            if (open == null) {
                return null;
            }
            if (str.endsWith(".9.png")) {
                Bitmap decodeStream = BitmapFactory.decodeStream(open);
                if (decodeStream == null) {
                    return null;
                }
                byte[] ninePatchChunk = decodeStream.getNinePatchChunk();
                NinePatch.isNinePatchChunk(ninePatchChunk);
                return new NinePatchDrawable(decodeStream, ninePatchChunk, new Rect(), null);
            }
            createFromStream = Drawable.createFromStream(open, str);
            try {
                open.close();
                return createFromStream;
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
    }

    private void a(final String str) {
        this.g.post(new Runnable(this) {
            final /* synthetic */ TaskGuide b;

            public void run() {
                Toast.makeText(this.b.F, "失败：" + str, 1).show();
            }
        });
    }

    public void showTaskGuideWindow(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.F = activity;
        this.c = iUiListener;
        if (this.i != k.WAITTING_BACK_TASKINFO && this.j != k.WAITTING_BACK_TASKINFO && !this.E) {
            Bundle bundle2;
            this.h = null;
            if (bundle != null) {
                bundle2 = new Bundle(bundle);
                bundle2.putAll(composeCGIParams());
            } else {
                bundle2 = composeCGIParams();
            }
            IRequestListener jVar = new j();
            bundle2.putString("action", "task_list");
            bundle2.putString("auth", "mobile");
            bundle2.putString("appid", this.mToken.getAppId());
            HttpUtils.requestAsync(this.mToken, this.F, "http://appact.qzone.qq.com/appstore_activity_task_pcpush_sdk", bundle2, "GET", jVar);
            a(2, k.WAITTING_BACK_TASKINFO);
        }
    }

    private void e(int i) {
        Bundle composeCGIParams = composeCGIParams();
        composeCGIParams.putString("action", "get_gift");
        composeCGIParams.putString("task_id", this.h.a);
        composeCGIParams.putString("step_no", new Integer(i).toString());
        composeCGIParams.putString("appid", this.mToken.getAppId());
        HttpUtils.requestAsync(this.mToken, this.F, "http://appact.qzone.qq.com/appstore_activity_task_pcpush_sdk", composeCGIParams, "GET", new d(this, i));
        a(i, k.WAITTING_BACK_REWARD);
        com.tencent.connect.a.a.a(this.F, this.mToken, "TaskApi", "getGift");
    }
}
