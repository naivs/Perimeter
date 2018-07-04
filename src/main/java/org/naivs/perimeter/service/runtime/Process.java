package org.naivs.perimeter.service.runtime;

import org.naivs.perimeter.data.entity.DeviceEntity;
import org.naivs.perimeter.data.entity.DeviceParamsEntity;
import org.naivs.perimeter.service.connect.HttpConnector;

import java.util.Date;
import java.util.List;

public class Process implements Runnable {

    private Long id;
    private Date startDate;
    private int interval;
    private boolean isRunning = false;
    private DeviceEntity device;

    Process(DeviceEntity deviceEntity) {
        device = deviceEntity;
        id = deviceEntity.getId();
    }

    @Override
    public void run() {
        isRunning = true;
        startDate = new Date();
        interval = 1500;
        String deviceUrl = "localhost";
//        for (DeviceParamsEntity parameter : deviceParamsList) {
//            if (parameter.getName().equals(DeviceParamsEntity.URL)) {
//                deviceUrl = parameter.getVal();
//            }
//        }

        while (isRunning) {
            try {
                Thread.sleep(interval);
                // do something
                // http request for get response from device
                HttpConnector httpConnector = new HttpConnector();
                String response = httpConnector.get(deviceUrl, 8080, null);
                System.out.println(String.format("Response from device (%s): %s", device.getDescription(), response));
            } catch (InterruptedException e) {
                System.err.println(String.format(
                        "Thread %s was interrupted unexpected. %s", Thread.currentThread().getName(), e.getMessage()));
            }
        }
    }

    void shutdown() {
        isRunning = false;
    }

    Long getId() {
        return id;
    }

    Date getStartDate() {
        return startDate;
    }
}
