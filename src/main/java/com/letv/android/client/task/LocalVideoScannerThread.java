package com.letv.android.client.task;

import android.os.Build;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.activity.MainActivity;
import com.letv.component.player.utils.NativeInfos;
import com.letv.core.bean.DownloadLocalVideoItemBean;
import com.letv.core.db.DBManager;
import com.letv.core.db.LocalVideoTraceHandler;
import com.letv.core.utils.FileUtils;
import com.letv.download.manager.StoreManager;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class LocalVideoScannerThread extends Thread {
    public static Object lock = new Object();
    private ArrayList<String> filterPath;
    private boolean isCancel;
    private ArrayList<DownloadLocalVideoItemBean> mDbList;
    private ArrayList<DownloadLocalVideoItemBean> mListFiles;
    private OnScannerListener mOnScannerListener;
    private ArrayList<DownloadLocalVideoItemBean> mTmpList;
    private LocalVideoTraceHandler mTraceHandler;
    private HashSet<String> scannerType;

    public interface OnScannerListener {
        void onPostExecute(ArrayList<DownloadLocalVideoItemBean> arrayList);

        void onPreScanner();

        void onProgressUpdate(DownloadLocalVideoItemBean downloadLocalVideoItemBean);
    }

    public LocalVideoScannerThread() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mListFiles = new ArrayList();
        this.mTmpList = new ArrayList();
        this.mDbList = new ArrayList();
        this.filterPath = new ArrayList();
        this.scannerType = new HashSet();
        this.isCancel = false;
        init();
    }

    public LocalVideoScannerThread(OnScannerListener listener) {
        this.mListFiles = new ArrayList();
        this.mTmpList = new ArrayList();
        this.mDbList = new ArrayList();
        this.filterPath = new ArrayList();
        this.scannerType = new HashSet();
        this.isCancel = false;
        setOnScannerListener(listener);
        init();
    }

    private void init() {
        this.mTraceHandler = DBManager.getInstance().getLocalVideoTrace();
    }

    public void run() {
        super.run();
        synchronized (lock) {
            onPreExecute();
            doInBackground(new Void[0]);
        }
    }

    public void onPreExecute() {
        if (this.mOnScannerListener != null) {
            this.mOnScannerListener.onPreScanner();
        }
        this.mTmpList = this.mTraceHandler.getLocalVideoHasPosition();
        this.mDbList = this.mTraceHandler.getLocalVideoList();
        this.mTraceHandler.delLocalVideoAll();
        if (this.mListFiles != null) {
            this.mListFiles.clear();
        } else {
            this.mListFiles = new ArrayList();
        }
        this.isCancel = false;
    }

    protected String doInBackground(Void... params) {
        if (LetvApplication.getInstance().getSuppportTssLevel() == 0 || !NativeInfos.ifSupportVfpOrNeon()) {
            this.scannerType.clear();
            this.scannerType.add("mp4");
            this.scannerType.add("3gp");
            this.scannerType.add(MainActivity.THIRD_PARTY_LETV);
        } else {
            this.scannerType.clear();
            this.scannerType.add("mp4");
            this.scannerType.add("3gp");
            this.scannerType.add(MainActivity.THIRD_PARTY_LETV);
        }
        traverseDBFiles();
        if (Build.MODEL.equals("vivo X5Max L")) {
            traverseFiles(new File("/storage/"));
        } else {
            traverseFiles(new File("/mnt/"));
        }
        onPostExecute(null);
        return null;
    }

    public void onProgressUpdate(DownloadLocalVideoItemBean... values) {
        if (this.mOnScannerListener != null && values != null) {
            this.mOnScannerListener.onProgressUpdate(values[0]);
        }
    }

    public void onPostExecute(String result) {
        this.isCancel = true;
        this.mTraceHandler.updateVideoPosition(this.mTmpList);
        if (this.mOnScannerListener != null) {
            this.mOnScannerListener.onPostExecute(this.mListFiles);
        }
    }

    public void cancelTask(boolean cancel) {
        this.isCancel = cancel;
    }

    public void setOnScannerListener(OnScannerListener listener) {
        this.mOnScannerListener = listener;
    }

    public void traverseDBFiles() {
        Iterator it = this.mDbList.iterator();
        while (it.hasNext()) {
            DownloadLocalVideoItemBean tmpItem = (DownloadLocalVideoItemBean) it.next();
            if (!this.isCancel) {
                File itemFile = new File(tmpItem.path);
                if (itemFile.exists() && !this.filterPath.contains(itemFile.getParent())) {
                    this.filterPath.add(itemFile.getParent());
                    File filterFile = new File(itemFile.getParent());
                    if (filterFile.exists() && filterFile.isDirectory()) {
                        File[] filterFiles = filterFile.listFiles();
                        int length = filterFiles.length;
                        int i = 0;
                        while (i < length) {
                            File tmpFile = filterFiles[i];
                            if (!this.isCancel) {
                                if (tmpFile.isFile()) {
                                    if (this.scannerType.contains(FileUtils.fileToType(tmpFile))) {
                                        DownloadLocalVideoItemBean item = FileUtils.fileToLocalVideoItem(tmpFile);
                                        boolean hasItem = false;
                                        Iterator it2 = this.mListFiles.iterator();
                                        while (it2.hasNext()) {
                                            DownloadLocalVideoItemBean itemTmp = (DownloadLocalVideoItemBean) it2.next();
                                            if (itemTmp != null && itemTmp.title.equalsIgnoreCase(item.title)) {
                                                hasItem = true;
                                                break;
                                            }
                                        }
                                        this.mTraceHandler.addLocalVideo(item);
                                        if (!hasItem) {
                                            this.mListFiles.add(item);
                                            onProgressUpdate(item);
                                        }
                                    }
                                }
                                i++;
                            } else {
                                return;
                            }
                        }
                        continue;
                    }
                }
            } else {
                return;
            }
        }
    }

    public void traverseFiles(File filePath) {
        if (!this.isCancel) {
            File[] files = filePath.listFiles();
            if (files != null) {
                int i = 0;
                while (i < files.length && !this.isCancel) {
                    if (!files[i].isDirectory()) {
                        if (this.scannerType.contains(FileUtils.fileToType(files[i])) && !this.filterPath.contains(files[i].getParent()) && files[i].length() > 0) {
                            DownloadLocalVideoItemBean item;
                            if (LetvApplication.getInstance().getSuppportTssLevel() == 0 || !NativeInfos.ifSupportVfpOrNeon()) {
                                item = FileUtils.fileToLocalVideoItemBySystem(files[i]);
                            } else {
                                item = FileUtils.fileToLocalVideoItem(files[i]);
                            }
                            boolean hasItem = false;
                            Iterator it = this.mListFiles.iterator();
                            while (it.hasNext()) {
                                DownloadLocalVideoItemBean itemTmp = (DownloadLocalVideoItemBean) it.next();
                                if (itemTmp != null && itemTmp.title.equalsIgnoreCase(item.title)) {
                                    hasItem = true;
                                    break;
                                }
                            }
                            this.mTraceHandler.addLocalVideo(item);
                            if (!hasItem) {
                                this.mListFiles.add(item);
                                onProgressUpdate(item);
                            }
                        }
                    } else if (!files[i].getAbsolutePath().toLowerCase().contains(StoreManager.PATH.toLowerCase())) {
                        traverseFiles(files[i]);
                    }
                    i++;
                }
            }
        }
    }
}
