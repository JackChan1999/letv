package com.letv.android.client.album;

import android.app.Activity;
import android.os.Bundle;

public class AlbumPlayVRActivity extends AlbumPlayActivity {
    protected void onCreate(Bundle savedInstanceState) {
        this.mIsVR = true;
        super.onCreate(savedInstanceState);
    }

    public String getActivityName() {
        return AlbumPlayVRActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
