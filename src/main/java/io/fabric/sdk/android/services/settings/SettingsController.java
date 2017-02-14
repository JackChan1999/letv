package io.fabric.sdk.android.services.settings;

public interface SettingsController {
    SettingsData loadSettingsData();

    SettingsData loadSettingsData(SettingsCacheBehavior settingsCacheBehavior);
}
