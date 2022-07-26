package com.good.gd.mam;

import java.util.Date;
import java.util.List;

/* loaded from: classes.dex */
public class GDCatalogReviews {
    private long laterReviewCount;
    private List<GDCatalogReview> reviews;
    private Date thresholdTimestamp;

    public long getLaterReviewCount() {
        return this.laterReviewCount;
    }

    public List<GDCatalogReview> getReviews() {
        return this.reviews;
    }

    public Date getThresholdTimestamp() {
        return this.thresholdTimestamp;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setLaterReviewCount(long j) {
        this.laterReviewCount = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setReviews(List<GDCatalogReview> list) {
        this.reviews = list;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setThresholdTimestamp(Date date) {
        this.thresholdTimestamp = date;
    }
}
