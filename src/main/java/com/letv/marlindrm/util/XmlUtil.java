package com.letv.marlindrm.util;

import android.text.TextUtils;
import android.util.Xml;
import com.letv.core.utils.LogInfo;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;

public class XmlUtil {
    private static String TAG = "drmTest";

    public static String readContentByTag(String xmlContent, String tag) {
        String content = null;
        if (!(TextUtils.isEmpty(xmlContent) || TextUtils.isEmpty(tag))) {
            InputStream inStream = new ByteArrayInputStream(xmlContent.getBytes());
            XmlPullParser parser = Xml.newPullParser();
            try {
                boolean isNeedEnd = false;
                for (int eventType = parser.getEventType(); eventType != 1 && !isNeedEnd; eventType = parser.next()) {
                    switch (eventType) {
                        case 2:
                            if (!tag.equalsIgnoreCase(parser.getName())) {
                                break;
                            }
                            content = parser.nextText();
                            isNeedEnd = true;
                            break;
                        default:
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogInfo.log(TAG, "xml parser exception....");
            }
            try {
                inStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return content;
    }
}
