package com.ppublica.apps.kiosk.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;

public class RegularTextLongDescriptionFieldRowMapper implements RowMapper<RegularTextLongDescriptionField> {

    @Override
    public RegularTextLongDescriptionField mapRow(ResultSet rs, int rowNum) throws SQLException {
        String fieldType = rs.getString("REGTLDF_FTYPE");
        String fieldName = rs.getString("REGTLDF_FNAME");
        String fieldValue = rs.getString("REGTLDF_FVALUE");

        return new RegularTextLongDescriptionField(fieldType, fieldName, fieldValue);
        
    }
}