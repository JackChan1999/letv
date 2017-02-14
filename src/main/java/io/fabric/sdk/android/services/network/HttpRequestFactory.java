package io.fabric.sdk.android.services.network;

import java.util.Map;

public interface HttpRequestFactory {
    HttpRequest buildHttpRequest(HttpMethod httpMethod, String str);

    HttpRequest buildHttpRequest(HttpMethod httpMethod, String str, Map<String, String> map);

    PinningInfoProvider getPinningInfoProvider();

    void setPinningInfoProvider(PinningInfoProvider pinningInfoProvider);
}
