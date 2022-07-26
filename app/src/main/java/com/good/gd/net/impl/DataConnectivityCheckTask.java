package com.good.gd.net.impl;

import android.os.AsyncTask;
import android.os.Build;
import com.good.gd.GDThreadPoolExecutorHelper;
import com.good.gd.ndkproxy.GDLog;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes.dex */
public class DataConnectivityCheckTask extends AsyncTask<String, String, DataConnectivityCheckResult> {
    private static final String EXCEPTION_IGNORE_MESSAGE = "Cleartext HTTP traffic";
    private static final int WALLED_GARDEN_SOCKET_TIMEOUT_MS = 10000;
    private static final String WALLED_GARDEN_URL_HTTP = "http://connectivitycheck.gstatic.com/generate_204";
    private static final String WALLED_GARDEN_URL_HTTPS = "https://connectivitycheck.gstatic.com/generate_204";
    private DataConnectivityCheckObserver mObserver;

    private DataConnectivityCheckTask(DataConnectivityCheckObserver dataConnectivityCheckObserver) {
        this.mObserver = dataConnectivityCheckObserver;
    }

    private static String getWalledGardenUrl() {
        return Build.VERSION.SDK_INT < 28 ? WALLED_GARDEN_URL_HTTP : WALLED_GARDEN_URL_HTTPS;
    }

    public static boolean submit(DataConnectivityCheckObserver dataConnectivityCheckObserver) {
        boolean z = false;
        try {
            if (dataConnectivityCheckObserver != null) {
                new DataConnectivityCheckTask(dataConnectivityCheckObserver).executeOnExecutor(GDThreadPoolExecutorHelper.GD_THREAD_POOL_EXECUTOR, new String[0]);
                z = true;
            } else {
                GDLog.DBGPRINTF(12, "DataConnectivityCheckTask null observer\n");
            }
        } catch (RejectedExecutionException e) {
            GDLog.DBGPRINTF(12, "DataConnectivityCheckTask unable to submit task to executor\n");
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Not initialized variable reg: 3, insn: 0x0107: MOVE  (r2 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:30:0x0107 */
    /* JADX WARN: Removed duplicated region for block: B:32:0x010a  */
    @Override // android.os.AsyncTask
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DataConnectivityCheckResult doInBackground(String... r9) {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.net.impl.DataConnectivityCheckTask.doInBackground(java.lang.String[]):com.good.gd.net.impl.DataConnectivityCheckResult");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(DataConnectivityCheckResult dataConnectivityCheckResult) {
        super.onPostExecute((DataConnectivityCheckTask) dataConnectivityCheckResult);
        GDLog.DBGPRINTF(14, "DataConnectivityCheckTask dataConnectivity = " + dataConnectivityCheckResult.isAvailable() + "\n");
        this.mObserver.dataConnectivityCheckResult(dataConnectivityCheckResult);
    }
}
