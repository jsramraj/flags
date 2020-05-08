package com.jsramraj.countryflags;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.jsramraj.flags.Flags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Flags.init(this);
//        findViewById(R.id.flagIcon).setBackground(Flags.forCountry("IN"));

        ArrayList<Country> countries = new ArrayList<>();
        String countryDataJson = readJSONFromAsset(this, "country_list.json");
        try {
            JSONArray countryData = new JSONArray(countryDataJson);
            for (int i = 0; i < countryData.length(); i++) {
                JSONObject countryJSONObject = countryData.getJSONObject(i);
                countries.add(new Country(countryJSONObject.getString("name"),
                        countryJSONObject.getString("dial_code"),
                        countryJSONObject.getString("code")));
            }
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
}
