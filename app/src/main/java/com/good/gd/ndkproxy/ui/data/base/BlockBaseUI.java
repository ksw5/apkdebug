package com.good.gd.ndkproxy.ui.data.base;

import android.content.Context;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.BlockFacade;
import com.good.gd.ndkproxy.ui.event.BBDUIUpdateEvent;
import com.good.gd.ndkproxy.ui.event.UIEventType;
import com.good.gd.ui.GDBlockView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.utils.ApplicationInfoProvider;
import com.good.gd.ui.utils.BlockMessageBuilder;

/* loaded from: classes.dex */
public abstract class BlockBaseUI extends BaseUI {
    protected final ApplicationInfoProvider applicationInfo;
    protected final BlockFacade blockFacade;
    protected String blockMessage;
    protected int blockReason;
    protected String blockTitle;
    protected GDDialogType dialogType;
    protected int interAppLockReason;
    protected boolean isWiped;
    protected String messageText;
    protected boolean offerRemoteUnlock;
    protected boolean willUpdateLocation;

    /* loaded from: classes.dex */
    public enum GDDialogType {
        UI_DIALOG_NONE,
        UI_DIALOG_DEVICE_WIPE_OR_RESET
    }

    public BlockBaseUI(BBUIType bBUIType, long j, BlockFacade blockFacade, ApplicationInfoProvider applicationInfoProvider) {
        super(bBUIType, j);
        this.messageText = "";
        this.interAppLockReason = -1;
        this.isWiped = false;
        this.dialogType = GDDialogType.UI_DIALOG_NONE;
        this.blockFacade = blockFacade;
        this.applicationInfo = applicationInfoProvider;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDBlockView(context, viewInteractor, this, viewCustomizer);
    }

    public void dialogAcknowledged() {
        this.dialogType = GDDialogType.UI_DIALOG_NONE;
    }

    public GDDialogType dialogWaiting() {
        return this.dialogType;
    }

    public String getAdditionalMessageText() {
        return "";
    }

    public String getBlockMessageKey() {
        return this.blockMessage;
    }

    public int getBlockReason() {
        return this.blockReason;
    }

    public String getBlockTitleKey() {
        return this.blockTitle;
    }

    public int getInterAppLockReason() {
        return this.interAppLockReason;
    }

    public String getMessageText() {
        return "";
    }

    public String getTitleText() {
        return "";
    }

    public boolean isGettingAccessKey() {
        return false;
    }

    public boolean isOfferRemoteUnlock() {
        return this.offerRemoteUnlock;
    }

    public boolean isUpdating() {
        return this.willUpdateLocation;
    }

    public boolean isWillUpdateLocation() {
        return this.willUpdateLocation;
    }

    public boolean isWiped() {
        return this.isWiped;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void proceedBlockState() {
        String str;
        int i = this.blockReason;
        if ((i != 18 && i != 24) || (str = this.blockTitle) == null || str.length() == 0) {
            this.blockTitle = "Blocked";
        }
        this.messageText = new BlockMessageBuilder(this.blockFacade, this.applicationInfo).setBlockMessage(getBlockReason(), getBlockMessageKey(), isWiped()).build();
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void setUpdateData(BBDUIUpdateEvent bBDUIUpdateEvent) {
        if (bBDUIUpdateEvent.getType() != UIEventType.UI_OPEN_WIPE_OR_RESET_DIALOG) {
            super.setUpdateData(bBDUIUpdateEvent);
        }
        this.dialogType = GDDialogType.UI_DIALOG_DEVICE_WIPE_OR_RESET;
    }

    public BlockBaseUI(BBUIType bBUIType, long j, String str, BlockFacade blockFacade, ApplicationInfoProvider applicationInfoProvider) {
        super(bBUIType, j);
        this.messageText = "";
        this.interAppLockReason = -1;
        this.isWiped = false;
        this.dialogType = GDDialogType.UI_DIALOG_NONE;
        this.blockTitle = str;
        this.blockFacade = blockFacade;
        this.applicationInfo = applicationInfoProvider;
    }

    public BlockBaseUI(BBUIType bBUIType, long j, int i, String str, BlockFacade blockFacade, ApplicationInfoProvider applicationInfoProvider) {
        super(bBUIType, j);
        this.messageText = "";
        this.interAppLockReason = -1;
        this.isWiped = false;
        this.dialogType = GDDialogType.UI_DIALOG_NONE;
        this.blockReason = i;
        this.blockMessage = str;
        this.blockFacade = blockFacade;
        this.applicationInfo = applicationInfoProvider;
    }

    public BlockBaseUI(BBUIType bBUIType, long j, int i, String str, String str2, BlockFacade blockFacade, ApplicationInfoProvider applicationInfoProvider) {
        super(bBUIType, j);
        this.messageText = "";
        this.interAppLockReason = -1;
        this.isWiped = false;
        this.dialogType = GDDialogType.UI_DIALOG_NONE;
        this.blockReason = i;
        this.blockTitle = str;
        this.blockMessage = str2;
        this.blockFacade = blockFacade;
        this.applicationInfo = applicationInfoProvider;
    }

    public BlockBaseUI(BBUIType bBUIType, long j, boolean z, boolean z2, int i, BlockFacade blockFacade, ApplicationInfoProvider applicationInfoProvider) {
        super(bBUIType, j);
        this.messageText = "";
        this.interAppLockReason = -1;
        this.isWiped = false;
        this.dialogType = GDDialogType.UI_DIALOG_NONE;
        this.willUpdateLocation = z;
        this.offerRemoteUnlock = z2;
        this.interAppLockReason = i;
        this.blockFacade = blockFacade;
        this.applicationInfo = applicationInfoProvider;
    }
}
