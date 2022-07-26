package com.good.gd.notifications.items;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.good.gd.ndkproxy.BBDNotificationPlatformCreator;

/* loaded from: classes.dex */
public abstract class BBDNotificationBase {
    public static final String GROUP_KEY_SDK_NOTIFICATIONS = "com.good.gd.notifications.group";
    public static final String SDK_NOTIFICATION_TAG = "BBSDK:";
    private String coreId;
    private boolean isGroupHeader;
    private String message;
    private int notificationId;
    private String title;

    public BBDNotificationBase(int i, String str) {
        this.coreId = str;
        this.notificationId = i;
        this.isGroupHeader = false;
    }

    public abstract void close(NotificationManager notificationManager);

    public String createTag() {
        return SDK_NOTIFICATION_TAG + this.coreId;
    }

    public String getCoreId() {
        return this.coreId;
    }

    public abstract Intent getMainAction();

    public PendingIntent getMainActionPI(Context context, Intent intent) {
        return PendingIntent.getActivity(context, 1, intent, 201326592);
    }

    public String getMessage() {
        return this.message;
    }

    public int getNotificationType() {
        return this.notificationId;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isGroupHeader() {
        return this.isGroupHeader;
    }

    public void setGroupHeader(boolean z) {
        this.isGroupHeader = z;
    }

    public abstract void show(Context context, NotificationManager notificationManager, String str);

    public abstract boolean update(Context context, NotificationManager notificationManager);

    public void updateTitleAndMessage() {
        String str = this.coreId;
        if (str == null) {
            return;
        }
        String title = BBDNotificationPlatformCreator.getTitle(str);
        String message = BBDNotificationPlatformCreator.getMessage(this.coreId);
        if (!title.isEmpty()) {
            this.title = title;
        }
        if (message.isEmpty()) {
            return;
        }
        this.message = message;
    }

    public BBDNotificationBase(int i, String str, String str2, String str3) {
        this(i, str);
        this.title = str2;
        this.message = str3;
    }
}
