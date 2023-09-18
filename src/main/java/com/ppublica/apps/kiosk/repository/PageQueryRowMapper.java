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
        Long id = rs.getLong("P_ID");
        String pageType = rs.getString("P_PTYPE");
        String pageName = rs.getString("P_PNAME");
        Long pageInternalsLocale = rs.getLong("PI_LOC");
        
        LocalDate createdOn = rs.getDate("PI_CR").toLocalDate();
        LocalDateTime lastModified = rs.getTimestamp("PI_LM").toLocalDateTime();
        PageStatus pageStatus = PageStatus.valueOf(rs.getString("PI_PSTATUS"));
        
        PageInternals pageInternals = new PageInternals(pageInternalsLocale, pageStatus, createdOn, lastModified);

        return new Page(id, pageType, pageName, pageInternals, null, null);
        
    }
}
