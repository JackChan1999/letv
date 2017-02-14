package com.letv.business.flow.live;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.WatchAndBuyAddResultBean;
import com.letv.core.bean.WatchAndBuyAttionBean;
import com.letv.core.bean.WatchAndBuyGetNumResultBean;
import com.letv.core.bean.WatchAndBuyGoodsBean;
import com.letv.core.bean.WatchAndBuyGoodsListBean;
import com.letv.core.constant.PlayConstant;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.parser.WatchAndBuyAddResultParser;
import com.letv.core.parser.WatchAndBuyAttionParser;
import com.letv.core.parser.WatchAndBuyGetNumResultParser;
import com.letv.core.parser.WatchAndBuyGoodsListParser;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.RxBus;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

public class LiveWatchAndBuyFlow {
    private static final int DURATION = 60000;
    private static final int MSG_REQUEST = 1;
    public static String TAG = "watchAndBuy";
    private LiveWatchAndBuyFlowCallback callback;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogInfo.log(LiveWatchAndBuyFlow.TAG, "receiver a message : MSG_REQUEST");
            if (msg.what == 1) {
                LiveWatchAndBuyFlow.this.requestForGoodsByStreamId(msg.getData().getString(PlayConstant.LIVE_WATCHANDBUY_PARTID));
            }
        }
    };
    private Message mMessage;

    public static class AddGoodsToCartResponseEvent {
        public String status;

        public AddGoodsToCartResponseEvent(String status) {
            this.status = status;
        }
    }

    public interface AddToCartResultStatus {
        public static final String CARTFULL = "300";
        public static final String FAIL = "-1";
        public static final String GOODSFULL = "302";
        public static final String NO_NETWORK = "-2";
        public static final String SUCCESS = "1";
    }

    public static class GetAttionNumResponseEvent {
        public int count;

        public GetAttionNumResponseEvent(int count) {
            this.count = count;
        }
    }

    public static class GetCartNumResponseEvent {
        public WatchAndBuyGetNumResultBean result;

        public GetCartNumResponseEvent(WatchAndBuyGetNumResultBean result) {
            this.result = result;
        }
    }

    public static class GetGoodsResponseEvent {
        public WatchAndBuyGoodsListBean result;

        public GetGoodsResponseEvent(WatchAndBuyGoodsListBean result) {
            this.result = result;
        }
    }

    private static class Network {
        String urlString;
        String urlString2;

        public interface NetWorkCallback {
            void onError();

            void onResponse(WatchAndBuyGoodsListBean watchAndBuyGoodsListBean);
        }

        private Network() {
            this.urlString = "http://dynamic.api.letv.com/block/%s.json?platform=mobile";
            this.urlString2 = "http://www.letv.com/cmsdata/block/%s.json?platform=mobile";
        }

        private void request(final String partId, final NetWorkCallback callback) {
            new Thread() {
                public void run() {
                    String str;
                    MalformedURLException e;
                    Throwable th;
                    IOException e2;
                    super.run();
                    LogInfo.log(LiveWatchAndBuyFlow.TAG, "Network request");
                    Network.this.urlString = String.format(Network.this.urlString, new Object[]{partId});
                    HttpURLConnection connection = null;
                    try {
                        connection = (HttpURLConnection) new URL(Network.this.urlString).openConnection();
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                        connection.connect();
                        if (connection.getResponseCode() == 200) {
                            String date = Network.this.getDate(connection.getHeaderFields());
                            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
                            ByteArrayOutputStream os = new ByteArrayOutputStream();
                            byte[] buffer = new byte[4096];
                            while (true) {
                                int length = bis.read(buffer);
                                if (length == -1) {
                                    break;
                                }
                                os.write(buffer, 0, length);
                            }
                            String result = new String(os.toByteArray(), "utf-8");
                            try {
                                System.out.print(result);
                                os.close();
                                bis.close();
                                WatchAndBuyGoodsListBean listBean = Network.this.getResult(result, date);
                                if (callback != null) {
                                    callback.onResponse(listBean);
                                }
                                str = result;
                            } catch (MalformedURLException e3) {
                                e = e3;
                                str = result;
                                try {
                                    if (callback != null) {
                                        callback.onError();
                                    }
                                    e.printStackTrace();
                                    if (connection != null) {
                                        connection.disconnect();
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    if (connection != null) {
                                        connection.disconnect();
                                    }
                                    throw th;
                                }
                            } catch (IOException e4) {
                                e2 = e4;
                                str = result;
                                if (callback != null) {
                                    callback.onError();
                                }
                                e2.printStackTrace();
                                if (connection != null) {
                                    connection.disconnect();
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                str = result;
                                if (connection != null) {
                                    connection.disconnect();
                                }
                                throw th;
                            }
                        }
                        Network.this.request2(partId, callback);
                        if (connection != null) {
                            connection.disconnect();
                        }
                    } catch (MalformedURLException e5) {
                        e = e5;
                        if (callback != null) {
                            callback.onError();
                        }
                        e.printStackTrace();
                        if (connection != null) {
                            connection.disconnect();
                        }
                    } catch (IOException e6) {
                        e2 = e6;
                        if (callback != null) {
                            callback.onError();
                        }
                        e2.printStackTrace();
                        if (connection != null) {
                            connection.disconnect();
                        }
                    }
                }
            }.start();
        }

        private void request2(String partId, NetWorkCallback callback) {
            String str;
            MalformedURLException e;
            Throwable th;
            IOException e2;
            LogInfo.log(LiveWatchAndBuyFlow.TAG, "Network request2");
            this.urlString2 = String.format(this.urlString2, new Object[]{partId});
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) new URL(this.urlString2).openConnection();
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                connection.connect();
                if (connection.getResponseCode() == 200) {
                    String date = getDate(connection.getHeaderFields());
                    BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    while (true) {
                        int length = bis.read(buffer);
                        if (length == -1) {
                            break;
                        }
                        os.write(buffer, 0, length);
                    }
                    String result = new String(os.toByteArray(), "utf-8");
                    try {
                        os.close();
                        bis.close();
                        WatchAndBuyGoodsListBean listBean = getResult(result, date);
                        if (callback != null) {
                            callback.onResponse(listBean);
                        }
                        str = result;
                    } catch (MalformedURLException e3) {
                        e = e3;
                        str = result;
                        if (callback != null) {
                            try {
                                callback.onError();
                            } catch (Throwable th2) {
                                th = th2;
                                if (connection != null) {
                                    connection.disconnect();
                                }
                                throw th;
                            }
                        }
                        e.printStackTrace();
                        if (connection != null) {
                            connection.disconnect();
                        }
                    } catch (IOException e4) {
                        e2 = e4;
                        str = result;
                        if (callback != null) {
                            callback.onError();
                        }
                        e2.printStackTrace();
                        if (connection != null) {
                            connection.disconnect();
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        str = result;
                        if (connection != null) {
                            connection.disconnect();
                        }
                        throw th;
                    }
                } else if (callback != null) {
                    callback.onError();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (MalformedURLException e5) {
                e = e5;
                if (callback != null) {
                    callback.onError();
                }
                e.printStackTrace();
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e6) {
                e2 = e6;
                if (callback != null) {
                    callback.onError();
                }
                e2.printStackTrace();
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        private String getDate(Map<String, List<String>> header) {
            String time = null;
            List<String> dateList = (List) header.get("Date");
            if (dateList != null && dateList.size() > 0) {
                time = (String) dateList.get(0);
            }
            if (time != null) {
                SimpleDateFormat inFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
                SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    inFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                    time = outFormat.format(inFormat.parse(time));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return time;
        }

        private WatchAndBuyGoodsListBean getResult(String json, String date) {
            LogInfo.log(LiveWatchAndBuyFlow.TAG, "getResult : " + json);
            if (TextUtils.isEmpty(json)) {
                return null;
            }
            WatchAndBuyGoodsListBean listBean = null;
            try {
                listBean = new WatchAndBuyGoodsListParser().getData(new JSONObject(json));
                listBean.time = date;
                return listBean;
            } catch (JSONException e) {
                e.printStackTrace();
                return listBean;
            } catch (Exception e2) {
                e2.printStackTrace();
                return listBean;
            }
        }
    }

    public void setCallback(LiveWatchAndBuyFlowCallback callback) {
        this.callback = callback;
    }

    public void requestForGoodsByStreamId(final String partId) {
        if (!TextUtils.isEmpty(partId)) {
            LogInfo.log(TAG, "requestForGoodsByStreamId : " + partId);
            this.mHandler.removeMessages(1);
            new Network().request(partId, new NetWorkCallback() {
                public void onResponse(final WatchAndBuyGoodsListBean result) {
                    if (result != null) {
                        LogInfo.log(LiveWatchAndBuyFlow.TAG, "requestForGoodsByStreamId canStartTime : " + result.startTime);
                        if (result.startTime) {
                            Bundle bundle = new Bundle();
                            bundle.putString(PlayConstant.LIVE_WATCHANDBUY_PARTID, partId);
                            LiveWatchAndBuyFlow.this.mMessage = LiveWatchAndBuyFlow.this.mHandler.obtainMessage();
                            LiveWatchAndBuyFlow.this.mMessage.what = 1;
                            LiveWatchAndBuyFlow.this.mMessage.setData(bundle);
                            LiveWatchAndBuyFlow.this.mHandler.sendMessageDelayed(LiveWatchAndBuyFlow.this.mMessage, 60000);
                        }
                        RxBus.getInstance().send(new GetGoodsResponseEvent(result));
                        LiveWatchAndBuyFlow.this.mHandler.post(new Runnable() {
                            public void run() {
                                if (LiveWatchAndBuyFlow.this.callback != null) {
                                    LiveWatchAndBuyFlow.this.callback.onGetGoodsResponse(null, result);
                                }
                            }
                        });
                    }
                }

                public void onError() {
                    Bundle bundle = new Bundle();
                    bundle.putString(PlayConstant.LIVE_WATCHANDBUY_PARTID, partId);
                    LiveWatchAndBuyFlow.this.mMessage = LiveWatchAndBuyFlow.this.mHandler.obtainMessage();
                    LiveWatchAndBuyFlow.this.mMessage.what = 1;
                    LiveWatchAndBuyFlow.this.mMessage.setData(bundle);
                    LiveWatchAndBuyFlow.this.mHandler.sendMessageDelayed(LiveWatchAndBuyFlow.this.mMessage, 60000);
                }
            });
        }
    }

    public void addGoodsToCart(WatchAndBuyGoodsBean goodsBean) {
        if (goodsBean != null) {
            String url = LetvUrlMaker.getWatchAndBuyAddToCartUrl(goodsBean.type, goodsBean.id, goodsBean.streamId, false);
            LogInfo.log("pjf", "addGoodsToCart url : " + url);
            new LetvRequest().setUrl(url).setTag(TAG).setParser(new WatchAndBuyAddResultParser()).setRequestType(RequestManner.NETWORK_ONLY).setCallback(new SimpleResponse<WatchAndBuyAddResultBean>() {
                public void onNetworkResponse(VolleyRequest<WatchAndBuyAddResultBean> request, final WatchAndBuyAddResultBean result, DataHull hull, NetworkResponseState state) {
                    super.onNetworkResponse(request, result, hull, state);
                    LogInfo.log("pjf", "addGoodsToCart response");
                    if (state == NetworkResponseState.SUCCESS) {
                        LogInfo.log("pjf", "addGoodsToCart success");
                        RxBus.getInstance().send(new AddGoodsToCartResponseEvent(result.status));
                        LiveWatchAndBuyFlow.this.mHandler.post(new Runnable() {
                            public void run() {
                                if (LiveWatchAndBuyFlow.this.callback != null) {
                                    LiveWatchAndBuyFlow.this.callback.onAddToCartResponse(result.status);
                                }
                            }
                        });
                    } else if (state == NetworkResponseState.NETWORK_ERROR || state == NetworkResponseState.RESULT_ERROR) {
                        RxBus.getInstance().send(new AddGoodsToCartResponseEvent("-1"));
                        LiveWatchAndBuyFlow.this.mHandler.post(new Runnable() {
                            public void run() {
                                if (LiveWatchAndBuyFlow.this.callback != null) {
                                    LiveWatchAndBuyFlow.this.callback.onAddToCartResponse("-1");
                                }
                            }
                        });
                        LogInfo.log("pjf", "addGoodsToCart failed");
                    } else if (state == NetworkResponseState.NETWORK_NOT_AVAILABLE) {
                        RxBus.getInstance().send(new AddGoodsToCartResponseEvent(AddToCartResultStatus.NO_NETWORK));
                        LiveWatchAndBuyFlow.this.mHandler.post(new Runnable() {
                            public void run() {
                                if (LiveWatchAndBuyFlow.this.callback != null) {
                                    LiveWatchAndBuyFlow.this.callback.onAddToCartResponse(AddToCartResultStatus.NO_NETWORK);
                                }
                            }
                        });
                    }
                }
            }).add();
        }
    }

    public void getCartNum() {
        String url = LetvUrlMaker.getWatchAndBuyCartNumUrl();
        LogInfo.log("pjf", "getCartNum url : " + url);
        new LetvRequest().setUrl(url).setTag(TAG).setParser(new WatchAndBuyGetNumResultParser()).setRequestType(RequestManner.NETWORK_ONLY).setCallback(new SimpleResponse<WatchAndBuyGetNumResultBean>() {
            public void onNetworkResponse(VolleyRequest<WatchAndBuyGetNumResultBean> request, final WatchAndBuyGetNumResultBean result, DataHull hull, NetworkResponseState state) {
                super.onNetworkResponse(request, result, hull, state);
                LogInfo.log("pjf", "getCartNum response");
                switch (state) {
                    case SUCCESS:
                        RxBus.getInstance().send(new GetCartNumResponseEvent(result));
                        if (LiveWatchAndBuyFlow.this.callback != null) {
                            LiveWatchAndBuyFlow.this.mHandler.post(new Runnable() {
                                public void run() {
                                    LiveWatchAndBuyFlow.this.callback.onGetCartNumResponse(result);
                                }
                            });
                            LogInfo.log("pjf", "getCartNum num : " + result.count);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }).add();
    }

    public void getAttionNum(String streamId, String startTime, String duration) {
        String url = LetvUrlMaker.getWatchAndBuyAttionNumUrl(streamId, startTime, duration);
        LogInfo.log("pjf", "getAttionNum url : " + url);
        new LetvRequest().setUrl(url).setTag(TAG).setParser(new WatchAndBuyAttionParser()).setCallback(new SimpleResponse<WatchAndBuyAttionBean>() {
            public void onNetworkResponse(VolleyRequest<WatchAndBuyAttionBean> request, final WatchAndBuyAttionBean result, DataHull hull, NetworkResponseState state) {
                super.onNetworkResponse(request, result, hull, state);
                LogInfo.log("pjf", "getAttionNum response");
                switch (state) {
                    case SUCCESS:
                        RxBus.getInstance().send(new GetAttionNumResponseEvent(result.count));
                        if (LiveWatchAndBuyFlow.this.callback != null) {
                            LiveWatchAndBuyFlow.this.mHandler.post(new Runnable() {
                                public void run() {
                                    LiveWatchAndBuyFlow.this.callback.onGetAttentionCount(result.count);
                                }
                            });
                            LogInfo.log("pjf", "getAttionNum num : " + result.count);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }).add();
    }

    public void destroy() {
        if (this.mHandler != null) {
            this.mHandler.removeMessages(1);
        }
    }
}
