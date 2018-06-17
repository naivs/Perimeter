package org.naivs.perimeter.controller;

import org.naivs.perimeter.data.entity.DeviceEntity;
import org.naivs.perimeter.data.frontEntity.DeviceFE;
import org.naivs.perimeter.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/all")
    public List<DeviceFE> getAllDevices() {
        return deviceService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public Long createDevice(@RequestBody DeviceFE device) {
        return deviceService.createDevice(device);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
    public Long delete(@PathVariable("id") Long id) {
        return deviceService.delete(id);
    }
}
