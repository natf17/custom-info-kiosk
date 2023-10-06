package com.ppublica.apps.kiosk.domain.model.pages;

import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;

public class EditedMapContainer {
    private EditedPageFieldPayload<Image> defaultMap;
    private EditedPageFieldPayload<Image> bathroomsMap;
    private EditedPageFieldPayload<Image> waterFountainsMap;
    private EditedPageFieldPayload<Image> firstAidMap;
    private EditedPageFieldPayload<Image> donationsMap;

    public EditedMapContainer(EditedPageFieldPayload<Image> defaultMap, EditedPageFieldPayload<Image> bathroomsMap, EditedPageFieldPayload<Image> waterFountainsMap,
                        EditedPageFieldPayload<Image> firstAidMap, EditedPageFieldPayload<Image> donationsMap) {

        this.defaultMap = defaultMap;
        this.bathroomsMap = bathroomsMap;
        this.waterFountainsMap = waterFountainsMap;
        this.firstAidMap = firstAidMap;
        this.donationsMap = donationsMap;
    }

    public EditedPageFieldPayload<Image> getDefaultMapField() {
        return this.defaultMap;
    }

    public EditedPageFieldPayload<Image> getBathroomsMapField() {
        return this.bathroomsMap;
    }

    public EditedPageFieldPayload<Image> getWaterFountainsMapField() {
        return this.waterFountainsMap;
    }

    public EditedPageFieldPayload<Image> getFirstAidMapField() {
        return this.firstAidMap;
    }

    public EditedPageFieldPayload<Image> getDonationsMapField() {
        return this.donationsMap;
    }

    public static EditedMapContainer empty(String defaultMapFieldName, String bathroomsMapFieldName, String waterFountainsMapFieldName,
                                                    String firstAidMapFieldName, String donationsMapFieldName) {
        EditedPageFieldPayload<Image> defaultMap = new EditedPageFieldPayload<Image>(defaultMapFieldName, null);
        EditedPageFieldPayload<Image> bathroomsMap = new EditedPageFieldPayload<Image>(bathroomsMapFieldName, null);
        EditedPageFieldPayload<Image> waterFountainsMap = new EditedPageFieldPayload<Image>(waterFountainsMapFieldName, null);
        EditedPageFieldPayload<Image> firstAidMap = new EditedPageFieldPayload<Image>(firstAidMapFieldName, null);
        EditedPageFieldPayload<Image> donationsMap = new EditedPageFieldPayload<Image>(donationsMapFieldName, null);

        return new EditedMapContainer(defaultMap, bathroomsMap, waterFountainsMap, firstAidMap, donationsMap);

    }


    public MapContainer toMapContainer(String defaultMapFieldType, String bathroomsMapFieldType, String waterFountainsMapFieldType,
                                                    String firstAidMapFieldType, String donationsMapFieldType) {
                                                    
        KioskPageField<Image> instructionsField = defaultMap.toKioskPageField(defaultMapFieldType, true);
        KioskPageField<Image> brLabelField = bathroomsMap.toKioskPageField(bathroomsMapFieldType, true);
        KioskPageField<Image> waterLabelField = waterFountainsMap.toKioskPageField(waterFountainsMapFieldType, true);
        KioskPageField<Image> firstAidLabelField = firstAidMap.toKioskPageField(firstAidMapFieldType, true);
        KioskPageField<Image> donationsLabelField = donationsMap.toKioskPageField(donationsMapFieldType, true);

        return new MapContainer(instructionsField, brLabelField, waterLabelField, firstAidLabelField, donationsLabelField);
    }
}
