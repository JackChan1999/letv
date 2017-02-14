package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.KitInfo;
import java.util.Collection;

public class AppRequestData {
    public final String apiKey;
    public final String appId;
    public final String buildVersion;
    public final String builtSdkVersion;
    public final String displayVersion;
    public final IconRequest icon;
    public final String instanceIdentifier;
    public final String minSdkVersion;
    public final String name;
    public final Collection<KitInfo> sdkKits;
    public final int source;

    public AppRequestData(String apiKey, String appId, String displayVersion, String buildVersion, String instanceIdentifier, String name, int source, String minSdkVersion, String builtSdkVersion, IconRequest icon, Collection<KitInfo> sdkKits) {
        this.apiKey = apiKey;
        this.appId = appId;
        this.displayVersion = displayVersion;
        this.buildVersion = buildVersion;
        this.instanceIdentifier = instanceIdentifier;
        this.name = name;
        this.source = source;
        this.minSdkVersion = minSdkVersion;
        this.builtSdkVersion = builtSdkVersion;
        this.icon = icon;
        this.sdkKits = sdkKits;
    }
}
