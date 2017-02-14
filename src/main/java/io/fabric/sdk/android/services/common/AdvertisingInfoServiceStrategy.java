package io.fabric.sdk.android.services.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import io.fabric.sdk.android.Fabric;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class AdvertisingInfoServiceStrategy implements AdvertisingInfoStrategy {
    public static final String GOOGLE_PLAY_SERVICES_INTENT = "com.google.android.gms.ads.identifier.service.START";
    public static final String GOOGLE_PLAY_SERVICES_INTENT_PACKAGE_NAME = "com.google.android.gms";
    private static final String GOOGLE_PLAY_SERVICE_PACKAGE_NAME = "com.android.vending";
    private final Context context;

    private static final class AdvertisingConnection implements ServiceConnection {
        private static final int QUEUE_TIMEOUT_IN_MS = 200;
        private final LinkedBlockingQueue<IBinder> queue;
        private boolean retrieved;

        private AdvertisingConnection() {
            this.retrieved = false;
            this.queue = new LinkedBlockingQueue(1);
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                this.queue.put(service);
            } catch (InterruptedException e) {
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            this.queue.clear();
        }

        public IBinder getBinder() {
            if (this.retrieved) {
                Fabric.getLogger().e(Fabric.TAG, "getBinder already called");
            }
            this.retrieved = true;
            try {
                return (IBinder) this.queue.poll(200, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                return null;
            }
        }
    }

    private static final class AdvertisingInterface implements IInterface {
        public static final String ADVERTISING_ID_SERVICE_INTERFACE_TOKEN = "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService";
        private static final int AD_TRANSACTION_CODE_ID = 1;
        private static final int AD_TRANSACTION_CODE_LIMIT_AD_TRACKING = 2;
        private static final int FLAGS_NONE = 0;
        private final IBinder binder;

        public AdvertisingInterface(IBinder binder) {
            this.binder = binder;
        }

        public IBinder asBinder() {
            return this.binder;
        }

        public String getId() throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            String id = null;
            try {
                data.writeInterfaceToken(ADVERTISING_ID_SERVICE_INTERFACE_TOKEN);
                this.binder.transact(1, data, reply, 0);
                reply.readException();
                id = reply.readString();
            } catch (Exception e) {
                Fabric.getLogger().d(Fabric.TAG, "Could not get parcel from Google Play Service to capture AdvertisingId");
            } finally {
                reply.recycle();
                data.recycle();
            }
            return id;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean isLimitAdTrackingEnabled() throws android.os.RemoteException {
            /*
            r9 = this;
            r4 = 1;
            r5 = 0;
            r0 = android.os.Parcel.obtain();
            r3 = android.os.Parcel.obtain();
            r2 = 0;
            r6 = "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService";
            r0.writeInterfaceToken(r6);	 Catch:{ Exception -> 0x002e }
            r6 = 1;
            r0.writeInt(r6);	 Catch:{ Exception -> 0x002e }
            r6 = r9.binder;	 Catch:{ Exception -> 0x002e }
            r7 = 2;
            r8 = 0;
            r6.transact(r7, r0, r3, r8);	 Catch:{ Exception -> 0x002e }
            r3.readException();	 Catch:{ Exception -> 0x002e }
            r6 = r3.readInt();	 Catch:{ Exception -> 0x002e }
            if (r6 == 0) goto L_0x002c;
        L_0x0024:
            r2 = r4;
        L_0x0025:
            r3.recycle();
            r0.recycle();
        L_0x002b:
            return r2;
        L_0x002c:
            r2 = r5;
            goto L_0x0025;
        L_0x002e:
            r1 = move-exception;
            r4 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ all -> 0x0041 }
            r5 = "Fabric";
            r6 = "Could not get parcel from Google Play Service to capture Advertising limitAdTracking";
            r4.d(r5, r6);	 Catch:{ all -> 0x0041 }
            r3.recycle();
            r0.recycle();
            goto L_0x002b;
        L_0x0041:
            r4 = move-exception;
            r3.recycle();
            r0.recycle();
            throw r4;
            */
            throw new UnsupportedOperationException("Method not decompiled: io.fabric.sdk.android.services.common.AdvertisingInfoServiceStrategy.AdvertisingInterface.isLimitAdTrackingEnabled():boolean");
        }
    }

    public AdvertisingInfoServiceStrategy(Context context) {
        this.context = context.getApplicationContext();
    }

    public AdvertisingInfo getAdvertisingInfo() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Fabric.getLogger().d(Fabric.TAG, "AdvertisingInfoServiceStrategy cannot be called on the main thread");
            return null;
        }
        try {
            this.context.getPackageManager().getPackageInfo(GOOGLE_PLAY_SERVICE_PACKAGE_NAME, 0);
            AdvertisingConnection connection = new AdvertisingConnection();
            Intent intent = new Intent(GOOGLE_PLAY_SERVICES_INTENT);
            intent.setPackage(GOOGLE_PLAY_SERVICES_INTENT_PACKAGE_NAME);
            try {
                if (this.context.bindService(intent, connection, 1)) {
                    AdvertisingInterface adInterface = new AdvertisingInterface(connection.getBinder());
                    AdvertisingInfo advertisingInfo = new AdvertisingInfo(adInterface.getId(), adInterface.isLimitAdTrackingEnabled());
                    this.context.unbindService(connection);
                    return advertisingInfo;
                }
                Fabric.getLogger().d(Fabric.TAG, "Could not bind to Google Play Service to capture AdvertisingId");
                return null;
            } catch (Exception e) {
                Fabric.getLogger().w(Fabric.TAG, "Exception in binding to Google Play Service to capture AdvertisingId", e);
                this.context.unbindService(connection);
                return null;
            } catch (Throwable t) {
                Fabric.getLogger().d(Fabric.TAG, "Could not bind to Google Play Service to capture AdvertisingId", t);
                return null;
            }
        } catch (NameNotFoundException e2) {
            Fabric.getLogger().d(Fabric.TAG, "Unable to find Google Play Services package name");
            return null;
        } catch (Exception e3) {
            Fabric.getLogger().d(Fabric.TAG, "Unable to determine if Google Play Services is available", e3);
            return null;
        }
    }
}
