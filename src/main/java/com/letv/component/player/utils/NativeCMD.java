package com.letv.component.player.utils;

import com.facebook.internal.NativeProtocol;
import java.util.HashMap;
import java.util.Map;

public class NativeCMD {
    public static Map<String, Object> runCmd(String cmd) {
        Map<String, Object> map = new HashMap();
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            map.put("input", process.getInputStream());
            map.put(NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE, process.getErrorStream());
            return map;
        } catch (Exception e) {
            return null;
        }
    }
}
