package io.fabric.sdk.android.services.common;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.cache.MemoryValueCache;
import io.fabric.sdk.android.services.cache.ValueLoader;

public class InstallerPackageNameProvider {
    private static final String NO_INSTALLER_PACKAGE_NAME = "";
    private final MemoryValueCache<String> installerPackageNameCache = new MemoryValueCache();
    private final ValueLoader<String> installerPackageNameLoader = new ValueLoader<String>() {
        public String load(Context context) throws Exception {
            String installerPackageName = context.getPackageManager().getInstallerPackageName(context.getPackageName());
            return installerPackageName == null ? "" : installerPackageName;
        }
    };

    public String getInstallerPackageName(Context appContext) {
        try {
            String name = (String) this.installerPackageNameCache.get(appContext, this.installerPackageNameLoader);
            if ("".equals(name)) {
                return null;
            }
            return name;
        } catch (Exception e) {
            Fabric.getLogger().e(Fabric.TAG, "Failed to determine installer package name", e);
            return null;
        }
    }
}
