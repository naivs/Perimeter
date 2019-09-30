package org.naivs.perimeter.smarthome.data.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "thumbnail", schema = "public")
public class Thumbnail implements Serializable {

    @Id
    private String uuid;
    private String name;
//    @Column(name = "photo_id")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "id")
    private PhotoEntity photoEntity;

    public Thumbnail() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PhotoEntity getPhotoEntity() {
        return photoEntity;
    }

    public void setPhotoEntity(PhotoEntity photoEntity) {
        this.photoEntity = photoEntity;
    }
}
