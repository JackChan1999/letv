package io.fabric.sdk.android;

public class KitInfo {
    private final String buildType;
    private final String identifier;
    private final String version;

    public KitInfo(String identifier, String version, String buildType) {
        this.identifier = identifier;
        this.version = version;
        this.buildType = buildType;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getVersion() {
        return this.version;
    }

    public String getBuildType() {
        return this.buildType;
    }
}
