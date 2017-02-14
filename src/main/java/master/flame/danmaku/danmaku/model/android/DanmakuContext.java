package master.flame.danmaku.danmaku.model.android;

import android.graphics.Typeface;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import master.flame.danmaku.controller.DanmakuFilters;
import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.AlphaValue;
import master.flame.danmaku.danmaku.model.GlobalFlagValues;
import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer.Proxy;
import master.flame.danmaku.danmaku.parser.DanmakuFactory;

public class DanmakuContext {
    public boolean FBDanmakuVisibility = true;
    public boolean FTDanmakuVisibility = true;
    public boolean L2RDanmakuVisibility = true;
    public boolean R2LDanmakuVisibility = true;
    public boolean SecialDanmakuVisibility = true;
    private boolean mBlockGuestDanmaku = false;
    private BaseCacheStuffer mCacheStuffer;
    private List<WeakReference<ConfigChangedCallback>> mCallbackList;
    List<Integer> mColorValueWhiteList = new ArrayList();
    public final DanmakuFactory mDanmakuFactory = new DanmakuFactory();
    public final DanmakuFilters mDanmakuFilters = new DanmakuFilters();
    private final AbsDisplayer mDisplayer = new AndroidDisplayer();
    private boolean mDuplicateMergingEnable = false;
    List<Integer> mFilterTypes = new ArrayList();
    public Typeface mFont = null;
    public final GlobalFlagValues mGlobalFlagValues = new GlobalFlagValues();
    private boolean mIsMaxLinesLimited;
    private boolean mIsPreventOverlappingEnabled;
    List<String> mUserHashBlackList = new ArrayList();
    List<Integer> mUserIdBlackList = new ArrayList();
    public int maximumNumsInScreen = -1;
    public int refreshRateMS = 15;
    public float scaleTextSize = 1.0f;
    public float scrollSpeedFactor = 1.0f;
    public int shadowRadius = 3;
    public BorderType shadowType = BorderType.SHADOW;
    public int transparency = AlphaValue.MAX;

    public interface ConfigChangedCallback {
        boolean onDanmakuConfigChanged(DanmakuContext danmakuContext, DanmakuConfigTag danmakuConfigTag, Object... objArr);
    }

    public enum BorderType {
        NONE,
        SHADOW,
        STROKEN
    }

    public enum DanmakuConfigTag {
        FT_DANMAKU_VISIBILITY,
        FB_DANMAKU_VISIBILITY,
        L2R_DANMAKU_VISIBILITY,
        R2L_DANMAKU_VISIBILIY,
        SPECIAL_DANMAKU_VISIBILITY,
        TYPEFACE,
        TRANSPARENCY,
        SCALE_TEXTSIZE,
        MAXIMUM_NUMS_IN_SCREEN,
        DANMAKU_STYLE,
        DANMAKU_BOLD,
        COLOR_VALUE_WHITE_LIST,
        USER_ID_BLACK_LIST,
        USER_HASH_BLACK_LIST,
        SCROLL_SPEED_FACTOR,
        BLOCK_GUEST_DANMAKU,
        DUPLICATE_MERGING_ENABLED,
        MAXIMUN_LINES,
        OVERLAPPING_ENABLE;

        public boolean isVisibilityRelatedTag() {
            return equals(FT_DANMAKU_VISIBILITY) || equals(FB_DANMAKU_VISIBILITY) || equals(L2R_DANMAKU_VISIBILITY) || equals(R2L_DANMAKU_VISIBILIY) || equals(SPECIAL_DANMAKU_VISIBILITY) || equals(COLOR_VALUE_WHITE_LIST) || equals(USER_ID_BLACK_LIST);
        }
    }

    public static DanmakuContext create() {
        return new DanmakuContext();
    }

    public AbsDisplayer getDisplayer() {
        return this.mDisplayer;
    }

    public DanmakuContext setTypeface(Typeface font) {
        if (this.mFont != font) {
            this.mFont = font;
            this.mDisplayer.clearTextHeightCache();
            this.mDisplayer.setTypeFace(font);
            notifyConfigureChanged(DanmakuConfigTag.TYPEFACE, new Object[0]);
        }
        return this;
    }

    public DanmakuContext setDanmakuTransparency(float p) {
        int newTransparency = (int) (((float) AlphaValue.MAX) * p);
        if (newTransparency != this.transparency) {
            this.transparency = newTransparency;
            this.mDisplayer.setTransparency(newTransparency);
            notifyConfigureChanged(DanmakuConfigTag.TRANSPARENCY, Float.valueOf(p));
        }
        return this;
    }

    public DanmakuContext setScaleTextSize(float p) {
        if (this.scaleTextSize != p) {
            this.scaleTextSize = p;
            this.mDisplayer.clearTextHeightCache();
            this.mDisplayer.setScaleTextSizeFactor(p);
            this.mGlobalFlagValues.updateMeasureFlag();
            this.mGlobalFlagValues.updateVisibleFlag();
            notifyConfigureChanged(DanmakuConfigTag.SCALE_TEXTSIZE, Float.valueOf(p));
        }
        return this;
    }

    public boolean getFTDanmakuVisibility() {
        return this.FTDanmakuVisibility;
    }

    public DanmakuContext setFTDanmakuVisibility(boolean visible) {
        setDanmakuVisible(visible, 5);
        setFilterData(DanmakuFilters.TAG_TYPE_DANMAKU_FILTER, this.mFilterTypes);
        this.mGlobalFlagValues.updateFilterFlag();
        if (this.FTDanmakuVisibility != visible) {
            this.FTDanmakuVisibility = visible;
            notifyConfigureChanged(DanmakuConfigTag.FT_DANMAKU_VISIBILITY, Boolean.valueOf(visible));
        }
        return this;
    }

    private <T> void setFilterData(String tag, T data) {
        setFilterData(tag, data, true);
    }

    private <T> void setFilterData(String tag, T data, boolean primary) {
        this.mDanmakuFilters.get(tag, primary).setData(data);
    }

    private void setDanmakuVisible(boolean visible, int type) {
        if (visible) {
            this.mFilterTypes.remove(Integer.valueOf(type));
        } else if (!this.mFilterTypes.contains(Integer.valueOf(type))) {
            this.mFilterTypes.add(Integer.valueOf(type));
        }
    }

    public boolean getFBDanmakuVisibility() {
        return this.FBDanmakuVisibility;
    }

    public DanmakuContext setFBDanmakuVisibility(boolean visible) {
        setDanmakuVisible(visible, 4);
        setFilterData(DanmakuFilters.TAG_TYPE_DANMAKU_FILTER, this.mFilterTypes);
        this.mGlobalFlagValues.updateFilterFlag();
        if (this.FBDanmakuVisibility != visible) {
            this.FBDanmakuVisibility = visible;
            notifyConfigureChanged(DanmakuConfigTag.FB_DANMAKU_VISIBILITY, Boolean.valueOf(visible));
        }
        return this;
    }

    public boolean getL2RDanmakuVisibility() {
        return this.L2RDanmakuVisibility;
    }

    public DanmakuContext setL2RDanmakuVisibility(boolean visible) {
        setDanmakuVisible(visible, 6);
        setFilterData(DanmakuFilters.TAG_TYPE_DANMAKU_FILTER, this.mFilterTypes);
        this.mGlobalFlagValues.updateFilterFlag();
        if (this.L2RDanmakuVisibility != visible) {
            this.L2RDanmakuVisibility = visible;
            notifyConfigureChanged(DanmakuConfigTag.L2R_DANMAKU_VISIBILITY, Boolean.valueOf(visible));
        }
        return this;
    }

    public boolean getR2LDanmakuVisibility() {
        return this.R2LDanmakuVisibility;
    }

    public DanmakuContext setR2LDanmakuVisibility(boolean visible) {
        setDanmakuVisible(visible, 1);
        setFilterData(DanmakuFilters.TAG_TYPE_DANMAKU_FILTER, this.mFilterTypes);
        this.mGlobalFlagValues.updateFilterFlag();
        if (this.R2LDanmakuVisibility != visible) {
            this.R2LDanmakuVisibility = visible;
            notifyConfigureChanged(DanmakuConfigTag.R2L_DANMAKU_VISIBILIY, Boolean.valueOf(visible));
        }
        return this;
    }

    public boolean getSecialDanmakuVisibility() {
        return this.SecialDanmakuVisibility;
    }

    public DanmakuContext setSpecialDanmakuVisibility(boolean visible) {
        setDanmakuVisible(visible, 7);
        setFilterData(DanmakuFilters.TAG_TYPE_DANMAKU_FILTER, this.mFilterTypes);
        this.mGlobalFlagValues.updateFilterFlag();
        if (this.SecialDanmakuVisibility != visible) {
            this.SecialDanmakuVisibility = visible;
            notifyConfigureChanged(DanmakuConfigTag.SPECIAL_DANMAKU_VISIBILITY, Boolean.valueOf(visible));
        }
        return this;
    }

    public DanmakuContext setMaximumVisibleSizeInScreen(int maxSize) {
        this.maximumNumsInScreen = maxSize;
        if (maxSize == 100) {
            this.mDanmakuFilters.unregisterFilter(DanmakuFilters.TAG_QUANTITY_DANMAKU_FILTER);
            this.mDanmakuFilters.unregisterFilter(DanmakuFilters.TAG_ELAPSED_TIME_FILTER);
            notifyConfigureChanged(DanmakuConfigTag.MAXIMUM_NUMS_IN_SCREEN, Integer.valueOf(maxSize));
        } else if (maxSize == -1) {
            this.mDanmakuFilters.unregisterFilter(DanmakuFilters.TAG_QUANTITY_DANMAKU_FILTER);
            this.mDanmakuFilters.registerFilter(DanmakuFilters.TAG_ELAPSED_TIME_FILTER);
            notifyConfigureChanged(DanmakuConfigTag.MAXIMUM_NUMS_IN_SCREEN, Integer.valueOf(maxSize));
        } else {
            setFilterData(DanmakuFilters.TAG_QUANTITY_DANMAKU_FILTER, Integer.valueOf(maxSize));
            this.mGlobalFlagValues.updateFilterFlag();
            notifyConfigureChanged(DanmakuConfigTag.MAXIMUM_NUMS_IN_SCREEN, Integer.valueOf(maxSize));
        }
        return this;
    }

    public DanmakuContext setDanmakuStyle(int style, float... values) {
        this.mDisplayer.setDanmakuStyle(style, values);
        notifyConfigureChanged(DanmakuConfigTag.DANMAKU_STYLE, Integer.valueOf(style), values);
        return this;
    }

    public DanmakuContext setDanmakuBold(boolean bold) {
        this.mDisplayer.setFakeBoldText(bold);
        notifyConfigureChanged(DanmakuConfigTag.DANMAKU_BOLD, Boolean.valueOf(bold));
        return this;
    }

    public DanmakuContext setColorValueWhiteList(Integer... colors) {
        this.mColorValueWhiteList.clear();
        if (colors == null || colors.length == 0) {
            this.mDanmakuFilters.unregisterFilter(DanmakuFilters.TAG_TEXT_COLOR_DANMAKU_FILTER);
        } else {
            Collections.addAll(this.mColorValueWhiteList, colors);
            setFilterData(DanmakuFilters.TAG_TEXT_COLOR_DANMAKU_FILTER, this.mColorValueWhiteList);
        }
        this.mGlobalFlagValues.updateFilterFlag();
        notifyConfigureChanged(DanmakuConfigTag.COLOR_VALUE_WHITE_LIST, this.mColorValueWhiteList);
        return this;
    }

    public List<Integer> getColorValueWhiteList() {
        return this.mColorValueWhiteList;
    }

    public DanmakuContext setUserHashBlackList(String... hashes) {
        this.mUserHashBlackList.clear();
        if (hashes == null || hashes.length == 0) {
            this.mDanmakuFilters.unregisterFilter(DanmakuFilters.TAG_USER_HASH_FILTER);
        } else {
            Collections.addAll(this.mUserHashBlackList, hashes);
            setFilterData(DanmakuFilters.TAG_USER_HASH_FILTER, this.mUserHashBlackList);
        }
        this.mGlobalFlagValues.updateFilterFlag();
        notifyConfigureChanged(DanmakuConfigTag.USER_HASH_BLACK_LIST, this.mUserHashBlackList);
        return this;
    }

    public DanmakuContext removeUserHashBlackList(String... hashes) {
        if (!(hashes == null || hashes.length == 0)) {
            for (String hash : hashes) {
                this.mUserHashBlackList.remove(hash);
            }
            setFilterData(DanmakuFilters.TAG_USER_HASH_FILTER, this.mUserHashBlackList);
            this.mGlobalFlagValues.updateFilterFlag();
            notifyConfigureChanged(DanmakuConfigTag.USER_HASH_BLACK_LIST, this.mUserHashBlackList);
        }
        return this;
    }

    public DanmakuContext addUserHashBlackList(String... hashes) {
        if (!(hashes == null || hashes.length == 0)) {
            Collections.addAll(this.mUserHashBlackList, hashes);
            setFilterData(DanmakuFilters.TAG_USER_HASH_FILTER, this.mUserHashBlackList);
            this.mGlobalFlagValues.updateFilterFlag();
            notifyConfigureChanged(DanmakuConfigTag.USER_HASH_BLACK_LIST, this.mUserHashBlackList);
        }
        return this;
    }

    public List<String> getUserHashBlackList() {
        return this.mUserHashBlackList;
    }

    public DanmakuContext setUserIdBlackList(Integer... ids) {
        this.mUserIdBlackList.clear();
        if (ids == null || ids.length == 0) {
            this.mDanmakuFilters.unregisterFilter(DanmakuFilters.TAG_USER_ID_FILTER);
        } else {
            Collections.addAll(this.mUserIdBlackList, ids);
            setFilterData(DanmakuFilters.TAG_USER_ID_FILTER, this.mUserIdBlackList);
        }
        this.mGlobalFlagValues.updateFilterFlag();
        notifyConfigureChanged(DanmakuConfigTag.USER_ID_BLACK_LIST, this.mUserIdBlackList);
        return this;
    }

    public DanmakuContext removeUserIdBlackList(Integer... ids) {
        if (!(ids == null || ids.length == 0)) {
            for (Integer id : ids) {
                this.mUserIdBlackList.remove(id);
            }
            setFilterData(DanmakuFilters.TAG_USER_ID_FILTER, this.mUserIdBlackList);
            this.mGlobalFlagValues.updateFilterFlag();
            notifyConfigureChanged(DanmakuConfigTag.USER_ID_BLACK_LIST, this.mUserIdBlackList);
        }
        return this;
    }

    public DanmakuContext addUserIdBlackList(Integer... ids) {
        if (!(ids == null || ids.length == 0)) {
            Collections.addAll(this.mUserIdBlackList, ids);
            setFilterData(DanmakuFilters.TAG_USER_ID_FILTER, this.mUserIdBlackList);
            this.mGlobalFlagValues.updateFilterFlag();
            notifyConfigureChanged(DanmakuConfigTag.USER_ID_BLACK_LIST, this.mUserIdBlackList);
        }
        return this;
    }

    public List<Integer> getUserIdBlackList() {
        return this.mUserIdBlackList;
    }

    public DanmakuContext blockGuestDanmaku(boolean block) {
        if (this.mBlockGuestDanmaku != block) {
            this.mBlockGuestDanmaku = block;
            if (block) {
                setFilterData(DanmakuFilters.TAG_GUEST_FILTER, Boolean.valueOf(block));
            } else {
                this.mDanmakuFilters.unregisterFilter(DanmakuFilters.TAG_GUEST_FILTER);
            }
            this.mGlobalFlagValues.updateFilterFlag();
            notifyConfigureChanged(DanmakuConfigTag.BLOCK_GUEST_DANMAKU, Boolean.valueOf(block));
        }
        return this;
    }

    public DanmakuContext setScrollSpeedFactor(float p) {
        if (this.scrollSpeedFactor != p) {
            this.scrollSpeedFactor = p;
            this.mDanmakuFactory.updateDurationFactor(p);
            this.mGlobalFlagValues.updateMeasureFlag();
            this.mGlobalFlagValues.updateVisibleFlag();
            notifyConfigureChanged(DanmakuConfigTag.SCROLL_SPEED_FACTOR, Float.valueOf(p));
        }
        return this;
    }

    public DanmakuContext setDuplicateMergingEnabled(boolean enable) {
        if (this.mDuplicateMergingEnable != enable) {
            this.mDuplicateMergingEnable = enable;
            this.mGlobalFlagValues.updateFilterFlag();
            notifyConfigureChanged(DanmakuConfigTag.DUPLICATE_MERGING_ENABLED, Boolean.valueOf(enable));
        }
        return this;
    }

    public boolean isDuplicateMergingEnabled() {
        return this.mDuplicateMergingEnable;
    }

    public DanmakuContext setMaximumLines(Map<Integer, Integer> pairs) {
        this.mIsMaxLinesLimited = pairs != null;
        if (pairs == null) {
            this.mDanmakuFilters.unregisterFilter(DanmakuFilters.TAG_MAXIMUN_LINES_FILTER, false);
        } else {
            setFilterData(DanmakuFilters.TAG_MAXIMUN_LINES_FILTER, pairs, false);
        }
        this.mGlobalFlagValues.updateFilterFlag();
        notifyConfigureChanged(DanmakuConfigTag.MAXIMUN_LINES, pairs);
        return this;
    }

    @Deprecated
    public DanmakuContext setOverlapping(Map<Integer, Boolean> pairs) {
        return preventOverlapping(pairs);
    }

    public DanmakuContext preventOverlapping(Map<Integer, Boolean> pairs) {
        this.mIsPreventOverlappingEnabled = pairs != null;
        if (pairs == null) {
            this.mDanmakuFilters.unregisterFilter(DanmakuFilters.TAG_OVERLAPPING_FILTER, false);
        } else {
            setFilterData(DanmakuFilters.TAG_OVERLAPPING_FILTER, pairs, false);
        }
        this.mGlobalFlagValues.updateFilterFlag();
        notifyConfigureChanged(DanmakuConfigTag.OVERLAPPING_ENABLE, pairs);
        return this;
    }

    public boolean isMaxLinesLimited() {
        return this.mIsMaxLinesLimited;
    }

    public boolean isPreventOverlappingEnabled() {
        return this.mIsPreventOverlappingEnabled;
    }

    public DanmakuContext setCacheStuffer(BaseCacheStuffer cacheStuffer, Proxy cacheStufferAdapter) {
        this.mCacheStuffer = cacheStuffer;
        if (this.mCacheStuffer != null) {
            this.mCacheStuffer.setProxy(cacheStufferAdapter);
            this.mDisplayer.setCacheStuffer(this.mCacheStuffer);
        }
        return this;
    }

    public void registerConfigChangedCallback(ConfigChangedCallback listener) {
        if (listener == null || this.mCallbackList == null) {
            this.mCallbackList = Collections.synchronizedList(new ArrayList());
        }
        for (WeakReference<ConfigChangedCallback> configReferer : this.mCallbackList) {
            if (listener.equals(configReferer.get())) {
                return;
            }
        }
        this.mCallbackList.add(new WeakReference(listener));
    }

    public void unregisterConfigChangedCallback(ConfigChangedCallback listener) {
        if (listener != null && this.mCallbackList != null) {
            for (WeakReference<ConfigChangedCallback> configReferer : this.mCallbackList) {
                if (listener.equals(configReferer.get())) {
                    this.mCallbackList.remove(listener);
                    return;
                }
            }
        }
    }

    public void unregisterAllConfigChangedCallbacks() {
        if (this.mCallbackList != null) {
            this.mCallbackList.clear();
            this.mCallbackList = null;
        }
    }

    private void notifyConfigureChanged(DanmakuConfigTag tag, Object... values) {
        if (this.mCallbackList != null) {
            for (WeakReference<ConfigChangedCallback> configReferer : this.mCallbackList) {
                ConfigChangedCallback cb = (ConfigChangedCallback) configReferer.get();
                if (cb != null) {
                    cb.onDanmakuConfigChanged(this, tag, values);
                }
            }
        }
    }
}
