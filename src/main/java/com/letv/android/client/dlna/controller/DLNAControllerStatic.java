package com.letv.android.client.dlna.controller;

import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.messagebus.task.LeMessageTask;
import com.letv.core.messagebus.task.LeMessageTask.TaskRunnable;

public class DLNAControllerStatic {
    static {
        LeMessageManager.getInstance().registerTask(new LeMessageTask(400, new TaskRunnable() {
            public LeResponseMessage run(LeMessage message) {
                if (message == null || message.getContext() == null) {
                    return null;
                }
                return new LeResponseMessage(400, new AlbumDLNAController(message.getContext()));
            }
        }));
        LeMessageManager.getInstance().registerTask(new LeMessageTask(401, new TaskRunnable() {
            public LeResponseMessage run(LeMessage message) {
                if (message == null || message.getContext() == null) {
                    return null;
                }
                return new LeResponseMessage(401, new LiveDLNAController(message.getContext()));
            }
        }));
        LeMessageManager.getInstance().registerTask(new LeMessageTask(402, new TaskRunnable() {
            public LeResponseMessage run(LeMessage message) {
                if (message == null || message.getContext() == null) {
                    return null;
                }
                return new LeResponseMessage(402, new LiveRoomDLNAController(message.getContext()));
            }
        }));
    }
}
