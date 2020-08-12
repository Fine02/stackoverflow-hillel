package com.ra.course.com.stackoverflow.repository;

import com.ra.course.com.stackoverflow.entity.*;
import com.ra.course.com.stackoverflow.entity.enums.QuestionClosingRemark;
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ra.course.com.stackoverflow.entity.jooq.Tables.*;
import static com.ra.course.com.stackoverflow.utils.EntityCreationUtil.*;
import static com.ra.course.com.stackoverflow.utils.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuestionRepositoryJooqTest {

    private static QuestionRepository data;
    private static BountyRepository bountyRepository;

    private Question question;
    private static final Tag tag = getTag();
    private static final Bounty bounty = getBounty();


    @BeforeAll
    static void beforeAll() {
        // Initialise data provider
        // Pass the mock connection to a jOOQ DSLContext
        var dslContext = DSL.using(new MockConnection(new QuestionMockProvider()), SQLDialect.H2);

        //mock inner repositories
        var photoRepository = mock(PhotoRepository.class);
        var commentRepository = mock(CommentRepository.class);
        bountyRepository = mock(BountyRepository.class);
        var tagRepository = mock(TagRepository.class);
        var answerRepository = mock(AnswerRepository.class);
        when(photoRepository.findByAnswerId(anyLong())).thenReturn(new ArrayList<>());
        when(commentRepository.findByAnswerId(anyLong())).thenReturn(new ArrayList<>());
        when(bountyRepository.findById(ID)).thenReturn(Optional.of(bounty));
        when(bountyRepository.findById(null)).thenReturn(Optional.empty());
        when(tagRepository.findByQuestionId(anyLong())).thenReturn(List.of(tag));
        when(answerRepository.findByQuestionId(anyLong())).thenReturn(new ArrayList<>());

        // Initialise repositories with mocked DSL
        data = new QuestionRepositoryJooqImpl(dslContext, bountyRepository, commentRepository, answerRepository,
                photoRepository, tagRepository);
    }

    @BeforeEach
    void setUp() {
        question = getQuestion();
        question.getTags().add(tag);
    }

    @Test
    public void whenSaveQuestionInDataBaseThenReturnQuestionWithId() {
        //given
        question.setId(null);
        //when
        var result = data.save(question);
        //then
        assertNotNull(result.getId());
    }

    @Test
    public void whenFindQuestionByIdAndQuestionPresentInDataBaseThenReturnQuestion() {
        //when
        var result = data.findById(ID);
        //then
        assertEquals(Optional.of(question), result);
    }

    @Test
    public void whenFindQuestionByIdAndQuestionNotPresentInDataBaseThenReturnOptionalEmpty() {
        //when
        var result = data.findById(666L);
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void whenDeleteQuestionFromDataBaseAndTryFindItThenReturnOptionalEmpty() {
        //given
        question.setId(666L);
        //when
        data.delete(question);
        var result = data.findById(666L);
        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void whenUpdateQuestionInDatabaseThenGetUpdatedQuestion() {
        //given
        question.setId(777L);
        question.setTitle("Test111");
        question.getMembersIdsWhoVotedQuestionToClose().put(ID, QuestionClosingRemark.ABUSE);
        //when
        data.update(question);
        var result = data.findById(777L).get();
        //then
        assertEquals(question, result);
        assertTrue(result.getMembersIdsWhoVotedQuestionToClose().containsKey(ID) &&
                    result.getMembersIdsWhoVotedQuestionToClose().containsValue(QuestionClosingRemark.ABUSE));
    }

    @Test
    void whenUpdateBountyInQuestion() {
        //given
        question.setBounty(bounty);
        //
        data.update(question);
        var result = data.findById(888L).get();
        //then
        assertEquals(bounty, result.getBounty());
    }

    @Test
    public void whenTryFindQuestionByMemberIdThenReturnListWithQuestion() {
        //when
        var result = data.findByMemberId(ID);
        //then
        assertEquals(List.of(question), result);
    }

    @Test
    public void whenTryFindQuestionByTagThenReturnListOfQuestion() {
        //when
        var result = data.findByTag(tag);
        //then
        assertEquals(List.of(question), result);
    }

    @Test
    public void whenTryFindQuestionByTitleThenReturnListOfQuestion() {
        //when
        var result = data.findByTitle(TITLE);
        //then
        assertEquals(List.of(question), result);
    }

    @Test
    public void whenTryFindQuestionByTitleAndTagThenReturnListOfQuestion() {
        //when
        var result = data.findByTitleAndTag(TITLE, tag);
        //then
        assertEquals(List.of(question), result);
    }

    @Test
    public void whenAddTagToQuestionThenGetQuestionByTag() {
        //given
        question.setTags(new ArrayList<>());
        //when
        data.addTagToQuestion(tag, question);
        var result = data.findByTag(tag);
        //then
        assertEquals(List.of(question), result);
        assertTrue(result.get(0).getTags().contains(tag));
    }

    @Test
    public void whenFindBySomethingButThereIsNotInDbThenReturnEmptyList() {
        //given
        var emptyList = new ArrayList<>();
        tag.setId(666L);
        //then
        assertThat(emptyList)
                .isEqualTo(data.findByMemberId(666L))
                .isEqualTo(data.findByTag(tag))
                .isEqualTo(data.findByTitleAndTag("666L", tag))
                .isEqualTo(data.findByTitle("666L"));
    }

}

//implementation MockProvider for this test
class QuestionMockProvider implements MockDataProvider {


    @Override
    public MockResult[] execute(MockExecuteContext ctx) throws SQLException {

        //DSLContext need to create org.jooq.Result and org.jooq.Record objects
        DSLContext create = DSL.using(SQLDialect.H2);
        MockResult[] mock = new MockResult[1];

        // The execute context contains SQL string(s), bind values, and other meta-data
        String sql = ctx.sql().toUpperCase();
        var value = ctx.bindings();

        //Results for mock
        var resultQuestion = create.newResult(QUESTION_TABLE);

        //Objects for mocked result
        var questionRecordID1 = new QuestionRecord(ID, TITLE, TEXT,
                0, 0, Timestamp.valueOf(LocalDateTime.MIN), Timestamp.valueOf(LocalDateTime.MIN),
                QuestionStatusType.open, QuestionClosingRemarkType.not_marked_for_closing, ID, null);
        var questionRecordID777 = new QuestionRecord(777L, "Test111", TEXT,
                0, 0, Timestamp.valueOf(LocalDateTime.MIN), Timestamp.valueOf(LocalDateTime.MIN),
                QuestionStatusType.open, QuestionClosingRemarkType.not_marked_for_closing, ID, null);


        //Stipulations for returning different results
        if (sql.startsWith("INSERT") ||
                (sql.startsWith("SELECT \"PUBLIC\".\"QUESTION\"") && value[0].equals(ID)) ||
                (sql.startsWith("SELECT \"PUBLIC\".\"QUESTION\"") && value[0].equals(TITLE))) {

            resultQuestion.add(questionRecordID1);
            mock[0] = new MockResult(1, resultQuestion);
        } else if (sql.startsWith("SELECT \"PUBLIC\".\"TAG_QUESTION\"") && value[0].equals(ID)) {
            var resultTagQuestion = create.newResult(TAG_QUESTION);
            resultTagQuestion.add(new TagQuestionRecord(ID, ID));
            mock[0] = new MockResult(1, resultTagQuestion);

        } else if (sql.startsWith("SELECT \"PUBLIC\".\"QUESTION_MEMBER_QUESTION_CLOSING_REMARK\"") && value[0].equals(777L)) {
            var result = create.newResult(QUESTION_MEMBER_QUESTION_CLOSING_REMARK);
            result.add(new QuestionMemberQuestionClosingRemarkRecord(777L, ID, QuestionClosingRemarkType.abuse, true, false));
            mock[0] = new MockResult(1, result);

        } else if (sql.startsWith("SELECT \"PUBLIC\".\"QUESTION\"") && value[0].equals(777L)) {

            resultQuestion.add(questionRecordID777);
            mock[0] = new MockResult(1, resultQuestion);
        } else if (sql.startsWith("SELECT \"PUBLIC\".\"QUESTION\"") && value[0].equals(888L)) {

            questionRecordID1.setBountyId(ID);
            resultQuestion.add(questionRecordID1);
            mock[0] = new MockResult(1, resultQuestion);
        } else {
            mock[0] = new MockResult(0, resultQuestion);
        }

        return mock;
    }
}
