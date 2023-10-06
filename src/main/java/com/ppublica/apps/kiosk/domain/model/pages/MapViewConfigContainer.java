package com.ppublica.apps.kiosk.domain.model.pages;

import com.ppublica.apps.kiosk.domain.model.cms.pages.ButtonField;
import com.ppublica.apps.kiosk.domain.model.cms.pages.FieldContainer;
import com.ppublica.apps.kiosk.domain.model.cms.pages.RegularTextLongDescriptionField;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToCmsPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.converter.ToKioskPageFieldConverter;
import com.ppublica.apps.kiosk.domain.model.pages.selector.MapViewConfigContainerFieldSelector;

public class MapViewConfigContainer {
    private KioskPageField<Boolean> enableFsCustomMaps;
    private KioskPageField<String> clearResults;
    private KioskPageField<String> levelSelect;
    private KioskPageField<String> mapNotAvailable;

    public MapViewConfigContainer(KioskPageField<Boolean> enableFsCustomMaps, KioskPageField<String> clearResults, KioskPageField<String> levelSelect, KioskPageField<String> mapNotAvailable) {
        this.enableFsCustomMaps = enableFsCustomMaps;
        this.clearResults = clearResults;
        this.levelSelect = levelSelect;
        this.mapNotAvailable = mapNotAvailable;
    }

    public KioskPageField<Boolean> enableFsCustomMapsField() {
        return this.enableFsCustomMaps;
    }

    public KioskPageField<String> getClearResultsField() {
        return this.clearResults;
    }

    public KioskPageField<String> getLevelSelectField() {
        return this.levelSelect;
    }

    public KioskPageField<String> getMapNotAvailableField() {
        return this.mapNotAvailable;
    }
    
    public FieldContainer toFieldContainer(String containerName, ToCmsPageFieldConverter converter) {

        FieldContainer tapWidgetContainer = new FieldContainer.Builder()
            .addButtonField(converter.toButtonField(enableFsCustomMaps))
            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(clearResults))
            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(levelSelect))
            .addRegularTextLongDescriptionField(converter.toRegTextLongDescr(mapNotAvailable))
            .fieldContainerName(containerName)
            .build();

        return tapWidgetContainer;
    }

    /*
     * Expects a fieldContainer with three RegularTextLongDescriptionFields and one ButtonField
     */
    public static MapViewConfigContainer fromFieldContainer(FieldContainer fieldContainer, ToKioskPageFieldConverter converter, MapViewConfigContainerFieldSelector fieldSelector) {
        

        ButtonField cmsEnableFsCustomMapsField = fieldSelector.enableFsCustomMapsField(fieldContainer);
        RegularTextLongDescriptionField cmsClearResultsField = fieldSelector.clearResultsField(fieldContainer);
        RegularTextLongDescriptionField cmsLevelSelectField = fieldSelector.levelSelectField(fieldContainer);
        RegularTextLongDescriptionField cmsMapNotAvailableField = fieldSelector.mapNotAvailableField(fieldContainer);

        KioskPageField<Boolean> enableFsCustomMapsField = converter.toBooleanField(cmsEnableFsCustomMapsField);
        KioskPageField<String> clearResultsField = converter.toStringField(cmsClearResultsField);
        KioskPageField<String> levelSelectField = converter.toStringField(cmsLevelSelectField);
        KioskPageField<String> mapNotAvailableField = converter.toStringField(cmsMapNotAvailableField);
  
        return new MapViewConfigContainer(enableFsCustomMapsField, clearResultsField, levelSelectField, mapNotAvailableField);
    }
}
