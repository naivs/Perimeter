package org.naivs.perimeter.smarthome.rest.api;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.naivs.perimeter.exception.PhotoServiceException;
import org.naivs.perimeter.library.service.PhotoService;
import org.naivs.perimeter.smarthome.data.entity.PhotoIndex;
import org.naivs.perimeter.smarthome.rest.to.MultipartPhotoForm;
import org.naivs.perimeter.smarthome.rest.to.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("photo") //todo: Rest exceptions response
public class PhotoController {

    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public PhotoIndex[] getAllIndexes() {
        return photoService.getIndexes().toArray(new PhotoIndex[0]);
    }

    @RequestMapping(value = "index/{indexName}", method = RequestMethod.GET)
    public org.naivs.perimeter.smarthome.data.entity.Photo[] getPhotos(@PathVariable String indexName) {
        return photoService.getPhotosFromDatabase(indexName).toArray(new org.naivs.perimeter.smarthome.data.entity.Photo[0]);
    }

    @RequestMapping(value = "original/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPhotoOriginal(@PathVariable Long id) throws PhotoServiceException {
        try (InputStream in = new BufferedInputStream(new FileInputStream(photoService.getPhoto(id)))) {
            byte[] media = IOUtils.toByteArray(in);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(media);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body(null);
    }

    @RequestMapping(value = "random", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getRandomPhoto() {
        try (InputStream in = new BufferedInputStream(new FileInputStream(
                photoService.getRandomPhoto()))) {
            byte[] image = IOUtils.toByteArray(in);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(new byte[0]);
    }

    @RequestMapping(value = "scan", method = RequestMethod.GET)
    public String scan() {
//        photoService.walk(new File(libraryDir));
        return "OK";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addPhotos(
            @ModelAttribute("multipartPhotoForm") MultipartPhotoForm multipartPhotoForm
    ) throws IOException, PhotoServiceException {
        String metadata = multipartPhotoForm.getMetadata();
        Gson g = new Gson();
        Photo[] photos = g.fromJson(metadata, Photo[].class);

        Assert.isTrue(photos.length == multipartPhotoForm.getFiles().length,
                String.format("Wrong metadata count (expected:%d, actual:%d)",
                        multipartPhotoForm.getFiles().length, photos.length));

        int index = 0;
        for (MultipartFile multipartFile : multipartPhotoForm.getFiles()) {
            Photo metadataItem = photos[index];
            metadataItem.setFilename(multipartFile.getOriginalFilename());
            if (metadataItem.getTimestamp() == null) {
                metadataItem.setTimestamp(LocalDateTime.now());
            }
            //todo: if throws PSQL Ex files will be save, but not query (needs transactional operation)
            photoService.saveToPhotobase(multipartFile.getInputStream(), metadataItem);
            index++;
        }
        return multipartPhotoForm.toString();
    }
}
