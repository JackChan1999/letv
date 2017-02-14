package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequestFactory;

public class UpdateAppSpiCall extends AbstractAppSpiCall {
    public /* bridge */ /* synthetic */ boolean invoke(AppRequestData x0) {
        return super.invoke(x0);
    }

    public UpdateAppSpiCall(Kit kit, String protocolAndHostOverride, String url, HttpRequestFactory requestFactory) {
        super(kit, protocolAndHostOverride, url, requestFactory, HttpMethod.PUT);
    }
}
