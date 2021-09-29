package com.bpr.rhm_client.settings;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.widget.Toast;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.bpr.rhm_client.R;

public class PreferencesFragment extends PreferenceFragmentCompat {

    private EditTextPreference ipPreference;
    private EditTextPreference portPreference;
    private ListPreference areaUnitPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        ipPreference = findPreference("ip");
        portPreference = findPreference("port");
        areaUnitPreference = findPreference("temp_unit");

        ipPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            ipPreference.setText(newValue.toString().trim());
            Options.getInstance().setIpAddress(ipPreference.getText());
            return false;
        });

        portPreference.setOnBindEditTextListener(editText -> {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
        });

        portPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            portPreference.setText(newValue.toString());
            int portNumber = Integer.parseInt(newValue.toString());
            if (portNumber < 0 || portNumber > 65353) {
                Toast.makeText(getActivity(), "Port number must be in range 0-65353", Toast.LENGTH_LONG).show();
                portPreference.setText(String.valueOf(65353));
            } else {
                portPreference.setText(newValue.toString());
            }
            Options.getInstance().setIpPort(Integer.parseInt(portPreference.getText()));
            return false;
        });

        areaUnitPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            areaUnitPreference.setValue(newValue.toString());
            Options.getInstance().setTemperatureUnit(areaUnitPreference.getValue());
            return false;
        });
    }
}
