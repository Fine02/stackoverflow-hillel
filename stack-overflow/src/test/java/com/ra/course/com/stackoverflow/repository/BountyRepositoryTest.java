package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Bounty;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.BountyRecord;
import com.ra.course.com.stackoverflow.repository.impl.BountyRepositoryJooqImpl;
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
import java.util.Optional;

import static com.ra.course.com.stackoverflow.entity.jooq.tables.BountyTable.BOUNTY_TABLE;
import static com.ra.course.com.stackoverflow.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

public class BountyRepositoryTest {
    private static BountyRepository data;
    private Bounty bounty;

    @BeforeAll
    static void beforeAll() {
        // Initialise data provider
        // Pass the mock connection to a jOOQ DSLContext
        var dslContext = DSL.using(new MockConnection(new BountyMockProvider()), SQLDialect.H2);

        // Initialise BountyRepositoryJooqImpl with mocked DSL
        data = new BountyRepositoryJooqImpl(dslContext);
    }

    @BeforeEach
    void setUp() {
        bounty = new Bounty();
            bounty.setId(1L);
            bounty.setReputation(1);
            bounty.setExpiry(LocalDateTime.MIN);
            bounty.setCreatorId(102L);
    }

    @Test
    public void whenBountySaveToDBThenReturnBountyWithId() {
        //given
        bounty.setId(null);
        //when
        var result = data.save(bounty);
        //then
        assertNotNull(result.getId());
    }

    @Test
    public void whenFindBountyByIdThenReturnOptionalOfBounty() {
        //when
        var result = data.findById(ID);
        //then
        assertEquals(Optional.of(bounty), result);
    }

    @Test
    public void whenFindBountyByIdAndNoSuchBountyThenReturnOptionalEmpty() {
        //when
        var result = data.findById(555L);
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void whenBountyDelete() {
        //given
        bounty.setId(555L);
        //when
        data.deleteById(555L);
        var result = data.findById(555L);
        //then
        assertTrue(result.isEmpty());
    }
}

class BountyMockProvider implements MockDataProvider {

    @Override
    public MockResult[] execute(MockExecuteContext ctx) {

        //DSLContext need to create org.jooq.Result and org.jooq.Record objects
        var dslContext = DSL.using(SQLDialect.H2);
        var mock = new MockResult[1];

        // The execute context contains SQL string(s), bind values, and other meta-data
        var sql = ctx.sql().toUpperCase();
        var bindings = ctx.bindings();

        //Results for mock
        var result = dslContext.newResult(BOUNTY_TABLE);
        var record1 = new BountyRecord(1L, 1, Timestamp.valueOf(LocalDateTime.MIN), 102L);

        //Stipulations for returning different results
        if (sql.startsWith("INSERT") || (sql.startsWith("SELECT") && bindings[0].equals(1L))) {
            result.add(record1);
            mock[0] = new MockResult(1, result);
        } else  {
            mock[0] = new MockResult(0, result);
        }
        return mock;
    }
}
