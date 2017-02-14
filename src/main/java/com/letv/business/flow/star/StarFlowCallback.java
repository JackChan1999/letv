package com.letv.business.flow.star;

import com.letv.core.bean.FollowStatusMapBean;
import com.letv.core.bean.PlayVoteListBean.PlayVoteResultBean;
import com.letv.core.bean.StarInfoBean;

public interface StarFlowCallback {

    public enum StarFollowType {
        STAR_INFO,
        FOLLOW,
        VOTE
    }

    void netError(StarFollowType starFollowType);

    void onAskFollowStatus(boolean z);

    void onAskFollowStatusList(FollowStatusMapBean followStatusMapBean);

    void onStarFollow(boolean z, int i);

    void onStarSuccess(StarInfoBean starInfoBean);

    void onVoteSuccess(PlayVoteResultBean playVoteResultBean);
}
