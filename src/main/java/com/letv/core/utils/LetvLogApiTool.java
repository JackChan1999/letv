package com.letv.core.utils;

import android.os.Environment;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LetvLogApiTool {
    private static LetvLogApiTool mLetvLogTool = null;
    public final String EXCEPTIONINFO_NAME = "exceptionInfo.log";
    private final String PATH = (Environment.getExternalStorageDirectory() + "/letv/exceptionInfo/");
    private final int POOL_SIZE = 3;
    private int cpuNums = Runtime.getRuntime().availableProcessors();
    private File dir = new File(this.PATH);
    private ExecutorService executor = Executors.newFixedThreadPool(this.cpuNums * 3);
    private File file = null;
    private double timeOutValue = 1.5d;

    public static synchronized LetvLogApiTool getInstance() {
        LetvLogApiTool letvLogApiTool;
        synchronized (LetvLogApiTool.class) {
            if (mLetvLogTool == null) {
                mLetvLogTool = new LetvLogApiTool();
            }
            letvLogApiTool = mLetvLogTool;
        }
        return letvLogApiTool;
    }

    public double getTimeOutValue() {
        return this.timeOutValue;
    }

    public void setTimeOutValue(double timeOutValue) {
        this.timeOutValue = timeOutValue;
    }

    private static boolean sdCardMounted() {
        try {
            String state = Environment.getExternalStorageState();
            if (!state.equals("mounted") || state.equals("mounted_ro")) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static String getTimeStamp() {
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatTime.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return formatTime.format(new Date(System.currentTimeMillis()));
    }

    public static void createExceptionInfo(int errorType, String errorDes, String requestUrl, String responseData) {
        getInstance().saveExceptionInfo(getTimeStamp() + " errorType = " + errorType + "  requestUrl =" + requestUrl + errorDes + "\n\r  responseData=" + responseData);
    }

    public static void createPlayLogInfo(String logType, String vid, String logDesc) {
        getInstance().saveExceptionInfo(logType + " time=" + getTimeStamp() + " vid=" + vid + " description=" + logDesc);
    }

    public File getExceptionFile() {
        return new File(this.dir, "exceptionInfo.log");
    }

    public void saveExceptionInfo(String data) {
        if (sdCardMounted()) {
            if (!this.dir.exists()) {
                this.dir.mkdirs();
            }
            this.file = new File(this.dir, "exceptionInfo.log");
            if (this.file.exists() && this.file.length() > 2097152) {
                this.file.delete();
            }
            try {
                this.file.createNewFile();
                this.executor.execute(new Handler(this, data));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
