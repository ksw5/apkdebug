package com.good.gd.notifications;

import android.app.NotificationManager;
import android.content.Context;
import android.service.notification.StatusBarNotification;
import com.good.gd.notifications.items.BBDNotificationBase;
import com.good.gd.notifications.items.GroupHeaderNotification;
import com.good.gd.utils.GDLocalizer;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/* loaded from: classes.dex */
public class BBDNotificationManager {
    private static final String CHANNEL_NAME_KEY = "BBSDK notifications channel";
    private static final String DESCRIPTION_KEY = "BBSDK notifications descr";
    private static BBDNotificationManager instance;
    private NotificationManager androidNotificationManager;
    private WeakReference<Context> appContext;
    private String channelId = "com.good.gd.notifications.channel";
    private int importance = 5;
    private int sizeToShowHeader = 1;
    private Map<String, BBDNotificationBase> notifications = new HashMap();

    private BBDNotificationManager(Context context) {
        this.appContext = new WeakReference<>(context);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        this.androidNotificationManager = notificationManager;
        if (notificationManager == null) {
            return;
        }
        NotificationHelper.createChannel(this.androidNotificationManager, GDLocalizer.getLocalizedString(CHANNEL_NAME_KEY), this.channelId, GDLocalizer.getLocalizedString(DESCRIPTION_KEY), this.importance);
    }

    private void closeHeaderIfPresent() {
        BBDNotificationBase bBDNotificationBase;
        if (this.androidNotificationManager == null || this.notifications.size() > this.sizeToShowHeader || (bBDNotificationBase = this.notifications.get(GroupHeaderNotification.UNIQUE_ID)) == null) {
            return;
        }
        bBDNotificationBase.close(this.androidNotificationManager);
        this.notifications.remove(GroupHeaderNotification.UNIQUE_ID);
    }

    public static BBDNotificationManager createInstance(Context context) {
        if (instance == null) {
            synchronized (BBDNotificationManager.class) {
                if (instance == null) {
                    instance = new BBDNotificationManager(context);
                }
            }
        }
        return instance;
    }

    public static BBDNotificationManager getInstance() {
        return instance;
    }

    private void requestHeaderIfRequired() {
        if (this.notifications.get(GroupHeaderNotification.UNIQUE_ID) == null && this.notifications.size() >= this.sizeToShowHeader) {
            GroupHeaderNotification groupHeaderNotification = new GroupHeaderNotification();
            Context context = this.appContext.get();
            if (context == null) {
                return;
            }
            groupHeaderNotification.show(context, this.androidNotificationManager, this.channelId);
            this.notifications.put(GroupHeaderNotification.UNIQUE_ID, groupHeaderNotification);
        }
    }

    public boolean closeNotification(String str) {
        BBDNotificationBase bBDNotificationBase;
        if (this.androidNotificationManager == null || (bBDNotificationBase = this.notifications.get(str)) == null) {
            return false;
        }
        bBDNotificationBase.close(this.androidNotificationManager);
        this.notifications.remove(str);
        closeHeaderIfPresent();
        return true;
    }

    public boolean closeSimilar(int i) {
        StatusBarNotification[] activeNotifications;
        if (this.androidNotificationManager == null) {
            return false;
        }
        LinkedList linkedList = new LinkedList();
        for (BBDNotificationBase bBDNotificationBase : this.notifications.values()) {
            if (bBDNotificationBase.getNotificationType() == i) {
                linkedList.add(bBDNotificationBase.getCoreId());
            }
        }
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            closeNotification((String) it.next());
        }
        for (StatusBarNotification statusBarNotification : this.androidNotificationManager.getActiveNotifications()) {
            int id = statusBarNotification.getId();
            String tag = statusBarNotification.getTag();
            if (id == i && tag != null && tag.startsWith(BBDNotificationBase.SDK_NOTIFICATION_TAG)) {
                this.androidNotificationManager.cancel(tag, id);
            }
        }
        closeHeaderIfPresent();
        return true;
    }

    public boolean showNotification(BBDNotificationBase bBDNotificationBase) {
        if (this.androidNotificationManager == null) {
            return false;
        }
        bBDNotificationBase.show(this.appContext.get(), this.androidNotificationManager, this.channelId);
        this.notifications.put(bBDNotificationBase.getCoreId(), bBDNotificationBase);
        requestHeaderIfRequired();
        return true;
    }

    public boolean updateNotification(String str) {
        BBDNotificationBase bBDNotificationBase;
        if (this.androidNotificationManager == null || (bBDNotificationBase = this.notifications.get(str)) == null) {
            return false;
        }
        return bBDNotificationBase.update(this.appContext.get(), this.androidNotificationManager);
    }
}
