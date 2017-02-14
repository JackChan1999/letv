package com.letv.mobile.lebox.httpmanager;

import android.content.Context;
import android.text.TextUtils;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.http.bean.CommonResponse;
import com.letv.mobile.lebox.LeBoxApp;
import com.letv.mobile.lebox.config.LeBoxAppConfig;
import com.letv.mobile.lebox.heartbeat.HeartbeatManager;
import com.letv.mobile.lebox.http.lebox.LeBoxDynamicHttpBaseParameter;
import com.letv.mobile.lebox.http.lebox.LeBoxGlobalHttpPathConfig;
import com.letv.mobile.lebox.http.lebox.bean.FollowAddExtBean;
import com.letv.mobile.lebox.http.lebox.bean.FollowAlbumBean;
import com.letv.mobile.lebox.http.lebox.bean.FollowAlbumTagBean;
import com.letv.mobile.lebox.http.lebox.bean.FollowGetAllListBean;
import com.letv.mobile.lebox.http.lebox.bean.HeartBeatBean;
import com.letv.mobile.lebox.http.lebox.bean.KeyLoginBean;
import com.letv.mobile.lebox.http.lebox.bean.OtaVersionBean;
import com.letv.mobile.lebox.http.lebox.bean.TaskAllListBean;
import com.letv.mobile.lebox.http.lebox.bean.TaskVersionBean;
import com.letv.mobile.lebox.http.lebox.bean.TaskVideoBean;
import com.letv.mobile.lebox.http.lebox.bean.WifiNameGetBean;
import com.letv.mobile.lebox.http.lebox.request.FollowAddHttpRequest;
import com.letv.mobile.lebox.http.lebox.request.FollowDelHttpRequest;
import com.letv.mobile.lebox.http.lebox.request.FollowGetAllHttpRequest;
import com.letv.mobile.lebox.http.lebox.request.HeartBeatHttpRequest;
import com.letv.mobile.lebox.http.lebox.request.KeyLoginHttpRequest;
import com.letv.mobile.lebox.http.lebox.request.OtaUpgradeHttpRequest;
import com.letv.mobile.lebox.http.lebox.request.OtaVersionHttpRequest;
import com.letv.mobile.lebox.http.lebox.request.TaskAddHttpRequest;
import com.letv.mobile.lebox.http.lebox.request.TaskGetAllHttpRequest;
import com.letv.mobile.lebox.http.lebox.request.TaskPauseHttpRequest;
import com.letv.mobile.lebox.http.lebox.request.TaskRemoveHttpRequest;
import com.letv.mobile.lebox.http.lebox.request.TaskUpdateHttpRequest;
import com.letv.mobile.lebox.http.lebox.request.TaskVersionHttpRequest;
import com.letv.mobile.lebox.http.lebox.request.TaskWaitHttpRequest;
import com.letv.mobile.lebox.http.lebox.request.WifiNameGetHttpRequest;
import com.letv.mobile.lebox.http.lebox.request.WifiNameSetHttpRequest;
import com.letv.mobile.lebox.ui.download.DownloadAlbum;
import com.letv.mobile.lebox.ui.qrcode.LeboxQrCodeBean;
import com.letv.mobile.lebox.utils.DeviceUtils;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.utils.SharedPreferencesUtil;
import com.letv.mobile.lebox.utils.Util;
import java.util.ArrayList;
import java.util.List;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpRequesetManager {
    public static final int CHECK_ALL = 0;
    public static final int CHECK_COMPLETE = 1;
    public static final int CHECK_UNFINISHED = 2;
    public static final String FILE_DOWNLOAD_TYPE_PICTURE_ALBUM = "2";
    public static final String FILE_DOWNLOAD_TYPE_PICTURE_VIDEO = "1";
    public static final String FILE_DOWNLOAD_TYPE_VIDEO = "0";
    static final String TAG = HttpRequesetManager.class.getSimpleName();
    private static HttpRequesetManager mManager;
    private final HttpCacheAssistant mCacheAssistant = HttpCacheAssistant.getInstanced();
    private final Context mContext = LeBoxApp.getApplication();

    public interface HttpCallBack<T> {
        void callback(int i, String str, String str2, T t);
    }

    private HttpRequesetManager() {
    }

    public static HttpRequesetManager getInstance() {
        if (mManager == null) {
            synchronized (HttpRequesetManager.class) {
                mManager = new HttpRequesetManager();
            }
        }
        return mManager;
    }

    public void getAllDownloadFinishTask(final HttpCallBack<List<TaskVideoBean>> callBack) {
        Logger.d(TAG, "--getAllDownloadFinishTask--CompletedList size=" + this.mCacheAssistant.getCompleteList().size() + "--version：" + this.mCacheAssistant.getCompleteVersion());
        if (TextUtils.isEmpty(this.mCacheAssistant.getCompleteVersion())) {
            getTaskAllBeanList(new HttpCallBack<List<TaskVideoBean>>() {
                public void callback(int code, String msg, String errorCode, List<TaskVideoBean> object) {
                    if (code == 0 || object != null) {
                        Logger.d(HttpRequesetManager.TAG, "---getAllDownloadFinishTask--CompletedList=" + HttpRequesetManager.this.mCacheAssistant.getCompleteList());
                        if (callBack != null) {
                            callBack.callback(code, msg, errorCode, HttpRequesetManager.this.mCacheAssistant.getCompleteList());
                        }
                        HttpRequesetManager.this.checkTaskVersion(new 1(this), 1);
                    } else if (callBack != null) {
                        callBack.callback(code, msg, errorCode, null);
                    }
                }
            }, "1");
        } else {
            checkTaskVersion(new HttpCallBack<Boolean>() {
                public void callback(int code, String msg, String errorCode, Boolean object) {
                    if (code != 0 || object == null) {
                        if (callBack != null) {
                            callBack.callback(code, msg, errorCode, null);
                        }
                    } else if (object.booleanValue()) {
                        HttpRequesetManager.this.getTaskAllBeanList(new 1(this), "1");
                    } else if (callBack != null) {
                        callBack.callback(code, msg, errorCode, HttpRequesetManager.this.mCacheAssistant.getCompleteList());
                    }
                }
            }, 1);
        }
    }

    public void getAllUnfinishTask(final HttpCallBack<List<TaskVideoBean>> callBack) {
        Logger.d(TAG, "--getAllUnfinishTask--UnfinishList size=" + this.mCacheAssistant.getUnFinishList().size() + "--version：" + this.mCacheAssistant.getUnFinishVersion());
        if (TextUtils.isEmpty(this.mCacheAssistant.getUnFinishVersion())) {
            getTaskAllBeanList(new HttpCallBack<List<TaskVideoBean>>() {
                public void callback(int code, String msg, String errorCode, List<TaskVideoBean> object) {
                    if (object != null) {
                        if (callBack != null) {
                            callBack.callback(code, msg, errorCode, HttpRequesetManager.this.mCacheAssistant.getUnFinishList());
                        }
                        HttpRequesetManager.this.checkTaskVersion(new 1(this), 2);
                    } else if (callBack != null) {
                        callBack.callback(code, msg, errorCode, null);
                    }
                }
            }, "2");
        } else {
            checkTaskVersion(new HttpCallBack<Boolean>() {
                public void callback(int code, String msg, String errorCode, Boolean object) {
                    if (object == null) {
                        if (callBack != null) {
                            callBack.callback(code, msg, errorCode, null);
                        }
                    } else if (object.booleanValue()) {
                        HttpRequesetManager.this.getTaskAllBeanList(new 1(this), "2");
                    } else if (callBack != null) {
                        callBack.callback(code, msg, errorCode, HttpRequesetManager.this.mCacheAssistant.getUnFinishList());
                    }
                }
            }, 2);
        }
    }

    public void getTaskAllBeanList(final HttpCallBack<List<TaskVideoBean>> callBack, final String type) {
        TaskGetAllHttpRequest.getGetAllRequest(this.mContext, new TaskCallBack() {
            public void callback(int code, String msg, String errorCode, Object object) {
                if (code == 0 && object != null && (object instanceof CommonResponse)) {
                    TaskAllListBean allListBean = (TaskAllListBean) ((CommonResponse) object).getData();
                    Logger.d(HttpRequesetManager.TAG, "---getTaskAllBeanList--allListBean=" + allListBean);
                    if ("1".equals(type)) {
                        HttpRequesetManager.this.mCacheAssistant.setCompleteList(allListBean.getList());
                    } else if ("2".equals(type)) {
                        HttpRequesetManager.this.mCacheAssistant.setUnFinishListList(allListBean.getList());
                    } else if ("0".equals(type)) {
                        HttpRequesetManager.this.mCacheAssistant.setAllList(allListBean.getList());
                    }
                    if (callBack != null) {
                        callBack.callback(code, msg, errorCode, allListBean.getList());
                    }
                } else if (callBack != null) {
                    callBack.callback(code, msg, errorCode, null);
                }
            }
        }).execute(TaskGetAllHttpRequest.getTaskGetAllParameter(type, null, "1").combineParams());
    }

    public void checkTaskVersion(final HttpCallBack<Boolean> callBack, final int checkType) {
        TaskVersionHttpRequest.getVersionRequest(this.mContext, new TaskCallBack() {
            public void callback(int code, String msg, String errorCode, Object object) {
                if (code == 0 && object != null && (object instanceof CommonResponse)) {
                    TaskVersionBean bean = (TaskVersionBean) ((CommonResponse) object).getData();
                    Logger.d(HttpRequesetManager.TAG, "--checkTaskVersion--TaskVersionBean=" + bean);
                    switch (checkType) {
                        case 0:
                            if (!(TextUtils.isEmpty(bean.getCompletedVer()) || TextUtils.isEmpty(bean.getUnFinishedVer()))) {
                                HttpRequesetManager.this.mCacheAssistant.setCompleteVersion(bean.getCompletedVer());
                                HttpRequesetManager.this.mCacheAssistant.setUnFinishVersion(bean.getUnFinishedVer());
                            }
                            if (callBack != null) {
                                callBack.callback(code, msg, errorCode, Boolean.valueOf(true));
                                return;
                            }
                            return;
                        case 1:
                            if (!TextUtils.isEmpty(bean.getCompletedVer())) {
                                if (!bean.getCompletedVer().equals(HttpRequesetManager.this.mCacheAssistant.getCompleteVersion())) {
                                    HttpRequesetManager.this.mCacheAssistant.setCompleteVersion(bean.getCompletedVer());
                                    if (callBack != null) {
                                        callBack.callback(code, msg, errorCode, Boolean.valueOf(true));
                                        return;
                                    }
                                    return;
                                } else if (callBack != null) {
                                    callBack.callback(code, msg, errorCode, Boolean.valueOf(false));
                                    return;
                                } else {
                                    return;
                                }
                            }
                            return;
                        case 2:
                            if (!TextUtils.isEmpty(bean.getUnFinishedVer())) {
                                if (!bean.getUnFinishedVer().equals(HttpRequesetManager.this.mCacheAssistant.getUnFinishVersion())) {
                                    if (callBack != null) {
                                        callBack.callback(code, msg, errorCode, Boolean.valueOf(true));
                                    }
                                    HttpRequesetManager.this.mCacheAssistant.setUnFinishVersion(bean.getUnFinishedVer());
                                    return;
                                } else if (callBack != null) {
                                    callBack.callback(code, msg, errorCode, Boolean.valueOf(false));
                                    return;
                                } else {
                                    return;
                                }
                            }
                            return;
                        default:
                            return;
                    }
                }
                Logger.e(HttpRequesetManager.TAG, "--checkTaskVersion---code=" + code + "-msg=" + msg + "-errorCode=" + errorCode + "-object=" + SearchCriteria.EQ + object);
                if (callBack != null) {
                    callBack.callback(code, msg, errorCode, Boolean.valueOf(false));
                }
            }
        }).execute(TaskVersionHttpRequest.getTaskVersionParameter().combineParams());
    }

    public static List<DownloadAlbum> getDownloadAlbumList(List<TaskVideoBean> list) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        List<DownloadAlbum> albumList = new ArrayList();
        for (TaskVideoBean videoBean : list) {
            DownloadAlbum album;
            if (albumList.size() == 0) {
                album = new DownloadAlbum();
                album.addVideo(videoBean);
                albumList.add(album);
            } else {
                boolean isHave = false;
                int id = -1;
                int i = 0;
                while (i < albumList.size()) {
                    if (((DownloadAlbum) albumList.get(i)).pid != 0 && ((DownloadAlbum) albumList.get(i)).pid == Util.getLong(videoBean.getPid(), 0)) {
                        isHave = true;
                        id = i;
                        break;
                    }
                    i++;
                }
                if (isHave) {
                    ((DownloadAlbum) albumList.get(id)).addVideo(videoBean);
                } else {
                    album = new DownloadAlbum();
                    album.addVideo(videoBean);
                    albumList.add(album);
                }
            }
        }
        return albumList;
    }

    public void setTaskPause(List<String> vidList, HttpCallBack<List<TaskVideoBean>> callBack) {
        Logger.d(TAG, "---SetTaskStart---vidList=" + vidList);
        if (vidList != null && vidList.size() != 0) {
            StringBuilder b = new StringBuilder();
            for (String vid : vidList) {
                b.append(vid);
                b.append(",");
            }
            String vidStr = b.toString().substring(0, b.length() - 1);
            Logger.d(TAG, "---SetTaskPause---vidStr=" + vidStr);
            setTaskPause(vidStr, (HttpCallBack) callBack);
        } else if (callBack != null) {
            callBack.callback(4, "vid is null", "4", null);
        }
    }

    public void setTaskPause(String vid, final HttpCallBack<List<TaskVideoBean>> callBack) {
        TaskPauseHttpRequest.getPauseRequest(this.mContext, new TaskCallBack() {
            public void callback(int code, String msg, String errorCode, Object object) {
                Logger.d(HttpRequesetManager.TAG, "--TaskPauseHttpRequest---code=" + code + "-msg=" + msg + "-errorCode=" + errorCode + "-object=" + SearchCriteria.EQ + object);
                if (code == 0 || "0".equals(errorCode)) {
                    HttpRequesetManager.this.getTaskAllBeanList(new 1(this), "2");
                } else if (callBack != null) {
                    callBack.callback(code, msg, errorCode, null);
                }
            }
        }).execute(TaskPauseHttpRequest.getTaskPauseParameter(vid).combineParams());
    }

    public void setTaskStart(List<String> vidList, HttpCallBack<List<TaskVideoBean>> callBack) {
        Logger.d(TAG, "---SetTaskStart---vidList=" + vidList);
        if (vidList != null && vidList.size() != 0) {
            StringBuilder b = new StringBuilder();
            for (String vid : vidList) {
                b.append(vid);
                b.append(",");
            }
            String vidStr = b.toString().substring(0, b.length() - 1);
            Logger.d(TAG, "---SetTaskStart---vidStr=" + vidStr);
            setTaskStart(vidStr, (HttpCallBack) callBack);
        } else if (callBack != null) {
            callBack.callback(4, "vid is null", "4", null);
        }
    }

    public void setTaskStart(String vid, final HttpCallBack<List<TaskVideoBean>> callBack) {
        TaskWaitHttpRequest.getWaitRequest(this.mContext, new TaskCallBack() {
            public void callback(int code, String msg, String errorCode, Object object) {
                Logger.d(HttpRequesetManager.TAG, "--TaskWaitHttpRequest---code=" + code + "-msg=" + msg + "-errorCode=" + errorCode + "-object=" + SearchCriteria.EQ + object);
                if (code == 0 || "0".equals(errorCode)) {
                    HttpRequesetManager.this.getTaskAllBeanList(new 1(this), "2");
                } else if (callBack != null) {
                    callBack.callback(code, msg, errorCode, null);
                }
            }
        }).execute(TaskWaitHttpRequest.getTaskWaitParameter(vid).combineParams());
    }

    public void addTask(long vid, long pid, String stream, String tag, HttpCallBack<List<TaskVideoBean>> callBack) {
        JSONObject extJson = new JSONObject();
        try {
            extJson.put(TaskAddHttpRequest.stream, stream);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Logger.d(TAG, "----addTask---extJson=" + extJson.toString());
        addTask(String.valueOf(vid), String.valueOf(pid), extJson.toString(), tag, (HttpCallBack) callBack);
    }

    public static String getAddTaskParamTag(String title, String cover, String albumTitle, String albumCover, String isEnd) throws JSONException {
        JSONObject tagJson = new JSONObject();
        tagJson.put("title", title);
        tagJson.put("cover", cover);
        tagJson.put("albumTitle", albumTitle);
        tagJson.put("albumCover", albumCover);
        tagJson.put("isEnd", isEnd);
        tagJson.put("isWatch", "0");
        return tagJson.toString();
    }

    public void addTask(String vid, String pid, String ext, String tag, final HttpCallBack<List<TaskVideoBean>> callBack) {
        TaskAddHttpRequest.getAddRequest(this.mContext, new TaskCallBack() {
            public void callback(int code, String msg, String errorCode, Object object) {
                Logger.d(HttpRequesetManager.TAG, "--TaskAddHttpRequest---code=" + code + "-msg=" + msg + "-errorCode=" + errorCode + "-object=" + object);
                if (code == 0 || "0".equals(errorCode)) {
                    HttpRequesetManager.this.getTaskAllBeanList(new 1(this), "2");
                } else if (callBack != null) {
                    callBack.callback(code, msg, errorCode, null);
                }
            }
        }).execute(TaskAddHttpRequest.getTaskAddParameter(vid, pid, ext, tag).combineParams());
    }

    public void deleteTask(List<String> taskList, HttpCallBack<List<TaskVideoBean>> callBack, String type) {
        Logger.d(TAG, "---DelectTask---taskList=" + taskList);
        if (taskList != null && taskList.size() != 0) {
            StringBuilder b = new StringBuilder();
            for (String vid : taskList) {
                b.append(vid);
                b.append(",");
            }
            String vidStr = b.toString().substring(0, b.length() - 1);
            Logger.d(TAG, "---DelectTask---vidStr=" + vidStr);
            deleteTask(vidStr, (HttpCallBack) callBack, type);
        } else if (callBack != null) {
            callBack.callback(4, "taskList is null", "4", null);
        }
    }

    public void deleteTask(String task, final HttpCallBack<List<TaskVideoBean>> callBack, final String type) {
        TaskRemoveHttpRequest.getRemoveRequest(this.mContext, new TaskCallBack() {
            public void callback(int code, String msg, String errorCode, Object object) {
                Logger.d(HttpRequesetManager.TAG, "--TaskRemoveHttpRequest---code=" + code + "-msg=" + msg + "-errorCode=" + errorCode + "-object=" + object);
                if (code == 0 || "0".equals(errorCode)) {
                    HttpRequesetManager.this.getTaskAllBeanList(new 1(this), type);
                } else if (callBack != null) {
                    callBack.callback(code, msg, errorCode, null);
                }
            }
        }).execute(TaskRemoveHttpRequest.getTaskRemoveParameter(task).combineParams());
    }

    public void TaskUpdateTag(String vid, String title, String cover, String albumTitle, String albumCover, String isEnd, String isWatch, final HttpCallBack<Object> callBack) {
        TaskUpdateHttpRequest.getUpdateRequest(this.mContext, new TaskCallBack() {
            public void callback(int code, String msg, String errorCode, Object object) {
                if (callBack != null) {
                    callBack.callback(code, msg, errorCode, object);
                }
            }
        }).execute(TaskUpdateHttpRequest.getTaskUpdateParameter(vid, title, cover, albumTitle, albumCover, isEnd, isWatch).combineParams());
    }

    public void getVideoinfo() {
    }

    public void getLeBoxName(final HttpCallBack<String> callBack) {
        WifiNameGetHttpRequest.getNameGetRequest(this.mContext, new TaskCallBack() {
            public void callback(int code, String msg, String errorCode, Object object) {
                Logger.d(HttpRequesetManager.TAG, "--WifiNameGetHttpRequest-code=" + code + "-msg=" + msg + "-errorCode=" + errorCode + "-object=" + object);
                if (code == 0 && object != null && (object instanceof CommonResponse)) {
                    WifiNameGetBean bean = (WifiNameGetBean) ((CommonResponse) object).getData();
                    Logger.d(HttpRequesetManager.TAG, "WifiNameGetBean :" + bean);
                    SharedPreferencesUtil.writeData(SharedPreferencesUtil.LEBOX_NAME_KEY, bean.getName());
                    if (callBack != null) {
                        callBack.callback(code, msg, errorCode, bean.getName());
                    }
                } else if (callBack != null) {
                    callBack.callback(code, msg, errorCode, SharedPreferencesUtil.readData(SharedPreferencesUtil.LEBOX_NAME_KEY, ""));
                }
            }
        }).execute(WifiNameGetHttpRequest.getWifiNameGetParameter().combineParams());
    }

    public void setLeboxName(final HttpCallBack<String> callBack, final String name) {
        WifiNameSetHttpRequest.getNameSetRequest(this.mContext, new TaskCallBack() {
            public void callback(int code, String msg, String errorCode, Object object) {
                Logger.d(HttpRequesetManager.TAG, "--WifiNameSetHttpRequest-code=" + code + "-msg=" + msg + "-errorCode=" + errorCode + "-object=" + object);
                if (code == 0) {
                    SharedPreferencesUtil.writeData(SharedPreferencesUtil.LEBOX_NAME_KEY, name);
                    if (callBack != null) {
                        callBack.callback(code, msg, errorCode, name);
                    }
                } else if (callBack != null) {
                    callBack.callback(code, msg, errorCode, SharedPreferencesUtil.readData(SharedPreferencesUtil.LEBOX_NAME_KEY, ""));
                }
            }
        }).execute(WifiNameSetHttpRequest.getWifiNameSetParameter(name).combineParams());
    }

    public void getUserPermissionFormCache(HttpCallBack<String> callBack) {
        if (HttpCacheAssistant.getInstanced().getUserIdentity() == 1) {
            if (callBack != null) {
                callBack.callback(0, "", "0", "1");
            }
        } else if (HttpCacheAssistant.getInstanced().getUserIdentity() != 2) {
            getUserPermission(callBack);
        } else if (callBack != null) {
            callBack.callback(0, "", "0", "0");
        }
    }

    public void getUserPermission(final HttpCallBack<String> callBack) {
        KeyLoginHttpRequest.getLoginRequest(this.mContext, new TaskCallBack() {
            public void callback(int code, String msg, String errorCode, Object object) {
                Logger.d(HttpRequesetManager.TAG, "-getUserPermission--code=" + code + "-msg=" + msg + "-errorCode=" + errorCode + "--object=" + object);
                if (code == 0 && object != null && (object instanceof CommonResponse)) {
                    KeyLoginBean bean = (KeyLoginBean) ((CommonResponse) object).getData();
                    Logger.d(HttpRequesetManager.TAG, "KeyLoginBean :" + bean);
                    if ("1".equals(bean.getIsAdmin())) {
                        HttpCacheAssistant.getInstanced().setUserIdentity(LeboxQrCodeBean.getSsid(), 1);
                    } else if ("0".equals(bean.getIsAdmin())) {
                        HttpCacheAssistant.getInstanced().setUserIdentity(LeboxQrCodeBean.getSsid(), 2);
                    }
                    if (callBack != null) {
                        callBack.callback(code, msg, errorCode, bean.getIsAdmin());
                    }
                } else if (1 == code && "1".equals(errorCode)) {
                    HttpCacheAssistant.getInstanced().setUserIdentity(LeboxQrCodeBean.getSsid(), 0);
                    if (callBack != null) {
                        callBack.callback(code, msg, errorCode, null);
                    }
                } else if (callBack != null) {
                    callBack.callback(code, msg, errorCode, null);
                }
            }
        }).execute(KeyLoginHttpRequest.getKeyLoginParameter().combineParams());
    }

    public void getOtaVersion(final HttpCallBack<OtaVersionBean> callBack) {
        OtaVersionHttpRequest.getVersionRequest(this.mContext, new TaskCallBack() {
            public void callback(int code, String msg, String errorCode, Object object) {
                if (code == 0 && object != null && (object instanceof CommonResponse)) {
                    OtaVersionBean bean = (OtaVersionBean) ((CommonResponse) object).getData();
                    Logger.d(HttpRequesetManager.TAG, "OtaVersionBean :" + bean);
                    if (callBack != null) {
                        callBack.callback(code, msg, errorCode, bean);
                    }
                } else if (callBack != null) {
                    callBack.callback(code, msg, errorCode, null);
                }
            }
        }).execute(OtaVersionHttpRequest.getOtaVersionParameter().combineParams());
    }

    public void getOtaUpgrade(final HttpCallBack<Object> callBack) {
        OtaUpgradeHttpRequest.getUpgradeRequest(this.mContext, new TaskCallBack() {
            public void callback(int code, String msg, String errorCode, Object object) {
                if (callBack != null) {
                    callBack.callback(code, msg, errorCode, object);
                }
            }
        }).execute(OtaUpgradeHttpRequest.getOtaUpgradeParameter().combineParams());
    }

    public void runHeartBeat(final HttpCallBack<HeartBeatBean> callBack) {
        HeartBeatHttpRequest.getSyncStatusRequest(this.mContext, new TaskCallBack() {
            public void callback(int code, String msg, String errorCode, Object object) {
                Logger.d(HttpRequesetManager.TAG, "-RunHeartBeat--code=" + code + "-msg=" + msg + "-errorCode=" + errorCode + "--object=" + object);
                if (object != null && (object instanceof CommonResponse)) {
                    HeartBeatBean bean = (HeartBeatBean) ((CommonResponse) object).getData();
                    Logger.d(HttpRequesetManager.TAG, "HeartBeatBean :" + bean);
                    if (callBack != null) {
                        callBack.callback(code, msg, errorCode, bean);
                    }
                } else if (callBack != null) {
                    callBack.callback(code, msg, errorCode, null);
                }
            }
        }).execute(HeartBeatHttpRequest.getWanStatusParameter().combineParams());
    }

    public static String getLeboxWifiHtmlPath() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(LeBoxAppConfig.getDynamicDomain());
        if (!LeBoxAppConfig.getDynamicDomain().endsWith("/")) {
            sBuilder.append("/");
        }
        sBuilder.append(LeBoxGlobalHttpPathConfig.PATH_GET_WEBS_WIFIMGR);
        sBuilder.append("?");
        sBuilder.append(LeBoxDynamicHttpBaseParameter.COMMON_KEY_CODE_SID);
        sBuilder.append(SearchCriteria.EQ);
        sBuilder.append(DeviceUtils.getDeviceId());
        sBuilder.append("&");
        sBuilder.append("code");
        sBuilder.append(SearchCriteria.EQ);
        sBuilder.append(LeboxQrCodeBean.getCode());
        return sBuilder.toString();
    }

    public static String getLeboxDeviceHtmlPath() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(LeBoxAppConfig.getDynamicDomain());
        if (!LeBoxAppConfig.getDynamicDomain().endsWith("/")) {
            sBuilder.append("/");
        }
        sBuilder.append(LeBoxGlobalHttpPathConfig.PATH_GET_WEBS_DEVMGR);
        sBuilder.append("?");
        sBuilder.append(LeBoxDynamicHttpBaseParameter.COMMON_KEY_CODE_SID);
        sBuilder.append(SearchCriteria.EQ);
        sBuilder.append(DeviceUtils.getDeviceId());
        sBuilder.append("&");
        sBuilder.append("code");
        sBuilder.append(SearchCriteria.EQ);
        sBuilder.append(LeboxQrCodeBean.getCode());
        return sBuilder.toString();
    }

    public void getAlbumAllVideo(final HttpCallBack<List<TaskVideoBean>> callBack, final String pid) {
        if (!TextUtils.isEmpty(this.mCacheAssistant.getCompleteVersion()) || !TextUtils.isEmpty(this.mCacheAssistant.getUnFinishVersion())) {
            TaskGetAllHttpRequest.getGetAllRequest(this.mContext, new TaskCallBack() {
                public void callback(int code, String msg, String errorCode, Object object) {
                    if (object != null && (object instanceof CommonResponse)) {
                        TaskAllListBean allListBean = (TaskAllListBean) ((CommonResponse) object).getData();
                        Logger.d(HttpRequesetManager.TAG, "---getTaskAllBeanList--allListBean=" + allListBean);
                        List<TaskVideoBean> list = allListBean.getList();
                        if (list != null && list.size() > 0) {
                            HttpRequesetManager.this.mCacheAssistant.setAllList(list);
                            if (callBack != null) {
                                callBack.callback(code, msg, errorCode, HttpRequesetManager.this.mCacheAssistant.getAllVideoByPid(pid, 0));
                            }
                        }
                        if (callBack != null) {
                            callBack.callback(code, msg, errorCode, null);
                        }
                    }
                    if (callBack != null) {
                        callBack.callback(code, msg, errorCode, null);
                    }
                }
            }).execute(TaskGetAllHttpRequest.getTaskGetAllParameter("0", null, "1"));
        } else if (callBack != null) {
            callBack.callback(0, "", "0", this.mCacheAssistant.getAllVideoByPid(pid, 0));
        }
    }

    public List<TaskVideoBean> getAlbumFormCache(String pid) {
        return this.mCacheAssistant.getAllVideoByPid(pid, 0);
    }

    public List<TaskVideoBean> getAlbumFormCache(long pid) {
        return getAlbumFormCache(String.valueOf(pid));
    }

    public TaskVideoBean getVideoFormCache(String vid) {
        if (TextUtils.isEmpty(vid)) {
            return null;
        }
        if (this.mCacheAssistant.isCompleteTaskEmpty() && this.mCacheAssistant.isUnFinishTaskEmpty()) {
            return null;
        }
        if (!this.mCacheAssistant.isCompleteTaskEmpty()) {
            for (TaskVideoBean video : this.mCacheAssistant.getCompleteList()) {
                if (vid.equals(video.getVid())) {
                    return video;
                }
            }
        }
        if (!this.mCacheAssistant.isUnFinishTaskEmpty()) {
            for (TaskVideoBean video2 : this.mCacheAssistant.getUnFinishList()) {
                if (vid.equals(video2.getVid())) {
                    return video2;
                }
            }
        }
        return null;
    }

    public boolean isHasDownload(String vid) {
        if (getVideoFormCache(vid) != null) {
            return true;
        }
        return false;
    }

    public boolean isHasDownload(long vid) {
        if (getVideoFormCache(String.valueOf(vid)) != null) {
            return true;
        }
        return false;
    }

    public void addFollowAlbum(final HttpCallBack<List<FollowAlbumBean>> callBack, String pid, FollowAddExtBean followAddExtBean, FollowAlbumTagBean albumTagBean) {
        FollowAddHttpRequest.getAddRequest(this.mContext, new TaskCallBack() {
            public void callback(int code, String msg, String errorCode, Object object) {
                Logger.d(HttpRequesetManager.TAG, "-addFollowAlbum--code=" + code + "-msg=" + msg + "-errorCode=" + errorCode + "--object=" + object);
                if (code == 0) {
                    HttpRequesetManager.this.getFollowAlbum(new 1(this), null, "1", "1", "0", "0", "1");
                } else if (callBack != null) {
                    callBack.callback(code, msg, errorCode, null);
                }
            }
        }).execute(FollowAddHttpRequest.getFollowAddParameter(pid, followAddExtBean.toString(), albumTagBean.toString()).combineParams());
    }

    public void deleteFollowAlbum(final HttpCallBack<Object> callBack, String pid) {
        this.mCacheAssistant.deleteFollowAlbumInCache(pid);
        FollowDelHttpRequest.getDelRequest(this.mContext, new TaskCallBack() {
            public void callback(int code, String msg, String errorCode, Object object) {
                if (callBack != null) {
                    callBack.callback(code, msg, errorCode, object);
                }
            }
        }).execute(FollowDelHttpRequest.getFollowDelParameter(pid).combineParams());
    }

    public void getFollowAlbum(final HttpCallBack<List<FollowAlbumBean>> callBack) {
        if (this.mCacheAssistant.isFollowEmpty()) {
            getFollowAlbum(new HttpCallBack<List<FollowAlbumBean>>() {
                public void callback(int code, String msg, String errorCode, List<FollowAlbumBean> object) {
                    if (code != 0 || object == null) {
                        Logger.d(HttpRequesetManager.TAG, "----获取追剧列表失败-----");
                        if (callBack != null) {
                            callBack.callback(code, msg, errorCode, null);
                            return;
                        }
                        return;
                    }
                    Logger.d(HttpRequesetManager.TAG, "---------list size=" + object.size());
                    if (callBack != null) {
                        callBack.callback(code, msg, errorCode, object);
                    }
                }
            }, null, "1", "1", "0", "0", "1");
        } else if (callBack != null) {
            callBack.callback(0, "", "0", this.mCacheAssistant.getFollowList());
        }
    }

    public void getFollowAlbum(final HttpCallBack<List<FollowAlbumBean>> callBack, String pid, String type, String needTag, String needInfo, String needAlbumInfo, String needExt) {
        FollowGetAllHttpRequest.getGetAllRequest(this.mContext, new TaskCallBack() {
            public void callback(int code, String msg, String errorCode, Object object) {
                if (code == 0 && object != null && (object instanceof CommonResponse)) {
                    FollowGetAllListBean list = (FollowGetAllListBean) ((CommonResponse) object).getData();
                    if (list != null) {
                        HttpRequesetManager.this.mCacheAssistant.setFollowList(list.getList());
                        if (callBack != null) {
                            callBack.callback(code, msg, errorCode, list.getList());
                        }
                    } else if (callBack != null) {
                        callBack.callback(code, msg, errorCode, null);
                    }
                } else if (callBack != null) {
                    callBack.callback(code, msg, errorCode, null);
                }
            }
        }).execute(FollowGetAllHttpRequest.getFollowGetAllParameter(pid, type, needTag, needInfo, needAlbumInfo, needExt).combineParams());
    }

    public static String getLeboxVideoFilePath(String type, String vid) {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(LeBoxAppConfig.getDynamicDomain());
        if (!LeBoxAppConfig.getDynamicDomain().endsWith("/")) {
            sBuilder.append("/");
        }
        sBuilder.append(LeBoxGlobalHttpPathConfig.PATH_GET_FILE_DOWNLOAD);
        sBuilder.append("?");
        sBuilder.append(LeBoxDynamicHttpBaseParameter.COMMON_KEY_CODE_SID);
        sBuilder.append(SearchCriteria.EQ);
        sBuilder.append(DeviceUtils.getDeviceId());
        sBuilder.append("&");
        sBuilder.append("code");
        sBuilder.append(SearchCriteria.EQ);
        sBuilder.append(LeboxQrCodeBean.getCode());
        sBuilder.append("&");
        sBuilder.append("type");
        sBuilder.append(SearchCriteria.EQ);
        sBuilder.append(type);
        sBuilder.append("&");
        sBuilder.append("vid");
        sBuilder.append(SearchCriteria.EQ);
        sBuilder.append(vid);
        return sBuilder.toString();
    }

    public void setVideoWatch(TaskVideoBean video) {
        if (HttpCacheAssistant.getInstanced().isAdmini() && video != null) {
            getInstance().TaskUpdateTag(video.getVid(), video.getTagTitle(), video.getTagCover(), video.getTagAlbumTitle(), video.getTagAlbumCover(), video.getTagIsEnd(), "1", null);
            video.setTagIsWatch(true);
            HeartbeatManager.getInstance().notifyStateChange(0);
        }
    }
}
