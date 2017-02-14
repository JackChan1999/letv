package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.QueueFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueueFileEventStorage implements EventsStorage {
    private final Context context;
    private QueueFile queueFile = new QueueFile(this.workingFile);
    private File targetDirectory;
    private final String targetDirectoryName;
    private final File workingDirectory;
    private final File workingFile;

    public QueueFileEventStorage(Context context, File workingDirectory, String workingFileName, String targetDirectoryName) throws IOException {
        this.context = context;
        this.workingDirectory = workingDirectory;
        this.targetDirectoryName = targetDirectoryName;
        this.workingFile = new File(this.workingDirectory, workingFileName);
        createTargetDirectory();
    }

    private void createTargetDirectory() {
        this.targetDirectory = new File(this.workingDirectory, this.targetDirectoryName);
        if (!this.targetDirectory.exists()) {
            this.targetDirectory.mkdirs();
        }
    }

    public void add(byte[] data) throws IOException {
        this.queueFile.add(data);
    }

    public int getWorkingFileUsedSizeInBytes() {
        return this.queueFile.usedBytes();
    }

    public void rollOver(String targetName) throws IOException {
        this.queueFile.close();
        move(this.workingFile, new File(this.targetDirectory, targetName));
        this.queueFile = new QueueFile(this.workingFile);
    }

    private void move(File sourceFile, File targetFile) throws IOException {
        Throwable th;
        OutputStream fos = null;
        FileInputStream fis = null;
        try {
            FileInputStream fis2 = new FileInputStream(sourceFile);
            try {
                fos = getMoveOutputStream(targetFile);
                CommonUtils.copyStream(fis2, fos, new byte[1024]);
                CommonUtils.closeOrLog(fis2, "Failed to close file input stream");
                CommonUtils.closeOrLog(fos, "Failed to close output stream");
                sourceFile.delete();
            } catch (Throwable th2) {
                th = th2;
                fis = fis2;
                CommonUtils.closeOrLog(fis, "Failed to close file input stream");
                CommonUtils.closeOrLog(fos, "Failed to close output stream");
                sourceFile.delete();
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            CommonUtils.closeOrLog(fis, "Failed to close file input stream");
            CommonUtils.closeOrLog(fos, "Failed to close output stream");
            sourceFile.delete();
            throw th;
        }
    }

    public OutputStream getMoveOutputStream(File targetFile) throws IOException {
        return new FileOutputStream(targetFile);
    }

    public File getWorkingDirectory() {
        return this.workingDirectory;
    }

    public File getRollOverDirectory() {
        return this.targetDirectory;
    }

    public List<File> getBatchOfFilesToSend(int maxBatchSize) {
        List<File> batch = new ArrayList();
        for (File file : this.targetDirectory.listFiles()) {
            batch.add(file);
            if (batch.size() >= maxBatchSize) {
                break;
            }
        }
        return batch;
    }

    public void deleteFilesInRollOverDirectory(List<File> files) {
        for (File file : files) {
            CommonUtils.logControlled(this.context, String.format("deleting sent analytics file %s", new Object[]{file.getName()}));
            file.delete();
        }
    }

    public List<File> getAllFilesInRollOverDirectory() {
        return Arrays.asList(this.targetDirectory.listFiles());
    }

    public void deleteWorkingFile() {
        try {
            this.queueFile.close();
        } catch (IOException e) {
        }
        this.workingFile.delete();
    }

    public boolean isWorkingFileEmpty() {
        return this.queueFile.isEmpty();
    }

    public boolean canWorkingFileStore(int newEventSizeInBytes, int maxByteSizePerFile) {
        return this.queueFile.hasSpaceFor(newEventSizeInBytes, maxByteSizePerFile);
    }
}
