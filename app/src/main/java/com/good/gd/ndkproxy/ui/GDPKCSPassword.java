package com.good.gd.ndkproxy.ui;

import com.good.gd.messages.PKCSPasswordMsg;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.data.CertificateImportUI;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import com.good.gd.ndkproxy.ui.event.BBDCertImportUpdateEvent;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class GDPKCSPassword {
    private static GDPKCSPassword _instance;

    /* loaded from: classes.dex */
    public static class KeyStoreRequestObject {
        String alias;
        String host;
        String[] issuers;
        String[] keyTypes;
        int port;

        public String getAlias() {
            return this.alias;
        }

        public String getHost() {
            return this.host;
        }

        public String[] getIssuers() {
            return this.issuers;
        }

        public String[] getKeyTypes() {
            return this.keyTypes;
        }

        public int getPort() {
            return this.port;
        }

        private KeyStoreRequestObject(String[] strArr, String[] strArr2, String str, int i, String str2) {
            this.issuers = strArr;
            this.keyTypes = strArr2;
            this.host = str;
            this.port = i;
            this.alias = str2;
        }
    }

    /* loaded from: classes.dex */
    public enum StateType {
        OtpWithoutErrors,
        Pkcs12,
        OtpPasswordIncorrect,
        GcError,
        DeviceKeyStore;
        
        private static final int TYPE_DEVICE_KEYSTORE = 4;
        private static final int TYPE_GC_ERROR = 3;
        private static final int TYPE_OTP_PASSWORD_INCORRECT = 2;
        private static final int TYPE_OTP_WITHOUT_ERRORS = 0;
        private static final int TYPE_PKCS12 = 1;

        public static StateType fromNativeInteger(int i) {
            switch (i) {
                case 0:
                    return OtpWithoutErrors;
                case 1:
                    return Pkcs12;
                case 2:
                    return OtpPasswordIncorrect;
                case 3:
                    return GcError;
                case 4:
                    return DeviceKeyStore;
                default:
                    return null;
            }
        }
    }

    public static synchronized GDPKCSPassword getInstance() {
        GDPKCSPassword gDPKCSPassword;
        synchronized (GDPKCSPassword.class) {
            if (_instance == null) {
                _instance = new GDPKCSPassword();
                ndkInit();
            }
            gDPKCSPassword = _instance;
        }
        return gDPKCSPassword;
    }

    public static KeyStoreRequestObject getKeyStoreObject(String str) {
        KeyStoreRequestObject profileKSObject = getProfileKSObject(str);
        if (profileKSObject == null) {
            GDLog.DBGPRINTF(12, "Filed to get profile KeyStoreRequestObject, not expecting..");
            return new KeyStoreRequestObject(new String[0], new String[0], "", -1, "");
        }
        GDLog.DBGPRINTF(16, "issuers: " + Arrays.toString(profileKSObject.issuers) + ", keyTypes: " + Arrays.toString(profileKSObject.keyTypes) + ", host:" + profileKSObject.host + ", port:" + profileKSObject.port + ", alias:" + profileKSObject.alias);
        return profileKSObject;
    }

    private static native KeyStoreRequestObject getProfileKSObject(String str);

    public static boolean hasProfileWithAlias(String str) {
        return hasProfileWithCertAlias(str);
    }

    private static native boolean hasProfileWithCertAlias(String str);

    private static native void ndkInit();

    private static void refresh(long j) {
        BBDUIObject uIData = BBDUIDataStore.getInstance().getUIData(j);
        if (uIData != null) {
            GDLog.DBGPRINTF(16, "GDPKCSPassword::refresh() IN\n");
            BBDCertImportUpdateEvent bBDCertImportUpdateEvent = new BBDCertImportUpdateEvent();
            bBDCertImportUpdateEvent.setInstructionType(CertificateImportUI.PKCSInstructionEnum.REFRESH);
            BBDUIEventManager.sendUpdateEvent(bBDCertImportUpdateEvent, uIData);
        }
    }

    private native void setPassword(long j, String str);

    private native void skipCertificateSetup(long j);

    private static void update(int i, String str, String str2, String str3, boolean z, boolean z2, long j) {
        BBDUIObject uIData = BBDUIDataStore.getInstance().getUIData(j);
        if (uIData != null) {
            GDLog.DBGPRINTF(16, "GDPKCSPassword::update() IN\n");
            BBDCertImportUpdateEvent bBDCertImportUpdateEvent = new BBDCertImportUpdateEvent();
            bBDCertImportUpdateEvent.setInstructionType(CertificateImportUI.PKCSInstructionEnum.RESPONSE_FOR_GET_NEW_REQUEST);
            bBDCertImportUpdateEvent.setStateType(StateType.fromNativeInteger(i));
            bBDCertImportUpdateEvent.setUuid(str);
            bBDCertImportUpdateEvent.setLabel(str2);
            bBDCertImportUpdateEvent.setsMessage(str3);
            bBDCertImportUpdateEvent.setRequirePassword(z);
            bBDCertImportUpdateEvent.setRequireCert(z2);
            BBDUIEventManager.sendUpdateEvent(bBDCertImportUpdateEvent, uIData);
        }
    }

    public void handlePKCSPasswordMsgRequest(long j, PKCSPasswordMsg pKCSPasswordMsg) {
        setPassword(j, pKCSPasswordMsg.password);
    }

    public void skipPKCSPasswordMsgRequest(long j) {
        skipCertificateSetup(j);
    }
}
