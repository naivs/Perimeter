package org.naivs.perimeter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.naivs.perimeter.smarthome.data.entity.Photo;
import org.naivs.perimeter.smarthome.data.entity.PhotoIndex;
import org.naivs.perimeter.smarthome.data.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PhotoJpaTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private PhotoRepository photoRepository;

    private Random random = new Random();
    private static final String ROOT = "/home/user/photobase";

    private List<Photo> photoList = new ArrayList<>();

    @Before
    public void setup() {
        for (int i = 0; i < 10; i++) {
            photoList.add(entityManager.persistAndFlush(generatePhotoEntity()));
        }
    }

    @Test
    public void findPhotoEntityByNameAndPath() {
        for (int i = 0; i < photoList.size(); i++) {
            Photo stored = photoList.get(i);
            Photo finded = photoRepository
                    .findPhotoByFilenameAndPath(stored.getName(), stored.getPath()).orElse(null);
            assertNotNull("Element " + i, finded);
            assertEquals("Element " + i + " (full eq)", stored, finded);

            assertEquals("Element " + i + " (id eq)", stored.getId(), finded.getId());
            assertEquals("Element " + i + " (name eq)", stored.getName(), finded.getName());
            assertEquals("Element " + i + " (path eq)", stored.getPath(), finded.getPath());
            assertEquals("Element " + i + " (added eq)", stored.getAdded(), finded.getAdded());
            assertEquals("Element " + i + " (timestamp eq)", stored.getTimestamp(), finded.getTimestamp());
            assertEquals("Element " + i + " (description eq)", stored.getDescription(), finded.getDescription());

            stored.getIndexes().forEach(storedIndex -> assertTrue(finded.getIndexes().stream().anyMatch(storedIndex::equals)));
//            assertEquals("Element " + i + " (indexes eq)", stored.getIndexes(), finded.getIndexes());
        }
    }

    @Test
    public void saveCascadeCheck() {
        Photo saved = photoRepository.saveAndFlush(generatePhotoEntity());
        entityManager.flush();
        Photo finded = photoRepository.findById(saved.getId()).orElse(new Photo());

        assertNotNull(finded.getIndexes());
        finded.getIndexes().forEach(findedIndex -> {
            assertNotNull(findedIndex.getId());
            assertNotNull(findedIndex.getName());
//            assertNotNull(findedIndex.getPhoto().getId());
        });
    }

    @Test
    public void delete() {

    }

    private Photo generatePhotoEntity() {
        Photo photo = new Photo();
        photo.setName("photo_" + random.nextInt(1000));
        photo.setFilename(photo.getName() + ".jpg");
        photo.setPath(ROOT + randomPath(random.nextInt(3)) + photo.getName());
//        photo.setAdded(LocalDateTime.now());
        photo.setTimestamp(LocalDateTime.of(
                random.nextInt(2019) + 1,
                random.nextInt(12) + 1,
                random.nextInt(29) + 1, 1, 1));
        photo.setDescription("test photo description " + random.nextInt(1000));

        int indexCount = random.nextInt(3) + 1;
        for (int i = 0; i < indexCount; i++) {
            photo.getIndexes().add(generatePhotoIndex());
        }

        photo.setThumbnail("thumb.jpg");
        return photo;
    }

    private PhotoIndex generatePhotoIndex() {
        PhotoIndex index = new PhotoIndex();
        index.setName("test_index_" + random.nextInt(100));
        return index;
    }

    private String randomPath(int depth) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            stringBuilder.append(random.nextInt(1000));
            stringBuilder.append("/");
        }
        return stringBuilder.toString();
    }
}
