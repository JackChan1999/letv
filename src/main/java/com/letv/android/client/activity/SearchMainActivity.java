package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import com.letv.adlib.sdk.types.AdElementMime;
import com.letv.ads.ex.client.SearchKeyWordCallBack;
import com.letv.ads.ex.utils.AdsManagerProxy;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.adapter.LetvBaseAdapter;
import com.letv.android.client.listener.LesoListener;
import com.letv.android.client.utils.UIs;
import com.letv.android.client.view.PublicLoadLayoutPlayerLibs;
import com.letv.core.BaseApplication;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.db.PreferencesManager;
import com.letv.core.db.SearchTraceHandler;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.lesophoneclient.ex.LeSoSearchListener;
import com.letv.lesophoneclient.ex.LesoOnResumCallBack;
import com.letv.lesophoneclient.ex.LesoParam;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.loader.JarLoader;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import java.net.URLEncoder;

public class SearchMainActivity extends LetvBaseActivity implements OnClickListener {
    public static final String LESO_FROM = "letvclient";
    public static final String LESO_FROM_BOTTOM = "ref＝0101_bottom";
    public static final String SEARCH_H5_WEB_URL = "http://m.letv.com/search?noheader=1&nofooter=1&wd=";
    public static final String SEARCH_H5_WEB_URL_REF = "&ref=0101";
    public static final String TEST_SEARCH_H5_WEB_URL = "http://t.m.letv.com/search?noheader=1&nofooter=1&wd=";
    private String adsKeyWords;
    private String from_flag;
    private String mCategory;
    private int mHotRankIndex;
    private String mKeyWord;
    private PublicLoadLayoutPlayerLibs mRootView;
    private SearchView mSearchAutoView;
    private ImageView mSearchCancel;
    private FrameLayout mSearchDialogView;
    private GridView mSearchHotRankGridView;
    private SearchHotTitleAdapter mSearchHotTitleAdapter;
    private LinearLayout mSearchHotTitleLayout;
    private SearchKeyWordCallBack mSearchKeyWordCallBack;
    private HorizontalScrollView mSearchScrollView;
    private ImageView mSearchSubmitButton;
    private AutoCompleteTextView mSearchTextView;
    private String oldWord;
    private OnItemClickListener onSearchHotRankGridItemClick;
    private ImageView searchDeleteButton;
    private boolean searchFlag;
    private ImageView searchIndicatorImagev;
    private int serchLogoTag;
    private TextWatcher textWatcher;

    private class SearchHotTitleAdapter extends LetvBaseAdapter {
        private Context mContext;
        final /* synthetic */ SearchMainActivity this$0;

        public SearchHotTitleAdapter(SearchMainActivity searchMainActivity, Context context) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = searchMainActivity;
            super(context);
            this.mContext = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = UIs.inflate(this.mContext, (int) R.layout.home_bottom_search_words_item, parent, false);
            }
            ((TextView) convertView).setText(String.valueOf(getItem(position)));
            ((TextView) convertView).setTextSize(1, 18.0f);
            if (position == this.this$0.mHotRankIndex) {
                ((TextView) convertView).setTextColor(this.this$0.getResources().getColor(2131493223));
                convertView.setBackgroundResource(2130838882);
            } else {
                ((TextView) convertView).setTextColor(this.this$0.getResources().getColor(2131493221));
                convertView.setBackgroundDrawable(null);
            }
            return convertView;
        }
    }

    public SearchMainActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mCategory = "";
        this.adsKeyWords = null;
        this.mHotRankIndex = 0;
        this.searchFlag = false;
        this.from_flag = "";
        this.textWatcher = new TextWatcher(this) {
            final /* synthetic */ SearchMainActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        };
        this.onSearchHotRankGridItemClick = new OnItemClickListener(this) {
            final /* synthetic */ SearchMainActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            }
        };
        this.mSearchKeyWordCallBack = new SearchKeyWordCallBack(this) {
            final /* synthetic */ SearchMainActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void updateSearchTextView(String messg) {
                if (this.this$0.mSearchTextView != null && !TextUtils.isEmpty(messg)) {
                    LogInfo.log("ads", "messg =" + messg);
                    if (messg.length() > 10) {
                        messg = messg.substring(0, 10);
                    }
                    this.this$0.adsKeyWords = messg;
                    this.this$0.setAdsKeyWords(this.this$0.adsKeyWords);
                }
            }

            public void updateSearchBannerTextView(AdElementMime bannerAd) {
            }
        };
    }

    public static String getSearchUrl() {
        return (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) ? TEST_SEARCH_H5_WEB_URL : SEARCH_H5_WEB_URL;
    }

    public static void launch(Activity activity, String from) {
        Intent intent = new Intent(activity, SearchMainActivity.class);
        intent.putExtra("From_flag", from);
        intent.putExtra("from", LESO_FROM);
        activity.startActivity(intent);
    }

    public static void launch(Context context, String from) {
        Intent intent = new Intent(context, SearchMainActivity.class);
        intent.putExtra("From_flag", from);
        intent.putExtra("from", LESO_FROM);
        context.startActivity(intent);
    }

    public static void launch(Context context, String from, String keyWord) {
        Intent intent = new Intent(context, SearchMainActivity.class);
        intent.putExtra("From_flag", from);
        intent.putExtra("key_word", keyWord);
        intent.putExtra("from", LESO_FROM);
        context.startActivity(intent);
    }

    public static void launch(Context context, String from, int logoTag) {
        Intent intent = new Intent(context, SearchMainActivity.class);
        intent.putExtra("From_flag", from);
        intent.putExtra("logo_tag", logoTag);
        intent.putExtra("from", LESO_FROM);
        context.startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Class clazz_lesoUtil = JarLoader.loadClass(getApplicationContext(), "LetvLeso.apk", JarConstant.LETV_LESO_PACKAGENAME, "utils.LesoInitData");
        if (clazz_lesoUtil == null) {
            finish();
            return;
        }
        JarLoader.invokeStaticMethod(clazz_lesoUtil, "setmSearchListener", new Class[]{LeSoSearchListener.class}, new Object[]{new LesoListener(this)});
        Intent intent = getIntent();
        this.from_flag = "0101_icon";
        String temp = intent.getStringExtra("From_flag");
        String fromWhere = intent.getStringExtra("fromWhere");
        if (!TextUtils.isEmpty(temp)) {
            this.from_flag = temp;
        }
        if (BaseApplication.getInstance().getSuppportTssLevel() != 0 || "mp4".equalsIgnoreCase(BaseApplication.getInstance().getVideoFormat())) {
        }
        int login_flag = PreferencesManager.getInstance().isLogin() ? 0 : 1;
        LesoParam lesoParam = new LesoParam();
        lesoParam.letvVersion = AbstractSpiCall.ANDROID_CLIENT_TYPE + LetvUtils.getClientVersionName();
        lesoParam.pcode = LetvConfig.getPcode();
        if ("fromStateBar".equalsIgnoreCase(intent.getStringExtra("fromWhere"))) {
            LogInfo.LogStatistics("搜索通知栏");
            StatisticsUtils.staticticsInfoPost(this, "0", "se01", null, 1, null, null, null, null, null, null, null);
        }
        String keyWord = intent.getStringExtra("key_word");
        int logo_tag = intent.getIntExtra("logo_tag", 0);
        Class clazz_lesoMain;
        if (TextUtils.isEmpty(keyWord) && logo_tag == 0) {
            if ("fromLetvNotify".equalsIgnoreCase(intent.getStringExtra("fromLetvNotify"))) {
                lesoParam.fromLetvNotification = "FromLetvNotification";
            } else {
                lesoParam.from = this.from_flag;
                lesoParam.loginFlag = login_flag;
                lesoParam.fromWhere = intent.getStringExtra("fromWhere");
                clazz_lesoMain = JarLoader.loadClass(this, "LetvLeso.apk", JarConstant.LETV_LESO_PACKAGENAME, "ui.LeSoMainActivity");
                if (clazz_lesoMain == null) {
                    finish();
                    return;
                }
                JarLoader.invokeStaticMethod(clazz_lesoMain, "launch", new Class[]{Context.class, LesoOnResumCallBack.class, LesoParam.class}, new Object[]{this, new LesoOnResumCallBack(this) {
                    final /* synthetic */ SearchMainActivity this$0;

                    {
                        if (HotFix.PREVENT_VERIFY) {
                            System.out.println(VerifyLoad.class);
                        }
                        this.this$0 = this$0;
                    }

                    public void onResume_lego(Activity activity) {
                        LogInfo.log("+-->", "onCreate 获取服务的message数据");
                        if (!PreferencesManager.getInstance().isRecover()) {
                        }
                    }

                    public boolean onHideFloat(MotionEvent ev) {
                        return false;
                    }

                    public boolean backEvent(int keyCode, KeyEvent event) {
                        return false;
                    }

                    public void clearFloat() {
                    }
                }, lesoParam});
            }
        } else {
            lesoParam.from = this.from_flag;
            lesoParam.loginFlag = login_flag;
            lesoParam.fromWhere = intent.getStringExtra("fromWhere");
            lesoParam.keyWord = keyWord;
            lesoParam.searchLogoTag = logo_tag;
            clazz_lesoMain = JarLoader.loadClass(this, "LetvLeso.apk", JarConstant.LETV_LESO_PACKAGENAME, "ui.LeSoMainActivity");
            if (clazz_lesoMain == null) {
                finish();
                return;
            }
            JarLoader.invokeStaticMethod(clazz_lesoMain, "launch", new Class[]{Context.class, LesoOnResumCallBack.class, LesoParam.class}, new Object[]{this, /* anonymous class already generated */, lesoParam});
        }
        finish();
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    private void doSearch(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            this.mKeyWord = keyword;
            if (!TextUtils.isEmpty(this.mKeyWord.trim())) {
                SearchTraceHandler.saveSearchTrace(this, this.mKeyWord, System.currentTimeMillis());
            }
            UIsUtils.hideSoftkeyboard(this);
        }
    }

    public void onClick(View v) {
        if (v == this.mSearchSubmitButton) {
            String keyword = this.mSearchTextView.getText().toString();
            if (this.searchFlag) {
                doSearch(keyword);
                StatisticsUtils.staticticsInfoPost(this, "0", "d13", "文字搜索", -1, "", PageIdConstant.searchPage, "", "", "", "", "");
            }
        } else if (v == this.searchDeleteButton) {
            this.mKeyWord = "";
            this.mSearchTextView.setText("");
            this.mSearchTextView.requestFocus();
        }
    }

    public String getKeyWord() {
        return this.mKeyWord;
    }

    public void setCategory(String cg) {
        this.mCategory = cg;
    }

    private void requestSearchKeyWord(Context context) {
        AdsManagerProxy.getInstance(context).requestSearchKeyWord(context, this.mSearchKeyWordCallBack);
    }

    private void setAdsKeyWords(String messg) {
        if (!TextUtils.isEmpty(messg) && !messg.equals(this.mSearchTextView.getText())) {
            this.mSearchTextView.removeTextChangedListener(this.textWatcher);
            this.mSearchTextView.append(messg);
            this.mSearchTextView.addTextChangedListener(this.textWatcher);
            this.searchFlag = true;
            this.mSearchSubmitButton.setImageResource(2130838886);
            this.searchDeleteButton.setVisibility(0);
            this.searchIndicatorImagev.setVisibility(8);
        }
    }

    public static String urlJoin(String keyword) {
        StringBuilder sb = new StringBuilder(getSearchUrl());
        sb.append(URLEncoder.encode(keyword)).append(SEARCH_H5_WEB_URL_REF).append("&from=mobile_01").append("&lc=").append(Global.DEVICEID).append("&uid=").append(PreferencesManager.getInstance().isLogin() ? PreferencesManager.getInstance().getUserId() : "").append("&session=").append("&os=android").append("&ua=").append(LetvUtils.getBrandName() + " " + LetvUtils.getDeviceName());
        return sb.toString();
    }

    public String[] getAllFragmentTags() {
        return null;
    }

    public String getActivityName() {
        return null;
    }

    public Activity getActivity() {
        return this;
    }
}
