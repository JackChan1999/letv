package com.letv.component.player.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    private static final int BUFF_SIZE = 1048576;

    public static void zipFiles(Collection<File> resFileList, File zipFile) throws IOException {
        if (!zipFile.exists()) {
            zipFile.createNewFile();
        }
        ZipOutputStream zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile), 1048576));
        for (File resFile : resFileList) {
            zipFile(resFile, zipout, "");
        }
        zipout.close();
    }

    public static void zipFiles(Collection<File> resFileList, File zipFile, String comment) throws IOException {
        ZipOutputStream zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile), 1048576));
        for (File resFile : resFileList) {
            zipFile(resFile, zipout, "");
        }
        zipout.setComment(comment);
        zipout.close();
    }

    public static void upZipFile(File zipFile, String folderPath) throws ZipException, IOException {
        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            desDir.mkdirs();
        }
        ZipFile zf = new ZipFile(zipFile);
        Enumeration<?> entries = zf.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            InputStream in = zf.getInputStream(entry);
            File desFile = new File(new String(new StringBuilder(String.valueOf(folderPath)).append(File.separator).append(entry.getName()).toString().getBytes("8859_1"), "GB2312"));
            if (!desFile.exists()) {
                File fileParentDir = desFile.getParentFile();
                if (!fileParentDir.exists()) {
                    fileParentDir.mkdirs();
                }
                desFile.createNewFile();
            }
            OutputStream out = new FileOutputStream(desFile);
            byte[] buffer = new byte[1048576];
            while (true) {
                int realLength = in.read(buffer);
                if (realLength <= 0) {
                    break;
                }
                out.write(buffer, 0, realLength);
            }
            in.close();
            out.close();
        }
    }

    public static ArrayList<File> upZipSelectedFile(File zipFile, String folderPath, String nameContains) throws ZipException, IOException {
        ArrayList<File> fileList = new ArrayList();
        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            desDir.mkdir();
        }
        ZipFile zf = new ZipFile(zipFile);
        Enumeration<?> entries = zf.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            if (entry.getName().contains(nameContains)) {
                InputStream in = zf.getInputStream(entry);
                File desFile = new File(new String(new StringBuilder(String.valueOf(folderPath)).append(File.separator).append(entry.getName()).toString().getBytes("8859_1"), "GB2312"));
                if (!desFile.exists()) {
                    File fileParentDir = desFile.getParentFile();
                    if (!fileParentDir.exists()) {
                        fileParentDir.mkdirs();
                    }
                    desFile.createNewFile();
                }
                OutputStream out = new FileOutputStream(desFile);
                byte[] buffer = new byte[1048576];
                while (true) {
                    int realLength = in.read(buffer);
                    if (realLength <= 0) {
                        break;
                    }
                    out.write(buffer, 0, realLength);
                }
                in.close();
                out.close();
                fileList.add(desFile);
            }
        }
        return fileList;
    }

    public static ArrayList<String> getEntriesNames(File zipFile) throws ZipException, IOException {
        ArrayList<String> entryNames = new ArrayList();
        Enumeration<?> entries = getEntriesEnumeration(zipFile);
        while (entries.hasMoreElements()) {
            entryNames.add(new String(getEntryName((ZipEntry) entries.nextElement()).getBytes("GB2312"), "8859_1"));
        }
        return entryNames;
    }

    public static Enumeration<?> getEntriesEnumeration(File zipFile) throws ZipException, IOException {
        return new ZipFile(zipFile).entries();
    }

    public static String getEntryComment(ZipEntry entry) throws UnsupportedEncodingException {
        return new String(entry.getComment().getBytes("GB2312"), "8859_1");
    }

    public static String getEntryName(ZipEntry entry) throws UnsupportedEncodingException {
        return new String(entry.getName().getBytes("GB2312"), "8859_1");
    }

    public static void zipFile(File resFile, ZipOutputStream zipout, String rootpath) throws FileNotFoundException, IOException {
        String rootpath2 = new String(new StringBuilder(String.valueOf(rootpath)).append(rootpath.trim().length() == 0 ? "" : File.separator).append(resFile.getName()).toString().getBytes("8859_1"), "GB2312");
        if (resFile.isDirectory()) {
            for (File file : resFile.listFiles()) {
                zipFile(file, zipout, rootpath2);
            }
            return;
        }
        byte[] buffer = new byte[1048576];
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(resFile), 1048576);
        zipout.putNextEntry(new ZipEntry(rootpath2));
        while (true) {
            int realLength = in.read(buffer);
            if (realLength == -1) {
                in.close();
                zipout.flush();
                zipout.closeEntry();
                return;
            }
            zipout.write(buffer, 0, realLength);
        }
    }
}
