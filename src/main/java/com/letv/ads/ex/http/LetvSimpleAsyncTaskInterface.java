package com.letv.ads.ex.http;

public interface LetvSimpleAsyncTaskInterface<T> {
    T doInBackground();

    void onPostExecute(T t);

    void onPreExecute();
}
