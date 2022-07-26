package com.good.gd.bypass;

/* loaded from: classes.dex */
public interface GDBypassAbility {
    void addBypassPolicyListener(BypassPolicyListener bypassPolicyListener);

    boolean isBypassActivity(String str);

    boolean isBypassAllowed();

    void removeBypassPolicyListener(BypassPolicyListener bypassPolicyListener);
}
