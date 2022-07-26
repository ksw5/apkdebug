package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import com.good.gd.messages.PKCSPasswordMsg;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.GDPKCSPassword;
import com.good.gd.ndkproxy.ui.data.base.BaseUI;
import com.good.gd.ndkproxy.ui.event.BBDCertImportUpdateEvent;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent;
import com.good.gd.ui.GDPKCSPasswordView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;

/* loaded from: classes.dex */
public class CertificateImportUI extends BaseUI {
    private BBDCertImportUpdateEvent certImportEvent;
    private String label;
    private boolean requireCert;
    private boolean requirePassword;
    private GDPKCSPassword.StateType stateType;
    private String uuid;

    /* loaded from: classes.dex */
    public enum PKCSInstructionEnum {
        PASSW_RESULT_PCKS12,
        PASSW_RESULT_OTP,
        AFTER_POSTPONE,
        RESPONSE_FOR_GET_NEW_REQUEST,
        REFRESH,
        UNKNOWN
    }

    public CertificateImportUI(long j, GDPKCSPassword.StateType stateType, String str, String str2, boolean z, boolean z2) {
        super(BBUIType.UI_SMIME_PKCS12_PASSWORD, j);
        this.stateType = stateType;
        this.uuid = str;
        this.label = str2;
        this.requirePassword = z;
        this.requireCert = z2;
        GDPKCSPassword.getInstance();
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDPKCSPasswordView(context, viewInteractor, this, viewCustomizer);
    }

    public BBDCertImportUpdateEvent getCertImportEvent() {
        return this.certImportEvent;
    }

    public String getLabel() {
        return this.label;
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

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onMessage(BBDUIMessageType bBDUIMessageType, Object obj) {
        int ordinal = bBDUIMessageType.ordinal();
        if (ordinal == 25) {
            GDPKCSPassword.getInstance().handlePKCSPasswordMsgRequest(getCoreHandle(), (PKCSPasswordMsg) obj);
        } else if (ordinal != 26) {
            super.onMessage(bBDUIMessageType, obj);
        } else {
            GDPKCSPassword.getInstance().skipPKCSPasswordMsgRequest(getCoreHandle());
        }
    }

    public void setRequireCert(boolean z) {
        this.requireCert = z;
    }

    public void setRequirePassword(boolean z) {
        this.requirePassword = z;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void setUpdateData(BBDUIUpdateEvent bBDUIUpdateEvent) {
        if (!(bBDUIUpdateEvent instanceof BBDCertImportUpdateEvent)) {
            super.setUpdateData(bBDUIUpdateEvent);
            return;
        }
        BBDCertImportUpdateEvent bBDCertImportUpdateEvent = (BBDCertImportUpdateEvent) bBDUIUpdateEvent;
        this.certImportEvent = bBDCertImportUpdateEvent;
        if (!bBDCertImportUpdateEvent.getInstructionType().equals(PKCSInstructionEnum.RESPONSE_FOR_GET_NEW_REQUEST)) {
            return;
        }
        this.stateType = this.certImportEvent.getStateType();
        this.uuid = this.certImportEvent.getUuid();
        this.label = this.certImportEvent.getLabel();
        this.requireCert = this.certImportEvent.isRequireCert();
        this.requirePassword = this.certImportEvent.isRequirePassword();
    }
}
