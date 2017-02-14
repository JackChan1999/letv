package com.letv.android.client.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.PointItemAdapter;
import com.letv.android.client.task.RequestUserByTokenTask;
import com.letv.android.client.view.RoundImageView;
import com.letv.cache.LetvCacheMannager;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.UserCenterApi;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.PointBeanList;
import com.letv.core.bean.PointBeanList.PointBean;
import com.letv.core.bean.UserBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.PointParser;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.ToastUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class PointsActivtiy extends PimBaseActivity {
    private TextView hPlay;
    private ListView list;
    private TextView mypoints;
    private RoundImageView pim_head;
    private TextView points_name;
    private Button pointsconvert;
    private Button pointsdraw;

    public PointsActivtiy() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        initUI();
        setTitle(2131100459);
    }

    private void getPoints() {
        RequestUserByTokenTask.getUserByTokenTask(getActivity(), PreferencesManager.getInstance().getSso_tk(), new SimpleResponse<UserBean>(this) {
            final /* synthetic */ PointsActivtiy this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onCacheResponse(VolleyRequest<UserBean> volleyRequest, UserBean result, DataHull hull, CacheResponseState state) {
                if (state == CacheResponseState.SUCCESS) {
                    this.this$0.setPoints(result);
                }
            }
        });
    }

    public void initUI() {
        super.initUI();
        Intent intent = getIntent();
        UserBean user = (UserBean) intent.getSerializableExtra("User");
        String fromValue = intent.getStringExtra("from");
        this.pointsdraw = (Button) findViewById(R.id.pointsdraw);
        this.pointsconvert = (Button) findViewById(R.id.pointsconvert);
        this.pim_head = (RoundImageView) findViewById(R.id.pim_head);
        this.hPlay = (TextView) findViewById(R.id.iniviteNum);
        this.points_name = (TextView) findViewById(R.id.points_name);
        this.mypoints = (TextView) findViewById(R.id.mypoints);
        this.list = (ListView) findViewById(R.id.poinlist);
        if (user != null) {
            initView(user);
            getUserPointInfo();
        } else if (!TextUtils.isEmpty(fromValue) && "webview".equals(fromValue)) {
            if (PreferencesManager.getInstance().isLogin()) {
                getUserBeanTask();
            } else {
                LetvLoginActivity.launch((Activity) this, 3);
            }
        }
    }

    private void getUserBeanTask() {
        RequestUserByTokenTask.getUserByTokenTask(this, PreferencesManager.getInstance().getSso_tk(), new SimpleResponse<UserBean>(this) {
            final /* synthetic */ PointsActivtiy this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<UserBean> volleyRequest, UserBean result, DataHull hull, NetworkResponseState state) {
                switch (state) {
                    case NETWORK_ERROR:
                        ToastUtils.showToast(this.this$0.getActivity(), 2131100332);
                        this.this$0.finish();
                        return;
                    case NETWORK_NOT_AVAILABLE:
                        ToastUtils.showToast(this.this$0.getActivity(), 2131100332);
                        this.this$0.finish();
                        break;
                }
                ToastUtils.showToast(this.this$0.getActivity(), 2131100332);
                this.this$0.finish();
            }

            public void onCacheResponse(VolleyRequest<UserBean> volleyRequest, UserBean result, DataHull hull, CacheResponseState state) {
                if (state == CacheResponseState.SUCCESS && result != null) {
                    this.this$0.initView(result);
                    this.this$0.getUserPointInfo();
                }
            }
        });
    }

    public void getUserPointInfo() {
        new LetvRequest(PointBeanList.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(UserCenterApi.getInstance().getPointInfo(0, PreferencesManager.getInstance().getSso_tk())).setCache(new VolleyNoCache()).setParser(new PointParser()).setCallback(new SimpleResponse<PointBeanList>(this) {
            final /* synthetic */ PointsActivtiy this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<PointBeanList> volleyRequest, PointBeanList result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("ZSM", "getUserPointInfo onNetworkResponse == " + state);
                switch (state) {
                    case SUCCESS:
                        this.this$0.initListText(result);
                        return;
                    default:
                        return;
                }
            }

            public void onErrorReport(VolleyRequest<PointBeanList> request, String errorInfo) {
                LogInfo.log("ZSM", "mineListRequestTask onErrorReport == " + errorInfo);
                super.onErrorReport(request, errorInfo);
            }
        }).add();
    }

    protected void onResume() {
        super.onResume();
        getPoints();
    }

    public void initListText(final PointBeanList bean) {
        this.list.setAdapter(new PointItemAdapter(this, bean));
        this.list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("my_points", Integer.parseInt(((PointBean) bean.get(position)).credit) * ((PointBean) bean.get(position)).state);
                intent.putExtra("flag", position);
                intent.setClass(PointsActivtiy.this, TaskDetailActivity.class);
                PointsActivtiy.this.startActivity(intent);
            }
        });
    }

    @SuppressLint({"ResourceAsColor"})
    public void initView(UserBean user) {
        String icon = user.picture;
        String tag = (String) this.pim_head.getTag();
        if (!TextUtils.isEmpty(tag) && TextUtils.isEmpty(icon) && tag.equalsIgnoreCase(icon)) {
            this.pim_head.setImageDrawable(getResources().getDrawable(2130837633));
        } else {
            LetvCacheMannager.getInstance().loadImage(icon, this.pim_head);
            this.pim_head.setTag(icon);
        }
        this.hPlay.setText(2131100680);
        this.hPlay.setVisibility(0);
        this.hPlay.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PointsActivtiy this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(this.this$0, PointsWeb.class);
                intent.putExtra("url", "http://my.letv.com/jifen/m/introduce");
                intent.putExtra("title", this.this$0.getString(2131100680));
                this.this$0.startActivity(intent);
            }
        });
        this.pointsconvert.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PointsActivtiy this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(this.this$0, PointsWeb.class);
                intent.putExtra("title", this.this$0.getString(2131100085));
                intent.putExtra("url", "http://my.letv.com/jifen/m/list");
                this.this$0.startActivity(intent);
            }
        });
        this.pointsdraw.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PointsActivtiy this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(this.this$0, PointsWeb.class);
                intent.putExtra("title", this.this$0.getString(2131099875));
                intent.putExtra("url", "http://my.letv.com/jifen/m/lottery");
                this.this$0.startActivity(intent);
            }
        });
        this.points_name.setText(TextUtils.isEmpty(user.nickname) ? user.username : user.nickname);
        setPoints(user);
    }

    private void setPoints(UserBean user) {
        String score = TextUtils.isEmpty(user.score) ? "0" : user.score;
        try {
            if (!TextUtils.isEmpty(score) && score.length() > 0) {
                score = score.substring(0, score.indexOf("."));
            }
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        this.mypoints.setText(score);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.pim_head != null) {
            this.pim_head.setImageDrawable(null);
            this.pim_head = null;
        }
    }

    public int getContentView() {
        return R.layout.my_points;
    }

    public String getActivityName() {
        return PointsActivtiy.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
