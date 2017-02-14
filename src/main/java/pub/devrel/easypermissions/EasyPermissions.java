package pub.devrel.easypermissions;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EasyPermissions {
    public static final String[] PERMS = new String[]{"android.permission.CAMERA", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.RECORD_AUDIO", "android.permission.READ_PHONE_STATE", "android.permission.CALL_PHONE", "android.permission.SEND_SMS", "android.permission.RECEIVE_SMS", "android.permission.READ_SMS", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private static final String TAG = "EasyPermissions";

    public interface PermissionCallbacks extends OnRequestPermissionsResultCallback {
        void onPermissionsDenied(List<String> list);

        void onPermissionsGranted(List<String> list);
    }

    public static boolean hasPermissions(Context context, String... perms) {
        for (String perm : perms) {
            boolean hasPerm;
            if (ContextCompat.checkSelfPermission(context, perm) == 0) {
                hasPerm = true;
            } else {
                hasPerm = false;
            }
            if (!hasPerm) {
                return false;
            }
        }
        return true;
    }

    public static void requestPermissions(Object object, String rationale, int requestCode, String... perms) {
        checkCallingObjectSuitability(object);
        boolean shouldShowRationale = false;
        for (String perm : perms) {
            if (shouldShowRationale || shouldShowRequestPermissionRationale(object, perm)) {
                shouldShowRationale = true;
            } else {
                shouldShowRationale = false;
            }
        }
        if (shouldShowRationale) {
            executePermissionsRequest(object, perms, requestCode);
        } else {
            executePermissionsRequest(object, perms, requestCode);
        }
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults, Object object) {
        checkCallingObjectSuitability(object);
        PermissionCallbacks callbacks = (PermissionCallbacks) object;
        ArrayList<String> granted = new ArrayList();
        ArrayList<String> denied = new ArrayList();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == 0) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }
        if (!granted.isEmpty()) {
            callbacks.onPermissionsGranted(granted);
        }
        if (!denied.isEmpty()) {
            callbacks.onPermissionsDenied(denied);
        }
        if (!granted.isEmpty() && denied.isEmpty()) {
            runAnnotatedMethods(object, requestCode);
        }
        if (granted.isEmpty() && denied.isEmpty()) {
            runAnnotatedMethods(object, requestCode);
        }
    }

    private static boolean shouldShowRequestPermissionRationale(Object object, String perm) {
        if (object instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) object, perm);
        }
        if (object instanceof Fragment) {
            return ((Fragment) object).shouldShowRequestPermissionRationale(perm);
        }
        return false;
    }

    private static void executePermissionsRequest(Object object, String[] perms, int requestCode) {
        checkCallingObjectSuitability(object);
        if (object instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) object, perms, requestCode);
        } else if (object instanceof Fragment) {
            ((Fragment) object).requestPermissions(perms, requestCode);
        }
    }

    private static Activity getActivity(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        }
        if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        }
        return null;
    }

    private static void runAnnotatedMethods(Object object, int requestCode) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(AfterPermissionGranted.class) && ((AfterPermissionGranted) method.getAnnotation(AfterPermissionGranted.class)).value() == requestCode) {
                if (method.getParameterTypes().length > 0) {
                    throw new RuntimeException("Cannot execute non-void method " + method.getName());
                }
                try {
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    method.invoke(object, new Object[0]);
                } catch (IllegalAccessException e) {
                    Log.e(TAG, "runDefaultMethod:IllegalAccessException", e);
                } catch (InvocationTargetException e2) {
                    Log.e(TAG, "runDefaultMethod:InvocationTargetException", e2);
                }
            }
        }
    }

    private static void checkCallingObjectSuitability(Object object) {
        if (!(object instanceof Fragment) && !(object instanceof Activity)) {
            throw new IllegalArgumentException("Caller must be an Activity or a Fragment.");
        } else if (!(object instanceof PermissionCallbacks)) {
            throw new IllegalArgumentException("Caller must implement PermissionCallbacks.");
        }
    }
}
