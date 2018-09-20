package org.naivs.perimeter.data.service;

import org.naivs.perimeter.data.entity.DeviceEntity;
import org.naivs.perimeter.data.entity.DeviceParamsEntity;
import org.naivs.perimeter.rest.to.DeviceTo;
import org.naivs.perimeter.data.repository.DeviceParamsRepository;
import org.naivs.perimeter.data.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceParamsRepository deviceParamsRepository;

    public Long createDevice(DeviceTo deviceTo) {
        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setTypeId(deviceTo.getTypeId());
        deviceEntity.setDescription(deviceTo.getDescription());
        deviceRepository.save(deviceEntity);

        Long deviceId = deviceRepository.findByTypeIdAndDescription(deviceTo.getTypeId(), deviceTo.getDescription()).getId();
        deviceTo.getDeviceParams().forEach(param -> {
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

    public List<DeviceTo> findAll() {
        List<DeviceTo> deviceToList = new ArrayList<>();

        List<DeviceEntity> deviceEntityList = new ArrayList<>();
        deviceRepository.findAll().forEach(deviceEntityList::add);

        deviceEntityList.forEach(deviceEntity -> {
            DeviceTo deviceTo = new DeviceTo();
            deviceTo.setId(deviceEntity.getId());
            deviceTo.setTypeId(deviceEntity.getTypeId());
            deviceTo.setDescription(deviceEntity.getDescription());
            deviceTo.setDeviceParams(deviceParamsRepository.findAllByDeviceId(deviceEntity.getId()));
            deviceToList.add(deviceTo);
        });

        return deviceToList;
    }

    public Long delete(Long id) {
        deviceParamsRepository.findAllByDeviceId(id).forEach(deviceParam -> {
            deviceParamsRepository.deleteById(deviceParam.getId());
        });

        deviceRepository.deleteById(id);
        return id;
    }
}
