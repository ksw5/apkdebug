package com.good.gd.ndkproxy.sub;

import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.support.GDConnectedApplication;
import com.good.gd.support.GDConnectedApplicationState;
import com.good.gd.support.GDConnectedApplicationType;
import com.good.gd.support.impl.GDConnectedApplicationSupportImpl;
import com.good.gt.icc.GTContainerInfo;
import com.good.gt.interdevice_icc.InterDeviceUtils;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class GDSubContainerManager {
    private static final String KSUBCONTAINERTYPE_WEARABLE = "wear";
    private static final String KWEARDEVICEIDKEY = "wearDeviceId";
    private static final String KWEARDEVICENAMEKEY = "wearDeviceName";
    private static final String KWEARGDAPPIDKEY = "wearGdAppId";
    private static final String KWEARGDVERSIONKEY = "wearGdVersion";
    private static final String KWEARNATIVEAPPIDKEY = "wearNativeAppId";
    private static final String KWIPE_COMMAND = "{\"Wipe\":true}";
    private static final int SUBCONTAINER_STATE_ACTIVATED = 0;
    private static final int SUBCONTAINER_STATE_REMOVED = 1;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ConnectedApplication {
        public String mAddress;
        public String mName;
        public int mState;
        public String mType;

        public ConnectedApplication(GDSubContainerManager gDSubContainerManager, String str, String str2, String str3, int i) {
            this.mType = str;
            this.mAddress = str2;
            this.mName = str3;
            this.mState = i;
        }
    }

    public static ArrayList<GDConnectedApplication> getConnectedApplications() {
        Object[] nativeGetConnectedApplications = nativeGetConnectedApplications();
        ArrayList<GDConnectedApplication> arrayList = new ArrayList<>();
        if (nativeGetConnectedApplications != null) {
            for (int i = 0; i < nativeGetConnectedApplications.length; i++) {
                ConnectedApplication connectedApplication = (ConnectedApplication) nativeGetConnectedApplications[i];
                GDLog.DBGPRINTF(16, "GDSubContainerManager ConnectedApp " + i + " Type = " + connectedApplication.mType + " State = " + connectedApplication.mState + " Address = " + connectedApplication.mAddress + " Name =" + connectedApplication.mName);
                GDConnectedApplication gDConnectedApplication = new GDConnectedApplication();
                if (connectedApplication.mType.equals(KSUBCONTAINERTYPE_WEARABLE)) {
                    gDConnectedApplication.setType(GDConnectedApplicationType.GD_WEARABLE);
                } else {
                    gDConnectedApplication.setType(GDConnectedApplicationType.GD_UNKNOWN);
                }
                gDConnectedApplication.setAddress(InterDeviceUtils.createSpecificNodeGDWearAddress(GDContext.getInstance().getApplicationContext().getPackageName(), connectedApplication.mAddress));
                gDConnectedApplication.setDisplayName(connectedApplication.mName);
                int i2 = connectedApplication.mState;
                if (i2 == 0) {
                    gDConnectedApplication.setState(GDConnectedApplicationState.StateActivated);
                } else if (i2 == 1) {
                    gDConnectedApplication.setState(GDConnectedApplicationState.StateRemoved);
                }
                arrayList.add(gDConnectedApplication);
            }
        }
        return arrayList;
    }

    public static native String getLinkKey(String str);

    public static native String getSubContainerPolicy(String str);

    public static native boolean isSubContainerActivated(String str);

    public static native boolean isSubcontainerAllowed();

    public static native Object[] nativeGetConnectedApplications();

    public static native void nativeSubContainerPolicyAcknowledgement(String str, String str2);

    public static void onActivationCompletedSuccess(GTContainerInfo gTContainerInfo, String str) {
        String format = String.format("{'wearDeviceId':'%s', 'wearDeviceName':'%s', 'wearGdAppId':'%s', 'wearNativeAppId':'%s', 'wearGdVersion':'%s',}", gTContainerInfo.mDeviceID, gTContainerInfo.mDeviceName, gTContainerInfo.mGDAppID, gTContainerInfo.mNativeBundleID, gTContainerInfo.mGDAppVersion);
        GDLog.DBGPRINTF(16, "GDSubContainerManager onActivationCompletedSuccess subContainerInfo = " + format);
        saveLinkKey(format, str);
    }

    public static native void persistAuthorisationSequenceID(String str, int i);

    public static native void removeConnectedApplication(String str);

    public static native int retrieveAuthorisationSequenceID(String str);

    public static native void saveLinkKey(String str, String str2);

    public static void subContainerPolicyAcknowledgement(String str, String str2) {
        nativeSubContainerPolicyAcknowledgement(InterDeviceUtils.getNodeID(str2), str);
        if (str.equals(KWIPE_COMMAND)) {
            GDLog.DBGPRINTF(16, "GDSubContainerManager Wipe policy acknowledged");
            GDConnectedApplicationSupportImpl.getInstance().updateConnectedApplications();
        }
    }
}
