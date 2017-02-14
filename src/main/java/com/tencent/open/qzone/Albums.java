package com.tencent.open.qzone;

import android.content.Context;
import android.os.Bundle;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.open.SocialConstants;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IUiListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: ProGuard */
public class Albums extends BaseApi {

    /* compiled from: ProGuard */
    public enum AlbumSecurity {
        publicToAll("1"),
        privateOnly("2"),
        friendsOnly("4"),
        needQuestion("5");
        
        private final String a;

        private AlbumSecurity(String str) {
            this.a = str;
        }

        public String getSecurity() {
            return this.a;
        }
    }

    public Albums(Context context, QQAuth qQAuth, QQToken qQToken) {
        super(qQAuth, qQToken);
    }

    public Albums(Context context, QQToken qQToken) {
        super(qQToken);
    }

    public void listAlbum(IUiListener iUiListener) {
        HttpUtils.requestAsync(this.mToken, Global.getContext(), "photo/list_album", composeCGIParams(), "GET", new TempRequestListener(iUiListener));
    }

    public void listPhotos(String str, IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        String str2 = "albumid";
        if (str == null) {
            str = "";
        }
        composeCGIParams.putString(str2, str);
        HttpUtils.requestAsync(this.mToken, Global.getContext(), "photo/list_photo", composeCGIParams, "GET", new TempRequestListener(iUiListener));
    }

    public void uploadPicture(String str, String str2, String str3, String str4, String str5, IUiListener iUiListener) {
        ByteArrayOutputStream byteArrayOutputStream;
        IOException e;
        Throwable th;
        InputStream inputStream = null;
        Object tempRequestListener = new TempRequestListener(iUiListener);
        InputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(str);
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
            } catch (IOException e2) {
                e = e2;
                byteArrayOutputStream = null;
                inputStream = fileInputStream;
                try {
                    tempRequestListener.onIOException(e);
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    if (inputStream == null) {
                        try {
                            inputStream.close();
                        } catch (IOException e32) {
                            e32.printStackTrace();
                            return;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = inputStream;
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e42) {
                            e42.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                byteArrayOutputStream = null;
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                bArr = byteArrayOutputStream.toByteArray();
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e422) {
                        e422.printStackTrace();
                    }
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e4222) {
                        e4222.printStackTrace();
                    }
                }
                Bundle composeCGIParams = composeCGIParams();
                File file = new File(str);
                composeCGIParams.putByteArray(SocialConstants.PARAM_AVATAR_URI, bArr);
                String str6 = "photodesc";
                if (str2 == null) {
                    str2 = "";
                }
                composeCGIParams.putString(str6, str2);
                composeCGIParams.putString("title", file.getName());
                if (str3 != null) {
                    str6 = "albumid";
                    if (str3 == null) {
                        str3 = "";
                    }
                    composeCGIParams.putString(str6, str3);
                }
                str6 = "x";
                if (str4 == null) {
                    str4 = "";
                }
                composeCGIParams.putString(str6, str4);
                str6 = "y";
                if (str5 == null) {
                    str5 = "";
                }
                composeCGIParams.putString(str6, str5);
                HttpUtils.requestAsync(this.mToken, Global.getContext(), "photo/upload_pic", composeCGIParams, "POST", tempRequestListener);
            } catch (IOException e5) {
                e32 = e5;
                inputStream = fileInputStream;
                tempRequestListener.onIOException(e32);
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                if (inputStream == null) {
                    inputStream.close();
                }
            } catch (Throwable th4) {
                th = th4;
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
        } catch (IOException e6) {
            e32 = e6;
            byteArrayOutputStream = null;
            tempRequestListener.onIOException(e32);
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            if (inputStream == null) {
                inputStream.close();
            }
        } catch (Throwable th5) {
            th = th5;
            byteArrayOutputStream = null;
            fileInputStream = null;
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            throw th;
        }
    }

    public void addAlbum(String str, String str2, AlbumSecurity albumSecurity, String str3, String str4, IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        String str5 = "albumname";
        if (str == null) {
            str = "";
        }
        composeCGIParams.putString(str5, str);
        str5 = "albumdesc";
        if (str2 == null) {
            str2 = "";
        }
        composeCGIParams.putString(str5, str2);
        composeCGIParams.putString("priv", albumSecurity == null ? AlbumSecurity.publicToAll.getSecurity() : albumSecurity.getSecurity());
        str5 = "question";
        if (str3 == null) {
            str3 = "";
        }
        composeCGIParams.putString(str5, str3);
        str5 = "answer";
        if (str4 == null) {
            str4 = "";
        }
        composeCGIParams.putString(str5, str4);
        HttpUtils.requestAsync(this.mToken, Global.getContext(), "photo/add_album", composeCGIParams, "POST", new TempRequestListener(iUiListener));
    }
}
