package com.letv.plugin.pluginloader.loader;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;

public class JarResources {
    static final HashMap<String, JarResources> loaders = new HashMap();
    AssetManager asset;
    Resources res;

    JarResources(Resources res, AssetManager asset) {
        this.res = res;
        this.asset = asset;
    }

    public Drawable getDrawable(int id) {
        return this.res.getDrawable(id);
    }

    public CharSequence getText(int id) {
        return this.res.getText(id);
    }

    public String getString(int id) {
        return this.res.getString(id);
    }

    public String[] getStringArray(int id) {
        return this.res.getStringArray(id);
    }

    public int getColor(int id) {
        return this.res.getColor(id);
    }

    public ColorStateList getColorStateList(int id) {
        return this.res.getColorStateList(id);
    }

    public float getDimension(int id) {
        return this.res.getDimension(id);
    }

    public int getDimensionPixelSize(int id) {
        return this.res.getDimensionPixelSize(id);
    }

    public int getDimensionPixelOffset(int id) {
        return this.res.getDimensionPixelOffset(id);
    }

    public InputStream openRawResource(int id) {
        return this.res.openRawResource(id);
    }

    public byte[] getRawResource(int id) {
        InputStream ins = openRawResource(id);
        try {
            int n = ins.available();
            if (n <= 0) {
                n = 4096;
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream(n);
            byte[] buf = new byte[4096];
            while (true) {
                int l = ins.read(buf);
                if (l != -1) {
                    bos.write(buf, 0, l);
                } else {
                    ins.close();
                    return bos.toByteArray();
                }
            }
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public Resources getResources() {
        return this.res;
    }

    public AssetManager getAssets() {
        return this.asset;
    }

    public View inflate(Context context, int id, ViewGroup parent, boolean attachToRoot) {
        if (context instanceof JarResOverrideInterface) {
            JarResOverrideInterface mri = (JarResOverrideInterface) context;
            JarResources old = mri.getOverrideResources();
            mri.setOverrideResources(this);
            try {
                View v = LayoutInflater.from(context).inflate(id, parent, attachToRoot);
                return v;
            } finally {
                mri.setOverrideResources(old);
            }
        } else {
            throw new RuntimeException("unable to inflate without MainActivity context");
        }
    }

    public static JarResources getResource(Class<?> clazz, Context context) {
        Log.i("aa", "clazz.getClassLoader() is " + clazz.getClassLoader());
        if (clazz.getClassLoader() instanceof JarClassLoader) {
            Log.i("aa", "getResource is 1");
            return getResourceByCl((JarClassLoader) clazz.getClassLoader(), context);
        }
        throw new RuntimeException(clazz + " is not loaded from dynamic loader");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.letv.plugin.pluginloader.loader.JarResources getResourceByCl(com.letv.plugin.pluginloader.loader.JarClassLoader r15, android.content.Context r16) {
        /*
        r10 = "aa";
        r11 = "getResource is 2";
        android.util.Log.i(r10, r11);
        r4 = r15.packagename;
        r10 = "aa";
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "pgname is ";
        r11 = r11.append(r12);
        r11 = r11.append(r4);
        r11 = r11.toString();
        android.util.Log.i(r10, r11);
        r10 = loaders;
        r6 = r10.get(r4);
        r6 = (com.letv.plugin.pluginloader.loader.JarResources) r6;
        if (r6 == 0) goto L_0x002e;
    L_0x002c:
        r7 = r6;
    L_0x002d:
        return r7;
    L_0x002e:
        r3 = r15.jarpath;
        r10 = "aa";
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "path is ";
        r11 = r11.append(r12);
        r11 = r11.append(r3);
        r11 = r11.toString();
        android.util.Log.i(r10, r11);
        r10 = android.content.res.AssetManager.class;
        r0 = r10.newInstance();	 Catch:{ Exception -> 0x00fa }
        r0 = (android.content.res.AssetManager) r0;	 Catch:{ Exception -> 0x00fa }
        r10 = r0.getClass();	 Catch:{ Exception -> 0x00fa }
        r11 = "addAssetPath";
        r12 = 1;
        r12 = new java.lang.Class[r12];	 Catch:{ Exception -> 0x00fa }
        r13 = 0;
        r14 = java.lang.String.class;
        r12[r13] = r14;	 Catch:{ Exception -> 0x00fa }
        r10 = r10.getMethod(r11, r12);	 Catch:{ Exception -> 0x00fa }
        r11 = 1;
        r11 = new java.lang.Object[r11];	 Catch:{ Exception -> 0x00fa }
        r12 = 0;
        r11[r12] = r3;	 Catch:{ Exception -> 0x00fa }
        r10.invoke(r0, r11);	 Catch:{ Exception -> 0x00fa }
        r10 = "aa";
        r11 = "path is 1";
        android.util.Log.i(r10, r11);	 Catch:{ Exception -> 0x00fa }
        r10 = com.letv.plugin.pluginloader.application.JarApplication.getInstance();	 Catch:{ Exception -> 0x00fa }
        if (r10 != 0) goto L_0x0081;
    L_0x007a:
        r10 = r16.getApplicationContext();	 Catch:{ Exception -> 0x00fa }
        com.letv.plugin.pluginloader.application.JarApplication.setInstance(r10);	 Catch:{ Exception -> 0x00fa }
    L_0x0081:
        r10 = com.letv.plugin.pluginloader.application.JarApplication.getInstance();	 Catch:{ Exception -> 0x00fa }
        r8 = r10.getResources();	 Catch:{ Exception -> 0x00fa }
        r10 = "aa";
        r11 = "path is 2";
        android.util.Log.i(r10, r11);	 Catch:{ Exception -> 0x00fa }
        r5 = new android.content.res.Resources;	 Catch:{ Exception -> 0x00fa }
        r10 = r8.getDisplayMetrics();	 Catch:{ Exception -> 0x00fa }
        r11 = r8.getConfiguration();	 Catch:{ Exception -> 0x00fa }
        r5.<init>(r0, r10, r11);	 Catch:{ Exception -> 0x00fa }
        r10 = "aa";
        r11 = "path is 3";
        android.util.Log.i(r10, r11);	 Catch:{ Exception -> 0x00fa }
        r10 = android.text.TextUtils.isEmpty(r4);	 Catch:{ Exception -> 0x00fa }
        if (r10 == 0) goto L_0x0102;
    L_0x00ac:
        r10 = "AndroidManifest.xml";
        r9 = r0.openXmlResourceParser(r10);	 Catch:{ Exception -> 0x00fa }
        r2 = r9.getEventType();	 Catch:{ Exception -> 0x00fa }
    L_0x00b6:
        r10 = 1;
        if (r2 == r10) goto L_0x00d5;
    L_0x00b9:
        switch(r2) {
            case 2: goto L_0x00c1;
            default: goto L_0x00bc;
        };	 Catch:{ Exception -> 0x00fa }
    L_0x00bc:
        r2 = r9.nextToken();	 Catch:{ Exception -> 0x00fa }
        goto L_0x00b6;
    L_0x00c1:
        r10 = "manifest";
        r11 = r9.getName();	 Catch:{ Exception -> 0x00fa }
        r10 = r10.equals(r11);	 Catch:{ Exception -> 0x00fa }
        if (r10 == 0) goto L_0x00bc;
    L_0x00cd:
        r10 = 0;
        r11 = "package";
        r4 = r9.getAttributeValue(r10, r11);	 Catch:{ Exception -> 0x00fa }
    L_0x00d5:
        r9.close();	 Catch:{ Exception -> 0x00fa }
        if (r4 != 0) goto L_0x0102;
    L_0x00da:
        r10 = new java.lang.RuntimeException;	 Catch:{ Exception -> 0x00fa }
        r11 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00fa }
        r11.<init>();	 Catch:{ Exception -> 0x00fa }
        r12 = "package not found in AndroidManifest.xml [";
        r11 = r11.append(r12);	 Catch:{ Exception -> 0x00fa }
        r11 = r11.append(r3);	 Catch:{ Exception -> 0x00fa }
        r12 = "]";
        r11 = r11.append(r12);	 Catch:{ Exception -> 0x00fa }
        r11 = r11.toString();	 Catch:{ Exception -> 0x00fa }
        r10.<init>(r11);	 Catch:{ Exception -> 0x00fa }
        throw r10;	 Catch:{ Exception -> 0x00fa }
    L_0x00fa:
        r1 = move-exception;
        r10 = r1 instanceof java.lang.RuntimeException;
        if (r10 == 0) goto L_0x010f;
    L_0x00ff:
        r1 = (java.lang.RuntimeException) r1;
        throw r1;
    L_0x0102:
        r6 = new com.letv.plugin.pluginloader.loader.JarResources;	 Catch:{ Exception -> 0x00fa }
        r6.<init>(r5, r0);	 Catch:{ Exception -> 0x00fa }
        r10 = loaders;
        r10.put(r4, r6);
        r7 = r6;
        goto L_0x002d;
    L_0x010f:
        r10 = new java.lang.RuntimeException;
        r10.<init>(r1);
        throw r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.plugin.pluginloader.loader.JarResources.getResourceByCl(com.letv.plugin.pluginloader.loader.JarClassLoader, android.content.Context):com.letv.plugin.pluginloader.loader.JarResources");
    }
}
