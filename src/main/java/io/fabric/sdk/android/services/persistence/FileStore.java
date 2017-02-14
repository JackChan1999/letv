package io.fabric.sdk.android.services.persistence;

import java.io.File;

public interface FileStore {
    File getCacheDir();

    File getExternalCacheDir();

    File getExternalFilesDir();

    File getFilesDir();
}
