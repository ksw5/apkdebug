package com.good.gd.ndkproxy.native2javabridges.ui;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class AlertButtons {
    private List<AlertButton> mList = new ArrayList();

    /* loaded from: classes.dex */
    public class AlertButton {
        private long mHandle;
        private String mTitle;

        AlertButton(String str, long j) {
            this.mTitle = str;
            this.mHandle = j;
        }

        public long getHandle() {
            return this.mHandle;
        }

        public String getTitleKey() {
            return this.mTitle;
        }
    }

    public void addButton(String str, long j) {
        this.mList.add(new AlertButton(str, j));
    }

    public List<AlertButton> getButtons() {
        return this.mList;
    }
}
