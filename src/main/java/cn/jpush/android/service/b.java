package cn.jpush.android.service;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import cn.jpush.android.data.c;
import cn.jpush.android.data.i;
import cn.jpush.android.data.s;
import cn.jpush.android.g;
import cn.jpush.android.util.h;
import cn.jpush.android.util.m;
import cn.jpush.android.util.o;
import cn.jpush.android.util.p;
import cn.jpush.android.util.z;
import com.letv.ads.ex.utils.PlayConstantUtils.SPConstant;
import com.letv.android.client.worldcup.util.StoreUtils;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.pp.utils.NetworkUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public final class b {
    public static boolean b = true;
    private static final String[] z;
    public boolean a = false;
    private c c = null;
    private long d = 0;
    private long e = 0;
    private Bundle f;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 22;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "\u0011o\u001dF7\u001eoI\u0000*\u001aoI\u0012,\u0002k\u0005F/\u0013d\u000e\u0012+Vl\u001b\t.Vb\u001d\u00123Vc\u001aFsX";
        r0 = -1;
        r4 = r3;
    L_0x0009:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002e;
    L_0x0012:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0017:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x0113;
            case 1: goto L_0x0117;
            case 2: goto L_0x011b;
            case 3: goto L_0x011f;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 67;
    L_0x0020:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002c;
    L_0x0028:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0017;
    L_0x002c:
        r5 = r1;
        r1 = r7;
    L_0x002e:
        if (r5 > r6) goto L_0x0012;
    L_0x0030:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0044;
            case 1: goto L_0x004c;
            case 2: goto L_0x0054;
            case 3: goto L_0x005c;
            case 4: goto L_0x0064;
            case 5: goto L_0x006c;
            case 6: goto L_0x0074;
            case 7: goto L_0x007d;
            case 8: goto L_0x0087;
            case 9: goto L_0x0092;
            case 10: goto L_0x009d;
            case 11: goto L_0x00a8;
            case 12: goto L_0x00b3;
            case 13: goto L_0x00be;
            case 14: goto L_0x00c9;
            case 15: goto L_0x00d4;
            case 16: goto L_0x00df;
            case 17: goto L_0x00ea;
            case 18: goto L_0x00f5;
            case 19: goto L_0x0100;
            case 20: goto L_0x010b;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "5e\u0007\u0012&\u0018~D*&\u0018m\u001d\u000e";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\"b\fF1\u0013y\u0006\u00131\u0015oI\u0002,\u0013yI\b,\u0002*\f\u001e*\u0005~IKc";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "&k\u001b\u0007.Vo\u001b\u0014,\u0004*HGc\u0003x\u0005\\";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "Z*\u000f\u000f/\u0013^\u0006\u0012\"\u001aF\f\b$\u0002bS";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "Vl\u0000\n&8k\u0004\u0003y";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "Z*\u000f\u000f/\u0013D\b\u000b&L";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "Vy\b\u0010&\u0010c\u0005\u0003\u0013\u0017~\u0001\\";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "\u0018o\u001d\u0011,\u0004aI\u0005,\u0018d\f\u00057Vy\u001d\u00077\u0003yI\u0005,\u0012oI\u0013-\u0013r\u0019\u0003 \u0002o\rFnV";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "\u0005~\u0006\u0016c\u0012e\u001e\b/\u0019k\rF!\u000f*\u001c\u0015&\u0004$";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "\u0017i\u001d\u000f,\u00180\r\t4\u0018f\u0006\u0007'V'I\u00131\u001a0";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = "3r\u001a\u000f7Vl\u0000\n&Vf\f\b$\u0002bS";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        r2 = 12;
        r1 = "\u0005~\b\u00147&e\u001a\u0012*\u0019dSF";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a8:
        r3[r2] = r1;
        r2 = 13;
        r1 = "Xk\u0019\r";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b3:
        r3[r2] = r1;
        r2 = 14;
        r1 = "Z*\u001a\u00075\u0013L\u0000\n&&k\u001d\u000ey";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00be:
        r3[r2] = r1;
        r2 = 15;
        r1 = "5e\u0007\b&\u0015~I\u0012*\u001boI\t6\u0002&I\u00121\u000f*\u001b\u00030\u0002*DF";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00c9:
        r3[r2] = r1;
        r2 = 16;
        r1 = "\u001bn\\F \u001eo\n\rc\u0013x\u001b\t1Z*\u001d\u0014:Vk\u000e\u0007*\u0018*DF";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d4:
        r3[r2] = r1;
        r2 = 17;
        r1 = "2e\u001e\b/\u0019k\rF\"\u0011k\u0000\boV~\u001b\u001fc\u0004o\u001a\u0012c[*";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00df:
        r3[r2] = r1;
        r2 = 18;
        r1 = "\u0014s\u001d\u00030K";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00ea:
        r3[r2] = r1;
        r2 = 19;
        r1 = "5f\u0006\u0015&";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f5:
        r3[r2] = r1;
        r2 = 20;
        r1 = "$k\u0007\u0001&";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0100:
        r3[r2] = r1;
        r2 = 21;
        r1 = "5e\u0007\b&\u0015~\u0000\t-";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x010b:
        r3[r2] = r1;
        z = r4;
        r0 = 1;
        b = r0;
        return;
    L_0x0113:
        r9 = 118; // 0x76 float:1.65E-43 double:5.83E-322;
        goto L_0x0020;
    L_0x0117:
        r9 = 10;
        goto L_0x0020;
    L_0x011b:
        r9 = 105; // 0x69 float:1.47E-43 double:5.2E-322;
        goto L_0x0020;
    L_0x011f:
        r9 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.b.<clinit>():void");
    }

    public b(Context context, c cVar, Bundle bundle, d dVar, int i) {
        int i2 = 0;
        z.a();
        this.a = false;
        this.f = bundle;
        this.c = new c(this, context.getMainLooper(), dVar);
        this.c.sendEmptyMessageDelayed(0, SPConstant.DELAY_BUFFER_DURATION);
        while (b) {
            if (this.a) {
                z.c();
                this.c.removeCallbacksAndMessages(null);
                dVar.a(1);
                return;
            } else if (cVar.z == 0) {
                z.d();
                if (dVar != null) {
                    this.a = true;
                    DownloadService.a.remove(cVar);
                    this.c.removeCallbacksAndMessages(null);
                    dVar.a(2);
                    return;
                }
                return;
            } else if (i2 >= 3) {
                z.d();
                if (dVar != null) {
                    this.a = true;
                    DownloadService.a.remove(cVar);
                    this.c.removeCallbacksAndMessages(null);
                    dVar.a(2);
                    return;
                }
                return;
            } else {
                int a = a(context, dVar, cVar);
                cVar.z--;
                if (a == -1) {
                    new StringBuilder(z[15]).append(cVar.z);
                    z.b();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                    }
                } else if (a == 0) {
                    new StringBuilder(z[17]).append(cVar.z);
                    z.b();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e2) {
                    }
                } else if (a == 1) {
                    z.b();
                    this.c.removeCallbacksAndMessages(null);
                    this.a = true;
                    return;
                } else if (a == 2) {
                    new StringBuilder(z[16]).append(cVar.z);
                    z.b();
                    i2++;
                } else if (a == -3) {
                    this.a = true;
                    DownloadService.a.remove(cVar);
                    this.c.removeCallbacksAndMessages(null);
                    dVar.a(3);
                    return;
                } else {
                    z.b();
                    this.a = true;
                    DownloadService.a.remove(cVar);
                    this.c.removeCallbacksAndMessages(null);
                    dVar.a(2);
                    return;
                }
            }
        }
        z.c();
        this.c.removeCallbacksAndMessages(null);
        this.a = true;
        dVar.a(1);
    }

    private static int a(long j) {
        long j2 = j / StoreUtils.DEFAULT_SDCARD_SIZE;
        int i = j2 < 1 ? 10 : j2 > 5 ? 50 : (int) (j2 * 10);
        return (int) (((double) i) * 1.1d);
    }

    private int a(Context context, d dVar, c cVar) {
        InputStream inputStream;
        BufferedInputStream bufferedInputStream;
        InputStream inputStream2;
        FileOutputStream fileOutputStream;
        InputStream inputStream3;
        BufferedOutputStream bufferedOutputStream;
        Throwable th;
        OutputStream fileOutputStream2;
        OutputStream outputStream;
        String d = cVar.d();
        String str = "";
        if (cVar.a()) {
            str = ((i) cVar).M;
            str = (str == null || "".equals(str)) ? null : str.trim();
        } else if (cVar.b()) {
            str = ((s) cVar).H;
            str = (str == null || "".equals(str)) ? null : str.trim();
        }
        String str2 = "";
        String str3 = "";
        if (cVar.a()) {
            str2 = m.a(context);
            str3 = cVar.c + z[13];
        } else if (cVar.b()) {
            str2 = m.b(context);
            str3 = cVar.c;
        } else if (cVar.e()) {
            str2 = m.b(context, cVar.c);
            str3 = cVar.c + o.b(cVar.d());
        }
        if (TextUtils.isEmpty(d) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            new StringBuilder(z[3]).append(d).append(z[7]).append(str2).append(z[5]).append(str3);
            z.e();
            return -2;
        }
        new StringBuilder(z[10]).append(d).append(z[14]).append(str2).append(z[6]).append(str3);
        z.c();
        File file = new File(str2);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        long j = this.f.getLong(d, -1);
        long j2 = 0;
        InputStream inputStream4 = null;
        BufferedInputStream bufferedInputStream2 = null;
        FileOutputStream fileOutputStream3 = null;
        BufferedOutputStream bufferedOutputStream2 = null;
        InputStream inputStream5 = null;
        File file2;
        BufferedInputStream bufferedInputStream3;
        byte[] bArr;
        int read;
        long a;
        if (j <= 0) {
            file2 = new File(str2, str3);
            if (!file2.exists() || file2.length() <= 0) {
                z.a();
                HttpURLConnection a2 = a(d, -1);
                try {
                    inputStream = a2.getInputStream();
                    if (inputStream != null) {
                        try {
                            int responseCode = a2.getResponseCode();
                            if (responseCode == 200) {
                                long a3 = a(a2);
                                this.f.putLong(d, a3);
                                cVar.z = a(a3);
                                if (inputStream != null) {
                                    try {
                                        bufferedInputStream = new BufferedInputStream(inputStream);
                                    } catch (NumberFormatException e) {
                                        inputStream2 = inputStream;
                                        fileOutputStream = null;
                                        bufferedInputStream3 = null;
                                        inputStream3 = inputStream;
                                        bufferedOutputStream = null;
                                        try {
                                            z.i();
                                            if (inputStream2 != null) {
                                                try {
                                                    inputStream2.close();
                                                } catch (IOException e2) {
                                                }
                                            }
                                            a(inputStream3, bufferedInputStream3, fileOutputStream, bufferedOutputStream, a2);
                                            return -2;
                                        } catch (Throwable th2) {
                                            bufferedOutputStream2 = bufferedOutputStream;
                                            fileOutputStream3 = fileOutputStream;
                                            bufferedInputStream = bufferedInputStream3;
                                            inputStream4 = inputStream3;
                                            inputStream = inputStream2;
                                            th = th2;
                                            if (inputStream != null) {
                                                try {
                                                    inputStream.close();
                                                } catch (IOException e3) {
                                                }
                                            }
                                            a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                            throw th;
                                        }
                                    } catch (ProtocolException e4) {
                                        bufferedInputStream = null;
                                        inputStream4 = inputStream;
                                        try {
                                            z.i();
                                            if (inputStream != null) {
                                                try {
                                                    inputStream.close();
                                                } catch (IOException e5) {
                                                }
                                            }
                                            a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                            return -2;
                                        } catch (Throwable th3) {
                                            th = th3;
                                            if (inputStream != null) {
                                                inputStream.close();
                                            }
                                            a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                            throw th;
                                        }
                                    } catch (IllegalStateException e6) {
                                        bufferedInputStream = null;
                                        inputStream4 = inputStream;
                                        z.i();
                                        if (inputStream != null) {
                                            try {
                                                inputStream.close();
                                            } catch (IOException e7) {
                                            }
                                        }
                                        a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                        return -2;
                                    } catch (FileNotFoundException e8) {
                                        bufferedInputStream = null;
                                        inputStream4 = inputStream;
                                        z.i();
                                        if (inputStream != null) {
                                            try {
                                                inputStream.close();
                                            } catch (IOException e9) {
                                            }
                                        }
                                        a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                        return -2;
                                    } catch (IOException e10) {
                                        bufferedInputStream = null;
                                        inputStream4 = inputStream;
                                        z.g();
                                        if (inputStream != null) {
                                            try {
                                                inputStream.close();
                                            } catch (IOException e11) {
                                            }
                                        }
                                        a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                        return -1;
                                    } catch (g e12) {
                                        bufferedInputStream = null;
                                        inputStream4 = inputStream;
                                        z.h();
                                        if (inputStream != null) {
                                            try {
                                                inputStream.close();
                                            } catch (IOException e13) {
                                            }
                                        }
                                        a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                        return -2;
                                    } catch (Throwable th4) {
                                        th = th4;
                                        bufferedInputStream = null;
                                        inputStream4 = inputStream;
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                        throw th;
                                    }
                                    try {
                                        file2.delete();
                                        file2.createNewFile();
                                        fileOutputStream2 = new FileOutputStream(file2);
                                    } catch (NumberFormatException e14) {
                                        inputStream2 = inputStream;
                                        fileOutputStream = null;
                                        bufferedInputStream3 = bufferedInputStream;
                                        inputStream3 = inputStream;
                                        bufferedOutputStream = null;
                                        z.i();
                                        if (inputStream2 != null) {
                                            inputStream2.close();
                                        }
                                        a(inputStream3, bufferedInputStream3, fileOutputStream, bufferedOutputStream, a2);
                                        return -2;
                                    } catch (ProtocolException e15) {
                                        inputStream4 = inputStream;
                                        z.i();
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                        return -2;
                                    } catch (IllegalStateException e16) {
                                        inputStream4 = inputStream;
                                        z.i();
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                        return -2;
                                    } catch (FileNotFoundException e17) {
                                        inputStream4 = inputStream;
                                        z.i();
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                        return -2;
                                    } catch (IOException e18) {
                                        inputStream4 = inputStream;
                                        z.g();
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                        return -1;
                                    } catch (g e19) {
                                        inputStream4 = inputStream;
                                        z.h();
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                        return -2;
                                    } catch (Throwable th5) {
                                        th = th5;
                                        inputStream4 = inputStream;
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                        throw th;
                                    }
                                    try {
                                        BufferedOutputStream bufferedOutputStream3 = new BufferedOutputStream(fileOutputStream2);
                                        try {
                                            bArr = new byte[1024];
                                            while (true) {
                                                read = bufferedInputStream.read(bArr);
                                                if (read == -1) {
                                                    break;
                                                } else if (this.a) {
                                                    z.d();
                                                    throw new g(z[9]);
                                                } else {
                                                    bufferedOutputStream3.write(bArr, 0, read);
                                                    j2 += (long) read;
                                                    this.d = j2;
                                                    this.e = a3;
                                                }
                                            }
                                            bufferedOutputStream3.flush();
                                            if (file2 != null && file2.length() == a3 && h.a(r4, file2)) {
                                                this.f.remove(d);
                                                if (dVar != null) {
                                                    dVar.a(file2.getAbsolutePath(), false);
                                                }
                                                if (inputStream != null) {
                                                    try {
                                                        inputStream.close();
                                                    } catch (IOException e20) {
                                                    }
                                                }
                                                a(inputStream, bufferedInputStream, fileOutputStream2, bufferedOutputStream3, a2);
                                                return 1;
                                            }
                                            z.d();
                                            if (file2.delete()) {
                                                if (inputStream != null) {
                                                    try {
                                                        inputStream.close();
                                                    } catch (IOException e21) {
                                                    }
                                                }
                                                a(inputStream, bufferedInputStream, fileOutputStream2, bufferedOutputStream3, a2);
                                                return 2;
                                            }
                                            z.e();
                                            if (inputStream != null) {
                                                try {
                                                    inputStream.close();
                                                } catch (IOException e22) {
                                                }
                                            }
                                            a(inputStream, bufferedInputStream, fileOutputStream2, bufferedOutputStream3, a2);
                                            return -2;
                                        } catch (NumberFormatException e23) {
                                            inputStream2 = inputStream;
                                            inputStream3 = inputStream;
                                            bufferedOutputStream = bufferedOutputStream3;
                                            fileOutputStream = fileOutputStream2;
                                            bufferedInputStream3 = bufferedInputStream;
                                        } catch (ProtocolException e24) {
                                            bufferedOutputStream2 = bufferedOutputStream3;
                                            fileOutputStream3 = fileOutputStream2;
                                            inputStream4 = inputStream;
                                        } catch (IllegalStateException e25) {
                                            bufferedOutputStream2 = bufferedOutputStream3;
                                            outputStream = fileOutputStream2;
                                            inputStream4 = inputStream;
                                        } catch (FileNotFoundException e26) {
                                            bufferedOutputStream2 = bufferedOutputStream3;
                                            outputStream = fileOutputStream2;
                                            inputStream4 = inputStream;
                                        } catch (IOException e27) {
                                            bufferedOutputStream2 = bufferedOutputStream3;
                                            outputStream = fileOutputStream2;
                                            inputStream4 = inputStream;
                                        } catch (g e28) {
                                            bufferedOutputStream2 = bufferedOutputStream3;
                                            outputStream = fileOutputStream2;
                                            inputStream4 = inputStream;
                                        } catch (Throwable th6) {
                                            th = th6;
                                            bufferedOutputStream2 = bufferedOutputStream3;
                                            outputStream = fileOutputStream2;
                                            inputStream4 = inputStream;
                                        }
                                    } catch (NumberFormatException e29) {
                                        inputStream2 = inputStream;
                                        OutputStream outputStream2 = fileOutputStream2;
                                        inputStream3 = inputStream;
                                        bufferedInputStream3 = bufferedInputStream;
                                        bufferedOutputStream = null;
                                        z.i();
                                        if (inputStream2 != null) {
                                            inputStream2.close();
                                        }
                                        a(inputStream3, bufferedInputStream3, fileOutputStream, bufferedOutputStream, a2);
                                        return -2;
                                    } catch (ProtocolException e30) {
                                        outputStream = fileOutputStream2;
                                        inputStream4 = inputStream;
                                        z.i();
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                        return -2;
                                    } catch (IllegalStateException e31) {
                                        outputStream = fileOutputStream2;
                                        inputStream4 = inputStream;
                                        z.i();
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                        return -2;
                                    } catch (FileNotFoundException e32) {
                                        outputStream = fileOutputStream2;
                                        inputStream4 = inputStream;
                                        z.i();
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                        return -2;
                                    } catch (IOException e33) {
                                        outputStream = fileOutputStream2;
                                        inputStream4 = inputStream;
                                        z.g();
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                        return -1;
                                    } catch (g e34) {
                                        outputStream = fileOutputStream2;
                                        inputStream4 = inputStream;
                                        z.h();
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                        return -2;
                                    } catch (Throwable th7) {
                                        th = th7;
                                        outputStream = fileOutputStream2;
                                        inputStream4 = inputStream;
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                                        throw th;
                                    }
                                }
                                z.d();
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException e35) {
                                    }
                                }
                                a(inputStream, null, null, null, a2);
                                return 0;
                            } else if (responseCode == LeMessageIds.MSG_DLNA_LIVE_PROTOCOL) {
                                new StringBuilder(z[2]).append(d);
                                z.b();
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException e36) {
                                    }
                                }
                                a(null, null, null, null, a2);
                                return -3;
                            } else {
                                new StringBuilder(z[8]).append(responseCode);
                                z.d();
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException e37) {
                                    }
                                }
                                a(null, null, null, null, a2);
                                return -2;
                            }
                        } catch (NumberFormatException e38) {
                            inputStream2 = inputStream;
                            fileOutputStream = null;
                            bufferedInputStream3 = null;
                            inputStream3 = null;
                            bufferedOutputStream = null;
                            z.i();
                            if (inputStream2 != null) {
                                inputStream2.close();
                            }
                            a(inputStream3, bufferedInputStream3, fileOutputStream, bufferedOutputStream, a2);
                            return -2;
                        } catch (ProtocolException e39) {
                            bufferedInputStream = null;
                            z.i();
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                            return -2;
                        } catch (IllegalStateException e40) {
                            bufferedInputStream = null;
                            z.i();
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                            return -2;
                        } catch (FileNotFoundException e41) {
                            bufferedInputStream = null;
                            z.i();
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                            return -2;
                        } catch (IOException e42) {
                            bufferedInputStream = null;
                            z.g();
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                            return -1;
                        } catch (g e43) {
                            bufferedInputStream = null;
                            z.h();
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                            return -2;
                        } catch (Throwable th8) {
                            th = th8;
                            bufferedInputStream = null;
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                            throw th;
                        }
                    }
                    z.d();
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e44) {
                        }
                    }
                    a(null, null, null, null, a2);
                    return 0;
                } catch (NumberFormatException e45) {
                    inputStream2 = null;
                    bufferedOutputStream = null;
                    fileOutputStream = null;
                    bufferedInputStream3 = null;
                    inputStream3 = null;
                    z.i();
                    if (inputStream2 != null) {
                        inputStream2.close();
                    }
                    a(inputStream3, bufferedInputStream3, fileOutputStream, bufferedOutputStream, a2);
                    return -2;
                } catch (ProtocolException e46) {
                    inputStream = null;
                    bufferedInputStream = null;
                    z.i();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                    return -2;
                } catch (IllegalStateException e47) {
                    inputStream = null;
                    bufferedInputStream = null;
                    z.i();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                    return -2;
                } catch (FileNotFoundException e48) {
                    inputStream = null;
                    bufferedInputStream = null;
                    z.i();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                    return -2;
                } catch (IOException e49) {
                    inputStream = null;
                    bufferedInputStream = null;
                    z.g();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                    return -1;
                } catch (g e50) {
                    inputStream = null;
                    bufferedInputStream = null;
                    z.h();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                    return -2;
                } catch (Throwable th9) {
                    th = th9;
                    inputStream = null;
                    bufferedInputStream = null;
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    a(inputStream4, bufferedInputStream, fileOutputStream3, bufferedOutputStream2, a2);
                    throw th;
                }
            } else if (file2.length() > 0) {
                HttpURLConnection a4 = a(d, -1);
                try {
                    a4.getInputStream();
                    a = a(a4);
                    if (file2.length() == a && h.a(r4, file2)) {
                        z.b();
                        if (dVar != null) {
                            dVar.a(file2.getAbsolutePath(), true);
                        }
                        return 1;
                    }
                    new StringBuilder(z[11]).append(file2.length()).append(z[4]).append(a);
                    z.c();
                    if (file2.delete()) {
                        return 2;
                    }
                    z.e();
                    return -2;
                } catch (ProtocolException e51) {
                    z.i();
                    return -2;
                } catch (IOException e52) {
                    z.g();
                    return -1;
                } catch (g e53) {
                    z.h();
                    return -2;
                }
            } else {
                z.e();
                return -2;
            }
        }
        z.c();
        file2 = new File(str2, str3);
        if (file2.exists()) {
            z.a();
            a = file2.length();
            j2 = a;
        } else {
            z.a();
            a = 0;
            try {
                file2.createNewFile();
            } catch (IOException e54) {
                z.i();
                return -2;
            }
        }
        new StringBuilder(z[12]).append(a);
        z.c();
        if (cVar.z == -1) {
            z.b();
            cVar.z = a(j);
        }
        HttpURLConnection a5 = a(d, a);
        try {
            inputStream5 = a5.getInputStream();
            if (inputStream5 != null) {
                int responseCode2 = a5.getResponseCode();
                if (responseCode2 == 200 || responseCode2 == LeMessageIds.MSG_MAIN_GET_CURR_PAGE) {
                    if (a(a5) + j2 != j) {
                        z.e();
                        this.f.remove(d);
                        if (!file2.delete()) {
                            z.e();
                        }
                        if (inputStream5 != null) {
                            try {
                                inputStream5.close();
                            } catch (IOException e55) {
                            }
                        }
                        a(null, null, null, null, a5);
                        return 0;
                    } else if (inputStream5 != null) {
                        try {
                            bufferedInputStream3 = new BufferedInputStream(inputStream5);
                        } catch (NumberFormatException e56) {
                            inputStream4 = inputStream5;
                            try {
                                z.i();
                                if (inputStream5 != null) {
                                    try {
                                        inputStream5.close();
                                    } catch (IOException e57) {
                                    }
                                }
                                a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                                return -2;
                            } catch (Throwable th10) {
                                th = th10;
                                if (inputStream5 != null) {
                                    try {
                                        inputStream5.close();
                                    } catch (IOException e58) {
                                    }
                                }
                                a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                                throw th;
                            }
                        } catch (ProtocolException e59) {
                            inputStream4 = inputStream5;
                            z.i();
                            if (inputStream5 != null) {
                                try {
                                    inputStream5.close();
                                } catch (IOException e60) {
                                }
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            return -2;
                        } catch (IllegalStateException e61) {
                            inputStream4 = inputStream5;
                            z.i();
                            if (inputStream5 != null) {
                                try {
                                    inputStream5.close();
                                } catch (IOException e62) {
                                }
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            return -2;
                        } catch (FileNotFoundException e63) {
                            inputStream4 = inputStream5;
                            z.i();
                            if (inputStream5 != null) {
                                try {
                                    inputStream5.close();
                                } catch (IOException e64) {
                                }
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            return -2;
                        } catch (IOException e65) {
                            inputStream4 = inputStream5;
                            z.g();
                            if (inputStream5 != null) {
                                try {
                                    inputStream5.close();
                                } catch (IOException e66) {
                                }
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            return -1;
                        } catch (g e67) {
                            inputStream4 = inputStream5;
                            z.h();
                            if (inputStream5 != null) {
                                try {
                                    inputStream5.close();
                                } catch (IOException e68) {
                                }
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            return -2;
                        } catch (Throwable th11) {
                            th = th11;
                            inputStream4 = inputStream5;
                            if (inputStream5 != null) {
                                inputStream5.close();
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            throw th;
                        }
                        try {
                            outputStream2 = new FileOutputStream(file2, true);
                        } catch (NumberFormatException e69) {
                            bufferedInputStream2 = bufferedInputStream3;
                            inputStream4 = inputStream5;
                            z.i();
                            if (inputStream5 != null) {
                                inputStream5.close();
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            return -2;
                        } catch (ProtocolException e70) {
                            bufferedInputStream2 = bufferedInputStream3;
                            inputStream4 = inputStream5;
                            z.i();
                            if (inputStream5 != null) {
                                inputStream5.close();
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            return -2;
                        } catch (IllegalStateException e71) {
                            bufferedInputStream2 = bufferedInputStream3;
                            inputStream4 = inputStream5;
                            z.i();
                            if (inputStream5 != null) {
                                inputStream5.close();
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            return -2;
                        } catch (FileNotFoundException e72) {
                            bufferedInputStream2 = bufferedInputStream3;
                            inputStream4 = inputStream5;
                            z.i();
                            if (inputStream5 != null) {
                                inputStream5.close();
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            return -2;
                        } catch (IOException e73) {
                            bufferedInputStream2 = bufferedInputStream3;
                            inputStream4 = inputStream5;
                            z.g();
                            if (inputStream5 != null) {
                                inputStream5.close();
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            return -1;
                        } catch (g e74) {
                            bufferedInputStream2 = bufferedInputStream3;
                            inputStream4 = inputStream5;
                            z.h();
                            if (inputStream5 != null) {
                                inputStream5.close();
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            return -2;
                        } catch (Throwable th12) {
                            th = th12;
                            bufferedInputStream2 = bufferedInputStream3;
                            inputStream4 = inputStream5;
                            if (inputStream5 != null) {
                                inputStream5.close();
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            throw th;
                        }
                        try {
                            bufferedOutputStream = new BufferedOutputStream(outputStream2);
                            try {
                                bArr = new byte[1024];
                                while (true) {
                                    read = bufferedInputStream3.read(bArr);
                                    if (read == -1) {
                                        break;
                                    } else if (this.a) {
                                        z.d();
                                        throw new g(z[9]);
                                    } else {
                                        bufferedOutputStream.write(bArr, 0, read);
                                        j2 += (long) read;
                                        this.d = j2;
                                        this.e = j;
                                    }
                                }
                                bufferedOutputStream.flush();
                                z.b();
                                if (file2 != null && file2.length() == j && h.a(r4, file2)) {
                                    this.f.remove(d);
                                    if (dVar != null) {
                                        dVar.a(file2.getAbsolutePath(), false);
                                    }
                                    if (inputStream5 != null) {
                                        try {
                                            inputStream5.close();
                                        } catch (IOException e75) {
                                        }
                                    }
                                    a(inputStream5, bufferedInputStream3, outputStream2, bufferedOutputStream, a5);
                                    return 1;
                                }
                                z.d();
                                if (file2.delete()) {
                                    if (inputStream5 != null) {
                                        try {
                                            inputStream5.close();
                                        } catch (IOException e76) {
                                        }
                                    }
                                    a(inputStream5, bufferedInputStream3, outputStream2, bufferedOutputStream, a5);
                                    return 2;
                                }
                                z.e();
                                if (inputStream5 != null) {
                                    try {
                                        inputStream5.close();
                                    } catch (IOException e77) {
                                    }
                                }
                                a(inputStream5, bufferedInputStream3, outputStream2, bufferedOutputStream, a5);
                                return -2;
                            } catch (NumberFormatException e78) {
                                bufferedOutputStream2 = bufferedOutputStream;
                                fileOutputStream3 = outputStream2;
                                bufferedInputStream2 = bufferedInputStream3;
                                inputStream4 = inputStream5;
                            } catch (ProtocolException e79) {
                                bufferedOutputStream2 = bufferedOutputStream;
                                fileOutputStream3 = outputStream2;
                                bufferedInputStream2 = bufferedInputStream3;
                                inputStream4 = inputStream5;
                            } catch (IllegalStateException e80) {
                                bufferedOutputStream2 = bufferedOutputStream;
                                outputStream = outputStream2;
                                bufferedInputStream2 = bufferedInputStream3;
                                inputStream4 = inputStream5;
                            } catch (FileNotFoundException e81) {
                                bufferedOutputStream2 = bufferedOutputStream;
                                outputStream = outputStream2;
                                bufferedInputStream2 = bufferedInputStream3;
                                inputStream4 = inputStream5;
                            } catch (IOException e82) {
                                bufferedOutputStream2 = bufferedOutputStream;
                                outputStream = outputStream2;
                                bufferedInputStream2 = bufferedInputStream3;
                                inputStream4 = inputStream5;
                            } catch (g e83) {
                                bufferedOutputStream2 = bufferedOutputStream;
                                outputStream = outputStream2;
                                bufferedInputStream2 = bufferedInputStream3;
                                inputStream4 = inputStream5;
                            } catch (Throwable th13) {
                                th = th13;
                                bufferedOutputStream2 = bufferedOutputStream;
                                outputStream = outputStream2;
                                bufferedInputStream2 = bufferedInputStream3;
                                inputStream4 = inputStream5;
                            }
                        } catch (NumberFormatException e84) {
                            outputStream = outputStream2;
                            bufferedInputStream2 = bufferedInputStream3;
                            inputStream4 = inputStream5;
                            z.i();
                            if (inputStream5 != null) {
                                inputStream5.close();
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            return -2;
                        } catch (ProtocolException e85) {
                            outputStream = outputStream2;
                            bufferedInputStream2 = bufferedInputStream3;
                            inputStream4 = inputStream5;
                            z.i();
                            if (inputStream5 != null) {
                                inputStream5.close();
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            return -2;
                        } catch (IllegalStateException e86) {
                            outputStream = outputStream2;
                            bufferedInputStream2 = bufferedInputStream3;
                            inputStream4 = inputStream5;
                            z.i();
                            if (inputStream5 != null) {
                                inputStream5.close();
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            return -2;
                        } catch (FileNotFoundException e87) {
                            outputStream = outputStream2;
                            bufferedInputStream2 = bufferedInputStream3;
                            inputStream4 = inputStream5;
                            z.i();
                            if (inputStream5 != null) {
                                inputStream5.close();
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            return -2;
                        } catch (IOException e88) {
                            outputStream = outputStream2;
                            bufferedInputStream2 = bufferedInputStream3;
                            inputStream4 = inputStream5;
                            z.g();
                            if (inputStream5 != null) {
                                inputStream5.close();
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            return -1;
                        } catch (g e89) {
                            outputStream = outputStream2;
                            bufferedInputStream2 = bufferedInputStream3;
                            inputStream4 = inputStream5;
                            z.h();
                            if (inputStream5 != null) {
                                inputStream5.close();
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            return -2;
                        } catch (Throwable th14) {
                            th = th14;
                            outputStream = outputStream2;
                            bufferedInputStream2 = bufferedInputStream3;
                            inputStream4 = inputStream5;
                            if (inputStream5 != null) {
                                inputStream5.close();
                            }
                            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
                            throw th;
                        }
                    } else {
                        z.d();
                        if (inputStream5 != null) {
                            try {
                                inputStream5.close();
                            } catch (IOException e90) {
                            }
                        }
                        a(inputStream5, null, null, null, a5);
                        return 0;
                    }
                } else if (responseCode2 == 416) {
                    z.e();
                    this.f.remove(d);
                    if (!file2.delete()) {
                        z.e();
                    }
                    if (inputStream5 != null) {
                        try {
                            inputStream5.close();
                        } catch (IOException e91) {
                        }
                    }
                    a(null, null, null, null, a5);
                    return 0;
                } else if (responseCode2 == LeMessageIds.MSG_DLNA_LIVE_PROTOCOL) {
                    new StringBuilder(z[2]).append(d);
                    z.b();
                    if (inputStream5 != null) {
                        try {
                            inputStream5.close();
                        } catch (IOException e92) {
                        }
                    }
                    a(null, null, null, null, a5);
                    return -3;
                } else {
                    new StringBuilder(z[8]).append(responseCode2);
                    z.d();
                    if (inputStream5 != null) {
                        try {
                            inputStream5.close();
                        } catch (IOException e93) {
                        }
                    }
                    a(null, null, null, null, a5);
                    return -2;
                }
            }
            z.d();
            if (inputStream5 != null) {
                try {
                    inputStream5.close();
                } catch (IOException e94) {
                }
            }
            a(null, null, null, null, a5);
            return 0;
        } catch (NumberFormatException e95) {
            z.i();
            if (inputStream5 != null) {
                inputStream5.close();
            }
            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
            return -2;
        } catch (ProtocolException e96) {
            z.i();
            if (inputStream5 != null) {
                inputStream5.close();
            }
            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
            return -2;
        } catch (IllegalStateException e97) {
            z.i();
            if (inputStream5 != null) {
                inputStream5.close();
            }
            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
            return -2;
        } catch (FileNotFoundException e98) {
            z.i();
            if (inputStream5 != null) {
                inputStream5.close();
            }
            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
            return -2;
        } catch (IOException e99) {
            z.g();
            if (inputStream5 != null) {
                inputStream5.close();
            }
            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
            return -1;
        } catch (g e100) {
            z.h();
            if (inputStream5 != null) {
                inputStream5.close();
            }
            a(inputStream4, bufferedInputStream2, fileOutputStream3, bufferedOutputStream2, a5);
            return -2;
        }
    }

    private static long a(HttpURLConnection httpURLConnection) {
        long longValue = Long.valueOf(httpURLConnection.getHeaderField(z[1])).longValue();
        if (longValue > 0) {
            return longValue;
        }
        throw new g(z[0]);
    }

    private static HttpURLConnection a(String str, long j) {
        HttpURLConnection httpURLConnection;
        IOException e;
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection.setRequestProperty(z[21], z[19]);
                if (j >= 0) {
                    httpURLConnection.setRequestProperty(z[20], new StringBuilder(z[18]).append(j).append(NetworkUtils.DELIMITER_LINE).toString());
                }
                p.a(httpURLConnection, false);
            } catch (IOException e2) {
                e = e2;
                e.printStackTrace();
                return httpURLConnection;
            }
        } catch (IOException e3) {
            IOException iOException = e3;
            httpURLConnection = null;
            e = iOException;
            e.printStackTrace();
            return httpURLConnection;
        }
        return httpURLConnection;
    }

    private static void a(InputStream inputStream, BufferedInputStream bufferedInputStream, FileOutputStream fileOutputStream, BufferedOutputStream bufferedOutputStream, HttpURLConnection httpURLConnection) {
        if (bufferedOutputStream != null) {
            try {
                bufferedOutputStream.close();
            } catch (IOException e) {
            }
        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e2) {
            }
        }
        if (bufferedInputStream != null) {
            try {
                bufferedInputStream.close();
            } catch (IOException e3) {
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e4) {
            }
        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }

    public static boolean a(int i) {
        return 2 == i || 3 == i;
    }
}
