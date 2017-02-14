package com.letv.mobile.lebox.sweep;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.common.IFunction;
import java.util.ArrayList;

public class SweepHelpView implements OnClickListener {
    private Button mBackToSweep;
    IFunction<Void> mCallback;
    private final View mDialogView;
    private ArrayList<View> pageViews;
    private ViewPager viewPager;

    public SweepHelpView(View mDialogView) {
        this.mDialogView = mDialogView;
    }

    public void initView() {
        LayoutInflater inflater = LayoutInflater.from(this.mDialogView.getContext());
        this.pageViews = new ArrayList();
        this.pageViews.add(inflater.inflate(R.layout.layout_sweep_help_page1, null));
        this.viewPager = (ViewPager) this.mDialogView.findViewById(R.id.guidePages);
        this.viewPager.setAdapter(new GuidePageAdapter(this));
    }

    public void onClick(View arg0) {
        if (this.mCallback != null) {
            this.mCallback.get();
        }
    }

    public void setOnCloseListener(IFunction<Void> callback) {
        this.mCallback = callback;
    }
}
