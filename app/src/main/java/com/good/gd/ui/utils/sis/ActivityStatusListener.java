package com.good.gd.ui.utils.sis;

/* loaded from: classes.dex */
public interface ActivityStatusListener {
    int getCurrentSISParticipationStatus();

    boolean isRequireToShowCustomPermissionDialog();

    boolean isRequireToShowDeviceLocationSettingsDialog();

    boolean isRequireToShowSISPrimerScreen();

    boolean isRequireToShowSystemPermissionDialog();

    void setIsCustomPermissionDialogAlreadyShown(boolean z);

    void setIsDeviceLocationDialogAlreadyShown(boolean z);

    void setIsPrimerScreenAlreadyShown(boolean z);

    void setIsSettingsActivityRunning(boolean z);

    void setIsSystemPermissionDialogAlreadyShown(boolean z);

    void setSISLearnMoreActivityRunning(boolean z);
}
