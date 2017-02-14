package com.letv.lepaysdk.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import com.letv.lepaysdk.utils.ResourceUtil;

public class ClearEditText extends EditText implements OnFocusChangeListener {
    private boolean hasFoucs;
    private String inputStr;
    private boolean isRun;
    private Drawable mClearDrawable;
    OnTextChangedListener onTextChangedListener;
    public boolean showExpType;
    public boolean showMobileType;
    public boolean showType;

    public interface OnTextChangedListener {
        void onFocusChange(boolean z);

        void onTextChanged(String str);
    }

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 16842862);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.isRun = false;
        this.inputStr = "";
        init();
    }

    private void init() {
        this.mClearDrawable = getCompoundDrawables()[2];
        if (this.mClearDrawable == null) {
            this.mClearDrawable = getResources().getDrawable(ResourceUtil.getDrawableResource(getContext(), "lepay_icon_delete"));
        }
        this.mClearDrawable.setBounds(0, 0, this.mClearDrawable.getIntrinsicWidth(), this.mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(new 1(this));
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean touchable = true;
        if (event.getAction() == 1 && getCompoundDrawables()[2] != null) {
            if (event.getX() <= ((float) (getWidth() - getTotalPaddingRight())) || event.getX() >= ((float) (getWidth() - getPaddingRight()))) {
                touchable = false;
            }
            if (touchable) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    public void onFocusChange(View v, boolean hasFocus) {
        boolean z = false;
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            if (getText().length() > 0) {
                z = true;
            }
            setClearIconVisible(z);
        } else {
            setClearIconVisible(false);
        }
        if (this.onTextChangedListener != null) {
            this.onTextChangedListener.onFocusChange(hasFocus);
        }
    }

    protected void setClearIconVisible(boolean visible) {
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], visible ? this.mClearDrawable : null, getCompoundDrawables()[3]);
    }

    private void show(CharSequence s) {
        if (!this.showType) {
            return;
        }
        if (this.isRun) {
            this.isRun = false;
            return;
        }
        this.isRun = true;
        this.inputStr = "";
        String newStr = s.toString().replace(" ", "");
        int index = 0;
        if (this.showMobileType && 3 < newStr.length()) {
            this.inputStr += newStr.substring(0, 3) + " ";
            index = 0 + 3;
        }
        if (this.showExpType) {
            newStr = newStr.replace("/", "");
            if (index + 2 < newStr.length()) {
                this.inputStr += newStr.substring(index, index + 2) + "/";
                index += 2;
            }
        }
        while (index + 4 < newStr.length()) {
            this.inputStr += newStr.substring(index, index + 4) + " ";
            index += 4;
        }
        this.inputStr += newStr.substring(index, newStr.length());
        setText(this.inputStr);
        setSelection(this.inputStr.length());
    }

    public OnTextChangedListener getOnTextChangedListener() {
        return this.onTextChangedListener;
    }

    public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener) {
        this.onTextChangedListener = onTextChangedListener;
    }
}
