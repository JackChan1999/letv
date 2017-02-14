package com.letv.android.client.dlna.controller;

import android.content.Context;
import com.letv.android.client.commonlib.messagemodel.DLNAToPlayerProtocol;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;

public class LiveRoomDLNAController extends LiveDLNAController {
    public LiveRoomDLNAController(Context context) {
        super(context);
    }

    protected void initProtocol() {
        LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(new LeMessage(LeMessageIds.MSG_DLNA_LIVE_ROOM_PROTOCOL));
        if (LeResponseMessage.checkResponseMessageValidity(response, DLNAToPlayerProtocol.class)) {
            this.mToPlayerProtocol = (DLNAToPlayerProtocol) response.getData();
            return;
        }
        throw new NullPointerException("LiveRoomDlnaProtocol is Null");
    }
}
