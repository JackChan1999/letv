package com.letv.pp.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    private static final String TAG = "ZipUtils";

    public static String compress(String data) {
        GZIPOutputStream gzipOs;
        Exception e;
        Throwable th;
        if (StringUtils.isEmpty(data)) {
            return data;
        }
        GZIPOutputStream gzipOs2 = null;
        ByteArrayOutputStream bos = null;
        try {
            ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
            try {
                gzipOs = new GZIPOutputStream(bos2);
            } catch (Exception e2) {
                e = e2;
                bos = bos2;
                try {
                    LogTool.e(TAG, "compress. " + e.toString());
                    IOUtils.closeSilently(bos);
                    IOUtils.closeSilently(gzipOs2);
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    IOUtils.closeSilently(bos);
                    IOUtils.closeSilently(gzipOs2);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                bos = bos2;
                IOUtils.closeSilently(bos);
                IOUtils.closeSilently(gzipOs2);
                throw th;
            }
            try {
                gzipOs.write(data.getBytes());
                gzipOs.close();
                gzipOs2 = null;
                data = bos2.toString("ISO-8859-1");
                IOUtils.closeSilently(bos2);
                IOUtils.closeSilently(null);
                return data;
            } catch (Exception e3) {
                e = e3;
                bos = bos2;
                gzipOs2 = gzipOs;
                LogTool.e(TAG, "compress. " + e.toString());
                IOUtils.closeSilently(bos);
                IOUtils.closeSilently(gzipOs2);
                return null;
            } catch (Throwable th4) {
                th = th4;
                bos = bos2;
                gzipOs2 = gzipOs;
                IOUtils.closeSilently(bos);
                IOUtils.closeSilently(gzipOs2);
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            LogTool.e(TAG, "compress. " + e.toString());
            IOUtils.closeSilently(bos);
            IOUtils.closeSilently(gzipOs2);
            return null;
        }
    }

    public static String uncompress(String data) {
        GZIPInputStream gzipIs;
        Exception e;
        Throwable th;
        if (StringUtils.isEmpty(data)) {
            return data;
        }
        GZIPInputStream gzipIs2 = null;
        ByteArrayInputStream bis = null;
        ByteArrayOutputStream bos = null;
        try {
            ByteArrayInputStream bis2 = new ByteArrayInputStream(data.getBytes("ISO-8859-1"));
            try {
                gzipIs = new GZIPInputStream(bis2);
            } catch (Exception e2) {
                e = e2;
                bis = bis2;
                try {
                    LogTool.e(TAG, "uncompress. " + e.toString());
                    IOUtils.closeSilently(bos);
                    IOUtils.closeSilently(gzipIs2);
                    IOUtils.closeSilently(bis);
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    IOUtils.closeSilently(bos);
                    IOUtils.closeSilently(gzipIs2);
                    IOUtils.closeSilently(bis);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                bis = bis2;
                IOUtils.closeSilently(bos);
                IOUtils.closeSilently(gzipIs2);
                IOUtils.closeSilently(bis);
                throw th;
            }
            try {
                ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
                try {
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int length = gzipIs.read(buffer);
                        if (length < 0) {
                            data = bos2.toString();
                            IOUtils.closeSilently(bos2);
                            IOUtils.closeSilently(gzipIs);
                            IOUtils.closeSilently(bis2);
                            return data;
                        }
                        bos2.write(buffer, 0, length);
                    }
                } catch (Exception e3) {
                    e = e3;
                    bos = bos2;
                    bis = bis2;
                    gzipIs2 = gzipIs;
                } catch (Throwable th4) {
                    th = th4;
                    bos = bos2;
                    bis = bis2;
                    gzipIs2 = gzipIs;
                }
            } catch (Exception e4) {
                e = e4;
                bis = bis2;
                gzipIs2 = gzipIs;
                LogTool.e(TAG, "uncompress. " + e.toString());
                IOUtils.closeSilently(bos);
                IOUtils.closeSilently(gzipIs2);
                IOUtils.closeSilently(bis);
                return null;
            } catch (Throwable th5) {
                th = th5;
                bis = bis2;
                gzipIs2 = gzipIs;
                IOUtils.closeSilently(bos);
                IOUtils.closeSilently(gzipIs2);
                IOUtils.closeSilently(bis);
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            LogTool.e(TAG, "uncompress. " + e.toString());
            IOUtils.closeSilently(bos);
            IOUtils.closeSilently(gzipIs2);
            IOUtils.closeSilently(bis);
            return null;
        }
    }

    public static byte[] uncompress(byte[] data) {
        GZIPInputStream gzipIs;
        Throwable e;
        Throwable th;
        if (data == null || data.length <= 0) {
            return data;
        }
        GZIPInputStream gzipIs2 = null;
        ByteArrayInputStream bis = null;
        ByteArrayOutputStream bos = null;
        try {
            ByteArrayInputStream bis2 = new ByteArrayInputStream(data);
            try {
                gzipIs = new GZIPInputStream(bis2);
            } catch (EOFException e2) {
                e = e2;
                bis = bis2;
                try {
                    LogTool.w(TAG, "", e);
                    data = bos.toByteArray();
                    IOUtils.closeSilently(bos);
                    IOUtils.closeSilently(gzipIs2);
                    IOUtils.closeSilently(bis);
                    return data;
                } catch (Throwable th2) {
                    th = th2;
                    IOUtils.closeSilently(bos);
                    IOUtils.closeSilently(gzipIs2);
                    IOUtils.closeSilently(bis);
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
                bis = bis2;
                LogTool.e(TAG, "", e);
                IOUtils.closeSilently(bos);
                IOUtils.closeSilently(gzipIs2);
                IOUtils.closeSilently(bis);
                return null;
            } catch (Throwable th3) {
                th = th3;
                bis = bis2;
                IOUtils.closeSilently(bos);
                IOUtils.closeSilently(gzipIs2);
                IOUtils.closeSilently(bis);
                throw th;
            }
            try {
                ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
                try {
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int length = gzipIs.read(buffer);
                        if (length < 0) {
                            data = bos2.toByteArray();
                            IOUtils.closeSilently(bos2);
                            IOUtils.closeSilently(gzipIs);
                            IOUtils.closeSilently(bis2);
                            return data;
                        }
                        bos2.write(buffer, 0, length);
                    }
                } catch (EOFException e4) {
                    e = e4;
                    bos = bos2;
                    bis = bis2;
                    gzipIs2 = gzipIs;
                } catch (IOException e5) {
                    e = e5;
                    bos = bos2;
                    bis = bis2;
                    gzipIs2 = gzipIs;
                } catch (Throwable th4) {
                    th = th4;
                    bos = bos2;
                    bis = bis2;
                    gzipIs2 = gzipIs;
                }
            } catch (EOFException e6) {
                e = e6;
                bis = bis2;
                gzipIs2 = gzipIs;
                LogTool.w(TAG, "", e);
                data = bos.toByteArray();
                IOUtils.closeSilently(bos);
                IOUtils.closeSilently(gzipIs2);
                IOUtils.closeSilently(bis);
                return data;
            } catch (IOException e7) {
                e = e7;
                bis = bis2;
                gzipIs2 = gzipIs;
                LogTool.e(TAG, "", e);
                IOUtils.closeSilently(bos);
                IOUtils.closeSilently(gzipIs2);
                IOUtils.closeSilently(bis);
                return null;
            } catch (Throwable th5) {
                th = th5;
                bis = bis2;
                gzipIs2 = gzipIs;
                IOUtils.closeSilently(bos);
                IOUtils.closeSilently(gzipIs2);
                IOUtils.closeSilently(bis);
                throw th;
            }
        } catch (EOFException e8) {
            e = e8;
            LogTool.w(TAG, "", e);
            data = bos.toByteArray();
            IOUtils.closeSilently(bos);
            IOUtils.closeSilently(gzipIs2);
            IOUtils.closeSilently(bis);
            return data;
        } catch (IOException e9) {
            e = e9;
            LogTool.e(TAG, "", e);
            IOUtils.closeSilently(bos);
            IOUtils.closeSilently(gzipIs2);
            IOUtils.closeSilently(bis);
            return null;
        }
    }

    public static void compressFile(String srcFilePath, String zipFilePath) {
        Throwable e;
        Throwable th;
        if (!StringUtils.isEmpty(srcFilePath) && !StringUtils.isEmpty(zipFilePath)) {
            File srcFile = new File(srcFilePath);
            if (srcFile.exists()) {
                ZipOutputStream zipOutputStream = null;
                try {
                    ZipOutputStream zipOutputStream2 = new ZipOutputStream(new FileOutputStream(zipFilePath));
                    try {
                        compressFiles(srcFile.getParent() + File.separator, srcFile.getName(), zipOutputStream2);
                        if (zipOutputStream2 != null) {
                            try {
                                zipOutputStream2.finish();
                                zipOutputStream2.close();
                                zipOutputStream = zipOutputStream2;
                                return;
                            } catch (Throwable e2) {
                                LogTool.e(TAG, "", e2);
                            }
                        }
                        zipOutputStream = zipOutputStream2;
                    } catch (Exception e3) {
                        e2 = e3;
                        zipOutputStream = zipOutputStream2;
                        try {
                            LogTool.e(TAG, "", e2);
                            if (zipOutputStream != null) {
                                try {
                                    zipOutputStream.finish();
                                    zipOutputStream.close();
                                } catch (Throwable e22) {
                                    LogTool.e(TAG, "", e22);
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (zipOutputStream != null) {
                                try {
                                    zipOutputStream.finish();
                                    zipOutputStream.close();
                                } catch (Throwable e222) {
                                    LogTool.e(TAG, "", e222);
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        zipOutputStream = zipOutputStream2;
                        if (zipOutputStream != null) {
                            zipOutputStream.finish();
                            zipOutputStream.close();
                        }
                        throw th;
                    }
                } catch (Exception e4) {
                    e222 = e4;
                    LogTool.e(TAG, "", e222);
                    if (zipOutputStream != null) {
                        zipOutputStream.finish();
                        zipOutputStream.close();
                    }
                }
            }
        }
    }

    private static void compressFiles(String folderPath, String fileName, ZipOutputStream zipOutputStream) {
        Throwable e;
        Throwable th;
        FileInputStream inputStream = null;
        try {
            File file = new File(new StringBuilder(String.valueOf(folderPath)).append(fileName).toString());
            if (file.isFile()) {
                ZipEntry zipEntry = new ZipEntry(fileName);
                FileInputStream inputStream2 = new FileInputStream(file);
                try {
                    zipOutputStream.putNextEntry(zipEntry);
                    byte[] buffer = new byte[4096];
                    while (true) {
                        int len = inputStream2.read(buffer);
                        if (len == -1) {
                            break;
                        }
                        zipOutputStream.write(buffer, 0, len);
                    }
                    zipOutputStream.closeEntry();
                    inputStream = inputStream2;
                } catch (Exception e2) {
                    e = e2;
                    inputStream = inputStream2;
                } catch (Throwable th2) {
                    th = th2;
                    inputStream = inputStream2;
                }
            } else {
                String[] fileList = file.list();
                if (fileList.length <= 0) {
                    zipOutputStream.putNextEntry(new ZipEntry(new StringBuilder(String.valueOf(fileName)).append(File.separator).toString()));
                    zipOutputStream.closeEntry();
                }
                for (String append : fileList) {
                    compressFiles(folderPath, new StringBuilder(String.valueOf(fileName)).append(File.separator).append(append).toString(), zipOutputStream);
                }
            }
            IOUtils.closeSilently(inputStream);
        } catch (Exception e3) {
            e = e3;
            try {
                LogTool.e(TAG, "", e);
                IOUtils.closeSilently(inputStream);
            } catch (Throwable th3) {
                th = th3;
                IOUtils.closeSilently(inputStream);
                throw th;
            }
        }
    }

    public static boolean uncompress(File compressFile, String uncompressFileName) {
        Throwable e;
        Throwable th;
        if (compressFile == null || !compressFile.exists() || StringUtils.isEmpty(uncompressFileName)) {
            return false;
        }
        ZipFile zipFile = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            ZipFile zipFile2 = new ZipFile(compressFile);
            try {
                ZipEntry zipEntry;
                Enumeration zipList = zipFile2.entries();
                byte[] buffer = new byte[1024];
                String fileName;
                do {
                    if (zipList.hasMoreElements()) {
                        zipEntry = (ZipEntry) zipList.nextElement();
                        fileName = zipEntry.getName();
                        if (!zipEntry.isDirectory()) {
                            break;
                        }
                    } else {
                        IOUtils.closeSilently(null);
                        IOUtils.closeSilently(null);
                        if (zipFile2 != null) {
                            try {
                                zipFile2.close();
                            } catch (Throwable e2) {
                                LogTool.e(TAG, "", e2);
                            }
                        }
                        return false;
                    }
                } while (!uncompressFileName.equals(fileName));
                InputStream inputStream2 = new BufferedInputStream(zipFile2.getInputStream(zipEntry));
                try {
                    OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(new File(compressFile.getParentFile(), uncompressFileName)));
                    while (true) {
                        try {
                            int length = inputStream2.read(buffer, 0, 1024);
                            if (length == -1) {
                                break;
                            }
                            outputStream2.write(buffer, 0, length);
                        } catch (Exception e3) {
                            e2 = e3;
                            outputStream = outputStream2;
                            inputStream = inputStream2;
                            zipFile = zipFile2;
                        } catch (Throwable th2) {
                            th = th2;
                            outputStream = outputStream2;
                            inputStream = inputStream2;
                            zipFile = zipFile2;
                        }
                    }
                    IOUtils.closeSilently(outputStream2);
                    IOUtils.closeSilently(inputStream2);
                    if (zipFile2 != null) {
                        try {
                            zipFile2.close();
                        } catch (Throwable e22) {
                            LogTool.e(TAG, "", e22);
                        }
                    }
                    return true;
                } catch (Exception e4) {
                    e22 = e4;
                    inputStream = inputStream2;
                    zipFile = zipFile2;
                    try {
                        LogTool.e(TAG, "", e22);
                        IOUtils.closeSilently(outputStream);
                        IOUtils.closeSilently(inputStream);
                        if (zipFile != null) {
                            try {
                                zipFile.close();
                            } catch (Throwable e222) {
                                LogTool.e(TAG, "", e222);
                            }
                        }
                        return false;
                    } catch (Throwable th3) {
                        th = th3;
                        IOUtils.closeSilently(outputStream);
                        IOUtils.closeSilently(inputStream);
                        if (zipFile != null) {
                            try {
                                zipFile.close();
                            } catch (Throwable e2222) {
                                LogTool.e(TAG, "", e2222);
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    inputStream = inputStream2;
                    zipFile = zipFile2;
                    IOUtils.closeSilently(outputStream);
                    IOUtils.closeSilently(inputStream);
                    if (zipFile != null) {
                        zipFile.close();
                    }
                    throw th;
                }
            } catch (Exception e5) {
                e2222 = e5;
                zipFile = zipFile2;
            } catch (Throwable th5) {
                th = th5;
                zipFile = zipFile2;
            }
        } catch (Exception e6) {
            e2222 = e6;
            LogTool.e(TAG, "", e2222);
            IOUtils.closeSilently(outputStream);
            IOUtils.closeSilently(inputStream);
            if (zipFile != null) {
                zipFile.close();
            }
            return false;
        }
    }

    private ZipUtils() {
    }
}
