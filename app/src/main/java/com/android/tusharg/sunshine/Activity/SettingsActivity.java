package com.android.tusharg.sunshine.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.tusharg.sunshine.Fragment.SettingsFragment;
import com.android.tusharg.sunshine.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_settings, new SettingsFragment())
                .commit();
    }
}
