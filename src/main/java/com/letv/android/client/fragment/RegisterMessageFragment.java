package com.letv.android.client.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.fragement.LetvBaseFragment;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.StatisticsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class RegisterMessageFragment extends LetvBaseFragment implements OnClickListener {
    private TextView mBackBtn;
    private View mRootView;
    private Button mSendMsgBtn;
    private TextView mTextView;

    public RegisterMessageFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRootView = inflater.inflate(R.layout.register_message, null, false);
        return this.mRootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        this.mTextView = (TextView) this.mRootView.findViewById(R.id.text_2);
        this.mSendMsgBtn = (Button) this.mRootView.findViewById(R.id.register_send_msg_now);
        this.mBackBtn = (TextView) this.mRootView.findViewById(R.id.register_message_backlogin);
        this.mTextView.setText(LetvTools.getTextFromServer(DialogMsgConstantId.CONSTANT_90001, getActivity().getResources().getString(2131100760)));
        this.mSendMsgBtn.setOnClickListener(this);
        this.mBackBtn.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_send_msg_now /*2131364185*/:
                StatisticsUtils.staticticsInfoPost(getActivity(), "0", "c833", null, 1, null, PageIdConstant.registerPage, null, null, null, null, null);
                getActivity().startActivity(new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + this.mTextView.getText())));
                return;
            case R.id.register_message_backlogin /*2131364186*/:
                getActivity().finish();
                return;
            default:
                return;
        }
    }

    public String getTagName() {
        return null;
    }

    public int getContainerId() {
        return 0;
    }
}
