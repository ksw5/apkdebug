package com.good.gd.ndkproxy;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import com.bold360.natwest.UtilsKt;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.net.NetworkStateMonitor;
import com.good.gd.net.WifiConnectionListener;
import com.good.gd.service.GDActivityStateManager;
import com.good.gd.utils.GDLocalizer;
import com.good.gd.utils.GDMemoryProfiling;
import com.good.gd.utils.GDNDKLibraryLoader;
import com.good.gd.utils.UserAuthUtils;
import com.good.gt.deviceid.BBDDeviceID;
import com.good.gt.deviceid.BBDDeviceIDCallback;
import com.good.gt.ndkproxy.icc.IccActivity;
import com.good.gt.ndkproxy.util.GTUtils;
import com.good.gt.util.StrictModeManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/* loaded from: classes.dex */
public final class GDDeviceInfo implements BBDDeviceIDCallback, WifiConnectionListener {
    private static final String DEBUG_PREFENECES_ID = "com.good.gd.debug";
    private static final String LOCAL_DEVICE_ID_KEY = "localdeviceid";
    private static final int MCC_LENGTH = 3;
    private static final int MNC_MIN_LENGTH = 5;
    private static final String PREFERENCES_ID = "com.good.gd";
    static GDDeviceInfo _instance = null;
    private static Object _isInitialised = new Object();
    private static final String macAddressFileName = "gd_mac";
    private BBDDeviceIDCallback mCallback;
    private boolean mIsInitialized = false;
    private TelephonyManager mTelephonyManager = null;

    static {
        GDNDKLibraryLoader.loadNDKLibrary();
    }

    private GDDeviceInfo() {
        ndkInit();
    }

    private String Get_ApplicationDataDir() {
        return GDContext.getInstance().getApplicationContext().getDir(UtilsKt.Data, 0).getAbsolutePath();
    }

    private native String computeLocalDeviceId();

    private boolean deleteRecursively(File file) {
        if (file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                if (!deleteRecursively(file2)) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    private String getData(String str) {
        return (str.isEmpty() || GDContext.getInstance().getApplicationContext() == null) ? "" : GDContext.getInstance().getApplicationContext().getSharedPreferences(DEBUG_PREFENECES_ID, 0).getString(str, "");
    }

    public static GDDeviceInfo getInstance() {
        if (_instance == null) {
            _instance = new GDDeviceInfo();
        }
        return _instance;
    }

    private boolean getIsFeaturePC() {
        if (Build.VERSION.SDK_INT >= 27) {
            return GDContext.getInstance().getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.type.pc");
        }
        return false;
    }

    private boolean getIsIccIdle() {
        return GDActivityStateManager.getInstance().isIccManagerStateIdle();
    }

    private boolean getIsInBackground() {
        return GDActivityStateManager.getInstance().inBackground();
    }

    private boolean getIsInForeground() {
        return GDActivityStateManager.getInstance().inForeground();
    }

    private String getMobileCountryCode() {
        if (this.mTelephonyManager.getSimState() == 5) {
            String simOperator = this.mTelephonyManager.getSimOperator();
            if (simOperator != null && simOperator.length() >= 3) {
                return simOperator.substring(0, 3);
            }
            GDLog.DBGPRINTF(12, "GDDeviceInfo: MCC is malformed (not enough digits): " + simOperator + "\n");
            return "";
        }
        GDLog.DBGPRINTF(12, "GDDeviceInfo: Can't get MCC, SIM not ready \n");
        return "";
    }

    private String getMobileNetworkCode() {
        if (this.mTelephonyManager.getSimState() == 5) {
            String simOperator = this.mTelephonyManager.getSimOperator();
            if (simOperator != null && simOperator.length() >= 5) {
                return simOperator.substring(3);
            }
            GDLog.DBGPRINTF(12, "GDDeviceInfo: MCC is malformed (not enough digits): " + simOperator + "\n");
            return "";
        }
        GDLog.DBGPRINTF(12, "GDDeviceInfo: Can't get MNC, SIM not ready \n");
        return "";
    }

    private String getNetworkCarrier() {
        String simOperatorName = this.mTelephonyManager.getSimOperatorName();
        if ((simOperatorName == null || simOperatorName.length() == 0) && !this.mTelephonyManager.isNetworkRoaming()) {
            simOperatorName = this.mTelephonyManager.getNetworkOperatorName();
        }
        GDLog.DBGPRINTF(16, "GDDeviceInfo.getNetworkCarrier: returning " + simOperatorName + "\n");
        return simOperatorName != null ? simOperatorName : "";
    }

    private String getNetworkMobileCountryCode() {
        String networkOperator = this.mTelephonyManager.getNetworkOperator();
        GDLog.DBGPRINTF(16, "GDDeviceInfo.getNetworkMobileCountryCode: returning " + networkOperator + "\n");
        if (networkOperator != null) {
            if (networkOperator.length() >= 3) {
                return networkOperator.substring(0, 3);
            }
            GDLog.DBGPRINTF(12, "GDDeviceInfo: MCC is malformed (not enough digits): " + networkOperator + "\n");
            return "";
        } else if (this.mTelephonyManager.getSimState() == 5) {
            return "";
        } else {
            GDLog.DBGPRINTF(12, "GDDeviceInfo: Can't get MCC, SIM not ready \n");
            return "";
        }
    }

    private String getNetworkMobileNetworkCode() {
        String networkOperator = this.mTelephonyManager.getNetworkOperator();
        GDLog.DBGPRINTF(16, "GDDeviceInfo.getNetworkMobileNetworkCode: returning " + networkOperator + "\n");
        if (networkOperator != null) {
            if (networkOperator.length() >= 5) {
                return networkOperator.substring(3);
            }
            GDLog.DBGPRINTF(12, "GDDeviceInfo: MCC is malformed (not enough digits): " + networkOperator + "\n");
            return "";
        } else if (this.mTelephonyManager.getSimState() == 5) {
            return "";
        } else {
            GDLog.DBGPRINTF(12, "GDDeviceInfo: Can't get MNC, SIM not ready \n");
            return "";
        }
    }

    private String getTime() {
        Date time = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyy HH:mm:ss z");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat.format(time);
    }

    private boolean isLauncherEnabled() {
        try {
            Class.forName("com.good.launcher.LauncherConstants", false, getClass().getClassLoader());
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private boolean isWiFiConnected() {
        return NetworkStateMonitor.getInstance().isWiFiConnected();
    }

    private native void ndkInit();

    private void printRemoteLockLog() {
        Context applicationContext = GDContext.getInstance().getApplicationContext();
        if (applicationContext != null) {
            File file = new File(applicationContext.getFilesDir() + "/bbdremotelock.txt");
            if (file.exists()) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                    try {
                        GDLog.DBGPRINTF(16, "\n REMOTE_LOCK_LOG_BEGIN \n");
                        for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
                            GDLog.DBGPRINTF(16, readLine + "\n");
                        }
                        GDLog.DBGPRINTF(16, "\n REMOTE_LOCK_LOG_END \n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fileInputStream.close();
                    bufferedReader.close();
                    return;
                } catch (FileNotFoundException e2) {
                    e2.printStackTrace();
                    return;
                } catch (IOException e3) {
                    e3.printStackTrace();
                    return;
                }
            }
            GDLog.DBGPRINTF(16, "printRemoteLockLog() NOT FOUND\n");
        }
    }

    private boolean removeData(String str) {
        if (str.isEmpty() || GDContext.getInstance().getApplicationContext() == null) {
            return false;
        }
        SharedPreferences.Editor edit = GDContext.getInstance().getApplicationContext().getSharedPreferences(DEBUG_PREFENECES_ID, 0).edit();
        edit.remove(str);
        edit.apply();
        return true;
    }

    private boolean saveLogData() {
        Context applicationContext = GDContext.getInstance().getApplicationContext();
        if (applicationContext != null) {
            File file = new File(applicationContext.getFilesDir() + "/bbdremotelock.txt");
            if (file.exists()) {
                file.delete();
            }
            try {
                file.createNewFile();
                GDLog.DBGPRINTF(16, "saveLogData() logFile:" + file.getAbsolutePath() + "\n");
                try {
                    Runtime.getRuntime().exec("logcat -d -f " + file.getAbsolutePath());
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return false;
    }

    private boolean setData(String str, String str2) {
        if (str.isEmpty() || str2.isEmpty() || GDContext.getInstance().getApplicationContext() == null) {
            return false;
        }
        SharedPreferences.Editor edit = GDContext.getInstance().getApplicationContext().getSharedPreferences(DEBUG_PREFENECES_ID, 0).edit();
        edit.putString(str, str2);
        edit.apply();
        return true;
    }

    private void setInitialized() {
        this.mIsInitialized = true;
    }

    private native void setProvDeviceId(String str);

    private boolean wipePlatformSpecificItems() {
        Context applicationContext = GDContext.getInstance().getApplicationContext();
        GDLog.DBGPRINTF(16, "GDDeviceInfo: deleteBBDDeviceID\n");
        if (applicationContext != null) {
            SharedPreferences.Editor edit = applicationContext.getSharedPreferences("com.good.gd", 0).edit();
            edit.remove(LOCAL_DEVICE_ID_KEY);
            edit.commit();
            applicationContext.deleteSharedPreferences("com.good.gd");
        }
        new BBDDeviceID().deleteBBDDeviceID();
        return true;
    }

    public boolean Do_RecursiveDirectoryDelete(String str) {
        return deleteRecursively(new File(str));
    }

    public String getClientVersion() {
        return SDKVersionNative.getSDKVersion();
    }

    public native String getDeviceId();

    public void initDeviceId() {
        GDLog.DBGPRINTF(16, "GDDeviceInfo: initDeviceId\n");
        new BBDDeviceID().getBBDDeviceID(this, UserAuthUtils.isActivated());
    }

    public String initLocalDeviceId() throws Exception {
        SharedPreferences sharedPreferences = GDContext.getInstance().getApplicationContext().getSharedPreferences("com.good.gd", 0);
        String string = sharedPreferences.getString(LOCAL_DEVICE_ID_KEY, null);
        if (string == null || string.length() <= 0) {
            String computeLocalDeviceId = computeLocalDeviceId();
            sharedPreferences.edit().putString(LOCAL_DEVICE_ID_KEY, computeLocalDeviceId).apply();
            return computeLocalDeviceId;
        }
        return string;
    }

    public void initialize() throws Exception {
        String str;
        TelephonyManager telephonyManager = (TelephonyManager) GDContext.getInstance().getApplicationContext().getSystemService("phone");
        this.mTelephonyManager = telephonyManager;
        if (telephonyManager != null) {
            StrictModeManager.getInstance().permitDiskReads();
            String initLocalDeviceId = initLocalDeviceId();
            GDRamInfo.getRAMInfo();
            String internalMemoryInfo = GDMemoryProfiling.getInternalMemoryInfo();
            String time = getTime();
            String str2 = isSimulator() ? "emulator-" + Build.VERSION.RELEASE : Build.MODEL;
            PackageManager packageManager = GDContext.getInstance().getApplicationContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(GDContext.getInstance().getApplicationContext().getPackageName(), 0);
            String str3 = packageInfo.versionName;
            if (str3 != null) {
                str = str3;
            } else {
                str = Integer.toString(packageInfo.versionCode);
            }
            String num = Integer.toString(packageInfo.versionCode);
            String charSequence = packageInfo.applicationInfo.loadLabel(packageManager) == null ? "" : packageInfo.applicationInfo.loadLabel(packageManager).toString();
            String str4 = Build.MANUFACTURER;
            initializeDeviceInfo(GDContext.getInstance().getApplicationContext().getPackageName(), "Android Device", initLocalDeviceId, str2, Build.DEVICE, System.getProperty("os.arch"), GDLocalizer.getSpecifiedLocale().toString(), GDLocalizer.getEffectiveLanguage(), "NativeContainer", "Android", Build.VERSION.RELEASE, GDContext.getInstance().getApplicationContext().getFilesDir().getAbsolutePath(), GDContext.getInstance().getApplicationContext().getCacheDir().getAbsolutePath(), Get_ApplicationDataDir(), isSimulator(), isICCSupported(), charSequence, str, num, str4 == null ? "" : str4, internalMemoryInfo, time, Build.VERSION.SDK_INT, Build.FINGERPRINT, Build.VERSION.SECURITY_PATCH);
            setInitialized();
            GDLog.DBGPRINTF(16, "GDDeviceInfo: Initialized (deviceId=" + getDeviceId() + ")\n");
            StrictModeManager.getInstance().restoreStrictModeThreadPolicy();
            return;
        }
        throw new Exception("Can't get TelephonyManager");
    }

    native void initializeDeviceInfo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, boolean z, boolean z2, String str15, String str16, String str17, String str18, String str19, String str20, int i, String str21, String str22);

    public boolean isICCSupported() {
        try {
            ActivityInfo[] activityInfoArr = GDContext.getInstance().getApplicationContext().getPackageManager().getPackageInfo(GDContext.getInstance().getApplicationContext().getPackageName(), 1).activities;
            if (activityInfoArr != null) {
                for (ActivityInfo activityInfo : activityInfoArr) {
                    if (activityInfo.name.equals(IccActivity.class.getName())) {
                        return true;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }

    public boolean isInitialized() {
        return this.mIsInitialized;
    }

    public boolean isSimulator() {
        return GTUtils.isSimulator();
    }

    public void obtainDeviceID(BBDDeviceIDCallback bBDDeviceIDCallback) {
        this.mCallback = bBDDeviceIDCallback;
        initDeviceId();
    }

    @Override // com.good.gt.deviceid.BBDDeviceIDCallback
    public void onDeviceID(String str) {
        GDLog.DBGPRINTF(16, "GTDeviceID: GDDeviceInfo onDeviceID deviceID= " + str + "\n");
        setProvDeviceId(str);
        this.mCallback.onDeviceID(str);
    }

    @Override // com.good.gd.net.WifiConnectionListener
    public native void setIsWifiConnected(boolean z);
}
