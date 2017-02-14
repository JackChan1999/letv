package com.letv.redpacketsdk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
    public static String getDownloadFileName(String url) {
        String name = "";
        if (url != null) {
            return url.substring(url.lastIndexOf("/") + 1);
        }
        return name;
    }

    public static boolean saveFileToSDCardPublicDir(byte[] data, String type, String fileName) {
        Exception e;
        Throwable th;
        BufferedOutputStream bos = null;
        try {
            BufferedOutputStream bos2 = new BufferedOutputStream(new FileOutputStream(new File(Environment.getExternalStoragePublicDirectory(type), fileName)));
            try {
                bos2.write(data);
                bos2.flush();
                if (bos2 != null) {
                    try {
                        bos2.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                bos = bos2;
                return true;
            } catch (Exception e3) {
                e = e3;
                bos = bos2;
                try {
                    e.printStackTrace();
                    if (bos != null) {
                        try {
                            bos.close();
                        } catch (IOException e22) {
                            e22.printStackTrace();
                        }
                    }
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                    if (bos != null) {
                        try {
                            bos.close();
                        } catch (IOException e222) {
                            e222.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                bos = bos2;
                if (bos != null) {
                    bos.close();
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            e.printStackTrace();
            if (bos != null) {
                bos.close();
            }
            return false;
        }
    }

    public static Bitmap loadBitmapFromLocal(String filePath) {
        LogInfo.log("FileUtil", "loadBitmapFromLocal+filePath=" + filePath);
        byte[] data = loadFileFromLocal(filePath);
        if (data != null) {
            Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
            if (bm != null) {
                return bm;
            }
        }
        return null;
    }

    public static byte[] loadFileFromLocal(String filePath) {
        Exception e;
        Throwable th;
        BufferedInputStream bufferedInputStream = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        File file = new File(filePath);
        if (file.exists()) {
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                try {
                    byte[] buffer = new byte[8192];
                    while (true) {
                        int c = bis.read(buffer);
                        if (c == -1) {
                            break;
                        }
                        baos.write(buffer, 0, c);
                        baos.flush();
                    }
                    byte[] toByteArray = baos.toByteArray();
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (baos != null) {
                        try {
                            baos.close();
                        } catch (IOException e22) {
                            e22.printStackTrace();
                        }
                    }
                    bufferedInputStream = bis;
                    return toByteArray;
                } catch (Exception e3) {
                    e = e3;
                    bufferedInputStream = bis;
                    try {
                        e.printStackTrace();
                        if (bufferedInputStream != null) {
                            try {
                                bufferedInputStream.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        if (baos != null) {
                            try {
                                baos.close();
                            } catch (IOException e2222) {
                                e2222.printStackTrace();
                            }
                        }
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        if (bufferedInputStream != null) {
                            try {
                                bufferedInputStream.close();
                            } catch (IOException e22222) {
                                e22222.printStackTrace();
                            }
                        }
                        if (baos != null) {
                            try {
                                baos.close();
                            } catch (IOException e222222) {
                                e222222.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    bufferedInputStream = bis;
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                    }
                    if (baos != null) {
                        baos.close();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                e.printStackTrace();
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                if (baos != null) {
                    baos.close();
                }
                return null;
            }
        }
        return null;
    }

    public static String getCacheDir(Context context) {
        if (context == null || context.getExternalCacheDir() == null) {
            return "";
        }
        return context.getExternalCacheDir().getAbsolutePath();
    }

    public static boolean isFileExist(String filePath) {
        return new File(filePath).isFile();
    }
}
