package com.letv.android.client.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.component.player.core.Configuration;
import com.letv.core.bean.MyFavoriteContentBean;
import com.letv.core.download.image.ImageDownloader;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.pp.utils.NetworkUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyFavoriteGridViewAdapter extends BaseAdapter implements OnItemClickListener {
    public static List<String> sSaveItemList = new ArrayList();
    private static List<Integer> sSelectedList = new ArrayList();
    private String LOG_TAG;
    private Button mButton;
    private Context mContext;
    private List<MyFavoriteContentBean> mFavoriteContentBeanList;
    private int mHeight;
    private ImageDownloader mImageDownloader;
    private ViewHolder mViewHolder;
    private int mWidth;

    public void setList(List<MyFavoriteContentBean> list) {
        this.mFavoriteContentBeanList = list;
        if (this.mFavoriteContentBeanList != null && this.mFavoriteContentBeanList.size() > 0) {
            for (int i = 0; i < this.mFavoriteContentBeanList.size(); i++) {
                sSaveItemList.add(String.valueOf(((MyFavoriteContentBean) this.mFavoriteContentBeanList.get(i)).pid) + NetworkUtils.DELIMITER_COLON + "0" + ";");
            }
        }
    }

    public MyFavoriteGridViewAdapter(Context context, int height, int width, Button button) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.LOG_TAG = "MyFavoriteGridViewAdapter";
        this.mContext = context;
        this.mImageDownloader = ImageDownloader.getInstance();
        this.mHeight = height;
        this.mWidth = width;
        this.mButton = button;
    }

    public int getCount() {
        return this.mFavoriteContentBeanList == null ? 0 : this.mFavoriteContentBeanList.size();
    }

    public Object getItem(int arg0) {
        return BaseTypeUtils.getElementFromList(this.mFavoriteContentBeanList, arg0) == null ? Integer.valueOf(0) : (Serializable) this.mFavoriteContentBeanList.get(arg0);
    }

    public long getItemId(int arg0) {
        return (long) arg0;
    }

    public View getView(int arg0, View view, ViewGroup arg2) {
        int lines;
        if (view == null || view.getTag() == null) {
            this.mViewHolder = new ViewHolder(this);
            view = LayoutInflater.from(this.mContext).inflate(R.layout.my_favorite_gridviewitem, null);
            this.mViewHolder.mFavoriteBlock = (ImageView) view.findViewById(R.id.favorite_block);
            this.mViewHolder.mSelectedStamp = (ImageView) view.findViewById(R.id.selected_stamp);
            this.mViewHolder.mFavoriteBlockName = (TextView) view.findViewById(R.id.block_content);
            this.mViewHolder.mBlackVeil = (ImageView) view.findViewById(R.id.black_veil);
            this.mViewHolder.mTextVeil = (ImageView) view.findViewById(R.id.text_veil);
            view.setTag(this.mViewHolder);
        } else {
            this.mViewHolder = (ViewHolder) view.getTag();
        }
        if (BaseTypeUtils.getElementFromList(this.mFavoriteContentBeanList, arg0) != null) {
            this.mImageDownloader.download(this.mViewHolder.mFavoriteBlock, ((MyFavoriteContentBean) this.mFavoriteContentBeanList.get(arg0)).mobilePic, 2130838801, true, true);
        }
        String title_content = null;
        if (!BaseTypeUtils.isListEmpty(this.mFavoriteContentBeanList) && this.mFavoriteContentBeanList.size() > arg0) {
            title_content = ((MyFavoriteContentBean) this.mFavoriteContentBeanList.get(arg0)).nameCn;
        }
        this.mViewHolder.mFavoriteBlockName.setText(title_content);
        LayoutParams params = (LayoutParams) this.mViewHolder.mFavoriteBlockName.getLayoutParams();
        params.leftMargin = 5;
        params.rightMargin = 5;
        this.mViewHolder.mFavoriteBlockName.setLayoutParams(params);
        LayoutParams params0 = (LayoutParams) this.mViewHolder.mTextVeil.getLayoutParams();
        LayoutParams params1 = (LayoutParams) this.mViewHolder.mFavoriteBlock.getLayoutParams();
        LayoutParams params2 = (LayoutParams) this.mViewHolder.mBlackVeil.getLayoutParams();
        params1.height = this.mHeight / 4;
        params2.height = this.mHeight / 4;
        int totle = getCount();
        if (totle % 3 != 0) {
            lines = (totle / 3) + 1;
        } else {
            lines = totle / 3;
        }
        int i = 0;
        while (i <= lines) {
            if (arg0 == 0 || arg0 == (i * 3) + 0) {
                params0.rightMargin = 2;
                params1.rightMargin = 2;
                params2.rightMargin = 2;
                params0.leftMargin = 0;
                params1.leftMargin = 0;
                params2.leftMargin = 0;
            } else if (arg0 == 2 || arg0 == (i * 3) + 2) {
                params0.leftMargin = 2;
                params1.leftMargin = 2;
                params2.leftMargin = 2;
                params0.rightMargin = 0;
                params1.rightMargin = 0;
                params2.rightMargin = 0;
            }
            i++;
        }
        this.mViewHolder.mFavoriteBlock.getLocationOnScreen(new int[2]);
        this.mViewHolder.mFavoriteBlock.setLayoutParams(params1);
        this.mViewHolder.mBlackVeil.setLayoutParams(params2);
        this.mViewHolder.mBlackVeil.setVisibility(8);
        if (sSelectedList.size() > 0) {
            for (i = 0; i < sSelectedList.size(); i++) {
                if (arg0 == ((Integer) sSelectedList.get(i)).intValue()) {
                    this.mViewHolder.mBlackVeil.setVisibility(0);
                    view.findViewById(R.id.selected_stamp_background).setVisibility(0);
                    view.findViewById(R.id.selected_stamp).setVisibility(0);
                    break;
                }
                view.findViewById(R.id.selected_stamp_background).setVisibility(8);
                view.findViewById(R.id.selected_stamp).setVisibility(8);
            }
        }
        return view;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        LogInfo.log(this.LOG_TAG, "onItemClick");
        ImageView mSelectedStampView = (ImageView) view.findViewById(R.id.selected_stamp);
        ImageView mBlueBackgroundView = (ImageView) view.findViewById(R.id.selected_stamp_background);
        ImageView mBlackVeil = (ImageView) view.findViewById(R.id.black_veil);
        AlphaAnimation alphaAnimation;
        Animation animationSet;
        int pid;
        if (mBlackVeil.getVisibility() == 0 && view != null) {
            alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(250);
            mBlackVeil.setAnimation(alphaAnimation);
            mBlackVeil.setVisibility(8);
            animationSet = new AnimationSet(true);
            ScaleAnimation mAnimation1 = new ScaleAnimation(1.0f, Configuration.SDK_VERSION_CODE, 1.0f, Configuration.SDK_VERSION_CODE, 1, 0.5f, 1, 0.5f);
            mAnimation1.setDuration(300);
            ScaleAnimation mAnimation2 = new ScaleAnimation(Configuration.SDK_VERSION_CODE, 0.9f, Configuration.SDK_VERSION_CODE, 0.9f, 1, 0.5f, 1, 0.5f);
            mAnimation2.setDuration(250);
            ScaleAnimation mAnimation3 = new ScaleAnimation(0.9f, 0.0f, 0.9f, 0.0f, 1, 0.5f, 1, 0.5f);
            mAnimation3.setDuration(125);
            animationSet.addAnimation(mAnimation1);
            animationSet.addAnimation(mAnimation2);
            animationSet.addAnimation(mAnimation3);
            mBlueBackgroundView.setAnimation(animationSet);
            mBlueBackgroundView.setVisibility(8);
            animationSet = new AlphaAnimation(1.0f, 0.0f);
            animationSet.setDuration(300);
            mSelectedStampView.setAnimation(animationSet);
            mSelectedStampView.setVisibility(8);
            if (sSelectedList.contains(Integer.valueOf(i))) {
                sSelectedList.remove(sSelectedList.indexOf(Integer.valueOf(i)));
            }
            pid = 0;
            if (BaseTypeUtils.getElementFromList(this.mFavoriteContentBeanList, i) != null) {
                pid = ((MyFavoriteContentBean) this.mFavoriteContentBeanList.get(i)).pid;
            }
            sSaveItemList.set(i, String.valueOf(pid) + NetworkUtils.DELIMITER_COLON + "0" + ";");
            if (sSelectedList.size() == 0) {
                this.mButton.setText(LetvUtils.getString(2131100859));
            }
        } else if (mBlackVeil.getVisibility() == 8 && view != null) {
            mBlackVeil.setVisibility(0);
            alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(300);
            mBlackVeil.setAnimation(alphaAnimation);
            sSelectedList.add(Integer.valueOf(i));
            pid = 0;
            if (BaseTypeUtils.getElementFromList(this.mFavoriteContentBeanList, i) != null) {
                pid = ((MyFavoriteContentBean) this.mFavoriteContentBeanList.get(i)).pid;
            }
            sSaveItemList.set(i, String.valueOf(pid) + NetworkUtils.DELIMITER_COLON + "1" + ";");
            mBlueBackgroundView.setVisibility(0);
            AnimatorSet mset = new AnimatorSet();
            ObjectAnimator an1 = ObjectAnimator.ofFloat(mBlueBackgroundView, "scaleX", new float[]{1.0f, 1.4f}).setDuration(100);
            ObjectAnimator an2 = ObjectAnimator.ofFloat(mBlueBackgroundView, "scaleY", new float[]{1.0f, 1.4f}).setDuration(100);
            ObjectAnimator an3 = ObjectAnimator.ofFloat(mBlueBackgroundView, "scaleX", new float[]{1.4f, 1.0f}).setDuration(100);
            Animator an4 = ObjectAnimator.ofFloat(mBlueBackgroundView, "scaleY", new float[]{1.4f, 1.0f}).setDuration(100);
            mset.play(an1).with(an2);
            mset.play(an3).with(an4).after(an1);
            mset.start();
            mSelectedStampView.setVisibility(0);
            animationSet = new AlphaAnimation(0.0f, 1.0f);
            animationSet.setDuration(300);
            mSelectedStampView.setAnimation(animationSet);
            this.mButton.setText(LetvUtils.getString(2131100885));
        }
    }
}
