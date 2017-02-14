package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.MyFavoriteGridViewAdapter;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.FavoriteDataContainerBean;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.parser.MyFavoriteContentBeanParser;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MyFavoriteActivity extends Activity {
    private String LOG_TAG;
    private final int UPDATE_UI;
    private RelativeLayout mContentLayout;
    private Context mContext;
    private Button mFavoriteButton;
    private GridView mFavoriteGridView;
    private MyFavoriteGridViewAdapter mFavoriteGridViewAdapter;
    private TextView mFavoriteLoading;
    private RelativeLayout mFavoriteNetError;
    private ImageView mFavoriteTitle;
    private Handler mHandler;

    public MyFavoriteActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.UPDATE_UI = 1;
        this.LOG_TAG = "MyFavoriteActivity";
        this.mContext = this;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_favorite_grideview);
        this.mContentLayout = (RelativeLayout) findViewById(R.id.my_content_layout);
        this.mFavoriteGridView = (GridView) findViewById(R.id.my_favorite_gridview);
        this.mFavoriteGridView.setSelector(new ColorDrawable(0));
        this.mFavoriteButton = (Button) findViewById(R.id.goahead_button);
        this.mFavoriteNetError = (RelativeLayout) findViewById(R.id.net_error);
        ((TextView) this.mFavoriteNetError.findViewById(2131361891)).setText(getResources().getString(2131100689).replace("#", "\n"));
        this.mFavoriteTitle = (ImageView) findViewById(R.id.page_title);
        this.mHandler = new Handler(this) {
            final /* synthetic */ MyFavoriteActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        this.this$0.mFavoriteNetError.setVisibility(0);
                        this.this$0.mFavoriteLoading.setVisibility(8);
                        return;
                    default:
                        LogInfo.log(this.this$0.LOG_TAG, "default");
                        return;
                }
            }
        };
        this.mFavoriteLoading = (TextView) findViewById(2131361884);
        this.mFavoriteLoading.setVisibility(0);
        this.mFavoriteButton.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ MyFavoriteActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View view) {
                Exception e;
                Throwable th;
                if (MyFavoriteGridViewAdapter.sSaveItemList != null && MyFavoriteGridViewAdapter.sSaveItemList.size() > 0) {
                    StringBuilder builder;
                    ObjectOutputStream outputStreams = null;
                    try {
                        ObjectOutputStream outputStreams2 = new ObjectOutputStream(this.this$0.mContext.openFileOutput("MyFavoriteItems.txt", 0));
                        try {
                            outputStreams2.writeObject(MyFavoriteGridViewAdapter.sSaveItemList);
                            outputStreams2.flush();
                            try {
                                outputStreams2.close();
                                outputStreams = outputStreams2;
                            } catch (IOException e2) {
                                e2.printStackTrace();
                                outputStreams = outputStreams2;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            outputStreams = outputStreams2;
                            try {
                                e.printStackTrace();
                                try {
                                    outputStreams.close();
                                } catch (IOException e22) {
                                    e22.printStackTrace();
                                }
                                builder = new StringBuilder("nid=");
                                for (String str : MyFavoriteGridViewAdapter.sSaveItemList) {
                                    builder.append(str);
                                }
                                StatisticsUtils.statisticsActionInfo(this.this$0.mContext, PageIdConstant.newFeaturePage, "0", "ns01", null, 1, builder.toString());
                                this.this$0.goMainActivity();
                            } catch (Throwable th2) {
                                th = th2;
                                try {
                                    outputStreams.close();
                                } catch (IOException e222) {
                                    e222.printStackTrace();
                                }
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            outputStreams = outputStreams2;
                            outputStreams.close();
                            throw th;
                        }
                    } catch (Exception e4) {
                        e = e4;
                        e.printStackTrace();
                        outputStreams.close();
                        builder = new StringBuilder("nid=");
                        while (r0.hasNext()) {
                            builder.append(str);
                        }
                        StatisticsUtils.statisticsActionInfo(this.this$0.mContext, PageIdConstant.newFeaturePage, "0", "ns01", null, 1, builder.toString());
                        this.this$0.goMainActivity();
                    }
                    builder = new StringBuilder("nid=");
                    while (r0.hasNext()) {
                        builder.append(str);
                    }
                    StatisticsUtils.statisticsActionInfo(this.this$0.mContext, PageIdConstant.newFeaturePage, "0", "ns01", null, 1, builder.toString());
                }
                this.this$0.goMainActivity();
            }
        });
        this.mFavoriteNetError.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ MyFavoriteActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View view) {
                if (NetworkUtils.isNetworkAvailable()) {
                    this.this$0.mFavoriteNetError.setVisibility(8);
                    this.this$0.mFavoriteLoading.setVisibility(0);
                    DisplayMetrics mDm = new DisplayMetrics();
                    ((WindowManager) this.this$0.mContext.getSystemService("window")).getDefaultDisplay().getMetrics(mDm);
                    int mWidth = mDm.widthPixels;
                    this.this$0.mFavoriteGridViewAdapter = new MyFavoriteGridViewAdapter(this.this$0.mContext, mDm.heightPixels, mWidth, this.this$0.mFavoriteButton);
                    this.this$0.requestFavoriteDataTask(RequestManner.NETWORK_ONLY);
                    this.this$0.mFavoriteGridView.setColumnWidth(mWidth / 3);
                    this.this$0.mFavoriteGridView.setVerticalSpacing(1);
                    this.this$0.mFavoriteGridView.setOnItemClickListener(this.this$0.mFavoriteGridViewAdapter);
                    return;
                }
                this.this$0.mFavoriteNetError.setVisibility(8);
                this.this$0.mFavoriteLoading.setVisibility(0);
                Message message = new Message();
                message.what = 1;
                this.this$0.mHandler.sendEmptyMessageDelayed(message.what, 1000);
            }
        });
        DisplayMetrics mDm = new DisplayMetrics();
        ((WindowManager) getSystemService("window")).getDefaultDisplay().getMetrics(mDm);
        int mWidth = mDm.widthPixels;
        this.mFavoriteGridViewAdapter = new MyFavoriteGridViewAdapter(this, mDm.heightPixels, mWidth, this.mFavoriteButton);
        requestFavoriteDataTask(RequestManner.NETWORK_ONLY);
        this.mFavoriteGridView.setColumnWidth(mWidth / 3);
        this.mFavoriteGridView.setVerticalSpacing(1);
        this.mFavoriteGridView.setOnItemClickListener(this.mFavoriteGridViewAdapter);
    }

    protected void goMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void requestFavoriteDataTask(RequestManner requestType) {
        new LetvRequest(FavoriteDataContainerBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(LetvUrlMaker.getFavoriteUrl()).setParser(new MyFavoriteContentBeanParser()).setCallback(new SimpleResponse<FavoriteDataContainerBean>(this) {
            final /* synthetic */ MyFavoriteActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<FavoriteDataContainerBean> volleyRequest, FavoriteDataContainerBean result, DataHull hull, NetworkResponseState state) {
                if (state == NetworkResponseState.SUCCESS) {
                    Log.i("hzz", "favorit = " + LetvUrlMaker.getFavoriteUrl());
                    this.this$0.mFavoriteGridViewAdapter.setList(result.mFavoriteContentBeanList);
                    this.this$0.mFavoriteGridView.setAdapter(this.this$0.mFavoriteGridViewAdapter);
                    this.this$0.mFavoriteLoading.setVisibility(8);
                    this.this$0.mFavoriteNetError.setVisibility(8);
                    this.this$0.mFavoriteGridView.setVisibility(0);
                    return;
                }
                this.this$0.mFavoriteLoading.setVisibility(8);
                this.this$0.mFavoriteNetError.setVisibility(0);
            }
        }).add();
    }
}
