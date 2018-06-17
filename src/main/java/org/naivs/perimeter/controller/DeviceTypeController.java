package org.naivs.perimeter.controller;

import org.naivs.perimeter.data.entity.DeviceTypeEntity;
import org.naivs.perimeter.service.DeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/deviceTypes")
public class DeviceTypeController {

    @Autowired
    private DeviceTypeService deviceTypeService;

    @GetMapping("/all")
    public List<DeviceTypeEntity> getAllDeviceTypes() {return deviceTypeService.findAll();}


}
