package com.tencent.open.weiyun;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import com.letv.core.constant.DatabaseConstant.LiveBookTrace.Field;
import com.letv.core.constant.LetvConstant;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.pp.utils.NetworkUtils;
import com.sina.weibo.sdk.component.ShareRequestParam;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.connect.common.Constants;
import com.tencent.open.a.f;
import com.tencent.open.utils.DataConvert;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.HttpUtils.HttpStatusException;
import com.tencent.open.utils.HttpUtils.NetworkUnavailableException;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class FileManager extends BaseApi {
    private static final String[] a = new String[]{"https://graph.qq.com/weiyun/get_photo_list", "https://graph.qq.com/weiyun/get_music_list", "https://graph.qq.com/weiyun/get_video_list"};
    private static final String[] b = new String[]{"https://graph.qq.com/weiyun/delete_photo", "https://graph.qq.com/weiyun/delete_music", "https://graph.qq.com/weiyun/delete_video"};

    /* compiled from: ProGuard */
    private class DownLoadImp {
        private static final String DOWNLOAD_COOKIE_NAME = "dl_cookie_name";
        private static final String DOWNLOAD_COOKIE_VALUE = "dl_cookie_value";
        private static final String DOWNLOAD_ENCRYPT_URL = "dl_encrypt_url";
        private static final String DOWNLOAD_MUSIC_URL = "https://graph.qq.com/weiyun/download_music";
        private static final String DOWNLOAD_PIC_URL = "https://graph.qq.com/weiyun/download_photo";
        private static final int DOWNLOAD_PROGRESS = 1;
        private static final int DOWNLOAD_PROGRESS_DONE = 2;
        private static final String DOWNLOAD_SERVER_HOST = "dl_svr_host";
        private static final String DOWNLOAD_SERVER_PORT = "dl_svr_port";
        private static final String DOWNLOAD_THUMB_SIZE = "dl_thumb_size";
        private static final String DOWNLOAD_THUMB_URL = "https://graph.qq.com/weiyun/get_photo_thumb";
        private static final String DOWNLOAD_VIDEO_URL = "https://graph.qq.com/weiyun/download_video";
        private static final int GET_PERMISSON_DOWN = 0;
        private static final int MAX_ERROR_TIMES = 10;
        private IDownLoadFileCallBack mCallback;
        private Context mContext;
        private String mDownloadCookieName;
        private String mDownloadCookieValue;
        private String mDownloadEncryptUrl;
        private String mDownloadServerHost;
        private int mDownloadServerPort;
        private String mDownloadThumbSize;
        private File mFile;
        private WeiyunFileType mFileType;
        private Handler mHandler;
        private String mSavePath;
        private String mThumbSize;
        private WeiyunFile mWeiyunFile;

        public DownLoadImp(Context context, WeiyunFileType weiyunFileType, WeiyunFile weiyunFile, String str, IDownLoadFileCallBack iDownLoadFileCallBack) {
            this.mContext = context;
            this.mFileType = weiyunFileType;
            this.mWeiyunFile = weiyunFile;
            this.mSavePath = str;
            this.mCallback = iDownLoadFileCallBack;
            this.mHandler = new Handler(this.mContext.getMainLooper(), FileManager.this) {
                public void handleMessage(Message message) {
                    switch (message.what) {
                        case 0:
                            JSONObject jSONObject = (JSONObject) message.obj;
                            try {
                                int i = jSONObject.getInt("ret");
                                if (i != 0) {
                                    DownLoadImp.this.mCallback.onError(new UiError(i, jSONObject.toString(), null));
                                    return;
                                }
                                jSONObject = jSONObject.getJSONObject(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA);
                                DownLoadImp.this.mDownloadEncryptUrl = jSONObject.getString(DownLoadImp.DOWNLOAD_ENCRYPT_URL);
                                DownLoadImp.this.mDownloadCookieName = jSONObject.getString(DownLoadImp.DOWNLOAD_COOKIE_NAME);
                                DownLoadImp.this.mDownloadCookieValue = jSONObject.getString(DownLoadImp.DOWNLOAD_COOKIE_VALUE);
                                DownLoadImp.this.mDownloadServerPort = jSONObject.getInt(DownLoadImp.DOWNLOAD_SERVER_PORT);
                                DownLoadImp.this.mDownloadServerHost = jSONObject.getString(DownLoadImp.DOWNLOAD_SERVER_HOST);
                                if (jSONObject.has(DownLoadImp.DOWNLOAD_THUMB_SIZE)) {
                                    DownLoadImp.this.mDownloadThumbSize = jSONObject.getString(DownLoadImp.DOWNLOAD_THUMB_SIZE);
                                }
                                DownLoadImp.this.mCallback.onDownloadStart();
                                DownLoadImp.this.doDownload();
                                return;
                            } catch (JSONException e) {
                                DownLoadImp.this.mCallback.onError(new UiError(-4, e.getMessage(), null));
                                return;
                            }
                        case 1:
                            DownLoadImp.this.mCallback.onDownloadProgress(Integer.parseInt((String) message.obj));
                            return;
                        case 2:
                            DownLoadImp.this.mCallback.onDownloadSuccess(DownLoadImp.this.mSavePath);
                            return;
                        default:
                            DownLoadImp.this.mCallback.onError(new UiError(message.what, (String) message.obj, null));
                            return;
                    }
                }
            };
        }

        public void setThumbSize(String str) {
            this.mThumbSize = str;
        }

        public void start() {
            if (this.mSavePath == null || this.mFileType == null || this.mWeiyunFile == null || this.mWeiyunFile.getFileId() == null) {
                Message obtainMessage = this.mHandler.obtainMessage();
                obtainMessage.what = -5;
                obtainMessage.obj = new String("");
                this.mHandler.sendMessage(obtainMessage);
                return;
            }
            this.mFile = new File(this.mSavePath);
            if (this.mFile.exists()) {
                obtainMessage = this.mHandler.obtainMessage();
                obtainMessage.what = -11;
                obtainMessage.obj = new String("");
                this.mHandler.sendMessage(obtainMessage);
                return;
            }
            this.mCallback.onPrepareStart();
            getDownloadPermission();
        }

        private String getDownloadUrl(WeiyunFileType weiyunFileType) {
            if (weiyunFileType == WeiyunFileType.ImageFile) {
                if (this.mThumbSize != null) {
                    return DOWNLOAD_THUMB_URL;
                }
                return DOWNLOAD_PIC_URL;
            } else if (weiyunFileType == WeiyunFileType.MusicFile) {
                return DOWNLOAD_MUSIC_URL;
            } else {
                if (weiyunFileType == WeiyunFileType.VideoFile) {
                    return DOWNLOAD_VIDEO_URL;
                }
                return DOWNLOAD_PIC_URL;
            }
        }

        private void getDownloadPermission() {
            new Thread() {
                public void run() {
                    Message obtainMessage;
                    Bundle c = FileManager.this.composeCGIParams();
                    c.putString("file_id", DownLoadImp.this.mWeiyunFile.getFileId());
                    if (!TextUtils.isEmpty(DownLoadImp.this.mThumbSize)) {
                        c.putString("thumb", DownLoadImp.this.mThumbSize);
                    }
                    try {
                        JSONObject request = HttpUtils.request(FileManager.this.mToken, DownLoadImp.this.mContext, DownLoadImp.this.getDownloadUrl(DownLoadImp.this.mFileType), c, "GET");
                        obtainMessage = DownLoadImp.this.mHandler.obtainMessage();
                        obtainMessage.what = 0;
                        obtainMessage.obj = request;
                        DownLoadImp.this.mHandler.sendMessage(obtainMessage);
                    } catch (MalformedURLException e) {
                        obtainMessage = DownLoadImp.this.mHandler.obtainMessage();
                        obtainMessage.what = -3;
                        obtainMessage.obj = e.getMessage();
                        DownLoadImp.this.mHandler.sendMessage(obtainMessage);
                    } catch (IOException e2) {
                        obtainMessage = DownLoadImp.this.mHandler.obtainMessage();
                        obtainMessage.obj = e2.getMessage();
                        obtainMessage.what = -2;
                        DownLoadImp.this.mHandler.sendMessage(obtainMessage);
                    } catch (JSONException e3) {
                        obtainMessage = DownLoadImp.this.mHandler.obtainMessage();
                        obtainMessage.obj = e3.getMessage();
                        obtainMessage.what = -4;
                        DownLoadImp.this.mHandler.sendMessage(obtainMessage);
                    } catch (NetworkUnavailableException e4) {
                        obtainMessage = DownLoadImp.this.mHandler.obtainMessage();
                        obtainMessage.obj = e4.getMessage();
                        obtainMessage.what = -10;
                        DownLoadImp.this.mHandler.sendMessage(obtainMessage);
                    } catch (HttpStatusException e5) {
                        obtainMessage = DownLoadImp.this.mHandler.obtainMessage();
                        obtainMessage.obj = e5.getMessage();
                        obtainMessage.what = -9;
                        DownLoadImp.this.mHandler.sendMessage(obtainMessage);
                    }
                }
            }.start();
        }

        private void doDownload() {
            new Thread() {
                public void run() {
                    Message obtainMessage;
                    Message obtainMessage2;
                    Throwable th;
                    FileOutputStream fileOutputStream;
                    Exception exception;
                    HttpParams basicHttpParams = new BasicHttpParams();
                    HttpConnectionParams.setConnectionTimeout(basicHttpParams, LetvConstant.WIDGET_UPDATE_UI_TIME);
                    HttpConnectionParams.setSoTimeout(basicHttpParams, 20000);
                    HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
                    HttpProtocolParams.setContentCharset(basicHttpParams, "UTF-8");
                    HttpProtocolParams.setUserAgent(basicHttpParams, "TX_QQF_ANDROID");
                    String str = "http://" + DownLoadImp.this.mDownloadServerHost + NetworkUtils.DELIMITER_COLON + DownLoadImp.this.mDownloadServerPort + "/ftn_handler/" + DownLoadImp.this.mDownloadEncryptUrl + "/";
                    if (!TextUtils.isEmpty(DownLoadImp.this.mDownloadThumbSize)) {
                        str = str + "?size=" + DownLoadImp.this.mDownloadThumbSize;
                    }
                    HttpClient defaultHttpClient = new DefaultHttpClient(basicHttpParams);
                    InputStream inputStream = null;
                    FileOutputStream fileOutputStream2 = null;
                    InputStream inputStream2;
                    try {
                        FileOutputStream fileOutputStream3 = new FileOutputStream(new File(DownLoadImp.this.mSavePath));
                        try {
                            byte[] bArr = new byte[262144];
                            int read;
                            int i;
                            if (TextUtils.isEmpty(DownLoadImp.this.mThumbSize)) {
                                long j;
                                if (DownLoadImp.this.mWeiyunFile.getFileSize() > 262144) {
                                    j = 262144;
                                } else {
                                    j = DownLoadImp.this.mWeiyunFile.getFileSize();
                                }
                                long j2 = j + 0;
                                j = 0;
                                int i2 = 0;
                                inputStream2 = inputStream;
                                while (j2 <= DownLoadImp.this.mWeiyunFile.getFileSize()) {
                                    long j3;
                                    try {
                                        HttpUriRequest httpGet = new HttpGet(str);
                                        httpGet.addHeader(HttpRequest.HEADER_ACCEPT_ENCODING, HttpRequest.ENCODING_GZIP);
                                        httpGet.addHeader("Host", DownLoadImp.this.mDownloadServerHost);
                                        httpGet.addHeader("Connection", "Keep-Alive");
                                        httpGet.addHeader("Cookie", DownLoadImp.this.mDownloadCookieName + SearchCriteria.EQ + DownLoadImp.this.mDownloadCookieValue);
                                        httpGet.addHeader("Pragma", "no-cache");
                                        httpGet.addHeader("RANGE", "bytes=" + j + NetworkUtils.DELIMITER_LINE + j2 + "");
                                        HttpResponse execute = defaultHttpClient.execute(httpGet);
                                        f.c("weiyun_test", "uploadFileToWeiyun doDownloadPic response:" + execute.toString());
                                        int statusCode = execute.getStatusLine().getStatusCode();
                                        if (statusCode != 200 && statusCode != LeMessageIds.MSG_MAIN_GET_CURR_PAGE) {
                                            inputStream = inputStream2;
                                            break;
                                        }
                                        inputStream2 = execute.getEntity().getContent();
                                        while (true) {
                                            read = inputStream2.read(bArr);
                                            if (read <= 0) {
                                                break;
                                            }
                                            fileOutputStream3.write(bArr, 0, read);
                                            j += (long) read;
                                        }
                                        j3 = j;
                                        inputStream = inputStream2;
                                        i = i2;
                                        if (DownLoadImp.this.mWeiyunFile.getFileSize() - j2 > 0) {
                                            long fileSize = (DownLoadImp.this.mWeiyunFile.getFileSize() - j3 > 262144 ? 262144 : DownLoadImp.this.mWeiyunFile.getFileSize() - j3) + j3;
                                            obtainMessage = DownLoadImp.this.mHandler.obtainMessage();
                                            obtainMessage.what = 1;
                                            obtainMessage.obj = ((100 * fileSize) / DownLoadImp.this.mWeiyunFile.getFileSize()) + "";
                                            DownLoadImp.this.mHandler.sendMessage(obtainMessage);
                                            j2 = fileSize;
                                            inputStream2 = inputStream;
                                            i2 = i;
                                            j = j3;
                                        }
                                    } catch (Exception e) {
                                        i2++;
                                        if (i2 > 10) {
                                            e.printStackTrace();
                                            f.e("weiyun_test", "uploadFileToWeiyun doDownloadPic error:" + e.getMessage() + "");
                                            obtainMessage2 = DownLoadImp.this.mHandler.obtainMessage();
                                            obtainMessage2.what = -2;
                                            obtainMessage2.obj = e.getMessage();
                                            DownLoadImp.this.mHandler.sendMessage(obtainMessage2);
                                            inputStream = inputStream2;
                                            break;
                                        }
                                        j3 = j;
                                        inputStream = inputStream2;
                                        i = i2;
                                    } catch (Throwable th2) {
                                        th = th2;
                                        fileOutputStream = fileOutputStream3;
                                    }
                                }
                                inputStream = inputStream2;
                            } else {
                                HttpUriRequest httpGet2 = new HttpGet(str);
                                httpGet2.addHeader(HttpRequest.HEADER_ACCEPT_ENCODING, HttpRequest.ENCODING_GZIP);
                                httpGet2.addHeader("Host", DownLoadImp.this.mDownloadServerHost);
                                httpGet2.addHeader("Connection", "Keep-Alive");
                                httpGet2.addHeader("Cookie", DownLoadImp.this.mDownloadCookieName + SearchCriteria.EQ + DownLoadImp.this.mDownloadCookieValue + "");
                                httpGet2.addHeader("Pragma", "no-cache");
                                try {
                                    InputStream content;
                                    HttpResponse execute2 = defaultHttpClient.execute(httpGet2);
                                    f.c("weiyun_test", "uploadFileToWeiyun doDownloadPic response:" + execute2.toString());
                                    i = execute2.getStatusLine().getStatusCode();
                                    if (i == 200 || i == LeMessageIds.MSG_MAIN_GET_CURR_PAGE) {
                                        content = execute2.getEntity().getContent();
                                        while (true) {
                                            try {
                                                read = content.read(bArr);
                                                if (read <= 0) {
                                                    break;
                                                }
                                                fileOutputStream3.write(bArr, 0, read);
                                            } catch (Exception e2) {
                                                Exception exception2 = e2;
                                                inputStream = content;
                                                exception = exception2;
                                            } catch (Throwable th3) {
                                                inputStream2 = content;
                                                th = th3;
                                                fileOutputStream = fileOutputStream3;
                                            }
                                        }
                                    } else {
                                        content = inputStream;
                                    }
                                    inputStream = content;
                                } catch (Exception e3) {
                                    exception = e3;
                                    exception.printStackTrace();
                                    f.e("weiyun_test", "uploadFileToWeiyun doDownloadPic error:" + exception.getMessage() + "");
                                    Message obtainMessage3 = DownLoadImp.this.mHandler.obtainMessage();
                                    obtainMessage3.what = -2;
                                    obtainMessage3.obj = exception.getMessage();
                                    DownLoadImp.this.mHandler.sendMessage(obtainMessage3);
                                    if (fileOutputStream3 != null) {
                                        try {
                                            fileOutputStream3.close();
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                        }
                                    }
                                    if (inputStream != null) {
                                        try {
                                            inputStream.close();
                                            return;
                                        } catch (IOException e42) {
                                            e42.printStackTrace();
                                            return;
                                        }
                                    }
                                    return;
                                }
                            }
                            try {
                                obtainMessage2 = DownLoadImp.this.mHandler.obtainMessage();
                                obtainMessage2.what = 2;
                                DownLoadImp.this.mHandler.sendMessage(obtainMessage2);
                                if (fileOutputStream3 != null) {
                                    try {
                                        fileOutputStream3.close();
                                    } catch (IOException e422) {
                                        e422.printStackTrace();
                                    }
                                }
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException e4222) {
                                        e4222.printStackTrace();
                                    }
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                inputStream2 = inputStream;
                                fileOutputStream = fileOutputStream3;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            inputStream2 = inputStream;
                            fileOutputStream = fileOutputStream3;
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                }
                            }
                            if (inputStream2 != null) {
                                try {
                                    inputStream2.close();
                                } catch (IOException e52) {
                                    e52.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (FileNotFoundException e6) {
                        obtainMessage = DownLoadImp.this.mHandler.obtainMessage();
                        obtainMessage.what = -2;
                        obtainMessage.obj = e6.getMessage();
                        DownLoadImp.this.mHandler.sendMessage(obtainMessage);
                        if (fileOutputStream2 != null) {
                            try {
                                fileOutputStream2.close();
                            } catch (IOException e42222) {
                                e42222.printStackTrace();
                            }
                        }
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e422222) {
                                e422222.printStackTrace();
                            }
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        inputStream2 = inputStream;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        if (inputStream2 != null) {
                            inputStream2.close();
                        }
                        throw th;
                    }
                }
            }.start();
        }
    }

    /* compiled from: ProGuard */
    private class GetFileListListener implements IUiListener {
        private IGetFileListListener mListener;

        public GetFileListListener(IGetFileListListener iGetFileListListener) {
            this.mListener = iGetFileListListener;
        }

        public void onComplete(Object obj) {
            JSONObject jSONObject = (JSONObject) obj;
            try {
                List arrayList = new ArrayList();
                JSONObject jSONObject2 = jSONObject.getJSONObject(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA);
                if (!jSONObject2.isNull(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT)) {
                    JSONArray jSONArray = jSONObject2.getJSONArray(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT);
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                        arrayList.add(new WeiyunFile(jSONObject3.getString("file_id"), jSONObject3.getString("file_name"), jSONObject3.getString("file_ctime"), (long) jSONObject3.getInt("file_size")));
                    }
                }
                this.mListener.onComplete(arrayList);
            } catch (JSONException e) {
                this.mListener.onError(new UiError(-4, Constants.MSG_JSON_ERROR, jSONObject.toString()));
            }
        }

        public void onError(UiError uiError) {
            this.mListener.onError(uiError);
        }

        public void onCancel() {
        }
    }

    /* compiled from: ProGuard */
    private class UploadFileImp {
        private static final int GET_PERMISSON_DONE = 0;
        private static final String UPLOAD_IMAGE_URL = "https://graph.qq.com/weiyun/upload_photo";
        private static final String UPLOAD_MUSIC_URL = "https://graph.qq.com/weiyun/upload_music";
        private static final int UPLOAD_PROGRESS = 1;
        private static final int UPLOAD_PROGRESS_DONE = 2;
        private static final String UPLOAD_VIDEO_URL = "https://graph.qq.com/weiyun/upload_video";
        private final IUploadFileCallBack mCallback;
        private final Context mContext;
        private String mFileKey;
        private final String mFilePath;
        private long mFileSize;
        private final WeiyunFileType mFileType;
        private final Handler mHandler;
        private String mHost;
        private String mMD5Hash;
        private byte[] mUKey;

        public UploadFileImp(Context context, WeiyunFileType weiyunFileType, String str, IUploadFileCallBack iUploadFileCallBack) {
            this.mContext = context;
            this.mFileType = weiyunFileType;
            this.mFilePath = str;
            this.mCallback = iUploadFileCallBack;
            this.mHandler = new Handler(this.mContext.getMainLooper(), FileManager.this) {
                public void handleMessage(Message message) {
                    switch (message.what) {
                        case 0:
                            JSONObject jSONObject = (JSONObject) message.obj;
                            try {
                                int i = jSONObject.getInt("ret");
                                if (i != 0) {
                                    UploadFileImp.this.mCallback.onError(new UiError(i, jSONObject.toString(), null));
                                    return;
                                }
                                jSONObject = jSONObject.getJSONObject(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA);
                                UploadFileImp.this.mUKey = DataConvert.string2bytes(jSONObject.getString("csum"));
                                UploadFileImp.this.mHost = jSONObject.getString("host");
                                UploadFileImp.this.mCallback.onUploadStart();
                                UploadFileImp.this.doUpload();
                                return;
                            } catch (Exception e) {
                                UploadFileImp.this.mCallback.onError(new UiError(-4, e.getMessage(), null));
                                return;
                            }
                        case 1:
                            UploadFileImp.this.mCallback.onUploadProgress(Integer.parseInt((String) message.obj));
                            return;
                        case 2:
                            UploadFileImp.this.mCallback.onUploadSuccess();
                            return;
                        default:
                            UploadFileImp.this.mCallback.onError(new UiError(message.what, (String) message.obj, null));
                            return;
                    }
                }
            };
        }

        public void start() {
            FileInputStream fileInputStream;
            DigestInputStream digestInputStream;
            FileInputStream fileInputStream2;
            Message obtainMessage;
            Throwable th;
            Throwable th2;
            FileInputStream fileInputStream3;
            DigestInputStream digestInputStream2 = null;
            if (this.mFilePath == null || !new File(this.mFilePath).exists()) {
                Message obtainMessage2 = this.mHandler.obtainMessage();
                obtainMessage2.what = -5;
                obtainMessage2.obj = new String("");
                this.mHandler.sendMessage(obtainMessage2);
                return;
            }
            this.mCallback.onPrepareStart();
            File file = new File(this.mFilePath);
            this.mFileSize = file.length();
            try {
                MessageDigest instance = MessageDigest.getInstance(CommonUtils.SHA1_INSTANCE);
                fileInputStream = new FileInputStream(file);
                try {
                    digestInputStream = new DigestInputStream(fileInputStream, instance);
                } catch (Exception e) {
                    digestInputStream = null;
                    fileInputStream2 = fileInputStream;
                    try {
                        obtainMessage = this.mHandler.obtainMessage();
                        obtainMessage.what = -2;
                        obtainMessage.obj = new String("");
                        this.mHandler.sendMessage(obtainMessage);
                        if (digestInputStream != null) {
                            try {
                                digestInputStream.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (fileInputStream2 == null) {
                            try {
                                fileInputStream2.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                                return;
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        fileInputStream = fileInputStream2;
                        digestInputStream2 = digestInputStream;
                        th2 = th;
                        if (digestInputStream2 != null) {
                            try {
                                digestInputStream2.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e32) {
                                e32.printStackTrace();
                            }
                        }
                        throw th2;
                    }
                } catch (Throwable th4) {
                    th2 = th4;
                    if (digestInputStream2 != null) {
                        digestInputStream2.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    throw th2;
                }
                try {
                    do {
                    } while (digestInputStream.read(new byte[524288]) > 0);
                    instance = digestInputStream.getMessageDigest();
                    this.mFileKey = DataConvert.toHexString(instance.digest());
                    instance.reset();
                    if (digestInputStream != null) {
                        try {
                            digestInputStream.close();
                        } catch (IOException e222) {
                            e222.printStackTrace();
                        }
                    }
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e2222) {
                            e2222.printStackTrace();
                        }
                    }
                    try {
                        instance = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
                        fileInputStream = new FileInputStream(file);
                        try {
                            digestInputStream = new DigestInputStream(fileInputStream, instance);
                        } catch (Exception e4) {
                            fileInputStream3 = fileInputStream;
                            try {
                                obtainMessage = this.mHandler.obtainMessage();
                                obtainMessage.what = -2;
                                obtainMessage.obj = new String("");
                                this.mHandler.sendMessage(obtainMessage);
                                if (digestInputStream2 != null) {
                                    try {
                                        digestInputStream2.close();
                                    } catch (IOException e322) {
                                        e322.printStackTrace();
                                    }
                                }
                                if (fileInputStream3 == null) {
                                    try {
                                        fileInputStream3.close();
                                    } catch (IOException e22222) {
                                        e22222.printStackTrace();
                                        return;
                                    }
                                }
                            } catch (Throwable th32) {
                                th = th32;
                                fileInputStream = fileInputStream3;
                                th2 = th;
                                if (digestInputStream2 != null) {
                                    try {
                                        digestInputStream2.close();
                                    } catch (IOException e3222) {
                                        e3222.printStackTrace();
                                    }
                                }
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (IOException e32222) {
                                        e32222.printStackTrace();
                                    }
                                }
                                throw th2;
                            }
                        } catch (Throwable th5) {
                            th2 = th5;
                            if (digestInputStream2 != null) {
                                digestInputStream2.close();
                            }
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            throw th2;
                        }
                        try {
                            do {
                            } while (digestInputStream.read(new byte[524288]) > 0);
                            MessageDigest messageDigest = digestInputStream.getMessageDigest();
                            this.mMD5Hash = DataConvert.toHexString(messageDigest.digest());
                            messageDigest.reset();
                            fileInputStream.close();
                            digestInputStream.close();
                            if (digestInputStream != null) {
                                try {
                                    digestInputStream.close();
                                } catch (IOException e222222) {
                                    e222222.printStackTrace();
                                }
                            }
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException e2222222) {
                                    e2222222.printStackTrace();
                                }
                            }
                            getUploadPermission();
                        } catch (Exception e5) {
                            digestInputStream2 = digestInputStream;
                            fileInputStream3 = fileInputStream;
                            obtainMessage = this.mHandler.obtainMessage();
                            obtainMessage.what = -2;
                            obtainMessage.obj = new String("");
                            this.mHandler.sendMessage(obtainMessage);
                            if (digestInputStream2 != null) {
                                digestInputStream2.close();
                            }
                            if (fileInputStream3 == null) {
                                fileInputStream3.close();
                            }
                        } catch (Throwable th6) {
                            th = th6;
                            digestInputStream2 = digestInputStream;
                            th2 = th;
                            if (digestInputStream2 != null) {
                                digestInputStream2.close();
                            }
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            throw th2;
                        }
                    } catch (Exception e6) {
                        fileInputStream3 = null;
                        obtainMessage = this.mHandler.obtainMessage();
                        obtainMessage.what = -2;
                        obtainMessage.obj = new String("");
                        this.mHandler.sendMessage(obtainMessage);
                        if (digestInputStream2 != null) {
                            digestInputStream2.close();
                        }
                        if (fileInputStream3 == null) {
                            fileInputStream3.close();
                        }
                    } catch (Throwable th7) {
                        th2 = th7;
                        fileInputStream = null;
                        if (digestInputStream2 != null) {
                            digestInputStream2.close();
                        }
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        throw th2;
                    }
                } catch (Exception e7) {
                    fileInputStream2 = fileInputStream;
                    obtainMessage = this.mHandler.obtainMessage();
                    obtainMessage.what = -2;
                    obtainMessage.obj = new String("");
                    this.mHandler.sendMessage(obtainMessage);
                    if (digestInputStream != null) {
                        digestInputStream.close();
                    }
                    if (fileInputStream2 == null) {
                        fileInputStream2.close();
                    }
                } catch (Throwable th62) {
                    th = th62;
                    digestInputStream2 = digestInputStream;
                    th2 = th;
                    if (digestInputStream2 != null) {
                        digestInputStream2.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    throw th2;
                }
            } catch (Exception e8) {
                digestInputStream = null;
                obtainMessage = this.mHandler.obtainMessage();
                obtainMessage.what = -2;
                obtainMessage.obj = new String("");
                this.mHandler.sendMessage(obtainMessage);
                if (digestInputStream != null) {
                    digestInputStream.close();
                }
                if (fileInputStream2 == null) {
                    fileInputStream2.close();
                }
            } catch (Throwable th8) {
                th2 = th8;
                fileInputStream = null;
                if (digestInputStream2 != null) {
                    digestInputStream2.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th2;
            }
        }

        private String getRequestUrl(WeiyunFileType weiyunFileType) {
            if (weiyunFileType == WeiyunFileType.ImageFile) {
                return UPLOAD_IMAGE_URL;
            }
            if (weiyunFileType == WeiyunFileType.MusicFile) {
                return UPLOAD_MUSIC_URL;
            }
            return UPLOAD_VIDEO_URL;
        }

        private void getUploadPermission() {
            new Thread() {
                public void run() {
                    Message obtainMessage;
                    String str = SystemClock.elapsedRealtime() + "__" + Uri.parse(UploadFileImp.this.mFilePath).getLastPathSegment();
                    Bundle a = FileManager.this.composeCGIParams();
                    a.putString("sha", UploadFileImp.this.mFileKey);
                    a.putString(Field.MD5_ID, UploadFileImp.this.mMD5Hash);
                    a.putString("size", UploadFileImp.this.mFileSize + "");
                    a.putString("name", str);
                    a.putString("upload_type", "control");
                    try {
                        JSONObject request = HttpUtils.request(FileManager.this.mToken, UploadFileImp.this.mContext, UploadFileImp.this.getRequestUrl(UploadFileImp.this.mFileType), a, "GET");
                        obtainMessage = UploadFileImp.this.mHandler.obtainMessage();
                        obtainMessage.what = 0;
                        obtainMessage.obj = request;
                        UploadFileImp.this.mHandler.sendMessage(obtainMessage);
                    } catch (MalformedURLException e) {
                        obtainMessage = UploadFileImp.this.mHandler.obtainMessage();
                        obtainMessage.what = -3;
                        obtainMessage.obj = e.getMessage();
                        UploadFileImp.this.mHandler.sendMessage(obtainMessage);
                    } catch (IOException e2) {
                        obtainMessage = UploadFileImp.this.mHandler.obtainMessage();
                        obtainMessage.obj = e2.getMessage();
                        obtainMessage.what = -2;
                        UploadFileImp.this.mHandler.sendMessage(obtainMessage);
                    } catch (JSONException e3) {
                        obtainMessage = UploadFileImp.this.mHandler.obtainMessage();
                        obtainMessage.obj = e3.getMessage();
                        obtainMessage.what = -4;
                        UploadFileImp.this.mHandler.sendMessage(obtainMessage);
                    } catch (NetworkUnavailableException e4) {
                        obtainMessage = UploadFileImp.this.mHandler.obtainMessage();
                        obtainMessage.obj = e4.getMessage();
                        obtainMessage.what = -10;
                        UploadFileImp.this.mHandler.sendMessage(obtainMessage);
                    } catch (HttpStatusException e5) {
                        obtainMessage = UploadFileImp.this.mHandler.obtainMessage();
                        obtainMessage.obj = e5.getMessage();
                        obtainMessage.what = -9;
                        UploadFileImp.this.mHandler.sendMessage(obtainMessage);
                    }
                }
            }.start();
        }

        private void doUpload() {
            new Thread() {
                public void run() {
                    Message obtainMessage;
                    HttpParams basicHttpParams = new BasicHttpParams();
                    HttpConnectionParams.setConnectionTimeout(basicHttpParams, LetvConstant.WIDGET_UPDATE_UI_TIME);
                    HttpConnectionParams.setSoTimeout(basicHttpParams, 20000);
                    HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
                    HttpProtocolParams.setContentCharset(basicHttpParams, "UTF-8");
                    HttpProtocolParams.setUserAgent(basicHttpParams, "TX_QQF_ANDROID");
                    HttpClient defaultHttpClient = new DefaultHttpClient(basicHttpParams);
                    byte[] bArr = new byte[131072];
                    try {
                        FileInputStream fileInputStream = new FileInputStream(UploadFileImp.this.mFilePath);
                        int i = 0;
                        while (((long) i) < UploadFileImp.this.mFileSize) {
                            try {
                                int read = fileInputStream.read(bArr);
                                byte[] access$1400 = UploadFileImp.this.packPostBody(bArr, read, i);
                                i += read;
                                if (access$1400 != null) {
                                    Message obtainMessage2;
                                    HttpUriRequest httpPost = new HttpPost("http://" + UploadFileImp.this.mHost + "/ftn_handler/?bmd5=" + UploadFileImp.this.mMD5Hash);
                                    httpPost.addHeader(HttpRequest.HEADER_ACCEPT_ENCODING, "*/*");
                                    httpPost.setHeader("Connection", "Keep-Alive");
                                    httpPost.setHeader("Pragma", "no-cache");
                                    httpPost.setHeader(HttpRequest.HEADER_CONTENT_TYPE, HttpRequest.CONTENT_TYPE_FORM);
                                    httpPost.setEntity(new ByteArrayEntity(access$1400));
                                    try {
                                        read = defaultHttpClient.execute(httpPost).getStatusLine().getStatusCode();
                                    } catch (IOException e) {
                                        obtainMessage2 = UploadFileImp.this.mHandler.obtainMessage();
                                        obtainMessage2.what = -2;
                                        obtainMessage2.obj = "";
                                        UploadFileImp.this.mHandler.sendMessage(obtainMessage2);
                                        read = 0;
                                    }
                                    if (read != 200) {
                                        obtainMessage = UploadFileImp.this.mHandler.obtainMessage();
                                        obtainMessage.what = -9;
                                        obtainMessage.obj = "";
                                        UploadFileImp.this.mHandler.sendMessage(obtainMessage);
                                        break;
                                    } else if (((long) i) < UploadFileImp.this.mFileSize) {
                                        read = (int) ((((long) i) * 100) / UploadFileImp.this.mFileSize);
                                        Message obtainMessage3 = UploadFileImp.this.mHandler.obtainMessage();
                                        obtainMessage3.what = 1;
                                        obtainMessage3.obj = read + "";
                                        UploadFileImp.this.mHandler.sendMessage(obtainMessage3);
                                    } else {
                                        obtainMessage2 = UploadFileImp.this.mHandler.obtainMessage();
                                        obtainMessage2.what = 2;
                                        obtainMessage2.obj = "";
                                        UploadFileImp.this.mHandler.sendMessage(obtainMessage2);
                                    }
                                }
                            } catch (IOException e2) {
                                obtainMessage = UploadFileImp.this.mHandler.obtainMessage();
                                obtainMessage.what = -2;
                                obtainMessage.obj = "";
                                UploadFileImp.this.mHandler.sendMessage(obtainMessage);
                            }
                        }
                        try {
                            fileInputStream.close();
                        } catch (IOException e3) {
                            Message obtainMessage4 = UploadFileImp.this.mHandler.obtainMessage();
                            obtainMessage4.what = -2;
                            obtainMessage4.obj = e3.getMessage();
                            UploadFileImp.this.mHandler.sendMessage(obtainMessage4);
                        }
                    } catch (FileNotFoundException e4) {
                        obtainMessage = UploadFileImp.this.mHandler.obtainMessage();
                        obtainMessage.what = -2;
                        obtainMessage.obj = "";
                        UploadFileImp.this.mHandler.sendMessage(obtainMessage);
                    }
                }
            }.start();
        }

        private byte[] packPostBody(byte[] bArr, int i, int i2) {
            try {
                MessageDigest instance = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
                instance.update(bArr, 0, i);
                this.mMD5Hash = DataConvert.toHexString(instance.digest());
                instance.reset();
                int i3 = i + 340;
                byte[] bArr2 = new byte[((((i3 + 4) + 4) + 4) + 4)];
                int putInt2Bytes = DataConvert.putInt2Bytes(-1412589450, bArr2, 0) + 0;
                putInt2Bytes += DataConvert.putInt2Bytes(1000, bArr2, putInt2Bytes);
                putInt2Bytes += DataConvert.putInt2Bytes(0, bArr2, putInt2Bytes);
                i3 = DataConvert.putInt2Bytes(i3, bArr2, putInt2Bytes) + putInt2Bytes;
                i3 += DataConvert.putShort2Bytes(304, bArr2, i3);
                i3 += DataConvert.putBytes2Bytes(this.mUKey, bArr2, i3);
                i3 += DataConvert.putShort2Bytes(20, bArr2, i3);
                i3 += DataConvert.putString2Bytes(this.mFileKey, bArr2, i3);
                i3 += DataConvert.putInt2Bytes((int) this.mFileSize, bArr2, i3);
                i3 += DataConvert.putInt2Bytes(i2, bArr2, i3);
                i3 += DataConvert.putInt2Bytes(i, bArr2, i3);
                i3 += DataConvert.putBytes2Bytes(bArr, i, bArr2, i3);
                return bArr2;
            } catch (NoSuchAlgorithmException e) {
                Message obtainMessage = this.mHandler.obtainMessage();
                obtainMessage.what = -2;
                obtainMessage.obj = e.getMessage();
                this.mHandler.sendMessage(obtainMessage);
                return null;
            }
        }
    }

    /* compiled from: ProGuard */
    public enum WeiyunFileType {
        ImageFile(0),
        MusicFile(1),
        VideoFile(2);
        
        private final int mType;

        private WeiyunFileType(int i) {
            this.mType = i;
        }

        public int value() {
            return this.mType;
        }
    }

    public FileManager(Context context, QQAuth qQAuth, QQToken qQToken) {
        super(qQAuth, qQToken);
    }

    public FileManager(Context context, QQToken qQToken) {
        super(qQToken);
    }

    public void getFileList(WeiyunFileType weiyunFileType, IGetFileListListener iGetFileListListener) {
        String str = a[weiyunFileType.value()];
        Bundle composeCGIParams = composeCGIParams();
        composeCGIParams.putString("offset", "0");
        composeCGIParams.putString("number", "100");
        HttpUtils.requestAsync(this.mToken, Global.getContext(), str, composeCGIParams, "GET", new TempRequestListener(new GetFileListListener(iGetFileListListener)));
    }

    public void deleteFile(WeiyunFileType weiyunFileType, String str, IUiListener iUiListener) {
        String str2 = b[weiyunFileType.value()];
        Bundle composeCGIParams = composeCGIParams();
        composeCGIParams.putString("file_id", str);
        HttpUtils.requestAsync(this.mToken, Global.getContext(), str2, composeCGIParams, "GET", new TempRequestListener(iUiListener));
    }

    public void downLoadFile(WeiyunFileType weiyunFileType, WeiyunFile weiyunFile, String str, IDownLoadFileCallBack iDownLoadFileCallBack) {
        new DownLoadImp(Global.getContext(), weiyunFileType, weiyunFile, str, iDownLoadFileCallBack).start();
    }

    public void downLoadThumb(WeiyunFile weiyunFile, String str, String str2, IDownLoadFileCallBack iDownLoadFileCallBack) {
        DownLoadImp downLoadImp = new DownLoadImp(Global.getContext(), WeiyunFileType.ImageFile, weiyunFile, str, iDownLoadFileCallBack);
        downLoadImp.setThumbSize(str2);
        downLoadImp.start();
    }

    public void uploadFile(WeiyunFileType weiyunFileType, String str, IUploadFileCallBack iUploadFileCallBack) {
        new UploadFileImp(Global.getContext(), weiyunFileType, str, iUploadFileCallBack).start();
    }
}
