package org.naivs.perimeter.data.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "operation_log", schema = "audit")
public class OperationLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "operation_id")
    private long operationId;
    @Column(name = "device_id")
    private long deviceId;
    private Date date;

    public OperationLogEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOperationId() {
        return operationId;
    }

    public void setOperationId(long operationId) {
        this.operationId = operationId;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
