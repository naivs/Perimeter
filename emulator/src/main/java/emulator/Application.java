package emulator;

import emulator.Devices.LightSwitcher;
import emulator.Devices.MeteoStation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Application {

    private static List<DeviceX> deviceList = new ArrayList<>();

    public static void main(String[] app) {
        PropertyReader propertyReader = new PropertyReader();
        deviceList.add(new MeteoStation(propertyReader));
        deviceList.add(new LightSwitcher(propertyReader));

        boolean isRunning = true;

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (isRunning) {
                switch (consoleReader.readLine()) {
                    case "run":
                        //if (deviceX.start()) {
                        deviceList.forEach(DeviceX::start);
                        //} else {
                        //    System.err.println("emulator.DeviceX is already running!");
                        //}
                        break;
                    case "shutdown":
                        //if (deviceX.isAlive()) {
                        deviceList.forEach(DeviceX::shutdown);
                        //} else {
                        //    System.err.println("emulator.DeviceX is not running!");
                        //}
                        break;
                    case "exit":
                        isRunning = false;
                        break;
                    default:
                        System.err.println("---\n> Unknown command!"
                                + "\n(run - start device; shutdown - stop device; exit - close emulator.)");
                }
            }
        } catch (IOException e) {
            System.err.println("IO Exception in main! " + e.getMessage());
        }
    }
}
