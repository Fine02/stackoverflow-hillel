package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Bounty;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.BountyRecord;
import com.ra.course.com.stackoverflow.repository.impl.BountyRepositoryImpl;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.ra.course.com.stackoverflow.entity.jooq.tables.BountyTable.BOUNTY_TABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class BountyRepositoryTest {
    private static BountyRepository bountyRepository;
    private Bounty bounty;

    @BeforeAll
    static void beforeAll() {
        var provider = new BountyProvider();
        var connection = new MockConnection(provider);
        var dslContext = DSL.using(connection, SQLDialect.H2);
        bountyRepository = new BountyRepositoryImpl(dslContext);
    }

    @BeforeEach
    void setUp() {
        bounty = new Bounty(1L, 10, LocalDateTime.MIN, 3L);
    }

    @Test
    public void shouldSaveBounty() {
        var bountyFromDb = bountyRepository.save(bounty);

        assertThat(bountyFromDb.getId()).isGreaterThan(0);
    }

    @Test
    public void shouldFindById() {
        var bountyFromDb = bountyRepository.findById(1);

        assertThat(bountyFromDb.get().getId()).isEqualTo(1);
    }

    @Test
    public void shouldReturnEmptyIfBountyNotFound() {
        assertThat(bountyRepository.findById(2L)).isEmpty();
    }

    @Test
    public void whenCommentDelete() {
        assertThatCode(() -> bountyRepository.deleteById(3L)).doesNotThrowAnyException();
    }
}

class BountyProvider implements MockDataProvider {
    @Override
    public MockResult[] execute(MockExecuteContext ctx) {
        var dslContext = DSL.using(SQLDialect.H2);
        var mock = new MockResult[1];
        var sql = ctx.sql().toUpperCase();
        var bindings = ctx.bindings();
        var result = dslContext.newResult(BOUNTY_TABLE);
        var record1 = new BountyRecord(1L, 1, Timestamp.valueOf(LocalDateTime.MIN), 102L);
        if (sql.startsWith("INSERT") || (sql.startsWith("SELECT") && bindings[0].equals(1L))) {
            result.add(record1);
            mock[0] = new MockResult(1, result);
        } else if (sql.startsWith("DELETE") ||
                   (sql.startsWith("SELECT") && bindings[0].equals(2L)) ||
                   (sql.startsWith("SELECT") && bindings[0].equals(3L))) {
            mock[0] = new MockResult(0, result);

        }
        return mock;
    }
}
