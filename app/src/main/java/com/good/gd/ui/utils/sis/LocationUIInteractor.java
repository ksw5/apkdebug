package com.good.gd.ui.utils.sis;

import android.app.Activity;

/* loaded from: classes.dex */
public interface LocationUIInteractor {
    Activity getActivity();

    LocationDialogListener getLocationDialogListener();

    String getLocationTag();

    void requestCloseView();

    void requestLocationPermission();
}
