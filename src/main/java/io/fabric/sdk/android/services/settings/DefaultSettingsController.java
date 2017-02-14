package io.fabric.sdk.android.services.settings;

import android.annotation.SuppressLint;
import android.content.SharedPreferences.Editor;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import org.json.JSONException;
import org.json.JSONObject;

class DefaultSettingsController implements SettingsController {
    private static final String LOAD_ERROR_MESSAGE = "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.";
    private static final String PREFS_BUILD_INSTANCE_IDENTIFIER = "existing_instance_identifier";
    private final CachedSettingsIo cachedSettingsIo;
    private final CurrentTimeProvider currentTimeProvider;
    private final Kit kit;
    private final PreferenceStore preferenceStore = new PreferenceStoreImpl(this.kit);
    private final SettingsJsonTransform settingsJsonTransform;
    private final SettingsRequest settingsRequest;
    private final SettingsSpiCall settingsSpiCall;

    public DefaultSettingsController(Kit kit, SettingsRequest settingsRequest, CurrentTimeProvider currentTimeProvider, SettingsJsonTransform settingsJsonTransform, CachedSettingsIo cachedSettingsIo, SettingsSpiCall settingsSpiCall) {
        this.kit = kit;
        this.settingsRequest = settingsRequest;
        this.currentTimeProvider = currentTimeProvider;
        this.settingsJsonTransform = settingsJsonTransform;
        this.cachedSettingsIo = cachedSettingsIo;
        this.settingsSpiCall = settingsSpiCall;
    }

    public SettingsData loadSettingsData() {
        return loadSettingsData(SettingsCacheBehavior.USE_CACHE);
    }

    public SettingsData loadSettingsData(SettingsCacheBehavior cacheBehavior) {
        SettingsData toReturn = null;
        try {
            if (!(Fabric.isDebuggable() || buildInstanceIdentifierChanged())) {
                toReturn = getCachedSettingsData(cacheBehavior);
            }
            if (toReturn == null) {
                JSONObject settingsJson = this.settingsSpiCall.invoke(this.settingsRequest);
                if (settingsJson != null) {
                    toReturn = this.settingsJsonTransform.buildFromJson(this.currentTimeProvider, settingsJson);
                    this.cachedSettingsIo.writeCachedSettings(toReturn.expiresAtMillis, settingsJson);
                    logSettings(settingsJson, "Loaded settings: ");
                    setStoredBuildInstanceIdentifier(getBuildInstanceIdentifierFromContext());
                }
            }
            if (toReturn == null) {
                toReturn = getCachedSettingsData(SettingsCacheBehavior.IGNORE_CACHE_EXPIRATION);
            }
        } catch (Exception e) {
            Fabric.getLogger().e(Fabric.TAG, LOAD_ERROR_MESSAGE, e);
        }
        return toReturn;
    }

    private SettingsData getCachedSettingsData(SettingsCacheBehavior cacheBehavior) {
        try {
            if (SettingsCacheBehavior.SKIP_CACHE_LOOKUP.equals(cacheBehavior)) {
                return null;
            }
            JSONObject settingsJson = this.cachedSettingsIo.readCachedSettings();
            if (settingsJson != null) {
                SettingsData settingsData = this.settingsJsonTransform.buildFromJson(this.currentTimeProvider, settingsJson);
                if (settingsData != null) {
                    logSettings(settingsJson, "Loaded cached settings: ");
                    long currentTimeMillis = this.currentTimeProvider.getCurrentTimeMillis();
                    if (SettingsCacheBehavior.IGNORE_CACHE_EXPIRATION.equals(cacheBehavior) || !settingsData.isExpired(currentTimeMillis)) {
                        SettingsData toReturn = settingsData;
                        Fabric.getLogger().d(Fabric.TAG, "Returning cached settings.");
                        return toReturn;
                    }
                    Fabric.getLogger().d(Fabric.TAG, "Cached settings have expired.");
                    return null;
                }
                Fabric.getLogger().e(Fabric.TAG, "Failed to transform cached settings data.", null);
                return null;
            }
            Fabric.getLogger().d(Fabric.TAG, "No cached settings data found.");
            return null;
        } catch (Exception e) {
            Fabric.getLogger().e(Fabric.TAG, "Failed to get cached settings", e);
            return null;
        }
    }

    private void logSettings(JSONObject json, String message) throws JSONException {
        Fabric.getLogger().d(Fabric.TAG, message + json.toString());
    }

    String getBuildInstanceIdentifierFromContext() {
        return CommonUtils.createInstanceIdFrom(CommonUtils.resolveBuildId(this.kit.getContext()));
    }

    String getStoredBuildInstanceIdentifier() {
        return this.preferenceStore.get().getString(PREFS_BUILD_INSTANCE_IDENTIFIER, "");
    }

    @SuppressLint({"CommitPrefEdits"})
    boolean setStoredBuildInstanceIdentifier(String buildInstanceIdentifier) {
        Editor editor = this.preferenceStore.edit();
        editor.putString(PREFS_BUILD_INSTANCE_IDENTIFIER, buildInstanceIdentifier);
        return this.preferenceStore.save(editor);
    }

    boolean buildInstanceIdentifierChanged() {
        return !getStoredBuildInstanceIdentifier().equals(getBuildInstanceIdentifierFromContext());
    }
}
