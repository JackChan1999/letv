package com.letv.android.client.task;

import android.content.Context;
import android.text.TextUtils;
import com.letv.android.client.parser.ChannelsParser;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.ChannelListBean;
import com.letv.core.bean.ChannelListBean.Channel;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.JSONObject;

public class ChannelWallFetcherTask {
    private static ChannelWallFetcherTask instance;
    private final String TAG = ChannelWallFetcherTask.class.getName();
    private ChannelListBean mChannelListBean;
    private String[] mzCidMoreChannel;
    private String[] mzCidNavigation;

    public interface ChannelListCallback {
        void onFetch(ChannelListBean channelListBean);
    }

    private ChannelWallFetcherTask() {
    }

    public static synchronized ChannelWallFetcherTask getInstance() {
        ChannelWallFetcherTask channelWallFetcherTask;
        synchronized (ChannelWallFetcherTask.class) {
            if (instance == null) {
                instance = new ChannelWallFetcherTask();
            }
            channelWallFetcherTask = instance;
        }
        return channelWallFetcherTask;
    }

    public void fetchChannelWall(Context context, ChannelListCallback callback) {
        if (callback != null) {
            if (this.mChannelListBean == null || BaseTypeUtils.isListEmpty(this.mChannelListBean.listChannel) || callback == null) {
                new LetvRequest().setUrl(MediaAssetApi.getInstance().getChannelListUrl("")).setParser(new ChannelsParser()).setCache(new VolleyDiskCache("channel_wall_list")).setTag(this.TAG + "channel_wall_list").setAlwaysCallbackNetworkResponse(true).setCallback(new 1(this, callback, context)).add();
            } else {
                doNavigation(this.mChannelListBean, callback);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void doNavigation(com.letv.core.bean.ChannelListBean r10, com.letv.android.client.task.ChannelWallFetcherTask.ChannelListCallback r11) {
        /*
        r9 = this;
        r8 = -1;
        r7 = 1;
        if (r11 != 0) goto L_0x0005;
    L_0x0004:
        return;
    L_0x0005:
        if (r10 == 0) goto L_0x000f;
    L_0x0007:
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r5 = com.letv.core.utils.BaseTypeUtils.isListEmpty(r5);	 Catch:{ Exception -> 0x0032 }
        if (r5 == 0) goto L_0x0013;
    L_0x000f:
        r5 = 0;
        r11.onFetch(r5);	 Catch:{ Exception -> 0x0032 }
    L_0x0013:
        r5 = 1;
        r5 = r9.getChannelListBean(r5);	 Catch:{ Exception -> 0x0032 }
        r9.mzCidNavigation = r5;	 Catch:{ Exception -> 0x0032 }
        r5 = 0;
        r5 = r9.getChannelListBean(r5);	 Catch:{ Exception -> 0x0032 }
        r9.mzCidMoreChannel = r5;	 Catch:{ Exception -> 0x0032 }
        r5 = r9.mzCidNavigation;	 Catch:{ Exception -> 0x0032 }
        r5 = com.letv.core.utils.BaseTypeUtils.isArrayEmpty(r5);	 Catch:{ Exception -> 0x0032 }
        if (r5 == 0) goto L_0x0037;
    L_0x0029:
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        java.util.Collections.sort(r5);	 Catch:{ Exception -> 0x0032 }
        r11.onFetch(r10);	 Catch:{ Exception -> 0x0032 }
        goto L_0x0004;
    L_0x0032:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0004;
    L_0x0037:
        r5 = r9.mzCidNavigation;	 Catch:{ Exception -> 0x0032 }
        r3 = java.util.Arrays.asList(r5);	 Catch:{ Exception -> 0x0032 }
        r4 = 0;
        r5 = r9.mzCidMoreChannel;	 Catch:{ Exception -> 0x0032 }
        r5 = com.letv.core.utils.BaseTypeUtils.isArrayEmpty(r5);	 Catch:{ Exception -> 0x0032 }
        if (r5 != 0) goto L_0x004c;
    L_0x0046:
        r5 = r9.mzCidMoreChannel;	 Catch:{ Exception -> 0x0032 }
        r4 = java.util.Arrays.asList(r5);	 Catch:{ Exception -> 0x0032 }
    L_0x004c:
        r2 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r1 = 0;
    L_0x004f:
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.size();	 Catch:{ Exception -> 0x0032 }
        if (r1 >= r5) goto L_0x0163;
    L_0x0057:
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.get(r1);	 Catch:{ Exception -> 0x0032 }
        if (r5 != 0) goto L_0x0062;
    L_0x005f:
        r1 = r1 + 1;
        goto L_0x004f;
    L_0x0062:
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.get(r1);	 Catch:{ Exception -> 0x0032 }
        r5 = (com.letv.core.bean.ChannelListBean.Channel) r5;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.lock;	 Catch:{ Exception -> 0x0032 }
        if (r5 != r7) goto L_0x0096;
    L_0x006e:
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.get(r1);	 Catch:{ Exception -> 0x0032 }
        r5 = (com.letv.core.bean.ChannelListBean.Channel) r5;	 Catch:{ Exception -> 0x0032 }
        r5.index = r1;	 Catch:{ Exception -> 0x0032 }
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.get(r1);	 Catch:{ Exception -> 0x0032 }
        r5 = (com.letv.core.bean.ChannelListBean.Channel) r5;	 Catch:{ Exception -> 0x0032 }
        r6 = 0;
        r5.top = r6;	 Catch:{ Exception -> 0x0032 }
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.get(r1);	 Catch:{ Exception -> 0x0032 }
        r5 = (com.letv.core.bean.ChannelListBean.Channel) r5;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.id;	 Catch:{ Exception -> 0x0032 }
        r5 = java.lang.String.valueOf(r5);	 Catch:{ Exception -> 0x0032 }
        r6 = 0;
        isTop(r10, r5, r6);	 Catch:{ Exception -> 0x0032 }
        goto L_0x005f;
    L_0x0096:
        if (r3 == 0) goto L_0x00e7;
    L_0x0098:
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.get(r1);	 Catch:{ Exception -> 0x0032 }
        r5 = (com.letv.core.bean.ChannelListBean.Channel) r5;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.id;	 Catch:{ Exception -> 0x0032 }
        r5 = java.lang.String.valueOf(r5);	 Catch:{ Exception -> 0x0032 }
        r5 = r3.indexOf(r5);	 Catch:{ Exception -> 0x0032 }
        if (r5 == r8) goto L_0x00e7;
    L_0x00ac:
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.get(r1);	 Catch:{ Exception -> 0x0032 }
        r5 = (com.letv.core.bean.ChannelListBean.Channel) r5;	 Catch:{ Exception -> 0x0032 }
        r6 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r6 = r6.get(r1);	 Catch:{ Exception -> 0x0032 }
        r6 = (com.letv.core.bean.ChannelListBean.Channel) r6;	 Catch:{ Exception -> 0x0032 }
        r6 = r6.id;	 Catch:{ Exception -> 0x0032 }
        r6 = java.lang.String.valueOf(r6);	 Catch:{ Exception -> 0x0032 }
        r6 = r3.indexOf(r6);	 Catch:{ Exception -> 0x0032 }
        r5.index = r6;	 Catch:{ Exception -> 0x0032 }
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.get(r1);	 Catch:{ Exception -> 0x0032 }
        r5 = (com.letv.core.bean.ChannelListBean.Channel) r5;	 Catch:{ Exception -> 0x0032 }
        r6 = 0;
        r5.top = r6;	 Catch:{ Exception -> 0x0032 }
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.get(r1);	 Catch:{ Exception -> 0x0032 }
        r5 = (com.letv.core.bean.ChannelListBean.Channel) r5;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.id;	 Catch:{ Exception -> 0x0032 }
        r5 = java.lang.String.valueOf(r5);	 Catch:{ Exception -> 0x0032 }
        r6 = 0;
        isTop(r10, r5, r6);	 Catch:{ Exception -> 0x0032 }
        goto L_0x005f;
    L_0x00e7:
        if (r4 == 0) goto L_0x0138;
    L_0x00e9:
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.get(r1);	 Catch:{ Exception -> 0x0032 }
        r5 = (com.letv.core.bean.ChannelListBean.Channel) r5;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.id;	 Catch:{ Exception -> 0x0032 }
        r5 = java.lang.String.valueOf(r5);	 Catch:{ Exception -> 0x0032 }
        r5 = r4.indexOf(r5);	 Catch:{ Exception -> 0x0032 }
        if (r5 == r8) goto L_0x0138;
    L_0x00fd:
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.get(r1);	 Catch:{ Exception -> 0x0032 }
        r5 = (com.letv.core.bean.ChannelListBean.Channel) r5;	 Catch:{ Exception -> 0x0032 }
        r6 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r6 = r6.get(r1);	 Catch:{ Exception -> 0x0032 }
        r6 = (com.letv.core.bean.ChannelListBean.Channel) r6;	 Catch:{ Exception -> 0x0032 }
        r6 = r6.id;	 Catch:{ Exception -> 0x0032 }
        r6 = java.lang.String.valueOf(r6);	 Catch:{ Exception -> 0x0032 }
        r6 = r4.indexOf(r6);	 Catch:{ Exception -> 0x0032 }
        r5.index = r6;	 Catch:{ Exception -> 0x0032 }
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.get(r1);	 Catch:{ Exception -> 0x0032 }
        r5 = (com.letv.core.bean.ChannelListBean.Channel) r5;	 Catch:{ Exception -> 0x0032 }
        r6 = 1;
        r5.top = r6;	 Catch:{ Exception -> 0x0032 }
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.get(r1);	 Catch:{ Exception -> 0x0032 }
        r5 = (com.letv.core.bean.ChannelListBean.Channel) r5;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.id;	 Catch:{ Exception -> 0x0032 }
        r5 = java.lang.String.valueOf(r5);	 Catch:{ Exception -> 0x0032 }
        r6 = 1;
        isTop(r10, r5, r6);	 Catch:{ Exception -> 0x0032 }
        goto L_0x005f;
    L_0x0138:
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.get(r1);	 Catch:{ Exception -> 0x0032 }
        r5 = (com.letv.core.bean.ChannelListBean.Channel) r5;	 Catch:{ Exception -> 0x0032 }
        r6 = r2 + r1;
        r5.index = r6;	 Catch:{ Exception -> 0x0032 }
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.get(r1);	 Catch:{ Exception -> 0x0032 }
        r5 = (com.letv.core.bean.ChannelListBean.Channel) r5;	 Catch:{ Exception -> 0x0032 }
        r6 = 0;
        r5.top = r6;	 Catch:{ Exception -> 0x0032 }
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.get(r1);	 Catch:{ Exception -> 0x0032 }
        r5 = (com.letv.core.bean.ChannelListBean.Channel) r5;	 Catch:{ Exception -> 0x0032 }
        r5 = r5.id;	 Catch:{ Exception -> 0x0032 }
        r5 = java.lang.String.valueOf(r5);	 Catch:{ Exception -> 0x0032 }
        r6 = 0;
        isTop(r10, r5, r6);	 Catch:{ Exception -> 0x0032 }
        goto L_0x005f;
    L_0x0163:
        r5 = r10.listChannel;	 Catch:{ Exception -> 0x0032 }
        java.util.Collections.sort(r5);	 Catch:{ Exception -> 0x0032 }
        r11.onFetch(r10);	 Catch:{ Exception -> 0x0032 }
        goto L_0x0004;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.android.client.task.ChannelWallFetcherTask.doNavigation(com.letv.core.bean.ChannelListBean, com.letv.android.client.task.ChannelWallFetcherTask$ChannelListCallback):void");
    }

    private static void isTop(ChannelListBean result, String key, int top) {
        if (BaseTypeUtils.getElementFromMap(result.getChannelMap(), key) != null) {
            ((Channel) result.getChannelMap().get(key)).top = top;
        }
    }

    private String[] getChannelListBean(boolean isNavigation) {
        String jsonChannel = "";
        if (isNavigation) {
            jsonChannel = PreferencesManager.getInstance().getChannelNavigation();
        } else {
            jsonChannel = PreferencesManager.getInstance().getChannelWallMore();
        }
        if (TextUtils.isEmpty(jsonChannel)) {
            return null;
        }
        return jsonChannel.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
    }

    private void fetchDefaultChannelWall(Context context, ChannelListCallback callback) {
        new LetvRequest().setRequestType(RequestManner.CACHE_ONLY).setParser(new ChannelsParser()).setCache(new 3(this, context)).setCallback(new 2(this, callback)).add();
    }

    private String getJson(Context context) {
        Exception e;
        Throwable th;
        BufferedReader bufReader = null;
        try {
            BufferedReader bufReader2 = new BufferedReader(new InputStreamReader(context.getResources().getAssets().open("letv_channel_wall.json")));
            try {
                String line = "";
                String result = "";
                while (true) {
                    line = bufReader2.readLine();
                    if (line == null) {
                        break;
                    }
                    result = result + line;
                }
                String parseJson = parseJson(result);
                if (bufReader2 != null) {
                    try {
                        bufReader2.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                bufReader = bufReader2;
                return parseJson;
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

    private String parseJson(String jsonStr) {
        if (TextUtils.isEmpty(jsonStr)) {
            throw new NullPointerException("channel wall local data null");
        }
        String localMessage = (LetvUtils.getCountryCode() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + LetvUtils.getCountry()).toLowerCase();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            String jsonMessage = jsonObject.optString(localMessage);
            if (!TextUtils.isEmpty(jsonMessage)) {
                return jsonMessage;
            }
            jsonMessage = jsonObject.optString(("cn_" + LetvUtils.getCountry()).toLowerCase());
            if (TextUtils.isEmpty(jsonMessage)) {
                return jsonObject.optString("cn_cn");
            }
            return jsonMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void cancleRequest() {
        Volley.getQueue().cancelWithTag(this.TAG + "channel_wall_list");
    }

    public void destroy() {
        try {
            if (this.mChannelListBean != null) {
                this.mChannelListBean.getChannelMap().clear();
                this.mChannelListBean.listChannel.clear();
                this.mChannelListBean = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
