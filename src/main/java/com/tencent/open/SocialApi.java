package com.tencent.open;

import android.app.Activity;
import android.os.Bundle;
import com.tencent.connect.auth.QQToken;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;

/* compiled from: ProGuard */
public class SocialApi {
    private SocialApiIml a;

    public SocialApi(QQToken qQToken) {
        this.a = new SocialApiIml(qQToken);
    }

    public void invite(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.a.invite(activity, bundle, iUiListener);
    }

    public void story(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.a.story(activity, bundle, iUiListener);
    }

    public void gift(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.a.gift(activity, bundle, iUiListener);
    }

    public void ask(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.a.ask(activity, bundle, iUiListener);
    }

    public void reactive(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.a.reactive(activity, bundle, iUiListener);
    }

    public void brag(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.a.brag(activity, bundle, iUiListener);
    }

    public void challenge(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.a.challenge(activity, bundle, iUiListener);
    }

    public void grade(Activity activity, Bundle bundle, IUiListener iUiListener) {
        bundle.putString("version", Util.getAppVersion(activity));
        this.a.grade(activity, bundle, iUiListener);
    }

    public void voice(Activity activity, Bundle bundle, IUiListener iUiListener) {
        bundle.putString("version", Util.getAppVersion(activity));
        this.a.voice(activity, bundle, iUiListener);
    }

    public boolean checkVoiceApi(Activity activity, Bundle bundle, IUiListener iUiListener) {
        bundle.putString("version", Util.getAppVersion(activity));
        this.a.grade(activity, bundle, iUiListener);
        return true;
    }
}
