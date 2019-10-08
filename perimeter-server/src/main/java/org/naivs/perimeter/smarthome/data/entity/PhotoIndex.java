package org.naivs.perimeter.smarthome.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "photo_index", schema = "public")
public class PhotoIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoIndex that = (PhotoIndex) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

//    @Override
//    public String toString() {
//        return "PhotoIndex{" +
//                "id=" + id +
////                ", photo=" + (photo != null ? String.valueOf(photo.getId()) : null) +
//                ", name='" + name + '\'' +
//                '}';
//    }
}
