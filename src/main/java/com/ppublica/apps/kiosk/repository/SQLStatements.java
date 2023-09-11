package com.ppublica.apps.kiosk.repository;

public class SQLStatements {
    protected static String INSERT_PAGE_TABLE = "INSERT INTO page(page_type, page_name) VALUES (?, ?)";

    protected static String INSERT_PAGE_INTERNALS_TABLE = "INSERT INTO page_internals(locale, created_on, last_modified, "
                                                            + "page_status, page) VALUES (?, ?, ?, ?, ?)";

    protected static String INSERT_PAGE_TITLE_FIELD_TABLE = "INSERT INTO page_title_field(field_name, field_value, page) "
                                                            + "VALUES (?, ?, ?)";

    protected static String INSERT_FIELD_CONTAINER_TABLE = "INSERT INTO field_container(field_container_name, has_nested_container, has_container_parent, parent) "
                                                            + "VALUES (?, ?, ?, ?)";

    protected static String INSERT_RICHTEXTLONGDESCR_FIELD_TABLE = "INSERT INTO rich_text_long_description_field(field_name, field_value, page_container) "
                                                                    + "VALUES (?, ?, ?)";

    protected static String FIND_PAGE_TABLE = "SELECT page.id, page.page_type, page.page_name, page_internals.locale, "
                                                + "page_internals.created_on, page_internals.last_modified, page_internals.page_status "
                                                + "FROM page INNER JOIN page_internals ON page.id = page_internals.id "
                                                + "INNER JOIN kiosk_locale.id ON page_internals.locale = kiosk_locale.id "
                                                + "WHERE kiosk_locale.abbrev = ? AND page.page_type = ?";
                                                        
    protected static String FIND_PAGE_TITLE_FIELD_TABLE = "SELECT page_title_field.field_name, page_title_field.field_value "
                                                + "FROM page INNER JOIN page_title_field ON page.id = page_title_field.page "
                                                + "WHERE page.id = ?";

    protected static String FIND_FIELD_CONTAINER_TABLE = "SELECT field_container.id, field_container.field_container_name, "
                                                + "field_container.has_nested_container "
                                                + "FROM field_container "
                                                + "WHERE field_container.has_container_parent = ? AND field_container.parent = ?";
    
    protected static String FIND_RICHTEXTLONGDESCR_FIELD_TABLE = "SELECT rich_text_long_description_field.field_name, rich_text_long_description_field.field_value "
                                                + "FROM rich_text_long_description_field "
                                                + "WHERE rich_text_long_description_field.page_container = ?";
    
}
