package com.ra.course.com.stackoverflow.entity.jooq;

import org.jooq.Schema;
import org.jooq.impl.CatalogImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DefaultCatalog extends CatalogImpl {

    private static final long serialVersionUID = -2092190054;
    public static final DefaultCatalog DEFAULT_CATALOG = new DefaultCatalog();
    public final Public PUBLIC = Public.PUBLIC;
    private DefaultCatalog() {
        super("");
    }

    @Override
    public final List<Schema> getSchemas() {
        List result = new ArrayList();
        result.addAll(getSchemas0());
        return result;
    }

    private final List<Schema> getSchemas0() {
        return Arrays.<Schema>asList(
            Public.PUBLIC);
    }
}
