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

    public static String getTemperatureUnit() {
        return temperatureUnit;
    }

    public static void setTemperatureUnit(String temperatureUnit) {
        Options.temperatureUnit = temperatureUnit;
    }

    public static String getIpAddress() {
        return ipAddress;
    }

    public static void setIpAddress(String ipAddress) {
        Options.ipAddress = ipAddress;
    }

    public static int getIpPort() {
        return ipPort;
    }

    public static void setIpPort(int ipPort) {
        Options.ipPort = ipPort;
    }
}
