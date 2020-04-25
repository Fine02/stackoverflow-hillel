package com.ra.course.com.stackoverflow.entity.jooq;

import org.jooq.Sequence;
import org.jooq.impl.SequenceImpl;

import static org.jooq.impl.SQLDataType.BIGINT;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    public static final Sequence<Long> ACCOUNT_ID_SEQ = new SequenceImpl<Long>("account_id_seq", Public.PUBLIC, BIGINT.nullable(false));
    public static final Sequence<Long> ANSWER_ID_SEQ = new SequenceImpl<Long>("answer_id_seq", Public.PUBLIC, BIGINT.nullable(false));
    public static final Sequence<Long> BOUNTY_ID_SEQ = new SequenceImpl<Long>("bounty_id_seq", Public.PUBLIC, BIGINT.nullable(false));
    public static final Sequence<Long> COMMENT_ID_SEQ = new SequenceImpl<Long>("comment_id_seq", Public.PUBLIC, BIGINT.nullable(false));
    public static final Sequence<Long> NOTIFICATION_ID_SEQ = new SequenceImpl<Long>("notification_id_seq", Public.PUBLIC, BIGINT.nullable(false));
    public static final Sequence<Long> PHOTO_ID_SEQ = new SequenceImpl<Long>("photo_id_seq", Public.PUBLIC, BIGINT.nullable(false));
    public static final Sequence<Long> QUESTION_ID_SEQ = new SequenceImpl<Long>("question_id_seq", Public.PUBLIC, BIGINT.nullable(false));
    public static final Sequence<Long> TAG_ID_SEQ = new SequenceImpl<Long>("tag_id_seq", Public.PUBLIC, BIGINT.nullable(false));
}
