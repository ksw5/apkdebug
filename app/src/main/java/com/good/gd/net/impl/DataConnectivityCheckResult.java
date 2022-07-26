package com.good.gd.net.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class DataConnectivityCheckResult {
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
    private boolean mAvailable;
    private String mCheckedURL;
    private Date mDate;
    private DataConnectivityType mResult;

    /* JADX INFO: Access modifiers changed from: package-private */
    public DataConnectivityCheckResult(Date date, String str, DataConnectivityType dataConnectivityType) {
        this.mDate = date;
        this.mCheckedURL = str;
        this.mResult = dataConnectivityType;
        this.mAvailable = dataConnectivityType != DataConnectivityType.DC_CAPTIVE_PORTAL;
    }

    public String getFormattedTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(this.mDate);
    }

    public DataConnectivityType getResult() {
        return this.mResult;
    }

    public String getResultString() {
        return this.mResult.getName();
    }

    public String getURLString() {
        return this.mCheckedURL;
    }

    public boolean isAvailable() {
        return this.mAvailable;
    }
}
