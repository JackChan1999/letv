package com.letv.business.flow.album.listener;

import com.letv.core.bean.PlayRecord;

public class SimplePlayVideoFragmentListener implements PlayVideoFragmentListener {
    public void initVideoView(boolean isLocal, boolean isChangeStream) {
    }

    public void start() {
    }

    public void pause() {
    }

    public void stopPlayback() {
    }

    public boolean isPlaying() {
        return false;
    }

    public boolean isPaused() {
        return false;
    }

    public void resetPlayFlag() {
    }

    public void setEnforcementPause(boolean pause) {
    }

    public void setEnforcementWait(boolean wait) {
    }

    public boolean isEnforcementPause() {
        return false;
    }

    public void seekTo(long position, boolean shouldAddDuration) {
    }

    public long getCurrentPosition() {
        return 0;
    }

    public long getDuration() {
        return 0;
    }

    public int getBufferPercentage() {
        return 0;
    }

    public void startPlayLocal(String uri, long mesc, boolean isChangeStream) {
    }

    public void startPlayNet(String uriString, long msec, boolean isChangeStream, boolean forceSeek) {
    }

    public PlayRecord getPoint(int pid, int vid, boolean isDownload) {
        return null;
    }

    public void handlerFloatBall(String pageId, int cid) {
    }

    public void startOverall() {
    }

    public void finishPlayer(boolean isJumpToPip) {
    }

    public void hideRecommendTip() {
    }

    public void playAnotherVideo(boolean isFromInter) {
    }

    public void rePlay(boolean isChangeStream) {
    }

    public void buyWo3G() {
    }

    public void onChangeStreamError() {
    }

    public void checkDrmPlugin() {
    }

    public void loadDrmPlugin() {
    }
}
