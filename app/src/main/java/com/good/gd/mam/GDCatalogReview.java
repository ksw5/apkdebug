package com.good.gd.mam;

import com.good.gd.GDVersion;

/* loaded from: classes.dex */
public class GDCatalogReview {
    private String content;
    private int rating;
    private String reviewIdentifier;
    private GDVersion reviewedEntitlementVersion;
    private String submitter;
    private String title;

    public String getContent() {
        return this.content;
    }

    public int getRating() {
        return this.rating;
    }

    public String getReviewIdentifier() {
        return this.reviewIdentifier;
    }

    public GDVersion getReviewedEntitlementVersion() {
        return this.reviewedEntitlementVersion;
    }

    public String getSubmitter() {
        return this.submitter;
    }

    public String getTitle() {
        return this.title;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setContent(String str) {
        this.content = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setRating(int i) {
        this.rating = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setReviewIdentifier(String str) {
        this.reviewIdentifier = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setReviewedEntitlementVersion(GDVersion gDVersion) {
        this.reviewedEntitlementVersion = gDVersion;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setSubmitter(String str) {
        this.submitter = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setTitle(String str) {
        this.title = str;
    }
}
