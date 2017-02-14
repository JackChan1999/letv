package com.letv.android.client.album.half.adapter;

import android.app.Activity;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.letv.android.client.album.R;
import com.letv.android.client.album.listener.OnCommentItemClickListener;
import com.letv.android.client.album.view.BubbleLayout;
import com.letv.android.client.commonlib.adapter.LetvBaseAdapter;
import com.letv.core.bean.CommentBean.User;
import com.letv.core.bean.ReplyBean;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CommentDetailReplyAdapter extends LetvBaseAdapter {
    private LinkedList<ReplyBean> mCommentLinkedList = new LinkedList();
    private Activity mContext;
    private OnCommentItemClickListener onCommentItemClickListener;

    public CommentDetailReplyAdapter(Activity context) {
        super(context);
        this.mContext = context;
    }

    public void setVideoList(List<?> list) {
        if (list != null) {
            this.mCommentLinkedList.clear();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                this.mCommentLinkedList.addLast((ReplyBean) it.next());
            }
        }
    }

    public void clearData() {
        if (this.mCommentLinkedList != null) {
            this.mCommentLinkedList.clear();
        }
    }

    public void setOnComentItemListener(OnCommentItemClickListener onCommentItemClickListener) {
        this.onCommentItemClickListener = onCommentItemClickListener;
    }

    public synchronized void insertList(List<?> list) {
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                this.mCommentLinkedList.addLast((ReplyBean) it.next());
            }
        }
    }

    public int getCount() {
        return this.mCommentLinkedList == null ? 0 : this.mCommentLinkedList.size();
    }

    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (this.mCommentLinkedList == null) {
            return null;
        }
        ReplyBean reply = (ReplyBean) this.mCommentLinkedList.get(i);
        if (reply == null) {
            return null;
        }
        ReplyViewHoldler holder;
        if (convertView == null) {
            convertView = UIsUtils.inflate(this.mContext, R.layout.album_commen_reply_item, null, false);
            holder = new ReplyViewHoldler(this, null);
            ReplyViewHoldler.access$102(holder, (TextView) convertView.findViewById(R.id.tv_reply_content));
            ReplyViewHoldler.access$202(holder, convertView.findViewById(R.id.left_view_line));
            ReplyViewHoldler.access$302(holder, convertView.findViewById(R.id.right_view_line));
            ReplyViewHoldler.access$402(holder, convertView.findViewById(R.id.bootom_view_line));
            ReplyViewHoldler.access$502(holder, (BubbleLayout) convertView.findViewById(R.id.bubble_view));
            ReplyViewHoldler.access$500(holder).setPadding(0, ReplyViewHoldler.access$500(holder).getPaddingTop(), 0, 0);
            ReplyViewHoldler.access$200(holder).setVisibility(0);
            ReplyViewHoldler.access$300(holder).setVisibility(0);
            convertView.setTag(holder);
        } else {
            holder = (ReplyViewHoldler) convertView.getTag();
        }
        ReplyViewHoldler.access$100(holder).setBackgroundColor(this.mContext.getResources().getColor(R.color.letv_color_f9f9f9));
        if (i == 0) {
            ReplyViewHoldler.access$500(holder).setVisibility(0);
            ReplyViewHoldler.access$100(holder).setPadding(UIsUtils.dipToPx(8.0f), UIsUtils.dipToPx(8.0f), UIsUtils.dipToPx(8.0f), UIsUtils.dipToPx(4.0f));
        } else {
            ReplyViewHoldler.access$500(holder).setVisibility(8);
            ReplyViewHoldler.access$100(holder).setPadding(UIsUtils.dipToPx(8.0f), 0, UIsUtils.dipToPx(8.0f), UIsUtils.dipToPx(4.0f));
        }
        if (i == getCount() - 1) {
            ReplyViewHoldler.access$400(holder).setVisibility(0);
        } else {
            ReplyViewHoldler.access$400(holder).setVisibility(8);
        }
        if (reply.reply == null || reply.reply.user == null || reply.reply.user.uid.equalsIgnoreCase(reply.user.uid)) {
            ReplyViewHoldler.access$100(holder).setText(Html.fromHtml("<font color='#5895ed'>" + BaseTypeUtils.ensureStringValidate(reply.user.username) + ": </font>" + "<font color='#5D5D5D'>" + reply.content + "</font>"));
        } else {
            ReplyViewHoldler.access$100(holder).setText(Html.fromHtml(getMergeName(reply) + "<font color='#5D5D5D'>" + reply.content + "</font>"));
        }
        convertView.setOnLongClickListener(new 1(this, i));
        convertView.setOnClickListener(new 2(this, reply, convertView, i));
        return convertView;
    }

    public boolean checkNet() {
        if (NetworkUtils.isNetworkAvailable()) {
            return true;
        }
        ToastUtils.showToast(LetvTools.getTextFromServer("100008", this.mContext.getString(R.string.network_unavailable)));
        return false;
    }

    private SpannableStringBuilder getMergeName(ReplyBean reply) {
        String replyerName = StringUtils.clipStringWithellipsis(reply.user.username, 19);
        SpannableString rSpannableStr = new SpannableString(replyerName);
        rSpannableStr.setSpan(new ForegroundColorSpan(getUserNameColor(reply.user)), 0, replyerName.length(), 33);
        SpannableString middleSpannableStr = new SpannableString(" <font color='#5D5D5D'>" + this.mContext.getResources().getString(R.string.share_reply) + "</font>" + " ");
        String talkerName = StringUtils.clipStringWithellipsis(reply.reply.user.username, 19);
        SpannableString tSpannableStr = new SpannableString(talkerName);
        tSpannableStr.setSpan(new ForegroundColorSpan(getUserNameColor(reply.reply.user)), 0, talkerName.length(), 33);
        SpannableStringBuilder stringContentBuilder = new SpannableStringBuilder();
        stringContentBuilder.append("<font color='#5895ed'>" + rSpannableStr + "</font>");
        stringContentBuilder.append(middleSpannableStr);
        stringContentBuilder.append("<font color='#5895ed'>" + tSpannableStr + ": </font>");
        return stringContentBuilder;
    }

    private int getUserNameColor(User user) {
        return this.mContext.getResources().getColor(R.color.letv_color_5895ed);
    }
}
