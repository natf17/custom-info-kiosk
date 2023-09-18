package com.ppublica.apps.kiosk.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ppublica.apps.kiosk.domain.model.cms.pages.ButtonField;

public class ButtonFieldRowMapper implements RowMapper<ButtonField> {

    @Override
    public ButtonField mapRow(ResultSet rs, int rowNum) throws SQLException {
        String fieldName = rs.getString("BF_FNAME");
        Boolean fieldValue = rs.getBoolean("BF_FVALUE");

        return new ButtonField(fieldName, fieldValue);
        
    }
}
