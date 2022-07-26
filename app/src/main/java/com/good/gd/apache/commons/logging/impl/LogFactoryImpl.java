package com.good.gd.apache.commons.logging.impl;

import com.good.gd.apache.commons.logging.Log;
import com.good.gd.apache.commons.logging.LogConfigurationException;
import com.good.gd.apache.commons.logging.LogFactory;
import com.good.gd.ndkproxy.PasswordType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* loaded from: classes.dex */
public class LogFactoryImpl extends LogFactory {
    public static final String ALLOW_FLAWED_CONTEXT_PROPERTY = "org.apache.commons.logging.Log.allowFlawedContext";
    public static final String ALLOW_FLAWED_DISCOVERY_PROPERTY = "org.apache.commons.logging.Log.allowFlawedDiscovery";
    public static final String ALLOW_FLAWED_HIERARCHY_PROPERTY = "org.apache.commons.logging.Log.allowFlawedHierarchy";
    public static final String LOG_PROPERTY = "org.apache.commons.logging.Log";
    protected static final String LOG_PROPERTY_OLD = "org.apache.commons.logging.log";
    private static final String PKG_IMPL = "org.apache.commons.logging.impl.";
    private static final int PKG_LEN = 32;
    private boolean allowFlawedContext;
    private boolean allowFlawedDiscovery;
    private boolean allowFlawedHierarchy;
    private String diagnosticPrefix;
    private String logClassName;
    private static final String LOGGING_IMPL_LOG4J_LOGGER = "org.apache.commons.logging.impl.Log4JLogger";
    private static final String LOGGING_IMPL_JDK14_LOGGER = "org.apache.commons.logging.impl.Jdk14Logger";
    private static final String LOGGING_IMPL_LUMBERJACK_LOGGER = "org.apache.commons.logging.impl.Jdk13LumberjackLogger";
    private static final String LOGGING_IMPL_SIMPLE_LOGGER = "org.apache.commons.logging.impl.SimpleLog";
    private static final String[] classesToDiscover = {LOGGING_IMPL_LOG4J_LOGGER, LOGGING_IMPL_JDK14_LOGGER, LOGGING_IMPL_LUMBERJACK_LOGGER, LOGGING_IMPL_SIMPLE_LOGGER};
    private boolean useTCCL = true;
    protected Hashtable attributes = new Hashtable();
    protected Hashtable instances = new Hashtable();
    protected Constructor logConstructor = null;
    protected Class[] logConstructorSignature = {String.class};
    protected Method logMethod = null;
    protected Class[] logMethodSignature = {LogFactory.class};

    public LogFactoryImpl() {
        initDiagnostics();
        if (isDiagnosticsEnabled()) {
            logDiagnostic("Instance created.");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x01a0 A[LOOP:0: B:5:0x003c->B:24:0x01a0, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x019d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0235 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private Log createLogFromClass(String r17, String r18, boolean r19) throws LogConfigurationException {
        /*
            Method dump skipped, instructions count: 698
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.apache.commons.logging.impl.LogFactoryImpl.createLogFromClass(java.lang.String, java.lang.String, boolean):com.good.gd.apache.commons.logging.Log");
    }

    private Log discoverLogImplementation(String str) throws LogConfigurationException {
        if (isDiagnosticsEnabled()) {
            logDiagnostic("Discovering a Log implementation...");
        }
        initConfiguration();
        Log log = null;
        String findUserSpecifiedLogClassName = findUserSpecifiedLogClassName();
        if (findUserSpecifiedLogClassName != null) {
            if (isDiagnosticsEnabled()) {
                logDiagnostic("Attempting to load user-specified log class '" + findUserSpecifiedLogClassName + "'...");
            }
            Log createLogFromClass = createLogFromClass(findUserSpecifiedLogClassName, str, true);
            if (createLogFromClass == null) {
                StringBuffer stringBuffer = new StringBuffer("User-specified log class '");
                stringBuffer.append(findUserSpecifiedLogClassName);
                stringBuffer.append("' cannot be found or is not useable.");
                informUponSimilarName(stringBuffer, findUserSpecifiedLogClassName, LOGGING_IMPL_LOG4J_LOGGER);
                informUponSimilarName(stringBuffer, findUserSpecifiedLogClassName, LOGGING_IMPL_JDK14_LOGGER);
                informUponSimilarName(stringBuffer, findUserSpecifiedLogClassName, LOGGING_IMPL_LUMBERJACK_LOGGER);
                informUponSimilarName(stringBuffer, findUserSpecifiedLogClassName, LOGGING_IMPL_SIMPLE_LOGGER);
                throw new LogConfigurationException(stringBuffer.toString());
            }
            return createLogFromClass;
        }
        if (isDiagnosticsEnabled()) {
            logDiagnostic("No user-specified Log implementation; performing discovery using the standard supported logging implementations...");
        }
        int i = 0;
        while (true) {
            String[] strArr = classesToDiscover;
            if (i >= strArr.length || log != null) {
                break;
            }
            log = createLogFromClass(strArr[i], str, true);
            i++;
        }
        if (log == null) {
            throw new LogConfigurationException("No suitable Log implementation");
        }
        return log;
    }

    private String findUserSpecifiedLogClassName() {
        if (isDiagnosticsEnabled()) {
            logDiagnostic("Trying to get log class from attribute 'org.apache.commons.logging.Log'");
        }
        String str = (String) getAttribute(LOG_PROPERTY);
        if (str == null) {
            if (isDiagnosticsEnabled()) {
                logDiagnostic("Trying to get log class from attribute 'org.apache.commons.logging.log'");
            }
            str = (String) getAttribute(LOG_PROPERTY_OLD);
        }
        if (str == null) {
            if (isDiagnosticsEnabled()) {
                logDiagnostic("Trying to get log class from system property 'org.apache.commons.logging.Log'");
            }
            try {
                str = System.getProperty(LOG_PROPERTY);
            } catch (SecurityException e) {
                if (isDiagnosticsEnabled()) {
                    logDiagnostic("No access allowed to system property 'org.apache.commons.logging.Log' - " + e.getMessage());
                }
            }
        }
        if (str == null) {
            if (isDiagnosticsEnabled()) {
                logDiagnostic("Trying to get log class from system property 'org.apache.commons.logging.log'");
            }
            try {
                str = System.getProperty(LOG_PROPERTY_OLD);
            } catch (SecurityException e2) {
                if (isDiagnosticsEnabled()) {
                    logDiagnostic("No access allowed to system property 'org.apache.commons.logging.log' - " + e2.getMessage());
                }
            }
        }
        return str != null ? str.trim() : str;
    }

    private ClassLoader getBaseClassLoader() throws LogConfigurationException {
        ClassLoader classLoader = getClassLoader(LogFactoryImpl.class);
        if (!this.useTCCL) {
            return classLoader;
        }
        ClassLoader contextClassLoader = getContextClassLoader();
        ClassLoader lowestClassLoader = getLowestClassLoader(contextClassLoader, classLoader);
        if (lowestClassLoader == null) {
            if (this.allowFlawedContext) {
                if (isDiagnosticsEnabled()) {
                    logDiagnostic("[WARNING] the context classloader is not part of a parent-child relationship with the classloader that loaded LogFactoryImpl.");
                }
                return contextClassLoader;
            }
            throw new LogConfigurationException("Bad classloader hierarchy; LogFactoryImpl was loaded via a classloader that is not related to the current context classloader.");
        }
        if (lowestClassLoader != contextClassLoader) {
            if (this.allowFlawedContext) {
                if (isDiagnosticsEnabled()) {
                    logDiagnostic("Warning: the context classloader is an ancestor of the classloader that loaded LogFactoryImpl; it should be the same or a descendant. The application using commons-logging should ensure the context classloader is used correctly.");
                }
            } else {
                throw new LogConfigurationException("Bad classloader hierarchy; LogFactoryImpl was loaded via a classloader that is not related to the current context classloader.");
            }
        }
        return lowestClassLoader;
    }

    private boolean getBooleanConfiguration(String str, boolean z) {
        String configurationValue = getConfigurationValue(str);
        return configurationValue == null ? z : Boolean.valueOf(configurationValue).booleanValue();
    }

    protected static ClassLoader getClassLoader(Class cls) {
        return LogFactory.getClassLoader(cls);
    }

    private String getConfigurationValue(String str) {
        String property;
        if (isDiagnosticsEnabled()) {
            logDiagnostic("[ENV] Trying to get configuration for item " + str);
        }
        Object attribute = getAttribute(str);
        if (attribute != null) {
            if (isDiagnosticsEnabled()) {
                logDiagnostic("[ENV] Found LogFactory attribute [" + attribute + "] for " + str);
            }
            return attribute.toString();
        }
        if (isDiagnosticsEnabled()) {
            logDiagnostic("[ENV] No LogFactory attribute found for " + str);
        }
        try {
            property = System.getProperty(str);
        } catch (SecurityException e) {
            if (isDiagnosticsEnabled()) {
                logDiagnostic("[ENV] Security prevented reading system property " + str);
            }
        }
        if (property != null) {
            if (isDiagnosticsEnabled()) {
                logDiagnostic("[ENV] Found system property [" + property + "] for " + str);
            }
            return property;
        }
        if (isDiagnosticsEnabled()) {
            logDiagnostic("[ENV] No system property found for property " + str);
        }
        if (!isDiagnosticsEnabled()) {
            return null;
        }
        logDiagnostic("[ENV] No configuration defined for item " + str);
        return null;
    }

    protected static ClassLoader getContextClassLoader() throws LogConfigurationException {
        return LogFactory.getContextClassLoader();
    }

    private ClassLoader getLowestClassLoader(ClassLoader classLoader, ClassLoader classLoader2) {
        if (classLoader == null) {
            return classLoader2;
        }
        if (classLoader2 == null) {
            return classLoader;
        }
        for (ClassLoader classLoader3 = classLoader; classLoader3 != null; classLoader3 = classLoader3.getParent()) {
            if (classLoader3 == classLoader2) {
                return classLoader;
            }
        }
        for (ClassLoader classLoader4 = classLoader2; classLoader4 != null; classLoader4 = classLoader4.getParent()) {
            if (classLoader4 == classLoader) {
                return classLoader2;
            }
        }
        return null;
    }

    private void handleFlawedDiscovery(String str, ClassLoader classLoader, Throwable th) {
        if (isDiagnosticsEnabled()) {
            logDiagnostic("Could not instantiate Log '" + str + "' -- " + th.getClass().getName() + ": " + th.getLocalizedMessage());
        }
        if (this.allowFlawedDiscovery) {
            return;
        }
        throw new LogConfigurationException(th);
    }

    private void handleFlawedHierarchy(ClassLoader classLoader, Class cls) throws LogConfigurationException {
        String name = Log.class.getName();
        Class<?>[] interfaces = cls.getInterfaces();
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= interfaces.length) {
                break;
            } else if (name.equals(interfaces[i].getName())) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (z) {
            if (isDiagnosticsEnabled()) {
                try {
                    logDiagnostic("Class '" + cls.getName() + "' was found in classloader " + LogFactory.objectId(classLoader) + ". It is bound to a Log interface which is not the one loaded from classloader " + LogFactory.objectId(getClassLoader(Log.class)));
                } catch (Throwable th) {
                    logDiagnostic("Error while trying to output diagnostics about bad class '" + cls + "'");
                }
            }
            if (!this.allowFlawedHierarchy) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Terminating logging for this context ");
                stringBuffer.append("due to bad log hierarchy. ");
                stringBuffer.append("You have more than one version of '");
                stringBuffer.append(Log.class.getName());
                stringBuffer.append("' visible.");
                if (isDiagnosticsEnabled()) {
                    logDiagnostic(stringBuffer.toString());
                }
                throw new LogConfigurationException(stringBuffer.toString());
            } else if (!isDiagnosticsEnabled()) {
            } else {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Warning: bad log hierarchy. ");
                stringBuffer2.append("You have more than one version of '");
                stringBuffer2.append(Log.class.getName());
                stringBuffer2.append("' visible.");
                logDiagnostic(stringBuffer2.toString());
            }
        } else if (!this.allowFlawedDiscovery) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Terminating logging for this context. ");
            stringBuffer3.append("Log class '");
            stringBuffer3.append(cls.getName());
            stringBuffer3.append("' does not implement the Log interface.");
            if (isDiagnosticsEnabled()) {
                logDiagnostic(stringBuffer3.toString());
            }
            throw new LogConfigurationException(stringBuffer3.toString());
        } else if (!isDiagnosticsEnabled()) {
        } else {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("[WARNING] Log class '");
            stringBuffer4.append(cls.getName());
            stringBuffer4.append("' does not implement the Log interface.");
            logDiagnostic(stringBuffer4.toString());
        }
    }

    private void informUponSimilarName(StringBuffer stringBuffer, String str, String str2) {
        if (!str.equals(str2) && str.regionMatches(true, 0, str2, 0, PKG_LEN + 5)) {
            stringBuffer.append(" Did you mean '");
            stringBuffer.append(str2);
            stringBuffer.append("'?");
        }
    }

    private void initConfiguration() {
        this.allowFlawedContext = getBooleanConfiguration(ALLOW_FLAWED_CONTEXT_PROPERTY, true);
        this.allowFlawedDiscovery = getBooleanConfiguration(ALLOW_FLAWED_DISCOVERY_PROPERTY, true);
        this.allowFlawedHierarchy = getBooleanConfiguration(ALLOW_FLAWED_HIERARCHY_PROPERTY, true);
    }

    private void initDiagnostics() {
        String str;
        ClassLoader classLoader = getClassLoader(LogFactoryImpl.class);
        if (classLoader == null) {
            str = "BOOTLOADER";
        } else {
            try {
                str = LogFactory.objectId(classLoader);
            } catch (SecurityException e) {
                str = PasswordType.SMNOTYETSET;
            }
        }
        this.diagnosticPrefix = "[LogFactoryImpl@" + System.identityHashCode(this) + " from " + str + "] ";
    }

    protected static boolean isDiagnosticsEnabled() {
        return LogFactory.isDiagnosticsEnabled();
    }

    private boolean isLogLibraryAvailable(String str, String str2) {
        if (isDiagnosticsEnabled()) {
            logDiagnostic("Checking for '" + str + "'.");
        }
        try {
            if (createLogFromClass(str2, getClass().getName(), false) == null) {
                if (isDiagnosticsEnabled()) {
                    logDiagnostic("Did not find '" + str + "'.");
                }
                return false;
            } else if (!isDiagnosticsEnabled()) {
                return true;
            } else {
                logDiagnostic("Found '" + str + "'.");
                return true;
            }
        } catch (LogConfigurationException e) {
            if (isDiagnosticsEnabled()) {
                logDiagnostic("Logging system '" + str + "' is available but not useable.");
            }
            return false;
        }
    }

    @Override // com.good.gd.apache.commons.logging.LogFactory
    public Object getAttribute(String str) {
        return this.attributes.get(str);
    }

    @Override // com.good.gd.apache.commons.logging.LogFactory
    public String[] getAttributeNames() {
        Vector vector = new Vector();
        Enumeration keys = this.attributes.keys();
        while (keys.hasMoreElements()) {
            vector.addElement((String) keys.nextElement());
        }
        int size = vector.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = (String) vector.elementAt(i);
        }
        return strArr;
    }

    @Override // com.good.gd.apache.commons.logging.LogFactory
    public Log getInstance(Class cls) throws LogConfigurationException {
        return getInstance(cls.getName());
    }

    @Deprecated
    protected String getLogClassName() {
        if (this.logClassName == null) {
            discoverLogImplementation(LogFactoryImpl.class.getName());
        }
        return this.logClassName;
    }

    @Deprecated
    protected Constructor getLogConstructor() throws LogConfigurationException {
        if (this.logConstructor == null) {
            discoverLogImplementation(LogFactoryImpl.class.getName());
        }
        return this.logConstructor;
    }

    @Deprecated
    protected boolean isJdk13LumberjackAvailable() {
        return isLogLibraryAvailable("Jdk13Lumberjack", LOGGING_IMPL_LUMBERJACK_LOGGER);
    }

    @Deprecated
    protected boolean isJdk14Available() {
        return isLogLibraryAvailable("Jdk14", LOGGING_IMPL_JDK14_LOGGER);
    }

    @Deprecated
    protected boolean isLog4JAvailable() {
        return isLogLibraryAvailable("Log4J", LOGGING_IMPL_LOG4J_LOGGER);
    }

    protected void logDiagnostic(String str) {
        if (isDiagnosticsEnabled()) {
            LogFactory.logRawDiagnostic(this.diagnosticPrefix + str);
        }
    }

    protected Log newInstance(String str) throws LogConfigurationException {
        Log log;
        try {
            Constructor constructor = this.logConstructor;
            if (constructor == null) {
                log = discoverLogImplementation(str);
            } else {
                log = (Log) constructor.newInstance(str);
            }
            Method method = this.logMethod;
            if (method != null) {
                method.invoke(log, this);
            }
            return log;
        } catch (LogConfigurationException e) {
            throw e;
        } catch (InvocationTargetException e2) {
            Throwable targetException = e2.getTargetException();
            if (targetException != null) {
                throw new LogConfigurationException(targetException);
            }
            throw new LogConfigurationException(e2);
        } catch (Throwable th) {
            throw new LogConfigurationException(th);
        }
    }

    @Override // com.good.gd.apache.commons.logging.LogFactory
    public void release() {
        logDiagnostic("Releasing all known loggers");
        this.instances.clear();
    }

    @Override // com.good.gd.apache.commons.logging.LogFactory
    public void removeAttribute(String str) {
        this.attributes.remove(str);
    }

    @Override // com.good.gd.apache.commons.logging.LogFactory
    public void setAttribute(String str, Object obj) {
        if (this.logConstructor != null) {
            logDiagnostic("setAttribute: call too late; configuration already performed.");
        }
        if (obj == null) {
            this.attributes.remove(str);
        } else {
            this.attributes.put(str, obj);
        }
        if (str.equals(LogFactory.TCCL_KEY)) {
            this.useTCCL = Boolean.valueOf(obj.toString()).booleanValue();
        }
    }

    @Override // com.good.gd.apache.commons.logging.LogFactory
    public Log getInstance(String str) throws LogConfigurationException {
        Log log = (Log) this.instances.get(str);
        if (log == null) {
            Log newInstance = newInstance(str);
            this.instances.put(str, newInstance);
            return newInstance;
        }
        return log;
    }
}
