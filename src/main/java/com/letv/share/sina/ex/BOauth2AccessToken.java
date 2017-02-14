package com.letv.share.sina.ex;

public abstract class BOauth2AccessToken {
    public abstract long getExpiresTime();

    public abstract String getRefreshToken();

    public abstract String getToken();

    public abstract int isSessionValid();

    public abstract void setExpiresIn(String str);

    public abstract void setExpiresTime(long j);

    public abstract void setRefreshToken(String str);

    public abstract void setToken(String str);
}
