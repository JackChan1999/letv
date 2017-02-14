package com.letv.core.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.letv.base.R;
import com.letv.core.BaseApplication;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.LetvConstant;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.db.PreferencesManager;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class LetvTools {

    public static class PointsUtils {
        public static boolean canLoginGainPoints() {
            if (!PreferencesManager.getInstance().isLogin()) {
                return false;
            }
            if (PreferencesManager.getInstance().getPointsLoginGainDate().equals(PreferencesManager.getInstance().getUserId() + StringUtils.timeString(System.currentTimeMillis()))) {
                return false;
            }
            return true;
        }

        public static boolean canVideoGainPoints(String date) {
            return !PreferencesManager.getInstance().getPointsVideoGainDate().equals(date);
        }

        public static boolean canShareGainPoints() {
            if (!PreferencesManager.getInstance().isLogin()) {
                return false;
            }
            if (PreferencesManager.getInstance().getPointsShareGainDate().equals(PreferencesManager.getInstance().getUserId() + StringUtils.timeString(System.currentTimeMillis()))) {
                return false;
            }
            return true;
        }

        public static void saveLoginGainPoints() {
            PreferencesManager.getInstance().setPointsLoginGainDate(PreferencesManager.getInstance().getUserId() + StringUtils.timeString(System.currentTimeMillis()));
        }

        public static void saveVideoGainPoints(String date) {
            PreferencesManager.getInstance().setPointsVideoGainDate(date);
        }

        public static void saveSharePoints() {
            PreferencesManager.getInstance().setPointsShareGainDate(PreferencesManager.getInstance().getUserId() + StringUtils.timeString(System.currentTimeMillis()));
        }
    }

    public static String generSignedKey53(HashMap<String, String> maps) {
        if (maps == null || maps.size() == 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        Set<String> set = maps.keySet();
        String[] keyArray = new String[set.size()];
        set.toArray(keyArray);
        Arrays.sort(keyArray);
        for (int i = 0; i < keyArray.length; i++) {
            builder.append(keyArray[i]);
            builder.append(SearchCriteria.EQ);
            if (!TextUtils.isEmpty((CharSequence) maps.get(keyArray[i]))) {
                builder.append((String) maps.get(keyArray[i]));
            }
            builder.append("&");
        }
        builder.append(LetvConstant.MIYUE53);
        return MD5.toMd5(builder.toString());
    }

    public static String generateLoginSignKey(String loginname, String password) {
        StringBuilder builder = new StringBuilder();
        builder.append(LetvUtils.getClientVersionName());
        builder.append(loginname);
        builder.append(password);
        builder.append(Global.DEVICEID);
        builder.append("e3F5gIfT3zj43MAc3F");
        return MD5.toMd5(builder.toString());
    }

    public static String generateSignKey(String userid, String pid) {
        StringBuilder builder = new StringBuilder();
        builder.append("mobile");
        builder.append(userid);
        builder.append(pid);
        return MD5.toMd5(builder.toString());
    }

    public static String generateVideoFileKey(String mid, String tm) {
        StringBuilder builder = new StringBuilder();
        builder.append(mid);
        builder.append(",");
        builder.append(tm);
        builder.append(",");
        builder.append("bh65OzqYYYmHRQ");
        return MD5.toMd5(builder.toString());
    }

    public static String generateBatchAuthorKey(String liveIds, String userId) {
        StringBuilder builder = new StringBuilder();
        builder.append(liveIds);
        builder.append(",");
        builder.append(userId);
        builder.append(",");
        builder.append("bh65O6eus9yRQ");
        return MD5.toMd5(builder.toString());
    }

    public static String generateExceptionFilesKey(String uuid) {
        StringBuilder builder = new StringBuilder();
        builder.append(uuid);
        builder.append("x6e2eAe2sB4ts1289wa2s");
        return MD5.toMd5(builder.toString());
    }

    public static String generateLiveEncryptKey(String streamId, String tm) {
        StringBuilder builder = new StringBuilder();
        builder.append(streamId);
        builder.append(",");
        builder.append(tm);
        builder.append(",");
        builder.append(LetvConstant.MIYUE);
        return MD5.toMd5(builder.toString());
    }

    public static void closeCursor(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    public static String getNumberTime(long time_second) {
        Formatter formatter = new Formatter(null, Locale.getDefault());
        try {
            long seconds = time_second % 60;
            long minutes = (time_second / 60) % 60;
            String formatter2 = formatter.format("%02d:%02d:%02d", new Object[]{Long.valueOf(time_second / 3600), Long.valueOf(minutes), Long.valueOf(seconds)}).toString();
            return formatter2;
        } finally {
            formatter.close();
        }
    }

    public static String getPcode() {
        return LetvConfig.getPcode();
    }

    public static String getClientVersionName() {
        try {
            return BaseApplication.getInstance().getPackageManager().getPackageInfo(BaseApplication.getInstance().getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int getClientVersionCode() {
        try {
            return BaseApplication.getInstance().getPackageManager().getPackageInfo(BaseApplication.getInstance().getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return PreferencesManager.getInstance().getSettingVersionCode();
        }
    }

    public static boolean sdCardMounted() {
        String state = Environment.getExternalStorageState();
        return state.equals("mounted") && !state.equals("mounted_ro");
    }

    public static int getScreenWidth(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getWidth();
    }

    public static int dipToPx(Context mContext, int dipValue) {
        if (mContext == null) {
            return dipValue;
        }
        return (int) ((((float) dipValue) * mContext.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static boolean checkIp(String controlAreas, int disableType) {
        return !"3".equals(isAllowforeign(controlAreas, disableType));
    }

    public static String isAllowforeign(String controlAreas, int disableType) {
        String allowforeign = "0";
        if (TextUtils.isEmpty(controlAreas)) {
            return allowforeign;
        }
        switch (disableType) {
            case 1:
                allowforeign = "0";
                break;
            case 2:
                if (BaseApplication.getInstance().getIp() != null) {
                    if (BaseApplication.getInstance().getIp().userCountry != null) {
                        if (!controlAreas.contains(BaseApplication.getInstance().getIp().userCountry)) {
                            allowforeign = "3";
                            break;
                        }
                        allowforeign = "0";
                        break;
                    }
                    allowforeign = "0";
                    break;
                }
                allowforeign = "0";
                break;
            case 3:
                allowforeign = "3";
                break;
            case 4:
                if (BaseApplication.getInstance().getIp() != null) {
                    if (!controlAreas.contains(BaseApplication.getInstance().getIp().userCountry)) {
                        allowforeign = "0";
                        break;
                    }
                    allowforeign = "3";
                    break;
                }
                allowforeign = "0";
                break;
        }
        return allowforeign;
    }

    public static String getTextFromServer(String constantId, String defaultText) {
        String str = "";
        str = TipUtils.getTipMessage(constantId);
        if (TextUtils.isEmpty(str)) {
            return defaultText;
        }
        return str;
    }

    public static String getTextTitleFromServer(String constantId, String defaultText) {
        String str = "";
        str = TipUtils.getTipTitle(constantId);
        if (TextUtils.isEmpty(str)) {
            return defaultText;
        }
        return str;
    }

    public static <T, P> ArrayList<T> copyBeanList(List<P> list, Class<T> clazz) {
        if (list == null) {
            return null;
        }
        ArrayList<T> copyList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            copyList.add(copyBean(list.get(i), clazz));
        }
        return copyList;
    }

    public static <T, P> T copyBean(P p, Class<T> clazz) {
        if (p == null) {
            return null;
        }
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Field[] fields = null;
        try {
            fields = p.getClass().getDeclaredFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i].setAccessible(true);
                String fieldName = fields[i].getName();
                Object fieldValue = fields[i].get(p);
                try {
                    Field filed = clazz.getDeclaredField(fieldName);
                    filed.setAccessible(true);
                    filed.set(t, fieldValue);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } catch (Exception e22) {
                e22.printStackTrace();
            }
        }
        return t;
    }

    public static void logBook(String log, Class<?> c) {
        if (LetvConfig.isDebug()) {
            LogInfo.log("book", c.getSimpleName() + " " + log);
        }
    }

    public static boolean checkServiceIsRunning(Context context, String serviceName) {
        if (context == null || TextUtils.isEmpty(serviceName)) {
            return false;
        }
        ActivityManager manager = (ActivityManager) context.getSystemService("activity");
        if (manager == null) {
            return false;
        }
        List<RunningServiceInfo> list = manager.getRunningServices(Integer.MAX_VALUE);
        if (BaseTypeUtils.isListEmpty(list)) {
            return false;
        }
        for (RunningServiceInfo service : list) {
            if (service != null && service.service != null && serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static String calculateDownloadSpeed(long lastStarted, long curtime, long downloadedSize) {
        long time = (curtime - lastStarted) / 1000;
        if (time <= 0) {
            return "";
        }
        return "" + ((downloadedSize / time) / 1024);
    }

    public static String formatSpeed(long speed) {
        if (speed < 1) {
            return "";
        }
        if (speed >= 1024 && speed < 1048576) {
            float msd = ((float) speed) / 1024.0f;
            return String.format("%.1f", new Object[]{Float.valueOf(msd)}) + "M/s";
        } else if (speed <= 1048576) {
            return speed + "K/s";
        } else {
            float gsd = ((float) speed) / 1024.0f;
            return String.format("%.1f", new Object[]{Float.valueOf(gsd)}) + "G/s";
        }
    }

    public static long formTimeToMillion(String time) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static String formTimeToDay(Context context, String time) {
        Calendar today = Calendar.getInstance();
        Calendar other = Calendar.getInstance();
        DateFormat df_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat df_time = new SimpleDateFormat("HH:mm");
        try {
            Date other_date = df_date.parse(time);
            String tm = " " + df_time.format(other_date);
            today.setTime(new Date());
            today.set(11, 0);
            today.set(12, 0);
            today.set(13, 0);
            other.setTime(other_date);
            switch ((int) Math.floor((double) (((float) (other.getTimeInMillis() - today.getTimeInMillis())) / 8.64E7f))) {
                case -1:
                    return context.getString(R.string.letvutil_week_yesterday) + tm;
                case 0:
                    return context.getString(R.string.letvutil_week_tody) + tm;
                case 1:
                    return context.getString(R.string.letvutil_week_tomorrow) + tm;
                default:
                    return new SimpleDateFormat("MM" + LetvUtils.getString(R.string.month) + "dd" + LetvUtils.getString(R.string.day) + " HH:mm").format(df_date.parse(time));
            }
        } catch (ParseException e) {
            return time;
        }
    }

    public static void changeLight(View imageView, int brightness) {
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1.0f, 0.0f, 0.0f, 0.0f, (float) brightness, 0.0f, 1.0f, 0.0f, 0.0f, (float) brightness, 0.0f, 0.0f, 1.0f, 0.0f, (float) brightness, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        ((ImageView) imageView).setColorFilter(new ColorMatrixColorFilter(cMatrix));
    }

    public static String toCommentLikeCountText(int playCount) {
        if (playCount <= 0) {
            return "";
        }
        if (playCount < 10000) {
            return new DecimalFormat("#,###").format((long) playCount) + "";
        } else if (playCount <= 100000000) {
            m = new DecimalFormat("#,###.0").format(((double) playCount) / 10000.0d);
            if (m != null && m.endsWith(".0")) {
                m = m.substring(0, m.length() - 2);
            }
            return m + LetvUtils.getString(R.string.ten_thousands);
        } else {
            m = new DecimalFormat("#,###.0").format(((double) playCount) / 1.0E8d);
            if (m != null && m.endsWith(".0")) {
                m = m.substring(0, m.length() - 2);
            }
            return m + LetvUtils.getString(R.string.hundred_million);
        }
    }
}
