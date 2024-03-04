package com.ppublica.apps.kiosk.service.util.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ppublica.apps.kiosk.domain.model.kiosk.DefaultEventSeasonType;
import com.ppublica.apps.kiosk.domain.model.kiosk.EventSeasonType;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionField;
import com.ppublica.apps.kiosk.domain.model.kiosk.KioskCollectionMetadata;
import com.ppublica.apps.kiosk.domain.model.kiosk.Status;
import com.ppublica.apps.kiosk.service.views.LocalizedField;
import com.ppublica.apps.kiosk.service.views.data.eventseason.EventSeasonAdminView;
import com.ppublica.apps.kiosk.service.views.data.eventseason.EventSeasonView;

public class EventSeasonViewsConverterTest {
    LocalDate testDate;
    LocalDateTime testDateTime;
    Long enLocaleId = 5L;

    EventSeasonType eventSeasonTypeEn;
    EventSeasonType eventSeasonTypeEs;
    EventSeasonView eventSeasonViewEn;
    EventSeasonView eventSeasonViewEs;
    EventSeasonAdminView eventSeasonAdminView;

    EventSeasonViewsConverter converter = new EventSeasonViewsConverter();


    @BeforeEach
    public void setup() {
        this.testDate = LocalDate.of(2024, 2, 27);
        this.testDateTime = LocalDateTime.of(2024, 2, 28, 8, 30);

        eventSeasonTypeEn = new DefaultEventSeasonType.Builder()
                                            .id(1L)
                                            .collectionNameField(new KioskCollectionField<String>("Event season", false))
                                            .kioskCollectionMetadata(new KioskCollectionMetadata(enLocaleId, "en", Status.PUBLISHED, testDate, testDateTime))
                                            .seasonType(new KioskCollectionField<String>("seasonType_fieldValue", false))
                                            .durationDays(new KioskCollectionField<Integer>(3, false))
                                            .theme(new KioskCollectionField<String>("theme_fieldValue", true))
                                            .serviceYear(new KioskCollectionField<Integer>(2024, false))
                                            .durationText(new KioskCollectionField<String>("durationText_fieldValue", true))
                                            .build();

        this.eventSeasonViewEn = new EventSeasonView(1L, "seasonType_fieldValue", 3, "theme_fieldValue", 2024, "", "durationText_fieldValue");

        eventSeasonTypeEs = new DefaultEventSeasonType.Builder()
                                            .id(1L)
                                            .collectionNameField(new KioskCollectionField<String>("Event season", false))
                                            .kioskCollectionMetadata(new KioskCollectionMetadata(2L, "es", Status.PUBLISHED, testDate, testDateTime))
                                            .seasonType(new KioskCollectionField<String>("seasonType_fieldValue", false))
                                            .durationDays(new KioskCollectionField<Integer>(3, false))
                                            .theme(new KioskCollectionField<String>("theme_fieldValue_es", true))
                                            .serviceYear(new KioskCollectionField<Integer>(2024, false))
                                            .durationText(new KioskCollectionField<String>("durationText_fieldValue_es", true))
                                            .build();



        eventSeasonAdminView = new EventSeasonAdminView(1L,
                                                    "seasonType_fieldValue",
                                                    3,
                                                    List.of(new LocalizedField("en", "theme_fieldValue"),
                                                            new LocalizedField("es", "theme_fieldValue_es")),
                                                    2024,
                                                    "",
                                                    List.of(new LocalizedField("en", "durationText_fieldValue"),
                                                            new LocalizedField("es", "durationText_fieldValue_es"))
                                                );

    }
    
    @Test
    public void givenEventSeasonType_buildView_returnsEventSeasonView() {
        EventSeasonView viewActual = converter.buildView(eventSeasonTypeEn);

        Assertions.assertEquals(eventSeasonViewEn, viewActual);
    }

    @Test
    public void givenEventSeasonTypeList_buildAdminView_returnsEventSeasonAdminView() {
        EventSeasonAdminView adminViewActual = converter.buildAdminView(List.of(eventSeasonTypeEn, eventSeasonTypeEs));

        Assertions.assertEquals(eventSeasonAdminView, adminViewActual);

    }
}
