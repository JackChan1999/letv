package com.letv.lemallsdk.api.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.util.EntityUtils;

public class AsyncHttpResponseHandler {
    protected static final int FAILURE_MESSAGE = 1;
    protected static final int FINISH_MESSAGE = 3;
    protected static final int PROGRESS_MESSAGE = 0;
    protected static final int START_MESSAGE = 2;
    protected static final int SUCCESS_MESSAGE = 4;
    private Handler handler;

    public AsyncHttpResponseHandler() {
        if (Looper.myLooper() != null) {
            this.handler = new Handler() {
                public void handleMessage(Message msg) {
                    AsyncHttpResponseHandler.this.handleMessage(msg);
                }
            };
        }
    }

    public void onStart() {
    }

    public void onFinish() {
    }

    public void onSuccess(String content) {
    }

    public void onProgress(long totalSize, long currentSize, long speed) {
    }

    public void onSuccess(int statusCode, Header[] headers, String content) {
        onSuccess(statusCode, content);
    }

    public void onSuccess(int statusCode, String content) {
        onSuccess(content);
    }

    public void onFailure(Throwable error) {
    }

    public void onFailure(Throwable error, String content) {
        onFailure(error);
    }

    protected void sendSuccessMessage(int statusCode, Header[] headers, String responseBody) {
        sendMessage(obtainMessage(4, new Object[]{new Integer(statusCode), headers, responseBody}));
    }

    protected void sendFailureMessage(Throwable e, String responseBody) {
        sendMessage(obtainMessage(1, new Object[]{e, responseBody}));
    }

    protected void sendFailureMessage(Throwable e, byte[] responseBody) {
        sendMessage(obtainMessage(1, new Object[]{e, responseBody}));
    }

    protected void sendStartMessage() {
        sendMessage(obtainMessage(2, null));
    }

    protected void sendFinishMessage() {
        sendMessage(obtainMessage(3, null));
    }

    protected void handleSuccessMessage(int statusCode, Header[] headers, String responseBody) {
        onSuccess(statusCode, headers, responseBody);
    }

    protected void handleFailureMessage(Throwable e, String responseBody) {
        onFailure(e, responseBody);
    }

    protected void handleProgressMessage(long totalSize, long currentSize, long speed) {
        onProgress(totalSize, currentSize, speed);
    }

    protected void handleMessage(Message msg) {
        Object[] response;
        switch (msg.what) {
            case 0:
                response = msg.obj;
                handleProgressMessage(((Long) response[0]).longValue(), ((Long) response[1]).longValue(), ((Long) response[2]).longValue());
                return;
            case 1:
                response = (Object[]) msg.obj;
                handleFailureMessage((Throwable) response[0], (String) response[1]);
                return;
            case 2:
                onStart();
                return;
            case 3:
                onFinish();
                return;
            case 4:
                response = (Object[]) msg.obj;
                handleSuccessMessage(((Integer) response[0]).intValue(), (Header[]) response[1], (String) response[2]);
                return;
            default:
                return;
        }
    }

    protected void sendMessage(Message msg) {
        if (this.handler != null) {
            this.handler.sendMessage(msg);
        } else {
            handleMessage(msg);
        }
    }

    protected Message obtainMessage(int responseMessage, Object response) {
        if (this.handler != null) {
            return this.handler.obtainMessage(responseMessage, response);
        }
        Message msg = Message.obtain();
        msg.what = responseMessage;
        msg.obj = response;
        return msg;
    }

    protected void sendResponseMessage(HttpResponse response) {
        Throwable e;
        StatusLine status = response.getStatusLine();
        String responseBody = null;
        try {
            HttpEntity temp = response.getEntity();
            if (temp != null) {
                HttpEntity entity = new BufferedHttpEntity(temp);
                try {
                    responseBody = EntityUtils.toString(entity, "UTF-8");
                } catch (IOException e2) {
                    e = e2;
                    HttpEntity httpEntity = entity;
                    sendFailureMessage(e, null);
                    if (status.getStatusCode() >= 300) {
                        sendSuccessMessage(status.getStatusCode(), response.getAllHeaders(), responseBody);
                    } else {
                        sendFailureMessage(new HttpResponseException(status.getStatusCode(), status.getReasonPhrase()), responseBody);
                    }
                }
            }
        } catch (IOException e3) {
            e = e3;
            sendFailureMessage(e, null);
            if (status.getStatusCode() >= 300) {
                sendFailureMessage(new HttpResponseException(status.getStatusCode(), status.getReasonPhrase()), responseBody);
            } else {
                sendSuccessMessage(status.getStatusCode(), response.getAllHeaders(), responseBody);
            }
        }
        if (status.getStatusCode() >= 300) {
            sendFailureMessage(new HttpResponseException(status.getStatusCode(), status.getReasonPhrase()), responseBody);
        } else {
            sendSuccessMessage(status.getStatusCode(), response.getAllHeaders(), responseBody);
        }
    }
}
