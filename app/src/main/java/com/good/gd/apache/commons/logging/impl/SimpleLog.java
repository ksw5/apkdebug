package com.good.gd.apache.commons.logging.impl;

import com.bold360.natwest.BuildConfig;
import com.bold360.natwest.UtilsKt;
import com.good.gd.apache.commons.logging.Log;
import com.good.gd.apache.commons.logging.LogConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import kotlinx.coroutines.DebugKt;

/* loaded from: classes.dex */
public class SimpleLog implements Log, Serializable {
    protected static final String DEFAULT_DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss:SSS zzz";
    public static final int LOG_LEVEL_ALL = 0;
    public static final int LOG_LEVEL_DEBUG = 2;
    public static final int LOG_LEVEL_ERROR = 5;
    public static final int LOG_LEVEL_FATAL = 6;
    public static final int LOG_LEVEL_INFO = 3;
    public static final int LOG_LEVEL_OFF = 7;
    public static final int LOG_LEVEL_TRACE = 1;
    public static final int LOG_LEVEL_WARN = 4;
    protected static DateFormat dateFormatter = null;
    protected static String dateTimeFormat = null;
    protected static boolean showDateTime = false;
    protected static boolean showLogName = false;
    protected static boolean showShortName = false;
    protected static final Properties simpleLogProps;
    protected static final String systemPrefix = "org.apache.commons.logging.simplelog.";
    protected int currentLogLevel;
    protected String logName;
    private String shortLogName = null;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class hbfhc implements PrivilegedAction {
        final /* synthetic */ String dbjc;

        hbfhc(String str) {
            this.dbjc = str;
        }

        @Override // java.security.PrivilegedAction
        public Object run() {
            ClassLoader contextClassLoader = SimpleLog.getContextClassLoader();
            if (contextClassLoader != null) {
                return contextClassLoader.getResourceAsStream(this.dbjc);
            }
            return ClassLoader.getSystemResourceAsStream(this.dbjc);
        }
    }

    static {
        Properties properties = new Properties();
        simpleLogProps = properties;
        showLogName = false;
        showShortName = true;
        showDateTime = false;
        dateTimeFormat = DEFAULT_DATE_TIME_FORMAT;
        dateFormatter = null;
        InputStream resourceAsStream = getResourceAsStream("simplelog.properties");
        if (resourceAsStream != null) {
            try {
                properties.load(resourceAsStream);
                resourceAsStream.close();
            } catch (IOException e) {
            }
        }
        showLogName = getBooleanProperty("org.apache.commons.logging.simplelog.showlogname", showLogName);
        showShortName = getBooleanProperty("org.apache.commons.logging.simplelog.showShortLogname", showShortName);
        boolean booleanProperty = getBooleanProperty("org.apache.commons.logging.simplelog.showdatetime", showDateTime);
        showDateTime = booleanProperty;
        if (booleanProperty) {
            dateTimeFormat = getStringProperty("org.apache.commons.logging.simplelog.dateTimeFormat", dateTimeFormat);
            try {
                dateFormatter = new SimpleDateFormat(dateTimeFormat);
            } catch (IllegalArgumentException e2) {
                dateTimeFormat = DEFAULT_DATE_TIME_FORMAT;
                dateFormatter = new SimpleDateFormat(dateTimeFormat);
            }
        }
    }

    public SimpleLog(String str) {
        this.logName = null;
        this.logName = str;
        setLevel(3);
        String stringProperty = getStringProperty("org.apache.commons.logging.simplelog.log." + this.logName);
        int lastIndexOf = String.valueOf(str).lastIndexOf(".");
        while (stringProperty == null && lastIndexOf > -1) {
            str = str.substring(0, lastIndexOf);
            stringProperty = getStringProperty("org.apache.commons.logging.simplelog.log." + str);
            lastIndexOf = String.valueOf(str).lastIndexOf(".");
        }
        stringProperty = stringProperty == null ? getStringProperty("org.apache.commons.logging.simplelog.defaultlog") : stringProperty;
        if ("all".equalsIgnoreCase(stringProperty)) {
            setLevel(0);
        } else if ("trace".equalsIgnoreCase(stringProperty)) {
            setLevel(1);
        } else if (BuildConfig.BUILD_TYPE.equalsIgnoreCase(stringProperty)) {
            setLevel(2);
        } else if ("info".equalsIgnoreCase(stringProperty)) {
            setLevel(3);
        } else if ("warn".equalsIgnoreCase(stringProperty)) {
            setLevel(4);
        } else if (UtilsKt.Error.equalsIgnoreCase(stringProperty)) {
            setLevel(5);
        } else if ("fatal".equalsIgnoreCase(stringProperty)) {
            setLevel(6);
        } else if (!DebugKt.DEBUG_PROPERTY_VALUE_OFF.equalsIgnoreCase(stringProperty)) {
        } else {
            setLevel(7);
        }
    }

    private static boolean getBooleanProperty(String str, boolean z) {
        String stringProperty = getStringProperty(str);
        return stringProperty == null ? z : "true".equalsIgnoreCase(stringProperty);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ClassLoader getContextClassLoader() {
        ClassLoader classLoader = null;
        try {
            try {
                classLoader = (ClassLoader) Thread.class.getMethod("getContextClassLoader", null).invoke(Thread.currentThread(), null);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e2) {
                if (!(e2.getTargetException() instanceof SecurityException)) {
                    throw new LogConfigurationException("Unexpected InvocationTargetException", e2.getTargetException());
                }
            }
        } catch (NoSuchMethodException e3) {
        }
        return classLoader == null ? SimpleLog.class.getClassLoader() : classLoader;
    }

    private static InputStream getResourceAsStream(String str) {
        return (InputStream) AccessController.doPrivileged(new hbfhc(str));
    }

    private static String getStringProperty(String str) {
        String str2;
        try {
            str2 = System.getProperty(str);
        } catch (SecurityException e) {
            str2 = null;
        }
        return str2 == null ? simpleLogProps.getProperty(str) : str2;
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public final void debug(Object obj) {
        if (isLevelEnabled(2)) {
            log(2, obj, null);
        }
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public final void error(Object obj) {
        if (isLevelEnabled(5)) {
            log(5, obj, null);
        }
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public final void fatal(Object obj) {
        if (isLevelEnabled(6)) {
            log(6, obj, null);
        }
    }

    public int getLevel() {
        return this.currentLogLevel;
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public final void info(Object obj) {
        if (isLevelEnabled(3)) {
            log(3, obj, null);
        }
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public final boolean isDebugEnabled() {
        return isLevelEnabled(2);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public final boolean isErrorEnabled() {
        return isLevelEnabled(5);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public final boolean isFatalEnabled() {
        return isLevelEnabled(6);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public final boolean isInfoEnabled() {
        return isLevelEnabled(3);
    }

    protected boolean isLevelEnabled(int i) {
        return i >= this.currentLogLevel;
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public final boolean isTraceEnabled() {
        return isLevelEnabled(1);
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public final boolean isWarnEnabled() {
        return isLevelEnabled(4);
    }

    protected void log(int i, Object obj, Throwable th) {
        StringBuffer stringBuffer = new StringBuffer();
        if (showDateTime) {
            stringBuffer.append(dateFormatter.format(new Date()));
            stringBuffer.append(" ");
        }
        switch (i) {
            case 1:
                stringBuffer.append("[TRACE] ");
                break;
            case 2:
                stringBuffer.append("[DEBUG] ");
                break;
            case 3:
                stringBuffer.append("[INFO] ");
                break;
            case 4:
                stringBuffer.append("[WARN] ");
                break;
            case 5:
                stringBuffer.append("[ERROR] ");
                break;
            case 6:
                stringBuffer.append("[FATAL] ");
                break;
        }
        if (showShortName) {
            if (this.shortLogName == null) {
                String str = this.logName;
                String substring = str.substring(str.lastIndexOf(".") + 1);
                this.shortLogName = substring;
                this.shortLogName = substring.substring(substring.lastIndexOf("/") + 1);
            }
            stringBuffer.append(String.valueOf(this.shortLogName)).append(" - ");
        } else if (showLogName) {
            stringBuffer.append(String.valueOf(this.logName)).append(" - ");
        }
        stringBuffer.append(String.valueOf(obj));
        if (th != null) {
            stringBuffer.append(" <");
            stringBuffer.append(th.toString());
            stringBuffer.append(">");
            StringWriter stringWriter = new StringWriter(1024);
            PrintWriter printWriter = new PrintWriter(stringWriter);
            th.printStackTrace(printWriter);
            printWriter.close();
            stringBuffer.append(stringWriter.toString());
        }
        write(stringBuffer);
    }

    public void setLevel(int i) {
        this.currentLogLevel = i;
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public final void trace(Object obj) {
        if (isLevelEnabled(1)) {
            log(1, obj, null);
        }
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public final void warn(Object obj) {
        if (isLevelEnabled(4)) {
            log(4, obj, null);
        }
    }

    protected void write(StringBuffer stringBuffer) {
        System.err.println(stringBuffer.toString());
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public final void debug(Object obj, Throwable th) {
        if (isLevelEnabled(2)) {
            log(2, obj, th);
        }
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public final void error(Object obj, Throwable th) {
        if (isLevelEnabled(5)) {
            log(5, obj, th);
        }
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public final void fatal(Object obj, Throwable th) {
        if (isLevelEnabled(6)) {
            log(6, obj, th);
        }
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public final void info(Object obj, Throwable th) {
        if (isLevelEnabled(3)) {
            log(3, obj, th);
        }
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public final void trace(Object obj, Throwable th) {
        if (isLevelEnabled(1)) {
            log(1, obj, th);
        }
    }

    @Override // com.good.gd.apache.commons.logging.Log
    public final void warn(Object obj, Throwable th) {
        if (isLevelEnabled(4)) {
            log(4, obj, th);
        }
    }

    private static String getStringProperty(String str, String str2) {
        String stringProperty = getStringProperty(str);
        return stringProperty == null ? str2 : stringProperty;
    }
}
