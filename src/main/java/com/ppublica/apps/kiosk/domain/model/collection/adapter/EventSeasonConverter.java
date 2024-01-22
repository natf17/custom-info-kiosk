package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionTypeImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.LocalizedFields;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.collection.DefaultEventSeason;
import com.ppublica.apps.kiosk.domain.model.collection.EventSeason;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionMetadata;

public class EventSeasonConverter {
    protected ToCmsCollectionConverter toCmsCollectionConverter = new ToCmsCollectionConverter();
    protected ToKioskCollectionConverter toKioskCollectionConverter = new ToKioskCollectionConverter();

    private SeasonalEventConverter eventConverter = new SeasonalEventConverter();

    public static final String DATA_COLLECTION_TYPE = "EventSeason";
    public static final String THEME_FIELD_TYPE = "Theme";
    public static final String DURATION_TEXT_FIELD_TYPE = "DurationText";
    public static final String ADD_SEASON_DATES_TEXT_FIELD_TYPE = "AddDates";

    public void transferEventSeasonRep(DataCollectionTypeImpl.Builder builder, EventSeason eventSeason) {

        List<TextField> localizedTextFields = new ArrayList<>();

        localizedTextFields.add(toCmsCollectionConverter.toTextField(eventSeason.getTheme(), THEME_FIELD_TYPE));
        localizedTextFields.add(toCmsCollectionConverter.toTextField(eventSeason.getDurationText(), DURATION_TEXT_FIELD_TYPE));
        localizedTextFields.add(toCmsCollectionConverter.toTextField(eventSeason.getAddSeasonDatesText(), ADD_SEASON_DATES_TEXT_FIELD_TYPE));

        LocalizedFields localizedFields = new LocalizedFields(toCmsCollectionConverter.toCollectionNameField(eventSeason.getKioskCollectionNameField()), localizedTextFields, null, null, eventSeason.getKioskCollectionMetadata().getCollectionInternals());

        builder.type(DATA_COLLECTION_TYPE)
                .subType(eventSeason.getSeasonYear().getFieldValue().toString())
                .withId(eventSeason.getId())
                .linkedDataElements(eventConverter.toDataCollectionElementList(eventSeason))
                .collectionInternals(eventSeason.getKioskCollectionMetadata().getCollectionInternals())
                .localizedFields(localizedFields)
                .build();
        
    }

    public EventSeason convert(DataCollectionType dataCollectionType) {        
        LocalizedFields localizedFields = dataCollectionType.getLocalizedFields();
        List<TextField> textFields = localizedFields.getTextFields();


        KioskCollectionField<String> theme = null;
        KioskCollectionField<String> durationText = null;
        KioskCollectionField<String> addSeasonDatesText = null;

        for(TextField textField : textFields) {
            if(textField.getFieldType().equals(THEME_FIELD_TYPE)) {
                theme = toKioskCollectionConverter.toStringField(textField);
            }

            if(textField.getFieldType().equals(DURATION_TEXT_FIELD_TYPE)) {
                durationText = toKioskCollectionConverter.toStringField(textField);
            }

            if(textField.getFieldType().equals(ADD_SEASON_DATES_TEXT_FIELD_TYPE)) {
                addSeasonDatesText = toKioskCollectionConverter.toStringField(textField);
            }
        }

        KioskCollectionMetadata kioskCollectionMetadata = KioskCollectionMetadata.fromCollectionInternals(dataCollectionType.getCollectionInternals());

        
        DefaultEventSeason.Builder builder = new DefaultEventSeason.Builder();
        
        // let SeasonalEventConverter obtain/insert missing fields from data elem; if not there, a wrapper/adapter should provide a default
        eventConverter.processToBuildEventSeason(builder, dataCollectionType);

        

        return builder.id(dataCollectionType.getId())
                        .collectionNameField(toKioskCollectionConverter.toStringField(localizedFields.getCollectionNameField()))
                        .kioskCollectionMetadata(kioskCollectionMetadata)
                        .theme(theme)
                        .durationText(durationText)
                        .addSeasonDatesText(addSeasonDatesText)
                        .build();
    }
}
