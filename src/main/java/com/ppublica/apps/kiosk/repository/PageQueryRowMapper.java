package com.ppublica.apps.kiosk.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Page;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageInternals;
import com.ppublica.apps.kiosk.domain.model.cms.pages.PageStatus;

public class PageQueryRowMapper implements RowMapper<Page> {

    @Override
    public Page mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("page.id");
        String pageType = rs.getString("page.page_type");
        String pageName = rs.getString("page.page_name");
        Long pageInternalsLocale = rs.getLong("page_internals.locale");
        
        LocalDate createdOn = rs.getDate("page_internals.created_on").toLocalDate();
        LocalDateTime lastModified = rs.getTimestamp("page_internals.last_modified").toLocalDateTime();
        PageStatus pageStatus = PageStatus.valueOf(rs.getString("page_internals.page_status"));
        
        PageInternals pageInternals = new PageInternals(pageInternalsLocale, pageStatus, createdOn, lastModified);

        return new Page(id, pageType, pageName, pageInternals, null, null);
        
    }
}
