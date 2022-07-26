package com.good.gd.apache.commons.logging;

import com.good.gd.apache.commons.logging.impl.LogFactoryImpl;
import com.good.gd.apache.commons.logging.impl.NoOpLog;
import java.lang.reflect.Constructor;
import java.util.Hashtable;

@Deprecated
/* loaded from: classes.dex */
public class LogSource {
    protected static boolean jdk14IsAvailable;
    protected static boolean log4jIsAvailable;
    protected static Hashtable logs = new Hashtable();
    protected static Constructor logImplctor = null;

    static {
        log4jIsAvailable = false;
        jdk14IsAvailable = false;
        String str = null;
        try {
            Class.forName("org.apache.log4j.Logger");
            log4jIsAvailable = true;
        } catch (Throwable th) {
            log4jIsAvailable = false;
        }
        try {
            Class.forName("java.util.logging.Logger");
            Class.forName("org.apache.commons.logging.impl.Jdk14Logger");
            jdk14IsAvailable = true;
        } catch (Throwable th2) {
            jdk14IsAvailable = false;
        }
        try {
            str = System.getProperty("org.apache.commons.logging.log");
            if (str == null) {
                str = System.getProperty(LogFactoryImpl.LOG_PROPERTY);
            }
        } catch (Throwable th3) {
        }
        try {
            if (str != null) {
                setLogImplementation(str);
            } else if (log4jIsAvailable) {
                setLogImplementation("org.apache.commons.logging.impl.Log4JLogger");
            } else if (jdk14IsAvailable) {
                setLogImplementation("org.apache.commons.logging.impl.Jdk14Logger");
            } else {
                setLogImplementation("org.apache.commons.logging.impl.NoOpLog");
            }
        }
    }

    private LogSource() {
    }

    public static Log getInstance(String str) {
        Log log = (Log) logs.get(str);
        if (log == null) {
            Log makeNewLogInstance = makeNewLogInstance(str);
            logs.put(str, makeNewLogInstance);
            return makeNewLogInstance;
        }
        return log;
    }

    public static String[] getLogNames() {
        return (String[]) logs.keySet().toArray(new String[logs.size()]);
    }

    public static Log makeNewLogInstance(String str) {
        Log log;
        try {
            log = (Log) logImplctor.newInstance(str);
        } catch (Throwable th) {
            log = null;
        }
        return log == null ? new NoOpLog(str) : log;
    }

    public static void setLogImplementation(String str) throws LinkageError, ExceptionInInitializerError, NoSuchMethodException, SecurityException, ClassNotFoundException {
        try {
            logImplctor = Class.forName(str).getConstructor("".getClass());
        } catch (Throwable th) {
            logImplctor = null;
        }
    }

    public static Log getInstance(Class cls) {
        return getInstance(cls.getName());
    }

    public static void setLogImplementation(Class cls) throws LinkageError, ExceptionInInitializerError, NoSuchMethodException, SecurityException {
        logImplctor = cls.getConstructor("".getClass());
    }
}
