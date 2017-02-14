package com.letv.component.player;

import android.hardware.SensorEvent;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.view.View;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import com.letv.component.player.Interface.OnMediaStateTimeListener;
import com.letv.component.player.Interface.OnVideoViewStateChangeListener;
import com.letv.component.player.core.PlayUrl;
import com.media.ffmpeg.FFMpegPlayer.OnAdNumberListener;
import com.media.ffmpeg.FFMpegPlayer.OnBlockListener;
import com.media.ffmpeg.FFMpegPlayer.OnCacheListener;
import com.media.ffmpeg.FFMpegPlayer.OnFirstPlayLitener;
import com.media.ffmpeg.FFMpegPlayer.OnHardDecodeErrorListner;
import java.util.Map;

public interface LetvMediaPlayerControl extends MediaPlayerControl {
    void adjust(int i);

    void forward();

    int getLastSeekWhenDestoryed();

    View getView();

    boolean isEnforcementPause();

    boolean isEnforcementWait();

    boolean isInPlaybackState();

    boolean isPaused();

    void processSensorEvent(SensorEvent sensorEvent);

    void rewind();

    int setAngleInit();

    void setCacheSize(int i, int i2, int i3, int i4);

    void setCacheTime(double d, double d2);

    void setEnforcementPause(boolean z);

    void setEnforcementWait(boolean z);

    int setGravityInfomation(float f, float f2, float f3);

    void setInitPosition(int i);

    int setMachineInfomation(float f);

    void setMediaController(MediaController mediaController);

    void setOnAdNumberListener(OnAdNumberListener onAdNumberListener);

    void setOnBlockListener(OnBlockListener onBlockListener);

    void setOnBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener);

    void setOnCacheListener(OnCacheListener onCacheListener);

    void setOnCompletionListener(OnCompletionListener onCompletionListener);

    void setOnErrorListener(OnErrorListener onErrorListener);

    void setOnFirstPlayListener(OnFirstPlayLitener onFirstPlayLitener);

    void setOnHardDecodeErrorListener(OnHardDecodeErrorListner onHardDecodeErrorListner);

    void setOnInfoListener(OnInfoListener onInfoListener);

    void setOnMediaStateTimeListener(OnMediaStateTimeListener onMediaStateTimeListener);

    void setOnPreparedListener(OnPreparedListener onPreparedListener);

    void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener);

    void setOnVideoSizeChangedListener(OnVideoSizeChangedListener onVideoSizeChangedListener);

    int setOneFingertouchInfomation(float f, float f2, float f3, float f4);

    int setSourceType(int i);

    int setTwoFingerZoom(float f);

    int setTwoFingertouchInfomation(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8);

    void setVideoPath(String str);

    void setVideoPath(String str, Map<String, String> map);

    void setVideoPlayUrl(PlayUrl playUrl);

    void setVideoViewStateChangeListener(OnVideoViewStateChangeListener onVideoViewStateChangeListener);

    void setVolume(int i);

    int setgravity_yroInfomation(float f, float f2, float f3);

    int setgravity_yroValidInfomation(boolean z);

    void stopPlayback();
}
