package org.naivs.perimeter.service.runtime;

import org.naivs.perimeter.data.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceRuntimeService {

    private static DeviceRuntimeService deviceRuntimeService;
    private final List<Process> processList;

    @Autowired
    private DeviceRepository deviceRepository;

    private DeviceRuntimeService() {
        processList = new ArrayList<>();
    }

    public static DeviceRuntimeService getDeviceRuntimeService() {
        if (deviceRuntimeService == null) {
            return new DeviceRuntimeService();
        } else {
            return deviceRuntimeService;
        }
    }

    // returns processId
    public String runDevice(Long deviceId) {
        try {
            Process process = new Process(deviceRepository.getById(deviceId));
            for (Process proc : processList) {
                if (proc.getId().equals(deviceId)) {
                    System.err.println(String.format("Device id:%d is already running", deviceId));
                    return String.format("Device id:%d is already running", deviceId);
                }
            }
            processList.add(process);
            process.run();
            return "Device running success!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception occurred! " + e.getMessage();
        }
    }

    public String shutdownDevice(Long deviceId) {
        try {
            Process process = null;
            for (Process proc : processList) {
                if (proc.getId().equals(deviceId)) {
                    process = proc;
                    break;
                }
            }
            if (process != null) {
                process.shutdown();
                processList.remove(process);
                return String.format("Device id:%d shutdown complete!", deviceId);
            }
        } catch (Exception e) {
            return "Exception occurred! " + e.getMessage();
        }
        return String.format("Device id:%d is not running!", deviceId);
    }
}
