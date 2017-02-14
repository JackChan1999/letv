package com.letv.android.client.commonlib.view;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.commonlib.R;
import com.letv.core.bean.LetvBaseBean;
import com.letv.core.bean.RedPacketBean;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataUtils;

public class RedPacketDialog extends Dialog {
    public static boolean mIsFirstOpen = true;
    public static boolean sIsFirstPressButton = false;
    private String Tag = "RedPacketDialog";
    private Context mContext;
    private boolean mIsFromHome;
    private Button mNoButton;
    private String mPageId = PageIdConstant.index;
    private RPCancelCallback mRPCancelCallback;
    RedPacketBean mRedPacketBean;
    private RedPacketCallback mRedPacketCallback;
    private ImageView mRedPacketIcon;
    private TextView mSubTitle;
    private TextView mTitle;
    private Button mYesButton;

    public interface RPCancelCallback {
        void onclick();
    }

    public interface RedPacketCallback {
        void onclick();
    }

    public RedPacketDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    public RedPacketDialog(Context context, int theme, boolean isFromHome, LetvBaseBean letvbean, RedPacketCallback callback) {
        super(context, theme);
        this.mContext = context;
        this.mRedPacketCallback = callback;
        this.mIsFromHome = isFromHome;
        this.mRedPacketBean = (RedPacketBean) letvbean;
    }

    public RedPacketDialog(Context context, int theme, boolean isFromHome, LetvBaseBean letvbean, RedPacketCallback callback, RPCancelCallback mRPCancelCallback) {
        super(context, theme);
        this.mContext = context;
        this.mRedPacketCallback = callback;
        this.mIsFromHome = isFromHome;
        this.mRedPacketBean = (RedPacketBean) letvbean;
        this.mRPCancelCallback = mRPCancelCallback;
    }

    @TargetApi(16)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redpacket_layout);
        initView();
        saveData();
    }

    private void initView() {
        this.mRedPacketIcon = (ImageView) findViewById(R.id.redpacket_icon);
        this.mTitle = (TextView) findViewById(R.id.title);
        this.mSubTitle = (TextView) findViewById(R.id.sub_title);
        this.mYesButton = (Button) findViewById(R.id.yes_button);
        this.mNoButton = (Button) findViewById(R.id.no_button);
        if (this.mRedPacketBean != null) {
            this.mTitle.setText(getLenghLimitedSubString(TextUtils.isEmpty(this.mRedPacketBean.shorDesc) ? this.mContext.getString(R.string.pointme_sendredpackage_title) : this.mRedPacketBean.title, 12));
            this.mSubTitle.setText(getLenghLimitedSubString(TextUtils.isEmpty(this.mRedPacketBean.shorDesc) ? this.mContext.getString(R.string.pointme_sendredpackage_content) : this.mRedPacketBean.shorDesc, 34));
            this.mYesButton.setText(getLenghLimitedSubString(TextUtils.isEmpty(this.mRedPacketBean.rightButton) ? this.mContext.getString(R.string.share_get_redpackage) : this.mRedPacketBean.rightButton, 6));
            this.mNoButton.setText(getLenghLimitedSubString(TextUtils.isEmpty(this.mRedPacketBean.leftButton) ? this.mContext.getString(R.string.share_get_redpackage_cancle) : this.mRedPacketBean.leftButton, 6));
            ImageDownloader.getInstance().download(this.mRedPacketIcon, this.mRedPacketBean.mobilePic, -2, true, true);
        }
        this.mYesButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (RedPacketDialog.this.mRedPacketCallback != null) {
                    RedPacketDialog.this.mRedPacketCallback.onclick();
                }
                RedPacketDialog.this.statistics(2);
            }
        });
        this.mNoButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (RedPacketDialog.this.mRPCancelCallback != null) {
                    RedPacketDialog.this.mRPCancelCallback.onclick();
                }
                RedPacketDialog.this.dismiss();
                RedPacketDialog.this.statistics(1);
            }
        });
    }

    private void saveData() {
        if (this.mIsFromHome) {
            SharedPreferences sp = this.mContext.getSharedPreferences("RedPacket", 32768);
            Editor editor = sp.edit();
            if (this.mRedPacketBean != null) {
                String id = this.mRedPacketBean.activeID;
                int limit = BaseTypeUtils.stoi(this.mRedPacketBean.limitNum, 1);
                if (!TextUtils.isEmpty(id)) {
                    if (sp.contains(id)) {
                        int num = sp.getInt(id, 0);
                        if (num == limit) {
                            LogInfo.log(this.Tag, "do not add num");
                        } else {
                            editor.putInt(id, num + 1);
                        }
                    } else {
                        editor.putInt(id, 1);
                    }
                    editor.commit();
                }
            }
        }
    }

    private String getLenghLimitedSubString(String theContent, int limitlength) {
        if (TextUtils.isEmpty(theContent)) {
            return null;
        }
        String content = theContent;
        if (theContent.length() > limitlength) {
            return content.substring(0, limitlength);
        }
        return content;
    }

    public void setCurrentPageId(String pageId) {
        this.mPageId = pageId;
    }

    private void statistics(int wz) {
        if (this.mRedPacketBean != null) {
            String hbid = DataUtils.getUnEmptyData(this.mRedPacketBean.activeID);
            if (TextUtils.equals(this.mPageId, PageIdConstant.woOrderSuccessPage)) {
                hbid = String.valueOf(this.mRedPacketBean.channelId);
            }
            StatisticsUtils.statisticsActionInfo(this.mContext, this.mPageId, "0", "hb01", null, wz, "hbid=" + hbid);
        }
    }
}
