package com.bpr.rhm_client.communication;

import com.bpr.rhm_client.IListener;
import com.bpr.rhm_client.settings.Options;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class AM2302 {

    private static final String COMMAND = "MEASURE";
    private static int SOCKET_TIMEOUT = 8000;

    private List<IListener> listeners = new ArrayList<IListener>();

    private void triggerListenerEvent(String[] response){
        for(IListener listener : listeners){
            listener.onResponseReceived(response);
        }
    }

    public AM2302(IListener listener){
        listeners.add(listener);
    }

    private static String[] printResponse(String text)
    {
        String PIPE = "|",	TEMP = "Temp=",	HUMIDITY = "Humidity=";
        int pipe_position = text.indexOf(PIPE);
        int temp_position = text.indexOf(TEMP);
        int humidity_position = text.indexOf(HUMIDITY);
        if(pipe_position == -1 || temp_position == -1 || humidity_position == -1)
            return new String[] {"WRONG SERVER RESPONSE"};

        String date = text.substring(0,pipe_position-1).trim();
        String temp = text.substring(temp_position+TEMP.length(), humidity_position).trim();
        String humidity = text.substring(humidity_position+HUMIDITY.length(), text.length()).trim();
        return new String[] {date, temp.substring(0, temp.length()-1), humidity.substring(0, humidity.length()-1)};
    }

    public void infoUpdate() {

        Thread thread = new Thread(() -> {
            try  {

                infoUpdateNewThread();

            } catch (Exception e) {
                triggerListenerEvent(new String[]{"Exception while creating new thread!"});
                e.printStackTrace();
            }
        });

        thread.start();
    }

    public  void infoUpdateNewThread() throws IOException
    {
        byte[] buffer = new byte[100];
        String[] response;

        String ipAddr = Options.getIpAddress();
        InetAddress IPAddress= InetAddress.getByName(ipAddr);
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket sendPacket = new DatagramPacket(COMMAND.getBytes(), COMMAND.getBytes().length, IPAddress, Options.getIpPort());
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);


        try {
            socket.send(sendPacket);

        } catch (Exception e) {
            triggerListenerEvent(new String[]{"Socket failed while sending request!"});

        } finally {

            try {
                socket.setSoTimeout(SOCKET_TIMEOUT);
                socket.receive(receivePacket);

            } catch(SocketTimeoutException e) {
                triggerListenerEvent(new String[]{"Socket timeout while waiting for response!"});

            } catch (SocketException e) {
                triggerListenerEvent(new String[]{"SocketException  while waiting for response!"});

            }catch (Exception e) {
                triggerListenerEvent(new String[]{"Socket failed while waiting for response! "});

            } finally {
                String serverResponse = new String(receivePacket.getData());
                response = printResponse(serverResponse);
            }
        }
        socket.close();
        triggerListenerEvent(response);
    }
}
