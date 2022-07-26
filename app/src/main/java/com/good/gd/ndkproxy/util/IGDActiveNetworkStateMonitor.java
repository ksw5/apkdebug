package com.good.gd.ndkproxy.util;

import com.good.gd.net.impl.ActiveNetworkInfo;

/* loaded from: classes.dex */
public interface IGDActiveNetworkStateMonitor extends INetworkStateMonitor {
    ActiveNetworkInfo getActiveNetworkInfo(boolean z);
}
