package org.naivs.perimeter.smarthome.rest.api;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.naivs.perimeter.converter.AbstractConverter;
import org.naivs.perimeter.exception.PhotoServiceException;
import org.naivs.perimeter.library.service.PhotoService;
import org.naivs.perimeter.library.service.SyncService;
import org.naivs.perimeter.smarthome.data.entity.Photo;
import org.naivs.perimeter.smarthome.data.entity.PhotoIndex;
import org.naivs.perimeter.smarthome.rest.to.MultipartPhotoForm;
import org.naivs.perimeter.smarthome.rest.to.PhotoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("photo") //todo: Rest exceptions response
@CrossOrigin(origins = {"http://localhost:8090", "http://localhost:4200"})
public class PhotoController {

    private final PhotoService photoService;
    private final SyncService syncService;
    private final AbstractConverter converter;

    @Autowired
    public PhotoController(PhotoService photoService, SyncService syncService, AbstractConverter converter) {
        this.photoService = photoService;
        this.syncService = syncService;
        this.converter = converter;
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public PhotoIndex[] getAllIndexes() {
        return photoService.getIndexes().toArray(new PhotoIndex[0]);
    }

    @RequestMapping(value = "index", method = RequestMethod.POST)
    public PhotoDto[] getPhotos(@RequestBody String[] indexNames) {
        List<org.naivs.perimeter.smarthome.data.entity.Photo> photos =
                photoService.getPhotosFromDatabase(indexNames);
        return converter.convert(photos, PhotoDto.class).toArray(new PhotoDto[0]);
    }

    @RequestMapping(value = "original/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPhotoOriginal(@PathVariable("id") Long id) throws PhotoServiceException {
        try (InputStream in = new BufferedInputStream(
                new FileInputStream(photoService.getOriginalFile(id)))) {
            byte[] media = IOUtils.toByteArray(in);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(media);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body(null);
    }

    @RequestMapping(value = "thumbnail/{name}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getThumbnail(@PathVariable(name = "name") String name) throws PhotoServiceException {
        try (InputStream in = new BufferedInputStream(
                new FileInputStream(photoService.getThumbnail(name)))) {
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

    @RequestMapping(value = "sync", method = RequestMethod.GET)
    public String scan() {
        syncService.syncPhoto();
        return "OK";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addPhotos(
            @ModelAttribute("multipartPhotoForm") MultipartPhotoForm multipartPhotoForm
    ) throws IOException, PhotoServiceException {
        String metadata = multipartPhotoForm.getMetadata();
        Gson g = new Gson();
        PhotoDto[] photos = g.fromJson(metadata, PhotoDto[].class);

        Assert.isTrue(photos.length == multipartPhotoForm.getFiles().length,
                String.format("Wrong metadata count (expected:%d, actual:%d)",
                        multipartPhotoForm.getFiles().length, photos.length));

        int index = 0;
        for (MultipartFile multipartFile : multipartPhotoForm.getFiles()) {
            Photo metadataItem = converter.convert(photos[index], Photo.class);
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
