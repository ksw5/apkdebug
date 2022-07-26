package com.good.gd.context;

import android.content.Context;
import com.good.gd.client.GDClient;
import com.good.gd.containerstate.ContainerState;
import com.good.gd.containerstate.ndkproxy.GDActivitySupport;
import com.good.gd.error.GDNotAuthorizedError;
import com.good.gd.ndkproxy.backgroundAuth.GDBackgroundAuthImpl;
import com.good.gd.ndkproxy.bypass.GDBypassAbilityImpl;
import com.good.gd.service.GDServiceHelper;
import com.good.gd.utils.ErrorUtils;
import com.good.gd.utils.GDSDKState;
import com.good.gd.utils.GDSDKStateListener;
import com.good.gd.utils.UserAuthUtils;
import com.good.gt.context.GTBaseContext;

/* loaded from: classes.dex */
public class GDContext implements GDSDKStateListener, ContainerState, GDContextAPI {
    private static GDContext _instance;
    private GDServiceHelper gdServiceHelper;
    private GDBackgroundAuthImpl m_Background_Auth;
    private GDBypassAbilityImpl m_Bypass_Unlock_Service;

    private GDContext() {
        GDSDKState.getInstance().setGDSDKStateListener(this);
    }

    public static GDContext getInstance() {
        if (_instance == null) {
            _instance = new GDContext();
        }
        return _instance;
    }

    @Override // com.good.gd.utils.GDSDKStateListener, com.good.gd.containerstate.ContainerState
    public void checkAuthorized() throws GDNotAuthorizedError {
        if (!UserAuthUtils.isAuthorised()) {
            ErrorUtils.throwGDNotAuthorizedError();
        }
    }

    @Override // com.good.gd.utils.GDSDKStateListener
    public Context getApplicationContext() {
        return GTBaseContext.getInstance().getApplicationContext();
    }

    @Override // com.good.gd.context.GDContextAPI
    public Object getDynamicsService(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == 610962921) {
            if (str.equals("dynamics_bypass_service")) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 1312575001) {
            if (hashCode == 1706011674 && str.equals("gd_service_helper")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals("background_auth")) {
                c = 2;
            }
            c = 65535;
        }
        if (c == 0) {
            if (this.m_Bypass_Unlock_Service == null) {
                this.m_Bypass_Unlock_Service = new GDBypassAbilityImpl();
            }
            return this.m_Bypass_Unlock_Service;
        } else if (c == 1) {
            if (this.gdServiceHelper == null) {
                this.gdServiceHelper = new GDServiceHelper();
            }
            return this.gdServiceHelper;
        } else if (c != 2) {
            return null;
        } else {
            if (this.m_Background_Auth == null) {
                this.m_Background_Auth = new GDBackgroundAuthImpl();
            }
            return this.m_Background_Auth;
        }
    }

    @Override // com.good.gd.containerstate.ContainerState
    public boolean isAuthorized() {
        return UserAuthUtils.isAuthorised();
    }

    @Override // com.good.gd.containerstate.ContainerState
    public boolean isGDIdAllowed(String str) {
        return GDClient.isGDIdAllowed(str);
    }

    @Override // com.good.gd.utils.GDSDKStateListener, com.good.gd.containerstate.ContainerState
    public boolean isWiped() {
        return GDActivitySupport.getContainerState() == 2;
    }

    public void setContext(Context context) {
        GTBaseContext.getInstance().setContext(context.getApplicationContext());
    }
}
