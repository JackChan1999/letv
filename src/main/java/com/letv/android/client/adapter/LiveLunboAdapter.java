package com.letv.android.client.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.adapter.LetvBaseAdapter;
import com.letv.core.bean.LiveBeanLeChannel;
import com.letv.core.bean.ProgramEntity;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.utils.LiveLunboUtils;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.pp.utils.NetworkUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LiveLunboAdapter extends LetvBaseAdapter<LiveBeanLeChannel> {
    private static final String TAG = "LiveLunboAdapter";
    private Set<String> mChannelSet;
    private Context mContext;
    private LayoutInflater mInflater;
    private Map<String, LiveProgram> mPrograms;
    private int mScrollState;

    public static class LiveProgram {
        public String mIconUrl;
        public String mName;
        public String mNextName;
        public String mNextTime;

        public LiveProgram() {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
        }
    }

    public LiveLunboAdapter(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.mPrograms = new HashMap();
        this.mChannelSet = new HashSet();
        this.mContext = context;
        this.mInflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
    }

    public void setScrollState(int state) {
        this.mScrollState = state;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LunboViewHolder lunboViewHolder;
        String nextPlayName;
        int start;
        int end;
        long now = System.currentTimeMillis();
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.half_lunbo_adapter, null);
            lunboViewHolder = new LunboViewHolder();
            lunboViewHolder.halfAdapterItem = (RelativeLayout) convertView.findViewById(R.id.halfAdapterItem);
            lunboViewHolder.title = (TextView) convertView.findViewById(R.id.itemTitle);
            lunboViewHolder.playProName = (TextView) convertView.findViewById(R.id.itemDescPlayNameTitle);
            lunboViewHolder.playNextProName = (TextView) convertView.findViewById(R.id.itemDescNextPlayTitle);
            lunboViewHolder.img = (ImageView) convertView.findViewById(R.id.programImg);
            convertView.setTag(lunboViewHolder);
        } else {
            lunboViewHolder = (LunboViewHolder) convertView.getTag();
        }
        LiveBeanLeChannel channelBean = (LiveBeanLeChannel) getItem(position);
        if (!this.mChannelSet.contains(channelBean.channelId)) {
            if (LiveLunboUtils.mChannelQueue.size() == 10) {
                this.mChannelSet.remove(((LiveBeanLeChannel) LiveLunboUtils.mChannelQueue.poll()).channelId);
            }
            LiveLunboUtils.mChannelQueue.offer(channelBean);
            this.mChannelSet.add(channelBean.channelId);
        }
        String seqNo = channelBean.numericKeys;
        String title = channelBean.channelName;
        if (!TextUtils.isEmpty(seqNo)) {
            String channelName = channelBean.channelName;
            if (Integer.valueOf(seqNo).intValue() < 10) {
                title = "0" + seqNo + "\t" + channelName;
            } else {
                title = seqNo + "\t" + channelName;
            }
        }
        lunboViewHolder.title.setText(title);
        if (channelBean.cur != null) {
            ProgramEntity cur = channelBean.cur;
            if (TextUtils.isEmpty(cur.title)) {
                lunboViewHolder.playProName.setText(this.mContext.getResources().getString(2131100334));
                lunboViewHolder.playProName.setCompoundDrawables(null, null, null, null);
            } else {
                lunboViewHolder.playProName.setText(cur.title);
                lunboViewHolder.playProName.setCompoundDrawablesWithIntrinsicBounds(2130839257, 0, 0, 0);
            }
            if (!TextUtils.isEmpty(channelBean.channelIcon)) {
                boolean z;
                ImageDownloader instance = ImageDownloader.getInstance();
                View view = lunboViewHolder.img;
                String str = channelBean.channelIcon;
                if (this.mScrollState == 0) {
                    z = true;
                } else {
                    z = false;
                }
                instance.download(view, str, 2130838795, z);
            } else if (TextUtils.isEmpty(cur.viewPic)) {
                lunboViewHolder.img.setScaleType(ScaleType.CENTER);
                lunboViewHolder.img.setImageResource(2130838795);
            } else {
                ImageDownloader.getInstance().download(lunboViewHolder.img, cur.viewPic, 2130838795, this.mScrollState == 0);
            }
        }
        if (channelBean.next != null) {
            ProgramEntity next = channelBean.next;
            lunboViewHolder.playNextProName.setText(next.title);
            nextPlayName = next.title;
            if (!TextUtils.isEmpty(next.playTime)) {
                start = next.playTime.indexOf(" ");
                end = next.playTime.lastIndexOf(NetworkUtils.DELIMITER_COLON);
                if (!(start == -1 || end == -1 || start >= end)) {
                    nextPlayName = next.playTime.substring(start + 1, end) + "  " + next.title;
                }
            }
            lunboViewHolder.playNextProName.setText(nextPlayName);
        }
        if (this.mPrograms != null) {
            LiveProgram program = (LiveProgram) this.mPrograms.get(channelBean.channelId);
            if (program == null) {
                program = new LiveProgram();
            }
            if (TextUtils.isEmpty(program.mName)) {
                lunboViewHolder.playProName.setText(this.mContext.getResources().getString(2131100334));
                lunboViewHolder.playProName.setCompoundDrawables(null, null, null, null);
            } else {
                lunboViewHolder.playProName.setText(program.mName);
                lunboViewHolder.playProName.setCompoundDrawablesWithIntrinsicBounds(2130839257, 0, 0, 0);
            }
            lunboViewHolder.playNextProName.setText(program.mNextName);
            if (!TextUtils.isEmpty(channelBean.channelIcon)) {
                ImageDownloader.getInstance().download(lunboViewHolder.img, channelBean.channelIcon, 2130838795, this.mScrollState == 0);
            } else if (TextUtils.isEmpty(program.mIconUrl)) {
                lunboViewHolder.img.setScaleType(ScaleType.CENTER);
                lunboViewHolder.img.setImageResource(2130838795);
            } else {
                ImageDownloader.getInstance().download(lunboViewHolder.img, program.mIconUrl, 2130838795, this.mScrollState == 0);
            }
            nextPlayName = program.mNextName;
            if (!TextUtils.isEmpty(program.mNextTime)) {
                start = program.mNextTime.indexOf(" ");
                end = program.mNextTime.lastIndexOf(NetworkUtils.DELIMITER_COLON);
                if (!(start == -1 || end == -1 || start >= end)) {
                    nextPlayName = program.mNextTime.substring(start + 1, end) + "  " + program.mNextName;
                }
            }
            lunboViewHolder.playNextProName.setText(nextPlayName);
        }
        LogInfo.log(TAG, "LiveLunboAdapter: " + (System.currentTimeMillis() - now) + NetworkUtils.DELIMITER_COLON + position);
        return convertView;
    }

    public void setLivePrograms(Map<String, LiveProgram> programs) {
        this.mPrograms = programs;
    }

    public long getItemId(int position) {
        return (long) position;
    }
}
