package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.FirstPageCustomAdapter;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.listener.ItemDragHelperCallback;
import com.letv.android.client.utils.CardSortManager;
import com.letv.core.bean.HomeBlock;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.mobile.lebox.utils.DialogUtil;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.List;

public class FirstPageCustomActivity extends LetvBaseActivity implements OnClickListener {
    private FirstPageCustomAdapter mAdapter;
    private ImageView mBtnBack;
    private View mHeadView;
    private List<HomeBlock> mList;
    private RecyclerView mRecyclerView;
    private PublicLoadLayout mRoot;
    private TextView mSaveText;
    private TextView mTitleText;

    public FirstPageCustomActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page_custom);
        this.mList = CardSortManager.getHomeBlock();
        findView();
        init();
    }

    public static void launch(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, FirstPageCustomActivity.class));
        }
    }

    private void findView() {
        this.mTitleText = (TextView) findViewById(2131362352);
        this.mTitleText.setText(2131100132);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.list_first_page_frig);
        this.mBtnBack = (ImageView) findViewById(2131362351);
        this.mBtnBack.setOnClickListener(this);
        this.mSaveText = (TextView) findViewById(2131362354);
        this.mSaveText.setText(2131100776);
        this.mSaveText.setVisibility(0);
        this.mSaveText.setOnClickListener(this);
        this.mSaveText.setTextColor(this.mContext.getResources().getColor(2131493082));
        this.mSaveText.setTextSize(15.0f);
        this.mHeadView = findViewById(R.id.my_navigation);
        this.mHeadView.bringToFront();
        this.mRoot = (PublicLoadLayout) findViewById(R.id.root);
    }

    private void init() {
        if (BaseTypeUtils.isListEmpty(this.mList)) {
            this.mRoot.setVisibility(0);
            this.mRoot.cardError();
            return;
        }
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper helper = new ItemTouchHelper(new ItemDragHelperCallback());
        helper.attachToRecyclerView(this.mRecyclerView);
        this.mAdapter = new FirstPageCustomAdapter(this, helper, this.mList, this.mSaveText, this.mRecyclerView);
        this.mRecyclerView.setAdapter(this.mAdapter);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case 2131362351:
                doBack();
                return;
            case 2131362354:
                saveFragList();
                return;
            default:
                return;
        }
    }

    private void doBack() {
        if (this.mAdapter == null || !this.mAdapter.getHasEdit()) {
            finish();
        } else {
            showDialog();
        }
    }

    private void showDialog() {
        try {
            DialogUtil.showDialog(this, this.mContext.getResources().getString(2131100779), this.mContext.getResources().getString(2131099768), this.mContext.getResources().getString(2131100776), new DialogInterface.OnClickListener(this) {
                final /* synthetic */ FirstPageCustomActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    this.this$0.finish();
                }
            }, new DialogInterface.OnClickListener(this) {
                final /* synthetic */ FirstPageCustomActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    this.this$0.saveFragList();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveFragList() {
        try {
            if (this.mAdapter.getHasEdit()) {
                StringBuilder sb = new StringBuilder();
                List<HomeBlock> list = this.mAdapter.getCurrentList();
                if (!BaseTypeUtils.isListEmpty(list)) {
                    for (HomeBlock block : list) {
                        if (block != null) {
                            sb.append(block.fragId).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                            String str = sb.toString();
                            if (!TextUtils.isEmpty(str)) {
                                PreferencesManager.getInstance().setCardSort(str.substring(0, str.length() - 1));
                            }
                        } else {
                            return;
                        }
                    }
                    PreferencesManager.getInstance().setHasEditCard(true);
                    this.mAdapter.setHasEdit(false);
                    this.mAdapter.setSaveViewColor(false);
                    ToastUtils.showToast((Context) this, TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700091, 2131101083));
                    CardSortManager.updateList(list);
                    finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        doBack();
        return true;
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public String[] getAllFragmentTags() {
        return null;
    }

    public Activity getActivity() {
        return this;
    }

    public String getActivityName() {
        return FirstPageCustomActivity.class.getName();
    }
}
