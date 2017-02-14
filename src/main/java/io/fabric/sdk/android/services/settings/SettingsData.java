package io.fabric.sdk.android.services.settings;

public class SettingsData {
    public final AnalyticsSettingsData analyticsSettingsData;
    public final AppSettingsData appData;
    public final BetaSettingsData betaSettingsData;
    public final int cacheDuration;
    public final long expiresAtMillis;
    public final FeaturesSettingsData featuresData;
    public final PromptSettingsData promptData;
    public final SessionSettingsData sessionData;
    public final int settingsVersion;

    public SettingsData(long expiresAtMillis, AppSettingsData appData, SessionSettingsData sessionData, PromptSettingsData promptData, FeaturesSettingsData featuresData, AnalyticsSettingsData analyticsSettingsData, BetaSettingsData betaSettingsData, int settingsVersion, int cacheDuration) {
        this.expiresAtMillis = expiresAtMillis;
        this.appData = appData;
        this.sessionData = sessionData;
        this.promptData = promptData;
        this.featuresData = featuresData;
        this.settingsVersion = settingsVersion;
        this.cacheDuration = cacheDuration;
        this.analyticsSettingsData = analyticsSettingsData;
        this.betaSettingsData = betaSettingsData;
    }

    public boolean isExpired(long currentTimeMillis) {
        return this.expiresAtMillis < currentTimeMillis;
    }
}
