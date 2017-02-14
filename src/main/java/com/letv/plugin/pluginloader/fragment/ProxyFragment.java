package com.letv.plugin.pluginloader.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.letv.plugin.pluginloader.application.JarApplication;
import com.letv.plugin.pluginloader.loader.JarLoader;
import com.letv.plugin.pluginloader.loader.JarResources;
import com.letv.plugin.pluginloader.util.JLog;
import java.lang.reflect.Field;

public class ProxyFragment extends JarBaseProxyFragment {
    private static final String TAG = "ProxyFragment";
    private String mClass;
    private String mJarPath;
    private String mPackageName;
    protected FragmentPlugin mRemoteFragment;

    protected void createRemoteFragment(Context context) {
        this.mPackageName = getArguments().getString("extra.packagename");
        this.mClass = getArguments().getString("extra.class");
        this.mJarPath = getArguments().getString("extra.jarname");
        Log.d(TAG, "mClass=" + this.mClass + " mPackageName=" + this.mPackageName + " mJarPath=" + this.mJarPath);
        Class<?> localClass = JarLoader.loadClass(context, this.mJarPath, this.mPackageName, this.mClass);
        if (localClass != null) {
            this.mRemoteFragment = (FragmentPlugin) JarLoader.newInstance(localClass, new Class[0], new Object[0]);
        }
    }

    public void onAttach(Activity activity) {
        attachTargetFragment();
        super.onAttach(activity);
        Field mActivityField = null;
        try {
            mActivityField = Fragment.class.getDeclaredField("mActivity");
            Log.d(TAG, "onAttach mActivityField=" + mActivityField);
            mActivityField.setAccessible(true);
            mActivityField.set(this.mRemoteFragment, activity);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onAttach mActivityField e=" + e);
        }
        if (mActivityField == null) {
            try {
                Field mHostField = Fragment.class.getDeclaredField("mHost");
                mHostField.setAccessible(true);
                mHostField.set(this.mRemoteFragment, mHostField.get(this));
            } catch (Exception e2) {
                e2.printStackTrace();
                Log.d(TAG, "onAttach mHostField e=" + e2);
            }
        }
        updateConfiguration();
    }

    @TargetApi(14)
    public void attachTargetFragment() {
        try {
            this.mRemoteFragment.onAttach(getActivity());
            this.mRemoteFragment.setProxy(this, this.mJarPath, this.mPackageName);
        } catch (Exception e) {
            JLog.i(TAG, "!!!ProxyFragment e is " + e.getMessage());
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (this.mRemoteFragment != null) {
            this.mRemoteFragment.onCreate(savedInstanceState);
        }
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.mRemoteFragment != null) {
            return this.mRemoteFragment.onCreateView(inflater, container, savedInstanceState);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (this.mRemoteFragment != null) {
            this.mRemoteFragment.onViewCreated(view, savedInstanceState);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        if (this.mRemoteFragment != null) {
            this.mRemoteFragment.onActivityCreated(savedInstanceState);
        }
        super.onActivityCreated(savedInstanceState);
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        if (this.mRemoteFragment != null) {
            this.mRemoteFragment.onViewStateRestored(savedInstanceState);
        }
        super.onViewStateRestored(savedInstanceState);
    }

    public void onStart() {
        if (this.mRemoteFragment != null) {
            this.mRemoteFragment.onStart();
        }
        super.onStart();
    }

    public void onResume() {
        if (this.mRemoteFragment != null) {
            this.mRemoteFragment.onResume();
        }
        super.onResume();
    }

    public void onSaveInstanceState(Bundle outState) {
        if (this.mRemoteFragment != null) {
            this.mRemoteFragment.onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    private void updateConfiguration() {
        if (JarApplication.getInstance() != null && JarApplication.getInstance().getResources() != null) {
            Resources superRes = JarApplication.getInstance().getResources();
            Configuration configuration = superRes.getConfiguration();
            DisplayMetrics displayMetrics = superRes.getDisplayMetrics();
            JarResources jarResources = getOverrideResources();
            if (jarResources != null) {
                Resources resources = jarResources.getResources();
                if (resources != null && configuration != null && displayMetrics != null) {
                    resources.updateConfiguration(configuration, displayMetrics);
                }
            }
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        updateConfiguration();
        if (this.mRemoteFragment != null) {
            this.mRemoteFragment.onConfigurationChanged(newConfig);
        }
        super.onConfigurationChanged(newConfig);
    }

    public void onPause() {
        if (this.mRemoteFragment != null) {
            this.mRemoteFragment.onPause();
        }
        super.onPause();
    }

    public void onStop() {
        if (this.mRemoteFragment != null) {
            this.mRemoteFragment.onStop();
        }
        super.onStop();
    }

    public void onDestroyView() {
        if (this.mRemoteFragment != null) {
            this.mRemoteFragment.onDestroyView();
        }
        super.onDestroyView();
    }

    public void onDestroy() {
        if (this.mRemoteFragment != null) {
            this.mRemoteFragment.onDestroy();
        }
        super.onDestroy();
    }

    public void onDetach() {
        if (this.mRemoteFragment != null) {
            this.mRemoteFragment.onDetach();
        }
        super.onDetach();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.mRemoteFragment != null) {
            this.mRemoteFragment.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
