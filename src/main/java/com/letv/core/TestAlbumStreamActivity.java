package com.letv.core;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.letv.base.R;

public class TestAlbumStreamActivity extends Activity {
    public static boolean s720Back0 = true;
    public static boolean s720Back1 = true;
    public static boolean s720Back2 = true;
    public static boolean s720Main = true;
    public static boolean sHighBack0 = true;
    public static boolean sHighBack1 = true;
    public static boolean sHighBack2 = true;
    public static boolean sHighMain = true;
    public static boolean sLowBack0 = true;
    public static boolean sLowBack1 = true;
    public static boolean sLowBack2 = true;
    public static boolean sLowMain = true;
    public static boolean sStandardBack0 = true;
    public static boolean sStandardBack1 = true;
    public static boolean sStandardBack2 = true;
    public static boolean sStandardMain = true;
    public static boolean sSuperBack0 = true;
    public static boolean sSuperBack1 = true;
    public static boolean sSuperBack2 = true;
    public static boolean sSuperMain = true;
    private CheckBox m720Back0;
    private CheckBox m720Back1;
    private CheckBox m720Back2;
    private CheckBox m720Main;
    private CheckBox mHighBack0;
    private CheckBox mHighBack1;
    private CheckBox mHighBack2;
    private CheckBox mHighMain;
    private CheckBox mLowBack0;
    private CheckBox mLowBack1;
    private CheckBox mLowBack2;
    private CheckBox mLowMain;
    private CheckBox mStandardBack0;
    private CheckBox mStandardBack1;
    private CheckBox mStandardBack2;
    private CheckBox mStandardMain;
    private CheckBox mSuperBack0;
    private CheckBox mSuperBack1;
    private CheckBox mSuperBack2;
    private CheckBox mSuperMain;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_album_retry_layout);
        init();
    }

    private void init() {
        this.m720Main = (CheckBox) findViewById(R.id.stream_720_main);
        this.m720Back0 = (CheckBox) findViewById(R.id.stream_720_back0);
        this.m720Back1 = (CheckBox) findViewById(R.id.stream_720_back1);
        this.m720Back2 = (CheckBox) findViewById(R.id.stream_720_back2);
        this.mSuperMain = (CheckBox) findViewById(R.id.stream_super_main);
        this.mSuperBack0 = (CheckBox) findViewById(R.id.stream_super_back0);
        this.mSuperBack1 = (CheckBox) findViewById(R.id.stream_super_back1);
        this.mSuperBack2 = (CheckBox) findViewById(R.id.stream_super_back2);
        this.mHighMain = (CheckBox) findViewById(R.id.stream_high_main);
        this.mHighBack0 = (CheckBox) findViewById(R.id.stream_high_back0);
        this.mHighBack1 = (CheckBox) findViewById(R.id.stream_high_back1);
        this.mHighBack2 = (CheckBox) findViewById(R.id.stream_high_back2);
        this.mStandardMain = (CheckBox) findViewById(R.id.stream_standard_main);
        this.mStandardBack0 = (CheckBox) findViewById(R.id.stream_standard_back0);
        this.mStandardBack1 = (CheckBox) findViewById(R.id.stream_standard_back1);
        this.mStandardBack2 = (CheckBox) findViewById(R.id.stream_standard_back2);
        this.mLowMain = (CheckBox) findViewById(R.id.stream_low_main);
        this.mLowBack0 = (CheckBox) findViewById(R.id.stream_low_back0);
        this.mLowBack1 = (CheckBox) findViewById(R.id.stream_low_back1);
        this.mLowBack2 = (CheckBox) findViewById(R.id.stream_low_back2);
        this.m720Main.setChecked(s720Main);
        this.m720Back0.setChecked(s720Back0);
        this.m720Back1.setChecked(s720Back1);
        this.m720Back2.setChecked(s720Back2);
        this.mSuperMain.setChecked(sSuperMain);
        this.mSuperBack0.setChecked(sSuperBack0);
        this.mSuperBack1.setChecked(sSuperBack1);
        this.mSuperBack2.setChecked(sSuperBack2);
        this.mHighMain.setChecked(sHighMain);
        this.mHighBack0.setChecked(sHighBack0);
        this.mHighBack1.setChecked(sHighBack1);
        this.mHighBack2.setChecked(sHighBack2);
        this.mStandardMain.setChecked(sStandardMain);
        this.mStandardBack0.setChecked(sStandardBack0);
        this.mStandardBack1.setChecked(sStandardBack1);
        this.mStandardBack2.setChecked(sStandardBack2);
        this.mLowMain.setChecked(sLowMain);
        this.mLowBack0.setChecked(sLowBack0);
        this.mLowBack1.setChecked(sLowBack1);
        this.mLowBack2.setChecked(sLowBack2);
        this.m720Main.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.s720Main = isChecked;
            }
        });
        this.m720Back0.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.s720Back0 = isChecked;
            }
        });
        this.m720Back1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.s720Back1 = isChecked;
            }
        });
        this.m720Back2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.s720Back2 = isChecked;
            }
        });
        this.mSuperMain.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.sSuperMain = isChecked;
            }
        });
        this.mSuperBack0.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.sSuperBack0 = isChecked;
            }
        });
        this.mSuperBack1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.sSuperBack1 = isChecked;
            }
        });
        this.mSuperBack2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.sSuperBack2 = isChecked;
            }
        });
        this.mHighMain.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.sHighMain = isChecked;
            }
        });
        this.mHighBack0.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.sHighBack0 = isChecked;
            }
        });
        this.mHighBack1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.sHighBack1 = isChecked;
            }
        });
        this.mHighBack2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.sHighBack2 = isChecked;
            }
        });
        this.mStandardMain.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.sStandardMain = isChecked;
            }
        });
        this.mStandardBack0.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.sStandardBack0 = isChecked;
            }
        });
        this.mStandardBack1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.sStandardBack1 = isChecked;
            }
        });
        this.mStandardBack2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.sStandardBack2 = isChecked;
            }
        });
        this.mLowMain.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.sLowMain = isChecked;
            }
        });
        this.mLowBack0.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.sLowBack0 = isChecked;
            }
        });
        this.mLowBack1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.sLowBack1 = isChecked;
            }
        });
        this.mLowBack2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TestAlbumStreamActivity.sLowBack2 = isChecked;
            }
        });
    }
}
