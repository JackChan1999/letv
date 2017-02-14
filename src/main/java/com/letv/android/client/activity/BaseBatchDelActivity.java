package com.letv.android.client.activity;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.android.client.commonlib.activity.WrapActivity.IBatchDel;
import com.letv.core.utils.LetvUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public abstract class BaseBatchDelActivity extends WrapActivity implements OnClickListener, IBatchDel {
    private View mBottomActionView;
    private Button mDeleteBtn;
    private TextView mEditView;
    private boolean mIsEditing;
    private boolean mIsSelectAll;
    OnClickListener mOnClickListener;
    private Button mSelectBtn;

    public BaseBatchDelActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mIsEditing = false;
        this.mIsSelectAll = false;
        this.mOnClickListener = new OnClickListener(this) {
            final /* synthetic */ BaseBatchDelActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                boolean z = true;
                if (LetvUtils.checkClickEvent(200)) {
                    BaseBatchDelActivity baseBatchDelActivity;
                    switch (v.getId()) {
                        case 2131362349:
                            baseBatchDelActivity = this.this$0;
                            if (this.this$0.mIsSelectAll) {
                                z = false;
                            }
                            baseBatchDelActivity.mIsSelectAll = z;
                            if (this.this$0.mIsSelectAll) {
                                this.this$0.onSelectAll();
                            } else {
                                this.this$0.onClearSelectAll();
                            }
                            this.this$0.updateBottomActionView(this.this$0.onSelectNum(), this.this$0.mIsSelectAll);
                            return;
                        case 2131362350:
                            this.this$0.onDoBatchDelete();
                            return;
                        case 2131362354:
                            if (!this.this$0.onHandleEditViewEvent()) {
                                baseBatchDelActivity = this.this$0;
                                if (this.this$0.mIsEditing) {
                                    z = false;
                                }
                                baseBatchDelActivity.mIsEditing = z;
                                this.this$0.onClearSelectAll();
                                this.this$0.clickEditView();
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }
            }
        };
    }

    public boolean isEditing() {
        return this.mIsEditing;
    }

    public boolean isSelectAll() {
        return this.mIsSelectAll;
    }

    public String getActivityName() {
        return null;
    }

    public Activity getActivity() {
        return this;
    }

    public void initBatchDelView() {
        this.mBottomActionView = findViewById(2131361934);
        this.mSelectBtn = (Button) findViewById(2131362349);
        this.mSelectBtn.setOnClickListener(this.mOnClickListener);
        this.mDeleteBtn = (Button) findViewById(2131362350);
        this.mDeleteBtn.setOnClickListener(this.mOnClickListener);
        this.mEditView = (TextView) findViewById(2131362354);
        this.mEditView.setOnClickListener(this.mOnClickListener);
    }

    protected void onResume() {
        super.onResume();
        initBatchDelView();
    }

    public boolean onHandleEditViewEvent() {
        return false;
    }

    public void updateEditViewState() {
        this.mIsEditing = false;
        this.mIsSelectAll = false;
        setSelectButton();
        setDeleteNumbers(0);
        if (onIsAdapterEmpty()) {
            this.mEditView.setVisibility(8);
        } else {
            this.mEditView.setVisibility(0);
            this.mEditView.setText(2131099788);
        }
        this.mBottomActionView.setVisibility(8);
    }

    public void updateBatchDelView() {
        this.mIsEditing = false;
        this.mIsSelectAll = false;
        setSelectButton();
        setDeleteNumbers(0);
        if (onIsAdapterEmpty()) {
            this.mEditView.setVisibility(8);
        } else {
            this.mEditView.setVisibility(0);
            this.mEditView.setText(2131099788);
        }
        onCancelEditState();
        this.mBottomActionView.setVisibility(8);
    }

    public TextView getEditView() {
        return this.mEditView;
    }

    private void clickEditView() {
        this.mIsSelectAll = false;
        if (!onIsAdapterEmpty() && this.mIsEditing) {
            this.mEditView.setVisibility(0);
            this.mBottomActionView.setVisibility(0);
            this.mEditView.setText(2131099798);
            onShowEditState();
        } else if (onIsAdapterEmpty()) {
            this.mEditView.setVisibility(8);
            this.mBottomActionView.setVisibility(8);
        } else {
            this.mEditView.setVisibility(0);
            this.mBottomActionView.setVisibility(8);
            this.mEditView.setText(2131099788);
            onCancelEditState();
        }
        setDeleteNumbers(0);
        setSelectButton();
    }

    private void setDeleteNumbers(int num) {
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

    private void setSelectButton() {
        this.mSelectBtn.setText(this.mIsSelectAll ? 2131099785 : 2131099791);
    }

    public void updateBottomActionView(int num, boolean selectAll) {
        setDeleteNumbers(num);
        setSelectStatus(selectAll);
    }
}
