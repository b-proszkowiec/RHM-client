package com.bpr.rhm_client.settings;

public class Options {
    private static final String LOG_TAG = Options.class.getSimpleName();
    private static final Options INSTANCE = new Options();

    // vars
    private static String temperatureUnit;
    private static String ipAddress;
    private static int ipPort;

    private Options() {
    }

    /**
     * Gets a Options using the defaults.
     *
     * @return unique instance of Options.
     */
    public static Options getInstance() {
        return INSTANCE;
    }

    /**
     * Convert temperature from celsius to given unit in settings.
     *
     * @param temperatureInCelsius temperature in celsius.
     * @return temperature in given unit.
     */
    public Float getTemperatureBasedOnUnit(Float temperatureInCelsius) {
        switch (getTemperatureUnit()) {
            case "kelvin":
                return temperatureInCelsius + (float) 273.15;
            case "fahrenheit":
                return temperatureInCelsius * (float) 1.8 + (float) 32;
            default:
                return temperatureInCelsius;
        }
    }

    /**
     * Gets temperature unit based on settings.
     *
     * @return temperature unit.
     */
    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    /**
     * Sets temperature unit to given.
     *
     * @param temperatureUnit temperature unit.
     */
    public void setTemperatureUnit(String temperatureUnit) {
        Options.temperatureUnit = temperatureUnit;
    }

    /**
     * Gets Ip address based on settings.
     *
     * @return ip or url address
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     *  Sets Ip address to given.
     *
     * @param ipAddress ip or url address
     */
    public void setIpAddress(String ipAddress) {
        Options.ipAddress = ipAddress;
    }

    /**
     * Gets port number based on settings.
     *
     * @return port number.
     */
    public int getIpPort() {
        return ipPort;
    }

    /**
     * Sets port number to given.
     *
     * @param ipPort port number.
     */
    public void setIpPort(int ipPort) {
        Options.ipPort = ipPort;
    }
}
