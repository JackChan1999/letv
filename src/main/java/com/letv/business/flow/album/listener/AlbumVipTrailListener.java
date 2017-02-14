package com.letv.business.flow.album.listener;

import com.letv.core.bean.AlbumPayInfoBean;

public interface AlbumVipTrailListener {

    public enum VipTrailErrorState {
        INIT,
        START_WITH_LOGIN,
        START_WITH_NOT_LOGIN,
        START_WITH_TICKET,
        START_WITH_NOT_TICKET,
        END_WITH_LOGIN,
        END_WITH_NOT_LOGIN,
        END_WITH_TICKET,
        END_WITH_NOT_TICKET,
        FORBIDDEN
    }

    boolean checkVipTrailIsStateEnd();

    void finish();

    void forbidden();

    VipTrailErrorState getErrState();

    void hide();

    boolean isError();

    boolean isVipTrailEnd();

    boolean isVipVideo();

    void setPayInfo(AlbumPayInfoBean albumPayInfoBean);

    void setStateForStartByHasLogined(boolean z);

    void setStateForStartByHasNoTicket();

    void setStateForStartByHasTicket(boolean z);

    void setVisibileWithController(boolean z);

    void vipTrailBackTitleScreenProcess(String str, boolean z);

    void vipTrailEndByHasLogined(boolean z);

    void vipTrailEndByHasTicket(boolean z);

    void vipTrailUseTicketSuccess();
}
