package org.naivs.perimeter.smarthome.rest.api;

import org.naivs.perimeter.smarthome.rest.to.DeviceTo;
import org.naivs.perimeter.smarthome.service.DeviceService;
import org.naivs.perimeter.smarthome.service.runtime.DeviceRuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceRuntimeService deviceRuntimeService;

    @GetMapping("/all")
    public List<DeviceTo> getAllDevices() {
        return deviceService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public Long createDevice(@RequestBody DeviceTo device) {
        return deviceService.createDevice(device);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
    public Long delete(@PathVariable("id") Long id) {
        return deviceService.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/run")
    public String run(@RequestParam("deviceId") Long id) {
        return deviceRuntimeService.runDevice(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/shutdown")
    public String shutdown(@RequestParam("deviceId") Long id) {
        return deviceRuntimeService.shutdownDevice(id);
    }
}
