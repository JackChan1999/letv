package com.letv.core.pagecard;

import android.content.Context;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.PageCardListBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.parser.PageCardParser;
import com.letv.core.utils.LetvUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PageCardFetcher {
    private static PageCardListBean sPageCardListBean;

    public interface PageCardCallback {
        void onFetch(PageCardListBean pageCardListBean);
    }

    public static void reset() {
        sPageCardListBean = null;
    }

    public static void fetchPageCard(Context context, PageCardCallback callback) {
        if (callback != null) {
            if (sPageCardListBean == null || callback == null) {
                new LetvRequest().setUrl(LetvUrlMaker.getPageCard(PreferencesManager.getInstance().getPageCardVersion(), "channel")).setParser(new PageCardParser(false)).setCache(new VolleyDiskCache("pagecard_" + LetvUtils.getClientVersionName(), true)).setAlwaysCallbackNetworkResponse(true).setCallback(new 1(callback, context)).add();
            } else {
                callback.onFetch(sPageCardListBean);
            }
        }
    }

    private static void fetchDefaultPageCard(Context context, PageCardCallback callback) {
        PreferencesManager.getInstance().setPageCardVersion("0");
        new LetvRequest().setRequestType(RequestManner.CACHE_ONLY).setParser(new PageCardParser(true)).setCache(new 3(context)).setCallback(new 2(callback)).add();
    }

    private static String getXml(Context context) {
        Exception e;
        Throwable th;
        BufferedReader bufReader = null;
        try {
            BufferedReader bufReader2 = new BufferedReader(new InputStreamReader(context.getResources().getAssets().open("pagecard_config.xml")));
            try {
                String line = "";
                String Result = "";
                while (true) {
                    line = bufReader2.readLine();
                    if (line == null) {
                        break;
                    }
                    Result = Result + line;
                }
                if (bufReader2 != null) {
                    try {
                        bufReader2.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                bufReader = bufReader2;
                return Result;
            } catch (Exception e3) {
                e = e3;
                bufReader = bufReader2;
                try {
                    e.printStackTrace();
                    if (bufReader != null) {
                        try {
                            bufReader.close();
                        } catch (IOException e22) {
                            e22.printStackTrace();
                        }
                    }
                    return "";
                } catch (Throwable th2) {
                    th = th2;
                    if (bufReader != null) {
                        try {
                            bufReader.close();
                        } catch (IOException e222) {
                            e222.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                bufReader = bufReader2;
                if (bufReader != null) {
                    bufReader.close();
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            e.printStackTrace();
            if (bufReader != null) {
                bufReader.close();
            }
            return "";
        }
    }
}
