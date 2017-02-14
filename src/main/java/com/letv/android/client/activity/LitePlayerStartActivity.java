package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.Toast;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.utils.AlbumLaunchUtils;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.utils.PluginInitedCallback;
import com.letv.business.flow.album.AlbumFlowUtils;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.utils.ActivityUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.plugin.pluginconfig.utils.JarLaunchUtils;
import com.letv.plugin.pluginloader.apk.pm.ApkManager;
import com.letv.plugin.pluginloader.util.JarUtil;
import com.letv.plugin.pluginloader.util.JarUtil.OnPluginInitedListener;

public class LitePlayerStartActivity extends LetvBaseActivity {
    private boolean isLitePlayer;
    private boolean mHasApplyPermissionsBefore;

    public LitePlayerStartActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.isLitePlayer = false;
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.mNeedApplyPermissions = true;
        this.mHasApplyPermissionsBefore = hasApplyPermissions();
        super.onCreate(savedInstanceState);
        LogInfo.log("lite", "oncreate...");
    }

    protected void onStart() {
        super.onStart();
        if (!this.isLitePlayer && hasApplyPermissions() && getIntent() != null) {
            AlbumPlayFlow mPlayAlbumFlow = AlbumFlowUtils.getPlayFlow(this, 1, getIntent().getExtras());
            if (mPlayAlbumFlow != null) {
                mPlayAlbumFlow.mPlayInfo.mReplayType = 3;
                mPlayAlbumFlow.mPlayInfo.mIsAfterHomeClicked = true;
                mPlayAlbumFlow.statisticsLaunch(false);
                mPlayAlbumFlow.updatePlayDataStatistics("init", -1);
            }
        }
    }

    protected void onResume() {
        super.onResume();
        LogInfo.log("lite", "onResume...");
        if (this.isLitePlayer) {
            if (LetvApplication.getInstance().mCallLiteIntent != null) {
                Toast.makeText(getApplicationContext(), 2131100675, 0).show();
            }
        } else if (!hasApplyPermissions()) {
        }
    }

    protected void onPause() {
        super.onPause();
        if (!this.isLitePlayer && !this.mHasApplyPermissionsBefore) {
        }
    }

    protected void onStop() {
        super.onStop();
        if (this.isLitePlayer) {
            finish();
        } else if (!this.mHasApplyPermissionsBefore) {
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (!this.isLitePlayer && !this.mHasApplyPermissionsBefore) {
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.getExtras() != null) {
            initLite(intent, false);
            if (!this.isLitePlayer) {
            }
        }
    }

    protected void onApplyPermissionsSuccess() {
        super.onApplyPermissionsSuccess();
        if (this.mHasApplyPermissionsBefore) {
            initLite(getIntent(), true);
            if (this.isLitePlayer) {
                if (getIntent() != null) {
                    StatisticsUtils.sLoginRef = getIntent().getStringExtra("packageName");
                }
                statisticsFirstLaunch();
                ActivityUtils.getInstance().removeActivity(BasePlayActivity.class.getName(), true);
            }
        } else if (getIntent() == null) {
            finish();
        } else {
            Uri uri = getIntent().getData();
            Bundle bundle = getIntent().getExtras();
            finish();
            try {
                Intent tempIntent = new Intent(LetvApplication.getInstance(), AlbumPlayActivity.class);
                if (uri != null) {
                    tempIntent.setData(uri);
                }
                if (bundle != null) {
                    tempIntent.putExtras(bundle);
                }
                tempIntent.addFlags(268435456);
                LetvApplication.getInstance().startActivity(tempIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || event.getRepeatCount() != 0 || !this.isLitePlayer) {
            return super.onKeyDown(keyCode, event);
        }
        finish();
        ActivityUtils.getInstance().removeAll();
        return true;
    }

    private void initLite(final Intent intent, boolean isCreate) {
        String scheme = intent.getScheme();
        if (!TextUtils.isEmpty(scheme) && AlbumLaunchUtils.LITESCHEME.equalsIgnoreCase(scheme)) {
            this.isLitePlayer = true;
            AlbumLaunchUtils.initLite(this, intent);
            ApkManager.getInstance().setLiteAppCallState(true);
            if (ApkManager.getInstance().getPluginInstallState("com.letv.android.lite") != 1) {
                UIsUtils.setScreenLandscape(this);
                if (isCreate) {
                    setContentView(2130903057);
                }
                LetvApplication.getInstance().mCallLiteIntent = intent;
                JarUtil.updatePlugin(this, 1, true);
            } else if (LetvApplication.getInstance().mIsPluginInitedSuccess) {
                JarLaunchUtils.launchLitePlayerDefault(this, intent);
            } else {
                PluginInitedCallback.mPluginInitedListener = new OnPluginInitedListener(this) {
                    final /* synthetic */ LitePlayerStartActivity this$0;

                    public void onInited() {
                        JarLaunchUtils.launchLitePlayerDefault(this.this$0.mContext, intent);
                    }
                };
            }
        } else if (isCreate) {
            setContentView(2130903068);
        }
    }

    public String[] getAllFragmentTags() {
        return FragmentConstant.ALBUM_FRAGMENT_TAG_ARRAY;
    }

    public String getActivityName() {
        return LitePlayerStartActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
