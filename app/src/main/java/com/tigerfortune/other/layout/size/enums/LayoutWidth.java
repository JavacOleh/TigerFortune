package com.tigerfortune.other.layout.size.enums;

public enum LayoutWidth {
    Small,
    Middle,
    High,
    Big,
    Large,
    XLarge;

    public static LayoutWidth fromDp(int widthDp) {
        if (widthDp <= 360) return Small;
        else if (widthDp <= 600) return Middle;
        else if (widthDp <= 960) return High;
        else if (widthDp <= 1280) return Big;
        else if (widthDp <= 1900) return Large;
        else return XLarge;
    }
}
