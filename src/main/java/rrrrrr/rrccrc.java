package rrrrrr;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.immersion.hapticmediasdk.models.HapticFileInformation;

public class rrccrc implements Creator {
    public static int b041104110411БББ = 59;
    public static int b0411Б0411БББ = 1;
    public static int bБ04110411БББ = 2;
    public static int bБББ0411ББ;

    public static int b04110411Б0411ББ() {
        return 2;
    }

    public static int b0411ББ0411ББ() {
        return 0;
    }

    public static int bБ0411Б0411ББ() {
        return 1;
    }

    public static int bББ0411БББ() {
        return 54;
    }

    public HapticFileInformation createFromParcel(Parcel parcel) {
        try {
            if (((b041104110411БББ + b0411Б0411БББ) * b041104110411БББ) % bБ04110411БББ != bБББ0411ББ) {
                b041104110411БББ = 65;
                bБББ0411ББ = 98;
            }
            return new HapticFileInformation(parcel);
        } catch (Exception e) {
            throw e;
        }
    }

    public HapticFileInformation[] newArray(int i) {
        int i2 = b041104110411БББ;
        switch ((i2 * (b0411Б0411БББ + i2)) % bБ04110411БББ) {
            case 0:
                break;
            default:
                b041104110411БББ = bББ0411БББ();
                bБББ0411ББ = 46;
                break;
        }
        try {
            return new HapticFileInformation[i];
        } catch (Exception e) {
            throw e;
        }
    }
}
