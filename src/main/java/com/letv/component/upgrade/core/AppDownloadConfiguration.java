package com.letv.component.upgrade.core;

import android.content.Context;
import android.os.Environment;
import java.io.File;

public final class AppDownloadConfiguration {
    public final DBSaveManage addFinishTaskToDB;
    public final DBSaveManage addStartTaskToDB;
    public final DataCallbackCategory callbackCategory;
    final Context context;
    public final String currentAppName;
    public final String downloadLocation;
    public final DownloadServiceManage downloadServiceType;
    public final DownloadStateManage downloadState;
    public final int downloadTaskNum;
    public final int downloadTaskThreadNum;
    public final String intentRemoteService;
    public final String notifyIntentAction;
    public final int sdcardSize;

    public static class ConfigurationBuild {
        public static final int DEFAULT_DEFAULT_SDCARD_SIZE = 52428800;
        public static final int DEFAULT_DOWNLOAD_RESOURCE_THREAD_COUNT = 1;
        public static final int DEFAULT_DOWNLOAD_TASK_NUM = 1;
        public static final String DEFAULT_FILE_NAME = "letv_app_client.apk";
        public static final String DEFAULT_INTENT_REMOTE_SERVICE = "com.letv.appupgrade.SERVICE";
        public static final String DEFAULT_NOTIFY_INTENT_ACTION = "com.letv.android.client.app.download.list";
        public static final String DEFAULT_PATH_DOWNLOAD = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getPath())).append(File.separator).append("letvapp_download/storage/download").toString();
        private DataCallbackCategory callbackCategory;
        private Context context;
        public String currentAppName;
        public DownloadServiceManage downloadServiceType;
        public DownloadStateManage downloadState;
        private int downloadTaskNum;
        private int downloadTaskThreadNum;
        private String intentRemoteServiceAction;
        public DBSaveManage isOnFinishAddTaskToDB;
        public DBSaveManage isOnStartAddTaskToDB;
        private int limitSdcardSize;
        private String notifyIntentAction;
        private String pathDownload;

        public ConfigurationBuild(Context context) {
            this.context = context.getApplicationContext();
        }

        public ConfigurationBuild downloadTaskNum(int downloadNum) {
            if (downloadNum < 1) {
                this.downloadTaskNum = 1;
            } else {
                this.downloadTaskNum = downloadNum;
            }
            return this;
        }

        public ConfigurationBuild downloadTaskThreadNum(int downloadThreadNum) {
            this.downloadTaskThreadNum = downloadThreadNum;
            return this;
        }

        public ConfigurationBuild notifyIntentAction(String action) {
            if (action == null || "".equalsIgnoreCase(action.trim())) {
                this.notifyIntentAction = DEFAULT_NOTIFY_INTENT_ACTION;
            } else {
                this.notifyIntentAction = action;
            }
            return this;
        }

        public ConfigurationBuild limitSdcardSize(int sdcardSize) {
            this.limitSdcardSize = sdcardSize;
            return this;
        }

        public ConfigurationBuild pathDownload(String path) {
            this.pathDownload = path;
            return this;
        }

        public ConfigurationBuild intentRemoteServiceAction(String remoteServiceAction) {
            this.intentRemoteServiceAction = remoteServiceAction;
            return this;
        }

        public ConfigurationBuild setCallbackCategoty(DataCallbackCategory category) {
            this.callbackCategory = category;
            return this;
        }

        public ConfigurationBuild downloadServiceType(DownloadServiceManage loadService) {
            this.downloadServiceType = loadService;
            return this;
        }

        public ConfigurationBuild downloadStateType(DownloadStateManage state) {
            this.downloadState = state;
            return this;
        }

        public ConfigurationBuild isOnStartAddTaskToDB(DBSaveManage addToDB) {
            this.isOnStartAddTaskToDB = addToDB;
            return this;
        }

        public ConfigurationBuild isOnFinishAddTaskToDB(DBSaveManage addToDB) {
            this.isOnFinishAddTaskToDB = addToDB;
            return this;
        }

        public ConfigurationBuild setCurrentAppName(String apkName) {
            this.currentAppName = apkName;
            return this;
        }

        public AppDownloadConfiguration build() {
            initEmptyFiledsWithDefaultValues();
            return new AppDownloadConfiguration();
        }

        private void initEmptyFiledsWithDefaultValues() {
            if (this.downloadTaskNum < 1) {
                this.downloadTaskNum = 1;
            }
            if (this.downloadTaskThreadNum < 1) {
                this.downloadTaskThreadNum = 1;
            }
            if (this.notifyIntentAction == null || "".equalsIgnoreCase(this.notifyIntentAction.trim())) {
                this.notifyIntentAction = DEFAULT_NOTIFY_INTENT_ACTION;
            }
            if (this.limitSdcardSize < DEFAULT_DEFAULT_SDCARD_SIZE) {
                this.limitSdcardSize = DEFAULT_DEFAULT_SDCARD_SIZE;
            }
            if (this.pathDownload == null || "".equalsIgnoreCase(this.pathDownload.trim())) {
                if (Environment.getExternalStorageState().equals("mounted")) {
                    this.pathDownload = DEFAULT_PATH_DOWNLOAD + File.separator + this.context.getPackageName();
                } else {
                    this.pathDownload = this.context.getDir("updata", 3).getPath();
                }
            } else if (Environment.getExternalStorageState().equals("mounted")) {
                this.pathDownload += File.separator + this.context.getPackageName();
            }
            if (this.intentRemoteServiceAction == null || "".equalsIgnoreCase(this.intentRemoteServiceAction.trim())) {
                this.intentRemoteServiceAction = "com.letv.appupgrade.SERVICE";
            }
            if (this.callbackCategory == null) {
                this.callbackCategory = DataCallbackCategory.BROADCAST;
            }
            if (this.downloadServiceType == null) {
                this.downloadServiceType = DownloadServiceManage.LOCALSERVICE;
            }
            if (this.downloadState == null) {
                this.downloadState = DownloadStateManage.REMOTE_STOP;
            }
            if (this.isOnStartAddTaskToDB == null) {
                this.isOnStartAddTaskToDB = DBSaveManage.START_ADD_TO_DB;
            }
            if (this.isOnFinishAddTaskToDB == null) {
                this.isOnFinishAddTaskToDB = DBSaveManage.FINISH_ADD_TO_DB;
            }
            if (this.currentAppName == null || "".equalsIgnoreCase(this.currentAppName)) {
                this.currentAppName = DEFAULT_FILE_NAME;
            }
        }
    }

    public enum DBSaveManage {
        START_ADD_TO_DB("start_add"),
        START_NOT_ADD_TO_DB("start_not_add"),
        FINISH_ADD_TO_DB("finish_add"),
        FINISH_NOT_ADD_TO_DB("finish_not_add");
        
        private String addToDb;

        private DBSaveManage(String type) {
            this.addToDb = type;
        }

        public String toString() {
            return this.addToDb;
        }

        public static DBSaveManage fromString(String type) {
            DBSaveManage[] states = values();
            for (int i = 0; i < states.length; i++) {
                if (states[i].addToDb.equalsIgnoreCase(type)) {
                    return states[i];
                }
            }
            return null;
        }
    }

    public enum DataCallbackCategory {
        BROADCAST("broadcast"),
        LISTENER("listener");
        
        private String callbackCategory;

        private DataCallbackCategory(String category) {
            this.callbackCategory = category;
        }

        public String toString() {
            return this.callbackCategory;
        }

        public DataCallbackCategory fromString(String category) {
            DataCallbackCategory[] states = values();
            for (int i = 0; i < states.length; i++) {
                if (states[i].callbackCategory.equalsIgnoreCase(category)) {
                    return states[i];
                }
            }
            return null;
        }
    }

    public enum DownloadServiceManage {
        REMOTESERVICE,
        LOCALSERVICE
    }

    private AppDownloadConfiguration(ConfigurationBuild build) {
        this.context = build.context;
        this.downloadTaskNum = build.downloadTaskNum;
        this.downloadTaskThreadNum = build.downloadTaskThreadNum;
        this.sdcardSize = build.limitSdcardSize;
        this.notifyIntentAction = build.notifyIntentAction;
        this.intentRemoteService = build.intentRemoteServiceAction;
        this.callbackCategory = build.callbackCategory;
        this.downloadServiceType = build.downloadServiceType;
        this.downloadLocation = build.pathDownload;
        this.downloadState = build.downloadState;
        this.addStartTaskToDB = build.isOnStartAddTaskToDB;
        this.addFinishTaskToDB = build.isOnFinishAddTaskToDB;
        this.currentAppName = build.currentAppName;
    }
}
