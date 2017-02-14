package com.letv.mobile.lebox.http.lebox;

import com.letv.mobile.http.parameter.LetvBaseParameter;
import com.letv.mobile.lebox.ui.qrcode.LeboxQrCodeBean;
import com.letv.mobile.lebox.utils.DeviceUtils;

public class LeBoxDynamicHttpBaseParameter extends LetvBaseParameter {
    public static final String COMMON_KEY_CODE = "code";
    public static final String COMMON_KEY_CODE_SID = "sid";
    private static final String COMMON_KEY_TOKEN = "token";
    private static final String COMMON_KEY_UID = "uid";
    private static final long serialVersionUID = -6696431583924895751L;
    private static String sid = DeviceUtils.getDeviceId();

    public LetvBaseParameter combineParams() {
        put(COMMON_KEY_CODE_SID, sid);
        put("code", LeboxQrCodeBean.getCode());
        return this;
    }
}
