package com.jsramraj.flags;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class Flags {
    private static final int FLAG_WIDTH = 32;
    private static final int FLAG_HEIGHT = 22;

    private static Context context;

    public static void init(final Context ctx) {
        context = ctx;
    }

    public static BitmapDrawable forCountry(String countryCode) {
        char[] ch = countryCode.toUpperCase().toCharArray();
        int ascii_index = 64;
        int firstLetterPosition = ch[0] - 64;
        int secondLetterPosition = ch[1] - 64;

        Bitmap flags = getImageFromAssetsFile(context, "all_flags.png");
        Bitmap flagForCountry = Bitmap.createBitmap(flags,
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
}
