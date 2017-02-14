package com.letv.mobile.lebox.connect;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

public class StepsQueue {
    private BaseStep mBaseStep;
    @SuppressLint({"HandlerLeak"})
    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (StepsQueue.this.mBaseStep != null) {
                StepsQueue.this.mBaseStep.loopCheck(msg);
            }
            super.handleMessage(msg);
        }
    };

    public BaseStep add(BaseStep step) {
        if (this.mBaseStep == null) {
            this.mBaseStep = step;
        } else {
            BaseStep s = this.mBaseStep;
            while (s.mNextStep != null) {
                s = s.mNextStep;
            }
            s.mNextStep = step;
        }
        return step;
    }

    public void startStep() {
        if (this.mBaseStep != null) {
            this.mBaseStep.startStep(this);
        }
    }

    public void onStepResult(boolean isSucceed, boolean isContinue) {
        if (isHasStep()) {
            BaseStep s = this.mBaseStep;
            this.mBaseStep = this.mBaseStep.mNextStep;
            s.mNextStep = null;
        }
        if (isSucceed) {
            if (isContinue && isHasStep()) {
                startStep();
            } else {
                cleanQueue();
            }
        } else if (isContinue && isHasStep()) {
            startStep();
        } else {
            cleanQueue();
        }
        if (isFinish()) {
            LeboxConnectManager.getInstance().notifyProgress(27);
        }
    }

    public boolean isHasStep() {
        return this.mBaseStep != null;
    }

    public boolean isFinish() {
        return this.mBaseStep == null;
    }

    public void cleanQueue() {
        while (isHasStep()) {
            BaseStep s = this.mBaseStep;
            this.mBaseStep = this.mBaseStep.mNextStep;
            s.mNextStep = null;
        }
    }

    public Handler getHandler() {
        return this.mHandler;
    }
}
