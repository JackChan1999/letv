package com.letv.android.client.commonlib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.commonlib.R;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;

public class ChannelListFootView extends LinearLayout {
    private RelativeLayout finishNoReply;
    private LinearLayout loadingLayout;
    private TextView noMoreInfo;
    private RelativeLayout noMoreLayout;
    private TextView noMoreOrErrorView;
    private LinearLayout refreshLayout;
    public State state = State.REFRESH;

    public ChannelListFootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChannelListFootView(Context context) {
        super(context);
        init(context);
    }

    protected void init(Context context) {
        View view = UIsUtils.inflate(context, R.layout.channel_listview_foot_playerlibs, null);
        view.setLayoutParams(new LayoutParams(-1, UIsUtils.dipToPx(40.0f)));
        this.loadingLayout = (LinearLayout) view.findViewById(R.id.loading_layout);
        this.refreshLayout = (LinearLayout) view.findViewById(R.id.refresh_layout);
        this.noMoreLayout = (RelativeLayout) view.findViewById(R.id.no_more_or_error_layout);
        this.finishNoReply = (RelativeLayout) view.findViewById(R.id.rl_no_more_reply_layout);
        this.noMoreInfo = (TextView) view.findViewById(R.id.tv_no_more_info);
        this.noMoreOrErrorView = (TextView) view.findViewById(R.id.no_more_or_error_text);
        addView(view);
    }

    public void showLoading() {
        this.state = State.LOADING;
        setVisibility(0);
        this.loadingLayout.setVisibility(0);
        this.refreshLayout.setVisibility(8);
        this.noMoreLayout.setVisibility(8);
    }

    public void showRefresh() {
        setVisibility(0);
        this.state = State.REFRESH;
        this.refreshLayout.setVisibility(0);
        this.loadingLayout.setVisibility(8);
        this.noMoreLayout.setVisibility(8);
    }

    public void showNoMore() {
        showNoMore(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700071, R.string.no_more));
    }

    public void showNoMore(String stringTip) {
        setVisibility(0);
        this.state = State.NOMORE;
        this.noMoreOrErrorView.setText(stringTip);
        this.noMoreLayout.setVisibility(0);
        this.loadingLayout.setVisibility(8);
        this.refreshLayout.setVisibility(8);
    }

    public void showNoMoreForReply(String stringTip) {
        setVisibility(0);
        this.state = State.NOMORE;
        this.noMoreOrErrorView.setVisibility(8);
        this.noMoreLayout.setVisibility(8);
        this.finishNoReply.setVisibility(0);
        this.noMoreInfo.setText(stringTip);
        this.loadingLayout.setVisibility(8);
        this.refreshLayout.setVisibility(8);
    }

    public void showError() {
        showError(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700071, R.string.dialog_loading_fail));
    }

    public void showError(String errorTip) {
        setVisibility(0);
        this.state = State.ERROR;
        this.noMoreOrErrorView.setText(errorTip);
        this.noMoreLayout.setVisibility(0);
        this.loadingLayout.setVisibility(8);
        this.refreshLayout.setVisibility(8);
    }

    public void destroy() {
        this.loadingLayout = null;
        this.refreshLayout = null;
        removeAllViewsInLayout();
    }
}
