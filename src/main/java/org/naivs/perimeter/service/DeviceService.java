package org.naivs.perimeter.service;

import org.naivs.perimeter.data.entity.DeviceEntity;
import org.naivs.perimeter.data.entity.DeviceParamsEntity;
import org.naivs.perimeter.data.frontEntity.DeviceFE;
import org.naivs.perimeter.data.repository.DeviceParamsRepository;
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

    @Autowired
    private DeviceParamsRepository deviceParamsRepository;

    public Long createDevice(DeviceFE deviceFE) {
        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setTypeId(deviceFE.getTypeId());
        deviceEntity.setDescription(deviceFE.getDescription());
        deviceRepository.save(deviceEntity);

        Long deviceId = deviceRepository.findByTypeIdAndDescription(deviceFE.getTypeId(), deviceFE.getDescription()).getId();
        deviceFE.getDeviceParams().forEach(param -> {
            DeviceParamsEntity deviceParamsEntity = new DeviceParamsEntity();
            deviceParamsEntity.setName(param.getName());
            deviceParamsEntity.setName(param.getName());
            deviceParamsEntity.setVal(param.getVal());
            deviceParamsEntity.setDescription(param.getDescription());
            deviceParamsEntity.setDeviceId(deviceId);
            deviceParamsRepository.save(deviceParamsEntity);
        });

        return deviceEntity.getId();
    }

    public List<DeviceFE> findAll() {
        List<DeviceFE> deviceFEList = new ArrayList<>();

        List<DeviceEntity> deviceEntityList = new ArrayList<>();
        deviceRepository.findAll().forEach(deviceEntityList::add);

        deviceEntityList.forEach(deviceEntity -> {
            DeviceFE deviceFE = new DeviceFE();
            deviceFE.setId(deviceEntity.getId());
            deviceFE.setTypeId(deviceEntity.getTypeId());
            deviceFE.setDescription(deviceEntity.getDescription());
            deviceFE.setDeviceParams(deviceParamsRepository.findAllByDeviceId(deviceEntity.getId()));
            deviceFEList.add(deviceFE);
        });

        return deviceFEList;
    }

    public Long delete(Long id) {
        deviceParamsRepository.findAllByDeviceId(id).forEach(deviceParam -> {
            deviceParamsRepository.deleteById(deviceParam.getId());
        });

        deviceRepository.deleteById(id);
        return id;
    }
}
