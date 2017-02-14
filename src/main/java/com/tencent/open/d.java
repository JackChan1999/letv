package com.tencent.open;

import android.location.Location;
import com.tencent.map.a.a.b;
import com.tencent.open.a.f;
import com.tencent.open.c.a;

/* compiled from: ProGuard */
public class d extends b {
    private a a;

    public d(a aVar) {
        super(1, 0, 0, 8);
        this.a = aVar;
    }

    public void a(byte[] bArr, int i) {
        super.a(bArr, i);
    }

    public void a(com.tencent.map.a.a.d dVar) {
        f.c(f.d, "location: onLocationUpdate = " + dVar);
        super.a(dVar);
        if (dVar != null) {
            Location location = new Location("passive");
            location.setLatitude(dVar.b);
            location.setLongitude(dVar.c);
            if (this.a != null) {
                this.a.onLocationUpdate(location);
            }
        }
    }

    public void a(int i) {
        f.c(f.d, "location: onStatusUpdate = " + i);
        super.a(i);
    }
}
