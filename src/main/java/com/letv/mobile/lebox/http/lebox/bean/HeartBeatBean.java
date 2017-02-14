package com.letv.mobile.lebox.http.lebox.bean;

import com.letv.mobile.http.model.LetvHttpBaseModel;

public class HeartBeatBean implements LetvHttpBaseModel {
    private WanDeviceStatusBean deviceState;
    private WanOtaStatusBean otaState;
    private TaskState taskState;

    public WanDeviceStatusBean getDeviceState() {
        return this.deviceState;
    }

    public void setDeviceState(WanDeviceStatusBean deviceState) {
        this.deviceState = deviceState;
    }

    public WanOtaStatusBean getOtaState() {
        return this.otaState;
    }

    public void setOtaState(WanOtaStatusBean otaState) {
        this.otaState = otaState;
    }

    public TaskState getTaskState() {
        return this.taskState;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    public String getDeviceMode() {
        WanDeviceStatusBean statusBean = getDeviceState();
        if (statusBean == null) {
            return null;
        }
        return statusBean.getMode();
    }

    public String getSsid() {
        WanDeviceStatusBean statusBean = getDeviceState();
        if (statusBean == null) {
            return null;
        }
        return statusBean.getSsid();
    }

    public boolean getHasInternet() {
        WanDeviceStatusBean statusBean = getDeviceState();
        if (statusBean == null) {
            return false;
        }
        return Boolean.valueOf(statusBean.getHasInternet()).booleanValue();
    }

    public String getUpdateState() {
        WanOtaStatusBean otaBean = getOtaState();
        if (otaBean == null) {
            return null;
        }
        return otaBean.getUpdateState();
    }

    public TaskPro getTaskPro() {
        TaskState taskState = getTaskState();
        if (taskState == null) {
            return null;
        }
        return taskState.getTaskPro();
    }

    public String getDownloadingTaskVid() {
        TaskPro taskPro = getTaskPro();
        if (taskPro == null) {
            return null;
        }
        return taskPro.getVid();
    }

    public String getDownloadingTaskPr() {
        TaskPro taskPro = getTaskPro();
        if (taskPro == null) {
            return null;
        }
        return taskPro.getPr();
    }

    public String getDownloadingTaskProgress() {
        TaskPro taskPro = getTaskPro();
        if (taskPro == null) {
            return null;
        }
        return taskPro.getProgress();
    }

    public TaskVer getTaskVer() {
        TaskState taskState = getTaskState();
        if (taskState == null) {
            return null;
        }
        return taskState.getTaskVer();
    }

    public String getCompletedVersion() {
        TaskVer taskVer = getTaskVer();
        if (taskVer == null) {
            return null;
        }
        return taskVer.getCompletedVer();
    }

    public String getUnFinishedVersion() {
        TaskVer taskVer = getTaskVer();
        if (taskVer == null) {
            return null;
        }
        return taskVer.getUnFinishedVer();
    }

    public String toString() {
        return "HeartBeatBean [deviceState=" + this.deviceState + ", otaState=" + this.otaState + ", taskState=" + this.taskState + "]";
    }
}
