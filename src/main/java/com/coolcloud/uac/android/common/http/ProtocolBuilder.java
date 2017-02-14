package com.coolcloud.uac.android.common.http;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.text.TextUtils;
import com.coolcloud.uac.android.common.Config;
import com.coolcloud.uac.android.common.util.ContextUtils;
import com.coolcloud.uac.android.common.util.SystemUtils;
import com.coolcloud.uac.android.common.util.TimeUtils;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import master.flame.danmaku.danmaku.parser.IDataSource;

public class ProtocolBuilder {
    private static final String CLIENT_TYPE = "clientype";
    private static final String DEVICE_ID = "devid";
    private static final String DEVICE_MODEL = "devmodel";
    private static final String IS_GZIP = "gz";
    private static final String JAR_VERSION = "jarversion";
    private static final String MAC_ID = "mac";
    private static final String MEID_ID = "medi";
    private static final String NETWORK_TYPE = "netype";
    private static final String PKG_NAME = "pkgname";
    private static final String PKG_VERSION = "pkgversion";
    private static final String PROTOCOL_VERSION = "pv";
    private static final String REQUEST_TIME = "t";
    private static final String SN_ID = "sn";
    private static final String UACBRAND = "uacbrand";
    private String clientType = AbstractSpiCall.ANDROID_CLIENT_TYPE;
    private Context context = null;
    private String gzip = "1";
    private String protocolVersion = "1";

    public ProtocolBuilder(Context context) {
        this.context = context.getApplicationContext();
    }

    protected Builder getBuilder(String host, String path) {
        Builder ub = new Builder().scheme(IDataSource.SCHEME_HTTP_TAG).authority(host);
        ub.appendEncodedPath(path);
        addCommonParam(ub);
        return ub;
    }

    protected Builder getBuilder(String path) {
        Builder ub = new Builder();
        ub.appendEncodedPath(path);
        addCommonParam(ub);
        return ub;
    }

    protected Builder getBuilder(String path, String key, String value) {
        Builder ub = new Builder();
        ub.appendEncodedPath(path);
        append(ub, key, value);
        addCommonParam(ub);
        return ub;
    }

    protected Builder addCommonParam(Builder ub) {
        append(ub, CLIENT_TYPE, this.clientType);
        append(ub, "devid", getDeviceId());
        append(ub, MAC_ID, getMacId());
        append(ub, SN_ID, getSnId());
        append(ub, PKG_NAME, getpkgName());
        append(ub, JAR_VERSION, "2.2.1");
        append(ub, UACBRAND, Config.getUacBrand());
        append(ub, PKG_VERSION, getAppVersion());
        append(ub, MEID_ID, getMeidId());
        append(ub, DEVICE_MODEL, getDeviceModel());
        append(ub, NETWORK_TYPE, SystemUtils.getNetworkType(ContextUtils.getContext()));
        append(ub, IS_GZIP, this.gzip);
        append(ub, PROTOCOL_VERSION, this.protocolVersion);
        append(ub, REQUEST_TIME, TimeUtils.nowTime());
        return ub;
    }

    protected Builder append(Builder builder, String key, String value) {
        if (!(TextUtils.isEmpty(key) || TextUtils.isEmpty(value))) {
            builder.appendQueryParameter(key, value);
        }
        return builder;
    }

    protected Builder append(Builder builder, Bundle bundle) {
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                append(builder, key, bundle.getString(key));
            }
        }
        return builder;
    }

    protected HTTPAgent.Builder append(HTTPAgent.Builder builder, String key, String value) {
        if (!(TextUtils.isEmpty(key) || TextUtils.isEmpty(value))) {
            builder.append(key, value);
        }
        return builder;
    }

    protected HTTPAgent.Builder append(HTTPAgent.Builder builder, Bundle bundle) {
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                append(builder, key, bundle.getString(key));
            }
        }
        return builder;
    }

    protected String getDeviceId() {
        if (this.context != null) {
            return SystemUtils.getDeviceId(this.context);
        }
        return "";
    }

    protected String getFreeCallDeviceId() {
        if (this.context != null) {
            return SystemUtils.getFreeCallDeviceId(this.context);
        }
        return "";
    }

    protected String getMacId() {
        if (this.context != null) {
            return SystemUtils.getMacId(this.context);
        }
        return "";
    }

    protected String getSnId() {
        if (this.context != null) {
            return SystemUtils.getSnId(this.context);
        }
        return "";
    }

    protected String getpkgName() {
        if (this.context != null) {
            return this.context.getPackageName();
        }
        return "";
    }

    public String getAppVersion() {
        if (this.context == null) {
            return "";
        }
        try {
            return this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    private String getMeidId() {
        if (this.context != null) {
            return SystemUtils.getMeidId(this.context);
        }
        return "";
    }

    protected String getDeviceModel() {
        return SystemUtils.getDeviceModel();
    }
}
