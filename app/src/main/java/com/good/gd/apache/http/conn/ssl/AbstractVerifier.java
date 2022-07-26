package com.good.gd.apache.http.conn.ssl;

import com.good.gd.apache.http.conn.util.InetAddressUtils;
import java.io.IOException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import kotlin.text.Typography;

/* loaded from: classes.dex */
public abstract class AbstractVerifier implements X509HostnameVerifier {
    private static final String[] BAD_COUNTRY_2LDS;

    static {
        String[] strArr = {"ac", "co", "com", "ed", "edu", "go", "gouv", "gov", "info", "lg", "ne", "net", "or", "org"};
        BAD_COUNTRY_2LDS = strArr;
        Arrays.sort(strArr);
    }

    public static boolean acceptableCountryWildcard(String str) {
        int length = str.length();
        if (length < 7 || length > 9) {
            return true;
        }
        int i = length - 3;
        if (str.charAt(i) != '.') {
            return true;
        }
        return Arrays.binarySearch(BAD_COUNTRY_2LDS, str.substring(2, i)) < 0;
    }

    public static int countDots(String str) {
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (str.charAt(i2) == '.') {
                i++;
            }
        }
        return i;
    }

    public static String[] getCNs(X509Certificate x509Certificate) {
        return null;
    }

    public static String[] getDNSSubjectAlts(X509Certificate x509Certificate) {
        Collection<List<?>> collection;
        LinkedList linkedList = new LinkedList();
        try {
            collection = x509Certificate.getSubjectAlternativeNames();
        } catch (CertificateParsingException e) {
            Logger.getLogger(AbstractVerifier.class.getName()).log(Level.FINE, "Error parsing certificate.", (Throwable) e);
            collection = null;
        }
        if (collection != null) {
            for (List<?> list : collection) {
                if (((Integer) list.get(0)).intValue() == 2) {
                    linkedList.add((String) list.get(1));
                }
            }
        }
        if (linkedList.isEmpty()) {
            return null;
        }
        String[] strArr = new String[linkedList.size()];
        linkedList.toArray(strArr);
        return strArr;
    }

    private final boolean verifyImpl(String str, String[] strArr, String[] strArr2, boolean z) throws SSLException {
        LinkedList linkedList = new LinkedList();
        if (strArr != null && strArr.length > 0 && strArr[0] != null) {
            linkedList.add(strArr[0]);
        }
        if (strArr2 != null) {
            for (String str2 : strArr2) {
                if (str2 != null) {
                    linkedList.add(str2);
                }
            }
        }
        if (!linkedList.isEmpty()) {
            StringBuffer stringBuffer = new StringBuffer();
            String lowerCase = str.trim().toLowerCase(Locale.ENGLISH);
            Iterator it = linkedList.iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                String lowerCase2 = ((String) it.next()).toLowerCase(Locale.ENGLISH);
                stringBuffer.append(" <");
                stringBuffer.append(lowerCase2);
                stringBuffer.append(Typography.greater);
                if (it.hasNext()) {
                    stringBuffer.append(" OR");
                }
                if (lowerCase2.startsWith("*.") && lowerCase2.indexOf(46, 2) != -1 && acceptableCountryWildcard(lowerCase2) && !InetAddressUtils.isIPv4Address(str)) {
                    boolean endsWith = lowerCase.endsWith(lowerCase2.substring(1));
                    if (!endsWith || !z) {
                        z2 = endsWith;
                        continue;
                    } else if (countDots(lowerCase) == countDots(lowerCase2)) {
                        z2 = true;
                        continue;
                    } else {
                        z2 = false;
                        continue;
                    }
                } else {
                    z2 = lowerCase.equals(lowerCase2);
                    continue;
                }
                if (z2) {
                    break;
                }
            }
            if (!z2) {
                throw new SSLException("hostname in certificate didn't match: <" + str + "> !=" + ((Object) stringBuffer));
            }
            return true;
        }
        throw new SSLException("Certificate for <" + str + "> doesn't contain CN or DNS subjectAlt");
    }

    @Override // com.good.gd.apache.http.conn.ssl.X509HostnameVerifier
    public final void verify(String str, SSLSocket sSLSocket) throws IOException {
        if (str != null) {
            verify(str, (X509Certificate) sSLSocket.getSession().getPeerCertificates()[0]);
            return;
        }
        throw new NullPointerException("host to verify is null");
    }

    @Override // com.good.gd.apache.http.conn.ssl.X509HostnameVerifier, javax.net.ssl.HostnameVerifier
    public final boolean verify(String str, SSLSession sSLSession) {
        try {
            verify(str, (X509Certificate) sSLSession.getPeerCertificates()[0]);
            return true;
        } catch (SSLException e) {
            return false;
        }
    }

    @Override // com.good.gd.apache.http.conn.ssl.X509HostnameVerifier
    public final void verify(String str, X509Certificate x509Certificate) throws SSLException {
        verify(str, getCNs(x509Certificate), getDNSSubjectAlts(x509Certificate));
    }

    public final void verify(String str, String[] strArr, String[] strArr2, boolean z) throws SSLException {
        if (verifyImpl(str, strArr, strArr2, z)) {
            return;
        }
        throw new SSLException("Verification failed");
    }
}
