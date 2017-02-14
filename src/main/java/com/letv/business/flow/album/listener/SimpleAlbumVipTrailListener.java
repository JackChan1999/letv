package com.letv.business.flow.album.listener;

import com.letv.business.flow.album.listener.AlbumVipTrailListener.VipTrailErrorState;
import com.letv.core.bean.AlbumPayInfoBean;

public class SimpleAlbumVipTrailListener implements AlbumVipTrailListener {
    public void setPayInfo(AlbumPayInfoBean payInfo) {
    }

    public void setStateForStartByHasLogined(boolean hasLogined) {
    }

    public void setStateForStartByHasNoTicket() {
    }

    public void setStateForStartByHasTicket(boolean isVip) {
    }

    public void vipTrailEndByHasLogined(boolean hasLogined) {
    }

    public void vipTrailEndByHasTicket(boolean hasTicket) {
    }

    public void vipTrailBackTitleScreenProcess(String title, boolean isDolby) {
    }

    public void vipTrailUseTicketSuccess() {
    }

    public boolean isError() {
        return false;
    }

    public void hide() {
    }

    public void finish() {
    }

    public VipTrailErrorState getErrState() {
        return null;
    }

    public boolean isVipTrailEnd() {
        return false;
    }

    public boolean checkVipTrailIsStateEnd() {
        return false;
    }

    public boolean isVipVideo() {
        return false;
    }

    public void setVisibileWithController(boolean show) {
    }

    public void forbidden() {
    }
}
