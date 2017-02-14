package com.letv.plugin.pluginloader.loader;

import dalvik.system.DexClassLoader;

public class JarClassLoader extends DexClassLoader {
    String jarpath;
    String packagename;

    public JarClassLoader(String packagename, String dexPath, String optimizedDirectory, String libraryPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, libraryPath, parent);
        this.packagename = packagename;
        this.jarpath = dexPath;
    }
}
