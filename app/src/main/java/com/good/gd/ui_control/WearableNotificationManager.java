package com.good.gd.ui_control;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import com.good.gd.resources.R;
import com.good.gd.utils.GDLocalizer;
import com.good.gt.context.GTBaseContext;
import com.good.gt.util.AndroidVersionUtils;

/* loaded from: classes.dex */
public class WearableNotificationManager {
    private static final String ACTIVATE_NOTIFICATION_DESCRIPTION = "Tap to activate";
    private static final String ACTIVATE_NOTIFICATION_NAME = "Wearable app requires activation";
    private static final String ACTIVATION_CHANNEL_ID = "ACT_WEARABLE_NOTIFICATION";
    private static final String AUTHORIZE_CHANNEL_ID = "AUTH_WEARABLE_NOTIFICATION";
    private static final String AUTHORIZE_NOTIFICATION_DESCRIPTION = "Tap to authorize";
    private static final String AUTHORIZE_NOTIFICATION_NAME = "Wearable app requires authorization";
    private static final String CHANNEL_NAME = "BlackBerry Dynamics Wearable";
    private static final int NOTIFICATION_ID = 9000;

    /* loaded from: classes.dex */
    public enum NotificationType {
        ACTIVATION(WearableNotificationManager.ACTIVATION_CHANNEL_ID, WearableNotificationManager.CHANNEL_NAME, WearableNotificationManager.ACTIVATE_NOTIFICATION_NAME, WearableNotificationManager.ACTIVATE_NOTIFICATION_DESCRIPTION),
        AUTHORIZATION(WearableNotificationManager.AUTHORIZE_CHANNEL_ID, WearableNotificationManager.CHANNEL_NAME, WearableNotificationManager.AUTHORIZE_NOTIFICATION_NAME, WearableNotificationManager.AUTHORIZE_NOTIFICATION_DESCRIPTION);
        
        NotificationChannel channel;
        String channelID;
        String channelName;
        String notificationDesc;
        String notificationName;

        NotificationType(String str, String str2, String str3, String str4) {
            this.channelID = str;
            this.channelName = str2;
            this.notificationName = GDLocalizer.getLocalizedString(str3);
            this.notificationDesc = GDLocalizer.getLocalizedString(str4);
            this.channel = new NotificationChannel(str, str2, 4);
        }
    }

    public static void dismissNotifications() {
        ((NotificationManager) GTBaseContext.getInstance().getApplicationContext().getSystemService(NotificationManager.class)).cancel(NOTIFICATION_ID);
    }

    public static void onActivationSuccess() {
        if (AndroidVersionUtils.androidHasBackgroundActivityStartRestrictions()) {
            dismissNotifications();
        }
    }

    public static void showNotification(IntentAndContextForStart intentAndContextForStart, NotificationType notificationType) {
        NotificationChannel notificationChannel = notificationType.channel;
        NotificationManager notificationManager = (NotificationManager) intentAndContextForStart.getContext().getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(NOTIFICATION_ID, new Notification.Builder(intentAndContextForStart.getContext(), notificationType.channelID).setSmallIcon(R.drawable.bbd_logo_phone).setContentTitle(notificationType.notificationName).setContentText(notificationType.notificationDesc).setPriority(2).setContentIntent(intentAndContextForStart.getPendingIntent()).setAutoCancel(true).build());
    }
}
