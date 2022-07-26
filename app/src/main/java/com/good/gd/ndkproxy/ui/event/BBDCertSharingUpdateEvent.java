package com.good.gd.ndkproxy.ui.event;

import com.good.gd.ndkproxy.ui.GDIdentitySharedStoreHandler;

/* loaded from: classes.dex */
public class BBDCertSharingUpdateEvent extends BBDUIUpdateEvent {
    private GDIdentitySharedStoreHandler.IMPORT_SHARING_RESULT resultState;

    public BBDCertSharingUpdateEvent(GDIdentitySharedStoreHandler.IMPORT_SHARING_RESULT import_sharing_result) {
        super(UIEventType.UI_UPDATE_CERT_SHARING_UI);
        this.resultState = import_sharing_result;
    }

    public GDIdentitySharedStoreHandler.IMPORT_SHARING_RESULT getResultState() {
        return this.resultState;
    }

    @Override // com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent
    public String toString() {
        return "[state=" + this.resultState + "]";
    }
}
