package com.ppublica.apps.kiosk.repository;

public class SQLStatements {
    protected static String INSERT_PAGE_TABLE = "INSERT INTO page(page_type, page_name) VALUES (?, ?) RETURNING id";

    protected static String INSERT_PAGE_INTERNALS_TABLE = "INSERT INTO page_internals(locale, created_on, last_modified, "
                                                            + "page_status, page) VALUES (?, ?, ?, ?, ?)";

    protected static String INSERT_PAGE_TITLE_FIELD_TABLE = "INSERT INTO page_title_field(field_type, field_name, field_value, page) "
                                                            + "VALUES (?, ?, ?, ?)";

    protected static String INSERT_FIELD_CONTAINER_TABLE = "INSERT INTO field_container(field_container_name, has_nested_container, has_container_parent, parent) "
                                                            + "VALUES (?, ?, ?, ?) RETURNING id";

    protected static String INSERT_RICHTEXTLONGDESCR_FIELD_TABLE = "INSERT INTO rich_text_long_description_field(field_type, field_name, field_value, field_container) "
                                                                    + "VALUES (?, ?, ?, ?)";
    
    protected static String INSERT_REGTEXTLONGDESCR_FIELD_TABLE = "INSERT INTO regular_text_long_description_field(field_type, field_name, field_value, field_container) "
                                                                    + "VALUES (?, ?, ?, ?)";
                                                    
    protected static String INSERT_IMAGE_FIELD_TABLE = "INSERT INTO image_field(field_type, field_name, field_container) "
                                                                    + "VALUES (?, ?, ?) RETURNING ID";

    protected static String INSERT_IMAGE_TABLE = "INSERT INTO image(location, width, height, image_field) "
                                                                    + "VALUES (?, ?, ?, ?)";

    protected static String INSERT_BUTTON_FIELD_TABLE = "INSERT INTO button_field(field_type, field_name, field_value, field_container) "
                                                                    + "VALUES (?, ?, ?, ?)";
                    
    protected static String INSERT_URL_FIELD_TABLE = "INSERT INTO url_field(field_type, field_name, field_value, field_container) "
                                                                    + "VALUES (?, ?, ?, ?)";

    protected static String FIND_PAGE_TABLE = "SELECT page.id AS P_ID, page.page_type AS P_PTYPE, page.page_name AS P_PNAME, page_internals.locale AS PI_LOC, "
                                                + "page_internals.created_on AS PI_CR, page_internals.last_modified AS PI_LM, page_internals.page_status AS PI_PSTATUS "
                                                + "FROM page INNER JOIN page_internals ON page.id = page_internals.page "
                                                + "INNER JOIN kiosk_locale ON page_internals.locale = kiosk_locale.id "
                                                + "WHERE kiosk_locale.abbrev = ? AND page.page_type = ?";
                                                        
    protected static String FIND_PAGE_TITLE_FIELD_TABLE = "SELECT page_title_field.field_type AS PTF_FTYPE, page_title_field.field_name AS PTF_FNAME, page_title_field.field_value AS PTF_FVALUE "
                                                + "FROM page INNER JOIN page_title_field ON page.id = page_title_field.page "
                                                + "WHERE page.id = ?";

    protected static String FIND_FIELD_CONTAINER_TABLE = "SELECT field_container.id AS FC_ID, field_container.field_container_name AS FC_NAME, "
                                                + "field_container.has_nested_container AS FC_HASNESTED "
                                                + "FROM field_container "
                                                + "WHERE field_container.has_container_parent = ? AND field_container.parent = ?";
    
    protected static String FIND_RICHTEXTLONGDESCR_FIELD_TABLE = "SELECT rich_text_long_description_field.field_type AS RTLDF_FTYPE, rich_text_long_description_field.field_name AS RTLDF_FNAME, rich_text_long_description_field.field_value AS RTLDF_FVALUE "
                                                + "FROM rich_text_long_description_field "
                                                + "WHERE rich_text_long_description_field.field_container = ?";
                        
    protected static String FIND_REGTEXTLONGDESCR_FIELD_TABLE = "SELECT regular_text_long_description_field.field_type AS REGTLDF_FTYPE, regular_text_long_description_field.field_name AS REGTLDF_FNAME, regular_text_long_description_field.field_value AS REGTLDF_FVALUE "
                                                + "FROM regular_text_long_description_field "
                                                + "WHERE regular_text_long_description_field.field_container = ?";

    protected static String FIND_IMAGE_FIELD_TABLE = "SELECT image_field.id AS IF_ID, image_field.field_type AS IF_FTYPE, image_field.field_name AS IF_FNAME, "
                                                + "image.id AS I_ID, image.location AS I_LOC, image.width AS I_WIDTH, image.height AS I_HEIGHT "
                                                + "FROM image_field INNER JOIN image ON image_field.id = image.image_field "
                                                + "WHERE image_field.field_container = ?";

    protected static String FIND_BUTTON_FIELD_TABLE = "SELECT button_field.field_name AS BF_FTYPE, button_field.field_name AS BF_FNAME, button_field.field_value AS BF_FVALUE "
                                                + "FROM button_field "
                                                + "WHERE button_field.field_container = ?";

    protected static String FIND_URL_FIELD_TABLE = "SELECT url_field.field_type AS UF_FTYPE, url_field.field_name AS UF_FNAME, url_field.field_value AS UF_FVALUE "
                                                + "FROM url_field "
                                                + "WHERE url_field.field_container = ?";

    protected static String FIND_FIELD_CONTAINER_IDS_TABLE = "SELECT url_field.field_name AS UF_FNAME, url_field.field_value AS UF_FVALUE "
                                                + "FROM url_field "
                                                + "WHERE url_field.field_container = ?";

    protected static String DELETE_FIELD_CONTAINER_TABLE = "DELETE FROM field_container WHERE field_container.id = ?";

    protected static String DELETE_PAGE_TABLE = "DELETE FROM page WHERE page.id = ?";
    
}
