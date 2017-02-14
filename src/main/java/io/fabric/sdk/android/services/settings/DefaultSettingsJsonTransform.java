package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import org.json.JSONException;
import org.json.JSONObject;

class DefaultSettingsJsonTransform implements SettingsJsonTransform {
    DefaultSettingsJsonTransform() {
    }

    public SettingsData buildFromJson(CurrentTimeProvider currentTimeProvider, JSONObject json) throws JSONException {
        int settingsVersion = json.optInt(SettingsJsonConstants.SETTINGS_VERSION, 0);
        int cacheDuration = json.optInt(SettingsJsonConstants.CACHE_DURATION_KEY, 3600);
        return new SettingsData(getExpiresAtFrom(currentTimeProvider, (long) cacheDuration, json), buildAppDataFrom(json.getJSONObject(SettingsJsonConstants.APP_KEY)), buildSessionDataFrom(json.getJSONObject(SettingsJsonConstants.SESSION_KEY)), buildPromptDataFrom(json.getJSONObject(SettingsJsonConstants.PROMPT_KEY)), buildFeaturesSessionDataFrom(json.getJSONObject(SettingsJsonConstants.FEATURES_KEY)), buildAnalyticsSessionDataFrom(json.getJSONObject(SettingsJsonConstants.ANALYTICS_KEY)), buildBetaSettingsDataFrom(json.getJSONObject(SettingsJsonConstants.BETA_KEY)), settingsVersion, cacheDuration);
    }

    public JSONObject toJson(SettingsData settingsData) throws JSONException {
        return new JSONObject().put(SettingsJsonConstants.EXPIRES_AT_KEY, settingsData.expiresAtMillis).put(SettingsJsonConstants.CACHE_DURATION_KEY, settingsData.cacheDuration).put(SettingsJsonConstants.SETTINGS_VERSION, settingsData.settingsVersion).put(SettingsJsonConstants.FEATURES_KEY, toFeaturesJson(settingsData.featuresData)).put(SettingsJsonConstants.ANALYTICS_KEY, toAnalyticsJson(settingsData.analyticsSettingsData)).put(SettingsJsonConstants.BETA_KEY, toBetaJson(settingsData.betaSettingsData)).put(SettingsJsonConstants.APP_KEY, toAppJson(settingsData.appData)).put(SettingsJsonConstants.SESSION_KEY, toSessionJson(settingsData.sessionData)).put(SettingsJsonConstants.PROMPT_KEY, toPromptJson(settingsData.promptData));
    }

    private AppSettingsData buildAppDataFrom(JSONObject json) throws JSONException {
        String identifier = json.getString(SettingsJsonConstants.APP_IDENTIFIER_KEY);
        String status = json.getString("status");
        String url = json.getString("url");
        String reportsUrl = json.getString(SettingsJsonConstants.APP_REPORTS_URL_KEY);
        boolean updateRequired = json.optBoolean(SettingsJsonConstants.APP_UPDATE_REQUIRED_KEY, false);
        AppIconSettingsData icon = null;
        if (json.has(SettingsJsonConstants.APP_ICON_KEY) && json.getJSONObject(SettingsJsonConstants.APP_ICON_KEY).has(SettingsJsonConstants.ICON_HASH_KEY)) {
            icon = buildIconDataFrom(json.getJSONObject(SettingsJsonConstants.APP_ICON_KEY));
        }
        return new AppSettingsData(identifier, status, url, reportsUrl, updateRequired, icon);
    }

    private AppIconSettingsData buildIconDataFrom(JSONObject iconJson) throws JSONException {
        return new AppIconSettingsData(iconJson.getString(SettingsJsonConstants.ICON_HASH_KEY), iconJson.getInt(SettingsJsonConstants.ICON_WIDTH_KEY), iconJson.getInt(SettingsJsonConstants.ICON_HEIGHT_KEY));
    }

    private FeaturesSettingsData buildFeaturesSessionDataFrom(JSONObject json) {
        return new FeaturesSettingsData(json.optBoolean(SettingsJsonConstants.FEATURES_PROMPT_ENABLED_KEY, false), json.optBoolean(SettingsJsonConstants.FEATURES_COLLECT_LOGGED_EXCEPTIONS_KEY, true), json.optBoolean(SettingsJsonConstants.FEATURES_COLLECT_REPORTS_KEY, true), json.optBoolean(SettingsJsonConstants.FEATURES_COLLECT_ANALYTICS_KEY, false));
    }

    private AnalyticsSettingsData buildAnalyticsSessionDataFrom(JSONObject json) {
        return new AnalyticsSettingsData(json.optString("url", SettingsJsonConstants.ANALYTICS_URL_DEFAULT), json.optInt(SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_KEY, 600), json.optInt(SettingsJsonConstants.ANALYTICS_MAX_BYTE_SIZE_PER_FILE_KEY, 8000), json.optInt(SettingsJsonConstants.ANALYTICS_MAX_FILE_COUNT_PER_SEND_KEY, 1), json.optInt(SettingsJsonConstants.ANALYTICS_MAX_PENDING_SEND_FILE_COUNT_KEY, 100), json.optBoolean(SettingsJsonConstants.ANALYTICS_TRACK_CUSTOM_EVENTS_KEY, true), json.optBoolean(SettingsJsonConstants.ANALYTICS_TRACK_PREDEFINED_EVENTS_KEY, true), json.optInt(SettingsJsonConstants.ANALYTICS_SAMPLING_RATE_KEY, 1), json.optBoolean(SettingsJsonConstants.ANALYTICS_FLUSH_ON_BACKGROUND_KEY, true));
    }

    private SessionSettingsData buildSessionDataFrom(JSONObject json) throws JSONException {
        return new SessionSettingsData(json.optInt(SettingsJsonConstants.SETTINGS_LOG_BUFFER_SIZE_KEY, SettingsJsonConstants.SETTINGS_LOG_BUFFER_SIZE_DEFAULT), json.optInt(SettingsJsonConstants.SETTINGS_MAX_CHAINED_EXCEPTION_DEPTH_KEY, 8), json.optInt(SettingsJsonConstants.SETTINGS_MAX_CUSTOM_EXCEPTION_EVENTS_KEY, 64), json.optInt(SettingsJsonConstants.SETTINGS_MAX_CUSTOM_KEY_VALUE_PAIRS_KEY, 64), json.optInt(SettingsJsonConstants.SETTINGS_IDENTIFIER_MASK_KEY, 255), json.optBoolean(SettingsJsonConstants.SETTINGS_SEND_SESSION_WITHOUT_CRASH_KEY, false));
    }

    private PromptSettingsData buildPromptDataFrom(JSONObject json) throws JSONException {
        return new PromptSettingsData(json.optString("title", SettingsJsonConstants.PROMPT_TITLE_DEFAULT), json.optString("message", SettingsJsonConstants.PROMPT_MESSAGE_DEFAULT), json.optString(SettingsJsonConstants.PROMPT_SEND_BUTTON_TITLE_KEY, SettingsJsonConstants.PROMPT_SEND_BUTTON_TITLE_DEFAULT), json.optBoolean(SettingsJsonConstants.PROMPT_SHOW_CANCEL_BUTTON_KEY, true), json.optString(SettingsJsonConstants.PROMPT_CANCEL_BUTTON_TITLE_KEY, SettingsJsonConstants.PROMPT_CANCEL_BUTTON_TITLE_DEFAULT), json.optBoolean(SettingsJsonConstants.PROMPT_SHOW_ALWAYS_SEND_BUTTON_KEY, true), json.optString(SettingsJsonConstants.PROMPT_ALWAYS_SEND_BUTTON_TITLE_KEY, SettingsJsonConstants.PROMPT_ALWAYS_SEND_BUTTON_TITLE_DEFAULT));
    }

    private BetaSettingsData buildBetaSettingsDataFrom(JSONObject json) throws JSONException {
        return new BetaSettingsData(json.optString(SettingsJsonConstants.BETA_UPDATE_ENDPOINT, SettingsJsonConstants.BETA_UPDATE_ENDPOINT_DEFAULT), json.optInt(SettingsJsonConstants.BETA_UPDATE_SUSPEND_DURATION, 3600));
    }

    private long getExpiresAtFrom(CurrentTimeProvider currentTimeProvider, long cacheDurationSeconds, JSONObject json) throws JSONException {
        if (json.has(SettingsJsonConstants.EXPIRES_AT_KEY)) {
            return json.getLong(SettingsJsonConstants.EXPIRES_AT_KEY);
        }
        return currentTimeProvider.getCurrentTimeMillis() + (1000 * cacheDurationSeconds);
    }

    private JSONObject toAppJson(AppSettingsData appData) throws JSONException {
        JSONObject json = new JSONObject().put(SettingsJsonConstants.APP_IDENTIFIER_KEY, appData.identifier).put("status", appData.status).put("url", appData.url).put(SettingsJsonConstants.APP_REPORTS_URL_KEY, appData.reportsUrl).put(SettingsJsonConstants.APP_UPDATE_REQUIRED_KEY, appData.updateRequired);
        if (appData.icon != null) {
            json.put(SettingsJsonConstants.APP_ICON_KEY, toIconJson(appData.icon));
        }
        return json;
    }

    private JSONObject toIconJson(AppIconSettingsData icon) throws JSONException {
        return new JSONObject().put(SettingsJsonConstants.ICON_HASH_KEY, icon.hash).put(SettingsJsonConstants.ICON_WIDTH_KEY, icon.width).put(SettingsJsonConstants.ICON_HEIGHT_KEY, icon.height);
    }

    private JSONObject toFeaturesJson(FeaturesSettingsData features) throws JSONException {
        return new JSONObject().put(SettingsJsonConstants.FEATURES_COLLECT_LOGGED_EXCEPTIONS_KEY, features.collectLoggedException).put(SettingsJsonConstants.FEATURES_COLLECT_REPORTS_KEY, features.collectReports).put(SettingsJsonConstants.FEATURES_COLLECT_ANALYTICS_KEY, features.collectAnalytics);
    }

    private JSONObject toSessionJson(SessionSettingsData data) throws JSONException {
        return new JSONObject().put(SettingsJsonConstants.SETTINGS_LOG_BUFFER_SIZE_KEY, data.logBufferSize).put(SettingsJsonConstants.SETTINGS_MAX_CHAINED_EXCEPTION_DEPTH_KEY, data.maxChainedExceptionDepth).put(SettingsJsonConstants.SETTINGS_MAX_CUSTOM_EXCEPTION_EVENTS_KEY, data.maxCustomExceptionEvents).put(SettingsJsonConstants.SETTINGS_MAX_CUSTOM_KEY_VALUE_PAIRS_KEY, data.maxCustomKeyValuePairs).put(SettingsJsonConstants.SETTINGS_IDENTIFIER_MASK_KEY, data.identifierMask).put(SettingsJsonConstants.SETTINGS_SEND_SESSION_WITHOUT_CRASH_KEY, data.sendSessionWithoutCrash);
    }

    private JSONObject toAnalyticsJson(AnalyticsSettingsData data) throws JSONException {
        return new JSONObject().put("url", data.analyticsURL).put(SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_KEY, data.flushIntervalSeconds).put(SettingsJsonConstants.ANALYTICS_MAX_BYTE_SIZE_PER_FILE_KEY, data.maxByteSizePerFile).put(SettingsJsonConstants.ANALYTICS_MAX_FILE_COUNT_PER_SEND_KEY, data.maxFileCountPerSend).put(SettingsJsonConstants.ANALYTICS_MAX_PENDING_SEND_FILE_COUNT_KEY, data.maxPendingSendFileCount);
    }

    private JSONObject toBetaJson(BetaSettingsData data) throws JSONException {
        return new JSONObject().put(SettingsJsonConstants.BETA_UPDATE_ENDPOINT, data.updateUrl).put(SettingsJsonConstants.BETA_UPDATE_SUSPEND_DURATION, data.updateSuspendDurationSeconds);
    }

    private JSONObject toPromptJson(PromptSettingsData data) throws JSONException {
        return new JSONObject().put("title", data.title).put("message", data.message).put(SettingsJsonConstants.PROMPT_SEND_BUTTON_TITLE_KEY, data.sendButtonTitle).put(SettingsJsonConstants.PROMPT_SHOW_CANCEL_BUTTON_KEY, data.showCancelButton).put(SettingsJsonConstants.PROMPT_CANCEL_BUTTON_TITLE_KEY, data.cancelButtonTitle).put(SettingsJsonConstants.PROMPT_SHOW_ALWAYS_SEND_BUTTON_KEY, data.showAlwaysSendButton).put(SettingsJsonConstants.PROMPT_ALWAYS_SEND_BUTTON_TITLE_KEY, data.alwaysSendButtonTitle);
    }
}
