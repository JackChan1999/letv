package io.fabric.sdk.android.services.settings;

class AppIconSettingsData {
    public final String hash;
    public final int height;
    public final int width;

    public AppIconSettingsData(String hash, int width, int height) {
        this.hash = hash;
        this.width = width;
        this.height = height;
    }
}
