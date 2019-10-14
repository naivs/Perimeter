package org.naivs.perimeter.smarthome.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "thumbnail")
public class Thumbnail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String fileName;

    public Thumbnail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thumbnail thumbnail = (Thumbnail) o;
        return Objects.equals(fileName, thumbnail.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName);
    }
}
