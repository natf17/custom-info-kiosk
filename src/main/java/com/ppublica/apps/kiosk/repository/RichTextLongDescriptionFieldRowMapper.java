package com.ppublica.apps.kiosk.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ppublica.apps.kiosk.domain.model.cms.pages.RichTextLongDescriptionField;

public class RichTextLongDescriptionFieldRowMapper implements RowMapper<RichTextLongDescriptionField> {

    @Override
    public RichTextLongDescriptionField mapRow(ResultSet rs, int rowNum) throws SQLException {
        String fieldName = rs.getString("rich_text_long_description_field.field_name");
        String fieldValue = rs.getString("rich_text_long_description_field.field_value");

        return new RichTextLongDescriptionField(fieldName, fieldValue);
        
    }
}
