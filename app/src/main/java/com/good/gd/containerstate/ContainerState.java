package com.good.gd.containerstate;

import com.good.gd.error.GDNotAuthorizedError;

/* loaded from: classes.dex */
public interface ContainerState {
    void checkAuthorized() throws GDNotAuthorizedError;

    boolean isAuthorized();

    boolean isGDIdAllowed(String str);

    boolean isWiped();
}
