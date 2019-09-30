package org.maivs.perimeter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.naivs.perimeter.Application;
import org.naivs.perimeter.library.data.*;
import org.naivs.perimeter.smarthome.rest.api.PaperController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class LibraryJpaTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Test
    public void writeReadAuthorRepository() {
        AuthorEntity authorEntity = getTestAuthor();
        authorRepository.save(authorEntity);
        List<AuthorEntity> authorEntityGetted = authorRepository.findAll();
        authorRepository.delete(authorEntity);

        assert authorEntityGetted.stream().anyMatch(author -> authorEntity.getName().equals(author.getName())
                && authorEntity.getDates().equals(author.getDates())
                && authorEntity.getDescription().equals(author.getDescription())
                && authorEntity.getPlace().equals(author.getPlace())
                && authorEntity.getPhotoUrl().equals(author.getPhotoUrl()));
    }

    @Test
    public void writeReadPaperRepository() {
        PaperEntity paperEntity = new PaperEntity();
        paperEntity.setName("Java 8. Full guide.");

        AuthorEntity authorEntity = getTestAuthor();
        authorRepository.save(authorEntity);
        paperEntity.setAuthorId(authorRepository.getByName(authorEntity.getName()).getId());

        paperEntity.setYear(2018);
        paperEntity.setLanguage("English");
        paperEntity.setGenre("Engineer guide");

        TypeEntity typeEntity = getType();
        typeRepository.save(typeEntity);
        paperEntity.setType(typeEntity.getName());

        paperEntity.setLocation("USA");
        paperEntity.setDescription("The most full guide of Java 8 programming language." +
                "This book contains much examples of code, best practice, patterns, etc.");
        paperEntity.setCoverPath("http://javabook/java8/images/cover.jpg");
        paperEntity.setFormat(".pdf");
        paperEntity.setLoadDate(Date.valueOf("2018-10-27"));

        paperRepository.save(paperEntity);
        List<PaperEntity> paperEntities = paperRepository.findAll();
        paperRepository.delete(paperEntity);
        authorRepository.delete(authorEntity);
        typeRepository.delete(typeEntity);

        assert paperEntities.stream().anyMatch(paper -> paper.getName().equals(paperEntity.getName())
                && paper.getAuthorId().equals(paperEntity.getAuthorId())
                && paper.getYear().equals(paperEntity.getYear())
                && paper.getLanguage().equals(paperEntity.getLanguage())
                && paper.getGenre().equals(paperEntity.getGenre())
                && paper.getType().equals(paperEntity.getType())
                && paper.getLocation().equals(paperEntity.getLocation())
                && paper.getDescription().equals(paperEntity.getDescription())
                && paper.getCoverPath().equals(paperEntity.getCoverPath())
                && paper.getFormat().equals(paperEntity.getFormat())
                && paper.getLoadDate().getTime() == paperEntity.getLoadDate().getTime());
    }

    @Test
    public void newTest() {
        PaperController paperController = new PaperController();
        paperController.getCover("test.jpg");
    }

    private AuthorEntity getTestAuthor() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName("Владимир Семенович Высоцкий");
        authorEntity.setDates("25.01.1938 - 25.07.1980");
        authorEntity.setDescription("советский поэт, актёр театра и кино, автор-исполнитель песен; автор " +
                "прозаических произведений и сценариев. Лауреат Государственной премии СССР. Как поэт " +
                "Высоцкий реализовал себя прежде всего в жанре авторской песни. Первые из написанных им " +
                "произведений относятся к началу 1960-х годов.");
        authorEntity.setPlace("CCCP, Москва");
        authorEntity.setPhotoUrl("https://www.film.ru/sites/default/files/people/03004.jpg");
        return authorEntity;
    }

    private TypeEntity getType() {
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setName("Book");
        typeEntity.setDescription("Just simple book with paper pages.");

        return typeEntity;
    }
}
