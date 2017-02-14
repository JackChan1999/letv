package com.letv.plugin.pluginloader.activity;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.support.v4.app.FragmentActivity;
import com.letv.plugin.pluginloader.loader.JarClassLoader;
import com.letv.plugin.pluginloader.loader.JarLoader;
import com.letv.plugin.pluginloader.loader.JarResOverrideInterface;
import com.letv.plugin.pluginloader.loader.JarResources;
import com.letv.plugin.pluginloader.util.JLog;

public class JarMainBFragmentActivity extends FragmentActivity implements JarResOverrideInterface {
    private static final String TAG = "JarMainBFragmentActivity";
    private AssetManager assetManager;
    private JarResources myResources;
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
        return this.myResources;
    }

    public void setOverrideResources(JarResources myres) {
        JLog.i("clf", "setOverrideResources...myres=" + myres);
        if (myres == null) {
            this.myResources = null;
            this.resources = null;
            this.assetManager = null;
            this.theme = null;
            return;
        }
        this.myResources = myres;
        JLog.i("clf", "setOverrideResources...this.myResources=" + this.myResources);
        this.resources = myres.getResources();
        JLog.i("clf", "setOverrideResources...this.resources=" + this.resources);
        this.assetManager = myres.getAssets();
        JLog.i("clf", "setOverrideResources...this.assetManager=" + this.assetManager);
        Theme t = myres.getResources().newTheme();
        JLog.i("clf", "setOverrideResources...t=" + t);
        t.setTo(getTheme());
        this.theme = t;
        JLog.i("clf", "setOverrideResources...this.theme=" + this.theme);
    }

    public void setResourcePath(boolean isPlugin, String jarname, String jar_packagename) {
        JLog.i("clf", "setResourcePath...isPlugin=" + isPlugin + ",jarname=" + jarname + ",jar_packagename=" + jar_packagename);
        if (isPlugin) {
            JarClassLoader jcl = JarLoader.getJarClassLoader(this, jarname, jar_packagename);
            JLog.i("clf", "setResourcePath...jcl=" + jcl);
            JarResources jres = JarResources.getResourceByCl(jcl, this);
            JLog.i("clf", "setResourcePath...jres=" + jres);
            setOverrideResources(jres);
            return;
        }
        setOverrideResources(null);
    }
}
