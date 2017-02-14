package org.cybergarage.upnp.std.av.player;

import org.cybergarage.upnp.std.av.controller.MediaController;
import org.cybergarage.upnp.std.av.renderer.MediaRenderer;

public class MediaPlayer {
    private MediaController controller;
    private MediaRenderer renderer;

    public MediaPlayer(boolean useDMC, boolean useDMR) {
        this.renderer = null;
        this.controller = null;
        if (useDMC) {
            enableRenderer();
        }
        if (useDMR) {
            enableController();
        }
    }

    public MediaPlayer() {
        this(true, true);
    }

    public MediaController getController() {
        return this.controller;
    }

    public void enableController() {
        if (this.controller == null) {
            this.controller = new MediaController();
        }
    }

    public void disableController() {
        this.controller = null;
    }

    public boolean isControllerEnable() {
        return this.controller != null;
    }

    public MediaRenderer getRenderer() {
        return this.renderer;
    }

    public void enableRenderer() {
        if (this.renderer == null) {
            this.renderer = new MediaRenderer();
        }
    }

    public void disableRenderer() {
        this.renderer = null;
    }

    public boolean isRendererEnable() {
        return this.renderer != null;
    }

    public void start() {
        if (this.renderer != null) {
            this.renderer.start();
        }
        if (this.controller != null) {
            this.controller.start();
        }
    }

    public void stop() {
        if (this.renderer != null) {
            this.renderer.stop();
        }
        if (this.controller != null) {
            this.controller.stop();
        }
    }
}
