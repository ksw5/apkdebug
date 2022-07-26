package com.good.gd.support.impl;

import android.content.Context;
import com.good.gd.support.GDConnectedApplication;
import com.good.gd.support.GDConnectedApplicationState;
import com.good.gd.support.GDConnectedApplicationType;
import java.util.ArrayList;

/* loaded from: classes.dex */
public interface GDConnectedApplicationControlListener {
    GDConnectedApplicationState getConnectedApplicationState(String str);

    GDConnectedApplicationType getConnectedApplicationType(String str);

    ArrayList<GDConnectedApplication> getConnectedApplications();

    boolean isConnectedApplicationActivationAllowed();

    void removeConnectedApplication(String str);

    boolean startConnectedApplicationActivation(String str, Context context);
}
