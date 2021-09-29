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
     * Gets temperature unit based on settings.
     *
     * @return temperature unit.
     */
    public static String getTemperatureUnit() {
        return temperatureUnit;
    }

    /**
     * Sets temperature unit to given.
     *
     * @param temperatureUnit temperature unit.
     */
    public static void setTemperatureUnit(String temperatureUnit) {
        Options.temperatureUnit = temperatureUnit;
    }

    /**
     * Gets Ip address based on settings.
     *
     * @return ip or url address
     */
    public static String getIpAddress() {
        return ipAddress;
    }

    /**
     *  Sets Ip address to given.
     *
     * @param ipAddress ip or url address
     */
    public static void setIpAddress(String ipAddress) {
        Options.ipAddress = ipAddress;
    }

    /**
     * Gets port number based on settings.
     *
     * @return port number.
     */
    public static int getIpPort() {
        return ipPort;
    }

    /**
     * Sets port number to given.
     *
     * @param ipPort port number.
     */
    public static void setIpPort(int ipPort) {
        Options.ipPort = ipPort;
    }
}
