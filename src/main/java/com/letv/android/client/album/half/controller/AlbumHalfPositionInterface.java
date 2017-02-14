package com.letv.android.client.album.half.controller;

import android.view.View;
import android.view.ViewGroup;
import com.letv.core.BaseApplication;

public abstract class AlbumHalfPositionInterface {
    public int controllerPosition = -1;
    public boolean needRefreshCard;

    public View getParentItemView(int position, View convertView, ViewGroup parent) {
        return new View(BaseApplication.getInstance());
    }

    public void onBindParentItemView() {
    }
}
