package com.letv.redpacketsdk.bean;

public class PollingResult {
    public boolean hasRedPacket;
    public int rollRate = -1;
    public int rollSwitch = -1;

    public String toString() {
        return "PollingResult: rollSwitch=" + this.rollSwitch + "\n rollRate=" + this.rollRate + "\n hasRedPacket=" + this.hasRedPacket;
    }
}
