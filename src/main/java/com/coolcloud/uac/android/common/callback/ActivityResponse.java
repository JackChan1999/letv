package com.coolcloud.uac.android.common.callback;

import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.coolcloud.uac.android.common.callback.IActivityResponse.Stub;
import com.coolcloud.uac.android.common.util.LOG;

public class ActivityResponse implements Parcelable {
    public static final Creator<ActivityResponse> CREATOR = new 1();
    private static final String TAG = "ActivityResponse";
    private IActivityResponse response = null;

    public ActivityResponse(IActivityResponse response) {
        this.response = response;
    }

    public ActivityResponse(Parcel parcel) {
        this.response = Stub.asInterface(parcel.readStrongBinder());
    }

    public void onResult(Bundle result) {
        try {
            this.response.onResult(result);
        } catch (DeadObjectException e) {
            LOG.e(TAG, "[result:" + result + "] on result failed(DeadObjectException)", e);
        } catch (RemoteException e2) {
            LOG.e(TAG, "[result:" + result + "] on result failed(RemoteException)", e2);
        } catch (Throwable e3) {
            LOG.e(TAG, "[result:" + result + "] on result failed(Throwable)", e3);
        }
    }

    public void onError(int error, String message) {
        try {
            this.response.onError(error, message);
        } catch (DeadObjectException e) {
            LOG.e(TAG, "[error:" + error + "][message:" + message + "] on error failed(DeadObjectException)", e);
        } catch (RemoteException e2) {
            LOG.e(TAG, "[error:" + error + "][message:" + message + "] on error failed(RemoteException)", e2);
        } catch (Throwable e3) {
            LOG.e(TAG, "[error:" + error + "][message:" + message + "] on error failed(Throwable)", e3);
        }
    }

    public void onCancel() {
        try {
            this.response.onCancel();
        } catch (DeadObjectException e) {
            LOG.e(TAG, "on cancel failed(DeadObjectException)", e);
        } catch (RemoteException e2) {
            LOG.e(TAG, "on cancel failed(RemoteException)", e2);
        } catch (Throwable e3) {
            LOG.e(TAG, "on cancel failed(Throwable)", e3);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(this.response.asBinder());
    }
}
