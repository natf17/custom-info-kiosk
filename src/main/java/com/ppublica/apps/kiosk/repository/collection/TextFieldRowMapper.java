package com.ppublica.apps.kiosk.repository.collection;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;

public class TextFieldRowMapper implements RowMapper<TextField> {
    @Override
    public TextField mapRow(ResultSet rs, int rowNum) throws SQLException {
        String fieldType = rs.getString("TF_FTYPE");
        String fieldName = rs.getString("TF_FNAME");
        String fieldValue = rs.getString("TF_FVALUE");

        return new TextField(fieldType, fieldName, fieldValue);
        
    }
}
