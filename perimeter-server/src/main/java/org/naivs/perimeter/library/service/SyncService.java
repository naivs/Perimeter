package org.naivs.perimeter.library.service;

import org.naivs.perimeter.exception.PhotoServiceException;
import org.naivs.perimeter.smarthome.data.entity.Photo;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<Photo> deleted = new ArrayList<>();
        List<Photo> newPhotos = new ArrayList<>();

        try {
            recursiveScan(newPhotos, deleted, Paths.get(""));
            for (Photo p : deleted) {
                photoService.removePhotoFromPhotobase(p);
            }
            newPhotos.forEach(photoService::saveToDatabase);
        } catch (PhotoServiceException e) {
            e.printStackTrace();
        }
    }

    private void recursiveScan(
            List<Photo> newItems, List<Photo> deletedItems, Path catalog
    ) throws PhotoServiceException {
        List<Photo> storagePhotos = photoService.getPhotosMetadataFromStorage(catalog);
        List<Photo> basePhotos = photoService.getPhotosFromDatabaseByPath(catalog);

        List<Photo> newPhotos = storagePhotos.stream().filter(sp ->
                basePhotos.stream().noneMatch(bf ->
                        bf.getFilename().equals(sp.getFilename()) &&
                                bf.getPath().equals(sp.getPath()) &&
                                bf.getTimestamp().equals(sp.getTimestamp())))
                .collect(Collectors.toList());

        List<Photo> deleted = basePhotos.stream().filter(bp ->
                storagePhotos.stream().noneMatch(sp ->
                        sp.getFilename().equals(bp.getFilename()) &&
                                sp.getPath().equals(bp.getPath()) &&
                                sp.getTimestamp().equals(bp.getTimestamp())))
                .collect(Collectors.toList());

        newItems.addAll(newPhotos);
        deletedItems.addAll(deleted);

        List<File> catalogs = photoService.getOriginalCatalogs(catalog);
        for (File cat : catalogs) {
            String name = cat.getName();
            recursiveScan(newItems, deletedItems, catalog.resolve(name));
        }
    }
}
