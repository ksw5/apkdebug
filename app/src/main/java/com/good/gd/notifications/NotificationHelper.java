package com.good.gd.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import com.good.gd.notifications.items.BBDNotificationBase;
import java.util.Iterator;

/* loaded from: classes.dex */
public class NotificationHelper {
    private static int convertImportanceToPriority(int i) {
        if (i != 1) {
            if (i == 3) {
                return 0;
            }
            if (i == 4) {
                return 1;
            }
            return i != 5 ? -1 : 2;
        }
        return -2;
    }

    public static void createChannel(NotificationManager notificationManager, String str, String str2, String str3, int i) {
        if (notificationManager != null && Build.VERSION.SDK_INT >= 26) {
            Iterator<NotificationChannelGroup> it = notificationManager.getNotificationChannelGroups().iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().getId().equals(BBDNotificationBase.GROUP_KEY_SDK_NOTIFICATIONS)) {
                        notificationManager.deleteNotificationChannelGroup(BBDNotificationBase.GROUP_KEY_SDK_NOTIFICATIONS);
                        break;
                    }
                } else {
                    break;
                }
            }
            notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(BBDNotificationBase.GROUP_KEY_SDK_NOTIFICATIONS, str));
            NotificationChannel notificationChannel = new NotificationChannel(str2, str, i);
            notificationChannel.setDescription(str3);
            notificationChannel.setShowBadge(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setGroup(BBDNotificationBase.GROUP_KEY_SDK_NOTIFICATIONS);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public static Notification.Builder createNotificationBuilder(Context context, String str, int i) {
        if (Build.VERSION.SDK_INT >= 26) {
            return new Notification.Builder(context, str);
        }
        Notification.Builder builder = new Notification.Builder(context);
        int convertImportanceToPriority = convertImportanceToPriority(i);
        if (convertImportanceToPriority != -1) {
            builder.setPriority(convertImportanceToPriority);
        }
        return builder;
    }
}
