package org.maivs.perimeter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.naivs.perimeter.Application;
import org.naivs.perimeter.library.data.AuthorEntity;
import org.naivs.perimeter.library.data.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class LibraryJpaTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void writeReadAuthorRepository() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName("Владимир Семенович Высоцкий");
        authorEntity.setDates("25.01.1938 - 25.07.1980");
        authorEntity.setDescription("советский поэт, актёр театра и кино, автор-исполнитель песен; автор " +
                "прозаических произведений и сценариев. Лауреат Государственной премии СССР. Как поэт " +
                "Высоцкий реализовал себя прежде всего в жанре авторской песни. Первые из написанных им " +
                "произведений относятся к началу 1960-х годов.");
        authorEntity.setPlace("CCCP, Москва");
        authorEntity.setPhotoUrl("https://www.film.ru/sites/default/files/people/03004.jpg");

        authorRepository.save(authorEntity);

        List<AuthorEntity> authorEntityGetted = authorRepository.findAll();
        authorRepository.delete(authorEntity);

        assert authorEntityGetted.stream().anyMatch(author -> author.getName().equals(authorEntity.getName()));
    }
}
