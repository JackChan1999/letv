package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.MyCollectListAdapter;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.config.MyDownloadActivityConfig;
import com.letv.android.client.fragment.MyCollectFragment;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class MyCollectActivity extends LetvBaseActivity {
    private ImageView mBackImageView;
    private View mBottomActionView;
    private RelativeLayout mBottomLoginBtn;
    private TextView mDeleteBtn;
    private TextView mEditView;
    private MyCollectFragment mFragment;
    private FragmentTransaction mFragmentTransaction;
    private boolean mIsEditing;
    private boolean mIsSelectAll;
    private TextView mLoginTitle;
    private TextView mSelectBtn;
    private TextView mTitleView;
    private OnClickListener onClickEvent;

    public MyCollectActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mIsEditing = false;
        this.mIsSelectAll = false;
        this.onClickEvent = new OnClickListener(this) {
            final /* synthetic */ MyCollectActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                boolean z = true;
                int i = 0;
                MyCollectActivity myCollectActivity;
                switch (v.getId()) {
                    case R.id.my_collect_bottom_login_btn /*2131361994*/:
                        LetvLoginActivity.launch(this.this$0, 8);
                        return;
                    case 2131362349:
                        myCollectActivity = this.this$0;
                        if (this.this$0.mIsSelectAll) {
                            z = false;
                        }
                        myCollectActivity.mIsSelectAll = z;
                        this.this$0.setSelectButton();
                        if (this.this$0.mFragment.getAdapter() != null) {
                            MyCollectListAdapter adapter = this.this$0.mFragment.getAdapter();
                            MyCollectActivity myCollectActivity2 = this.this$0;
                            if (this.this$0.mIsSelectAll) {
                                i = adapter.getCount();
                            }
                            myCollectActivity2.setDeleteNumbers(i);
                            adapter.selectAllOrNot(this.this$0.mIsSelectAll);
                            return;
                        }
                        return;
                    case 2131362350:
                        if (NetworkUtils.isNetworkAvailable()) {
                            if (this.this$0.mFragment.getAdapter() != null) {
                                this.this$0.mFragment.onDeleteSelected();
                            }
                            this.this$0.setDeleteNumbers(0);
                            return;
                        }
                        ToastUtils.showToast(this.this$0, 2131100495);
                        return;
                    case 2131362351:
                    case 2131362352:
                        this.this$0.finish();
                        return;
                    case 2131362354:
                        myCollectActivity = this.this$0;
                        if (this.this$0.mIsEditing) {
                            z = false;
                        }
                        myCollectActivity.mIsEditing = z;
                        this.this$0.editOrNot();
                        this.this$0.setDeleteNumbers(0);
                        if (this.this$0.mFragment.getAdapter() != null) {
                            this.this$0.mFragment.getAdapter().editOrNot(this.this$0.mIsEditing);
                        }
                        this.this$0.mFragment.changeBottomPadding();
                        return;
                    default:
                        return;
                }
            }
        };
    }

    public static void launch(Context context, int page) {
        if (context != null) {
            Intent intent = new Intent(context, MyCollectActivity.class);
            intent.putExtra(MyDownloadActivityConfig.PAGE, page);
            context.startActivity(intent);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect_main);
        initNavigationBar();
        initView();
        this.mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        this.mFragment = new MyCollectFragment();
        this.mFragmentTransaction.replace(R.id.my_collect_content, this.mFragment, MyCollectFragment.class.getSimpleName());
        this.mFragmentTransaction.commitAllowingStateLoss();
    }

    protected void onStart() {
        super.onStart();
        this.mBottomLoginBtn.setVisibility(PreferencesManager.getInstance().isLogin() ? 8 : 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.mFragment.onActivityResult(requestCode, resultCode, data);
    }

    private void initNavigationBar() {
        this.mBackImageView = (ImageView) findViewById(2131362351);
        this.mEditView = (TextView) findViewById(2131362354);
        this.mTitleView = (TextView) findViewById(2131362352);
        this.mTitleView.setText(getResources().getString(2131100922));
        this.mBackImageView.setOnClickListener(this.onClickEvent);
        this.mEditView.setOnClickListener(this.onClickEvent);
    }

    private void initView() {
        this.mBottomLoginBtn = (RelativeLayout) findViewById(R.id.my_collect_bottom_login_btn);
        this.mLoginTitle = (TextView) findViewById(R.id.my_collect_account_login);
        this.mBottomActionView = findViewById(R.id.my_collect_layout_delete_and_select);
        this.mSelectBtn = (Button) findViewById(2131362349);
        this.mDeleteBtn = (Button) findViewById(2131362350);
        this.mLoginTitle.setText(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_FAVORITE_BOTTOM_LOGIN_GUIDE_TITLE, 2131100462));
        this.mBottomLoginBtn.setOnClickListener(this.onClickEvent);
        this.mSelectBtn.setOnClickListener(this.onClickEvent);
        this.mDeleteBtn.setOnClickListener(this.onClickEvent);
    }

    private void editOrNot() {
        if (this.mIsEditing) {
            this.mEditView.setText(2131099798);
            this.mFragment.setPullToRefreshEnabled(false);
            showBottomActionView(true);
            showBottomLoginBtn(false);
            return;
        }
        this.mEditView.setText(2131099788);
        this.mFragment.setPullToRefreshEnabled(true);
        showBottomActionView(false);
        showBottomLoginBtn(true);
        this.mIsSelectAll = false;
        setSelectButton();
    }

    public void resetEditState() {
        this.mIsEditing = false;
        editOrNot();
        showEditView(false);
    }

    private void setSelectButton() {
        this.mSelectBtn.setText(this.mIsSelectAll ? 2131099785 : 2131099791);
    }

    public void showEditView(boolean show) {
        this.mEditView.setVisibility(show ? 0 : 8);
        editOrNot();
    }

    public void showBottomActionView(boolean show) {
        this.mBottomActionView.setVisibility(show ? 0 : 8);
    }

    public void showBottomLoginBtn(boolean isShow) {
        if (!isShow || PreferencesManager.getInstance().isLogin()) {
            this.mBottomLoginBtn.setVisibility(8);
        } else {
            this.mBottomLoginBtn.setVisibility(0);
        }
    }

    public void setDeleteNumbers(int num) {
        if (num == 0) {
            this.mDeleteBtn.setClickable(false);
            this.mDeleteBtn.setText(2131099787);
            this.mDeleteBtn.setBackgroundResource(2130838921);
            return;
        }
        this.mDeleteBtn.setClickable(true);
        this.mDeleteBtn.setText(String.format(getString(2131099929), new Object[]{Integer.valueOf(num)}));
        this.mDeleteBtn.setBackgroundResource(2130837889);
    }

    public void setSelectStatus(boolean selectAll) {
        this.mIsSelectAll = selectAll;
        setSelectButton();
    }

    public boolean isSelectAll() {
        return this.mIsSelectAll;
    }

    public boolean isEditing() {
        return this.mIsEditing;
    }

    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.sink_in, R.anim.out_to_right);
        this.mFragment = null;
        this.mFragmentTransaction = null;
    }

    public String[] getAllFragmentTags() {
        return null;
    }

    public String getActivityName() {
        return null;
    }

    public Activity getActivity() {
        return this;
    }
}
