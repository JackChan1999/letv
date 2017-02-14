package com.letv.plugin.pluginloader.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import com.letv.plugin.pluginloader.loader.JarLoader;
import com.letv.plugin.pluginloader.loader.JarResOverrideInterface;
import com.letv.plugin.pluginloader.loader.JarResources;

public class JarBaseProxyFragment extends Fragment implements JarResOverrideInterface {
    private static final String TAG = "JarBaseProxyFragment";
    private JarResources jarResources;
    private Activity mActivity;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    public JarResources getOverrideResources() {
        return this.jarResources;
    }

    public void setOverrideResources(JarResources myres) {
        this.jarResources = myres;
    }

    public void setResourcePath(boolean isPlugin, String jarname, String jar_packagename) {
        if (isPlugin) {
            setOverrideResources(JarResources.getResourceByCl(JarLoader.getJarClassLoader(getActivity(), jarname, jar_packagename), getActivity()));
        } else {
            setOverrideResources(null);
        }
    }
}
