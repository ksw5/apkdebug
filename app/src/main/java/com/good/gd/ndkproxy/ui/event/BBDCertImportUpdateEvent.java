package com.good.gd.ndkproxy.ui.event;

import com.good.gd.ndkproxy.ui.GDPKCSPassword;
import com.good.gd.ndkproxy.ui.data.CertificateImportUI;

/* loaded from: classes.dex */
public class BBDCertImportUpdateEvent extends BBDUIUpdateEvent {
    private String label;
    private boolean requireCert;
    private boolean requirePassword;
    private String sMessage;
    private String uuid;
    private GDPKCSPassword.StateType stateType = GDPKCSPassword.StateType.GcError;
    private CertificateImportUI.PKCSInstructionEnum instructionType = CertificateImportUI.PKCSInstructionEnum.UNKNOWN;
    private String message = "";

    /* loaded from: classes.dex */
    public static class NewRequestObject {
        public String label;
        public boolean requireCert;
        public boolean requirePassword;
        String sMessage;
        GDPKCSPassword.StateType stateType;
        public String uuid;
    }

    public BBDCertImportUpdateEvent() {
        super(UIEventType.UI_UPDATE_CERT_IMPORT_UI);
    }

    public CertificateImportUI.PKCSInstructionEnum getInstructionType() {
        return this.instructionType;
    }

    public String getLabel() {
        return this.label;
    }

    public String getMessage() {
        return this.message;
    }

    public NewRequestObject getNewRequest() {
        NewRequestObject newRequestObject = new NewRequestObject();
        newRequestObject.stateType = this.stateType;
        newRequestObject.uuid = this.uuid;
        newRequestObject.label = this.label;
        newRequestObject.sMessage = this.sMessage;
        newRequestObject.requirePassword = this.requirePassword;
        newRequestObject.requireCert = this.requireCert;
        return newRequestObject;
    }

    public GDPKCSPassword.StateType getStateType() {
        return this.stateType;
    }

    public String getUuid() {
        return this.uuid;
    }

    public boolean isRequireCert() {
        return this.requireCert;
    }

    public boolean isRequirePassword() {
        return this.requirePassword;
    }

    public void setInstructionType(CertificateImportUI.PKCSInstructionEnum pKCSInstructionEnum) {
        this.instructionType = pKCSInstructionEnum;
    }

    public void setLabel(String str) {
        this.label = str;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setRequireCert(boolean z) {
        this.requireCert = z;
    }

    public void setRequirePassword(boolean z) {
        this.requirePassword = z;
    }

    public void setStateType(GDPKCSPassword.StateType stateType) {
        this.stateType = stateType;
    }

    public void setUuid(String str) {
        this.uuid = str;
    }

    public void setsMessage(String str) {
        this.sMessage = str;
    }
}
