package com.letv.android.client.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.activity.FindActActivity;
import com.letv.android.client.commonlib.adapter.ViewHolder;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.utils.UIControllerUtils;
import com.letv.android.client.hot.LetvHotActivity;
import com.letv.core.bean.FindChildDataAreaBean;
import com.letv.core.bean.FindDataAreaBean;
import com.letv.core.bean.FindDataBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.StaticticsName;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginconfig.utils.JarLaunchUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class FindAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private ExpandableListView mExpandableListView;
    private List<FindDataBean> mFindDataListBean;

    public FindAdapter(Context context, List<FindDataBean> findDataListBean) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mContext = context;
        this.mFindDataListBean = findDataListBean;
    }

    public void setFindDataListBean(List<FindDataBean> findDataListBean) {
        this.mFindDataListBean = findDataListBean;
        notifyDataSetChanged();
        LogInfo.log("wlx", "刷新发现首页数据.");
    }

    public void setListView(ExpandableListView expandableListView) {
        this.mExpandableListView = expandableListView;
    }

    public void onGroupExpanded(int groupPosition) {
        int len = getGroupCount();
        for (int i = 0; i < len; i++) {
            if (groupPosition != i) {
                this.mExpandableListView.expandGroup(i);
            }
        }
    }

    public int getGroupCount() {
        return this.mFindDataListBean.size();
    }

    public int getChildrenCount(int groupPosition) {
        if (this.mFindDataListBean.get(groupPosition) == null || ((FindDataBean) this.mFindDataListBean.get(groupPosition)).getData() == null) {
            return 0;
        }
        return ((FindDataBean) this.mFindDataListBean.get(groupPosition)).getData().size();
    }

    public Object getGroup(int groupPosition) {
        return this.mFindDataListBean.get(groupPosition);
    }

    public Object getChild(int groupPosition, int childPosition) {
        if (this.mFindDataListBean.get(groupPosition) == null || ((FindDataBean) this.mFindDataListBean.get(groupPosition)).getData() == null) {
            return null;
        }
        return ((FindDataBean) this.mFindDataListBean.get(groupPosition)).getData().get(childPosition);
    }

    public long getGroupId(int groupPosition) {
        return (long) groupPosition;
    }

    public long getChildId(int groupPosition, int childPosition) {
        return (long) childPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return ViewHolder.get(this.mContext, convertView, R.layout.find_group_item).getConvertView();
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder childViewHolder = ViewHolder.get(this.mContext, convertView, R.layout.find_child_list_item);
        initItemView(groupPosition, childPosition, isLastChild, childViewHolder);
        return childViewHolder.getConvertView();
    }

    private void initItemView(int groupPosition, int childPosition, boolean isLastChild, ViewHolder childViewHolder) {
        RelativeLayout childItemRL = (RelativeLayout) childViewHolder.getView(R.id.find_child_itemRL);
        View childImageView = (ImageView) childViewHolder.getView(R.id.find_child_item_nameIconIV);
        TextView childTextView = (TextView) childViewHolder.getView(R.id.find_child_item_nameTV);
        ImageView lineTop = (ImageView) childViewHolder.getView(R.id.find_child_line_top);
        ImageView lineBottom = (ImageView) childViewHolder.getView(R.id.find_child_line_bottom);
        View newsPointView = childViewHolder.getView(R.id.find_news_point_view);
        FindDataBean findDataBean = (FindDataBean) this.mFindDataListBean.get(groupPosition);
        if (findDataBean != null && findDataBean.getData() != null) {
            FindDataAreaBean findDataAreaBean = (FindDataAreaBean) findDataBean.getData().get(childPosition);
            if (findDataAreaBean != null) {
                setListener(childPosition, childItemRL, findDataBean, findDataAreaBean);
                if (!isLastChild) {
                    lineBottom.setVisibility(8);
                }
                if (childPosition != 0) {
                    lineTop.setPadding(UIsUtils.dipToPx(15.0f), 0, 0, 0);
                }
                String picUrl = null;
                if ("4".equals(findDataBean.area)) {
                    newsPointView.setVisibility(8);
                    picUrl = findDataAreaBean.findAppBean.mobilePic;
                } else if (!"3".equals(findDataBean.area)) {
                    newsPointView.setVisibility(8);
                    picUrl = findDataAreaBean.mobilePic;
                } else if ("201".equalsIgnoreCase(findDataAreaBean.type)) {
                    newsPointView.setVisibility(findDataAreaBean.isNewData() ? 0 : 8);
                    picUrl = findDataAreaBean.mobilePic;
                } else if (DialogMsgConstantId.TWO_ZERO_TWO_CONSTANT.equalsIgnoreCase(findDataAreaBean.type)) {
                    ArrayList<FindChildDataAreaBean> fcdas = findDataAreaBean.data;
                    if (fcdas != null && fcdas.size() > 0) {
                        picUrl = ((FindChildDataAreaBean) fcdas.get(0)).mobilePic;
                    }
                    newsPointView.setVisibility(8);
                } else {
                    picUrl = findDataAreaBean.mobilePic;
                    newsPointView.setVisibility(8);
                }
                if (!TextUtils.isEmpty(picUrl)) {
                    ImageDownloader.getInstance().download(childImageView, picUrl);
                }
                setTitle(childTextView, findDataBean, findDataAreaBean);
            }
        }
    }

    private boolean isShowNews(FindDataAreaBean findDataAreaBean, JSONObject jsonObject) {
        if (jsonObject == null) {
            return true;
        }
        String prefTimes = jsonObject.optString(findDataAreaBean.title);
        if (prefTimes == null || !prefTimes.equals(findDataAreaBean.mtime)) {
            return true;
        }
        return false;
    }

    private void setTitle(TextView childTextView, FindDataBean findDataBean, FindDataAreaBean findDataAreaBean) {
        if ("1".equals(findDataBean.area) || "2".equals(findDataBean.area)) {
            childTextView.setText(findDataAreaBean.title);
        } else if ("3".equals(findDataBean.area)) {
            childTextView.setText(findDataAreaBean.name);
        } else if ("4".equals(findDataBean.area)) {
            childTextView.setText(findDataAreaBean.findAppBean.nameCn);
        }
    }

    private void setListener(int childPosition, RelativeLayout childItemRL, FindDataBean findDataBean, FindDataAreaBean findDataAreaBean) {
        childItemRL.setOnClickListener(new 1(this, findDataBean, childPosition, findDataAreaBean));
    }

    private void doItemClick(FindDataBean findDataBean, FindDataAreaBean findDataAreaBean, int childPosition) {
        if ("1".equals(findDataBean.area)) {
            StringBuilder sb;
            if ("1".equals(findDataAreaBean.type)) {
                LogInfo.log("plugin", "准备调起专题Activity...");
                JarLaunchUtils.launchTopicActivity(this.mContext);
                LogInfo.LogStatistics("发现--专题");
                StatisticsUtils.staticticsInfoPost(this.mContext, "0", "di01", null, 1, null, PageIdConstant.finaPage, null, null, null, null, null);
                sb = new StringBuilder();
                sb.append(StaticticsName.STATICTICS_NAM_PAGE_ID).append(PageIdConstant.topicListCategoryPage);
                DataStatistics.getInstance().sendActionInfo(this.mContext, "0", "0", LetvUtils.getPcode(), "19", sb.toString(), "0", null, null, null, LetvUtils.getUID(), null, null, null, null, PreferencesManager.getInstance().isLogin() ? 0 : 1, null);
            } else if ("5".equals(findDataAreaBean.type)) {
                this.mContext.startActivity(new Intent(this.mContext, LetvHotActivity.class));
                LogInfo.LogStatistics("发现--热点");
                StatisticsUtils.staticticsInfoPost(this.mContext, "0", "di01", null, 2, null, PageIdConstant.finaPage, null, null, null, null, null);
                sb = new StringBuilder();
                sb.append(StaticticsName.STATICTICS_NAM_PAGE_ID).append(PageIdConstant.hotIndexCategoryPage);
                DataStatistics.getInstance().sendActionInfo(this.mContext, "0", "0", LetvUtils.getPcode(), "19", sb.toString(), "0", null, null, null, LetvUtils.getUID(), null, null, null, null, PreferencesManager.getInstance().isLogin() ? 0 : 1, null);
            }
        } else if ("2".equals(findDataBean.area) && "101".equals(findDataAreaBean.type)) {
            Intent intent = new Intent(JarConstant.LEZXING_ACTION_CAPTUREACTIVITY);
            intent.putExtra("extra.jarname", JarConstant.LETV_ZXING_NAME);
            intent.putExtra("extra.packagename", JarConstant.LETV_ZXING_PACKAGENAME);
            intent.putExtra("extra.class", "CaptureActivity");
            intent.putExtra("top", this.mContext.getString(2131100440));
            intent.putExtra("bottom", this.mContext.getString(2131100441));
            if (!(this.mContext instanceof Activity)) {
                intent.addFlags(268435456);
            }
            this.mContext.startActivity(intent);
            if (VERSION.SDK_INT >= 23 && (this.mContext instanceof Activity)) {
                ((Activity) this.mContext).overridePendingTransition(R.anim.slide_in_from_right, 0);
            }
            LogInfo.LogStatistics("发现--二维码");
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", "di02", null, 1, null, PageIdConstant.finaPage, null, null, null, null, null);
        } else if ("3".equals(findDataBean.area) && "201".equals(findDataAreaBean.type)) {
            LogInfo.LogStatistics("发现--活动中心");
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", "di03", null, 1, null, PageIdConstant.finaPage, null, null, null, null, null);
            FindActActivity.launch(this.mContext);
            findDataAreaBean.saveTimeStamp();
            notifyDataSetChanged();
        } else if ("3".equals(findDataBean.area) && DialogMsgConstantId.TWO_ZERO_TWO_CONSTANT.equals(findDataAreaBean.type)) {
            notifyDataSetChanged();
            if (findDataAreaBean.data != null) {
                try {
                    FindChildDataAreaBean fcda = (FindChildDataAreaBean) findDataAreaBean.data.get(0);
                    if (fcda != null) {
                        if ("1".equalsIgnoreCase(fcda.issdk)) {
                            JarLaunchUtils.launchSportGameDefault(this.mContext);
                        } else if ("0".equalsIgnoreCase(fcda.issdk) && fcda.webViewUrl != null) {
                            new LetvWebViewActivityConfig(this.mContext).launch(fcda.webViewUrl, fcda.nameCn);
                        }
                    }
                } catch (Exception e) {
                }
            }
            LogInfo.LogStatistics("发现--游戏中心");
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", "di03", null, 2, null, PageIdConstant.finaPage, null, null, null, null, null);
        } else if ("4".equals(findDataBean.area)) {
            if (findDataAreaBean.findAppBean != null) {
                UIControllerUtils.gotoActivity(this.mContext, findDataAreaBean.findAppBean);
            }
            LogInfo.LogStatistics("发现--APP推广区域");
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", "di04", null, childPosition + 1, null, PageIdConstant.finaPage, null, null, null, null, null);
        }
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public boolean hasStableIds() {
        return true;
    }
}
