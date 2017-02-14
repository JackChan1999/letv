package com.letv.mobile.lebox.sweep.camera;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Build.VERSION;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.widget.Toast;
import com.letv.core.utils.LetvUtils;
import com.letv.mobile.lebox.utils.Logger;
import java.io.IOException;

public final class CameraManager {
    private static int MAX_FRAME_HEIGHT = 240;
    private static int MAX_FRAME_WIDTH = 240;
    private static int MIN_FRAME_HEIGHT = 240;
    private static int MIN_FRAME_WIDTH = 240;
    static final int SDK_INT;
    private static final String TAG = CameraManager.class.getSimpleName();
    private static CameraManager cameraManager;
    private static float scale;
    private final AutoFocusCallback autoFocusCallback;
    private Camera camera;
    private final CameraConfigurationManager configManager;
    private final Context context;
    private Rect framingRect;
    private Rect framingRectInPreview;
    private boolean initialized;
    private final PreviewCallback previewCallback;
    private boolean previewing;
    private final boolean useOneShotPreviewCallback;

    static {
        int sdkInt;
        try {
            sdkInt = Integer.parseInt(VERSION.SDK);
        } catch (NumberFormatException e) {
            sdkInt = 10000;
        }
        SDK_INT = sdkInt;
    }

    public static void init(Context context) {
        if (cameraManager == null) {
            cameraManager = new CameraManager(context);
        }
    }

    public static CameraManager get() {
        return cameraManager;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public void reset() {
        if (this.camera != null && this.configManager != null) {
            this.framingRect = null;
            this.framingRectInPreview = null;
            this.configManager.reset(this.camera);
        }
    }

    private CameraManager(Context context) {
        this.context = context;
        this.configManager = new CameraConfigurationManager(context);
        scale = this.context.getResources().getDisplayMetrics().density;
        MIN_FRAME_HEIGHT = (int) ((((float) MIN_FRAME_HEIGHT) * scale) + 0.5f);
        MIN_FRAME_WIDTH = (int) ((((float) MIN_FRAME_WIDTH) * scale) + 0.5f);
        MAX_FRAME_HEIGHT = (int) ((((float) MAX_FRAME_HEIGHT) * scale) + 0.5f);
        MAX_FRAME_WIDTH = (int) ((((float) MAX_FRAME_WIDTH) * scale) + 0.5f);
        this.useOneShotPreviewCallback = Integer.parseInt(VERSION.SDK) > 3;
        this.previewCallback = new PreviewCallback(this.configManager, this.useOneShotPreviewCallback);
        this.autoFocusCallback = new AutoFocusCallback();
    }

    public void openDriver(SurfaceHolder holder) throws IOException {
        if (this.camera == null) {
            try {
                this.camera = Camera.open();
            } catch (Exception e) {
            }
            Logger.e(TAG, "camera打开");
            if (this.camera == null) {
                throw new IOException();
            }
            this.camera.setPreviewDisplay(holder);
            if (!this.initialized) {
                this.initialized = true;
                this.configManager.initFromCameraParameters(this.camera);
            }
            this.configManager.setDesiredCameraParameters(this.camera);
        }
    }

    public void closeDriver() {
        if (this.camera != null) {
            FlashlightManager.disableFlashlight();
            this.camera.release();
            this.camera = null;
        }
    }

    public void startPreview() {
        Logger.e(TAG, "startPreview()");
        if (this.camera != null && !this.previewing) {
            this.camera.startPreview();
            this.previewing = true;
        }
    }

    public void stopPreview() {
        Logger.e(TAG, "stopPreview()");
        if (this.camera != null && this.previewing) {
            if (!this.useOneShotPreviewCallback) {
                this.camera.setPreviewCallback(null);
            }
            this.camera.stopPreview();
            this.previewCallback.setHandler(null, 0);
            this.autoFocusCallback.setHandler(null, 0);
            this.previewing = false;
        }
    }

    public void requestPreviewFrame(Handler handler, int message) {
        if (this.camera != null && this.previewing) {
            this.previewCallback.setHandler(handler, message);
            if (this.useOneShotPreviewCallback) {
                this.camera.setOneShotPreviewCallback(this.previewCallback);
            } else {
                this.camera.setPreviewCallback(this.previewCallback);
            }
        }
    }

    public void requestAutoFocus(Handler handler, int message) {
        if (this.camera != null && this.previewing) {
            this.autoFocusCallback.setHandler(handler, message);
            this.camera.autoFocus(this.autoFocusCallback);
        }
    }

    public Rect getFramingRect() {
        Point screenResolution = this.configManager.getScreenResolution();
        if (this.framingRect == null) {
            if (this.camera == null) {
                return null;
            }
            int width = (screenResolution.x * 3) / 4;
            if (width < MIN_FRAME_WIDTH) {
                width = MIN_FRAME_WIDTH;
            } else if (width > MAX_FRAME_WIDTH) {
                width = MAX_FRAME_WIDTH;
            }
            int height = (screenResolution.y * 3) / 4;
            if (height < MIN_FRAME_HEIGHT) {
                height = MIN_FRAME_HEIGHT;
            } else if (height > MAX_FRAME_HEIGHT) {
                height = MAX_FRAME_HEIGHT;
            }
            int leftOffset = (screenResolution.x - width) / 2;
            int topOffset = (screenResolution.y - height) / 3;
            this.framingRect = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);
            Logger.d(TAG, "Calculated framing rect: " + this.framingRect);
        }
        return this.framingRect;
    }

    public Rect getFramingRectInPreview() {
        if (this.framingRectInPreview == null) {
            Rect rect = new Rect(getFramingRect());
            Point cameraResolution = this.configManager.getCameraResolution();
            Point screenResolution = this.configManager.getScreenResolution();
            rect.left = (rect.left * cameraResolution.y) / screenResolution.x;
            rect.right = (rect.right * cameraResolution.y) / screenResolution.x;
            rect.top = (rect.top * cameraResolution.x) / screenResolution.y;
            rect.bottom = (rect.bottom * cameraResolution.x) / screenResolution.y;
            this.framingRectInPreview = rect;
        }
        return this.framingRectInPreview;
    }

    public PlanarYUVLuminanceSource buildLuminanceSource(byte[] data, int width, int height) {
        Rect rect = getFramingRectInPreview();
        int previewFormat = this.configManager.getPreviewFormat();
        String previewFormatString = this.configManager.getPreviewFormatString();
        switch (previewFormat) {
            case 16:
            case 17:
                return new PlanarYUVLuminanceSource(data, width, height, rect.left, rect.top, rect.width(), rect.height());
            default:
                if ("yuv420p".equals(previewFormatString)) {
                    return new PlanarYUVLuminanceSource(data, width, height, rect.left, rect.top, rect.width(), rect.height());
                }
                throw new IllegalArgumentException("Unsupported picture format: " + previewFormat + LetvUtils.CHARACTER_BACKSLASH + previewFormatString);
        }
    }

    public void zoomIn() {
        if (this.camera != null) {
            Parameters parameters = this.camera.getParameters();
            int cz = parameters.getZoom();
            if (cz > 0) {
                parameters.setZoom(cz - 1);
                this.camera.setParameters(parameters);
                return;
            }
            Toast.makeText(this.context, "焦距已经调节到最小", 0).show();
        }
    }

    public void zoomOut() {
        if (this.camera != null) {
            Parameters parameters = this.camera.getParameters();
            int cz = parameters.getZoom();
            if (cz < parameters.getMaxZoom()) {
                parameters.setZoom(cz + 1);
                this.camera.setParameters(parameters);
                return;
            }
            Toast.makeText(this.context, "焦距已经调节到最大", 0).show();
        }
    }
}
