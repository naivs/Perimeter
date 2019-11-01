package org.naivs.perimeter.smarthome.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "photo_indexes",
            joinColumns = @JoinColumn(name = "photo_id"),
            inverseJoinColumns = @JoinColumn(name = "photo_index_id")
    )
    private List<PhotoIndex> indexes = new ArrayList<>();
    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;
    private String hash;

    public Photo() {
        PhotoIndex photoIndex = new PhotoIndex();
        photoIndex.setName("root");
        photoIndex.setDescription("Index for photos in root catalog");
        indexes.add(photoIndex);
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

    public List<PhotoIndex> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<PhotoIndex> indexes) {
        this.indexes = indexes;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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

    @JsonIgnore
    public String getUuid() {
        //todo: timestamp evolve only not file system timestamp. Also needs size or checksum
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
