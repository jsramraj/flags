package com.jsramraj.flags;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;


public class Flags implements FlagDrawableProvider {

    private Context context;
    private Bitmap flagSpriteBitMap;
    private int tileWidth;
    private int tileHeight;

    private Flags(Builder builder) {
        this.context = builder.context;
        tileWidth = builder.width > 0 ? builder.width : 32;
        tileHeight = builder.height > 0 ? builder.height : 22;
        flagSpriteBitMap = builder.flagSpriteBitMap != null ?
                builder.flagSpriteBitMap :
                getImageFromAssetsFile(context, "flags_sprite.png");

        if (!(context instanceof AppCompatActivity)) {
            throw new IllegalArgumentException("Invalid context, you should pass the context of activity context here");
        }
    }

    /**
     * Crops the flag icon for the given country code from the big sprite image of all the flag icons
     * The country code should be only two character and should follow the ISO_3166-1 standard as mentioned in https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2.
     * If not, this method will raise FlagsException
     *
     * @param countryCode Two character country code
     * @return Flag icon of the given country code in Bitmap format
     * @throws FlagsException
     */
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
                firstLetterPosition * tileWidth,
                secondLetterPosition * tileHeight,
                tileWidth,
                tileHeight);
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

    /**
     * Builder class to create the Flags object
     */
    public static class Builder {

        private final Context context;
        private Bitmap flagSpriteBitMap;
        private int width;
        private int height;

        /**
         * Constructor. Initiate the builder with a valid context.
         * @param context Activity context
         */
        public Builder(@NonNull Context context) {
            this.context = context;
        }

        /**
         * Optionally, you can supply your own source sprite image if you want.
         * Visit https://github.com/jsramraj/flag-sprite-creator to know how you can create your own sprite image
         * If you supply your own source image, make sure you also set the correct tile width and tile height
         *
         * @param bitmap Source sprite image in bitmap format
         * @return Builder object
         */
        public Builder setSourceImage(Bitmap bitmap) {
            this.flagSpriteBitMap = bitmap;
            return this;
        }

        /**
         * Sets width of the individual flag icon in the source image
         * @param width Width of each flag icon
         * @return Builder object
         */
        public Builder setTileWidth(int width) {
            this.width = width;
            return this;
        }

        /**
         * Sets Height of the individual flag icon in the source image
         * @param height Height of each flag icon
         * @return Builder object
         */
        public Builder setTileHeight(int height) {
            this.height = height;
            return this;
        }

        /**
         * Creates the flags object from the given builder configuration
         * @return Created Flags object
         */
        public Flags build() {
            return new Flags(this);
        }

    }

    public static class FlagsException extends UnsupportedOperationException {
        public FlagsException(String message) {
            super("com.jsramraj.flags" + message);
        }
    }
}
