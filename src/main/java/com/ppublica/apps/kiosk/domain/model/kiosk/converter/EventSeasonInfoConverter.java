package com.ppublica.apps.kiosk.domain.model.kiosk.converter;

import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionLocalizedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedProperties;
import com.ppublica.apps.kiosk.domain.model.cms.collection.CollectionSharedPropertiesImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.cms.collection.TextField;
import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultEventSeasonPiece;
import com.ppublica.apps.kiosk.domain.model.kiosk.EventSeason;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;

public class EventSeasonInfoConverter {
    protected ToCmsCollectionConverter toCmsCollectionConverter = new ToCmsCollectionConverter();
    protected ToKioskCollectionConverter toKioskCollectionConverter = new ToKioskCollectionConverter();

    // localized fields
    public static final String THEME_FIELD_TYPE = "Theme";
    public static final String DURATION_TEXT_FIELD_TYPE = "DurationText";

    // non-localized fields
    public static final String SEASON_TYPE_FIELD_TYPE = "EventSeasonType";
    public static final String SERVICE_YEAR_FIELD_TYPE = "ServiceYear";
    public static final String DURATION_DAYS_FIELD_TYPE = "DurationDays";


    public EventSeason convert(CollectionSharedProperties sharedCmsPiece, CollectionLocalizedProperties localizedCmsPiece) {
        List<TextField> locTextFields = localizedCmsPiece.locTextFields();


        List<NumericField> sharedNumericFields = sharedCmsPiece.numericFields();
        List<TextField> sharedTextFields = sharedCmsPiece.textFields();

        KioskCollectionField<Integer> serviceYear = null;
        KioskCollectionField<Integer> durationDays = null;
        for(NumericField numericField : sharedNumericFields) {
            if(numericField.getFieldType().equals(SERVICE_YEAR_FIELD_TYPE)) {
                serviceYear = toKioskCollectionConverter.toIntField(numericField, false);
            }

            if(numericField.getFieldType().equals(DURATION_DAYS_FIELD_TYPE)) {
                durationDays = toKioskCollectionConverter.toIntField(numericField, false);
            }
        }

        KioskCollectionField<String> eventSeasonType = null;
        for(TextField textField : sharedTextFields) {
            if(textField.getFieldType().equals(SEASON_TYPE_FIELD_TYPE)) {
                eventSeasonType = toKioskCollectionConverter.toStringField(textField, false);
            }
        }


        KioskCollectionField<String> theme = null;
        KioskCollectionField<String> durationText = null;
        for(TextField textField : locTextFields) {
            if(textField.getFieldType().equals(THEME_FIELD_TYPE)) {
                theme = toKioskCollectionConverter.toStringField(textField);
            }

            if(textField.getFieldType().equals(DURATION_TEXT_FIELD_TYPE)) {
                durationText = toKioskCollectionConverter.toStringField(textField);
            }

        }


        return new DefaultEventSeasonPiece.Builder()
                                    .seasonType(eventSeasonType)
                                    .durationDays(durationDays)
                                    .theme(theme)
                                    .serviceYear(serviceYear)
                                    .durationText(durationText)
                                    .build();
        
    }

    public void transferKioskRepToCmsBuilders(CollectionSharedPropertiesImpl.Builder sharedCmsBuilder, CollectionLocalizedPropertiesImpl.Builder localizedCmsBuilder, EventSeason eventSeasonInfo) {
        localizedCmsBuilder
            .addTextField(toCmsCollectionConverter.toTextField(eventSeasonInfo.theme(), THEME_FIELD_TYPE))
            .addTextField(toCmsCollectionConverter.toTextField(eventSeasonInfo.durationText(), DURATION_TEXT_FIELD_TYPE));

            // ugly - should be improved
            KioskCollectionField<Integer> levelNum = eventSeasonInfo.durationDays();
            Long value = levelNum.fieldValue() != null ? Long.valueOf(levelNum.fieldValue()) : null;
            KioskCollectionField<Long> levelNum_Long = new KioskCollectionField<Long>(value, levelNum.isLocalized());

            KioskCollectionField<Integer> serviceYear = eventSeasonInfo.serviceYear();
            Long valueServiceYear = levelNum.fieldValue() != null ? Long.valueOf(serviceYear.fieldValue()) : null;
            KioskCollectionField<Long> serviceYear_Long = new KioskCollectionField<Long>(valueServiceYear, serviceYear.isLocalized());


        sharedCmsBuilder.addNumericField(toCmsCollectionConverter.toNumericField(levelNum_Long, DURATION_DAYS_FIELD_TYPE))
                        .addTextField(toCmsCollectionConverter.toTextField(eventSeasonInfo.seasonType(), SEASON_TYPE_FIELD_TYPE))
                        .addNumericField(toCmsCollectionConverter.toNumericField(serviceYear_Long, SERVICE_YEAR_FIELD_TYPE));
        
    }
}
