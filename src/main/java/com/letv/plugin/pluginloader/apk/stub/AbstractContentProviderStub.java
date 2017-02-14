package com.letv.plugin.pluginloader.apk.stub;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.letv.plugin.pluginloader.apk.common.ApkConstant;
import com.letv.plugin.pluginloader.apk.pm.ApkManager;
import com.letv.plugin.pluginloader.apk.pm.PluginProcessManager;
import com.letv.plugin.pluginloader.apk.utils.FieldUtils;
import com.letv.plugin.pluginloader.apk.utils.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractContentProviderStub extends ContentProvider {
    private static final String TAG = AbstractContentProviderStub.class.getSimpleName();
    private ContentResolver mContentResolver;
    private Map<String, ContentProviderClient> sContentProviderClients = new HashMap();

    public boolean onCreate() {
        this.mContentResolver = getContext().getContentResolver();
        return true;
    }

    private Uri buildNewUri(Uri uri, String targetAuthority) {
        Builder b = new Builder();
        b.scheme(uri.getScheme());
        b.authority(targetAuthority);
        b.path(uri.getPath());
        if (VERSION.SDK_INT >= 11) {
            Set<String> names = uri.getQueryParameterNames();
            if (names != null && names.size() > 0) {
                for (String name : names) {
                    if (!TextUtils.equals(name, ApkConstant.EXTRA_TARGET_AUTHORITY)) {
                        b.appendQueryParameter(name, uri.getQueryParameter(name));
                    }
                }
            }
        } else {
            b.query(uri.getQuery());
        }
        b.fragment(uri.getFragment());
        return b.build();
    }

    private synchronized ContentProviderClient getContentProviderClient(String targetAuthority) {
        ContentProviderClient client;
        client = (ContentProviderClient) this.sContentProviderClients.get(targetAuthority);
        if (client == null) {
            if (Looper.getMainLooper() != Looper.myLooper()) {
                ApkManager.getInstance().waitForConnected();
            }
            ProviderInfo stubInfo = null;
            ProviderInfo targetInfo = null;
            try {
                stubInfo = getContext().getPackageManager().resolveContentProvider(getMyAuthority(), 0);
                targetInfo = ApkManager.getInstance().resolveContentProvider(targetAuthority, Integer.valueOf(0));
            } catch (Exception e) {
                Log.e(TAG, "Can not reportMyProcessName on ContentProvider", new Object[0]);
            }
            if (!(stubInfo == null || targetInfo == null)) {
                try {
                    ApkManager.getInstance().reportMyProcessName(stubInfo.processName, targetInfo.processName, targetInfo.packageName);
                } catch (RemoteException e2) {
                    Log.e(TAG, "RemoteException on reportMyProcessName", e2, new Object[0]);
                }
            }
            if (targetInfo != null) {
                try {
                    PluginProcessManager.preLoadApk(getContext(), targetInfo);
                } catch (Exception e3) {
                    handleExpcetion(e3);
                }
            }
            this.sContentProviderClients.put(targetAuthority, this.mContentResolver.acquireContentProviderClient(targetAuthority));
            if (!(stubInfo == null || targetInfo == null)) {
                try {
                    ApkManager.getInstance().onProviderCreated(stubInfo, targetInfo);
                } catch (Exception e32) {
                    Log.e(TAG, "Exception on report onProviderCreated", e32, new Object[0]);
                }
            }
            client = (ContentProviderClient) this.sContentProviderClients.get(targetAuthority);
        }
        return client;
    }

    private String getMyAuthority() throws NameNotFoundException, IllegalAccessException {
        if (VERSION.SDK_INT >= 21) {
            return (String) FieldUtils.readField((Object) this, "mAuthority");
        }
        Context context = getContext();
        PackageInfo pkgInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 8);
        if (!(pkgInfo == null || pkgInfo.providers == null || pkgInfo.providers.length <= 0)) {
            for (ProviderInfo info : pkgInfo.providers) {
                if (TextUtils.equals(info.name, getClass().getName())) {
                    return info.authority;
                }
            }
        }
        return null;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String targetAuthority = uri.getQueryParameter(ApkConstant.EXTRA_TARGET_AUTHORITY);
        if (!(TextUtils.isEmpty(targetAuthority) || TextUtils.equals(targetAuthority, uri.getAuthority()))) {
            try {
                return getContentProviderClient(targetAuthority).query(buildNewUri(uri, targetAuthority), projection, selection, selectionArgs, sortOrder);
            } catch (RemoteException e) {
                handleExpcetion(e);
            }
        }
        return null;
    }

    protected void handleExpcetion(Exception e) {
        Log.e(TAG, "handleExpcetion", e, new Object[0]);
    }

    public String getType(Uri uri) {
        String targetAuthority = uri.getQueryParameter(ApkConstant.EXTRA_TARGET_AUTHORITY);
        if (!(TextUtils.isEmpty(targetAuthority) || TextUtils.equals(targetAuthority, uri.getAuthority()))) {
            try {
                return getContentProviderClient(targetAuthority).getType(buildNewUri(uri, targetAuthority));
            } catch (RemoteException e) {
                handleExpcetion(e);
            }
        }
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        String targetAuthority = uri.getQueryParameter(ApkConstant.EXTRA_TARGET_AUTHORITY);
        if (!(TextUtils.isEmpty(targetAuthority) || TextUtils.equals(targetAuthority, uri.getAuthority()))) {
            try {
                return getContentProviderClient(targetAuthority).insert(buildNewUri(uri, targetAuthority), contentValues);
            } catch (RemoteException e) {
                handleExpcetion(e);
            }
        }
        return null;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String targetAuthority = uri.getQueryParameter(ApkConstant.EXTRA_TARGET_AUTHORITY);
        if (!(TextUtils.isEmpty(targetAuthority) || TextUtils.equals(targetAuthority, uri.getAuthority()))) {
            try {
                return getContentProviderClient(targetAuthority).delete(buildNewUri(uri, targetAuthority), selection, selectionArgs);
            } catch (RemoteException e) {
                handleExpcetion(e);
            }
        }
        return 0;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String targetAuthority = uri.getQueryParameter(ApkConstant.EXTRA_TARGET_AUTHORITY);
        if (!(TextUtils.isEmpty(targetAuthority) || TextUtils.equals(targetAuthority, uri.getAuthority()))) {
            try {
                return getContentProviderClient(targetAuthority).update(buildNewUri(uri, targetAuthority), values, selection, selectionArgs);
            } catch (RemoteException e) {
                handleExpcetion(e);
            }
        }
        return 0;
    }

    @TargetApi(17)
    public Bundle call(String method, String arg, Bundle extras) {
        String targetAuthority;
        String targetMethod = null;
        if (extras != null) {
            targetAuthority = extras.getString(ApkConstant.EXTRA_TARGET_AUTHORITY);
        } else {
            targetAuthority = null;
        }
        if (extras != null) {
            targetMethod = extras.getString(ApkConstant.EXTRA_TARGET_AUTHORITY);
        }
        if (!(TextUtils.isEmpty(targetMethod) || TextUtils.equals(targetMethod, method))) {
            try {
                return getContentProviderClient(targetAuthority).call(targetMethod, arg, extras);
            } catch (RemoteException e) {
                handleExpcetion(e);
            }
        }
        return super.call(method, arg, extras);
    }

    public int bulkInsert(Uri uri, ContentValues[] values) {
        String targetAuthority = uri.getQueryParameter(ApkConstant.EXTRA_TARGET_AUTHORITY);
        if (!(TextUtils.isEmpty(targetAuthority) || TextUtils.equals(targetAuthority, uri.getAuthority()))) {
            try {
                return getContentProviderClient(targetAuthority).bulkInsert(buildNewUri(uri, targetAuthority), values);
            } catch (RemoteException e) {
                handleExpcetion(e);
            }
        }
        return super.bulkInsert(uri, values);
    }

    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        return super.applyBatch(operations);
    }
}
