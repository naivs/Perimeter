package org.naivs.perimeter.smarthome.data.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "sync")
public class Sync {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "catalog_path", nullable = false)
    private String catalogPath;
    @Column(name = "last_change_time", nullable = false)
    private LocalDateTime lastChangeTime;
    // todo: make as enum
    @Column(nullable = false)
    private String module;

    public Sync() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCatalogPath() {
        return catalogPath;
    }

    public void setCatalogPath(String catalogPath) {
        this.catalogPath = catalogPath;
    }

    public LocalDateTime getLastChangeTime() {
        return lastChangeTime;
    }

    public void setLastChangeTime(LocalDateTime lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
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
        Sync sync = (Sync) o;
        return Objects.equals(id, sync.id) &&
                Objects.equals(catalogPath, sync.catalogPath) &&
                Objects.equals(lastChangeTime, sync.lastChangeTime) &&
                Objects.equals(module, sync.module);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, catalogPath, lastChangeTime, module);
    }
}
