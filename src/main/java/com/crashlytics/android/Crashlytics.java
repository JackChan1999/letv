package com.crashlytics.android;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.beta.Beta;
import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.core.CrashlyticsListener;
import com.crashlytics.android.core.PinningInfoProvider;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.KitGroup;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Crashlytics extends Kit<Void> implements KitGroup {
    public static final String TAG = "Crashlytics";
    public final Answers answers;
    public final Beta beta;
    public final CrashlyticsCore core;
    public final Collection<? extends Kit> kits;

    public static class Builder {
        private Answers answers;
        private Beta beta;
        private CrashlyticsCore core;
        private com.crashlytics.android.core.CrashlyticsCore.Builder coreBuilder;

        @Deprecated
        public Builder delay(float delay) {
            getCoreBuilder().delay(delay);
            return this;
        }

        @Deprecated
        public Builder listener(CrashlyticsListener listener) {
            getCoreBuilder().listener(listener);
            return this;
        }

        @Deprecated
        public Builder pinningInfo(PinningInfoProvider pinningInfoProvider) {
            getCoreBuilder().pinningInfo(pinningInfoProvider);
            return this;
        }

        @Deprecated
        public Builder disabled(boolean isDisabled) {
            getCoreBuilder().disabled(isDisabled);
            return this;
        }

        public Builder answers(Answers answers) {
            if (answers == null) {
                throw new NullPointerException("Answers Kit must not be null.");
            } else if (this.answers != null) {
                throw new IllegalStateException("Answers Kit already set.");
            } else {
                this.answers = answers;
                return this;
            }
        }

        public Builder beta(Beta beta) {
            if (beta == null) {
                throw new NullPointerException("Beta Kit must not be null.");
            } else if (this.beta != null) {
                throw new IllegalStateException("Beta Kit already set.");
            } else {
                this.beta = beta;
                return this;
            }
        }

        public Builder core(CrashlyticsCore core) {
            if (core == null) {
                throw new NullPointerException("CrashlyticsCore Kit must not be null.");
            } else if (this.core != null) {
                throw new IllegalStateException("CrashlyticsCore Kit already set.");
            } else {
                this.core = core;
                return this;
            }
        }

        public Crashlytics build() {
            if (this.coreBuilder != null) {
                if (this.core != null) {
                    throw new IllegalStateException("Must not use Deprecated methods delay(), disabled(), listener(), pinningInfoProvider() with core()");
                }
                this.core = this.coreBuilder.build();
            }
            if (this.answers == null) {
                this.answers = new Answers();
            }
            if (this.beta == null) {
                this.beta = new Beta();
            }
            if (this.core == null) {
                this.core = new CrashlyticsCore();
            }
            return new Crashlytics(this.answers, this.beta, this.core);
        }

        private synchronized com.crashlytics.android.core.CrashlyticsCore.Builder getCoreBuilder() {
            if (this.coreBuilder == null) {
                this.coreBuilder = new com.crashlytics.android.core.CrashlyticsCore.Builder();
            }
            return this.coreBuilder;
        }
    }

    public Crashlytics() {
        this(new Answers(), new Beta(), new CrashlyticsCore());
    }

    Crashlytics(Answers answers, Beta beta, CrashlyticsCore core) {
        this.answers = answers;
        this.beta = beta;
        this.core = core;
        this.kits = Collections.unmodifiableCollection(Arrays.asList(new Kit[]{answers, beta, core}));
    }

    public String getVersion() {
        return "2.5.5.97";
    }

    public String getIdentifier() {
        return "com.crashlytics.sdk.android:crashlytics";
    }

    public Collection<? extends Kit> getKits() {
        return this.kits;
    }

    protected Void doInBackground() {
        return null;
    }

    public static Crashlytics getInstance() {
        return (Crashlytics) Fabric.getKit(Crashlytics.class);
    }

    public static PinningInfoProvider getPinningInfoProvider() {
        checkInitialized();
        return getInstance().core.getPinningInfoProvider();
    }

    public static void logException(Throwable throwable) {
        checkInitialized();
        getInstance().core.logException(throwable);
    }

    public static void log(String msg) {
        checkInitialized();
        getInstance().core.log(msg);
    }

    public static void log(int priority, String tag, String msg) {
        checkInitialized();
        getInstance().core.log(priority, tag, msg);
    }

    public static void setUserIdentifier(String identifier) {
        checkInitialized();
        getInstance().core.setUserIdentifier(identifier);
    }

    public static void setUserName(String name) {
        checkInitialized();
        getInstance().core.setUserName(name);
    }

    public static void setUserEmail(String email) {
        checkInitialized();
        getInstance().core.setUserEmail(email);
    }

    public static void setString(String key, String value) {
        checkInitialized();
        getInstance().core.setString(key, value);
    }

    public static void setBool(String key, boolean value) {
        checkInitialized();
        getInstance().core.setBool(key, value);
    }

    public static void setDouble(String key, double value) {
        checkInitialized();
        getInstance().core.setDouble(key, value);
    }

    public static void setFloat(String key, float value) {
        checkInitialized();
        getInstance().core.setFloat(key, value);
    }

    public static void setInt(String key, int value) {
        checkInitialized();
        getInstance().core.setInt(key, value);
    }

    public static void setLong(String key, long value) {
        checkInitialized();
        getInstance().core.setLong(key, value);
    }

    public void crash() {
        this.core.crash();
    }

    public boolean verifyPinning(URL url) {
        return this.core.verifyPinning(url);
    }

    @Deprecated
    public synchronized void setListener(CrashlyticsListener listener) {
        this.core.setListener(listener);
    }

    @Deprecated
    public void setDebugMode(boolean debug) {
        Fabric.getLogger().w(TAG, "Use of Crashlytics.setDebugMode is deprecated.");
    }

    @Deprecated
    public boolean getDebugMode() {
        Fabric.getLogger().w(TAG, "Use of Crashlytics.getDebugMode is deprecated.");
        getFabric();
        return Fabric.isDebuggable();
    }

    @Deprecated
    public static void setPinningInfoProvider(PinningInfoProvider pinningInfo) {
        Fabric.getLogger().w(TAG, "Use of Crashlytics.setPinningInfoProvider is deprecated");
    }

    private static void checkInitialized() {
        if (getInstance() == null) {
            throw new IllegalStateException("Crashlytics must be initialized by calling Fabric.with(Context) prior to calling Crashlytics.getInstance()");
        }
    }
}
