package com.facebook.internal;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.util.Log;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public final class NativeAppCallAttachmentStore {
    static final String ATTACHMENTS_DIR_NAME = "com.facebook.NativeAppCallAttachmentStore.files";
    private static final String TAG = NativeAppCallAttachmentStore.class.getName();
    private static File attachmentsDirectory;

    private NativeAppCallAttachmentStore() {
    }

    public static Attachment createAttachment(UUID callId, Bitmap attachmentBitmap) {
        Validate.notNull(callId, "callId");
        Validate.notNull(attachmentBitmap, "attachmentBitmap");
        return new Attachment(callId, attachmentBitmap, null, null);
    }

    public static Attachment createAttachment(UUID callId, Uri attachmentUri) {
        Validate.notNull(callId, "callId");
        Validate.notNull(attachmentUri, "attachmentUri");
        return new Attachment(callId, null, attachmentUri, null);
    }

    private static void processAttachmentBitmap(Bitmap bitmap, File outputFile) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        try {
            bitmap.compress(CompressFormat.JPEG, 100, outputStream);
        } finally {
            Utility.closeQuietly(outputStream);
        }
    }

    private static void processAttachmentFile(Uri imageUri, boolean isContentUri, File outputFile) throws IOException {
        InputStream inputStream;
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        if (isContentUri) {
            inputStream = FacebookSdk.getApplicationContext().getContentResolver().openInputStream(imageUri);
        } else {
            try {
                inputStream = new FileInputStream(imageUri.getPath());
            } catch (Throwable th) {
                Utility.closeQuietly(outputStream);
            }
        }
        Utility.copyAndCloseInputStream(inputStream, outputStream);
        Utility.closeQuietly(outputStream);
    }

    public static void addAttachments(Collection<Attachment> attachments) {
        if (attachments != null && attachments.size() != 0) {
            if (attachmentsDirectory == null) {
                cleanupAllAttachments();
            }
            ensureAttachmentsDirectoryExists();
            List<File> filesToCleanup = new ArrayList();
            File file;
            try {
                for (Attachment attachment : attachments) {
                    if (Attachment.access$100(attachment)) {
                        file = getAttachmentFile(Attachment.access$200(attachment), Attachment.access$300(attachment), true);
                        filesToCleanup.add(file);
                        if (Attachment.access$400(attachment) != null) {
                            processAttachmentBitmap(Attachment.access$400(attachment), file);
                        } else if (Attachment.access$500(attachment) != null) {
                            processAttachmentFile(Attachment.access$500(attachment), Attachment.access$600(attachment), file);
                        }
                    }
                }
            } catch (Throwable exception) {
                Log.e(TAG, "Got unexpected exception:" + exception);
                for (File file2 : filesToCleanup) {
                    try {
                        file2.delete();
                    } catch (Exception e) {
                    }
                }
                throw new FacebookException(exception);
            }
        }
    }

    public static void cleanupAttachmentsForCall(UUID callId) {
        File dir = getAttachmentsDirectoryForCall(callId, false);
        if (dir != null) {
            Utility.deleteDirectory(dir);
        }
    }

    public static File openAttachment(UUID callId, String attachmentName) throws FileNotFoundException {
        if (Utility.isNullOrEmpty(attachmentName) || callId == null) {
            throw new FileNotFoundException();
        }
        try {
            return getAttachmentFile(callId, attachmentName, false);
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }

    static synchronized File getAttachmentsDirectory() {
        File file;
        synchronized (NativeAppCallAttachmentStore.class) {
            if (attachmentsDirectory == null) {
                attachmentsDirectory = new File(FacebookSdk.getApplicationContext().getCacheDir(), ATTACHMENTS_DIR_NAME);
            }
            file = attachmentsDirectory;
        }
        return file;
    }

    static File ensureAttachmentsDirectoryExists() {
        File dir = getAttachmentsDirectory();
        dir.mkdirs();
        return dir;
    }

    static File getAttachmentsDirectoryForCall(UUID callId, boolean create) {
        if (attachmentsDirectory == null) {
            return null;
        }
        File dir = new File(attachmentsDirectory, callId.toString());
        if (!create || dir.exists()) {
            return dir;
        }
        dir.mkdirs();
        return dir;
    }

    static File getAttachmentFile(UUID callId, String attachmentName, boolean createDirs) throws IOException {
        File dir = getAttachmentsDirectoryForCall(callId, createDirs);
        if (dir == null) {
            return null;
        }
        try {
            return new File(dir, URLEncoder.encode(attachmentName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static void cleanupAllAttachments() {
        Utility.deleteDirectory(getAttachmentsDirectory());
    }
}
