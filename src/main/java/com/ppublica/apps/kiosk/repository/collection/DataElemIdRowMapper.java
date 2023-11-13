package com.ppublica.apps.kiosk.repository.collection;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DataElemIdRowMapper implements RowMapper<Long> {

    @Override
    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long dataElemId = rs.getLong("ELEM_ID");
    
        return dataElemId;
    }

}