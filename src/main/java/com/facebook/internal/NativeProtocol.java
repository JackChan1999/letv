package com.facebook.internal;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookSdk;
import com.facebook.login.DefaultAudience;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public final class NativeProtocol {
    public static final String ACTION_APPINVITE_DIALOG = "com.facebook.platform.action.request.APPINVITES_DIALOG";
    public static final String ACTION_FEED_DIALOG = "com.facebook.platform.action.request.FEED_DIALOG";
    public static final String ACTION_LIKE_DIALOG = "com.facebook.platform.action.request.LIKE_DIALOG";
    public static final String ACTION_MESSAGE_DIALOG = "com.facebook.platform.action.request.MESSAGE_DIALOG";
    public static final String ACTION_OGACTIONPUBLISH_DIALOG = "com.facebook.platform.action.request.OGACTIONPUBLISH_DIALOG";
    public static final String ACTION_OGMESSAGEPUBLISH_DIALOG = "com.facebook.platform.action.request.OGMESSAGEPUBLISH_DIALOG";
    public static final String AUDIENCE_EVERYONE = "everyone";
    public static final String AUDIENCE_FRIENDS = "friends";
    public static final String AUDIENCE_ME = "only_me";
    public static final String BRIDGE_ARG_ACTION_ID_STRING = "action_id";
    public static final String BRIDGE_ARG_APP_NAME_STRING = "app_name";
    public static final String BRIDGE_ARG_ERROR_BUNDLE = "error";
    public static final String BRIDGE_ARG_ERROR_CODE = "error_code";
    public static final String BRIDGE_ARG_ERROR_DESCRIPTION = "error_description";
    public static final String BRIDGE_ARG_ERROR_JSON = "error_json";
    public static final String BRIDGE_ARG_ERROR_SUBCODE = "error_subcode";
    public static final String BRIDGE_ARG_ERROR_TYPE = "error_type";
    private static final String CONTENT_SCHEME = "content://";
    public static final String ERROR_APPLICATION_ERROR = "ApplicationError";
    public static final String ERROR_NETWORK_ERROR = "NetworkError";
    public static final String ERROR_PERMISSION_DENIED = "PermissionDenied";
    public static final String ERROR_PROTOCOL_ERROR = "ProtocolError";
    public static final String ERROR_SERVICE_DISABLED = "ServiceDisabled";
    public static final String ERROR_UNKNOWN_ERROR = "UnknownError";
    public static final String ERROR_USER_CANCELED = "UserCanceled";
    public static final String EXTRA_ACCESS_TOKEN = "com.facebook.platform.extra.ACCESS_TOKEN";
    public static final String EXTRA_APPLICATION_ID = "com.facebook.platform.extra.APPLICATION_ID";
    public static final String EXTRA_APPLICATION_NAME = "com.facebook.platform.extra.APPLICATION_NAME";
    public static final String EXTRA_DIALOG_COMPLETE_KEY = "com.facebook.platform.extra.DID_COMPLETE";
    public static final String EXTRA_DIALOG_COMPLETION_GESTURE_KEY = "com.facebook.platform.extra.COMPLETION_GESTURE";
    public static final String EXTRA_EXPIRES_SECONDS_SINCE_EPOCH = "com.facebook.platform.extra.EXPIRES_SECONDS_SINCE_EPOCH";
    public static final String EXTRA_GET_INSTALL_DATA_PACKAGE = "com.facebook.platform.extra.INSTALLDATA_PACKAGE";
    public static final String EXTRA_PERMISSIONS = "com.facebook.platform.extra.PERMISSIONS";
    public static final String EXTRA_PROTOCOL_ACTION = "com.facebook.platform.protocol.PROTOCOL_ACTION";
    public static final String EXTRA_PROTOCOL_BRIDGE_ARGS = "com.facebook.platform.protocol.BRIDGE_ARGS";
    public static final String EXTRA_PROTOCOL_CALL_ID = "com.facebook.platform.protocol.CALL_ID";
    public static final String EXTRA_PROTOCOL_METHOD_ARGS = "com.facebook.platform.protocol.METHOD_ARGS";
    public static final String EXTRA_PROTOCOL_METHOD_RESULTS = "com.facebook.platform.protocol.RESULT_ARGS";
    public static final String EXTRA_PROTOCOL_VERSION = "com.facebook.platform.protocol.PROTOCOL_VERSION";
    static final String EXTRA_PROTOCOL_VERSIONS = "com.facebook.platform.extra.PROTOCOL_VERSIONS";
    public static final String EXTRA_USER_ID = "com.facebook.platform.extra.USER_ID";
    private static final NativeAppInfo FACEBOOK_APP_INFO = new KatanaAppInfo(null);
    private static final String FACEBOOK_PROXY_AUTH_ACTIVITY = "com.facebook.katana.ProxyAuth";
    public static final String FACEBOOK_PROXY_AUTH_APP_ID_KEY = "client_id";
    public static final String FACEBOOK_PROXY_AUTH_E2E_KEY = "e2e";
    public static final String FACEBOOK_PROXY_AUTH_PERMISSIONS_KEY = "scope";
    private static final String FACEBOOK_TOKEN_REFRESH_ACTIVITY = "com.facebook.katana.platform.TokenRefreshService";
    public static final String IMAGE_URL_KEY = "url";
    public static final String IMAGE_USER_GENERATED_KEY = "user_generated";
    static final String INTENT_ACTION_PLATFORM_ACTIVITY = "com.facebook.platform.PLATFORM_ACTIVITY";
    static final String INTENT_ACTION_PLATFORM_SERVICE = "com.facebook.platform.PLATFORM_SERVICE";
    private static final List<Integer> KNOWN_PROTOCOL_VERSIONS = Arrays.asList(new Integer[]{Integer.valueOf(PROTOCOL_VERSION_20141218), Integer.valueOf(PROTOCOL_VERSION_20141107), Integer.valueOf(PROTOCOL_VERSION_20141028), Integer.valueOf(PROTOCOL_VERSION_20141001), Integer.valueOf(PROTOCOL_VERSION_20140701), Integer.valueOf(PROTOCOL_VERSION_20140324), Integer.valueOf(PROTOCOL_VERSION_20140204), Integer.valueOf(PROTOCOL_VERSION_20131107), Integer.valueOf(PROTOCOL_VERSION_20130618), Integer.valueOf(PROTOCOL_VERSION_20130502), Integer.valueOf(PROTOCOL_VERSION_20121101)});
    public static final int MESSAGE_GET_ACCESS_TOKEN_REPLY = 65537;
    public static final int MESSAGE_GET_ACCESS_TOKEN_REQUEST = 65536;
    public static final int MESSAGE_GET_INSTALL_DATA_REPLY = 65541;
    public static final int MESSAGE_GET_INSTALL_DATA_REQUEST = 65540;
    public static final int MESSAGE_GET_LIKE_STATUS_REPLY = 65543;
    public static final int MESSAGE_GET_LIKE_STATUS_REQUEST = 65542;
    static final int MESSAGE_GET_PROTOCOL_VERSIONS_REPLY = 65539;
    static final int MESSAGE_GET_PROTOCOL_VERSIONS_REQUEST = 65538;
    public static final int NO_PROTOCOL_AVAILABLE = -1;
    public static final String OPEN_GRAPH_CREATE_OBJECT_KEY = "fbsdk:create_object";
    private static final String PLATFORM_PROVIDER = ".provider.PlatformProvider";
    private static final String PLATFORM_PROVIDER_VERSIONS = ".provider.PlatformProvider/versions";
    private static final String PLATFORM_PROVIDER_VERSION_COLUMN = "version";
    public static final int PROTOCOL_VERSION_20121101 = 20121101;
    public static final int PROTOCOL_VERSION_20130502 = 20130502;
    public static final int PROTOCOL_VERSION_20130618 = 20130618;
    public static final int PROTOCOL_VERSION_20131107 = 20131107;
    public static final int PROTOCOL_VERSION_20140204 = 20140204;
    public static final int PROTOCOL_VERSION_20140324 = 20140324;
    public static final int PROTOCOL_VERSION_20140701 = 20140701;
    public static final int PROTOCOL_VERSION_20141001 = 20141001;
    public static final int PROTOCOL_VERSION_20141028 = 20141028;
    public static final int PROTOCOL_VERSION_20141107 = 20141107;
    public static final int PROTOCOL_VERSION_20141218 = 20141218;
    public static final String RESULT_ARGS_ACCESS_TOKEN = "access_token";
    public static final String RESULT_ARGS_DIALOG_COMPLETE_KEY = "didComplete";
    public static final String RESULT_ARGS_DIALOG_COMPLETION_GESTURE_KEY = "completionGesture";
    public static final String RESULT_ARGS_EXPIRES_SECONDS_SINCE_EPOCH = "expires_seconds_since_epoch";
    public static final String RESULT_ARGS_PERMISSIONS = "permissions";
    public static final String STATUS_ERROR_CODE = "com.facebook.platform.status.ERROR_CODE";
    public static final String STATUS_ERROR_DESCRIPTION = "com.facebook.platform.status.ERROR_DESCRIPTION";
    public static final String STATUS_ERROR_JSON = "com.facebook.platform.status.ERROR_JSON";
    public static final String STATUS_ERROR_SUBCODE = "com.facebook.platform.status.ERROR_SUBCODE";
    public static final String STATUS_ERROR_TYPE = "com.facebook.platform.status.ERROR_TYPE";
    public static final String WEB_DIALOG_ACTION = "action";
    public static final String WEB_DIALOG_IS_FALLBACK = "is_fallback";
    public static final String WEB_DIALOG_PARAMS = "params";
    public static final String WEB_DIALOG_URL = "url";
    private static Map<String, List<NativeAppInfo>> actionToAppInfoMap = buildActionToAppInfoMap();
    private static List<NativeAppInfo> facebookAppInfoList = buildFacebookAppList();
    private static AtomicBoolean protocolVersionsAsyncUpdating = new AtomicBoolean(false);

    private static List<NativeAppInfo> buildFacebookAppList() {
        List<NativeAppInfo> list = new ArrayList();
        list.add(FACEBOOK_APP_INFO);
        list.add(new WakizashiAppInfo(null));
        return list;
    }

    private static Map<String, List<NativeAppInfo>> buildActionToAppInfoMap() {
        Map<String, List<NativeAppInfo>> map = new HashMap();
        ArrayList<NativeAppInfo> messengerAppInfoList = new ArrayList();
        messengerAppInfoList.add(new MessengerAppInfo(null));
        map.put(ACTION_OGACTIONPUBLISH_DIALOG, facebookAppInfoList);
        map.put(ACTION_FEED_DIALOG, facebookAppInfoList);
        map.put(ACTION_LIKE_DIALOG, facebookAppInfoList);
        map.put(ACTION_APPINVITE_DIALOG, facebookAppInfoList);
        map.put(ACTION_MESSAGE_DIALOG, messengerAppInfoList);
        map.put(ACTION_OGMESSAGEPUBLISH_DIALOG, messengerAppInfoList);
        return map;
    }

    static Intent validateActivityIntent(Context context, Intent intent, NativeAppInfo appInfo) {
        if (intent == null) {
            return null;
        }
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, 0);
        if (resolveInfo == null) {
            return null;
        }
        if (appInfo.validateSignature(context, resolveInfo.activityInfo.packageName)) {
            return intent;
        }
        return null;
    }

    static Intent validateServiceIntent(Context context, Intent intent, NativeAppInfo appInfo) {
        if (intent == null) {
            return null;
        }
        ResolveInfo resolveInfo = context.getPackageManager().resolveService(intent, 0);
        if (resolveInfo == null) {
            return null;
        }
        if (appInfo.validateSignature(context, resolveInfo.serviceInfo.packageName)) {
            return intent;
        }
        return null;
    }

    public static Intent createProxyAuthIntent(Context context, String applicationId, Collection<String> permissions, String e2e, boolean isRerequest, boolean isForPublish, DefaultAudience defaultAudience) {
        for (NativeAppInfo appInfo : facebookAppInfoList) {
            Intent intent = new Intent().setClassName(appInfo.getPackage(), FACEBOOK_PROXY_AUTH_ACTIVITY).putExtra("client_id", applicationId);
            if (!Utility.isNullOrEmpty(permissions)) {
                intent.putExtra("scope", TextUtils.join(",", permissions));
            }
            if (!Utility.isNullOrEmpty(e2e)) {
                intent.putExtra(FACEBOOK_PROXY_AUTH_E2E_KEY, e2e);
            }
            intent.putExtra("response_type", "token,signed_request");
            intent.putExtra("return_scopes", "true");
            if (isForPublish) {
                intent.putExtra("default_audience", defaultAudience.getNativeProtocolAudience());
            }
            intent.putExtra("legacy_override", "v2.5");
            if (isRerequest) {
                intent.putExtra("auth_type", "rerequest");
            }
            intent = validateActivityIntent(context, intent, appInfo);
            if (intent != null) {
                return intent;
            }
        }
        return null;
    }

    public static Intent createTokenRefreshIntent(Context context) {
        for (NativeAppInfo appInfo : facebookAppInfoList) {
            Intent intent = validateServiceIntent(context, new Intent().setClassName(appInfo.getPackage(), FACEBOOK_TOKEN_REFRESH_ACTIVITY), appInfo);
            if (intent != null) {
                return intent;
            }
        }
        return null;
    }

    public static final int getLatestKnownVersion() {
        return ((Integer) KNOWN_PROTOCOL_VERSIONS.get(0)).intValue();
    }

    private static Intent findActivityIntent(Context context, String activityAction, String internalAction) {
        List<NativeAppInfo> list = (List) actionToAppInfoMap.get(internalAction);
        if (list == null) {
            return null;
        }
        Intent intent = null;
        for (NativeAppInfo appInfo : list) {
            intent = validateActivityIntent(context, new Intent().setAction(activityAction).setPackage(appInfo.getPackage()).addCategory("android.intent.category.DEFAULT"), appInfo);
            if (intent != null) {
                return intent;
            }
        }
        return intent;
    }

    public static boolean isVersionCompatibleWithBucketedIntent(int version) {
        return KNOWN_PROTOCOL_VERSIONS.contains(Integer.valueOf(version)) && version >= PROTOCOL_VERSION_20140701;
    }

    public static Intent createPlatformActivityIntent(Context context, String callId, String action, int version, Bundle extras) {
        Intent intent = findActivityIntent(context, INTENT_ACTION_PLATFORM_ACTIVITY, action);
        if (intent == null) {
            return null;
        }
        setupProtocolRequestIntent(intent, callId, action, version, extras);
        return intent;
    }

    public static void setupProtocolRequestIntent(Intent intent, String callId, String action, int version, Bundle params) {
        String applicationId = FacebookSdk.getApplicationId();
        String applicationName = FacebookSdk.getApplicationName();
        intent.putExtra(EXTRA_PROTOCOL_VERSION, version).putExtra(EXTRA_PROTOCOL_ACTION, action).putExtra(EXTRA_APPLICATION_ID, applicationId);
        if (isVersionCompatibleWithBucketedIntent(version)) {
            Bundle methodArguments;
            Bundle bridgeArguments = new Bundle();
            bridgeArguments.putString(BRIDGE_ARG_ACTION_ID_STRING, callId);
            Utility.putNonEmptyString(bridgeArguments, "app_name", applicationName);
            intent.putExtra(EXTRA_PROTOCOL_BRIDGE_ARGS, bridgeArguments);
            if (params == null) {
                methodArguments = new Bundle();
            } else {
                methodArguments = params;
            }
            intent.putExtra(EXTRA_PROTOCOL_METHOD_ARGS, methodArguments);
            return;
        }
        intent.putExtra(EXTRA_PROTOCOL_CALL_ID, callId);
        if (!Utility.isNullOrEmpty(applicationName)) {
            intent.putExtra(EXTRA_APPLICATION_NAME, applicationName);
        }
        intent.putExtras(params);
    }

    public static Intent createProtocolResultIntent(Intent requestIntent, Bundle results, FacebookException error) {
        UUID callId = getCallIdFromIntent(requestIntent);
        if (callId == null) {
            return null;
        }
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_PROTOCOL_VERSION, getProtocolVersionFromIntent(requestIntent));
        Bundle bridgeArguments = new Bundle();
        bridgeArguments.putString(BRIDGE_ARG_ACTION_ID_STRING, callId.toString());
        if (error != null) {
            bridgeArguments.putBundle(BRIDGE_ARG_ERROR_BUNDLE, createBundleForException(error));
        }
        resultIntent.putExtra(EXTRA_PROTOCOL_BRIDGE_ARGS, bridgeArguments);
        if (results == null) {
            return resultIntent;
        }
        resultIntent.putExtra(EXTRA_PROTOCOL_METHOD_RESULTS, results);
        return resultIntent;
    }

    public static Intent createPlatformServiceIntent(Context context) {
        for (NativeAppInfo appInfo : facebookAppInfoList) {
            Intent intent = validateServiceIntent(context, new Intent(INTENT_ACTION_PLATFORM_SERVICE).setPackage(appInfo.getPackage()).addCategory("android.intent.category.DEFAULT"), appInfo);
            if (intent != null) {
                return intent;
            }
        }
        return null;
    }

    public static int getProtocolVersionFromIntent(Intent intent) {
        return intent.getIntExtra(EXTRA_PROTOCOL_VERSION, 0);
    }

    public static UUID getCallIdFromIntent(Intent intent) {
        if (intent == null) {
            return null;
        }
        String callIdString = null;
        if (isVersionCompatibleWithBucketedIntent(getProtocolVersionFromIntent(intent))) {
            Bundle bridgeArgs = intent.getBundleExtra(EXTRA_PROTOCOL_BRIDGE_ARGS);
            if (bridgeArgs != null) {
                callIdString = bridgeArgs.getString(BRIDGE_ARG_ACTION_ID_STRING);
            }
        } else {
            callIdString = intent.getStringExtra(EXTRA_PROTOCOL_CALL_ID);
        }
        UUID callId = null;
        if (callIdString == null) {
            return callId;
        }
        try {
            return UUID.fromString(callIdString);
        } catch (IllegalArgumentException e) {
            return callId;
        }
    }

    public static Bundle getBridgeArgumentsFromIntent(Intent intent) {
        if (isVersionCompatibleWithBucketedIntent(getProtocolVersionFromIntent(intent))) {
            return intent.getBundleExtra(EXTRA_PROTOCOL_BRIDGE_ARGS);
        }
        return null;
    }

    public static Bundle getMethodArgumentsFromIntent(Intent intent) {
        if (isVersionCompatibleWithBucketedIntent(getProtocolVersionFromIntent(intent))) {
            return intent.getBundleExtra(EXTRA_PROTOCOL_METHOD_ARGS);
        }
        return intent.getExtras();
    }

    public static Bundle getSuccessResultsFromIntent(Intent resultIntent) {
        int version = getProtocolVersionFromIntent(resultIntent);
        Bundle extras = resultIntent.getExtras();
        return (!isVersionCompatibleWithBucketedIntent(version) || extras == null) ? extras : extras.getBundle(EXTRA_PROTOCOL_METHOD_RESULTS);
    }

    public static boolean isErrorResult(Intent resultIntent) {
        Bundle bridgeArgs = getBridgeArgumentsFromIntent(resultIntent);
        if (bridgeArgs != null) {
            return bridgeArgs.containsKey(BRIDGE_ARG_ERROR_BUNDLE);
        }
        return resultIntent.hasExtra(STATUS_ERROR_TYPE);
    }

    public static Bundle getErrorDataFromResultIntent(Intent resultIntent) {
        if (!isErrorResult(resultIntent)) {
            return null;
        }
        Bundle bridgeArgs = getBridgeArgumentsFromIntent(resultIntent);
        if (bridgeArgs != null) {
            return bridgeArgs.getBundle(BRIDGE_ARG_ERROR_BUNDLE);
        }
        return resultIntent.getExtras();
    }

    public static FacebookException getExceptionFromErrorData(Bundle errorData) {
        if (errorData == null) {
            return null;
        }
        String type = errorData.getString(BRIDGE_ARG_ERROR_TYPE);
        if (type == null) {
            type = errorData.getString(STATUS_ERROR_TYPE);
        }
        String description = errorData.getString(BRIDGE_ARG_ERROR_DESCRIPTION);
        if (description == null) {
            description = errorData.getString(STATUS_ERROR_DESCRIPTION);
        }
        if (type == null || !type.equalsIgnoreCase(ERROR_USER_CANCELED)) {
            return new FacebookException(description);
        }
        return new FacebookOperationCanceledException(description);
    }

    public static Bundle createBundleForException(FacebookException e) {
        if (e == null) {
            return null;
        }
        Bundle errorBundle = new Bundle();
        errorBundle.putString(BRIDGE_ARG_ERROR_DESCRIPTION, e.toString());
        if (!(e instanceof FacebookOperationCanceledException)) {
            return errorBundle;
        }
        errorBundle.putString(BRIDGE_ARG_ERROR_TYPE, ERROR_USER_CANCELED);
        return errorBundle;
    }

    public static int getLatestAvailableProtocolVersionForService(int minimumVersion) {
        return getLatestAvailableProtocolVersionForAppInfoList(facebookAppInfoList, new int[]{minimumVersion});
    }

    public static int getLatestAvailableProtocolVersionForAction(String action, int[] versionSpec) {
        return getLatestAvailableProtocolVersionForAppInfoList((List) actionToAppInfoMap.get(action), versionSpec);
    }

    private static int getLatestAvailableProtocolVersionForAppInfoList(List<NativeAppInfo> appInfoList, int[] versionSpec) {
        updateAllAvailableProtocolVersionsAsync();
        if (appInfoList == null) {
            return -1;
        }
        for (NativeAppInfo appInfo : appInfoList) {
            int protocolVersion = computeLatestAvailableVersionFromVersionSpec(appInfo.getAvailableVersions(), getLatestKnownVersion(), versionSpec);
            if (protocolVersion != -1) {
                return protocolVersion;
            }
        }
        return -1;
    }

    public static void updateAllAvailableProtocolVersionsAsync() {
        if (protocolVersionsAsyncUpdating.compareAndSet(false, true)) {
            FacebookSdk.getExecutor().execute(new 1());
        }
    }

    private static TreeSet<Integer> fetchAllAvailableProtocolVersionsForAppInfo(NativeAppInfo appInfo) {
        TreeSet<Integer> allAvailableVersions = new TreeSet();
        ContentResolver contentResolver = FacebookSdk.getApplicationContext().getContentResolver();
        String[] projection = new String[]{"version"};
        Uri uri = buildPlatformProviderVersionURI(appInfo);
        Cursor c = null;
        try {
            if (FacebookSdk.getApplicationContext().getPackageManager().resolveContentProvider(appInfo.getPackage() + PLATFORM_PROVIDER, 0) != null) {
                c = contentResolver.query(uri, projection, null, null, null);
                if (c != null) {
                    while (c.moveToNext()) {
                        allAvailableVersions.add(Integer.valueOf(c.getInt(c.getColumnIndex("version"))));
                    }
                }
            }
            if (c != null) {
                c.close();
            }
            return allAvailableVersions;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static int computeLatestAvailableVersionFromVersionSpec(TreeSet<Integer> allAvailableFacebookAppVersions, int latestSdkVersion, int[] versionSpec) {
        int versionSpecIndex = versionSpec.length - 1;
        Iterator<Integer> fbAppVersionsIterator = allAvailableFacebookAppVersions.descendingIterator();
        int latestFacebookAppVersion = -1;
        while (fbAppVersionsIterator.hasNext()) {
            int fbAppVersion = ((Integer) fbAppVersionsIterator.next()).intValue();
            latestFacebookAppVersion = Math.max(latestFacebookAppVersion, fbAppVersion);
            while (versionSpecIndex >= 0 && versionSpec[versionSpecIndex] > fbAppVersion) {
                versionSpecIndex--;
            }
            if (versionSpecIndex < 0) {
                return -1;
            }
            if (versionSpec[versionSpecIndex] == fbAppVersion) {
                return versionSpecIndex % 2 == 0 ? Math.min(latestFacebookAppVersion, latestSdkVersion) : -1;
            }
        }
        return -1;
    }

    private static Uri buildPlatformProviderVersionURI(NativeAppInfo appInfo) {
        return Uri.parse(CONTENT_SCHEME + appInfo.getPackage() + PLATFORM_PROVIDER_VERSIONS);
    }
}
