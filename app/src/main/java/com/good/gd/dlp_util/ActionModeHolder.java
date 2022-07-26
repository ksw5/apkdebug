package com.good.gd.dlp_util;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

/* loaded from: classes.dex */
public class ActionModeHolder implements ActionModeControl, ActionMode.Callback {
    private ActionMode actionMode;
    private final ActionMode.Callback actionModeCallback;

    public ActionModeHolder(ActionMode.Callback callback) {
        this.actionModeCallback = callback;
    }

    @Override // android.view.ActionMode.Callback
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        this.actionMode = actionMode;
        return this.actionModeCallback.onActionItemClicked(actionMode, menuItem);
    }

    @Override // android.view.ActionMode.Callback
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        return this.actionModeCallback.onCreateActionMode(actionMode, menu);
    }

    @Override // android.view.ActionMode.Callback
    public void onDestroyActionMode(ActionMode actionMode) {
        this.actionModeCallback.onDestroyActionMode(actionMode);
        this.actionMode = null;
    }

    @Override // android.view.ActionMode.Callback
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return this.actionModeCallback.onPrepareActionMode(actionMode, menu);
    }

    @Override // com.good.gd.dlp_util.ActionModeControl
    public void stopActionMode() {
        ActionMode actionMode = this.actionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
    }
}
