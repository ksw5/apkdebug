package com.good.gd.service.interception;

import com.good.gd.GDAndroid;
import com.good.gd.GDServiceProvider;
import com.good.gd.GDServiceType;
import com.good.gd.error.GDNotAuthorizedError;
import com.good.gd.utils.ServiceProvidersHelper;
import java.util.List;

/* loaded from: classes.dex */
public class ServiceProvidersImpl implements ServiceProvidersHelper {
    @Override // com.good.gd.utils.ServiceProvidersHelper
    public List<GDServiceProvider> getServiceProvidersFor(String str, String str2, GDServiceType gDServiceType) throws GDNotAuthorizedError {
        return GDAndroid.getInstance().getServiceProvidersFor(str, str2, gDServiceType);
    }
}
