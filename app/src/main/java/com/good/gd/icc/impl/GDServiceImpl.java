package com.good.gd.icc.impl;

import android.os.Bundle;
import com.good.gd.error.GDNotAuthorizedError;
import com.good.gd.icc.GDICCForegroundOptions;
import com.good.gd.icc.GDServiceError;
import com.good.gd.icc.GDServiceErrorCode;
import com.good.gd.icc.GDServiceErrorHandler;
import com.good.gd.icc.GDServiceException;
import com.good.gd.icc.GDServiceListener;
import com.good.gd.utils.ErrorUtils;
import com.good.gt.icc.BundleKeys;
import com.good.gt.icc.GTServicesException;
import com.good.gt.icc.ICCController;
import com.good.gt.icc.IccServicesServer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes.dex */
public final class GDServiceImpl {
    private static GDServiceImpl _instance;
    GDIccProviderInterface mProviderInterface;
    GDServiceListener mServicesListener;

    /* loaded from: classes.dex */
    class hbfhc implements Runnable {
        final /* synthetic */ String dbjc;

        hbfhc(String str) {
            this.dbjc = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                Thread.sleep(1000L);
                GDServiceImpl.this.bringToFront(this.dbjc);
            } catch (GDServiceException e) {
            } catch (InterruptedException e2) {
            }
        }
    }

    private GDServiceImpl() {
    }

    public static synchronized GDServiceImpl getInstance() {
        GDServiceImpl gDServiceImpl;
        synchronized (GDServiceImpl.class) {
            if (_instance == null) {
                _instance = new GDServiceImpl();
            }
            gDServiceImpl = _instance;
        }
        return gDServiceImpl;
    }

    public void bringToFront(String str) throws GDServiceException, GDNotAuthorizedError {
        if (this.mProviderInterface == null) {
            ErrorUtils.throwGDNotAuthorizedError();
        }
        this.mProviderInterface.checkAuthorized();
        try {
            this.mProviderInterface.getServicesServer().bringToFront(str);
        } catch (GTServicesException e) {
            throw GDServiceErrorHandler.GDServiceException(e);
        }
    }

    public void replyTo(String str, Object obj, GDICCForegroundOptions gDICCForegroundOptions, String[] strArr, String str2) throws GDServiceException, GDNotAuthorizedError {
        if (this.mProviderInterface == null) {
            ErrorUtils.throwGDNotAuthorizedError();
        }
        this.mProviderInterface.checkAuthorized();
        IccServicesServer servicesServer = this.mProviderInterface.getServicesServer();
        if (str != null) {
            if (obj != null && !(obj instanceof Boolean) && !(obj instanceof Double) && !(obj instanceof Integer) && !(obj instanceof Map) && !(obj instanceof ArrayList) && !(obj instanceof String) && !(obj instanceof byte[]) && !(obj instanceof GDServiceError)) {
                throw GDServiceErrorHandler.GDServiceException(GDServiceErrorCode.GDServicesErrorInvalidParams, null);
            }
            Bundle bundle = new Bundle();
            if (!(obj instanceof GDServiceError)) {
                bundle.putSerializable(BundleKeys.GTBundleTypeKey, BundleKeys.GTBundleTypeObject);
                bundle.putSerializable(BundleKeys.GTBundlePayload, (Serializable) obj);
            } else {
                GDServiceError gDServiceError = (GDServiceError) obj;
                boolean z = gDServiceError.getErrorCode() == GDServiceErrorCode.GDServicesErrorCustom;
                int customErrorCode = z ? gDServiceError.getCustomErrorCode() : gDServiceError.getErrorCode().getValue();
                bundle.putSerializable(BundleKeys.GTBundleTypeKey, BundleKeys.GTBundleTypeError);
                bundle.putSerializable(BundleKeys.GTBundleErrorCode, Integer.valueOf(customErrorCode));
                bundle.putSerializable(BundleKeys.GTBundleCustomError, Boolean.valueOf(z));
                bundle.putSerializable(BundleKeys.GTErrorMessage, gDServiceError.getMessage());
                bundle.putSerializable(BundleKeys.GTBundlePayload, (Serializable) gDServiceError.getDetails());
            }
            try {
                servicesServer.replyTo(str, bundle, ICCController.ForegroundOption.createFromOrdinal(gDICCForegroundOptions.ordinal()), strArr, str2);
                return;
            } catch (GTServicesException e) {
                if (e.getMajor() == GTServicesException.Code.SERVICES_SENDING_APP_IN_BACKGROUND) {
                    try {
                        new Thread(new hbfhc(str)).start();
                        return;
                    } catch (Throwable th) {
                        return;
                    }
                }
                throw GDServiceErrorHandler.GDServiceException(e);
            }
        }
        throw GDServiceErrorHandler.GDServiceException(GDServiceErrorCode.GDServicesErrorInvalidParams, null);
    }

    public void setProviderInterface(GDIccProviderInterface gDIccProviderInterface) {
        this.mProviderInterface = gDIccProviderInterface;
        GDServiceListener gDServiceListener = this.mServicesListener;
        if (gDServiceListener != null) {
            gDIccProviderInterface.setApplicationListener(gDServiceListener);
        }
    }

    public void setServiceListener(GDServiceListener gDServiceListener) throws GDServiceException, GDNotAuthorizedError {
        GDIccProviderInterface gDIccProviderInterface = this.mProviderInterface;
        if (gDIccProviderInterface != null) {
            gDIccProviderInterface.setApplicationListener(gDServiceListener);
        } else {
            this.mServicesListener = gDServiceListener;
        }
    }
}
