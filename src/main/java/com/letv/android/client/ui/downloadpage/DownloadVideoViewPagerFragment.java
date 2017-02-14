package com.letv.android.client.ui.downloadpage;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.letv.android.client.commonlib.messagemodel.AlbumHalfConfig.ExpendViewpagerLayout;
import com.letv.android.client.commonlib.view.TabPageIndicator;
import com.letv.android.client.utils.CursorLoader;
import com.letv.android.client.utils.UIs;
import com.letv.core.BaseApplication;
import com.letv.core.bean.DownloadPageConfig;
import com.letv.core.bean.VideoListBean;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.download.manager.DownloadManager;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.pp.utils.NetworkUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;

public class DownloadVideoViewPagerFragment extends Fragment implements IDownloadVideoFragment, LoaderCallbacks<Cursor> {
    private static final int LOADER_ALBUM_ID = 1;
    private static final String TAG = "DownloadPage";
    private ArrayList<BaseDownloadPageFragment> mArrayGridFragment;
    private Context mContext;
    private int mCurrSelectPeriods;
    private DownloadPageConfig mDownloadPageConfig;
    private ViewPager mDownloadPageViewPager;
    private DownloadVideoPagerAdapter mDownloadVideoPagerAdapter;
    private IDownloadPage mIDownloadPage;
    private TabPageIndicator mTabPageIndicator;

    public DownloadVideoViewPagerFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mContext = BaseApplication.getInstance();
        this.mArrayGridFragment = new ArrayList();
        this.mCurrSelectPeriods = 0;
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == 1) {
            return new CursorLoader(this.mContext, DownloadManager.DOWNLOAD_ALBUM_URI, null, null, null, null);
        }
        return null;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        notifyAdapter();
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    private void initCommonTabData() {
        int position = 0;
        while (position < this.mDownloadPageConfig.pageNum) {
            BaseDownloadPageFragment downloadPageFragment = null;
            if (this.mDownloadPageConfig.mCurrentStyple == 1) {
                downloadPageFragment = new DownloadVideoGridFragment();
            } else if (this.mDownloadPageConfig.mCurrentStyple == 2) {
                downloadPageFragment = new DownloadVideoListFragment();
            }
            if (downloadPageFragment != null) {
                downloadPageFragment.setDownloadPage(this.mIDownloadPage);
                int star = (position * 50) + 1;
                int end = (position + 1) * 50;
                if (end > this.mDownloadPageConfig.total) {
                    end = this.mDownloadPageConfig.total;
                }
                downloadPageFragment.title = star + NetworkUtils.DELIMITER_LINE + end;
                downloadPageFragment.currentPage = String.valueOf(position + 1);
                this.mArrayGridFragment.add(downloadPageFragment);
                position++;
            } else {
                return;
            }
        }
    }

    private void initPeriodsTabData() {
        boolean isCurrenOpenPeriods = false;
        this.mCurrSelectPeriods = 0;
        ArrayList<Entry<String, VideoListBean>> sortArrayList = new ArrayList(this.mIDownloadPage.getVideoMap().entrySet());
        Collections.sort(sortArrayList, new 1(this));
        for (int i = 0; i < sortArrayList.size(); i++) {
            String key = (String) ((Entry) sortArrayList.get(i)).getKey();
            DownloadVideoListFragment downloadPageFragment = new DownloadVideoListFragment();
            downloadPageFragment.title = key;
            downloadPageFragment.currentPage = key;
            if (((ArrayList) this.mIDownloadPage.getVideoMap().get(key)) != null) {
                isCurrenOpenPeriods = true;
            } else if (!isCurrenOpenPeriods) {
                this.mCurrSelectPeriods++;
            }
            this.mArrayGridFragment.add(downloadPageFragment);
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mArrayGridFragment.clear();
        this.mIDownloadPage = (IDownloadPage) activity;
        this.mDownloadPageConfig = ((IDownloadPage) activity).getDownloadPageConfig();
        getActivity().getSupportLoaderManager().initLoader(1, null, this);
        if (this.mDownloadPageConfig.mCurrentStyple != 3) {
            initCommonTabData();
        } else {
            initPeriodsTabData();
        }
    }

    private void showTabPageIndicator() {
        if (this.mDownloadPageViewPager != null) {
            LayoutParams layoutParams = this.mDownloadPageViewPager.getLayoutParams();
            this.mTabPageIndicator.setVisibility(0);
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) layoutParams).setMargins(0, UIs.dipToPx(38.0f), 0, 0);
            }
        }
    }

    private void hideTabPageIndicator() {
        if (this.mDownloadPageViewPager != null) {
            LayoutParams layoutParams = this.mDownloadPageViewPager.getLayoutParams();
            this.mTabPageIndicator.setVisibility(8);
            if (layoutParams instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) layoutParams).setMargins(0, 0, 0, 0);
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(getActivity(), new LeMessage(LeMessageIds.MSG_ALBUM_HALF_FETCH_EXPEND_VIEWPAGER_LAYOUT));
        if (!LeResponseMessage.checkResponseMessageValidity(response, ExpendViewpagerLayout.class)) {
            return new View(this.mContext);
        }
        ExpendViewpagerLayout layout = (ExpendViewpagerLayout) response.getData();
        View view = layout.view;
        view.setBackgroundColor(this.mContext.getResources().getColor(2131493376));
        this.mDownloadPageViewPager = layout.viewPager;
        this.mDownloadVideoPagerAdapter = new DownloadVideoPagerAdapter(getChildFragmentManager(), this.mArrayGridFragment);
        this.mDownloadPageViewPager.setAdapter(this.mDownloadVideoPagerAdapter);
        this.mTabPageIndicator = layout.indicator;
        this.mTabPageIndicator.setViewPager(this.mDownloadPageViewPager);
        if (this.mDownloadPageConfig.mCurrentStyple == 3) {
            if (this.mIDownloadPage.getVideoMap().size() > 1) {
                showTabPageIndicator();
                if (this.mDownloadVideoPagerAdapter.getCount() <= this.mCurrSelectPeriods) {
                    return view;
                }
                this.mTabPageIndicator.setCurrentItem(this.mCurrSelectPeriods);
                return view;
            }
            hideTabPageIndicator();
            return view;
        } else if (this.mDownloadPageConfig.pageNum > 1) {
            showTabPageIndicator();
            this.mTabPageIndicator.setCurrentItem(this.mIDownloadPage.getCurPage() - 1);
            return view;
        } else {
            hideTabPageIndicator();
            return view;
        }
    }

    public void notifyAdapter() {
        if (this.mDownloadVideoPagerAdapter != null && this.mDownloadPageViewPager != null) {
            Fragment fragment = this.mDownloadVideoPagerAdapter.getItem(this.mDownloadPageViewPager.getCurrentItem());
            if (fragment != null && (fragment instanceof DownloadVideoGridFragment)) {
                ((DownloadVideoGridFragment) fragment).onUpdateAdapter();
            }
            if (fragment != null && (fragment instanceof DownloadVideoListFragment)) {
                ((DownloadVideoListFragment) fragment).onUpdateAdapter();
            }
        }
    }
}
