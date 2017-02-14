package com.immersion.hapticmediasdk.controllers;

import com.immersion.aws.pm.PolicyManager;
import com.immersion.content.HapticHeaderUtils;
import com.immersion.content.HeaderUtils;
import com.immersion.hapticmediasdk.utils.FileManager;
import com.immersion.hapticmediasdk.utils.Log;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public class FileReaderFactory {
    public static int b04150415ЕЕ0415Е = 1;
    public static int b0415Е0415Е0415Е = 0;
    private static final int b041EО041E041E041E041E = 2;
    public static int bЕ0415ЕЕ0415Е = 29;
    public static int bЕЕ0415Е0415Е = 2;
    private static final String bОО041E041E041E041E = "FileReaderFactory";

    public FileReaderFactory() {
        String str = null;
        try {
            while (true) {
                try {
                    str.length();
                } catch (Exception e) {
                    return;
                }
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    private static int b044C044C044Cь044Cь(FileChannel fileChannel, PolicyManager policyManager) {
        IOException iOException;
        int i = 0;
        try {
            ByteBuffer allocate = ByteBuffer.allocate(4);
            allocate.order(ByteOrder.LITTLE_ENDIAN);
            allocate.position(0);
            if (fileChannel.read(allocate, 16) != 4) {
                return 0;
            }
            allocate.flip();
            int i2 = allocate.getInt();
            int i3 = i2 + 28;
            ByteBuffer allocate2 = ByteBuffer.allocate(i3);
            allocate2.order(ByteOrder.LITTLE_ENDIAN);
            if (fileChannel.read(allocate2, 0) != i3) {
                return 0;
            }
            allocate2.position(4);
            i3 = allocate2.getInt() + 8;
            allocate2.position(20);
            HeaderUtils hapticHeaderUtils = new HapticHeaderUtils();
            hapticHeaderUtils.setEncryptedHSI(allocate2, i2);
            i2 = hapticHeaderUtils.getMajorVersionNumber();
            try {
                String str = "";
                if (i2 >= 2) {
                    str = hapticHeaderUtils.getContentUUID();
                }
                policyManager.prepareContentID(str);
                return i2;
            } catch (IOException e) {
                IOException iOException2 = e;
                i = i2;
                iOException = iOException2;
                iOException.printStackTrace();
                return i;
            }
        } catch (IOException e2) {
            iOException = e2;
            iOException.printStackTrace();
            return i;
        }
    }

    public static int bЕ04150415Е0415Е() {
        return 91;
    }

    private static int bь044C044Cь044Cь(String str, FileManager fileManager, PolicyManager policyManager) {
        File hapticStorageFile;
        int i = 0;
        FileChannel fileChannel = null;
        if (fileManager != null) {
            try {
                hapticStorageFile = fileManager.getHapticStorageFile(str);
            } catch (Exception e) {
                try {
                    e.printStackTrace();
                    return i;
                } catch (Exception e2) {
                    throw e2;
                }
            }
        } else if (str == null) {
            return i;
        } else {
            hapticStorageFile = new File(str);
        }
        if (hapticStorageFile.length() == 0) {
            return -1;
        }
        if (null == null) {
            fileChannel = new RandomAccessFile(hapticStorageFile, "r").getChannel();
        }
        if (fileChannel == null) {
            return i;
        }
        i = b044C044C044Cь044Cь(fileChannel, policyManager);
        if (((bЕ0415ЕЕ0415Е + b04150415ЕЕ0415Е) * bЕ0415ЕЕ0415Е) % bЕЕ0415Е0415Е != b0415Е0415Е0415Е) {
            bЕ0415ЕЕ0415Е = bЕ04150415Е0415Е();
            b0415Е0415Е0415Е = bЕ04150415Е0415Е();
        }
        fileChannel.close();
        return i;
    }

    public static IHapticFileReader getHapticFileReaderInstance(String str, FileManager fileManager, PolicyManager policyManager) {
        try {
            switch (bь044C044Cь044Cь(str, fileManager, policyManager)) {
                case -1:
                    Log.i(bОО041E041E041E041E, "Can't retrieve Major version! Not enough bytes available yet.");
                    return null;
                case 1:
                    return new MemoryMappedFileReader(str, fileManager);
                case 2:
                    return new MemoryAlignedFileReader(str, fileManager, 2);
                case 3:
                    return new MemoryAlignedFileReader(str, fileManager, 3);
                default:
                    Log.e(bОО041E041E041E041E, "Unsupported HAPT file version");
                    return null;
            }
        } catch (Error e) {
            e.printStackTrace();
            return null;
        }
        e.printStackTrace();
        return null;
    }
}
