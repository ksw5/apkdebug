package com.good.gd.daq;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.core.util.Consumer;
import com.blackberry.bis.core.BlackberryAnalyticsCommon;
import com.good.gd.kloes.ehnkx;
import com.good.gd.whhmi.gioey;
import com.google.android.gms.common.GoogleApiAvailability;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class hbfhc {
    private static com.good.gd.xohxj.hbfhc dbjc;

    public static boolean dbjc() {
        Context jwxax = BlackberryAnalyticsCommon.rynix().jwxax();
        int i = Build.VERSION.SDK_INT;
        boolean z = false;
        if (28 <= i) {
            ehnkx.dbjc("AnalyticsLocationFactory", "SDK Version " + i + " is greater than or equal to Android P.");
            LocationManager locationManager = (LocationManager) jwxax.getSystemService("location");
            if (locationManager == null) {
                ehnkx.jwxax("AnalyticsLocationFactory", "Unable to instantiate LocationManager.");
                return false;
            }
            boolean isLocationEnabled = locationManager.isLocationEnabled();
            com.good.gd.kloes.hbfhc.wxau("AnalyticsLocationFactory", "Device Location Services Enabled: " + isLocationEnabled);
            return isLocationEnabled;
        } else if (19 <= i && 28 > i) {
            ehnkx.dbjc("AnalyticsLocationFactory", "SDK Version " + i + " is lower than Android P.");
            try {
                if (Settings.Secure.getInt(jwxax.getContentResolver(), "location_mode") != 0) {
                    z = true;
                }
                com.good.gd.kloes.hbfhc.wxau("AnalyticsLocationFactory", "Device Location Services Enabled: " + z);
                return z;
            } catch (Settings.SettingNotFoundException e) {
                return false;
            }
        } else {
            ehnkx.dbjc("AnalyticsLocationFactory", "SDK Version " + i + " is lower than Android Kitkat.");
            String string = Settings.Secure.getString(jwxax.getContentResolver(), "location_providers_allowed");
            if (string != null && true != TextUtils.isEmpty(string)) {
                z = true;
            }
            com.good.gd.kloes.hbfhc.wxau("AnalyticsLocationFactory", "Device Location Services Enabled: " + z);
            return z;
        }
    }

    public static boolean jwxax() {
        boolean dbjc2 = gioey.dbjc("android.location.LocationManager", "getCurrentLocation", new Class[]{String.class, CancellationSignal.class, Executor.class, Consumer.class});
        if (!dbjc2) {
            com.good.gd.kloes.hbfhc.jwxax("AnalyticsLocationFactory", "Get Current Location API is not available in this Android version, hence using alternate API.");
        }
        return dbjc2;
    }

    public static void liflu() {
        dbjc = null;
    }

    public static boolean lqox() {
        com.good.gd.kloes.hbfhc.wxau("AnalyticsLocationFactory", "Check Google Play Services.");
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        if (googleApiAvailability == null) {
            com.good.gd.kloes.hbfhc.wxau("AnalyticsLocationFactory", "Unable to create instance of Google Play Api Availability.");
            return false;
        } else if (googleApiAvailability.isGooglePlayServicesAvailable(BlackberryAnalyticsCommon.rynix().jwxax()) != 0) {
            com.good.gd.kloes.hbfhc.ztwf("AnalyticsLocationFactory", "Google Play Services Not Available.");
            return false;
        } else {
            com.good.gd.kloes.hbfhc.wxau("AnalyticsLocationFactory", "Google Play Services Available.");
            return true;
        }
    }

    public static com.good.gd.xohxj.hbfhc qkduk() {
        if (dbjc == null) {
            if (wxau()) {
                com.good.gd.kloes.hbfhc.wxau("AnalyticsLocationFactory", "Analytics Geo Location Provider Instance: Framework Location Single Update.");
                dbjc = com.good.gd.xohxj.yfdke.jsgtu();
            } else if (lqox()) {
                com.good.gd.kloes.hbfhc.wxau("AnalyticsLocationFactory", "Analytics Geo Location Provider Instance: Google Location Provider.");
                com.good.gd.xohxj.hbfhc wuird = com.good.gd.xohxj.fdyxd.wuird();
                dbjc = wuird;
                return wuird;
            } else if (gioey.dbjc("android.location.LocationManager", "requestLocationUpdates", new Class[]{String.class, Long.TYPE, Float.TYPE, LocationListener.class, Looper.class})) {
                com.good.gd.kloes.hbfhc.wxau("AnalyticsLocationFactory", "Analytics Geo Location Provider Instance: Framework Location Provider.");
                dbjc = com.good.gd.xohxj.yfdke.jsgtu();
            } else {
                com.good.gd.kloes.hbfhc.ztwf("AnalyticsLocationFactory", "Could not initiate Analytics Geo Location Provider.");
            }
        }
        return dbjc;
    }

    public static boolean wxau() {
        boolean dbjc2 = gioey.dbjc("android.location.LocationManager", "requestSingleUpdate", new Class[]{String.class, LocationListener.class, Looper.class});
        if (!dbjc2) {
            com.good.gd.kloes.hbfhc.jwxax("AnalyticsLocationFactory", "Request Single Update Location API is not available in this Android version, hence using alternate API.");
        }
        return dbjc2;
    }

    public static boolean ztwf() {
        LocationManager locationManager = (LocationManager) BlackberryAnalyticsCommon.rynix().jwxax().getSystemService("location");
        if (locationManager == null) {
            com.good.gd.kloes.hbfhc.ztwf("AnalyticsLocationFactory", "Unable to instantiate LocationManager.");
            return false;
        }
        return locationManager.isProviderEnabled("gps");
    }
}
