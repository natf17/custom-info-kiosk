package com.ppublica.apps.kiosk.repository.collection;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CollectionRelationshipQueryResultsRowMapper implements RowMapper<CollectionRelationshipQueryResults> {
    @Override
    public CollectionRelationshipQueryResults mapRow(ResultSet rs, int rowNum) throws SQLException {
        String type = rs.getString("CR_TYPE");
        Long relatedEntityId1 = rs.getLong("CR_coll1");
        Long relatedEntityId2 = rs.getLong("CR_coll2");

        //Long relatedEntityId = relatedEntityId1 == n
        return new CollectionRelationshipQueryResults(type, relatedEntityId1, relatedEntityId2);
        
    }
}