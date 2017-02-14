package io.fabric.sdk.android.services.events;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPQueueFileEventStorage extends QueueFileEventStorage {
    public GZIPQueueFileEventStorage(Context context, File workingDirectory, String workingFileName, String targetDirectoryName) throws IOException {
        super(context, workingDirectory, workingFileName, targetDirectoryName);
    }

    public OutputStream getMoveOutputStream(File targetFile) throws IOException {
        return new GZIPOutputStream(new FileOutputStream(targetFile));
    }
}
