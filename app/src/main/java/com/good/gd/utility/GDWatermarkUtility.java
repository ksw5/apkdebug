package com.good.gd.utility;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import com.good.gd.GDAndroid;
import com.good.gd.ndkproxy.GDLog;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes.dex */
public class GDWatermarkUtility {
    public static void drawWaterMark(Canvas canvas, Paint paint, Context context, View view) {
        Canvas canvas2 = canvas;
        String userId = getUserId();
        if (userId.isEmpty()) {
            GDLog.DBGPRINTF(12, "GDWatermarkUtility::drawWaterMark: userId is empty. Failed to render watermark \n");
        } else if (!isAllowedByPolicy()) {
            GDLog.DBGPRINTF(16, "GDWatermarkUtility::drawWaterMark: watermark policy disabled. Returning \n");
        } else {
            int width = view.getWidth();
            int height = view.getHeight();
            int systemWindowInsetBottom = view.getRootWindowInsets().getSystemWindowInsetBottom();
            int systemWindowInsetRight = view.getRootWindowInsets().getSystemWindowInsetRight();
            int systemWindowInsetLeft = view.getRootWindowInsets().getSystemWindowInsetLeft();
            int systemWindowInsetTop = view.getRootWindowInsets().getSystemWindowInsetTop();
            int i = systemWindowInsetTop * 2;
            if (systemWindowInsetBottom > i + 10) {
                systemWindowInsetBottom = i;
            }
            float f = context.getResources().getDisplayMetrics().density;
            int i2 = (int) (40.0f * f);
            int argb = Color.argb(30, 154, 154, 154);
            paint.setColor(Color.alpha(30));
            paint.setStyle(Paint.Style.FILL);
            canvas2.clipRect(systemWindowInsetLeft, systemWindowInsetTop, systemWindowInsetRight > 0 ? width - systemWindowInsetRight : width, systemWindowInsetBottom > 0 ? height - systemWindowInsetBottom : height);
            canvas.drawPaint(paint);
            paint.setColor(argb);
            paint.setTextSize(i2);
            String dateAndTimeAsString = getDateAndTimeAsString();
            String gMTOffsetAsString = getGMTOffsetAsString();
            int i3 = (int) (f * 200.0f);
            int i4 = height > width ? 150 : 10;
            int measureText = (int) paint.measureText(userId);
            int measureText2 = (int) paint.measureText(dateAndTimeAsString);
            int measureText3 = (int) paint.measureText(gMTOffsetAsString);
            int i5 = (measureText2 > measureText ? measureText2 - measureText : measureText - measureText2) / 2;
            int i6 = (measureText3 > measureText ? measureText3 - measureText : measureText - measureText3) / 2;
            boolean z = measureText > measureText2;
            boolean z2 = measureText > measureText3;
            int i7 = i4;
            int sqrt = (int) Math.sqrt(Math.pow(width, 2.0d) + Math.pow(height, 2.0d));
            int i8 = z ? sqrt / (measureText + i3) : (sqrt / (measureText2 + i3)) + 1;
            int i9 = (sqrt / i3) + 5;
            int i10 = 0;
            int i11 = 10;
            int i12 = i7;
            while (i10 < i9) {
                canvas.save();
                canvas2.rotate(-45, i11, i12);
                int i13 = z ? measureText : measureText2;
                int i14 = i10;
                int i15 = i12;
                int i16 = measureText2;
                int i17 = measureText;
                int i18 = i11;
                int i19 = i3;
                int i20 = i11;
                int i21 = i13;
                int i22 = i9;
                String str = dateAndTimeAsString;
                int i23 = i2;
                int i24 = height;
                String str2 = userId;
                renderWaterMark(canvas, paint, userId, dateAndTimeAsString, gMTOffsetAsString, i2, i18, i15, i21, i5, i6, i8, i19, z, z2);
                canvas.restore();
                int i25 = i15 + i19;
                if (i25 >= i24) {
                    i11 = i20 + i19;
                    i12 = i24;
                } else {
                    i12 = i25;
                    i11 = i20;
                }
                i10 = i14 + 1;
                height = i24;
                i9 = i22;
                measureText2 = i16;
                measureText = i17;
                i3 = i19;
                dateAndTimeAsString = str;
                i2 = i23;
                userId = str2;
                canvas2 = canvas;
            }
        }
    }

    private static String getDateAndTimeAsString() {
        return DateFormat.getDateTimeInstance(3, 3, Locale.getDefault()).format(new Date(System.currentTimeMillis()));
    }

    private static String getGMTOffsetAsString() {
        return " [" + new SimpleDateFormat("Z").format(new Date()) + " GMT]";
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static String getUserId() {
        /*
            java.lang.String r0 = "@"
            java.lang.String r1 = ""
            com.good.gd.GDAndroid r2 = com.good.gd.GDAndroid.getInstance()     // Catch: com.good.gd.error.GDError -> L2d
            java.util.Map r2 = r2.getApplicationConfig()     // Catch: com.good.gd.error.GDError -> L2d
            java.lang.String r3 = "userId"
            java.lang.Object r2 = r2.get(r3)     // Catch: com.good.gd.error.GDError -> L2d
            java.lang.String r2 = (java.lang.String) r2     // Catch: com.good.gd.error.GDError -> L2d
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch: com.good.gd.error.GDError -> L2b
            if (r3 != 0) goto L36
            int r3 = r2.indexOf(r0)     // Catch: com.good.gd.error.GDError -> L2b
            if (r3 <= 0) goto L36
            r3 = 0
            int r0 = r2.indexOf(r0)     // Catch: com.good.gd.error.GDError -> L2b
            java.lang.String r2 = r2.substring(r3, r0)     // Catch: com.good.gd.error.GDError -> L2b
            goto L36
        L2b:
            r0 = move-exception
            goto L2f
        L2d:
            r0 = move-exception
            r2 = r1
        L2f:
            r0 = 14
            java.lang.String r3 = "GDWatermarkUtility::getUserId : GDError while retrieving userid.  \n"
            com.good.gd.ndkproxy.GDLog.DBGPRINTF(r0, r3)
        L36:
            if (r2 == 0) goto L39
            r1 = r2
        L39:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.good.gd.utility.GDWatermarkUtility.getUserId():java.lang.String");
    }

    private static boolean isAllowedByPolicy() {
        GDLog.DBGPRINTF(16, "GDWatermarkUtility::isAllowedByPolicy  IN \n");
        if (GDAndroid.getInstance().getApplicationPolicyString().contains("\"blackberry.security.EnableDLPWatermark\": true")) {
            GDLog.DBGPRINTF(16, "GDWatermarkUtility::Watermark policy is found and is enabled");
            return true;
        }
        GDLog.DBGPRINTF(16, "GDWatermarkUtility::Watermark policy is not found or not enabled");
        return false;
    }

    private static void renderWaterMark(Canvas canvas, Paint paint, String str, String str2, String str3, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z, boolean z2) {
        GDLog.DBGPRINTF(14, "GDWatermarkUtility::renderWaterMark\n");
        int i9 = i2;
        for (int i10 = 0; i10 < i7; i10++) {
            canvas.drawText(str, i9, i3, paint);
            canvas.drawText(str2, z ? i9 + i5 : i9 - i5, i3 + i, paint);
            canvas.drawText(str3, z2 ? i9 + i6 : i9 - i6, (i * 2) + i3, paint);
            i9 += i4 + i8;
        }
    }
}
