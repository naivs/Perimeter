package org.naivs.perimeter.smarthome.data.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "device_type", schema = "smart_home")
public class DeviceTypeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;
    private String description;

    public DeviceTypeEntity() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
