package com.good.gd.richtext;

import android.os.Parcel;
import android.text.Annotation;
import android.text.ParcelableSpan;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.CharacterStyle;
import android.text.style.EasyEditSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.LocaleSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuggestionSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import com.good.gd.ndkproxy.GDLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class RichTextUtils {
    private static final int ABSOLUTE_SIZE_SPAN = 16;
    private static final int ALIGNMENT_SPAN = 1;
    private static final int ANNOTATION = 18;
    private static final int BACKGROUND_COLOR_SPAN = 12;
    private static final int BULLET_SPAN = 8;
    public static final int EASY_EDIT_SPAN = 22;
    private static final int FIRST_SPAN = 1;
    private static final int FOREGROUND_COLOR_SPAN = 2;
    public static final int LAST_SPAN = 24;
    private static final int LEADING_MARGIN_SPAN = 10;
    public static final int LOCALE_SPAN = 23;
    private static final int QUOTE_SPAN = 9;
    private static final int RELATIVE_SIZE_SPAN = 3;
    private static final int SCALE_X_SPAN = 4;
    private static Map<Class<?>, Integer> SPANS_ID = new hbfhc();
    private static final int STRIKETHROUGH_SPAN = 5;
    private static final int STYLE_SPAN = 7;
    private static final int SUBSCRIPT_SPAN = 15;
    private static final int SUGGESTION_SPAN = 19;
    private static final int SUPERSCRIPT_SPAN = 14;
    private static final String TAG = "RichTextUtils";
    private static final int TEXT_APPEARANCE_SPAN = 17;
    public static final int TTS_SPAN = 24;
    private static final int TYPEFACE_SPAN = 13;
    private static final int UNDERLINE_SPAN = 6;
    private static final int URL_SPAN = 11;

    /* loaded from: classes.dex */
    static class hbfhc extends HashMap<Class<?>, Integer> {
        hbfhc() {
            put(AlignmentSpan.Standard.class, 1);
            put(ForegroundColorSpan.class, 2);
            put(RelativeSizeSpan.class, 3);
            put(ScaleXSpan.class, 4);
            put(StrikethroughSpan.class, 5);
            put(UnderlineSpan.class, 6);
            put(StyleSpan.class, 7);
            put(BulletSpan.class, 8);
            put(QuoteSpan.class, 9);
            put(LeadingMarginSpan.Standard.class, 10);
            put(URLSpan.class, 11);
            put(BackgroundColorSpan.class, 12);
            put(TypefaceSpan.class, 13);
            put(SuperscriptSpan.class, 14);
            put(SubscriptSpan.class, 15);
            put(AbsoluteSizeSpan.class, 16);
            put(TextAppearanceSpan.class, 17);
            put(Annotation.class, 18);
            put(SuggestionSpan.class, 19);
            put(EasyEditSpan.class, 22);
            put(LocaleSpan.class, 23);
        }
    }

    public static void applySpans(List<SpanInfo> list, SpannableStringBuilder spannableStringBuilder) {
        Object obj;
        for (SpanInfo spanInfo : list) {
            if (spanInfo != null && (obj = spanInfo.span) != null) {
                spannableStringBuilder.setSpan(obj, spanInfo.start, spanInfo.end, spanInfo.flags);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ParcelableSpan createFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == -1) {
            return null;
        }
        switch (readInt) {
            case 1:
                return new AlignmentSpan.Standard(parcel);
            case 2:
                return new ForegroundColorSpan(parcel);
            case 3:
                return new RelativeSizeSpan(parcel);
            case 4:
                return new ScaleXSpan(parcel);
            case 5:
                return new StrikethroughSpan(parcel);
            case 6:
                return new UnderlineSpan(parcel);
            case 7:
                return new StyleSpan(parcel);
            case 8:
                return new BulletSpan(parcel);
            case 9:
                return new QuoteSpan(parcel);
            case 10:
                return new LeadingMarginSpan.Standard(parcel);
            case 11:
                return new URLSpan(parcel);
            case 12:
                return new BackgroundColorSpan(parcel);
            case 13:
                return new TypefaceSpan(parcel);
            case 14:
                return new SuperscriptSpan(parcel);
            case 15:
                return new SubscriptSpan(parcel);
            case 16:
                return new AbsoluteSizeSpan(parcel);
            case 17:
                return new TextAppearanceSpan(parcel);
            case 18:
                return new Annotation(parcel);
            case 19:
                return new SuggestionSpan(parcel);
            case 20:
            case 21:
            default:
                throw new RuntimeException("bogus span encoding " + readInt);
            case 22:
                return new EasyEditSpan(parcel);
            case 23:
                return new LocaleSpan(parcel);
        }
    }

    public static List<SpanInfo> extractSpans(Spannable spannable) {
        Object[] spans = spannable.getSpans(0, spannable.length(), Object.class);
        ArrayList arrayList = new ArrayList(spans.length);
        for (Object obj : spans) {
            if (obj instanceof CharSequence) {
                GDLog.DBGPRINTF(13, TAG + "\tspan is a CharSequence, skipping");
            } else if ((obj instanceof ParcelableSpan) || (obj instanceof CharacterStyle)) {
                arrayList.add(new SpanInfo(spannable.getSpanStart(obj), spannable.getSpanEnd(obj), spannable.getSpanFlags(obj), obj));
            }
        }
        return arrayList;
    }

    private static int getSpanTypeId(Class<? extends ParcelableSpan> cls) {
        Integer num = SPANS_ID.get(cls);
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void writeToParcel(Object obj, Parcel parcel, int i) {
        if (obj instanceof CharacterStyle) {
            obj = ((CharacterStyle) obj).getUnderlying();
        }
        if (obj instanceof ParcelableSpan) {
            ParcelableSpan parcelableSpan = (ParcelableSpan) obj;
            parcel.writeInt(getSpanTypeId(parcelableSpan.getClass()));
            parcelableSpan.writeToParcel(parcel, i);
        }
    }
}
