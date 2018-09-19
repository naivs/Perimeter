package org.naivs.perimeter.emulator;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class DeviceX implements Runnable {

    protected int port;
    protected String reply;

    private final AtomicBoolean isListening = new AtomicBoolean(false);
    protected ServerSocket serverSocket;
    protected Socket clientSocket;

    public void start() {
        Thread worker = new Thread(this);
        worker.start();
    }

    public void shutdown() {
        try {
            isListening.set(false);
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Server socket close error! " + e.getMessage());
        }
    }

    @Override
    public void run() {
        isListening.set(true);

        while (isListening.get()) {
            acceptSocket();
        }
    }

    abstract public void acceptSocket ();

    protected String generateResponse(String rawResponse) {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: EmuServer/2009-09-09\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + rawResponse.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        return response + rawResponse + "\n";
    }
}
