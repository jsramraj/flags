package com.jsramraj.flags;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;

public class Flags implements FlagDrawableProvider {
    private static final int FLAG_WIDTH = 32;
    private static final int FLAG_HEIGHT = 22;

    private static Context context;
    private static Flags flags = new Flags();
    private Bitmap flagSpriteBitMap = getImageFromAssetsFile(context, "flags_sprite.png");


    public static FlagDrawableProvider with(@NonNull final Context ctx) {
        if(!(ctx instanceof AppCompatActivity)) {
            throw new IllegalArgumentException("Invalid context, you should pass the context of activity context here");
        }
        context = ctx;
        return flags;
    }

    public BitmapDrawable forCountry(String countryCode) throws FlagsException {
        if (context == null) {
            throw new FlagsException(
                    "Context is not set. Call Flags.init(getApplicationContext()) before calling this method.");
        }
        if (countryCode == null) {
            throw new NullPointerException("Country code cannot be null. Supply a valid ISO two digit country code. Refer: https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2");
        }
        if (countryCode.length() != 2) {
            throw new InvalidParameterException("Country code is not valid. Supply a valid ISO two digit country code. Refer: https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2");
        }
        char[] ch = countryCode.toUpperCase().toCharArray();
        int ascii_index = 64;
        int firstLetterPosition = ch[0] - ascii_index;
        int secondLetterPosition = ch[1] - ascii_index;

        Bitmap flagForCountry = Bitmap.createBitmap(flagSpriteBitMap,
                firstLetterPosition * FLAG_WIDTH,
                secondLetterPosition * FLAG_HEIGHT,
                FLAG_WIDTH,
                FLAG_HEIGHT);
        Log.d("TTA", String.valueOf(flags));
        return new BitmapDrawable(context.getResources(), flagForCountry);
    }

    private static Bitmap getImageFromAssetsFile(Context mContext, String fileName) {
        Bitmap image = null;
        AssetManager am = mContext.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static class FlagsException extends UnsupportedOperationException {
        public FlagsException(String message) {
            super("com.jsramraj.flags" + message);
        }
    }
}
