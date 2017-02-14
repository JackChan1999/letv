package cn.com.iresearch.vvtracker.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import cn.com.iresearch.vvtracker.db.annotation.sqlite.Id;
import cn.com.iresearch.vvtracker.db.annotation.sqlite.Table;
import cn.com.iresearch.vvtracker.db.d.b;
import cn.com.iresearch.vvtracker.db.d.c;
import cn.com.iresearch.vvtracker.db.d.d;
import cn.com.iresearch.vvtracker.db.d.e;
import cn.com.iresearch.vvtracker.db.d.f;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.VType;
import com.letv.pp.utils.NetworkUtils;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@SuppressLint({"NewApi"})
public class a {
    public static <T> T getEntity(Cursor cursor, Class<T> cls) {
        if (cursor != null) {
            try {
                f a = f.a(cls);
                int columnCount = cursor.getColumnCount();
                if (columnCount > 0) {
                    T newInstance = cls.newInstance();
                    for (int i = 0; i < columnCount; i++) {
                        String columnName = cursor.getColumnName(i);
                        e eVar = (e) a.a.get(columnName);
                        if (eVar != null) {
                            eVar.a(newInstance, cursor.getString(i));
                        } else if (a.b().c().equals(columnName)) {
                            a.b().a(newInstance, cursor.getString(i));
                        }
                    }
                    return newInstance;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getTableName(Class<?> cls) {
        Table table = (Table) cls.getAnnotation(Table.class);
        if (table == null || table.name().trim().length() == 0) {
            return cls.getName().replace('.', '_');
        }
        return table.name();
    }

    public static cn.com.iresearch.vvtracker.db.c.a buildInsertSql(Object obj) {
        List<b> linkedList = new LinkedList();
        f a = f.a(obj.getClass());
        Object a2 = a.b().a(obj);
        if (!((a2 instanceof Integer) || !(a2 instanceof String) || a2 == null)) {
            linkedList.add(new b(a.b().c(), a2));
        }
        for (e eVar : a.a.values()) {
            String c = eVar.c();
            Object a3 = eVar.a(obj);
            if (a3 != null) {
                a2 = new b(c, a3);
            } else if (eVar.d() == null || eVar.d().trim().length() == 0) {
                a2 = null;
            } else {
                b bVar = new b(c, eVar.d());
            }
            if (a2 != null) {
                linkedList.add(a2);
            }
        }
        for (c cVar : a.b.values()) {
            String c2 = cVar.c();
            a2 = cVar.a(obj);
            if (a2 != null) {
                Object a4 = f.a(a2.getClass()).b().a(a2);
                if (!(c2 == null || a4 == null)) {
                    a2 = new b(c2, a4);
                    if (a2 != null) {
                        linkedList.add(a2);
                    }
                }
            }
            a2 = null;
            if (a2 != null) {
                linkedList.add(a2);
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (linkedList.size() <= 0) {
            return null;
        }
        cn.com.iresearch.vvtracker.db.c.a aVar = new cn.com.iresearch.vvtracker.db.c.a();
        stringBuffer.append("INSERT INTO ");
        stringBuffer.append(f.a(obj.getClass()).a());
        stringBuffer.append(" (");
        for (b bVar2 : linkedList) {
            stringBuffer.append(bVar2.a()).append(",");
            aVar.a(bVar2.b());
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.append(") VALUES ( ");
        int size = linkedList.size();
        for (int i = 0; i < size; i++) {
            stringBuffer.append("?,");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.append(")");
        aVar.a(stringBuffer.toString());
        return aVar;
    }

    public static String getUrl(Context context, VideoPlayInfo videoPlayInfo) {
        if (videoPlayInfo == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("http://irs01.com/irt");
        stringBuffer.append("?");
        stringBuffer.append("_iwt_id=" + getUid2(context) + "&");
        stringBuffer.append("_iwt_UA=" + context.getSharedPreferences("VV_Tracker", 0).getString("vv_uaid", "") + "&");
        stringBuffer.append("_t=i&");
        if ("play".equals(videoPlayInfo.getAction())) {
            stringBuffer.append("_iwt_p1=A-0-0&");
        } else if ("end".equals(videoPlayInfo.getAction())) {
            stringBuffer.append("_iwt_p1=B-0-0&");
        }
        stringBuffer.append("_iwt_p2=" + videoPlayInfo.getVideoID() + "&");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(videoPlayInfo.getVideoLength());
        stringBuilder.append(NetworkUtils.DELIMITER_LINE);
        stringBuilder.append(videoPlayInfo.getPlayTime());
        stringBuilder.append(NetworkUtils.DELIMITER_LINE);
        stringBuilder.append(videoPlayInfo.getPauseCount());
        stringBuilder.append(NetworkUtils.DELIMITER_LINE);
        stringBuilder.append(videoPlayInfo.getHeartTime());
        stringBuffer.append("_iwt_p3=" + stringBuilder.toString() + "&");
        stringBuffer.append("_iwt_p4=" + videoPlayInfo.getCustomVal() + "&");
        stringBuffer.append("_iwt_p5=android&");
        stringBuffer.append("_iwt_p6=" + getOsVersion$1afe14f3() + "&");
        stringBuffer.append("_iwt_p7=" + Build.MODEL.replace(" ", NetworkUtils.DELIMITER_LINE));
        return stringBuffer.toString();
    }

    public static List<b> getSaveKeyValueListByEntity(Object obj) {
        List<b> linkedList = new LinkedList();
        f a = f.a(obj.getClass());
        Object a2 = a.b().a(obj);
        if (!((a2 instanceof Integer) || !(a2 instanceof String) || a2 == null)) {
            linkedList.add(new b(a.b().c(), a2));
        }
        for (e eVar : a.a.values()) {
            String c = eVar.c();
            Object a3 = eVar.a(obj);
            if (a3 != null) {
                a2 = new b(c, a3);
            } else if (eVar.d() == null || eVar.d().trim().length() == 0) {
                a2 = null;
            } else {
                b bVar = new b(c, eVar.d());
            }
            if (a2 != null) {
                linkedList.add(a2);
            }
        }
        for (c cVar : a.b.values()) {
            String c2 = cVar.c();
            a2 = cVar.a(obj);
            if (a2 != null) {
                Object a4 = f.a(a2.getClass()).b().a(a2);
                if (!(c2 == null || a4 == null)) {
                    a2 = new b(c2, a4);
                    if (a2 != null) {
                        linkedList.add(a2);
                    }
                }
            }
            a2 = null;
            if (a2 != null) {
                linkedList.add(a2);
            }
        }
        return linkedList;
    }

    public static Field getPrimaryKeyField(Class<?> cls) {
        Field[] declaredFields = cls.getDeclaredFields();
        if (declaredFields != null) {
            for (Field field : declaredFields) {
                if (field.getAnnotation(Id.class) != null) {
                    break;
                }
            }
            Field field2 = null;
            if (field2 == null) {
                for (Field field3 : declaredFields) {
                    if ("_id".equals(field3.getName())) {
                        break;
                    }
                }
            }
            Field field32 = field2;
            if (field32 != null) {
                return field32;
            }
            for (Field field4 : declaredFields) {
                if ("id".equals(field4.getName())) {
                    return field4;
                }
            }
            return field32;
        }
        throw new RuntimeException("this model[" + cls + "] has no field");
    }

    private static String getDeleteSqlBytableName(String str) {
        return "DELETE FROM " + str;
    }

    public static cn.com.iresearch.vvtracker.db.c.a buildDeleteSql(Object obj) {
        f a = f.a(obj.getClass());
        cn.com.iresearch.vvtracker.db.d.a b = a.b();
        Object a2 = b.a(obj);
        if (a2 == null) {
            throw new cn.com.iresearch.vvtracker.db.a.b("getDeleteSQL:" + obj.getClass() + " id value is null");
        }
        StringBuffer stringBuffer = new StringBuffer("DELETE FROM " + a.a());
        stringBuffer.append(" WHERE ").append(b.c()).append("=?");
        cn.com.iresearch.vvtracker.db.c.a aVar = new cn.com.iresearch.vvtracker.db.c.a();
        aVar.a(stringBuffer.toString());
        aVar.a(a2);
        return aVar;
    }

    public static String getPrimaryKeyFieldName(Class<?> cls) {
        Field primaryKeyField = getPrimaryKeyField(cls);
        return primaryKeyField == null ? null : primaryKeyField.getName();
    }

    public static List<e> getPropertyList(Class<?> cls) {
        List<e> arrayList = new ArrayList();
        try {
            Field[] declaredFields = cls.getDeclaredFields();
            String primaryKeyFieldName = getPrimaryKeyFieldName(cls);
            for (Field field : declaredFields) {
                if (!(cn.com.iresearch.vvtracker.db.b.a.c(field) || !cn.com.iresearch.vvtracker.db.b.a.f(field) || field.getName().equals(primaryKeyFieldName))) {
                    e eVar = new e();
                    eVar.a(cn.com.iresearch.vvtracker.db.b.a.a(field));
                    field.getName();
                    e.b();
                    eVar.a(field.getType());
                    eVar.b(cn.com.iresearch.vvtracker.db.b.a.b(field));
                    eVar.b(cn.com.iresearch.vvtracker.db.b.a.b((Class) cls, field));
                    eVar.a(cn.com.iresearch.vvtracker.db.b.a.a((Class) cls, field));
                    eVar.a(field);
                    arrayList.add(eVar);
                }
            }
            return arrayList;
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void vv_Logd$16da05f7() {
    }

    public static void vv_Loge$16da05f7() {
    }

    private static String getOsVersion$1afe14f3() {
        String str = "UNKNOW";
        try {
            return VERSION.RELEASE;
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public static List<c> getManyToOneList(Class<?> cls) {
        List<c> arrayList = new ArrayList();
        try {
            for (Field field : cls.getDeclaredFields()) {
                if (!cn.com.iresearch.vvtracker.db.b.a.c(field) && cn.com.iresearch.vvtracker.db.b.a.d(field)) {
                    c cVar = new c();
                    field.getType();
                    c.a();
                    cVar.a(cn.com.iresearch.vvtracker.db.b.a.a(field));
                    field.getName();
                    e.b();
                    cVar.a(field.getType());
                    cVar.b(cn.com.iresearch.vvtracker.db.b.a.b((Class) cls, field));
                    cVar.a(cn.com.iresearch.vvtracker.db.b.a.a((Class) cls, field));
                    arrayList.add(cVar);
                }
            }
            return arrayList;
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static String getSelectSqlByTableName(String str) {
        return new StringBuffer("SELECT * FROM ").append(str).toString();
    }

    public static List<d> getOneToManyList(Class<?> cls) {
        List<d> arrayList = new ArrayList();
        try {
            for (Field field : cls.getDeclaredFields()) {
                if (!cn.com.iresearch.vvtracker.db.b.a.c(field) && cn.com.iresearch.vvtracker.db.b.a.e(field)) {
                    d dVar = new d();
                    dVar.a(cn.com.iresearch.vvtracker.db.b.a.a(field));
                    field.getName();
                    e.b();
                    if (field.getGenericType() instanceof ParameterizedType) {
                        if (((Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]) != null) {
                            d.a();
                        }
                        dVar.a(field.getClass());
                        dVar.b(cn.com.iresearch.vvtracker.db.b.a.b((Class) cls, field));
                        dVar.a(cn.com.iresearch.vvtracker.db.b.a.a((Class) cls, field));
                        arrayList.add(dVar);
                    } else {
                        throw new cn.com.iresearch.vvtracker.db.a.b("getOneToManyList Exception:" + field.getName() + "'s type is null");
                    }
                }
            }
            return arrayList;
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        if ((context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) == 0 ? 1 : null) == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
            return false;
        }
        return true;
    }

    public static String getSelectSQL(Class<?> cls) {
        return new StringBuffer("SELECT * FROM ").append(f.a(cls).a()).toString();
    }

    private static String newString(byte[] bArr, Charset charset) {
        return bArr == null ? null : new String(bArr, charset);
    }

    public static long getUnixTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    public static int urlGet(String str) {
        return open(str);
    }

    private static int open(String str) {
        InputStream inputStream = null;
        try {
            int i;
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setUseCaches(false);
            if (200 == httpURLConnection.getResponseCode()) {
                inputStream = httpURLConnection.getInputStream();
                StreamToString(inputStream);
                i = 1;
            } else {
                inputStream = httpURLConnection.getErrorStream();
                StreamToString(inputStream);
                i = 0;
            }
            String.valueOf(i);
            if (inputStream == null) {
                return i;
            }
            try {
                inputStream.close();
                return i;
            } catch (IOException e) {
                e.printStackTrace();
                return i;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            return 0;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
        }
    }

    private static String StreamToString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1024);
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                bufferedReader.close();
                return stringBuilder.toString();
            }
            stringBuilder.append(readLine);
        }
    }

    public static String getCreatTableSQL(Class<?> cls) {
        f a = f.a(cls);
        cn.com.iresearch.vvtracker.db.d.a b = a.b();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CREATE TABLE IF NOT EXISTS ");
        stringBuffer.append(a.a());
        stringBuffer.append(" ( ");
        Class e = b.e();
        if (e == Integer.TYPE || e == Integer.class) {
            stringBuffer.append("\"").append(b.c()).append("\"    INTEGER PRIMARY KEY AUTOINCREMENT,");
        } else {
            stringBuffer.append("\"").append(b.c()).append("\"    TEXT PRIMARY KEY,");
        }
        for (e c : a.a.values()) {
            stringBuffer.append("\"").append(c.c());
            stringBuffer.append("\",");
        }
        for (c c2 : a.b.values()) {
            stringBuffer.append("\"").append(c2.c()).append("\",");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.append(" )");
        return stringBuffer.toString();
    }

    private static String MD5(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                int i = b & 255;
                if (i < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String newStringUtf8(byte[] bArr) {
        return bArr == null ? null : new String(bArr, cn.com.iresearch.vvtracker.a.a.d.a);
    }

    private static String getUid(Context context) {
        try {
            String MD5 = MD5("uid");
            SharedPreferences sharedPreferences = context.getSharedPreferences(MD5, 0);
            String string = sharedPreferences.getString(MD5, "");
            if (!"".equals(string)) {
                return string;
            }
            string = getUniqueID(context);
            sharedPreferences.edit().putString(MD5, string).commit();
            return string;
        } catch (Exception e) {
            return getUniqueID(context);
        }
    }

    private static b property2KeyValue(e eVar, Object obj) {
        String c = eVar.c();
        Object a = eVar.a(obj);
        if (a != null) {
            return new b(c, a);
        }
        if (eVar.d() == null || eVar.d().trim().length() == 0) {
            return null;
        }
        return new b(c, eVar.d());
    }

    private static String getUid2(Context context) {
        String str = "emt";
        try {
            boolean z = cn.com.iresearch.vvtracker.a.b.a;
            int[] iArr = new int[]{17};
            String str2 = getChar('a', new int[]{12, 21, 2, 21, 17, 19, 10});
            String str3 = getChar(' ', iArr);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(str2.substring(0, 4));
            stringBuffer.append(str3);
            stringBuffer.append(str2.substring(4, str2.length()).toUpperCase(Locale.getDefault()));
            str2 = stringBuffer.toString();
            if (str2 != null && str2.trim().length() > 0) {
                str = cn.com.iresearch.vvtracker.a.a.a(str2, getUid(context));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    private static b manyToOne2KeyValue(c cVar, Object obj) {
        String c = cVar.c();
        Object a = cVar.a(obj);
        if (a == null) {
            return null;
        }
        a = f.a(a.getClass()).b().a(a);
        if (c == null || a == null) {
            return null;
        }
        return new b(c, a);
    }

    private static String getIMEI(Context context) {
        String str = "";
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    private static String getUniqueID(Context context) {
        String string;
        MessageDigest instance;
        NoSuchAlgorithmException e;
        String imei = getIMEI(context);
        String stringBuilder = new StringBuilder(VType.FLV_1080P3M).append(Build.BOARD.length() % 10).append(Build.BRAND.length() % 10).append(Build.CPU_ABI.length() % 10).append(Build.DEVICE.length() % 10).append(Build.DISPLAY.length() % 10).append(Build.HOST.length() % 10).append(Build.ID.length() % 10).append(Build.MANUFACTURER.length() % 10).append(Build.MODEL.length() % 10).append(Build.PRODUCT.length() % 10).append(Build.TAGS.length() % 10).append(Build.TYPE.length() % 10).append(Build.USER.length() % 10).toString();
        String str = "";
        try {
            string = Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception e2) {
            e2.printStackTrace();
            string = str;
        }
        String str2 = "";
        try {
            str = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo().getMacAddress();
        } catch (Exception e3) {
            e3.printStackTrace();
            str = str2;
        }
        str2 = new StringBuilder(String.valueOf(imei)).append(stringBuilder).append(string).append(str).toString();
        try {
            instance = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            try {
                instance.update(str2.getBytes(), 0, str2.length());
            } catch (NoSuchAlgorithmException e4) {
                e = e4;
                e.printStackTrace();
                return new BigInteger(1, instance.digest()).toString(16).toUpperCase(Locale.getDefault());
            }
        } catch (NoSuchAlgorithmException e5) {
            NoSuchAlgorithmException noSuchAlgorithmException = e5;
            instance = null;
            e = noSuchAlgorithmException;
            e.printStackTrace();
            return new BigInteger(1, instance.digest()).toString(16).toUpperCase(Locale.getDefault());
        }
        return new BigInteger(1, instance.digest()).toString(16).toUpperCase(Locale.getDefault());
    }

    private static String getChar(char c, int[] iArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i : iArr) {
            stringBuffer.append(getChar(c, i));
        }
        return stringBuffer.toString();
    }

    private static char getChar(char c, int i) {
        char c2 = c;
        for (int i2 = 0; i2 < i; i2++) {
            c2 = (char) (c2 + 1);
            if (i2 == i - 1) {
                break;
            }
        }
        return c2;
    }
}
