package com.good.gd.service;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.NativeExecutionHandler;
import com.good.gd.ndkproxy.ui.GDIdleTimeoutHandler;
import com.good.gd.service.machines.LibStartupUserSession;
import com.good.gd.utils.MessageFactory;

/* loaded from: classes.dex */
public class GDServiceHelper {
    private LibStartupUserSession _userSessionMachine = null;
    private HandlerThread handlerThread = null;
    private Messenger _serviceThreadMessenger = null;
    private GDService _gdService = null;
    private boolean _inited = false;

    /* loaded from: classes.dex */
    class hbfhc implements Runnable {
        final /* synthetic */ Looper dbjc;

        hbfhc(Looper looper) {
            this.dbjc = looper;
        }

        @Override // java.lang.Runnable
        public void run() {
            GDServiceHelper.this.serviceThreadInit(this.dbjc);
        }
    }

    /* loaded from: classes.dex */
    class yfdke extends Handler {
        yfdke(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            GDLog.DBGPRINTF(16, "GDService.IncomingHandler.handleMessage: " + MessageFactory.showMessage(message) + "\n");
            GDServiceHelper.this._userSessionMachine.handleClientMessage(message);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void serviceThreadInit(Looper looper) {
        NativeExecutionHandler.createInstance(this._gdService.getApplicationContext(), looper);
        LibStartupUserSession libStartupUserSession = new LibStartupUserSession();
        this._userSessionMachine = libStartupUserSession;
        libStartupUserSession.initialize();
        GDIdleTimeoutHandler.getInstance().initialize();
        GDUSBMonitor.getInstance().startMonitoring();
        GDBroadcastMonitor.getInstance().startMonitoring();
        GDBroadcastMonitor.getInstance().startMonitoring();
        GDLog.DBGPRINTF(16, "GDService: ******* READY *******\n");
        synchronized (this._gdService) {
            this._inited = true;
            this._gdService.notifyAll();
        }
    }

    public Messenger getMessenger() {
        return this._serviceThreadMessenger;
    }

    public void initHelperThread(GDService gDService) {
        this._gdService = gDService;
        GDLog.DBGPRINTF(16, "******* GDService.initHelperThread\n");
        if (this.handlerThread == null) {
            HandlerThread handlerThread = new HandlerThread("GDServiceHelper", 10);
            this.handlerThread = handlerThread;
            handlerThread.start();
            Looper looper = this.handlerThread.getLooper();
            this._serviceThreadMessenger = new Messenger(new yfdke(looper));
            new Handler(looper).post(new hbfhc(looper));
            return;
        }
        GDLog.DBGPRINTF(13, "******* GDService.initHelperThread: service thread already started, no action\n");
    }

    public boolean isInited() {
        return this._inited;
    }
}
