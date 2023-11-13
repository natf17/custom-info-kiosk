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

    protected static String INSERT_BOOLEAN_FIELD_TABLE = "INSERT INTO boolean_field(field_type, field_name, field_value, simple_collection_type) "
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

    protected static String FIND_BOOLEAN_FIELD_TABLE = "SELECT boolean_field.field_type AS BF_FTYPE, boolean_field.field_name AS BF_FNAME, boolean_field.field_value AS BF_FVALUE "
                                                + "FROM boolean_field "
                                                + "WHERE boolean_field.simple_collection_type = ?";
    
    protected static String FIND_IMAGE_FIELD_TABLE = "SELECT image_field_coll.id AS IF_ID, image_field_coll.field_type AS IF_FTYPE, image_field_coll.field_name AS IF_FNAME, "
                                                + "image_coll.id AS I_ID, image_coll.location AS I_LOC, image_coll.width AS I_WIDTH, image_coll.height AS I_HEIGHT "
                                                + "FROM image_field_coll INNER JOIN image_coll ON image_field_coll.id = image_coll.image_field "
                                                + "WHERE image_field_coll.simple_collection_type = ?";

    protected static String FIND_LINKEDCOLL_FIELD_TABLE = "SELECT linked_coll_field.field_type AS LF_FTYPE, linked_coll_field.field_name AS LF_FNAME, linked_coll_field.field_value AS LF_FVALUE "
                                                + "FROM linked_coll_field "
                                                + "WHERE linked_coll_field.simple_collection_type = ?";
    
    protected static String DELETE_SIMPLE_COLL_TYPE_TABLE = "DELETE FROM simple_collection_type_impl WHERE simple_collection_type_impl.id = ?";
    
    /* DATA COLLECTION SQL BELOW */

    protected static String INSERT_DATA_COLL_TYPE_TABLE = "INSERT INTO data_collection_type_impl(type, subType) VALUES (?, ?) RETURNING id";

    protected static String INSERT_DATA_COLL_INTERNALS_TABLE = "INSERT INTO data_collection_internals(locale, created_on, last_modified, "
                                                            + "coll_status, data_collection_type) VALUES (?, ?, ?, ?, ?)";

    protected static String INSERT_LOC_FIELDS_TABLE = "INSERT INTO data_collection_localized_fields(data_collection_type) VALUES (?) RETURNING id";

    protected static String INSERT_DATA_COLL_NAME_FIELD_TABLE = "INSERT INTO data_collection_name_field(field_type, field_name, field_value, localized_fields) "
                                                            + "VALUES (?, ?, ?, ?)";

    protected static String INSERT_DATA_LOC_TEXT_FIELD_TABLE = "INSERT INTO data_localized_text_field(field_type, field_name, field_value, localized_fields) "
                                                                    + "VALUES (?, ?, ?, ?)";
                                                                    
    protected static String INSERT_DATA_LOC_NUMERIC_FIELD_TABLE = "INSERT INTO data_localized_numeric_field(field_type, field_name, field_value, localized_fields) "
                                                                    + "VALUES (?, ?, ?, ?)";

    protected static String INSERT_DATA_LOC_BOOLEAN_FIELD_TABLE = "INSERT INTO data_localized_boolean_field(field_type, field_name, field_value, localized_fields) "
                                                                    + "VALUES (?, ?, ?, ?)";

    protected static String INSERT_DATA_COLL_LOC_INTERNALS_TABLE = "INSERT INTO data_collection_localized_internals(locale, created_on, last_modified, "
                                                            + "coll_status, localized_fields) VALUES (?, ?, ?, ?, ?)";

    protected static String INSERT_DATA_ELEM_TABLE = "INSERT INTO data_linked_elem(type, data_collection_type) VALUES (?, ?) RETURNING id";

    protected static String INSERT_DATA_ELEM_TEXT_FIELD_TABLE = "INSERT INTO data_elem_text_field(field_type, field_name, field_value, data_linked_elem) "
                                                                    + "VALUES (?, ?, ?, ?)";
                                                                    
    protected static String INSERT_DATA_ELEM_NUMERIC_FIELD_TABLE = "INSERT INTO data_elem_numeric_field(field_type, field_name, field_value, data_linked_elem) "
                                                                    + "VALUES (?, ?, ?, ?)";

    protected static String INSERT_DATA_ELEM_BOOLEAN_FIELD_TABLE = "INSERT INTO data_elem_boolean_field(field_type, field_name, field_value, data_linked_elem) "
                                                                    + "VALUES (?, ?, ?, ?)";

    protected static String FIND_DATA_COLL_TYPE_TABLE = "SELECT data_collection_type_impl.id AS COLL_ID, data_collection_type_impl.type AS COLL_TYPE, data_collection_type_impl.subType AS COLL_SUBTYPE, "
                                                + "data_collection_internals.locale AS CI_LOC, data_collection_internals.created_on AS CI_CR, "
                                                + "data_collection_internals.last_modified AS CI_LM, data_collection_internals.coll_status AS CI_STATUS, "
                                                + "data_collection_localized_fields.id AS LF_ID, data_collection_localized_fields.data_collection_type AS LF_DCOLL, "
                                                + "data_collection_localized_internals.locale AS CLI_LOC, data_collection_localized_internals.created_on AS CLI_CR, "
                                                + "data_collection_localized_internals.last_modified AS CLI_LM, data_collection_localized_internals.coll_status AS CLI_STATUS, "
                                                + "data_collection_name_field.field_type AS DCNF_FT, data_collection_name_field.field_name AS DCNF_FN, data_collection_name_field.field_value AS DCNF_FV "
                                                + "FROM data_collection_type_impl INNER JOIN data_collection_internals ON data_collection_type_impl.id = data_collection_internals.data_collection_type "
                                                + "INNER JOIN data_collection_localized_fields ON data_collection_localized_fields.data_collection_type = data_collection_type_impl.id "
                                                + "INNER JOIN data_collection_localized_internals ON data_collection_localized_internals.localized_fields = data_collection_localized_fields.id "
                                                + "INNER JOIN kiosk_locale ON data_collection_localized_internals.locale = kiosk_locale.id "
                                                + "INNER JOIN data_collection_name_field ON data_collection_localized_fields.id = data_collection_name_field.localized_fields "
                                                + "WHERE kiosk_locale.abbrev = ? AND data_collection_type_impl.type = ?";

    protected static String FIND_DATA_COLL_TYPE_TABLE_WITH_SUB = FIND_DATA_COLL_TYPE_TABLE + " AND data_collection_type_impl.subType = ?";

    protected static String FIND_LOC_TEXT_FIELD_TABLE = "SELECT data_localized_text_field.field_type AS TF_FTYPE, data_localized_text_field.field_name AS TF_FNAME, data_localized_text_field.field_value AS TF_FVALUE "
                                                + "FROM data_localized_text_field "
                                                + "WHERE data_localized_text_field.localized_fields = ?";
    
    protected static String FIND_LOC_NUMERIC_FIELD_TABLE = "SELECT data_localized_numeric_field.field_type AS NF_FTYPE, data_localized_numeric_field.field_name AS NF_FNAME, data_localized_numeric_field.field_value AS NF_FVALUE "
                                                + "FROM data_localized_numeric_field "
                                                + "WHERE data_localized_numeric_field.localized_fields = ?";

    protected static String FIND_LOC_BOOLEAN_FIELD_TABLE = "SELECT data_localized_boolean_field.field_type AS BF_FTYPE, data_localized_boolean_field.field_name AS BF_FNAME, data_localized_boolean_field.field_value AS BF_FVALUE "
                                                + "FROM data_localized_boolean_field "
                                                + "WHERE data_localized_boolean_field.localized_fields = ?";

    protected static String FIND_ELEMS_TABLE = "SELECT data_linked_elem.id AS ELEM_ID, data_linked_elem.type AS ELEM_TYPE, data_linked_elem.data_collection_type AS ELEM_COLLID "
                                                + "FROM data_linked_elem "
                                                + "WHERE data_linked_elem.data_collection_type = ?";

    protected static String FIND_ELEM_TEXT_FIELD_TABLE = "SELECT data_elem_text_field.field_type AS TF_FTYPE, data_elem_text_field.field_name AS TF_FNAME, data_elem_text_field.field_value AS TF_FVALUE "
                                                + "FROM data_elem_text_field "
                                                + "WHERE data_elem_text_field.data_linked_elem = ?";
    
    protected static String FIND_ELEM_NUMERIC_FIELD_TABLE = "SELECT data_elem_numeric_field.field_type AS NF_FTYPE, data_elem_numeric_field.field_name AS NF_FNAME, data_elem_numeric_field.field_value AS NF_FVALUE "
                                                + "FROM data_elem_numeric_field "
                                                + "WHERE data_elem_numeric_field.data_linked_elem = ?";

    protected static String FIND_ELEM_BOOLEAN_FIELD_TABLE = "SELECT data_elem_boolean_field.field_type AS BF_FTYPE, data_elem_boolean_field.field_name AS BF_FNAME, data_elem_boolean_field.field_value AS BF_FVALUE "
                                                + "FROM data_elem_boolean_field "
                                                + "WHERE data_elem_boolean_field.data_linked_elem = ?";

    protected static String FIND_LOC_FIELDS_TABLE = "SELECT data_collection_localized_fields.id AS LF_ID, data_collection_localized_fields.data_collection_type AS LF_DCOLL, "
                                                + "data_collection_localized_internals.locale AS CLI_LOC, data_collection_localized_internals.created_on AS CLI_CR, "
                                                + "data_collection_localized_internals.last_modified AS CLI_LM, data_collection_localized_internals.coll_status AS CLI_STATUS, "
                                                + "data_collection_name_field.field_type AS DCNF_FT, data_collection_name_field.field_name AS DCNF_FN, data_collection_name_field.field_value AS DCNF_FV "
                                                + "FROM data_collection_localized_fields "
                                                + "INNER JOIN data_collection_localized_internals ON data_collection_localized_internals.localized_fields = data_collection_localized_fields.id "
                                                + "INNER JOIN data_collection_name_field ON data_collection_localized_fields.id = data_collection_name_field.localized_fields "
                                                + "WHERE data_collection_localized_fields.data_collection_type = ?";

    protected static String FIND_LOCAL_FIELDS_ID = "SELECT data_collection_localized_fields.id AS LF_ID, data_collection_localized_fields.data_collection_type AS LF_DCOLL "
                                                + "FROM data_collection_localized_fields INNER JOIN data_collection_localized_internals ON data_collection_localized_internals.localized_fields = data_collection_localized_fields.id "
                                                + "INNER JOIN kiosk_locale ON data_collection_localized_internals.locale = kiosk_locale.id "
                                                + "WHERE kiosk_locale.abbrev = ? AND data_collection_localized_fields.data_collection_type = ?";

    protected static String FIND_DATA_ELEMS_ID = "SELECT data_linked_elem.id AS ELEM_ID "
                                                + "FROM data_linked_elem "
                                                + "WHERE data_linked_elem.data_collection_type = ?";

    protected static String DELETE_LOC_FIELDS_TABLE = "DELETE FROM data_collection_localized_fields WHERE data_collection_localized_fields.id = ?";

    protected static String DELETE_DATA_ELEMS_TABLE = "DELETE FROM data_linked_elem WHERE data_linked_elem.id = ?";

    protected static String DELETE_COLL_TYPE_TABLE = "DELETE FROM data_collection_type_impl WHERE data_collection_type_impl.id = ?";

}