package io.fabric.sdk.android.services.settings;

import android.content.res.Resources.NotFoundException;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.KitInfo;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.io.InputStream;
import java.util.Locale;

abstract class AbstractAppSpiCall extends AbstractSpiCall implements AppSpiCall {
    public static final String APP_BUILD_VERSION_PARAM = "app[build_version]";
    public static final String APP_BUILT_SDK_VERSION_PARAM = "app[built_sdk_version]";
    public static final String APP_DISPLAY_VERSION_PARAM = "app[display_version]";
    public static final String APP_ICON_DATA_PARAM = "app[icon][data]";
    public static final String APP_ICON_HASH_PARAM = "app[icon][hash]";
    public static final String APP_ICON_HEIGHT_PARAM = "app[icon][height]";
    public static final String APP_ICON_PRERENDERED_PARAM = "app[icon][prerendered]";
    public static final String APP_ICON_WIDTH_PARAM = "app[icon][width]";
    public static final String APP_IDENTIFIER_PARAM = "app[identifier]";
    public static final String APP_INSTANCE_IDENTIFIER_PARAM = "app[instance_identifier]";
    public static final String APP_MIN_SDK_VERSION_PARAM = "app[minimum_sdk_version]";
    public static final String APP_NAME_PARAM = "app[name]";
    public static final String APP_SDK_MODULES_PARAM_BUILD_TYPE = "app[build][libraries][%s][type]";
    public static final String APP_SDK_MODULES_PARAM_PREFIX = "app[build][libraries][%s]";
    public static final String APP_SDK_MODULES_PARAM_VERSION = "app[build][libraries][%s][version]";
    public static final String APP_SOURCE_PARAM = "app[source]";
    static final String ICON_CONTENT_TYPE = "application/octet-stream";
    static final String ICON_FILE_NAME = "icon.png";

    public AbstractAppSpiCall(Kit kit, String protocolAndHostOverride, String url, HttpRequestFactory requestFactory, HttpMethod method) {
        super(kit, protocolAndHostOverride, url, requestFactory, method);
    }

    public boolean invoke(AppRequestData requestData) {
        HttpRequest httpRequest = applyMultipartDataTo(applyHeadersTo(getHttpRequest(), requestData), requestData);
        Fabric.getLogger().d(Fabric.TAG, "Sending app info to " + getUrl());
        if (requestData.icon != null) {
            Fabric.getLogger().d(Fabric.TAG, "App icon hash is " + requestData.icon.hash);
            Fabric.getLogger().d(Fabric.TAG, "App icon size is " + requestData.icon.width + "x" + requestData.icon.height);
        }
        int statusCode = httpRequest.code();
        Fabric.getLogger().d(Fabric.TAG, ("POST".equals(httpRequest.method()) ? "Create" : "Update") + " app request ID: " + httpRequest.header(AbstractSpiCall.HEADER_REQUEST_ID));
        Fabric.getLogger().d(Fabric.TAG, "Result was " + statusCode);
        if (ResponseParser.parse(statusCode) == 0) {
            return true;
        }
        return false;
    }

    private HttpRequest applyHeadersTo(HttpRequest request, AppRequestData requestData) {
        return request.header(AbstractSpiCall.HEADER_API_KEY, requestData.apiKey).header(AbstractSpiCall.HEADER_CLIENT_TYPE, AbstractSpiCall.ANDROID_CLIENT_TYPE).header(AbstractSpiCall.HEADER_CLIENT_VERSION, this.kit.getVersion());
    }

    private HttpRequest applyMultipartDataTo(HttpRequest request, AppRequestData requestData) {
        request = request.part(APP_IDENTIFIER_PARAM, requestData.appId).part(APP_NAME_PARAM, requestData.name).part(APP_DISPLAY_VERSION_PARAM, requestData.displayVersion).part(APP_BUILD_VERSION_PARAM, requestData.buildVersion).part(APP_SOURCE_PARAM, Integer.valueOf(requestData.source)).part(APP_MIN_SDK_VERSION_PARAM, requestData.minSdkVersion).part(APP_BUILT_SDK_VERSION_PARAM, requestData.builtSdkVersion);
        if (!CommonUtils.isNullOrEmpty(requestData.instanceIdentifier)) {
            request.part(APP_INSTANCE_IDENTIFIER_PARAM, requestData.instanceIdentifier);
        }
        if (requestData.icon != null) {
            InputStream inputStream = null;
            try {
                inputStream = this.kit.getContext().getResources().openRawResource(requestData.icon.iconResourceId);
                request.part(APP_ICON_HASH_PARAM, requestData.icon.hash).part(APP_ICON_DATA_PARAM, ICON_FILE_NAME, ICON_CONTENT_TYPE, inputStream).part(APP_ICON_WIDTH_PARAM, Integer.valueOf(requestData.icon.width)).part(APP_ICON_HEIGHT_PARAM, Integer.valueOf(requestData.icon.height));
            } catch (NotFoundException e) {
                Fabric.getLogger().e(Fabric.TAG, "Failed to find app icon with resource ID: " + requestData.icon.iconResourceId, e);
            } finally {
                CommonUtils.closeOrLog(inputStream, "Failed to close app icon InputStream.");
            }
        }
        if (requestData.sdkKits != null) {
            for (KitInfo kit : requestData.sdkKits) {
                request.part(getKitVersionKey(kit), kit.getVersion());
                request.part(getKitBuildTypeKey(kit), kit.getBuildType());
            }
        }
        return request;
    }

    String getKitVersionKey(KitInfo kit) {
        return String.format(Locale.US, APP_SDK_MODULES_PARAM_VERSION, new Object[]{kit.getIdentifier()});
    }

    String getKitBuildTypeKey(KitInfo kit) {
        return String.format(Locale.US, APP_SDK_MODULES_PARAM_BUILD_TYPE, new Object[]{kit.getIdentifier()});
    }
}
