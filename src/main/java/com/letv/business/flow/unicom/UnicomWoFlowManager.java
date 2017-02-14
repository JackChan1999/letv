package com.letv.business.flow.unicom;

import android.content.Context;
import com.letv.android.wo.ex.IWoFlowManager;
import com.letv.android.wo.ex.WoInterface.LetvWoFlowListener;
import com.letv.component.player.http.parser.BaseParser.CODE_VALUES;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.IPBean;
import com.letv.core.bean.WoFlowBean;
import com.letv.core.bean.WoFlowBean.Data;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.IPParser;
import com.letv.core.parser.UnicomWoParser;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.loader.JarLoader;

public class UnicomWoFlowManager {
    public static final boolean DISABLE_WO = false;
    public static final String UNICOM_WO_OPENED = "1";
    public static UnicomWoFlowManager _instance;

    public static synchronized UnicomWoFlowManager getInstance() {
        UnicomWoFlowManager unicomWoFlowManager;
        synchronized (UnicomWoFlowManager.class) {
            if (_instance == null) {
                _instance = new UnicomWoFlowManager();
            }
            unicomWoFlowManager = _instance;
        }
        return unicomWoFlowManager;
    }

    public void checkIfProviceWoFlowOpened(final Context context, final LetvWoFlowListener listener, final boolean checkProvinceOnly) {
        new LetvRequest().setUrl("http://api.letv.com/getipgeo").setCache(new VolleyNoCache()).setRequestType(RequestManner.NETWORK_ONLY).setParser(new IPParser()).setCallback(new SimpleResponse<IPBean>() {
            public void onNetworkResponse(VolleyRequest<IPBean> volleyRequest, IPBean ip, DataHull hull, NetworkResponseState state) {
                if (NetworkResponseState.SUCCESS == state) {
                    LogInfo.log("unicom", "NetworkResponseState.SUCCESS == state，请求询问是否开启联通免流量");
                    BaseApplication.getInstance().setIp(ip);
                    UnicomWoFlowManager.this.requestWoFlowOpen(context, listener, checkProvinceOnly);
                    return;
                }
                LogInfo.log("unicom", "NetworkResponseState.SUCCESS != state");
                listener.onResponseOrderInfo(false, false, false, null, false);
            }
        }).add();
    }

    private void requestWoFlowOpen(final Context context, final LetvWoFlowListener listener, final boolean checkProvinceOnly) {
        String ip = BaseApplication.getInstance().getIp() == null ? "" : BaseApplication.getInstance().getIp().clientIp;
        LogInfo.log("unicom", "WoFlowOpen url = " + LetvUrlMaker.getWoFlowOpenUrl("unicom", ip));
        LogInfo.log("unicom", "WoFlowOpen context = " + context);
        LogInfo.log("unicom", "WoFlowOpen ip = " + ip);
        new LetvRequest().setUrl(LetvUrlMaker.getWoFlowOpenUrl("unicom", ip)).setMaxRetries(0).setCache(new VolleyNoCache()).setParser(new UnicomWoParser()).setCallback(new SimpleResponse<WoFlowBean>() {
            public void onNetworkResponse(VolleyRequest<WoFlowBean> volleyRequest, WoFlowBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("unicom", "检测网络返回值 state = " + state);
                if (state != NetworkResponseState.SUCCESS) {
                    listener.onResponseOrderInfo(false, false, false, null, false);
                    return;
                }
                LogInfo.log("unicom", "WoFlowOpen code = " + result.code);
                LogInfo.log("unicom", "检测网络返回值OK ");
                if (result.code.equals(CODE_VALUES.SUCCESS)) {
                    Data data = result.data;
                    LogInfo.log("unicom", "WoFlowOpen data is open = " + data.isopen);
                    if (data != null && data.isopen.equals("1")) {
                        LogInfo.log("unicom", "已经开通");
                        if (checkProvinceOnly) {
                            LogInfo.log("unicom", "checkProvinceOnly = true");
                            listener.onResponseOrderInfo(true, false, false, null, false);
                            return;
                        }
                        LogInfo.log("unicom", "checkProvinceOnly = false");
                        IWoFlowManager woFlowManager = (IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null);
                        LogInfo.log("unicom", "调用woFlowManager.isUserOrder");
                        woFlowManager.isUserOrder(context, listener, false, true);
                        return;
                    } else if (checkProvinceOnly) {
                        listener.onResponseOrderInfo(false, false, false, null, false);
                        return;
                    } else {
                        ((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(context, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).isUserOrder(context, listener, false, false);
                        return;
                    }
                }
                listener.onResponseOrderInfo(false, false, false, null, false);
            }
        }).add();
    }

    public void checkUnicomWoFreeFlow(Context context, LetvWoFlowListener listener) {
        if (listener != null) {
            LogInfo.log("unicom", "检测是否为联通3G");
            if (NetworkUtils.isUnicom3G(false)) {
                LogInfo.log("unicom", "是联通3G，检测是否开通了联通流量套餐");
                checkIfProviceWoFlowOpened(context, listener, false);
                return;
            }
            listener.onResponseOrderInfo(false, false, false, null, false);
        }
    }

    public boolean supportWoFree() {
        return !LetvUtils.isInHongKong();
    }
}
