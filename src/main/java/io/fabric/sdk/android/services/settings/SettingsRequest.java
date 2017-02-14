package io.fabric.sdk.android.services.settings;

public class SettingsRequest {
    public final String advertisingId;
    public final String androidId;
    public final String apiKey;
    public final String buildVersion;
    public final String deviceModel;
    public final String displayVersion;
    public final String iconHash;
    public final String installationId;
    public final String instanceId;
    public final String osBuildVersion;
    public final String osDisplayVersion;
    public final int source;

    public SettingsRequest(String apiKey, String deviceModel, String osBuildVersion, String osDisplayVersion, String advertisingId, String installationId, String androidId, String instanceId, String displayVersion, String buildVersion, int source, String iconHash) {
        this.apiKey = apiKey;
        this.deviceModel = deviceModel;
        this.osBuildVersion = osBuildVersion;
        this.osDisplayVersion = osDisplayVersion;
        this.advertisingId = advertisingId;
        this.installationId = installationId;
        this.androidId = androidId;
        this.instanceId = instanceId;
        this.displayVersion = displayVersion;
        this.buildVersion = buildVersion;
        this.source = source;
        this.iconHash = iconHash;
    }
}
