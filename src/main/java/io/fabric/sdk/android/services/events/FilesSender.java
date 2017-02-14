package io.fabric.sdk.android.services.events;

import java.io.File;
import java.util.List;

public interface FilesSender {
    boolean send(List<File> list);
}
