package com.ppublica.apps.kiosk.repository.collection;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;

public class NumericFieldRowMapper implements RowMapper<NumericField> {
    @Override
    public NumericField mapRow(ResultSet rs, int rowNum) throws SQLException {
        String fieldType = rs.getString("NF_FTYPE");
        String fieldName = rs.getString("NF_FNAME");
        Long fieldValue = rs.getLong("NF_FVALUE");

        return new NumericField(fieldType, fieldName, fieldValue);
        
    }
}