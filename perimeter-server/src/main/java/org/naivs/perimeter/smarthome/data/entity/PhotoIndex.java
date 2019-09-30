package org.naivs.perimeter.smarthome.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "photo_index", schema = "public")
public class PhotoIndex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public PhotoIndex() {
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
}
