package com.letv.android.client.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.activity.MyPlayRecordActivity;
import com.letv.android.client.activity.SearchMainActivity;
import com.letv.android.client.commonlib.config.MyDownloadActivityConfig;
import com.letv.android.client.utils.ThemeDataManager;
import com.letv.android.client.utils.ThemeImageUtils;
import com.letv.android.client.view.MainBottomNavigationView.NavigationType;
import com.letv.core.bean.switchinfo.ThemeDataBean.ThemeItemInfo;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class MainTopNavigationView extends RelativeLayout implements OnClickListener {
    private Context mContext;
    private ImageView mDownloadView;
    private ImageView mHomeLesoHotSearchView;
    private TextView mHomeLesoHotTextView;
    private ImageView mHomeLesoImageView;
    private ImageView mHomeLogoView;
    private View mHomeSearchView;
    private ImageView mLogoView;
    private View mNavView;
    private ImageView mPlayRecordView;
    private ImageView mSearchView;
    private View mStatusBarView;
    private TextView mTitleView;
    private View mTopViewLayout;

    public MainTopNavigationView(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this(context, null, -1);
    }

    public MainTopNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MainTopNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
        initStatusBar();
    }

    private void init() {
        inflate(this.mContext, R.layout.main_top_navigation_view, this);
        this.mHomeLogoView = (ImageView) findViewById(R.id.main_top_nav_logo_home);
        this.mLogoView = (ImageView) findViewById(R.id.main_top_nav_logo);
        this.mSearchView = (ImageView) findViewById(R.id.main_top_nav_search);
        this.mPlayRecordView = (ImageView) findViewById(R.id.main_top_nav_play_record);
        this.mDownloadView = (ImageView) findViewById(R.id.main_top_nav_download);
        this.mTitleView = (TextView) findViewById(R.id.main_top_nav_title);
        this.mTopViewLayout = findViewById(R.id.main_top_nav_view_layout);
        this.mHomeSearchView = findViewById(R.id.main_top_nav_search_home);
        this.mStatusBarView = findViewById(R.id.main_top_nav_statusbar);
        this.mNavView = findViewById(R.id.main_top_nav_view);
        this.mHomeLesoHotSearchView = (ImageView) findViewById(R.id.main_top_nav_home_leso_hot_search);
        this.mHomeLesoImageView = (ImageView) findViewById(R.id.main_top_nav_home_leso_image);
        this.mHomeLesoHotTextView = (TextView) findViewById(R.id.main_top_nav_home_leso_hot_text);
        this.mSearchView.setOnClickListener(this);
        this.mPlayRecordView.setOnClickListener(this);
        this.mDownloadView.setOnClickListener(this);
        this.mLogoView.setOnClickListener(this);
        this.mHomeSearchView.setOnClickListener(this);
        ((GradientDrawable) this.mHomeSearchView.getBackground()).setColor(this.mContext.getResources().getColor(2131493377));
    }

    public void setTitle(String title) {
        if (TextUtils.equals(title, this.mContext.getString(2131100365))) {
            this.mTitleView.setVisibility(8);
            this.mTopViewLayout.setVisibility(8);
            return;
        }
        this.mTitleView.setText(title);
        this.mTitleView.setVisibility(0);
        this.mTopViewLayout.setVisibility(0);
    }

    public void setImagesVisibility(NavigationType type) {
        if (type == NavigationType.HOME) {
            this.mHomeLogoView.setVisibility(0);
            this.mLogoView.setVisibility(8);
            this.mSearchView.setVisibility(8);
            this.mPlayRecordView.setVisibility(0);
            this.mHomeSearchView.setVisibility(0);
        } else {
            this.mHomeLogoView.setVisibility(8);
            this.mLogoView.setVisibility(0);
            this.mSearchView.setVisibility(0);
            this.mPlayRecordView.setVisibility(4);
            this.mHomeSearchView.setVisibility(8);
        }
        if (LetvUtils.isInHongKong()) {
            this.mDownloadView.setVisibility(8);
            findViewById(R.id.main_top_nav_frame_download).getLayoutParams().width = 1;
        } else {
            this.mDownloadView.setVisibility(0);
            findViewById(R.id.main_top_nav_frame_download).getLayoutParams().width = UIsUtils.dipToPx(36.0f);
        }
        updateTheme(type);
    }

    public void updateTheme(NavigationType type) {
        if (type == NavigationType.HOME) {
            setTopNavigationBgTheme();
            ThemeDataManager themeDataManager = ThemeDataManager.getInstance(this.mContext);
            if (this.mHomeLogoView.getVisibility() == 0) {
                themeDataManager.setContentTheme(this.mHomeLogoView, ThemeDataManager.NAME_LOGO_ICON);
            }
            if (this.mHomeSearchView.getVisibility() == 0) {
                themeDataManager.setShapeViewTheme(this.mHomeSearchView, ThemeDataManager.NAME_SEARCH_COLOR);
                if (this.mHomeLesoHotTextView.getVisibility() == 0) {
                    themeDataManager.setContentTheme(this.mHomeLesoHotTextView, ThemeDataManager.NAME_SEARCH_TEXT_COLOR);
                }
                if (this.mHomeLesoHotSearchView.getVisibility() == 0) {
                    themeDataManager.setContentTheme(this.mHomeLesoHotSearchView, ThemeDataManager.NAME_HOME_LESO_ICON);
                }
                if (this.mHomeLesoImageView.getVisibility() == 0) {
                    themeDataManager.setContentTheme(this.mHomeLesoImageView, ThemeDataManager.NAME_HOME_LESO_PIC);
                }
            }
            if (this.mPlayRecordView.getVisibility() == 0) {
                themeDataManager.setContentTheme(this.mPlayRecordView, ThemeDataManager.NAME_HOME_HISTORY);
            }
            if (this.mDownloadView.getVisibility() == 0) {
                themeDataManager.setContentTheme(this.mDownloadView, ThemeDataManager.NAME_HOME_DOWNLOAD);
                return;
            }
            return;
        }
        setBackgroundColor(this.mContext.getResources().getColor(2131492951));
        this.mNavView.setBackgroundColor(this.mContext.getResources().getColor(2131493356));
        this.mNavView.setAlpha(0.9f);
        if (this.mStatusBarView.getVisibility() == 0) {
            this.mStatusBarView.setBackgroundColor(this.mContext.getResources().getColor(2131492950));
        }
        if (this.mDownloadView.getVisibility() == 0) {
            this.mDownloadView.setImageDrawable(this.mContext.getResources().getDrawable(2130837952));
        }
    }

    public void setLesoSearchContent() {
        String[] searchWords = PreferencesManager.getInstance().getSearchWordsInfo();
        if (searchWords != null && searchWords.length >= 3) {
            if (TextUtils.equals("2", searchWords[0])) {
                this.mHomeLesoHotTextView.setVisibility(8);
                this.mHomeLesoHotSearchView.setVisibility(8);
                this.mHomeLesoImageView.setVisibility(0);
                this.mHomeLesoImageView.setImageResource(TextUtils.equals("1", searchWords[2]) ? 2130838429 : 2130838427);
                return;
            }
            this.mHomeLesoImageView.setVisibility(8);
            this.mHomeLesoHotSearchView.setVisibility(0);
            this.mHomeLesoHotTextView.setVisibility(0);
            this.mHomeLesoHotTextView.setText(searchWords[1]);
        }
    }

    public void onClick(View v) {
        String currentPageId = ((MainActivity) this.mContext).getCurrentPageId();
        switch (v.getId()) {
            case R.id.main_top_nav_logo /*2131363850*/:
                StatisticsUtils.statisticsActionInfo(this.mContext, currentPageId, "0", "a2", "logo", -1, null);
                ((MainActivity) this.mContext).gotoHomeFragment();
                return;
            case R.id.main_top_nav_search_home /*2131363855*/:
            case R.id.main_top_nav_search /*2131363861*/:
                String sname;
                if (this.mHomeLesoHotTextView.getVisibility() != 0 || TextUtils.isEmpty(this.mHomeLesoHotTextView.getText())) {
                    int i;
                    String[] searchWords = PreferencesManager.getInstance().getSearchWordsInfo();
                    Context context = this.mContext;
                    String str = "ref＝0101_channel";
                    if (TextUtils.equals("2", searchWords[2])) {
                        i = 2;
                    } else {
                        i = 1;
                    }
                    SearchMainActivity.launch(context, str, i);
                    sname = this.mContext.getString(2131100238);
                } else {
                    SearchMainActivity.launch(this.mContext, "ref＝0101_channel", this.mHomeLesoHotTextView.getText().toString());
                    sname = this.mHomeLesoHotTextView.getText().toString();
                }
                StatisticsUtils.statisticsActionInfo(this.mContext, currentPageId, "0", "a2", "搜索", -1, "sname=" + sname);
                return;
            case R.id.main_top_nav_play_record /*2131363856*/:
                this.mContext.startActivity(new Intent(this.mContext, MyPlayRecordActivity.class));
                StatisticsUtils.statisticsActionInfo(this.mContext, currentPageId, "0", "a2", "播放记录", -1, null);
                return;
            case R.id.main_top_nav_download /*2131363862*/:
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MyDownloadActivityConfig(this.mContext).create(0)));
                StatisticsUtils.statisticsActionInfo(this.mContext, currentPageId, "0", "a2", "缓存", -1, null);
                return;
            default:
                return;
        }
    }

    private void initStatusBar() {
        if (VERSION.SDK_INT >= 19) {
            this.mStatusBarView.setVisibility(0);
            int statusBarHeight = UIsUtils.getStatusBarHeight(this.mContext);
            if (this.mStatusBarView.getLayoutParams().height != statusBarHeight) {
                this.mStatusBarView.getLayoutParams().height = statusBarHeight;
                return;
            }
            return;
        }
        this.mStatusBarView.setVisibility(8);
    }

    private void setTopNavigationBgTheme() {
        ThemeDataManager themeDataManager = ThemeDataManager.getInstance(this.mContext);
        if (themeDataManager.hasNewTheme()) {
            ThemeItemInfo itemInfo = themeDataManager.getContentInfo(ThemeDataManager.NAME_TOP_PIC);
            if (itemInfo != null) {
                if (VERSION.SDK_INT < 19 || !(this.mContext instanceof MainActivity)) {
                    this.mStatusBarView.setVisibility(8);
                    getLayoutParams().height = UIsUtils.dipToPx(44.0f);
                } else {
                    ((MainActivity) this.mContext).getWindow().addFlags(67108864);
                    int transprent = this.mContext.getResources().getColor(2131492951);
                    this.mNavView.setBackgroundColor(transprent);
                    this.mStatusBarView.setVisibility(0);
                    this.mStatusBarView.setBackgroundColor(transprent);
                }
                if (TextUtils.equals("1", itemInfo.mType)) {
                    setBackgroundDrawable(ThemeImageUtils.getImageDrawable(this.mContext, themeDataManager.getLargeBgRealUrl(itemInfo), itemInfo.mDefaultUnChecked));
                } else if (TextUtils.equals("2", itemInfo.mType)) {
                    themeDataManager.setColorTheme(this, itemInfo);
                }
            }
        } else if (VERSION.SDK_INT < 19 || !(this.mContext instanceof MainActivity)) {
            this.mStatusBarView.setVisibility(8);
        } else {
            this.mStatusBarView.setBackgroundColor(this.mContext.getResources().getColor(2131492950));
        }
    }
}
