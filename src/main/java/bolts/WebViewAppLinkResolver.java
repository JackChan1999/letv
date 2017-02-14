package bolts;

import android.content.Context;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import bolts.AppLink.Target;
import bolts.Task.TaskCompletionSource;
import com.letv.pp.utils.NetworkUtils;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebViewAppLinkResolver implements AppLinkResolver {
    private static final String KEY_AL_VALUE = "value";
    private static final String KEY_ANDROID = "android";
    private static final String KEY_APP_NAME = "app_name";
    private static final String KEY_CLASS = "class";
    private static final String KEY_PACKAGE = "package";
    private static final String KEY_SHOULD_FALLBACK = "should_fallback";
    private static final String KEY_URL = "url";
    private static final String KEY_WEB = "web";
    private static final String KEY_WEB_URL = "url";
    private static final String META_TAG_PREFIX = "al";
    private static final String PREFER_HEADER = "Prefer-Html-Meta-Tags";
    private static final String TAG_EXTRACTION_JAVASCRIPT = "javascript:boltsWebViewAppLinkResolverResult.setValue((function() {  var metaTags = document.getElementsByTagName('meta');  var results = [];  for (var i = 0; i < metaTags.length; i++) {    var property = metaTags[i].getAttribute('property');    if (property && property.substring(0, 'al:'.length) === 'al:') {      var tag = { \"property\": metaTags[i].getAttribute('property') };      if (metaTags[i].hasAttribute('content')) {        tag['content'] = metaTags[i].getAttribute('content');      }      results.push(tag);    }  }  return JSON.stringify(results);})())";
    private final Context context;

    public WebViewAppLinkResolver(Context context) {
        this.context = context;
    }

    public Task<AppLink> getAppLinkFromUrlInBackground(final Uri url) {
        final Capture<String> content = new Capture();
        final Capture<String> contentType = new Capture();
        return Task.callInBackground(new Callable<Void>() {
            public Void call() throws Exception {
                URL currentURL = new URL(url.toString());
                URLConnection connection = null;
                while (currentURL != null) {
                    connection = currentURL.openConnection();
                    if (connection instanceof HttpURLConnection) {
                        ((HttpURLConnection) connection).setInstanceFollowRedirects(true);
                    }
                    connection.setRequestProperty(WebViewAppLinkResolver.PREFER_HEADER, WebViewAppLinkResolver.META_TAG_PREFIX);
                    connection.connect();
                    if (connection instanceof HttpURLConnection) {
                        HttpURLConnection httpConnection = (HttpURLConnection) connection;
                        if (httpConnection.getResponseCode() < 300 || httpConnection.getResponseCode() >= 400) {
                            currentURL = null;
                        } else {
                            currentURL = new URL(httpConnection.getHeaderField(HttpRequest.HEADER_LOCATION));
                            httpConnection.disconnect();
                        }
                    } else {
                        currentURL = null;
                    }
                }
                try {
                    content.set(WebViewAppLinkResolver.readFromConnection(connection));
                    contentType.set(connection.getContentType());
                    return null;
                } finally {
                    if (connection instanceof HttpURLConnection) {
                        ((HttpURLConnection) connection).disconnect();
                    }
                }
            }
        }).onSuccessTask(new Continuation<Void, Task<JSONArray>>() {
            public Task<JSONArray> then(Task<Void> task) throws Exception {
                final TaskCompletionSource tcs = Task.create();
                WebView webView = new WebView(WebViewAppLinkResolver.this.context);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setNetworkAvailable(false);
                webView.setWebViewClient(new WebViewClient() {
                    private boolean loaded = false;

                    private void runJavaScript(WebView view) {
                        if (!this.loaded) {
                            this.loaded = true;
                            view.loadUrl(WebViewAppLinkResolver.TAG_EXTRACTION_JAVASCRIPT);
                        }
                    }

                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        runJavaScript(view);
                    }

                    public void onLoadResource(WebView view, String url) {
                        super.onLoadResource(view, url);
                        runJavaScript(view);
                    }
                });
                webView.addJavascriptInterface(new Object() {
                    @JavascriptInterface
                    public void setValue(String value) {
                        try {
                            tcs.trySetResult(new JSONArray(value));
                        } catch (JSONException e) {
                            tcs.trySetError(e);
                        }
                    }
                }, "boltsWebViewAppLinkResolverResult");
                String inferredContentType = null;
                if (contentType.get() != null) {
                    inferredContentType = ((String) contentType.get()).split(";")[0];
                }
                webView.loadDataWithBaseURL(url.toString(), (String) content.get(), inferredContentType, null, null);
                return tcs.getTask();
            }
        }, Task.UI_THREAD_EXECUTOR).onSuccess(new Continuation<JSONArray, AppLink>() {
            public AppLink then(Task<JSONArray> task) throws Exception {
                return WebViewAppLinkResolver.makeAppLinkFromAlData(WebViewAppLinkResolver.parseAlData((JSONArray) task.getResult()), url);
            }
        });
    }

    private static Map<String, Object> parseAlData(JSONArray dataArray) throws JSONException {
        HashMap<String, Object> al = new HashMap();
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject tag = dataArray.getJSONObject(i);
            String[] nameComponents = tag.getString("property").split(NetworkUtils.DELIMITER_COLON);
            if (nameComponents[0].equals(META_TAG_PREFIX)) {
                Map<String, Object> root = al;
                int j = 1;
                while (j < nameComponents.length) {
                    Map<String, Object> child;
                    List<Map<String, Object>> children = (List) root.get(nameComponents[j]);
                    if (children == null) {
                        children = new ArrayList();
                        root.put(nameComponents[j], children);
                    }
                    if (children.size() > 0) {
                        child = (Map) children.get(children.size() - 1);
                    } else {
                        child = null;
                    }
                    if (child == null || j == nameComponents.length - 1) {
                        child = new HashMap();
                        children.add(child);
                    }
                    root = child;
                    j++;
                }
                if (tag.has(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT)) {
                    if (tag.isNull(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT)) {
                        root.put("value", null);
                    } else {
                        root.put("value", tag.getString(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT));
                    }
                }
            }
        }
        return al;
    }

    private static List<Map<String, Object>> getAlList(Map<String, Object> map, String key) {
        List<Map<String, Object>> result = (List) map.get(key);
        if (result == null) {
            return Collections.emptyList();
        }
        return result;
    }

    private static AppLink makeAppLinkFromAlData(Map<String, Object> appLinkDict, Uri destination) {
        List<Target> targets = new ArrayList();
        List<Map<String, Object>> platformMapList = (List) appLinkDict.get("android");
        if (platformMapList == null) {
            platformMapList = Collections.emptyList();
        }
        for (Map<String, Object> platformMap : platformMapList) {
            List<Map<String, Object>> urls = getAlList(platformMap, "url");
            List<Map<String, Object>> packages = getAlList(platformMap, KEY_PACKAGE);
            List<Map<String, Object>> classes = getAlList(platformMap, KEY_CLASS);
            List<Map<String, Object>> appNames = getAlList(platformMap, "app_name");
            int maxCount = Math.max(urls.size(), Math.max(packages.size(), Math.max(classes.size(), appNames.size())));
            int i = 0;
            while (i < maxCount) {
                targets.add(new Target((String) (packages.size() > i ? ((Map) packages.get(i)).get("value") : null), (String) (classes.size() > i ? ((Map) classes.get(i)).get("value") : null), tryCreateUrl((String) (urls.size() > i ? ((Map) urls.get(i)).get("value") : null)), (String) (appNames.size() > i ? ((Map) appNames.get(i)).get("value") : null)));
                i++;
            }
        }
        Uri webUrl = destination;
        List<Map<String, Object>> webMapList = (List) appLinkDict.get(KEY_WEB);
        if (webMapList != null && webMapList.size() > 0) {
            Map<String, Object> webMap = (Map) webMapList.get(0);
            urls = (List) webMap.get("url");
            List<Map<String, Object>> shouldFallbacks = (List) webMap.get(KEY_SHOULD_FALLBACK);
            if (shouldFallbacks != null && shouldFallbacks.size() > 0) {
                if (Arrays.asList(new String[]{"no", "false", "0"}).contains(((String) ((Map) shouldFallbacks.get(0)).get("value")).toLowerCase())) {
                    webUrl = null;
                }
            }
            if (!(webUrl == null || urls == null || urls.size() <= 0)) {
                webUrl = tryCreateUrl((String) ((Map) urls.get(0)).get("value"));
            }
        }
        return new AppLink(destination, targets, webUrl);
    }

    private static Uri tryCreateUrl(String urlString) {
        if (urlString == null) {
            return null;
        }
        return Uri.parse(urlString);
    }

    private static String readFromConnection(URLConnection connection) throws IOException {
        InputStream stream;
        if (connection instanceof HttpURLConnection) {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            try {
                stream = connection.getInputStream();
            } catch (Exception e) {
                stream = httpConnection.getErrorStream();
            }
        } else {
            stream = connection.getInputStream();
        }
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            while (true) {
                int read = stream.read(buffer);
                if (read == -1) {
                    break;
                }
                output.write(buffer, 0, read);
            }
            String charset = connection.getContentEncoding();
            if (charset == null) {
                for (String part : connection.getContentType().split(";")) {
                    String part2 = part2.trim();
                    if (part2.startsWith("charset=")) {
                        charset = part2.substring("charset=".length());
                        break;
                    }
                }
                if (charset == null) {
                    charset = "UTF-8";
                }
            }
            String str = new String(output.toByteArray(), charset);
            return str;
        } finally {
            stream.close();
        }
    }
}
