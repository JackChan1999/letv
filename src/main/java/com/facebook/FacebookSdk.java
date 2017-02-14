package com.facebook;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import com.facebook.internal.BoltsMeasurementEventListener;
import com.facebook.internal.LockOnGetVariable;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public final class FacebookSdk {
    public static final String APPLICATION_ID_PROPERTY = "com.facebook.sdk.ApplicationId";
    public static final String APPLICATION_NAME_PROPERTY = "com.facebook.sdk.ApplicationName";
    private static final String ATTRIBUTION_PREFERENCES = "com.facebook.sdk.attributionTracking";
    static final String CALLBACK_OFFSET_CHANGED_AFTER_INIT = "The callback request code offset can't be updated once the SDK is initialized.";
    static final String CALLBACK_OFFSET_NEGATIVE = "The callback request code offset can't be negative.";
    public static final String CLIENT_TOKEN_PROPERTY = "com.facebook.sdk.ClientToken";
    private static final int DEFAULT_CORE_POOL_SIZE = 5;
    private static final int DEFAULT_KEEP_ALIVE = 1;
    private static final int DEFAULT_MAXIMUM_POOL_SIZE = 128;
    private static final ThreadFactory DEFAULT_THREAD_FACTORY = new 1();
    private static final BlockingQueue<Runnable> DEFAULT_WORK_QUEUE = new LinkedBlockingQueue(10);
    private static final String FACEBOOK_COM = "facebook.com";
    private static final Object LOCK = new Object();
    private static final int MAX_REQUEST_CODE_RANGE = 100;
    private static final String PUBLISH_ACTIVITY_PATH = "%s/activities";
    private static final String TAG = FacebookSdk.class.getCanonicalName();
    public static final String WEB_DIALOG_THEME = "com.facebook.sdk.WebDialogTheme";
    private static volatile String appClientToken;
    private static Context applicationContext;
    private static volatile String applicationId;
    private static volatile String applicationName;
    private static LockOnGetVariable<File> cacheDir;
    private static int callbackRequestCodeOffset = 64206;
    private static volatile Executor executor;
    private static volatile String facebookDomain = FACEBOOK_COM;
    private static volatile boolean isDebugEnabled = false;
    private static boolean isLegacyTokenUpgradeSupported = false;
    private static final HashSet<LoggingBehavior> loggingBehaviors = new HashSet(Arrays.asList(new LoggingBehavior[]{LoggingBehavior.DEVELOPER_ERRORS}));
    private static AtomicLong onProgressThreshold = new AtomicLong(65536);
    private static Boolean sdkInitialized = Boolean.valueOf(false);
    private static volatile int webDialogTheme;

    public static synchronized void sdkInitialize(Context applicationContext, int callbackRequestCodeOffset) {
        synchronized (FacebookSdk.class) {
            sdkInitialize(applicationContext, callbackRequestCodeOffset, null);
        }
    }

    public static synchronized void sdkInitialize(Context applicationContext, int callbackRequestCodeOffset, InitializeCallback callback) {
        synchronized (FacebookSdk.class) {
            if (sdkInitialized.booleanValue() && callbackRequestCodeOffset != callbackRequestCodeOffset) {
                throw new FacebookException(CALLBACK_OFFSET_CHANGED_AFTER_INIT);
            } else if (callbackRequestCodeOffset < 0) {
                throw new FacebookException(CALLBACK_OFFSET_NEGATIVE);
            } else {
                callbackRequestCodeOffset = callbackRequestCodeOffset;
                sdkInitialize(applicationContext);
            }
        }
    }

    public static synchronized void sdkInitialize(Context applicationContext) {
        synchronized (FacebookSdk.class) {
            sdkInitialize(applicationContext, null);
        }
    }

    public static synchronized void sdkInitialize(Context applicationContext, InitializeCallback callback) {
        synchronized (FacebookSdk.class) {
            if (!sdkInitialized.booleanValue()) {
                Validate.notNull(applicationContext, "applicationContext");
                Validate.hasFacebookActivity(applicationContext, false);
                Validate.hasInternetPermissions(applicationContext, false);
                applicationContext = applicationContext.getApplicationContext();
                loadDefaultsFromMetadata(applicationContext);
                Utility.loadAppSettingsAsync(applicationContext, applicationId);
                NativeProtocol.updateAllAvailableProtocolVersionsAsync();
                BoltsMeasurementEventListener.getInstance(applicationContext);
                cacheDir = new LockOnGetVariable(new 2());
                getExecutor().execute(new FutureTask(new 3(callback)));
                sdkInitialized = Boolean.valueOf(true);
            } else if (callback != null) {
                callback.onInitialized();
            }
        }
    }

    public static synchronized boolean isInitialized() {
        boolean booleanValue;
        synchronized (FacebookSdk.class) {
            booleanValue = sdkInitialized.booleanValue();
        }
        return booleanValue;
    }

    public static Set<LoggingBehavior> getLoggingBehaviors() {
        Set<LoggingBehavior> unmodifiableSet;
        synchronized (loggingBehaviors) {
            unmodifiableSet = Collections.unmodifiableSet(new HashSet(loggingBehaviors));
        }
        return unmodifiableSet;
    }

    public static void addLoggingBehavior(LoggingBehavior behavior) {
        synchronized (loggingBehaviors) {
            loggingBehaviors.add(behavior);
            updateGraphDebugBehavior();
        }
    }

    public static void removeLoggingBehavior(LoggingBehavior behavior) {
        synchronized (loggingBehaviors) {
            loggingBehaviors.remove(behavior);
        }
    }

    public static void clearLoggingBehaviors() {
        synchronized (loggingBehaviors) {
            loggingBehaviors.clear();
        }
    }

    public static boolean isLoggingBehaviorEnabled(LoggingBehavior behavior) {
        boolean z;
        synchronized (loggingBehaviors) {
            z = isDebugEnabled() && loggingBehaviors.contains(behavior);
        }
        return z;
    }

    public static boolean isDebugEnabled() {
        return isDebugEnabled;
    }

    public static void setIsDebugEnabled(boolean enabled) {
        isDebugEnabled = enabled;
    }

    public static boolean isLegacyTokenUpgradeSupported() {
        return isLegacyTokenUpgradeSupported;
    }

    private static void updateGraphDebugBehavior() {
        if (loggingBehaviors.contains(LoggingBehavior.GRAPH_API_DEBUG_INFO) && !loggingBehaviors.contains(LoggingBehavior.GRAPH_API_DEBUG_WARNING)) {
            loggingBehaviors.add(LoggingBehavior.GRAPH_API_DEBUG_WARNING);
        }
    }

    public static void setLegacyTokenUpgradeSupported(boolean supported) {
        isLegacyTokenUpgradeSupported = supported;
    }

    public static Executor getExecutor() {
        synchronized (LOCK) {
            if (executor == null) {
                executor = AsyncTask.THREAD_POOL_EXECUTOR;
            }
        }
        return executor;
    }

    public static void setExecutor(Executor executor) {
        Validate.notNull(executor, "executor");
        synchronized (LOCK) {
            executor = executor;
        }
    }

    public static String getFacebookDomain() {
        return facebookDomain;
    }

    public static void setFacebookDomain(String facebookDomain) {
        Log.w(TAG, "WARNING: Calling setFacebookDomain from non-DEBUG code.");
        facebookDomain = facebookDomain;
    }

    public static Context getApplicationContext() {
        Validate.sdkInitialized();
        return applicationContext;
    }

    public static void publishInstallAsync(Context context, String applicationId) {
        getExecutor().execute(new 4(context.getApplicationContext(), applicationId));
    }

    static com.facebook.GraphResponse publishInstallAndWaitForResponse(android.content.Context r24, java.lang.String r25) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.facebook.FacebookSdk.publishInstallAndWaitForResponse(android.content.Context, java.lang.String):com.facebook.GraphResponse. bs: [B:2:0x0004, B:10:0x007a]
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:86)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        if (r24 == 0) goto L_0x0004;
    L_0x0002:
        if (r25 != 0) goto L_0x0029;
    L_0x0004:
        r19 = new java.lang.IllegalArgumentException;	 Catch:{ Exception -> 0x000c }
        r20 = "Both context and applicationId must be non-null";	 Catch:{ Exception -> 0x000c }
        r19.<init>(r20);	 Catch:{ Exception -> 0x000c }
        throw r19;	 Catch:{ Exception -> 0x000c }
    L_0x000c:
        r4 = move-exception;
        r19 = "Facebook-publish";
        r0 = r19;
        com.facebook.internal.Utility.logd(r0, r4);
        r19 = new com.facebook.GraphResponse;
        r20 = 0;
        r21 = 0;
        r22 = new com.facebook.FacebookRequestError;
        r23 = 0;
        r0 = r22;
        r1 = r23;
        r0.<init>(r1, r4);
        r19.<init>(r20, r21, r22);
    L_0x0028:
        return r19;
    L_0x0029:
        r8 = com.facebook.internal.AttributionIdentifiers.getAttributionIdentifiers(r24);	 Catch:{ Exception -> 0x000c }
        r19 = "com.facebook.sdk.attributionTracking";	 Catch:{ Exception -> 0x000c }
        r20 = 0;	 Catch:{ Exception -> 0x000c }
        r0 = r24;	 Catch:{ Exception -> 0x000c }
        r1 = r19;	 Catch:{ Exception -> 0x000c }
        r2 = r20;	 Catch:{ Exception -> 0x000c }
        r14 = r0.getSharedPreferences(r1, r2);	 Catch:{ Exception -> 0x000c }
        r19 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x000c }
        r19.<init>();	 Catch:{ Exception -> 0x000c }
        r0 = r19;	 Catch:{ Exception -> 0x000c }
        r1 = r25;	 Catch:{ Exception -> 0x000c }
        r19 = r0.append(r1);	 Catch:{ Exception -> 0x000c }
        r20 = "ping";	 Catch:{ Exception -> 0x000c }
        r19 = r19.append(r20);	 Catch:{ Exception -> 0x000c }
        r13 = r19.toString();	 Catch:{ Exception -> 0x000c }
        r19 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x000c }
        r19.<init>();	 Catch:{ Exception -> 0x000c }
        r0 = r19;	 Catch:{ Exception -> 0x000c }
        r1 = r25;	 Catch:{ Exception -> 0x000c }
        r19 = r0.append(r1);	 Catch:{ Exception -> 0x000c }
        r20 = "json";	 Catch:{ Exception -> 0x000c }
        r19 = r19.append(r20);	 Catch:{ Exception -> 0x000c }
        r9 = r19.toString();	 Catch:{ Exception -> 0x000c }
        r20 = 0;	 Catch:{ Exception -> 0x000c }
        r0 = r20;	 Catch:{ Exception -> 0x000c }
        r10 = r14.getLong(r13, r0);	 Catch:{ Exception -> 0x000c }
        r19 = 0;	 Catch:{ Exception -> 0x000c }
        r0 = r19;	 Catch:{ Exception -> 0x000c }
        r12 = r14.getString(r9, r0);	 Catch:{ Exception -> 0x000c }
        r19 = com.facebook.internal.AppEventsLoggerUtility.GraphAPIActivityType.MOBILE_INSTALL_EVENT;	 Catch:{ JSONException -> 0x00e5 }
        r20 = com.facebook.appevents.AppEventsLogger.getAnonymousAppDeviceGUID(r24);	 Catch:{ JSONException -> 0x00e5 }
        r21 = getLimitEventAndDataUsage(r24);	 Catch:{ JSONException -> 0x00e5 }
        r0 = r19;	 Catch:{ JSONException -> 0x00e5 }
        r1 = r20;	 Catch:{ JSONException -> 0x00e5 }
        r2 = r21;	 Catch:{ JSONException -> 0x00e5 }
        r3 = r24;	 Catch:{ JSONException -> 0x00e5 }
        r15 = com.facebook.internal.AppEventsLoggerUtility.getJSONObjectForGraphAPICall(r0, r8, r1, r2, r3);	 Catch:{ JSONException -> 0x00e5 }
        r19 = "%s/activities";	 Catch:{ Exception -> 0x000c }
        r20 = 1;	 Catch:{ Exception -> 0x000c }
        r0 = r20;	 Catch:{ Exception -> 0x000c }
        r0 = new java.lang.Object[r0];	 Catch:{ Exception -> 0x000c }
        r20 = r0;	 Catch:{ Exception -> 0x000c }
        r21 = 0;	 Catch:{ Exception -> 0x000c }
        r20[r21] = r25;	 Catch:{ Exception -> 0x000c }
        r18 = java.lang.String.format(r19, r20);	 Catch:{ Exception -> 0x000c }
        r19 = 0;	 Catch:{ Exception -> 0x000c }
        r20 = 0;	 Catch:{ Exception -> 0x000c }
        r0 = r19;	 Catch:{ Exception -> 0x000c }
        r1 = r18;	 Catch:{ Exception -> 0x000c }
        r2 = r20;	 Catch:{ Exception -> 0x000c }
        r16 = com.facebook.GraphRequest.newPostRequest(r0, r1, r15, r2);	 Catch:{ Exception -> 0x000c }
        r20 = 0;
        r19 = (r10 > r20 ? 1 : (r10 == r20 ? 0 : -1));
        if (r19 == 0) goto L_0x0107;
    L_0x00b6:
        r6 = 0;
        if (r12 == 0) goto L_0x00bf;
    L_0x00b9:
        r7 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0130 }
        r7.<init>(r12);	 Catch:{ JSONException -> 0x0130 }
        r6 = r7;
    L_0x00bf:
        if (r6 != 0) goto L_0x00f2;
    L_0x00c1:
        r19 = "true";	 Catch:{ Exception -> 0x000c }
        r20 = 0;	 Catch:{ Exception -> 0x000c }
        r21 = new com.facebook.GraphRequestBatch;	 Catch:{ Exception -> 0x000c }
        r22 = 1;	 Catch:{ Exception -> 0x000c }
        r0 = r22;	 Catch:{ Exception -> 0x000c }
        r0 = new com.facebook.GraphRequest[r0];	 Catch:{ Exception -> 0x000c }
        r22 = r0;	 Catch:{ Exception -> 0x000c }
        r23 = 0;	 Catch:{ Exception -> 0x000c }
        r22[r23] = r16;	 Catch:{ Exception -> 0x000c }
        r21.<init>(r22);	 Catch:{ Exception -> 0x000c }
        r19 = com.facebook.GraphResponse.createResponsesFromString(r19, r20, r21);	 Catch:{ Exception -> 0x000c }
        r20 = 0;	 Catch:{ Exception -> 0x000c }
        r19 = r19.get(r20);	 Catch:{ Exception -> 0x000c }
        r19 = (com.facebook.GraphResponse) r19;	 Catch:{ Exception -> 0x000c }
        goto L_0x0028;	 Catch:{ Exception -> 0x000c }
    L_0x00e5:
        r4 = move-exception;	 Catch:{ Exception -> 0x000c }
        r19 = new com.facebook.FacebookException;	 Catch:{ Exception -> 0x000c }
        r20 = "An error occurred while publishing install.";	 Catch:{ Exception -> 0x000c }
        r0 = r19;	 Catch:{ Exception -> 0x000c }
        r1 = r20;	 Catch:{ Exception -> 0x000c }
        r0.<init>(r1, r4);	 Catch:{ Exception -> 0x000c }
        throw r19;	 Catch:{ Exception -> 0x000c }
    L_0x00f2:
        r19 = new com.facebook.GraphResponse;	 Catch:{ Exception -> 0x000c }
        r20 = 0;	 Catch:{ Exception -> 0x000c }
        r21 = 0;	 Catch:{ Exception -> 0x000c }
        r22 = 0;	 Catch:{ Exception -> 0x000c }
        r0 = r19;	 Catch:{ Exception -> 0x000c }
        r1 = r20;	 Catch:{ Exception -> 0x000c }
        r2 = r21;	 Catch:{ Exception -> 0x000c }
        r3 = r22;	 Catch:{ Exception -> 0x000c }
        r0.<init>(r1, r2, r3, r6);	 Catch:{ Exception -> 0x000c }
        goto L_0x0028;	 Catch:{ Exception -> 0x000c }
    L_0x0107:
        r17 = r16.executeAndWait();	 Catch:{ Exception -> 0x000c }
        r5 = r14.edit();	 Catch:{ Exception -> 0x000c }
        r10 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x000c }
        r5.putLong(r13, r10);	 Catch:{ Exception -> 0x000c }
        r19 = r17.getJSONObject();	 Catch:{ Exception -> 0x000c }
        if (r19 == 0) goto L_0x0129;	 Catch:{ Exception -> 0x000c }
    L_0x011c:
        r19 = r17.getJSONObject();	 Catch:{ Exception -> 0x000c }
        r19 = r19.toString();	 Catch:{ Exception -> 0x000c }
        r0 = r19;	 Catch:{ Exception -> 0x000c }
        r5.putString(r9, r0);	 Catch:{ Exception -> 0x000c }
    L_0x0129:
        r5.apply();	 Catch:{ Exception -> 0x000c }
        r19 = r17;
        goto L_0x0028;
    L_0x0130:
        r19 = move-exception;
        goto L_0x00bf;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.FacebookSdk.publishInstallAndWaitForResponse(android.content.Context, java.lang.String):com.facebook.GraphResponse");
    }

    public static String getSdkVersion() {
        return "4.8.0";
    }

    public static boolean getLimitEventAndDataUsage(Context context) {
        Validate.sdkInitialized();
        return context.getSharedPreferences("com.facebook.sdk.appEventPreferences", 0).getBoolean("limitEventUsage", false);
    }

    public static void setLimitEventAndDataUsage(Context context, boolean limitEventUsage) {
        context.getSharedPreferences("com.facebook.sdk.appEventPreferences", 0).edit().putBoolean("limitEventUsage", limitEventUsage).apply();
    }

    public static long getOnProgressThreshold() {
        Validate.sdkInitialized();
        return onProgressThreshold.get();
    }

    public static void setOnProgressThreshold(long threshold) {
        onProgressThreshold.set(threshold);
    }

    static void loadDefaultsFromMetadata(Context context) {
        if (context != null) {
            try {
                ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                if (ai != null && ai.metaData != null) {
                    if (applicationId == null) {
                        String appId = ai.metaData.get(APPLICATION_ID_PROPERTY);
                        if (appId instanceof String) {
                            String appIdString = appId;
                            if (appIdString.toLowerCase(Locale.ROOT).startsWith("fb")) {
                                applicationId = appIdString.substring(2);
                            } else {
                                applicationId = appIdString;
                            }
                        } else if (appId instanceof Integer) {
                            throw new FacebookException("App Ids cannot be directly placed in the manifest.They must be prefixed by 'fb' or be placed in the string resource file.");
                        }
                    }
                    if (applicationName == null) {
                        applicationName = ai.metaData.getString(APPLICATION_NAME_PROPERTY);
                    }
                    if (appClientToken == null) {
                        appClientToken = ai.metaData.getString(CLIENT_TOKEN_PROPERTY);
                    }
                    if (webDialogTheme == 0) {
                        setWebDialogTheme(ai.metaData.getInt(WEB_DIALOG_THEME));
                    }
                }
            } catch (NameNotFoundException e) {
            }
        }
    }

    public static String getApplicationSignature(Context context) {
        Validate.sdkInitialized();
        if (context == null) {
            return null;
        }
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return null;
        }
        try {
            PackageInfo pInfo = packageManager.getPackageInfo(context.getPackageName(), 64);
            Signature[] signatures = pInfo.signatures;
            if (signatures == null || signatures.length == 0) {
                return null;
            }
            try {
                MessageDigest md = MessageDigest.getInstance(CommonUtils.SHA1_INSTANCE);
                md.update(pInfo.signatures[0].toByteArray());
                return Base64.encodeToString(md.digest(), 9);
            } catch (NoSuchAlgorithmException e) {
                return null;
            }
        } catch (NameNotFoundException e2) {
            return null;
        }
    }

    public static String getApplicationId() {
        Validate.sdkInitialized();
        return applicationId;
    }

    public static void setApplicationId(String applicationId) {
        applicationId = applicationId;
    }

    public static String getApplicationName() {
        Validate.sdkInitialized();
        return applicationName;
    }

    public static void setApplicationName(String applicationName) {
        applicationName = applicationName;
    }

    public static String getClientToken() {
        Validate.sdkInitialized();
        return appClientToken;
    }

    public static void setClientToken(String clientToken) {
        appClientToken = clientToken;
    }

    public static int getWebDialogTheme() {
        Validate.sdkInitialized();
        return webDialogTheme;
    }

    public static void setWebDialogTheme(int theme) {
        if (theme == 0) {
            theme = 16973840;
        }
        webDialogTheme = theme;
    }

    public static File getCacheDir() {
        Validate.sdkInitialized();
        return (File) cacheDir.getValue();
    }

    public static void setCacheDir(File cacheDir) {
        cacheDir = new LockOnGetVariable(cacheDir);
    }

    public static int getCallbackRequestCodeOffset() {
        Validate.sdkInitialized();
        return callbackRequestCodeOffset;
    }

    public static boolean isFacebookRequestCode(int requestCode) {
        return requestCode >= callbackRequestCodeOffset && requestCode < callbackRequestCodeOffset + 100;
    }
}
