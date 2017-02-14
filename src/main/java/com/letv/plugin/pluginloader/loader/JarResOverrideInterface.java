package com.letv.plugin.pluginloader.loader;

public interface JarResOverrideInterface {
    JarResources getOverrideResources();

    void setOverrideResources(JarResources jarResources);

    void setResourcePath(boolean z, String str, String str2);
}
