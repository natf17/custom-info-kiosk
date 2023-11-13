package com.ppublica.apps.kiosk.repository.collection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionInternals;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionNameField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public class LocalizedFieldsResultsRowMapper implements RowMapper<LocalizedFieldsResults> {

    @Override
    public LocalizedFieldsResults mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long localizedFieldsId = rs.getLong("LF_ID");


        Long locInternalsLocale = rs.getLong("CLI_LOC");
        LocalDate locInternalsCreatedOn = rs.getDate("CLI_CR").toLocalDate();
        LocalDateTime locInternalsLastModified = rs.getTimestamp("CLI_LM").toLocalDateTime();
        PageStatus locInternalsStatus = PageStatus.valueOf(rs.getString("CLI_STATUS"));
        CollectionInternals localizedCollInternals = new CollectionInternals(locInternalsLocale, locInternalsStatus, locInternalsCreatedOn, locInternalsLastModified);

        String collectionNameFName = rs.getString("DCNF_FN");
        String collectionNameFValue = rs.getString("DCNF_FV");
        CollectionNameField locCollNameField = new CollectionNameField(collectionNameFName, collectionNameFValue);

    
    

        return new LocalizedFieldsResults(localizedFieldsId, localizedCollInternals, locCollNameField);
    }

}
