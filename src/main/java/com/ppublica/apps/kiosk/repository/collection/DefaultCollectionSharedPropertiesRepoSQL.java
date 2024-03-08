package com.ppublica.apps.kiosk.repository.collection;

public class DefaultCollectionSharedPropertiesRepoSQL {
    protected static String INSERT_COLL_SHARED_PROPS_TABLE = "INSERT INTO collection_shared_properties_impl(type, subType) VALUES (?, ?) RETURNING id";

    protected static String INSERT_COLL_INTERNALS_TABLE = "INSERT INTO collection_shared_internals(created_on, last_modified, "
                                                            + "coll_status, collection_shared_properties) VALUES (?, ?, ?, ?)";
    
    protected static String INSERT_COLL_NAME_FIELD_TABLE = "INSERT INTO collection_shared_name_field(field_type, field_name, field_value, collection_shared_properties) "
                                                            + "VALUES (?, ?, ?, ?)";

    protected static String INSERT_TEXT_FIELD_TABLE = "INSERT INTO collection_shared_text_field(field_type, field_name, field_value, collection_shared_properties) "
                                                                    + "VALUES (?, ?, ?, ?)";
                                                                    
    protected static String INSERT_NUMERIC_FIELD_TABLE = "INSERT INTO collection_shared_numeric_field(field_type, field_name, field_value, collection_shared_properties) "
                                                                    + "VALUES (?, ?, ?, ?)";

    protected static String INSERT_BOOLEAN_FIELD_TABLE = "INSERT INTO collection_shared_boolean_field(field_type, field_name, field_value, collection_shared_properties) "
                                                                    + "VALUES (?, ?, ?, ?)";

    protected static String INSERT_IMAGE_FIELD_TABLE = "INSERT INTO collection_shared_image_field(field_type, field_name, collection_shared_properties) "
                                                                    + "VALUES (?, ?, ?) RETURNING id" ;                  
                                                            
    protected static String INSERT_IMAGE_TABLE = "INSERT INTO collection_shared_image(location, width, height, image_field) "
                                                                    + "VALUES (?, ?, ?, ?)";   
                                                                    
    protected static String INSERT_LINKEDCOLL_FIELD_TABLE = "INSERT INTO collection_shared_linked_coll_field(field_type, field_name, field_value, collection_shared_properties) "
                                                                    + "VALUES (?, ?, ?, ?)";

    protected static String INSERT_COLLRELL_FIELD_TABLE = "INSERT INTO collection_relationships(coll_1, coll_2, rel_type) "
                                                                    + "VALUES (?, ?, ?)";

    protected static String FIND_COLL_TYPE_TABLE_BY_TYPE = "SELECT collection_shared_properties_impl.id AS COLL_ID, collection_shared_properties_impl.type AS COLL_TYPE, collection_shared_properties_impl.subType AS COLL_SUBTYPE, "
                                                + "collection_shared_internals.created_on AS CI_CR, collection_shared_internals.last_modified AS CI_LM, collection_shared_internals.coll_status AS CI_STATUS, "
                                                + "collection_shared_name_field.field_type AS CNF_FT, collection_shared_name_field.field_name AS CNF_FN, collection_shared_name_field.field_value AS CNF_FV "
                                                + "FROM collection_shared_properties_impl INNER JOIN collection_shared_internals ON collection_shared_properties_impl.id = collection_shared_internals.collection_shared_properties "
                                                + "INNER JOIN collection_shared_name_field ON collection_shared_properties_impl.id = collection_shared_name_field.collection_shared_properties "
                                                + "WHERE collection_shared_properties_impl.type = ?";
    
    protected static String FIND_COLL_TYPE_TABLE_BY_TYPE_AND_SUBTYPE = "SELECT collection_shared_properties_impl.id AS COLL_ID, collection_shared_properties_impl.type AS COLL_TYPE, collection_shared_properties_impl.subType AS COLL_SUBTYPE, "
                                                + "collection_shared_internals.created_on AS CI_CR, collection_shared_internals.last_modified AS CI_LM, collection_shared_internals.coll_status AS CI_STATUS, "
                                                + "collection_shared_name_field.field_type AS CNF_FT, collection_shared_name_field.field_name AS CNF_FN, collection_shared_name_field.field_value AS CNF_FV "
                                                + "FROM collection_shared_properties_impl INNER JOIN collection_shared_internals ON collection_shared_properties_impl.id = collection_shared_internals.collection_shared_properties "
                                                + "INNER JOIN collection_shared_name_field ON collection_shared_properties_impl.id = collection_shared_name_field.collection_shared_properties "
                                                + "WHERE collection_shared_properties_impl.type = ? AND collection_shared_properties_impl.subType = ?";


    protected static String FIND_TEXT_FIELD_TABLE = "SELECT collection_shared_text_field.field_type AS TF_FTYPE, collection_shared_text_field.field_name AS TF_FNAME, collection_shared_text_field.field_value AS TF_FVALUE "
                                                + "FROM collection_shared_text_field "
                                                + "WHERE collection_shared_text_field.collection_shared_properties = ?";

    protected static String FIND_NUMERIC_FIELD_TABLE = "SELECT collection_shared_numeric_field.field_type AS NF_FTYPE, collection_shared_numeric_field.field_name AS NF_FNAME, collection_shared_numeric_field.field_value AS NF_FVALUE "
                                                + "FROM collection_shared_numeric_field "
                                                + "WHERE collection_shared_numeric_field.collection_shared_properties = ?";

    protected static String FIND_BOOLEAN_FIELD_TABLE = "SELECT collection_shared_boolean_field.field_type AS BF_FTYPE, collection_shared_boolean_field.field_name AS BF_FNAME, collection_shared_boolean_field.field_value AS BF_FVALUE "
                                                + "FROM collection_shared_boolean_field "
                                                + "WHERE collection_shared_boolean_field.collection_shared_properties = ?";
    
    protected static String FIND_IMAGE_FIELD_TABLE = "SELECT collection_shared_image_field.id AS IF_ID, collection_shared_image_field.field_type AS IF_FTYPE, collection_shared_image_field.field_name AS IF_FNAME, "
                                                + "collection_shared_image.id AS I_ID, collection_shared_image.location AS I_LOC, collection_shared_image.width AS I_WIDTH, collection_shared_image.height AS I_HEIGHT "
                                                + "FROM collection_shared_image_field INNER JOIN collection_shared_image ON collection_shared_image_field.id = collection_shared_image.image_field "
                                                + "WHERE collection_shared_image_field.collection_shared_properties = ?";

    protected static String FIND_LINKEDCOLL_FIELD_TABLE = "SELECT collection_shared_linked_coll_field.field_type AS LF_FTYPE, collection_shared_linked_coll_field.field_name AS LF_FNAME, collection_shared_linked_coll_field.field_value AS LF_FVALUE "
                                                + "FROM collection_shared_linked_coll_field "
                                                + "WHERE collection_shared_linked_coll_field.collection_shared_properties = ?";

    protected static String FIND_COLL_RELATIONSHIPS_TABLE = "SELECT collection_relationships.coll_1 AS CR_coll1, collection_relationships.coll_2 AS CR_coll2, collection_relationships.rel_type AS CR_TYPE "
                                                + "FROM collection_relationships "
                                                + "WHERE collection_relationships.coll_1 = ? OR collection_relationships.coll_2 = ?";
    
    protected static String DELETE_SIMPLE_COLL_TYPE_TABLE = "DELETE FROM simple_collection_type_impl WHERE simple_collection_type_impl.id = ?";
}
