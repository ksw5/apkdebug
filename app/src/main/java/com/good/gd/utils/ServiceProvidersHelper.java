package com.good.gd.utils;

import com.good.gd.GDServiceProvider;
import com.good.gd.GDServiceType;
import com.good.gd.error.GDNotAuthorizedError;
import java.util.List;

/* loaded from: classes.dex */
public interface ServiceProvidersHelper {
    List<GDServiceProvider> getServiceProvidersFor(String str, String str2, GDServiceType gDServiceType) throws GDNotAuthorizedError;
}
