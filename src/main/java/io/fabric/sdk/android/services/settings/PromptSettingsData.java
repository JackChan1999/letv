package io.fabric.sdk.android.services.settings;

public class PromptSettingsData {
    public final String alwaysSendButtonTitle;
    public final String cancelButtonTitle;
    public final String message;
    public final String sendButtonTitle;
    public final boolean showAlwaysSendButton;
    public final boolean showCancelButton;
    public final String title;

    public PromptSettingsData(String title, String message, String sendButtonTitle, boolean showCancelButton, String cancelButtonTitle, boolean showAlwaysSendButton, String alwaysSendButtonTitle) {
        this.title = title;
        this.message = message;
        this.sendButtonTitle = sendButtonTitle;
        this.showCancelButton = showCancelButton;
        this.cancelButtonTitle = cancelButtonTitle;
        this.showAlwaysSendButton = showAlwaysSendButton;
        this.alwaysSendButtonTitle = alwaysSendButtonTitle;
    }
}
