package com.good.gd.ndkproxy;

/* loaded from: classes.dex */
public class GDAppResultCodeNDK {
    public static final int GDErrorActivationFailed = -101;
    public static final int GDErrorAppDenied = -104;
    public static final int GDErrorAppVersionNotEntitled = -105;
    public static final int GDErrorBlocked = -301;
    public static final int GDErrorIdleLockout = -300;
    public static final int GDErrorNone = 0;
    public static final int GDErrorPasswordChangeRequired = -304;
    public static final int GDErrorProgrammaticActivationAttestationFailed = -605;
    public static final int GDErrorProgrammaticActivationCredentialsFailed = -602;
    public static final int GDErrorProgrammaticActivationNoNetwork = -601;
    public static final int GDErrorProgrammaticActivationServerCommsFailed = -603;
    public static final int GDErrorProgrammaticActivationUnknown = -600;
    public static final int GDErrorProvisioningFailed = -102;
    public static final int GDErrorPushConnectionTimeout = -103;
    public static final int GDErrorRemoteLockout = -303;
    public static final int GDErrorSecurityError = -100;
    public static final int GDErrorWiped = -302;

    public static native void initialize();

    public static String stringFor(int i) {
        if (i != -605) {
            if (i == 0) {
                return "GDErrorNone";
            }
            switch (i) {
                case GDErrorProgrammaticActivationServerCommsFailed /* -603 */:
                    return "GDErrorProgrammaticActivationServerCommsFailed";
                case GDErrorProgrammaticActivationCredentialsFailed /* -602 */:
                    return "GDErrorProgrammaticActivationCredentialsFailed";
                case GDErrorProgrammaticActivationNoNetwork /* -601 */:
                    return "GDErrorProgrammaticActivationNoNetwork";
                case GDErrorProgrammaticActivationUnknown /* -600 */:
                    return "GDErrorProgrammaticActivationUnknown";
                default:
                    switch (i) {
                        case GDErrorPasswordChangeRequired /* -304 */:
                            return "GDErrorPasswordChangeRequired";
                        case GDErrorRemoteLockout /* -303 */:
                            return "GDErrorRemoteLockout";
                        case GDErrorWiped /* -302 */:
                            return "GDErrorWiped";
                        case GDErrorBlocked /* -301 */:
                            return "GDErrorBlocked";
                        case GDErrorIdleLockout /* -300 */:
                            return "GDErrorIdleLockout";
                        default:
                            switch (i) {
                                case GDErrorAppVersionNotEntitled /* -105 */:
                                    return "GDErrorAppVersionNotEntitled";
                                case GDErrorAppDenied /* -104 */:
                                    return "GDErrorAppDenied";
                                case GDErrorPushConnectionTimeout /* -103 */:
                                    return "GDErrorPushConnectionTimeout";
                                case GDErrorProvisioningFailed /* -102 */:
                                    return "GDErrorProvisioningFailed";
                                case GDErrorActivationFailed /* -101 */:
                                    return "GDErrorActivationFailed";
                                case -100:
                                    return "GDErrorSecurityError";
                                default:
                                    return "Unknown Result Code";
                            }
                    }
            }
        }
        return "GDErrorProgrammaticActivationAttestationFailed";
    }
}
