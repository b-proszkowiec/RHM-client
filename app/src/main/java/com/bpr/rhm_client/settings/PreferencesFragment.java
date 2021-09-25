package com.bpr.rhm_client.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.bpr.rhm_client.R;

public class PreferencesFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
