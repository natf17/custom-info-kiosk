package com.ppublica.apps.kiosk.domain.model.pages;

import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.Image;
import com.ppublica.apps.kiosk.domain.model.cms.pages.ImageField;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToCmsPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToKioskPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.selector.MapContainerSelector;

public class MapContainer {

    private KioskPageField<Image> defaultMap;
    private KioskPageField<Image> bathroomsMap;
    private KioskPageField<Image> waterFountainsMap;
    private KioskPageField<Image> firstAidMap;
    private KioskPageField<Image> donationsMap;

    public MapContainer(KioskPageField<Image> defaultMap, KioskPageField<Image> bathroomsMap, KioskPageField<Image> waterFountainsMap,
                        KioskPageField<Image> firstAidMap, KioskPageField<Image> donationsMap) {

        this.defaultMap = defaultMap;
        this.bathroomsMap = bathroomsMap;
        this.waterFountainsMap = waterFountainsMap;
        this.firstAidMap = firstAidMap;
        this.donationsMap = donationsMap;
    }

    public KioskPageField<Image> getDefaultMapField() {
        return this.defaultMap;
    }

    public KioskPageField<Image> getBathroomsMapField() {
        return this.bathroomsMap;
    }

    public KioskPageField<Image> getWaterFountainsMapField() {
        return this.waterFountainsMap;
    }

    public KioskPageField<Image> getFirstAidMapField() {
        return this.firstAidMap;
    }

    public KioskPageField<Image> getDonationsMapField() {
        return this.donationsMap;
    }

    public FieldContainer toFieldContainer(String containerName, ToCmsPageFieldConverter converter) {

        FieldContainer mapsContainer = new FieldContainer.Builder()
            .addImageField(converter.toImageField(defaultMap))
            .addImageField(converter.toImageField(bathroomsMap))
            .addImageField(converter.toImageField(waterFountainsMap))
            .addImageField(converter.toImageField(firstAidMap))
            .addImageField(converter.toImageField(donationsMap))
            .fieldContainerName(containerName)
            .build();

        return mapsContainer;
    }

    /*
     * Expects a fieldContainer with five ImageFields
     */
    public static MapContainer fromFieldContainer(FieldContainer fieldContainer, ToKioskPageFieldConverter converter, MapContainerSelector fieldSelector) {
        

        ImageField cmsDefaultMapField = fieldSelector.defaultMapField(fieldContainer);
        ImageField cmsBathroomsMapField = fieldSelector.bathroomsMapField(fieldContainer);
        ImageField cmsWaterFountainsMapField = fieldSelector.waterFountainsMapField(fieldContainer);
        ImageField cmsFirstAidMapField = fieldSelector.firstAidMapField(fieldContainer);
        ImageField cmsDonationsMapField = fieldSelector.donationsMapField(fieldContainer);

        KioskPageField<Image> defaultMapField = converter.toImageField(cmsDefaultMapField);
        KioskPageField<Image> bathroomsMapField = converter.toImageField(cmsBathroomsMapField);
        KioskPageField<Image> waterFountainsMapField = converter.toImageField(cmsWaterFountainsMapField);
        KioskPageField<Image> firstAidMapField = converter.toImageField(cmsFirstAidMapField);
        KioskPageField<Image> donationsMapField = converter.toImageField(cmsDonationsMapField);
  
        return new MapContainer(defaultMapField, bathroomsMapField, waterFountainsMapField, firstAidMapField, donationsMapField);
    }


    
}
