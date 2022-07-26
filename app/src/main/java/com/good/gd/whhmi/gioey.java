package com.good.gd.whhmi;

import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class gioey {
    private static final Class dbjc = gioey.class;

    public static boolean dbjc(String str, String str2, Class[] clsArr) {
        Class<?> cls;
        Method method = null;
        try {
            cls = Class.forName(str);
        } catch (ClassNotFoundException e) {
            com.good.gd.kloes.hbfhc.dbjc(dbjc, "Class " + str + " is not available.");
            cls = null;
        }
        if (cls == null) {
            return false;
        }
        try {
            Method method2 = cls.getMethod(str2, clsArr);
            if (method2 != null) {
                method = method2;
            }
        } catch (NoSuchMethodException e2) {
            com.good.gd.kloes.hbfhc.dbjc(dbjc, "Method " + str2 + " is not available.");
        } catch (SecurityException e3) {
            com.good.gd.kloes.hbfhc.qkduk(dbjc, "SecurityException Exception while invoking " + str2 + ". " + e3.getLocalizedMessage());
        }
        return method != null;
    }
}
