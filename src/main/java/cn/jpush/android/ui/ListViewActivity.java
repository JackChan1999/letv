package cn.jpush.android.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import cn.jpush.android.data.c;
import cn.jpush.android.util.z;
import java.util.List;

public class ListViewActivity extends Activity {
    private static final String z;
    private c a;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "\u0001Vg]";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x0027;
    L_0x000b:
        r3 = r0;
        r4 = r2;
        r7 = r1;
        r1 = r0;
        r0 = r7;
    L_0x0010:
        r6 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x0035;
            case 1: goto L_0x0038;
            case 2: goto L_0x003b;
            case 3: goto L_0x003d;
            default: goto L_0x0017;
        };
    L_0x0017:
        r5 = 122; // 0x7a float:1.71E-43 double:6.03E-322;
    L_0x0019:
        r5 = r5 ^ r6;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x0025;
    L_0x0021:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x0010;
    L_0x0025:
        r1 = r0;
        r0 = r3;
    L_0x0027:
        if (r1 > r2) goto L_0x000b;
    L_0x0029:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        z = r0;
        return;
    L_0x0035:
        r5 = 99;
        goto L_0x0019;
    L_0x0038:
        r5 = 57;
        goto L_0x0019;
    L_0x003b:
        r5 = 3;
        goto L_0x0019;
    L_0x003d:
        r5 = 36;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.ui.ListViewActivity.<clinit>():void");
    }

    protected void onCreate(Bundle bundle) {
        z.c();
        super.onCreate(bundle);
        if (getIntent() != null) {
            this.a = (c) getIntent().getSerializableExtra(z);
            c cVar = this.a;
            z.c();
            View gridView = new GridView(getApplicationContext());
            gridView.setNumColumns(2);
            List list = cVar.u;
            gridView.setAdapter(new e(this, Integer.MIN_VALUE, list));
            gridView.setOnItemClickListener(new d(this, list));
            setContentView(gridView);
            return;
        }
        z.d();
    }
}
