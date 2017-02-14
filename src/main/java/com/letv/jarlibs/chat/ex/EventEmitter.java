package com.letv.jarlibs.chat.ex;

import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class EventEmitter {
    HashList<EventCallback> callbacks = new HashList();

    interface OnceCallback extends EventCallback {
    }

    public abstract void disconnect();

    public abstract void emit(String str);

    public abstract void emit(String str, Acknowledge acknowledge);

    public abstract void emit(String str, JSONArray jSONArray);

    public abstract void emit(String str, JSONArray jSONArray, Acknowledge acknowledge);

    public abstract void emit(JSONObject jSONObject);

    public abstract void emit(JSONObject jSONObject, Acknowledge acknowledge);

    public abstract void emitEvent(String str);

    public abstract void emitEvent(String str, Acknowledge acknowledge);

    public abstract boolean isConnected();

    public abstract void of(String str, ConnectCallback connectCallback);

    public abstract void reconnect();

    public void onEvent(String event, JSONArray arguments, Acknowledge acknowledge) {
        List<EventCallback> list = this.callbacks.get(event);
        if (list != null) {
            Iterator<EventCallback> iter = list.iterator();
            while (iter.hasNext()) {
                EventCallback cb = (EventCallback) iter.next();
                cb.onEvent(arguments, acknowledge);
                if (cb instanceof OnceCallback) {
                    iter.remove();
                }
            }
        }
    }

    public void addListener(String event, EventCallback callback) {
        on(event, callback);
    }

    public void once(String event, final EventCallback callback) {
        on(event, new OnceCallback() {
            public void onEvent(JSONArray arguments, Acknowledge acknowledge) {
                callback.onEvent(arguments, acknowledge);
            }
        });
    }

    public void on(String event, EventCallback callback) {
        if (!this.callbacks.contains(event)) {
            this.callbacks.add(event, callback);
        }
    }

    public void removeListener(String event, EventCallback callback) {
        List<EventCallback> list = this.callbacks.get(event);
        if (list != null) {
            list.remove(callback);
        }
    }
}
