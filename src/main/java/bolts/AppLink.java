package bolts;

import android.net.Uri;
import java.util.Collections;
import java.util.List;

public class AppLink {
    private Uri sourceUrl;
    private List<Target> targets;
    private Uri webUrl;

    public static class Target {
        private final String appName;
        private final String className;
        private final String packageName;
        private final Uri url;

        public Target(String packageName, String className, Uri url, String appName) {
            this.packageName = packageName;
            this.className = className;
            this.url = url;
            this.appName = appName;
        }

        public Uri getUrl() {
            return this.url;
        }

        public String getAppName() {
            return this.appName;
        }

        public String getClassName() {
            return this.className;
        }

        public String getPackageName() {
            return this.packageName;
        }
    }

    public AppLink(Uri sourceUrl, List<Target> targets, Uri webUrl) {
        this.sourceUrl = sourceUrl;
        if (targets == null) {
            targets = Collections.emptyList();
        }
        this.targets = targets;
        this.webUrl = webUrl;
    }

    public Uri getSourceUrl() {
        return this.sourceUrl;
    }

    public List<Target> getTargets() {
        return Collections.unmodifiableList(this.targets);
    }

    public Uri getWebUrl() {
        return this.webUrl;
    }
}
