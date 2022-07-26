package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import android.text.TextUtils;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.BlockBaseUI;
import com.good.gd.ndkproxy.ui.event.BBDAppLockUpdateEvent;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent;
import com.good.gd.ndkproxy.ui.event.UIEventType;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.utils.ApplicationInfoProvider;
import com.good.gd.ui.utils.BlockMessageBuilder;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class AuthDelegationBlockedUI extends BlockBaseUI {
    public static final int SSO_FAILURE_APP_NOT_INSTALLED = 0;
    public static final int SSO_FAILURE_ENTERPRISE_USER_NOT_MATCH = 4;
    public static final int SSO_FAILURE_SECURITY = 1;
    private String bundleId;
    private String messageText;

    public AuthDelegationBlockedUI(long j, boolean z, String str, int i, BlockFacade blockFacade, ApplicationInfoProvider applicationInfoProvider) {
        super(BBUIType.UI_INTERAPP_LOCK, j, z, true, i, blockFacade, applicationInfoProvider);
        this.bundleId = str;
        proceedInterApplicationLockState();
    }

    private static native void _unlock(long j);

    private BlockMessageBuilder getDetailsMessageBuilder(BBDAppLockUpdateEvent bBDAppLockUpdateEvent) {
        boolean z = !TextUtils.isEmpty(bBDAppLockUpdateEvent.getDownloadLocation());
        BlockMessageBuilder blockMessageBuilder = new BlockMessageBuilder(this.blockFacade, this.applicationInfo);
        if (bBDAppLockUpdateEvent.getFailureReason() != 4) {
            blockMessageBuilder.addMessageKey("AD is not installed. Details: BundleID, Name, Version, no URL");
        } else if (z) {
            blockMessageBuilder.addMessageKey("AD is registered to another user. Details: BundleID, Name, Version, ObtainFrom");
        } else {
            blockMessageBuilder.addMessageKey("AD is registered to another user. Details: BundleID, Name, Version, no URL");
        }
        if (z) {
            blockMessageBuilder.setParams(bBDAppLockUpdateEvent.getBundleId(), bBDAppLockUpdateEvent.getName(), bBDAppLockUpdateEvent.getVersion(), bBDAppLockUpdateEvent.getDownloadLocation());
        } else {
            blockMessageBuilder.setParams(bBDAppLockUpdateEvent.getBundleId(), bBDAppLockUpdateEvent.getName(), bBDAppLockUpdateEvent.getVersion());
        }
        return blockMessageBuilder;
    }

    private void proceedInterApplicationLockState() {
        GDLog.DBGPRINTF(14, "InterAppLockReason = " + this.interAppLockReason);
        BlockMessageBuilder blockMessageBuilder = new BlockMessageBuilder(this.blockFacade, this.applicationInfo);
        int i = this.interAppLockReason;
        if (i == 1) {
            blockMessageBuilder.addMessageKey("This application does not have security permission to communicate with authentication application");
        } else if (i != 4) {
            if (i == 0) {
                this.blockTitle = "Authorization Unavailable";
            } else {
                this.blockTitle = "Access Blocked";
            }
            if (this.willUpdateLocation) {
                blockMessageBuilder.addMessageKey("AD is not installed. Details: BundleID").setParams(this.bundleId);
            } else if (this.offerRemoteUnlock) {
                if (this.interAppLockReason == 0) {
                    blockMessageBuilder.addMessageKey("AD is unavailable. Details: BundleID, Name, Version, no URL").setParams("", this.bundleId, "");
                } else {
                    blockMessageBuilder.addMessageKey(GDLocalizer.getLocalizedString("AD is not installed")).addMessageKey("\n\n").addMessageKey(GDLocalizer.getLocalizedString("InterApp lock description message"));
                }
            } else {
                blockMessageBuilder.addMessageKey("AD is not installed");
            }
        } else {
            this.blockTitle = "Cannot Authenticate";
            if (this.willUpdateLocation) {
                blockMessageBuilder.addMessageKey("AD is registered to another user. Details: BundleID");
            } else {
                blockMessageBuilder.addMessageKey("AD is registered to another user");
            }
        }
        this.messageText = blockMessageBuilder.build();
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BlockBaseUI, com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new AuthDelegationBlockView(context, viewInteractor, this, viewCustomizer);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BlockBaseUI
    public String getMessageText() {
        return this.messageText;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BlockBaseUI
    public String getTitleText() {
        return GDLocalizer.getLocalizedString(this.blockTitle);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onMessage(BBDUIMessageType bBDUIMessageType, Object obj) {
        if (bBDUIMessageType.ordinal() != 4) {
            super.onMessage(bBDUIMessageType, obj);
        } else {
            _unlock(getCoreHandle());
        }
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BlockBaseUI, com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void setUpdateData(BBDUIUpdateEvent bBDUIUpdateEvent) {
        GDLog.DBGPRINTF(16, "AuthDelegationBlockedUI.setUpdateData() IN");
        if ((bBDUIUpdateEvent instanceof BBDAppLockUpdateEvent) && bBDUIUpdateEvent.getType() == UIEventType.UI_UPDATE_INTERAPP_LOCK) {
            BBDAppLockUpdateEvent bBDAppLockUpdateEvent = (BBDAppLockUpdateEvent) bBDUIUpdateEvent;
            this.willUpdateLocation = false;
            this.bundleId = bBDAppLockUpdateEvent.getBundleId();
            this.interAppLockReason = bBDAppLockUpdateEvent.getFailureReason();
            this.messageText = getDetailsMessageBuilder(bBDAppLockUpdateEvent).build();
            GDLog.DBGPRINTF(16, "AuthDelegationBlockedUI.setUpdateData() OUT");
            return;
        }
        super.setUpdateData(bBDUIUpdateEvent);
    }
}
