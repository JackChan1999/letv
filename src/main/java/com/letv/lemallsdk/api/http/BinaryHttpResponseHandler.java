package com.letv.lemallsdk.api.http;

import android.os.Message;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.IOException;
import java.util.regex.Pattern;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.util.EntityUtils;

public class BinaryHttpResponseHandler extends AsyncHttpResponseHandler {
    private static String[] mAllowedContentTypes = new String[]{"image/jpeg", "image/png"};

    public BinaryHttpResponseHandler(String[] allowedContentTypes) {
        this();
        mAllowedContentTypes = allowedContentTypes;
    }

    public void onSuccess(byte[] binaryData) {
    }

    public void onSuccess(int statusCode, byte[] binaryData) {
        onSuccess(binaryData);
    }

    @Deprecated
    public void onFailure(Throwable error, byte[] binaryData) {
        onFailure(error);
    }

    protected void sendSuccessMessage(int statusCode, byte[] responseBody) {
        sendMessage(obtainMessage(4, new Object[]{Integer.valueOf(statusCode), responseBody}));
    }

    protected void sendFailureMessage(Throwable e, byte[] responseBody) {
        sendMessage(obtainMessage(1, new Object[]{e, responseBody}));
    }

    protected void handleSuccessMessage(int statusCode, byte[] responseBody) {
        onSuccess(statusCode, responseBody);
    }

    protected void handleFailureMessage(Throwable e, byte[] responseBody) {
        onFailure(e, responseBody);
    }

    protected void handleMessage(Message msg) {
        Object[] response;
        switch (msg.what) {
            case 1:
                response = (Object[]) msg.obj;
                handleFailureMessage((Throwable) response[0], response[1].toString());
                return;
            case 4:
                response = msg.obj;
                handleSuccessMessage(((Integer) response[0]).intValue(), (byte[]) response[1]);
                return;
            default:
                super.handleMessage(msg);
                return;
        }
    }

    protected void sendResponseMessage(HttpResponse response) {
        int i = 0;
        StatusLine status = response.getStatusLine();
        Header[] contentTypeHeaders = response.getHeaders(HttpRequest.HEADER_CONTENT_TYPE);
        byte[] responseBody = null;
        if (contentTypeHeaders.length != 1) {
            sendFailureMessage(new HttpResponseException(status.getStatusCode(), "None, or more than one, Content-Type Header found!"), null);
            return;
        }
        Header contentTypeHeader = contentTypeHeaders[0];
        boolean foundAllowedContentType = false;
        String[] strArr = mAllowedContentTypes;
        int length = strArr.length;
        while (i < length) {
            if (Pattern.matches(strArr[i], contentTypeHeader.getValue())) {
                foundAllowedContentType = true;
            }
            i++;
        }
        if (foundAllowedContentType) {
            HttpEntity entity = null;
            try {
                HttpEntity temp = response.getEntity();
                if (temp != null) {
                    entity = new BufferedHttpEntity(temp);
                }
                responseBody = EntityUtils.toByteArray(entity);
            } catch (IOException e) {
                sendFailureMessage(e, null);
            }
            if (status.getStatusCode() >= 300) {
                sendFailureMessage(new HttpResponseException(status.getStatusCode(), status.getReasonPhrase()), responseBody);
                return;
            } else {
                sendSuccessMessage(status.getStatusCode(), responseBody);
                return;
            }
        }
        sendFailureMessage(new HttpResponseException(status.getStatusCode(), "Content-Type not allowed!"), null);
    }
}
