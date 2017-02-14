package com.letv.business.flow.album.controller;

import android.content.Context;
import com.letv.android.client.business.R;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.core.bean.RealPlayUrlInfoBean;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.utils.TipUtils;
import com.letv.datastatistics.constant.LetvErrorCode;

public class AlbumFlowControllerSeparate extends AlbumFlowRequestUrlController {
    public AlbumFlowControllerSeparate(Context context, AlbumPlayFlow flow) {
        super(context, flow);
    }

    protected void doRequest(boolean isRetry) {
        if (this.mFlow.mVideoType != VideoType.Drm || !startPlayDrm()) {
            if (this.mCdeEnable) {
                getRealUrlFromCde();
            } else {
                getRealUrlFromNet();
            }
        }
    }

    protected boolean onAfterFetchRealUrlFromCde(RealPlayUrlInfoBean result, CacheResponseState state) {
        if (!super.onAfterFetchRealUrlFromCde(result, state)) {
            if (state == CacheResponseState.SUCCESS) {
                onAfterFetchRealUrl(result.realUrl, VideoPlayChannel.CDE);
            } else {
                this.mFlow.mLoadListener.requestError(TipUtils.getTipMessage("100077", R.string.commit_error_info), "0302", "");
                showError(true, LetvErrorCode.REQUEST_REAL_URL_FROM_CDE_ERROR);
            }
        }
        return true;
    }

    protected void onAfterFetchRealUrlFromNet(RealPlayUrlInfoBean result) {
        onAfterFetchRealUrl(result.realUrl, VideoPlayChannel.CDN);
    }
}
