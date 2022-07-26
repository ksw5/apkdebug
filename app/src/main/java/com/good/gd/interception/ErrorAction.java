package com.good.gd.interception;

import android.content.Context;
import com.good.gd.GDDialog;
import com.good.gd.interception.ServiceAction;

/* loaded from: classes.dex */
public class ErrorAction implements ServiceAction<Void> {
    final String errorMessage;
    final GDDialog gdDialog;

    public ErrorAction(String str, GDDialog gDDialog) {
        this.errorMessage = str;
        this.gdDialog = gDDialog;
    }

    public void execute(Context context) {
        this.gdDialog.show(this.errorMessage, context);
    }

    @Override // com.good.gd.interception.ServiceAction
    public void execute(Callback<Void> callback, Context context) {
    }
}
