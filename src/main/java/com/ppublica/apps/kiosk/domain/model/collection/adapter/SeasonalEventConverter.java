package com.ppublica.apps.kiosk.domain.model.collection.adapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionElement;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionElementImpl;
import com.ppublica.apps.kiosk.domain.model.cms.collection.DataCollectionType;
import com.ppublica.apps.kiosk.domain.model.cms.collection.NumericField;
import com.ppublica.apps.kiosk.domain.model.collection.DefaultEventSeason;
import com.ppublica.apps.kiosk.domain.model.collection.DefaultSeasonalEvent;
import com.ppublica.apps.kiosk.domain.model.collection.EventSeason;
import com.ppublica.apps.kiosk.domain.model.collection.KioskCollectionField;

/*
 * Expects all fields to be present when saving and reading.
 */
public class SeasonalEventConverter {
    protected ToCmsCollectionConverter toCmsCollectionConverter = new ToCmsCollectionConverter();
    protected ToKioskCollectionConverter toKioskCollectionConverter = new ToKioskCollectionConverter();

    public static final String SEASONAL_EVENT_FIELD_TYPE = "SeasonalEvent";
    public static final String DURATION_DAYS_FIELD_TYPE = "Duration_Days";
    public static final String SEASON_YEAR_FIELD_TYPE = "Season_Year";


    // requires DefaultSeasonalEvent to have non-null start time
    public List<DataCollectionElement> toDataCollectionElementList(EventSeason season) {
        String fieldNameFiller = season.getAddSeasonDatesText().getFieldName();

        List<DefaultSeasonalEvent> events = season.getEvents();

        List<DataCollectionElement> dataCollectionElements = new ArrayList<>();

        if(events.isEmpty()) {
            events = new ArrayList<>();
            events.add(new DefaultSeasonalEvent(null));
        }

        for(DefaultSeasonalEvent event : events) {
            dataCollectionElements.add(new DataCollectionElementImpl.Builder()  
                                            .addNumericField(
                                                new NumericField(SEASONAL_EVENT_FIELD_TYPE, fieldNameFiller, event.getStartDate() != null ? event.getStartDate().toEpochDay() : null)
                                            )
                                            .addNumericField(toCmsCollectionConverter.toNumericField(season.getDurationDays(), DURATION_DAYS_FIELD_TYPE))
                                            .addNumericField(toCmsCollectionConverter.toNumericField(season.getSeasonYear(), SEASON_YEAR_FIELD_TYPE))
                                            .build()
                                        );

        }

        return dataCollectionElements;
    }

    public void processToBuildEventSeason(DefaultEventSeason.Builder builder, DataCollectionType dataCollectionType) {
        List<DataCollectionElement> elems = dataCollectionType.getLinkedDataElements();
        List<DefaultSeasonalEvent> events = new ArrayList<>();

        if(elems.isEmpty()) {
            return;
        }

        // extract fields from first element
        KioskCollectionField<Long> durationDays = null;
        KioskCollectionField<Long> seasonYear = null;
        for (NumericField numericField : elems.get(0).getNumericFields()) {
            if(numericField.getFieldType().equals(DURATION_DAYS_FIELD_TYPE)) {
                durationDays = toKioskCollectionConverter.toLongField(numericField);
            }

            if(numericField.getFieldType().equals(SEASON_YEAR_FIELD_TYPE)) {
                seasonYear = toKioskCollectionConverter.toLongField(numericField);
            }
        }

        builder.durationDays(durationDays);
        builder.seasonYear(seasonYear);
        

        for (DataCollectionElement elem : elems) {
            for(NumericField numericField : elem.getNumericFields()) {
                if(numericField.getFieldType().equals(SEASONAL_EVENT_FIELD_TYPE) && numericField.getFieldValue() != null) {
                    events.add(new DefaultSeasonalEvent(LocalDate.ofEpochDay(numericField.getFieldValue())));
                }
            }
        }

        builder.events(events);

    }
    
}
