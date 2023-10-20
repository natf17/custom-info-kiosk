package com.ppublica.apps.kiosk.repository.collection;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ppublica.apps.kiosk.domain.model.cms.collection.BooleanField;

public class BooleanFieldRowMapper implements RowMapper<BooleanField> {
    @Override
    public BooleanField mapRow(ResultSet rs, int rowNum) throws SQLException {
        String fieldType = rs.getString("BF_FTYPE");
        String fieldName = rs.getString("BF_FNAME");
        Boolean fieldValue = rs.getBoolean("BF_FVALUE");

        return new BooleanField(fieldType, fieldName, fieldValue);
        
    }
}