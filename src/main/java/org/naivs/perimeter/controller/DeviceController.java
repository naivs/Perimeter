package org.naivs.perimeter.controller;

import org.naivs.perimeter.data.entity.DeviceEntity;
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
    public List<DeviceEntity> getAllDevices() {
        return deviceService.findAll();
    }
//
//    @RequestMapping(method = RequestMethod.POST, value = "/create")
//    public Long createDevice(@RequestBody DeviceEntity device) {
//        return deviceService.createDevice(device);
//    }
}
