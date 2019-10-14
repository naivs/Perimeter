package org.naivs.perimeter.smarthome.data.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(
        name = "photo",
        uniqueConstraints = @UniqueConstraint(columnNames = {"filename", "path"})
)
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String filename;
    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private LocalDateTime timestamp;
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime added;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable
    private Set<PhotoIndex> indexes = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "thumbnail_id", nullable = false)
    private Thumbnail thumbnail;

    public Photo() {
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

    public Set<PhotoIndex> getIndexes() {
        return indexes;
    }

    public void setIndexes(Set<PhotoIndex> indexes) {
        this.indexes = indexes;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return Objects.equals(name, photo.name) &&
                Objects.equals(filename, photo.filename) &&
                Objects.equals(path, photo.path) &&
                Objects.equals(timestamp, photo.timestamp) &&
                Objects.equals(description, photo.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, filename, path, timestamp, description);
    }

    public String getUuid() {
        return UUID.nameUUIDFromBytes(("Photo-" + path + filename + timestamp).getBytes()).toString();
    }

//    @Override
//    public String toString() {
//        return "Photo{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", path='" + path + '\'' +
//                ", added=" + added +
//                ", indexes=" + Arrays.toString(indexes.toArray(new PhotoIndex[0])) +
//                '}';
//    }
}
