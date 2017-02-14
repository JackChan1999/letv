package com.soundink.lib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.letv.core.constant.LiveRoomConstant;
import com.letv.pp.utils.NetworkUtils;
import com.soundink.lib.c.a;
import com.soundink.lib.c.b;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

final class g {
    protected static Map<String, SoundInkAgent> a = Collections.synchronizedMap(new LinkedHashMap(10, 1.5f, true));
    private static final g c = new g();
    private static Map<String, String> d = new LinkedHashMap();
    private static Map<String, String> e = new LinkedHashMap();
    private static ExecutorService f;
    private static Context g;
    private static long h;
    protected final Handler b = new 1(this);
    private boolean i = true;
    private final Handler j = new 2(this);

    private g() {
    }

    public static g a() {
        return c;
    }

    protected static void a(Context context) {
        f = Executors.newFixedThreadPool(5);
        a aVar = new a(context.getApplicationContext());
        d dVar = new d(a.a().toString());
        g = context;
        e.a(context);
        e.d();
    }

    protected static void b() {
        try {
            a.clear();
            d.clear();
            e.clear();
            e.d();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    static /* synthetic */ void a(g gVar, Message message) {
        Bundle data = message.getData();
        int i = data.getInt("kukid");
        int i2 = data.getInt("kukid_type");
        if (i == -1 && i2 == -1) {
            gVar.a(e.i(), "", d);
            return;
        }
        StringBuilder append = new StringBuilder(String.valueOf(i)).append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        String str = (i2 == 1 || i2 == 3) ? "HARD" : i2 == 2 ? "SOFT" : "HARD";
        String e = e.e(append.append(str).append("_kukida619").toString());
        Object obj = (e.b(g) || a.containsKey(e)) ? null : 1;
        if (obj != null) {
            gVar.a(e.g(), e, e);
            if (SoundInkInterface.getReceiveSignalAtNoNetWork()) {
                e.a(e, e.j());
                obj = (a.containsKey(e) && (((SoundInkAgent) a.get(e)).getStatus() == 1000 || ((SoundInkAgent) a.get(e)).getStatus() == 1002)) ? 1 : null;
                if (obj == null) {
                    e.c(e);
                    return;
                }
                return;
            }
            return;
        }
        gVar.a(e);
    }

    protected final void a(String str) {
        SoundInkAgent soundInkAgent;
        if (!a.containsKey(str)) {
            a.put(str, e.e());
            if (e.b(g)) {
                f.submit(new h(g, str, this.j));
            }
        } else if (((SoundInkAgent) a.get(str)).getStatus() != 1000) {
            if (((SoundInkAgent) a.get(str)).getCallTimes() < 3) {
                ((SoundInkAgent) a.get(str)).setCallTimes(((SoundInkAgent) a.get(str)).getCallTimes() + 1);
            } else {
                if (e.b(g)) {
                    f.submit(new h(g, str, this.j));
                }
                soundInkAgent = (SoundInkAgent) a.get(str);
                soundInkAgent.setdelayTimes(0);
                a(soundInkAgent, str);
            }
        }
        soundInkAgent = (SoundInkAgent) a.get(str);
        soundInkAgent.setdelayTimes(0);
        a(soundInkAgent, str);
    }

    private void a(SoundInkAgent soundInkAgent, String str) {
        if (soundInkAgent.getStatus() == 1000) {
            a(soundInkAgent, str, d);
        } else if (soundInkAgent.getStatus() == 1002) {
            a(e.h(), "", d);
        } else if (soundInkAgent.getStatus() == LiveRoomConstant.LIVE_ROOM_LOADER_BOOK_ID) {
            a(e.g(), "", d);
        } else {
            a(e.i(), "", d);
        }
    }

    private void a(SoundInkAgent soundInkAgent, String str, Map<String, String> map) {
        if (this.i) {
            Intent intent;
            if (g != null && com.soundink.lib.a.a.a) {
                intent = new Intent();
                intent.putExtra(SoundInkInterface.SOUNDINK_LOG_AGENT, soundInkAgent);
                intent.setAction(SoundInkInterface.ACTION_SEND_SOUNDINK_LOG_SIGNAL);
                intent.addCategory(g.getApplicationContext().getPackageName());
                g.sendBroadcast(intent);
                Log.d(SoundInkInterface.getLogTag(), "interactive_index: " + String.valueOf(soundInkAgent.getInteractiveIndex()) + "\nmessage:" + String.valueOf(soundInkAgent.getMessage()));
                if (soundInkAgent != null) {
                    String str2 = System.currentTimeMillis() + NetworkUtils.DELIMITER_LINE + e.a(new StringBuilder(String.valueOf(System.currentTimeMillis())).toString(), "MM-dd HH:mm:ss") + ", message: " + soundInkAgent.getMessage() + "\n";
                    if (!(soundInkAgent.getInteractiveIndex() == null || "".equals(soundInkAgent.getInteractiveIndex()))) {
                        str2 = new StringBuilder(String.valueOf(str2)).append(soundInkAgent.getInteractiveIndex()).append("\n\n").toString();
                    }
                    b.a(str2, "SoundInkSdkDataLog.txt");
                }
            }
            if (SoundInkInterface.getSingleReceiveModel() && SoundInkInterface.getSingleReceiveInterval() != 0) {
                if (soundInkAgent.getStatus() == 2000) {
                    soundInkAgent = e.i();
                } else {
                    if (map.size() > 0 && ((String) map.get("last_agent_key")).equals(str)) {
                        Object obj = (!SoundInkInterface.getIsSetDuplicateSignalInterval() || System.currentTimeMillis() - h <= ((long) SoundInkInterface.getSingleReceiveInterval())) ? null : 1;
                        if (obj == null) {
                            soundInkAgent = e.i();
                        }
                    }
                    if (!(str.equals("") || str == null)) {
                        map.put("last_agent_key", str);
                        h = System.currentTimeMillis();
                    }
                }
            }
            if (soundInkAgent.getStatus() == 1000) {
                if (g != null) {
                    intent = new Intent();
                    intent.putExtra(SoundInkInterface.SOUNDINK_AGENT, soundInkAgent);
                    intent.setAction(SoundInkInterface.ACTION_SEND_SOUNDINK_SIGNAL);
                    intent.addCategory(g.getApplicationContext().getPackageName());
                    g.sendBroadcast(intent);
                }
            } else if (g != null) {
                intent = new Intent();
                intent.putExtra(SoundInkInterface.SOUNDINK_AGENT, soundInkAgent);
                intent.setAction(SoundInkInterface.ACTION_SEND_SOUNDINK_INVALID_SIGNAL);
                intent.addCategory(g.getApplicationContext().getPackageName());
                g.sendBroadcast(intent);
            }
        }
    }
}
