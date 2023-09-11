package com.ppublica.apps.kiosk.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FieldContainerQueryRowMapper implements RowMapper<FieldContainerQueryResults> {

    @Override
    public FieldContainerQueryResults mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("field_container.id");
        String containerName = rs.getString("field_container.field_container_name");
        Boolean hasNestedContainer = rs.getBoolean("field_container.has_nested_container");

        return new FieldContainerQueryResults(id, containerName, hasNestedContainer);
        
    }
}
