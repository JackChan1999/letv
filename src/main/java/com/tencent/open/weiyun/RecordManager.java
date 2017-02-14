package com.tencent.open.weiyun;

import android.content.Context;
import android.os.Bundle;
import com.letv.lemallsdk.util.Constants;
import com.sina.weibo.sdk.component.ShareRequestParam;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.open.a.f;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class RecordManager extends BaseApi {
    public RecordManager(Context context, QQAuth qQAuth, QQToken qQToken) {
        super(qQAuth, qQToken);
    }

    public RecordManager(Context context, QQToken qQToken) {
        super(qQToken);
    }

    public void createRecord(String str, String str2, final IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        IRequestListener tempRequestListener = new TempRequestListener(new IUiListener(this) {
            final /* synthetic */ RecordManager b;

            public void onError(UiError uiError) {
                iUiListener.onError(uiError);
            }

            public void onComplete(Object obj) {
                JSONObject jSONObject = (JSONObject) obj;
                try {
                    if (jSONObject.getInt("ret") == 0) {
                        iUiListener.onComplete("");
                    } else {
                        iUiListener.onError(new UiError(-4, jSONObject.toString(), null));
                    }
                } catch (JSONException e) {
                    iUiListener.onError(new UiError(-4, e.getMessage(), null));
                }
            }

            public void onCancel() {
                iUiListener.onCancel();
            }
        });
        composeCGIParams.putString("key", Util.toHexString(str));
        try {
            composeCGIParams.putByteArray(Constants.VALUE_ID, Util.toHexString(str2).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            f.b("RecordManager", "-->cr, get value of utf-8 exception.");
        }
        HttpUtils.requestAsync(this.mToken, Global.getContext(), "https://graph.qq.com/weiyun/create_record", composeCGIParams, "POST", tempRequestListener);
    }

    public void deleteRecord(String str, final IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        IRequestListener tempRequestListener = new TempRequestListener(new IUiListener(this) {
            final /* synthetic */ RecordManager b;

            public void onError(UiError uiError) {
                iUiListener.onError(uiError);
            }

            public void onComplete(Object obj) {
                JSONObject jSONObject = (JSONObject) obj;
                try {
                    if (jSONObject.getInt("ret") == 0) {
                        iUiListener.onComplete("");
                    } else {
                        iUiListener.onError(new UiError(-4, jSONObject.toString(), null));
                    }
                } catch (JSONException e) {
                    iUiListener.onError(new UiError(-4, e.getMessage(), null));
                }
            }

            public void onCancel() {
                iUiListener.onCancel();
            }
        });
        composeCGIParams.putString("key", Util.toHexString(str));
        HttpUtils.requestAsync(this.mToken, Global.getContext(), "https://graph.qq.com/weiyun/delete_record", composeCGIParams, "GET", tempRequestListener);
    }

    public void getRecord(String str, final IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        IRequestListener tempRequestListener = new TempRequestListener(new IUiListener(this) {
            final /* synthetic */ RecordManager b;

            public void onError(UiError uiError) {
                iUiListener.onError(uiError);
            }

            public void onComplete(Object obj) {
                JSONObject jSONObject = (JSONObject) obj;
                try {
                    if (jSONObject.getInt("ret") == 0) {
                        iUiListener.onComplete(Util.hexToString(jSONObject.getJSONObject(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA).getString(Constants.VALUE_ID)));
                        return;
                    }
                    iUiListener.onError(new UiError(-4, jSONObject.toString(), null));
                } catch (JSONException e) {
                    iUiListener.onError(new UiError(-4, e.getMessage(), null));
                }
            }

            public void onCancel() {
                iUiListener.onCancel();
            }
        });
        composeCGIParams.putString("key", Util.toHexString(str));
        HttpUtils.requestAsync(this.mToken, Global.getContext(), "https://graph.qq.com/weiyun/get_record", composeCGIParams, "GET", tempRequestListener);
    }

    public void modifyRecord(String str, String str2, final IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        IRequestListener tempRequestListener = new TempRequestListener(new IUiListener(this) {
            final /* synthetic */ RecordManager b;

            public void onError(UiError uiError) {
                iUiListener.onError(uiError);
            }

            public void onComplete(Object obj) {
                JSONObject jSONObject = (JSONObject) obj;
                try {
                    if (jSONObject.getInt("ret") == 0) {
                        iUiListener.onComplete("");
                    } else {
                        iUiListener.onError(new UiError(-4, jSONObject.toString(), null));
                    }
                } catch (JSONException e) {
                    iUiListener.onError(new UiError(-4, e.getMessage(), null));
                }
            }

            public void onCancel() {
                iUiListener.onCancel();
            }
        });
        composeCGIParams.putString("key", Util.toHexString(str));
        try {
            composeCGIParams.putByteArray(Constants.VALUE_ID, Util.toHexString(str2).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            f.b("RecordManager", "-->mr, get value of utf-8 exception.");
        }
        HttpUtils.requestAsync(this.mToken, Global.getContext(), "https://graph.qq.com/weiyun/modify_record", composeCGIParams, "POST", tempRequestListener);
    }

    public void queryAllRecord(final IUiListener iUiListener) {
        HttpUtils.requestAsync(this.mToken, Global.getContext(), "https://graph.qq.com/weiyun/query_all_record", composeCGIParams(), "GET", new TempRequestListener(new IUiListener(this) {
            final /* synthetic */ RecordManager b;

            public void onError(UiError uiError) {
                iUiListener.onError(uiError);
            }

            public void onComplete(Object obj) {
                JSONObject jSONObject = (JSONObject) obj;
                try {
                    if (jSONObject.getInt("ret") == 0) {
                        List arrayList = new ArrayList();
                        JSONObject jSONObject2 = jSONObject.getJSONObject(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA);
                        if (!jSONObject2.isNull("keys")) {
                            JSONArray jSONArray = jSONObject2.getJSONArray("keys");
                            for (int i = 0; i < jSONArray.length(); i++) {
                                arrayList.add(Util.hexToString(jSONArray.getJSONObject(i).getString("key")));
                            }
                        }
                        iUiListener.onComplete(arrayList);
                        return;
                    }
                    iUiListener.onError(new UiError(-4, jSONObject.toString(), null));
                } catch (JSONException e) {
                    iUiListener.onError(new UiError(-4, e.getMessage(), null));
                }
            }

            public void onCancel() {
                iUiListener.onCancel();
            }
        }));
    }

    public void checkRecord(String str, final IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        IRequestListener tempRequestListener = new TempRequestListener(new IUiListener(this) {
            final /* synthetic */ RecordManager b;

            public void onError(UiError uiError) {
                iUiListener.onError(uiError);
            }

            public void onComplete(Object obj) {
                try {
                    if (((JSONObject) obj).getInt("ret") == 0) {
                        iUiListener.onComplete(Boolean.TRUE);
                    } else {
                        iUiListener.onComplete(Boolean.FALSE);
                    }
                } catch (JSONException e) {
                    iUiListener.onError(new UiError(-4, e.getMessage(), null));
                }
            }

            public void onCancel() {
                iUiListener.onCancel();
            }
        });
        composeCGIParams.putString("key", Util.toHexString(str));
        HttpUtils.requestAsync(this.mToken, Global.getContext(), "https://graph.qq.com/weiyun/check_record", composeCGIParams, "GET", tempRequestListener);
    }
}
