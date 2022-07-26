package com.good.gd;

import android.content.Context;
import android.content.Intent;
import com.good.gd.context.GDContext;
import com.good.gd.machines.activation.GDActivationManager;
import java.util.List;

/* loaded from: classes.dex */
public class ApplicationState {
    public static final String BBDActivationDescriptionKey = "BBDActivationDescriptionKey";
    public static final String BBDActivationErrorKey = "BBDActivationErrorKey";
    public static final String BBDActivationProcessingIndexKey = "BBDActivationProcessingIndexKey";
    public static final String BBDActivationStateKey = "BBDActivationStateKey";

    /* loaded from: classes.dex */
    public enum ActivationParameter {
        UserIdentifier,
        AccessKey,
        Password,
        NOCAddress,
        EnrollmentAddress,
        ShowUserInterface
    }

    /* loaded from: classes.dex */
    public enum ActivationState {
        ENotActivated,
        EActivationInProgress,
        EActivated,
        EActivatingForegroundRequired
    }

    public static final GDAppResultCode getActivationError(Intent intent) {
        return (GDAppResultCode) intent.getSerializableExtra(BBDActivationErrorKey);
    }

    public static final ActivationState getActivationState(Intent intent) {
        return (ActivationState) intent.getSerializableExtra(BBDActivationStateKey);
    }

    public static final String getActivationStateDescription(Intent intent) {
        return intent.getStringExtra(BBDActivationDescriptionKey);
    }

    public static final int getActivationStateIndex(Intent intent) {
        return intent.getIntExtra(BBDActivationProcessingIndexKey, 0);
    }

    public List<String> getActivationProcessingDescriptions() {
        return GDActivationManager.getInstance().getActivationProcessingDescriptions();
    }

    public ActivationState getActivationState(Context context) {
        GDContext.getInstance().setContext(context.getApplicationContext());
        return GDActivationManager.getInstance().getActivationState();
    }
}
