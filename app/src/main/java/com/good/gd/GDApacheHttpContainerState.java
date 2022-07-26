package com.good.gd;

import com.good.gd.containerstate.ContainerState;

/* loaded from: classes.dex */
public class GDApacheHttpContainerState {
    private static ContainerState containerState;

    public static ContainerState getContainerState() {
        return containerState;
    }

    public static void setContainerState(ContainerState containerState2) {
        containerState = containerState2;
    }
}
