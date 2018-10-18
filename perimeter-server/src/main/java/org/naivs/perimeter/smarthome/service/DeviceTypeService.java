package org.naivs.perimeter.smarthome.service;

import org.naivs.perimeter.smarthome.data.entity.DeviceTypeEntity;
import org.naivs.perimeter.smarthome.data.repository.DeviceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceTypeService {

    @Autowired
    private DeviceTypeRepository deviceTypeRepository;

    public List<DeviceTypeEntity> findAll() {
        List<DeviceTypeEntity> deviceTypeEntityList = new ArrayList<>();
        deviceTypeRepository.findAll().forEach(deviceTypeEntityList::add);
        return deviceTypeEntityList;
    }
}
