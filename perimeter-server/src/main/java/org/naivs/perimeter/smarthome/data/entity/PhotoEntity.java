package org.naivs.perimeter.smarthome.data.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "photo", schema = "public")
public class PhotoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String path;
    private LocalDateTime timestamp;
    private LocalDateTime added;
    private String description;
//    @OneToMany
//    private Set<PhotoIndex> indexSet;

    public PhotoEntity() {
    }

    public String generateUuid() {
        return UUID.fromString(this.toString()).toString();
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getAdded() {
        return added;
    }

    public void setAdded(LocalDateTime added) {
        this.added = added;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public Set<PhotoIndex> getIndexSet() {
//        return indexSet;
//    }
//
//    public void setIndexSet(Set<PhotoIndex> indexSet) {
//        this.indexSet = indexSet;
//    }

    @Override
    public String toString() {
        return "PhotoEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", added=" + added +
                '}';
    }
}
