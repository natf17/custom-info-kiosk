package com.ppublica.apps.kiosk.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FieldContainerQueryRowMapper implements RowMapper<FieldContainerQueryResults> {

    @Override
    public FieldContainerQueryResults mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("FC_ID");
        String containerName = rs.getString("FC_NAME");
        Boolean hasNestedContainer = rs.getBoolean("FC_HASNESTED");

        return new FieldContainerQueryResults(id, containerName, hasNestedContainer);
        
    }
}
