package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.services.common.IdManager.DeviceIdentifierType;
import java.util.Map;

public interface DeviceIdentifierProvider {
    Map<DeviceIdentifierType, String> getDeviceIdentifiers();
}
