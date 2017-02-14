package io.fabric.sdk.android.services.network;

import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedList;

final class CertificateChainCleaner {
    private CertificateChainCleaner() {
    }

    public static X509Certificate[] getCleanChain(X509Certificate[] chain, SystemKeyStore systemKeyStore) throws CertificateException {
        LinkedList<X509Certificate> cleanChain = new LinkedList();
        boolean trustedChain = false;
        if (systemKeyStore.isTrustRoot(chain[0])) {
            trustedChain = true;
        }
        cleanChain.add(chain[0]);
        int i = 1;
        while (i < chain.length) {
            if (systemKeyStore.isTrustRoot(chain[i])) {
                trustedChain = true;
            }
            if (!isValidLink(chain[i], chain[i - 1])) {
                break;
            }
            cleanChain.add(chain[i]);
            i++;
        }
        X509Certificate trustRoot = systemKeyStore.getTrustRootFor(chain[i - 1]);
        if (trustRoot != null) {
            cleanChain.add(trustRoot);
            trustedChain = true;
        }
        if (trustedChain) {
            return (X509Certificate[]) cleanChain.toArray(new X509Certificate[cleanChain.size()]);
        }
        throw new CertificateException("Didn't find a trust anchor in chain cleanup!");
    }

    private static boolean isValidLink(X509Certificate parent, X509Certificate child) {
        if (!parent.getSubjectX500Principal().equals(child.getIssuerX500Principal())) {
            return false;
        }
        try {
            child.verify(parent.getPublicKey());
            return true;
        } catch (GeneralSecurityException e) {
            return false;
        }
    }
}
