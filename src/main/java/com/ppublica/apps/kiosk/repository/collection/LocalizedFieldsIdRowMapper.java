package com.ppublica.apps.kiosk.repository.collection;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LocalizedFieldsIdRowMapper implements RowMapper<Long> {

    @Override
    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long localizedFieldsId = rs.getLong("LF_ID");
    
        return localizedFieldsId;
    }

}
