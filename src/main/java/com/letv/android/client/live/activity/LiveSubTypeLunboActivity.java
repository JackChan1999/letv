package com.letv.android.client.live.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.LiveLunboAdapter.LiveProgram;
import com.letv.android.client.live.controller.LivePlayerController.FullScreenBtnClickEvent;
import com.letv.android.client.utils.LiveLaunchUtils;
import com.letv.android.client.view.ChannelDialog;
import com.letv.core.bean.LiveBeanLeChannel;
import com.letv.core.constant.DatabaseConstant.ChannelHisListTrace.Field;
import com.letv.core.constant.DatabaseConstant.ChannelListTrace.ChannelStatus;
import com.letv.core.constant.LiveRoomConstant;
import com.letv.core.db.LetvContentProvider;
import com.letv.core.utils.LiveLunboUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;

public class LiveSubTypeLunboActivity extends LiveSubTypeActivity {
    private int mActionType;
    private OnClickListener mAddToFavariteListener;
    private ChannelDialog mChannelDialog;
    private TextView mCurrentSelectTab;
    private ArrayList<LiveBeanLeChannel> mFavoriteData;
    private View mHeadTitleView;
    private ArrayList<LiveBeanLeChannel> mHistoryData;
    LoaderCallbacks<Cursor> mLiveLoaderCallback;
    private int mLoaderId;
    private LinearLayout mSaveLinearLayout;
    private OnClickListener mTabClickListener;
    private LinearLayout mTabTitleContainer;
    private ArrayList<RelativeLayout> mTitleList;

    public LiveSubTypeLunboActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mActionType = 1;
        this.mHistoryData = new ArrayList();
        this.mFavoriteData = new ArrayList();
        this.mTabClickListener = new OnClickListener(this) {
            final /* synthetic */ LiveSubTypeLunboActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                this.this$0.onTabClickAction(((Integer) v.getTag()).intValue());
                this.this$0.setTabViewSelected(v);
            }
        };
        this.mAddToFavariteListener = new OnClickListener(this) {
            final /* synthetic */ LiveSubTypeLunboActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                if (this.this$0.mChannelDialog.isShowing()) {
                    this.this$0.mChannelDialog.dismiss();
                } else {
                    this.this$0.mChannelDialog.show();
                }
            }
        };
        this.mLiveLoaderCallback = new LoaderCallbacks<Cursor>(this) {
            private int mActionType;
            final /* synthetic */ LiveSubTypeLunboActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
                this.mActionType = 1;
                if (bundle != null) {
                    this.mActionType = bundle.getInt("actionType");
                }
                String channelType = "";
                if (id == 1001) {
                    channelType = "lunbo";
                } else if (id == 1002) {
                    channelType = "weishi";
                } else if (id == 1004) {
                    channelType = "hk_movie";
                } else if (id == LiveRoomConstant.LIVE_ROOM_LOADER_HK_TVSERIES_ID) {
                    channelType = "hk_tvseries";
                } else if (id == LiveRoomConstant.LIVE_ROOM_LOADER_HK_VARIETY_ID) {
                    channelType = "hk_variety";
                } else if (id == LiveRoomConstant.LIVE_ROOM_LOADER_HK_MUSIC_ID) {
                    channelType = "hk_music";
                } else if (id == 1008) {
                    channelType = "hk_sports";
                }
                switch (this.mActionType) {
                    case 0:
                        return new CursorLoader(this.this$0.mContext, LetvContentProvider.URI_CHANNELHISLISTTRACE, null, "isRecord= ?  and channel_type=?", new String[]{"1", channelType}, Field.SYSTEMILLISECOND);
                    case 1:
                        return new CursorLoader(this.this$0.mContext, LetvContentProvider.URI_CHANNELHISLISTTRACE, null, null, null, "channelid");
                    case 2:
                        return new CursorLoader(this.this$0.mContext, LetvContentProvider.URI_CHANNELLISTTRACE, null, "hassave= ?  and channel_type=? AND channelstatus = ?", new String[]{"1", channelType, ChannelStatus.NORMAL}, null);
                    default:
                        return null;
                }
            }

            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                if (this.mActionType == 2) {
                    this.this$0.mFavoriteData.clear();
                } else if (this.mActionType == 0) {
                    this.this$0.mHistoryData.clear();
                }
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        try {
                            LiveBeanLeChannel channel = new LiveBeanLeChannel();
                            channel.channelId = cursor.getString(cursor.getColumnIndexOrThrow("channelid"));
                            if (loader.getId() == 1002) {
                                channel.numericKeys = "";
                            } else {
                                channel.numericKeys = cursor.getString(cursor.getColumnIndexOrThrow("numericKeys"));
                            }
                            channel.channelName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                            channel.channelEname = cursor.getString(cursor.getColumnIndexOrThrow("ename"));
                            channel.signal = cursor.getString(cursor.getColumnIndexOrThrow("signal"));
                            channel.channelIcon = cursor.getString(cursor.getColumnIndexOrThrow("channelIcon"));
                            if (this.mActionType == 0) {
                                channel.currentmillisecond = cursor.getLong(cursor.getColumnIndexOrThrow(Field.SYSTEMILLISECOND));
                            }
                            if (this.mActionType == 2) {
                                this.this$0.mFavoriteData.add(channel);
                            } else if (this.mActionType == 0) {
                                this.this$0.mHistoryData.add(channel);
                            }
                        } catch (SQLiteException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    if (this.mActionType == 2) {
                        this.this$0.mLunboAdapter.clear();
                        this.this$0.mLunboAdapter.addList(this.this$0.mFavoriteData);
                        if (this.this$0.mFavoriteData.size() == 0) {
                            this.this$0.mLunboAdapter.notifyDataSetChanged();
                        }
                    } else if (this.mActionType == 0) {
                        this.this$0.mLunboAdapter.clear();
                        this.this$0.mLunboAdapter.addList(this.this$0.mHistoryData);
                        if (this.this$0.mHistoryData.size() == 0) {
                            this.this$0.mLunboAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            public void onLoaderReset(Loader<Cursor> loader) {
            }
        };
    }

    public static void launch(Context context, int pageIndex) {
        Intent mIntent = new Intent(context, LiveSubTypeLunboActivity.class);
        mIntent.putExtra("pageIndex", pageIndex);
        context.startActivity(mIntent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mChannelDialog = new ChannelDialog(getActivity(), R.style.letv_soft_keyboard_dialog, LiveLunboUtils.getChannelDBType(this.pageIndex));
        initSecondaryTabs();
        if (this.pageIndex == 1) {
            this.mLoaderId = 1001;
        } else if (this.pageIndex == 2) {
            this.mLoaderId = 1002;
        } else if (this.pageIndex == 13) {
            this.mLoaderId = 1004;
        } else if (this.pageIndex == 14) {
            this.mLoaderId = LiveRoomConstant.LIVE_ROOM_LOADER_HK_TVSERIES_ID;
        } else if (this.pageIndex == 15) {
            this.mLoaderId = LiveRoomConstant.LIVE_ROOM_LOADER_HK_VARIETY_ID;
        } else if (this.pageIndex == 16) {
            this.mLoaderId = LiveRoomConstant.LIVE_ROOM_LOADER_HK_MUSIC_ID;
        } else if (this.pageIndex == 17) {
            this.mLoaderId = 1008;
        }
        getSupportLoaderManager().initLoader(this.mLoaderId, null, this.mLiveLoaderCallback);
        this.mListView.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ LiveSubTypeLunboActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (this.this$0.mPrograms != null && !this.this$0.mPrograms.isEmpty() && id >= 0) {
                    StatisticsUtils.setActionProperty("l09", position + 1, PageIdConstant.halpPlayPage);
                    LiveBeanLeChannel bean = (LiveBeanLeChannel) this.this$0.mListView.getItemAtPosition(position);
                    String eName = bean.channelEname;
                    String channelId = bean.channelId;
                    String cName = bean.channelName;
                    String numericKey = bean.numericKeys;
                    String signal = bean.signal;
                    String pName = null;
                    if (this.this$0.mPrograms.containsKey(channelId)) {
                        pName = ((LiveProgram) this.this$0.mPrograms.get(channelId)).mName;
                    }
                    if (this.this$0.pageIndex == 2) {
                        LiveLaunchUtils.launchLiveWeishi(this.this$0.mContext, eName, false, pName, cName, channelId, signal, false);
                        return;
                    }
                    LiveLaunchUtils.launchLiveLunbo(this.this$0.mContext, LiveLunboUtils.getLaunchMode(this.this$0.pageIndex), eName, false, pName, cName, channelId, numericKey, false);
                }
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        getLoaderManager().destroyLoader(this.mLoaderId);
    }

    private void initSecondaryTabs() {
        this.mHeadTitleView = UIsUtils.inflate(getActivity(), R.layout.half_lunbo_title, null);
        this.mTabTitleContainer = (LinearLayout) this.mHeadTitleView.findViewById(R.id.tabTitleContainer);
        LayoutParams tabTitlParams = new LayoutParams(-1, -2);
        tabTitlParams.height = UIsUtils.zoomWidth(40);
        this.mTabTitleContainer.setLayoutParams(tabTitlParams);
        String[] titles = getResources().getStringArray(R.array.channel_Titles);
        int j = titles.length;
        this.mTitleList = new ArrayList();
        for (int i = 0; i < j; i++) {
            RelativeLayout titleRelative = new RelativeLayout(getActivity());
            LayoutParams params = new LayoutParams(-1, -2);
            params.weight = 1.0f;
            titleRelative.setLayoutParams(params);
            TextView text = new TextView(getActivity());
            text.setBackgroundColor(getActivity().getResources().getColor(2131492949));
            text.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
            text.setGravity(17);
            text.setTextSize(1, 15.0f);
            text.setText(titles[i]);
            text.setTag(Integer.valueOf(i));
            if (i == 1) {
                this.mCurrentSelectTab = text;
                text.setTextColor(getResources().getColor(2131493202));
            } else {
                text.setTextColor(getResources().getColor(2131493022));
            }
            text.setOnClickListener(this.mTabClickListener);
            titleRelative.addView(text);
            this.mTabTitleContainer.addView(titleRelative);
            if (i < j - 1) {
                View lineView = new View(getActivity());
                lineView.setBackgroundColor(getResources().getColor(2131493321));
                RelativeLayout.LayoutParams lineParams = new RelativeLayout.LayoutParams(-1, -1);
                lineParams.width = 1;
                lineParams.height = UIsUtils.zoomWidth(24);
                lineView.setLayoutParams(lineParams);
                this.mTabTitleContainer.addView(lineView);
            }
            this.mTitleList.add(titleRelative);
        }
        this.mSaveLinearLayout = (LinearLayout) this.mHeadTitleView.findViewById(R.id.saveRootLinear);
        LinearLayout saveChildLayout = (LinearLayout) this.mHeadTitleView.findViewById(R.id.saveChildLinear);
        LayoutParams saveLinearParams = (LayoutParams) saveChildLayout.getLayoutParams();
        saveLinearParams.height = UIsUtils.zoomWidth(65);
        saveChildLayout.setLayoutParams(saveLinearParams);
        saveChildLayout.setPadding(UIsUtils.zoomWidth(15), 0, 0, 0);
        this.mSaveLinearLayout.setOnClickListener(this.mAddToFavariteListener);
        this.mListView.addHeaderView(this.mHeadTitleView);
    }

    protected void handleFullScreenEvent(FullScreenBtnClickEvent fullScreenEvent) {
        super.handleFullScreenEvent(fullScreenEvent);
        if (fullScreenEvent.isFull) {
            if (this.mHeadTitleView != null) {
                this.mListView.removeHeaderView(this.mHeadTitleView);
            }
            this.mListView.requestLayout();
        } else if (this.mHeadTitleView != null) {
            this.mListView.addHeaderView(this.mHeadTitleView);
            forceUpdate(this.mCurrentActionType);
        }
    }

    private void setTabViewSelected(View v) {
        TextView tv = (TextView) v;
        if (this.mCurrentSelectTab == null) {
            this.mCurrentSelectTab = tv;
        }
        tv.setTextColor(getResources().getColor(2131493202));
        if (this.mCurrentSelectTab != tv) {
            this.mCurrentSelectTab.setTextColor(getResources().getColor(2131493022));
        }
        this.mCurrentSelectTab = tv;
    }

    private void onTabClickAction(int index) {
        if (index != this.mCurrentActionType) {
            forceUpdate(index);
        }
    }

    private void forceUpdate(int index) {
        this.mCurrentActionType = index;
        Bundle bundle = new Bundle();
        bundle.putInt("actionType", index);
        switch (this.mCurrentActionType) {
            case 0:
                this.mActionType = 0;
                this.mSaveLinearLayout.setVisibility(8);
                getSupportLoaderManager().restartLoader(this.mLoaderId, bundle, this.mLiveLoaderCallback);
                return;
            case 1:
                this.mActionType = 1;
                this.mLunboAdapter.clear();
                this.mLunboAdapter.addList(this.mLunboData);
                this.mSaveLinearLayout.setVisibility(8);
                getSupportLoaderManager().restartLoader(this.mLoaderId, bundle, this.mLiveLoaderCallback);
                return;
            case 2:
                this.mActionType = 2;
                this.mSaveLinearLayout.setVisibility(0);
                getSupportLoaderManager().restartLoader(this.mLoaderId, bundle, this.mLiveLoaderCallback);
                return;
            default:
                return;
        }
    }

    public void onClick(View v) {
        super.onClick(v);
    }
}
