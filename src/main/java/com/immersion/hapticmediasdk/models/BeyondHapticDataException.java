package com.immersion.hapticmediasdk.models;

public class BeyondHapticDataException extends Exception {
    public BeyondHapticDataException(String str) {
        try {
            super(str);
        } catch (Exception e) {
            throw e;
        }
    }
}
