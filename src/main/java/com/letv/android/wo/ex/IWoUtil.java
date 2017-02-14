package com.letv.android.wo.ex;

import android.content.Context;

public interface IWoUtil {
    String getWoBusinessScope(Context context);

    String getWoBusinessTerms(Context context);

    void setWoBusinessScope(Context context, String str);

    void setWoBusinessTerms(Context context, String str);
}
