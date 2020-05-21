package com.jsramraj.flags;

import android.graphics.drawable.BitmapDrawable;

public interface FlagDrawableProvider {
    BitmapDrawable forCountry(String countryCode);
}
