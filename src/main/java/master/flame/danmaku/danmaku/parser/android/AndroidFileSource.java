package master.flame.danmaku.danmaku.parser.android;

import android.net.Uri;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.util.IOUtils;

public class AndroidFileSource implements IDataSource<InputStream> {
    private InputStream inStream;

    public AndroidFileSource(String filepath) {
        fillStreamFromFile(new File(filepath));
    }

    public AndroidFileSource(Uri uri) {
        fillStreamFromUri(uri);
    }

    public AndroidFileSource(File file) {
        fillStreamFromFile(file);
    }

    public AndroidFileSource(InputStream stream) {
        this.inStream = stream;
    }

    public void fillStreamFromFile(File file) {
        try {
            this.inStream = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void fillStreamFromUri(Uri uri) {
        String scheme = uri.getScheme();
        if (IDataSource.SCHEME_HTTP_TAG.equalsIgnoreCase(scheme) || IDataSource.SCHEME_HTTPS_TAG.equalsIgnoreCase(scheme)) {
            fillStreamFromHttpFile(uri);
        } else if (IDataSource.SCHEME_FILE_TAG.equalsIgnoreCase(scheme)) {
            fillStreamFromFile(new File(uri.getPath()));
        }
    }

    public void fillStreamFromHttpFile(Uri uri) {
        try {
            URL url = new URL(uri.getPath());
            url.openConnection();
            this.inStream = new BufferedInputStream(url.openStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void release() {
        IOUtils.closeQuietly(this.inStream);
        this.inStream = null;
    }

    public InputStream data() {
        return this.inStream;
    }
}
