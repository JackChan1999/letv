package cn.jpush.android.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import java.util.ArrayList;
import java.util.List;

public final class s {
    public static ArrayList<aa> a(Context context, boolean z) {
        ArrayList<aa> arrayList = new ArrayList();
        try {
            List installedPackages = context.getPackageManager().getInstalledPackages(0);
            for (int i = 0; i < installedPackages.size(); i++) {
                PackageInfo packageInfo = (PackageInfo) installedPackages.get(i);
                aa aaVar = new aa();
                aaVar.a = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
                aaVar.b = packageInfo.packageName;
                aaVar.c = packageInfo.versionName;
                aaVar.d = packageInfo.versionCode;
                arrayList.add(aaVar);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            z.b();
        } catch (Exception e2) {
            e2.printStackTrace();
            z.b();
        }
        return arrayList;
    }

    public static String[] a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions;
        } catch (NameNotFoundException e) {
            z.i();
            return null;
        }
    }
}
