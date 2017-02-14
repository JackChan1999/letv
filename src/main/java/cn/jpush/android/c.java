package cn.jpush.android;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public abstract class c extends Binder implements b {
    private static final String z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "e\u0010\u001b\t\u0015s\r]M\u0004h\u001aG\f\fbP|'\u0004r\u001ff\u000b\u0004t\u001b";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x0027;
    L_0x000b:
        r3 = r0;
        r4 = r2;
        r7 = r1;
        r1 = r0;
        r0 = r7;
    L_0x0010:
        r6 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x0035;
            case 1: goto L_0x0037;
            case 2: goto L_0x003a;
            case 3: goto L_0x003d;
            default: goto L_0x0017;
        };
    L_0x0017:
        r5 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
    L_0x0019:
        r5 = r5 ^ r6;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x0025;
    L_0x0021:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x0010;
    L_0x0025:
        r1 = r0;
        r0 = r3;
    L_0x0027:
        if (r1 > r2) goto L_0x000b;
    L_0x0029:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        z = r0;
        return;
    L_0x0035:
        r5 = 6;
        goto L_0x0019;
    L_0x0037:
        r5 = 126; // 0x7e float:1.77E-43 double:6.23E-322;
        goto L_0x0019;
    L_0x003a:
        r5 = 53;
        goto L_0x0019;
    L_0x003d:
        r5 = 99;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.c.<clinit>():void");
    }

    public c() {
        attachInterface(this, z);
    }

    public static b a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface(z);
        return (queryLocalInterface == null || !(queryLocalInterface instanceof b)) ? new d(iBinder) : (b) queryLocalInterface;
    }

    public IBinder asBinder() {
        return this;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        boolean z = false;
        String readString;
        switch (i) {
            case 1:
                parcel.enforceInterface(z);
                int readInt = parcel.readInt();
                long readLong = parcel.readLong();
                if (parcel.readInt() != 0) {
                    z = true;
                }
                a(readInt, readLong, z, parcel.readFloat(), parcel.readDouble(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 2:
                parcel.enforceInterface(z);
                int a = a(parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(a);
                return true;
            case 3:
                parcel.enforceInterface(z);
                b(parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 4:
                parcel.enforceInterface(z);
                long a2 = a(parcel.readString(), parcel.readLong());
                parcel2.writeNoException();
                parcel2.writeLong(a2);
                return true;
            case 5:
                parcel.enforceInterface(z);
                b(parcel.readString(), parcel.readLong());
                parcel2.writeNoException();
                return true;
            case 6:
                int i3;
                parcel.enforceInterface(z);
                boolean a3 = a(parcel.readString(), parcel.readInt() != 0);
                parcel2.writeNoException();
                if (a3) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            case 7:
                parcel.enforceInterface(z);
                readString = parcel.readString();
                if (parcel.readInt() != 0) {
                    z = true;
                }
                b(readString, z);
                parcel2.writeNoException();
                return true;
            case 8:
                parcel.enforceInterface(z);
                readString = a(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeString(readString);
                return true;
            case 9:
                parcel.enforceInterface(z);
                b(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 1598968902:
                parcel2.writeString(z);
                return true;
            default:
                return super.onTransact(i, parcel, parcel2, i2);
        }
    }
}
