package com.letv.lemallsdk.api.http;

import java.io.IOException;
import java.net.ConnectException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

public class AsyncHttpRequest implements Runnable {
    private final AbstractHttpClient client;
    private final HttpContext context;
    private int executionCount;
    private boolean isBinaryRequest;
    private final HttpUriRequest request;
    private final AsyncHttpResponseHandler responseHandler;

    public AsyncHttpRequest(AbstractHttpClient client, HttpContext context, HttpUriRequest request, AsyncHttpResponseHandler responseHandler) {
        this.client = client;
        this.context = context;
        this.request = request;
        this.responseHandler = responseHandler;
        if (responseHandler instanceof BinaryHttpResponseHandler) {
            this.isBinaryRequest = true;
        }
    }

    public void run() {
        try {
            if (this.responseHandler != null) {
                this.responseHandler.sendStartMessage();
            }
            makeRequestWithRetries();
            if (this.responseHandler != null) {
                this.responseHandler.sendFinishMessage();
            }
        } catch (Throwable e) {
            if (this.responseHandler != null) {
                this.responseHandler.sendFinishMessage();
                if (this.isBinaryRequest) {
                    this.responseHandler.sendFailureMessage(e, null);
                } else {
                    this.responseHandler.sendFailureMessage(e, null);
                }
            }
        }
    }

    private void makeRequest() throws IOException {
        if (!Thread.currentThread().isInterrupted()) {
            try {
                HttpResponse response = this.client.execute(this.request, this.context);
                if (!Thread.currentThread().isInterrupted() && this.responseHandler != null) {
                    this.responseHandler.sendResponseMessage(response);
                }
            } catch (IOException e) {
                if (!Thread.currentThread().isInterrupted()) {
                    throw e;
                }
            }
        }
    }

    private void makeRequestWithRetries() throws ConnectException {
        int i;
        boolean retry = true;
        IOException cause = null;
        HttpRequestRetryHandler retryHandler = this.client.getHttpRequestRetryHandler();
        while (retry) {
            try {
                makeRequest();
                return;
            } catch (Throwable e) {
                if (this.responseHandler != null) {
                    this.responseHandler.sendFailureMessage(e, "can't resolve host");
                    return;
                }
                return;
            } catch (Throwable e2) {
                if (this.responseHandler != null) {
                    this.responseHandler.sendFailureMessage(e2, "can't resolve host");
                    return;
                }
                return;
            } catch (Throwable e22) {
                if (this.responseHandler != null) {
                    this.responseHandler.sendFailureMessage(e22, "socket time out");
                    return;
                }
                return;
            } catch (IOException e3) {
                cause = e3;
                i = this.executionCount + 1;
                this.executionCount = i;
                retry = retryHandler.retryRequest(cause, i, this.context);
            } catch (NullPointerException e4) {
                cause = new IOException("NPE in HttpClient" + e4.getMessage());
                i = this.executionCount + 1;
                this.executionCount = i;
                retry = retryHandler.retryRequest(cause, i, this.context);
            }
        }
        ConnectException ex = new ConnectException();
        ex.initCause(cause);
        throw ex;
    }
}
