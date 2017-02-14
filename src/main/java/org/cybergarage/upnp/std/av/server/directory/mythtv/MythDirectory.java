package org.cybergarage.upnp.std.av.server.directory.mythtv;

import org.cybergarage.upnp.std.av.server.Directory;
import org.cybergarage.upnp.std.av.server.object.item.mythtv.MythRecordedItemNode;
import org.cybergarage.util.Debug;

public class MythDirectory extends Directory {
    private static final String NAME = "MythTV";

    public MythDirectory(String name) {
        super(name);
    }

    public MythDirectory() {
        this(NAME);
    }

    private MythRecordedItemNode[] getAddedRecordedItemNodes() {
        int nContents = getNContentNodes();
        MythRecordedItemNode[] recNode = new MythRecordedItemNode[nContents];
        for (int n = 0; n < nContents; n++) {
            recNode[n] = (MythRecordedItemNode) getContentNode(n);
        }
        return recNode;
    }

    private MythRecordedInfo[] getCurrentRecordedInfos() {
        MythDatabase mythdb = new MythDatabase();
        mythdb.open();
        MythRecordedInfo[] recInfo = mythdb.getRecordedInfos();
        mythdb.close();
        return recInfo;
    }

    public boolean update() {
        boolean updateFlag = false;
        MythRecordedItemNode[] addedItemNode = getAddedRecordedItemNodes();
        MythRecordedInfo[] currRecInfo = getCurrentRecordedInfos();
        for (MythRecordedItemNode recItem : addedItemNode) {
            boolean hasRecItem = false;
            for (MythRecordedInfo recInfo : currRecInfo) {
                MythRecordedItemNode recItem2;
                if (recItem2.equals(recInfo)) {
                    hasRecItem = true;
                    break;
                }
            }
            if (!hasRecItem) {
                removeContentNode(recItem2);
                updateFlag = true;
            }
        }
        for (MythRecordedInfo recInfo2 : currRecInfo) {
            hasRecItem = false;
            for (MythRecordedItemNode recItem22 : addedItemNode) {
                if (recItem22.equals(recInfo2)) {
                    hasRecItem = true;
                    break;
                }
            }
            if (!hasRecItem) {
                recItem22 = new MythRecordedItemNode();
                recItem22.setID(getContentDirectory().getNextItemID());
                recItem22.setContentDirectory(getContentDirectory());
                recItem22.setRecordedInfo(recInfo2);
                addContentNode(recItem22);
                updateFlag = true;
            }
        }
        return updateFlag;
    }

    public static void main(String[] args) {
        Debug.on();
        new MythDirectory().update();
    }
}
