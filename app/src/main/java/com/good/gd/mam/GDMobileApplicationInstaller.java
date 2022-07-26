package com.good.gd.mam;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.AsyncTask;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.core.content.FileProvider;
import com.good.gd.GDResult;
import com.good.gd.apache.http.HttpEntity;
import com.good.gd.apache.http.client.methods.HttpGet;
import com.good.gd.context.GDContext;
import com.good.gd.database.sqlite.SQLiteDatabase;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.net.GDHttpClient;
import com.good.gt.context.GTBaseContext;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;

/* loaded from: classes.dex */
public class GDMobileApplicationInstaller {
    private static final String MAM_SUPPORT_FILEPROVIDER = ".bbdsdk.mamsupport.provider";
    private static GDMobileApplicationInstaller s_instance;
    private HashMap<String, String> m_ActiveInstalls = new HashMap<>();
    private Object m_packageManagerSync = new Object();
    private GDMobileApplicationInstallerBroadcastReceiver m_broadcastReceiver = new GDMobileApplicationInstallerBroadcastReceiver();

    /* loaded from: classes.dex */
    static class GDMobileApplicationInstallerBroadcastReceiver extends BroadcastReceiver {
        public GDMobileApplicationInstallerBroadcastReceiver() {
            GDLog.DBGPRINTF(16, "InstallableBroadcastReceiver::constructor:");
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String str = null;
            if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                Uri data = intent.getData();
                if (data != null) {
                    str = data.getSchemeSpecificPart();
                }
                intent.getIntExtra("android.intent.extra.UID", 0);
                if (str == null) {
                    return;
                }
                GDLog.DBGPRINTF(16, "InstallableBroadcastReceiver::onReceive: " + str + " was successfully installed");
                GDMobileApplicationInstaller.getInstance().removeInstallable(str);
            } else if (!"android.intent.action.PACKAGE_CHANGED".equals(action)) {
            } else {
                Uri data2 = intent.getData();
                if (data2 != null) {
                    str = data2.getSchemeSpecificPart();
                }
                intent.getIntExtra("android.intent.extra.UID", 0);
                if (str == null) {
                    return;
                }
                GDLog.DBGPRINTF(16, "InstallableBroadcastReceiver::onReceive: " + str + " was successfully updated");
                GDMobileApplicationInstaller.getInstance().removeInstallable(str);
            }
        }
    }

    /* loaded from: classes.dex */
    static class hbfhc extends AsyncTask<String, Integer, Void> {
        private int dbjc;
        private String jwxax;
        private GDMobileApplicationManagementListener lqox;
        private File wxau;
        private SecureRandom qkduk = new SecureRandom();
        private boolean ztwf = false;

        hbfhc(int i, GDMobileApplicationManagementListener gDMobileApplicationManagementListener) {
            this.dbjc = i;
            this.lqox = gDMobileApplicationManagementListener;
        }

        private GDResult dbjc() {
            GDResult gDResult = new GDResult(0);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            intent.addFlags(BasicMeasure.EXACTLY);
            Context applicationContext = GTBaseContext.getInstance().getApplicationContext();
            GDLog.DBGPRINTF(16, "GDInstallerTask::onPostExecute: using content Uri\n");
            Uri uriForFile = FileProvider.getUriForFile(applicationContext, applicationContext.getPackageName() + GDMobileApplicationInstaller.MAM_SUPPORT_FILEPROVIDER, this.wxau);
            intent.addFlags(1);
            GDLog.DBGPRINTF(16, "GDInstallerTask::onPostExecute: fileUri = " + uriForFile + "\n");
            intent.setDataAndType(uriForFile, "application/vnd.android.package-archive");
            applicationContext.startActivity(intent);
            return gDResult;
        }

        @Override // android.os.AsyncTask
        protected Void doInBackground(String[] strArr) {
            String[] strArr2 = strArr;
            GDLog.DBGPRINTF(16, "GDInstallerTask::doInBackground");
            try {
                boolean z = false;
                URL url = new URL(strArr2[0]);
                GDLog.DBGPRINTF(16, "GDInstallerTask::doInBackground url: " + url);
                HttpEntity entity = new GDHttpClient().execute(new HttpGet(url.toURI())).getEntity();
                if (entity == null) {
                    return null;
                }
                long contentLength = entity.getContentLength();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(entity.getContent(), 10240);
                this.jwxax = new BigInteger(130, this.qkduk).toString(32);
                File file = new File(GDContext.getInstance().getApplicationContext().getExternalCacheDir().getPath() + "/" + this.jwxax + ".apk");
                this.wxau = file;
                if (file.exists()) {
                    this.wxau.delete();
                } else if (!this.wxau.createNewFile()) {
                    GDLog.DBGPRINTF(12, "GDInstallerTask::doInBackground failed to create: " + this.wxau.getPath());
                }
                GDLog.DBGPRINTF(16, "GDInstallerTask::doInBackground filename: " + this.wxau.getPath());
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(this.wxau));
                byte[] bArr = new byte[1024];
                long j = 0;
                while (true) {
                    int read = bufferedInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    j += read;
                    publishProgress(Integer.valueOf((int) ((100 * j) / contentLength)));
                    bufferedOutputStream.write(bArr, 0, read);
                }
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                bufferedInputStream.close();
                if (this.wxau.length() > 0) {
                    z = true;
                }
                this.ztwf = z;
                GDLog.DBGPRINTF(16, "GDInstallerTask::doInBackground download completed");
                return null;
            } catch (Exception e) {
                GDLog.DBGPRINTF(12, "GDInstallerTask::doInBackground: exception thrown whilst trying to download installer");
                e.printStackTrace();
                return null;
            }
        }

        @Override // android.os.AsyncTask
        protected void onPostExecute(Void r4) {
            GDResult gDResult;
            GDLog.DBGPRINTF(16, "GDInstallerTask::onPostExecute");
            if (this.ztwf) {
                GDMobileApplicationInstaller.getInstance().addInstallableFile(this.wxau);
                try {
                    gDResult = dbjc();
                } catch (Exception e) {
                    GDLog.DBGPRINTF(12, "GDInstallerTask::onPostExecute: exception thrown whilst trying to install the application");
                    e.printStackTrace();
                    gDResult = new GDResult(100, -3, "exception thrown whilst trying to install the application");
                }
            } else {
                gDResult = new GDResult(100, -4, "Could not download the apk");
            }
            this.lqox.onApplicationDispatchedForInstallation(this.dbjc, gDResult);
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            GDLog.DBGPRINTF(16, "GDInstallerTask::onPreExecute");
        }
    }

    private GDMobileApplicationInstaller() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        GDContext.getInstance().getApplicationContext().registerReceiver(this.m_broadcastReceiver, intentFilter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addInstallableFile(File file) {
        if (file == null) {
            GDLog.DBGPRINTF(12, "InstallableBroadcastReceiver::addInstallableFile - file is null");
            return;
        }
        PackageInfo packageArchiveInfo = GDContext.getInstance().getApplicationContext().getPackageManager().getPackageArchiveInfo(file.getAbsolutePath(), 0);
        if (packageArchiveInfo != null && packageArchiveInfo.packageName != null) {
            synchronized (this.m_packageManagerSync) {
                GDLog.DBGPRINTF(16, "InstallableBroadcastReceiver::addInstallableFile: package: " + packageArchiveInfo.packageName + " at path: " + file.getAbsolutePath());
                this.m_ActiveInstalls.put(packageArchiveInfo.packageName, file.getAbsolutePath());
            }
            return;
        }
        GDLog.DBGPRINTF(12, "InstallableBroadcastReceiver::addInstallableFile: unable to add file: " + file.getAbsolutePath() + " info: " + packageArchiveInfo);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean checkMAMSupportIsInstalled(Context context) {
        String str = context.getPackageName() + MAM_SUPPORT_FILEPROVIDER;
        GDLog.DBGPRINTF(16, "GDMobileApplicationInstaller: authorities = " + str + "\n");
        return context.getPackageManager().resolveContentProvider(str, 128) != null;
    }

    public static synchronized GDMobileApplicationInstaller getInstance() {
        GDMobileApplicationInstaller gDMobileApplicationInstaller;
        synchronized (GDMobileApplicationInstaller.class) {
            if (s_instance == null) {
                s_instance = new GDMobileApplicationInstaller();
            }
            gDMobileApplicationInstaller = s_instance;
        }
        return gDMobileApplicationInstaller;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeInstallable(String str) {
        synchronized (this.m_packageManagerSync) {
            if (this.m_ActiveInstalls.containsKey(str)) {
                String str2 = this.m_ActiveInstalls.get(str);
                File file = new File(str2);
                GDLog.DBGPRINTF(16, "InstallableBroadcastReceiver::removeInstallable: package: " + str + " at path: " + str2);
                if (file.exists()) {
                    if (file.delete()) {
                        GDLog.DBGPRINTF(16, "InstallableBroadcastReceiver::deleteFileForPackageAndUid: apk file deleted");
                        this.m_ActiveInstalls.remove(str);
                    } else {
                        GDLog.DBGPRINTF(12, "InstallableBroadcastReceiver::deleteFileForPackageAndUid: error whilst trying to delete the download apk file");
                    }
                }
            }
        }
    }

    public void install(int i, String str, GDMobileApplicationManagementListener gDMobileApplicationManagementListener) {
        new hbfhc(i, gDMobileApplicationManagementListener).execute(str);
    }
}
