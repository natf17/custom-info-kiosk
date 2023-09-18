package com.ppublica.apps.kiosk.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ppublica.apps.kiosk.domain.model.cms.pages.RichTextLongDescriptionField;

public class RichTextLongDescriptionFieldRowMapper implements RowMapper<RichTextLongDescriptionField> {

    @Override
    public RichTextLongDescriptionField mapRow(ResultSet rs, int rowNum) throws SQLException {
        String fieldName = rs.getString("RTLDF_FNAME");
        String fieldValue = rs.getString("RTLDF_FVALUE");

        return new RichTextLongDescriptionField(fieldName, fieldValue);
        
    }
}
