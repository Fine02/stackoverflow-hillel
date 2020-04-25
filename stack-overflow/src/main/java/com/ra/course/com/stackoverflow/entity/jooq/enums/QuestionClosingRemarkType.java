package com.ra.course.com.stackoverflow.entity.jooq.enums;

import com.ra.course.com.stackoverflow.entity.jooq.Public;
import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public enum QuestionClosingRemarkType implements EnumType {

    duplicate("duplicate"),
    offtopic("offtopic"),
    too_broad("too_broad"),
    not_constructive("not_constructive"),
    not_real_question("not_real_question"),
    primarly_opinion_based("primarly_opinion_based"),
    advertising("advertising"),
    abuse("abuse"),
    spam("spam"),
    not_marked_for_closing("not_marked_for_closing");

    private final String literal;

    private QuestionClosingRemarkType(String literal) {
        this.literal = literal;
    }

    @Override
    public Catalog getCatalog() {
        return getSchema() == null ? null : getSchema().getCatalog();
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public String getName() {
        return "question_closing_remark_type";
    }

    @Override
    public String getLiteral() {
        return literal;
    }
}
