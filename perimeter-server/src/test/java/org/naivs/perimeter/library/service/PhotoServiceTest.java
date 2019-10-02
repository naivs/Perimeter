package org.naivs.perimeter.library.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.naivs.perimeter.Application;
import org.naivs.perimeter.smarthome.data.entity.PhotoEntity;
import org.naivs.perimeter.smarthome.data.entity.PhotoIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.Assert.*;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class PhotoServiceTest {

    @Autowired
    private PhotoService photoService;

    @Test
    public void scanAndPersist() {
        photoService.scanAndPersist();
    }

    @Test
    public void addPhoto() {
        PhotoEntity photo = new PhotoEntity();
        photo.setAdded(LocalDateTime.now());
        photo.setName("Test photo");
        photo.setPath("/fewf4g/34g34g/34g");

        PhotoIndex index = new PhotoIndex();
        index.setName("index_1");
        PhotoIndex index1 = new PhotoIndex();
        index1.setName("index_2");

        photo.getIndexes().add(index);
        photo.getIndexes().add(index1);


        photoService.addPhoto(photo);
    }
}