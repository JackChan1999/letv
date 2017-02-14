package com.letv.android.client.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.core.bean.switchinfo.ThemeDataBean;
import com.letv.core.bean.switchinfo.ThemeDataBean.ThemeDataItem;
import com.letv.core.bean.switchinfo.ThemeDataBean.ThemeItemInfo;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.UIsUtils;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class ThemeDataManager {
    public static final String NAME_BOTTOM_NAVIGATION_LINE = "bottom_navigation_line";
    public static final String NAME_BOTTOM_NAVIGATION_PIC = "bottom_navigation_pic";
    public static final String NAME_FIND_COLOR = "find_color";
    public static final String NAME_FIND_PIC = "find_pic";
    public static final String NAME_HOME_COLOR = "home_color";
    public static final String NAME_HOME_DOWNLOAD = "home_download";
    public static final String NAME_HOME_HISTORY = "home_history";
    public static final String NAME_HOME_LESO_ICON = "le_search_icon";
    public static final String NAME_HOME_LESO_PIC = "le_search_pic";
    public static final String NAME_HOME_PIC = "home_pic";
    public static final String NAME_HOME_SERVICE_PIC = "service_bgcolor";
    public static final String NAME_LE_GAME_ICON = "le_game_icon";
    public static final String NAME_LE_MALL_ICON = "le_mall_icon";
    public static final String NAME_LIVING_COLOR = "living_color";
    public static final String NAME_LIVING_PIC = "living_pic";
    public static final String NAME_LOGO_ICON = "logo_icon";
    public static final String NAME_MYSELF_COLOR = "myself_color";
    public static final String NAME_MYSELF_PIC = "myself_pic";
    public static final String NAME_SEARCH_COLOR = "le_search_color";
    public static final String NAME_SEARCH_TEXT_COLOR = "le_search_wdcolor";
    public static final String NAME_TOP_CHANNEL_PIC = "top_channel_pic";
    public static final String NAME_TOP_NAVIGATION_BG_COLOR = "top_navigation_bgcolor";
    public static final String NAME_TOP_NAVIGATION_COLOR = "top_navigation_color";
    public static final String NAME_TOP_PIC = "top_pic";
    public static final String NAME_TOP_STATUSBAR_COLOR = "top_statusbar_color";
    public static final String NAME_VIP_COLOR = "vip_color";
    public static final String NAME_VIP_PIC = "vip_pic";
    private static ThemeDataManager sInstance;
    private ThemeImagesDownloader mImageDownloader;
    private ThemeDataItem mNewDataItem;

    public interface IThemeImagesDownloadCallBack {
        void updateViews();
    }

    private ThemeDataManager() {
    }

    public static ThemeDataManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ThemeDataManager();
            sInstance.mImageDownloader = new ThemeImagesDownloader();
        }
        return sInstance;
    }

    public void init(ThemeDataBean themeDataBean, IThemeImagesDownloadCallBack callBack) {
        if (callBack != null && themeDataBean != null) {
            this.mNewDataItem = getCorrectTheme(themeDataBean);
            if (hasNewTheme()) {
                ArrayList<String> urlList = new ArrayList();
                Iterator it = this.mNewDataItem.mItemInfos.iterator();
                while (it.hasNext()) {
                    ThemeItemInfo itemInfo = (ThemeItemInfo) it.next();
                    setDefaultTheme(itemInfo);
                    if (TextUtils.equals("1", itemInfo.mType)) {
                        if (isLargePic(itemInfo)) {
                            String topUrl = getLargeBgRealUrl(itemInfo);
                            if (!(TextUtils.isEmpty(topUrl) || ThemeImageUtils.hasLocalImage(topUrl))) {
                                urlList.add(topUrl);
                            }
                        } else {
                            String unCheckedUrl = getRealUrl(itemInfo, false);
                            if (!(TextUtils.isEmpty(unCheckedUrl) || ThemeImageUtils.hasLocalImage(unCheckedUrl))) {
                                urlList.add(unCheckedUrl);
                            }
                            String checkedUrl = getRealUrl(itemInfo, true);
                            if (!(TextUtils.isEmpty(checkedUrl) || ThemeImageUtils.hasLocalImage(checkedUrl))) {
                                urlList.add(checkedUrl);
                            }
                        }
                    }
                }
                if (urlList.size() <= 0) {
                    callBack.updateViews();
                } else if (NetworkUtils.isNetworkAvailable()) {
                    this.mImageDownloader.downloadImages(urlList, callBack);
                }
            }
        }
    }

    public boolean hasNewTheme() {
        return (this.mNewDataItem == null || BaseTypeUtils.isListEmpty(this.mNewDataItem.mItemInfos)) ? false : true;
    }

    public void setContentTheme(View view, String contentName) {
        if (view != null && hasNewTheme()) {
            ThemeItemInfo itemInfo = getContentInfo(contentName);
            if (itemInfo == null) {
                return;
            }
            if (TextUtils.equals("1", itemInfo.mType)) {
                setImageTheme(view, itemInfo);
            } else if (TextUtils.equals("2", itemInfo.mType)) {
                setColorTheme(view, itemInfo);
            }
        }
    }

    public void setShapeViewTheme(View view, String contentName) {
        if (view != null && hasNewTheme()) {
            ThemeItemInfo itemInfo = getContentInfo(contentName);
            if (itemInfo != null) {
                int color = getIntColor(itemInfo.mUnChecked);
                if (color != -100) {
                    ((GradientDrawable) view.getBackground()).setColor(color);
                }
            }
        }
    }

    public void setShapeSelectorViewTheme(View view, String contentName, int radious, boolean transpanent) {
        if (view != null && hasNewTheme()) {
            ThemeItemInfo itemInfo = getContentInfo(contentName);
            if (itemInfo != null) {
                int checkedColor = getIntColor(itemInfo.mChecked);
                if (checkedColor == -100) {
                    checkedColor = itemInfo.mDefaultChecked;
                }
                int unCheckedColor = getIntColor(itemInfo.mUnChecked);
                if (transpanent) {
                    unCheckedColor = 0;
                } else if (unCheckedColor == -100) {
                    unCheckedColor = itemInfo.mDefaultUnChecked;
                }
                Drawable pressedDrawable = UIsUtils.createGradientDrawable(UIsUtils.dipToPx((float) radious), checkedColor);
                view.setBackgroundDrawable(UIsUtils.createStateDrawable(UIsUtils.createGradientDrawable(UIsUtils.dipToPx((float) radious), unCheckedColor), pressedDrawable, pressedDrawable, pressedDrawable));
            }
        }
    }

    public static void clearThemeInfos() {
        if (sInstance != null) {
            sInstance.mImageDownloader.closeThreadPool();
            sInstance.mNewDataItem = null;
            sInstance = null;
        }
    }

    private void setImageTheme(View view, ThemeItemInfo itemInfo) {
        String url;
        if (isLargePic(itemInfo)) {
            url = getLargeBgRealUrl(itemInfo);
        } else {
            url = getRealUrl(itemInfo, false);
        }
        Drawable normalDrawable = ThemeImageUtils.getImageDrawable(view.getContext(), getRealUrl(itemInfo, false), itemInfo.mDefaultUnChecked);
        Drawable pressedDrawable = ThemeImageUtils.getImageDrawable(view.getContext(), getRealUrl(itemInfo, true), itemInfo.mDefaultChecked);
        if (view instanceof ImageView) {
            view.setBackgroundDrawable(null);
            ((ImageView) view).setImageDrawable(UIsUtils.createStateDrawable(normalDrawable, pressedDrawable, pressedDrawable, pressedDrawable));
            return;
        }
        view.setBackgroundDrawable(normalDrawable);
    }

    public void setColorTheme(View view, ThemeItemInfo itemInfo) {
        if (view != null && itemInfo != null) {
            int checkedColor = getIntColor(itemInfo.mChecked);
            if (checkedColor == -100) {
                checkedColor = view.getResources().getColor(itemInfo.mDefaultChecked);
            }
            int unCheckedColor = getIntColor(itemInfo.mUnChecked);
            if (unCheckedColor == -100) {
                unCheckedColor = view.getResources().getColor(itemInfo.mDefaultUnChecked);
            }
            ColorStateList colorStateList = UIsUtils.createColorStateList(unCheckedColor, checkedColor);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(colorStateList);
            } else {
                view.setBackgroundColor(unCheckedColor);
            }
        }
    }

    public ThemeItemInfo getContentInfo(String contentName) {
        ThemeItemInfo itemInfo = null;
        int size = this.mNewDataItem.mItemInfos.size();
        for (int i = 0; i < size; i++) {
            itemInfo = (ThemeItemInfo) this.mNewDataItem.mItemInfos.get(i);
            if (TextUtils.equals(contentName, itemInfo.mContentName)) {
                break;
            }
            itemInfo = null;
        }
        return itemInfo;
    }

    public String getLargeBgRealUrl(ThemeItemInfo itemInfo) {
        String url = itemInfo.mIcon1080;
        if (LetvApplication.sDensity <= 2.0f && !TextUtils.isEmpty(itemInfo.mUnChecked)) {
            return itemInfo.mUnChecked;
        }
        if (LetvApplication.sDensity > 3.0f || TextUtils.isEmpty(itemInfo.mIcon720)) {
            return url;
        }
        return itemInfo.mIcon720;
    }

    private ThemeDataItem getCorrectTheme(ThemeDataBean themeDataBean) {
        ThemeDataItem dataItem = null;
        if (BaseTypeUtils.isListEmpty(themeDataBean.mItemList)) {
            return null;
        }
        long serverTime = getLongTime(themeDataBean.mServerTime, new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US));
        if (serverTime == 0) {
            serverTime = System.currentTimeMillis();
        }
        int size = themeDataBean.mItemList.size();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (int i = 0; i < size; i++) {
            dataItem = (ThemeDataItem) themeDataBean.mItemList.get(i);
            long startTime = getLongTime(dataItem.mStartTime, simpleDateFormat);
            long endTime = getLongTime(dataItem.mEndTime, simpleDateFormat);
            if (startTime <= serverTime && serverTime <= endTime) {
                break;
            }
            dataItem = null;
        }
        return dataItem;
    }

    private boolean isLargePic(ThemeItemInfo itemInfo) {
        return TextUtils.equals(itemInfo.mContentName, NAME_TOP_PIC) || TextUtils.equals(itemInfo.mContentName, NAME_BOTTOM_NAVIGATION_PIC) || TextUtils.equals(itemInfo.mContentName, NAME_HOME_SERVICE_PIC);
    }

    private long getLongTime(String timeStr, SimpleDateFormat format) {
        long time = 0;
        try {
            time = format.parse(timeStr).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    private int getIntColor(String colorStr) {
        int color = -100;
        try {
            color = Color.parseColor(colorStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return color;
    }

    private String getRealUrl(ThemeItemInfo itemInfo, boolean selectChecked) {
        String url = "";
        if (isLargePic(itemInfo)) {
            return getLargeBgRealUrl(itemInfo);
        }
        if (selectChecked) {
            return LetvApplication.sDensity > 2.0f ? itemInfo.mCheckedHigh : itemInfo.mChecked;
        }
        return LetvApplication.sDensity > 2.0f ? itemInfo.mUnCheckedHigh : itemInfo.mUnChecked;
    }

    private void setDefaultTheme(ThemeItemInfo info) {
        String str = info.mContentName;
        Object obj = -1;
        switch (str.hashCode()) {
            case -1585330901:
                if (str.equals(NAME_BOTTOM_NAVIGATION_LINE)) {
                    obj = 11;
                    break;
                }
                break;
            case -1570381038:
                if (str.equals(NAME_SEARCH_COLOR)) {
                    obj = 1;
                    break;
                }
                break;
            case -1560739187:
                if (str.equals(NAME_LOGO_ICON)) {
                    obj = null;
                    break;
                }
                break;
            case -1139544160:
                if (str.equals(NAME_TOP_PIC)) {
                    obj = 6;
                    break;
                }
                break;
            case -1092123399:
                if (str.equals(NAME_HOME_LESO_PIC)) {
                    obj = 17;
                    break;
                }
                break;
            case -1083961151:
                if (str.equals(NAME_VIP_COLOR)) {
                    obj = 19;
                    break;
                }
                break;
            case -678866428:
                if (str.equals(NAME_FIND_PIC)) {
                    obj = 15;
                    break;
                }
                break;
            case -535521952:
                if (str.equals(NAME_LE_GAME_ICON)) {
                    obj = 3;
                    break;
                }
                break;
            case -523995587:
                if (str.equals(NAME_TOP_NAVIGATION_BG_COLOR)) {
                    obj = 8;
                    break;
                }
                break;
            case -485863894:
                if (str.equals(NAME_HOME_PIC)) {
                    obj = 12;
                    break;
                }
                break;
            case -349049756:
                if (str.equals(NAME_TOP_CHANNEL_PIC)) {
                    obj = 7;
                    break;
                }
                break;
            case -328230541:
                if (str.equals(NAME_BOTTOM_NAVIGATION_PIC)) {
                    obj = 10;
                    break;
                }
                break;
            case -177655500:
                if (str.equals(NAME_HOME_HISTORY)) {
                    obj = 4;
                    break;
                }
                break;
            case 432576861:
                if (str.equals(NAME_FIND_COLOR)) {
                    obj = 21;
                    break;
                }
                break;
            case 463688296:
                if (str.equals(NAME_VIP_PIC)) {
                    obj = 13;
                    break;
                }
                break;
            case 817553534:
                if (str.equals(NAME_LE_MALL_ICON)) {
                    obj = 2;
                    break;
                }
                break;
            case 886286068:
                if (str.equals(NAME_LIVING_PIC)) {
                    obj = 14;
                    break;
                }
                break;
            case 1224418307:
                if (str.equals(NAME_HOME_COLOR)) {
                    obj = 18;
                    break;
                }
                break;
            case 1305571917:
                if (str.equals(NAME_LIVING_COLOR)) {
                    obj = 20;
                    break;
                }
                break;
            case 1545277416:
                if (str.equals(NAME_HOME_DOWNLOAD)) {
                    obj = 5;
                    break;
                }
                break;
            case 1588579267:
                if (str.equals(NAME_MYSELF_PIC)) {
                    obj = 16;
                    break;
                }
                break;
            case 1806209826:
                if (str.equals(NAME_TOP_NAVIGATION_COLOR)) {
                    obj = 9;
                    break;
                }
                break;
            case 1899470684:
                if (str.equals(NAME_MYSELF_COLOR)) {
                    obj = 22;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                info.mDefaultChecked = 2130838565;
                info.mDefaultUnChecked = 2130838565;
                return;
            case 1:
                info.mDefaultChecked = 2130838891;
                info.mDefaultUnChecked = 2130838890;
                return;
            case 2:
                info.mDefaultChecked = 2130838293;
                info.mDefaultUnChecked = 2130838292;
                return;
            case 3:
                info.mDefaultChecked = 2130838132;
                info.mDefaultUnChecked = 2130838131;
                return;
            case 4:
                info.mDefaultChecked = 2130838593;
                info.mDefaultUnChecked = 2130838592;
                return;
            case 5:
                info.mDefaultChecked = 2130837949;
                info.mDefaultUnChecked = 2130837946;
                return;
            case 6:
                info.mDefaultChecked = 2131493118;
                info.mDefaultUnChecked = 2131493118;
                return;
            case 7:
                info.mDefaultChecked = 2130838263;
                info.mDefaultUnChecked = 2130838262;
                return;
            case 8:
                info.mDefaultChecked = 2131493118;
                info.mDefaultUnChecked = 2131493118;
                return;
            case 9:
                info.mDefaultChecked = 2131493202;
                info.mDefaultUnChecked = 2131493237;
                return;
            case 10:
                info.mDefaultChecked = 2131493353;
                info.mDefaultUnChecked = 2131493353;
                return;
            case 11:
                info.mDefaultChecked = 2131493308;
                info.mDefaultUnChecked = 2131493308;
                return;
            case 12:
                info.mDefaultChecked = 2130838573;
                info.mDefaultUnChecked = 2130838574;
                return;
            case 13:
                info.mDefaultChecked = 2130838579;
                info.mDefaultUnChecked = 2130838580;
                return;
            case 14:
                info.mDefaultChecked = 2130838575;
                info.mDefaultUnChecked = 2130838576;
                return;
            case 15:
                info.mDefaultChecked = 2130838571;
                info.mDefaultUnChecked = 2130838572;
                return;
            case 16:
                info.mDefaultChecked = 2130838577;
                info.mDefaultUnChecked = 2130838578;
                return;
            case 17:
                info.mDefaultChecked = 2130838429;
                info.mDefaultUnChecked = 2130838429;
                return;
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                info.mDefaultChecked = 2131493202;
                info.mDefaultUnChecked = 2131493071;
                return;
            default:
                return;
        }
    }

    public void setStatusBarDarkMode(boolean darkmode, Window window) {
        int i = 0;
        Class<? extends Window> clazz = window.getClass();
        try {
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            int darkModeFlag = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", new Class[]{Integer.TYPE, Integer.TYPE});
            Object[] objArr = new Object[2];
            if (darkmode) {
                i = darkModeFlag;
            }
            objArr[0] = Integer.valueOf(i);
            objArr[1] = Integer.valueOf(darkModeFlag);
            extraFlagField.invoke(window, objArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
