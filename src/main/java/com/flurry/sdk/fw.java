package com.flurry.sdk;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class fw {
    private static final String a = fw.class.getSimpleName();

    public Map<String, List<String>> a(String str) {
        ib.a(3, a, "Parsing referrer map");
        if (str == null) {
            return Collections.emptyMap();
        }
        Map<String, List<String>> hashMap = new HashMap();
        String[] split = str.split("&");
        int length = split.length;
        for (String str2 : split) {
            String str22;
            String[] split2 = str22.split(SearchCriteria.EQ);
            if (split2.length != 2) {
                ib.a(5, a, "Invalid referrer Element: " + str22 + " in referrer tag " + str);
            } else {
                str22 = URLDecoder.decode(split2[0]);
                String decode = URLDecoder.decode(split2[1]);
                if (hashMap.get(str22) == null) {
                    hashMap.put(str22, new ArrayList());
                }
                ((List) hashMap.get(str22)).add(decode);
            }
        }
        for (Entry entry : hashMap.entrySet()) {
            ib.a(3, a, "entry: " + ((String) entry.getKey()) + SearchCriteria.EQ + entry.getValue());
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (hashMap.get("utm_source") == null) {
            stringBuilder.append("Campaign Source is missing.\n");
        }
        if (hashMap.get("utm_medium") == null) {
            stringBuilder.append("Campaign Medium is missing.\n");
        }
        if (hashMap.get("utm_campaign") == null) {
            stringBuilder.append("Campaign Name is missing.\n");
        }
        if (stringBuilder.length() > 0) {
            ib.a(5, a, "Detected missing referrer keys : " + stringBuilder.toString());
        }
        return hashMap;
    }
}
