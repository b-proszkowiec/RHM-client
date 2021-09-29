package com.bpr.rhm_client.communication;

import android.util.Log;

import com.bpr.rhm_client.IListener;
import com.bpr.rhm_client.settings.Options;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class AM2302 {
    private static final String LOG_TAG = AM2302.class.getSimpleName();
    private static final String COMMAND = "MEASURE";
    private static final int SOCKET_TIMEOUT = 8000;

    // vars
    private IListener listener;

    /**
     * Constructor of AM2302 class.
     *
     * @param listener register listener to getting data from server side.
     */
    public AM2302(IListener listener) {
        this.listener = listener;
    }

    /**
     * Trigger server to get measurement update.
     * New data will be accessible by using onResponseReceived event of IListener interface.
     *
     */
    public void infoUpdate() {
        Thread thread = new Thread(() -> {
            try {
                infoUpdateNewThread();
            } catch (Exception e) {
                Log.e(LOG_TAG, "Exception while creating new thread!");
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void triggerListenerEvent(String[] response) {
        listener.onResponseReceived(response);
    }

    private static String[] printResponse(String text) {
        String PIPE = "|", TEMP = "Temp=", HUMIDITY = "Humidity=";
        int pipe_position = text.indexOf(PIPE);
        int temp_position = text.indexOf(TEMP);
        int humidity_position = text.indexOf(HUMIDITY);
        if (pipe_position == -1 || temp_position == -1 || humidity_position == -1)
            return new String[]{"WRONG SERVER RESPONSE"};

        String date = text.substring(0, pipe_position - 1).trim();
        String temp = text.substring(temp_position + TEMP.length(), humidity_position).trim();
        String humidity = text.substring(humidity_position + HUMIDITY.length(), text.length()).trim();
        return new String[]{date, temp.substring(0, temp.length() - 1), humidity.substring(0, humidity.length() - 1)};
    }

    private void infoUpdateNewThread() {
        byte[] buffer = new byte[100];
        String ipAddress = Options.getIpAddress();
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

        try {
            InetAddress IPAddress = InetAddress.getByName(ipAddress);
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket sendPacket = new DatagramPacket(COMMAND.getBytes(), COMMAND.getBytes().length, IPAddress, Options.getIpPort());
            socket.send(sendPacket);
            socket.setSoTimeout(SOCKET_TIMEOUT);
            socket.receive(receivePacket);
            socket.close();
        } catch (UnknownHostException e) {
            Log.e(LOG_TAG, "Cannot send packet due UnknownHostException!");
        } catch (SocketTimeoutException e) {
            Log.e(LOG_TAG, "Socket timeout while waiting for response!");
        } catch (SocketException e) {
            Log.e(LOG_TAG, "SocketException while waiting for response!");
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception while waiting for response!");
        } finally {
            String serverResponse = new String(receivePacket.getData());
            triggerListenerEvent(printResponse(serverResponse));
        }
    }
}
