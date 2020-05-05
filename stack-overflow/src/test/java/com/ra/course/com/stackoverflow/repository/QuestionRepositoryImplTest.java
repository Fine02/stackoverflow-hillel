package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.entity.jooq.enums.QuestionClosingRemarkType;
import com.ra.course.com.stackoverflow.entity.jooq.enums.QuestionStatusType;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.*;
import com.ra.course.com.stackoverflow.repository.impl.*;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.ra.course.com.stackoverflow.entity.jooq.Tables.*;
import static com.ra.course.com.stackoverflow.entity.jooq.Tables.COMMENT_TABLE;
import static com.ra.course.com.stackoverflow.entity.jooq.tables.BountyTable.BOUNTY_TABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuestionRepositoryImplTest {
    private long ID = 1L;
    private Account account = createNewAccount(ID);
    private Member member = createNewMember(account);
    private Question question = createNewQuestion(ID, member);


    // Initialise data provider
    private final MockDataProvider provider = new QuestionRepositoryImplTest.MockProvider();
    private final MockConnection connection = new MockConnection(provider);

    // Pass the mock connection to a jOOQ DSLContext:
    private final DSLContext dslContext = DSL.using(connection, SQLDialect.H2);

    // Initialise repositories with mocked DSL
    private final CommentRepositoryImpl commentRepository = new CommentRepositoryImpl(dslContext);
    private final PhotoRepositoryImpl photoRepository = new PhotoRepositoryImpl(dslContext);
    private final BountyRepository bountyRepository = new BountyRepositoryImpl(dslContext);
    private final TagRepository tagRepository = new TagRepositoryImpl(dslContext);
    private final AnswerRepository answerRepository = new AnswerRepositoryImpl(dslContext, commentRepository, photoRepository);
    private final QuestionRepository questionRepository = new QuestionRepositoryImpl(dslContext, bountyRepository, commentRepository, answerRepository,
            photoRepository, tagRepository);

    @Test
    public void whenFindQuestionByIdAndQuestionPresentInDataBaseThenReturnQuestion() {
        var question = questionRepository.findById(1L).get();

        assertEquals(question.getId(), 1L);
    }

    @Test
    public void whenFindQuestionByIdAndQuestionNotPresentInDataBaseThenReturnOptionalEmpty() {
        Optional<Question> question = questionRepository.findById(666L);

        assertThat(question.isEmpty()).isTrue();
    }

    @Test
    public void whenTryFindByIdNullThenThrowException() {
        assertThatThrownBy(() -> questionRepository.findById(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenSaveQuestionInDataBaseThenReturnQuestionWithId() {
        var savedQuestion = questionRepository.save(question);

        assertThat(savedQuestion.getId() > 0).isTrue();
    }

    @Test
    public void whenTrySaveNullThenThrowException() {
        assertThatThrownBy(() -> questionRepository.save(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenDeleteQuestionFromDataBaseAndTryFindItThenReturnOptionalEmpty() {
        var questionForDeleting = createNewQuestion(666L, member);
        questionRepository.delete(questionForDeleting);

        Optional<Question> result = questionRepository.findById(questionForDeleting.getId());

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void whenTryDeleteNullThenThrowException() {
        assertThatThrownBy(() -> questionRepository.delete(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenUpdateQuestionInDatabaseThenGetUpdatedQuestion() {
        var before = createNewQuestion(777L, member);
        questionRepository.save(before);

        before.setTitle("Test111");
        questionRepository.update(before);
        var after = questionRepository.findById(777L).get();

        assertThat(after.getTitle().equals("Test111")).isTrue();
    }

    @Test
    public void whenTryUpdateNullThenThrowException() {
        assertThatThrownBy(() -> questionRepository.update(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenTryFindQuestionByMemberIdThenReturnListOfQuestion() {
        var result = questionRepository.findByMemberId(member.getAccount().getId());

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenTryFindQuestionByTagThenReturnListOfQuestion() {
        var result = questionRepository.findByTag(new Tag(1L, "JAVA", "Some tag description"));

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenTryFindQuestionByTagNullThenThrowException() {
        assertThatThrownBy(() -> questionRepository.findByTag(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenTryFindByMemberIdNullThenThrowException() {
        assertThatThrownBy(() -> questionRepository.findByMemberId(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenTryFindQuestionByTitleThenReturnListOfQuestion() {
        var result = questionRepository.findByTitle("title");

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenTryFindQuestionByTitleNullThrowException() {
        assertThatThrownBy(() -> questionRepository.findByTitle(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenTryFindQuestionByTitleAndTagThenReturnListOfQuestion() {
        var result = questionRepository.findByTitleAndTag("title", new Tag(1L, "JAVA", "Some tag description"));

        assertTrue(result.size() > 0);
    }

    @Test
    public void whenTryFindQuestionByTitleAndTagNullThenThrowException() {
        assertThatThrownBy(() -> questionRepository.findByTitleAndTag(null, new Tag(1L, "JAVA", "Some tag description")))
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> questionRepository.findByTitleAndTag("title", null))
                .isInstanceOf(NullPointerException.class);
    }

    private Account createNewAccount(long id) {
        return Account.builder()
                .id(id)
                .password("password")
                .email("email")
                .name("name")
                .build();
    }

    private Member createNewMember(Account account) {
        return Member.builder()
                .account(account)
                .build();
    }

    private Question createNewQuestion(long id, Member member) {
        return Question.builder()
                .id(id)
                .description("some_question")
                .title("title")
                .authorId(member.getAccount().getId())
                .bounty(Optional.of(new Bounty(1L, 10, LocalDateTime.now(), 1L)))
                .build();
    }

    //implementation MockProvider for this test
    class MockProvider implements MockDataProvider {


        @Override
        public MockResult[] execute(MockExecuteContext ctx) throws SQLException {

            //DSLContext need to create org.jooq.Result and org.jooq.Record objects
            DSLContext create = DSL.using(SQLDialect.H2);
            MockResult[] mock = new MockResult[1];

            // The execute context contains SQL string(s), bind values, and other meta-data
            String sql = ctx.sql().toUpperCase();
            var value = ctx.bindings();

            //Results for mock
            var resultQuestion = create.newResult(QUESTION_TABLE.ID, QUESTION_TABLE.TITLE, QUESTION_TABLE.DESCRIPTION, QUESTION_TABLE.VIEW_COUNT,
                    QUESTION_TABLE.VOTE_COUNT, QUESTION_TABLE.CREATION_TIME, QUESTION_TABLE.UPDATE_TIME, QUESTION_TABLE.STATUS,
                    QUESTION_TABLE.CLOSING_REMARK, QUESTION_TABLE.AUTHOR_ID, QUESTION_TABLE.BOUNTY_ID);
            var resultAnswer = create.newResult(ANSWER_TABLE.ID, ANSWER_TABLE.ANSWER_TEXT, ANSWER_TABLE.ACCEPTED,
                    ANSWER_TABLE.VOTE_COUNT, ANSWER_TABLE.FLAG_COUNT, ANSWER_TABLE.CREATION_DATE,
                    ANSWER_TABLE.AUTHOR_ID, ANSWER_TABLE.QUESTION_ID);
            var resultPhoto = create.newResult(PHOTO_TABLE.ID, PHOTO_TABLE.PHOTO_PATH, PHOTO_TABLE.CREATION_DATE,
                    PHOTO_TABLE.ANSWER_ID, PHOTO_TABLE.QUESTION_ID);
            var resultComment = create.newResult(COMMENT_TABLE.ID, COMMENT_TABLE.COMMENT_TEXT, COMMENT_TABLE.CREATION_DATE,
                    COMMENT_TABLE.VOTE_COUNT, COMMENT_TABLE.AUTHOR_ID, COMMENT_TABLE.ANSWER_ID, COMMENT_TABLE.QUESTION_ID);
            var resultTag = create.newResult(TAG_TABLE.ID, TAG_TABLE.NAME, TAG_TABLE.DESCRIPTION);
            var resultBounty = create.newResult(BOUNTY_TABLE.ID, BOUNTY_TABLE.REPUTATION, BOUNTY_TABLE.EXPIRY, BOUNTY_TABLE.CREATOR_ID);


            //Objects for mocked result
            var commentRecordID1 = new CommentRecord(1L, "Some comment text", Timestamp.valueOf(LocalDateTime.now()),
                    2, 1L, 1L, 1L);
            var photoRecordID1 = new PhotoRecord(1L, "/image/1/photo123.jpg", Timestamp.valueOf(LocalDateTime.now()),
                    1L, 1L);
            var answerRecordID1 = new AnswerRecord(1L, "Some answer text",
                    true, 10, 2, Timestamp.valueOf(LocalDateTime.now()),
                    1L, 1L);
            var bountyRecordID1 = new BountyRecord(1L, 10, Timestamp.valueOf(LocalDateTime.now()), 1L);
            var tagRecordID1 = new TagRecord(1L, "JAVA", "Some tag description");
            var questionRecordID1 = new QuestionRecord(1L, "Some question title", "Some question description",
                    10, 7, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()),
                    QuestionStatusType.open, QuestionClosingRemarkType.not_marked_for_closing, 1L, 1L);
            var questionRecordID777 = new QuestionRecord(777L, "Test111", "Some question description",
                    10, 7, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()),
                    QuestionStatusType.open, QuestionClosingRemarkType.not_marked_for_closing, 1L, 1L);


            //Stipulations for returning different results
            if (sql.startsWith("INSERT") || (sql.startsWith("SELECT \"PUBLIC\".\"QUESTION\"")&& value[0].equals(1L))) {
                resultQuestion.add(questionRecordID1);

                mock[0] = new MockResult(1, resultQuestion);
            }else if (sql.startsWith("SELECT \"PUBLIC\".\"QUESTION\"") && value[0].equals("title")) {
                resultQuestion.add(questionRecordID1);

                mock[0] = new MockResult(1, resultQuestion);
            }else if (sql.startsWith("SELECT \"PUBLIC\".\"PHOTO\"") && value[0].equals(1L)) {
                resultPhoto.add(photoRecordID1);

                mock[0] = new MockResult(1, resultPhoto);
            }else if (sql.startsWith("SELECT \"PUBLIC\".\"COMMENT\"") && value[0].equals(1L)) {
                resultComment.add(commentRecordID1);

                mock[0] = new MockResult(1, resultComment);
            }else if (sql.startsWith("SELECT \"PUBLIC\".\"ANSWER\"") && value[0].equals(1L)) {
                resultAnswer.add(answerRecordID1);

                mock[0] = new MockResult(1, resultAnswer);
            }else if (sql.startsWith("SELECT \"PUBLIC\".\"BOUNTY\"") && value[0].equals(1L)) {
                resultBounty.add(bountyRecordID1);

                mock[0] = new MockResult(1, resultBounty);
            }else if (sql.startsWith("SELECT \"PUBLIC\".\"TAG\"") && value[0].equals(1L)) {
                resultTag.add(tagRecordID1);

                mock[0] = new MockResult(1, resultTag);
            }else if (sql.startsWith("SELECT \"PUBLIC\".\"TAG_QUESTION\"") && value[0].equals(1L)) {
                resultTag.add(tagRecordID1);

                mock[0] = new MockResult(1, resultTag);
            }else if (sql.startsWith("SELECT \"PUBLIC\".\"QUESTION\"") && value[0].equals(777L)) {
                resultQuestion.add(questionRecordID777);

                mock[0] = new MockResult(1, resultQuestion);
            }else {

                mock[0] = new MockResult(1, resultQuestion);
            }

            return mock;
        }
    }
}
