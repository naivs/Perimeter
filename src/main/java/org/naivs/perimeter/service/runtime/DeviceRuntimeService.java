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
    public void runDevice(Long deviceId) {
        Process process = new Process(deviceRepository.getById(deviceId));
        for (Process proc : processList) {
            if (proc.getId().equals(deviceId)) {
                System.err.println(String.format("Device id:%d is already running", deviceId));
                return;
            }
        }
        processList.add(process);
        process.run();
    }

    public void shutdownDevice(Long deviceId) {
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
        }
    }
}
