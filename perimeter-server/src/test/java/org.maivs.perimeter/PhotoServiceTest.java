package org.maivs.perimeter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.naivs.perimeter.Application;
import org.naivs.perimeter.library.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class PhotoServiceTest {

    @Autowired
    private PhotoService photoService;

    @Test
    public void scanAndPersist() {
        photoService.scanAndPersist("/home/ivan/photobase");
    }

    @Test
    public void saveCatalog() {
        photoService.saveCatalog();
    }
}
