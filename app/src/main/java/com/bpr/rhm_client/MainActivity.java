package com.bpr.rhm_client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.bpr.rhm_client.communication.AM2302;
import com.bpr.rhm_client.settings.Options;

public class MainActivity extends AppCompatActivity implements IListener {

    Button updateButton;
    AM2302 controller = new AM2302(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateButton = findViewById(R.id.updateButton);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        updateButton.setOnClickListener(v -> controller.infoUpdate());

        updateSettings();
    }

    void updateSettings() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String serverIP = sharedPreferences.getString("ip", "0.0.0.0");
        String serverPort = sharedPreferences.getString("port", "0");
        String tempUnit = sharedPreferences.getString("temp_unit", "n/a");

        Options.setIpAddress(serverIP);
        Options.setIpPort(Integer.parseInt(serverPort));
        Options.setTemperatureUnit(tempUnit);
    }

    public void onSettingClick(View view) {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResponseReceived(String[] data) {
        Float humidity = Float.parseFloat(data[2]);
        Float temperature = Float.parseFloat(data[1]);
        String date = data[0];
    }
}
