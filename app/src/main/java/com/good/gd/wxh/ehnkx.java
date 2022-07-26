package com.good.gd.wxh;

import android.app.Activity;
import android.content.IntentSender;
import com.good.gd.ui.utils.sis.LocationInfoProvider;
import com.good.gd.ui.utils.sis.LocationUIInteractor;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/* loaded from: classes.dex */
public class ehnkx implements LocationInfoProvider {

    /* loaded from: classes.dex */
    class hbfhc implements OnCompleteListener<LocationSettingsResponse> {
        final /* synthetic */ String dbjc;
        final /* synthetic */ int jwxax;
        final /* synthetic */ LocationUIInteractor qkduk;

        hbfhc(String str, LocationUIInteractor locationUIInteractor, int i) {
            this.dbjc = str;
            this.qkduk = locationUIInteractor;
            this.jwxax = i;
        }

        @Override // com.google.android.gms.tasks.OnCompleteListener
        public void onComplete(Task<LocationSettingsResponse> task) {
            try {
                if (task != null) {
                    task.getResult(ApiException.class);
                } else {
                    com.good.gd.kloes.hbfhc.ztwf("ehnkx", this.dbjc + " : Location Settings Response On Complete Listener Task is Null.");
                }
                com.good.gd.idl.hbfhc.pqq().qkduk(true);
                if (this.qkduk.getLocationDialogListener() != null) {
                    this.qkduk.getLocationDialogListener().onLocationSettingsUpdated(true);
                }
                if (ehnkx.this != null) {
                    if (true != com.good.gd.whhmi.yfdke.ztwf()) {
                        if (com.good.gd.idl.hbfhc.pqq().odlf()) {
                            this.qkduk.requestLocationPermission();
                            return;
                        }
                        com.good.gd.kloes.hbfhc.jwxax("ehnkx", "Location Services is already enabled and upgraded, But GEO-entitlement is disabled in between.");
                        this.qkduk.requestCloseView();
                        return;
                    }
                    this.qkduk.requestCloseView();
                    return;
                }
                throw null;
            } catch (ApiException e) {
                int statusCode = e.getStatusCode();
                if (statusCode != 6) {
                    if (statusCode != 8502) {
                        return;
                    }
                    com.good.gd.kloes.hbfhc.ztwf("ehnkx", this.dbjc + " : Settings Change Unavailable.");
                    this.qkduk.requestCloseView();
                    return;
                }
                com.good.gd.kloes.hbfhc.jwxax("ehnkx", this.dbjc + " : Resolution Required for Location Settings.");
                try {
                    ((ResolvableApiException) e).startResolutionForResult(this.qkduk.getActivity(), this.jwxax);
                } catch (IntentSender.SendIntentException e2) {
                    com.good.gd.kloes.hbfhc.ztwf("ehnkx", this.dbjc + " : Intent Sender Exception. " + e2.getLocalizedMessage());
                    this.qkduk.requestCloseView();
                } catch (ClassCastException e3) {
                    com.good.gd.kloes.hbfhc.ztwf("ehnkx", this.dbjc + " : Class cast Exception. " + e3.getLocalizedMessage());
                    this.qkduk.requestCloseView();
                }
            } catch (Exception e4) {
                com.good.gd.kloes.hbfhc.ztwf("ehnkx", this.dbjc + " : Exception Occur. " + e4.getLocalizedMessage());
                this.qkduk.requestCloseView();
            }
        }
    }

    @Override // com.good.gd.ui.utils.sis.LocationInfoProvider
    public boolean checkIfLocationServicesEnabled() {
        return com.good.gd.daq.hbfhc.dbjc();
    }

    @Override // com.good.gd.ui.utils.sis.LocationInfoProvider
    public void checkLocationSettingsWithGoogleAPI(LocationUIInteractor locationUIInteractor, int i) {
        String locationTag = locationUIInteractor.getLocationTag();
        com.good.gd.kloes.hbfhc.jwxax("ehnkx", locationTag + ": Check device location services and mode with Google API.");
        com.good.gd.xohxj.hbfhc qkduk = com.good.gd.daq.hbfhc.qkduk();
        if (qkduk instanceof com.good.gd.xohxj.fdyxd) {
            LocationRequest jsgtu = ((com.good.gd.xohxj.fdyxd) qkduk).jsgtu();
            if (jsgtu != null) {
                LocationSettingsRequest.Builder addLocationRequest = new LocationSettingsRequest.Builder().addLocationRequest(jsgtu);
                if (addLocationRequest != null) {
                    SettingsClient settingsClient = LocationServices.getSettingsClient(locationUIInteractor.getActivity());
                    if (settingsClient != null) {
                        Task<LocationSettingsResponse> checkLocationSettings = settingsClient.checkLocationSettings(addLocationRequest.build());
                        if (checkLocationSettings != null) {
                            checkLocationSettings.addOnCompleteListener(new hbfhc(locationTag, locationUIInteractor, i));
                            return;
                        }
                        com.good.gd.kloes.hbfhc.ztwf("ehnkx", locationTag + " : Location Settings Response is Null.");
                        locationUIInteractor.requestCloseView();
                        return;
                    }
                    com.good.gd.kloes.hbfhc.ztwf("ehnkx", locationTag + " : Location Settings Client is Null.");
                    locationUIInteractor.requestCloseView();
                    return;
                }
                com.good.gd.kloes.hbfhc.ztwf("ehnkx", locationTag + " : Location Settings Request Builder is Null.");
                locationUIInteractor.requestCloseView();
                return;
            }
            com.good.gd.kloes.hbfhc.ztwf("ehnkx", locationTag + " : Location Request is Null.");
            locationUIInteractor.requestCloseView();
            return;
        }
        com.good.gd.kloes.hbfhc.ztwf("ehnkx", locationTag + " : Can check device location services or mode directly for Google Location API only.");
        locationUIInteractor.requestCloseView();
    }

    @Override // com.good.gd.ui.utils.sis.LocationInfoProvider
    public String getLocationProviderAPIName() {
        com.good.gd.xohxj.hbfhc qkduk = com.good.gd.daq.hbfhc.qkduk();
        return qkduk == null ? "" : qkduk.jwxax();
    }

    @Override // com.good.gd.ui.utils.sis.LocationInfoProvider
    public boolean isAppLocationPermissionGranted() {
        return com.good.gd.whhmi.yfdke.ztwf();
    }

    @Override // com.good.gd.ui.utils.sis.LocationInfoProvider
    public boolean isDenyOptionSelectedExplicitly() {
        return com.good.gd.daq.hbfhc.qkduk().ztwf();
    }

    @Override // com.good.gd.ui.utils.sis.LocationInfoProvider
    public boolean isGeoLocationEntitlementEnabled() {
        return com.good.gd.idl.hbfhc.pqq().odlf();
    }

    @Override // com.good.gd.ui.utils.sis.LocationInfoProvider
    public boolean isGoogleLocationProvider() {
        return com.good.gd.daq.hbfhc.qkduk() instanceof com.good.gd.xohxj.fdyxd;
    }

    @Override // com.good.gd.ui.utils.sis.LocationInfoProvider
    public boolean isNeverAskAgainOptionSelectedSystemCheck(Activity activity) {
        return true != activity.shouldShowRequestPermissionRationale("android.permission.ACCESS_FINE_LOCATION");
    }

    @Override // com.good.gd.ui.utils.sis.LocationInfoProvider
    public void setDenyOptionSelectedExplicitly(boolean z) {
        com.good.gd.daq.hbfhc.qkduk().dbjc(z);
    }

    @Override // com.good.gd.ui.utils.sis.LocationInfoProvider
    public void setNeverAskAgainOptionSelected(boolean z) {
        com.blackberry.bis.core.yfdke.sbesx().dbjc("isNeverAskAgainSelected", z);
    }
}
