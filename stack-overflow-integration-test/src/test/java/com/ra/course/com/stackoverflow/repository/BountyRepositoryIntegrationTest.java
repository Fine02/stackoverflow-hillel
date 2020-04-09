package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Bounty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = RepositoryTestConfiguration.class)
@ActiveProfiles("test")
@Sql({"classpath:schema.sql", "classpath:data.sql"})
public class BountyRepositoryIntegrationTest {
    @Autowired
    private BountyRepository bountyRepo;
    private Bounty bounty;
    private Bounty savedBounty;
    private final Long ID = 11L;
    private final int REPUTATION = 12;
    private final Long CREATOR_ID = 1L;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeEach
    void setUp() {
        bounty = new Bounty(ID, REPUTATION, LocalDateTime.parse("2020-03-19 13:32:37", formatter), CREATOR_ID);
        savedBounty = bountyRepo.save(bounty);
    }

    @Test
    public void shouldFindBountyById() {
        Optional<Bounty> actualBountyById = bountyRepo.findById(savedBounty.getId());

        assertThat(actualBountyById.get())
                .isEqualToIgnoringGivenFields(bounty, "id");
    }

    @Test
    public void shouldSaveBounty() {
        assertThat(savedBounty).isNotNull();
        assertThat(savedBounty.getId()).isGreaterThan(0L);
    }

    @Test
    public void shouldDeleteBountyById() {
        bountyRepo.deleteById(savedBounty.getId());

        assertThat(bountyRepo.findById(savedBounty.getId())).isEmpty();
    }
}