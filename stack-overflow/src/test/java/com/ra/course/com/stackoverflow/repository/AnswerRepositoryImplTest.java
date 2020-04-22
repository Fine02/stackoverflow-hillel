package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.Account;
import com.ra.course.com.stackoverflow.entity.Answer;
import com.ra.course.com.stackoverflow.entity.Member;
import com.ra.course.com.stackoverflow.entity.Question;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.AnswerRecord;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.CommentRecord;
import com.ra.course.com.stackoverflow.entity.jooq.tables.records.PhotoRecord;
import com.ra.course.com.stackoverflow.repository.impl.AnswerRepositoryImpl;
import com.ra.course.com.stackoverflow.repository.impl.CommentRepositoryImpl;
import com.ra.course.com.stackoverflow.repository.impl.PhotoRepositoryImpl;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ra.course.com.stackoverflow.entity.jooq.Tables.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnswerRepositoryImplTest {
    private long ID = 1L;
    private Account account = createNewAccount();
    private Member member = createNewMember(ID, account);
    private Question question = createNewQuestion(ID, member);
    private Answer answer = createNewAnswer(ID, member, question);

    // Initialise data provider
    private final MockDataProvider provider = new AnswerRepositoryImplTest.MockProvider();
    private final MockConnection connection = new MockConnection(provider);

    // Pass the mock connection to a jOOQ DSLContext:
    private final DSLContext dslContext = DSL.using(connection, SQLDialect.H2);

    // Initialise CommentRepositoryImpl, PhotoRepositoryImpl, AnswerRepository with mocked DSL
    private final CommentRepositoryImpl commentRepository = new CommentRepositoryImpl(dslContext);
    private final PhotoRepositoryImpl photoRepository = new PhotoRepositoryImpl(dslContext);
    private final AnswerRepository answerRepository = new AnswerRepositoryImpl(dslContext, commentRepository, photoRepository);

    @Test
    public void whenFindAnswerByIdAndAnswerPresentInDataBaseThenReturnAnswer() {
        var answer = answerRepository.findById(1L).get();

        assertEquals(answer.getId(), 1L);
    }

    @Test
    public void whenFindAnswerByIdAndAnswerNotPresentInDataBaseThenReturnOptionalEmpty() {
        Optional<Answer> answer = answerRepository.findById(666L);

        assertThat(answer.isEmpty()).isTrue();
    }

    @Test
    public void whenTryFindByIdNullThenThrowException() {
        assertThatThrownBy(() -> answerRepository.findById(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenSaveAnswerInDataBaseThenReturnAnswerWithId() {
        var savedAnswer = answerRepository.save(answer);

        assertThat(savedAnswer.getId() > 0).isTrue();
    }

    @Test
    public void whenTrySaveNullThenThrowException() {
        assertThatThrownBy(() -> answerRepository.save(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenDeleteAnswerFromDataBaseAndTryFindItThenReturnOptionalEmpty() {
        var answerForDeleting = createNewAnswer(666L, member, question);
        answerRepository.delete(answerForDeleting);

        Optional<Answer> result = answerRepository.findById(answerForDeleting.getId());

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void whenTryDeleteNullThenThrowException() {
        assertThatThrownBy(() -> answerRepository.delete(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenUpdateAnswerInDatabaseThenGetUpdatedAnswer() {
        var before = createNewAnswer(777L, member, question);
        answerRepository.save(before);

        before.setAnswerText("Test111");
        answerRepository.update(before);
        var after = answerRepository.findById(777L).get();

        assertThat(after.getAnswerText().equals("Test111")).isTrue();
    }

    @Test
    public void whenTryUpdateNullThenThrowException() {
        assertThatThrownBy(() -> answerRepository.update(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenFindAnswerByQuestionIdThenReturnList() {
        List<Answer> listResult = answerRepository.findByQuestionId(1L);

        assertThat(listResult.size() > 0).isTrue();
    }

    @Test
    public void whenTryFindByQuestionIdNullThenThrowException() {
        assertThatThrownBy(() -> answerRepository.findByQuestionId(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void whenFindAnswerByMemberIdThenReturnList() {
        List<Answer> listResult = answerRepository.findByMemberId(1L);

        assertThat(listResult.size() > 0).isTrue();
    }

    @Test
    public void whenTryFindByMemberIdNullThenThrowException() {
        assertThatThrownBy(() -> answerRepository.findByMemberId(null))
                .isInstanceOf(NullPointerException.class);
    }

    private Account createNewAccount() {
        return Account.builder()
                .password("password")
                .email("email")
                .name("name")
                .build();
    }

    private Member createNewMember(long id, Account account) {
        return Member.builder()
                .id(id)
                .account(account)
                .build();
    }

    private Question createNewQuestion(long id, Member member) {
        return Question.builder()
                .id(id)
                .description("some_question")
                .title("title")
                .authorId(member.getId())
                .build();
    }

    private Answer createNewAnswer(long id, Member member, Question question) {
        return Answer.builder()
                .id(id)
                .answerText("answer_text")
                .creationDate(LocalDateTime.now())
                .authorId(member.getId())
                .questionId(question.getId())
                .photos(new ArrayList<>())
                .comments(new ArrayList<>())
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
            var resultAnswer = create.newResult(ANSWER_TABLE.ID, ANSWER_TABLE.ANSWER_TEXT, ANSWER_TABLE.ACCEPTED,
                    ANSWER_TABLE.VOTE_COUNT, ANSWER_TABLE.FLAG_COUNT, ANSWER_TABLE.CREATION_DATE,
                    ANSWER_TABLE.AUTHOR_ID, ANSWER_TABLE.QUESTION_ID);
            var resultPhoto = create.newResult(PHOTO_TABLE.ID, PHOTO_TABLE.PHOTO_PATH, PHOTO_TABLE.CREATION_DATE,
                    PHOTO_TABLE.ANSWER_ID, PHOTO_TABLE.QUESTION_ID);
            var resultComment = create.newResult(COMMENT_TABLE.ID, COMMENT_TABLE.COMMENT_TEXT, COMMENT_TABLE.CREATION_DATE,
                    COMMENT_TABLE.VOTE_COUNT, COMMENT_TABLE.AUTHOR_ID, COMMENT_TABLE.ANSWER_ID, COMMENT_TABLE.QUESTION_ID);

            //Objects for mocked result
            var commentRecordID1 = new CommentRecord(1L, "Some comment text", Timestamp.valueOf(LocalDateTime.now()),
                    2, 1L, 1L, 1L);
            var photoRecordID1 = new PhotoRecord(1L, "/image/1/photo123.jpg", Timestamp.valueOf(LocalDateTime.now()),
                    1L, 1L);
            var answerRecordID1 = new AnswerRecord(1L, "Some answer text",
                    true, 10, 2, Timestamp.valueOf(LocalDateTime.now()),
                    1L, 1L);
            var answerRecordID777 = new AnswerRecord(2L, "Test111",
                    true, 10, 2, Timestamp.valueOf(LocalDateTime.now()),
                    1L, 1L);

            //Stipulations for returning different results
            if (sql.startsWith("INSERT") || (sql.startsWith("SELECT \"PUBLIC\".\"ANSWER\"")&& value[0].equals(1L))) {
                resultAnswer.add(answerRecordID1);

                mock[0] = new MockResult(1, resultAnswer);
            }else if (sql.startsWith("SELECT \"PUBLIC\".\"PHOTO\"") && value[0].equals(1L)) {
                resultPhoto.add(photoRecordID1);

                mock[0] = new MockResult(1, resultAnswer);
            }else if (sql.startsWith("SELECT \"PUBLIC\".\"COMMENT\"") && value[0].equals(1L)) {
                resultComment.add(commentRecordID1);

                mock[0] = new MockResult(1, resultAnswer);
            }else if (sql.startsWith("SELECT \"PUBLIC\".\"ANSWER\"") && value[0].equals(777L)) {
                resultAnswer.add(answerRecordID777);

                mock[0] = new MockResult(1, resultAnswer);
            }else {

                mock[0] = new MockResult(1, resultAnswer);
            }

            return mock;
        }
    }
}
