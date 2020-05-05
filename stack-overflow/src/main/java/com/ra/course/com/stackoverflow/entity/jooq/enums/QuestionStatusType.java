package com.ra.course.com.stackoverflow.entity.jooq.enums;

import com.ra.course.com.stackoverflow.entity.jooq.Public;
import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public enum QuestionStatusType implements EnumType {

    open("open"),
    close("close"),
    on_hold("on_hold"),
    deleted("deleted");

    private final String literal;

    private QuestionStatusType(String literal) {
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
        return "question_status_type";
    }

    @Override
    public String getLiteral() {
        return literal;
    }
}
