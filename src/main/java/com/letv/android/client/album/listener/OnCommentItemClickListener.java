package com.letv.android.client.album.listener;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public interface OnCommentItemClickListener {
    void onCommentEditClickEvent(int i, int i2, boolean z);

    void onCommentItemClickEvent(View view, TextView textView, int i, ImageView imageView, boolean z);

    void onExpendMoreReplyClickEvent(int i, String str);

    void onItemLongClickEvent(View view, int i, int i2, boolean z);

    void onLogoutLikeClickEvent();

    void onReplyItemClickEvent(View view, TextView textView, int i, int i2, ImageView imageView, boolean z);
}
