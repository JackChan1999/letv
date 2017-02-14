package com.letv.plugin.pluginloader.activity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.letv.plugin.pluginloader.R;
import com.letv.plugin.pluginloader.application.JarApplication;
import com.letv.plugin.pluginloader.loader.JarLoader;
import com.letv.plugin.pluginloader.util.JLog;
import com.letv.plugin.pluginloader.util.JarUtil;

public class ProxyFragmentActivity extends JarMainBFragmentActivity {
    public static final String EXTRA_CLASS = "extra.class";
    public static final String EXTRA_JARNAME = "extra.jarname";
    public static final String EXTRA_PACKAGENAME = "extra.packagename";
    private static final String TAG = "ProxyFragmentActivity";
    private String jar_packagename;
    private String jarname;
    private String mClass;
    private String mDexPath;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            JLog.i("clf", "config...action=" + action);
            if (action.equals("android.intent.action.LOCALE_CHANGED")) {
                ProxyFragmentActivity.this.updateLocalConfig();
            }
        }
    };
    private JarBaseFragmentActivity mRemoteActivity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.plugin_root);
        init();
        if (this.mRemoteActivity != null) {
            this.mRemoteActivity.onCreate(savedInstanceState);
        }
    }

    private void init() {
        this.jarname = getIntent().getStringExtra("extra.jarname");
        this.jar_packagename = getIntent().getStringExtra("extra.packagename");
        this.mClass = getIntent().getStringExtra("extra.class");
        this.mDexPath = JarUtil.getJarInFolderName(this, this.jarname);
        if (this.mClass == null) {
            initTargetActivity();
        } else {
            initTargetActivity(this.mClass);
        }
        registerReceiver();
    }

    protected void initTargetActivity() {
        PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(this.mDexPath, 1);
        if (packageInfo.activities != null && packageInfo.activities.length > 0) {
            this.mClass = packageInfo.activities[0].name;
            initTargetActivity(this.mClass);
        }
    }

    @TargetApi(14)
    protected void initTargetActivity(String className) {
        try {
            setRemoteActivity(JarLoader.newInstance(JarLoader.loadClass(this, this.jarname, this.jar_packagename, className), new Class[0], new Object[0]));
            this.mRemoteActivity.setProxy(this, this.jarname, this.jar_packagename);
        } catch (Exception e) {
            JLog.i("clf", "initTargetActivity..e=" + e.getMessage());
        }
    }

    protected void setRemoteActivity(Object activity) {
        try {
            this.mRemoteActivity = (JarBaseFragmentActivity) activity;
        } catch (ClassCastException e) {
            JLog.i("clf", "!!!setRemoteActivity e=" + e.getMessage());
        }
    }

    public JarBaseFragmentActivity getRemoteActivity() {
        return this.mRemoteActivity;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.mRemoteActivity != null) {
            this.mRemoteActivity.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onStart() {
        super.onStart();
        if (this.mRemoteActivity != null) {
            this.mRemoteActivity.onStart();
        }
    }

    protected void onRestart() {
        super.onRestart();
        if (this.mRemoteActivity != null) {
            this.mRemoteActivity.onRestart();
        }
    }

    protected void onResume() {
        super.onResume();
        if (this.mRemoteActivity != null) {
            this.mRemoteActivity.onResume();
        }
    }

    protected void onPause() {
        if (this.mRemoteActivity != null) {
            this.mRemoteActivity.onPause();
        }
        super.onPause();
    }

    protected void onStop() {
        if (this.mRemoteActivity != null) {
            this.mRemoteActivity.onStop();
        }
        super.onStop();
    }

    protected void onDestroy() {
        unregisterReceiver(this.mReceiver);
        if (this.mRemoteActivity != null) {
            this.mRemoteActivity.onDestroy();
        }
        super.onDestroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (this.mRemoteActivity == null || !this.mRemoteActivity.onKeyDown(keyCode, event)) {
            return super.onKeyDown(keyCode, event);
        }
        return true;
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.mRemoteActivity == null || !this.mRemoteActivity.dispatchTouchEvent(ev)) {
            return super.dispatchTouchEvent(ev);
        }
        return true;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (this.mRemoteActivity != null) {
            this.mRemoteActivity.onWindowFocusChanged(hasFocus);
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (this.mRemoteActivity != null) {
            this.mRemoteActivity.onNewIntent(intent);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (this.mRemoteActivity != null) {
            this.mRemoteActivity.onRestoreInstanceState(savedInstanceState);
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mRemoteActivity != null) {
            this.mRemoteActivity.onConfigurationChanged(newConfig);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (this.mRemoteActivity != null) {
            this.mRemoteActivity.onBackPressed();
        }
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.LOCALE_CHANGED");
        registerReceiver(this.mReceiver, filter);
    }

    private void updateLocalConfig() {
        if (JarApplication.getInstance() != null) {
            Resources resources = getResources();
            Configuration config = resources.getConfiguration();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            config.locale = JarApplication.getInstance().getResources().getConfiguration().locale;
            resources.updateConfiguration(config, metrics);
        }
    }
}
