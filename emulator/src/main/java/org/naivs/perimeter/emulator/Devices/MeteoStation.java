package org.naivs.perimeter.emulator.Devices;

import org.naivs.perimeter.emulator.DeviceX;
import org.naivs.perimeter.emulator.PropertyReader;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.Map;

public class MeteoStation extends DeviceX {

    public MeteoStation(PropertyReader propertyReader) {
        Map<String, String> properties = propertyReader.readProperties(MeteoStation.class.getSimpleName());
        port = Integer.parseInt(properties.get("port"));
        reply = properties.get("response");
    }

    public void acceptSocket() {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            System.out.println(String.format("Device: %s started on port: %d..", MeteoStation.class.getSimpleName(), port));
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());

            String incomingData;
            while (!(incomingData = inputStream.readLine()).equals("")) {
                System.out.println(incomingData);
            }
            outputStream.write(generateResponse(reply).getBytes("ASCII"));
            outputStream.flush();
        } catch (SocketTimeoutException e) {
            System.err.println("Server socket timeout exception! " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Server socket exception! " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                }
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                    System.out.println(String.format("Device: %s stopped..", MeteoStation.class.getSimpleName()));
                }
            } catch (IOException e) {
                System.err.println("Sockets close error! " + e.getMessage());
            }
        }
    }
}
