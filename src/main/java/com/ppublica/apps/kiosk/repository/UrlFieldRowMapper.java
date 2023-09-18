package com.ppublica.apps.kiosk.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ppublica.apps.kiosk.domain.model.cms.pages.UrlField;

public class UrlFieldRowMapper implements RowMapper<UrlField> {

    @Override
    public UrlField mapRow(ResultSet rs, int rowNum) throws SQLException {
        String fieldName = rs.getString("UF_FNAME");
        String fieldValue = rs.getString("UF_FVALUE");

        return new UrlField(fieldName, fieldValue);
        
    }
}
