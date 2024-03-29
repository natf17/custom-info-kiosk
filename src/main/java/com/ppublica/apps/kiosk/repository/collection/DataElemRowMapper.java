package com.ppublica.apps.kiosk.repository.collection;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DataElemRowMapper implements RowMapper<DataElemQueryResults> {
    @Override
    public DataElemQueryResults mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long elemId = rs.getLong("ELEM_ID");
        String type = rs.getString("ELEM_TYPE");

        return new DataElemQueryResults(elemId, type);
        
    }
}
