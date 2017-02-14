package com.letv.core.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.base.R;
import com.letv.core.BaseApplication;

public class CommonDialog extends Dialog implements OnClickListener {
    private final int BUTTON_CENTER = 2;
    private final int BUTTON_LEFT = 0;
    private final int BUTTON_RIGHT = 1;
    private final int DEFAULT_LAYOUT = R.layout.layout_common_dialog;
    private TextView centerButton;
    private boolean hasOneBtn = false;
    private TextView leftButton;
    private DialogInterface.OnClickListener mCenterListener;
    private ImageView mImageView;
    private int mLayout = DEFAULT_LAYOUT;
    private DialogInterface.OnClickListener mLeftListener;
    private DialogInterface.OnClickListener mRightListener;
    private View mRootView;
    private View mTwoBtnLayout;
    private TextView messageView;
    private TextView rightButton;
    private TextView titleView;

    public CommonDialog(Context context) {
        super(context, R.style.upgrade_dialog_style);
        initView(context);
    }

    public CommonDialog(Context context, int layoutId) {
        super(context, R.style.upgrade_dialog_style);
        mLayout = layoutId;
        initView(context);
    }

    private void initView(Context mContext) {
        mRootView = LayoutInflater.from(mContext).inflate(mLayout, null);
        if (mRootView != null) {
            leftButton = (TextView) mRootView.findViewById(R.id.common_dialog_btn_left);
            rightButton = (TextView) mRootView.findViewById(R.id.common_dialog_btn_right);
            centerButton = (TextView) mRootView.findViewById(R.id.common_dialog_btn_center);
            titleView = (TextView) mRootView.findViewById(R.id.common_dialog_title);
            messageView = (TextView) mRootView.findViewById(R.id.common_dialog_content);
            mTwoBtnLayout = mRootView.findViewById(R.id.common_dialog_btn_layout);
            mImageView = (ImageView) mRootView.findViewById(R.id.common_dialog_image);
            setContentView(mRootView);
            leftButton.setOnClickListener(;
            rightButton.setOnClickListener(;
            centerButton.setOnClickListener(;
        } else {
            setContentView(new View(mContext));
        }
        setCanceledOnTouchOutside(false);
    }

    public void setWindowParams(int w, int h, int gravity) {
        LayoutParams params = getWindow().getAttributes();
        params.width = w;
        params.height = h;
        if (gravity == -1) {
            gravity = 17;
        }
        params.gravity = gravity;
        getWindow().setAttributes(params);
    }

    public void setTitleColor(int color) {
        if (titleView != null && color > 0) {
            titleView.setTextColor(color);
        }
    }

    public void setMessageColor(int color) {
        if (messageView != null && color > 0) {
            messageView.setTextColor(color);
        }
    }

    public void setTitle(CharSequence title) {
        if (titleView != null && !TextUtils.isEmpty(title)) {
            titleView.setVisibility(View.VISIBLE);
            titleView.setText(title);
        }
    }

    public void setTitle(int titleId) {
        setTitle(BaseApplication.getInstance().getText(titleId));
    }

    public void setContent(String content) {
        if (messageView != null && !TextUtils.isEmpty(content)) {
            messageView.setVisibility(View.VISIBLE);
            messageView.setText(content);
        }
    }

    public void setImage(int resId) {
        if (mImageView != null && resId > 0) {
            mImageView.setImageResource(resId);
            mImageView.setVisibility(View.VISIBLE);
        }
    }

    public void setImage(Drawable drawable) {
        if (mImageView != null && drawable != null) {
            mImageView.setImageDrawable(drawable);
            mImageView.setVisibility(View.VISIBLE);
        }
    }

    public void setButtonText(int leftTextId, int rightTextId) {
        CharSequence charSequence = null;
        CharSequence text = leftTextId == -1 ? null : BaseApplication.getInstance().getText(leftTextId);
        if (rightTextId != -1) {
            charSequence = BaseApplication.getInstance().getText(rightTextId);
        }
        setButtonText(text, charSequence);
    }

    public void setButtonText(CharSequence leftText, CharSequence rightText) {
        if (mRootView != null) {
            hasOneBtn = false;
            if (!TextUtils.isEmpty(leftText)) {
                leftButton.setText(leftText);
            }
            if (!TextUtils.isEmpty(rightText)) {
                rightButton.setText(rightText);
            }
        }
    }

    public void setButtonText(int centerTextId) {
        CharSequence charSequence;
        if (centerTextId == -1) {
            charSequence = null;
        } else {
            charSequence = BaseApplication.getInstance().getString(centerTextId);
        }
        setButtonText(charSequence);
    }

    public void setButtonText(CharSequence centerText) {
        if (centerButton != null && !TextUtils.isEmpty(centerText)) {
            hasOneBtn = true;
            centerButton.setText(centerText);
        }
    }

    public void setButtonTextColor(int color) {
        if (color > 0) {
            centerButton.setTextColor(color);
        }
    }

    public void setButtonTextColor(int leftTextColorResId, int rightTextColorResId) {
        if (leftTextColorResId > 0) {
            leftButton.setTextColor(BaseApplication.getInstance().getResources().getColor(leftTextColorResId));
        }
        if (rightTextColorResId > 0) {
            rightButton.setTextColor(BaseApplication.getInstance().getResources().getColor(rightTextColorResId));
        }
    }

    public void setLeftOnClickListener(DialogInterface.OnClickListener listener) {
        if (leftButton != null && leftButton.getVisibility() == View.VISIBLE) {
            mLeftListener = listener;
        }
    }

    public void setRightOnClickListener(DialogInterface.OnClickListener listener) {
        if (rightButton != null && rightButton.getVisibility() == View.VISIBLE) {
            mRightListener = listener;
        }
    }

    public void setCenterOnClickListener(DialogInterface.OnClickListener listener) {
        if (centerButton != null && centerButton.getVisibility() == View.VISIBLE) {
            mLeftListener = listener;
        }
    }

    public View getRootView() {
        return mRootView;
    }

    public void dismiss() {
        super.dismiss();
        titleView.setText("");
        messageView.setText("");
        messageView.setVisibility(View.GONE);
        hasOneBtn = false;
        setButtonText((CharSequence) "", (CharSequence) "");
        setButtonText((CharSequence) "");
    }

    public void setHasOneBtn(boolean hasOneBtn) {
        hasOneBtn = hasOneBtn;
    }

    public void show() {
        if (mRootView != null) {
            if (hasOneBtn) {
                mTwoBtnLayout.setVisibility(View.GONE);
                centerButton.setVisibility(View.VISIBLE);
            } else {
                mTwoBtnLayout.setVisibility(View.VISIBLE);
                centerButton.setVisibility(View.GONE);
            }
        }
        super.show();
    }

    public void onClick(View v) {
        if (R.id.common_dialog_btn_left == v.getId()) {
            if (mLeftListener != null) {
                mLeftListener.onClick(this, 0);
            } else {
                dismiss();
            }
        } else if (R.id.common_dialog_btn_right == v.getId()) {
            if (mRightListener != null) {
                mRightListener.onClick(this, 1);
            }
        } else if (R.id.common_dialog_btn_center != v.getId()) {
        } else {
            if (mCenterListener != null) {
                mCenterListener.onClick(this, 2);
            } else {
                dismiss();
            }
        }
    }
}
