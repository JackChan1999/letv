package com.letv.mobile.lebox.ui.main;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.config.LeBoxAppConfig;
import com.letv.mobile.lebox.connect.LeboxConnectManager;
import com.letv.mobile.lebox.connect.LeboxConnectManager.ConnectProgressReciver;
import com.letv.mobile.lebox.heartbeat.HeartbeatManager;
import com.letv.mobile.lebox.heartbeat.HeartbeatObserver;
import com.letv.mobile.lebox.http.lebox.bean.OtaVersionBean;
import com.letv.mobile.lebox.httpmanager.HttpCacheAssistant;
import com.letv.mobile.lebox.httpmanager.HttpRequesetManager;
import com.letv.mobile.lebox.httpmanager.HttpRequesetManager.HttpCallBack;
import com.letv.mobile.lebox.jump.PageJumpUtil;
import com.letv.mobile.lebox.net.LeBoxNetworkManager;
import com.letv.mobile.lebox.ui.qrcode.LeboxQrCodeBean;
import com.letv.mobile.lebox.utils.DialogUtil;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.utils.SharedPreferencesUtil;
import com.letv.mobile.lebox.utils.Util;
import com.letv.mobile.lebox.view.pulltorefresh.PullToRefreshBase;
import com.letv.mobile.lebox.view.pulltorefresh.PullToRefreshBase.Mode;
import com.letv.mobile.lebox.view.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.letv.mobile.lebox.view.pulltorefresh.PullToRefreshScrollView;
import com.tencent.open.yyb.TitleBar;

public class LeBoxMainActivity extends Activity implements OnClickListener, OnLongClickListener {
    private static final String TAG = LeBoxMainActivity.class.getSimpleName();
    private boolean isNeedUpgrade;
    private boolean isResetName;
    private ImageView le_box_room_status;
    private ImageView mExitImageView;
    HeartbeatObserver mHeartbeatObserver = new HeartbeatObserver() {
        public void change(int data) {
            switch (data) {
                case 4:
                    Logger.d(LeBoxMainActivity.TAG, "----------------main ui receive net state change----------------");
                    if (!LeBoxNetworkManager.getInstance().isWifiEnable()) {
                        Util.showToast(R.string.wifi_disable);
                    } else if (LeBoxNetworkManager.getInstance().isLeboxWifiAvailable()) {
                        LeBoxMainActivity.this.initData(false);
                    }
                    LeBoxMainActivity.this.checkUiConnection();
                    return;
                case 6:
                    Logger.d(LeBoxMainActivity.TAG, "----------------main ui receive p2p connect change-------------------------");
                    if (LeBoxNetworkManager.getInstance().isConnectionWifiDirect()) {
                        LeBoxMainActivity.this.initData(false);
                    }
                    LeBoxMainActivity.this.checkUiConnection();
                    return;
                case 7:
                    LeBoxMainActivity.this.initData(false);
                    LeBoxMainActivity.this.checkUiConnection();
                    return;
                case 8:
                    Logger.d(LeBoxMainActivity.TAG, "----------------main ui receive init user permission success-------------------------");
                    LeBoxMainActivity.this.initData(false);
                    LeBoxMainActivity.this.checkUiConnection();
                    return;
                case 9:
                    Logger.d(LeBoxMainActivity.TAG, "---------------receive permission change-------------------------");
                    LeBoxMainActivity.this.initData(false);
                    LeBoxMainActivity.this.checkUiConnection();
                    return;
                case 14:
                    Logger.d(LeBoxMainActivity.TAG, "---------------receive lebox internet change-------------------------");
                    LeBoxMainActivity.this.showLeboxInternetPrompt();
                    return;
                case 15:
                    Logger.d(LeBoxMainActivity.TAG, "---------------receive lebox connect ssid change-------------------------");
                    LeBoxMainActivity.this.showLeboxConnectSsid();
                    return;
                default:
                    return;
            }
        }
    };
    private RelativeLayout mLeBoxBuy;
    private RelativeLayout mLeBoxIntroduce;
    private RelativeLayout mLeBoxMyDownload;
    private RelativeLayout mLeBoxMyFollow;
    private EditText mLeBoxName;
    private Button mLeBoxRename;
    private RelativeLayout mLeBoxRoomUpdate;
    private RelativeLayout mLeBoxSettings;
    private Button mLeBoxShare;
    private TextView mLeBoxStatus;
    private TextView mLeBoxVersion;
    private RelativeLayout mLeBoxWifiConnect;
    private TextView mLeboxAdmin;
    private LinearLayout mLeboxBtnGroup;
    private RelativeLayout mLeboxCleanUser;
    private TextView mLeboxWifiName;
    private OtaVersionBean mOtaVersionBean;
    private PullToRefreshScrollView mPullToRefreshScrollView;
    OnRefreshListener<ScrollView> mRefreshListener = new OnRefreshListener<ScrollView>() {
        public void onRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
            LeboxConnectManager.getInstance().setConnectProgressReciver(new ConnectProgressReciver() {
                public void notifyProgress(int p) {
                    switch (p) {
                        case 27:
                            LeBoxMainActivity.this.mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel("");
                            LeBoxMainActivity.this.mPullToRefreshScrollView.onRefreshComplete();
                            LeBoxMainActivity.this.initData(false);
                            return;
                        default:
                            if (10 <= p && p < 27) {
                                String text = Util.getConnectProcessPrompt(p);
                                if (!TextUtils.isEmpty(text)) {
                                    LeBoxMainActivity.this.mPullToRefreshScrollView.getLoadingLayoutProxy().setLastUpdatedLabel(text);
                                    return;
                                }
                                return;
                            }
                            return;
                    }
                }
            });
            LeboxConnectManager.getInstance().startConnect();
        }
    };
    private ImageView mScanImageView;
    TextWatcher mTextWatcher = new TextWatcher() {
        private int cou = 0;
        private final int mMaxLenth = 10;
        int selectionEnd = 0;

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            this.cou = before + count;
            String editable = LeBoxMainActivity.this.mLeBoxName.getText().toString();
            String str = Util.stringFilter(editable);
            if (!editable.equals(str)) {
                LeBoxMainActivity.this.mLeBoxName.setText(str);
            }
            LeBoxMainActivity.this.mLeBoxName.setSelection(LeBoxMainActivity.this.mLeBoxName.length());
            this.cou = LeBoxMainActivity.this.mLeBoxName.length();
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
            int i = this.cou;
            getClass();
            if (i > 10) {
                this.selectionEnd = LeBoxMainActivity.this.mLeBoxName.getSelectionEnd();
                getClass();
                s.delete(10, this.selectionEnd);
            }
        }
    };
    private TextView mTitle;
    private Toast mToast;
    private RelativeLayout mleboxNetState;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG, "------------LeBoxMainActivity----------------onCreate--------------------------");
        setContentView(R.layout.base_line_main_layout);
        getWindow().setSoftInputMode(240);
        initView();
        initListener();
        initRegister();
        initData(true);
    }

    private void initView() {
        this.mLeBoxName = (EditText) findViewById(R.id.lebox_main_et_nickname);
        this.mLeBoxStatus = (TextView) findViewById(R.id.lebox_main_status);
        this.mLeBoxShare = (Button) findViewById(R.id.btn_lebox_main_share);
        this.mLeBoxRename = (Button) findViewById(R.id.btn_lebox_main_rename);
        this.mLeBoxMyDownload = (RelativeLayout) findViewById(R.id.le_box_my_download);
        this.mLeBoxMyFollow = (RelativeLayout) findViewById(R.id.le_box_my_follow);
        this.mLeBoxIntroduce = (RelativeLayout) findViewById(R.id.le_box_introduce);
        this.mLeBoxBuy = (RelativeLayout) findViewById(R.id.le_box_buy);
        this.mLeBoxSettings = (RelativeLayout) findViewById(R.id.le_box_settings);
        this.mLeBoxRoomUpdate = (RelativeLayout) findViewById(R.id.le_box_room_update);
        this.mExitImageView = (ImageView) findViewById(R.id.common_nav_left);
        this.mScanImageView = (ImageView) findViewById(R.id.common_nav_right);
        int paddingPx = Util.dip2px(this, TitleBar.SHAREBTN_RIGHT_MARGIN);
        Logger.d(TAG, "---initView---paddingPx=" + paddingPx);
        this.mScanImageView.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        this.mScanImageView.setImageResource(R.drawable.lebox_selector_navigation_right_scan);
        this.mScanImageView.setVisibility(0);
        this.mTitle = (TextView) findViewById(R.id.common_nav_title);
        this.mTitle.setText(R.string.lebox_main_navigation_title);
        this.mLeboxAdmin = (TextView) findViewById(R.id.lebox_main_admin);
        this.mLeBoxVersion = (TextView) findViewById(R.id.le_box_room_version);
        this.le_box_room_status = (ImageView) findViewById(R.id.le_box_room_status);
        this.mLeboxBtnGroup = (LinearLayout) findViewById(R.id.lebox_main_btn_layout);
        this.mLeBoxWifiConnect = (RelativeLayout) findViewById(R.id.lebox_main_valid_wifi);
        this.mPullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.lebox_main_pull_refresh_scrollview);
        this.mPullToRefreshScrollView.setMode(Mode.PULL_FROM_START);
        this.mleboxNetState = (RelativeLayout) findViewById(R.id.lebox_main_net_state);
        this.mLeboxCleanUser = (RelativeLayout) findViewById(R.id.lebox_main_clean_user);
        this.mLeboxWifiName = (TextView) findViewById(R.id.lebox_main_valid_wifi_name);
    }

    private void initListener() {
        this.mLeBoxShare.setOnClickListener(this);
        this.mLeBoxRename.setOnClickListener(this);
        this.mLeBoxMyDownload.setOnClickListener(this);
        this.mLeBoxIntroduce.setOnClickListener(this);
        this.mLeBoxBuy.setOnClickListener(this);
        this.mLeBoxRoomUpdate.setOnClickListener(this);
        this.mExitImageView.setOnClickListener(this);
        this.mScanImageView.setOnClickListener(this);
        this.mLeBoxWifiConnect.setOnClickListener(this);
        this.mLeBoxMyFollow.setOnClickListener(this);
        this.mLeBoxSettings.setOnClickListener(this);
        this.mLeBoxIntroduce.setOnLongClickListener(this);
        this.mLeBoxName.addTextChangedListener(this.mTextWatcher);
        this.mleboxNetState.setOnClickListener(this);
        this.mLeboxCleanUser.setOnClickListener(this);
    }

    private void initData(boolean isReadCache) {
        String domain = LeBoxAppConfig.getDynamicDomain();
        Logger.d(TAG, "---initData-----domain=" + domain);
        if (!LeBoxNetworkManager.getInstance().isLeboxConnectedAvailable() || TextUtils.isEmpty(domain)) {
            checkUiConnection();
        } else if (isReadCache) {
            HttpRequesetManager.getInstance().getUserPermissionFormCache(new HttpCallBack<String>() {
                public void callback(int code, String msg, String errorCode, String object) {
                    Logger.d(LeBoxMainActivity.TAG, "--getUserPermissionFormCache--code=" + code + "--errorCode=" + errorCode + "--msg=" + msg + "--object=" + object);
                    LeBoxMainActivity.this.showUserPermission(code, msg, errorCode, object);
                }
            });
        } else {
            HttpRequesetManager.getInstance().getUserPermission(new HttpCallBack<String>() {
                public void callback(int code, String msg, String errorCode, String object) {
                    Logger.d(LeBoxMainActivity.TAG, "--getUserPermission--code=" + code + "--errorCode=" + errorCode + "--msg=" + msg + "--object=" + object);
                    LeBoxMainActivity.this.showUserPermission(code, msg, errorCode, object);
                }
            });
        }
    }

    private void showLeboxName() {
        HttpRequesetManager.getInstance().getLeBoxName(new HttpCallBack<String>() {
            public void callback(int code, String msg, String errorCode, String object) {
                if (!TextUtils.isEmpty(object)) {
                    LeBoxMainActivity.this.mLeBoxName.setText(object);
                }
            }
        });
    }

    private void setLeboxName(String boxNewName) {
        HttpRequesetManager.getInstance().setLeboxName(new HttpCallBack<String>() {
            public void callback(int code, String msg, String errorCode, String object) {
                if (code == 0 && "0".equals(errorCode)) {
                    if (!TextUtils.isEmpty(object)) {
                        LeBoxMainActivity.this.mLeBoxName.setText(object);
                    }
                    Util.showToast(R.string.toast_msg_http_nameset);
                    return;
                }
                if (!TextUtils.isEmpty(object)) {
                    LeBoxMainActivity.this.mLeBoxName.setText(object);
                }
                Util.showToast(R.string.toast_msg_http_error_nameset);
            }
        }, boxNewName);
    }

    private void showUserPermission(int code, String msg, String errorCode, String object) {
        if (code == 0 && object != null) {
            HeartbeatManager.getInstance().onStartHeartbeat();
            showLeboxName();
            checkLeboxVersion();
            showLeboxConnectSsid();
        } else if ("1".equals(errorCode)) {
            Util.showToast(R.string.toast_msg_http_error_login_permission);
        }
        checkUiConnection();
    }

    private void checkLeboxVersion() {
        HttpRequesetManager.getInstance().getOtaVersion(new HttpCallBack<OtaVersionBean>() {
            public void callback(int code, String msg, String errorCode, OtaVersionBean object) {
                if (code == 0 && "0".equals(errorCode) && object != null) {
                    LeBoxMainActivity.this.mOtaVersionBean = object;
                    if ("true".equals(LeBoxMainActivity.this.mOtaVersionBean.getHasNew())) {
                        LeBoxMainActivity.this.isNeedUpgrade = true;
                        LeBoxMainActivity.this.le_box_room_status.setVisibility(0);
                    } else {
                        LeBoxMainActivity.this.isNeedUpgrade = false;
                        LeBoxMainActivity.this.le_box_room_status.setVisibility(8);
                    }
                    LeBoxMainActivity.this.mLeBoxVersion.setText(LeBoxMainActivity.this.mOtaVersionBean.getCurVersion());
                    if (!"true".equals(LeBoxMainActivity.this.mOtaVersionBean.getHasNew())) {
                        return;
                    }
                    return;
                }
                LeBoxMainActivity.this.mOtaVersionBean = null;
                LeBoxMainActivity.this.isNeedUpgrade = false;
            }
        });
    }

    protected void onStart() {
        checkUiConnection();
        super.onStart();
    }

    protected void onResume() {
        Logger.d(TAG, "------------LeBoxMainActivity----------------onResume--------------------------");
        setNameDisable();
        showLeboxInternetPrompt();
        showLeboxConnectSsid();
        super.onResume();
    }

    protected void onPause() {
        Logger.d(TAG, "------------LeBoxMainActivity----------------onPause--------------------------");
        super.onPause();
    }

    private void initRegister() {
        HeartbeatManager.getInstance().regustHeartbeatObserver(this.mHeartbeatObserver);
        this.mPullToRefreshScrollView.setOnRefreshListener(this.mRefreshListener);
    }

    private void UnRegister() {
        HeartbeatManager.getInstance().unRegustHeartbeatObserver(this.mHeartbeatObserver);
    }

    protected void onStop() {
        Logger.d(TAG, "------------LeBoxMainActivity----------------onStop--------------------------");
        super.onStop();
    }

    protected void onDestroy() {
        Logger.d(TAG, "------------LeBoxMainActivity----------------onDestroy--------------------------");
        UnRegister();
        super.onDestroy();
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id != R.id.lebox_main_et_nickname) {
            if (id == R.id.btn_lebox_main_rename) {
                if (this.mLeBoxRename.getText().toString().equals(getResources().getString(R.string.lebox_main_text_rename))) {
                    this.mLeBoxRename.setText(R.string.lebox_main_text_rename_ok);
                    setNameEnable();
                    return;
                }
                this.mLeBoxRename.setText(R.string.lebox_main_text_rename);
                setNameDisable();
                String curLeboxName = this.mLeBoxName.getText().toString();
                if (TextUtils.isEmpty(curLeboxName)) {
                    showToast(R.string.lebox_main_text_rename_null_name);
                } else if (curLeboxName.equals(SharedPreferencesUtil.readData(SharedPreferencesUtil.LEBOX_NAME_KEY, ""))) {
                    showToast(R.string.lebox_main_text_rename_no_operation);
                } else {
                    setLeboxName(curLeboxName);
                }
            } else if (id == R.id.le_box_my_download) {
                if (this.isResetName) {
                    showToast(R.string.toast_msg_reset_name_confirm);
                } else {
                    PageJumpUtil.jumpLeboxDownloadPage(this);
                }
            } else if (id == R.id.le_box_introduce) {
                if (this.isResetName) {
                    showToast(R.string.toast_msg_reset_name_confirm);
                    return;
                }
                PageJumpUtil.jumpToIntroduce(this);
                if (TextUtils.isEmpty(LeboxQrCodeBean.getSsid())) {
                    finish();
                }
            } else if (id == R.id.le_box_buy) {
                if (this.isResetName) {
                    showToast(R.string.toast_msg_reset_name_confirm);
                    return;
                }
                PageJumpUtil.jumpBuyPage(this);
                if (TextUtils.isEmpty(LeboxQrCodeBean.getSsid())) {
                    finish();
                }
            } else if (id == R.id.le_box_room_update) {
                if (this.isResetName) {
                    showToast(R.string.toast_msg_reset_name_confirm);
                } else if (this.isNeedUpgrade) {
                    String title = getResources().getString(R.string.lebox_main_text_update_title);
                    String content = getUpgradeContentText();
                    Logger.d(TAG, "---upgrade content text :" + content);
                    DialogUtil.showDialog(this, title, content, "", "", null, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            HttpRequesetManager.getInstance().getOtaUpgrade(new 1(this));
                            dialog.dismiss();
                        }
                    });
                } else {
                    Logger.d(TAG, "---mOtaVersionBean=" + this.mOtaVersionBean);
                    if (this.mOtaVersionBean != null) {
                        DialogUtil.showDialog(this, String.format(getResources().getString(R.string.lebox_main_text_nu_need_upgrade), new Object[]{this.mOtaVersionBean.getCurVersion()}), getResources().getString(R.string.dialog_default_ok), null);
                    }
                }
            } else if (id == R.id.common_nav_left) {
                finish();
            } else if (id == R.id.common_nav_right) {
                if (this.isResetName) {
                    showToast(R.string.toast_msg_reset_name_confirm);
                } else {
                    PageJumpUtil.jumpQrCodeScan(this);
                }
            } else if (id == R.id.btn_lebox_main_share) {
                if (this.isResetName) {
                    showToast(R.string.toast_msg_reset_name_confirm);
                } else {
                    PageJumpUtil.jumpToPageQrCodeShareActivity(this);
                }
            } else if (id == R.id.lebox_main_valid_wifi) {
                if (this.isResetName) {
                    showToast(R.string.toast_msg_reset_name_confirm);
                } else {
                    PageJumpUtil.jumpWifiManagerPage(this);
                }
            } else if (id == R.id.le_box_my_follow) {
                if (this.isResetName) {
                    showToast(R.string.toast_msg_reset_name_confirm);
                } else {
                    PageJumpUtil.jumpMyFollow(this);
                }
            } else if (id == R.id.le_box_settings) {
                if (this.isResetName) {
                    showToast(R.string.toast_msg_reset_name_confirm);
                } else {
                    PageJumpUtil.jumpLeboxSettings(this);
                }
            } else if (id == R.id.lebox_main_net_state) {
                if (this.isResetName) {
                    showToast(R.string.toast_msg_reset_name_confirm);
                } else {
                    PageJumpUtil.jumpWifiManagerPage(this);
                }
            } else if (id != R.id.lebox_main_clean_user) {
            } else {
                if (this.isResetName) {
                    showToast(R.string.toast_msg_reset_name_confirm);
                } else {
                    DialogUtil.showDialog(this, getResources().getString(R.string.dialog_clean_user_prompt), "", "", null, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            LeboxQrCodeBean.cleanAllInfo();
                            LeBoxMainActivity.this.initData(false);
                            dialogInterface.dismiss();
                        }
                    });
                }
            }
        }
    }

    private void setNameEnable() {
        this.mLeBoxName.setEnabled(true);
        this.mLeBoxName.setSelection(this.mLeBoxName.getText().length());
        ((InputMethodManager) getSystemService("input_method")).showSoftInput(this.mLeBoxName, 0);
        this.isResetName = true;
    }

    private void setNameDisable() {
        this.mLeBoxName.setEnabled(false);
        this.isResetName = false;
    }

    private String getUpgradeContentText() {
        if (this.mOtaVersionBean == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(getResources().getString(R.string.lebox_main_text_need_upgrade), new Object[]{this.mOtaVersionBean.getCurVersion(), this.mOtaVersionBean.getNextVersion()}));
        return stringBuilder.toString();
    }

    private void checkUiConnection() {
        String showStatus = this.mLeBoxStatus.getText().toString();
        boolean isConnection = LeBoxNetworkManager.getInstance().isLeboxConnectedAvailable();
        Logger.d(TAG, "checkUiConnection showStatus:" + showStatus + "--isConnection=" + isConnection);
        if (!isConnection) {
            setConnectFailView();
            setLeboxConnectStatus(isConnection);
        } else if (HttpCacheAssistant.getInstanced().isAdmini()) {
            setAdminView();
            setLeboxConnectStatus(isConnection);
        } else if (HttpCacheAssistant.getInstanced().getUserIdentity() == 2) {
            setNotAdminView();
            setLeboxConnectStatus(isConnection);
        } else {
            setConnectFailView();
            setLeboxConnectStatus(!isConnection);
        }
    }

    private void setAdminView() {
        this.mLeboxAdmin.setText(R.string.lebox_main_text_admin);
        this.mLeboxAdmin.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_lebox_tv_admin));
        this.mLeboxAdmin.setVisibility(0);
        this.mLeboxBtnGroup.setVisibility(0);
        this.mLeBoxWifiConnect.setVisibility(0);
        this.mLeBoxMyDownload.setVisibility(0);
        this.mLeBoxRoomUpdate.setVisibility(0);
        this.mLeBoxMyFollow.setVisibility(0);
        this.mLeBoxSettings.setVisibility(0);
        if (HeartbeatManager.getInstance().isLeboxHasInternet()) {
            this.mleboxNetState.setVisibility(8);
        } else {
            this.mleboxNetState.setVisibility(0);
        }
    }

    private void setNotAdminView() {
        this.mLeboxAdmin.setText(R.string.lebox_main_text_guest);
        this.mLeboxAdmin.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_lebox_tv_guest));
        this.mLeboxAdmin.setVisibility(0);
        this.mLeboxBtnGroup.setVisibility(8);
        this.mLeBoxWifiConnect.setVisibility(8);
        this.mLeBoxMyDownload.setVisibility(0);
        this.mLeBoxRoomUpdate.setVisibility(8);
        this.mLeBoxMyFollow.setVisibility(8);
        this.mLeBoxSettings.setVisibility(8);
        this.mleboxNetState.setVisibility(8);
    }

    private void setConnectFailView() {
        this.mLeboxAdmin.setVisibility(8);
        this.mLeboxBtnGroup.setVisibility(8);
        this.mLeBoxWifiConnect.setVisibility(8);
        this.mLeBoxMyDownload.setVisibility(8);
        this.mLeBoxRoomUpdate.setVisibility(8);
        this.mLeBoxMyFollow.setVisibility(8);
        this.mLeBoxSettings.setVisibility(8);
        this.mleboxNetState.setVisibility(8);
    }

    private void setLeboxConnectStatus(boolean isConnect) {
        if (isConnect) {
            this.mLeBoxStatus.setText(getResources().getString(R.string.le_box_connect_status_connected));
        } else {
            this.mLeBoxStatus.setText(getResources().getString(R.string.le_box_connect_status_un_connect));
        }
    }

    public boolean onLongClick(View v) {
        if (v.getId() != R.id.le_box_introduce) {
            return false;
        }
        PageJumpUtil.jumpTestPage(this);
        return true;
    }

    private void showToast(int msg_id) {
        if (this.mToast != null) {
            this.mToast.cancel();
        }
        this.mToast = Toast.makeText(this, getString(msg_id), 1);
        this.mToast.show();
    }

    private void showLeboxInternetPrompt() {
        if (!LeBoxNetworkManager.getInstance().isLeboxConnectedAvailable() || !HttpCacheAssistant.getInstanced().isAdmini()) {
            this.mleboxNetState.setVisibility(8);
        } else if (HeartbeatManager.getInstance().isLeboxHasInternet()) {
            this.mleboxNetState.setVisibility(8);
        } else {
            this.mleboxNetState.setVisibility(0);
        }
    }

    private void showLeboxConnectSsid() {
        if (!LeBoxNetworkManager.getInstance().isLeboxConnectedAvailable() || !HttpCacheAssistant.getInstanced().isAdmini()) {
            this.mLeboxWifiName.setVisibility(8);
        } else if (TextUtils.isEmpty(HeartbeatManager.getInstance().getSsid())) {
            this.mLeboxWifiName.setVisibility(8);
        } else {
            this.mLeboxWifiName.setText(HeartbeatManager.getInstance().getSsid());
            this.mLeboxWifiName.setVisibility(0);
        }
    }
}
