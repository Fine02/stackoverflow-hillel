package com.ra.course.com.stackoverflow.repository.impl;

import com.ra.course.com.stackoverflow.entity.Notification;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.NotificationRecord;
import com.ra.course.com.stackoverflow.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ra.course.com.stackoverflow.entity.jooq.Tables.NOTIFICATION_TABLE;


@Repository
@AllArgsConstructor
public class NotificationRepositoryJooqImpl implements NotificationRepository {

    private final DSLContext dslContext;

    @Override
    public List<Notification> getAllByMemberId(final Long memberId) {
        return dslContext.selectFrom(NOTIFICATION_TABLE)
                .where(NOTIFICATION_TABLE.CONTENT.startsWith(String.valueOf(memberId)))
//todo sort notifications by time of creating
//                .orderBy(NOTIFICATION_TABLE.CREATED_ON)
                .fetch()
                .stream()
                .map(this::mapperFromDb)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Notification> getById(final Long id) {
        final var record = dslContext.fetchOne(NOTIFICATION_TABLE, NOTIFICATION_TABLE.ID.eq(id));

        return Optional.ofNullable(mapperFromDb(record));
    }

    @Override
    public Notification save(final Notification notification) {
        final var record = dslContext.insertInto(NOTIFICATION_TABLE, NOTIFICATION_TABLE.CREATED_ON, NOTIFICATION_TABLE.CONTENT)
                .values(Timestamp.valueOf(notification.getCreationTime()), contentToSave(notification))
                .returning()
                .fetchOne();
        return mapperFromDb(record);
    }

    @Override
    public void updateRead(final Notification notification) {
        dslContext.update(NOTIFICATION_TABLE)
                .set(NOTIFICATION_TABLE.CONTENT, contentToSave(notification))
                .where(NOTIFICATION_TABLE.ID.eq(notification.getId()))
                .execute();
    }

    private String contentToSave(final Notification notification){
        return String.join(",",
                List.of(notification.getMember().toString(),
                Objects.nonNull(notification.getQuestion()) ? notification.getQuestion().toString()
                                                            : String.valueOf(0),
                Objects.nonNull(notification.getAnswer()) ? notification.getAnswer().toString()
                                                            : String.valueOf(0),
                Objects.nonNull(notification.getComment()) ? notification.getComment().toString()
                                                            : String.valueOf(0),
                notification.getText(),
                Boolean.toString(notification.isRead())));
    }

    private Notification mapperFromDb(final NotificationRecord record){
        if(Objects.isNull(record)){
            return null;
        }

        final String[] content = record.getContent().split(",");

        final var notification = new Notification();
            notification.setId(record.getId());
            notification.setCreationTime(record.getCreatedOn().toLocalDateTime());
            notification.setMember(Long.valueOf(content[0]));
            if (!content[1].equals(String.valueOf(0))) {
                notification.setQuestion(Long.valueOf(content[1]));
            } else if(!content[2].equals(String.valueOf(0))){
                notification.setAnswer(Long.valueOf(content[2]));
            } else {
                notification.setComment(Long.valueOf(content[3]));
            }
            notification.setText(content[4]);
            notification.setRead(Boolean.parseBoolean(content[5]));
        return notification;
    }
}
