package com.letv.mobile.lebox.ui.download;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.mobile.http.utils.NetworkUtil;
import com.letv.mobile.lebox.LeBoxApp;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.http.lebox.bean.TaskVideoBean;
import com.letv.mobile.lebox.httpmanager.HttpCacheAssistant;
import com.letv.mobile.lebox.httpmanager.HttpRequesetManager;
import com.letv.mobile.lebox.httpmanager.HttpRequesetManager.HttpCallBack;
import com.letv.mobile.lebox.jump.PageJumpUtil;
import com.letv.mobile.lebox.ui.album.DownloadVideoAlbumPageActivity;
import com.letv.mobile.lebox.utils.DialogUtil;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.utils.Util;
import com.letv.mobile.lebox.view.CustomLoadingDialog;
import com.letv.mobile.lebox.view.DownloadFinishItem;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyDownloadSubActivity extends BaseBatchDelActivity implements OnClickListener {
    private static final String TAG = MyDownloadSubActivity.class.getSimpleName();
    private TextView actTextV;
    private ImageView backImageV;
    View bottomActionView;
    private ProgressBar capacityPBar;
    private TextView capacityTextV;
    private TextView delBtn;
    Button deleteBtn;
    private RelativeLayout footView;
    private int from;
    private ListView listView;
    private String mAlbumName;
    private final Context mContext = LeBoxApp.getApplication();
    private long mCurrentAid = 0;
    private CustomLoadingDialog mDialog;
    Handler mHandler = new Handler();
    private int measuredHeight = 0;
    private boolean mhasmeasured = false;
    private MyDownloadFinishSubListAdapter myDownloadFinishSubListAdapter;
    Button selectBtn;
    private TextView titleTextV;

    public class MyDownloadFinishSubListAdapter extends BaseAdapter {
        private final List<String> mAllVid = new ArrayList();
        private final Context mContext;
        private final Set<TaskVideoBean> mDeleteSet = new HashSet();
        private final List<TaskVideoBean> mDownloadTaskList = new ArrayList();
        private final LayoutInflater mInflater;

        public Set<TaskVideoBean> getDeleteSet() {
            return this.mDeleteSet;
        }

        public MyDownloadFinishSubListAdapter(Context context) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(this.mContext);
        }

        public int getBatchDelNum() {
            return this.mDeleteSet.size();
        }

        public void selectAllOrNot(boolean select) {
            this.mDeleteSet.clear();
            if (select) {
                for (int i = 0; i < getCount(); i++) {
                    this.mDeleteSet.add((TaskVideoBean) getItem(i));
                }
            }
            notifyDataSetChanged();
        }

        public List<String> getAllVid() {
            this.mAllVid.clear();
            if (this.mDownloadTaskList.size() == 0) {
                return this.mAllVid;
            }
            for (TaskVideoBean vid : this.mDownloadTaskList) {
                this.mAllVid.add(vid.getVid());
            }
            return this.mAllVid;
        }

        public int getCount() {
            return this.mDownloadTaskList.size();
        }

        public Object getItem(int position) {
            return this.mDownloadTaskList.get(position);
        }

        public long getItemId(int position) {
            return (long) ((TaskVideoBean) this.mDownloadTaskList.get(position)).hashCode();
        }

        public void updateList(List<TaskVideoBean> list) {
            if (list == null || list.size() == 0) {
                this.mDownloadTaskList.clear();
                return;
            }
            this.mDownloadTaskList.clear();
            for (TaskVideoBean video : list) {
                this.mDownloadTaskList.add(video);
            }
            Logger.d(MyDownloadSubActivity.TAG, "---MyDownloadFinishSubListAdapter--mDownloadTaskList=" + this.mDownloadTaskList);
            notifyDataSetChanged();
        }

        @SuppressLint({"ViewHolder"})
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = this.mInflater.inflate(R.layout.fragment_lebox_download_finish_item_new, parent, false);
            }
            DownloadFinishItem downloadFinishItem = (DownloadFinishItem) convertView;
            TaskVideoBean downloadVideo = (TaskVideoBean) this.mDownloadTaskList.get(position);
            downloadFinishItem.setIsDownloadFinish(true);
            downloadFinishItem.setBatchDel(MyDownloadSubActivity.this);
            downloadFinishItem.setDeleteSetVideo(this.mDeleteSet);
            downloadFinishItem.bindView(downloadVideo);
            downloadFinishItem.setOnClickListener(new 1(this, downloadVideo, downloadFinishItem));
            return convertView;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lebox_download_sub);
        initBatchDelView();
        readIntent();
        initView();
        iniData();
    }

    protected void onResume() {
        super.onResume();
        RefreshData();
        updateBatchDelView();
    }

    private void readIntent() {
        this.mCurrentAid = getIntent().getLongExtra(PageJumpUtil.IN_TO_ALBUM_PID, 0);
        this.mAlbumName = getIntent().getStringExtra(PageJumpUtil.IN_TO_ALBUM_NAME);
        this.from = getIntent().getIntExtra("from", 0);
    }

    private void initView() {
        this.backImageV = (ImageView) findViewById(R.id.common_nav_left);
        this.backImageV.setOnClickListener(this);
        this.titleTextV = (TextView) findViewById(R.id.common_nav_title);
        this.titleTextV.setText(this.mAlbumName);
        this.actTextV = (TextView) findViewById(R.id.textv_act);
        this.capacityTextV = (TextView) findViewById(R.id.textv_capacity);
        this.capacityPBar = (ProgressBar) findViewById(R.id.progressbar_capacity);
        this.delBtn = (TextView) findViewById(R.id.btn_del);
        this.listView = (ListView) findViewById(R.id.listv);
        if (HttpCacheAssistant.getInstanced().isAdmini()) {
            this.footView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.lebox_layout_my_download_foot, null);
            this.footView.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                public boolean onPreDraw() {
                    if (!MyDownloadSubActivity.this.mhasmeasured) {
                        MyDownloadSubActivity.this.measuredHeight = MyDownloadSubActivity.this.footView.getMeasuredHeight();
                        int width = MyDownloadSubActivity.this.footView.getMeasuredWidth();
                        if (NetworkUtil.isNetAvailable()) {
                            MyDownloadSubActivity.this.footView.setPadding(0, 0, 0, 0);
                        } else {
                            MyDownloadSubActivity.this.footView.setPadding(0, -MyDownloadSubActivity.this.measuredHeight, 0, 0);
                            Logger.d("HYX", "measuredHeight = " + MyDownloadSubActivity.this.measuredHeight);
                        }
                        MyDownloadSubActivity.this.mhasmeasured = true;
                    }
                    return true;
                }
            });
            this.footView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Logger.d(MyDownloadSubActivity.TAG, ">> mCurrentAid >> " + MyDownloadSubActivity.this.mCurrentAid);
                    if (NetworkUtil.isNetAvailable()) {
                        DownloadVideoAlbumPageActivity.launch(MyDownloadSubActivity.this, MyDownloadSubActivity.this.mCurrentAid, 1, 2);
                    } else {
                        Util.showToast(R.string.net_error);
                    }
                }
            });
            this.listView.addHeaderView(this.footView);
        }
        this.myDownloadFinishSubListAdapter = new MyDownloadFinishSubListAdapter(this.mContext);
        this.listView.setAdapter(this.myDownloadFinishSubListAdapter);
        this.mDialog = new CustomLoadingDialog(this);
        this.mDialog.setCanceledOnTouchOutside(false);
    }

    private void iniData() {
        showLoadingDialog();
        HttpRequesetManager.getInstance().getAllDownloadFinishTask(new HttpCallBack<List<TaskVideoBean>>() {
            public void callback(int code, String msg, String errorCode, List<TaskVideoBean> object) {
                if (object != null) {
                    List<DownloadAlbum> albumList = HttpRequesetManager.getDownloadAlbumList(object);
                    Logger.d(MyDownloadSubActivity.TAG, "--initData---albumList" + albumList);
                    DownloadAlbum currentAlbum = null;
                    if (albumList != null && albumList.size() > 0) {
                        for (DownloadAlbum album : albumList) {
                            if (album.pid == MyDownloadSubActivity.this.mCurrentAid) {
                                currentAlbum = album;
                            }
                        }
                    }
                    if (currentAlbum != null) {
                        MyDownloadSubActivity.this.myDownloadFinishSubListAdapter.updateList(currentAlbum.getVideoAlbum());
                    } else {
                        Util.showToast(R.string.toast_msg_get_album_fail);
                    }
                } else {
                    Logger.d(MyDownloadSubActivity.TAG, "--initData-code=" + code + "-msg=" + msg + "-errorCode=" + errorCode + "-object=" + object);
                    Util.showToast(R.string.toast_msg_get_album_fail);
                }
                MyDownloadSubActivity.this.updateBatchDelView();
                MyDownloadSubActivity.this.cancelLoadingDialog();
            }
        });
    }

    private void RefreshData() {
        List<DownloadAlbum> albumList = HttpRequesetManager.getDownloadAlbumList(HttpCacheAssistant.getInstanced().getCompleteList());
        Logger.d(TAG, "--RefreshData---albumList" + albumList);
        DownloadAlbum currentAlbum = null;
        if (albumList != null && albumList.size() > 0) {
            for (DownloadAlbum album : albumList) {
                if (album.pid == this.mCurrentAid) {
                    currentAlbum = album;
                }
            }
        }
        if (currentAlbum != null) {
            this.myDownloadFinishSubListAdapter.updateList(currentAlbum.getVideoAlbum());
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

    public void onClick(View v) {
        if (v == this.backImageV) {
            finish();
        }
    }

    public void onShowEditState() {
    }

    public void onCancelEditState() {
    }

    public void onDoBatchDelete() {
        if (isSelectAll()) {
            DialogUtil.showDialog(this, this.mContext.getResources().getString(R.string.tip_del_download_all_dialog), "", "", null, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    MyDownloadSubActivity.this.BachDelete();
                    dialog.dismiss();
                }
            });
        } else {
            BachDelete();
        }
    }

    private void BachDelete() {
        Set<TaskVideoBean> videoSet = this.myDownloadFinishSubListAdapter.getDeleteSet();
        if (videoSet.size() > 0) {
            List deleteVidList = new ArrayList();
            for (TaskVideoBean video : videoSet) {
                deleteVidList.add(video.getVid());
            }
            showLoadingDialog();
            HttpRequesetManager.getInstance().deleteTask(deleteVidList, new HttpCallBack<List<TaskVideoBean>>() {
                public void callback(int code, String msg, String errorCode, List<TaskVideoBean> object) {
                    MyDownloadSubActivity.this.cancelLoadingDialog();
                    if (MyDownloadSubActivity.this.isSelectAll()) {
                        MyDownloadSubActivity.this.finish();
                        return;
                    }
                    if (object != null) {
                        List<DownloadAlbum> albumList = HttpRequesetManager.getDownloadAlbumList(object);
                        Logger.d(MyDownloadSubActivity.TAG, "--onDoBatchDelete---albumList" + albumList);
                        DownloadAlbum currentAlbum = null;
                        for (DownloadAlbum album : albumList) {
                            if (album.pid == MyDownloadSubActivity.this.mCurrentAid) {
                                currentAlbum = album;
                            }
                        }
                        if (currentAlbum == null) {
                            MyDownloadSubActivity.this.finish();
                        }
                        MyDownloadSubActivity.this.myDownloadFinishSubListAdapter.updateList(currentAlbum.getVideoAlbum());
                    } else {
                        Logger.d(MyDownloadSubActivity.TAG, "--onDoBatchDelete-code=" + code + "-msg=" + msg + "-errorCode=" + errorCode + "-object=" + object);
                        Util.showToast(R.string.toast_msg_delete_fail);
                    }
                    MyDownloadSubActivity.this.updateBatchDelView();
                }
            }, "1");
        }
    }

    public void onSelectAll() {
        if (this.myDownloadFinishSubListAdapter != null) {
            this.myDownloadFinishSubListAdapter.selectAllOrNot(true);
        }
    }

    public boolean onIsAdapterEmpty() {
        if (this.myDownloadFinishSubListAdapter != null && this.myDownloadFinishSubListAdapter.getCount() > 0) {
            return false;
        }
        return true;
    }

    public void onClearSelectAll() {
        if (this.myDownloadFinishSubListAdapter != null) {
            this.myDownloadFinishSubListAdapter.selectAllOrNot(false);
        }
    }

    public int onSelectNum() {
        return this.myDownloadFinishSubListAdapter.getBatchDelNum();
    }

    public void onInitData() {
    }
}
