package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;

public class ApiKey {
    static final String CRASHLYTICS_API_KEY = "com.crashlytics.ApiKey";
    static final String FABRIC_API_KEY = "io.fabric.ApiKey";

    @Deprecated
    public static String getApiKey(Context context) {
        Fabric.getLogger().w(Fabric.TAG, "getApiKey(context) is deprecated, please upgrade kit(s) to the latest version.");
        return new ApiKey().getValue(context);
    }

    @Deprecated
    public static String getApiKey(Context context, boolean debug) {
        Fabric.getLogger().w(Fabric.TAG, "getApiKey(context, debug) is deprecated, please upgrade kit(s) to the latest version.");
        return new ApiKey().getValue(context);
    }

    public String getValue(Context context) {
        String apiKey = getApiKeyFromManifest(context);
        if (TextUtils.isEmpty(apiKey)) {
            apiKey = getApiKeyFromStrings(context);
        }
        if (TextUtils.isEmpty(apiKey)) {
            logErrorOrThrowException(context);
        }
        return apiKey;
    }

    protected String getApiKeyFromManifest(Context context) {
        String apiKey = null;
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            if (bundle != null) {
                apiKey = bundle.getString(FABRIC_API_KEY);
                if (apiKey == null) {
                    Fabric.getLogger().d(Fabric.TAG, "Falling back to Crashlytics key lookup from Manifest");
                    apiKey = bundle.getString(CRASHLYTICS_API_KEY);
                }
            }
        } catch (Exception e) {
            Fabric.getLogger().d(Fabric.TAG, "Caught non-fatal exception while retrieving apiKey: " + e);
        }
        return apiKey;
    }

    protected String getApiKeyFromStrings(Context context) {
        int id = CommonUtils.getResourcesIdentifier(context, FABRIC_API_KEY, "string");
        if (id == 0) {
            Fabric.getLogger().d(Fabric.TAG, "Falling back to Crashlytics key lookup from Strings");
            id = CommonUtils.getResourcesIdentifier(context, CRASHLYTICS_API_KEY, "string");
        }
        if (id != 0) {
            return context.getResources().getString(id);
        }
        return null;
    }

    protected void logErrorOrThrowException(Context context) {
        if (Fabric.isDebuggable() || CommonUtils.isAppDebuggable(context)) {
            throw new IllegalArgumentException(buildApiKeyInstructions());
        }
        Fabric.getLogger().e(Fabric.TAG, buildApiKeyInstructions());
    }

    protected String buildApiKeyInstructions() {
        return "Fabric could not be initialized, API key missing from AndroidManifest.xml. Add the following tag to your Application element \n\t<meta-data android:name=\"io.fabric.ApiKey\" android:value=\"YOUR_API_KEY\"/>";
    }
}
