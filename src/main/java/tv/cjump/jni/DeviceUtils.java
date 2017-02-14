package tv.cjump.jni;

import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;

public class DeviceUtils {
    public static final String ABI_MIPS = "mips";
    public static final String ABI_X86 = "x86";
    private static final int EM_386 = 3;
    private static final int EM_AARCH64 = 183;
    private static final int EM_ARM = 40;
    private static final int EM_MIPS = 8;
    private static ARCH sArch = ARCH.Unknown;

    public enum ARCH {
        Unknown,
        ARM,
        X86,
        MIPS,
        ARM64
    }

    public static synchronized ARCH getMyCpuArch() {
        IOException e;
        FileNotFoundException e2;
        ARCH arch;
        Throwable th;
        synchronized (DeviceUtils.class) {
            byte[] data = new byte[20];
            File libc = new File(Environment.getRootDirectory(), "lib/libc.so");
            if (libc.canRead()) {
                RandomAccessFile randomAccessFile = null;
                try {
                    RandomAccessFile fp = new RandomAccessFile(libc, "r");
                    try {
                        fp.readFully(data);
                        int machine = (data[19] << 8) | data[18];
                        switch (machine) {
                            case 3:
                                sArch = ARCH.X86;
                                break;
                            case 8:
                                sArch = ARCH.MIPS;
                                break;
                            case 40:
                                sArch = ARCH.ARM;
                                break;
                            case EM_AARCH64 /*183*/:
                                sArch = ARCH.ARM64;
                                break;
                            default:
                                Log.e("NativeBitmapFactory", "libc.so is unknown arch: " + Integer.toHexString(machine));
                                break;
                        }
                        if (fp != null) {
                            try {
                                fp.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                    } catch (FileNotFoundException e4) {
                        e2 = e4;
                        randomAccessFile = fp;
                        try {
                            e2.printStackTrace();
                            if (randomAccessFile != null) {
                                try {
                                    randomAccessFile.close();
                                } catch (IOException e32) {
                                    e32.printStackTrace();
                                }
                            }
                            arch = sArch;
                            return arch;
                        } catch (Throwable th2) {
                            th = th2;
                            if (randomAccessFile != null) {
                                try {
                                    randomAccessFile.close();
                                } catch (IOException e322) {
                                    e322.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (IOException e5) {
                        e322 = e5;
                        randomAccessFile = fp;
                        e322.printStackTrace();
                        if (randomAccessFile != null) {
                            try {
                                randomAccessFile.close();
                            } catch (IOException e3222) {
                                e3222.printStackTrace();
                            }
                        }
                        arch = sArch;
                        return arch;
                    } catch (Throwable th3) {
                        th = th3;
                        randomAccessFile = fp;
                        if (randomAccessFile != null) {
                            randomAccessFile.close();
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e6) {
                    e2 = e6;
                    e2.printStackTrace();
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                    arch = sArch;
                    return arch;
                } catch (IOException e7) {
                    e3222 = e7;
                    e3222.printStackTrace();
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                    arch = sArch;
                    return arch;
                }
            }
            arch = sArch;
        }
        return arch;
    }

    public static String get_CPU_ABI() {
        return Build.CPU_ABI;
    }

    public static String get_CPU_ABI2() {
        try {
            Field field = Build.class.getDeclaredField("CPU_ABI2");
            if (field == null) {
                return null;
            }
            Object fieldValue = field.get(null);
            if (fieldValue instanceof String) {
                return (String) fieldValue;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean supportABI(String requestAbi) {
        String abi = get_CPU_ABI();
        if (!TextUtils.isEmpty(abi) && abi.equalsIgnoreCase(requestAbi)) {
            return true;
        }
        if (TextUtils.isEmpty(get_CPU_ABI2()) || !abi.equalsIgnoreCase(requestAbi)) {
            return false;
        }
        return true;
    }

    public static boolean supportX86() {
        return supportABI(ABI_X86);
    }

    public static boolean supportMips() {
        return supportABI(ABI_MIPS);
    }

    public static boolean isARMSimulatedByX86() {
        return !supportX86() && ARCH.X86.equals(getMyCpuArch());
    }

    public static boolean isMiBox2Device() {
        return Build.MANUFACTURER.equalsIgnoreCase("Xiaomi") && Build.PRODUCT.equalsIgnoreCase("dredd");
    }

    public static boolean isMagicBoxDevice() {
        return Build.MANUFACTURER.equalsIgnoreCase("MagicBox") && Build.PRODUCT.equalsIgnoreCase("MagicBox");
    }

    public static boolean isProblemBoxDevice() {
        return isMiBox2Device() || isMagicBoxDevice();
    }

    public static boolean isRealARMArch() {
        return (supportABI("armeabi-v7a") || supportABI("armeabi")) && ARCH.ARM.equals(getMyCpuArch());
    }

    public static boolean isRealX86Arch() {
        return supportABI(ABI_X86) || ARCH.X86.equals(getMyCpuArch());
    }
}
