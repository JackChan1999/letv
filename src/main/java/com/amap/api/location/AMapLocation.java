package com.amap.api.location;

import android.location.Location;
import android.os.Bundle;
import com.letv.core.api.UrlConstdata.HOME_RECOMMEND_PARAMETERS;
import com.letv.lemallsdk.util.Constants;
import com.tencent.open.SocialConstants;
import org.json.JSONObject;

public class AMapLocation extends Location {
    public static final int ERROR_CODE_FAILURE_AUTH = 7;
    public static final int ERROR_CODE_FAILURE_CELL = 11;
    public static final int ERROR_CODE_FAILURE_CONNECTION = 4;
    public static final int ERROR_CODE_FAILURE_INIT = 9;
    public static final int ERROR_CODE_FAILURE_LOCATION = 6;
    public static final int ERROR_CODE_FAILURE_LOCATION_PARAMETER = 3;
    public static final int ERROR_CODE_FAILURE_LOCATION_PERMISSION = 12;
    public static final int ERROR_CODE_FAILURE_PARSER = 5;
    public static final int ERROR_CODE_FAILURE_WIFI_INFO = 2;
    public static final int ERROR_CODE_INVALID_PARAMETER = 1;
    public static final int ERROR_CODE_SERVICE_FAIL = 10;
    public static final int ERROR_CODE_UNKNOWN = 8;
    public static final int LOCATION_SUCCESS = 0;
    public static final int LOCATION_TYPE_AMAP = 7;
    public static final int LOCATION_TYPE_CELL = 6;
    public static final int LOCATION_TYPE_FIX_CACHE = 4;
    public static final int LOCATION_TYPE_GPS = 1;
    public static final int LOCATION_TYPE_OFFLINE = 8;
    public static final int LOCATION_TYPE_SAME_REQ = 2;
    public static final int LOCATION_TYPE_WIFI = 5;
    private String a = "";
    private String b = "";
    private String c = "";
    private String d = "";
    private String e = "";
    private String f = "";
    private String g = "";
    private String h = "";
    private String i = "";
    private String j = "";
    private String k = "";
    private boolean l = true;
    private int m = 0;
    private String n = Constants.CALLBACK_SUCCESS;
    private String o = "";
    private int p = 0;
    private double q = 0.0d;
    private double r = 0.0d;
    private int s = 0;

    public AMapLocation(String str) {
        super(str);
    }

    public AMapLocation(Location location) {
        super(location);
        this.q = location.getLatitude();
        this.r = location.getLongitude();
    }

    public int getLocationType() {
        return this.p;
    }

    public void setLocationType(int i) {
        this.p = i;
    }

    public String getLocationDetail() {
        return this.o;
    }

    public void setLocationDetail(String str) {
        this.o = str;
    }

    public int getErrorCode() {
        return this.m;
    }

    public void setErrorCode(int i) {
        if (this.m == 0) {
            switch (i) {
                case 0:
                    this.n = Constants.CALLBACK_SUCCESS;
                    break;
                case 1:
                    this.n = "重要参数为空";
                    break;
                case 2:
                    this.n = "WIFI信息不足";
                    break;
                case 3:
                    this.n = "请求参数获取出现异常";
                    break;
                case 4:
                    this.n = "网络连接异常";
                    break;
                case 5:
                    this.n = "解析XML出错";
                    break;
                case 6:
                    this.n = "定位结果错误";
                    break;
                case 7:
                    this.n = "KEY错误";
                    break;
                case 8:
                    this.n = "其他错误";
                    break;
                case 9:
                    this.n = "初始化异常";
                    break;
                case 10:
                    this.n = "定位服务启动失败";
                    break;
                case 11:
                    this.n = "错误的基站信息，请检查是否插入SIM卡";
                    break;
                case 12:
                    this.n = "缺少定位权限";
                    break;
            }
            this.m = i;
        }
    }

    public String getErrorInfo() {
        return this.n;
    }

    public void setErrorInfo(String str) {
        this.n = str;
    }

    public String getCountry() {
        return this.h;
    }

    public void setCountry(String str) {
        this.h = str;
    }

    public String getRoad() {
        return this.i;
    }

    public void setRoad(String str) {
        this.i = str;
    }

    public String getAddress() {
        return this.f;
    }

    public void setAddress(String str) {
        this.f = str;
    }

    public String getProvince() {
        return this.a;
    }

    public void setProvince(String str) {
        this.a = str;
    }

    public String getCity() {
        return this.b;
    }

    public void setCity(String str) {
        this.b = str;
    }

    public String getDistrict() {
        return this.c;
    }

    public void setDistrict(String str) {
        this.c = str;
    }

    public String getCityCode() {
        return this.d;
    }

    public void setCityCode(String str) {
        this.d = str;
    }

    public String getAdCode() {
        return this.e;
    }

    public void setAdCode(String str) {
        this.e = str;
    }

    public String getPoiName() {
        return this.g;
    }

    public void setPoiName(String str) {
        this.g = str;
    }

    public double getLatitude() {
        return this.q;
    }

    public void setLatitude(double d) {
        this.q = d;
    }

    public double getLongitude() {
        return this.r;
    }

    public void setLongitude(double d) {
        this.r = d;
    }

    public int getSatellites() {
        return this.s;
    }

    public void setSatellites(int i) {
        this.s = i;
    }

    public String getStreet() {
        return this.j;
    }

    public void setStreet(String str) {
        this.j = str;
    }

    public String getStreetNum() {
        return this.k;
    }

    public void setNumber(String str) {
        this.k = str;
    }

    public void setOffset(boolean z) {
        this.l = z;
    }

    public boolean isOffset() {
        return this.l;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("latitude=" + this.q);
        stringBuffer.append("longitude=" + this.r);
        stringBuffer.append("province=" + this.a + "#");
        stringBuffer.append("city=" + this.b + "#");
        stringBuffer.append("district=" + this.c + "#");
        stringBuffer.append("cityCode=" + this.d + "#");
        stringBuffer.append("adCode=" + this.e + "#");
        stringBuffer.append("address=" + this.f + "#");
        stringBuffer.append("country=" + this.h + "#");
        stringBuffer.append("road=" + this.i + "#");
        stringBuffer.append("poiName=" + this.g + "#");
        stringBuffer.append("street=" + this.j + "#");
        stringBuffer.append("streetNum=" + this.k + "#");
        stringBuffer.append("errorCode=" + this.m + "#");
        stringBuffer.append("errorInfo=" + this.n + "#");
        stringBuffer.append("locationDetail=" + this.o + "#");
        stringBuffer.append("locationType=" + this.p);
        return stringBuffer.toString();
    }

    public String toStr() {
        return toStr(1);
    }

    public String toStr(int i) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject();
            switch (i) {
                case 1:
                    jSONObject.put(HOME_RECOMMEND_PARAMETERS.COUNTRY, this.h);
                    jSONObject.put("province", this.a);
                    jSONObject.put("city", this.b);
                    jSONObject.put("cityCode", this.d);
                    jSONObject.put("district", this.c);
                    jSONObject.put("adCode", this.e);
                    jSONObject.put("address", this.f);
                    jSONObject.put("road", this.i);
                    jSONObject.put("street", this.j);
                    jSONObject.put("number", this.k);
                    jSONObject.put("poiName", this.g);
                    jSONObject.put("errorCode", this.m);
                    jSONObject.put("errorInfo", this.n);
                    jSONObject.put("locationDetail", this.o);
                    jSONObject.put("altitude", getAltitude());
                    jSONObject.put("bearing", (double) getBearing());
                    jSONObject.put("speed", (double) getSpeed());
                    jSONObject.put("satellites", this.s);
                    try {
                        Bundle extras = getExtras();
                        if (extras != null && extras.containsKey(SocialConstants.PARAM_APP_DESC)) {
                            jSONObject.put(SocialConstants.PARAM_APP_DESC, extras.getString(SocialConstants.PARAM_APP_DESC));
                            break;
                        }
                    } catch (Throwable th) {
                        break;
                    }
                case 2:
                    break;
                case 3:
                    break;
            }
            if (jSONObject != null) {
                return null;
            }
            return jSONObject.toString();
            jSONObject.put("time", getTime());
            jSONObject.put("locationType", this.p);
            jSONObject.put("accuracy", (double) getAccuracy());
            jSONObject.put("latitude", getLatitude());
            jSONObject.put("longitude", getLongitude());
            jSONObject.put("provider", getProvider());
            if (jSONObject != null) {
                return jSONObject.toString();
            }
            return null;
        } catch (Exception e) {
            jSONObject = null;
        }
    }

    public float getAccuracy() {
        return super.getAccuracy();
    }

    public float getBearing() {
        return super.getBearing();
    }

    public double getAltitude() {
        return super.getAltitude();
    }

    public float getSpeed() {
        return super.getSpeed();
    }

    public String getProvider() {
        return super.getProvider();
    }
}
