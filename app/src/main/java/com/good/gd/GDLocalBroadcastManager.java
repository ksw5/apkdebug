package com.good.gd;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.bold360.natwest.UtilsKt;
import com.good.gd.ndkproxy.GDLog;
import com.good.gt.context.GTBaseContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes.dex */
public class GDLocalBroadcastManager {
    private static final boolean DEBUG = false;
    static final int MSG_EXEC_PENDING_BROADCASTS = 1;
    private static final String TAG = "GDLocalBroadcastManager";
    private static GDLocalBroadcastManager mInstance;
    private static final Object mLock = new Object();
    private final HashMap<BroadcastReceiver, ArrayList<IntentFilter>> mReceivers = new HashMap<>();
    private final HashMap<String, ArrayList<fdyxd>> mActions = new HashMap<>();
    private final ArrayList<yfdke> mPendingBroadcasts = new ArrayList<>();
    private final Handler mHandler = new hbfhc(GTBaseContext.getInstance().getApplicationContext().getMainLooper());

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class fdyxd {
        final IntentFilter dbjc;
        boolean jwxax;
        final BroadcastReceiver qkduk;

        fdyxd(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            this.dbjc = intentFilter;
            this.qkduk = broadcastReceiver;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append("Receiver{");
            sb.append(this.qkduk);
            sb.append(" filter=");
            sb.append(this.dbjc);
            sb.append("}");
            return sb.toString();
        }
    }

    /* loaded from: classes.dex */
    class hbfhc extends Handler {
        hbfhc(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 1) {
                GDLocalBroadcastManager.this.executePendingBroadcasts();
            } else {
                super.handleMessage(message);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class yfdke {
        final Intent dbjc;
        final ArrayList<fdyxd> qkduk;

        yfdke(Intent intent, ArrayList<fdyxd> arrayList) {
            this.dbjc = intent;
            this.qkduk = arrayList;
        }
    }

    private GDLocalBroadcastManager() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executePendingBroadcasts() {
        int size;
        yfdke[] yfdkeVarArr;
        while (true) {
            synchronized (this.mReceivers) {
                size = this.mPendingBroadcasts.size();
                if (size <= 0) {
                    return;
                }
                yfdkeVarArr = new yfdke[size];
                this.mPendingBroadcasts.toArray(yfdkeVarArr);
                this.mPendingBroadcasts.clear();
            }
            for (int i = 0; i < size; i++) {
                yfdke yfdkeVar = yfdkeVarArr[i];
                for (int i2 = 0; i2 < yfdkeVar.qkduk.size(); i2++) {
                    yfdkeVar.qkduk.get(i2).qkduk.onReceive(GTBaseContext.getInstance().getApplicationContext(), yfdkeVar.dbjc);
                }
            }
        }
    }

    public static GDLocalBroadcastManager getInstance() {
        GDLocalBroadcastManager gDLocalBroadcastManager;
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = new GDLocalBroadcastManager();
            }
            gDLocalBroadcastManager = mInstance;
        }
        return gDLocalBroadcastManager;
    }

    public boolean containsReceiverAction(String str) {
        synchronized (this.mReceivers) {
            for (ArrayList<IntentFilter> arrayList : this.mReceivers.values()) {
                Iterator<IntentFilter> it = arrayList.iterator();
                while (it.hasNext()) {
                    if (it.next().hasAction(str)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        synchronized (this.mReceivers) {
            fdyxd fdyxdVar = new fdyxd(intentFilter, broadcastReceiver);
            ArrayList<IntentFilter> arrayList = this.mReceivers.get(broadcastReceiver);
            if (arrayList == null) {
                arrayList = new ArrayList<>(1);
                this.mReceivers.put(broadcastReceiver, arrayList);
            }
            arrayList.add(intentFilter);
            for (int i = 0; i < intentFilter.countActions(); i++) {
                String action = intentFilter.getAction(i);
                ArrayList<fdyxd> arrayList2 = this.mActions.get(action);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList<>(1);
                    this.mActions.put(action, arrayList2);
                }
                arrayList2.add(fdyxdVar);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v2, types: [boolean, int] */
    public boolean sendBroadcast(Intent intent) {
        int i;
        String str;
        ArrayList arrayList;
        ArrayList<fdyxd> arrayList2;
        String str2;
        int i2;
        char c;
        String str3;
        synchronized (this.mReceivers) {
            String action = intent.getAction();
            String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(GTBaseContext.getInstance().getApplicationContext().getContentResolver());
            Uri data = intent.getData();
            String scheme = intent.getScheme();
            Set<String> categories = intent.getCategories();
            int i3 = 1;
            Object[] objArr = (intent.getFlags() & 8) != 0 ? 1 : null;
            if (objArr != null) {
                GDLog.DBGPRINTF(16, "Resolving type " + resolveTypeIfNeeded + " scheme " + scheme + " of intent " + intent);
            }
            ArrayList<fdyxd> arrayList3 = this.mActions.get(intent.getAction());
            if (arrayList3 != null) {
                if (objArr != null) {
                    GDLog.DBGPRINTF(16, TAG, "Action list: " + arrayList3);
                }
                ArrayList arrayList4 = null;
                int i4 = 0;
                while (i4 < arrayList3.size()) {
                    fdyxd fdyxdVar = arrayList3.get(i4);
                    if (objArr != null) {
                        String[] strArr = new String[i3];
                        strArr[0] = "Matching against filter " + fdyxdVar.dbjc;
                        GDLog.DBGPRINTF(16, TAG, strArr);
                    }
                    if (!fdyxdVar.jwxax) {
                        IntentFilter intentFilter = fdyxdVar.dbjc;
                        String str4 = action;
                        i = i4;
                        String str5 = resolveTypeIfNeeded;
                        str = action;
                        arrayList = arrayList4;
                        arrayList2 = arrayList3;
                        str2 = resolveTypeIfNeeded;
                        i2 = 1;
                        int match = intentFilter.match(str4, str5, scheme, data, categories, TAG);
                        if (match >= 0) {
                            if (objArr != null) {
                                GDLog.DBGPRINTF(16, TAG, "  Filter matched!  match=0x" + Integer.toHexString(match));
                            }
                            if (arrayList != null) {
                                arrayList4 = arrayList;
                            } else {
                                arrayList4 = new ArrayList();
                            }
                            arrayList4.add(fdyxdVar);
                            fdyxdVar.jwxax = true;
                            c = 16;
                            i4 = i + 1;
                            i3 = i2;
                            action = str;
                            arrayList3 = arrayList2;
                            resolveTypeIfNeeded = str2;
                        } else if (objArr != null) {
                            switch (match) {
                                case -4:
                                    str3 = "category";
                                    break;
                                case -3:
                                    str3 = "action";
                                    break;
                                case -2:
                                    str3 = UtilsKt.Data;
                                    break;
                                case -1:
                                    str3 = "type";
                                    break;
                                default:
                                    str3 = "unknown reason";
                                    break;
                            }
                            String[] strArr2 = {"  Filter did not match: " + str3};
                            c = 16;
                            GDLog.DBGPRINTF(16, TAG, strArr2);
                        } else {
                            c = 16;
                        }
                    } else if (objArr == null) {
                        i = i4;
                        arrayList2 = arrayList3;
                        c = 16;
                        str = action;
                        str2 = resolveTypeIfNeeded;
                        i2 = 1;
                        arrayList = arrayList4;
                    } else {
                        GDLog.DBGPRINTF(16, TAG, "  Filter's target already added");
                        i = i4;
                        arrayList2 = arrayList3;
                        c = 16;
                        str = action;
                        str2 = resolveTypeIfNeeded;
                        i2 = 1;
                        arrayList = arrayList4;
                    }
                    arrayList4 = arrayList;
                    i4 = i + 1;
                    i3 = i2;
                    action = str;
                    arrayList3 = arrayList2;
                    resolveTypeIfNeeded = str2;
                }
                ArrayList arrayList5 = arrayList4;
                ?? r11 = i3;
                if (arrayList5 != null) {
                    for (int i5 = 0; i5 < arrayList5.size(); i5++) {
                        ((fdyxd) arrayList5.get(i5)).jwxax = false;
                    }
                    this.mPendingBroadcasts.add(new yfdke(intent, arrayList5));
                    if (!this.mHandler.hasMessages(r11 == true ? 1 : 0)) {
                        this.mHandler.sendEmptyMessage(r11);
                    }
                    return r11;
                }
            }
            return false;
        }
    }

    public void sendBroadcastSync(Intent intent) {
        if (sendBroadcast(intent)) {
            executePendingBroadcasts();
        }
    }

    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        synchronized (this.mReceivers) {
            ArrayList<IntentFilter> remove = this.mReceivers.remove(broadcastReceiver);
            if (remove == null) {
                return;
            }
            for (int i = 0; i < remove.size(); i++) {
                IntentFilter intentFilter = remove.get(i);
                for (int i2 = 0; i2 < intentFilter.countActions(); i2++) {
                    String action = intentFilter.getAction(i2);
                    ArrayList<fdyxd> arrayList = this.mActions.get(action);
                    if (arrayList != null) {
                        int i3 = 0;
                        while (i3 < arrayList.size()) {
                            if (arrayList.get(i3).qkduk == broadcastReceiver) {
                                arrayList.remove(i3);
                                i3--;
                            }
                            i3++;
                        }
                        if (arrayList.size() <= 0) {
                            this.mActions.remove(action);
                        }
                    }
                }
            }
        }
    }
}
