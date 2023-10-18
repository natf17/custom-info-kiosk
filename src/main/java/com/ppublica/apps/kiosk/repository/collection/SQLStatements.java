package com.ppublica.apps.kiosk.repository.collection;

public class SQLStatements {
    protected static String INSERT_SIMPLE_COLL_TYPE_TABLE = "INSERT INTO simple_collection_type_impl(type) VALUES (?) RETURNING id";

    protected static String INSERT_COLL_INTERNALS_TABLE = "INSERT INTO collection_internals(locale, created_on, last_modified, "
                                                            + "page_status, simple_collection_type) VALUES (?, ?, ?, ?, ?)";
    
    protected static String INSERT_COLL_NAME_FIELD_TABLE = "INSERT INTO collection_name_field(field_type, field_name, field_value, simple_collection_type) "
                                                            + "VALUES (?, ?, ?, ?)";
    
    protected static String INSERT_TEXT_FIELD_TABLE = "INSERT INTO text_field(field_type, field_name, field_value, simple_collection_type) "
                                                                    + "VALUES (?, ?, ?, ?)";
                                                                    
    protected static String INSERT_NUMERIC_FIELD_TABLE = "INSERT INTO numeric_field(field_type, field_name, field_value, simple_collection_type) "
                                                                    + "VALUES (?, ?, ?, ?)";

    protected static String INSERT_IMAGE_FIELD_TABLE = "INSERT INTO image_field_coll(field_type, field_name, simple_collection_type) "
                                                                    + "VALUES (?, ?, ?) RETURNING id" ;
                                                            
    protected static String INSERT_IMAGE_TABLE = "INSERT INTO image_coll(location, width, height, image_field) "
                                                                    + "VALUES (?, ?, ?, ?)";    
                                                                    
    protected static String INSERT_LINKEDCOLL_FIELD_TABLE = "INSERT INTO linked_coll_field(field_type, field_name, field_value, simple_collection_type) "
                                                                    + "VALUES (?, ?, ?, ?)";
    
    protected static String FIND_SIMPLE_COLL_TYPE_TABLE = "SELECT simple_collection_type_impl.id AS COLL_ID, simple_collection_type_impl.type AS COLL_TYPE, collection_internals.locale AS CI_LOC, "
                                                + "collection_internals.created_on AS CI_CR, collection_internals.last_modified AS CI_LM, collection_internals.page_status AS CI_STATUS, "
                                                + "collection_name_field.field_type AS CNF_FT, collection_name_field.field_name AS CNF_FN, collection_name_field.field_value AS CNF_FV "
                                                + "FROM simple_collection_type_impl INNER JOIN collection_internals ON simple_collection_type_impl.id = collection_internals.simple_collection_type "
                                                + "INNER JOIN kiosk_locale ON collection_internals.locale = kiosk_locale.id "
                                                + "INNER JOIN collection_name_field ON simple_collection_type_impl.id = collection_name_field.simple_collection_type "
                                                + "WHERE kiosk_locale.abbrev = ? AND simple_collection_type_impl.type = ?";
    
    protected static String FIND_TEXT_FIELD_TABLE = "SELECT text_field.field_type AS TF_FTYPE, text_field.field_name AS TF_FNAME, text_field.field_value AS TF_FVALUE "
                                                + "FROM text_field "
                                                + "WHERE text_field.simple_collection_type = ?";

    protected static String FIND_NUMERIC_FIELD_TABLE = "SELECT numeric_field.field_type AS NF_FTYPE, numeric_field.field_name AS NF_FNAME, numeric_field.field_value AS NF_FVALUE "
                                                + "FROM numeric_field "
                                                + "WHERE numeric_field.simple_collection_type = ?";
    
    protected static String FIND_IMAGE_FIELD_TABLE = "SELECT image_field_coll.id AS IF_ID, image_field_coll.field_type AS IF_FTYPE, image_field_coll.field_name AS IF_FNAME, "
                                                + "image_coll.id AS I_ID, image_coll.location AS I_LOC, image_coll.width AS I_WIDTH, image_coll.height AS I_HEIGHT "
                                                + "FROM image_field_coll INNER JOIN image_coll ON image_field_coll.id = image_coll.image_field "
                                                + "WHERE image_field_coll.simple_collection_type = ?";

    protected static String FIND_LINKEDCOLL_FIELD_TABLE = "SELECT linked_coll_field.field_type AS LF_FTYPE, linked_coll_field.field_name AS LF_FNAME, linked_coll_field.field_value AS LF_FVALUE "
                                                + "FROM linked_coll_field "
                                                + "WHERE linked_coll_field.simple_collection_type = ?";
    
    protected static String DELETE_SIMPLE_COLL_TYPE_TABLE = "DELETE FROM simple_collection_type_impl WHERE simple_collection_type_impl.id = ?";
    /*
                                                        
   

    protected static String DELETE_FIELD_CONTAINER_TABLE = "DELETE FROM field_container WHERE field_container.id = ?";

    protected static String DELETE_PAGE_TABLE = "DELETE FROM page WHERE page.id = ?";
     */
}
