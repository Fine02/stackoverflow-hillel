package com.ra.course.com.stackoverflow.repository.impl;

import com.ra.course.com.stackoverflow.entity.Bounty;
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
public class BountyRepositoryJooqImpl implements BountyRepository {
    private transient final DSLContext dslContext;

    @Override
    public Bounty save(final Bounty bounty) {
        final var bountyRecord = dslContext.insertInto(BOUNTY_TABLE, REPUTATION, EXPIRY, CREATOR_ID)
                                                     .values(bounty.getReputation(), Timestamp.valueOf(bounty.getExpiry()), bounty.getCreatorId())
                                                     .returning()
                                                     .fetchOne();

        final var savedBounty = new Bounty();
            savedBounty.setId(bountyRecord.getId());
            savedBounty.setReputation(bountyRecord.getReputation());
            savedBounty.setExpiry(bountyRecord.getExpiry().toLocalDateTime());
            savedBounty.setCreatorId(bountyRecord.getCreatorId());
        return savedBounty;
    }

    @Override
    public Optional<Bounty> findById(final Long id) {
        final var bounty = dslContext.selectFrom(BOUNTY)
                                         .where(BOUNTY_TABLE.ID.eq(id))
                                         .fetchAnyInto(Bounty.class);
        return Optional.ofNullable(bounty);
    }

    @Override
    public void deleteById(final long id) {
        dslContext.delete(BOUNTY).where(BOUNTY_TABLE.ID.eq(id)).execute();
    }
}