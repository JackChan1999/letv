package com.letv.core.messagebus.manager;

import android.content.Context;
import android.util.SparseArray;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.messagebus.task.LeMessageTask;
import com.letv.core.messagebus.task.LeMessageTask.TaskRunnable;
import com.letv.core.utils.LogInfo;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class LeMessageManager {
    public static final String TAG = "messagebus";
    private static LeMessageManager sInstance = null;
    private final SparseArray<LeMessageTask> mTasks = new SparseArray();
    private SparseArray<List<Subject>> subjectMapper = new SparseArray();

    private LeMessageManager() {
    }

    public static LeMessageManager getInstance() {
        if (sInstance == null) {
            synchronized (LeMessageManager.class) {
                if (sInstance == null) {
                    sInstance = new LeMessageManager();
                }
            }
        }
        return sInstance;
    }

    public LeResponseMessage dispatchMessage(LeMessage message) {
        return dispatchMessage(null, message, null);
    }

    public LeResponseMessage dispatchMessage(Context context, LeMessage message) {
        return dispatchMessage(context, message, null);
    }

    private LeResponseMessage dispatchMessage(Context context, LeMessage message, LeMessageTask task) {
        LeResponseMessage leResponseMessage = null;
        if (message != null) {
            if (context != null) {
                message.setContext(context);
            }
            int id = message.getId();
            if (task == null) {
                task = findTask(id);
            }
            if (task != null) {
                TaskRunnable runnable = task.getRunnable();
                if (runnable != null) {
                    try {
                        leResponseMessage = runnable.run(message);
                    } catch (Exception e) {
                    }
                }
            }
        }
        return leResponseMessage;
    }

    public void asyncDispatchMessage(LeMessage message) {
        asyncDispatchMessage(null, message, null);
    }

    public void asyncDispatchMessage(Context context, LeMessage message) {
        asyncDispatchMessage(context, message, null);
    }

    private void asyncDispatchMessage(Context context, LeMessage message, LeMessageTask task) {
        new 1(this, context, message, task).execute(new Void[0]);
    }

    public void registerTask(LeMessageTask task) {
        if (task != null) {
            int id = task.getId();
            this.mTasks.put(id, task);
            LogInfo.log(TAG, "add task:" + id);
        }
    }

    public void unRegister(int id) {
        LogInfo.log(TAG, "remove task:" + id);
        unRegisterTask(id);
        unregisterRx(id);
    }

    public void unRegister(int minId, int maxId) {
        for (int i = minId; i <= maxId; i++) {
            unRegister(i);
        }
    }

    private void unRegisterTask(int taskId) {
        this.mTasks.remove(taskId);
    }

    public LeMessageTask findTask(int taskId) {
        return (LeMessageTask) this.mTasks.get(taskId);
    }

    public <T> Observable<T> registerRx(int id, boolean onMainThread) {
        List<Subject> subjectList = (List) this.subjectMapper.get(id);
        if (subjectList == null) {
            subjectList = new ArrayList();
            this.subjectMapper.put(id, subjectList);
        }
        Subject<T, T> subject = PublishSubject.create();
        if (onMainThread) {
            subject.observeOn(AndroidSchedulers.mainThread());
        }
        subjectList.add(subject);
        return subject;
    }

    public Observable<LeResponseMessage> registerRxOnMainThread(int id) {
        return registerRx(id, true);
    }

    private void unregisterRx(int id) {
        List<Subject> subjectList = (List) this.subjectMapper.get(id);
        if (subjectList != null) {
            subjectList.clear();
            this.subjectMapper.remove(id);
        }
    }

    public void sendMessageByRx(int id) {
        sendMessageByRx(new LeResponseMessage(id));
    }

    public void sendMessageByRx(LeResponseMessage responseMessage) {
        if (responseMessage != null) {
            List<Subject> subjectList = (List) this.subjectMapper.get(responseMessage.getId());
            if (subjectList != null && !subjectList.isEmpty()) {
                for (Subject subject : subjectList) {
                    subject.onNext(responseMessage);
                }
            }
        }
    }
}
