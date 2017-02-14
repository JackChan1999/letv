package com.letv.android.client.worldcup.util;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.letv.android.client.worldcup.dao.PreferencesManager;

public final class LetvServiceConfiguration {
    final Context context;
    final int maxDownloadCount;
    final int maxThreadCount;

    public static class Builder {
        public static final int DEFAULT_DOWNLOAD_SIZE = 1;
        public static final String DEFAULT_HTTP_URL = "http://dynamic.app.m.letv.com/android/dynamic.php";
        private String content_provider_uri;
        private Context context;
        private String download_path;
        private String download_path_default;
        private boolean loggingEnabled = false;
        private int maxDownloadCount = 1;
        private int maxThreadCount = 2;
        private String pcode;
        private String version;

        public Builder(Context context, String pcode, String version) {
            this.context = context.getApplicationContext();
            this.pcode = pcode;
            this.version = version;
            PreferencesManager.getInstance().setPcode(context, this.pcode);
            PreferencesManager.getInstance().setVersion(context, this.version);
        }

        public Builder threadSize(int threadSize) {
            this.maxThreadCount = threadSize;
            return this;
        }

        public Builder downloadMaxSize(int downloadSize) {
            this.maxDownloadCount = downloadSize;
            return this;
        }

        public Builder enableLogging(boolean enable) {
            this.loggingEnabled = enable;
            return this;
        }

        public Builder contentProviderUri(String uri) {
            Constants.debug("============contentProviderUri uri" + uri);
            this.content_provider_uri = uri;
            PreferencesManager.getInstance().setContentProviderUri(this.context, uri);
            return this;
        }

        public Builder downloadPath(String path, String defaultPath) {
            this.download_path = new StringBuilder(String.valueOf(path)).append("/").append("worldcup").toString();
            this.download_path_default = new StringBuilder(String.valueOf(defaultPath)).append("/").append("worldcup").toString();
            PreferencesManager.getInstance().setDownloadPath(this.context, this.download_path, this.download_path_default);
            return this;
        }

        public LetvServiceConfiguration build() {
            return new LetvServiceConfiguration();
        }
    }

    private LetvServiceConfiguration(Builder builder) {
        Constants.debug("------------LetvServiceConfiguration pcode" + builder.pcode);
        Constants.debug("------------LetvServiceConfiguration version" + builder.version);
        this.context = builder.context;
        this.maxThreadCount = builder.maxThreadCount;
        this.maxDownloadCount = builder.maxDownloadCount;
        Constants.setDebug(builder.loggingEnabled);
        Constants.DOWNLOAD_JOB_NUM_LIMIT = this.maxDownloadCount;
        Constants.DOWNLOAD_JOB_THREAD_LIMIT = this.maxThreadCount;
    }

    public static String getPcode(Context context) {
        return PreferencesManager.getInstance().getPcode(context);
    }

    public static String getVersion(Context context) {
        return PreferencesManager.getInstance().getVersion(context);
    }

    public static Uri getContent_provider_uri(Context context) {
        String uri = PreferencesManager.getInstance().getContentProviderUri(context);
        if (TextUtils.isEmpty(uri)) {
            return null;
        }
        return Uri.parse(uri);
    }

    public static String getDownload_path(Context context) {
        return PreferencesManager.getInstance().getDownloadPath(context);
    }

    public static String getDownload_path_default(Context context) {
        return PreferencesManager.getInstance().getDownloadDefaultPath(context);
    }

    public static LetvServiceConfiguration createDefault(Context context, String pcode, String version) {
        return new Builder(context, pcode, version).build();
    }
}
