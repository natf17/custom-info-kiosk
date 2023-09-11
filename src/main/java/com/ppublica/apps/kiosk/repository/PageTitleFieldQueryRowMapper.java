package com.ppublica.apps.kiosk.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ppublica.apps.kiosk.domain.model.cms.pages.PageTitleField;

public class PageTitleFieldQueryRowMapper implements RowMapper<PageTitleField> {

    @Override
    public PageTitleField mapRow(ResultSet rs, int rowNum) throws SQLException {
        String fieldName = rs.getString("page_title_field.field_name");
        String fieldValue = rs.getString("page_title_field.field_value");

        return new PageTitleField(fieldName, fieldValue);
    }

}
