package cn.jpush.android.util;

import java.io.File;
import java.util.Comparator;

final class n implements Comparator<File> {
    n() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        File file = (File) obj;
        File file2 = (File) obj2;
        return file.lastModified() > file2.lastModified() ? -1 : file.lastModified() < file2.lastModified() ? 1 : 0;
    }
}
