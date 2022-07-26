package com.good.gd.ui_control;

import com.good.gd.bypass.BypassPolicyListener;
import com.good.gd.bypass.GDBypassAbility;
import com.good.gd.context.GDContext;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.bypass.GDBypassAbilityImpl;
import com.good.gd.ndkproxy.ui.BBDUIHelper;
import com.good.gd.ndkproxy.ui.BBDUIManager;
import com.good.gd.ndkproxy.ui.data.base.BBDUIObject;

/* loaded from: classes.dex */
public class GDMonitorBypassFragmentImpl extends GDMonitorFragmentImpl implements BypassPolicyListener {
    public static final String BYPASS_ACTIVITY_TAG = "com.good.gd.bypassunlock";

    public GDMonitorBypassFragmentImpl(GDMonitorFragmentImplInterface gDMonitorFragmentImplInterface) {
        super(gDMonitorFragmentImplInterface);
    }

    private boolean isBypassAllowed() {
        if (!((GDBypassAbilityImpl) GDContext.getInstance().getDynamicsService("dynamics_bypass_service")).isBypassAllowed()) {
            return false;
        }
        if (isBypassIdleLockAllowedView()) {
            return true;
        }
        GDLog.DBGPRINTF(16, "GDMonitorBypassFragmentImpl.isBypassAllowed view can't be bypassed\n");
        return false;
    }

    private boolean isBypassIdleLockAllowedView() {
        BBDUIObject currentUI = BBDUIManager.getInstance().getCurrentUI();
        if (currentUI != null) {
            boolean isBypassAllowed = BBDUIHelper.isBypassAllowed(currentUI.getCoreHandle());
            GDLog.DBGPRINTF(14, "GDMonitorBypassFragmentImpl isBypassIdleLockAllowedView view = " + currentUI.getBBDUIType() + ", allowed: " + isBypassAllowed + " \n");
            return isBypassAllowed;
        }
        return true;
    }

    @Override // com.good.gd.ui_control.GDMonitorFragmentImpl
    protected void activityResumed() {
        if (!isBypassAllowed()) {
            this._uac.activityResumed(getContainerActivity());
        }
    }

    @Override // com.good.gd.ui_control.GDMonitorFragmentImpl
    protected void activityWindowFocusGained() {
        if (!isBypassAllowed()) {
            this._uac.activityWindowFocusGained(getContainerActivity());
        }
    }

    @Override // com.good.gd.ui_control.GDMonitorFragmentImpl
    protected boolean forceShow() {
        return isBypassAllowed();
    }

    @Override // com.good.gd.ui_control.GDMonitorFragmentImpl
    protected boolean isInactivityWarningRequired() {
        return false;
    }

    @Override // com.good.gd.bypass.BypassPolicyListener
    public void onBypassPolicyChanged(boolean z) {
        GDLog.DBGPRINTF(16, "GDMonitorBypassFragmentImpl::onBypassPolicyChanged\n");
        if (!this.mActivityInForground || !z || !isBypassAllowed()) {
            return;
        }
        this._uac.activityPaused(getContainerActivity());
    }

    @Override // com.good.gd.ui_control.GDMonitorFragmentImpl
    public void onFragmentCreate() {
        super.onFragmentCreate();
        ((GDBypassAbility) GDContext.getInstance().getDynamicsService("dynamics_bypass_service")).addBypassPolicyListener(this);
    }

    @Override // com.good.gd.ui_control.GDMonitorFragmentImpl
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        ((GDBypassAbility) GDContext.getInstance().getDynamicsService("dynamics_bypass_service")).removeBypassPolicyListener(this);
    }

    @Override // com.good.gd.ui_control.GDMonitorFragmentImpl, com.good.gd.GDStateListener
    public void onLocked() {
        GDLog.DBGPRINTF(16, "GDMonitorBypassFragmentImpl::onLocked\n");
        if (!isBypassAllowed()) {
            super.onLocked();
            if (this._uac.getCurrentActivity() != null) {
                return;
            }
            startInternalActivityForResult();
        }
    }

    @Override // com.good.gd.ui_control.GDMonitorFragmentImpl, com.good.gd.GDStateListener
    public void onWiped() {
        GDLog.DBGPRINTF(16, "GDMonitorBypassFragmentImpl::onWiped\n");
        super.onWiped();
        if (this._uac.getCurrentActivity() == null) {
            startInternalActivityForResult();
        }
    }
}
