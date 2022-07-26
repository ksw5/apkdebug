package com.good.gd.pki;

/* loaded from: classes.dex */
public class CredentialException extends Exception {
    private Code code;

    /* loaded from: classes.dex */
    public enum Code {
        GDSuccess(0),
        GDErrorOutOfMemory(-1),
        GDErrorNotAuthorized(-2),
        GDErrorNotFound(-3),
        GDErrorNotMapped(-4),
        GDErrorWrongPassword(-5),
        GDErrorGeneral(-6),
        GDErrorInvalidArgument(-7),
        GDErrorNotAllowed(-8);
        
        private static String[] messages = {"Success", "Out of memory", "Not authorized", "Not found", "Not mapped to a User Credential Profile", "Wrong password", "General error", "Invalid argument", "Not allowed"};
        private final int err;

        Code(int i) {
            this.err = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getMsg() {
            return messages[Math.abs(this.err)];
        }

        public int errValue() {
            return this.err;
        }
    }

    public CredentialException(Code code) {
        this.code = code;
    }

    public Code getErrorCode() {
        return this.code;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return "CredentialException: " + this.code.getMsg();
    }
}
