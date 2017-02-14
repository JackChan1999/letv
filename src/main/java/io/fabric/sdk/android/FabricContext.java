package io.fabric.sdk.android;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import com.letv.pp.utils.NetworkUtils;
import java.io.File;

class FabricContext extends ContextWrapper {
    private final String componentName;
    private final String componentPath;

    public FabricContext(Context base, String componentName, String componentPath) {
        super(base);
        this.componentName = componentName;
        this.componentPath = componentPath;
    }

    public File getDatabasePath(String name) {
        File dir = new File(super.getDatabasePath(name).getParentFile(), this.componentPath);
        dir.mkdirs();
        return new File(dir, name);
    }

    public SQLiteDatabase openOrCreateDatabase(String name, int mode, CursorFactory factory) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
    }

    @TargetApi(11)
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name).getPath(), factory, errorHandler);
    }

    public File getFilesDir() {
        return new File(super.getFilesDir(), this.componentPath);
    }

    @TargetApi(8)
    public File getExternalFilesDir(String type) {
        return new File(super.getExternalFilesDir(type), this.componentPath);
    }

    public File getCacheDir() {
        return new File(super.getCacheDir(), this.componentPath);
    }

    @TargetApi(8)
    public File getExternalCacheDir() {
        return new File(super.getExternalCacheDir(), this.componentPath);
    }

    public SharedPreferences getSharedPreferences(String name, int mode) {
        return super.getSharedPreferences(this.componentName + NetworkUtils.DELIMITER_COLON + name, mode);
    }
}
