package org.naivs.perimeter.library.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.naivs.perimeter.Application;
import org.naivs.perimeter.exception.PhotoServiceException;
import org.naivs.perimeter.smarthome.data.entity.Photo;
import org.naivs.perimeter.smarthome.data.repository.PhotoRepository;
import org.naivs.perimeter.smarthome.rest.to.PhotoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class PhotoServiceTest {

    @Autowired
    private PhotoService photoService;
    @MockBean
    private PhotoRepository photoRepository;

    @Test
    public void getPhotosFromStorage() {
        try {
            List<Photo> photoList = photoService.getPhotosMetadataFromStorage(Paths.get("."));
            assertNotNull(photoList);
            assertTrue(photoList.size() > 0);
            photoList.forEach(photo -> {
                assertNotNull(photo.getFilename());
                assertNotNull(photo.getPath());
                assertNotNull(photo.getTimestamp());
            });
        } catch (PhotoServiceException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void scanAndPersist() {
        photoService.scanAndPersist();
    }

    @Test
    public void saveToDatabase() {
        Photo photo = new Photo();
        photo.setFilename("dead_space_3_game_hero_helmet_weapon_96578_3840x2160.jpg");
        photo.setPath(Paths.get("Game/best").normalize().toString());
        photo.setDescription("This is test photo");
        photo.setTimestamp(LocalDateTime.now());

        Mockito.when(photoRepository.saveAndFlush(any())).thenAnswer(answer -> {
            photo.setId(10L);
            photo.setAdded(LocalDateTime.now());
            return photo;
        });

        Photo saved = photoService.saveToDatabase(photo);
        assertNotNull(saved);

        assertEquals(photo.getFilename(), saved.getFilename());
        assertEquals(photo.getPath(), saved.getPath());
        assertEquals(photo.getTimestamp(), saved.getTimestamp());
        assertTrue(saved.getAdded().minusSeconds(5).isBefore(saved.getAdded()));
        assertEquals(photo.getUuid(), saved.getThumbnail()
                .substring(0, saved.getThumbnail().lastIndexOf('.')));
        assertEquals(photo.getDescription(), saved.getDescription());
        assertEquals(photo.getName(), saved.getName());
        assertNotNull(saved.getIndexes());
        assertTrue(saved.getIndexes().stream().anyMatch(photoIndex -> photoIndex.getName().equals("Game")));
        assertTrue(saved.getIndexes().stream().anyMatch(photoIndex -> photoIndex.getName().equals("best")));
    }

    @Test
    public void saveToPhotobase() {
        Mockito.when(photoRepository.saveAndFlush(any())).thenAnswer(answer -> {
           Photo photo = answer.getArgument(0);
           photo.setId(10L);
           photo.setAdded(LocalDateTime.now());
           return photo;
        });

        // todo: cyrillic name doesn't work
        File file = Paths.get(this.getClass().getClassLoader()
                .getResource("but.jpeg").getFile()).toFile();

        Photo metadata = new Photo();
        metadata.setName("name from metadata");
        metadata.setFilename(file.getName());
        metadata.setDescription("This is test photo");
        metadata.setIndexes(new ArrayList<>());
        metadata.setTimestamp(LocalDateTime.now());
        try {
            Photo photo = photoService.saveToPhotobase(Files.newInputStream(file.toPath()), metadata);
            assertNotNull(photo);
        } catch (IOException | PhotoServiceException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void getPhoto() {
        Path path = Paths.get("/home/ivan/photobase/Game/best/dead_space_3_game_hero_helmet_weapon_96578_3840x2160.jpg");

        try {
//            Photo photo = photoService.getPhoto(path);
//            assertNotNull(photo);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}