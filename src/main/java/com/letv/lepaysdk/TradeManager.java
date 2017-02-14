package com.letv.lepaysdk;

import com.letv.lepaysdk.model.TradeInfo;
import java.util.HashMap;
import java.util.Map;

public class TradeManager {
    private static TradeManager sInstance;
    private Map<String, TradeInfo> mTradeMap;

    private TradeManager() {
        if (this.mTradeMap == null) {
            this.mTradeMap = new HashMap();
        }
    }

    public static TradeManager getInstance() {
        if (sInstance == null) {
            sInstance = new TradeManager();
        }
        return sInstance;
    }

    public void addTradeInfo(TradeInfo info) {
        this.mTradeMap.put(info.getKey(), info);
    }

    public boolean checkTradeIsExist(TradeInfo info) {
        return this.mTradeMap.containsKey(info.getKey());
    }

    public void removeTradeInfo(TradeInfo info) {
        this.mTradeMap.remove(info.getKey());
    }

    public TradeInfo getTradeInfo(String key) {
        if (this.mTradeMap.containsKey(key)) {
            return (TradeInfo) this.mTradeMap.get(key);
        }
        return null;
    }

    public void destory() {
        this.mTradeMap.clear();
        this.mTradeMap = null;
        sInstance = null;
    }
}
