package com.letv.android.client.album.half.controller;

import android.content.Context;
import android.os.Bundle;
import java.util.HashSet;
import java.util.Set;

public abstract class AlbumCompositeInterface<T extends AlbumCompositeInterface> extends AlbumHalfPositionInterface {
    private Set<AlbumCompositeInterface> subList = new HashSet();

    public void addSubLifeCycleController(AlbumCompositeInterface albumCompositeInterface) {
        this.subList.add(albumCompositeInterface);
    }

    public AlbumCompositeInterface(Context context, T parent) {
        if (parent != null) {
            parent.addSubLifeCycleController(this);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        for (AlbumCompositeInterface albumCompositeInterface : this.subList) {
            albumCompositeInterface.onCreate(savedInstanceState);
        }
    }

    public void onStart() {
        for (AlbumCompositeInterface albumCompositeInterface : this.subList) {
            albumCompositeInterface.onStart();
        }
    }

    public void onResume() {
        for (AlbumCompositeInterface albumCompositeInterface : this.subList) {
            albumCompositeInterface.onResume();
        }
    }

    public void onPause() {
        for (AlbumCompositeInterface albumCompositeInterface : this.subList) {
            albumCompositeInterface.onPause();
        }
    }

    public void onStop() {
        for (AlbumCompositeInterface albumCompositeInterface : this.subList) {
            albumCompositeInterface.onStop();
        }
    }

    public void onDestroy() {
        for (AlbumCompositeInterface albumCompositeInterface : this.subList) {
            albumCompositeInterface.onDestroy();
        }
    }

    public void onPlayVideo() {
        for (AlbumCompositeInterface albumCompositeInterface : this.subList) {
            albumCompositeInterface.onPlayVideo();
        }
    }

    public void onNetChange() {
        for (AlbumCompositeInterface albumCompositeInterface : this.subList) {
            albumCompositeInterface.onNetChange();
        }
    }

    public void onSuccessRequestPlayCard(boolean isNetwork) {
        for (AlbumCompositeInterface albumCompositeInterface : this.subList) {
            albumCompositeInterface.onSuccessRequestPlayCard(isNetwork);
        }
    }
}
