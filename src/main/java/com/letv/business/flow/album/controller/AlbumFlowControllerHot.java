package com.letv.business.flow.album.controller;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.letv.android.client.business.R;
import com.letv.android.wo.ex.IWoFlowManager;
import com.letv.android.wo.ex.WoInterface.LetvWoFlowListener;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.business.flow.album.AlbumPlayHotFlow;
import com.letv.business.flow.unicom.UnicomWoFlowDialogUtils;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.bean.DDUrlsResultBean;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.RealPlayUrlInfoBean;
import com.letv.core.bean.VideoFileBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.parser.RealPlayUrlInfoParser;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.PlayUtils;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.loader.JarLoader;

public class AlbumFlowControllerHot extends AlbumFlowControllerSeparate {
    private AlbumPlayHotFlow mHotFlow;

    public AlbumFlowControllerHot(Context context, AlbumPlayFlow flow) {
        super(context, flow);
        this.mHotFlow = (AlbumPlayHotFlow) flow;
    }

    protected boolean onPreRequestRealPlayUrl() {
        if (this.mFlow.mVideoFile == null || this.mFlow.mCurrentPlayingVideo == null) {
            return false;
        }
        boolean z;
        AlbumPlayHotFlow.sIsWo3GUser = this.mFlow.mIsWo3GUser;
        int playLevel = PreferencesManager.getInstance().getPlayLevel();
        VideoFileBean videoFileBean = this.mFlow.mVideoFile;
        if (this.mFlow.mCurrentPlayingVideo.pay == 1) {
            z = true;
        } else {
            z = false;
        }
        DDUrlsResultBean ddUrlsResultBean = PlayUtils.getDDUrls(videoFileBean, playLevel, z, this.mFlow.mVideoType);
        if (ddUrlsResultBean == null) {
            return false;
        }
        this.mFlow.mDdUrlsResult = ddUrlsResultBean;
        this.mFlow.mPlayLevel = this.mFlow.mDdUrlsResult.playLevel;
        return true;
    }

    protected void getRealUrlFromNet() {
        final View view = this.mHotFlow.mListener.getAttachView();
        if (view != null) {
            this.mFlow.mPlayInfo.type9 = System.currentTimeMillis();
            LogInfo.log("wuxinrong", "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB Hot播放，传入空的音轨id");
            String url = PlayUtils.getLinkShell(this.mFlow.mAlbumUrl.dispatchUrl, PlayUtils.getPlayToken(this.mFlow.mDdUrlsResult, this.mFlow.mPayInfo), PreferencesManager.getInstance().getUserId(), this.mFlow.mVid + "", this.mFlow.mPlayInfo.mUuidTimp, "");
            if (!TextUtils.isEmpty(url)) {
                Volley.getQueue().cancelWithTag(AlbumPlayFlow.REQUEST_REAL_URL);
                new LetvRequest().setUrl(url).setParser(new RealPlayUrlInfoParser()).setRequestType(RequestManner.NETWORK_ONLY).setTag(AlbumPlayFlow.REQUEST_REAL_URL).setCallback(new SimpleResponse<RealPlayUrlInfoBean>() {
                    public void onNetworkResponse(VolleyRequest<RealPlayUrlInfoBean> volleyRequest, RealPlayUrlInfoBean result, DataHull hull, NetworkResponseState state) {
                        AlbumFlowControllerHot.this.mFlow.mPlayInfo.type9 = System.currentTimeMillis() - AlbumFlowControllerHot.this.mFlow.mPlayInfo.type9;
                        if (state == NetworkResponseState.SUCCESS) {
                            AlbumFlowControllerHot.this.onAfterFetchRealUrl(result.realUrl, VideoPlayChannel.CDN);
                        } else if (state == NetworkResponseState.NETWORK_NOT_AVAILABLE || state == NetworkResponseState.NETWORK_ERROR) {
                            AlbumFlowControllerHot.this.mHotFlow.mListener.onNetError(true, view.getTag(), view);
                        } else if (state == NetworkResponseState.RESULT_ERROR) {
                            AlbumFlowControllerHot.this.mHotFlow.mListener.onDataError(true, view.getTag(), view);
                        }
                    }
                }).add();
            }
        }
    }

    protected void onAfterFetchRealUrl(String url, VideoPlayChannel channel) {
        AlbumPlayHotFlow.sIsWo3GUser = this.mFlow.mIsWo3GUser;
        final View view = this.mHotFlow.mListener.getAttachView();
        if (view != null) {
            this.mHotFlow.mListener.dismissErrorLayout(view.getTag());
            if (AlbumPlayHotFlow.sIsWo3GUser) {
                ((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(this.mContext, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).identifyWoVideoSDK(this.mContext, url, 1, new LetvWoFlowListener() {
                    public void onResponseOrderInfo(boolean isSupportProvince, boolean isOrder, boolean isUnOrderSure, String freeUrl, boolean isSmsSuccess) {
                        if (AlbumFlowControllerHot.this.mContext != null) {
                            if (TextUtils.isEmpty(freeUrl)) {
                                AlbumFlowControllerHot.this.mHandler.post(new Runnable() {
                                    public void run() {
                                        AlbumFlowControllerHot.this.mHotFlow.mListener.onNetError(true, view.getTag(), view);
                                    }
                                });
                                return;
                            }
                            AlbumFlowControllerHot.this.mHotFlow.mAlbumUrl.realUrl = freeUrl;
                            AlbumFlowControllerHot.this.mHotFlow.addPlayInfo("3G请求获取真实地址 realUrl", AlbumFlowControllerHot.this.mHotFlow.mAlbumUrl.realUrl);
                            AlbumFlowControllerHot.this.mHotFlow.mIsPlayFreeUrl = true;
                            AlbumFlowControllerHot.this.mHandler.post(new Runnable() {
                                public void run() {
                                    UnicomWoFlowDialogUtils.showWoFreeActivatedToast(AlbumFlowControllerHot.this.mContext);
                                    if (AlbumFlowControllerHot.this.mHotFlow.mPlayListener != null) {
                                        AlbumFlowControllerHot.this.mHotFlow.mPlayListener.setShowToast(false);
                                    }
                                    AlbumFlowControllerHot.this.mHotFlow.mListener.createPlayerForPlay(view.getTag(), view);
                                    AlbumFlowControllerHot.this.mHotFlow.startPlayNet();
                                }
                            });
                        }
                    }
                });
                return;
            }
            if (channel == VideoPlayChannel.CDE) {
                if (TextUtils.isEmpty(this.mHotFlow.mAlbumUrl.p2pUrl)) {
                    this.mHotFlow.mAlbumUrl.realUrl = BaseApplication.getInstance().getCdeHelper().getPlayUrl(this.mHotFlow.mAlbumUrl.p2pUrl, "", "");
                }
            } else if (TextUtils.isEmpty(url)) {
                this.mHotFlow.mListener.setErrorText(R.string.hot_play_error_net_null, true, view.getTag(), view);
                return;
            } else {
                this.mHotFlow.mAlbumUrl.realUrl = url;
                this.mHotFlow.addPlayInfo("不走p2p,请求获取真实地址 realUrl", this.mHotFlow.mAlbumUrl.realUrl);
            }
            this.mHotFlow.mListener.dismissErrorLayout(view.getTag());
            this.mHotFlow.mListener.createPlayerForPlay(view.getTag(), view);
            this.mHotFlow.startPlayNet();
        }
    }
}
