package com.good.gd.support.impl;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import com.good.gd.error.GDNotSupportedError;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.support.GDConnectedApplication;
import com.good.gd.support.GDConnectedApplicationState;
import com.good.gd.support.GDConnectedApplicationSupportListener;
import com.good.gd.support.GDConnectedApplicationType;
import com.good.gd.support.impl.GDConnectedApplicationControl;
import com.good.gd.utils.ErrorUtils;
import com.good.gd.utils.GDSDKState;
import com.good.gt.context.GTBaseContext;
import com.good.gt.icc.IccCommand;
import com.good.gt.interdevice_icc.InterDeviceConnectedCallbackInterface;
import com.good.gt.interdevice_icc.InterDeviceHardwareAdaptor;
import com.good.gt.interdevice_icc.InterDeviceObject;
import com.good.gt.interdevice_icc.InterDeviceUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes.dex */
public class GDConnectedApplicationSupportImpl implements InterDeviceConnectedCallbackInterface {
    private static GDConnectedApplicationSupportImpl _instance;
    private boolean mClientRequestedConnectedApplicationState;
    private ArrayList<GDConnectedApplication> mConnectedApplications;
    private String mDeviceAddressActivating;
    private boolean mGooglePlayServicesIsLinked;
    private InterDeviceHardwareAdaptor mInterDeviceControl;
    private GDConnectedApplicationSupportListener mListener;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static /* synthetic */ class hbfhc {
        static final /* synthetic */ int[] dbjc;

        static {
            int[] iArr = new int[GDConnectedApplicationControl.AppActivationCompletedState.values().length];
            dbjc = iArr;
            try {
                GDConnectedApplicationControl.AppActivationCompletedState appActivationCompletedState = GDConnectedApplicationControl.AppActivationCompletedState.CONNECTED_APP_ACTIVATION_ERROR;
                iArr[2] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                int[] iArr2 = dbjc;
                GDConnectedApplicationControl.AppActivationCompletedState appActivationCompletedState2 = GDConnectedApplicationControl.AppActivationCompletedState.CONNECTED_APP_ACTIVATION_SUCCESS;
                iArr2[0] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                int[] iArr3 = dbjc;
                GDConnectedApplicationControl.AppActivationCompletedState appActivationCompletedState3 = GDConnectedApplicationControl.AppActivationCompletedState.CONNECTED_APP_ACTIVATION_USER_CANCELLED;
                iArr3[1] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class yfdke extends AsyncTask<Void, Void, Void> {
        private yfdke() {
        }

        private GDConnectedApplication dbjc(String str, ArrayList<GDConnectedApplication> arrayList) {
            if (arrayList == null) {
                return null;
            }
            Iterator<GDConnectedApplication> it = arrayList.iterator();
            while (it.hasNext()) {
                GDConnectedApplication next = it.next();
                if (next.getAddress().equals(str)) {
                    return next;
                }
            }
            return null;
        }

        @Override // android.os.AsyncTask
        protected Void doInBackground(Void[] voidArr) {
            GDLog.DBGPRINTF(16, "GetConnectedDevicesTask: doInBackground\n");
            ArrayList<GDConnectedApplication> connectedApplications = GDConnectedApplicationControl.getInstance().getListener().getConnectedApplications();
            if (connectedApplications != null) {
                GDLog.DBGPRINTF(16, "GetConnectedDevicesTask: doInBackground localApps count = " + connectedApplications.size() + "\n");
            } else {
                GDLog.DBGPRINTF(16, "GetConnectedDevicesTask: doInBackground no localApps\n");
            }
            int connect = GDConnectedApplicationSupportImpl.this.mInterDeviceControl.connect();
            if (connect == 1) {
                Collection<InterDeviceObject> connectedNodes = GDConnectedApplicationSupportImpl.this.mInterDeviceControl.getConnectedNodes();
                GDLog.DBGPRINTF(16, "GetConnectedDevicesTask: doInBackground connected apps count = " + connectedNodes.size() + "\n");
                for (InterDeviceObject interDeviceObject : connectedNodes) {
                    if (GDConnectedApplicationSupportImpl.this.mInterDeviceControl.sendMessageToNode(interDeviceObject.getAddress(), IccCommand.INTER_DEVICE_PING.toStr(), new Bundle()) == 1) {
                        GDConnectedApplication gDConnectedApplication = new GDConnectedApplication();
                        String createSpecificNodeGDWearAddress = InterDeviceUtils.createSpecificNodeGDWearAddress(GTBaseContext.getInstance().getApplicationContext().getPackageName(), interDeviceObject.getAddress());
                        gDConnectedApplication.setAddress(createSpecificNodeGDWearAddress);
                        gDConnectedApplication.setDisplayName(interDeviceObject.getName());
                        GDConnectedApplication dbjc = dbjc(createSpecificNodeGDWearAddress, connectedApplications);
                        if (dbjc != null) {
                            gDConnectedApplication.setType(dbjc.getType());
                            gDConnectedApplication.setState(dbjc.getState());
                        } else {
                            gDConnectedApplication.setType(GDConnectedApplicationControl.getInstance().getListener().getConnectedApplicationType(interDeviceObject.getAddress()));
                            gDConnectedApplication.setState(GDConnectedApplicationSupportImpl.this.getConnectedApplicationState(gDConnectedApplication.getAddress()));
                        }
                        GDConnectedApplicationSupportImpl.this.mConnectedApplications.add(gDConnectedApplication);
                    }
                }
                GDConnectedApplicationSupportImpl.this.mInterDeviceControl.disconnect();
                if (connectedApplications != null) {
                    Iterator<GDConnectedApplication> it = connectedApplications.iterator();
                    while (it.hasNext()) {
                        GDConnectedApplication next = it.next();
                        if (dbjc(next.getAddress(), GDConnectedApplicationSupportImpl.this.mConnectedApplications) == null) {
                            GDConnectedApplication gDConnectedApplication2 = new GDConnectedApplication();
                            gDConnectedApplication2.setAddress(next.getAddress());
                            gDConnectedApplication2.setState(next.getState());
                            gDConnectedApplication2.setType(next.getType());
                            gDConnectedApplication2.setDisplayName(next.getDisplayName());
                            if (gDConnectedApplication2.getState() == GDConnectedApplicationState.StateActivated) {
                                gDConnectedApplication2.setState(GDConnectedApplicationState.StateNotConnected);
                            }
                            GDConnectedApplicationSupportImpl.this.mConnectedApplications.add(gDConnectedApplication2);
                        }
                    }
                }
                GDLog.DBGPRINTF(16, "GetConnectedDevicesTask: doInBackground combined count = " + GDConnectedApplicationSupportImpl.this.mConnectedApplications.size() + "\n");
                return null;
            }
            GDLog.DBGPRINTF(16, "queryConnectedApplications error= " + connect + "\n");
            return null;
        }

        @Override // android.os.AsyncTask
        protected void onPostExecute(Void r3) {
            super.onPostExecute(r3);
            GDLog.DBGPRINTF(16, "GDConnectedApplicationSupportImpl: queryConnectedApplications number applications = " + GDConnectedApplicationSupportImpl.this.mConnectedApplications.size() + "\n");
            if (GDConnectedApplicationSupportImpl.this.mClientRequestedConnectedApplicationState) {
                GDConnectedApplicationSupportImpl.this.mListener.onApplicationsConnected(new ArrayList<>(GDConnectedApplicationSupportImpl.this.mConnectedApplications));
                GDConnectedApplicationSupportImpl.this.mConnectedApplications.clear();
            }
        }

        /* synthetic */ yfdke(GDConnectedApplicationSupportImpl gDConnectedApplicationSupportImpl, hbfhc hbfhcVar) {
            this();
        }
    }

    private GDConnectedApplicationSupportImpl(GDConnectedApplicationSupportListener gDConnectedApplicationSupportListener) {
        this.mGooglePlayServicesIsLinked = false;
        InterDeviceHardwareAdaptor createInstance = InterDeviceHardwareAdaptor.createInstance();
        this.mInterDeviceControl = createInstance;
        boolean isWearableFrameworkSupported = createInstance.isWearableFrameworkSupported();
        this.mGooglePlayServicesIsLinked = isWearableFrameworkSupported;
        if (!isWearableFrameworkSupported) {
            GDLog.DBGPRINTF(12, "GooglePlayServices lib not linked to app so can't use InterDevice comms\n");
            this.mGooglePlayServicesIsLinked = false;
            ErrorUtils.throwGDMissingGooglePlayServicesError();
        }
        this.mGooglePlayServicesIsLinked = true;
        this.mListener = gDConnectedApplicationSupportListener;
        this.mConnectedApplications = new ArrayList<>();
        this.mClientRequestedConnectedApplicationState = false;
    }

    public static GDConnectedApplicationSupportImpl createInstance(GDConnectedApplicationSupportListener gDConnectedApplicationSupportListener) {
        if (_instance == null) {
            _instance = new GDConnectedApplicationSupportImpl(gDConnectedApplicationSupportListener);
        }
        return _instance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public GDConnectedApplicationState getConnectedApplicationState(String str) {
        if (!this.mGooglePlayServicesIsLinked) {
            ErrorUtils.throwGDMissingGooglePlayServicesError();
        }
        String nodeID = InterDeviceUtils.getNodeID(str);
        GDConnectedApplicationState connectedApplicationState = GDConnectedApplicationControl.getInstance().getListener().getConnectedApplicationState(nodeID);
        GDLog.DBGPRINTF(16, "GDConnectedApplicationSupportImpl: getConnectedApplicationState remoteDevice =" + nodeID + "State =" + connectedApplicationState + "\n");
        return connectedApplicationState;
    }

    public static GDConnectedApplicationSupportImpl getInstance() {
        return _instance;
    }

    public boolean isConnectedApplicationActivationAllowed() {
        boolean isConnectedApplicationActivationAllowed = GDConnectedApplicationControl.getInstance().getListener().isConnectedApplicationActivationAllowed();
        GDLog.DBGPRINTF(16, "GDConnectedApplicationSupportImpl: isConnectedApplicationActivationAllowed" + isConnectedApplicationActivationAllowed + "\n");
        return isConnectedApplicationActivationAllowed;
    }

    public void onConnectedApplicationActivationComplete(GDConnectedApplicationControl.AppActivationCompletedState appActivationCompletedState) {
        GDConnectedApplication gDConnectedApplication = new GDConnectedApplication();
        gDConnectedApplication.setType(GDConnectedApplicationType.GD_WEARABLE);
        gDConnectedApplication.setAddress(this.mDeviceAddressActivating);
        int ordinal = appActivationCompletedState.ordinal();
        if (ordinal == 0) {
            gDConnectedApplication.setState(GDConnectedApplicationState.StateActivated);
        } else if (ordinal == 1) {
            gDConnectedApplication.setState(GDConnectedApplicationState.StateActivationFailed_UserCancelled);
        } else if (ordinal == 2) {
            gDConnectedApplication.setState(GDConnectedApplicationState.StateActivationFailed_Error);
        }
        GDLog.DBGPRINTF(16, "GDConnectedApplicationSupportImpl:   appState=" + gDConnectedApplication.getState() + "\n");
        this.mListener.onApplicationActivationComplete(gDConnectedApplication);
        queryConnectedApplications();
    }

    @Override // com.good.gt.interdevice_icc.InterDeviceConnectedCallbackInterface
    public void onNodeConnected(InterDeviceObject interDeviceObject) {
        GDLog.DBGPRINTF(16, "GDConnectedApplicationSupportImpl: onDeviceConnected\n");
        if (this.mClientRequestedConnectedApplicationState) {
            new yfdke(this, null).execute(new Void[0]);
        }
    }

    @Override // com.good.gt.interdevice_icc.InterDeviceConnectedCallbackInterface
    public void onNodeDisconnected(InterDeviceObject interDeviceObject) {
        GDLog.DBGPRINTF(16, "GDConnectedApplicationSupportImpl: onDeviceDisconnected\n");
        if (this.mClientRequestedConnectedApplicationState) {
            new yfdke(this, null).execute(new Void[0]);
        }
    }

    public void queryConnectedApplications() {
        GDSDKState.getInstance().checkAuthorized();
        if (!this.mGooglePlayServicesIsLinked) {
            ErrorUtils.throwGDMissingGooglePlayServicesError();
        }
        GDLog.DBGPRINTF(16, "GDConnectedApplicationSupportImpl: queryConnectedDevices\n");
        new yfdke(this, null).execute(new Void[0]);
        this.mClientRequestedConnectedApplicationState = true;
    }

    public void removeConnectedApplication(String str) {
        GDLog.DBGPRINTF(16, "GDConnectedApplicationSupportImpl: removeConnectedApplication deviceAddress = " + str + "\n");
        GDConnectedApplicationControl.getInstance().getListener().removeConnectedApplication(InterDeviceUtils.getNodeID(str));
        if (this.mClientRequestedConnectedApplicationState) {
            new yfdke(this, null).execute(new Void[0]);
        }
    }

    public GDConnectedApplicationState startConnectedApplicationActivation(String str, Context context) {
        GDLog.DBGPRINTF(16, "GDConnectedApplicationSupportImpl: startConnectedApplicationActivation\n");
        GDSDKState.getInstance().checkAuthorized();
        if (!this.mGooglePlayServicesIsLinked) {
            ErrorUtils.throwGDMissingGooglePlayServicesError();
        }
        if (GDConnectedApplicationControl.getInstance().getMyAppType() != GDConnectedApplicationType.GD_WEARABLE) {
            if (getConnectedApplicationState(str) != GDConnectedApplicationState.StateNotActivated) {
                return GDConnectedApplicationState.StateActivated;
            }
            GDConnectedApplicationControl.getInstance().getListener().startConnectedApplicationActivation(str, context);
            this.mDeviceAddressActivating = str;
            return GDConnectedApplicationState.StateActivationStarted;
        }
        throw new GDNotSupportedError("GD Wearable Framework does not support activating Connected Applications. Use this APIin the main GD SDK to activate connected wearable application");
    }

    public void updateConnectedApplications() {
        if (this.mClientRequestedConnectedApplicationState) {
            new yfdke(this, null).execute(new Void[0]);
        }
    }
}
