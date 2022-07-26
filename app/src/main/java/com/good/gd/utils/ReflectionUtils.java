package com.good.gd.utils;

import android.os.Build;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class ReflectionUtils {

    /* loaded from: classes.dex */
    public static class ClassAccessException extends Exception {
        static final long serialVersionUID = 42983726478263L;

        private ClassAccessException(String str) {
            super(str);
        }
    }

    /* loaded from: classes.dex */
    public static class FieldAccessException extends Exception {
        static final long serialVersionUID = 750083948672642L;

        private FieldAccessException(String str) {
            super(str);
        }
    }

    /* loaded from: classes.dex */
    public static class MethodAccessException extends Exception {
        static final long serialVersionUID = 387287349720098L;

        private MethodAccessException(String str) {
            super(str);
        }
    }

    public static boolean canUseReflectionInAndroidPorLater() {
        return Build.VERSION.SDK_INT < 28 && !Build.VERSION.CODENAME.equals("P");
    }

    public static Object createObject(String str, Class<?>[] clsArr, Object... objArr) throws ClassAccessException {
        try {
            Constructor<?> declaredConstructor = getClassForName(str).getDeclaredConstructor(clsArr);
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance(objArr);
        } catch (IllegalAccessException e) {
            throw new ClassAccessException(e.getMessage());
        } catch (InstantiationException e2) {
            throw new ClassAccessException(e2.getMessage());
        } catch (NoSuchMethodException e3) {
            throw new ClassAccessException(e3.getMessage());
        } catch (InvocationTargetException e4) {
            throw new ClassAccessException(e4.getMessage());
        }
    }

    public static Class<?> getClassForName(String str) throws ClassAccessException {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new ClassAccessException(e.getMessage());
        }
    }

    public static <T> T getFieldValue(Class<?> cls, Object obj, String str) throws FieldAccessException {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return (T) declaredField.get(obj);
        } catch (ClassCastException e) {
            throw new FieldAccessException(e.getMessage());
        } catch (IllegalAccessException e2) {
            throw new FieldAccessException(e2.getMessage());
        } catch (NoSuchFieldException e3) {
            throw new FieldAccessException(e3.getMessage());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:5:0x0016 A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean hasMethod(Class<?> r2, String r3, Class<?>[] r4) {
        /*
            r0 = 0
            if (r4 == 0) goto Lc
            int r1 = r4.length     // Catch: java.lang.NoSuchMethodException -> L18
            if (r1 != 0) goto L7
            goto Lc
        L7:
            java.lang.reflect.Method r2 = r2.getDeclaredMethod(r3, r4)     // Catch: java.lang.NoSuchMethodException -> L18
            goto L13
        Lc:
            java.lang.Class[] r4 = new java.lang.Class[r0]     // Catch: java.lang.NoSuchMethodException -> L18
            java.lang.reflect.Method r2 = r2.getDeclaredMethod(r3, r4)     // Catch: java.lang.NoSuchMethodException -> L18
        L13:
            if (r2 == 0) goto L19
            r0 = 1
            goto L19
        L18:
            r2 = move-exception
        L19:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.utils.ReflectionUtils.hasMethod(java.lang.Class, java.lang.String, java.lang.Class[]):boolean");
    }

    public static <T> T invokeMethod(Class<?> cls, Object obj, String str, Class<?>[] clsArr, Object... objArr) throws MethodAccessException {
        Method declaredMethod;
        if (clsArr != null) {
            try {
                if (clsArr.length != 0) {
                    declaredMethod = cls.getDeclaredMethod(str, clsArr);
                    declaredMethod.setAccessible(true);
                    return (T) declaredMethod.invoke(obj, objArr);
                }
            } catch (ClassCastException e) {
                throw new MethodAccessException(e.getMessage());
            } catch (IllegalAccessException e2) {
                throw new MethodAccessException(e2.getMessage());
            } catch (NoSuchMethodException e3) {
                throw new MethodAccessException(e3.getMessage());
            } catch (InvocationTargetException e4) {
                throw new MethodAccessException(e4.getMessage());
            }
        }
        declaredMethod = cls.getDeclaredMethod(str, new Class[0]);
        declaredMethod.setAccessible(true);
        return (T) declaredMethod.invoke(obj, objArr);
    }

    public static void invokeVoidMethod(Class<?> cls, Object obj, String str, Class<?>[] clsArr, Object... objArr) throws MethodAccessException {
        Method declaredMethod;
        if (clsArr != null) {
            try {
                if (clsArr.length != 0) {
                    declaredMethod = cls.getDeclaredMethod(str, clsArr);
                    declaredMethod.setAccessible(true);
                    declaredMethod.invoke(obj, objArr);
                }
            } catch (IllegalAccessException e) {
                throw new MethodAccessException(e.getMessage());
            } catch (NoSuchMethodException e2) {
                throw new MethodAccessException(e2.getMessage());
            } catch (InvocationTargetException e3) {
                throw new MethodAccessException(e3.getMessage());
            }
        }
        declaredMethod = cls.getDeclaredMethod(str, new Class[0]);
        declaredMethod.setAccessible(true);
        declaredMethod.invoke(obj, objArr);
    }

    public static void setFieldValue(Class<?> cls, Object obj, String str, Object obj2) throws FieldAccessException {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            declaredField.set(obj, obj2);
        } catch (IllegalAccessException e) {
            throw new FieldAccessException(e.getMessage());
        } catch (NoSuchFieldException e2) {
            throw new FieldAccessException(e2.getMessage());
        }
    }
}
