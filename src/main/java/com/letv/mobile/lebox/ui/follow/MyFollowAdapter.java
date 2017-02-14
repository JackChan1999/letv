package com.letv.mobile.lebox.ui.follow;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.http.lebox.bean.FollowAlbumBean;
import com.letv.mobile.lebox.http.lebox.bean.Info;
import com.letv.mobile.lebox.httpmanager.HttpCacheAssistant;
import com.letv.mobile.lebox.httpmanager.HttpRequesetManager;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.utils.imagecache.LetvCacheMannager;
import java.util.ArrayList;
import java.util.List;

public class MyFollowAdapter extends BaseAdapter {
    private static final String TAG = MyFollowAdapter.class.getSimpleName();
    private final Context mContext;
    private final MyFollowActivity mFollowActivity;
    private final LayoutInflater mInflater;
    private final List<FollowAlbumBean> mList = new ArrayList();
    private final UpdateUi mUpdateUi;

    interface UpdateUi {
        void callBack();
    }

    class ViewHold {
        ImageView albumImage;
        TextView albumTitle;
        Button cancelFollow;
        TextView followStatus;

        ViewHold() {
        }
    }

    public MyFollowAdapter(Context context, UpdateUi updateUi) {
        this.mContext = context;
        this.mFollowActivity = (MyFollowActivity) context;
        this.mInflater = LayoutInflater.from(this.mContext);
        this.mUpdateUi = updateUi;
    }

    public int getCount() {
        return this.mList.size();
    }

    public Object getItem(int position) {
        return this.mList.get(position);
    }

    public long getItemId(int position) {
        return (long) ((FollowAlbumBean) this.mList.get(position)).hashCode();
    }

    public void updateList(List<FollowAlbumBean> list) {
        this.mList.clear();
        if (!(list == null || list.size() == 0)) {
            this.mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold hold;
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.lebox_my_follow_item, null);
        }
        if (convertView.getTag() == null) {
            hold = new ViewHold();
            hold.albumImage = (ImageView) convertView.findViewById(R.id.my_follow_item_image);
            hold.albumTitle = (TextView) convertView.findViewById(R.id.my_follow_item_name);
            hold.followStatus = (TextView) convertView.findViewById(R.id.my_follow_item_description);
            hold.cancelFollow = (Button) convertView.findViewById(R.id.btn_cancel_follow);
        } else {
            hold = (ViewHold) convertView.getTag();
        }
        final FollowAlbumBean albumBean = (FollowAlbumBean) this.mList.get(position);
        String imageUrl = "";
        String title = "";
        String followDetails = "";
        if (TextUtils.isEmpty(title) && !HttpCacheAssistant.getInstanced().isCompleteTaskEmpty()) {
            Info info = HttpCacheAssistant.getInstanced().getInfo(albumBean.getPid(), HttpCacheAssistant.getInstanced().getCompleteList());
            Logger.d(TAG, "----from cache----- info=" + info);
            if (info != null) {
                if (!TextUtils.isEmpty(info.getVideoInfo().getId())) {
                    imageUrl = HttpRequesetManager.getLeboxVideoFilePath("2", info.getVideoInfo().getId());
                }
                if (!TextUtils.isEmpty(info.getAlbumInfo().getNameCn())) {
                    title = info.getAlbumInfo().getNameCn();
                }
                followDetails = getDetailsStr(HttpCacheAssistant.getInstanced().getFollowAlbumPro(albumBean.getPid(), 1));
            }
            Logger.d(TAG, "----from cache----- title=" + title + "--followDetails=" + followDetails);
        }
        if (!(!TextUtils.isEmpty(title) || albumBean == null || albumBean.getTag() == null || TextUtils.isEmpty(albumBean.getTag().getAlbumCover()))) {
            imageUrl = albumBean.getTag().getAlbumCover();
            title = albumBean.getTag().getAlbumTitle();
            Logger.d(TAG, "----from tag----- title=" + title + "--imageUrl=" + imageUrl);
        }
        if (!TextUtils.isEmpty(imageUrl)) {
            LetvCacheMannager.getInstance().loadImage(imageUrl, hold.albumImage);
        }
        hold.albumTitle.setText(title);
        hold.followStatus.setText(followDetails);
        hold.cancelFollow.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyFollowAdapter.this.mFollowActivity.showLoadingDialog();
                HttpRequesetManager.getInstance().deleteFollowAlbum(new 1(this), albumBean.getPid());
            }
        });
        return convertView;
    }

    private String getDetailsStr(String episode) {
        if (TextUtils.isEmpty(episode)) {
            return "";
        }
        return String.format(this.mContext.getResources().getString(R.string.follow_details), new Object[]{episode});
    }
}
