package com.bpr.rhm_client.communication;

import com.bpr.rhm_client.settings.Options;

import java.util.Locale;

public class Measurement {
    private String measurementDate;
    private float temperatureInCelsius;
    private float humidity;

    public Measurement(String[] measurementData) {
        this.measurementDate = measurementData[0];
        this.temperatureInCelsius = Float.parseFloat(measurementData[1]);
        this.humidity = Float.parseFloat(measurementData[2]);
    }

    public String getTemperatureFormatted() {
        return String.format(Locale.US, "%.1f", Options.getInstance().getTemperatureBasedOnUnit(temperatureInCelsius));
    }

    public String getHumidityFormatted() {
        return String.format(Locale.US, "%d %%", (int) humidity);
    }

    public String getMeasurementDate() {
        return measurementDate;
    }
}
