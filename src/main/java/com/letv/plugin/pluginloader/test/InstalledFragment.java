package com.letv.plugin.pluginloader.test;

import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.letv.plugin.pluginloader.R;
import com.letv.plugin.pluginloader.apk.pm.ApkManager;

public class InstalledFragment extends ListFragment implements ServiceConnection {
    private ArrayAdapter<ApkItem> adapter;
    final Handler handler = new Handler();
    private MyBroadcastReceiver mMyBroadcastReceiver = new MyBroadcastReceiver(this, null);

    public void onListItemClick(ListView l, View v, int position, long id) {
        ApkItem item = (ApkItem) this.adapter.getItem(position);
        if (v.getId() == R.id.button2) {
            Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(item.packageInfo.packageName);
            intent.addFlags(268435456);
            startActivity(intent);
        } else if (v.getId() == R.id.button3) {
            doUninstall(item);
        }
    }

    private void doUninstall(ApkItem item) {
        Builder builder = new Builder(getActivity());
        builder.setTitle("警告，你确定要删除么？");
        builder.setMessage("警告，你确定要删除" + item.title + "么？");
        builder.setNegativeButton("删除", new 1(this, item));
        builder.setNeutralButton("取消", null);
        builder.show();
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        startLoad();
    }

    private void startLoad() {
        new 2(this, "ApkScanner").start();
    }

    public void onServiceDisconnected(ComponentName componentName) {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mMyBroadcastReceiver.registerReceiver(getActivity().getApplication());
        this.adapter = new 3(this, getActivity(), 0);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEmptyText("没有安装插件");
        setListAdapter(this.adapter);
        setListShown(false);
        getListView().setOnItemClickListener(null);
        if (ApkManager.getInstance().isConnected()) {
            startLoad();
        } else {
            ApkManager.getInstance().addServiceConnection(this);
        }
    }

    public void onDestroy() {
        ApkManager.getInstance().removeServiceConnection(this);
        this.mMyBroadcastReceiver.unregisterReceiver(getActivity().getApplication());
        super.onDestroy();
    }
}
