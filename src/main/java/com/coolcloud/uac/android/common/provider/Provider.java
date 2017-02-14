package com.coolcloud.uac.android.common.provider;

import android.os.Bundle;
import java.util.List;
import java.util.Set;

public interface Provider {
    boolean clearRTKT();

    List<Bundle> getAccountHistory();

    RTKTEntity getDefaultRTKT();

    Set<String> getHistoryInputAccount();

    boolean getMetaBoolean(String str, boolean z);

    RTKTEntity getRTKT();

    TKTEntity getTKT(String str);

    Bundle getUserInfo(String str);

    String getUserItem(String str, String str2);

    boolean putAccountHistory(String str, Bundle bundle);

    boolean putMetaBoolean(String str, boolean z);

    boolean putRTKT(RTKTEntity rTKTEntity);

    boolean putTKT(String str, TKTEntity tKTEntity);

    boolean putUserInfo(String str, Bundle bundle);

    boolean putUserItem(String str, String str2, String str3);

    boolean removeMetaBoolean(String str);

    boolean removeTKT(String str);

    boolean removeUserInfo(String str);

    boolean removeUserItem(String str, String str2);

    RTKTEntity syncRTKT();

    boolean updatePwd(String str, String str2);

    boolean updateUser(String str, String str2);
}
