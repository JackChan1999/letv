package com.letv.mobile.lebox.sweep.result;

public abstract class ResultHandler {
    public static final int SWEEP_FAILED = 1;
    public static final int SWEEP_SUCCESSED = 0;
    public static final int SWEEP_UNLOGIN = 2;
    protected OnSweepResultListener mListener;

    public abstract int handleDecode();

    public void setListner(OnSweepResultListener listner) {
        this.mListener = listner;
    }
}
