package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Photo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Sql({"classpath:schema.sql", "classpath:data.sql"})
public class PhotoRepositoryIntegrationTest {
    @Autowired
    private PhotoRepository photoRepo;
    private Photo photo;
    private final Long ID = 11L;
    private final String PATH = "test@path";
    private final Long ANSWER_ID = 1L;
    private final Long QUESTION_ID = 1L;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeEach
    void setUp() {
        photo = new Photo(ID, PATH, LocalDateTime.parse("2020-03-19 13:32:37", formatter), ANSWER_ID, QUESTION_ID);
    }

    @Test
    public void shouldFindPhotoById() {
        Photo expected = photoRepo.save(photo);
        Optional<Photo> actual = photoRepo.findById(expected.getId());

        assertThat(actual.get())
                .isEqualTo(expected);
    }

    @Test
    public void shouldSavePhoto() {
        Photo expected = photoRepo.save(photo);

        assertThat(photo)
                .isEqualToIgnoringGivenFields(expected, "id");
    }

    @Test
    @Rollback
    public void shouldDeletePhotoById() {
        Photo expected = photoRepo.save(photo);

        photoRepo.deleteById(expected.getId());

        assertThat(photoRepo.findById(expected.getId())).isEmpty();
    }

    @Test
    public void shouldGetListOfAnswersId() {
        Photo expected = photoRepo.save(photo);

        List<Photo> byAnswerId = photoRepo.findByAnswerId(photo.getAnswerId());

        assertThat(byAnswerId).contains(expected);
    }

    @Test
    public void shouldGetListOfPhotoByQuestionId() {
        Photo expected = photoRepo.save(photo);

        List<Photo> byQuestionId = photoRepo.findByQuestionId(expected.getQuestionId());

        assertThat(byQuestionId).contains(expected);
    }
}