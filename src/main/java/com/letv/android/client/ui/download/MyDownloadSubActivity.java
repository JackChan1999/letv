package com.letv.android.client.ui.download;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.activity.BaseBatchDelActivity;
import com.letv.android.client.ui.downloadpage.DownloadVideoPageActivity;
import com.letv.android.client.utils.CursorAdapter;
import com.letv.android.client.utils.CursorLoader;
import com.letv.android.client.view.CustomLoadingDialog;
import com.letv.core.BaseApplication;
import com.letv.core.utils.DialogUtil;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.download.bean.DownloadVideo;
import com.letv.download.manager.DownloadManager;
import com.letv.download.manager.VideoFileManager;
import com.letv.download.util.DownloadUtil;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.mobile.lebox.jump.PageJumpUtil;
import java.util.HashSet;
import java.util.Set;
import master.flame.danmaku.danmaku.parser.DanmakuFactory;

public class MyDownloadSubActivity extends BaseBatchDelActivity implements OnClickListener, LoaderCallbacks<Cursor> {
    private static final String TAG = MyDownloadSubActivity.class.getSimpleName();
    private TextView actTextV;
    private ImageView backImageV;
    private ProgressBar capacityPBar;
    private TextView capacityTextV;
    private TextView delBtn;
    private RelativeLayout footView;
    private int from;
    private ListView listView;
    private String mAlbumName;
    private Context mContext;
    private long mCurrentAid;
    private CustomLoadingDialog mDialog;
    private ValueAnimator mFootViewAnimator;
    Handler mHandler;
    private boolean mIsFromRecom;
    private int measuredHeight;
    private boolean mhasmeasured;
    private MyDownloadFinishSubListAdapter myDownloadFinishSubListAdapter;
    private int startFootViewHeight;

    public class MyDownloadFinishSubListAdapter extends CursorAdapter {
        private Set<DownloadVideo> mDeleteSet;
        final /* synthetic */ MyDownloadSubActivity this$0;

        public Set<DownloadVideo> getDeleteSet() {
            return this.mDeleteSet;
        }

        public MyDownloadFinishSubListAdapter(MyDownloadSubActivity this$0, Context context, Cursor c) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
            super(context, c, false);
            this.mDeleteSet = new HashSet();
        }

        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return UIsUtils.inflate(this.mContext, R.layout.fragment_my_download_finish_item_new, parent, false);
        }

        public void bindView(View view, Context context, Cursor cursor) {
            DownloadFinishItem downloadFinishItem = (DownloadFinishItem) view;
            DownloadVideo downloadVideo = DownloadManager.getDownloadVideo(cursor);
            downloadFinishItem.setIsDownloadFinish(true);
            downloadFinishItem.setBatchDel(this.this$0);
            downloadFinishItem.setDeleteSetVideo(this.mDeleteSet);
            downloadFinishItem.bindView(downloadVideo);
            downloadFinishItem.setOnClickListener(new 1(this, downloadVideo, downloadFinishItem));
        }

        public int getBatchDelNum() {
            return this.mDeleteSet.size();
        }

        public void selectAllOrNot(boolean select) {
            this.mDeleteSet.clear();
            if (select) {
                for (int i = 0; i < getCount(); i++) {
                    this.mDeleteSet.add(DownloadManager.getDownloadVideo((Cursor) getItem(i)));
                }
            }
            notifyDataSetChanged();
        }
    }

    public MyDownloadSubActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mhasmeasured = false;
        this.measuredHeight = 0;
        this.mContext = BaseApplication.instance;
        this.mCurrentAid = 0;
        this.mIsFromRecom = false;
        this.startFootViewHeight = 0;
        this.mHandler = new Handler();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_download_sub);
        initBatchDelView();
        readIntent();
        initView();
        getSupportLoaderManager().initLoader(1, null, this);
    }

    protected void onResume() {
        super.onResume();
        updateBatchDelView();
    }

    public static void launch(Context context, int albumId, String albumName, int from, boolean isFromRecom) {
        if (context != null) {
            Intent intent = new Intent(context, MyDownloadSubActivity.class);
            intent.putExtra(PageJumpUtil.IN_TO_ALBUM_PID, albumId);
            intent.putExtra(PageJumpUtil.IN_TO_ALBUM_NAME, albumName);
            intent.putExtra("isFromRecom", isFromRecom);
            intent.putExtra("from", from);
            intent.setFlags(268435456);
            context.startActivity(intent);
        }
    }

    private void readIntent() {
        this.mCurrentAid = (long) getIntent().getIntExtra(PageJumpUtil.IN_TO_ALBUM_PID, 0);
        this.mAlbumName = getIntent().getStringExtra(PageJumpUtil.IN_TO_ALBUM_NAME);
        this.from = getIntent().getIntExtra("from", 0);
        this.mIsFromRecom = getIntent().getBooleanExtra("isFromRecom", false);
    }

    private void doFootViewAnimator(int start, final int end) {
        this.mFootViewAnimator = ValueAnimator.ofInt(new int[]{start, end});
        this.mFootViewAnimator.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ MyDownloadSubActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                int value = ((Integer) animation.getAnimatedValue()).intValue();
                LayoutParams layoutParams = this.this$0.footView.getLayoutParams();
                layoutParams.height = value;
                this.this$0.footView.setLayoutParams(layoutParams);
            }
        });
        this.mFootViewAnimator.addListener(new AnimatorListener(this) {
            final /* synthetic */ MyDownloadSubActivity this$0;

            public void onAnimationStart(Animator animation) {
                if (end != 0) {
                    this.this$0.footView.setPadding(0, 0, 0, 0);
                }
            }

            public void onAnimationEnd(Animator animation) {
                if (end == 0) {
                    this.this$0.footView.setPadding(0, -this.this$0.measuredHeight, 0, 0);
                }
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        this.mFootViewAnimator.start();
    }

    private void showFootView() {
        if (!this.mIsFromRecom) {
            if (VERSION.SDK_INT >= 14) {
                doFootViewAnimator(0, this.startFootViewHeight);
            } else {
                this.footView.setPadding(0, 0, 0, 0);
            }
        }
    }

    private void hideFootView() {
        if (!this.mIsFromRecom) {
            if (VERSION.SDK_INT >= 14) {
                this.startFootViewHeight = this.footView.getHeight();
                doFootViewAnimator(this.startFootViewHeight, 0);
                return;
            }
            this.footView.setPadding(0, -this.measuredHeight, 0, 0);
        }
    }

    private void initView() {
        ((TextView) findViewById(2131362352)).setText(this.mAlbumName);
        this.backImageV = (ImageView) findViewById(2131362351);
        this.backImageV.setOnClickListener(this);
        this.capacityTextV = (TextView) findViewById(2131361941);
        this.capacityPBar = (ProgressBar) findViewById(2131361940);
        this.listView = (ListView) findViewById(2131361938);
        this.footView = (RelativeLayout) UIsUtils.inflate(this.mContext, R.layout.layout_my_download_foot, null);
        this.footView.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener(this) {
            final /* synthetic */ MyDownloadSubActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean onPreDraw() {
                if (!this.this$0.mhasmeasured) {
                    this.this$0.measuredHeight = this.this$0.footView.getMeasuredHeight();
                    int width = this.this$0.footView.getMeasuredWidth();
                    if (NetworkUtils.isNetworkAvailable()) {
                        this.this$0.footView.setPadding(0, 0, 0, 0);
                    } else {
                        this.this$0.footView.setPadding(0, -this.this$0.measuredHeight, 0, 0);
                        LogInfo.log("HYX", "measuredHeight = " + this.this$0.measuredHeight);
                    }
                    this.this$0.mhasmeasured = true;
                }
                return true;
            }
        });
        this.footView.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ MyDownloadSubActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                LogInfo.log(MyDownloadSubActivity.TAG, ">> mCurrentAid >> " + this.this$0.mCurrentAid);
                if (NetworkUtils.isNetworkAvailable()) {
                    DownloadVideoPageActivity.launch(this.this$0.getActivity(), this.this$0.mCurrentAid, 1, 2);
                } else {
                    UIsUtils.showToast(2131100493);
                }
            }
        });
        if (!this.mIsFromRecom) {
            this.listView.addHeaderView(this.footView);
        }
        this.myDownloadFinishSubListAdapter = new MyDownloadFinishSubListAdapter(this, this.mContext, null);
        this.listView.setAdapter(this.myDownloadFinishSubListAdapter);
        this.mDialog = new CustomLoadingDialog(getActivity());
        this.mDialog.setCanceledOnTouchOutside(false);
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
        switch (v.getId()) {
            case 2131362351:
                finish();
                return;
            default:
                return;
        }
    }

    public String getActivityName() {
        return MyDownloadSubActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }

    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        return new CursorLoader(this.mContext, DownloadManager.DOWNLOAD_VIDEO_URI, null, "aid = " + this.mCurrentAid + " and " + "state" + " = " + 4, null, "ord asc ");
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor newCursor) {
        if (this.listView.getVisibility() == 8) {
            this.listView.setVisibility(0);
        }
        if (this.myDownloadFinishSubListAdapter != null) {
            this.myDownloadFinishSubListAdapter.swapCursor(newCursor);
        }
        this.mHandler.postDelayed(new Runnable(this) {
            final /* synthetic */ MyDownloadSubActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void run() {
                DownloadUtil.asyUpdateFileData();
            }
        }, DanmakuFactory.MIN_DANMAKU_DURATION);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void onShowEditState() {
    }

    public void onCancelEditState() {
    }

    private void asynBachDelete() {
        new AsyncTask<Void, Void, Void>(this) {
            final /* synthetic */ MyDownloadSubActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            protected void onPreExecute() {
                this.this$0.showLoadingDialog();
            }

            protected Void doInBackground(Void... params) {
                Set<DownloadVideo> deleteSet = this.this$0.myDownloadFinishSubListAdapter.getDeleteSet();
                synchronized (deleteSet) {
                    for (DownloadVideo downloadVideo : deleteSet) {
                        VideoFileManager.delDownloadedVideo(downloadVideo);
                    }
                }
                return null;
            }

            protected void onPostExecute(Void result) {
                try {
                    this.this$0.showFootView();
                    this.this$0.cancelLoadingDialog();
                    this.this$0.updateBatchDelView();
                    DownloadManager.updateDownloadAlbumWatchByAid(this.this$0.mCurrentAid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute(new Void[0]);
    }

    public void onDoBatchDelete() {
        if (isSelectAll()) {
            DialogUtil.showDialog(this, this.mContext.getResources().getString(2131100973), "", "", null, new DialogInterface.OnClickListener(this) {
                final /* synthetic */ MyDownloadSubActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onClick(DialogInterface dialog, int which) {
                    this.this$0.asynBachDelete();
                    dialog.dismiss();
                }
            });
        } else {
            asynBachDelete();
        }
    }

    public void onSelectAll() {
        if (this.myDownloadFinishSubListAdapter != null) {
            this.myDownloadFinishSubListAdapter.selectAllOrNot(true);
        }
    }

    public boolean onIsAdapterEmpty() {
        return !DownloadManager.isHasDownloadAlbumVideo(this.mCurrentAid);
    }

    public void onClearSelectAll() {
        if (isEditing()) {
            hideFootView();
        } else {
            showFootView();
        }
        if (this.myDownloadFinishSubListAdapter != null) {
            this.myDownloadFinishSubListAdapter.selectAllOrNot(false);
        }
    }

    public int onSelectNum() {
        return this.myDownloadFinishSubListAdapter.getBatchDelNum();
    }
}
