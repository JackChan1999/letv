package com.letv.adlib.sdk.types;

public class AdElementOffline extends AdElementMime {
    public static int _identityId = 102;
    public String adId;
    public long adSize;
    public String format;
    public String oid;
    public String realPath;
    public int uniqueId;

    public AdElementOffline() {
        int i = _identityId + 1;
        _identityId = i;
        this.uniqueId = i;
    }

    public String toString() {
        return "uniqueId＝" + this.uniqueId + ",adSize=" + this.adSize + ",shortPath=" + getShortPath() + "," + "adId=" + this.adId + ",oid" + this.adId + ",oiid=" + this.adId;
    }
}
