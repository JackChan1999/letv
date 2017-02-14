package com.letv.android.client.activity;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.channel.ChannelFragmentAdapter;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.listener.ItemDragHelperCallback;
import com.letv.android.client.task.ChannelWallFetcherTask;
import com.letv.android.client.task.ChannelWallFetcherTask.ChannelListCallback;
import com.letv.core.BaseApplication;
import com.letv.core.bean.ChannelListBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class ChannelWallActivity extends LetvBaseActivity implements OnClickListener {
    private ChannelFragmentAdapter mAdapter;
    private ImageView mBtnBack;
    private ChannelListBean mChannelListBean;
    private boolean mFromMineCustom;
    private boolean mIsEdit;
    private RelativeLayout mNavigation;
    private RecyclerView mRecyclerView;
    private TextView mSaveText;
    private RelativeLayout mTeach1;
    private RelativeLayout mTeach2;
    private RelativeLayout mTeachLayout;
    private TextView mTitleText;
    private SpaceItemDecoration spaceItemDecoration;

    public class SpaceItemDecoration extends ItemDecoration {
        private int space;
        final /* synthetic */ ChannelWallActivity this$0;

        public SpaceItemDecoration(ChannelWallActivity this$0, int space) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
            this.space = space;
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
            if (parent.getChildLayoutPosition(view) != 0) {
                outRect.set(this.space, this.space, this.space, this.space);
            }
        }

        private int getSpanSize(int itemPosition) {
            int viewType = this.this$0.mAdapter.getItemViewType(itemPosition);
            if (viewType == 2 || viewType == 1) {
                return 1;
            }
            return 4;
        }
    }

    public ChannelWallActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.spaceItemDecoration = new SpaceItemDecoration(this, UIsUtils.dipToPx(5.0f));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_wall);
        this.mFromMineCustom = getIntent().getBooleanExtra("fromMine", false);
        findView();
        initData();
    }

    private void findView() {
        this.mNavigation = (RelativeLayout) findViewById(R.id.my_navigation);
        this.mNavigation.bringToFront();
        this.mTitleText = (TextView) findViewById(2131362352);
        this.mTitleText.setText(2131099817);
        this.mBtnBack = (ImageView) findViewById(2131362351);
        this.mBtnBack.setOnClickListener(this);
        this.mSaveText = (TextView) findViewById(2131362354);
        this.mSaveText.setText(2131100474);
        this.mSaveText.setVisibility(0);
        this.mSaveText.setOnClickListener(this);
        this.mSaveText.setTextColor(this.mContext.getResources().getColor(2131493261));
        this.mSaveText.setTextSize(15.0f);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recy);
        this.mRecyclerView.removeItemDecoration(this.spaceItemDecoration);
        this.mRecyclerView.addItemDecoration(this.spaceItemDecoration);
        if (!PreferencesManager.getInstance().getHasShowTeach()) {
            this.mTeach1 = (RelativeLayout) findViewById(R.id.channel_teach_1);
            this.mTeach2 = (RelativeLayout) findViewById(R.id.channel_teach_2);
            this.mTeachLayout = (RelativeLayout) findViewById(R.id.teach_layout);
            this.mTeachLayout.setVisibility(0);
            this.mTeach1.setVisibility(0);
            this.mTeachLayout.setOnClickListener(this);
        }
    }

    private void init() {
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        this.mRecyclerView.setLayoutManager(manager);
        ItemTouchHelper helper = new ItemTouchHelper(new ItemDragHelperCallback());
        helper.attachToRecyclerView(this.mRecyclerView);
        this.mAdapter = new ChannelFragmentAdapter(this, helper, this.mChannelListBean, this.mRecyclerView);
        this.mAdapter.setFrom(this.mFromMineCustom);
        manager.setSpanSizeLookup(new SpanSizeLookup(this) {
            final /* synthetic */ ChannelWallActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public int getSpanSize(int position) {
                int viewType = this.this$0.mAdapter.getItemViewType(position);
                if (viewType == 2 || viewType == 1) {
                    return 1;
                }
                return 4;
            }
        });
        this.mRecyclerView.setAdapter(this.mAdapter);
    }

    private void initData() {
        ChannelWallFetcherTask.getInstance().fetchChannelWall(BaseApplication.getInstance(), new ChannelListCallback(this) {
            final /* synthetic */ ChannelWallActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onFetch(ChannelListBean channelList) {
                if (channelList != null && !BaseTypeUtils.isListEmpty(channelList.listChannel)) {
                    this.this$0.mChannelListBean = channelList;
                    this.this$0.init();
                }
            }
        });
        StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.categoryPage, "19", null, null, -1, null);
    }

    public void onClick(View v) {
        boolean z = true;
        ChannelFragmentAdapter channelFragmentAdapter;
        switch (v.getId()) {
            case R.id.teach_layout /*2131361921*/:
                if (this.mTeach1.getVisibility() == 0) {
                    this.mTeach1.setVisibility(8);
                    this.mTeach2.setVisibility(0);
                    return;
                } else if (this.mTeach2.getVisibility() == 0) {
                    this.mTeachLayout.setVisibility(8);
                    PreferencesManager.getInstance().setHasShowTeach(true);
                    return;
                } else {
                    return;
                }
            case 2131362351:
                if (this.mIsEdit) {
                    channelFragmentAdapter = this.mAdapter;
                    if (this.mIsEdit) {
                        z = false;
                    }
                    updateEditState(channelFragmentAdapter.notifiDataChange(z));
                    return;
                }
                backToHomeFragment();
                return;
            case 2131362354:
                this.mAdapter.statisticsPositionChange();
                channelFragmentAdapter = this.mAdapter;
                if (this.mIsEdit) {
                    z = false;
                }
                updateEditState(channelFragmentAdapter.notifiDataChange(z));
                return;
            default:
                return;
        }
    }

    public void updateEditState(boolean isEdit) {
        this.mIsEdit = isEdit;
        this.mSaveText.setText(this.mIsEdit ? 2131099789 : 2131100474);
    }

    private void backToHomeFragment() {
        if (this.mAdapter != null) {
            this.mAdapter.setResult("");
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && this.mIsEdit) {
            updateEditState(this.mAdapter.notifiDataChange(!this.mIsEdit));
            return true;
        }
        backToHomeFragment();
        return super.onKeyDown(keyCode, event);
    }

    public String[] getAllFragmentTags() {
        return null;
    }

    public Activity getActivity() {
        return this;
    }

    public String getActivityName() {
        return ChannelWallActivity.class.getName();
    }
}
