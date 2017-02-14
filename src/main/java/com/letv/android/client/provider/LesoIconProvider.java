package com.letv.android.client.provider;

import android.content.ContentProvider;
import android.content.Context;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LesoIconProvider extends ContentProvider {
    private static final int ADS = 100;
    public static String AUTHORITY = null;
    private static final String LESO = "leso";
    public static final String LESO_ICON = "leso_icon";
    public static final String LESO_ID = "id";
    private static final String TABLE_NAME = "leso_icon";
    public static final Uri URI = Uri.parse("content://" + AUTHORITY + "/" + "leso_icon");
    private static final UriMatcher URI_MATCHER = new UriMatcher(-1);
    DBHelper helper;

    class DBHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "leso_icon.db";
        private static final int VERSION = 1;
        final /* synthetic */ LesoIconProvider this$0;

        public DBHelper(LesoIconProvider this$0, Context context) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
            super(context, DB_NAME, null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS leso_icon (id integer primary key autoincrement, leso_icon text)");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS leso_icon");
            onCreate(db);
        }
    }

    public LesoIconProvider() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.helper = null;
    }

    static {
        try {
            Properties properties = new Properties();
            InputStream in = LesoIconProvider.class.getClassLoader().getResourceAsStream("letv.properties");
            if (in != null) {
                properties.load(in);
                AUTHORITY = properties.getProperty("lesoicoContentProvider.authorities");
            }
            if (AUTHORITY == null) {
                AUTHORITY = "com.letv.android.client.db.lesoicon";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        URI_MATCHER.addURI(AUTHORITY, "leso_icon", 100);
    }

    public boolean onCreate() {
        this.helper = new DBHelper(this, getContext());
        return false;
    }

    public String getType(Uri uri) {
        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.net.Uri insert(android.net.Uri r10, android.content.ContentValues r11) {
        /*
        r9 = this;
        r3 = 0;
        r6 = URI_MATCHER;	 Catch:{ Exception -> 0x002d }
        r2 = r6.match(r10);	 Catch:{ Exception -> 0x002d }
        r6 = r9.helper;	 Catch:{ Exception -> 0x002d }
        r0 = r6.getWritableDatabase();	 Catch:{ Exception -> 0x002d }
        switch(r2) {
            case 100: goto L_0x0032;
            default: goto L_0x0010;
        };	 Catch:{ Exception -> 0x002d }
    L_0x0010:
        r6 = new java.lang.UnsupportedOperationException;	 Catch:{ Exception -> 0x002d }
        r7 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x002d }
        r7.<init>();	 Catch:{ Exception -> 0x002d }
        r8 = "Unknown or unsupported URL: ";
        r7 = r7.append(r8);	 Catch:{ Exception -> 0x002d }
        r8 = r10.toString();	 Catch:{ Exception -> 0x002d }
        r7 = r7.append(r8);	 Catch:{ Exception -> 0x002d }
        r7 = r7.toString();	 Catch:{ Exception -> 0x002d }
        r6.<init>(r7);	 Catch:{ Exception -> 0x002d }
        throw r6;	 Catch:{ Exception -> 0x002d }
    L_0x002d:
        r1 = move-exception;
        r1.printStackTrace();
    L_0x0031:
        return r3;
    L_0x0032:
        r6 = "leso_icon";
        r7 = "leso";
        r4 = r0.insert(r6, r7, r11);	 Catch:{ Exception -> 0x002d }
        r6 = 0;
        r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r6 <= 0) goto L_0x0046;
    L_0x0040:
        r6 = URI;	 Catch:{ Exception -> 0x002d }
        r3 = android.content.ContentUris.withAppendedId(r6, r4);	 Catch:{ Exception -> 0x002d }
    L_0x0046:
        if (r3 == 0) goto L_0x0031;
    L_0x0048:
        r6 = r9.getContext();	 Catch:{ Exception -> 0x002d }
        r6 = r6.getContentResolver();	 Catch:{ Exception -> 0x002d }
        r7 = 0;
        r6.notifyChange(r10, r7);	 Catch:{ Exception -> 0x002d }
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.android.client.provider.LesoIconProvider.insert(android.net.Uri, android.content.ContentValues):android.net.Uri");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int delete(android.net.Uri r8, java.lang.String r9, java.lang.String[] r10) {
        /*
        r7 = this;
        r0 = 0;
        r4 = URI_MATCHER;	 Catch:{ Exception -> 0x002d }
        r3 = r4.match(r8);	 Catch:{ Exception -> 0x002d }
        r4 = r7.helper;	 Catch:{ Exception -> 0x002d }
        r1 = r4.getWritableDatabase();	 Catch:{ Exception -> 0x002d }
        switch(r3) {
            case 100: goto L_0x0032;
            default: goto L_0x0010;
        };	 Catch:{ Exception -> 0x002d }
    L_0x0010:
        r4 = new java.lang.UnsupportedOperationException;	 Catch:{ Exception -> 0x002d }
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x002d }
        r5.<init>();	 Catch:{ Exception -> 0x002d }
        r6 = "Unknown or unsupported URL: ";
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x002d }
        r6 = r8.toString();	 Catch:{ Exception -> 0x002d }
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x002d }
        r5 = r5.toString();	 Catch:{ Exception -> 0x002d }
        r4.<init>(r5);	 Catch:{ Exception -> 0x002d }
        throw r4;	 Catch:{ Exception -> 0x002d }
    L_0x002d:
        r2 = move-exception;
        r2.printStackTrace();
    L_0x0031:
        return r0;
    L_0x0032:
        r4 = "leso_icon";
        r0 = r1.delete(r4, r9, r10);	 Catch:{ Exception -> 0x002d }
        r4 = r7.getContext();	 Catch:{ Exception -> 0x002d }
        r4 = r4.getContentResolver();	 Catch:{ Exception -> 0x002d }
        r5 = 0;
        r4.notifyChange(r8, r5);	 Catch:{ Exception -> 0x002d }
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.android.client.provider.LesoIconProvider.delete(android.net.Uri, java.lang.String, java.lang.String[]):int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int update(android.net.Uri r8, android.content.ContentValues r9, java.lang.String r10, java.lang.String[] r11) {
        /*
        r7 = this;
        r0 = 0;
        r4 = URI_MATCHER;	 Catch:{ Exception -> 0x002d }
        r3 = r4.match(r8);	 Catch:{ Exception -> 0x002d }
        r4 = r7.helper;	 Catch:{ Exception -> 0x002d }
        r1 = r4.getWritableDatabase();	 Catch:{ Exception -> 0x002d }
        switch(r3) {
            case 100: goto L_0x0032;
            default: goto L_0x0010;
        };	 Catch:{ Exception -> 0x002d }
    L_0x0010:
        r4 = new java.lang.UnsupportedOperationException;	 Catch:{ Exception -> 0x002d }
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x002d }
        r5.<init>();	 Catch:{ Exception -> 0x002d }
        r6 = "Unknown or unsupported URL: ";
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x002d }
        r6 = r8.toString();	 Catch:{ Exception -> 0x002d }
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x002d }
        r5 = r5.toString();	 Catch:{ Exception -> 0x002d }
        r4.<init>(r5);	 Catch:{ Exception -> 0x002d }
        throw r4;	 Catch:{ Exception -> 0x002d }
    L_0x002d:
        r2 = move-exception;
        r2.printStackTrace();
    L_0x0031:
        return r0;
    L_0x0032:
        r4 = "leso_icon";
        r0 = r1.update(r4, r9, r10, r11);	 Catch:{ Exception -> 0x002d }
        r4 = r7.getContext();	 Catch:{ Exception -> 0x002d }
        r4 = r4.getContentResolver();	 Catch:{ Exception -> 0x002d }
        r5 = 0;
        r4.notifyChange(r8, r5);	 Catch:{ Exception -> 0x002d }
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.android.client.provider.LesoIconProvider.update(android.net.Uri, android.content.ContentValues, java.lang.String, java.lang.String[]):int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.database.Cursor query(android.net.Uri r12, java.lang.String[] r13, java.lang.String r14, java.lang.String[] r15, java.lang.String r16) {
        /*
        r11 = this;
        r8 = 0;
        r1 = URI_MATCHER;	 Catch:{ Exception -> 0x002d }
        r10 = r1.match(r12);	 Catch:{ Exception -> 0x002d }
        r1 = r11.helper;	 Catch:{ Exception -> 0x002d }
        r0 = r1.getWritableDatabase();	 Catch:{ Exception -> 0x002d }
        switch(r10) {
            case 100: goto L_0x0032;
            default: goto L_0x0010;
        };	 Catch:{ Exception -> 0x002d }
    L_0x0010:
        r1 = new java.lang.UnsupportedOperationException;	 Catch:{ Exception -> 0x002d }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x002d }
        r2.<init>();	 Catch:{ Exception -> 0x002d }
        r3 = "Unknown or unsupported URL: ";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x002d }
        r3 = r12.toString();	 Catch:{ Exception -> 0x002d }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x002d }
        r2 = r2.toString();	 Catch:{ Exception -> 0x002d }
        r1.<init>(r2);	 Catch:{ Exception -> 0x002d }
        throw r1;	 Catch:{ Exception -> 0x002d }
    L_0x002d:
        r9 = move-exception;
        r9.printStackTrace();
    L_0x0031:
        return r8;
    L_0x0032:
        r1 = "leso_icon";
        r2 = 0;
        r5 = 0;
        r6 = 0;
        r3 = r14;
        r4 = r15;
        r7 = r16;
        r8 = r0.query(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Exception -> 0x002d }
        r1 = r11.getContext();	 Catch:{ Exception -> 0x002d }
        r1 = r1.getContentResolver();	 Catch:{ Exception -> 0x002d }
        r8.setNotificationUri(r1, r12);	 Catch:{ Exception -> 0x002d }
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.android.client.provider.LesoIconProvider.query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String):android.database.Cursor");
    }
}
