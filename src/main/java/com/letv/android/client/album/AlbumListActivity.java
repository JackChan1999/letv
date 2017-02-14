package com.letv.android.client.album;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.letv.android.client.album.half.adapter.AlbumListAdapter;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.config.AlbumListActivityConfig;
import com.letv.core.bean.AlbumInfo;
import java.util.ArrayList;

public class AlbumListActivity extends LetvBaseActivity implements OnClickListener {
    private View mBtnBack;
    private ArrayList<AlbumInfo> mList;
    private ListView mListView;
    private TextView mTitle;
    private String mtitle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);
        initData();
        initViews();
    }

    private void initData() {
        if (getIntent() != null) {
            this.mList = (ArrayList) getIntent().getSerializableExtra(AlbumListActivityConfig.INTENT_KEY_ALBUM_LIST);
            this.mtitle = getIntent().getStringExtra("title");
        }
    }

    private void initViews() {
        this.mBtnBack = findViewById(R.id.album_btn_back);
        this.mTitle = (TextView) findViewById(R.id.album_title);
        this.mListView = (ListView) findViewById(R.id.album_list);
        this.mTitle.setText(this.mtitle);
        this.mBtnBack.setOnClickListener(this);
        this.mListView.setAdapter(new AlbumListAdapter(this, this.mList));
    }

    public void onClick(View view) {
        if (view.getId() == this.mBtnBack.getId()) {
            finish();
        }
    }

    public String[] getAllFragmentTags() {
        return null;
    }

    public String getActivityName() {
        return AlbumListActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
