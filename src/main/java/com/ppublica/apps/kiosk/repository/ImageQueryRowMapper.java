package com.ppublica.apps.kiosk.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.ImageField;

public class ImageQueryRowMapper implements RowMapper<ImageField> {

    @Override
    @Nullable
    public ImageField mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long imageFieldId = rs.getLong("IF_ID");
        String imageFieldName = rs.getString("IF_FNAME");
        Long imageId = rs.getLong("I_ID");
        String imageLocation = rs.getString("I_LOC");
        Integer imageWidth = rs.getInt("I_WIDTH");
        Integer imageLength = rs.getInt("I_HEIGHT");

        Image image = new Image(imageLocation, imageWidth, imageLength);

        return new ImageField(imageFieldName, image);
    }
    
}
