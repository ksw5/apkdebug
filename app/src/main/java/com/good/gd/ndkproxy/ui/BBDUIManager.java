package com.good.gd.ndkproxy.ui;

import android.os.Handler;
import android.os.Looper;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.data.DynamicsBackgroundUI;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ndkproxy.ui.data.dialog.DialogUI;
import com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent;
import com.good.gd.ui.ViewInteractor;
import java.util.List;

/* loaded from: classes.dex */
public class BBDUIManager {
    private static BBDUIManager sInstance;
    private BaseUI mCurrent = null;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ViewInteractor viewInteractor;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ehnkx implements Runnable {
        final /* synthetic */ BBDUIUpdateEvent dbjc;

        ehnkx(BBDUIUpdateEvent bBDUIUpdateEvent) {
            this.dbjc = bBDUIUpdateEvent;
        }

        @Override // java.lang.Runnable
        public void run() {
            BBDUIManager.this.viewInteractor.updateHost(this.dbjc);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class fdyxd implements Runnable {
        final /* synthetic */ BBDUIObject dbjc;
        final /* synthetic */ BBDUIUpdateEvent qkduk;

        fdyxd(BBDUIObject bBDUIObject, BBDUIUpdateEvent bBDUIUpdateEvent) {
            this.dbjc = bBDUIObject;
            this.qkduk = bBDUIUpdateEvent;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.dbjc.setUpdateData(this.qkduk);
            BBDUIManager.this.viewInteractor.updateUI(this.dbjc);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class hbfhc implements Runnable {
        final /* synthetic */ List dbjc;

        hbfhc(List list) {
            this.dbjc = list;
        }

        @Override // java.lang.Runnable
        public void run() {
            BaseUI baseUI = null;
            DialogUI dialogUI = null;
            for (BBDUIObject bBDUIObject : this.dbjc) {
                if (bBDUIObject.isModal()) {
                    dialogUI = (DialogUI) bBDUIObject;
                } else {
                    baseUI = (BaseUI) bBDUIObject;
                }
            }
            if (baseUI == null && dialogUI != null) {
                baseUI = new DynamicsBackgroundUI(dialogUI.isDynamicsBackgroundEnabled());
            }
            BBDUIManager.this.mCurrent = baseUI;
            GDLog.DBGPRINTF(14, "BBDUIManager showInternalUIs: " + BBDUIManager.toStr(baseUI) + ":" + BBDUIManager.toStr(dialogUI) + "\n");
            BBDUIManager.this.viewInteractor.requestNewUIs(baseUI, dialogUI);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class yfdke implements Runnable {
        yfdke() {
        }

        @Override // java.lang.Runnable
        public void run() {
            BBDUIManager.this.mCurrent = null;
            BBDUIManager.this.viewInteractor.cleanInternalDialogHandler();
            BBDUIManager.this.viewInteractor.cleanInternalDialogUI();
            BBDUIManager.this.viewInteractor.closeInternalUI();
        }
    }

    private BBDUIManager() {
    }

    public static synchronized BBDUIManager getInstance() {
        BBDUIManager bBDUIManager;
        synchronized (BBDUIManager.class) {
            if (sInstance == null) {
                sInstance = new BBDUIManager();
            }
            bBDUIManager = sInstance;
        }
        return bBDUIManager;
    }

    public static void initialize(ViewInteractor viewInteractor) {
        getInstance().viewInteractor = viewInteractor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String toStr(BBDUIObject bBDUIObject) {
        return bBDUIObject != null ? bBDUIObject.getBBDUIType().toString() : "";
    }

    public BBDUIObject getCurrentUI() {
        return this.mCurrent;
    }

    public boolean isUiAllowedForTAF() {
        BaseUI baseUI = this.mCurrent;
        if (baseUI == null) {
            return false;
        }
        int ordinal = baseUI.getBBDUIType().ordinal();
        return ordinal == 2 || ordinal == 19 || ordinal == 21 || ordinal == 22;
    }

    public void moveTaskToBackground() {
        this.viewInteractor.moveTaskToBack();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void processUIs(List<BBDUIObject> list) {
        if (list != null && !list.isEmpty()) {
            showInternalUIs(list);
        } else {
            showAppUI();
        }
    }

    void showAppUI() {
        GDLog.DBGPRINTF(16, "BBDUIManager showAppUI\n");
        this.mHandler.post(new yfdke());
    }

    void showInternalUIs(List<BBDUIObject> list) {
        GDLog.DBGPRINTF(16, "BBDUIManager showInternalUIs IN\n");
        this.mHandler.post(new hbfhc(list));
    }

    public void updateHost(BBDUIUpdateEvent bBDUIUpdateEvent) {
        GDLog.DBGPRINTF(16, "BBDUIManager updateHost: " + bBDUIUpdateEvent + "\n");
        this.mHandler.post(new ehnkx(bBDUIUpdateEvent));
    }

    public void updateUI(BBDUIObject bBDUIObject, BBDUIUpdateEvent bBDUIUpdateEvent) {
        GDLog.DBGPRINTF(16, "BBDUIManager update " + bBDUIObject.getBBDUIType() + " : " + bBDUIUpdateEvent + "\n");
        this.mHandler.post(new fdyxd(bBDUIObject, bBDUIUpdateEvent));
    }
}
