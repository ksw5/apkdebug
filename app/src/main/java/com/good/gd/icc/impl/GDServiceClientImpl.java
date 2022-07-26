package com.good.gd.icc.impl;

import android.os.Bundle;
import com.good.gd.error.GDNotAuthorizedError;
import com.good.gd.icc.GDICCForegroundOptions;
import com.good.gd.icc.GDServiceClientListener;
import com.good.gd.icc.GDServiceErrorCode;
import com.good.gd.icc.GDServiceErrorHandler;
import com.good.gd.icc.GDServiceException;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.utils.ErrorUtils;
import com.good.gt.icc.BundleKeys;
import com.good.gt.icc.GTServicesException;
import com.good.gt.icc.ICCController;
import com.good.gt.icc.IccServicesClient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes.dex */
public final class GDServiceClientImpl {
    private static GDServiceClientImpl _instance;
    GDIccConsumerInterface mConsumerInterface;
    GDServiceClientListener mServicesClientListener;

    private GDServiceClientImpl() {
    }

    public static synchronized GDServiceClientImpl getInstance() {
        GDServiceClientImpl gDServiceClientImpl;
        synchronized (GDServiceClientImpl.class) {
            if (_instance == null) {
                _instance = new GDServiceClientImpl();
            }
            gDServiceClientImpl = _instance;
        }
        return gDServiceClientImpl;
    }

    public void bringToFront(String str) throws GDServiceException, GDNotAuthorizedError {
        if (this.mConsumerInterface == null) {
            ErrorUtils.throwGDNotAuthorizedError();
        }
        this.mConsumerInterface.checkAuthorized();
        try {
            this.mConsumerInterface.getServicesClient().bringToFront(str);
        } catch (GTServicesException e) {
            throw GDServiceErrorHandler.GDServiceException(e);
        }
    }

    public String sendTo(String str, String str2, String str3, String str4, Object obj, String[] strArr, GDICCForegroundOptions gDICCForegroundOptions) throws GDServiceException, GDNotAuthorizedError {
        if (this.mConsumerInterface == null) {
            ErrorUtils.throwGDNotAuthorizedError();
        }
        this.mConsumerInterface.checkAuthorized();
        IccServicesClient servicesClient = this.mConsumerInterface.getServicesClient();
        if (str != null) {
            if (obj != null && !(obj instanceof Boolean) && !(obj instanceof Double) && !(obj instanceof Integer) && !(obj instanceof Map) && !(obj instanceof ArrayList) && !(obj instanceof String) && !(obj instanceof byte[])) {
                throw GDServiceErrorHandler.GDServiceException(GDServiceErrorCode.GDServicesErrorInvalidParams, null);
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable(BundleKeys.GTBundleTypeKey, BundleKeys.GTBundleTypeObject);
            bundle.putSerializable(BundleKeys.GTBundlePayload, (Serializable) obj);
            try {
                return servicesClient.sendTo(str, str2, str3, str4, bundle, strArr, ICCController.ForegroundOption.createFromOrdinal(gDICCForegroundOptions.ordinal()));
            } catch (GTServicesException e) {
                GDLog.DBGPRINTF(12, "Exception = " + e.getMessage() + " error = " + e.toString());
                throw GDServiceErrorHandler.GDServiceException(e);
            }
        }
        throw GDServiceErrorHandler.GDServiceException(GDServiceErrorCode.GDServicesErrorInvalidParams, null);
    }

    public void setConsumerInterface(GDIccConsumerInterface gDIccConsumerInterface) {
        this.mConsumerInterface = gDIccConsumerInterface;
        GDServiceClientListener gDServiceClientListener = this.mServicesClientListener;
        if (gDServiceClientListener != null) {
            gDIccConsumerInterface.setApplicationListener(gDServiceClientListener);
        }
    }

    public void setServiceClientListener(GDServiceClientListener gDServiceClientListener) throws GDServiceException, GDNotAuthorizedError {
        GDIccConsumerInterface gDIccConsumerInterface = this.mConsumerInterface;
        if (gDIccConsumerInterface != null) {
            gDIccConsumerInterface.setApplicationListener(gDServiceClientListener);
        } else {
            this.mServicesClientListener = gDServiceClientListener;
        }
    }
}
