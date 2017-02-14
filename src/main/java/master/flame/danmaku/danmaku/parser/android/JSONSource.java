package master.flame.danmaku.danmaku.parser.android;

import android.net.Uri;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.util.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;

public class JSONSource implements IDataSource<JSONArray> {
    private InputStream mInput;
    private JSONArray mJSONArray;
    private String mJson;

    public JSONSource(String json) throws JSONException {
        init(json);
    }

    public JSONSource(InputStream in) throws JSONException {
        init(in);
    }

    private void init(InputStream in) throws JSONException {
        if (in == null) {
            throw new NullPointerException("input stream cannot be null!");
        }
        this.mInput = in;
        init(IOUtils.getString(this.mInput));
    }

    public JSONSource(URL url) throws JSONException, IOException {
        this(url.openStream());
    }

    public JSONSource(File file) throws FileNotFoundException, JSONException {
        init(new FileInputStream(file));
    }

    public JSONSource(Uri uri) throws IOException, JSONException {
        String scheme = uri.getScheme();
        if (IDataSource.SCHEME_HTTP_TAG.equalsIgnoreCase(scheme) || IDataSource.SCHEME_HTTPS_TAG.equalsIgnoreCase(scheme)) {
            HttpURLConnection conn = (HttpURLConnection) new URL(uri.toString()).openConnection();
            conn.setConnectTimeout(6000);
            conn.setReadTimeout(6000);
            init(conn.getInputStream());
        } else if (IDataSource.SCHEME_FILE_TAG.equalsIgnoreCase(scheme)) {
            init(new FileInputStream(uri.getPath()));
        }
    }

    public String getJsonStr() {
        return this.mJson;
    }

    private void init(String json) throws JSONException {
        if (!TextUtils.isEmpty(json)) {
            this.mJson = json;
        }
    }

    public JSONArray data() {
        return this.mJSONArray;
    }

    public void release() {
        IOUtils.closeQuietly(this.mInput);
        this.mInput = null;
        this.mJSONArray = null;
    }
}
