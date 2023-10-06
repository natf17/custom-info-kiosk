package com.ppublica.apps.kiosk.domain.model.pages;

public class EditedMapViewConfigContainer {
    private EditedPageFieldPayload<Boolean> enableFsCustomMaps;
    private EditedPageFieldPayload<String> clearResults;
    private EditedPageFieldPayload<String> levelSelect;
    private EditedPageFieldPayload<String> mapNotAvailable;

    public EditedMapViewConfigContainer(EditedPageFieldPayload<Boolean> enableFsCustomMaps, EditedPageFieldPayload<String> clearResults, EditedPageFieldPayload<String> levelSelect, EditedPageFieldPayload<String> mapNotAvailable) {
        this.enableFsCustomMaps = enableFsCustomMaps;
        this.clearResults = clearResults;
        this.levelSelect = levelSelect;
        this.mapNotAvailable = mapNotAvailable;
    }

    public EditedPageFieldPayload<Boolean> enableFsCustomMapsField() {
        return this.enableFsCustomMaps;
    }

    public EditedPageFieldPayload<String> getClearResultsField() {
        return this.clearResults;
    }

    public EditedPageFieldPayload<String> getLevelSelectField() {
        return this.levelSelect;
    }

    public EditedPageFieldPayload<String> getMapNotAvailableField() {
        return this.mapNotAvailable;
    }
    
    
    
    public static EditedMapViewConfigContainer empty(String enableFsCustomMapsFieldName, String clearResultsFieldName, String levelSelectFieldName, 
                                                        String mapNotAvailableFieldName) {
        EditedPageFieldPayload<Boolean> enableFsCustomMaps = new EditedPageFieldPayload<Boolean>(enableFsCustomMapsFieldName, null);
        EditedPageFieldPayload<String> clearResults = new EditedPageFieldPayload<String>(clearResultsFieldName, null);
        EditedPageFieldPayload<String> levelSelect = new EditedPageFieldPayload<String>(levelSelectFieldName, null);
        EditedPageFieldPayload<String> mapNotAvailable = new EditedPageFieldPayload<String>(mapNotAvailableFieldName, null);

        return new EditedMapViewConfigContainer(enableFsCustomMaps, clearResults, levelSelect, mapNotAvailable);

    }


    public MapViewConfigContainer toMapViewConfigContainer(String enableFsCustomMapsFieldType, String clearResultsFieldType, String levelSelectFieldType, 
                                                        String mapNotAvailableFieldType) {
                                                    
        KioskPageField<Boolean> enableFsCustomMapsField = enableFsCustomMaps.toKioskPageField(enableFsCustomMapsFieldType, true);
        KioskPageField<String> clearResultsField = clearResults.toKioskPageField(clearResultsFieldType, true);
        KioskPageField<String> levelSelectField = levelSelect.toKioskPageField(levelSelectFieldType, true);
        KioskPageField<String> mapNotAvailableField = mapNotAvailable.toKioskPageField(mapNotAvailableFieldType, true);

        return new MapViewConfigContainer(enableFsCustomMapsField, clearResultsField, levelSelectField, mapNotAvailableField);
    }
}
