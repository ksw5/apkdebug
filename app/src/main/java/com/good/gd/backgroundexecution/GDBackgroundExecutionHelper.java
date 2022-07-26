package com.good.gd.backgroundexecution;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.good.gd.containerstate.ndkproxy.GDActivitySupport;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.push.PushFactory;
import com.good.gd.net.GDConnectivityManagerImpl;

/* loaded from: classes.dex */
public final class GDBackgroundExecutionHelper {
    private static final PushConnectionHandler handler;
    private static final HandlerThread pushConnectionStateHandlerThread;
    private static final SocketPoolHandler socketPoolHandler;

    /* loaded from: classes.dex */
    private static class SocketPoolHandler extends Handler {
        private boolean dbjc = false;
        private final Runnable qkduk = new hbfhc();

        /* loaded from: classes.dex */
        class hbfhc implements Runnable {
            hbfhc() {
            }

            @Override // java.lang.Runnable
            public void run() {
                synchronized (this) {
                    SocketPoolHandler.this.dbjc = true;
                }
                GDConnectivityManagerImpl.drainSocketPool();
                synchronized (this) {
                    SocketPoolHandler.this.dbjc = false;
                }
            }
        }

        SocketPoolHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 15) {
                this.qkduk.run();
            }
        }

        synchronized void dbjc() {
            if (!hasMessages(15) && !this.dbjc) {
                sendEmptyMessage(15);
            }
        }
    }

    static {
        HandlerThread handlerThread = new HandlerThread("PushConnectionHandlerThread");
        pushConnectionStateHandlerThread = handlerThread;
        handlerThread.start();
        handler = new PushConnectionHandler(handlerThread.getLooper());
        socketPoolHandler = new SocketPoolHandler(handlerThread.getLooper());
    }

    public static void connectPushConnection() {
        if (readyToPushConnect()) {
            pushConnectionConnectAsync();
        }
    }

    public static void disconnectPushConnection() {
        if (readyToPushConnect()) {
            pushConnectionDisconnectAsync();
        }
    }

    public static void drainSocketPoolAsync() {
        socketPoolHandler.dbjc();
    }

    public static boolean isPushConnectionConnected() {
        return GDActivitySupport.isAuthorised() && GDActivitySupport.isStartupSuccessful() && PushFactory.getPushConnection().isConnected();
    }

    private static void pushConnectionConnectAsync() {
        handler.dbjc();
    }

    private static void pushConnectionDisconnectAsync() {
        handler.qkduk();
    }

    private static boolean readyToPushConnect() {
        return GDActivitySupport.isAuthorised() && GDActivitySupport.isStartupSuccessful();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class PushConnectionHandler extends Handler {
        private Runnable dbjc = null;
        private static final Runnable qkduk = new hbfhc();
        private static final Runnable jwxax = new yfdke();

        /* loaded from: classes.dex */
        static class hbfhc implements Runnable {
            hbfhc() {
            }

            @Override // java.lang.Runnable
            public void run() {
                if (!PushFactory.getPushConnection().isConnected()) {
                    PushFactory.getPushConnection().connectInternal(true);
                } else {
                    GDLog.DBGPRINTF(14, "Push connection is already connected\n");
                }
            }

            public String toString() {
                return "PushConnectTask";
            }
        }

        /* loaded from: classes.dex */
        static class yfdke implements Runnable {
            yfdke() {
            }

            @Override // java.lang.Runnable
            public void run() {
                if (PushFactory.getPushConnection().isConnected() || PushFactory.getPushConnection().isWaiting()) {
                    PushFactory.getPushConnection().disconnectInternal();
                } else {
                    GDLog.DBGPRINTF(14, "Push connection is already disconnected\n");
                }
            }

            public String toString() {
                return "PushDisconnectTask";
            }
        }

        PushConnectionHandler(Looper looper) {
            super(looper);
        }

        private synchronized Runnable jwxax() {
            return this.dbjc;
        }

        synchronized void dbjc() {
            Runnable runnable = this.dbjc;
            Runnable runnable2 = qkduk;
            if (runnable != runnable2) {
                dbjc(runnable2);
                sendEmptyMessage(0);
            }
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            GDLog.DBGPRINTF(14, "PushConnectionHandler: handleMessage\n");
            Runnable jwxax2 = jwxax();
            if (jwxax2 != null) {
                jwxax2.run();
            }
        }

        synchronized void qkduk() {
            Runnable runnable = this.dbjc;
            Runnable runnable2 = jwxax;
            if (runnable != runnable2) {
                dbjc(runnable2);
                sendEmptyMessage(0);
            }
        }

        private synchronized void dbjc(Runnable runnable) {
            this.dbjc = runnable;
        }
    }
}
