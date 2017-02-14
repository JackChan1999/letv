package com.letv.android.client.view;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class DeleteButtonEditText extends EditText {
    private TextWatcher mTextWatcher;
    private OnTouchListener onTouchListener;

    public DeleteButtonEditText(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.mTextWatcher = new 1(this);
        this.onTouchListener = new 2(this);
        init();
    }

    public DeleteButtonEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTextWatcher = new 1(this);
        this.onTouchListener = new 2(this);
        init();
    }

    public DeleteButtonEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mTextWatcher = new 1(this);
        this.onTouchListener = new 2(this);
        init();
    }

    private void init() {
        addTextChangedListener(this.mTextWatcher);
        setOnTouchListener(this.onTouchListener);
    }
}
