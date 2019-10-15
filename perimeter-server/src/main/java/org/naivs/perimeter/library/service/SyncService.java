package org.naivs.perimeter.library.service;

import org.naivs.perimeter.smarthome.data.entity.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SyncService {

    private final PhotoService photoService;

    @Autowired
    public SyncService(PhotoService photoService) {
        this.photoService = photoService;
    }

    public void syncPhoto() {
        List<Photo> storagePhotos = new ArrayList<>();
        List<File> catalogs = new ArrayList<>();


    }


//    public void syncPhoto() throws Exception {
//        syncPhoto(Paths.get(photoBasePath));
//    }

//    private void syncPhoto(Path path) throws Exception {
//        List<Photo> storagePhotos = new ArrayList<>();
//        List<File> catalogs = new ArrayList<>();
//
//        File root = path.toFile();
//        if (root.exists() && root.isDirectory()) {
//            File[] files = root.listFiles(pathname -> {
//                String name = pathname.getName();
//                return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png")
//                        || name.endsWith(".JPG") || name.endsWith(".JPEG") || name.endsWith(".PNG");
//            });
//            files = files == null ? new File[0] : files;
//            for (File file : files) {
//                if (file.isFile()) {
//                    storagePhotos.add(file);
//                } else {
//                    catalogs.add(file);
//                }
//            }
//        } else {
//            throw new Exception("Base catalog not exists or it is a file");
//        }
//
//        List<Photo> dbPhotos = photoService.getPhotos(Path path);
//
//        List<Photo> diff = diffPhoto(storagePhotos, dbPhotos);
//    }
//
//    private List<Photo> diffPhoto(List<File> storagePhotos, List<Photo> database) {
//        List<Photo> db = photoService.getPhotos(
//                fs.stream().map(File::getPath).collect(Collectors.toList()));
//        fs.stream()
//    }
}
