package com.letv.android.client.dlna.engine;

import android.text.TextUtils;
import com.letv.android.client.dlna.inter.IDLNAController;
import org.cybergarage.upnp.Action;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.Service;
import org.cybergarage.upnp.std.av.renderer.AVTransport;
import org.cybergarage.upnp.std.av.renderer.RenderingControl;

public class MultiPointController implements IDLNAController {
    private static final String AVTransport1 = "urn:schemas-upnp-org:service:AVTransport:1";
    private static final String Play = "Play";
    private static final String RenderingControl = "urn:schemas-upnp-org:service:RenderingControl:1";
    private static final String SetAVTransportURI = "SetAVTransportURI";

    public boolean play(Device device, String path) {
        if (device == null) {
            return false;
        }
        Service service = device.getService("urn:schemas-upnp-org:service:AVTransport:1");
        if (service == null) {
            return false;
        }
        Action action = service.getAction("SetAVTransportURI");
        if (action == null) {
            return false;
        }
        Action playAction = service.getAction("Play");
        if (playAction == null || TextUtils.isEmpty(path)) {
            return false;
        }
        action.setArgumentValue("InstanceID", 0);
        action.setArgumentValue(AVTransport.CURRENTURI, path);
        action.setArgumentValue(AVTransport.CURRENTURIMETADATA, 0);
        if (!action.postControlAction()) {
            return false;
        }
        playAction.setArgumentValue("InstanceID", 0);
        playAction.setArgumentValue(AVTransport.SPEED, "1");
        return playAction.postControlAction();
    }

    public boolean goon(Device device, String pausePosition) {
        if (device == null) {
            return false;
        }
        Service localService = device.getService("urn:schemas-upnp-org:service:AVTransport:1");
        if (localService == null) {
            return false;
        }
        Action localAction = localService.getAction(AVTransport.SEEK);
        if (localAction == null) {
            return false;
        }
        localAction.setArgumentValue("InstanceID", "0");
        localAction.setArgumentValue(AVTransport.UNIT, "ABS_TIME");
        localAction.setArgumentValue(AVTransport.TARGET, pausePosition);
        localAction.postControlAction();
        Action playAction = localService.getAction("Play");
        if (playAction == null) {
            return false;
        }
        playAction.setArgumentValue("InstanceID", 0);
        playAction.setArgumentValue(AVTransport.SPEED, "1");
        return playAction.postControlAction();
    }

    public String getTransportState(Device device) {
        if (device == null) {
            return "STOPPED";
        }
        Service localService = device.getService("urn:schemas-upnp-org:service:AVTransport:1");
        if (localService == null) {
            return null;
        }
        Action localAction = localService.getAction(AVTransport.GETTRANSPORTINFO);
        if (localAction == null) {
            return null;
        }
        localAction.setArgumentValue("InstanceID", "0");
        if (localAction.postControlAction()) {
            return localAction.getArgumentValue(AVTransport.CURRENTTRANSPORTSTATE);
        }
        return null;
    }

    public String getVolumeDbRange(Device device, String argument) {
        if (device == null) {
            return "";
        }
        Service localService = device.getService("urn:schemas-upnp-org:service:RenderingControl:1");
        if (localService == null) {
            return null;
        }
        Action localAction = localService.getAction(RenderingControl.GETVOLUMEDBRANGE);
        if (localAction == null) {
            return null;
        }
        localAction.setArgumentValue("InstanceID", "0");
        localAction.setArgumentValue(RenderingControl.CHANNEL, RenderingControl.MASTER);
        if (localAction.postControlAction()) {
            return localAction.getArgumentValue(argument);
        }
        return null;
    }

    public int getMinVolumeValue(Device device) {
        String minValue = getVolumeDbRange(device, RenderingControl.MINVALUE);
        if (TextUtils.isEmpty(minValue)) {
            return 0;
        }
        return Integer.parseInt(minValue);
    }

    public int getMaxVolumeValue(Device device) {
        String maxValue = getVolumeDbRange(device, RenderingControl.MAXVALUE);
        if (TextUtils.isEmpty(maxValue)) {
            return 100;
        }
        return Integer.parseInt(maxValue);
    }

    public boolean seek(Device device, String targetPosition) {
        if (device == null) {
            return false;
        }
        Service localService = device.getService("urn:schemas-upnp-org:service:AVTransport:1");
        if (localService == null) {
            return false;
        }
        Action localAction = localService.getAction(AVTransport.SEEK);
        if (localAction == null) {
            return false;
        }
        localAction.setArgumentValue("InstanceID", "0");
        localAction.setArgumentValue(AVTransport.UNIT, "ABS_TIME");
        localAction.setArgumentValue(AVTransport.TARGET, targetPosition);
        boolean postControlAction = localAction.postControlAction();
        if (postControlAction) {
            return postControlAction;
        }
        localAction.setArgumentValue(AVTransport.UNIT, "REL_TIME");
        localAction.setArgumentValue(AVTransport.TARGET, targetPosition);
        return localAction.postControlAction();
    }

    public String[] getPositionInfo(Device device) {
        if (device == null) {
            return new String[]{"00:00:00", ""};
        }
        Service localService = device.getService("urn:schemas-upnp-org:service:AVTransport:1");
        if (localService == null) {
            return null;
        }
        Action localAction = localService.getAction(AVTransport.GETPOSITIONINFO);
        if (localAction == null) {
            return null;
        }
        localAction.setArgumentValue("InstanceID", "0");
        if (localAction.postControlAction()) {
            String seek = localAction.getArgumentValue(AVTransport.RELTIME);
            String url = localAction.getArgumentValue(AVTransport.TRACKURI);
            return new String[]{seek, url};
        }
        return new String[]{"00:00:00", ""};
    }

    public String getMediaDuration(Device device) {
        if (device == null) {
            return "00:00:00";
        }
        Service localService = device.getService("urn:schemas-upnp-org:service:AVTransport:1");
        if (localService == null) {
            return null;
        }
        Action localAction = localService.getAction(AVTransport.GETMEDIAINFO);
        if (localAction == null) {
            return null;
        }
        localAction.setArgumentValue("InstanceID", "0");
        if (localAction.postControlAction()) {
            return localAction.getArgumentValue(AVTransport.MEDIADURATION);
        }
        return null;
    }

    public boolean setMute(Device mediaRenderDevice, String targetValue) {
        if (mediaRenderDevice == null) {
            return false;
        }
        Service service = mediaRenderDevice.getService("urn:schemas-upnp-org:service:RenderingControl:1");
        if (service == null) {
            return false;
        }
        Action action = service.getAction(RenderingControl.SETMUTE);
        if (action == null) {
            return false;
        }
        action.setArgumentValue("InstanceID", "0");
        action.setArgumentValue(RenderingControl.CHANNEL, RenderingControl.MASTER);
        action.setArgumentValue(RenderingControl.DESIREDMUTE, targetValue);
        return action.postControlAction();
    }

    public String getMute(Device device) {
        if (device == null) {
            return "";
        }
        Service service = device.getService("urn:schemas-upnp-org:service:RenderingControl:1");
        if (service == null) {
            return null;
        }
        Action getAction = service.getAction(RenderingControl.GETMUTE);
        if (getAction == null) {
            return null;
        }
        getAction.setArgumentValue("InstanceID", "0");
        getAction.setArgumentValue(RenderingControl.CHANNEL, RenderingControl.MASTER);
        getAction.postControlAction();
        return getAction.getArgumentValue(RenderingControl.CURRENTMUTE);
    }

    public boolean setVoice(Device device, int value) {
        if (device == null) {
            return false;
        }
        Service service = device.getService("urn:schemas-upnp-org:service:RenderingControl:1");
        if (service == null) {
            return false;
        }
        Action action = service.getAction(RenderingControl.SETVOLUME);
        if (action == null) {
            return false;
        }
        action.setArgumentValue("InstanceID", "0");
        action.setArgumentValue(RenderingControl.CHANNEL, RenderingControl.MASTER);
        action.setArgumentValue(RenderingControl.DESIREDVOLUME, value);
        return action.postControlAction();
    }

    public int getVoice(Device device) {
        if (device == null) {
            return 0;
        }
        Service service = device.getService("urn:schemas-upnp-org:service:RenderingControl:1");
        if (service == null) {
            return -1;
        }
        Action getAction = service.getAction(RenderingControl.GETVOLUME);
        if (getAction == null) {
            return -1;
        }
        getAction.setArgumentValue("InstanceID", "0");
        getAction.setArgumentValue(RenderingControl.CHANNEL, RenderingControl.MASTER);
        if (getAction.postControlAction()) {
            return getAction.getArgumentIntegerValue(RenderingControl.CURRENTVOLUME);
        }
        return -1;
    }

    public boolean stop(Device device) {
        if (device == null) {
            return false;
        }
        Service service = device.getService("urn:schemas-upnp-org:service:AVTransport:1");
        if (service == null) {
            return false;
        }
        Action stopAction = service.getAction(AVTransport.STOP);
        if (stopAction == null) {
            return false;
        }
        stopAction.setArgumentValue("InstanceID", 0);
        return stopAction.postControlAction();
    }

    public boolean pause(Device mediaRenderDevice) {
        if (mediaRenderDevice == null) {
            return false;
        }
        Service service = mediaRenderDevice.getService("urn:schemas-upnp-org:service:AVTransport:1");
        if (service == null) {
            return false;
        }
        Action pauseAction = service.getAction(AVTransport.PAUSE);
        if (pauseAction == null) {
            return false;
        }
        pauseAction.setArgumentValue("InstanceID", 0);
        return pauseAction.postControlAction();
    }
}
