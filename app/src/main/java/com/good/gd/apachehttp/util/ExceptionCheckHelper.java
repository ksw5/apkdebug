package com.good.gd.apachehttp.util;

/* loaded from: classes.dex */
public class ExceptionCheckHelper {
    private String predicate;
    private Throwable rootException;

    public ExceptionCheckHelper(String str) {
        this.predicate = str;
    }

    private Throwable getRootException(Throwable th) {
        while (th.getCause() != null && th.getCause() != th) {
            th = th.getCause();
        }
        return th;
    }

    private boolean hasEqualType(Class cls, Class cls2) {
        return cls.getName().equals(cls2.getName());
    }

    public Exception getExceptionCause() {
        return (Exception) this.rootException;
    }

    public boolean isCausedBy(Class cls, Exception exc) {
        Throwable th;
        Throwable th2 = null;
        boolean z = false;
        if (exc == null || cls == null) {
            th = null;
        } else {
            th = getRootException(exc);
            if (th.getMessage() != null && hasEqualType(th.getClass(), cls)) {
                z = th.getMessage().contains(this.predicate);
            }
        }
        if (z) {
            th2 = th;
        }
        this.rootException = th2;
        return z;
    }

    public void setPredicate(String str) {
        this.predicate = str;
    }
}
