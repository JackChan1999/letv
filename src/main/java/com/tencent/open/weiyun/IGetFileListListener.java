package com.tencent.open.weiyun;

import com.tencent.tauth.UiError;
import java.util.List;

/* compiled from: ProGuard */
public interface IGetFileListListener {
    void onComplete(List<WeiyunFile> list);

    void onError(UiError uiError);
}
