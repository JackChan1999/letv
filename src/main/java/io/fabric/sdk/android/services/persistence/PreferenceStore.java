package io.fabric.sdk.android.services.persistence;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public interface PreferenceStore {
    Editor edit();

    SharedPreferences get();

    boolean save(Editor editor);
}
