package com.good.gd.ndkproxy.ui.data;

import android.content.Context;
import android.content.pm.PackageManager;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.BlockBaseUI;
import com.good.gd.ui.GDMDMBlockView;
import com.good.gd.ui.ViewCustomizer;
import com.good.gd.ui.ViewInteractor;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.utils.ApplicationInfoProvider;
import com.good.gd.ui.utils.BlockMessageBuilder;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class MDMBlockedUI extends BlockBaseUI {
    private static final int AGENT_VERSION_UNKNOWN = -1;
    private int agentMinimumVersion;
    private final ApplicationInfoProvider applicationInfo;

    /* loaded from: classes.dex */
    public enum MdmAgentState {
        INSTALLED,
        UPGRADE_REQUIRED,
        NOT_INSTALLED
    }

    public MDMBlockedUI(long j, int i, BlockFacade blockFacade, ApplicationInfoProvider applicationInfoProvider) {
        super(BBUIType.UI_BLOCK, j, blockFacade, applicationInfoProvider);
        this.agentMinimumVersion = -1;
        this.blockReason = i;
        this.applicationInfo = applicationInfoProvider;
        proceedBlockState();
        if (i == 13) {
            String agentMinVersion = blockFacade.getAgentMinVersion();
            if (agentMinVersion.isEmpty()) {
                return;
            }
            try {
                this.agentMinimumVersion = Integer.parseInt(agentMinVersion);
            } catch (NumberFormatException e) {
                GDLog.DBGPRINTF(12, "Invalid agent minimum version: " + agentMinVersion);
            }
        }
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BlockBaseUI, com.good.gd.ndkproxy.ui.data.base.BaseUI
    /* renamed from: createView */
    public GDView mo294createView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        return new GDMDMBlockView(context, viewInteractor, this, viewCustomizer, this.blockFacade);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BlockBaseUI
    public String getMessageText() {
        return this.messageText;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BlockBaseUI
    public String getTitleText() {
        return GDLocalizer.getLocalizedString(this.blockTitle);
    }

    public MdmAgentState updateMdmAgentState(PackageManager packageManager) {
        MdmAgentState mdmAgentState = MdmAgentState.NOT_INSTALLED;
        try {
            int i = packageManager.getPackageInfo(this.blockFacade.getAgentInfo(), 1).versionCode;
            mdmAgentState = MdmAgentState.INSTALLED;
            if (i < this.agentMinimumVersion) {
                this.messageText = new BlockMessageBuilder(this.blockFacade, this.applicationInfo).addMessageKey("Wipe/Block MDM update required").build();
                mdmAgentState = MdmAgentState.UPGRADE_REQUIRED;
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (IllegalArgumentException e2) {
            GDLog.DBGPRINTF(12, "Unable to determine agent version\n");
        }
        GDLog.DBGPRINTF(14, "GDBlockState::updateMdmAgentState - result: " + mdmAgentState + '\n');
        return mdmAgentState;
    }
}
