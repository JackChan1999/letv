package com.letv.android.client.view;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View.OnTouchListener;
import android.widget.AutoCompleteTextView;
import com.letv.android.client.R;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class EmailAutoCompleteTextView extends AutoCompleteTextView {
    private String[] email_suffixs;
    private TextWatcher mTextWatcher;
    private OnTouchListener onTouchListener;

    public EmailAutoCompleteTextView(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.email_suffixs = null;
        this.mTextWatcher = new 1(this);
        this.onTouchListener = new 2(this);
        init();
    }

    public EmailAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.email_suffixs = null;
        this.mTextWatcher = new 1(this);
        this.onTouchListener = new 2(this);
        init();
    }

    public EmailAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.email_suffixs = null;
        this.mTextWatcher = new 1(this);
        this.onTouchListener = new 2(this);
        init();
    }

    private void init() {
        this.email_suffixs = getResources().getStringArray(R.array.email_suffix);
        addTextChangedListener(this.mTextWatcher);
        setOnTouchListener(this.onTouchListener);
        setDropDownWidth(UIsUtils.zoomWidth(270));
        setDropDownBackgroundResource(2130838926);
    }
}
