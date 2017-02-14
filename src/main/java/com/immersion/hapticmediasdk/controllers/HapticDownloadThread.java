package com.immersion.hapticmediasdk.controllers;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import com.immersion.hapticmediasdk.models.HttpUnsuccessfulException;
import com.immersion.hapticmediasdk.utils.FileManager;
import com.immersion.hapticmediasdk.utils.Log;
import com.letv.core.messagebus.config.LeMessageIds;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import org.apache.http.HttpResponse;

public class HapticDownloadThread extends Thread {
    private static final String b0414Д0414041404140414 = "HapticDownloadThread";
    public static int b0429ЩЩЩЩЩ = 2;
    public static int b043Bл043B043B043B043B = 0;
    private static final int b044Eююююю = 60000;
    private static final int bД04140414041404140414 = 4096;
    public static int bл043B043B043B043B043B = 1;
    public static int bлл043B043B043B043B = 68;
    private volatile boolean b044E044E044Eююю = false;
    private Handler b044E044Eюююю;
    private Thread b044Eю044Eююю;
    private volatile boolean b044Eюю044Eюю = false;
    private FileManager bю044E044Eююю;
    private String bю044Eюююю;
    private boolean bюю044Eююю;
    private volatile boolean bююю044Eюю = false;

    public HapticDownloadThread(String str, Handler handler, boolean z, FileManager fileManager) {
        super(b0414Д0414041404140414);
        this.bю044Eюююю = str;
        this.b044E044Eюююю = handler;
        if (((bлл043B043B043B043B + bл043B043B043B043B043B) * bлл043B043B043B043B) % b0429ЩЩЩЩЩ != b043Bл043B043B043B043B) {
            bлл043B043B043B043B = bЩ0429ЩЩЩЩ();
            b043Bл043B043B043B043B = 79;
        }
        this.bюю044Eююю = z;
        this.bю044E044Eююю = fileManager;
        this.b044Eю044Eююю = this.b044E044Eюююю.getLooper().getThread();
    }

    public static int b04290429ЩЩЩЩ() {
        return 0;
    }

    public static int bЩ0429ЩЩЩЩ() {
        return 93;
    }

    public static int bЩЩ0429ЩЩЩ() {
        return 2;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean isFirstPacketReady() {
        /*
        r5 = this;
        r1 = 1;
        r2 = 0;
        monitor-enter(r5);
        r0 = r5.b044E044E044Eююю;	 Catch:{ all -> 0x002a }
        if (r0 != 0) goto L_0x001e;
    L_0x0007:
        r0 = r5.bююю044Eюю;	 Catch:{ all -> 0x002a }
        r3 = bлл043B043B043B043B;	 Catch:{ all -> 0x002a }
        r4 = bл043B043B043B043B043B;	 Catch:{ all -> 0x002a }
        r4 = r4 + r3;
        r3 = r3 * r4;
        r4 = b0429ЩЩЩЩЩ;	 Catch:{ all -> 0x002a }
        r3 = r3 % r4;
        switch(r3) {
            case 0: goto L_0x001c;
            default: goto L_0x0015;
        };	 Catch:{ all -> 0x002a }
    L_0x0015:
        r3 = 21;
        bлл043B043B043B043B = r3;	 Catch:{ all -> 0x002a }
        r3 = 2;
        b043Bл043B043B043B043B = r3;	 Catch:{ all -> 0x002a }
    L_0x001c:
        if (r0 == 0) goto L_0x0028;
    L_0x001e:
        r0 = r1;
    L_0x001f:
        switch(r2) {
            case 0: goto L_0x0026;
            case 1: goto L_0x001f;
            default: goto L_0x0022;
        };
    L_0x0022:
        switch(r1) {
            case 0: goto L_0x001f;
            case 1: goto L_0x0026;
            default: goto L_0x0025;
        };
    L_0x0025:
        goto L_0x0022;
    L_0x0026:
        monitor-exit(r5);
        return r0;
    L_0x0028:
        r0 = r2;
        goto L_0x001f;
    L_0x002a:
        r0 = move-exception;
        monitor-exit(r5);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticDownloadThread.isFirstPacketReady():boolean");
    }

    public void run() {
        String str = null;
        if (this.bюю044Eююю) {
            Process.setThreadPriority(10);
            try {
                HttpResponse executeGet = ImmersionHttpClient.getHttpClient().executeGet(this.bю044Eюююю, null, 60000);
                int statusCode = executeGet.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    writeToFile(executeGet.getEntity().getContent(), Integer.parseInt(executeGet.getFirstHeader(HttpRequest.HEADER_CONTENT_LENGTH).getValue()));
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder("HTTP STATUS CODE: ");
                stringBuilder.append(statusCode);
                switch (statusCode) {
                    case 400:
                        stringBuilder.append(" Bad Request");
                        break;
                    case LeMessageIds.MSG_DLNA_ALBUM_PROTOCOL /*403*/:
                        stringBuilder.append(" Forbidden");
                        break;
                    case LeMessageIds.MSG_DLNA_LIVE_PROTOCOL /*404*/:
                        stringBuilder.append(" Not Found");
                        break;
                    case LeMessageIds.MSG_FLOAT_BALL_REQUEST_DATA /*500*/:
                        stringBuilder.append(" Internal Server Error");
                        break;
                    case 502:
                        stringBuilder.append(" Bad Gateway");
                        break;
                    case 503:
                        stringBuilder.append(" Service Unavailable");
                        break;
                }
                throw new HttpUnsuccessfulException(statusCode, stringBuilder.toString());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            } catch (Serializable e2) {
                r1 = this.b044E044Eюююю.obtainMessage(8);
                Bundle bundle = new Bundle();
                bundle.putSerializable(HapticPlaybackThread.HAPTIC_DOWNLOAD_EXCEPTION_KEY, e2);
                r1.setData(bundle);
                if (this.b044Eю044Eююю.isAlive() && !this.b044Eюю044Eюю) {
                    while (true) {
                        try {
                            str.length();
                        } catch (Exception e3) {
                            Message obtainMessage;
                            bлл043B043B043B043B = 56;
                            this.b044E044Eюююю.sendMessage(obtainMessage);
                        }
                    }
                }
                Log.e(b0414Д0414041404140414, "Fail to download haptic file.");
                return;
            }
        }
        InputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(this.bю044Eюююю);
        } catch (FileNotFoundException e4) {
            e4.printStackTrace();
            fileInputStream = null;
        }
        if (fileInputStream != null) {
            try {
                writeToFile(fileInputStream, fileInputStream.available());
            } catch (IOException e5) {
                e5.printStackTrace();
            }
        }
    }

    public void terminate() {
        if (((bлл043B043B043B043B + bл043B043B043B043B043B) * bлл043B043B043B043B) % bЩЩ0429ЩЩЩ() != b04290429ЩЩЩЩ()) {
            bлл043B043B043B043B = 57;
            b043Bл043B043B043B043B = bЩ0429ЩЩЩЩ();
        }
        try {
            this.b044Eюю044Eюю = true;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean writeToFile(InputStream inputStream, int i) throws IOException {
        Bundle bundle;
        Throwable th;
        Closeable closeable;
        String str;
        Handler handler;
        int i2 = 0;
        Closeable closeable2 = null;
        try {
            byte[] bArr = new byte[4096];
            String str2;
            if (inputStream == null || i <= 0) {
                if (!this.b044E044E044Eююю) {
                    str2 = "downloaded an empty file";
                    Message obtainMessage = this.b044E044Eюююю.obtainMessage(8);
                    bundle = new Bundle();
                    bundle.putSerializable(HapticPlaybackThread.HAPTIC_DOWNLOAD_EXCEPTION_KEY, new FileNotFoundException(str2));
                    obtainMessage.setData(bundle);
                    if (this.b044Eю044Eююю.isAlive()) {
                        if (!this.b044Eюю044Eюю) {
                            this.b044E044Eюююю.sendMessage(obtainMessage);
                        }
                    }
                    Log.e(b0414Д0414041404140414, str2);
                }
                this.bю044E044Eююю.closeCloseable(null);
                this.bю044E044Eююю.closeCloseable(closeable2);
                this.bююю044Eюю = true;
                return false;
            }
            Message obtainMessage2;
            try {
                Closeable bufferedInputStream = new BufferedInputStream(inputStream);
                try {
                    closeable2 = this.bюю044Eююю ? this.bю044E044Eююю.makeOutputStreamForStreaming(this.bю044Eюююю) : this.bю044E044Eююю.makeOutputStream(this.bю044Eюююю);
                    if (closeable2 == null) {
                        if (!this.b044E044E044Eююю) {
                            str2 = "downloaded an empty file";
                            obtainMessage2 = this.b044E044Eюююю.obtainMessage(8);
                            bundle = new Bundle();
                            bundle.putSerializable(HapticPlaybackThread.HAPTIC_DOWNLOAD_EXCEPTION_KEY, new FileNotFoundException(str2));
                            obtainMessage2.setData(bundle);
                            if (this.b044Eю044Eююю.isAlive() && !this.b044Eюю044Eюю) {
                                this.b044E044Eюююю.sendMessage(obtainMessage2);
                            }
                            Log.e(b0414Д0414041404140414, str2);
                        }
                        this.bю044E044Eююю.closeCloseable(bufferedInputStream);
                        this.bю044E044Eююю.closeCloseable(closeable2);
                        this.bююю044Eюю = true;
                        return false;
                    }
                    try {
                        String str3;
                        if (this.bюю044Eююю) {
                            while (!isInterrupted() && !this.b044Eюю044Eюю) {
                                int read = bufferedInputStream.read(bArr, 0, 4096);
                                if (read < 0) {
                                    break;
                                }
                                closeable2.write(bArr, 0, read);
                                i2 += read;
                                if (this.b044Eю044Eююю.isAlive()) {
                                    if (!this.b044E044E044Eююю) {
                                        this.b044E044E044Eююю = true;
                                    }
                                    closeable2.flush();
                                    this.b044E044Eюююю.sendMessage(this.b044E044Eюююю.obtainMessage(3, i2, 0));
                                }
                            }
                        } else {
                            this.b044E044E044Eююю = true;
                            if (this.b044Eюю044Eюю) {
                                if (!this.b044E044E044Eююю) {
                                    str3 = "downloaded an empty file";
                                    obtainMessage2 = this.b044E044Eюююю.obtainMessage(8);
                                    bundle = new Bundle();
                                    Serializable fileNotFoundException = new FileNotFoundException(str3);
                                    String str4 = HapticPlaybackThread.HAPTIC_DOWNLOAD_EXCEPTION_KEY;
                                    if (((bлл043B043B043B043B + bл043B043B043B043B043B) * bлл043B043B043B043B) % b0429ЩЩЩЩЩ != b043Bл043B043B043B043B) {
                                        bлл043B043B043B043B = 13;
                                        b043Bл043B043B043B043B = 91;
                                    }
                                    bundle.putSerializable(str4, fileNotFoundException);
                                    obtainMessage2.setData(bundle);
                                    if (this.b044Eю044Eююю.isAlive() && !this.b044Eюю044Eюю) {
                                        this.b044E044Eюююю.sendMessage(obtainMessage2);
                                    }
                                    Log.e(b0414Д0414041404140414, str3);
                                }
                                this.bю044E044Eююю.closeCloseable(bufferedInputStream);
                                this.bю044E044Eююю.closeCloseable(closeable2);
                                this.bююю044Eюю = true;
                                return true;
                            }
                            this.b044E044Eюююю.sendMessage(this.b044E044Eюююю.obtainMessage(3, i, 0));
                        }
                        Log.i(b0414Д0414041404140414, "file download completed");
                        if (!this.b044E044E044Eююю) {
                            str3 = "downloaded an empty file";
                            obtainMessage2 = this.b044E044Eюююю.obtainMessage(8);
                            bundle = new Bundle();
                            bundle.putSerializable(HapticPlaybackThread.HAPTIC_DOWNLOAD_EXCEPTION_KEY, new FileNotFoundException(str3));
                            obtainMessage2.setData(bundle);
                            if (this.b044Eю044Eююю.isAlive()) {
                                if (!this.b044Eюю044Eюю) {
                                    this.b044E044Eюююю.sendMessage(obtainMessage2);
                                }
                            }
                            Log.e(b0414Д0414041404140414, str3);
                        }
                        this.bю044E044Eююю.closeCloseable(bufferedInputStream);
                        this.bю044E044Eююю.closeCloseable(closeable2);
                        this.bююю044Eюю = true;
                        return true;
                    } catch (Throwable th2) {
                        th = th2;
                        closeable = closeable2;
                        closeable2 = bufferedInputStream;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    closeable = closeable2;
                    closeable2 = bufferedInputStream;
                    if (!this.b044E044E044Eююю) {
                        str = "downloaded an empty file";
                        obtainMessage2 = this.b044E044Eюююю.obtainMessage(8);
                        bundle = new Bundle();
                        bundle.putSerializable(HapticPlaybackThread.HAPTIC_DOWNLOAD_EXCEPTION_KEY, new FileNotFoundException(str));
                        obtainMessage2.setData(bundle);
                        if (this.b044Eю044Eююю.isAlive() && !this.b044Eюю044Eюю) {
                            handler = this.b044E044Eюююю;
                            if (((bЩ0429ЩЩЩЩ() + bл043B043B043B043B043B) * bЩ0429ЩЩЩЩ()) % b0429ЩЩЩЩЩ != b043Bл043B043B043B043B) {
                                bлл043B043B043B043B = bЩ0429ЩЩЩЩ();
                                b043Bл043B043B043B043B = 13;
                            }
                            handler.sendMessage(obtainMessage2);
                        }
                        Log.e(b0414Д0414041404140414, str);
                    }
                    this.bю044E044Eююю.closeCloseable(closeable2);
                    this.bю044E044Eююю.closeCloseable(closeable);
                    this.bююю044Eюю = true;
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                closeable = closeable2;
                closeable2 = null;
                if (this.b044E044E044Eююю) {
                    str = "downloaded an empty file";
                    obtainMessage2 = this.b044E044Eюююю.obtainMessage(8);
                    bundle = new Bundle();
                    bundle.putSerializable(HapticPlaybackThread.HAPTIC_DOWNLOAD_EXCEPTION_KEY, new FileNotFoundException(str));
                    obtainMessage2.setData(bundle);
                    handler = this.b044E044Eюююю;
                    if (((bЩ0429ЩЩЩЩ() + bл043B043B043B043B043B) * bЩ0429ЩЩЩЩ()) % b0429ЩЩЩЩЩ != b043Bл043B043B043B043B) {
                        bлл043B043B043B043B = bЩ0429ЩЩЩЩ();
                        b043Bл043B043B043B043B = 13;
                    }
                    handler.sendMessage(obtainMessage2);
                    Log.e(b0414Д0414041404140414, str);
                }
                this.bю044E044Eююю.closeCloseable(closeable2);
                this.bю044E044Eююю.closeCloseable(closeable);
                this.bююю044Eюю = true;
                throw th;
            }
        } catch (Exception e) {
            throw e;
        } catch (Exception e2) {
            throw e2;
        } catch (Exception e22) {
            throw e22;
        }
    }
}
