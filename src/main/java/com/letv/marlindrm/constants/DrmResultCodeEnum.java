package com.letv.marlindrm.constants;

public enum DrmResultCodeEnum {
    DRM_OK(0),
    DRM_CANCEL(1),
    DRM_INIT_ERROR(2),
    DRM_TOKEN_XML_READ_ERROR(3),
    DRM_PROCESS_TOKEN_ERROR(4),
    DRM_PLAYPROXY_START_ERROR(5),
    DRM_MAKE_URL_ERROR(6),
    DRM_UNSUPPORT_ERROR(7);
    
    private int resultCode;

    private DrmResultCodeEnum(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getResultCode() {
        return this.resultCode;
    }
}
