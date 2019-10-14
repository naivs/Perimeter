package org.naivs.perimeter.smarthome.rest.to;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class MultipartPhotoForm {
    // json
    private String metadata;
    private MultipartFile[] files;

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "MultipartPhotoForm{" +
                "metadata='" + metadata + '\'' +
                ", files=" + Arrays.toString(files) +
                '}';
    }
}
