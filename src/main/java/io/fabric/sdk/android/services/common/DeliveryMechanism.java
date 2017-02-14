package io.fabric.sdk.android.services.common;

public enum DeliveryMechanism {
    DEVELOPER(1),
    USER_SIDELOAD(2),
    TEST_DISTRIBUTION(3),
    APP_STORE(4);
    
    public static final String BETA_APP_PACKAGE_NAME = "io.crash.air";
    private final int id;

    private DeliveryMechanism(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return Integer.toString(this.id);
    }

    public static DeliveryMechanism determineFrom(String installerPackageName) {
        if (BETA_APP_PACKAGE_NAME.equals(installerPackageName)) {
            return TEST_DISTRIBUTION;
        }
        if (installerPackageName != null) {
            return APP_STORE;
        }
        return DEVELOPER;
    }
}
