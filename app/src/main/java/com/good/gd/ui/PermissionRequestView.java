package com.good.gd.ui;

import android.app.Activity;
import android.content.Context;
import com.good.gd.ui.base_ui.GDView;
import com.good.gd.ui.utils.permissions.PermissionsListener;
import com.good.gd.ui.utils.permissions.PermissionsRequestor;
import com.good.gd.ui.utils.permissions.PermissionsSequenceRequestor;
import java.util.LinkedList;

/* loaded from: classes.dex */
public abstract class PermissionRequestView extends GDView implements PermissionsListener {
    private Activity mActivity;
    protected PermissionsRequestor mPermissionsRequestor = new PermissionsSequenceRequestor();

    public PermissionRequestView(Context context, ViewInteractor viewInteractor, ViewCustomizer viewCustomizer) {
        super(context, viewInteractor, viewCustomizer);
        this.mActivity = (Activity) context;
    }

    public void onAllPermissionsRequested() {
    }

    public void onPermissionDenied(String str) {
    }

    public void onPermissionGranted(String str) {
    }

    @Override // com.good.gd.ui.base_ui.GDView
    public void onPermissions(int i, String[] strArr, int[] iArr) {
        this.mPermissionsRequestor.onPermissions(i, strArr, iArr);
    }

    public void requestNextPermissions() {
        this.mPermissionsRequestor.requestNextPermissions();
    }

    public void requestPermissions(LinkedList<String> linkedList, int i) {
        this.mPermissionsRequestor.requestPermissions(this.mActivity, linkedList, i, this);
    }

    public boolean shouldShowExplanation(String str) {
        return false;
    }

    public void showExplanation(String str) {
    }
}
