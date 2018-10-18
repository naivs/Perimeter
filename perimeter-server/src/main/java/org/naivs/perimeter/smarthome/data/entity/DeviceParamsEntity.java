package org.naivs.perimeter.smarthome.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "device_params", schema = "smart_home")
public class DeviceParamsEntity {

    public static final String URL = "URL адрес";
    public static final String PORT = "Порт";
    public static final String UPDATE_INTERVAL = "Интервал обновления данных";
    public static final String IS_RUNNING = "Запущен";
    public static final String OPERATION = "Поддерживаемая операция";
    public static final String IP_ADDRESS = "IP адрес";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "value")
    private String val;
    private String description;
    @Column(name = "device_id")
    private Long deviceId;

    public DeviceParamsEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
}
