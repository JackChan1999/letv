package com.letv.mobile.lebox.ui.qrcode;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.http.bean.CommonResponse;
import com.letv.mobile.http.utils.StringUtils;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.http.lebox.bean.KeyLoginBean;
import com.letv.mobile.lebox.http.lebox.request.KeyLoginHttpRequest;
import com.letv.mobile.lebox.jump.PageJumpUtil;
import com.letv.mobile.lebox.utils.DialogUtil;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.utils.SharedPreferencesUtil;
import com.letv.mobile.lebox.utils.Util;
import com.letv.pp.utils.NetworkUtils;
import java.util.Hashtable;
import java.util.UUID;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class QrCodeShareActivity extends Activity implements OnClickListener {
    private static final int COLOR_BLACK = -16777216;
    private static final int COLOR_WHITE = -1;
    private static final int PADDING_SIZE_MIN = 0;
    private static final String TAG = "QrCodeShareActivity";
    private final int QR_HEIGHT = 200;
    private final int QR_WIDTH = 200;
    private Button mBtnRefresh;
    private Button mBtnShare;
    private ImageView mIvQrCodeArea;
    private ImageView mIvTitleLeft;
    private TextView mTitleTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_gen);
        this.mIvTitleLeft = (ImageView) findViewById(R.id.common_nav_left);
        this.mIvQrCodeArea = (ImageView) findViewById(R.id.qrcode_gen_area);
        this.mBtnShare = (Button) findViewById(R.id.btn_share);
        this.mBtnRefresh = (Button) findViewById(R.id.btn_refresh);
        this.mTitleTextView = (TextView) findViewById(R.id.common_nav_title);
        this.mTitleTextView.setText(SharedPreferencesUtil.readData(SharedPreferencesUtil.LEBOX_NAME_KEY, ""));
        this.mIvTitleLeft.setOnClickListener(this);
        this.mBtnShare.setOnClickListener(this);
        this.mBtnRefresh.setOnClickListener(this);
        createQrCode();
    }

    public void onClick(View v) {
        int id = v.getId();
        if (R.id.btn_share == id) {
            this.mIvQrCodeArea.setDrawingCacheEnabled(true);
            PageJumpUtil.jumpToPageSystemShare(this, "", "", "", Uri.parse(Media.insertImage(getContentResolver(), this.mIvQrCodeArea.getDrawingCache(), null, null)));
        } else if (R.id.btn_refresh == id) {
            DialogUtil.showDialog(this, getResources().getString(R.string.qrcode_gen_refresh_prompt), "", "", null, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    QrCodeShareActivity.this.refreshQrCode();
                }
            });
        } else if (R.id.common_nav_left == id) {
            finish();
        }
    }

    private void createQRImage(ImageView imageView, String url) {
        getClass();
        getClass();
        try {
            Hashtable<EncodeHintType, String> hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, 200, 200, hints);
            boolean isFirstBlackPoint = true;
            int startBlackPointX = 0;
            int startBlackPointY = 0;
            int[] pixels = new int[40000];
            for (int y = 0; y < 200; y++) {
                for (int x = 0; x < 200; x++) {
                    if (bitMatrix.get(x, y)) {
                        if (isFirstBlackPoint) {
                            isFirstBlackPoint = false;
                            startBlackPointX = x;
                            startBlackPointY = y;
                            Logger.i(TAG, "QrCodeGen: x = " + x + ", y = " + y);
                        }
                        pixels[(y * 200) + x] = -16777216;
                    } else {
                        pixels[(y * 200) + x] = -1;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(200, 200, Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, 200, 0, 0, 200, 200);
            if (startBlackPointX > 0) {
                int x1 = startBlackPointX + 0;
                int y1 = startBlackPointY + 0;
                if (x1 < 0 || y1 < 0) {
                    imageView.setImageBitmap(bitmap);
                    return;
                }
                imageView.setImageBitmap(Bitmap.createBitmap(bitmap, x1, y1, 200 - (x1 * 2), 200 - (y1 * 2)));
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void refreshQrCode() {
        String oldCode = LeboxQrCodeBean.getCode();
        final String newCode = UUID.randomUUID().toString();
        KeyLoginHttpRequest.getLoginRequest(this, new TaskCallBack() {
            public void callback(int code, String msg, String errorCode, Object object) {
                if (code != 0) {
                    Logger.d(QrCodeShareActivity.TAG, "--KeyLoginHttpRequest-code=" + code + "-msg=" + msg + "-errorCode=" + errorCode);
                    QrCodeShareActivity.this.finish();
                } else if (object == null) {
                    QrCodeShareActivity.this.showLoggerD("KeyLoginHttpRequest", code, msg, errorCode, "object", object);
                    QrCodeShareActivity.this.finish();
                } else {
                    KeyLoginBean bean = (KeyLoginBean) ((CommonResponse) object).getData();
                    String strIsAdmin = bean.getIsAdmin();
                    if (StringUtils.equalsNull(strIsAdmin)) {
                        QrCodeShareActivity.this.showLoggerD("KeyLoginHttpRequest", code, msg, errorCode, "KeyLoginBean", bean);
                        QrCodeShareActivity.this.finish();
                    } else if (strIsAdmin.equals("1")) {
                        LeboxQrCodeBean.setCode(newCode);
                        QrCodeShareActivity.this.createQrCode();
                        Util.showToast(QrCodeShareActivity.this, R.string.qrcode_gen_refresh_success);
                    } else if (strIsAdmin.equals("0")) {
                        Util.showToast(QrCodeShareActivity.this, R.string.qrcode_gen_refresh_not_admin);
                        QrCodeShareActivity.this.finish();
                    }
                }
            }
        }).execute(KeyLoginHttpRequest.getKeyLoginParameter().combineParams());
    }

    private void createQrCode() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(LeboxQrCodeBean.KEY_SCHEME);
        sb.append(NetworkUtils.DELIMITER_COLON);
        sb.append("\"");
        sb.append(LeboxQrCodeBean.getScheme());
        sb.append("\"");
        sb.append(",");
        sb.append(LeboxQrCodeBean.KEY_VERSION);
        sb.append(NetworkUtils.DELIMITER_COLON);
        sb.append("\"");
        sb.append(LeboxQrCodeBean.getVersion());
        sb.append("\"");
        sb.append(",");
        sb.append(LeboxQrCodeBean.KEY_MAC);
        sb.append(NetworkUtils.DELIMITER_COLON);
        sb.append("\"");
        sb.append(LeboxQrCodeBean.getMac());
        sb.append("\"");
        sb.append(",");
        sb.append(LeboxQrCodeBean.KEY_SSID);
        sb.append(NetworkUtils.DELIMITER_COLON);
        sb.append("\"");
        sb.append(LeboxQrCodeBean.getSsid());
        sb.append("\"");
        sb.append(",");
        sb.append(LeboxQrCodeBean.KEY_PASSWORD);
        sb.append(NetworkUtils.DELIMITER_COLON);
        sb.append("\"");
        sb.append(LeboxQrCodeBean.getPassword());
        sb.append("\"");
        sb.append(",");
        sb.append(LeboxQrCodeBean.KEY_CODE);
        sb.append(NetworkUtils.DELIMITER_COLON);
        sb.append("\"");
        sb.append(LeboxQrCodeBean.getCode());
        sb.append("\"");
        sb.append("}");
        Logger.d(TAG, "create qr image json:" + sb.toString());
        createQRImage(this.mIvQrCodeArea, sb.toString());
    }

    private void showLoggerD(String title, int code, String msg, String errorCode, String dataTitle, Object data) {
        Logger.d(TAG, "--" + title + "-code=" + code + "-msg=" + msg + "-errorCode=" + errorCode + NetworkUtils.DELIMITER_LINE + dataTitle + SearchCriteria.EQ + data);
    }
}
