package org.naivs.perimeter.smarthome.rest.api;

import org.apache.commons.io.IOUtils;
import org.naivs.perimeter.library.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;

@RestController
@RequestMapping("photo")
public class PhotoController {

    @Value("${photo.catalog}")
    private String libraryDir;
    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
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

    @RequestMapping(value = "catalog", method = RequestMethod.POST)
    public String addCatalog(@RequestBody String name) {
        photoService.addCatalog(Paths.get(libraryDir + "/" + name));
        return Paths.get(libraryDir + "/" + name).toString();
    }
    /*
    random photo
    concrete photo
    photos of dir (pageable)
    list dir

     */

    @RequestMapping(value = "scan", method = RequestMethod.GET)
    public String scan() {
        photoService.walk(new File(libraryDir));
        return "OK";
    }
}
