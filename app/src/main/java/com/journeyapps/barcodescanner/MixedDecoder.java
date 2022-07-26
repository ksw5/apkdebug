package com.journeyapps.barcodescanner;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.common.HybridBinarizer;

/* loaded from: classes.dex */
public class MixedDecoder extends Decoder {
    private boolean isInverted = true;

    public MixedDecoder(Reader reader) {
        super(reader);
    }

    @Override // com.journeyapps.barcodescanner.Decoder
    protected BinaryBitmap toBitmap(LuminanceSource source) {
        if (this.isInverted) {
            this.isInverted = false;
            return new BinaryBitmap(new HybridBinarizer(source.invert()));
        }
        this.isInverted = true;
        return new BinaryBitmap(new HybridBinarizer(source));
    }
}
