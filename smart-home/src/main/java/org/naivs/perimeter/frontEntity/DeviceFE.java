package org.naivs.perimeter.frontEntity;

import org.naivs.perimeter.data.entity.DeviceParamsEntity;

import java.util.List;

public class DeviceFE {

    private Long id;
    private Long typeId;
    private String description;
    private List<DeviceParamsEntity> deviceParams;

    public DeviceFE() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DeviceParamsEntity> getDeviceParams() {
        return deviceParams;
    }

    public void setDeviceParams(List<DeviceParamsEntity> deviceParams) {
        this.deviceParams = deviceParams;
    }
}
