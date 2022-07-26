package com.good.gd.notifications.items;

/* loaded from: classes.dex */
public class GroupHeaderNotification extends PlatformNotification {
    public static final Integer TYPE_ID = -1;
    public static final String UNIQUE_ID = "CommonHeader";

    public GroupHeaderNotification() {
        super(TYPE_ID.intValue(), UNIQUE_ID, "Apps requires actions to be done", "Apps requires actions to be done");
        setGroupHeader(true);
    }
}
