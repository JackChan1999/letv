package com.letv.android.client.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.List;

public class FootSearchView extends RelativeLayout {
    private TextView footSearchAutoCompleteTextView;
    private RelativeLayout footSearchBoxLayout;
    private FootSearchHotWordAdapter footSearchHotWordAdapter;
    private int footSearchHotWordSpace;
    private LinearLayout footSearchHotWordsLayout;
    private LinearLayout footSearchHotWordsRootView;
    private HorizontalScrollView footSearchScrollView;
    private View footSearchView;
    private Context mContext;
    private int mid;
    private OnClickListener searchBoxClickListener;

    public FootSearchView(Context context, AttributeSet attrs) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context, attrs);
        this.footSearchHotWordSpace = 0;
        this.searchBoxClickListener = new 1(this);
        init(context);
    }

    public FootSearchView(Context context) {
        super(context);
        this.footSearchHotWordSpace = 0;
        this.searchBoxClickListener = new 1(this);
        init(context);
    }

    public FootSearchView(Context context, int id) {
        super(context);
        this.footSearchHotWordSpace = 0;
        this.searchBoxClickListener = new 1(this);
        init(context);
        this.mid = id;
    }

    protected void init(Context context) {
        this.mContext = context;
        this.footSearchView = UIsUtils.inflate(this.mContext, R.layout.home_bottom_search_words, null);
        this.footSearchAutoCompleteTextView = (TextView) this.footSearchView.findViewById(R.id.search_textview);
        this.footSearchBoxLayout = (RelativeLayout) this.footSearchView.findViewById(R.id.search_box_layout);
        this.footSearchHotWordsLayout = (LinearLayout) this.footSearchView.findViewById(R.id.linearlayout_search_words);
        this.footSearchHotWordsRootView = (LinearLayout) this.footSearchView.findViewById(R.id.search_words_root);
        this.footSearchScrollView = (HorizontalScrollView) this.footSearchView.findViewById(R.id.horizontal_scroll_view);
        this.footSearchHotWordSpace = getResources().getDimensionPixelSize(2131165547);
        this.footSearchBoxLayout.setOnClickListener(this.searchBoxClickListener);
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.topMargin = UIsUtils.zoomWidth(12);
        this.footSearchView.setLayoutParams(layoutParams);
        addView(this.footSearchView);
    }

    public void setList(List<?> dataList) {
        if (this.footSearchHotWordAdapter == null) {
            this.footSearchHotWordAdapter = new FootSearchHotWordAdapter(this, this.mContext);
        }
        if (dataList == null || dataList.size() == 0) {
            this.footSearchHotWordsRootView.setVisibility(8);
            return;
        }
        this.footSearchHotWordAdapter.setList(dataList);
        this.footSearchHotWordsRootView.setVisibility(0);
        addSearchHotTextView();
    }

    private void addSearchHotTextView() {
        if (this.footSearchHotWordsLayout.getChildCount() > 0) {
            this.footSearchHotWordsLayout.removeAllViewsInLayout();
        }
        int totalWidth = 0;
        for (int i = 0; i < this.footSearchHotWordAdapter.getCount(); i++) {
            View item = this.footSearchHotWordAdapter.getView(i, null, this.footSearchHotWordsLayout);
            item.setOnClickListener(new 2(this, (String) this.footSearchHotWordAdapter.getItem(i), i));
            item.measure(0, 0);
            totalWidth += item.getMeasuredWidth();
            this.footSearchHotWordsLayout.addView(item, i, new LinearLayout.LayoutParams(item.getMeasuredWidth() + this.footSearchHotWordSpace, -2));
        }
        this.footSearchHotWordsLayout.getLayoutParams().width = (this.footSearchHotWordSpace * this.footSearchHotWordAdapter.getCount()) + totalWidth;
    }
}
