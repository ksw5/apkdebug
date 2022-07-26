package com.journeyapps.barcodescanner;

/* loaded from: classes.dex */
public class Size implements Comparable<Size> {
    public final int height;
    public final int width;

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Size rotate() {
        return new Size(this.height, this.width);
    }

    public Size scale(int n, int d) {
        return new Size((this.width * n) / d, (this.height * n) / d);
    }

    public Size scaleFit(Size into) {
        if (this.width * into.height >= into.width * this.height) {
            int i = into.width;
            return new Size(i, (this.height * i) / this.width);
        }
        int i2 = this.width;
        int i3 = into.height;
        return new Size((i2 * i3) / this.height, i3);
    }

    public Size scaleCrop(Size into) {
        if (this.width * into.height <= into.width * this.height) {
            int i = into.width;
            return new Size(i, (this.height * i) / this.width);
        }
        int i2 = this.width;
        int i3 = into.height;
        return new Size((i2 * i3) / this.height, i3);
    }

    public boolean fitsIn(Size other) {
        return this.width <= other.width && this.height <= other.height;
    }

    @Override // java.lang.Comparable
    public int compareTo(Size other) {
        int aPixels = this.height * this.width;
        int bPixels = other.height * other.width;
        if (bPixels < aPixels) {
            return 1;
        }
        if (bPixels > aPixels) {
            return -1;
        }
        return 0;
    }

    public String toString() {
        return this.width + "x" + this.height;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Size size = (Size) o;
        if (this.width == size.width && this.height == size.height) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = this.width;
        return (result * 31) + this.height;
    }
}
