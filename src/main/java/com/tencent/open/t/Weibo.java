package com.tencent.open.t;

import android.content.Context;
import android.os.Bundle;
import com.letv.download.db.Download.DownloadBaseColumns;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.connect.common.Constants;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IUiListener;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: ProGuard */
public class Weibo extends BaseApi {
    public Weibo(Context context, QQAuth qQAuth, QQToken qQToken) {
        super(qQAuth, qQToken);
    }

    public Weibo(Context context, QQToken qQToken) {
        super(qQToken);
    }

    public void getWeiboInfo(IUiListener iUiListener) {
        HttpUtils.requestAsync(this.mToken, Global.getContext(), "user/get_info", composeCGIParams(), "GET", new TempRequestListener(iUiListener));
    }

    public void sendText(String str, IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        String str2 = WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT;
        if (str == null) {
            str = "";
        }
        composeCGIParams.putString(str2, str);
        HttpUtils.requestAsync(this.mToken, Global.getContext(), "t/add_t", composeCGIParams, "POST", new TempRequestListener(iUiListener));
    }

    public void sendPicText(String str, String str2, IUiListener iUiListener) {
        ByteArrayOutputStream byteArrayOutputStream;
        IOException e;
        Throwable th;
        InputStream inputStream = null;
        Object tempRequestListener = new TempRequestListener(iUiListener);
        InputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(str2);
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
                String str3 = WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT;
                if (str == null) {
                    str = "";
                }
                composeCGIParams.putString(str3, str);
                composeCGIParams.putByteArray(DownloadBaseColumns.COLUMN_PIC, bArr);
                HttpUtils.requestAsync(this.mToken, Global.getContext(), "t/add_pic_t", composeCGIParams, "POST", tempRequestListener);
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

    public void nickTips(String str, int i, IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        String str2 = "match";
        if (str == null) {
            str = "";
        }
        composeCGIParams.putString(str2, str);
        composeCGIParams.putString("reqnum", i + "");
        HttpUtils.requestAsync(this.mToken, Global.getContext(), Constants.GRAPH_NICK_TIPS, composeCGIParams, "GET", new TempRequestListener(iUiListener));
    }

    public void atFriends(int i, IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        composeCGIParams.putString("reqnum", i + "");
        HttpUtils.requestAsync(this.mToken, Global.getContext(), Constants.GRAPH_INTIMATE_FRIENDS, composeCGIParams, "GET", new TempRequestListener(iUiListener));
    }

    public void deleteText(String str, IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        composeCGIParams.putString("id", str);
        HttpUtils.requestAsync(this.mToken, Global.getContext(), "t/del_t", composeCGIParams, "POST", new TempRequestListener(iUiListener));
    }
}
