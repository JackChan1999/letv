package com.letv.lemallsdk.api.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class RequestParams {
    private static String ENCODING = "UTF-8";
    protected ConcurrentHashMap<String, FileWrapper> fileParams;
    protected ConcurrentHashMap<String, String> urlParams;
    protected ConcurrentHashMap<String, ArrayList<String>> urlParamsWithArray;

    private static class FileWrapper {
        public String contentType;
        public String fileName;
        public InputStream inputStream;

        public FileWrapper(InputStream inputStream, String fileName, String contentType) {
            this.inputStream = inputStream;
            this.fileName = fileName;
            this.contentType = contentType;
        }

        public String getFileName() {
            if (this.fileName != null) {
                return this.fileName;
            }
            return "nofilename";
        }
    }

    public RequestParams() {
        init();
    }

    public RequestParams(Map<String, String> source) {
        init();
        for (Entry<String, String> entry : source.entrySet()) {
            put((String) entry.getKey(), (String) entry.getValue());
        }
    }

    public RequestParams(String key, String value) {
        init();
        put(key, value);
    }

    public RequestParams(Object... keysAndValues) {
        init();
        int len = keysAndValues.length;
        if (len % 2 != 0) {
            throw new IllegalArgumentException("Supplied arguments must be even");
        }
        for (int i = 0; i < len; i += 2) {
            put(String.valueOf(keysAndValues[i]), String.valueOf(keysAndValues[i + 1]));
        }
    }

    public void put(String key, String value) {
        if (key != null && value != null) {
            this.urlParams.put(key, value);
        }
    }

    public void put(String key, File file) throws FileNotFoundException {
        put(key, new FileInputStream(file), file.getName());
    }

    public void put(String key, ArrayList<String> values) {
        if (key != null && values != null) {
            this.urlParamsWithArray.put(key, values);
        }
    }

    public void put(String key, InputStream stream) {
        put(key, stream, null);
    }

    public void put(String key, InputStream stream, String fileName) {
        put(key, stream, fileName, null);
    }

    public void put(String key, InputStream stream, String fileName, String contentType) {
        if (key != null && stream != null) {
            this.fileParams.put(key, new FileWrapper(stream, fileName, contentType));
        }
    }

    public void remove(String key) {
        this.urlParams.remove(key);
        this.fileParams.remove(key);
        this.urlParamsWithArray.remove(key);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Entry<String, String> entry : this.urlParams.entrySet()) {
            if (result.length() > 0) {
                result.append("&");
            }
            result.append((String) entry.getKey());
            result.append(SearchCriteria.EQ);
            result.append((String) entry.getValue());
        }
        for (Entry<String, FileWrapper> entry2 : this.fileParams.entrySet()) {
            if (result.length() > 0) {
                result.append("&");
            }
            result.append((String) entry2.getKey());
            result.append(SearchCriteria.EQ);
            result.append("FILE");
        }
        for (Entry<String, ArrayList<String>> entry3 : this.urlParamsWithArray.entrySet()) {
            if (result.length() > 0) {
                result.append("&");
            }
            ArrayList<String> values = (ArrayList) entry3.getValue();
            Iterator it = values.iterator();
            while (it.hasNext()) {
                String value = (String) it.next();
                if (values.indexOf(value) != 0) {
                    result.append("&");
                }
                result.append((String) entry3.getKey());
                result.append(SearchCriteria.EQ);
                result.append(value);
            }
        }
        return result.toString();
    }

    public HttpEntity getEntity() {
        if (this.fileParams.isEmpty()) {
            try {
                return new UrlEncodedFormEntity(getParamsList(), ENCODING);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }
        Iterator it;
        SimpleMultipartEntity multipartEntity = new SimpleMultipartEntity();
        for (Entry<String, String> entry : this.urlParams.entrySet()) {
            multipartEntity.addPart((String) entry.getKey(), (String) entry.getValue());
        }
        for (Entry<String, ArrayList<String>> entry2 : this.urlParamsWithArray.entrySet()) {
            it = ((ArrayList) entry2.getValue()).iterator();
            while (it.hasNext()) {
                multipartEntity.addPart((String) entry2.getKey(), (String) it.next());
            }
        }
        int currentIndex = 0;
        int lastIndex = this.fileParams.entrySet().size() - 1;
        for (Entry<String, FileWrapper> entry3 : this.fileParams.entrySet()) {
            FileWrapper file = (FileWrapper) entry3.getValue();
            if (file.inputStream != null) {
                boolean isLast = currentIndex == lastIndex;
                if (file.contentType != null) {
                    multipartEntity.addPart((String) entry3.getKey(), file.getFileName(), file.inputStream, file.contentType, isLast);
                } else {
                    multipartEntity.addPart((String) entry3.getKey(), file.getFileName(), file.inputStream, isLast);
                }
            }
            currentIndex++;
        }
        return multipartEntity;
    }

    private void init() {
        this.urlParams = new ConcurrentHashMap();
        this.fileParams = new ConcurrentHashMap();
        this.urlParamsWithArray = new ConcurrentHashMap();
    }

    protected List<BasicNameValuePair> getParamsList() {
        Iterator it;
        List<BasicNameValuePair> lparams = new LinkedList();
        for (Entry<String, String> entry : this.urlParams.entrySet()) {
            lparams.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
        }
        for (Entry<String, ArrayList<String>> entry2 : this.urlParamsWithArray.entrySet()) {
            it = ((ArrayList) entry2.getValue()).iterator();
            while (it.hasNext()) {
                lparams.add(new BasicNameValuePair((String) entry2.getKey(), (String) it.next()));
            }
        }
        return lparams;
    }

    protected String getParamString() {
        return URLEncodedUtils.format(getParamsList(), ENCODING);
    }
}
