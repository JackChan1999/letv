package com.letv.android.client.view.channel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.activity.ChannelDetailItemActivity;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.adapter.LiveRoomAdapter;
import com.letv.android.client.commonlib.config.MainActivityConfig;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChannelLivehallView extends FrameLayout {
    private View channelListLiveHallLayout;
    private ListView liveHallList;
    private Set<String> mBookedPrograms;
    private int mChannelId;
    private LiveRoomAdapter mChannelLiveHallAdapter;
    private Context mContext;
    private List<LiveRemenBaseBean> mLiveSportsList;
    private TextView mTitleView;

    public ChannelLivehallView(Context context, List<LiveRemenBaseBean> mLiveSportsList) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.mBookedPrograms = new HashSet();
        this.mContext = context;
        this.mLiveSportsList = mLiveSportsList;
        init(context);
    }

    public void setList(List<LiveRemenBaseBean> liveSportsList) {
        if (BaseTypeUtils.isListEmpty(liveSportsList)) {
            this.liveHallList.setVisibility(8);
            this.channelListLiveHallLayout.setVisibility(8);
        } else {
            this.liveHallList.setVisibility(0);
            this.channelListLiveHallLayout.setVisibility(0);
            LayoutParams layoutParams = (LayoutParams) this.liveHallList.getLayoutParams();
            layoutParams.height = ((int) this.mContext.getResources().getDimension(2131165406)) * liveSportsList.size();
            this.liveHallList.setLayoutParams(layoutParams);
        }
        this.mLiveSportsList = liveSportsList;
        this.mChannelLiveHallAdapter.setList(this.mLiveSportsList);
    }

    protected void init(Context context) {
        inflate(context, R.layout.channel_detail_live_hall_layout, this);
        findView();
    }

    private void findView() {
        this.channelListLiveHallLayout = findViewById(R.id.channel_live_hall_txt);
        this.liveHallList = (ListView) findViewById(R.id.channel_live_hall_list);
        this.mTitleView = (TextView) findViewById(R.id.group_item_title);
        this.mChannelLiveHallAdapter = new LiveRoomAdapter(this.mContext);
        this.mChannelLiveHallAdapter.setFrom(2);
        this.liveHallList.setAdapter(this.mChannelLiveHallAdapter);
        this.channelListLiveHallLayout.setOnClickListener(new 1(this));
    }

    public void doClick() {
        String type = "";
        switch (this.mChannelId) {
            case 4:
                type = "sports";
                break;
            case 9:
                type = "music";
                break;
            case 104:
                type = "game";
                break;
            case 1009:
                type = "information";
                break;
            default:
                return;
        }
        if (this.mContext instanceof MainActivity) {
            ((MainActivity) this.mContext).gotoLiveFragment(type);
        } else if (this.mContext instanceof ChannelDetailItemActivity) {
            Intent intent = new Intent(this.mContext, MainActivity.class);
            intent.putExtra("tag", FragmentConstant.TAG_FRAGMENT_LIVE);
            intent.putExtra(MainActivityConfig.CHILD_LIVE_ID, type);
            this.mContext.startActivity(intent);
        }
        StatisticsUtils.staticticsInfoPost(this.mContext, "218", "体育频道更多", 0, -1, null, null, null, null, null);
    }

    public void setTitleAndCmsId(String name, int id) {
        this.mChannelId = id;
    }

    public void clear() {
        if (this.mLiveSportsList != null) {
            this.mLiveSportsList.clear();
        }
        this.mBookedPrograms.clear();
        this.mChannelLiveHallAdapter.clear();
        this.channelListLiveHallLayout = null;
    }

    public void setBookedPrograms(Set<String> bookedPrograms) {
        this.mChannelLiveHallAdapter.setBookedPrograms(bookedPrograms);
        this.mBookedPrograms = bookedPrograms;
    }

    public void clearBookedPrograms() {
        this.mBookedPrograms.clear();
    }
}
