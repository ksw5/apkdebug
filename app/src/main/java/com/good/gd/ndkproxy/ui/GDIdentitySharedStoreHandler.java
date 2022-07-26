package com.good.gd.ndkproxy.ui;

import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.event.BBDCertSharingUpdateEvent;
import com.good.gd.ndkproxy.ui.event.BBDUIEventManager;

/* loaded from: classes.dex */
public final class GDIdentitySharedStoreHandler {
    private static GDIdentitySharedStoreHandler _instance;

    /* loaded from: classes.dex */
    public enum IMPORT_SHARING_RESULT {
        StatusOpened,
        StatusConnecting,
        StatusConnected,
        StatusConfirmation,
        StatusSuccess,
        StatusMissingCerts,
        StatusNotInstalled,
        StatusNotSupported,
        StatusLocked,
        StatusWrongUser,
        StatusWillClose,
        StatusUnknownErr;
        
        private static final int CONFIRMATION = 3;
        private static final int CONNECTED = 2;
        private static final int CONNECTING = 1;
        private static final int LOCKED = 8;
        private static final int MISSING_CERTIFICATES = 5;
        private static final int NOT_INSTALLED = 6;
        private static final int NOT_SUPPORTED = 7;
        private static final int OPENED = 0;
        private static final int SUCCESS = 4;
        private static final int UNKNOWN_ERROR = 10;
        private static final int WILL_CLOSE = 11;
        private static final int WRONG_USER = 9;

        public static IMPORT_SHARING_RESULT fromNativeInteger(int i) {
            switch (i) {
                case 0:
                    return StatusOpened;
                case 1:
                    return StatusConnecting;
                case 2:
                    return StatusConnected;
                case 3:
                    return StatusConfirmation;
                case 4:
                    return StatusSuccess;
                case 5:
                    return StatusMissingCerts;
                case 6:
                    return StatusNotInstalled;
                case 7:
                    return StatusNotSupported;
                case 8:
                    return StatusLocked;
                case 9:
                    return StatusWrongUser;
                case 10:
                default:
                    return StatusUnknownErr;
                case 11:
                    return StatusWillClose;
            }
        }
    }

    /* loaded from: classes.dex */
    static /* synthetic */ class hbfhc {
        static final /* synthetic */ int[] dbjc;

        static {
            int[] iArr = new int[IMPORT_SHARING_RESULT.values().length];
            dbjc = iArr;
            try {
                IMPORT_SHARING_RESULT import_sharing_result = IMPORT_SHARING_RESULT.StatusWillClose;
                iArr[10] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                int[] iArr2 = dbjc;
                IMPORT_SHARING_RESULT import_sharing_result2 = IMPORT_SHARING_RESULT.StatusSuccess;
                iArr2[4] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                int[] iArr3 = dbjc;
                IMPORT_SHARING_RESULT import_sharing_result3 = IMPORT_SHARING_RESULT.StatusConnecting;
                iArr3[1] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                int[] iArr4 = dbjc;
                IMPORT_SHARING_RESULT import_sharing_result4 = IMPORT_SHARING_RESULT.StatusConnected;
                iArr4[2] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                int[] iArr5 = dbjc;
                IMPORT_SHARING_RESULT import_sharing_result5 = IMPORT_SHARING_RESULT.StatusConfirmation;
                iArr5[3] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                int[] iArr6 = dbjc;
                IMPORT_SHARING_RESULT import_sharing_result6 = IMPORT_SHARING_RESULT.StatusMissingCerts;
                iArr6[5] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                int[] iArr7 = dbjc;
                IMPORT_SHARING_RESULT import_sharing_result7 = IMPORT_SHARING_RESULT.StatusLocked;
                iArr7[8] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                int[] iArr8 = dbjc;
                IMPORT_SHARING_RESULT import_sharing_result8 = IMPORT_SHARING_RESULT.StatusNotInstalled;
                iArr8[6] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                int[] iArr9 = dbjc;
                IMPORT_SHARING_RESULT import_sharing_result9 = IMPORT_SHARING_RESULT.StatusNotSupported;
                iArr9[7] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                int[] iArr10 = dbjc;
                IMPORT_SHARING_RESULT import_sharing_result10 = IMPORT_SHARING_RESULT.StatusOpened;
                iArr10[0] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                int[] iArr11 = dbjc;
                IMPORT_SHARING_RESULT import_sharing_result11 = IMPORT_SHARING_RESULT.StatusUnknownErr;
                iArr11[11] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                int[] iArr12 = dbjc;
                IMPORT_SHARING_RESULT import_sharing_result12 = IMPORT_SHARING_RESULT.StatusWrongUser;
                iArr12[9] = 12;
            } catch (NoSuchFieldError e12) {
            }
        }
    }

    public static synchronized GDIdentitySharedStoreHandler getInstance() {
        GDIdentitySharedStoreHandler gDIdentitySharedStoreHandler;
        synchronized (GDIdentitySharedStoreHandler.class) {
            if (_instance == null) {
                _instance = new GDIdentitySharedStoreHandler();
                initialize();
            }
            gDIdentitySharedStoreHandler = _instance;
        }
        return gDIdentitySharedStoreHandler;
    }

    private static void handleImportSharingResult(int i) {
        IMPORT_SHARING_RESULT fromNativeInteger = IMPORT_SHARING_RESULT.fromNativeInteger(i);
        GDLog.DBGPRINTF(16, "Import Sharing Result State " + fromNativeInteger + "\n");
        int i2 = hbfhc.dbjc[fromNativeInteger.ordinal()];
        BBDUIEventManager.sendUpdateEvent(new BBDCertSharingUpdateEvent(fromNativeInteger), BBDUIDataStore.getInstance().getUIData(BBUIType.UI_CERTIFICATESHARING));
    }

    private static native void initialize();

    public native boolean allowCancellation();

    public native void cancelImport();

    public native void performNextAction();

    public native void registerCallback();

    public native void unregisterCallback();
}
