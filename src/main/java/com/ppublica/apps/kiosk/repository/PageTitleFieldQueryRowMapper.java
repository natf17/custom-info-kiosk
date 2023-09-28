package com.ppublica.apps.kiosk.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ppublica.apps.kiosk.domain.model.cms.pages.PageTitleField;

public class PageTitleFieldQueryRowMapper implements RowMapper<PageTitleField> {

    @Override
    public PageTitleField mapRow(ResultSet rs, int rowNum) throws SQLException {
        String fieldType = rs.getString("PTF_FTYPE");
        String fieldName = rs.getString("PTF_FNAME");
        String fieldValue = rs.getString("PTF_FVALUE");

        return new PageTitleField(fieldType, fieldName, fieldValue);
    }

}
