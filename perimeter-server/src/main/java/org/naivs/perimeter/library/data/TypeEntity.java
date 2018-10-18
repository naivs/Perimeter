package org.naivs.perimeter.library.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "lib_type", schema = "library")
public class TypeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;
    private String description;

    public TypeEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
