package com.good.gd.ui.utils.permissions;

import android.app.Activity;
import android.content.Context;
import java.util.Arrays;
import java.util.LinkedList;

/* loaded from: classes.dex */
public class PermissionsSequenceRequestor implements PermissionsRequestor {
    private Activity mActivity;
    private int mRequestCode;
    protected LinkedList<String> mPermissionsToRequest = new LinkedList<>();
    private LinkedList<String> mNextPermissionsToRequest = new LinkedList<>();
    private PermissionsListener mPermissionsListener = null;

    @Override // com.good.gd.ui.utils.permissions.PermissionsRequestor
    public void cancelRequesting() {
        this.mPermissionsToRequest.clear();
        this.mPermissionsListener.onAllPermissionsRequested();
    }

    protected String getNextPermissionToRequest() {
        if (this.mPermissionsToRequest.isEmpty()) {
            return null;
        }
        return this.mPermissionsToRequest.peek();
    }

    @Override // com.good.gd.ui.utils.permissions.PermissionsRequestor
    public boolean isPermissionGranted(String str) {
        return this.mActivity.checkSelfPermission(str) == 0;
    }

    protected void onPermissionDenied(String str) {
        this.mPermissionsListener.onPermissionDenied(str);
    }

    protected void onPermissionGranted(String str) {
        this.mPermissionsListener.onPermissionGranted(str);
    }

    @Override // com.good.gd.ui.utils.permissions.PermissionsRequestor
    public void onPermissions(int i, String[] strArr, int[] iArr) {
        boolean z = strArr.length == this.mNextPermissionsToRequest.size() && Arrays.equals(strArr, this.mNextPermissionsToRequest.toArray(new String[0])) && i == this.mRequestCode;
        if (i == this.mRequestCode && strArr.length == 0 && iArr.length == 0) {
            this.mPermissionsListener.showExplanation(this.mNextPermissionsToRequest.element());
        } else if (z) {
            for (int i2 = 0; i2 < iArr.length; i2++) {
                if (iArr[i2] == 0) {
                    onPermissionGranted(strArr[i2]);
                } else {
                    onPermissionDenied(strArr[i2]);
                }
            }
        }
    }

    @Override // com.good.gd.ui.utils.permissions.PermissionsRequestor
    public void removePermission(String str) {
        this.mPermissionsToRequest.remove(str);
    }

    @Override // com.good.gd.ui.utils.permissions.PermissionsRequestor
    public void requestNextPermissions() {
        this.mNextPermissionsToRequest.clear();
        String nextPermissionToRequest = getNextPermissionToRequest();
        if (nextPermissionToRequest == null) {
            this.mPermissionsListener.onAllPermissionsRequested();
        } else if (isPermissionGranted(nextPermissionToRequest)) {
            onPermissionGranted(nextPermissionToRequest);
        } else if (this.mPermissionsListener.shouldShowExplanation(nextPermissionToRequest)) {
            this.mPermissionsListener.showExplanation(nextPermissionToRequest);
        } else {
            this.mNextPermissionsToRequest.add(nextPermissionToRequest);
            this.mActivity.requestPermissions(new String[]{nextPermissionToRequest}, this.mRequestCode);
        }
    }

    @Override // com.good.gd.ui.utils.permissions.PermissionsRequestor
    public void requestPermissions(Context context, LinkedList<String> linkedList, int i, PermissionsListener permissionsListener) {
        this.mActivity = (Activity) context;
        this.mPermissionsToRequest = linkedList;
        this.mRequestCode = i;
        this.mPermissionsListener = permissionsListener;
        requestNextPermissions();
    }
}
