package com.ppublica.apps.kiosk.repository.collection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public class DataCollectionTypeQueryRowMapper implements RowMapper<DataCollectionTypeQueryResults> {

    @Override
    @Nullable
    public DataCollectionTypeQueryResults mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("COLL_ID");
        String type = rs.getString("COLL_TYPE");
        String subType = rs.getString("COLL_SUBTYPE");

        Long collInternalsLocale = rs.getLong("CI_LOC");
        LocalDate collInternalsCreatedOn = rs.getDate("CI_CR").toLocalDate();
        LocalDateTime collInternalsLastModified = rs.getTimestamp("CI_LM").toLocalDateTime();
        PageStatus collInternalsStatus = PageStatus.valueOf(rs.getString("CI_STATUS"));
        CollectionInternals collectionInternals = new CollectionInternals(collInternalsLocale, collInternalsStatus, collInternalsCreatedOn, collInternalsLastModified);


        Long localizedFieldsId = rs.getLong("LF_ID");

        Long locInternalsLocale = rs.getLong("CLI_LOC");
        LocalDate locInternalsCreatedOn = rs.getDate("CLI_CR").toLocalDate();
        LocalDateTime locInternalsLastModified = rs.getTimestamp("CLI_LM").toLocalDateTime();
        PageStatus locInternalsStatus = PageStatus.valueOf(rs.getString("CLI_STATUS"));
        CollectionInternals localizedCollInternals = new CollectionInternals(locInternalsLocale, locInternalsStatus, locInternalsCreatedOn, locInternalsLastModified);


        String collectionNameFName = rs.getString("DCNF_FN");
        String collectionNameFValue = rs.getString("DCNF_FV");
        CollectionNameField locCollNameField = new CollectionNameField(collectionNameFName, collectionNameFValue);

    

        return new DataCollectionTypeQueryResults(id, type, subType, localizedFieldsId, collectionInternals, locCollNameField, localizedCollInternals);
    }
    
}
