package com.good.gd.ndkproxy.ui.data;

import android.text.TextUtils;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.BlockBaseUI;
import com.good.gd.ndkproxy.ui.event.BBDUIMessageType;
import com.good.gd.ui.utils.ApplicationInfoProvider;
import com.good.gd.ui.utils.BlockMessageBuilder;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class BlockedUI extends BlockBaseUI {
    private final ClientDefinedStrings clientDefinedStrings;

    public BlockedUI(int i, String str, String str2, long j, BlockFacade blockFacade, ApplicationInfoProvider applicationInfoProvider, ClientDefinedStrings clientDefinedStrings) {
        super(BBUIType.UI_BLOCK, j, i, str, str2, blockFacade, applicationInfoProvider);
        this.clientDefinedStrings = clientDefinedStrings;
        if (i == 12) {
            proceedRemoteLockState();
        } else {
            proceedBlockState();
        }
    }

    private void proceedRemoteLockState() {
        this.blockTitle = "Remote Lock";
        String blockMessage = this.clientDefinedStrings.getBlockMessage();
        BlockMessageBuilder blockMessageBuilder = new BlockMessageBuilder(this.blockFacade, this.applicationInfo);
        if (TextUtils.isEmpty(blockMessage)) {
            blockMessageBuilder.setBlockMessage(getBlockReason(), getBlockMessageKey(), isWiped());
        } else {
            blockMessageBuilder.addMessageKey(blockMessage);
        }
        this.messageText = blockMessageBuilder.build();
    }

    private void requestRemoteUnlock() {
        this.blockFacade.remoteUnlock(getCoreHandle());
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BlockBaseUI
    public String getMessageText() {
        return this.messageText;
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BlockBaseUI
    public String getTitleText() {
        return GDLocalizer.getLocalizedString(this.blockTitle);
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BlockBaseUI
    public boolean isOfferRemoteUnlock() {
        if (getBlockReason() == 12) {
            return true;
        }
        return super.isOfferRemoteUnlock();
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BBDUIObject
    public void onMessage(BBDUIMessageType bBDUIMessageType, Object obj) {
        if (bBDUIMessageType.ordinal() != 4) {
            super.onMessage(bBDUIMessageType, obj);
        } else {
            requestRemoteUnlock();
        }
    }
}
