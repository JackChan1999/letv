package com.letv.android.client.share;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.utils.UIs;
import com.letv.android.client.view.CustomLoadingDialog;
import com.letv.core.bean.ShareAlbumBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.pp.utils.NetworkUtils;
import com.letv.share.sina.ex.BSsoHandler;

public class HotSquareShareDialog extends DialogFragment implements OnClickListener, OnDismissListener {
    public static onFragmentResult onFragmentResult;
    private ShareAlbumBean album;
    String cid;
    CustomLoadingDialog mDialog;
    private LetvApplication mLetvApplication;
    private BSsoHandler mSsoHandler;
    private View mTransparentView;
    public OnShareDialogDismissListener onShareDialogDismissListener;
    String pid;
    private int resH;
    private View root;
    private int singleLayerH;
    String vid;

    public interface OnShareDialogDismissListener {
        void shareDismiss(boolean z);
    }

    public interface onFragmentResult {
        void onFragmentResult_back(int i, int i2, Intent intent);
    }

    public HotSquareShareDialog() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.album = LetvShareControl.mShareAlbum;
        this.mTransparentView = null;
        this.vid = NetworkUtils.DELIMITER_LINE;
        this.pid = NetworkUtils.DELIMITER_LINE;
        this.cid = NetworkUtils.DELIMITER_LINE;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setCancelable(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.root = UIs.inflate(getActivity(), 2130903140, null);
        if (this.mLetvApplication == null) {
            this.mLetvApplication = (LetvApplication) getActivity().getApplicationContext();
        }
        this.mDialog = new CustomLoadingDialog(getActivity(), this.root, 2131230820);
        this.mDialog.setCanceledOnTouchOutside(true);
        this.mDialog.setWindowParams(0, 0, 80);
        this.mDialog.setOnDismissListener(this);
        initView();
        return this.mDialog;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        LogInfo.log("ljnalex", "hotsqureshare ----onActivityCreated----");
        super.onActivityCreated(savedInstanceState);
    }

    public void initView() {
        HorizontalScrollView horizonView = (HorizontalScrollView) this.root.findViewById(2131362427);
        TextView tv_share = (TextView) this.root.findViewById(2131362332);
        this.mTransparentView = this.root.findViewById(2131362426);
        this.mTransparentView.setOnClickListener(this);
        ImageView sina_icon = (ImageView) this.root.findViewById(2131362433);
        ImageView qzone_icon = (ImageView) this.root.findViewById(2131362435);
        ImageView weixin_icon = (ImageView) this.root.findViewById(2131362431);
        ImageView qq_icon = (ImageView) this.root.findViewById(2131362437);
        ImageView wx_timeline_icon = (ImageView) this.root.findViewById(2131362429);
        ImageView tencent_icon = (ImageView) this.root.findViewById(2131362439);
        ((HorizontalScrollView) this.root.findViewById(2131362442)).setVisibility(8);
        this.root.findViewById(2131362443).setVisibility(8);
        TextView cancelBtn = (TextView) this.root.findViewById(2131362444);
        ImageView refreshBtn = (ImageView) this.root.findViewById(2131362032);
        scalview(sina_icon);
        scalview(qzone_icon);
        scalview(weixin_icon);
        scalview(qq_icon);
        scalview(wx_timeline_icon);
        scalview(tencent_icon);
        scalview(refreshBtn);
        onFragmentResult = new 1(this);
        sina_icon.setOnClickListener(this);
        qzone_icon.setOnClickListener(this);
        weixin_icon.setOnClickListener(this);
        qq_icon.setOnClickListener(this);
        wx_timeline_icon.setOnClickListener(this);
        tencent_icon.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        refreshBtn.setOnClickListener(this);
    }

    public void scalview(View view) {
        LayoutParams params = view.getLayoutParams();
        params.width = UIs.dipToPx(60.0f);
        params.height = UIs.dipToPx(80.0f);
        view.setLayoutParams(params);
    }

    public void onResume() {
        super.onResume();
        this.mLetvApplication.setWxisShare(false);
    }

    public void onClick(View v) {
        if (!LetvApplication.getInstance().isWxisShare()) {
            if (com.letv.core.utils.NetworkUtils.isNetworkAvailable() || v.getId() == 2131362444) {
                this.vid = this.album.Share_vid + "";
                this.pid = NetworkUtils.DELIMITER_LINE;
                this.cid = this.album.cid + "";
                LetvShareControl.mShareAlbum.type = 2;
                switch (v.getId()) {
                    case 2131362426:
                    case 2131362444:
                        this.root.setVisibility(8);
                        this.root = null;
                        if (getShowsDialog()) {
                            dismissAllowingStateLoss();
                            return;
                        }
                        return;
                    case 2131362429:
                        onShareWxTimeLine(true);
                        if (getShowsDialog()) {
                            dismissAllowingStateLoss();
                            return;
                        }
                        return;
                    case 2131362431:
                        onShareWxTimeLine(false);
                        if (getShowsDialog()) {
                            dismissAllowingStateLoss();
                            return;
                        }
                        return;
                    case 2131362433:
                        LetvShareControl.mShareAlbum.type = 1;
                        onShareSina();
                        if (getShowsDialog()) {
                            dismissAllowingStateLoss();
                            return;
                        }
                        return;
                    case 2131362435:
                        onShareQzone();
                        if (getShowsDialog()) {
                            dismissAllowingStateLoss();
                            return;
                        }
                        return;
                    case 2131362437:
                        onShareTencent();
                        if (getShowsDialog()) {
                            dismissAllowingStateLoss();
                            return;
                        }
                        return;
                    case 2131362439:
                        onShareQQ();
                        if (getShowsDialog()) {
                            dismissAllowingStateLoss();
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
            ToastUtils.showToast(LetvApplication.getInstance(), 2131100332);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
    }

    public void onShareSina() {
        if (LetvSinaShareSSO.isLogin(getActivity()) == 1) {
            sinaShareLogin();
        } else if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            sinaShareLogin();
        } else {
            ToastUtils.showToast(getActivity(), 2131101012);
        }
    }

    private void sinaShareLogin() {
        this.mSsoHandler = LetvSinaShareSSO.login(getActivity(), this.album, null, this.album.order, this.album.Share_vid, "", 9, PageIdConstant.hotIndexCategoryPage, "s10");
        StatisticsUtils.staticticsInfoPost(this.mLetvApplication, "4", "c321", "5003", 3, null, PageIdConstant.hotIndexCategoryPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
    }

    public void onShareQzone() {
        LetvQZoneShare.getInstance(getActivity()).gotoSharePage(getActivity(), this.album, this.album.order, this.album.Share_vid, PageIdConstant.hotIndexCategoryPage, "s10");
        StatisticsUtils.staticticsInfoPost(this.mLetvApplication, "4", "c321", "5004", 4, null, PageIdConstant.hotIndexCategoryPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
    }

    public void onShareTencent() {
        letvTencentShare.getInstance(getActivity()).gotoSharePage(getActivity(), this.album, null, this.album.order, this.album.Share_vid, 9, "", PageIdConstant.hotIndexCategoryPage, "s10");
        StatisticsUtils.staticticsInfoPost(this.mLetvApplication, "4", "c321", "5005", 5, null, PageIdConstant.hotIndexCategoryPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
    }

    public void onShareWxTimeLine(boolean isWxTimeLine) {
        if (ShareUtils.checkPackageInstalled(getActivity(), "com.tencent.mm")) {
            LogInfo.log("wx", "album.Share_AlbumName = " + this.album.Share_AlbumName);
            if (!this.mLetvApplication.isWxisShare()) {
                this.mLetvApplication.setWxisShare(true);
                if (isWxTimeLine) {
                    LetvWeixinShare.share(getActivity(), this.album.Share_AlbumName, ShareUtils.getShareHint(this.album.Share_AlbumName, this.album.type, this.album.Share_id, this.album.order, this.album.Share_vid, this.album.sharedPid, (long) this.album.cid, 2, 4, 0), this.album.icon, ShareUtils.getSharePlayUrl(this.album.type, this.album.Share_id, this.album.order, this.album.Share_vid), this.album.cid, isWxTimeLine, PageIdConstant.hotIndexCategoryPage, "s10");
                } else {
                    LetvWeixinShare.share(getActivity(), "", this.album.Share_AlbumName, this.album.icon, ShareUtils.getSharePlayUrl(this.album.type, this.album.Share_id, this.album.order, this.album.Share_vid), this.album.cid, isWxTimeLine, PageIdConstant.hotIndexCategoryPage, "s10");
                }
                if (isWxTimeLine) {
                    StatisticsUtils.staticticsInfoPost(this.mLetvApplication, "4", "c321", "5001", 1, null, PageIdConstant.hotIndexCategoryPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                    return;
                } else {
                    StatisticsUtils.staticticsInfoPost(this.mLetvApplication, "4", "c321", "5002", 2, null, PageIdConstant.hotIndexCategoryPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                    return;
                }
            }
            return;
        }
        UIs.callDialogMsgPositiveButton(getActivity(), DialogMsgConstantId.SEVEN_ZERO_SEVEN_CONSTANT, null);
    }

    public void onShareQQ() {
        if (LetvTencentWeiboShare.isLogin(getActivity()) == 1) {
            qqShareLogin();
        } else if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            qqShareLogin();
        } else {
            ToastUtils.showToast(getActivity(), 2131101012);
        }
    }

    private void qqShareLogin() {
        LetvTencentWeiboShare.login(getActivity(), LetvShareControl.mShareAlbum, 2, LetvShareControl.mShareAlbum.Share_id, LetvShareControl.mShareAlbum.Share_vid, "", 9, PageIdConstant.hotIndexCategoryPage, "s10");
        StatisticsUtils.staticticsInfoPost(this.mLetvApplication, "4", "c321", "5006", 6, null, PageIdConstant.hotIndexCategoryPage, this.cid, this.pid, this.vid, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onDismiss(DialogInterface dialog) {
        this.onShareDialogDismissListener.shareDismiss(true);
    }

    public void setOnShareDialogDismissListener(OnShareDialogDismissListener listener) {
        this.onShareDialogDismissListener = listener;
    }
}
