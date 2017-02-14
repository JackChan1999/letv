package io.fabric.sdk.android.services.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;

class AdvertisingInfoProvider {
    private static final String ADVERTISING_INFO_PREFERENCES = "TwitterAdvertisingInfoPreferences";
    private static final String PREFKEY_ADVERTISING_ID = "advertising_id";
    private static final String PREFKEY_LIMIT_AD_TRACKING = "limit_ad_tracking_enabled";
    private final Context context;
    private final PreferenceStore preferenceStore;

    public AdvertisingInfoProvider(Context context) {
        this.context = context.getApplicationContext();
        this.preferenceStore = new PreferenceStoreImpl(context, ADVERTISING_INFO_PREFERENCES);
    }

    public AdvertisingInfo getAdvertisingInfo() {
        AdvertisingInfo infoToReturn = getInfoFromPreferences();
        if (isInfoValid(infoToReturn)) {
            Fabric.getLogger().d(Fabric.TAG, "Using AdvertisingInfo from Preference Store");
            refreshInfoIfNeededAsync(infoToReturn);
            return infoToReturn;
        }
        infoToReturn = getAdvertisingInfoFromStrategies();
        storeInfoToPreferences(infoToReturn);
        return infoToReturn;
    }

    private void refreshInfoIfNeededAsync(final AdvertisingInfo advertisingInfo) {
        new Thread(new BackgroundPriorityRunnable() {
            public void onRun() {
                AdvertisingInfo infoToStore = AdvertisingInfoProvider.this.getAdvertisingInfoFromStrategies();
                if (!advertisingInfo.equals(infoToStore)) {
                    Fabric.getLogger().d(Fabric.TAG, "Asychronously getting Advertising Info and storing it to preferences");
                    AdvertisingInfoProvider.this.storeInfoToPreferences(infoToStore);
                }
            }
        }).start();
    }

    @SuppressLint({"CommitPrefEdits"})
    private void storeInfoToPreferences(AdvertisingInfo infoToReturn) {
        if (isInfoValid(infoToReturn)) {
            this.preferenceStore.save(this.preferenceStore.edit().putString(PREFKEY_ADVERTISING_ID, infoToReturn.advertisingId).putBoolean(PREFKEY_LIMIT_AD_TRACKING, infoToReturn.limitAdTrackingEnabled));
        } else {
            this.preferenceStore.save(this.preferenceStore.edit().remove(PREFKEY_ADVERTISING_ID).remove(PREFKEY_LIMIT_AD_TRACKING));
        }
    }

    protected AdvertisingInfo getInfoFromPreferences() {
        return new AdvertisingInfo(this.preferenceStore.get().getString(PREFKEY_ADVERTISING_ID, ""), this.preferenceStore.get().getBoolean(PREFKEY_LIMIT_AD_TRACKING, false));
    }

    public AdvertisingInfoStrategy getReflectionStrategy() {
        return new AdvertisingInfoReflectionStrategy(this.context);
    }

    public AdvertisingInfoStrategy getServiceStrategy() {
        return new AdvertisingInfoServiceStrategy(this.context);
    }

    private boolean isInfoValid(AdvertisingInfo advertisingInfo) {
        return (advertisingInfo == null || TextUtils.isEmpty(advertisingInfo.advertisingId)) ? false : true;
    }

    private AdvertisingInfo getAdvertisingInfoFromStrategies() {
        AdvertisingInfo infoToReturn = getReflectionStrategy().getAdvertisingInfo();
        if (isInfoValid(infoToReturn)) {
            Fabric.getLogger().d(Fabric.TAG, "Using AdvertisingInfo from Reflection Provider");
        } else {
            infoToReturn = getServiceStrategy().getAdvertisingInfo();
            if (isInfoValid(infoToReturn)) {
                Fabric.getLogger().d(Fabric.TAG, "Using AdvertisingInfo from Service Provider");
            } else {
                Fabric.getLogger().d(Fabric.TAG, "AdvertisingInfo not present");
            }
        }
        return infoToReturn;
    }
}
