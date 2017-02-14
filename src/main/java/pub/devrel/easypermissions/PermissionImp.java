package pub.devrel.easypermissions;

import android.app.Activity;
import com.letv.core.utils.LogInfo;
import java.util.List;
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks;

public class PermissionImp implements PermissionCallbacks {
    private static final int PEM_INIT_APP = 1;
    private Activity mActivity;
    private final OnPermissionsGrantedCallback mCallback;
    private int mPermsCount;

    public PermissionImp(Activity activity, OnPermissionsGrantedCallback callback) {
        this.mActivity = activity;
        this.mCallback = callback;
    }

    @AfterPermissionGranted(1)
    public void initTask() {
        String[] perms = new String[]{"android.permission.READ_PHONE_STATE", "android.permission.WRITE_EXTERNAL_STORAGE"};
        this.mPermsCount = perms.length;
        if (!EasyPermissions.hasPermissions(this.mActivity, perms)) {
            EasyPermissions.requestPermissions(this.mActivity, "乐视视频会使用网络权限", 1, perms);
        }
    }

    public void onPermissionsGranted(List<String> perms) {
        LogInfo.log("zhuqiao", "授权成功:" + perms.size());
        if (this.mCallback != null) {
            this.mCallback.onPermissionsGrantedCallback();
        }
    }

    public void onPermissionsDenied(List<String> perms) {
        LogInfo.log("zhuqiao", "授权失败:" + perms.size());
        this.mActivity.finish();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this.mActivity);
    }
}
