package com.good.gd.apachehttp.impl.auth;

import com.good.gd.apache.http.auth.AuthenticationException;

/* loaded from: classes.dex */
public class GDGSSException extends AuthenticationException {
    private static final long serialVersionUID = -4003752647920350244L;
    private Code major;
    private int minor;
    private String minorMessage;

    /* loaded from: classes.dex */
    public enum Code {
        BAD_BINDINGS(1),
        BAD_MECH(2),
        BAD_NAME(3),
        BAD_NAMETYPE(4),
        BAD_STATUS(5),
        BAD_MIC(6),
        CONTEXT_EXPIRED(7),
        CREDENTIALS_EXPIRED(8),
        DEFECTIVE_CREDENTIAL(9),
        DEFECTIVE_TOKEN(10),
        FAILURE(11),
        NO_CONTEXT(12),
        NO_CRED(13),
        BAD_QOP(14),
        UNAUTHORIZED(15),
        UNAVAILABLE(16),
        DUPLICATE_ELEMENT(17),
        NAME_NOT_MN(18),
        DUPLICATE_TOKEN(19),
        OLD_TOKEN(20),
        UNSEQ_TOKEN(21),
        GAP_TOKEN(22);
        
        private static String[] messages = {"Channel binding mismatch", "Unsupported mechanism requested", "Invalid name provided", "Name of unsupported type provided", "Invalid input status selector", "Token had invalid integrity check", "Specified security context expired", "Expired credentials detected", "Defective credential detected", "Defective token detected", "Failure unspecified at GSS-API level", "Security context init/accept not yet called or context deleted", "No valid credentials provided", "Unsupported QOP value", "Operation unauthorized", "Operation unavailable", "Duplicate credential element requested", "Name contains multi-mechanism elements", "The token was a duplicate of an earlier token", "The token's validity period has expired", "A later token has already been processed", "An expected per-message token was not received"};
        private final int err;

        Code(int i) {
            this.err = i;
        }

        public String getMsg() {
            return messages[this.err - 1];
        }
    }

    public GDGSSException(Code code) {
        this.minor = 0;
        this.minorMessage = null;
        this.major = code;
    }

    public Code getMajor() {
        return this.major;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        if (this.minor == 0) {
            return this.major.getMsg();
        }
        return this.major.getMsg() + " (Mechanism level: " + this.minorMessage + ")";
    }

    @Override // java.lang.Throwable
    public String toString() {
        return "GSSException: " + getMessage();
    }

    public GDGSSException(Code code, int i, String str) {
        this.minor = 0;
        this.minorMessage = null;
        this.major = code;
        this.minor = i;
        this.minorMessage = str;
    }
}
