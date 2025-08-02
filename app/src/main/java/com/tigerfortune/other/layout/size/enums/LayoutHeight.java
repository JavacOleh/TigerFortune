package com.tigerfortune.other.layout.size.enums;

public enum LayoutHeight {
    Small,
    Middle,
    High,
    Big,
    Large,
    XLarge;

    public static LayoutHeight fromDp(int heightDp) {
        if (heightDp <= 640) return Small;
        else if (heightDp <= 800) return Middle;
        else if (heightDp <= 1280) return High;
        else if (heightDp <= 1600) return Big;
        else if (heightDp <= 1900) return Large;
        else return XLarge;
    }
}
