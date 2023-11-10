package com.ppublica.apps.kiosk.repository.collection;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DataElemIdRowMapper implements RowMapper<DataElemQueryResults> {
    @Override
    public DataElemQueryResults mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long elemId = rs.getLong("ELEM_ID");

        return new DataElemQueryResults(elemId);
        
    }
}
