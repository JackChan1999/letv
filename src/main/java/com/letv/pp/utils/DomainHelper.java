package com.letv.pp.utils;

import java.util.HashMap;
import java.util.List;

public class DomainHelper {
    private static final String G3_CN_DOMAIN_CIBNTV = "g3cn.cp21.ott.cibntv.net";
    private static final String G3_CN_DOMAIN_LETV = "g3.letv.cn";
    private static final String G3_CN_DOMAIN_VMOTERS = "g3cn.vmoters.com";
    private static final String G3_CN_DOMAIN_WASU = "g3cn.letv-epg.wasu.tv";
    private static final String G3_COM_DOMAIN_CIBNTV = "g3com.cp21.ott.cibntv.net";
    private static final String G3_COM_DOMAIN_LETV = "g3.letv.com";
    private static final String G3_COM_DOMAIN_VMOTERS = "g3com.vmoters.com";
    private static final String G3_COM_DOMAIN_WASU = "g3com.letv-epg.wasu.tv";
    private static final String KEY_ROOT_DOMAIN = "root_domain";
    private static final String KEY_TEST_REPORT = "test_report";
    private static final String KEY_TEST_REPORT_ADDRESS = "test_report_address";
    private static final String KEY_TEST_UPGRADE = "test_upgrade";
    private static final String KEY_TEST_UPGRADE_ADDRESS = "test_upgrade_address";
    private static final String KEY_USE_CIBN_DNS = "use_cibn_dns";
    private static final String KEY_USE_GUOGUANG_DNS = "use_guoguang_dns";
    private static final String KEY_USE_STRENGTHEN_VERSION = "use_strengthen_version";
    private static final String PULL_DOMAIN_CIBNTV = "so.cde.cp21.ott.cibntv.net";
    private static final String PULL_DOMAIN_LETV = "so.cde.letv.com";
    private static final String PULL_DOMAIN_STANDBY_BGP = "115.182.51.55";
    private static final String PULL_DOMAIN_STANDBY_CTCC = "220.181.155.179";
    private static final String PULL_DOMAIN_STANDBY_CUCC = "111.206.208.242";
    private static final String PULL_DOMAIN_VMOTERS = "so.cde.vmoters.com";
    private static final String PULL_DOMAIN_WASU = "so.cde.letv-epg.wasu.tv";
    private static final String REPORT_DOMAIN_CIBNTV = "s.webp2p.cp21.ott.cibntv.net";
    private static final String REPORT_DOMAIN_LETV = "s.webp2p.letv.com";
    private static final String REPORT_DOMAIN_TEST = "10.181.155.153:9999";
    private static final String REPORT_DOMAIN_VMOTERS = "s.webp2p.vmoters.com";
    private static final String REPORT_DOMAIN_WASU = "s.webp2p.letv-epg.wasu.tv";
    private static final String UPGRADE_DOMAIN_CIBNTV = "api.platform.cp21.ott.cibntv.net";
    private static final String UPGRADE_DOMAIN_LETV = "api.platform.letv.com";
    private static final String UPGRADE_DOMAIN_TEST = "test.push.platform.letv.com";
    private static final String UPGRADE_DOMAIN_VMOTERS = "api.platform.vmoters.com";
    private static final String UPGRADE_DOMAIN_WASU = "api.platform.letv-epg.wasu.tv";
    private static final String VALUE_1 = "1";
    private static final String VALUE_CIBN = "cibn";
    private static final String VALUE_VMOTERS = "vmoters";
    private static final String VALUE_WASU = "wasu";
    private static DomainHelper sSingleton;
    private String mG3CnDomain = G3_CN_DOMAIN_LETV;
    private String mG3ComDomain = G3_COM_DOMAIN_LETV;
    private boolean mIsTestReport;
    private boolean mIsTestUpgrade;
    private boolean mIsUseSv;
    private String mPullDomain = PULL_DOMAIN_LETV;
    private final List<String> mPullDomainList;
    private String mReportDomain = REPORT_DOMAIN_LETV;
    private String mUpgradeDomain = UPGRADE_DOMAIN_LETV;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private DomainHelper(java.util.HashMap<java.lang.String, java.lang.String> r7) {
        /*
        r6 = this;
        r5 = 1;
        r6.<init>();
        r3 = "s.webp2p.letv.com";
        r6.mReportDomain = r3;
        r3 = "api.platform.letv.com";
        r6.mUpgradeDomain = r3;
        r3 = "g3.letv.cn";
        r6.mG3CnDomain = r3;
        r3 = "g3.letv.com";
        r6.mG3ComDomain = r3;
        r3 = "so.cde.letv.com";
        r6.mPullDomain = r3;
        if (r7 == 0) goto L_0x00fc;
    L_0x001c:
        r3 = "use_guoguang_dns";
        r2 = r7.get(r3);
        r2 = (java.lang.String) r2;
        r3 = android.text.TextUtils.isEmpty(r2);
        if (r3 == 0) goto L_0x0034;
    L_0x002b:
        r3 = "use_cibn_dns";
        r2 = r7.get(r3);
        r2 = (java.lang.String) r2;
    L_0x0034:
        r3 = "1";
        r3 = r3.equalsIgnoreCase(r2);
        if (r3 != 0) goto L_0x004d;
    L_0x003c:
        r3 = "cibn";
        r4 = "root_domain";
        r2 = r7.get(r4);
        r2 = (java.lang.String) r2;
        r3 = r3.equalsIgnoreCase(r2);
        if (r3 == 0) goto L_0x0063;
    L_0x004d:
        r3 = "s.webp2p.cp21.ott.cibntv.net";
        r6.mReportDomain = r3;
        r3 = "api.platform.cp21.ott.cibntv.net";
        r6.mUpgradeDomain = r3;
        r3 = "g3cn.cp21.ott.cibntv.net";
        r6.mG3CnDomain = r3;
        r3 = "g3com.cp21.ott.cibntv.net";
        r6.mG3ComDomain = r3;
        r3 = "so.cde.cp21.ott.cibntv.net";
        r6.mPullDomain = r3;
    L_0x0063:
        r3 = "vmoters";
        r3 = r3.equalsIgnoreCase(r2);
        if (r3 == 0) goto L_0x0082;
    L_0x006c:
        r3 = "s.webp2p.vmoters.com";
        r6.mReportDomain = r3;
        r3 = "api.platform.vmoters.com";
        r6.mUpgradeDomain = r3;
        r3 = "g3cn.vmoters.com";
        r6.mG3CnDomain = r3;
        r3 = "g3com.vmoters.com";
        r6.mG3ComDomain = r3;
        r3 = "so.cde.vmoters.com";
        r6.mPullDomain = r3;
    L_0x0082:
        r3 = "wasu";
        r3 = r3.equalsIgnoreCase(r2);
        if (r3 == 0) goto L_0x00a1;
    L_0x008b:
        r3 = "s.webp2p.letv-epg.wasu.tv";
        r6.mReportDomain = r3;
        r3 = "api.platform.letv-epg.wasu.tv";
        r6.mUpgradeDomain = r3;
        r3 = "g3cn.letv-epg.wasu.tv";
        r6.mG3CnDomain = r3;
        r3 = "g3com.letv-epg.wasu.tv";
        r6.mG3ComDomain = r3;
        r3 = "so.cde.letv-epg.wasu.tv";
        r6.mPullDomain = r3;
    L_0x00a1:
        r4 = "1";
        r3 = "test_report";
        r3 = r7.get(r3);
        r3 = (java.lang.String) r3;
        r3 = r4.equalsIgnoreCase(r3);
        if (r3 == 0) goto L_0x00c5;
    L_0x00b2:
        r6.mIsTestReport = r5;
        r3 = "test_report_address";
        r0 = r7.get(r3);
        r0 = (java.lang.String) r0;
        r3 = android.text.TextUtils.isEmpty(r0);
        if (r3 != 0) goto L_0x0120;
    L_0x00c3:
        r6.mReportDomain = r0;
    L_0x00c5:
        r4 = "1";
        r3 = "test_upgrade";
        r3 = r7.get(r3);
        r3 = (java.lang.String) r3;
        r3 = r4.equalsIgnoreCase(r3);
        if (r3 == 0) goto L_0x00e9;
    L_0x00d6:
        r6.mIsTestUpgrade = r5;
        r3 = "test_upgrade_address";
        r1 = r7.get(r3);
        r1 = (java.lang.String) r1;
        r3 = android.text.TextUtils.isEmpty(r1);
        if (r3 != 0) goto L_0x0123;
    L_0x00e7:
        r6.mUpgradeDomain = r1;
    L_0x00e9:
        r4 = "1";
        r3 = "use_strengthen_version";
        r3 = r7.get(r3);
        r3 = (java.lang.String) r3;
        r3 = r4.equalsIgnoreCase(r3);
        if (r3 == 0) goto L_0x00fc;
    L_0x00fa:
        r6.mIsUseSv = r5;
    L_0x00fc:
        r3 = new java.util.ArrayList;
        r3.<init>();
        r6.mPullDomainList = r3;
        r3 = r6.mPullDomainList;
        r4 = r6.mPullDomain;
        r3.add(r4);
        r3 = r6.mPullDomainList;
        r4 = "115.182.51.55";
        r3.add(r4);
        r3 = r6.mPullDomainList;
        r4 = "111.206.208.242";
        r3.add(r4);
        r3 = r6.mPullDomainList;
        r4 = "220.181.155.179";
        r3.add(r4);
        return;
    L_0x0120:
        r0 = "10.181.155.153:9999";
        goto L_0x00c3;
    L_0x0123:
        r1 = "test.push.platform.letv.com";
        goto L_0x00e7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.pp.utils.DomainHelper.<init>(java.util.HashMap):void");
    }

    public static void init(HashMap<String, String> paramMap) {
        if (sSingleton == null) {
            synchronized (DomainHelper.class) {
                if (sSingleton == null) {
                    sSingleton = new DomainHelper(paramMap);
                }
            }
        }
    }

    public static DomainHelper getInstance() {
        if (sSingleton != null) {
            return sSingleton;
        }
        throw new IllegalStateException("Not initialized");
    }

    public boolean isTestReport() {
        return this.mIsTestReport;
    }

    public boolean isTestUpgrade() {
        return this.mIsTestUpgrade;
    }

    public boolean isUseSv() {
        return this.mIsUseSv;
    }

    public String getReportUrl() {
        return String.format("http://%s/act/pf?", new Object[]{this.mReportDomain});
    }

    public String getUpgradeUrl() {
        return String.format("http://%s/upgrade?", new Object[]{this.mUpgradeDomain});
    }

    public String getPullDomain() {
        return this.mPullDomain;
    }

    public int getPullDomainListSize() {
        return this.mPullDomainList.size();
    }

    public String getPullUrl(String libraryVersion, String serialNumber) {
        if (this.mIsUseSv) {
            String str = "http://%s/%s/%s/libcde-%s%s.gz.sv";
            Object[] objArr = new Object[5];
            objArr[0] = this.mPullDomain;
            objArr[1] = libraryVersion;
            objArr[2] = CpuUtils.getCpuType();
            objArr[3] = libraryVersion;
            objArr[4] = StringUtils.isEmpty(serialNumber) ? "" : new StringBuilder(NetworkUtils.DELIMITER_LINE).append(serialNumber).toString();
            return String.format(str, objArr);
        }
        str = "http://%s/%s/%s/libcde-%s%s.gz";
        objArr = new Object[5];
        objArr[0] = this.mPullDomain;
        objArr[1] = libraryVersion;
        objArr[2] = CpuUtils.getCpuType();
        objArr[3] = libraryVersion;
        objArr[4] = StringUtils.isEmpty(serialNumber) ? "" : new StringBuilder(NetworkUtils.DELIMITER_LINE).append(serialNumber).toString();
        return String.format(str, objArr);
    }

    public String replaceUrlForPull(String url, int num) {
        return (StringUtils.isEmpty(url) || num <= 0) ? url : url.replace((CharSequence) this.mPullDomainList.get((num - 1) % this.mPullDomainList.size()), (CharSequence) this.mPullDomainList.get(num % this.mPullDomainList.size()));
    }

    public String replaceUrlForG3(String url) {
        return (StringUtils.isEmpty(url) || G3_CN_DOMAIN_LETV.equals(this.mG3CnDomain) || G3_COM_DOMAIN_LETV.equals(this.mG3CnDomain)) ? url : url.replace(G3_CN_DOMAIN_LETV, this.mG3CnDomain).replace(G3_COM_DOMAIN_LETV, this.mG3ComDomain);
    }
}
