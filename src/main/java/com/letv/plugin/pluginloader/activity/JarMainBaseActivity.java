package com.letv.plugin.pluginloader.activity;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import com.letv.plugin.pluginloader.loader.JarLoader;
import com.letv.plugin.pluginloader.loader.JarResOverrideInterface;
import com.letv.plugin.pluginloader.loader.JarResources;

public class JarMainBaseActivity extends Activity implements JarResOverrideInterface {
    private static final String TAG = "JarMainBaseActivity";
    private AssetManager assetManager;
    private JarResources jarResources;
    private Resources resources;
    private Theme theme;

    public AssetManager getAssets() {
        return this.assetManager == null ? super.getAssets() : this.assetManager;
    }

    public Resources getResources() {
        return this.resources == null ? super.getResources() : this.resources;
    }

    public Theme getTheme() {
        return this.theme == null ? super.getTheme() : this.theme;
    }

    public JarResources getOverrideResources() {
        return this.jarResources;
    }

    public void setOverrideResources(JarResources myres) {
        if (myres == null) {
            this.jarResources = null;
            this.resources = null;
            this.assetManager = null;
            this.theme = null;
            return;
        }
        this.jarResources = myres;
        this.resources = myres.getResources();
        this.assetManager = myres.getAssets();
        Theme t = myres.getResources().newTheme();
        t.setTo(getTheme());
        this.theme = t;
    }

    public void setResourcePath(boolean isPlugin, String jarname, String jar_packagename) {
        if (isPlugin) {
            setOverrideResources(JarResources.getResourceByCl(JarLoader.getJarClassLoader(this, jarname, jar_packagename), this));
        } else {
            setOverrideResources(null);
        }
    }
}
