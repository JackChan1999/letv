package com.letv.android.client.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.letv.android.client.R;
import com.letv.android.client.adapter.RecommGalleryAdapter;
import com.letv.android.client.adapter.RecommendlListViewAdapter;
import com.letv.android.client.adapter.RecommendlListViewAdapter.ViewHolder;
import com.letv.android.client.commonlib.fragement.LetvBaseFragment;
import com.letv.android.client.commonlib.view.LetvGallery;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.commonlib.view.PublicLoadLayout.RefreshData;
import com.letv.android.client.utils.UIs;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.RecommData;
import com.letv.core.bean.RecommenApp;
import com.letv.core.bean.RecommendColumn;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.parser.RecommendDataParser;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TopRecommendFragment extends LetvBaseFragment {
    private static final int MSG_GALLERY_IMAGE_MOVE = 1;
    private static final int TIME_GALLERY_IMAGE_MOVE = 5000;
    private static int selectedType = 0;
    private int currentPage;
    private int currentPage2;
    private int currentPage3;
    private String exchid;
    private LetvGallery focusGallery;
    private RecommGalleryAdapter galleryAdapter;
    private LinearLayout gallerySwitchLayout;
    private BootReciever inBootReciever;
    private Set<String> infodatas;
    private boolean isError;
    private boolean isFirst;
    private boolean isRequestData;
    private RecommendlListViewAdapter listAdapter1;
    private RecommendlListViewAdapter listAdapter2;
    private RecommendlListViewAdapter listAdapter3;
    private ListView listView1;
    private ListView listView2;
    private ListView listView3;
    private RecommData mRecommData;
    int pagenumber;
    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radio3;
    private RequestRecommendDataTask requestTask;
    private PublicLoadLayout root;
    private RadioGroup tableGroup;
    int totalNum;
    int totalNum2;
    int totalNum3;

    class BootReciever extends BroadcastReceiver {
        final /* synthetic */ TopRecommendFragment this$0;

        BootReciever(TopRecommendFragment this$0) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
        }

        public void onReceive(Context context, Intent intent) {
            RecommenApp recommenApp;
            boolean z = true;
            if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
                String packageName = intent.getDataString();
                LogInfo.log("HYX", "安装了:" + packageName + "包名的程序");
                String substring = packageName.substring(8);
                LogInfo.log("HYX", "包名:" + substring);
                this.this$0.infodatas.add(substring);
                boolean z2;
                switch (TopRecommendFragment.selectedType) {
                    case 0:
                        LogInfo.log("HYX", "当前为:0");
                        this.this$0.listAdapter1.setdatas(this.this$0.infodatas);
                        recommenApp = (RecommenApp) this.this$0.mRecommData.getAppList().get(0);
                        if (((RecommenApp) this.this$0.mRecommData.getAppList().get(0)).isFlag()) {
                            z2 = false;
                        } else {
                            z2 = true;
                        }
                        recommenApp.setFlag(z2);
                        this.this$0.listAdapter1.notifyDataSetChanged();
                        break;
                    case 1:
                        LogInfo.log("HYX", "当前为:1");
                        this.this$0.listAdapter2.setdatas(this.this$0.infodatas);
                        recommenApp = (RecommenApp) this.this$0.mRecommData.getAppList().get(0);
                        if (((RecommenApp) this.this$0.mRecommData.getAppList().get(0)).isFlag()) {
                            z2 = false;
                        } else {
                            z2 = true;
                        }
                        recommenApp.setFlag(z2);
                        this.this$0.listAdapter2.notifyDataSetChanged();
                        break;
                    case 2:
                        LogInfo.log("HYX", "当前为:2");
                        this.this$0.listAdapter3.setdatas(this.this$0.infodatas);
                        recommenApp = (RecommenApp) this.this$0.mRecommData.getAppList().get(0);
                        if (((RecommenApp) this.this$0.mRecommData.getAppList().get(0)).isFlag()) {
                            z2 = false;
                        } else {
                            z2 = true;
                        }
                        recommenApp.setFlag(z2);
                        this.this$0.listAdapter3.notifyDataSetChanged();
                        break;
                }
            }
            if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
                packageName = intent.getDataString();
                LogInfo.log("HYX", "卸载了:" + packageName + "包名的程序");
                substring = packageName.substring(8);
                LogInfo.log("HYX", "卸载的包名:" + substring);
                this.this$0.infodatas.remove(substring);
                switch (TopRecommendFragment.selectedType) {
                    case 0:
                        LogInfo.log("HYX", "当前为:0");
                        this.this$0.listAdapter1.setdatas(this.this$0.infodatas);
                        recommenApp = (RecommenApp) this.this$0.mRecommData.getAppList().get(1);
                        if (((RecommenApp) this.this$0.mRecommData.getAppList().get(1)).isFlag()) {
                            z = false;
                        }
                        recommenApp.setFlag(z);
                        this.this$0.listAdapter1.notifyDataSetChanged();
                        return;
                    case 1:
                        LogInfo.log("HYX", "当前为:1");
                        this.this$0.listAdapter2.setdatas(this.this$0.infodatas);
                        recommenApp = (RecommenApp) this.this$0.mRecommData.getAppList().get(1);
                        if (((RecommenApp) this.this$0.mRecommData.getAppList().get(1)).isFlag()) {
                            z = false;
                        }
                        recommenApp.setFlag(z);
                        this.this$0.listAdapter2.notifyDataSetChanged();
                        return;
                    case 2:
                        LogInfo.log("HYX", "当前为:2");
                        this.this$0.listAdapter3.setdatas(this.this$0.infodatas);
                        recommenApp = (RecommenApp) this.this$0.mRecommData.getAppList().get(1);
                        if (((RecommenApp) this.this$0.mRecommData.getAppList().get(1)).isFlag()) {
                            z = false;
                        }
                        recommenApp.setFlag(z);
                        this.this$0.listAdapter3.notifyDataSetChanged();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private class GalleryItemSelectedEvent implements OnItemSelectedListener {
        private GalleryItemSelectedEvent() {
        }

        /* synthetic */ GalleryItemSelectedEvent(TopRecommendFragment x0, AnonymousClass1 x1) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this();
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            TopRecommendFragment.this.updateGallerySwitch(TopRecommendFragment.this.gallerySwitchLayout, position % TopRecommendFragment.this.gallerySwitchLayout.getChildCount());
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    private class RequestRecommendDataTask {
        int curpage;
        String markId;
        int pageSize;
        final /* synthetic */ TopRecommendFragment this$0;

        public RequestRecommendDataTask(TopRecommendFragment topRecommendFragment, Context context, int current) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = topRecommendFragment;
            this.curpage = 1;
            this.markId = "0";
            this.pageSize = 30;
            topRecommendFragment.isRequestData = true;
            topRecommendFragment.root.loading(false);
            this.curpage = current;
        }

        public void start() {
            new LetvRequest(RecommData.class).setRequestType(RequestManner.CACHE_THEN_NETROWK).setUrl(MediaAssetApi.getInstance().getRecommDataUrl(this.this$0.exchid, this.curpage, this.pageSize, this.markId)).setCache(new VolleyDiskCache()).setParser(new RecommendDataParser()).setCallback(new 1(this)).add();
        }
    }

    private class ScrollEvent implements OnScrollListener {
        private int _firstVisibleItem;
        private int _totalItemCount;
        private int _visibleItemCount;
        private RecommendlListViewAdapter adapter;
        private ListView listview;
        private int pageNum;
        final /* synthetic */ TopRecommendFragment this$0;

        public ScrollEvent(TopRecommendFragment topRecommendFragment, RecommendlListViewAdapter adapter, ListView listview, int pageNum, int totalNum) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = topRecommendFragment;
            this._firstVisibleItem = 0;
            this._visibleItemCount = 0;
            this._totalItemCount = 0;
            this.pageNum = pageNum;
            this.adapter = adapter;
            this.listview = listview;
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case 0:
                    this.adapter.unLock();
                    this.this$0.loadImage(this.listview);
                    if (this.adapter != null && !this.this$0.isRequestData) {
                        this._totalItemCount = this._firstVisibleItem + this._visibleItemCount;
                        if (this._totalItemCount >= this.adapter.getCount() && this._totalItemCount * 2 < this.this$0.totalNum && this.adapter.getCount() >= 15) {
                            this.this$0.pagenumber = 1;
                            switch (TopRecommendFragment.selectedType) {
                                case 0:
                                    this.this$0.pagenumber = this.this$0.currentPage;
                                    break;
                                case 1:
                                    this.this$0.pagenumber = this.this$0.currentPage2;
                                    break;
                                case 2:
                                    this.this$0.pagenumber = this.this$0.currentPage3;
                                    break;
                            }
                            this.this$0.requestTask = new RequestRecommendDataTask(this.this$0, this.this$0.getActivity(), this.this$0.pagenumber);
                            this.this$0.requestTask.start();
                            return;
                        }
                        return;
                    }
                    return;
                case 1:
                    this.adapter.lock();
                    return;
                case 2:
                    this.adapter.lock();
                    return;
                default:
                    return;
            }
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            this._firstVisibleItem = firstVisibleItem;
            this._visibleItemCount = visibleItemCount;
        }
    }

    public TopRecommendFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.isError = false;
        this.isFirst = true;
        this.infodatas = new HashSet();
        this.currentPage = 1;
        this.currentPage2 = 1;
        this.currentPage3 = 1;
        this.exchid = "2";
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.root = PublicLoadLayout.createPage(getActivity(), (int) R.layout.fragment_top_recommend);
        this.root.setRefreshData(new RefreshData(this) {
            final /* synthetic */ TopRecommendFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void refreshData() {
                this.this$0.currentPage = 1;
                this.this$0.requestTask = new RequestRecommendDataTask(this.this$0, this.this$0.getActivity(), this.this$0.currentPage);
                this.this$0.requestTask.start();
            }
        });
        return this.root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedType = 0;
        this.exchid = "2";
        this.isFirst = true;
        this.currentPage = 1;
        this.currentPage2 = 1;
        this.currentPage3 = 1;
        findView();
        this.inBootReciever = new BootReciever(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PACKAGE_ADDED");
        filter.addAction("android.intent.action.PACKAGE_REMOVED");
        filter.addDataScheme("package");
        getActivity().registerReceiver(this.inBootReciever, filter);
    }

    public void onResume() {
        super.onResume();
        if (this.focusGallery != null) {
            this.focusGallery.startMove(true, 5000);
        }
    }

    public void onPause() {
        super.onPause();
        if (this.focusGallery != null) {
            this.focusGallery.stopMove();
        }
    }

    private void findView() {
        int width = UIs.getScreenWidth();
        int height = UIs.getScreenHeight();
        this.gallerySwitchLayout = (LinearLayout) this.root.findViewById(R.id.home_content_gallery_switch);
        this.focusGallery = (LetvGallery) this.root.findViewById(R.id.top_gallery);
        this.focusGallery.getLayoutParams().height = UIs.zoomWidth(getResources().getDimensionPixelSize(2131165538));
        this.listView1 = (ListView) this.root.findViewById(R.id.listview1);
        this.listView2 = (ListView) this.root.findViewById(R.id.listview2);
        this.listView3 = (ListView) this.root.findViewById(R.id.listview3);
        this.galleryAdapter = new RecommGalleryAdapter(getActivity());
        this.listAdapter1 = new RecommendlListViewAdapter(getActivity(), 1);
        this.listAdapter2 = new RecommendlListViewAdapter(getActivity(), 2);
        this.listAdapter3 = new RecommendlListViewAdapter(getActivity(), 3);
        this.listView1.setAdapter(this.listAdapter1);
        this.listView2.setAdapter(this.listAdapter2);
        this.listView3.setAdapter(this.listAdapter3);
        this.focusGallery.setAdapter(this.galleryAdapter);
        this.focusGallery.setOnItemSelectedListener(new GalleryItemSelectedEvent());
        this.tableGroup = (RadioGroup) this.root.findViewById(R.id.table);
        this.radio1 = (RadioButton) this.root.findViewById(R.id.radiobtn1);
        this.radio2 = (RadioButton) this.root.findViewById(R.id.radiobtn2);
        this.radio3 = (RadioButton) this.root.findViewById(R.id.radiobtn3);
        this.tableGroup.check(new int[]{R.id.radiobtn1, R.id.radiobtn2, R.id.radiobtn3}[selectedType]);
        this.listView1.setVisibility(0);
        this.tableGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(this) {
            int current;
            final /* synthetic */ TopRecommendFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
                this.current = 1;
            }

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String tag = null;
                switch (checkedId) {
                    case R.id.radiobtn1 /*2131362725*/:
                        if (TopRecommendFragment.selectedType != 0) {
                            TopRecommendFragment.selectedType = 0;
                            this.this$0.listView1.setVisibility(0);
                            this.this$0.listView2.setVisibility(8);
                            this.this$0.listView3.setVisibility(8);
                            if (this.this$0.listAdapter1.getList() == null || this.this$0.listAdapter1.getList().size() <= 0) {
                                this.current = this.this$0.currentPage;
                                tag = (String) this.this$0.radio1.getTag();
                                break;
                            }
                            return;
                        }
                        return;
                    case R.id.radiobtn2 /*2131362726*/:
                        if (TopRecommendFragment.selectedType != 1) {
                            TopRecommendFragment.selectedType = 1;
                            this.this$0.listView2.setVisibility(0);
                            this.this$0.listView3.setVisibility(8);
                            this.this$0.listView1.setVisibility(8);
                            if (this.this$0.listAdapter2.getList() == null || this.this$0.listAdapter2.getList().size() <= 0) {
                                this.current = this.this$0.currentPage2;
                                tag = (String) this.this$0.radio2.getTag();
                                break;
                            }
                            return;
                        }
                        return;
                        break;
                    case R.id.radiobtn3 /*2131362727*/:
                        if (TopRecommendFragment.selectedType != 2) {
                            TopRecommendFragment.selectedType = 2;
                            this.this$0.listView3.setVisibility(0);
                            this.this$0.listView2.setVisibility(8);
                            this.this$0.listView1.setVisibility(8);
                            if (this.this$0.listAdapter3.getList() == null || this.this$0.listAdapter3.getList().size() <= 0) {
                                tag = (String) this.this$0.radio3.getTag();
                                this.current = this.this$0.currentPage3;
                                break;
                            }
                            return;
                        }
                        return;
                }
                if (tag != null && !"".equals(tag)) {
                    this.this$0.exchid = tag;
                    this.this$0.requestTask = new RequestRecommendDataTask(this.this$0, this.this$0.getActivity(), this.current);
                    this.this$0.requestTask.start();
                }
            }
        });
        this.listView1.setOnScrollListener(new ScrollEvent(this, this.listAdapter1, this.listView1, this.currentPage, this.totalNum));
        this.listView2.setOnScrollListener(new ScrollEvent(this, this.listAdapter2, this.listView2, this.currentPage2, this.totalNum));
        this.listView3.setOnScrollListener(new ScrollEvent(this, this.listAdapter3, this.listView3, this.currentPage3, this.totalNum));
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogInfo.log("HYX", "onActivityCreated");
        if (this.mRecommData == null || this.mRecommData.getAppList().size() == 0) {
            LogInfo.log("HYX", "访问服务器数据。。");
            this.requestTask = new RequestRecommendDataTask(this, getActivity(), this.currentPage);
            this.requestTask.start();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.inBootReciever != null) {
            getActivity().unregisterReceiver(this.inBootReciever);
        }
        if (this.focusGallery != null) {
            this.focusGallery.destroyView();
        }
        this.focusGallery = null;
        this.tableGroup = null;
        this.radio1 = null;
        this.radio2 = null;
        this.radio3 = null;
        this.galleryAdapter = null;
        this.listAdapter1 = null;
        this.listAdapter2 = null;
        this.listAdapter3 = null;
        this.currentPage = 1;
        this.currentPage2 = 1;
        this.currentPage3 = 1;
        this.mRecommData = null;
        this.requestTask = null;
        if (this.gallerySwitchLayout != null) {
            this.gallerySwitchLayout.removeAllViewsInLayout();
        }
        this.gallerySwitchLayout = null;
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    private void updateUI() {
        if (this.isFirst) {
            this.isFirst = false;
            if (this.mRecommData != null) {
                List<RecommendColumn> columns = this.mRecommData.getColumns();
                if (columns != null && columns.size() > 0) {
                    for (int i = 0; i < columns.size(); i++) {
                        RecommendColumn column = (RecommendColumn) columns.get(i);
                        if (i == 0) {
                            if (this.radio1 != null) {
                                this.radio1.setText(column.getName());
                                this.radio1.setTag(column.getId());
                            }
                        } else if (i == 1) {
                            if (this.radio2 != null) {
                                this.radio2.setText(column.getName());
                                this.radio2.setTag(column.getId());
                            }
                        } else if (i == 2 && this.radio3 != null) {
                            this.radio3.setText(column.getName());
                            this.radio3.setTag(column.getId());
                        }
                    }
                }
            }
            if (!(this.galleryAdapter == null || this.mRecommData.getFocusApps() == null || this.mRecommData.getFocusApps().size() <= 0)) {
                this.galleryAdapter.setList(this.mRecommData.getFocusApps());
                makeGallerySwitch(this.gallerySwitchLayout, this.mRecommData.getFocusApps().size());
                this.galleryAdapter.notifyDataSetChanged();
                this.focusGallery.setSelection(this.mRecommData.getFocusApps().size() * 20);
                this.focusGallery.startMove(true, 5000);
            }
        }
        updateListUI(selectedType);
    }

    private void updateListUI(int selectType) {
        if (this.mRecommData.getAppList() != null) {
            switch (selectType) {
                case 0:
                    this.currentPage++;
                    if (this.totalNum == 0) {
                        this.totalNum = this.mRecommData.getTotalNum();
                    }
                    new AsyncTask<Void, Void, Void>(this) {
                        final /* synthetic */ TopRecommendFragment this$0;

                        {
                            if (HotFix.PREVENT_VERIFY) {
                                System.out.println(VerifyLoad.class);
                            }
                            this.this$0 = this$0;
                        }

                        protected Void doInBackground(Void... params) {
                            if (this.this$0.getActivity() != null) {
                                List<PackageInfo> packageinfos = null;
                                try {
                                    packageinfos = this.this$0.getActivity().getPackageManager().getInstalledPackages(0);
                                } catch (RuntimeException e) {
                                    LogInfo.log("HYX", "异常=" + e.getMessage());
                                }
                                if (packageinfos != null) {
                                    for (PackageInfo info : packageinfos) {
                                        this.this$0.infodatas.add(info.packageName);
                                    }
                                }
                            }
                            return null;
                        }

                        protected void onPostExecute(Void result) {
                            if (this.this$0.listAdapter1 != null) {
                                this.this$0.listAdapter1.setdatas(this.this$0.infodatas);
                                this.this$0.listAdapter1.addList(this.this$0.mRecommData.getAppList());
                                this.this$0.listAdapter1.notifyDataSetChanged();
                            }
                        }
                    }.execute(new Void[0]);
                    return;
                case 1:
                    this.currentPage2++;
                    if (this.totalNum2 == 0) {
                        this.totalNum2 = this.mRecommData.getTotalNum();
                    }
                    new AsyncTask<Void, Void, Void>(this) {
                        final /* synthetic */ TopRecommendFragment this$0;

                        {
                            if (HotFix.PREVENT_VERIFY) {
                                System.out.println(VerifyLoad.class);
                            }
                            this.this$0 = this$0;
                        }

                        protected Void doInBackground(Void... params) {
                            if (this.this$0.getActivity() != null) {
                                List<PackageInfo> packageinfos = null;
                                try {
                                    packageinfos = this.this$0.getActivity().getPackageManager().getInstalledPackages(0);
                                } catch (RuntimeException e) {
                                    LogInfo.log("HYX", "异常=" + e.getMessage());
                                }
                                if (packageinfos != null) {
                                    for (PackageInfo info : packageinfos) {
                                        this.this$0.infodatas.add(info.packageName);
                                    }
                                }
                            }
                            return null;
                        }

                        protected void onPostExecute(Void result) {
                            if (this.this$0.listAdapter2 != null) {
                                this.this$0.listAdapter2.setdatas(this.this$0.infodatas);
                                this.this$0.listAdapter2.addList(this.this$0.mRecommData.getAppList());
                                this.this$0.listAdapter2.notifyDataSetChanged();
                            }
                        }
                    }.execute(new Void[0]);
                    return;
                case 2:
                    this.currentPage3++;
                    if (this.totalNum3 == 0) {
                        this.totalNum3 = this.mRecommData.getTotalNum();
                    }
                    new AsyncTask<Void, Void, Void>(this) {
                        final /* synthetic */ TopRecommendFragment this$0;

                        {
                            if (HotFix.PREVENT_VERIFY) {
                                System.out.println(VerifyLoad.class);
                            }
                            this.this$0 = this$0;
                        }

                        protected Void doInBackground(Void... params) {
                            if (this.this$0.getActivity() != null) {
                                List<PackageInfo> packageinfos = null;
                                try {
                                    packageinfos = this.this$0.getActivity().getPackageManager().getInstalledPackages(0);
                                } catch (RuntimeException e) {
                                    LogInfo.log("HYX", "异常=" + e.getMessage());
                                }
                                if (packageinfos != null) {
                                    for (PackageInfo info : packageinfos) {
                                        this.this$0.infodatas.add(info.packageName);
                                    }
                                }
                            }
                            return null;
                        }

                        protected void onPostExecute(Void result) {
                            if (this.this$0.listAdapter3 != null) {
                                this.this$0.listAdapter3.setdatas(this.this$0.infodatas);
                                this.this$0.listAdapter3.addList(this.this$0.mRecommData.getAppList());
                                this.this$0.listAdapter3.notifyDataSetChanged();
                            }
                        }
                    }.execute(new Void[0]);
                    return;
                default:
                    return;
            }
        }
    }

    private void getRecommendDataTask() {
        if (this.requestTask != null) {
            this.requestTask.start();
        } else if (getActivity() != null) {
            this.requestTask = new RequestRecommendDataTask(this, getActivity(), this.pagenumber);
            this.requestTask.start();
        }
    }

    private void updateGallerySwitch(LinearLayout gallerySwtichLayout, int position) {
        if (gallerySwtichLayout != null) {
            int count = gallerySwtichLayout.getChildCount();
            int i = 0;
            while (i < count) {
                gallerySwtichLayout.getChildAt(i).setSelected(i == position);
                i++;
            }
        }
    }

    private void makeGallerySwitch(LinearLayout gallerySwtichLayout, int count) {
        gallerySwtichLayout.removeAllViews();
        for (int i = 0; i < count; i++) {
            if (getActivity() != null) {
                ImageView itemImageView = new ImageView(getActivity());
                itemImageView.setLayoutParams(new LayoutParams(-2, -2));
                itemImageView.setImageResource(2130838158);
                itemImageView.setPadding(5, 0, 5, 0);
                gallerySwtichLayout.addView(itemImageView);
            }
        }
    }

    private void makeGallerySwitchBackGround(LinearLayout gallerySwtichLayout, int count) {
        int childCount = gallerySwtichLayout.getChildCount();
        if (count > childCount) {
            count -= childCount;
            for (int i = 0; i < count; i++) {
                ImageView itemImageView = new ImageView(getActivity());
                itemImageView.setLayoutParams(new LayoutParams(-2, -2));
                itemImageView.setImageResource(2130838158);
                itemImageView.setPadding(5, 0, 5, 0);
                gallerySwtichLayout.addView(itemImageView, childCount + i);
            }
        }
    }

    private void loadImage(ListView listView) {
        if (listView != null) {
            try {
                int count = listView.getChildCount();
                for (int i = 0; i < count; i++) {
                    Object tag = listView.getChildAt(i).getTag();
                    if (tag != null) {
                        ViewHolder holder = (ViewHolder) tag;
                        Object t1 = holder.iv_1.getTag();
                        if (t1 != null) {
                            ImageDownloader.getInstance().download(holder.iv_1, (String) t1);
                            holder.iv_1.setTag(null);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getTagName() {
        return TopRecommendFragment.class.getSimpleName();
    }

    public int getContainerId() {
        return 0;
    }
}
