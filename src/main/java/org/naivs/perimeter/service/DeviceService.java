package org.naivs.perimeter.service;

import org.naivs.perimeter.data.entity.DeviceEntity;
import org.naivs.perimeter.data.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DeviceService {

    private List<DeviceEntity> deviceList;

    @Autowired
    private DeviceRepository deviceRepository;

//    public DeviceService() {
//        deviceList = new ArrayList<>();
//        deviceList.add(new DeviceEntity(1L, 234, "first device"));
//    }

//    public Long createDevice(DeviceEntity deviceEntity) {
//        DeviceEntity deviceEntityOut = new DeviceEntity(
//                deviceList.get(0).getId() + 1, deviceEntity.getTypeId(), deviceEntity.getDescription()
//        );
//        deviceList.add(deviceEntityOut);
//        return deviceEntityOut.getId();
//    }

    public List<DeviceEntity> findAll() {
        List<DeviceEntity> deviceEntityList = new ArrayList<>();
        deviceRepository.findAll().forEach(deviceEntityList::add);
        return deviceEntityList;
    }
}
