package com.letv.android.client.hot;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.adapter.LetvBaseAdapter;
import com.letv.android.client.episode.widget.CommentHeadImageViewPlayerLibs;
import com.letv.android.client.hot.HotTopControl.OnAddHotTopListener;
import com.letv.android.client.view.LetvImageView;
import com.letv.core.bean.HotVideoBean;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.List;

public class HotVideoAdapter extends LetvBaseAdapter {
    private HotVideoClickListener hotVideoClickListener;
    public LetvHotActivity mActivity;
    private int mDefaultHeight;
    private int mDefaultWidth;
    private OnAddHotTopListener mOnAddHotTopListener;
    public List<HotVideoBean> videoList;

    public interface HotVideoClickListener {
        void clickPlayOrPause(HotVideoBean hotVideoBean, View view);

        void gotoComment(String str, String str2);

        void shareClick(HotVideoBean hotVideoBean, View view, int i);
    }

    public class ViewHolder {
        public LinearLayout commentLayout;
        public TextView comment_count;
        public TextView comment_user_name;
        public RelativeLayout contentLayout;
        public TextView description;
        public TextView duration;
        public RelativeLayout errorLayout;
        public TextView errorRetry;
        public TextView errorText;
        public LetvImageView image;
        public ProgressBar loading;
        public ImageView play;
        public RelativeLayout rootView;
        public LinearLayout shareLayout;
        final /* synthetic */ HotVideoAdapter this$0;
        public TextView title;
        public TextView top;
        public int topCount;
        public LinearLayout topLayout;
        public TextView tread;
        public LinearLayout treadLayout;
        public CommentHeadImageViewPlayerLibs userHead;
        public int vid;

        public ViewHolder(HotVideoAdapter this$0) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
        }
    }

    public void setOnAddHotTopListener(OnAddHotTopListener onAddHotTopListener) {
        this.mOnAddHotTopListener = onAddHotTopListener;
    }

    public HotVideoAdapter(LetvHotActivity activity, List<HotVideoBean> dataList, HotVideoClickListener hotVideoClickListener) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(activity);
        this.mActivity = activity;
        this.videoList = dataList;
        this.hotVideoClickListener = hotVideoClickListener;
        if (this.mDefaultWidth == 0) {
            this.mDefaultWidth = this.mActivity.getWindowManager().getDefaultDisplay().getWidth();
            this.mDefaultHeight = (this.mDefaultWidth * 3) / 4;
        }
    }

    public void setDataSource(List<HotVideoBean> dataList) {
        if (dataList != null && dataList.size() > 0) {
            this.videoList.clear();
            this.videoList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void addDataSource(List<HotVideoBean> dataList) {
        if (dataList != null && dataList.size() > 0) {
            this.videoList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public int getCount() {
        return this.videoList == null ? 0 : this.videoList.size();
    }

    public Object getItem(int position) {
        return this.videoList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (this.videoList == null || position >= getCount()) {
            return null;
        }
        ViewHolder holder;
        final HotVideoBean video = (HotVideoBean) getItem(position);
        if (convertView == null) {
            holder = new ViewHolder(this);
            convertView = LayoutInflater.from(this.mActivity).inflate(R.layout.hot_play_view, null);
            holder.loading = (ProgressBar) convertView.findViewById(R.id.hot_play_loading);
            holder.title = (TextView) convertView.findViewById(R.id.hotPlay_title);
            holder.description = (TextView) convertView.findViewById(R.id.hotPlay_description);
            holder.comment_user_name = (TextView) convertView.findViewById(2131362315);
            holder.userHead = (CommentHeadImageViewPlayerLibs) convertView.findViewById(R.id.image_user_head);
            holder.duration = (TextView) convertView.findViewById(R.id.hot_play_duration);
            holder.image = (LetvImageView) convertView.findViewById(R.id.hot_play_image);
            holder.top = (TextView) convertView.findViewById(R.id.hotPlay_up);
            holder.tread = (TextView) convertView.findViewById(R.id.hotPlay_tread);
            holder.comment_count = (TextView) convertView.findViewById(R.id.hotPlay_comment_count);
            holder.play = (ImageView) convertView.findViewById(R.id.hot_play_playButton);
            holder.topLayout = (LinearLayout) convertView.findViewById(R.id.hotPlay_up_layout);
            holder.shareLayout = (LinearLayout) convertView.findViewById(R.id.hotPlay_share_layout);
            holder.contentLayout = (RelativeLayout) convertView.findViewById(R.id.hot_play_contentLayout);
            holder.commentLayout = (LinearLayout) convertView.findViewById(R.id.hotPlay_comment_layout);
            holder.treadLayout = (LinearLayout) convertView.findViewById(R.id.hotPlay_tread_layout);
            holder.errorLayout = (RelativeLayout) convertView.findViewById(R.id.hot_play_errer_layout);
            holder.errorText = (TextView) convertView.findViewById(R.id.hot_play_errer_tip);
            holder.errorRetry = (TextView) convertView.findViewById(R.id.hot_play_errer_retry);
            holder.rootView = (RelativeLayout) convertView.findViewById(R.id.hot_play_root_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            clearViewData(holder.image);
        }
        holder.image.setVisibility(0);
        holder.vid = Integer.parseInt(video.id);
        holder.title.setText(video.nameCn);
        if (TextUtils.isEmpty(video.content) || "null".equals(video.content)) {
            holder.userHead.setVisibility(8);
            holder.description.setVisibility(8);
            holder.comment_user_name.setVisibility(8);
        } else {
            if (TextUtils.isEmpty(video.user_photo)) {
                holder.userHead.setImageResource(2130837633);
            } else {
                ImageDownloader.getInstance().download(holder.userHead, video.user_photo);
            }
            holder.userHead.setVisibility(0);
            holder.description.setVisibility(0);
            holder.comment_user_name.setVisibility(0);
            holder.description.setText(video.content);
            holder.comment_user_name.setText(video.username);
        }
        if (TextUtils.isEmpty(video.duration) || "0".equals(video.duration)) {
            holder.duration.setText("");
            holder.duration.setTag("0");
            holder.duration.setVisibility(8);
        } else {
            holder.duration.setText(LetvUtils.getNumberTime2(Long.parseLong(video.duration)));
            holder.duration.setTag(video.duration);
            holder.duration.setVisibility(0);
        }
        LayoutParams lp = (LayoutParams) holder.contentLayout.getLayoutParams();
        lp.width = this.mDefaultWidth;
        lp.height = this.mDefaultHeight;
        holder.contentLayout.setLayoutParams(new LayoutParams(this.mDefaultWidth, this.mDefaultHeight));
        ImageDownloader.getInstance().download(holder.image, video.pic_400_300, 2130838798, ScaleType.FIT_XY);
        holder.contentLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (HotVideoAdapter.this.hotVideoClickListener != null) {
                    HotVideoAdapter.this.hotVideoClickListener.clickPlayOrPause(video, (View) v.getParent());
                }
            }
        });
        holder.contentLayout.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ HotVideoAdapter this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        holder.play.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (HotVideoAdapter.this.hotVideoClickListener != null) {
                    HotVideoAdapter.this.hotVideoClickListener.clickPlayOrPause(video, (View) v.getParent().getParent());
                }
            }
        });
        holder.topCount = video.topCount;
        holder.top.setText(video.topCount + "");
        holder.tread.setText(video.treadCount + "");
        holder.comment_count.setText(video.commentCount + "");
        if (video.isTop) {
            holder.topLayout.findViewById(R.id.hotPlay_up_image).setBackgroundResource(2130838204);
            holder.top.setTextColor(this.mActivity.getResources().getColor(2131493280));
        } else {
            holder.top.setTextColor(this.mActivity.getResources().getColor(2131493390));
            holder.topLayout.findViewById(R.id.hotPlay_up_image).setBackgroundResource(2130838203);
        }
        if (video.isTread) {
            holder.treadLayout.findViewById(R.id.hotPlay_tread_image).setBackgroundResource(2130838202);
            holder.tread.setTextColor(this.mActivity.getResources().getColor(2131493280));
        } else {
            holder.tread.setTextColor(this.mActivity.getResources().getColor(2131493390));
            holder.treadLayout.findViewById(R.id.hotPlay_tread_image).setBackgroundResource(2130838201);
        }
        holder.topLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (video.isTop) {
                    LogInfo.log("+-->", "--->>>holder.topLayout.setOnClickListene");
                    ToastUtils.showToast(HotVideoAdapter.this.mActivity, 2131100180);
                } else if (video.isTread) {
                    LogInfo.log("+-->", "--->>>holder.topLayout.setOnClickListene");
                    ToastUtils.showToast(HotVideoAdapter.this.mActivity, 2131100181);
                } else if (NetworkUtils.isNetworkAvailable()) {
                    ImageView imageBg = (ImageView) v.findViewById(R.id.hotPlay_up_image);
                    TextView topView = (TextView) v.findViewById(R.id.hotPlay_up);
                    if (HotVideoAdapter.this.mOnAddHotTopListener != null) {
                        HotVideoAdapter.this.mOnAddHotTopListener.OnAddHotTop(video, topView, imageBg, position);
                    }
                } else {
                    ToastUtils.showToast(HotVideoAdapter.this.mActivity, 2131100332);
                }
            }
        });
        holder.treadLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (video.isTop) {
                    LogInfo.log("+-->", "--->>>holder.topLayout.setOnClickListene");
                    ToastUtils.showToast(HotVideoAdapter.this.mActivity, 2131100180);
                } else if (video.isTread) {
                    LogInfo.log("+-->", "--->>>holder.topLayout.setOnClickListene");
                    ToastUtils.showToast(HotVideoAdapter.this.mActivity, 2131100181);
                } else if (NetworkUtils.isNetworkAvailable()) {
                    ImageView imageBg = (ImageView) v.findViewById(R.id.hotPlay_tread_image);
                    TextView topView = (TextView) v.findViewById(R.id.hotPlay_tread);
                    if (HotVideoAdapter.this.mOnAddHotTopListener != null) {
                        HotVideoAdapter.this.mOnAddHotTopListener.OnAddHotTread(video, topView, imageBg, position);
                    }
                } else {
                    ToastUtils.showToast(HotVideoAdapter.this.mActivity, 2131100332);
                }
            }
        });
        holder.commentLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (HotVideoAdapter.this.hotVideoClickListener != null) {
                    HotVideoAdapter.this.hotVideoClickListener.gotoComment("0", video.id);
                }
            }
        });
        holder.shareLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (HotVideoAdapter.this.hotVideoClickListener == null) {
                    return;
                }
                if (LetvUtils.checkClickEvent(1000)) {
                    HotVideoAdapter.this.hotVideoClickListener.shareClick(video, v, position);
                } else {
                    ToastUtils.showToast(HotVideoAdapter.this.mActivity, 2131099876);
                }
            }
        });
        return convertView;
    }

    public void setVideoTopCount(String vid, String count, boolean isTopOrTread, String act) {
        for (HotVideoBean bean : this.videoList) {
            if (bean.id.equals(vid)) {
                if ("1".equals(act)) {
                    bean.topCount = Integer.parseInt(count);
                    if (isTopOrTread) {
                        bean.isTop = true;
                        return;
                    }
                    return;
                } else if ("2".equals(act)) {
                    bean.treadCount = Integer.parseInt(count);
                    if (isTopOrTread) {
                        bean.isTread = true;
                        return;
                    }
                    return;
                } else {
                    return;
                }
            }
        }
    }

    private void clearViewData(ImageView iv) {
        if (iv != null) {
            iv.setImageDrawable(null);
        }
    }

    public int getItemViewType(int position) {
        if (this.mActivity.getHotPlayVideoView() == null) {
            return 0;
        }
        if (!((HotVideoBean) this.videoList.get(position)).id.equals(this.mActivity.getHotPlayVideoView().vid + "")) {
            return 0;
        }
        LogInfo.log("HotVideoAdapter||wlx", "播放项 不复用");
        return 1;
    }

    public int getViewTypeCount() {
        return 2;
    }
}
