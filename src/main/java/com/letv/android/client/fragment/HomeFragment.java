package com.letv.android.client.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.activity.ChannelWallActivity;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.adapter.channel.ChannelDetailPagerAdapter;
import com.letv.android.client.adapter.channel.ChannelFragmentAdapter;
import com.letv.android.client.commonlib.fragement.LetvBaseFragment;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.task.ChannelWallFetcherTask;
import com.letv.android.client.task.ChannelWallFetcherTask.ChannelListCallback;
import com.letv.android.client.utils.CardSortManager;
import com.letv.android.client.utils.ThemeDataManager;
import com.letv.android.client.view.channel.ChannelTabPageIndicator;
import com.letv.core.BaseApplication;
import com.letv.core.bean.ChannelListBean;
import com.letv.core.bean.ChannelListBean.Channel;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.RxBus;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class HomeFragment extends LetvBaseFragment implements OnPageChangeListener {
    private static final int REQUEST_CODE = 1001;
    private int downX;
    private ChannelListBean mChannelListBean;
    private RelativeLayout mChannelWallIcon;
    private int mCurrentIndex;
    private Channel mLastChannel;
    private ChannelDetailPagerAdapter mPageAdapter;
    private int mRequestTag;
    private PublicLoadLayout mRootView;
    private ScrollStateChange mScrollStateChange;
    private ChannelTabPageIndicator mTabPageIndicator;
    private View mViewshadow;
    private ViewPager myViewPager;
    private OnTouchListener onTouchListener;

    public static class ScrollStateChange {
        public boolean idle;

        public ScrollStateChange(boolean idle) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.idle = idle;
        }
    }

    public HomeFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mChannelListBean = new ChannelListBean();
        this.onTouchListener = new OnTouchListener(this) {
            final /* synthetic */ HomeFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public boolean onTouch(View v, MotionEvent event) {
                if (this.this$0.mChannelListBean != null && BaseTypeUtils.getElementFromList(this.this$0.mChannelListBean.listChannel, this.this$0.mCurrentIndex + 1) == null) {
                    switch (event.getAction()) {
                        case 0:
                            this.this$0.downX = (int) event.getX();
                            break;
                        case 1:
                            if (this.this$0.downX - ((int) event.getX()) > UIsUtils.getDisplayWidth() / 8) {
                                this.this$0.startChannelWallActivity();
                            }
                            this.this$0.downX = 0;
                            break;
                        case 2:
                            if (this.this$0.downX == 0) {
                                this.this$0.downX = (int) event.getX();
                                break;
                            }
                            break;
                        default:
                            break;
                    }
                }
                return false;
            }
        };
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogInfo.log("zhaoxiang", "onCreateView");
        this.mRootView = PublicLoadLayout.createPage(this.mContext, (int) R.layout.fragment_home_page_layout, true);
        return this.mRootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogInfo.log("zhaoxiang", "onViewCreated");
        findView();
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (this.mPageAdapter != null) {
            Fragment fragment = this.mPageAdapter.getItem(this.mCurrentIndex);
            if (fragment != null) {
                fragment.onHiddenChanged(hidden);
            }
        }
        if ((this.mContext instanceof MainActivity) && this.mChannelListBean != null) {
            ((MainActivity) this.mContext).initUserGuide(!hidden);
        }
        if (!hidden && PreferencesManager.getInstance().getChannelNavigationChange()) {
            updateNavigation("");
        }
    }

    private void findView() {
        this.myViewPager = (ViewPager) this.mRootView.findViewById(R.id.channel_detail_viewpager);
        this.mTabPageIndicator = (ChannelTabPageIndicator) this.mRootView.findViewById(R.id.channel_detail_indicator);
        this.mChannelWallIcon = (RelativeLayout) this.mRootView.findViewById(R.id.channel_wall_icon);
        this.mViewshadow = this.mRootView.findViewById(R.id.channel_wall_shadow);
        this.mChannelWallIcon.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ HomeFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                this.this$0.startChannelWallActivity();
            }
        });
        this.myViewPager.setOnTouchListener(this.onTouchListener);
        this.mRootView.setOnTouchListener(this.onTouchListener);
        this.mRootView.loading(false);
        this.mViewshadow.bringToFront();
    }

    public void updateTheme() {
        try {
            ThemeDataManager.getInstance(this.mContext).setContentTheme((ImageView) this.mRootView.findViewById(R.id.channel_wall_icon_image), ThemeDataManager.NAME_TOP_CHANNEL_PIC);
            ThemeDataManager.getInstance(this.mContext).setContentTheme(this.mChannelWallIcon, ThemeDataManager.NAME_TOP_NAVIGATION_BG_COLOR);
            ThemeDataManager.getInstance(this.mContext).setContentTheme(this.mTabPageIndicator, ThemeDataManager.NAME_TOP_NAVIGATION_BG_COLOR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initNavigation(int requestTag) {
        if (getActivity() != null) {
            if ((requestTag == 2 && this.mRequestTag == 1) || requestTag == 1) {
                ChannelWallFetcherTask.getInstance().cancleRequest();
                ChannelWallFetcherTask.getInstance().destroy();
                this.mRequestTag = requestTag;
                getNavigation();
            }
        }
    }

    private void getNavigation() {
        ChannelWallFetcherTask.getInstance().fetchChannelWall(BaseApplication.getInstance(), new ChannelListCallback(this) {
            final /* synthetic */ HomeFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onFetch(ChannelListBean channelList) {
                if (channelList == null || BaseTypeUtils.isListEmpty(channelList.listChannel) || this.this$0.myViewPager == null) {
                    this.this$0.mRootView.netError(false);
                    return;
                }
                int i = 0;
                while (i < channelList.listChannel.size()) {
                    if (channelList.listChannel.get(i) != null && ((Channel) channelList.listChannel.get(i)).top == 0) {
                        this.this$0.mChannelListBean.listChannel.add(channelList.listChannel.get(i));
                        this.this$0.mChannelListBean.getChannelMap().put(String.valueOf(((Channel) channelList.listChannel.get(i)).id), channelList.listChannel.get(i));
                    }
                    i++;
                }
                this.this$0.initViewPage();
                LetvApplication.getInstance().setChannelList(channelList);
            }
        });
    }

    private void initViewPage() {
        this.mPageAdapter = new ChannelDetailPagerAdapter(getChildFragmentManager(), this.mContext);
        this.mPageAdapter.setData(this.mChannelListBean.listChannel);
        this.myViewPager.setAdapter(this.mPageAdapter);
        this.mTabPageIndicator.setViewPager(this.myViewPager);
        this.mTabPageIndicator.setOnPageChangeListener(this);
        this.mTabPageIndicator.notifyDataSetChanged();
        this.mRootView.finish();
        if ((this.mContext instanceof MainActivity) && !this.mIsHidden) {
            ((MainActivity) this.mContext).initUserGuide(true);
        }
    }

    private void startChannelWallActivity() {
        startActivityForResult(new Intent(this.mContext, ChannelWallActivity.class), 1001);
        ((MainActivity) this.mContext).overridePendingTransition(R.anim.in_from_right_short, R.anim.out_to_left);
    }

    private void updataIndicator(Channel channel) {
        if (channel != null && this.mChannelListBean != null && !BaseTypeUtils.isListEmpty(this.mChannelListBean.listChannel)) {
            int index = this.mChannelListBean.listChannel.indexOf(channel);
            if (index != -1) {
                this.mTabPageIndicator.setCurrentItem(index);
                this.myViewPager.setCurrentItem(index, false);
            } else if (BaseTypeUtils.getElementFromList(this.mChannelListBean.listChannel, this.mCurrentIndex) != null) {
                this.mTabPageIndicator.setCurrentItem(this.mCurrentIndex);
                this.myViewPager.setCurrentItem(this.mCurrentIndex, false);
                LogInfo.log("zhaoxiang", "position" + this.mCurrentIndex);
            } else {
                this.mTabPageIndicator.setCurrentItem(0);
                this.myViewPager.setCurrentItem(0);
            }
        }
    }

    public void updataIndicator(String cid) {
        if (!TextUtils.isEmpty(cid) && this.mChannelListBean != null) {
            updataIndicator((Channel) this.mChannelListBean.getChannelMap().get(cid));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == 1002 && data != null) {
            updateNavigation(data.getStringExtra(ChannelFragmentAdapter.CHANNEL_ID));
        }
    }

    private void updateNavigation(String cid) {
        ChannelListBean channelList = BaseApplication.getInstance().getChannelList();
        if (channelList == null || this.mPageAdapter == null) {
            this.mRootView.dataError(false);
            return;
        }
        if (PreferencesManager.getInstance().getChannelNavigationChange()) {
            PreferencesManager.getInstance().setChannelNavigationChange(false);
            this.mChannelListBean = null;
            this.mChannelListBean = new ChannelListBean();
            int i = 0;
            while (i < channelList.listChannel.size()) {
                if (channelList.listChannel.get(i) != null && ((Channel) channelList.listChannel.get(i)).top == 0) {
                    this.mChannelListBean.listChannel.add(channelList.listChannel.get(i));
                    this.mChannelListBean.getChannelMap().put(String.valueOf(((Channel) channelList.listChannel.get(i)).id), channelList.listChannel.get(i));
                }
                i++;
            }
            this.mPageAdapter.setData(this.mChannelListBean.listChannel);
            this.mTabPageIndicator.notifyDataSetChanged();
        }
        if (TextUtils.isEmpty(cid) && this.mLastChannel != null) {
            cid = String.valueOf(this.mLastChannel.id);
        }
        if (!TextUtils.isEmpty(cid)) {
            updataIndicator(cid);
        }
    }

    public void onPageSelected(int i) {
        this.mCurrentIndex = i;
        if (this.mChannelListBean != null && BaseTypeUtils.getElementFromList(this.mChannelListBean.listChannel, i) != null) {
            this.mLastChannel = (Channel) this.mChannelListBean.listChannel.get(i);
            String mPageId = PageIdConstant.getPageIdByChannelId(this.mLastChannel.id);
            LogInfo.log("zhangying", " channel name = " + this.mLastChannel.name + " channelId = " + this.mLastChannel.id + " channel pageId = " + mPageId);
            ((MainActivity) this.mContext).setCurrentPageId(mPageId);
        }
    }

    public void onPageScrollStateChanged(int i) {
        LogInfo.log("zhaoxiang", "-------1");
        setRxBusMessage(i == 0);
    }

    private void setRxBusMessage(boolean isScrolling) {
        if (this.mScrollStateChange == null) {
            this.mScrollStateChange = new ScrollStateChange(isScrolling);
        }
        this.mScrollStateChange.idle = isScrolling;
        RxBus.getInstance().send(this.mScrollStateChange);
    }

    public void onPageScrolled(int i, float v, int i1) {
    }

    public void onDestroy() {
        LogInfo.log("zhaoxiang", "11onDestroy");
        if (this.myViewPager != null) {
            this.myViewPager.removeAllViews();
            this.myViewPager = null;
        }
        if (this.mTabPageIndicator != null) {
            this.mTabPageIndicator.removeAllViews();
            this.mTabPageIndicator = null;
        }
        if (this.mPageAdapter != null) {
            this.mPageAdapter.destroy();
        }
        this.mChannelListBean = null;
        CardSortManager.reset();
        FirstPageFragment.sHasShowPlayRecord = false;
        ChannelWallFetcherTask.getInstance().cancleRequest();
        ChannelWallFetcherTask.getInstance().destroy();
        super.onDestroy();
    }

    public String getTagName() {
        return FragmentConstant.TAG_FRAGMENT_HOME;
    }

    public int getContainerId() {
        return R.id.main_content;
    }

    public void setHomeRecordVisible(boolean show) {
        if (this.mPageAdapter != null) {
            Fragment fragment = this.mPageAdapter.getItem(this.mCurrentIndex);
            if (fragment instanceof FirstPageFragment) {
                ((FirstPageFragment) fragment).setHomeRecord(show);
            }
        }
    }

    public void setHomeRecommendVisible(boolean show) {
        if (!show && this.mPageAdapter != null) {
            Fragment fragment = this.mPageAdapter.getItem(this.mCurrentIndex);
            if (fragment instanceof FirstPageFragment) {
                ((FirstPageFragment) fragment).removeRecommendView();
            }
        }
    }

    public ViewPager getViewPager() {
        return this.myViewPager;
    }

    public boolean scrollToTop() {
        if (this.mPageAdapter == null) {
            return false;
        }
        Fragment fragment = this.mPageAdapter.getItem(this.mCurrentIndex);
        if (fragment instanceof HomeBaseFragment) {
            return ((HomeBaseFragment) fragment).scrollToTop();
        }
        return false;
    }

    public void updataIndicatorToFirst() {
        if (this.mPageAdapter != null && this.myViewPager != null && this.mCurrentIndex != 0) {
            this.myViewPager.setCurrentItem(0, false);
        }
    }
}
