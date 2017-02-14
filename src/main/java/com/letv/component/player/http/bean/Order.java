package com.letv.component.player.http.bean;

import java.util.ArrayList;

public class Order extends ArrayList<Tag> {

    public static class Tag {
        private Long cmdId;
        private String cmdParam;
        private int cmdType;
        private String cmdUrl;
        private int upType;

        public Long getCmdId() {
            return this.cmdId;
        }

        public void setCmdId(Long cmdId) {
            this.cmdId = cmdId;
        }

        public int getUpType() {
            return this.upType;
        }

        public void setUpType(int upType) {
            this.upType = upType;
        }

        public int getCmdType() {
            return this.cmdType;
        }

        public void setCmdType(int cmdType) {
            this.cmdType = cmdType;
        }

        public String getCmdUrl() {
            return this.cmdUrl;
        }

        public void setCmdUrl(String cmdUrl) {
            this.cmdUrl = cmdUrl;
        }

        public String getCmdParam() {
            return this.cmdParam;
        }

        public void setCmdParam(String cmdParam) {
            this.cmdParam = cmdParam;
        }
    }
}
