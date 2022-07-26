package com.good.gd.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.view.GravityCompat;
import com.good.gd.database.sqlite.SQLiteDatabase;
import com.good.gd.ndkproxy.GDLog;
import com.good.gd.ndkproxy.ui.data.BlockFacade;
import com.good.gd.ndkproxy.ui.data.MDMBlockedUI;
import com.good.gd.resources.R;
import com.good.gd.utils.GDLocalizer;

/* loaded from: classes.dex */
public class GDMDMBlockView extends GDBlockView {
    private MDMBlockedUI.MdmAgentState _mdmAgentState;
    private final BlockFacade blockFacade;
    protected final TextView customMessageView;
    private final TextView mdmButton;

    /* loaded from: classes.dex */
    class hbfhc implements OnClickListener {
        final /* synthetic */ BlockFacade dbjc;
        final /* synthetic */ Context qkduk;

        hbfhc(BlockFacade blockFacade, Context context) {
            this.dbjc = blockFacade;
            this.qkduk = context;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            String agentInfo = this.dbjc.getAgentInfo();
            if (GDMDMBlockView.this._mdmAgentState == MDMBlockedUI.MdmAgentState.INSTALLED) {
                try {
                    Intent launchIntentForPackage = this.qkduk.getPackageManager().getLaunchIntentForPackage(agentInfo);
                    launchIntentForPackage.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                    this.qkduk.startActivity(launchIntentForPackage);
                    return;
                } catch (NullPointerException | SecurityException e) {
                    GDLog.DBGPRINTF(12, "Unable to start Agent app " + agentInfo + " due to exception\n");
                    Toast.makeText(this.qkduk, "Error Communicating with MDM Agent App, Contact IT Admin", 0).show();
                    return;
                }
            }
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + agentInfo));
                intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                this.qkduk.startActivity(intent);
            } catch (ActivityNotFoundException e2) {
                Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + agentInfo));
                intent2.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                this.qkduk.startActivity(intent2);
            }
        }
    }

    public GDMDMBlockView(Context context, ViewInteractor viewInteractor, MDMBlockedUI mDMBlockedUI, ViewCustomizer viewCustomizer, BlockFacade blockFacade) {
        super(context, viewInteractor, mDMBlockedUI, viewCustomizer);
        String str;
        this.blockFacade = blockFacade;
        TextView textView = (TextView) findViewById(R.id.COM_GOOD_GD_BLOCK_VIEW_CUSTOM_MESSAGE_VIEW);
        this.customMessageView = textView;
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setVisibility(4);
        TextView textView2 = (TextView) findViewById(R.id.gd_mdm_activation_button);
        this.mdmButton = textView2;
        if (this.uiData.getBlockReason() == 13) {
            MDMBlockedUI.MdmAgentState updateMdmAgentState = mDMBlockedUI.updateMdmAgentState(context.getPackageManager());
            this._mdmAgentState = updateMdmAgentState;
            int ordinal = updateMdmAgentState.ordinal();
            if (ordinal == 0) {
                str = "InitiateMDM";
            } else if (ordinal == 1) {
                str = "UpdateMdmAgent";
            } else if (ordinal != 2) {
                GDLog.DBGPRINTF(12, "Invalid agent state");
                str = "";
            } else {
                str = "InstallMdmAgent";
            }
            if (!str.isEmpty()) {
                textView2.setText(GDLocalizer.getLocalizedString(str));
                textView2.setVisibility(0);
            }
        }
        textView2.setOnClickListener(new hbfhc(blockFacade, context));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.good.gd.ui.GDBlockView
    public void setMessage(String str) {
        super.setMessage(str);
        if (this.blockFacade.getEnrollmentFailureReason() == null || this.blockFacade.getEnrollmentFailureReason().length() <= 0) {
            return;
        }
        this.customMessageView.setVisibility(0);
        this.customMessageView.setMovementMethod(new ScrollingMovementMethod());
        this.customMessageView.setText(this.blockFacade.getEnrollmentFailureReason());
        setTextWithoutUnderlinesInLinks(this.customMessageView);
    }

    @Override // com.good.gd.ui.GDBlockView
    protected void updateGravity() {
        boolean z = false;
        int i = 1;
        boolean z2 = this.unlockButton.getVisibility() == 0;
        boolean z3 = this.okButton.getVisibility() == 0;
        if (this.mdmButton.getVisibility() == 0) {
            z = true;
        }
        if (z2 || z3 || z) {
            i = GravityCompat.START;
        }
        this.titleView.setGravity(i);
        this.messageView.setGravity(i);
    }
}
