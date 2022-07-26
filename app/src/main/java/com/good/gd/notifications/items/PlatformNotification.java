package com.good.gd.notifications.items;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import com.good.gd.notifications.NotificationHelper;
import com.good.gd.resources.R;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class PlatformNotification extends BBDNotificationBase {
    private WeakReference<Context> ctxWeakRef;
    private Notification.Builder nativeBuilder;

    public PlatformNotification(int i, String str, String str2, String str3) {
        super(i, str, str2, str3);
    }

    @Override // com.good.gd.notifications.items.BBDNotificationBase
    public void close(NotificationManager notificationManager) {
        notificationManager.cancel(createTag(), getNotificationType());
    }

    @Override // com.good.gd.notifications.items.BBDNotificationBase
    public Intent getMainAction() {
        Context context;
        WeakReference<Context> weakReference = this.ctxWeakRef;
        if (weakReference == null || (context = weakReference.get()) == null) {
            return null;
        }
        return context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
    }

    @Override // com.good.gd.notifications.items.BBDNotificationBase
    public void show(Context context, NotificationManager notificationManager, String str) {
        this.ctxWeakRef = new WeakReference<>(context);
        this.nativeBuilder = NotificationHelper.createNotificationBuilder(context, str, 5).setSmallIcon(R.drawable.cp_notification_icon).setContentTitle(getTitle()).setStyle(new Notification.BigTextStyle().bigText(getMessage())).setGroup(BBDNotificationBase.GROUP_KEY_SDK_NOTIFICATIONS).setOngoing(true).setAutoCancel(false).setOnlyAlertOnce(true).setDefaults(2).setVisibility(0).setGroupSummary(isGroupHeader());
        Intent mainAction = getMainAction();
        if (mainAction != null) {
            this.nativeBuilder.setContentIntent(getMainActionPI(context, mainAction));
        }
        notificationManager.notify(createTag(), getNotificationType(), this.nativeBuilder.build());
        this.ctxWeakRef.clear();
    }

    @Override // com.good.gd.notifications.items.BBDNotificationBase
    public boolean update(Context context, NotificationManager notificationManager) {
        updateTitleAndMessage();
        String title = getTitle();
        String message = getMessage();
        if (title == null || title.isEmpty() || message == null || message.isEmpty()) {
            return false;
        }
        this.nativeBuilder.setContentTitle(title);
        this.nativeBuilder.setStyle(new Notification.BigTextStyle().bigText(getMessage()));
        this.nativeBuilder.setGroupSummary(isGroupHeader());
        this.ctxWeakRef = new WeakReference<>(context);
        Intent mainAction = getMainAction();
        this.ctxWeakRef.clear();
        if (mainAction == null) {
            return false;
        }
        this.nativeBuilder.setContentIntent(getMainActionPI(context, mainAction));
        notificationManager.notify(createTag(), getNotificationType(), this.nativeBuilder.build());
        return true;
    }
}
