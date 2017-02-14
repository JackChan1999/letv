package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class EventsFilesManager<T> {
    public static final int MAX_BYTE_SIZE_PER_FILE = 8000;
    public static final int MAX_FILES_IN_BATCH = 1;
    public static final int MAX_FILES_TO_KEEP = 100;
    public static final String ROLL_OVER_FILE_NAME_SEPARATOR = "_";
    protected final Context context;
    protected final CurrentTimeProvider currentTimeProvider;
    private final int defaultMaxFilesToKeep;
    protected final EventsStorage eventStorage;
    protected volatile long lastRollOverTime;
    protected final List<EventsStorageListener> rollOverListeners = new CopyOnWriteArrayList();
    protected final EventTransform<T> transform;

    static class FileWithTimestamp {
        final File file;
        final long timestamp;

        public FileWithTimestamp(File file, long timestamp) {
            this.file = file;
            this.timestamp = timestamp;
        }
    }

    protected abstract String generateUniqueRollOverFileName();

    public EventsFilesManager(Context context, EventTransform<T> transform, CurrentTimeProvider currentTimeProvider, EventsStorage eventStorage, int defaultMaxFilesToKeep) throws IOException {
        this.context = context.getApplicationContext();
        this.transform = transform;
        this.eventStorage = eventStorage;
        this.currentTimeProvider = currentTimeProvider;
        this.lastRollOverTime = this.currentTimeProvider.getCurrentTimeMillis();
        this.defaultMaxFilesToKeep = defaultMaxFilesToKeep;
    }

    public void writeEvent(T event) throws IOException {
        byte[] eventBytes = this.transform.toBytes(event);
        rollFileOverIfNeeded(eventBytes.length);
        this.eventStorage.add(eventBytes);
    }

    public void registerRollOverListener(EventsStorageListener listener) {
        if (listener != null) {
            this.rollOverListeners.add(listener);
        }
    }

    public boolean rollFileOver() throws IOException {
        boolean fileRolledOver = false;
        String targetFileName = null;
        if (!this.eventStorage.isWorkingFileEmpty()) {
            targetFileName = generateUniqueRollOverFileName();
            this.eventStorage.rollOver(targetFileName);
            CommonUtils.logControlled(this.context, 4, Fabric.TAG, String.format(Locale.US, "generated new file %s", new Object[]{targetFileName}));
            this.lastRollOverTime = this.currentTimeProvider.getCurrentTimeMillis();
            fileRolledOver = true;
        }
        triggerRollOverOnListeners(targetFileName);
        return fileRolledOver;
    }

    private void rollFileOverIfNeeded(int newEventSizeInBytes) throws IOException {
        if (!this.eventStorage.canWorkingFileStore(newEventSizeInBytes, getMaxByteSizePerFile())) {
            CommonUtils.logControlled(this.context, 4, Fabric.TAG, String.format(Locale.US, "session analytics events file is %d bytes, new event is %d bytes, this is over flush limit of %d, rolling it over", new Object[]{Integer.valueOf(this.eventStorage.getWorkingFileUsedSizeInBytes()), Integer.valueOf(newEventSizeInBytes), Integer.valueOf(getMaxByteSizePerFile())}));
            rollFileOver();
        }
    }

    protected int getMaxFilesToKeep() {
        return this.defaultMaxFilesToKeep;
    }

    protected int getMaxByteSizePerFile() {
        return 8000;
    }

    public long getLastRollOverTime() {
        return this.lastRollOverTime;
    }

    private void triggerRollOverOnListeners(String rolledOverFile) {
        for (EventsStorageListener eventStorageRollOverListener : this.rollOverListeners) {
            try {
                eventStorageRollOverListener.onRollOver(rolledOverFile);
            } catch (Exception e) {
                CommonUtils.logControlledError(this.context, "One of the roll over listeners threw an exception", e);
            }
        }
    }

    public List<File> getBatchOfFilesToSend() {
        return this.eventStorage.getBatchOfFilesToSend(1);
    }

    public void deleteSentFiles(List<File> files) {
        this.eventStorage.deleteFilesInRollOverDirectory(files);
    }

    public void deleteAllEventsFiles() {
        this.eventStorage.deleteFilesInRollOverDirectory(this.eventStorage.getAllFilesInRollOverDirectory());
        this.eventStorage.deleteWorkingFile();
    }

    public void deleteOldestInRollOverIfOverMax() {
        List<File> allFiles = this.eventStorage.getAllFilesInRollOverDirectory();
        int maxFiles = getMaxFilesToKeep();
        if (allFiles.size() > maxFiles) {
            int numberOfFilesToDelete = allFiles.size() - maxFiles;
            CommonUtils.logControlled(this.context, String.format(Locale.US, "Found %d files in  roll over directory, this is greater than %d, deleting %d oldest files", new Object[]{Integer.valueOf(allFiles.size()), Integer.valueOf(maxFiles), Integer.valueOf(numberOfFilesToDelete)}));
            TreeSet<FileWithTimestamp> sortedFiles = new TreeSet(new Comparator<FileWithTimestamp>() {
                public int compare(FileWithTimestamp arg0, FileWithTimestamp arg1) {
                    return (int) (arg0.timestamp - arg1.timestamp);
                }
            });
            for (File file : allFiles) {
                sortedFiles.add(new FileWithTimestamp(file, parseCreationTimestampFromFileName(file.getName())));
            }
            ArrayList<File> toDelete = new ArrayList();
            Iterator i$ = sortedFiles.iterator();
            while (i$.hasNext()) {
                toDelete.add(((FileWithTimestamp) i$.next()).file);
                if (toDelete.size() == numberOfFilesToDelete) {
                    break;
                }
            }
            this.eventStorage.deleteFilesInRollOverDirectory(toDelete);
        }
    }

    public long parseCreationTimestampFromFileName(String fileName) {
        long j = 0;
        String[] fileNameParts = fileName.split(ROLL_OVER_FILE_NAME_SEPARATOR);
        if (fileNameParts.length == 3) {
            try {
                j = Long.valueOf(fileNameParts[2]).longValue();
            } catch (NumberFormatException e) {
            }
        }
        return j;
    }
}
