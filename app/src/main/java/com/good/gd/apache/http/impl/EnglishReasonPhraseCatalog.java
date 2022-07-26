package com.good.gd.apache.http.impl;

import com.good.gd.apache.http.HttpStatus;
import com.good.gd.apache.http.ReasonPhraseCatalog;
import java.util.Locale;

/* loaded from: classes.dex */
public class EnglishReasonPhraseCatalog implements ReasonPhraseCatalog {
    public static final EnglishReasonPhraseCatalog INSTANCE = new EnglishReasonPhraseCatalog();
    private static final String[][] REASON_PHRASES = {null, new String[3], new String[8], new String[8], new String[25], new String[8]};

    static {
        setReason(200, "OK");
        setReason(HttpStatus.SC_CREATED, "Created");
        setReason(HttpStatus.SC_ACCEPTED, "Accepted");
        setReason(HttpStatus.SC_NO_CONTENT, "No Content");
        setReason(HttpStatus.SC_MOVED_PERMANENTLY, "Moved Permanently");
        setReason(HttpStatus.SC_MOVED_TEMPORARILY, "Moved Temporarily");
        setReason(HttpStatus.SC_NOT_MODIFIED, "Not Modified");
        setReason(400, "Bad Request");
        setReason(401, "Unauthorized");
        setReason(403, "Forbidden");
        setReason(HttpStatus.SC_NOT_FOUND, "Not Found");
        setReason(500, "Internal Server Error");
        setReason(501, "Not Implemented");
        setReason(HttpStatus.SC_BAD_GATEWAY, "Bad Gateway");
        setReason(503, "Service Unavailable");
        setReason(100, "Continue");
        setReason(HttpStatus.SC_TEMPORARY_REDIRECT, "Temporary Redirect");
        setReason(HttpStatus.SC_METHOD_NOT_ALLOWED, "Method Not Allowed");
        setReason(HttpStatus.SC_CONFLICT, "Conflict");
        setReason(HttpStatus.SC_PRECONDITION_FAILED, "Precondition Failed");
        setReason(HttpStatus.SC_REQUEST_TOO_LONG, "Request Too Long");
        setReason(HttpStatus.SC_REQUEST_URI_TOO_LONG, "Request-URI Too Long");
        setReason(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE, "Unsupported Media Type");
        setReason(HttpStatus.SC_MULTIPLE_CHOICES, "Multiple Choices");
        setReason(HttpStatus.SC_SEE_OTHER, "See Other");
        setReason(HttpStatus.SC_USE_PROXY, "Use Proxy");
        setReason(402, "Payment Required");
        setReason(HttpStatus.SC_NOT_ACCEPTABLE, "Not Acceptable");
        setReason(HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED, "Proxy Authentication Required");
        setReason(HttpStatus.SC_REQUEST_TIMEOUT, "Request Timeout");
        setReason(101, "Switching Protocols");
        setReason(HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION, "Non Authoritative Information");
        setReason(HttpStatus.SC_RESET_CONTENT, "Reset Content");
        setReason(HttpStatus.SC_PARTIAL_CONTENT, "Partial Content");
        setReason(504, "Gateway Timeout");
        setReason(505, "Http Version Not Supported");
        setReason(HttpStatus.SC_GONE, "Gone");
        setReason(HttpStatus.SC_LENGTH_REQUIRED, "Length Required");
        setReason(HttpStatus.SC_REQUESTED_RANGE_NOT_SATISFIABLE, "Requested Range Not Satisfiable");
        setReason(HttpStatus.SC_EXPECTATION_FAILED, "Expectation Failed");
        setReason(102, "Processing");
        setReason(HttpStatus.SC_MULTI_STATUS, "Multi-Status");
        setReason(HttpStatus.SC_UNPROCESSABLE_ENTITY, "Unprocessable Entity");
        setReason(HttpStatus.SC_INSUFFICIENT_SPACE_ON_RESOURCE, "Insufficient Space On Resource");
        setReason(HttpStatus.SC_METHOD_FAILURE, "Method Failure");
        setReason(HttpStatus.SC_LOCKED, "Locked");
        setReason(507, "Insufficient Storage");
        setReason(HttpStatus.SC_FAILED_DEPENDENCY, "Failed Dependency");
    }

    protected EnglishReasonPhraseCatalog() {
    }

    private static void setReason(int i, String str) {
        int i2 = i / 100;
        REASON_PHRASES[i2][i - (i2 * 100)] = str;
    }

    @Override // com.good.gd.apache.http.ReasonPhraseCatalog
    public String getReason(int i, Locale locale) {
        if (i >= 100 && i < 600) {
            int i2 = i / 100;
            int i3 = i - (i2 * 100);
            String[][] strArr = REASON_PHRASES;
            if (strArr[i2].length <= i3) {
                return null;
            }
            return strArr[i2][i3];
        }
        throw new IllegalArgumentException("Unknown category for status code " + i + ".");
    }
}
