package com.good.gd.ndkproxy.ui.data;

import android.text.TextUtils;
import com.good.gd.ndkproxy.ui.BBUIType;
import com.good.gd.ndkproxy.ui.data.base.BlockBaseUI;
import com.good.gd.ui.utils.ApplicationInfoProvider;
import com.good.gd.ui.utils.BlockMessageBuilder;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class ContainerWipedUI extends BlockBaseUI {
    private BlockMessageBuilder blockMessageBuilder;
    private final ClientDefinedStrings clientDefinedStrings;

    public ContainerWipedUI(long j, int i, BlockFacade blockFacade, ApplicationInfoProvider applicationInfoProvider, ClientDefinedStrings clientDefinedStrings) {
        super(BBUIType.UI_DEVICE_WIPE, j, i, "", blockFacade, applicationInfoProvider);
        this.clientDefinedStrings = clientDefinedStrings;
        proceedWipeState();
    }

    private void proceedWipeState() {
        this.isWiped = true;
        this.blockTitle = "Invalid Device";
        String wipeMessage = this.clientDefinedStrings.getWipeMessage();
        this.blockMessageBuilder = new BlockMessageBuilder(this.blockFacade, this.applicationInfo);
        if (TextUtils.isEmpty(wipeMessage)) {
            this.blockMessageBuilder = this.blockMessageBuilder.setBlockMessage(getBlockReason(), getBlockMessageKey(), isWiped());
        } else {
            this.blockMessageBuilder = this.blockMessageBuilder.addMessageKey(wipeMessage);
        }
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BlockBaseUI
    public String getMessageText() {
        return this.blockMessageBuilder.build();
    }

    @Override // com.good.gd.ndkproxy.ui.data.base.BlockBaseUI
    public String getTitleText() {
        return GDLocalizer.getLocalizedString(this.blockTitle);
    }
}
