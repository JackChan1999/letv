package io.fabric.sdk.android.services.settings;

import org.json.JSONObject;

public interface SettingsSpiCall {
    JSONObject invoke(SettingsRequest settingsRequest);
}
