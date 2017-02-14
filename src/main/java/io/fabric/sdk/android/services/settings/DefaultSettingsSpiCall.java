package io.fabric.sdk.android.services.settings;

import com.letv.core.messagebus.config.LeMessageIds;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class DefaultSettingsSpiCall extends AbstractSpiCall implements SettingsSpiCall {
    static final String BUILD_VERSION_PARAM = "build_version";
    static final String DISPLAY_VERSION_PARAM = "display_version";
    static final String HEADER_ADVERTISING_TOKEN = "X-CRASHLYTICS-ADVERTISING-TOKEN";
    static final String HEADER_ANDROID_ID = "X-CRASHLYTICS-ANDROID-ID";
    static final String HEADER_DEVICE_MODEL = "X-CRASHLYTICS-DEVICE-MODEL";
    static final String HEADER_INSTALLATION_ID = "X-CRASHLYTICS-INSTALLATION-ID";
    static final String HEADER_OS_BUILD_VERSION = "X-CRASHLYTICS-OS-BUILD-VERSION";
    static final String HEADER_OS_DISPLAY_VERSION = "X-CRASHLYTICS-OS-DISPLAY-VERSION";
    static final String ICON_HASH = "icon_hash";
    static final String INSTANCE_PARAM = "instance";
    static final String SOURCE_PARAM = "source";

    public DefaultSettingsSpiCall(Kit kit, String protocolAndHostOverride, String url, HttpRequestFactory requestFactory) {
        this(kit, protocolAndHostOverride, url, requestFactory, HttpMethod.GET);
    }

    DefaultSettingsSpiCall(Kit kit, String protocolAndHostOverride, String url, HttpRequestFactory requestFactory, HttpMethod method) {
        super(kit, protocolAndHostOverride, url, requestFactory, method);
    }

    public JSONObject invoke(SettingsRequest requestData) {
        JSONObject toReturn = null;
        HttpRequest httpRequest = null;
        try {
            Map<String, String> queryParams = getQueryParamsFor(requestData);
            httpRequest = applyHeadersTo(getHttpRequest(queryParams), requestData);
            Fabric.getLogger().d(Fabric.TAG, "Requesting settings from " + getUrl());
            Fabric.getLogger().d(Fabric.TAG, "Settings query params were: " + queryParams);
            toReturn = handleResponse(httpRequest);
            return toReturn;
        } finally {
            if (httpRequest != null) {
                Fabric.getLogger().d(Fabric.TAG, "Settings request ID: " + httpRequest.header(AbstractSpiCall.HEADER_REQUEST_ID));
            }
        }
    }

    JSONObject handleResponse(HttpRequest httpRequest) {
        int statusCode = httpRequest.code();
        Fabric.getLogger().d(Fabric.TAG, "Settings result was: " + statusCode);
        if (requestWasSuccessful(statusCode)) {
            return getJsonObjectFrom(httpRequest.body());
        }
        Fabric.getLogger().e(Fabric.TAG, "Failed to retrieve settings from " + getUrl());
        return null;
    }

    boolean requestWasSuccessful(int httpStatusCode) {
        return httpStatusCode == 200 || httpStatusCode == 201 || httpStatusCode == 202 || httpStatusCode == LeMessageIds.MSG_MAIN_CHECK_CONTEXT;
    }

    private JSONObject getJsonObjectFrom(String httpRequestBody) {
        try {
            return new JSONObject(httpRequestBody);
        } catch (Exception e) {
            Fabric.getLogger().d(Fabric.TAG, "Failed to parse settings JSON from " + getUrl(), e);
            Fabric.getLogger().d(Fabric.TAG, "Settings response " + httpRequestBody);
            return null;
        }
    }

    private Map<String, String> getQueryParamsFor(SettingsRequest requestData) {
        Map<String, String> queryParams = new HashMap();
        queryParams.put(BUILD_VERSION_PARAM, requestData.buildVersion);
        queryParams.put(DISPLAY_VERSION_PARAM, requestData.displayVersion);
        queryParams.put("source", Integer.toString(requestData.source));
        if (requestData.iconHash != null) {
            queryParams.put(ICON_HASH, requestData.iconHash);
        }
        String instanceId = requestData.instanceId;
        if (!CommonUtils.isNullOrEmpty(instanceId)) {
            queryParams.put(INSTANCE_PARAM, instanceId);
        }
        return queryParams;
    }

    private HttpRequest applyHeadersTo(HttpRequest request, SettingsRequest requestData) {
        applyNonNullHeader(request, AbstractSpiCall.HEADER_API_KEY, requestData.apiKey);
        applyNonNullHeader(request, AbstractSpiCall.HEADER_CLIENT_TYPE, AbstractSpiCall.ANDROID_CLIENT_TYPE);
        applyNonNullHeader(request, AbstractSpiCall.HEADER_CLIENT_VERSION, this.kit.getVersion());
        applyNonNullHeader(request, "Accept", "application/json");
        applyNonNullHeader(request, HEADER_DEVICE_MODEL, requestData.deviceModel);
        applyNonNullHeader(request, HEADER_OS_BUILD_VERSION, requestData.osBuildVersion);
        applyNonNullHeader(request, HEADER_OS_DISPLAY_VERSION, requestData.osDisplayVersion);
        applyNonNullHeader(request, HEADER_ADVERTISING_TOKEN, requestData.advertisingId);
        applyNonNullHeader(request, HEADER_INSTALLATION_ID, requestData.installationId);
        applyNonNullHeader(request, HEADER_ANDROID_ID, requestData.androidId);
        return request;
    }

    private void applyNonNullHeader(HttpRequest request, String key, String value) {
        if (value != null) {
            request.header(key, value);
        }
    }
}
