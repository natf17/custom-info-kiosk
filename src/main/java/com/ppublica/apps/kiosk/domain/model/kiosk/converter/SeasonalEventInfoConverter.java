package com.ppublica.apps.kiosk.domain.model.kiosk.converter;

import java.time.LocalDate;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionRelationship;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultSeasonalEventPiece;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.SeasonalEvent;

public class SeasonalEventInfoConverter {
    protected ToCmsCollectionConverter toCmsCollectionConverter = new ToCmsCollectionConverter();
    protected ToKioskCollectionConverter toKioskCollectionConverter = new ToKioskCollectionConverter();

    // non-localized fields
    public static final String START_DATE_FIELD_TYPE = "Start_Date";
    public static final String EVENT_LANG_FIELD_TYPE = "EventLanguage";
    public static final String SEASON_TO_EVENT_REL_FIELD_TYPE = "Season:Event";


    public SeasonalEvent convert(CollectionSharedProperties sharedCmsPiece) {
        List<NumericField> sharedNumericFields = sharedCmsPiece.numericFields();
        List<TextField> sharedTextFields = sharedCmsPiece.textFields();
        List<CollectionRelationship> sharedRelationships = sharedCmsPiece.collectionRelationships();

        KioskCollectionField<LocalDate> startDate = null;
        for(NumericField numericField : sharedNumericFields) {
            if(numericField.getFieldType().equals(START_DATE_FIELD_TYPE)) {
                startDate = toKioskCollectionConverter.toLocalDateField(numericField, false);
            }
        }

        KioskCollectionField<Long> eventSeasonId = null;
        for(CollectionRelationship collRel : sharedRelationships) {
            if(collRel.type().equals(SEASON_TO_EVENT_REL_FIELD_TYPE)) {
                eventSeasonId = toKioskCollectionConverter.toLongField(collRel);
            }
        }

        KioskCollectionField<String> eventLaguange = null;
        for(TextField textField : sharedTextFields) {

            if(textField.getFieldType().equals(EVENT_LANG_FIELD_TYPE)) {
                eventLaguange = toKioskCollectionConverter.toStringField(textField, false);
            }
        }

        return new DefaultSeasonalEventPiece.Builder()
                                    .startDate(startDate)
                                    .eventLanguage(eventLaguange)
                                    .seasonId(eventSeasonId)
                                    .build();
        
    }

    public void transferKioskRepToCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, SeasonalEvent seasonalEventInfo) {

        sharedCmsBuilder.addNumericField(toCmsCollectionConverter.toNumericFieldFromDate(seasonalEventInfo.startDate(), START_DATE_FIELD_TYPE))
                        .addTextField(toCmsCollectionConverter.toTextField(seasonalEventInfo.eventLanguage(), EVENT_LANG_FIELD_TYPE))
                        .addCollectionRelationship(toCmsCollectionConverter.toCollectionRelationship(seasonalEventInfo.seasonId(), SEASON_TO_EVENT_REL_FIELD_TYPE))
                        ;
        
    }
}
