package com.good.gd.ui;

import android.app.Activity;
import android.os.Message;
import android.view.LayoutInflater;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ndkproxy.ui.data.dialog.DialogUI;
import com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent;

/* loaded from: classes.dex */
public interface ViewInteractor {
    void cancelDialog();

    void cleanInternalDialogHandler();

    void cleanInternalDialogUI();

    void closeInternalUI();

    /* renamed from: getInternalActivity */
    Activity mo295getInternalActivity();

    LayoutInflater getLayoutInflater();

    void moveTaskToBack();

    void requestNewUIs(BaseUI baseUI, DialogUI dialogUI);

    void sendMessage(Message message);

    void showDialog();

    void updateHost(BBDUIUpdateEvent bBDUIUpdateEvent);

    void updateUI(BBDUIObject bBDUIObject);
}
