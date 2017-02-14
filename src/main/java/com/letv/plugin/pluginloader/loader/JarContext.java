package com.letv.plugin.pluginloader.loader;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.view.ContextThemeWrapper;
import android.view.Display;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class JarContext extends ContextThemeWrapper {
    private ClassLoader mClassLoader;
    private Context mConctext;
    private JarLayoutInflater mInflater;

    public JarContext(Context mConctext, ClassLoader mClassLoader) {
        super(mConctext, 0);
        this.mConctext = mConctext;
        this.mClassLoader = mClassLoader;
    }

    public AssetManager getAssets() {
        return this.mConctext.getAssets();
    }

    public Resources getResources() {
        return this.mConctext.getResources();
    }

    public PackageManager getPackageManager() {
        return this.mConctext.getPackageManager();
    }

    public ContentResolver getContentResolver() {
        return this.mConctext.getContentResolver();
    }

    public Looper getMainLooper() {
        return this.mConctext.getMainLooper();
    }

    public Context getApplicationContext() {
        return this.mConctext.getApplicationContext();
    }

    public void setTheme(int resid) {
        this.mConctext.setTheme(resid);
    }

    public Theme getTheme() {
        return this.mConctext.getTheme();
    }

    public ClassLoader getClassLoader() {
        return this.mClassLoader;
    }

    public String getPackageName() {
        return this.mConctext.getPackageName();
    }

    public ApplicationInfo getApplicationInfo() {
        return this.mConctext.getApplicationInfo();
    }

    public String getPackageResourcePath() {
        return this.mConctext.getPackageResourcePath();
    }

    public String getPackageCodePath() {
        return this.mConctext.getPackageCodePath();
    }

    public SharedPreferences getSharedPreferences(String name, int mode) {
        return this.mConctext.getSharedPreferences(name, mode);
    }

    public FileInputStream openFileInput(String name) throws FileNotFoundException {
        return this.mConctext.openFileInput(name);
    }

    public FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException {
        return this.mConctext.openFileOutput(name, mode);
    }

    public boolean deleteFile(String name) {
        return this.mConctext.deleteFile(name);
    }

    public File getFileStreamPath(String name) {
        return this.mConctext.getFileStreamPath(name);
    }

    public File getFilesDir() {
        return this.mConctext.getFilesDir();
    }

    public File getExternalFilesDir(String type) {
        return this.mConctext.getExternalFilesDir(type);
    }

    public File getObbDir() {
        return this.mConctext.getObbDir();
    }

    public File getCacheDir() {
        return this.mConctext.getCacheDir();
    }

    public File getExternalCacheDir() {
        return this.mConctext.getExternalCacheDir();
    }

    public String[] fileList() {
        return this.mConctext.fileList();
    }

    public File getDir(String name, int mode) {
        return this.mConctext.getDir(name, mode);
    }

    public SQLiteDatabase openOrCreateDatabase(String name, int mode, CursorFactory factory) {
        return this.mConctext.openOrCreateDatabase(name, mode, factory);
    }

    public SQLiteDatabase openOrCreateDatabase(String name, int mode, CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return this.mConctext.openOrCreateDatabase(name, mode, factory, errorHandler);
    }

    public boolean deleteDatabase(String name) {
        return this.mConctext.deleteDatabase(name);
    }

    public File getDatabasePath(String name) {
        return this.mConctext.getDatabasePath(name);
    }

    public String[] databaseList() {
        return this.mConctext.databaseList();
    }

    @Deprecated
    public Drawable getWallpaper() {
        return this.mConctext.getWallpaper();
    }

    @Deprecated
    public Drawable peekWallpaper() {
        return this.mConctext.peekWallpaper();
    }

    @Deprecated
    public int getWallpaperDesiredMinimumWidth() {
        return this.mConctext.getWallpaperDesiredMinimumWidth();
    }

    @Deprecated
    public int getWallpaperDesiredMinimumHeight() {
        return this.mConctext.getWallpaperDesiredMinimumHeight();
    }

    @Deprecated
    public void setWallpaper(Bitmap bitmap) throws IOException {
        this.mConctext.setWallpaper(bitmap);
    }

    @Deprecated
    public void setWallpaper(InputStream data) throws IOException {
        this.mConctext.setWallpaper(data);
    }

    @Deprecated
    public void clearWallpaper() throws IOException {
        this.mConctext.clearWallpaper();
    }

    public void startActivity(Intent intent) {
        this.mConctext.startActivity(intent);
    }

    public void startActivity(Intent intent, Bundle options) {
        this.mConctext.startActivity(intent, options);
    }

    public void startActivities(Intent[] intents) {
        this.mConctext.startActivities(intents);
    }

    public void startActivities(Intent[] intents, Bundle options) {
        this.mConctext.startActivities(intents, options);
    }

    public void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws SendIntentException {
        this.mConctext.startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags);
    }

    public void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) throws SendIntentException {
        this.mConctext.startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }

    public void sendBroadcast(Intent intent) {
        this.mConctext.sendBroadcast(intent);
    }

    public void sendBroadcast(Intent intent, String receiverPermission) {
        this.mConctext.sendBroadcast(intent, receiverPermission);
    }

    public void sendOrderedBroadcast(Intent intent, String receiverPermission) {
        this.mConctext.sendOrderedBroadcast(intent, receiverPermission);
    }

    public void sendOrderedBroadcast(Intent intent, String receiverPermission, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
    }

    public void sendBroadcastAsUser(Intent intent, UserHandle user) {
        this.mConctext.sendBroadcastAsUser(intent, user);
    }

    public void sendBroadcastAsUser(Intent intent, UserHandle user, String receiverPermission) {
        this.mConctext.sendBroadcastAsUser(intent, user, receiverPermission);
    }

    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle user, String receiverPermission, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
        this.mConctext.sendOrderedBroadcastAsUser(intent, user, receiverPermission, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    public void sendStickyBroadcast(Intent intent) {
        this.mConctext.sendStickyBroadcast(intent);
    }

    public void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
        this.mConctext.sendStickyOrderedBroadcast(intent, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    public void removeStickyBroadcast(Intent intent) {
        this.mConctext.removeStickyBroadcast(intent);
    }

    public void sendStickyBroadcastAsUser(Intent intent, UserHandle user) {
        this.mConctext.sendStickyBroadcastAsUser(intent, user);
    }

    public void sendStickyOrderedBroadcastAsUser(Intent intent, UserHandle user, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
        this.mConctext.sendStickyOrderedBroadcastAsUser(intent, user, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    public void removeStickyBroadcastAsUser(Intent intent, UserHandle user) {
        this.mConctext.removeStickyBroadcastAsUser(intent, user);
    }

    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return this.mConctext.registerReceiver(receiver, filter);
    }

    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler) {
        return this.mConctext.registerReceiver(receiver, filter, broadcastPermission, scheduler);
    }

    public void unregisterReceiver(BroadcastReceiver receiver) {
        this.mConctext.unregisterReceiver(receiver);
    }

    public ComponentName startService(Intent service) {
        return this.mConctext.startService(service);
    }

    public boolean stopService(Intent service) {
        return this.mConctext.stopService(service);
    }

    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        return this.mConctext.bindService(service, conn, flags);
    }

    public void unbindService(ServiceConnection conn) {
        this.mConctext.unbindService(conn);
    }

    public boolean startInstrumentation(ComponentName className, String profileFile, Bundle arguments) {
        return this.mConctext.startInstrumentation(className, profileFile, arguments);
    }

    public Object getSystemService(String name) {
        if (!"layout_inflater".equals(name)) {
            return this.mConctext.getSystemService(name);
        }
        if (this.mInflater == null) {
            this.mInflater = new JarLayoutInflater(this);
        }
        return this.mInflater;
    }

    public int checkPermission(String permission, int pid, int uid) {
        return this.mConctext.checkPermission(permission, pid, uid);
    }

    public int checkCallingPermission(String permission) {
        return this.mConctext.checkCallingPermission(permission);
    }

    public int checkCallingOrSelfPermission(String permission) {
        return this.mConctext.checkCallingOrSelfPermission(permission);
    }

    public void enforcePermission(String permission, int pid, int uid, String message) {
        this.mConctext.enforcePermission(permission, pid, uid, message);
    }

    public void enforceCallingPermission(String permission, String message) {
        this.mConctext.enforceCallingPermission(permission, message);
    }

    public void enforceCallingOrSelfPermission(String permission, String message) {
        this.mConctext.enforceCallingOrSelfPermission(permission, message);
    }

    public void grantUriPermission(String toPackage, Uri uri, int modeFlags) {
        this.mConctext.grantUriPermission(toPackage, uri, modeFlags);
    }

    public void revokeUriPermission(Uri uri, int modeFlags) {
        this.mConctext.revokeUriPermission(uri, modeFlags);
    }

    public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags) {
        return this.mConctext.checkUriPermission(uri, pid, uid, modeFlags);
    }

    public int checkCallingUriPermission(Uri uri, int modeFlags) {
        return this.mConctext.checkCallingUriPermission(uri, modeFlags);
    }

    public int checkCallingOrSelfUriPermission(Uri uri, int modeFlags) {
        return this.mConctext.checkCallingOrSelfUriPermission(uri, modeFlags);
    }

    public int checkUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags) {
        return this.mConctext.checkUriPermission(uri, pid, uid, modeFlags);
    }

    public void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags, String message) {
        this.mConctext.enforceUriPermission(uri, pid, uid, modeFlags, message);
    }

    public void enforceCallingUriPermission(Uri uri, int modeFlags, String message) {
        this.mConctext.enforceCallingUriPermission(uri, modeFlags, message);
    }

    public void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags, String message) {
        this.mConctext.enforceCallingOrSelfUriPermission(uri, modeFlags, message);
    }

    public void enforceUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags, String message) {
        this.mConctext.enforceUriPermission(uri, readPermission, writePermission, pid, uid, modeFlags, message);
    }

    public Context createPackageContext(String packageName, int flags) throws NameNotFoundException {
        return this.mConctext.createPackageContext(packageName, flags);
    }

    public Context createConfigurationContext(Configuration overrideConfiguration) {
        return this.mConctext.createConfigurationContext(overrideConfiguration);
    }

    public Context createDisplayContext(Display display) {
        return this.mConctext.createDisplayContext(display);
    }

    public String getBasePackageName() {
        return this.mConctext.getPackageName();
    }

    public File[] getExternalFilesDirs(String type) {
        return this.mConctext.getExternalFilesDirs(type);
    }

    public File[] getObbDirs() {
        return this.mConctext.getObbDirs();
    }

    public File[] getExternalCacheDirs() {
        return this.mConctext.getExternalCacheDirs();
    }

    public String getOpPackageName() {
        return getBasePackageName();
    }
}
