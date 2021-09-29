package com.bpr.rhm_client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.bpr.rhm_client.communication.AM2302;
import com.bpr.rhm_client.communication.Measurement;
import com.bpr.rhm_client.settings.Options;

public class MainActivity extends AppCompatActivity implements IListener {

    private TextView temperatureTextView;
    private TextView humidityTextView;
    private TextView dateTextView;
    private TextView tempUnitTextView;
    private View updateButton;
    private View loadingPanel;
    private AM2302 controller = new AM2302(this);
    private Measurement lastMeasurement;
    private String humidity;
    private String temperature;
    private String date;

    @Override
    protected void onStart() {
        super.onStart();
        setTemperatureUnit();
        fillMeasurementData(lastMeasurement, getResources().getString(R.string.measurement_date_val));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(v -> {
            controller.infoUpdate();
            setVisible(loadingPanel, true);
            setVisible(updateButton, false);
        });
        loadingPanel = findViewById(R.id.loadingPanel);
        temperatureTextView = findViewById(R.id.temp_value);
        humidityTextView = findViewById(R.id.humidity_value);
        dateTextView = findViewById(R.id.date_value);
        tempUnitTextView = findViewById(R.id.temp_unit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        updateSettings();
        setTemperatureUnit();
    }

    /**
     * Start SettingsActivity activity after click settings button.
     *
     * @param view current view.
     */
    public void onSettingClick(View view) {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResponseReceived(String[] data) {
        lastMeasurement = null;

        if (data.length == 3) {
            lastMeasurement = new Measurement(data);
        }
        fillMeasurementData(lastMeasurement, data[0]);
        setVisible(loadingPanel, false);
        setVisible(updateButton, true);
    }

    private void setVisible(View element, Boolean state) {
        if (state == true) {
            element.setVisibility(View.VISIBLE);
        } else {
            element.setVisibility(View.INVISIBLE);
        }
    }

    private void fillMeasurementData(Measurement measurement, String defaultDate) {
        humidity = "--";
        temperature = "--";
        date = defaultDate;

        if (measurement != null) {
            humidity = measurement.getHumidityFormatted();
            temperature = measurement.getTemperatureFormatted();
            date = measurement.getMeasurementDate();
        }

        runOnUiThread(() -> {
            humidityTextView.setText(humidity);
            temperatureTextView.setText(temperature);
            dateTextView.setText(date);
        });
    }

    private void setTemperatureUnit() {
        String temperatureUnit;
        switch (Options.getInstance().getTemperatureUnit()) {
            case "kelvin":
                temperatureUnit = getResources().getString(R.string.kelvin_unit);
                break;
            case "fahrenheit":
                temperatureUnit = getResources().getString(R.string.fahrenheit_unit);
                break;
            default:
                temperatureUnit = getResources().getString(R.string.celsius_unit);
        }
        tempUnitTextView.setText(temperatureUnit);
    }

    private void updateSettings() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String serverIP = sharedPreferences.getString("ip", "0.0.0.0");
        String serverPort = sharedPreferences.getString("port", "0");
        String tempUnit = sharedPreferences.getString("temp_unit", "n/a");

        Options.getInstance().setIpAddress(serverIP);
        Options.getInstance().setIpPort(Integer.parseInt(serverPort));
        Options.getInstance().setTemperatureUnit(tempUnit);
        controller.infoUpdate();
    }
}
