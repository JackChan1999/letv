package io.fabric.sdk.android.services.common;

import android.content.Context;
import io.fabric.sdk.android.Fabric;

class AdvertisingInfoReflectionStrategy implements AdvertisingInfoStrategy {
    private static final String CLASS_NAME_ADVERTISING_ID_CLIENT = "com.google.android.gms.ads.identifier.AdvertisingIdClient";
    private static final String CLASS_NAME_ADVERTISING_ID_CLIENT_INFO = "com.google.android.gms.ads.identifier.AdvertisingIdClient$Info";
    private static final String CLASS_NAME_GOOGLE_PLAY_SERVICES_UTILS = "com.google.android.gms.common.GooglePlayServicesUtil";
    private static final int GOOGLE_PLAY_SERVICES_SUCCESS_CODE = 0;
    private static final String METHOD_NAME_GET_ADVERTISING_ID_INFO = "getAdvertisingIdInfo";
    private static final String METHOD_NAME_GET_ID = "getId";
    private static final String METHOD_NAME_IS_GOOGLE_PLAY_SERVICES_AVAILABLE = "isGooglePlayServicesAvailable";
    private static final String METHOD_NAME_IS_LIMITED_AD_TRACKING_ENABLED = "isLimitAdTrackingEnabled";
    private final Context context;

    public AdvertisingInfoReflectionStrategy(Context context) {
        this.context = context.getApplicationContext();
    }

    boolean isGooglePlayServiceAvailable(Context context) {
        try {
            if (((Integer) Class.forName(CLASS_NAME_GOOGLE_PLAY_SERVICES_UTILS).getMethod(METHOD_NAME_IS_GOOGLE_PLAY_SERVICES_AVAILABLE, new Class[]{Context.class}).invoke(null, new Object[]{context})).intValue() == 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public AdvertisingInfo getAdvertisingInfo() {
        if (isGooglePlayServiceAvailable(this.context)) {
            return new AdvertisingInfo(getAdvertisingId(), isLimitAdTrackingEnabled());
        }
        return null;
    }

    private String getAdvertisingId() {
        try {
            return (String) Class.forName(CLASS_NAME_ADVERTISING_ID_CLIENT_INFO).getMethod(METHOD_NAME_GET_ID, new Class[0]).invoke(getInfo(), new Object[0]);
        } catch (Exception e) {
            Fabric.getLogger().w(Fabric.TAG, "Could not call getId on com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
            return null;
        }
    }

    private boolean isLimitAdTrackingEnabled() {
        try {
            return ((Boolean) Class.forName(CLASS_NAME_ADVERTISING_ID_CLIENT_INFO).getMethod(METHOD_NAME_IS_LIMITED_AD_TRACKING_ENABLED, new Class[0]).invoke(getInfo(), new Object[0])).booleanValue();
        } catch (Exception e) {
            Fabric.getLogger().w(Fabric.TAG, "Could not call isLimitAdTrackingEnabled on com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
            return false;
        }
    }

    private Object getInfo() {
        Object obj = null;
        try {
            obj = Class.forName(CLASS_NAME_ADVERTISING_ID_CLIENT).getMethod(METHOD_NAME_GET_ADVERTISING_ID_INFO, new Class[]{Context.class}).invoke(null, new Object[]{this.context});
        } catch (Exception e) {
            Fabric.getLogger().w(Fabric.TAG, "Could not call getAdvertisingIdInfo on com.google.android.gms.ads.identifier.AdvertisingIdClient");
        }
        return obj;
    }
}
