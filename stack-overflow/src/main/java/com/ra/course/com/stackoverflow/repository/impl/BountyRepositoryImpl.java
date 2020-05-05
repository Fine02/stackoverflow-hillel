package com.ra.course.com.stackoverflow.repository.impl;

import com.ra.course.com.stackoverflow.entity.Bounty;
import com.ra.course.com.stackoverflow.entity.jooq.tables.BountyTable;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.BountyRecord;
import com.ra.course.com.stackoverflow.repository.BountyRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

import static com.ra.course.com.stackoverflow.entity.jooq.Tables.BOUNTY;
import static com.ra.course.com.stackoverflow.entity.jooq.tables.BountyTable.*;

@Repository
@AllArgsConstructor
public class BountyRepositoryImpl implements BountyRepository {
    private transient final DSLContext dslContext;

    @Override
    public Bounty save(final Bounty bounty) {
        final BountyRecord bountyRecord = dslContext.insertInto(BOUNTY_TABLE, REPUTATION, EXPIRY, CREATOR_ID)
                                                     .values(bounty.getReputation(), Timestamp.valueOf(bounty.getExpiry()), bounty.getCreator_id())
                                                     .returning()
                                                     .fetchOne();

        return new Bounty(bountyRecord.getId(), bountyRecord.getReputation(), bountyRecord.getExpiry().toLocalDateTime(), bountyRecord.getCreatorId());
    }

    @Override
    public Optional<Bounty> findById(final long id) {
        final Bounty bounty = dslContext.selectFrom(BOUNTY)
                                         .where(BountyTable.ID.eq(id))
                                         .fetchAnyInto(Bounty.class);
        return Optional.ofNullable(bounty);
    }

    @Override
    public void deleteById(final long id) {
        dslContext.delete(BOUNTY).where(BountyTable.ID.eq(id)).execute();
    }
}