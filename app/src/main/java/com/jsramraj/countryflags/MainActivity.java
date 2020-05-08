package com.jsramraj.countryflags;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jsramraj.flags.Flags;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Flags.init(this);
        findViewById(R.id.flagIcon).setBackground(Flags.forCountry("IN"));
    }
}
