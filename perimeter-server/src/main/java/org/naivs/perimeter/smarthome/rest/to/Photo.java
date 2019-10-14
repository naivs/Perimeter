package org.naivs.perimeter.smarthome.rest.to;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Photo {
    private String name;
    private String filename;
    private String description;
    private String[] indexes;
    private LocalDateTime timestamp;

    public Photo() {
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

    public String[] getIndexes() {
        return indexes;
    }

    public void setIndexes(String[] indexes) {
        this.indexes = indexes;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", indexes=" + Arrays.toString(indexes) +
                '}';
    }
}
