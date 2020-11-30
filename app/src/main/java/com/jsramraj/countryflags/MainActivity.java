package com.jsramraj.countryflags;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.jsramraj.flags.FlagDrawableProvider;
import com.jsramraj.flags.Flags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements FlagSelectionObserver {

    private FlagDrawableProvider flagDrawableProvider;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flagDrawableProvider = new Flags.Builder(this)
                .setSourceImage(getImageFromAssetsFile(this, "flags_sprite_big.png"))
                .setTileWidth(320)
                .setTileHeight(220)
                .build();

        ((ImageView) findViewById(R.id.selectedCountry)).setImageDrawable(flagDrawableProvider.forCountry("US"));

        final ArrayList<Country> countries = new ArrayList<>();
        String countryDataJson = readJSONFromAsset(this, "country_list.json");
        try {
            JSONArray countryData = new JSONArray(countryDataJson);
            for (int i = 0; i < countryData.length(); i++) {
                JSONObject countryJSONObject = countryData.getJSONObject(i);
                countries.add(new Country(countryJSONObject.getString("name"),
                        countryJSONObject.getString("dial_code"),
                        countryJSONObject.getString("code")));
            }

            //sort the countries by country name alphabetically
            Collections.sort(
                    countries, new Comparator<Country>() {
                        @Override
                        public int compare(Country c1, Country c2) {
                            return c1.getName().compareToIgnoreCase(c2.getName());
                        }
                    });
            CountryAdapter adapter = new CountryAdapter(this, countries);
            ListView countryListView = findViewById(R.id.country_list_view);
            countryListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static String readJSONFromAsset(Context aContext, String aJsonFileName) {
        try {
            InputStream is = aContext.getAssets().open(aJsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public void onFlagSelected(String countryCode) {
        ((ImageView) findViewById(R.id.selectedCountry)).setImageDrawable(flagDrawableProvider.forCountry(countryCode));
    }
}
