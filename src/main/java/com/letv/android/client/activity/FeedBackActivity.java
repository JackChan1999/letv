package com.letv.android.client.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.letv.ads.ex.utils.AdsManagerProxy;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.fragment.FeedBackAnimFragment;
import com.letv.android.client.utils.FeedbackUtils;
import com.letv.android.client.view.FeedBackImageView;
import com.letv.android.client.view.FeedbackDialog;
import com.letv.android.client.view.FeedbackDialog.Builder;
import com.letv.component.player.core.LetvMediaPlayerManager;
import com.letv.component.upgrade.utils.LetvUtil;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.FeedBackBean;
import com.letv.core.bean.UploadFileBean;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.download.db.Download.DownloadBaseColumns;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import io.fabric.sdk.android.services.events.EventsFilesManager;

public class FeedBackActivity extends WrapActivity implements OnClickListener, TextWatcher, OnTouchListener {
    public static String mContact;
    public final int CONTENT_MAX_LENGTH;
    private Handler handler;
    private TextView mAddImageTextView;
    private Button mBackBtn;
    private ImageView mBackImageView;
    private TextView mCommunityTextView;
    private String mContent;
    private FeedBackAnimFragment mFeedBackAnimFragment;
    private LinearLayout mFeedBackBodyLayout;
    private EditText mFeedBackEditText;
    private UploadFileBean[] mFormFiles;
    private int mHeight;
    private LinearLayout mImageDefaultLayout;
    private LinearLayout mImageParentLayout;
    private InputMethodManager mInputMethodManager;
    private int mMarginLeft;
    private int mMarginRight;
    private TextView mNumberTextView;
    private LayoutParams mParams;
    private TextView mQQFansTextView;
    private PublicLoadLayout mRootView;
    private ScrollView mScrollView;
    private TextView mSubmitTextView;
    private LinearLayout mSuccessLayout;
    private int mTotalWidth;
    private EditText mUserContactEditText;
    private int mWidth;
    private int tag;

    class ImageDeleteListener implements OnClickListener {
        final /* synthetic */ FeedBackActivity this$0;

        ImageDeleteListener(FeedBackActivity this$0) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
        }

        public void onClick(View view) {
            View v = view;
            this.this$0.mInputMethodManager.hideSoftInputFromWindow(this.this$0.getCurrentFocus().getWindowToken(), 2);
            this.this$0.mFeedBackAnimFragment.selectAnimWidget(1);
            this.this$0.mFeedBackAnimFragment.setmFlowCallBack(new 1(this, v));
        }
    }

    public FeedBackActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.CONTENT_MAX_LENGTH = LeMessageIds.MSG_FLOAT_BALL_REQUEST_DATA;
        this.tag = -1;
        this.mHeight = -1;
        this.handler = new Handler();
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, FeedBackActivity.class));
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        this.mRootView = PublicLoadLayout.createPage(getActivity(), (int) R.layout.setting_center_feedback_layout);
        setContentView(this.mRootView);
        initUI();
    }

    private void initUI() {
        this.mInputMethodManager = (InputMethodManager) getSystemService("input_method");
        getWindow().setSoftInputMode(16);
        this.mFeedBackAnimFragment = new FeedBackAnimFragment();
        this.mScrollView = (ScrollView) findViewById(R.id.feedback_scroll_view);
        this.mNumberTextView = (TextView) findViewById(R.id.text_number);
        this.mBackImageView = (ImageView) findViewById(R.id.back_my);
        this.mQQFansTextView = (TextView) findViewById(R.id.letv_fans_qq);
        this.mCommunityTextView = (TextView) findViewById(R.id.le_community);
        this.mFeedBackEditText = (EditText) findViewById(R.id.feedback_content);
        this.mSubmitTextView = (TextView) findViewById(R.id.feedback_submit);
        this.mUserContactEditText = (EditText) findViewById(R.id.feedback_contact);
        this.mImageParentLayout = (LinearLayout) findViewById(R.id.imageParent);
        this.mImageDefaultLayout = (LinearLayout) findViewById(R.id.feedback_image_default);
        this.mAddImageTextView = (TextView) findViewById(R.id.add_image_text);
        this.mSuccessLayout = (LinearLayout) findViewById(R.id.submit_success);
        this.mBackBtn = (Button) findViewById(R.id.back_to_main);
        this.mFeedBackBodyLayout = (LinearLayout) findViewById(R.id.feedback_body);
        this.mBackImageView.setOnClickListener(this);
        this.mBackBtn.setOnClickListener(this);
        this.mAddImageTextView.setOnClickListener(this);
        this.mImageDefaultLayout.setOnClickListener(this);
        this.mUserContactEditText.setOnClickListener(this);
        this.mUserContactEditText.setOnTouchListener(this);
        this.mSubmitTextView.setOnClickListener(this);
        this.mCommunityTextView.setOnClickListener(this);
        this.mFeedBackEditText.setOnClickListener(this);
        this.mFeedBackEditText.addTextChangedListener(this);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.feedback_bottom, this.mFeedBackAnimFragment);
        fragmentTransaction.commit();
        initData();
    }

    private void initData() {
        this.mParams = (LayoutParams) this.mAddImageTextView.getLayoutParams();
        this.mTotalWidth = getWindowManager().getDefaultDisplay().getWidth();
        this.mWidth = ((this.mTotalWidth - (this.mParams.rightMargin * 3)) - (this.mImageParentLayout.getPaddingLeft() * 2)) / 4;
        this.mMarginRight = this.mParams.rightMargin;
        this.mMarginLeft = this.mParams.leftMargin;
        this.mQQFansTextView.setText(getString(2131100713, new Object[]{TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_100058, getString(2131101050))}));
    }

    public boolean onTouchEvent(MotionEvent event) {
        LogInfo.log("ZSM", "ontouch");
        if (!(event.getAction() != 0 || getCurrentFocus() == null || getCurrentFocus().getWindowToken() == null)) {
            this.mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 2);
        }
        return super.onTouchEvent(event);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_my /*2131364218*/:
                try {
                    this.mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 2);
                } catch (Exception e) {
                    LogInfo.log("wdm", "崩溃：" + e.toString());
                }
                if (this.mFeedBackBodyLayout.getVisibility() == 0) {
                    finish();
                    return;
                }
                finish();
                startActivity(new Intent(this, FeedBackActivity.class));
                overridePendingTransition(R.anim.in_from_left_short, R.anim.out_to_right_short);
                return;
            case R.id.feedback_submit /*2131364220*/:
                if (this.mSuccessLayout.getVisibility() != 0) {
                    this.mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 2);
                    if (calculateLength(this.mFeedBackEditText.getText().toString()) > LeMessageIds.MSG_FLOAT_BALL_REQUEST_DATA) {
                        String msg = TipUtils.getTipMessage("700012", getString(2131099900));
                        Builder builder = new Builder(this);
                        builder.setmMessage(msg).setmNegativeButton(getString(2131099998), new DialogInterface.OnClickListener(this) {
                            final /* synthetic */ FeedBackActivity this$0;

                            {
                                if (HotFix.PREVENT_VERIFY) {
                                    System.out.println(VerifyLoad.class);
                                }
                                this.this$0 = this$0;
                            }

                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        FeedbackDialog dialog = builder.create();
                        dialog.getWindow().setLayout(-2, -2);
                        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(2130838947));
                        dialog.show();
                        return;
                    }
                    SystemClock.sleep(100);
                    submitFeedback();
                    return;
                }
                return;
            case R.id.le_community /*2131364223*/:
                LogInfo.log("ZSM 乐迷社区地址 == " + TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_100059));
                new LetvWebViewActivityConfig(this).launch(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_100059, "http://bbs.letv.com/forum-907-1.html"), this.mContext.getResources().getString(2131099888));
                return;
            case R.id.feedback_content /*2131364226*/:
            case R.id.feedback_contact /*2131364232*/:
                this.mFeedBackAnimFragment.set_album_wedget_gone();
                this.mFeedBackAnimFragment.set_delete_wedgit_gone();
                return;
            case R.id.feedback_image_default /*2131364229*/:
            case R.id.add_image_text /*2131364231*/:
                this.mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 2);
                this.mFeedBackAnimFragment.selectAnimWidget(0);
                return;
            case R.id.back_to_main /*2131364235*/:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("tag", FragmentConstant.TAG_FRAGMENT_HOME);
                startActivity(intent);
                finish();
                return;
            default:
                return;
        }
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == -1) {
            FeedBackImageView image;
            Uri uri = data.getData();
            if (this.tag == -1) {
                image = new FeedBackImageView(this);
            } else {
                image = (FeedBackImageView) this.mImageParentLayout.getChildAt(this.tag);
            }
            this.mParams.width = this.mWidth;
            this.mParams.height = this.mWidth;
            this.mParams.setMargins(0, 0, this.mMarginRight, 0);
            image.setLayoutParams(this.mParams);
            this.mAddImageTextView.setLayoutParams(this.mParams);
            image.setUri(uri);
            image.setScaleType(ScaleType.FIT_XY);
            image.setOnClickListener(new ImageDeleteListener(this));
            if (this.tag == -1) {
                this.mImageParentLayout.addView(image);
            }
            if (this.mImageDefaultLayout.getParent() != null) {
                this.mImageParentLayout.removeView(this.mImageDefaultLayout);
            }
            if (this.mAddImageTextView.getParent() != null) {
                this.mImageParentLayout.removeView(this.mAddImageTextView);
            }
            if (this.mImageParentLayout.getChildCount() < 4) {
                this.mImageParentLayout.addView(this.mAddImageTextView);
                this.mAddImageTextView.setVisibility(0);
            }
            if (this.mImageParentLayout.getChildCount() == 0) {
                this.mImageParentLayout.addView(this.mImageDefaultLayout);
            }
            this.tag = -1;
        }
    }

    private int calculateLength(String etstring) {
        char[] ch = etstring.toCharArray();
        int varlength = 0;
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] >= '') {
                varlength += 2;
            } else if (ch[i] != '\n') {
                varlength++;
            }
        }
        return varlength;
    }

    private void submitFeedback() {
        generateUploadFile();
        LetvMediaPlayerManager.getInstance().feedbackCommit();
        if (!NetworkUtils.isNetworkAvailable()) {
            ToastUtils.showToast(getActivity(), 2131100493);
        } else if (TextUtils.isEmpty(this.mContent)) {
            ToastUtils.showToast(getActivity(), TipUtils.getTipMessage("700009", getString(2131100425)));
        } else if ("#ark*#".equalsIgnoreCase(this.mContent)) {
            AdsManagerProxy.getInstance(this.mContext).sendFeedbackToAd(mContact);
            ToastUtils.showToast(getActivity(), 2131100410);
        } else {
            sendFeedback();
        }
    }

    private void generateUploadFile() {
        int fileCount = this.mImageParentLayout.getChildCount();
        if (this.mAddImageTextView.getParent() != null) {
            fileCount--;
        }
        if (this.mImageDefaultLayout.getParent() != null) {
            fileCount--;
        }
        this.mFormFiles = new UploadFileBean[fileCount];
        int i = 0;
        while (i < fileCount) {
            this.mFormFiles[i] = new UploadFileBean(((FeedBackImageView) this.mImageParentLayout.getChildAt(i)).mFile, i == 0 ? DownloadBaseColumns.COLUMN_PIC : DownloadBaseColumns.COLUMN_PIC + i, "image/JPEG");
            LogInfo.log("FeedBackActivity", "feedBack上传的图片信息：" + this.mFormFiles[i].mFile.length());
            i++;
        }
        this.mContent = this.mFeedBackEditText.getText().toString().trim();
        mContact = this.mUserContactEditText.getText().toString().trim();
        if (TextUtils.isEmpty(mContact)) {
            mContact = getNativePhoneNumber();
        }
    }

    private void sendFeedback() {
        this.mRootView.loading(true);
        StringBuilder brandModelVersion = new StringBuilder();
        brandModelVersion.append(this.mContent);
        brandModelVersion.append(" " + LetvUtil.getBrandName() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        brandModelVersion.append(LetvUtils.getModelName() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        brandModelVersion.append(LetvUtil.getOSVersionName());
        submitExceptionInfo(mContact, brandModelVersion.toString(), this.mFormFiles);
    }

    private void submitExceptionInfo(final String mobile, final String feedback, UploadFileBean[] formFiles) {
        if (NetworkUtils.isNetworkAvailable()) {
            loading(true);
            upLoadState(false);
            FeedbackUtils.submitFeedbackInfo(this, mobile, feedback, formFiles, new SimpleResponse<FeedBackBean>(this) {
                final /* synthetic */ FeedBackActivity this$0;

                public void onNetworkResponse(VolleyRequest<FeedBackBean> volleyRequest, FeedBackBean result, DataHull hull, NetworkResponseState state) {
                    LogInfo.log("FeedBackActivity", "requestFocusImageTask == " + state);
                    if (state == NetworkResponseState.SUCCESS) {
                        this.this$0.submitCDEExceptionInfo(mobile, feedback);
                        return;
                    }
                    this.this$0.finishLoad();
                    ToastUtils.showToast(this.this$0.getActivity(), TipUtils.getTipMessage("700013", this.this$0.getString(2131100110)));
                    this.this$0.upLoadState(true);
                }
            });
            return;
        }
        ToastUtils.showToast(getActivity(), TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_FAVORITE_NO_NET, getString(2131100495)));
    }

    private void submitCDEExceptionInfo(String mobile, String feedback) {
        FeedbackUtils.submitCDEExceptionInfo(this, mobile, feedback, new SimpleResponse<FeedBackBean>(this) {
            final /* synthetic */ FeedBackActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<FeedBackBean> volleyRequest, FeedBackBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("FeedBackActivity", "requestFocusImageTask == " + state);
                if (state == NetworkResponseState.SUCCESS) {
                    this.this$0.finishLoad();
                    this.this$0.mSuccessLayout.setVisibility(0);
                    this.this$0.mFeedBackBodyLayout.setVisibility(8);
                    this.this$0.mSubmitTextView.setVisibility(8);
                    this.this$0.mSubmitTextView.setEnabled(true);
                    return;
                }
                this.this$0.finishLoad();
                ToastUtils.showToast(this.this$0.getActivity(), TipUtils.getTipMessage("700013", this.this$0.getString(2131100110)));
                this.this$0.upLoadState(true);
            }
        });
    }

    private void loading(boolean flag) {
        if (this.mRootView != null) {
            this.mRootView.getLoadingText().setText(getResources().getString(2131100118));
            this.mRootView.loading(flag);
        }
    }

    private void finishLoad() {
        if (this.mRootView != null) {
            this.mRootView.finishLoad();
        }
    }

    private void upLoadState(boolean isLoading) {
        this.mUserContactEditText.setEnabled(isLoading);
        this.mFeedBackEditText.setEnabled(isLoading);
        this.mSubmitTextView.setEnabled(isLoading);
        for (int i = 0; i < this.mImageParentLayout.getChildCount(); i++) {
            this.mImageParentLayout.getChildAt(i).setEnabled(isLoading);
        }
    }

    public String getActivityName() {
        return FeedBackActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public void afterTextChanged(Editable s) {
        int length = calculateLength(s.toString());
        this.mFeedBackEditText.removeTextChangedListener(this);
        if (length > LeMessageIds.MSG_FLOAT_BALL_REQUEST_DATA) {
            this.mNumberTextView.setTextColor(getResources().getColor(2131493199));
        } else {
            this.mNumberTextView.setTextColor(getResources().getColor(2131493160));
        }
        this.mNumberTextView.setText(String.valueOf(length / 2));
        LayoutParams params = (LayoutParams) this.mFeedBackEditText.getLayoutParams();
        if (this.mHeight == -1) {
            this.mHeight = params.height;
        }
        if (this.mFeedBackEditText.getLineCount() > 5) {
            params.height = -2;
            this.mFeedBackEditText.setLayoutParams(params);
        } else {
            params.height = this.mHeight;
            this.mFeedBackEditText.setLayoutParams(params);
        }
        this.mFeedBackEditText.addTextChangedListener(this);
    }

    public boolean onTouch(View v, MotionEvent event) {
        LogInfo.log("wdm", "feedback_contact onclick");
        this.handler.post(new Runnable(this) {
            final /* synthetic */ FeedBackActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void run() {
                LogInfo.log("wdm", "scroll down");
                this.this$0.mScrollView.fullScroll(130);
            }
        });
        return false;
    }

    public String getNativePhoneNumber() {
        return ((TelephonyManager) getActivity().getSystemService("phone")).getLine1Number();
    }
}
