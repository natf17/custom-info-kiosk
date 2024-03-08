package com.ppublica.apps.kiosk.repository.collection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedInternals;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public class CollectionSharedPropertiesQueryRowMapper implements RowMapper<CollectionSharedPropertiesQueryResults> {
    @Override
    public CollectionSharedPropertiesQueryResults mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("COLL_ID");
        String type = rs.getString("COLL_TYPE");
        String subType = rs.getString("COLL_SUBTYPE");

        String collectionNameFName = rs.getString("CNF_FN");
        String collectionNameFValue = rs.getString("CNF_FV");

        CollectionNameField collectionNameField = new CollectionNameField(collectionNameFName, collectionNameFValue);

        LocalDate createdOn = rs.getDate("CI_CR").toLocalDate();
        LocalDateTime lastModified = rs.getTimestamp("CI_LM").toLocalDateTime();
        PageStatus status = PageStatus.valueOf(rs.getString("CI_STATUS"));
        
        CollectionSharedInternals collectionInternals = new CollectionSharedInternals(status, createdOn, lastModified);

        return new CollectionSharedPropertiesQueryResults(id, type, subType, collectionNameField, collectionInternals);
        
    }
    
}