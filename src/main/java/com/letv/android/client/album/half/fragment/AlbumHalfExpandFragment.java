package com.letv.android.client.album.half.fragment;

import android.animation.ObjectAnimator;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.android.client.album.half.widget.OnSlidingListener;
import com.letv.android.client.album.half.widget.SlidingLayout;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.core.BaseApplication;
import com.letv.core.bean.AlbumPageCard;
import com.letv.core.pagecard.LayoutParser;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.UIsUtils;

public class AlbumHalfExpandFragment extends Fragment {
    private View backgroudView;
    private View headView;
    private boolean mIsFull;
    private OnSlidingListener onSlidingForBackListener = new 1(this);
    private SlidingLayout slidingLayout;
    private TextView subTitleView;
    private TextView titleView;
    private RelativeLayout toolBarLayout;

    public void setIsFull(boolean isFull) {
        if (this.mIsFull != isFull) {
            close(false);
        }
        this.mIsFull = isFull;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.half_play_card_fragment_layout, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.backgroudView = view.findViewById(R.id.sliding_backgroud);
        this.slidingLayout = (SlidingLayout) view.findViewById(R.id.slidingview);
        this.toolBarLayout = (RelativeLayout) view.findViewById(R.id.handler);
        this.headView = view.findViewById(R.id.head_view);
    }

    public void onDetach() {
        super.onDetach();
    }

    public boolean isOpened() {
        return this.slidingLayout != null && this.slidingLayout.isOpened();
    }

    private void initToolbar(AlbumPageCard albumPageCard) {
        if (albumPageCard != null) {
            LayoutParser parser = LayoutParser.from(BaseApplication.getInstance());
            if (this.titleView == null && albumPageCard.generalCard != null && BaseTypeUtils.isMapContainsKey(albumPageCard.generalCard.itemMap, "general_header")) {
                View headBar = parser.inflate((String) albumPageCard.generalCard.itemMap.get("general_header"), null);
                this.titleView = (TextView) parser.getViewByName("title", new TextView(BaseApplication.getInstance()));
                this.subTitleView = (TextView) parser.getViewByName("subtitle", new TextView(BaseApplication.getInstance()));
                parser.getViewByName("top_line", new View(BaseApplication.getInstance())).setVisibility(4);
                View topView = parser.getViewByName("top_divider", null);
                if (topView != null) {
                    topView.setVisibility(8);
                }
                this.toolBarLayout.addView(headBar);
            }
        }
    }

    private void showBackView(boolean anim) {
        this.backgroudView.setVisibility(0);
        if (anim) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this.backgroudView, "alpha", new float[]{0.0f, 1.0f});
            objectAnimator.setDuration(300);
            if (VERSION.SDK_INT >= 18) {
                objectAnimator.setAutoCancel(true);
            }
            objectAnimator.addListener(new 2(this));
            objectAnimator.start();
            return;
        }
        this.backgroudView.setAlpha(1.0f);
    }

    private void hideBackView(boolean anim) {
        if (anim) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this.backgroudView, "alpha", new float[]{1.0f, 0.0f});
            objectAnimator.setDuration(300);
            if (VERSION.SDK_INT >= 18) {
                objectAnimator.setAutoCancel(true);
            }
            objectAnimator.addListener(new 3(this));
            objectAnimator.start();
            return;
        }
        this.backgroudView.setVisibility(8);
    }

    public void setTitle(String string, String subString) {
        if (this.titleView != null && this.subTitleView != null) {
            this.titleView.setText(string);
            if (subString != null) {
                this.subTitleView.setText(subString);
                this.subTitleView.setVisibility(0);
            }
        }
    }

    public void open(View contentView, AlbumPageCard albumPageCard, String title, String subTitle) {
        if (contentView != null && this.slidingLayout != null) {
            initToolbar(albumPageCard);
            setTitle(title, subTitle);
            boolean isLandscape = UIsUtils.isLandscape(BaseApplication.getInstance());
            this.slidingLayout.setScreenOrientation(isLandscape);
            this.slidingLayout.setContentView(contentView);
            this.slidingLayout.setOnSlidingListener(!isLandscape ? this.onSlidingForBackListener : null);
            if (UIsUtils.isLandscape(getActivity())) {
                if ((getActivity() instanceof AlbumPlayActivity) && ((AlbumPlayActivity) getActivity()).mIsPlayingNonCopyright) {
                    this.slidingLayout.setBackgroundColor(-671088640);
                } else {
                    this.slidingLayout.setBackgroundColor(-872415232);
                }
                this.headView.setVisibility(8);
                this.backgroudView.setVisibility(8);
            } else {
                this.slidingLayout.setBackgroundColor(-1);
                this.headView.setVisibility(0);
            }
            this.slidingLayout.openFormOrigin();
        }
    }

    public void openErrorView(String errorStr) {
        if (this.slidingLayout != null && getActivity() != null) {
            boolean isLandscape = UIsUtils.isLandscape(BaseApplication.getInstance());
            this.slidingLayout.setScreenOrientation(isLandscape);
            PublicLoadLayout publicLoadLayout = PublicLoadLayout.createPage(getActivity(), new View(getActivity()));
            publicLoadLayout.setRefreshData(new 4(this));
            int titleColor = getResources().getColor(R.color.letv_color_ffffff);
            int subTitleColor = getResources().getColor(R.color.letv_color_ff5895ed);
            if ((getActivity() instanceof AlbumPlayActivity) && ((AlbumPlayActivity) getActivity()).mIsPlayingNonCopyright) {
                subTitleColor = getResources().getColor(R.color.letv_color_noncopyright);
            }
            if (NetworkUtils.isNetworkAvailable()) {
                publicLoadLayout.dataError(true, true, titleColor, subTitleColor);
            } else {
                publicLoadLayout.netError(true, false, titleColor, subTitleColor);
            }
            if (!TextUtils.isEmpty(errorStr)) {
                publicLoadLayout.error(errorStr);
            }
            this.slidingLayout.setContentView(publicLoadLayout);
            this.slidingLayout.setOnSlidingListener(!isLandscape ? this.onSlidingForBackListener : null);
            this.slidingLayout.openFormOrigin();
            if (UIsUtils.isLandscape(getActivity())) {
                this.slidingLayout.setBackgroundColor(-872415232);
                this.headView.setVisibility(8);
                this.backgroudView.setVisibility(8);
                return;
            }
            this.slidingLayout.setBackgroundColor(-1);
            this.headView.setVisibility(0);
        }
    }

    public void close() {
        close(true);
    }

    private void close(boolean enAbleAnim) {
        if (this.slidingLayout != null) {
            this.slidingLayout.closeFromOrigin(enAbleAnim);
        }
    }

    public SlidingLayout getSlidingLayout() {
        return this.slidingLayout;
    }
}
