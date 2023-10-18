package com.ppublica.apps.kiosk.repository.collection;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ppublica.apps.kiosk.domain.model.cms.collection.LinkedCollectionField;

public class LinkedCollectionFieldQueryRowMapper implements RowMapper<LinkedCollectionField> {
    @Override
    public LinkedCollectionField mapRow(ResultSet rs, int rowNum) throws SQLException {
        String fieldType = rs.getString("LF_FTYPE");
        String fieldName = rs.getString("LF_FNAME");
        Long fieldValue = rs.getLong("LF_FVALUE");

        return new LinkedCollectionField(fieldType, fieldName, fieldValue);
        
    }
}