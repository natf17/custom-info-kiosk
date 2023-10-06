package com.ppublica.apps.kiosk.domain.model.pages.selector;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.ImageField;

public class MapContainerSelector {

    private String defaultMapFieldType;
    private String bathroomsMapFieldType;
    private String waterFountainsMapFieldType;
    private String firstAidMapFieldType;
    private String donationsMapFieldType;

    public MapContainerSelector(String defaultMapFieldType, String bathroomsMapFieldType, String waterFountainsMapFieldType,
                        String firstAidMapFieldType, String donationsMapFieldType) {
        this.defaultMapFieldType = defaultMapFieldType;
        this.bathroomsMapFieldType = bathroomsMapFieldType;
        this.waterFountainsMapFieldType = waterFountainsMapFieldType;
        this.firstAidMapFieldType = firstAidMapFieldType;
        this.donationsMapFieldType = donationsMapFieldType;

    }

    public ImageField defaultMapField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectImageField(defaultMapFieldType);
    }
    
    public ImageField bathroomsMapField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectImageField(bathroomsMapFieldType);
    }

    public ImageField waterFountainsMapField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectImageField(waterFountainsMapFieldType);
    }

    public ImageField firstAidMapField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectImageField(firstAidMapFieldType);
    }

    public ImageField donationsMapField(FieldContainer fieldContainer) {
        FieldSelector fieldSelector = new FieldSelector(fieldContainer);

        return fieldSelector.selectImageField(donationsMapFieldType);
    }
    
}
