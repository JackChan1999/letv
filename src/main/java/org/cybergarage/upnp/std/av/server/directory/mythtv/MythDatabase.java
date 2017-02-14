package org.cybergarage.upnp.std.av.server.directory.mythtv;

import com.sina.weibo.sdk.component.ShareRequestParam;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import java.util.Vector;
import org.cybergarage.sql.mysql.MySQL;

public class MythDatabase extends MySQL {
    private static final String DB_NAME = "mythconverg";
    private static final String DB_PASSWD = "mythtv";
    private static final String DB_USER = "mythtv";

    public boolean open(String host) {
        return super.open(host, DB_NAME, "mythtv", "mythtv");
    }

    public boolean open() {
        return open("localhost");
    }

    public String getRecordFilePrefix() {
        if (!query("select * from settings where value = 'RecordFilePrefix'")) {
            return "";
        }
        if (fetch()) {
            return getString(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA);
        }
        return "";
    }

    public MythRecordedInfo[] getRecordedInfos() {
        Vector recVec = new Vector();
        String recFilePrefix = getRecordFilePrefix();
        if (!query("select * from recorded")) {
            return new MythRecordedInfo[0];
        }
        while (fetch()) {
            MythRecordedInfo recInfo = new MythRecordedInfo();
            recInfo.setRecordFilePrefix(recFilePrefix);
            recInfo.setChanID(getInteger("chanid"));
            recInfo.setRecordID(getInteger("recordid"));
            recInfo.setStartTime(getTimestamp("starttime"));
            recInfo.setEndTime(getTimestamp("endtime"));
            recInfo.setTitle(getString("title"));
            recInfo.setSubTitle(getString("subtitle"));
            recInfo.setDescription(getString("description"));
            recInfo.setCategory(getString(WidgetRequestParam.REQ_PARAM_COMMENT_CATEGORY));
            recInfo.setFileName(getString("basename"));
            recInfo.setFileSize(recInfo.getFile().length());
            recVec.add(recInfo);
        }
        int recCnt = recVec.size();
        MythRecordedInfo[] recArray = new MythRecordedInfo[recCnt];
        for (int n = 0; n < recCnt; n++) {
            recArray[n] = (MythRecordedInfo) recVec.get(n);
        }
        return recArray;
    }
}
