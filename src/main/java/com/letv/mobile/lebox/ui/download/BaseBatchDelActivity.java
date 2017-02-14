package com.letv.mobile.lebox.ui.download;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.httpmanager.HttpCacheAssistant;
import com.letv.mobile.lebox.ui.WrapActivity;
import com.letv.mobile.lebox.ui.WrapActivity.IBatchDel;
import com.letv.mobile.lebox.view.CustomLoadingDialog;

public abstract class BaseBatchDelActivity extends WrapActivity implements OnClickListener, IBatchDel {
    protected static BaseBatchDelActivity mActivity;
    private View mBottomActionView;
    private Button mDeleteBtn;
    private CustomLoadingDialog mDialog;
    private TextView mEditView;
    private boolean mIsEditing = false;
    private boolean mIsSelectAll = false;
    OnClickListener mOnClickListener = new OnClickListener() {
        public void onClick(View v) {
            boolean z = true;
            int id = v.getId();
            BaseBatchDelActivity baseBatchDelActivity;
            if (id == R.id.common_nav_right_text) {
                if (!BaseBatchDelActivity.this.onHandleEditViewEvent()) {
                    baseBatchDelActivity = BaseBatchDelActivity.this;
                    if (BaseBatchDelActivity.this.mIsEditing) {
                        z = false;
                    }
                    baseBatchDelActivity.mIsEditing = z;
                    BaseBatchDelActivity.this.onClearSelectAll();
                    BaseBatchDelActivity.this.clickEditView();
                }
            } else if (id == R.id.common_button_select) {
                baseBatchDelActivity = BaseBatchDelActivity.this;
                if (BaseBatchDelActivity.this.mIsSelectAll) {
                    z = false;
                }
                baseBatchDelActivity.mIsSelectAll = z;
                if (BaseBatchDelActivity.this.mIsSelectAll) {
                    BaseBatchDelActivity.this.onSelectAll();
                } else {
                    BaseBatchDelActivity.this.onClearSelectAll();
                }
                BaseBatchDelActivity.this.updateBottomActionView(BaseBatchDelActivity.this.onSelectNum(), BaseBatchDelActivity.this.mIsSelectAll);
            } else if (id == R.id.common_button_delete) {
                BaseBatchDelActivity.this.onDoBatchDelete();
            }
        }
    };
    private Button mSelectBtn;

    public boolean isEditing() {
        return this.mIsEditing;
    }

    public boolean isSelectAll() {
        return this.mIsSelectAll;
    }

    public void initBatchDelView() {
        this.mBottomActionView = findViewById(R.id.my_download_layout_delete_and_select);
        this.mSelectBtn = (Button) findViewById(R.id.common_button_select);
        this.mSelectBtn.setOnClickListener(this.mOnClickListener);
        this.mDeleteBtn = (Button) findViewById(R.id.common_button_delete);
        this.mDeleteBtn.setOnClickListener(this.mOnClickListener);
        this.mEditView = (TextView) findViewById(R.id.common_nav_right_text);
        this.mEditView.setOnClickListener(this.mOnClickListener);
    }

    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        mActivity = this;
        this.mDialog = new CustomLoadingDialog(this);
        this.mDialog.setCanceledOnTouchOutside(false);
        super.onCreate(savedInstanceState, persistentState);
    }

    protected void onResume() {
        super.onResume();
        initBatchDelView();
    }

    public void showLoadingDialog() {
        try {
            if (this.mDialog.isShowing()) {
                this.mDialog.cancel();
            } else {
                this.mDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelLoadingDialog() {
        try {
            if (this.mDialog != null && this.mDialog.isShowing()) {
                this.mDialog.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onHandleEditViewEvent() {
        return false;
    }

    public void updateBatchDelView() {
        this.mIsEditing = false;
        this.mIsSelectAll = false;
        setSelectButton();
        setDeleteNumbers(0);
        if (onIsAdapterEmpty() || !HttpCacheAssistant.getInstanced().isAdmini()) {
            this.mEditView.setVisibility(8);
        } else {
            this.mEditView.setVisibility(0);
            this.mEditView.setText(R.string.btn_text_edit);
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
            this.mEditView.setText(R.string.cancel);
            onShowEditState();
        } else if (onIsAdapterEmpty()) {
            this.mEditView.setVisibility(8);
            this.mBottomActionView.setVisibility(8);
        } else {
            this.mEditView.setVisibility(0);
            this.mBottomActionView.setVisibility(8);
            this.mEditView.setText(R.string.btn_text_edit);
            onCancelEditState();
        }
        setDeleteNumbers(0);
        setSelectButton();
    }

    private void setDeleteNumbers(int num) {
        if (num == 0) {
            this.mDeleteBtn.setClickable(false);
            this.mDeleteBtn.setText(R.string.btn_text_delete);
            this.mDeleteBtn.setBackgroundResource(R.drawable.shape_download_btn_cannot_click);
            return;
        }
        this.mDeleteBtn.setClickable(true);
        this.mDeleteBtn.setText(String.format(getString(R.string.lebox_delete_with_number), new Object[]{Integer.valueOf(num)}));
        this.mDeleteBtn.setBackgroundResource(R.drawable.common_button_delete_selector);
    }

    public void setSelectStatus(boolean selectAll) {
        this.mIsSelectAll = selectAll;
        setSelectButton();
    }

    private void setSelectButton() {
        this.mSelectBtn.setText(this.mIsSelectAll ? R.string.btn_text_cancel_all : R.string.btn_text_pick_all);
    }

    public void updateBottomActionView(int num, boolean selectAll) {
        setDeleteNumbers(num);
        setSelectStatus(selectAll);
    }

    public static BaseBatchDelActivity getActivity() {
        return mActivity;
    }
}
