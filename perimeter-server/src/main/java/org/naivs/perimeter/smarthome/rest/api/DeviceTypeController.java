package org.naivs.perimeter.smarthome.rest.api;

import org.naivs.perimeter.smarthome.data.entity.DeviceTypeEntity;
import org.naivs.perimeter.smarthome.service.DeviceTypeService;
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
