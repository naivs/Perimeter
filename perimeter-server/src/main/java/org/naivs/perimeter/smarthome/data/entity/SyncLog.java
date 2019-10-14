package org.naivs.perimeter.smarthome.data.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "sync_log")
public class SyncLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Column(name = "last_sync", nullable = false)
    private LocalDateTime lastSync;
    @Column(name = "is_success", nullable = false)
    private boolean isSuccess;
    @Column(nullable = false)
    private Long time;
    // todo: make as enum
    @Column(nullable = false)
    private String module;

    public SyncLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getLastSync() {
        return lastSync;
    }

    public void setLastSync(LocalDateTime lastSync) {
        this.lastSync = lastSync;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SyncLog syncLog = (SyncLog) o;
        return isSuccess == syncLog.isSuccess &&
                Objects.equals(id, syncLog.id) &&
                Objects.equals(lastSync, syncLog.lastSync) &&
                Objects.equals(time, syncLog.time) &&
                Objects.equals(module, syncLog.module);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastSync, isSuccess, time, module);
    }
}
