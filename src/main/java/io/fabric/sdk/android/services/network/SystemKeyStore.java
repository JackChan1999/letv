package io.fabric.sdk.android.services.network;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashMap;

class SystemKeyStore {
    private final HashMap<Principal, X509Certificate> trustRoots;
    final KeyStore trustStore;

    public SystemKeyStore(InputStream keystoreStream, String passwd) {
        KeyStore trustStore = getTrustStore(keystoreStream, passwd);
        this.trustRoots = initializeTrustedRoots(trustStore);
        this.trustStore = trustStore;
    }

    public boolean isTrustRoot(X509Certificate certificate) {
        X509Certificate trustRoot = (X509Certificate) this.trustRoots.get(certificate.getSubjectX500Principal());
        return trustRoot != null && trustRoot.getPublicKey().equals(certificate.getPublicKey());
    }

    public X509Certificate getTrustRootFor(X509Certificate certificate) {
        X509Certificate trustRoot = (X509Certificate) this.trustRoots.get(certificate.getIssuerX500Principal());
        if (trustRoot == null) {
            return null;
        }
        if (trustRoot.getSubjectX500Principal().equals(certificate.getSubjectX500Principal())) {
            return null;
        }
        try {
            certificate.verify(trustRoot.getPublicKey());
            return trustRoot;
        } catch (GeneralSecurityException e) {
            return null;
        }
    }

    private HashMap<Principal, X509Certificate> initializeTrustedRoots(KeyStore trustStore) {
        try {
            HashMap<Principal, X509Certificate> trusted = new HashMap();
            Enumeration<String> aliases = trustStore.aliases();
            while (aliases.hasMoreElements()) {
                X509Certificate cert = (X509Certificate) trustStore.getCertificate((String) aliases.nextElement());
                if (cert != null) {
                    trusted.put(cert.getSubjectX500Principal(), cert);
                }
            }
            return trusted;
        } catch (KeyStoreException e) {
            throw new AssertionError(e);
        }
    }

    private KeyStore getTrustStore(InputStream keystoreStream, String passwd) {
        BufferedInputStream bin;
        try {
            KeyStore trustStore = KeyStore.getInstance("BKS");
            bin = new BufferedInputStream(keystoreStream);
            trustStore.load(bin, passwd.toCharArray());
            bin.close();
            return trustStore;
        } catch (KeyStoreException kse) {
            throw new AssertionError(kse);
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        } catch (CertificateException e2) {
            throw new AssertionError(e2);
        } catch (IOException e3) {
            throw new AssertionError(e3);
        } catch (Throwable th) {
            bin.close();
        }
    }
}
