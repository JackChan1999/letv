package io.fabric.sdk.android.services.settings;

import org.json.JSONObject;

public interface CachedSettingsIo {
    JSONObject readCachedSettings();

    void writeCachedSettings(long j, JSONObject jSONObject);
}
