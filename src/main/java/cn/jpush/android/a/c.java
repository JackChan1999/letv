package cn.jpush.android.a;

import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;

final class c extends PhoneStateListener {
    final /* synthetic */ b a;

    c(b bVar) {
        this.a = bVar;
    }

    public final void onCellLocationChanged(CellLocation cellLocation) {
    }

    public final void onSignalStrengthChanged(int i) {
        this.a.a = i;
    }
}
