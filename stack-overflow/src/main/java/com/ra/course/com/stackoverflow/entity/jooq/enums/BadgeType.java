package com.ra.course.com.stackoverflow.entity.jooq.enums;

import com.ra.course.com.stackoverflow.entity.jooq.Public;
import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public enum BadgeType implements EnumType {

    student("student"),
    teacher("teacher"),
    commentator("commentator"),
    critic("critic"),
    supporter("supporter"),
    benefactor("benefactor"),
    nice_question("nice_question"),
    good_question("good_question"),
    great_question("great_question");

    private final String literal;

    private BadgeType(String literal) {
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
        return "badge_type";
    }

    @Override
    public String getLiteral() {
        return literal;
    }
}
