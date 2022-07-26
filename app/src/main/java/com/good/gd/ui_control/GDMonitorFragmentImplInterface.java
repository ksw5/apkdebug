package com.good.gd.ui_control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/* loaded from: classes.dex */
public interface GDMonitorFragmentImplInterface {
    void fragmentStartActivityForResult(Intent intent, int i);

    Activity getContainerActivity();

    Bundle getFragmentArguments();

    void setFragmentRetainInstance(boolean z);

    boolean shouldIgnoreDetach();
}
