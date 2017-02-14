package com.flurry.android;

public enum FlurryEventRecordStatus {
    kFlurryEventFailed,
    kFlurryEventRecorded,
    kFlurryEventUniqueCountExceeded,
    kFlurryEventParamsCountExceeded,
    kFlurryEventLogCountExceeded,
    kFlurryEventLoggingDelayed
}
