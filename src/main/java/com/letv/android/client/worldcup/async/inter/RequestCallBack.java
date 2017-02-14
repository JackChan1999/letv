package com.letv.android.client.worldcup.async.inter;

import com.letv.core.bean.LetvBaseBean;

public interface RequestCallBack<T extends LetvBaseBean> {
    void dataNull(int i, String str);

    void netErr(int i, String str);

    void netNull();

    void onPostExecute(int i, T t);

    boolean onPreExecute();
}
