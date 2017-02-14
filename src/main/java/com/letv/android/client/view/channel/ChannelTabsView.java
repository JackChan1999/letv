package com.letv.android.client.view.channel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import com.letv.android.client.R;
import com.letv.core.bean.ChannelListBean.Channel;
import com.letv.core.bean.channel.ChannelNavigation;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.tencent.open.yyb.TitleBar;
import java.util.ArrayList;

public class ChannelTabsView {
    private final int COLUMN_NUM;
    private final int ROW_HEIGHT;
    private final int ROW_SPACING;
    private TabsListAdapter mAdapter;
    private Channel mChannel;
    private Context mContext;
    private ChannelTabGridView mGridView;
    private View mView;

    public ChannelTabsView(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.COLUMN_NUM = 4;
        this.ROW_HEIGHT = UIsUtils.dipToPx(30.0f);
        this.ROW_SPACING = UIsUtils.dipToPx(TitleBar.SHAREBTN_RIGHT_MARGIN);
        this.mContext = context;
        init();
    }

    private void init() {
        this.mView = LayoutInflater.from(this.mContext).inflate(R.layout.channel_detail_home_tabs, null);
        this.mGridView = (ChannelTabGridView) this.mView.findViewById(R.id.channel_detail_home_tabs_gridview);
        this.mAdapter = new TabsListAdapter(this, this.mContext);
        this.mGridView.setAdapter(this.mAdapter);
    }

    public View getView() {
        return this.mView;
    }

    public ChannelTabGridView getmGridView() {
        return this.mGridView;
    }

    public void showView(boolean show) {
        this.mView.setVisibility(show ? 0 : 8);
    }

    public void setTabs(ArrayList<ChannelNavigation> navigations, Channel mChannel) {
        if (!BaseTypeUtils.isListEmpty(navigations)) {
            int row = ((navigations.size() + 4) - 1) / 4;
            int height = (this.ROW_HEIGHT * row) + ((row - 1) * this.ROW_SPACING);
            if (this.mGridView.getLayoutParams() != null) {
                this.mGridView.getLayoutParams().height = height;
            } else {
                this.mGridView.setLayoutParams(new LayoutParams(UIsUtils.getScreenWidth(), height));
            }
        }
        this.mChannel = mChannel;
        this.mAdapter.setData(navigations);
    }
}
