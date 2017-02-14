package com.letv.mobile.lebox.ui.follow;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.http.lebox.bean.FollowAlbumBean;
import com.letv.mobile.lebox.httpmanager.HttpRequesetManager;
import com.letv.mobile.lebox.httpmanager.HttpRequesetManager.HttpCallBack;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.view.CustomLoadingDialog;
import java.util.List;

public class MyFollowActivity extends Activity implements OnClickListener {
    static final String TAG = MyFollowActivity.class.getSimpleName();
    ImageView backBtn;
    TextView emptyText;
    private CustomLoadingDialog mDialog;
    ListView mListView;
    UpdateUi mUpdateUi = new UpdateUi() {
        public void callBack() {
            MyFollowActivity.this.initDate();
        }
    };
    MyFollowAdapter myFollowAdapter;
    TextView title;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lebox_my_follow);
        this.mDialog = new CustomLoadingDialog(this);
        this.mDialog.setCanceledOnTouchOutside(false);
        initView();
        initListener();
        initDate();
    }

    private void initView() {
        this.title = (TextView) findViewById(R.id.common_nav_title);
        this.title.setText(R.string.btn_text_follow_list);
        this.emptyText = (TextView) findViewById(R.id.my_follow_empty);
        this.backBtn = (ImageView) findViewById(R.id.common_nav_left);
        this.mListView = (ListView) findViewById(R.id.my_follow_list);
        this.myFollowAdapter = new MyFollowAdapter(this, this.mUpdateUi);
        this.mListView.setAdapter(this.myFollowAdapter);
    }

    private void initListener() {
        this.backBtn.setOnClickListener(this);
    }

    private void initDate() {
        showLoadingDialog();
        HttpRequesetManager.getInstance().getFollowAlbum(new HttpCallBack<List<FollowAlbumBean>>() {
            public void callback(int code, String msg, String errorCode, List<FollowAlbumBean> object) {
                if (code != 0 || object == null) {
                    Logger.d(MyFollowActivity.TAG, "----获取追剧列表失败-----");
                } else {
                    List<FollowAlbumBean> list = object;
                    Logger.d(MyFollowActivity.TAG, "---------list size=" + list.size());
                    if (list.size() > 0) {
                        MyFollowActivity.this.emptyText.setVisibility(8);
                    } else {
                        MyFollowActivity.this.emptyText.setVisibility(0);
                    }
                    MyFollowActivity.this.myFollowAdapter.updateList(list);
                }
                MyFollowActivity.this.cancelLoadingDialog();
            }
        });
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.common_nav_left) {
            finish();
        }
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
}
