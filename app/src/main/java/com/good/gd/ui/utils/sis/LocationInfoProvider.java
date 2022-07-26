package com.good.gd.ui.utils.sis;

import android.app.Activity;

/* loaded from: classes.dex */
public interface LocationInfoProvider {
    boolean checkIfLocationServicesEnabled();

    void checkLocationSettingsWithGoogleAPI(LocationUIInteractor locationUIInteractor, int i);

    String getLocationProviderAPIName();

    boolean isAppLocationPermissionGranted();

    boolean isDenyOptionSelectedExplicitly();

    boolean isGeoLocationEntitlementEnabled();

    boolean isGoogleLocationProvider();

    boolean isNeverAskAgainOptionSelectedSystemCheck(Activity activity);

    void setDenyOptionSelectedExplicitly(boolean z);

    void setNeverAskAgainOptionSelected(boolean z);
}
