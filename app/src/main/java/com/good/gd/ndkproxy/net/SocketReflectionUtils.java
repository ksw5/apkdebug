package com.good.gd.ndkproxy.net;

import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.ReflectionUtils;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/* loaded from: classes.dex */
public final class SocketReflectionUtils {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static InetAddress createDummyInetAddress() {
        try {
            return InetAddress.getByAddress(new byte[]{0, 0, 0, 0});
        } catch (UnknownHostException e) {
            GDLog.DBGPRINTF(12, "SocketReflectionUtils.initInetAddrInInetSocketAddress exception", e);
            return null;
        }
    }

    public static String getHostNameFromInetSocketAddress(InetSocketAddress inetSocketAddress) {
        GDLog.DBGPRINTF(19, "SocketReflectionUtils::getHostNameFromInetSocketAddress() IN\n");
        String hostName = inetSocketAddress.getHostName();
        if (hostName == null) {
            try {
                Field declaredField = InetSocketAddress.class.getDeclaredField("hostname");
                declaredField.setAccessible(true);
                hostName = (String) declaredField.get(inetSocketAddress);
            } catch (IllegalAccessException e) {
                GDLog.DBGPRINTF(12, "SocketReflectionUtils.getHostNameFromInetSocketAddress IllegalAccessException", e);
            } catch (IllegalArgumentException e2) {
                GDLog.DBGPRINTF(12, "SocketReflectionUtils.getHostNameFromInetSocketAddress IllegalArgumentException", e2);
            } catch (NoSuchFieldException e3) {
                GDLog.DBGPRINTF(12, "SocketReflectionUtils.getHostNameFromInetSocketAddress NoSuchFieldException ", e3);
            } catch (SecurityException e4) {
                GDLog.DBGPRINTF(12, "SocketReflectionUtils.getHostNameFromInetSocketAddress exception", e4);
            }
        }
        GDLog.DBGPRINTF(16, "SRU::getHostNameFromInetSocketAddress() OUT: " + hostName + "\n");
        return hostName;
    }

    private static boolean initInetAddrAndroidNApproach(InetSocketAddress inetSocketAddress) {
        try {
            Field declaredField = InetSocketAddress.class.getDeclaredField("holder");
            declaredField.setAccessible(true);
            declaredField.get(inetSocketAddress);
            Field[] declaredFields = Class.forName(InetSocketAddress.class.getName() + "$InetSocketAddressHolder").getDeclaredFields();
            Field field = null;
            int length = declaredFields.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Field field2 = declaredFields[i];
                if (field2.getName().equals("addr")) {
                    field = field2;
                    break;
                }
                i++;
            }
            if (field == null) {
                return false;
            }
            field.setAccessible(true);
            field.get(declaredField.get(inetSocketAddress));
            field.set(declaredField.get(inetSocketAddress), createDummyInetAddress());
            return true;
        } catch (ClassNotFoundException e) {
            GDLog.DBGPRINTF(12, "SocketReflectionUtils.initInetAddrAndroidNApproach ClassNotFoundException", e);
            return false;
        } catch (IllegalAccessException e2) {
            GDLog.DBGPRINTF(12, "SocketReflectionUtils.initInetAddrAndroidNApproach IllegalAccessException", e2);
            return false;
        } catch (NoSuchFieldException e3) {
            GDLog.DBGPRINTF(12, "SocketReflectionUtils.initInetAddrAndroidNApproach NoSuchFieldException", e3);
            return false;
        } catch (SecurityException e4) {
            GDLog.DBGPRINTF(12, "SocketReflectionUtils.initInetAddrAndroidNApproach SecurityException ", e4);
            return false;
        }
    }

    public static void initInetAddrInInetSocketAddress(InetSocketAddress inetSocketAddress) {
        boolean z;
        GDLog.DBGPRINTF(19, "SocketReflectionUtils::initInetAddrInInetSocketAddress() IN\n");
        if (ReflectionUtils.canUseReflectionInAndroidPorLater()) {
            z = initInetAddrInInetSocketAddressImpl(inetSocketAddress);
        } else {
            GDLog.DBGPRINTF(16, "SRU::initInetAddr() skip\n");
            z = false;
        }
        GDLog.DBGPRINTF(16, "SRU::initInetAddr " + z + "\n");
    }

    private static boolean initInetAddrInInetSocketAddressImpl(InetSocketAddress inetSocketAddress) {
        GDLog.DBGPRINTF(16, "SRU::initInetAddr() Android N+\n");
        boolean initInetAddrAndroidNApproach = initInetAddrAndroidNApproach(inetSocketAddress);
        return !initInetAddrAndroidNApproach ? initInetAddrOldApproach(inetSocketAddress) : initInetAddrAndroidNApproach;
    }

    private static boolean initInetAddrOldApproach(InetSocketAddress inetSocketAddress) {
        GDLog.DBGPRINTF(16, "SRU::initInetAddrOldApproach() IN\n");
        try {
            Field declaredField = InetSocketAddress.class.getDeclaredField("addr");
            declaredField.setAccessible(true);
            declaredField.get(inetSocketAddress);
            declaredField.set(inetSocketAddress, createDummyInetAddress());
            return true;
        } catch (IllegalAccessException e) {
            GDLog.DBGPRINTF(12, "SocketReflectionUtils.initInetAddrOldApproach IllegalAccessException", e);
            return false;
        } catch (IllegalArgumentException e2) {
            GDLog.DBGPRINTF(12, "SocketReflectionUtils.initInetAddrOldApproach IllegalArgumentException ", e2);
            return false;
        } catch (NoSuchFieldException e3) {
            GDLog.DBGPRINTF(12, "SocketReflectionUtils.initInetAddrOldApproach NoSuchFieldException", e3);
            return false;
        } catch (SecurityException e4) {
            GDLog.DBGPRINTF(12, "SocketReflectionUtils.initInetAddrOldApproach SecurityException ", e4);
            return false;
        }
    }
}
