package com.letv.android.client.view;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.utils.UIs;
import com.letv.core.bean.LiveBeanLeChannel;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.List;

public abstract class ChannelBaseDialog extends Dialog {
    protected MyGridViewAdapter mAdapter;
    protected ImageView mBack;
    protected String mChannelType;
    protected Context mContext;
    protected View mDash;
    protected DialogCallBackListener mDialogCallBackListener;
    protected GridView mGridView;
    protected List<LiveBeanLeChannel> mList;
    private OnItemClickListener mOnItemClickListener;
    protected View mRoot;
    private int mSaveIndex;
    protected RelativeLayout mStatus;
    protected TextView mTip;
    protected TextView mTitle;
    protected RelativeLayout mTopLayout;

    protected abstract View getAdapterView(MyGridViewAdapter myGridViewAdapter, View view, int i);

    protected abstract void setDialogAttribute(View view);

    protected abstract void setGridAdpater();

    protected abstract void setLayoutViewAttribute();

    public ChannelBaseDialog(Context context, int theme, String type) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context, theme);
        this.mOnItemClickListener = new 2(this);
        this.mChannelType = type;
        init(context);
    }

    public ChannelBaseDialog(Context context, String type) {
        super(context);
        this.mOnItemClickListener = new 2(this);
        this.mChannelType = type;
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.mRoot = UIs.inflate(context, R.layout.dialog_channel_save_layout, null);
        this.mBack = (ImageView) this.mRoot.findViewById(2131362471);
        this.mGridView = (GridView) this.mRoot.findViewById(R.id.gridView);
        this.mTopLayout = (RelativeLayout) this.mRoot.findViewById(R.id.topLayout);
        this.mDash = this.mRoot.findViewById(R.id.toplayout_dashed);
        this.mTitle = (TextView) this.mRoot.findViewById(R.id.dialogTitle);
        this.mStatus = (RelativeLayout) this.mRoot.findViewById(R.id.statusRelative);
        this.mTip = (TextView) this.mRoot.findViewById(R.id.tipTv);
        setLayoutViewAttribute();
        setGridAdpater();
        this.mGridView.setOnItemClickListener(this.mOnItemClickListener);
        this.mBack.setOnClickListener(new 1(this));
        setDialogAttribute(this.mRoot);
    }

    public void setDialogCallBackListener(DialogCallBackListener mDialogCallBackListener) {
        this.mDialogCallBackListener = mDialogCallBackListener;
    }

    public void show() {
        super.show();
        new 3(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public void dismiss() {
        super.dismiss();
        if (this.mList != null) {
            this.mList.clear();
            this.mList = null;
        }
        this.mSaveIndex = 0;
    }

    private void dataErrorView(String msg) {
        this.mGridView.setVisibility(8);
        this.mStatus.setVisibility(0);
        this.mTip.setVisibility(0);
        this.mTip.setText(msg);
    }

    private void setTitle() {
        int i = 0;
        Context context = this.mContext;
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(this.mSaveIndex);
        if (this.mList != null) {
            i = this.mList.size();
        }
        objArr[1] = Integer.valueOf(i);
        this.mTitle.setText(context.getString(2131100778, objArr).replace("!1", "").replace("!2", ""));
    }
}
