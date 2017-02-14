package cn.jpush.android.a;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

final class h implements LocationListener {
    final /* synthetic */ g a;

    h(g gVar) {
        this.a = gVar;
    }

    public final void onLocationChanged(Location location) {
        this.a.a(location);
    }

    public final void onProviderDisabled(String str) {
        this.a.a(null);
        this.a.c();
    }

    public final void onProviderEnabled(String str) {
        this.a.b();
    }

    public final void onStatusChanged(String str, int i, Bundle bundle) {
    }
}
