package com.nauktis.core.utility;

public enum Color {
    BLACK("\u00a70"),
    WHITE("\u00a7f"),
    GREY("\u00a77"),
    AQUA("\u00a7b"),
    GREEN("\u00a7a"),
    RED("\u00a7c"),
    YELLOW("\u00a7e");

    public final String mColorCode;

    Color(String pColorCode) {
        mColorCode = pColorCode;
    }

    @Override
    public String toString() {
        return mColorCode;
    }
}
