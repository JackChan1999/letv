package com.letv.android.client.adapter.channel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.activity.ChannelWallActivity;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.utils.UIControllerUtils;
import com.letv.android.client.listener.OnGridItemMoveListener;
import com.letv.android.remotedevice.Constant.ControlAction;
import com.letv.core.bean.ChannelListBean;
import com.letv.core.bean.ChannelListBean.Channel;
import com.letv.core.db.PreferencesManager;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.pp.utils.NetworkUtils;
import com.tencent.open.yyb.TitleBar;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ChannelFragmentAdapter extends Adapter<ViewHolder> implements OnGridItemMoveListener {
    private static final long ANIM_TIME = 360;
    public static final String CHANNEL_ID = "chnnel_ID";
    private static final int COUNT_PRE_MY_HEADER = 1;
    private static final int COUNT_PRE_OTHER_HEADER = 2;
    public static final int RESULT_CODE = 1002;
    public static final int TYPE_CHANNEL = 2;
    public static final int TYPE_LOCK_CHANNEL = 1;
    public static final int TYPE_MY_CHANNEL_HEADER = 0;
    public static final int TYPE_OTHER_CHANNEL_HEADER = 3;
    private Handler delayHandler = new Handler();
    private boolean isEditMode;
    private boolean mChannelChange;
    private ChannelListBean mChannelListBean;
    private Context mContext;
    private boolean mFromMineCustom;
    private ImageDownloader mImageDownloader;
    private LayoutInflater mInflater;
    private boolean mIsMoveing;
    private ItemTouchHelper mItemTouchHelper;
    private int mLockSize;
    private List<Channel> mMyChannelItems = new ArrayList();
    private boolean mNavigationHasChange;
    private RecyclerView mRecyclerView;
    private int mTopSize;

    public ChannelFragmentAdapter(Context context, ItemTouchHelper helper, ChannelListBean channelListBean, RecyclerView view) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mItemTouchHelper = helper;
        this.mRecyclerView = view;
        this.mChannelListBean = channelListBean;
        if (channelListBean != null && !BaseTypeUtils.isListEmpty(channelListBean.listChannel)) {
            for (int i = 0; i < channelListBean.listChannel.size(); i++) {
                if (channelListBean.listChannel.get(i) != null) {
                    this.mMyChannelItems.add(channelListBean.listChannel.get(i));
                    if (((Channel) channelListBean.listChannel.get(i)).top == 0) {
                        this.mTopSize++;
                    }
                    if (((Channel) channelListBean.listChannel.get(i)).lock == 1) {
                        this.mLockSize++;
                    }
                }
            }
            this.mImageDownloader = ImageDownloader.getInstance();
        }
    }

    public void setFrom(boolean isFromMineCustom) {
        this.mFromMineCustom = isFromMineCustom;
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        if (position > 0 && position < this.mLockSize + 1) {
            return 1;
        }
        if (position == this.mTopSize + 1) {
            return 3;
        }
        return 2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new 1(this, this.mInflater.inflate(R.layout.channel_wall_head, parent, false));
            case 1:
                LockViewHolder lockHolder = new LockViewHolder(this, this.mInflater.inflate(R.layout.fragment_lock_channel_item, parent, false));
                lockHolder.layout.setOnClickListener(new 5(this));
                return lockHolder;
            case 2:
                ViewHolder myHolder = new MyViewHolder(this, this.mInflater.inflate(R.layout.fragment_channel_item, parent, false));
                myHolder.layout.setOnClickListener(new 2(this, myHolder, parent));
                myHolder.layout.setOnLongClickListener(new 3(this, parent, myHolder));
                return myHolder;
            case 3:
                return new 4(this, this.mInflater.inflate(R.layout.item_other_channel_header, parent, false));
            default:
                return null;
        }
    }

    public boolean notifiDataChange(boolean isEdit) {
        this.isEditMode = isEdit;
        if (!this.isEditMode) {
            saveMzcid();
        }
        notifyDataSetChanged();
        this.mChannelChange = false;
        return this.isEditMode;
    }

    public void statisticsPositionChange() {
        if (this.isEditMode && this.mChannelChange && !BaseTypeUtils.isListEmpty(this.mMyChannelItems)) {
            StringBuffer sb = new StringBuffer();
            sb.append("sorts=");
            for (int i = 0; i < this.mMyChannelItems.size(); i++) {
                if (this.mMyChannelItems.get(i) != null) {
                    String cid = String.valueOf(((Channel) this.mMyChannelItems.get(i)).id);
                    if (TextUtils.equals(cid, "3000")) {
                        sb.append("0").append(NetworkUtils.DELIMITER_COLON);
                    } else if (i == this.mMyChannelItems.size() - 1) {
                        sb.append(cid);
                    } else {
                        sb.append(cid).append(NetworkUtils.DELIMITER_COLON);
                    }
                }
            }
            StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.categoryPage, "0", "a11", ControlAction.ACTION_KEY_OK, 2, sb.toString());
        }
    }

    private void doClickOtherToNavigation(ViewGroup parent, int position, MyViewHolder myHolder) {
        RecyclerView recyclerView = (RecyclerView) parent;
        LayoutManager manager = recyclerView.getLayoutManager();
        View currentView = manager.findViewByPosition(myHolder.getAdapterPosition());
        View preTargetView = manager.findViewByPosition(this.mTopSize);
        GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
        int spanCount = gridLayoutManager.getSpanCount();
        int targetPosition = this.mTopSize + 1;
        if (recyclerView.indexOfChild(preTargetView) >= 0) {
            int targetX = preTargetView.getLeft();
            int targetY = preTargetView.getTop();
            if ((targetPosition - 1) % spanCount == 0) {
                View targetView = manager.findViewByPosition(targetPosition);
                targetX = targetView.getLeft();
                targetY = targetView.getTop();
            } else {
                targetX += preTargetView.getWidth() + UIsUtils.dipToPx(TitleBar.SHAREBTN_RIGHT_MARGIN);
                if (gridLayoutManager.findLastVisibleItemPosition() == getItemCount() - 1 && (((getItemCount() - 1) - this.mTopSize) - 2) % spanCount == 0) {
                    if (gridLayoutManager.findFirstVisibleItemPosition() != 0) {
                        targetY += preTargetView.getHeight() + UIsUtils.dipToPx(TitleBar.SHAREBTN_RIGHT_MARGIN);
                    } else if (gridLayoutManager.findFirstCompletelyVisibleItemPosition() != 0) {
                        targetY += (-recyclerView.getChildAt(0).getTop()) - recyclerView.getPaddingTop();
                    }
                }
            }
            moveOtherToMy(myHolder);
            startAnimation(recyclerView, currentView, (float) targetX, (float) targetY);
            return;
        }
        moveOtherToMy(myHolder);
        int index = (this.mTopSize % spanCount) - 1;
        if (index < 0) {
            index = spanCount - 1;
        }
        startAnimation(recyclerView, currentView, (float) ((currentView.getWidth() * index) + (index == 0 ? 0 : (index - 1) * UIsUtils.dipToPx(TitleBar.SHAREBTN_RIGHT_MARGIN))), 0.0f);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {
            TextView tvBtnEdit = (TextView) holder.itemView.findViewById(R.id.my_tip_text);
            if (this.isEditMode) {
                tvBtnEdit.setVisibility(0);
            } else {
                tvBtnEdit.setVisibility(8);
            }
        } else if (holder instanceof MyViewHolder) {
            MyViewHolder myHolder = (MyViewHolder) holder;
            if (position >= this.mTopSize + 2) {
                bean = (Channel) this.mMyChannelItems.get(position - 2);
                myHolder.tipImage.setTag(Integer.valueOf(1));
            } else {
                bean = (Channel) this.mMyChannelItems.get(position - 1);
                myHolder.tipImage.setTag(Integer.valueOf(0));
            }
            myHolder.textView.setText(bean.name);
            this.mImageDownloader.download(myHolder.image, bean.icon, 2130838714, true, false);
            if (this.isEditMode) {
                myHolder.tipImage.setVisibility(0);
                if (position <= this.mTopSize) {
                    myHolder.tipImage.setBackgroundResource(2130837812);
                } else {
                    myHolder.tipImage.setBackgroundResource(2130837811);
                }
                myHolder.layout.setBackgroundResource(R.drawable.channel_wall_item_selecter_bg);
                return;
            }
            myHolder.tipImage.setVisibility(4);
            if (this.mFromMineCustom) {
                myHolder.layout.setBackgroundDrawable(null);
            } else {
                myHolder.layout.setBackgroundResource(2130837816);
            }
        } else if (holder instanceof LockViewHolder) {
            LockViewHolder myHolder2 = (LockViewHolder) holder;
            bean = (Channel) this.mMyChannelItems.get(position - 1);
            myHolder2.textView.setText(bean.name);
            this.mImageDownloader.download(myHolder2.image, bean.icon, 2130838714, true, false);
            if (this.mFromMineCustom) {
                myHolder2.layout.setBackgroundDrawable(null);
            } else {
                myHolder2.layout.setBackgroundResource(2130837816);
            }
        }
    }

    private void startEditMode(RecyclerView parent) {
        this.isEditMode = true;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            if (view != null) {
                ImageView imgEdit = (ImageView) view.findViewById(R.id.edit_tip);
                RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.item_group_id);
                if (layout != null) {
                    layout.setBackgroundResource(R.drawable.channel_wall_item_selecter_bg);
                }
                if (imgEdit != null) {
                    if (((Integer) imgEdit.getTag()).intValue() == 0) {
                        imgEdit.setBackgroundResource(2130837812);
                    } else {
                        imgEdit.setBackgroundResource(2130837811);
                    }
                    imgEdit.setVisibility(0);
                }
            }
        }
    }

    public int getItemCount() {
        return this.mMyChannelItems.size() + 2;
    }

    public int getTopSize() {
        return this.mTopSize;
    }

    private void startAnimation(RecyclerView recyclerView, View currentView, float targetX, float targetY) {
        ViewGroup viewGroup = (ViewGroup) recyclerView.getParent();
        ImageView mirrorView = addMirrorView(viewGroup, recyclerView, currentView);
        Animation animation = getTranslateAnimator(targetX - ((float) currentView.getLeft()), targetY - ((float) currentView.getTop()));
        currentView.setVisibility(4);
        mirrorView.startAnimation(animation);
        animation.setAnimationListener(new 6(this, viewGroup, mirrorView, currentView));
    }

    private void moveMyToOther(MyViewHolder myHolder) {
        int position = myHolder.getAdapterPosition();
        int startPosition = position - 1;
        if (BaseTypeUtils.getElementFromList(this.mMyChannelItems, startPosition) != null) {
            Channel item = (Channel) this.mMyChannelItems.get(startPosition);
            item.top = 1;
            if (this.mTopSize + 1 > this.mMyChannelItems.size()) {
                this.mMyChannelItems.add(item);
                this.mMyChannelItems.remove(startPosition);
            } else {
                this.mMyChannelItems.add(this.mTopSize, item);
                this.mMyChannelItems.remove(startPosition);
            }
            notifyItemMoved(position, this.mTopSize + 1);
            this.mTopSize--;
        }
    }

    private void delaynotify() {
        this.delayHandler.postDelayed(new 7(this), ANIM_TIME);
    }

    private void moveOtherToMy(MyViewHolder otherHolder) {
        int position = processItemRemoveAdd(otherHolder);
        if (position != -1) {
            notifyItemMoved(position, this.mTopSize);
        }
    }

    private int processItemRemoveAdd(MyViewHolder otherHolder) {
        int position = otherHolder.getAdapterPosition();
        int startPosition = position - 2;
        if (BaseTypeUtils.getElementFromList(this.mMyChannelItems, startPosition) == null) {
            return -1;
        }
        Channel item = (Channel) this.mMyChannelItems.get(startPosition);
        this.mMyChannelItems.remove(startPosition);
        this.mMyChannelItems.add(this.mTopSize, item);
        this.mTopSize++;
        return position;
    }

    private ImageView addMirrorView(ViewGroup parent, RecyclerView recyclerView, View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        ImageView mirrorView = new ImageView(recyclerView.getContext());
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        mirrorView.setImageBitmap(bitmap);
        view.setDrawingCacheEnabled(false);
        int[] locations = new int[2];
        view.getLocationOnScreen(locations);
        int[] parenLocations = new int[2];
        recyclerView.getLocationOnScreen(parenLocations);
        LayoutParams params = new LayoutParams(bitmap.getWidth(), bitmap.getHeight());
        params.setMargins(locations[0], (locations[1] - parenLocations[1]) + UIsUtils.dipToPx(44.0f), 0, 0);
        parent.addView(mirrorView, params);
        return mirrorView;
    }

    public void onItemMove(int fromPosition, int toPosition, boolean isFirst, boolean isLast) {
        if (isFirst) {
            if (fromPosition < this.mTopSize + 1) {
                addNavigationLast(fromPosition - 1);
            } else {
                addNavigationLast(fromPosition - 2);
                this.mTopSize++;
            }
        } else if (isLast) {
            if (fromPosition < this.mTopSize + 1) {
                addLastItem(fromPosition - 1);
                this.mTopSize--;
            } else {
                addLastItem(fromPosition - 2);
            }
        } else if (fromPosition > this.mTopSize + 1 && toPosition < this.mTopSize + 1) {
            addAndRemove(fromPosition - 2, toPosition - 1);
            this.mTopSize++;
        } else if (fromPosition < this.mTopSize + 1 && toPosition > this.mTopSize + 1) {
            addAndRemove(fromPosition - 1, toPosition - 2);
            this.mTopSize--;
        } else if (fromPosition < this.mTopSize + 1 && toPosition < this.mTopSize + 1) {
            addAndRemove(fromPosition - 1, toPosition - 1);
        } else if (fromPosition > this.mTopSize + 1 && toPosition > this.mTopSize + 1) {
            addAndRemove(fromPosition - 2, toPosition - 2);
        }
        notifyItemMoved(fromPosition, toPosition);
        this.mChannelChange = true;
    }

    private void addNavigationLast(int fromPosition) {
        if (BaseTypeUtils.getElementFromList(this.mMyChannelItems, fromPosition) != null) {
            this.mMyChannelItems.add(this.mTopSize, (Channel) this.mMyChannelItems.get(fromPosition));
            this.mMyChannelItems.remove(fromPosition + 1);
        }
    }

    private void addLastItem(int fromPosition) {
        if (BaseTypeUtils.getElementFromList(this.mMyChannelItems, fromPosition) != null) {
            this.mMyChannelItems.add((Channel) this.mMyChannelItems.get(fromPosition));
            this.mMyChannelItems.remove(fromPosition);
        }
    }

    private void addAndRemove(int fromPosition, int toPosition) {
        if (BaseTypeUtils.getElementFromList(this.mMyChannelItems, fromPosition) != null) {
            Channel item = (Channel) this.mMyChannelItems.get(fromPosition);
            if (fromPosition >= toPosition) {
                this.mMyChannelItems.add(toPosition, item);
                this.mMyChannelItems.remove(fromPosition + 1);
            } else if (toPosition + 1 > this.mMyChannelItems.size() - 1) {
                this.mMyChannelItems.add(item);
                this.mMyChannelItems.remove(fromPosition);
            } else {
                this.mMyChannelItems.add(toPosition + 1, item);
                this.mMyChannelItems.remove(fromPosition);
            }
        }
    }

    private TranslateAnimation getTranslateAnimator(float targetX, float targetY) {
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 0, targetX, 1, 0.0f, 0, targetY);
        translateAnimation.setDuration(ANIM_TIME);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }

    private void saveMzcid() {
        if (!BaseTypeUtils.isListEmpty(this.mMyChannelItems)) {
            StringBuffer top = new StringBuffer();
            StringBuffer bottom = new StringBuffer();
            String str = "";
            String id = "";
            for (int i = 0; i < this.mMyChannelItems.size(); i++) {
                id = String.valueOf(((Channel) this.mMyChannelItems.get(i)).id);
                if (i < this.mTopSize) {
                    top.append(id).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                    ((Channel) this.mMyChannelItems.get(i)).top = 0;
                    updateMap(id, 0);
                } else {
                    bottom.append(((Channel) this.mMyChannelItems.get(i)).id).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                    ((Channel) this.mMyChannelItems.get(i)).top = 1;
                    updateMap(id, 1);
                }
            }
            str = top.toString();
            if (!TextUtils.isEmpty(str)) {
                str = str.substring(0, str.length() - 1);
                if (TextUtils.equals(str, PreferencesManager.getInstance().getChannelNavigation())) {
                    PreferencesManager.getInstance().setChannelNavigationChange(false);
                } else {
                    this.mNavigationHasChange = true;
                    PreferencesManager.getInstance().setChannelNavigationChange(true);
                }
                PreferencesManager.getInstance().setChannelNavigation(str);
            }
            str = bottom.toString();
            if (!TextUtils.isEmpty(str)) {
                PreferencesManager.getInstance().setChannelWallMore(str.substring(0, str.length() - 1));
            }
            this.mChannelListBean.listChannel = this.mMyChannelItems;
            LetvApplication.getInstance().setChannelList(this.mChannelListBean);
        }
    }

    private void updateMap(String key, int top) {
        if (this.mChannelListBean != null && BaseTypeUtils.isMapContainsKey(this.mChannelListBean.getChannelMap(), key)) {
            ((Channel) this.mChannelListBean.getChannelMap().get(key)).top = top;
        }
    }

    private void doItemClick(boolean top, int position) {
        Channel channel;
        if (!com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            UIsUtils.showToast(2131100332);
        }
        if (BaseTypeUtils.getElementFromList(this.mMyChannelItems, position) != null) {
            channel = (Channel) this.mMyChannelItems.get(position);
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", "c11", channel.name, position, null, PageIdConstant.categoryPage, channel.id + "", null, null, null, null);
        } else {
            channel = (Channel) this.mMyChannelItems.get(position);
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", "c11", channel.name, position, null, PageIdConstant.categoryPage, channel.id + "", null, null, null, null);
        }
        if (!TextUtils.isEmpty(channel.htmlUrl)) {
            goToWebView(channel, LetvUtils.checkUrl(channel.htmlUrl));
        } else if (channel.type == 3 || channel.id <= 0) {
            ToastUtils.showToast(this.mContext, 2131099864);
        } else if (top) {
            setResult(String.valueOf(channel.id));
        } else {
            UIControllerUtils.jumpDetailChannelActivity(this.mContext, String.valueOf(channel.id), channel.pageid, null, channel.name);
        }
    }

    public void setResult(String cid) {
        if (this.mNavigationHasChange || !TextUtils.isEmpty(cid)) {
            Intent intent = new Intent();
            intent.putExtra(CHANNEL_ID, cid);
            ((ChannelWallActivity) this.mContext).setResult(1002, intent);
            this.mNavigationHasChange = false;
        }
        ((ChannelWallActivity) this.mContext).finish();
    }

    private void goToWebView(Channel channel, String webViewUrl) {
        if (PreferencesManager.getInstance().isLogin() && ((channel.id == 4 || TextUtils.equals(this.mContext.getString(2131101224), channel.name)) && !TextUtils.isEmpty(webViewUrl))) {
            String ssoTk = PreferencesManager.getInstance().getSso_tk();
            if (!TextUtils.isEmpty(ssoTk)) {
                new LetvWebViewActivityConfig(this.mContext).launch("http://sso.letv.com/user/setUserStatus?tk=" + ssoTk + "&from=mobile_tv&next_action=" + URLEncoder.encode(webViewUrl), channel.name);
                return;
            }
        }
        new LetvWebViewActivityConfig(this.mContext).launch(webViewUrl, channel.name);
    }
}
