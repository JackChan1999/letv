package com.letv.plugin.pluginloader.test;

import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.letv.plugin.pluginloader.R;
import com.letv.plugin.pluginloader.apk.pm.ApkManager;

public class ApkFragment extends ListFragment implements ServiceConnection {
    private ArrayAdapter<ApkItem> adapter;
    final Handler handler = new Handler();
    boolean isViewCreated = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.adapter = new 1(this, getActivity(), 0);
    }

    private void doUninstall(ApkItem item) {
        Builder builder = new Builder(getActivity());
        builder.setTitle("警告，你确定要删除么？");
        builder.setMessage("警告，你确定要删除" + item.title + "么？");
        builder.setNegativeButton("删除", new 2(this, item));
        builder.setNeutralButton("取消", null);
        builder.show();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.isViewCreated = true;
        setEmptyText("没有在sdcard找到apk");
        setListAdapter(this.adapter);
        setListShown(false);
        getListView().setOnItemClickListener(null);
        if (ApkManager.getInstance().isConnected()) {
            startLoad();
        } else {
            ApkManager.getInstance().addServiceConnection(this);
        }
    }

    public void onDestroyView() {
        this.isViewCreated = false;
        super.onDestroyView();
    }

    public void setListShown(boolean shown) {
        if (this.isViewCreated) {
            super.setListShown(shown);
        }
    }

    private void startLoad() {
        this.handler.post(new 3(this));
        if (this.isViewCreated) {
            new 4(this, "ApkScanner").start();
        }
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        ApkItem item = (ApkItem) this.adapter.getItem(position);
        if (v.getId() == R.id.button2) {
            if (!item.installing) {
                if (!ApkManager.getInstance().isConnected()) {
                    Toast.makeText(getActivity(), "插件服务正在初始化，请稍后再试。。。", 0).show();
                }
                try {
                    if (ApkManager.getInstance().getPackageInfo(item.packageInfo.packageName, 0) != null) {
                        Toast.makeText(getActivity(), "已经安装了，不能再安装", 0).show();
                    } else {
                        new 5(this, item).start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ApkManager.getInstance().installPackage(item.apkfile, 0);
                    this.adapter.remove(item);
                }
            }
        } else if (v.getId() == R.id.button3) {
            doUninstall(item);
        }
    }

    private synchronized void doInstall(ApkItem item) {
        item.installing = true;
        this.handler.post(new 6(this));
        int result = ApkManager.getInstance().installPackage(item.apkfile, 0);
        item.installing = false;
        this.handler.post(new 7(this, result));
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        startLoad();
    }

    public void onServiceDisconnected(ComponentName componentName) {
    }

    public void onDestroy() {
        ApkManager.getInstance().removeServiceConnection(this);
        super.onDestroy();
    }
}
