package com.nostra13.universalimageloader.core.download;

import com.sina.weibo.sdk.component.WidgetRequestParam;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import master.flame.danmaku.danmaku.parser.IDataSource;

public interface ImageDownloader {

    public enum Scheme {
        HTTP(IDataSource.SCHEME_HTTP_TAG),
        HTTPS(IDataSource.SCHEME_HTTPS_TAG),
        FILE(IDataSource.SCHEME_FILE_TAG),
        CONTENT(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT),
        ASSETS("assets"),
        DRAWABLE("drawable"),
        UNKNOWN("");
        
        private String scheme;
        private String uriPrefix;

        private Scheme(String scheme) {
            this.scheme = scheme;
            this.uriPrefix = scheme + "://";
        }

        public static Scheme ofUri(String uri) {
            if (uri != null) {
                for (Scheme s : values()) {
                    if (s.belongsTo(uri)) {
                        return s;
                    }
                }
            }
            return UNKNOWN;
        }

        private boolean belongsTo(String uri) {
            return uri.toLowerCase(Locale.US).startsWith(this.uriPrefix);
        }

        public String wrap(String path) {
            return this.uriPrefix + path;
        }

        public String crop(String uri) {
            if (belongsTo(uri)) {
                return uri.substring(this.uriPrefix.length());
            }
            throw new IllegalArgumentException(String.format("URI [%1$s] doesn't have expected scheme [%2$s]", new Object[]{uri, this.scheme}));
        }
    }

    InputStream getStream(String str, Object obj) throws IOException;
}
