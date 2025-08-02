package com.tigerfortune.other.layout.size;

import android.content.Context;

import com.tigerfortune.other.layout.size.enums.LayoutHeight;
import com.tigerfortune.other.layout.size.enums.LayoutWidth;

import java.util.Objects;


public class LayoutSize {
    public final LayoutHeight height;
    public final LayoutWidth width;

    public LayoutSize(LayoutHeight height, LayoutWidth width) {
        this.height = height;
        this.width = width;
    }

    public static LayoutSize detect(Context context) {
        int widthDp = context.getResources().getConfiguration().screenWidthDp;
        int heightDp = context.getResources().getConfiguration().screenHeightDp;

        return new LayoutSize(
                LayoutHeight.fromDp(heightDp),
                LayoutWidth.fromDp(widthDp)
        );
    }
    public int distanceTo(LayoutSize other) {
        int heightDiff = Math.abs(this.height.ordinal() - height.ordinal());
        int widthDiff = Math.abs(this.width.ordinal() - width.ordinal());
        return heightDiff + widthDiff;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LayoutSize)) return false;
        LayoutSize that = (LayoutSize) o;
        return height == that.height && width == that.width;
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, width);
    }

    @Override
    public String toString() {
        return height.name() + "Height" + width.name() + "Width";
    }
}
