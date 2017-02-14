package com.letv.plugin.pluginloader.test;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import com.letv.plugin.pluginloader.R;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MyActivity extends FragmentActivity {
    private static final String TAG = "MyActivity";
    private FragmentStatePagerAdapter mFragmentStatePagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        public int getCount() {
            return 2;
        }

        public Fragment getItem(int position) {
            if (position == 0) {
                return new InstalledFragment();
            }
            return new ApkFragment();
        }

        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "已安装";
            }
            return "待安装";
        }
    };
    private ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_main);
        this.mViewPager = (ViewPager) findViewById(R.id.plugin_pager);
        this.mViewPager.setAdapter(this.mFragmentStatePagerAdapter);
    }

    private void getPerms() {
        final PackageManager pm = getPackageManager();
        final List<PackageInfo> pkgs = pm.getInstalledPackages(4096);
        new Thread() {
            public void run() {
                IOException e;
                Throwable th;
                try {
                    PackageInfo pkg;
                    StringBuilder sb = new StringBuilder();
                    Set<String> ps = new TreeSet();
                    for (PackageInfo pkg2 : pkgs) {
                        if (pkg2.permissions != null && pkg2.permissions.length > 0) {
                            for (PermissionInfo permission : pkg2.permissions) {
                                ps.add(permission.name);
                            }
                        }
                    }
                    for (String p : ps) {
                        pkg2 = pm.getPackageInfo(pm.getPermissionInfo(p, 0).packageName, 0);
                        String re = String.format("<uses-permission android:name=\"%s\"/>", new Object[]{permission.name});
                        sb.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s", new Object[]{permission.packageName, pkg2.applicationInfo.loadLabel(pm), permission.name, permission.group, Integer.valueOf(permission.protectionLevel), permission.loadLabel(pm), permission.loadDescription(pm), re})).append("\r\n");
                    }
                    FileWriter w = null;
                    try {
                        FileWriter w2 = new FileWriter(new File(Environment.getExternalStorageDirectory(), "per.txt"));
                        try {
                            w2.write(sb.toString());
                            if (w2 != null) {
                                try {
                                    w2.close();
                                    w = w2;
                                    return;
                                } catch (IOException e2) {
                                    w = w2;
                                    return;
                                }
                            }
                        } catch (IOException e3) {
                            e = e3;
                            w = w2;
                            try {
                                e.printStackTrace();
                                if (w != null) {
                                    try {
                                        w.close();
                                    } catch (IOException e4) {
                                    }
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                if (w != null) {
                                    try {
                                        w.close();
                                    } catch (IOException e5) {
                                    }
                                }
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            w = w2;
                            if (w != null) {
                                w.close();
                            }
                            throw th;
                        }
                    } catch (IOException e6) {
                        e = e6;
                        e.printStackTrace();
                        if (w != null) {
                            w.close();
                        }
                    }
                } catch (NameNotFoundException e7) {
                    e7.printStackTrace();
                }
            }
        }.start();
    }
}
