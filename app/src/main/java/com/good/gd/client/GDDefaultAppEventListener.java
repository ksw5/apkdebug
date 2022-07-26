package com.good.gd.client;

import android.content.Intent;
import com.good.gd.GDAndroid;
import com.good.gd.GDAppEvent;
import com.good.gd.GDAppEventListener;
import com.good.gd.GDInternalAppEvent;
import com.good.gd.GDLocalBroadcastManager;
import com.good.gd.GDStateAction;
import com.good.gd.GDStateListener;
import com.good.gd.GDTrustListener;
import com.good.gd.context.GDContext;
import com.good.gd.machines.activation.GDActivationManager;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.icc.GDInterDeviceContainerControl;
import com.good.gd.ndkproxy.pki.CredentialsProfileImpl;
import com.good.gd.ndkproxy.trust.GDTrustImpl;
import com.good.gd.service.GDActivityTimerProvider;
import com.good.gd.service.activity_timer.ActivityTimer;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes.dex */
public class GDDefaultAppEventListener implements GDAppEventListener {
    private static GDDefaultAppEventListener instance;
    private GDAppEventListener clientAppEventListener;
    private boolean isAuthorized;
    private boolean isTrustedAuthenticator;
    private GDStateListener mainStateListener;
    private boolean shouldUpdatePolicy;
    private Set<GDStateListener> stateListeners = new HashSet();
    private Set<WeakReference<GDStateListener>> servicestateListeners = new HashSet();
    private LinkedBlockingQueue<Runnable> runnablesForFirstAuth = new LinkedBlockingQueue<>();

    private GDDefaultAppEventListener() {
    }

    public static GDDefaultAppEventListener getInstance() {
        if (instance == null) {
            instance = new GDDefaultAppEventListener();
        }
        return instance;
    }

    private void onAuthorized(GDAppEvent gDAppEvent) {
        synchronized (this) {
            this.isAuthorized = true;
        }
        GDInterDeviceContainerControl.getInstance().onContainerAuthorized();
        ActivityTimer activityTimer = GDActivityTimerProvider.getInstance().getActivityTimer();
        if (activityTimer != null) {
            activityTimer.resumeTimer();
        }
        GDActivationManager.getInstance().informAuthorized();
        for (GDStateListener gDStateListener : this.stateListeners) {
            gDStateListener.onAuthorized();
        }
        Iterator<WeakReference<GDStateListener>> it = this.servicestateListeners.iterator();
        while (it.hasNext()) {
            GDStateListener gDStateListener2 = it.next().get();
            if (gDStateListener2 == null) {
                it.remove();
            } else {
                GDLog.DBGPRINTF(14, "onAuthorized: call serviceListener onAuthorized()\n");
                gDStateListener2.onAuthorized();
            }
        }
        sendBroadcasts(GDStateAction.GD_STATE_AUTHORIZED_ACTION, null);
        if (this.shouldUpdatePolicy) {
            updatePolicy();
        }
        CredentialsProfileImpl.getInstance().register();
        while (true) {
            Runnable poll = this.runnablesForFirstAuth.poll();
            if (poll != null) {
                GDClient.getInstance().getClientHandler().post(poll);
            } else {
                return;
            }
        }
    }

    private void onContainerMigrationCompleted() {
        GDLog.DBGPRINTF(14, "onContainerMigrationCompleted: call onContainerMigrationCompleted()\n");
        sendBroadcasts(GDStateAction.GD_STATE_CONTAINER_MIGRATION_COMPLETED, null);
    }

    private void onContainerMigrationPending() {
        GDLog.DBGPRINTF(14, "onContainerMigrationPending: call onContainerMigrationPending()\n");
        sendBroadcasts(GDStateAction.GD_STATE_CONTAINER_MIGRATION_PENDING, null);
    }

    private void onNotAuthorized(GDAppEvent gDAppEvent) {
        this.isAuthorized = false;
        switch (gDAppEvent.getResultCode().ordinal()) {
            case 1:
            case 2:
            case 3:
                GDLog.DBGPRINTF(14, "onNotAuthorized: pre-activate error, ignoring\n");
                break;
            case 4:
            case 8:
                GDLog.DBGPRINTF(14, "onNotAuthorized: permanent notAuthorized, calling onWiped\n");
                for (GDStateListener gDStateListener : this.stateListeners) {
                    gDStateListener.onWiped();
                }
                Iterator<WeakReference<GDStateListener>> it = this.servicestateListeners.iterator();
                while (it.hasNext()) {
                    GDStateListener gDStateListener2 = it.next().get();
                    if (gDStateListener2 == null) {
                        it.remove();
                    } else {
                        gDStateListener2.onWiped();
                    }
                }
                sendBroadcasts(GDStateAction.GD_STATE_WIPED_ACTION, null);
                break;
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
                GDLog.DBGPRINTF(14, "onNotAuthorized: temporary notAuthorized, calling onLocked()\n");
                for (GDStateListener gDStateListener3 : this.stateListeners) {
                    gDStateListener3.onLocked();
                }
                Iterator<WeakReference<GDStateListener>> it2 = this.servicestateListeners.iterator();
                while (it2.hasNext()) {
                    GDStateListener gDStateListener4 = it2.next().get();
                    if (gDStateListener4 == null) {
                        it2.remove();
                    } else {
                        GDLog.DBGPRINTF(14, "onAuthorized: call serviceListener onAuthorized()\n");
                        gDStateListener4.onLocked();
                    }
                }
                sendBroadcastsWithSerializableExtra(GDStateAction.GD_STATE_LOCKED_ACTION, gDAppEvent.getResultCode());
                break;
            case 11:
                break;
            default:
                GDLog.DBGPRINTF(14, "onNotAuthorized: unknown error, ignoring\n");
                break;
        }
        CredentialsProfileImpl.getInstance().unregister();
    }

    private void sendBroadcasts(String str, Map<String, Object> map) {
        GDLog.DBGPRINTF(14, "GDDefaultAppEventListener.sendBroadcasts action=" + str + "\n");
        if (GDContext.getInstance().getApplicationContext() != null) {
            Intent intent = new Intent(str);
            if (map != null) {
                intent.putExtra(str, new HashMap(map));
            }
            GDLocalBroadcastManager.getInstance().sendBroadcast(intent);
        }
    }

    private void sendBroadcastsWithSerializableExtra(String str, Serializable serializable) {
        GDLog.DBGPRINTF(14, "GDDefaultAppEventListener.sendBroadcasts action=" + str + " serializableExtra=" + serializable + "\n");
        if (GDContext.getInstance().getApplicationContext() != null) {
            Intent intent = new Intent(str);
            if (serializable != null) {
                intent.putExtra(str, serializable);
            }
            GDLocalBroadcastManager.getInstance().sendBroadcast(intent);
        }
    }

    private void updateConfig() {
        Map<String, Object> applicationConfig = GDAndroid.getInstance().getApplicationConfig();
        GDLog.DBGPRINTF(14, "updateConfig: settings update: calling onUpdateConfig()\n");
        for (GDStateListener gDStateListener : this.stateListeners) {
            gDStateListener.onUpdateConfig(applicationConfig);
        }
        Iterator<WeakReference<GDStateListener>> it = this.servicestateListeners.iterator();
        while (it.hasNext()) {
            GDStateListener gDStateListener2 = it.next().get();
            if (gDStateListener2 != null) {
                GDLog.DBGPRINTF(14, "onAuthorized: call serviceListener onAuthorized()\n");
                gDStateListener2.onUpdateConfig(applicationConfig);
            } else {
                it.remove();
            }
        }
        sendBroadcasts(GDStateAction.GD_STATE_UPDATE_CONFIG_ACTION, applicationConfig);
    }

    private void updateEntitlements() {
        if (!GDContext.getInstance().isWiped()) {
            GDLog.DBGPRINTF(14, "updateEntitlements: entitlements update: calling onUpdateEntitlements()\n");
            for (GDStateListener gDStateListener : this.stateListeners) {
                gDStateListener.onUpdateEntitlements();
            }
            Iterator<WeakReference<GDStateListener>> it = this.servicestateListeners.iterator();
            while (it.hasNext()) {
                GDStateListener gDStateListener2 = it.next().get();
                if (gDStateListener2 != null) {
                    GDLog.DBGPRINTF(14, "onAuthorized: call serviceListener onAuthorized()\n");
                    gDStateListener2.onUpdateEntitlements();
                } else {
                    it.remove();
                }
            }
            sendBroadcasts(GDStateAction.GD_STATE_UPDATE_ENTITLEMENTS_ACTION, null);
            return;
        }
        GDLog.DBGPRINTF(12, "updateEntitlements: onUpdateEntitlements() is not called\n");
    }

    private void updatePolicy() {
        if (this.isAuthorized) {
            GDLog.DBGPRINTF(14, "updatePolicy: settings update: calling onUpdatePolicy()\n");
            Map<String, Object> applicationPolicy = GDAndroid.getInstance().getApplicationPolicy();
            for (GDStateListener gDStateListener : this.stateListeners) {
                gDStateListener.onUpdatePolicy(applicationPolicy);
            }
            Iterator<WeakReference<GDStateListener>> it = this.servicestateListeners.iterator();
            while (it.hasNext()) {
                GDStateListener gDStateListener2 = it.next().get();
                if (gDStateListener2 == null) {
                    it.remove();
                } else {
                    GDLog.DBGPRINTF(14, "onAuthorized: call serviceListener onAuthorized()\n");
                    gDStateListener2.onUpdatePolicy(applicationPolicy);
                }
            }
            sendBroadcasts(GDStateAction.GD_STATE_UPDATE_POLICY_ACTION, applicationPolicy);
            this.shouldUpdatePolicy = false;
            return;
        }
        this.shouldUpdatePolicy = true;
        GDLog.DBGPRINTF(14, "updatePolicy: settings update: not authorized, ignoring\n");
    }

    private void updateServices() {
        GDLog.DBGPRINTF(14, "updateServices: services update: calling onUpdateServices()\n");
        for (GDStateListener gDStateListener : this.stateListeners) {
            gDStateListener.onUpdateServices();
        }
        Iterator<WeakReference<GDStateListener>> it = this.servicestateListeners.iterator();
        while (it.hasNext()) {
            GDStateListener gDStateListener2 = it.next().get();
            if (gDStateListener2 != null) {
                GDLog.DBGPRINTF(14, "onAuthorized: call serviceListener onAuthorized()\n");
                gDStateListener2.onUpdateServices();
            } else {
                it.remove();
            }
        }
        sendBroadcasts(GDStateAction.GD_STATE_UPDATE_SERVICES_ACTION, null);
    }

    public void addGDServiceStateListener(WeakReference<GDStateListener> weakReference) {
        this.servicestateListeners.add(weakReference);
    }

    public void addGDStateListener(GDStateListener gDStateListener) {
        this.stateListeners.add(gDStateListener);
    }

    public void checkTrustedAuthenticator() {
        GDAppEventListener gDAppEventListener = this.clientAppEventListener;
        if (gDAppEventListener instanceof GDTrustListener) {
            GDTrustImpl.checkTrustDelegate((GDTrustListener) gDAppEventListener);
            this.isTrustedAuthenticator = true;
            return;
        }
        GDStateListener gDStateListener = this.mainStateListener;
        if (!(gDStateListener instanceof GDTrustListener)) {
            return;
        }
        GDTrustImpl.checkTrustDelegate((GDTrustListener) gDStateListener);
        this.isTrustedAuthenticator = true;
    }

    public synchronized void doClientCallOnceAuthorized(Runnable runnable) {
        if (this.isAuthorized) {
            GDClient.getInstance().getClientHandler().post(runnable);
        } else {
            try {
                this.runnablesForFirstAuth.put(runnable);
            } catch (InterruptedException e) {
            }
        }
    }

    public synchronized boolean isAuthorized() {
        return this.isAuthorized;
    }

    public boolean isTrustedAuthenticator() {
        return this.isTrustedAuthenticator;
    }

    @Override // com.good.gd.GDAppEventListener
    public void onGDEvent(GDAppEvent gDAppEvent) {
        GDLog.DBGPRINTF(14, "onGDEvent(" + gDAppEvent + ")\n");
        switch (gDAppEvent.getEventType().ordinal()) {
            case 0:
            case 6:
                onAuthorized(gDAppEvent);
                break;
            case 1:
            case 7:
                onNotAuthorized(gDAppEvent);
                break;
            case 2:
                updateConfig();
                break;
            case 3:
                updateServices();
                break;
            case 4:
                updatePolicy();
                break;
            case 5:
                updateEntitlements();
                break;
        }
        GDAppEventListener gDAppEventListener = this.clientAppEventListener;
        if (gDAppEventListener != null) {
            gDAppEventListener.onGDEvent(gDAppEvent);
        }
    }

    public void onGDInternalEvent(GDInternalAppEvent gDInternalAppEvent) {
        GDLog.DBGPRINTF(14, "onGDInternalEvent(" + gDInternalAppEvent + ")\n");
        int ordinal = gDInternalAppEvent.getEventType().ordinal();
        if (ordinal == 0) {
            onContainerMigrationPending();
        } else if (ordinal != 1) {
        } else {
            onContainerMigrationCompleted();
        }
    }

    public void removeGDStateListener(GDStateListener gDStateListener) {
        this.stateListeners.remove(gDStateListener);
    }

    public void setGDAppEventListener(GDAppEventListener gDAppEventListener) {
        if (gDAppEventListener != null) {
            this.clientAppEventListener = gDAppEventListener;
        }
    }

    public void setMainGDStateListener(GDStateListener gDStateListener) {
        GDStateListener gDStateListener2 = this.mainStateListener;
        if (gDStateListener2 != null) {
            this.stateListeners.remove(gDStateListener2);
        }
        this.mainStateListener = gDStateListener;
        if (gDStateListener != null) {
            this.stateListeners.add(gDStateListener);
        }
    }
}
