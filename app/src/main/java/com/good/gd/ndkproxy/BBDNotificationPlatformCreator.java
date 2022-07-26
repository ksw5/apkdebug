package com.good.gd.ndkproxy;

import com.good.gd.ndkproxy.items.MalwareAppsNotification;
import com.good.gd.notifications.BBDNotificationManager;
import com.good.gd.notifications.items.PlatformNotification;

/* loaded from: classes.dex */
public class BBDNotificationPlatformCreator {
    private static boolean closeAllByType(int i) {
        return BBDNotificationManager.getInstance().closeSimilar(i);
    }

    private static boolean closeNotification(String str) {
        return BBDNotificationManager.getInstance().closeNotification(str);
    }

    private static boolean createMalwareAppNotification(int i, String str, String str2, String str3, int i2, String str4) {
        return BBDNotificationManager.getInstance().showNotification(new MalwareAppsNotification(i, str, str2, str3, MalwareAppsNotification.Action.getActionOrDefault(i2), str4));
    }

    private static boolean createNotification(int i, String str, String str2, String str3) {
        return BBDNotificationManager.getInstance().showNotification(new PlatformNotification(i, str, str2, str3));
    }

    public static native String getMessage(String str);

    public static native String getTitle(String str);

    private static boolean updateNotification(String str) {
        return BBDNotificationManager.getInstance().updateNotification(str);
    }
}
