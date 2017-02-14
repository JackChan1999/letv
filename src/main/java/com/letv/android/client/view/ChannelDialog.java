package com.letv.android.client.view;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.view.ChannelBaseDialog.MyGridViewAdapter;
import com.letv.core.bean.LiveBeanLeChannel;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.tencent.open.yyb.TitleBar;

public class ChannelDialog extends ChannelBaseDialog {
    public ChannelDialog(Context context, int theme, String type) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context, theme, type);
        initWinodw();
    }

    public ChannelDialog(Context context, String type) {
        super(context, type);
        initWinodw();
    }

    private void initWinodw() {
        Window window = getWindow();
        LayoutParams params = window.getAttributes();
        params.width = -1;
        params.height = -1;
        window.setAttributes(params);
    }

    protected void setLayoutViewAttribute() {
        this.mRoot.setBackgroundColor(this.mContext.getResources().getColor(2131492949));
        this.mTitle.setTextColor(this.mContext.getResources().getColor(2131492906));
        this.mTip.setTextColor(this.mContext.getResources().getColor(2131493426));
        this.mTip.setTextSize(1, TitleBar.BACKBTN_LEFT_MARGIN);
    }

    public void show() {
        super.show();
        if (this.mList != null && this.mList.size() > 0 && this.mTip != null) {
            this.mTip.setVisibility(8);
        }
    }

    protected void setGridAdpater() {
        this.mAdapter = new MyGridViewAdapter(this, this.mContext);
        this.mGridView.setAdapter(this.mAdapter);
        this.mGridView.setScrollbarFadingEnabled(true);
    }

    protected View getAdapterView(MyGridViewAdapter adapter, View contentView, int pos) {
        ViewHolder viewHolder;
        if (contentView == null) {
            viewHolder = new ViewHolder(this);
            contentView = UIsUtils.inflate(this.mContext, R.layout.dialog_channel_save_adapter, null);
            viewHolder.mGridLayout = (RelativeLayout) contentView.findViewById(R.id.gridViewAdpaterItemLayout);
            viewHolder.mGridLayout.setBackgroundResource(2130838140);
            viewHolder.mIcon = (ImageView) contentView.findViewById(R.id.saveIcon);
            viewHolder.mTitle = (TextView) contentView.findViewById(R.id.channelTitleText);
            viewHolder.mDivider = contentView.findViewById(R.id.verticalDivider);
            AbsListView.LayoutParams gridViewAdpaterItemLayoutParams = new AbsListView.LayoutParams(-1, -2);
            gridViewAdpaterItemLayoutParams.height = UIsUtils.zoomHeight(38);
            viewHolder.mGridLayout.setLayoutParams(gridViewAdpaterItemLayoutParams);
            viewHolder.mDivider.getLayoutParams().height = UIsUtils.zoomHeight(20);
            contentView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) contentView.getTag();
        }
        LiveBeanLeChannel liveLunboWeishi = (LiveBeanLeChannel) adapter.getItem(pos);
        if (liveLunboWeishi.saveFlag == 0) {
            viewHolder.mIcon.setSelected(false);
        } else {
            viewHolder.mIcon.setSelected(true);
        }
        if (liveLunboWeishi.numericKeys == null || liveLunboWeishi.numericKeys.equals("")) {
            if (liveLunboWeishi.channelName == null || liveLunboWeishi.channelName.equals("")) {
                viewHolder.mTitle.setText("");
            } else {
                viewHolder.mTitle.setText(liveLunboWeishi.channelName);
            }
        } else if (liveLunboWeishi.channelName == null || liveLunboWeishi.channelName.equals("")) {
            viewHolder.mTitle.setText("");
        } else {
            CharSequence charSequence;
            TextView textView = viewHolder.mTitle;
            if (Integer.valueOf(liveLunboWeishi.numericKeys).intValue() < 10) {
                charSequence = "0" + liveLunboWeishi.numericKeys + "\t" + liveLunboWeishi.channelName;
            } else {
                charSequence = liveLunboWeishi.numericKeys + "\t" + liveLunboWeishi.channelName;
            }
            textView.setText(charSequence);
        }
        return contentView;
    }

    protected void setDialogAttribute(View root) {
        requestWindowFeature(1);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(root);
    }
}
