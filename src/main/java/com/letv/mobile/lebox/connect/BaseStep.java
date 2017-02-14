package com.letv.mobile.lebox.connect;

import android.os.Message;
import com.letv.mobile.lebox.utils.Logger;

public abstract class BaseStep {
    public BaseStep mNextStep;
    protected StepsQueue mStepsQueue;
    protected int timeOut;

    protected abstract void loopCheck(Message message);

    public abstract void startStep(StepsQueue stepsQueue);

    protected void onResult(StepsQueue stepsQueue, boolean isSucceed, boolean isContinue) {
        if (stepsQueue != null) {
            stepsQueue.onStepResult(isSucceed, isContinue);
        } else {
            Logger.e(getClass().getSimpleName(), "error !!! onResult  method   param stepsQueue is null");
        }
    }

    public int getTimeOut() {
        return this.timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
}
